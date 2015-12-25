package com.amani.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import com.amani.model.Compchainstruct;
import com.amani.tools.OrgManager;
import com.amani.tools.SystemFinal;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;

import com.amani.tools.OrgNode;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/orgController")
public class OrgController extends AMN_Action{
	private int orgLevel;
	private String orgId;
	private int maxLevel;
	private Map childOrgs = new HashMap();
	private ArrayList<OrgNode> orgTree = new ArrayList<OrgNode>();
	
	private OrgManager orgManager;
	
	@Action(value = "loadChildOrgs",  results = { 
			 @Result(name = "load_success", type = "json"),
          @Result(name = "load_failure", type = "json")	
       }) 
	public String loadChildOrgs()
	{
		ActionContext ctx = ActionContext.getContext();
		//Map session = ctx.getSession();
		Map application = ctx.getApplication();
		List<Compchainstruct> chain = (List<Compchainstruct>)application.get("chain");
		HashMap orgList = (HashMap)application.get("orgList");
		List<String> childOrgsId = orgManager.getChildOrgs(orgId, chain);
		
		for(int i=0;i<childOrgsId.size();i++)
		{
			String orgId = childOrgsId.get(i);
			this.childOrgs.put(orgId, orgList.get(orgId));
		}
		chain=null;
		orgList=null;
		return SystemFinal.LOAD_SUCCESS;
	}
	public String genOrgTree()
	{
		ActionContext ctx = ActionContext.getContext();
		Map application = ctx.getApplication();
		List<Compchainstruct> chain = (List<Compchainstruct>)application.get("chain");
		HashMap orgList = (HashMap)application.get("orgList");
		
		for(int i=0;i<chain.size();i++)
		{
			Compchainstruct bpf = chain.get(i);
			OrgNode nd = new OrgNode(bpf.getCurcompno(),bpf.getParentcompno(),bpf.getCurcompno()+"-"+(String)orgList.get(bpf.getCurcompno()));
			nd.setChildList((ArrayList<String>)orgManager.getChildOrgs(bpf.getCurcompno(), chain));
			this.orgTree.add(nd);
		}
		adjustNodeDepth();
		return super.SUCCESS;
	}
	
	private void adjustNodeDepth()
	{
		OrgNode root = new OrgNode();
		for(int i=0;i<this.orgTree.size();i++)
		{
			OrgNode nd = orgTree.get(i);
			if(nd.getPid() == null || nd.getPid().equalsIgnoreCase("")||nd.getPid().equalsIgnoreCase("0"))
			{
				this.orgTree.get(i).setDepth(0);
				this.orgTree.get(i).setPid("-1");
				
				root = nd;
				break;
			}
		}
		
		computeChildNodeDepth(root);
		
	}
	private void computeChildNodeDepth(OrgNode nd)
	{
		ArrayList<String> childList = nd.getChildList();
		for(int i=0;i<childList.size();i++)
		{
			for(int j=0;j<this.orgTree.size();j++)
			{
				if(orgTree.get(j).getId().equalsIgnoreCase(childList.get(i)))
				{
					this.orgTree.get(j).setDepth(nd.getDepth()+1);
					computeChildNodeDepth(this.orgTree.get(j));
				}
			}
			
		}
	}
	
	public void setOrgManager(OrgManager orgManager) {
		this.orgManager = orgManager;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public int getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}
	public Map getChildOrgs() {
		
		return childOrgs;
	}
	public void setChildOrgs(Map childOrgs) {
		this.childOrgs = childOrgs;
	}


	public int getMaxLevel() {
		return maxLevel;
	}


	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public ArrayList<OrgNode> getOrgTree() {
		return orgTree;
	}
	public void setOrgTree(ArrayList<OrgNode> orgTree) {
		this.orgTree = orgTree;
	}
	
	
}
