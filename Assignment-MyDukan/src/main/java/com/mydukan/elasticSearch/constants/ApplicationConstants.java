package com.mydukan.elasticSearch.constants;

import org.springframework.http.HttpStatus;

public class ApplicationConstants {

	public static final  String PRODUCT_SPREADSHEET_NAME = "product_listing";
	public static final  String GROUP_SPREADSHEET_NAME = "group_listing";
	
	//Product Name	Model Name	Product Serial No	Group Associated	product MRP (rs.)
	public static final  String PRODUCT_NAME = "Product Name";
	public static final  String MODEL_NAME = "Model Name";
	public static final  String PRODUCT_SERIAL_NO = "Product Serial No";
	public static final  String PRODUCT_GROUP_ASSOCIATED = "Group Associated";
	public static final  String PRODUCT_MRP = "product MRP (rs.)";
	
	//group name	group description	isActive
	public static final  String GROUP_NAME = "group name";
	public static final  String GROUP_DESC = "group description";
	public static final  String GROUP_IS_ACTIVE = "isActive";
	
	public static final String GROUP_INDEX="ayush_group";
	public static final String GROUP_TYPE="group_type";
	
	public static final String GROUP_LIST="GROUP_LIST";
	public static final String TOTAL_COST="TOTAL_COST";
	public static final String TOTAL_PRODUCT_COUNT="TOTAL_PRODUCT_COUNT";
	
	public static final String API_RESPONSE=HttpStatus.OK.toString();
	
}
