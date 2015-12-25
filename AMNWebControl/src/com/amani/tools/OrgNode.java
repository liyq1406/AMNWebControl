package com.amani.tools;

import java.util.ArrayList;
public class OrgNode {
	private String id;
	private String pid;
	private int depth;
	private String title;
	private ArrayList<String> childList = new ArrayList<String>();
	
	public OrgNode(String id, String pid, String title) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
	}
	public OrgNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrgNode(String id, String pid, int depth, String title, ArrayList<String> childList) {
		super();
		this.id = id;
		this.pid = pid;
		this.depth = depth;
		this.title = title;
		this.childList = childList;
	}
	public ArrayList<String> getChildList() {
		return childList;
	}
	public void setChildList(ArrayList<String> childList) {
		this.childList = childList;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
