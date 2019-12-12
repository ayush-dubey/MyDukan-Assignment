package com.mydukan.elasticSearch.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mydukan.elasticSearch.exceptions.AssignmentException;
import com.mydukan.elasticSearch.service.ElasticSearchService;

@Controller
@RequestMapping(value="/file")
public class FileController {

	@Autowired
	ElasticSearchService service;
	
	@RequestMapping(value="/import")
	public void imortExcelFile() {
		 String filePath=null;
		try {
			filePath = ResourceUtils.getFile("classpath:beginner_assignment01.xlsx").getPath();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			service.getDataFromExcel(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AssignmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
