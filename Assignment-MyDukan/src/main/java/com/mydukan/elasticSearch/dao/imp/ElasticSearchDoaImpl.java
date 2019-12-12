package com.mydukan.elasticSearch.dao.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydukan.elasticSearch.beans.Group;
import com.mydukan.elasticSearch.constants.ApplicationConstants;
import com.mydukan.elasticSearch.dao.ElasticSearchDao;

@Repository
public class ElasticSearchDoaImpl implements ElasticSearchDao {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void bulkSave(List<Group> groupList) {

		BulkRequest bulkReq = new BulkRequest();
		groupList.forEach(group -> {
			IndexRequest indexRequest = new IndexRequest(ApplicationConstants.GROUP_INDEX,
					ApplicationConstants.GROUP_TYPE, group.getName())
							.source(objectMapper.convertValue(group, Map.class));
			bulkReq.add(indexRequest);
		});

		try {
			restHighLevelClient.bulk(bulkReq);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Group getGroupFromGroupName(String groupName) {
		GetRequest getRequest = new GetRequest(ApplicationConstants.GROUP_INDEX, ApplicationConstants.GROUP_TYPE,
				groupName);
		Group group = null;
		try {
			GetResponse getResponse = restHighLevelClient.get(getRequest);
			String groupJSON = getResponse.getSourceAsString();
			if (groupJSON != null)
				group = objectMapper.readValue(groupJSON, Group.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public void deleteGroupByName(String groupName) {
		DeleteRequest deleteRequest = new DeleteRequest(ApplicationConstants.GROUP_INDEX,
				ApplicationConstants.GROUP_TYPE, groupName);
		try {
			DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
	}

	@Override
	public void updateGroup(Group group) {
		Map ObjectMap = objectMapper.convertValue(group, Map.class);
		UpdateRequest updateReq = new UpdateRequest(ApplicationConstants.GROUP_INDEX, ApplicationConstants.GROUP_TYPE,
				group.getName());
		updateReq.doc(ObjectMap);
		try {
			restHighLevelClient.update(updateReq);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateGroupProductList(Group group) {
		Map<String, Object> objectMap = new HashMap<>();
		objectMap.put("products", objectMapper.convertValue(group.getProducts(), List.class));
		UpdateRequest updateReq = new UpdateRequest(ApplicationConstants.GROUP_INDEX, ApplicationConstants.GROUP_TYPE,
				group.getName());
		updateReq.doc(objectMap);
		try {
			restHighLevelClient.update(updateReq);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * @Override public Group getGroupFromProductSerialNo(long productSerialNo) {
	 * SearchRequest searchRequest = new
	 * SearchRequest(ApplicationConstants.GROUP_INDEX); SearchSourceBuilder
	 * sourceBuilder = new SearchSourceBuilder();
	 * sourceBuilder.query(QueryBuilders.termQuery("serialNo", productSerialNo));
	 * searchRequest.source(sourceBuilder); SearchResponse response=null; Group
	 * group =null; try { response=restHighLevelClient.search(searchRequest,
	 * RequestOptions.DEFAULT); SearchHits hits =response.getHits(); String json =
	 * hits.getAt(0).getSourceAsString();; group=objectMapper.readValue(json,
	 * Group.class); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return group; }
	 */
	@Override
	public void saveGroup(Group group) {
		IndexRequest indexRequest = new IndexRequest(ApplicationConstants.GROUP_INDEX, ApplicationConstants.GROUP_TYPE,
				group.getName()).source(objectMapper.convertValue(group, Map.class));
		try {
			restHighLevelClient.index(indexRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Group> getAllGroup(int from, int size) {
		List<Group> groupList = new ArrayList<>();
		SearchRequest searchRequest = new SearchRequest(ApplicationConstants.GROUP_INDEX);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		sourceBuilder.from(from);
		sourceBuilder.size(size);
		searchRequest.source(sourceBuilder);
		SearchResponse response = null;
		Group group = null;
		try {
			response = restHighLevelClient.search(searchRequest);
			SearchHits hits = response.getHits();
			for (SearchHit hit : hits) {
				String json = hit.getSourceAsString();
				group = objectMapper.readValue(json, Group.class);
				groupList.add(group);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groupList;
	}

}
