package com.mydukan.elasticSearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydukan.elasticSearch.beans.Group;
import com.mydukan.elasticSearch.beans.Product;
import com.mydukan.elasticSearch.constants.ApplicationConstants;
import com.mydukan.elasticSearch.dao.ElasticSearchDao;
import com.mydukan.elasticSearch.exceptions.AssignmentException;
import com.mydukan.elasticSearch.exceptions.GroupNotFoundException;
import com.mydukan.elasticSearch.exceptions.InvalidExcelFileExcetion;
import com.mydukan.elasticSearch.service.ElasticSearchService;
import com.mydukan.elasticSearch.utilities.ApplicationUtility;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService{
	
	@Autowired
	ApplicationUtility appUtility;
	
	@Autowired
	ElasticSearchDao esDao;

	@Override
	public void getDataFromExcel(String filePath) throws IOException, AssignmentException, InvalidExcelFileExcetion {

		List<Product> productList = new ArrayList<>();
		List<Group> groupList = new ArrayList<>();
		Map<String,List<List<String>>> data = appUtility.extractDataFromexcel(filePath);
		List<List<String>> productRows = data.get(ApplicationConstants.PRODUCT_SPREADSHEET_NAME);
		List<List<String>> groupRows = data.get(ApplicationConstants.GROUP_SPREADSHEET_NAME);
		/*
		 * if the excel does not contains any of the data of group of product throw exception
		 */
		if(productRows==null || groupRows==null)
			throw new InvalidExcelFileExcetion("The Excel Does not contains the Described Spread Sheets");
		
		int i=0;
		List<String> headers = new ArrayList<>();
		//extracting the data from product details spread sheet
		for(List<String> row : productRows) {
			if(i==0) {
				for(String cellData : row) {
					if(cellData==null || cellData.equals(""))
						break;
					headers.add(cellData);
				}
				i++;
				continue;
			}
			int j=0;
			Product prod  = new Product();
			for(String cellData : row) {
				if (j >= headers.size() /* || cellData==null || cellData.equals("") */)
					break;
				if(cellData==null || cellData.equals(""))
					continue;
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.PRODUCT_NAME))
					prod.setName(cellData);
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.MODEL_NAME))
					prod.setModelName(cellData);
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.PRODUCT_SERIAL_NO))
				{
					try {
						
						prod.setSerialNo(Long.parseLong(cellData));
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.PRODUCT_GROUP_ASSOCIATED)) {
					Group group = new Group();
					group.setName(cellData);
					prod.setGroupAssociated(group);
				}
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.PRODUCT_MRP))
					prod.setCost(Double.parseDouble(cellData));
				j++;
			}
			//if(prod.getName()!=null  && prod.getGroupAssociated()!=null)
			productList.add(prod);
			i++;
		}
		headers.clear();
		i=0;
		//extracting the data from group details spread sheet
		for(List<String> cells : groupRows) {
			if(i==0) {
				for(String cellData : cells) {
					if(cellData==null || cellData.equals(""))
						break;
					headers.add(cellData);
				}
				i++;
				continue;
			}
			int j=0;
			Group group  = new Group();
			for(String cellData : cells) {
				if (j >= headers.size())
					break;
				if(cellData==null || cellData.equals(""))
					continue;
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.GROUP_NAME))
					group.setName(cellData);
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.GROUP_DESC))
					group.setDescription(cellData);
				if(headers.get(j).equalsIgnoreCase(ApplicationConstants.GROUP_IS_ACTIVE))
					group.setIsActive(cellData);
				j++;
			}
			/*
			 * adding the products associated to the group 
			 */
			group.setProducts(productList.stream()
					.filter(product -> group.getName().equalsIgnoreCase(
							product.getGroupAssociated().getName()))
					.collect(Collectors.toList()));
			groupList.add(group);
			i++;
		}
		/*
		 * saving group list which contains the products associated with groups
		 */
		esDao.bulkSave(groupList);
	}

	@Override
	public Group getGroup(String groupName) {
		return esDao.getGroupFromGroupName(groupName);
	}
	
	@Override
	public void updateProductPrice(long productSerialNo,String groupName,double price) {
		Group group = esDao.getGroupFromGroupName(groupName);
		Product product = group.getProducts().stream().filter(prod -> prod.getSerialNo() == productSerialNo).findAny()
				.get();
		product.setCost(price);
		esDao.updateGroup(group);
	}
	
	@Override
	public void addNewProduct(Product product,Group group) {
		 Group groupFromDb= esDao.getGroupFromGroupName(group.getName());
		
		 /*
		  * if the group dosen't exists then add the group with product associated
		  */
		 if(groupFromDb==null) {
			List<Product> productList = new ArrayList<>();
			productList.add(product);
			group.setProducts(productList);
			esDao.saveGroup(group);
		}
		/*
		 * Else add the product to the existing product list and update the  details 
		 */
		else {
			List<Product> productList=groupFromDb.getProducts();
			productList.add(product);
			esDao.updateGroup(groupFromDb);	
		}
		
	}

	@Override
	public void changeProductGroup(long productSerialNo,String currentGroupName,String newGroupName) throws AssignmentException {
		//getting the current group from db
		Group currentGroup = esDao.getGroupFromGroupName(currentGroupName);
		List<Product> productList=currentGroup.getProducts();
		//filtering the desired product
		Product product = currentGroup.getProducts().stream().filter(prod -> prod.getSerialNo() == productSerialNo)
				.findAny().get();
		//removing the product from current group
		List<Product> updateCurrentGroupProductList = currentGroup.getProducts().stream()
				.filter(prod -> prod.getSerialNo()!=productSerialNo).collect(Collectors.toList());
		currentGroup.setProducts(updateCurrentGroupProductList);
		
		//getting new group details
		Group newGroup= esDao.getGroupFromGroupName(newGroupName);
		//getting the product list of new group
		if(newGroup==null)
			throw new GroupNotFoundException("The Requested Group Does Not Exist");
		List<Product> newGroupProductList=newGroup.getProducts();
		//adding the product to existing new group product list
		newGroupProductList.add(product);
		
		/*
		 * saving the changes made to DB
		 */
		esDao.saveGroup(currentGroup);
		esDao.saveGroup(newGroup);
	}
	
	@Override
	public Map<String,Object> getGroupData(Integer from,Integer size){
		Map<String,Object> returnData = new HashMap<>();
		//getting all the data of groups
		List<Group> groupList = esDao.getAllGroup(from,size);
		double totalCost =0.0;
		int totalProductCount = 0;
		/*
		 * iterating over each group to get the total product count & total cost
		 */
		for(Group group :groupList) {
			List<Product> productList = group.getProducts();
			totalProductCount+=productList.size();
			for(Product product : productList) {
				totalCost+=product.getCost();
			}
		}
		/*
		 * putting the values into map to retrun
		 */
		returnData.put(ApplicationConstants.GROUP_LIST, groupList);
		returnData.put(ApplicationConstants.TOTAL_COST, totalCost);
		returnData.put(ApplicationConstants.TOTAL_PRODUCT_COUNT,totalProductCount);
		return returnData;
	}
}
