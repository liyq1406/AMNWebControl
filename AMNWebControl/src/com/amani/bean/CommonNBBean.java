package com.amani.bean;

import java.util.List;
import java.util.Map;

public class CommonNBBean {
	private Map<String,String> projectName;
	private List<Map<String,String>> projectDataList;
	private Map<String,String> projectColumnsNames;
	private Map<String,String> allProjectColumnsNames;
	public Map<String, String> getProjectName() {
		return projectName;
	}
	public void setProjectName(Map<String, String> projectName) {
		this.projectName = projectName;
	}
	public List<Map<String, String>> getProjectDataList() {
		return projectDataList;
	}
	public void setProjectDataList(List<Map<String, String>> projectDataList) {
		this.projectDataList = projectDataList;
	}
	public Map<String, String> getProjectColumnsNames() {
		return projectColumnsNames;
	}
	public void setProjectColumnsNames(Map<String, String> projectColumnsNames) {
		this.projectColumnsNames = projectColumnsNames;
	}
	public Map<String, String> getAllProjectColumnsNames() {
		return allProjectColumnsNames;
	}
	public void setAllProjectColumnsNames(Map<String, String> allProjectColumnsNames) {
		this.allProjectColumnsNames = allProjectColumnsNames;
	}
	
}
