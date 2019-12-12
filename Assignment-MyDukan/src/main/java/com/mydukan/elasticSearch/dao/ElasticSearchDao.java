package com.mydukan.elasticSearch.dao;

import java.util.List;

import com.mydukan.elasticSearch.beans.Group;

public interface ElasticSearchDao {
	
	/**
	 * Saves the group to  the Elastic Search Engine in bulk
	 * Uses RestHighLevelClient for putting data to ES engine
	 * @param groupList
	 */
	public void bulkSave(List<Group> groupList);
	
	/**
	 * getting the group with groupName
	 * @param groupName
	 * @return
	 */
	public Group getGroupFromGroupName(String groupName);
	
	/**
	 * Deletes the document of group from Elastic Search
	 * @param groupName
	 */
	public void deleteGroupByName(String groupName);
	
	/**
	 * Updates group details in elastic search
	 * @param group
	 */
	public void updateGroup(Group group);
	
	/**
	 * Indexes the group in elastic search engine
	 * @param group
	 */
	public void saveGroup(Group group);

	/**
	 * Gets all the groups available from Elastic Search Engine</br>
	 * Takes from & size as input </br>
	 * From indicate the starting range and Size indicates the total number of documents
	 * 
	 * @return
	 */
	public List<Group> getAllGroup(int from,int size);

	void updateGroupProductList(Group group);
}
