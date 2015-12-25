package com.amani.service.CardControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.Dmedicalcare;
import com.amani.model.Dpayinfo;
import com.amani.model.Mcooperatesaleinfo;
import com.amani.model.McooperatesaleinfoId;
import com.amani.model.Mmedicalcare;
import com.amani.model.Mpackageinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC013Service  extends AMN_ModuleService{
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
	

	
	public List<Mcooperatesaleinfo> loadMasterDateByCompId(String strCompId)
	{
		try
		{
			String strSql=" select salecompid,salebillid,salecooperid,salebillflag  from mcooperatesaleinfo " +
					     " where salecompid='"+strCompId+"' and saledate='"+CommonTool.getCurrDate()+"'  ";
			AnlyResultSet<List<Mcooperatesaleinfo>> analysis = new AnlyResultSet<List<Mcooperatesaleinfo>>()
			{
				public List<Mcooperatesaleinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcooperatesaleinfo> returnValue = new ArrayList();
					Mcooperatesaleinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcooperatesaleinfo();
							record.setId(new McooperatesaleinfoId(rs.getString("salecompid"),rs.getString("salebillid")));
							record.setBsalecompid(rs.getString("salecompid"));
							record.setBsalebillid(rs.getString("salebillid"));
							record.setSalecooperid(rs.getString("salecooperid"));
							record.setSalebillflag(rs.getInt("salebillflag"));
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
			return (List<Mcooperatesaleinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public List<Mcooperatesaleinfo> loadMasterDateByCard(String strCompId,String strSearchContent )
	{
		try
		{
			String strSql=" select  salecompid,salebillid,salecooperid  from mcooperatesaleinfo,compchaininfo " +
					" where salecompid=relationcomp and curcomp='"+strCompId+"' " +
					" and (  salebillid='"+strSearchContent+"' or  salecostcardno='"+strSearchContent+"'  ) ";
			AnlyResultSet<List<Mcooperatesaleinfo>> analysis = new AnlyResultSet<List<Mcooperatesaleinfo>>()
			{
				public List<Mcooperatesaleinfo> anlyResultSet(ResultSet rs)
				{
					List<Mcooperatesaleinfo> returnValue = new ArrayList();
					Mcooperatesaleinfo record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Mcooperatesaleinfo();
							record.setId(new McooperatesaleinfoId(rs.getString("salecompid"),rs.getString("salebillid")));
							record.setBsalecompid(rs.getString("salecompid"));
							record.setBsalebillid(rs.getString("salebillid"));
							record.setSalecooperid(rs.getString("salecooperid"));
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
			return (List<Mcooperatesaleinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Mcooperatesaleinfo loadcurMaster(String strCompId,String strBillId)
	{
		try
		{
			String strSql=" From Mcooperatesaleinfo mcooperatesaleinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
			List<Mcooperatesaleinfo> ls=(List<Mcooperatesaleinfo>)this.amn_Dao.findByHql(strSql);
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
	
	public List<Dpayinfo> loadDpayinfoByBill(String strCompId,String strBillId,int ttype)
	{
		try
		{
			String strSql="";
			if(ttype==0)
				strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='HZW' ";
			else
				strSql=" From Dpayinfo dpayinfo  where paycompid='"+strCompId+"' and paybillid='"+strBillId+"' and paybilltype='HZ' ";
			return (List<Dpayinfo>)this.amn_Dao.findByHql(strSql);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	
	
	 //新增主档
	 public Mcooperatesaleinfo addMastRecord(String strCompId)
	 {
		 Mcooperatesaleinfo record=new Mcooperatesaleinfo();
		 record.setId(new McooperatesaleinfoId(strCompId,this.dataTool.loadBillIdByRule(strCompId,"mcooperatesaleinfo", "salebillid", "SP010")));
		 record.setSaledate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setSaletime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setOperationer(CommonTool.getLoginInfo("USERID"));
		 record.setOperationdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setBsalebillid(record.getId().getSalebillid());
		 record.setBsalecompid(strCompId);
		 record.setSalebillflag(0);
		 return record;
	 }
	
	 
	
	public boolean postChangeInfo(Mcooperatesaleinfo curMcooperatesaleinfo,List<Dpayinfo> lsDpayinfofos)
	{
			try
			{
				this.amn_Dao.saveOrUpdate(curMcooperatesaleinfo);
				if(lsDpayinfofos!=null && lsDpayinfofos.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDpayinfofos);
				}
			
				return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}
	
	public boolean postSaleInid(String strCompId,String strBillId)
	{
		try
		{
			String strSql="	update mcooperatesaleinfo set firstsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and firstsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set secondsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and secondsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set thirdsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and thirdsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set fourthsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and fourthsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set fifthsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and fifthsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set sixthsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and sixthsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set seventhsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and seventhsalerid=staffno and compno=salecompid";
			strSql=strSql+"	update mcooperatesaleinfo set eighthsalerinid=manageno from mcooperatesaleinfo,staffinfo where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' and eighthsalerid=staffno and compno=salecompid";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean postConFirmInfo(String strCompId,String strBillId,List<Dpayinfo> lsDpayinfofos)
	{
			try
			{
				String strSql="update mcooperatesaleinfo set salebillflag=2 where salecompid='"+strCompId+"' and salebillid='"+strBillId+"' ";
				this.amn_Dao.executeSql(strSql);
				if(lsDpayinfofos!=null && lsDpayinfofos.size()>0)
				{
					this.amn_Dao.saveOrUpdateAll(lsDpayinfofos);
				}
				return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	public boolean updateCardaccount(String strCompId,String strBillId) {
		try{
			String strSql=" exec upg_handaccountChangebill_cooperatesale_card '"+strCompId+"','"+strBillId+"' ";
			return this.amn_Dao.executeSql(strSql);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	//保存合作医疗套餐信息
	public boolean saveMedicalInfo(Mmedicalcare mmedical, List<Dmedicalcare> dmedical) {
		try{
			if(StringUtils.isNotBlank(mmedical.getSalebillid()) && dmedical.size()>0){
				this.amn_Dao.save(mmedical);
				this.amn_Dao.saveOrUpdateAll(dmedical);
			}
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Mpackageinfo> loadMaster(String strCurCompId)
	{
		String strSql="select a.packageno,a.packagename,a.packageprice, " +
				"(select top 1 packageprono from dmpackageinfo b where a.packageno=b.packageno and a.compid = b.compid) packageprono "+
				" From mpackageinfo a where a.compid='"+strCurCompId+"' and usetype = 4 and ishz = 2";
		try {
			AnlyResultSet<List<Mpackageinfo>> analysis = new AnlyResultSet<List<Mpackageinfo>>()
			{
				public List<Mpackageinfo> anlyResultSet(ResultSet rs)
				{
					List<Mpackageinfo> returnValue = new ArrayList<Mpackageinfo>();
					try
					{
						while(rs != null && rs.next())
						{
							Mpackageinfo record = new Mpackageinfo();
							record.setBpackageno(CommonTool.FormatString(rs.getString("packageno")));
							record.setPackagename(CommonTool.FormatString(rs.getString("packagename")));
							record.setPackageprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getString("packageprice"))));
							record.setBcompid(CommonTool.FormatString(rs.getString("packageprono")));
							returnValue.add(record);
						}				
					}catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  null;
					}
					return returnValue;
				}
			};
			List<Mpackageinfo> list= (List<Mpackageinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查询合作医疗套餐信息-套数
	@SuppressWarnings("unchecked")
	public List<Mpackageinfo> loadMedicalDetail(String strCurCompId, String strBillId)
	{
		try
		{
			String strSql=" From Mpackageinfo  mpackageinfo where compid='"+strCurCompId+"' and usetype = 4 and ishz = 2";
			List<Mpackageinfo> list = (List<Mpackageinfo>)this.amn_Dao.findByHql(strSql);
			strSql=" from Dmedicalcare  where compno='"+strCurCompId.trim()+"' and salebillid='"+strBillId+"'";
			List<Dmedicalcare> dmedicalcares = (List<Dmedicalcare>)this.amn_Dao.findByHql(strSql);
			for (Mpackageinfo mpackageinfo : list) {
				for (Dmedicalcare dmedicalcare : dmedicalcares) {
					if(StringUtils.equals(mpackageinfo.getId().getPackageno(), dmedicalcare.getPackageno())){
						mpackageinfo.setSalecount(dmedicalcare.getSalecount());
						mpackageinfo.setPackageprice(CommonTool.FormatBigDecimal(new BigDecimal(dmedicalcare.getSaleamt())));
					}
				}
			}
			return list;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
