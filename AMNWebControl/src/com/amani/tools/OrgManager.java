package com.amani.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.ActionContext;
import com.amani.dao.AMN_DaoImp;
import com.amani.dao.AMN_PADDaoImp;
import com.amani.model.Commoninfo;
import com.amani.model.Companyinfo;
import com.amani.model.Compchaininfo;
import com.amani.model.Compchainstruct;

import com.amani.tools.CommonTool;
@Repository("orgManager") 
public class OrgManager {
	@Resource(name="amn_Dao")
	private AMN_DaoImp amn_Dao;
	
	@Resource(name="amn_PADDao")
	private AMN_PADDaoImp amn_PADDao;
	

	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}

	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}

	public AMN_PADDaoImp getAmn_PADDao() {
		return amn_PADDao;
	}

	public void setAmn_PADDao(AMN_PADDaoImp amn_PADDao) {
		this.amn_PADDao = amn_PADDao;
	}

	
	//获得连锁店系统中的组织结构的级别字典
	public HashMap getChainLevelNames()
	{
		HashMap chainLevelNames = new HashMap();
		
		//DataTool dataTool = new DataTool();
		
		List<Commoninfo> commoninfos =(List<Commoninfo>)this.amn_Dao.findByHql("From  Commoninfo  commoninfo where infotype='LSJB' ");
		if(commoninfos == null)
		{
			return null;
		}
		for(int i=0;i<commoninfos.size();i++)
		{
			Commoninfo commoninfo = (Commoninfo)commoninfos.get(i);
			chainLevelNames.put(commoninfo.getId().getParentcodekey(), commoninfo.getParentcodevalue());
		}
		commoninfos=null;
		return chainLevelNames;
	}
	//获得连锁店系统的原始组织结构数据
	public List<Compchainstruct> getChainMetaData()
	{
		
		List<Compchainstruct> chain = null;
		
		try{
			chain = (List<Compchainstruct>)amn_Dao.findByHql(" From Compchainstruct compchainstruct order by curcompno "); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return chain;
	}
	
	//Get some orgnization's level
	public int getOrgLevel(String orgId)
	{
		int orgLevel = -1;
		if(CommonTool.isNullForString(orgId).equalsIgnoreCase(""))
			return -1;
		List<Compchainstruct> chain = getChainMetaData();//应该从APPLICATION中取，性能提高
		for(int i=0;i<chain.size();i++)
		{
			Compchainstruct org = chain.get(i);
			if(org.getCurcompno().equalsIgnoreCase(orgId))
			{
				orgLevel = org.getComplevel();
				break;
			}
		}
		
		return orgLevel;
	}
	//获得某组织下属的子组织列表
	public List<String> getChildOrgs(String curOrgId,List<Compchainstruct> chain)
	{
		if(curOrgId==null||curOrgId.equalsIgnoreCase(""))
		{
			return null;
		}
		if(chain == null)
		{
			return null;
		}
		
		List<String> childOrgs = new ArrayList<String>();
		
		ActionContext ctx = ActionContext.getContext();
		Map application = ctx.getApplication();
		HashMap comps = (HashMap)application.get("orgList");
		
		for(int i=0; i<chain.size();i++)
		{
			Compchainstruct org = chain.get(i);
			
			if(CommonTool.isNullForString(org.getParentcompno()).equalsIgnoreCase(curOrgId))
			{
				childOrgs.add(org.getCurcompno());
			}
		}
		comps=null;
		application=null;
		return childOrgs;
	}
	//获得连锁店系统组织的根组织，即总部
	public String getRootOrg(List<Compchainstruct> chain)
	{
		if(chain == null)
		{
			return null;
		}
		String rootOrg = "";
		
		for(int i=0;i<chain.size();i++)
		{
			Compchainstruct compchainstruct = chain.get(i);
			if(compchainstruct.getCurcompno() == null || compchainstruct.getParentcompno().equalsIgnoreCase(""))
			{
				rootOrg = compchainstruct.getCurcompno();
				break;
			}
		}
		return rootOrg;
	}
	//获得某组织的直属上级组织
	public String getParentOrg(String curOrgId,List<Compchainstruct> chain)
	{
		
		if(curOrgId==null||curOrgId.equalsIgnoreCase(""))
		{
			return null;
		}
		if(chain == null)
		{
			return null;
		}
		
		String parentOrg = "";
		
		for(int i=0; i<chain.size();i++)
		{
			Compchainstruct org = chain.get(i);
			
			if(org.getCurcompno().equalsIgnoreCase(curOrgId))
			{
				parentOrg = org.getParentcompno();
				break;
			}
		} 
		return parentOrg;
	}
	//获得连锁店系统所有的组织
	public HashMap getOrgList()
	{
		HashMap comps = new HashMap();
		List<Companyinfo> companyinfos=null;
		//BK_DaoImp dao = new BK_DaoImp();
		try{
			companyinfos= (List<Companyinfo>)amn_Dao.findByHql(" From Companyinfo companyinfo order by compno ");
			for(int i=0;i<companyinfos.size();i++)
			{
				Companyinfo comp = companyinfos.get(i);
				comps.put(comp.getCompno(), comp.getCompname());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		companyinfos=null;
		return comps;
	}
	
	//判断某组织集合中是否有某个特定组织
	public boolean hasOrg(String curOrgId,HashMap orgList)
	{
		Set<String> keys = (Set<String>)orgList.keySet();
		
		Iterator iter = keys.iterator();
		while(iter.hasNext())
		{
			String key = (String)iter.next();
			if(curOrgId.equalsIgnoreCase(key))
			{
				return true;
			}
		}
		iter=null;
		keys=null;
		return false;
	}
	
	public HashMap getLevelOrgs() {
		String curOrgId = CommonTool.getLoginInfo("COMPID");//"001";
		int curOrgLevel = -1;
		HashMap orgList = null;
		HashMap chainLevels = null;
		List<Compchainstruct> chain = null;
		
		HashMap levelOrgs = new HashMap();
		
		orgList = getOrgList();
		curOrgLevel = getOrgLevel(curOrgId);
		chainLevels = getChainLevelNames();
		chain = getChainMetaData();

		
		//获得当前组织
		List<String> curOrg = new ArrayList<String>();
		curOrg.add(curOrgId);
		levelOrgs.put((new Integer(curOrgLevel)).toString(), curOrg);
		
		//获得当前组织的子组织集合
		int childlevel = curOrgLevel+1;
		if(curOrgLevel+1 <= chainLevels.size())
		{
			List<String> childOrgs = new ArrayList<String>();
			childOrgs.add("*");
			List<String> childOrgsTmp = getChildOrgs(curOrgId, chain);
			if(childOrgsTmp != null)
			{
				if(childOrgsTmp.size()!=0)
					childOrgs.addAll(childOrgsTmp);
			}
			//判断子组织的连锁级别
			int index = 0;
			String someChildOrg = childOrgs.get(index);
			while(CommonTool.isNullForString(someChildOrg).equalsIgnoreCase("*"))
			{
				index++;
				if(index<=childOrgs.size()-1)
					someChildOrg = childOrgs.get(index);//????
				else
					someChildOrg = null;
			}
			childlevel = (someChildOrg == null)?childlevel:getOrgLevel(someChildOrg);
			levelOrgs.put((new Integer(childlevel)).toString(), childOrgs);
			
			//设置虚子组织集合
			int nullChildLevel = curOrgLevel+1;
			while(nullChildLevel<childlevel)
			{
				if(levelOrgs.containsKey(new Integer(nullChildLevel).toString()))
				{
					nullChildLevel++;
					continue;
				}
				List<String> nullOrgs = new ArrayList<String>();
				nullOrgs.add("no");
				levelOrgs.put((new Integer(nullChildLevel)).toString(), nullOrgs);
				nullChildLevel++;
			}
			
		}
		//获得子组织后代的组织集合，*替代全部
		int level = childlevel+1;//curOrgLevel+2;
		for(;level<=chainLevels.size();level++)
		{
			List<String> descendantOrgs = new ArrayList<String>();
			descendantOrgs.add("*");
			levelOrgs.put((new Integer(level)).toString(), descendantOrgs);
		}
		//获得当前组织的父组织以及祖先组织
		if(curOrgLevel!=1)
		{
			String tmpOrgId = curOrgId;
			String curOrgParent = getParentOrg(tmpOrgId, chain);
			int curOrgParentLevel = getOrgLevel(curOrgParent);
			
			int plevel = curOrgParentLevel;//curOrgLevel-1;
			while(plevel>=1)
			{
				String parentOrg = getParentOrg(tmpOrgId, chain);
				int parentOrgLevel = getOrgLevel(parentOrg);
				if(parentOrgLevel!=plevel)
				{
					plevel--;
					continue;
				}
				List<String> parentOrgs = new ArrayList<String>();
				parentOrgs.add(parentOrg);
				levelOrgs.put((new Integer(plevel)).toString(), parentOrgs);
				tmpOrgId = parentOrg;
				
				plevel--;
			}
			//设置虚父组织
			int nullParentLevel = curOrgLevel-1;
			while(nullParentLevel>1)
			{
				if(levelOrgs.containsKey(new Integer(nullParentLevel).toString()))
				{
					nullParentLevel--;
					continue;
				}
				
				List<String> nullParentOrgs = new ArrayList<String>();
				nullParentOrgs.add("no");
				levelOrgs.put((new Integer(nullParentLevel)).toString(), nullParentOrgs);
				nullParentLevel--;
			}
		}
		orgList = null;
		chainLevels = null;
		chain = null;
		return levelOrgs;
	}


}


