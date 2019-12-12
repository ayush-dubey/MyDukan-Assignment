package com.mydukan.elasticSearch.beans;

import java.util.List;

public class Group {
	private String name;
	private String description;
	private String isActive;
	private List<Product> products;
	
	public Group() {}
	
	public Group(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	@Override
	public String toString() {
		return "Group [name=" + name + ", description=" + description + ", isActive=" + isActive + ", products="
				+ products + "]";
	}
	
	
}
