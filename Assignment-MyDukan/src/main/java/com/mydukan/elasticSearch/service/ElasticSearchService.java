package com.mydukan.elasticSearch.service;

import java.io.IOException;
import java.util.Map;

import com.mydukan.elasticSearch.beans.Group;
import com.mydukan.elasticSearch.beans.Product;
import com.mydukan.elasticSearch.exceptions.AssignmentException;
import com.mydukan.elasticSearch.exceptions.InvalidExcelFileExcetion;

public interface ElasticSearchService {

	/**
	 * Accepts the file path as string
	 * used for uploading the data through excel files into the elastic search engine
	 * uses apache poi library 
	 * @param filePath
	 * @throws AssignmentException 
	 * @throws InvalidExcelFileExcetion 
	 */
	public void getDataFromExcel(String filePath) throws IOException, AssignmentException, InvalidExcelFileExcetion ;
	
	/**
	 * gets the group data with its associated products using group name
	 * @param groupName
	 * @return Group
	 */
	public Group getGroup(String groupName);
	
	/**
	 * Updates the product price into the DB
	 * @param productSerialNo
	 * @param groupName
	 * @param price
	 */
	public void updateProductPrice(long productSerialNo,String groupName,double price);
	
	/**
	 * Add new product if the with group associated to it
	 * if the group dosen't exists the group will be added to the database,
	 * along with the product, else the product will be added to group list in DB  
	 * @param product
	 * @param group
	 */
	public void addNewProduct(Product product, Group group);

	/**
	 * Changes the group of the given product provided it's serial number
	 * @param productSerialNo
	 * @param currentGroupName
	 * @param newGroupName
	 */
	public void changeProductGroup(long productSerialNo, String currentGroupName, String newGroupName);

	/**
	 * Fetches the data all groups present in the database.</br>
	 * Returns following key value pairs : </br>
	 * GROUP_LIST : list of groups available in the db </br>
	 * TOTAL_PRODUCT_COUNT : total number of products </br>
	 * TOTAL_COST : total value of all products 
	 * @return Map  &ltString, Object&gt
	 */
	public Map<String, Object> getGroupData(Integer from,Integer size);
}
