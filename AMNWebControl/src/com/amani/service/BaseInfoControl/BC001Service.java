package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Companyinfo;
import com.amani.model.Compchaininfo;
import com.amani.model.CompchaininfoId;
import com.amani.model.Compchainstruct;
import com.amani.model.Compscheduling;
import com.amani.model.Compwarehouse;
import com.amani.model.Roominfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
@Service
public class BC001Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		List<Compwarehouse> lsCompwarehouse=(List<Compwarehouse>)details;
		if(lsCompwarehouse!=null && lsCompwarehouse.size()>0)
		{
			for(int i=0;i<lsCompwarehouse.size();i++)
			{
				if(!CommonTool.FormatString(lsCompwarehouse.get(i).getBwarehouseno()).equals(""))
				{
					this.amn_Dao.saveOrUpdate(lsCompwarehouse.get(i));
				}
			}
		}
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.update(curMaster);
		curMaster=null;
		return true;
	}
	
	public boolean postCompinfo(Companyinfo record,List<Compwarehouse>		lsCompwarehouse,List<Compscheduling>	lsCompscheduling,List<Roominfo> lsRoominfo)
	{
		try
		{
			this.amn_Dao.update(record);
		
			if(lsCompwarehouse!=null && lsCompwarehouse.size()>0)
			{
				for(int i=0;i<lsCompwarehouse.size();i++)
				{
					if(!CommonTool.FormatString(lsCompwarehouse.get(i).getBwarehouseno()).equals(""))
					{
						this.amn_Dao.saveOrUpdate(lsCompwarehouse.get(i));
					}
				}
			}
			String strSql=" delete compscheduling  where compno='"+record.getCompno()+"' ";
			if(lsCompscheduling!=null && lsCompscheduling.size()>0)
			{
				for(int i=0;i<lsCompscheduling.size();i++)
				{
					if(!CommonTool.FormatString(lsCompscheduling.get(i).getSchedulno()).equals(""))
					{
						strSql=strSql+" insert compscheduling(compno,schedulno,schedulname,fromtime,totime)" +
								" values('"+lsCompscheduling.get(i).getCompno()+"','"+lsCompscheduling.get(i).getSchedulno()+"','"+lsCompscheduling.get(i).getSchedulname()+"','"+lsCompscheduling.get(i).getFromtime()+"','"+lsCompscheduling.get(i).getTotime()+"')";
					}
				}
			}
			strSql=" delete roominfo  where compno='"+record.getCompno()+"' ";
			if(lsRoominfo!=null && lsRoominfo.size()>0)
			{
				for(int i=0;i<lsRoominfo.size();i++)
				{
					if(!CommonTool.FormatString(lsRoominfo.get(i).getRoomno()).equals(""))
					{
						strSql=strSql+" insert roominfo(compno,roomno,roomname)" +
								" values('"+lsRoominfo.get(i).getCompno()+"','"+lsRoominfo.get(i).getRoomno()+"','"+lsRoominfo.get(i).getRoomname()+"')";
					}
				}
			}
			
			record=null;
			lsCompwarehouse=null;
			lsCompscheduling=null;
			lsRoominfo = null;
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public  Companyinfo loadCompanyinfoByCurCompId(String strCompId)
	{
		try
		{
			String strSql=" From Companyinfo companyinfo where  compno='"+strCompId+"'  ";
			List<Companyinfo> ls= (List<Companyinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
				return ls.get(0);
			else
				return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}
	
	public  void loadCompanyinfoByCurCompId_JDBC(String strCompId)
	{
		
		String strSql=" select compname From companyinfo where compno='"+strCompId+"' ";
		AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		{
			public String anlyResultSet(ResultSet rs)
			{
				try
				{
					if(rs != null && rs.next()==true)
					{
						System.out.println("AAAA33333333333331111111111111111111111");
						System.out.println(rs.getString("compname"));
				
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				
				}
				return "";
			}
		};
		this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		strSql=" select gae03c from gam05 where gae01c='"+strCompId+"' ";
		AnlyResultSet<String>  analysis1 = new AnlyResultSet<String>()
		{
			public String anlyResultSet(ResultSet rs)
			{
				try
				{
					if(rs != null && rs.next()==true)
					{
						System.out.println("S33333333333331111111111111111111111");
						System.out.println(rs.getString("gae03c"));
				
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
				
				}
				return "";
			}
		};
		this.amn_PADDao.executeQuery_ex(strSql,analysis1);
	}
	
	public  List<Compchainstruct>  loadCompchainstructByCurCompId()
	{
		/*try
		{
			String strSql=" From Compchainstruct compchainstruct  ";
			return (List<Compchainstruct>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}*/
		String strSql=" select curcompno,compname,parentcompno,complevel,createdate From companyinfo,compchainstruct where compno=curcompno  ";
		AnlyResultSet<List<Compchainstruct>> analysis = new AnlyResultSet<List<Compchainstruct>>()
		{
			public List<Compchainstruct> anlyResultSet(ResultSet rs)
			{
				List<Compchainstruct> returnValue = new ArrayList();
				Compchainstruct record=new Compchainstruct();
				try
				{
					while(rs != null && rs.next()==true)
					{
						record=new Compchainstruct();
						record.setCurcompno(rs.getString("curcompno"));
						record.setCurcompname(rs.getString("compname"));
						record.setParentcompno(rs.getString("parentcompno"));
						record.setComplevel(rs.getInt("complevel"));
						returnValue.add(record);
					}				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  null;
				}
				return returnValue;
			}
		};
		return (List<Compchainstruct>)this.amn_Dao.executeQuery_ex(strSql,analysis);
	
	}
	
	public  List<Compwarehouse>  loadCompwarehouseByCurCompId(String strCurCompId)
	{
		try
		{
			String strSql=" From Compwarehouse compwarehouse  where compno='"+strCurCompId+"' ";
			return (List<Compwarehouse>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public  List<Compscheduling>  loadCompschedulingByCurCompId(String strCurCompId)
	{
		try
		{
			String strSql=" select  compno,schedulno,schedulname,fromtime,totime From compscheduling   where compno='"+strCurCompId+"' ";
			AnlyResultSet<List<Compscheduling>> analysis = new AnlyResultSet<List<Compscheduling>>()
			{
				public List<Compscheduling> anlyResultSet(ResultSet rs)
				{
					List<Compscheduling> returnValue = new ArrayList();
					Compscheduling record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Compscheduling();
							record.setCompno(rs.getString("compno"));
							record.setSchedulno(rs.getString("schedulno"));
							record.setSchedulname(rs.getString("schedulname"));
							record.setFromtime(rs.getString("fromtime"));
							record.setTotime(rs.getString("totime"));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  null;
					}
					return returnValue;
				}
			};
			List<Compscheduling> ls= (List<Compscheduling>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public  List<Roominfo>  loadRoominfoByCurCompId(String strCurCompId)
	{
		try
		{
			String strSql=" select  compno,roomno,roomname From roominfo   where compno='"+strCurCompId+"' ";
			AnlyResultSet<List<Roominfo>> analysis = new AnlyResultSet<List<Roominfo>>()
			{
				public List<Roominfo> anlyResultSet(ResultSet rs)
				{
					List<Roominfo> returnValue = new ArrayList();
					Roominfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Roominfo();
							record.setCompno(rs.getString("compno"));
							record.setRoomno(rs.getString("roomno"));
							record.setRoomname(rs.getString("roomname"));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  null;
					}
					return returnValue;
				}
			};
			List<Roominfo> ls= (List<Roominfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteWareHouse(String strCompId,String strWareId)
	{
		try
		{
			String strSql=" delete compwarehouse where compno='"+strCompId+"' and warehouseno='"+strWareId+"' ";
			return this.amn_Dao.deleteBySql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	
	
	public boolean handChainInfo(List<Compchainstruct> lsCompchainstruct)
	{
		try
		{
			this.amn_Dao.saveOrUpdateAll(lsCompchainstruct);
			for(int i=0;i<lsCompchainstruct.size();i++)
			{
				if(lsCompchainstruct.get(i)!=null && !CommonTool.FormatString(lsCompchainstruct.get(i).getCurcompno()).equals(""))
				{
					this.amn_Dao.saveOrUpdate(lsCompchainstruct.get(i));
				}
			}
			 List<Compchaininfo> lsCompchaininfo=this.loadChainInfoByLvl("001", lsCompchainstruct);
			 this.amn_Dao.deleteBySql(" delete compchaininfo ");
			 for(int i=0;i<lsCompchaininfo.size();i++)
			 {
				if(lsCompchaininfo.get(i)!=null && lsCompchaininfo.get(i).getId()!=null)
				{
					this.amn_Dao.save(lsCompchaininfo.get(i));
				}
			 }
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	 
	public List<Compchaininfo> loadChainInfoByLvl(String strCompId,List<Compchainstruct> lsCompchainstruct)
	{
		try
		{
			List<Compchaininfo> ls=new ArrayList();
			Compchaininfo bean=null;
			String strSecondCompId="";
			String strThirthCompId="";
			String strFouthCompId="";
			for(int i=0;i<lsCompchainstruct.size();i++)
			{
				//2级
				if(lsCompchainstruct.get(i).getComplevel()==2 && lsCompchainstruct.get(i).getParentcompno().equals(CommonTool.FormatString(strCompId)))//2级
				{
					strSecondCompId=lsCompchainstruct.get(i).getCurcompno();
					bean=new Compchaininfo();
					bean.setId(new CompchaininfoId(strSecondCompId,strSecondCompId));
					ls.add(bean);
					bean=new Compchaininfo();
					bean.setId(new CompchaininfoId(strCompId,strSecondCompId));
					ls.add(bean);
					//3级
					for(int j=0;j<lsCompchainstruct.size();j++)
					{
						if(lsCompchainstruct.get(j).getComplevel()==3 && lsCompchainstruct.get(j).getParentcompno().equals(CommonTool.FormatString(strSecondCompId)))//3级
						{
							strThirthCompId=lsCompchainstruct.get(j).getCurcompno();
							bean=new Compchaininfo();
							bean.setId(new CompchaininfoId(strThirthCompId,strThirthCompId));
							ls.add(bean);
							bean=new Compchaininfo();
							bean.setId(new CompchaininfoId(strSecondCompId,strThirthCompId));
							ls.add(bean);
							bean=new Compchaininfo();
							bean.setId(new CompchaininfoId(strCompId,strThirthCompId));
							ls.add(bean);
							//四级
							for(int k=0;k<lsCompchainstruct.size();k++)
							{
								if(lsCompchainstruct.get(k).getComplevel()==4 && lsCompchainstruct.get(k).getParentcompno().equals(CommonTool.FormatString(strThirthCompId)))//4级
								{
									strFouthCompId=lsCompchainstruct.get(k).getCurcompno();
									bean=new Compchaininfo();
									bean.setId(new CompchaininfoId(strFouthCompId,strFouthCompId));
									ls.add(bean);
									bean=new Compchaininfo();
									bean.setId(new CompchaininfoId(strThirthCompId,strFouthCompId));
									ls.add(bean);
									bean=new Compchaininfo();
									bean.setId(new CompchaininfoId(strSecondCompId,strFouthCompId));
									ls.add(bean);
									bean=new Compchaininfo();
									bean.setId(new CompchaininfoId(strCompId,strFouthCompId));
									ls.add(bean);
								}
							}
						}
					}
				}
			}
			bean=new Compchaininfo();
			bean.setId(new CompchaininfoId(strCompId,strCompId));
			ls.add(bean);
			bean=null;		
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean handMangerPass(String strCompId,String strPassword)
	{
		try
		{
			String strSql=" update companyinfo set mangerPassword='"+strPassword+"' where compno='"+strCompId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

}
