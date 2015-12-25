package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Dpayoutinfo;
import com.amani.model.Mpayoutinfo;
import com.amani.model.MpayoutinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC015Service  extends AMN_ModuleService{
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
		try
		{
			this.amn_Dao.delete(curMaster);
			return true;
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
	
		List<Dpayoutinfo> lsDpayoutinfo=(List<Dpayoutinfo>)details;
		if(lsDpayoutinfo!=null && lsDpayoutinfo.size()>0)
		{
			 this.amn_Dao.saveOrUpdateAll(lsDpayoutinfo);
		}
		return true;
	}
	
	public boolean postInfo(Mpayoutinfo curMpayoutinfo,List<Dpayoutinfo> lsDpayoutinfo)
	{
		this.amn_Dao.saveOrUpdate(curMpayoutinfo);
		this.amn_Dao.executeSql("delete dpayoutinfo where payoutcompid='"+curMpayoutinfo.getId().getPayoutcompid()+"' and payoutbillid='"+curMpayoutinfo.getId().getPayoutbillid()+"' ");
		if(lsDpayoutinfo!=null && lsDpayoutinfo.size()>0)
		{
			 this.amn_Dao.saveOrUpdateAll(lsDpayoutinfo);
		}
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	

	public List<Mpayoutinfo> loadMaster(String strCurCompId)
	{
		try
		{
			String strSql=" From Mpayoutinfo  mpayoutinfo where payoutcompid='"+strCurCompId+"' and substring(payoutdate,1,6)='"+CommonTool.getCurrDate().substring(0,6)+"' ";
			return (List<Mpayoutinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public Mpayoutinfo loadCurMaster(String strCurCompId,String strCurBillNo)
	{
		try
		{
			String strSql=" From Mpayoutinfo mpayoutinfo where payoutcompid='"+strCurCompId+"' and payoutbillid='"+strCurBillNo+"'  ";
			List<Mpayoutinfo> ls= (List<Mpayoutinfo>)this.amn_Dao.findByHql(strSql);
			if(ls!=null && ls.size()>0)
			{
				return ls.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mpayoutinfo addMpayoutinfoinfo(String strCompId)
	{
		try
		{
			Mpayoutinfo record=new Mpayoutinfo();
			record.setId(new MpayoutinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mpayoutinfo", "payoutbillid", "SP008")));
			record.setBpayoutcompid(record.getId().getPayoutcompid());
			record.setBpayoutbillid(record.getId().getPayoutbillid());
			record.setPayoutdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
			record.setPayouttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
			record.setBillstate(0);
			return record;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public List<Dpayoutinfo> loadDetial(String strCurCompId,String strBillId)
	{
		try
		{
			String strSql=" select payoutitemid,payoutitemamt,checkbookflag,checkbookno,payoutmark,payoutbillstate,checkedopationerid,checkedopationdate,confirmopationerid,confirmopationdate " +
					" From dpayoutinfo where payoutcompid='"+strCurCompId+"' and payoutbillid='"+strBillId+"' ";
			AnlyResultSet<List<Dpayoutinfo>> analysis = new AnlyResultSet<List<Dpayoutinfo>>()
			{
				public List<Dpayoutinfo> anlyResultSet(ResultSet rs)
				{
					List<Dpayoutinfo> returnValue = new ArrayList();
					Dpayoutinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dpayoutinfo();
							record.setPayoutitemid(CommonTool.FormatString(rs.getString("payoutitemid")));
							record.setPayoutitemamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payoutitemamt")))));
							record.setCheckbookflag(rs.getInt("checkbookflag"));
							record.setCheckbookno(CommonTool.FormatString(rs.getString("checkbookno")));
							record.setPayoutmark(CommonTool.FormatString(rs.getString("payoutmark")));
							record.setPayoutbillstate(CommonTool.FormatInteger(rs.getInt("payoutbillstate")));
							if(record.getPayoutbillstate()==0)
								record.setPayoutbillstateText("门店录入");
							else if(record.getPayoutbillstate()==1)
								record.setPayoutbillstateText("门店经理审核");
							else if(record.getPayoutbillstate()==2)
								record.setPayoutbillstateText("财务专员审核");
							else if(record.getPayoutbillstate()==3)
								record.setPayoutbillstateText("财务经理审核");
							else if(record.getPayoutbillstate()==11)
								record.setPayoutbillstateText("门店经理驳回");
							else if(record.getPayoutbillstate()==22)
								record.setPayoutbillstateText("财务专员驳回");
							else if(record.getPayoutbillstate()==33)
								record.setPayoutbillstateText("财务经理驳回");
							record.setCheckedopationerid(CommonTool.FormatString(rs.getString("checkedopationerid")));
							record.setCheckedopationdate(CommonTool.getDateMask(rs.getString("checkedopationdate")));
							record.setConfirmopationerid(CommonTool.FormatString(rs.getString("confirmopationerid")));
							record.setConfirmopationdate(CommonTool.getDateMask(rs.getString("confirmopationdate")));
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
			List<Dpayoutinfo> ls= (List<Dpayoutinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Dpayoutinfo> loadSearchDetial(String strCurCompId,String strFromDate,String strToDate,String strFromItem,String strToItem,int itemState)
	{
		try
		{
			String strSql=" select a.payoutbillid,a.payoutdate,a.payoutopationerid,payoutitemid,payoutitemamt,checkbookflag,checkbookno,payoutmark,payoutbillstate,checkedopationerid,checkedopationdate,confirmopationerid,confirmopationdate " +
					" From mpayoutinfo a,dpayoutinfo b " +
					" where a.payoutcompid=b.payoutcompid and a.payoutbillid=b.payoutbillid " +
					" and a.payoutcompid='"+strCurCompId+"' " +
					" and a.payoutdate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"'" +
					" and (b.payoutitemid between '"+strFromItem+"' and '"+strToItem+"' or  '"+strFromItem+"' ='' ) " +
					" and (isnull(b.payoutbillstate,0)="+itemState+" or "+itemState+"=(-1) ) ";
			AnlyResultSet<List<Dpayoutinfo>> analysis = new AnlyResultSet<List<Dpayoutinfo>>()
			{
				public List<Dpayoutinfo> anlyResultSet(ResultSet rs)
				{
					List<Dpayoutinfo> returnValue = new ArrayList();
					Dpayoutinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dpayoutinfo();
							record.setBpayoutbillid(CommonTool.FormatString(rs.getString("payoutbillid")));
							record.setPayoutdate(CommonTool.getDateMask(rs.getString("payoutdate")));
							record.setPayoutopationerid(CommonTool.FormatString(rs.getString("payoutopationerid")));
							record.setPayoutitemid(CommonTool.FormatString(rs.getString("payoutitemid")));
							record.setPayoutitemamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payoutitemamt")))));
							record.setCheckbookflag(rs.getInt("checkbookflag"));
							record.setCheckbookno(CommonTool.FormatString(rs.getString("checkbookno")));
							record.setPayoutmark(CommonTool.FormatString(rs.getString("payoutmark")));
							record.setPayoutbillstate(CommonTool.FormatInteger(rs.getInt("payoutbillstate")));
							if(record.getPayoutbillstate()==0)
								record.setPayoutbillstateText("门店录入");
							else if(record.getPayoutbillstate()==1)
								record.setPayoutbillstateText("门店经理审核");
							else if(record.getPayoutbillstate()==2)
								record.setPayoutbillstateText("财务专员审核");
							else if(record.getPayoutbillstate()==3)
								record.setPayoutbillstateText("财务经理审核");
							else if(record.getPayoutbillstate()==11)
								record.setPayoutbillstateText("门店经理驳回");
							else if(record.getPayoutbillstate()==22)
								record.setPayoutbillstateText("财务专员驳回");
							else if(record.getPayoutbillstate()==33)
								record.setPayoutbillstateText("财务经理驳回");
							record.setCheckedopationerid(CommonTool.FormatString(rs.getString("checkedopationerid")));
							record.setCheckedopationdate(CommonTool.getDateMask(rs.getString("checkedopationdate")));
							record.setConfirmopationerid(CommonTool.FormatString(rs.getString("confirmopationerid")));
							record.setConfirmopationdate(CommonTool.getDateMask(rs.getString("confirmopationdate")));
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
			List<Dpayoutinfo> ls= (List<Dpayoutinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Dpayoutinfo> loadConfirmDetial(String strCurCompId,String strFromDate,String strToDate,int itemState)
	{
		try
		{
			String strSql=" select a.payoutcompid,d.compname,a.payoutbillid,b.payoutseqno,a.payoutdate,a.payoutopationerid,payoutitemid,payoutitemamt,checkbookflag,checkbookno,payoutmark,payoutbillstate,checkedopationerid,checkedopationdate,confirmopationerid,confirmopationdate " +
					" From mpayoutinfo a,dpayoutinfo b,compchaininfo c , companyinfo d" +
					" where a.payoutcompid=b.payoutcompid and a.payoutbillid=b.payoutbillid " +
					" and a.payoutcompid=c.relationcomp and c.curcomp='"+strCurCompId+"' " +
					" and a.payoutcompid=d.compno " +
					" and a.payoutdate between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"'" ;
					//" and (isnull(b.payoutbillstate,0)="+itemState+" or "+itemState+"=(-1) ) ";
			if(itemState==1)  //需门店审核单据
			{
				strSql=strSql+" and isnull(b.payoutbillstate,0)=0 ";
			}
			else if(itemState==2)  //需财务专员
			{
				strSql=strSql+" and isnull(b.payoutbillstate,0)=1 and isnull(payoutitemamt,0)<500 ";
			}
			else if(itemState==3)  //需财务经理
			{
				strSql=strSql+" and ( (isnull(b.payoutbillstate,0)=1 and isnull(payoutitemamt,0)>=500) or isnull(b.payoutbillstate,0)=2 ) ";
			}
			AnlyResultSet<List<Dpayoutinfo>> analysis = new AnlyResultSet<List<Dpayoutinfo>>()
			{
				public List<Dpayoutinfo> anlyResultSet(ResultSet rs)
				{
					List<Dpayoutinfo> returnValue = new ArrayList();
					Dpayoutinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Dpayoutinfo();
							record.setBpayoutcompid(CommonTool.FormatString(rs.getString("payoutcompid")));
							record.setBpayoutcompname(CommonTool.FormatString(rs.getString("compname")));
							record.setBpayoutbillid(CommonTool.FormatString(rs.getString("payoutbillid")));
							record.setBpayoutseqno(rs.getDouble("payoutseqno"));
							record.setPayoutdate(CommonTool.getDateMask(rs.getString("payoutdate")));
							record.setPayoutopationerid(CommonTool.FormatString(rs.getString("payoutopationerid")));
							record.setPayoutitemid(CommonTool.FormatString(rs.getString("payoutitemid")));
							record.setPayoutitemamt(CommonTool.FormatBigDecimal(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("payoutitemamt")))));
							record.setCheckbookflag(rs.getInt("checkbookflag"));
							record.setCheckbookno(CommonTool.FormatString(rs.getString("checkbookno")));
							record.setPayoutmark(CommonTool.FormatString(rs.getString("payoutmark")));
							record.setPayoutbillstate(CommonTool.FormatInteger(rs.getInt("payoutbillstate")));
							if(record.getPayoutbillstate()==0)
								record.setPayoutbillstateText("门店录入");
							else if(record.getPayoutbillstate()==1)
								record.setPayoutbillstateText("门店经理已审核");
							else if(record.getPayoutbillstate()==2)
								record.setPayoutbillstateText("财务专员已审核");
							else if(record.getPayoutbillstate()==3)
								record.setPayoutbillstateText("财务经理已审核");
							else if(record.getPayoutbillstate()==11)
								record.setPayoutbillstateText("门店经理已驳回");
							else if(record.getPayoutbillstate()==22)
								record.setPayoutbillstateText("财务专员已驳回");
							else if(record.getPayoutbillstate()==33)
								record.setPayoutbillstateText("财务经理已驳回");
							record.setCheckedopationerid(CommonTool.FormatString(rs.getString("checkedopationerid")));
							record.setCheckedopationdate(CommonTool.getDateMask(rs.getString("checkedopationdate")));
							record.setConfirmopationerid(CommonTool.FormatString(rs.getString("confirmopationerid")));
							record.setConfirmopationdate(CommonTool.getDateMask(rs.getString("confirmopationdate")));
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
			List<Dpayoutinfo> ls= (List<Dpayoutinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean PostComfirmBill(int checkState,List<Dpayoutinfo> lsDpayoutinfo)
	{
		try
		{
			String strSql="";
			if(lsDpayoutinfo!=null && lsDpayoutinfo.size()>0)
			{
				for(int i=0;i<lsDpayoutinfo.size();i++)
				{
					if(checkState==1)
					{
						strSql=strSql+" update dpayoutinfo set payoutbillstate=1 " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
					
					}
					else if(checkState==2)
					{
						strSql=strSql+" update dpayoutinfo set payoutbillstate=2,checkedopationerid='"+CommonTool.getLoginInfo("USERID")+"',checkedopationdate='"+CommonTool.getCurrDate()+"' " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
					
					}
					else if(checkState==3)
					{
						strSql=strSql+" update dpayoutinfo set payoutbillstate=3 ,confirmopationerid='"+CommonTool.getLoginInfo("USERID")+"',confirmopationdate='"+CommonTool.getCurrDate()+"' " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
					}
				}
			}
			if(!strSql.equals(""))
				return this.amn_Dao.executeSql(strSql);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean PostCallbackBill(int checkState,List<Dpayoutinfo> lsDpayoutinfo)
	{
		try
		{
			String strSql="";
			if(lsDpayoutinfo!=null && lsDpayoutinfo.size()>0)
			{
				for(int i=0;i<lsDpayoutinfo.size();i++)
				{
					if(checkState==1)
						strSql=strSql+" update dpayoutinfo set payoutbillstate=11 " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
					else if(checkState==2)
						strSql=strSql+" update dpayoutinfo set payoutbillstate=22,checkedopationerid='"+CommonTool.getLoginInfo("USERID")+"',checkedopationdate='"+CommonTool.getCurrDate()+"' " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
					else if(checkState==3)
						strSql=strSql+" update dpayoutinfo set payoutbillstate=33 ,confirmopationerid='"+CommonTool.getLoginInfo("USERID")+"',confirmopationdate='"+CommonTool.getCurrDate()+"' " +
							" where payoutcompid='"+lsDpayoutinfo.get(i).getBpayoutcompid()+"' and payoutbillid='"+lsDpayoutinfo.get(i).getBpayoutbillid()+"' and payoutseqno="+lsDpayoutinfo.get(i).getBpayoutseqno()+" and payoutitemid='"+lsDpayoutinfo.get(i).getPayoutitemid()+"' ";
				
				}
			}
			if(!strSql.equals(""))
				return this.amn_Dao.executeSql(strSql);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	 
}
