package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;

import com.amani.model.Cardchangerule;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Goodsinfo;
import com.amani.model.Projectinfo;
import com.amani.model.Syscommoninfomode;
import com.amani.model.SyscommoninfomodeId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class BC013Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Syscommoninfomode record=(Syscommoninfomode)curMaster;
			if(record!=null && !CommonTool.FormatString(record.getBmodeid()).equals(""))
			{
				String strSql=" update syscommoninfomode set invalid=1 where modeid='"+CommonTool.FormatString(record.getBmodeid())+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
			return false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.saveOrUpdate(curMaster);
		curMaster=null;
		return true;
	}
	
	
	public List<Syscommoninfomode> loadInfoModesByCompId(String strCompId)
	{
		String strSql=" select a.modeid,a.modetype,modename=a.modename,a.parentmodeid,parentmodename=b.modename,a.modesource,compname,a.createdate,a.createemp " +
				"   from compchaininfo ,companyinfo ,syscommoninfomode a   left join syscommoninfomode b on  a.parentmodeid=b.modeid " +
				" where a.modesource=relationcomp and curcomp='"+strCompId+"'  and a.modesource=compno " +
				" and isnull(a.invalid,0)=0 and isnull(b.invalid,0)=0 " +
				" order by a.modetype,a.modeid";
		try
		{
			AnlyResultSet<List<Syscommoninfomode>> analysis = new AnlyResultSet<List<Syscommoninfomode>>() {
				public List<Syscommoninfomode> anlyResultSet(ResultSet rs) {
					List<Syscommoninfomode> returnValue = new ArrayList();
					Syscommoninfomode record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Syscommoninfomode();
							record.setId(new SyscommoninfomodeId(CommonTool.FormatString(rs.getString("modeid")),CommonTool.FormatInteger(rs.getInt("modetype"))));
							record.setBmodetype(CommonTool.FormatInteger(rs.getInt("modetype")));
							record.setBmodeid(CommonTool.FormatString(rs.getString("modeid")));
							if(CommonTool.FormatInteger(rs.getInt("modetype"))==1)
								record.setStrModeTypeText("项目模板");
							else if(CommonTool.FormatInteger(rs.getInt("modetype"))==2)
								record.setStrModeTypeText("产品模板");
							else if(CommonTool.FormatInteger(rs.getInt("modetype"))==3)
								record.setStrModeTypeText("卡种模板");
							record.setModename(rs.getString("modename"));
							record.setModesource(rs.getString("modesource"));
							record.setParentmodeid(CommonTool.FormatString(rs.getString("parentmodeid")));
							record.setParentmodeName(CommonTool.FormatString(rs.getString("parentmodename")));
							record.setModesourceName(CommonTool.FormatString(rs.getString("compname")));
							record.setCreatedate(CommonTool.getDateMask(rs.getString("createdate")));
							record.setCreateemp(CommonTool.FormatString(rs.getString("createemp")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			return (List<Syscommoninfomode>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Syscommoninfomode> loadInfoModesByTypeId(String strCompId,int strTypeId)
	{
		String strSql="  select a.modeid,modename=a.modename from syscommoninfomode a ,compchaininfo  " +
				"  where a.modesource=curcomp  and relationcomp='"+strCompId+"' and curcomp<>'"+strCompId+"' and modetype="+strTypeId+"  and isnull(invalid,0)=0";
		try
		{
			AnlyResultSet<List<Syscommoninfomode>> analysis = new AnlyResultSet<List<Syscommoninfomode>>() {
				public List<Syscommoninfomode> anlyResultSet(ResultSet rs) {
					List<Syscommoninfomode> returnValue = new ArrayList();
					Syscommoninfomode record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Syscommoninfomode();
							record.setBmodeid(CommonTool.FormatString(rs.getString("modeid")));
							record.setModename(CommonTool.FormatString(rs.getString("modename")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			return (List<Syscommoninfomode>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean validateModeId(String strModeId)
	{
		try
		{
			String strSql=" select 1 from syscommoninfomode where modeid='"+strModeId+"' ";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
				public Boolean anlyResultSet(ResultSet rs) {
					Boolean returnValue = true;
					try {
						if (rs != null && rs.next() == true) {
							returnValue=false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =false;
					}
					return returnValue;
				}
			};
			return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	// 项目模板 
	public List<Projectinfo> loadProjectinfo(String strCompId,String strModeId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			/*String strSql=" select prjmodeId,prjno,prjname,prjtype,prjreporttype,selfFlag=(case when prjmodeId='"+strModeId+"' then 1 else 0 end ) " +
					" from projectinfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
			
			AnlyResultSet<List<Projectinfo>> analysis = new AnlyResultSet<List<Projectinfo>>() {
				public List<Projectinfo> anlyResultSet(ResultSet rs) {
					List<Projectinfo> returnValue = new ArrayList();
					Projectinfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Projectinfo();
							record.setBprjmodeId(CommonTool.FormatString(rs.getString("prjmodeId")));
							record.setBprjno(CommonTool.FormatString(rs.getString("prjno")));
							record.setPrjname(CommonTool.FormatString(rs.getString("prjname")));
							record.setSelfFlag(CommonTool.FormatInteger(rs.getInt("selfFlag")));
							record.setPrjtype(CommonTool.FormatString(rs.getString("prjtype")));
							record.setPrjreporttype(CommonTool.FormatString(rs.getString("prjreporttype")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			return (List<Projectinfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);*/
			String strSql=" From Projectinfo projectinfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
			List <Projectinfo> lsProjectinfo=this.amn_Dao.findByHql(strSql);
			if(lsProjectinfo!=null && lsProjectinfo.size()>0)
			{
				for(int i=0;i<lsProjectinfo.size();i++)
				{
					lsProjectinfo.get(i).setBprjmodeId(lsProjectinfo.get(i).getId().getPrjmodeId());
					lsProjectinfo.get(i).setBprjno(lsProjectinfo.get(i).getId().getPrjno());
					lsProjectinfo.get(i).setBprisource(lsProjectinfo.get(i).getId().getPrisource());
					if(CommonTool.FormatString(lsProjectinfo.get(i).getId().getPrisource()).equals(strCompId))
					{
						lsProjectinfo.get(i).setSelfFlag(1);
					}
					else
					{
						lsProjectinfo.get(i).setSelfFlag(0);
					}
				}
			}
			return lsProjectinfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		
	}
	//获得当前项目信息
	public Projectinfo loadProjectinfoById(String strCompId,String strModeId,String strProjectId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			Projectinfo returnRecord=new Projectinfo();
			String strSql=" From Projectinfo projectinfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and prjno='"+strProjectId+"' ";
			List <Projectinfo> lsProjectinfo=this.amn_Dao.findByHql(strSql);
			if(lsProjectinfo!=null && lsProjectinfo.size()>0)
			{
				returnRecord=lsProjectinfo.get(0);
				returnRecord.setBprjmodeId(returnRecord.getId().getPrjmodeId());
				returnRecord.setBprjno(returnRecord.getId().getPrjno());
				if(CommonTool.FormatString(returnRecord.getId().getPrisource()).equals(strCompId))
				{
					returnRecord.setSelfFlag(1);
				}
				else
				{
					returnRecord.setSelfFlag(0);
				}
			}
			return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public boolean validateProjectById(String strCompId,String strModeId,String strProjectId)
	{
		String strFristModeId="";//第一级模板Id
		String strSecondModeId="";//第2级模板ID
		String strThirthModeId="";//第三级模板Id
		//先定位模板门店的连锁级别(暂时支持4级连锁)
		int compLvl=this.dataTool.loadCompLvl(strCompId);
		if(compLvl==2)
		{
			strFristModeId=this.dataTool.loadparentModeId(strModeId);
		}
		else if(compLvl==3)
		{
			strSecondModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		else if(compLvl==4)
		{
			strThirthModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		Projectinfo returnRecord=new Projectinfo();
		String strSql=" select 1 from   projectinfo where prjmodeId in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and prjno='"+strProjectId+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
			public Boolean anlyResultSet(ResultSet rs) {
				boolean returnValue = true;
				try {
					if (rs != null && rs.next() == true) {
						returnValue=false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =false;
				}
				return returnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
	public boolean postProject(Projectinfo curProjectinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curProjectinfo);
			curProjectinfo=null;
			String strSql=" insert projectnameinfo(prjno,prjname,prjtype,prjpricetype,prjreporttype) " +
					" select prjno,prjname,prjtype,prjpricetype,prjreporttype " +
					" from  projectinfo where prjno not in (select prjno from projectnameinfo)" +
					" group by prjno,prjname,prjtype,prjpricetype,prjreporttype ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteProject(Projectinfo curProjectinfo)
	{
		try
		{
			this.amn_Dao.delete(curProjectinfo);
			curProjectinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	//---------------------------------------------产品模板---------------------------------------------
	public List<Goodsinfo> loadGoodsinfo(String strCompId,String strModeId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			
			String strSql=" From Goodsinfo goodsinfo where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
			List <Goodsinfo> lsGoodsinfo=this.amn_Dao.findByHql(strSql);
			if(lsGoodsinfo!=null && lsGoodsinfo.size()>0)
			{
				for(int i=0;i<lsGoodsinfo.size();i++)
				{
					lsGoodsinfo.get(i).setBgoodsmodeid(lsGoodsinfo.get(i).getId().getGoodsmodeid());
					lsGoodsinfo.get(i).setBgoodsno(lsGoodsinfo.get(i).getId().getGoodsno());
					lsGoodsinfo.get(i).setBgoodssource(lsGoodsinfo.get(i).getId().getGoodssource());
					if(CommonTool.FormatString(lsGoodsinfo.get(i).getId().getGoodssource()).equals(strCompId))
					{
						lsGoodsinfo.get(i).setSelfFlag(1);
					}
					else
					{
						lsGoodsinfo.get(i).setSelfFlag(0);
					}
				}
			}
			return lsGoodsinfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		
	}
	//获得当前项目信息
	public Goodsinfo loadGoodsinfoById(String strCompId,String strModeId,String strGoodsId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			Goodsinfo returnRecord=new Goodsinfo();
			String strSql=" From Goodsinfo goodsinfo where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and goodsno='"+strGoodsId+"' ";
			List <Goodsinfo> lsGoodsinfo=this.amn_Dao.findByHql(strSql);
			if(lsGoodsinfo!=null && lsGoodsinfo.size()>0)
			{
				returnRecord=lsGoodsinfo.get(0);
				returnRecord.setBgoodsmodeid(returnRecord.getId().getGoodsmodeid());
				returnRecord.setBgoodsno(returnRecord.getId().getGoodsno());
				returnRecord.setBgoodssource(returnRecord.getId().getGoodssource());
				if(CommonTool.FormatString(returnRecord.getId().getGoodssource()).equals(strCompId))
				{
					returnRecord.setSelfFlag(1);
				}
				else
				{
					returnRecord.setSelfFlag(0);
				}
			}
			return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	

	
	public boolean validateGoodsById(String strCompId,String strModeId,String strGoodsId)
	{
		String strFristModeId="";//第一级模板Id
		String strSecondModeId="";//第2级模板ID
		String strThirthModeId="";//第三级模板Id
		//先定位模板门店的连锁级别(暂时支持4级连锁)
		int compLvl=this.dataTool.loadCompLvl(strCompId);
		if(compLvl==2)
		{
			strFristModeId=this.dataTool.loadparentModeId(strModeId);
		}
		else if(compLvl==3)
		{
			strSecondModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		else if(compLvl==4)
		{
			strThirthModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		String strSql=" select 1 from   goodsinfo where goodsmodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and goodsno='"+strGoodsId+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
			public Boolean anlyResultSet(ResultSet rs) {
				boolean returnValue = true;
				try {
					if (rs != null && rs.next() == true) {
						returnValue=false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =false;
				}
				return returnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
	public boolean postGoods(Goodsinfo curGoodsinfo)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curGoodsinfo);
			String strSql=" if not exists (select 1 from  goodsnameinfo where goodsno='"+curGoodsinfo.getId().getGoodsno()+"')  ";
			strSql=strSql+" begin ";
			strSql=strSql+" insert goodsnameinfo(goodsno,goodsbarno,goodsname,goodstype,goodspricetype)" +
					      "	 select goodsno,goodsbarno,goodsname,goodstype,goodspricetype " +
					      "  from goodsinfo where goodsmodeid='"+curGoodsinfo.getId().getGoodsmodeid()+"' and  goodsno='"+curGoodsinfo.getId().getGoodsno()+"'  ";
			strSql=strSql+" end ";
			strSql=strSql+" else ";
			strSql=strSql+" begin ";
			strSql=strSql+"  update a  set a.goodsname=b.goodsname , a.goodstype=b.goodstype,a.goodspricetype=b.goodspricetype " +
					      "   from goodsnameinfo a,goodsinfo b" +
					      "   where b.goodsmodeid='"+curGoodsinfo.getId().getGoodsmodeid()+"' and  b.goodsno='"+curGoodsinfo.getId().getGoodsno()+"' and a.goodsno=b.goodsno ";	
			strSql=strSql+" end ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteGoods(Goodsinfo curGoodsinfo)
	{
		try
		{
			this.amn_Dao.delete(curGoodsinfo);
			curGoodsinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	//---------------------------------------------卡类型模板---------------------------------------------
	public List<Cardtypeinfo> loadCardtypeinfo(String strCompId,String strModeId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			
			String strSql=" From Cardtypeinfo cardtypeinfo where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') ";
			List <Cardtypeinfo> lsCardtypeinfo=this.amn_Dao.findByHql(strSql);
			if(lsCardtypeinfo!=null && lsCardtypeinfo.size()>0)
			{
				for(int i=0;i<lsCardtypeinfo.size();i++)
				{
					lsCardtypeinfo.get(i).setBcardtypemodeid(lsCardtypeinfo.get(i).getId().getCardtypemodeid());
					lsCardtypeinfo.get(i).setBcardtypeno(lsCardtypeinfo.get(i).getId().getCardtypeno());
					lsCardtypeinfo.get(i).setBcardtypesource(lsCardtypeinfo.get(i).getId().getCardtypesource());
					if(CommonTool.FormatString(lsCardtypeinfo.get(i).getId().getCardtypesource()).equals(strCompId))
					{
						lsCardtypeinfo.get(i).setSelfFlag(1);
					}
					else
					{
						lsCardtypeinfo.get(i).setSelfFlag(0);
					}
				}
			}
			return lsCardtypeinfo;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		
	}
	
	//---------------------------------------------卡类型模板---------------------------------------------
	public List<Cardchangerule> loadCardchangerule(String strCompId,String strModeId,String cardTypeId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			
			String strSql=" From Cardchangerule cardchangerule where rulemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno='"+cardTypeId+"' ";
			return (List<Cardchangerule>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		
	}
	//获得当前项目信息
	public Cardtypeinfo loadCardtypeinfoById(String strCompId,String strModeId,String strCardTypeId)
	{
		try
		{
			String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			Cardtypeinfo returnRecord=new Cardtypeinfo();
			String strSql=" From Cardtypeinfo cardtypeinfo where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno='"+strCardTypeId+"' ";
			List <Cardtypeinfo> lsCardtypeinfo=this.amn_Dao.findByHql(strSql);
			if(lsCardtypeinfo!=null && lsCardtypeinfo.size()>0)
			{
				returnRecord=lsCardtypeinfo.get(0);
				returnRecord.setBcardtypemodeid(returnRecord.getId().getCardtypemodeid());
				returnRecord.setBcardtypeno(returnRecord.getId().getCardtypeno());
				returnRecord.setBcardtypesource(returnRecord.getId().getCardtypesource());
				if(CommonTool.FormatString(returnRecord.getId().getCardtypesource()).equals(strCompId))
				{
					returnRecord.setSelfFlag(1);
				}
				else
				{
					returnRecord.setSelfFlag(0);
				}
			}
			return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	

	
	public boolean validateCardTypeById(String strCompId,String strModeId,String strCardTypeId)
	{
		String strFristModeId="";//第一级模板Id
		String strSecondModeId="";//第2级模板ID
		String strThirthModeId="";//第三级模板Id
		//先定位模板门店的连锁级别(暂时支持4级连锁)
		int compLvl=this.dataTool.loadCompLvl(strCompId);
		if(compLvl==2)
		{
			strFristModeId=this.dataTool.loadparentModeId(strModeId);
		}
		else if(compLvl==3)
		{
			strSecondModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		else if(compLvl==4)
		{
			strThirthModeId=this.dataTool.loadparentModeId(strModeId);
			if(!strThirthModeId.equals(""))
				strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
			if(!strSecondModeId.equals(""))
				strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
		}
		String strSql=" select 1 from   cardtypeinfo where cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and cardtypeno='"+strCardTypeId+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
			public Boolean anlyResultSet(ResultSet rs) {
				boolean returnValue = true;
				try {
					if (rs != null && rs.next() == true) {
						returnValue=false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =false;
				}
				return returnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
	public boolean postCardType(Cardtypeinfo curCardtypeinfo,List<Cardchangerule> lsCardRule)
	{
		try
		{
			this.amn_Dao.saveOrUpdate(curCardtypeinfo);
			curCardtypeinfo=null;
			if(lsCardRule!=null && lsCardRule.size()>0)
			{
				for(int i=0;i<lsCardRule.size();i++)
				{
					this.amn_Dao.saveOrUpdate(lsCardRule.get(i));
				}
			}
			String strSql="insert cardtypenameinfo(cardtypeno,cardtypename)" +
					" select cardtypeno,cardtypename " +
					" from cardtypeinfo where cardtypeno not in (select cardtypeno from cardtypenameinfo ) group by cardtypeno,cardtypename ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteCardType(Cardtypeinfo curCardtypeinfo)
	{
		try
		{
			this.amn_Dao.delete(curCardtypeinfo);
			curCardtypeinfo=null;
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postDownProjectInfo(String strTargerModeId,String strProject)
	{
		try
		{
			String strSql=" delete projectinfo where prjmodeId='"+strTargerModeId+"' and prjno='"+strProject+"' ";
			strSql=strSql+" insert projectinfo(prjmodeId,prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice" +
					",hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,memberprice,salelowprice,needhairflag,useflag,saleflag,rateflag," +
					"prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,prisource,finaltype,newcosttc,oldcosttc)";
			strSql=strSql+" select '"+strTargerModeId+"',prjno,prjname,prjtype,prjpricetype,prjreporttype,saleunit,saleprice,msalecount,msaleprice,rsalecount,rsaleprice" +
					",hsalecount,hsaleprice,ysalecount,ysaleprice,onecountprice,onepageprice,memberprice,salelowprice,needhairflag,useflag,saleflag,rateflag, " +
					" prjsaletype,editflag,pointtype,pointvalue,costtype,costprice,kyjrate,ktcrate,lyjrate,ltcrate,'"+strTargerModeId.replace("SPM", "")+"',finaltype,newcosttc,oldcosttc " +
					" from projectinfo where prjmodeId='SPM' and prjno='"+strProject+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public String loadMaxGoodsuniquebar()
	{
		try
		{
			String strSql=" select goodsuniquebar=max(goodsuniquebar) from goodsinfo with(nolock)  ";
			AnlyResultSet<String> analysis = new AnlyResultSet<String>() {
				public String anlyResultSet(ResultSet rs) {
					String returnValue = "";
					try {
						if (rs != null && rs.next() == true) {
							returnValue=rs.getString("goodsuniquebar");
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue ="";
					}
					return returnValue;
				}
			};
			String strMaxunique= (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			if(!strMaxunique.equals(""))
			{
				return (Integer.parseInt(strMaxunique)+1)+"";
			}
			return strMaxunique;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
	}
	
	public List<CommonBean> loadCompanyData(){
		String sql ="select a.compno,a.compname from companyinfo a LEFT JOIN compchainstruct b on a.compno=b.curcompno where a.compstate=1 and b.complevel=4";
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("compno")));	//ordercompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("compname")));  	//compname 公司名称
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		@SuppressWarnings("unchecked")
		List<CommonBean> list = (List<CommonBean>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
	}
}

