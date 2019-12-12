package com.mydukan.elasticSearch.beans;

public class Product {

	private long serialNo;
	private String name;
	private double cost;
	private String modelName;
	private Group groupAssociated;
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Group getGroupAssociated() {
		return groupAssociated;
	}
	public void setGroupAssociated(Group groupAssociated) {
		this.groupAssociated = groupAssociated;
	}
	
	
}
