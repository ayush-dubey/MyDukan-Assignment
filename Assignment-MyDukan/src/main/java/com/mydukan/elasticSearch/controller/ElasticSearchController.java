package com.mydukan.elasticSearch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mydukan.elasticSearch.beans.AddProductDTO;
import com.mydukan.elasticSearch.beans.Group;
import com.mydukan.elasticSearch.beans.Product;
import com.mydukan.elasticSearch.constants.ApplicationConstants;
import com.mydukan.elasticSearch.exceptions.AssignmentException;
import com.mydukan.elasticSearch.service.ElasticSearchService;

@RestController
@RequestMapping(value="/elastic_search")
public class ElasticSearchController {
	
	@Autowired
	ElasticSearchService service;
	
	@RequestMapping(value="/getGroupFromName",method = RequestMethod.POST) 
	public Map<String,Object> getGroup(@RequestParam String groupName) {
		Group group =service.getGroup(groupName); 
		Map<String,Object> returnMap = new HashMap<>();
		if(group!=null)
			returnMap.put(ApplicationConstants.API_RESPONSE, group);
		else
			returnMap.put(ApplicationConstants.API_RESPONSE, "No Data Found For Group Name : "+groupName);
		return returnMap;
	}
	
	@RequestMapping(value="/updateProductPrice",method = RequestMethod.PUT)
	public Map<String,Object> updateProductPrice(@RequestParam long productSerialNo,
			@RequestParam String groupName, @RequestParam double price)  throws AssignmentException  {
		service.updateProductPrice(productSerialNo, groupName, price);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put(ApplicationConstants.API_RESPONSE, "Data Successfully Updated");
		return returnMap;
	}
	
	@RequestMapping(value="/addProduct",method = RequestMethod.POST)
	public Map<String,Object> addProduct(@RequestBody AddProductDTO dto)  throws AssignmentException {
		Product product = dto.getProduct();
		Group group = dto.getGroup();
		service.addNewProduct(product, group);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put(ApplicationConstants.API_RESPONSE, "Product Added");
		return returnMap;
	}
	
	@RequestMapping(value="/changeProductGroup",method = RequestMethod.POST)
	public Map<String,Object> changeProductGroup(@RequestParam long productSerialNo,
			@RequestParam String currentGroupName,@RequestParam String newGroupName) throws AssignmentException {
		
		service.changeProductGroup(productSerialNo, currentGroupName, newGroupName);
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put(ApplicationConstants.API_RESPONSE, "Product Group Changed");
		return returnMap;
		
	}
	
	@RequestMapping(value="/getAllGroups",method = RequestMethod.POST)
	public Map<String,Object> getAllGroups(Integer from,Integer size)  throws AssignmentException {
		return service.getGroupData(from,size);
	}
}
