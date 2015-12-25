package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dproexchangeinfo;
import com.amani.model.Dproexchangeinfobypro;
import com.amani.model.Mconsumeinfo;
import com.amani.model.Mpackageinfo;
import com.amani.model.Mproexchangeinfo;
import com.amani.model.MproexchangeinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC012Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return true;
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
		return true;
	}
	
	
	public List<Mproexchangeinfo> loadMasterDateByCompId(String strCompId)
	{
		try
		{
			String strSql=" select changecompid,changebillid,changedate,changetime,changecardno,changecardtype,membername,memberphone,changeopationerid,changeopationdate,backcsflag " +
					" from Mproexchangeinfo " +
					" where changecompid='"+strCompId+"' and changedate='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<List<Mproexchangeinfo>> analysis = new AnlyResultSet<List<Mproexchangeinfo>>()
			{
				public List<Mproexchangeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mproexchangeinfo> returnValue = new ArrayList();
					Mproexchangeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mproexchangeinfo();
							record.setId(new MproexchangeinfoId(rs.getString("changecompid"),rs.getString("changebillid")));
							record.setBchangecompid(rs.getString("changecompid"));
							record.setBchangebillid(rs.getString("changebillid"));
							record.setChangedate(CommonTool.getDateMask(rs.getString("changedate")));
							record.setChangetime(CommonTool.getTimeMask(rs.getString("changetime"), 1));
							record.setChangecardno(rs.getString("changecardno"));
							record.setChangecardtype(rs.getString("changecardtype"));
							record.setMembername(rs.getString("membername"));
							record.setMemberphone(rs.getString("memberphone"));
							record.setChangeopationerid(rs.getString("changeopationerid"));
							record.setChangeopationdate(CommonTool.getDateMask(rs.getString("changeopationdate")));
							record.setBackcsflag(CommonTool.FormatInteger(rs.getInt("backcsflag")));
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
			return (List<Mproexchangeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public Mproexchangeinfo loadcurMaster(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mproexchangeinfo mproexchangeinfo where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' ";
			List<Mproexchangeinfo> ls=(List<Mproexchangeinfo>)this.amn_Dao.findByHql(strSql);
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
	
	public List<Dproexchangeinfo> loadDetialById(String strCompId,String strBillId)
	{
		try
		{
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		   
			 String strModeId_p=this.dataTool.loadSysParam(strCompId,"SP059");
		     String strFristModeId_p="";//第一级模板Id
			 String strSecondModeId_p="";//第2级模板ID
			 String strThirthModeId_p="";//第三级模板Id
			 if(compLvl==2)
			 {
				strFristModeId_p=this.dataTool.loadparentModeId(strModeId_p);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strThirthModeId_p.equals(""))
					strSecondModeId_p=this.dataTool.loadparentModeId(strThirthModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			String strSql=" select changeproid,prjname,procount,changeprocount,changeprorate,changeproamt,changebyproaccountamt,changebyaccountamt,changepaymode,changebycashamt,nointernalcardno,changebydyqamt," +
					      " firstsalerid,firstsaleamt,secondsalerid,secondsaleamt,thirdsalerid,thirdsaleamt,fourthsalerid,fourthsaleamt,changemark,changebyfaceamt " +
					      " From  dproexchangeinfo,projectinfo" +
					      " where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' " +
					      " and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=changeproid  ";
							
			AnlyResultSet<List<Dproexchangeinfo>> analysis = new AnlyResultSet<List<Dproexchangeinfo>>()
			{
				public List<Dproexchangeinfo> anlyResultSet(ResultSet rs)
				{
					List<Dproexchangeinfo> returnValue = new ArrayList();
					Dproexchangeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dproexchangeinfo();
							record.setChangeproid(CommonTool.FormatString(rs.getString("changeproid")));
							record.setChangeproname(CommonTool.FormatString(rs.getString("prjname")));
							record.setProcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("procount")))));
							record.setChangeprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprocount")))));
							record.setChangeprorate(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprorate")))));
							record.setChangeproamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeproamt")))));
							record.setChangebyproaccountamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebyproaccountamt")))));
							record.setChangebyaccountamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebyaccountamt")))));
							record.setChangepaymode(CommonTool.FormatString(rs.getString("changepaymode")));
							record.setChangebycashamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebycashamt")))));
							record.setNointernalcardno(CommonTool.FormatString(rs.getString("nointernalcardno")));
							record.setChangebydyqamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebydyqamt")))));
							record.setFirstsalerid(CommonTool.FormatString(rs.getString("firstsalerid")));
							record.setFirstsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("firstsaleamt")))));
							record.setSecondsalerid(CommonTool.FormatString(rs.getString("secondsalerid")));
							record.setSecondsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("secondsaleamt")))));
							record.setThirdsalerid(CommonTool.FormatString(rs.getString("thirdsalerid")));
							record.setThirdsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("thirdsaleamt")))));
							record.setFourthsalerid(CommonTool.FormatString(rs.getString("fourthsalerid")));
							record.setFourthsaleamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("fourthsaleamt")))));
							record.setChangemark(CommonTool.FormatString(rs.getString("changemark")));
							record.setChangebyfaceamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changebyfaceamt")))));
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
			List<Dproexchangeinfo> ls= (List<Dproexchangeinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Dproexchangeinfobypro> loadDetialproById(String strCompId,String strBillId)
	{
		try
		{
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.dataTool.loadCompLvl(strCompId);
		   
			 String strModeId_p=this.dataTool.loadSysParam(strCompId,"SP059");
		     String strFristModeId_p="";//第一级模板Id
			 String strSecondModeId_p="";//第2级模板ID
			 String strThirthModeId_p="";//第三级模板Id
			 if(compLvl==2)
			 {
				strFristModeId_p=this.dataTool.loadparentModeId(strModeId_p);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId_p=this.dataTool.loadparentModeId(strModeId_p);
				if(!strThirthModeId_p.equals(""))
					strSecondModeId_p=this.dataTool.loadparentModeId(strThirthModeId_p);
				if(!strSecondModeId_p.equals(""))
					strFristModeId_p=this.dataTool.loadparentModeId(strSecondModeId_p);
			 }
			String strSql=" select changeproid,prjname,bproseqno,lastcount,lastamt,changeprocount,changeproamt " +
					" From  dproexchangeinfobypro,projectinfo" +
					" where changecompid='"+strCompId+"' and changebillid='"+strBillId+"' " +
					" and prjmodeId  in ('"+strModeId_p+"','"+strFristModeId_p+"','"+strSecondModeId_p+"','"+strThirthModeId_p+"') and prjno=changeproid  ";
					 
			AnlyResultSet<List<Dproexchangeinfobypro>> analysis = new AnlyResultSet<List<Dproexchangeinfobypro>>()
			{
				public List<Dproexchangeinfobypro> anlyResultSet(ResultSet rs)
				{
					List<Dproexchangeinfobypro> returnValue = new ArrayList();
					Dproexchangeinfobypro record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dproexchangeinfobypro();
							record.setChangeproid(CommonTool.FormatString(rs.getString("changeproid")));
							record.setChangeproname(CommonTool.FormatString(rs.getString("prjname")));
							record.setBproseqno(CommonTool.FormatDouble(rs.getDouble("bproseqno")));
							record.setLastcount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastcount")))));
							record.setLastamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("lastamt")))));
							record.setChangeprocount(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeprocount")))));
							record.setChangeproamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeproamt")))));
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
			List<Dproexchangeinfobypro> ls= (List<Dproexchangeinfobypro>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	 //新增主档
	 public Mproexchangeinfo addMastRecord(String strCompId)
	 {
		 Mproexchangeinfo record=new Mproexchangeinfo();
		 record.setId(new MproexchangeinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mproexchangeinfo", "changebillid", "SP008")));
		 record.setChangedate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setChangetime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setChangeopationerid(CommonTool.getLoginInfo("USERID"));
		 record.setChangeopationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBchangebillid(record.getId().getChangebillid());
		 record.setBchangecompid(strCompId);
		 record.setBackcsflag(0);
		 return record;
	 }
	
	 public double validateDiyqNo(String strDiyqNo)
	 {
		 String strSql=" select cardfaceamt from nointernalcardinfo where cardno='"+strDiyqNo+"' and cardtype=1 and cardstate=1 and carduseflag=2 ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=rs.getDouble("cardfaceamt");
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0;
					}
					return returnValue;
				}
			};
			return (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	 
	 public boolean validateManagerPass(String strManagerNo,String strManagerPass)
	 {
		 String strSql=" select 1 from staffinfo where staffno='"+strManagerNo+"' and staffpassword='"+strManagerPass+"' and isnull(staffpassword,'')<>'' ";
		 AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>()
			{
				public Boolean anlyResultSet(ResultSet rs)
				{
					boolean returnValue = false;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=true;
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  false;
					}
					return returnValue;
				}
			};
			return (Boolean)this.amn_Dao.executeQuery_ex(strSql,analysis);
	 }
	 
	 
	 public boolean postChangeInfo(Mproexchangeinfo curRecord,List<Dproexchangeinfo> lsDetial, List<Dproexchangeinfobypro> lsDetialByPro)
	 {
		 try
			{
				this.amn_Dao.saveOrUpdate(curRecord);
				if(lsDetial!=null && lsDetial.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDetial);
				}
				if(lsDetialByPro!=null && lsDetialByPro.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDetialByPro);
				}
				return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	 }
	 
	public List<Mpackageinfo> loadMpackageinfo(String strCurCompId)
	{
			try
			{
				String strSql=" From Mpackageinfo  mpackageinfo where compid='"+strCurCompId+"' and isnull(usedate,'')>'"+CommonTool.getCurrDate()+"' and isnull(usetype,1)=2 ";
				return (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	 
	 public boolean afterPost(String strCompId,String strBillId)
	 {
		 try
		 {
			 	String strSql=" exec upg_handaccountChangebill_card '"+strCompId+"','"+strBillId+"' ";
				return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 public int loadPackRate(String strCompId,String strPackageno)
	 {
			String strSql=" select ratetype from mpackageinfo where compid='"+strCompId+"'  and  packageno='"+strPackageno+"' ";
			AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
			{
				public Integer anlyResultSet(ResultSet rs)
				{
					int returnValue = 0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue =  CommonTool.FormatInteger(rs.getInt("ratetype"));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  0;
					}
					return returnValue;
				}
			};
			int flag=(Integer)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return flag;
		}
	
	 
	  public double loadCardPrjCostRate(String strCompId,String strPrjtype)
		{
			String strSql=" select changerate from cardratetocostrate where compid='"+strCompId+"' and projecttypeid='"+strPrjtype+"' and startdate<='"+CommonTool.getCurrDate()+"' and  enddate>='"+CommonTool.getCurrDate()+"' ";
			AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue =1;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("changerate"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  1;
					}
					if(returnValue==0)
						returnValue=1;
					return returnValue;
				}
			};
			Double costRate=(Double)this.amn_Dao.executeQuery_ex(strSql,analysis);	
			analysis=null;
			return costRate;
		}
	  
	  //获取当天消费主档/明细中的C类美容师体验列表
	  @SuppressWarnings("unchecked")
	  public List<Mconsumeinfo> loadConsumeCtype(){
		  String sql = "select a.csbillid, a.cscardno, a.csname from mconsumeinfo a, dconsumeinfo b where a.csbillid=b.csbillid and isnull(a.ctypestate,0)<>1 "+
		  		"and a.cscompid='"+ CommonTool.getLoginInfo("COMPID") +"' and a.financedate=CONVERT(varchar(100), GETDATE(), 112) and b.costpricetype=10 and isnull(backcsflag,0)=0";
		  AnlyResultSet<List<Mconsumeinfo>> analysis = new AnlyResultSet<List<Mconsumeinfo>>()
		  {
				public List<Mconsumeinfo> anlyResultSet(ResultSet rs)
				{
					List<Mconsumeinfo> returnValue = new ArrayList<Mconsumeinfo>();
					Mconsumeinfo record=null;
					try
					{
						while(rs != null && rs.next()==true){
							record = new Mconsumeinfo();
							record.setBcsbillid(ObjectUtils.toString(rs.getString("csbillid")));
							record.setCscardno(ObjectUtils.toString(rs.getString("cscardno")));
							record.setCsname(ObjectUtils.toString(rs.getString("csname")));
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
			List<Mconsumeinfo> list=  (List<Mconsumeinfo>)this.amn_Dao.executeQuery_ex(sql,analysis);
			analysis=null;
			return list;
	  }
	  
	  //更新消费主档的C类美容师体验打折状态
	  public boolean upConsumeCtype(String strCurBillId)
		{
			String strSql="update Mconsumeinfo set ctypestate=1 where cscompid='"+ CommonTool.getLoginInfo("COMPID")
						+"' and csbillid='"+strCurBillId+"'";
			try {
				return amn_Dao.executeSql(strSql);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	//获取微信绑定卡OpenId
	public String loadWechatOpenId(String memberno){
		String openId = "";
		String sql ="select openid from memberinfo where memberno='"+ memberno +"'";
		ResultSet rs = null;
		try {
			rs = amn_Dao.executeQuery(sql);
			if(rs.next()){
				openId = rs.getString("openid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return openId;
	}
}
