package com.amani.service.InvoicingControl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SaleOrderInfo;
import com.amani.bean.SaleOrderProduct;
import com.amani.model.Dgoodsorderinfo;
import com.amani.model.DgoodsorderinfoId;
import com.amani.model.Dgoodsouter;
import com.amani.model.DgoodsouterId;
import com.amani.model.Dgoodssendinfo;
import com.amani.model.DgoodssendinfoId;
import com.amani.model.Mgoodsorderinfo;
import com.amani.model.MgoodsorderinfoId;
import com.amani.model.Mgoodsouter;
import com.amani.model.MgoodsouterId;
import com.amani.model.Mgoodssendinfo;
import com.amani.model.MgoodssendinfoId;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Service
public class IC009Service extends AMN_ModuleService{

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub

		
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		try
		{
			Mgoodsorderinfo record=(Mgoodsorderinfo)curMaster;
			if(record!=null && record.getId()!=null)
			{
				String strSql=" update mgoodsorderinfo set invalid=1 where ordercompid='"+record.getId().getOrdercompid()+"' and orderbillid='"+record.getId().getOrderbillid()+"' ";
				return this.amn_Dao.deleteBySql(strSql);
			}
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
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		try
		{
			List<Dgoodsorderinfo> lsDgoodsorderinfo=(List<Dgoodsorderinfo>)details;
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsorderinfo);
			}
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public String postInfo(Mgoodsorderinfo curMgoodsorderinfo,List<Dgoodsorderinfo> lsDgoodsorderinfo)
	{
		String[] strWareIds = null;
		try
		{
			//this.amn_Dao.saveOrUpdate(curMgoodsorderinfo);
			String strSql=" update mgoodsorderinfo set orderstate=2,downordercompid='"+curMgoodsorderinfo.getDownordercompid()+"',downorderstaffid='"+curMgoodsorderinfo.getDownorderstaffid()+"',downorderdate='"+curMgoodsorderinfo.getDownorderdate()+"',downordertime='"+curMgoodsorderinfo.getDownordertime()+"'," +
					" sendbillno='"+curMgoodsorderinfo.getSendbillno()+"', revicebillno='"+curMgoodsorderinfo.getRevicebillno()+"' ,headwareid='"+curMgoodsorderinfo.getHeadwareid()+"',headwarename='"+curMgoodsorderinfo.getHeadwarename()+"'  " +
					" where ordercompid='"+curMgoodsorderinfo.getId().getOrdercompid()+"' and orderbillid='"+curMgoodsorderinfo.getId().getOrderbillid()+"' ";
			strSql=strSql+" update dgoodsorderinfo set ordergoodstype=2 where ordercompid='"+curMgoodsorderinfo.getId().getOrdercompid()+"' and orderbillid='"+curMgoodsorderinfo.getId().getOrderbillid()+"' ";
			this.amn_Dao.executeSql(strSql);
			if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
			{
				 this.amn_Dao.saveOrUpdateAll(lsDgoodsorderinfo);
			}
			//生成发货单
			Mgoodssendinfo mgoodssendinfo=null;
			Mgoodsouter mgoodsouter=null;
			List<Dgoodssendinfo> lsDgoodssendinfo=null;
			List<Dgoodsouter> lsDgoodsouter=null;
			Dgoodssendinfo dgoodssendinfo=null;
			Dgoodsouter dgoodsouter=null;
			String strWareId=curMgoodsorderinfo.getHeadwareid();
			strWareIds=strWareId.split(";");
			if(strWareIds!=null && strWareIds.length>0)
			{
				for(int i=0;i<strWareIds.length;i++)
				{
					 mgoodssendinfo=null;
					 mgoodsouter=null;
					 lsDgoodssendinfo=new ArrayList<Dgoodssendinfo>();
					 lsDgoodsouter=new ArrayList<Dgoodsouter>();
					 dgoodssendinfo=null;
					 dgoodsouter=null;
					if(strWareIds[i].equals("03") || strWareIds[i].equals("05")  || strWareIds[i].equals("06")  )
					{
						mgoodsouter=new Mgoodsouter();
						mgoodsouter.setId(new MgoodsouterId(CommonTool.getLoginInfo("COMPID"),curMgoodsorderinfo.getId().getOrderbillid()+"_"+strWareIds[i]));
						mgoodsouter.setOuterdate(CommonTool.getCurrDate());
						mgoodsouter.setOutertime(CommonTool.getCurrTime());
						mgoodsouter.setBillflag(0);
						mgoodsouter.setOuterwareid(strWareIds[i]);
						mgoodsouter.setOutertype(1);
						mgoodsouter.setRevicetype(2);
						mgoodsouter.setOuterstaffid(curMgoodsorderinfo.getId().getOrdercompid());
						mgoodsouter.setSendbillid(curMgoodsorderinfo.getOrderstaffid());
						mgoodsouter.setOrderbilltype(curMgoodsorderinfo.getOrderbilltype());
						mgoodsouter.setOuteropationerid(curMgoodsorderinfo.getOrderstaffid());
						mgoodsouter.setInvalid(0);
					}
					else
					{
						mgoodssendinfo=new Mgoodssendinfo();
						mgoodssendinfo.setId(new MgoodssendinfoId(CommonTool.getLoginInfo("COMPID"),curMgoodsorderinfo.getId().getOrderbillid()+"_"+strWareIds[i]));
						mgoodssendinfo.setSenddate(CommonTool.getCurrDate());
						mgoodssendinfo.setSendtime(CommonTool.getCurrTime());
						mgoodssendinfo.setOrderdate(curMgoodsorderinfo.getOrderdate());
						mgoodssendinfo.setOrdertime(curMgoodsorderinfo.getOrdertime());
						mgoodssendinfo.setSendstate(0);
						mgoodssendinfo.setStorewareid(curMgoodsorderinfo.getStorewareid());
						mgoodssendinfo.setHeadwareid(strWareIds[i]);
						mgoodssendinfo.setOrdercompid(curMgoodsorderinfo.getId().getOrdercompid());
						mgoodssendinfo.setOrderbill(curMgoodsorderinfo.getId().getOrderbillid());
						mgoodssendinfo.setOrderbilltype(curMgoodsorderinfo.getOrderbilltype());
						mgoodssendinfo.setStorestaffid(curMgoodsorderinfo.getOrderstaffid());
						mgoodssendinfo.setInvalid(0);
					}
					if(mgoodssendinfo!=null)
						
						this.amn_Dao.save(mgoodssendinfo);
					if(mgoodsouter!=null)
						this.amn_Dao.save(mgoodsouter);
					if(lsDgoodsorderinfo!=null && lsDgoodsorderinfo.size()>0)
					{
						for(int k=0;k<lsDgoodsorderinfo.size();k++)
						{
							if(CommonTool.FormatString(lsDgoodsorderinfo.get(k).getHeadwareno()).equals(strWareIds[i]))
							{
								if(strWareIds[i].equals("03")  || strWareIds[i].equals("05") || strWareIds[i].equals("06")  )
								{
									dgoodsouter=new Dgoodsouter();
									dgoodsouter.setId(new DgoodsouterId(CommonTool.getLoginInfo("COMPID"),curMgoodsorderinfo.getId().getOrderbillid()+"_"+strWareIds[i],k));
									dgoodsouter.setOutergoodsno(lsDgoodsorderinfo.get(k).getOrdergoodsno());
									dgoodsouter.setStandunit(lsDgoodsorderinfo.get(k).getOrdergoodsunit());
									dgoodsouter.setStandprice(lsDgoodsorderinfo.get(k).getOrdergoodsprice());
									dgoodsouter.setOuterunit(lsDgoodsorderinfo.get(k).getOrdergoodsunit());
									dgoodsouter.setOutercount(lsDgoodsorderinfo.get(k).getDownordercount());
									dgoodsouter.setOuterprice(lsDgoodsorderinfo.get(k).getOrdergoodsprice());
									dgoodsouter.setOuteramt(new BigDecimal(CommonTool.FormatBigDecimal(lsDgoodsorderinfo.get(k).getDownordercount()).doubleValue()*CommonTool.FormatBigDecimal(lsDgoodsorderinfo.get(k).getOrdergoodsprice()).doubleValue()));
									dgoodsouter.setOuterrate(new BigDecimal(1));
									lsDgoodsouter.add(dgoodsouter);
								}
								else
								{
									dgoodssendinfo=new Dgoodssendinfo();
									dgoodssendinfo.setId(new DgoodssendinfoId(CommonTool.getLoginInfo("COMPID"),curMgoodsorderinfo.getId().getOrderbillid()+"_"+strWareIds[i],k));
									dgoodssendinfo.setSendgoodsno(lsDgoodsorderinfo.get(k).getOrdergoodsno());
									dgoodssendinfo.setSendgoodsunit(lsDgoodsorderinfo.get(k).getOrdergoodsunit());
									dgoodssendinfo.setOrdergoodscount(lsDgoodsorderinfo.get(k).getOrdergoodscount());
									dgoodssendinfo.setOrdergoodsamt(lsDgoodsorderinfo.get(k).getOrdergoodsamt());
									dgoodssendinfo.setDownordercount(lsDgoodsorderinfo.get(k).getDownordercount());
									dgoodssendinfo.setSendgoodprice(lsDgoodsorderinfo.get(k).getOrdergoodsprice());
									dgoodssendinfo.setNodowncount(lsDgoodsorderinfo.get(k).getNodowncount());
									dgoodssendinfo.setSendgoodrate(new BigDecimal(1));
									lsDgoodssendinfo.add(dgoodssendinfo);
								}
							}
						}
						if(lsDgoodsouter!=null)
							  this.amn_Dao.saveOrUpdateAll(lsDgoodsouter);
						if(lsDgoodssendinfo!=null)
						  this.amn_Dao.saveOrUpdateAll(lsDgoodssendinfo);
					}
				}
			}
			//发给菲林的产品集合
			List<SaleOrderProduct> product = new ArrayList<SaleOrderProduct>();
			Map<String, SaleOrderProduct> map = new HashMap<String, SaleOrderProduct>();
			StringBuffer _sql = new StringBuffer();
			for (Dgoodsorderinfo dgoodsorderinfo : lsDgoodsorderinfo) {
				String ordergoodsno = dgoodsorderinfo.getOrdergoodsno();
				_sql.append(",'").append(ordergoodsno).append("'");
				SaleOrderProduct detail = new SaleOrderProduct();
				//detail.setProduct_id(ordergoodsno);
				detail.setQty(ObjectUtils.toString(dgoodsorderinfo.getDownordercount()));
				detail.setPrice(ObjectUtils.toString(dgoodsorderinfo.getOrdergoodsprice()));
				detail.setAmount(ObjectUtils.toString(dgoodsorderinfo.getOrdergoodsamt()));
				map.put(ordergoodsno, detail);
			}//为菲林的产品才能发送到菲林接口
			String sql = "select goodsno,bindgoodsno from Goodsinfo where goodspricetype='03' and goodssupplier='003' and goodsappsource=1 and goodsno in("+ StringUtils.substring(_sql.toString(), 1)+")";
			ResultSet rs = this.amn_Dao.executeQuery(sql);
			while (rs.next()) {
				String ordergoodsno = rs.getString("goodsno");
				SaleOrderProduct sop = map.get(ordergoodsno);
				if(!"0".equals(sop.getQty())){
					SaleOrderProduct _product = map.get(ordergoodsno);
					_product.setProduct_id(ObjectUtils.toString(rs.getString("bindgoodsno")));//增加望子供应商产品编码
					product.add(_product);
				}
			}
			map = null;
			if(product.size()>0){
				Gson gson = new Gson();
				SaleOrderInfo orderInfo = new SaleOrderInfo();
				orderInfo.setOrder_number(curMgoodsorderinfo.getId().getOrderbillid());
				orderInfo.setOrder_date(CommonTool.getDateMask(curMgoodsorderinfo.getDownorderdate()));
				String strCompId = curMgoodsorderinfo.getId().getOrdercompid();
				orderInfo.setCustomer_id(strCompId);
				orderInfo.setDelivery_address(this.getDataTool().loadCompAddressById(strCompId));
				orderInfo.setMemo("");
				orderInfo.setProduct(product);
		    	try {
		    		String saleOrderInfo = gson.toJson(orderInfo);
	    			String json = callWebService(saleOrderInfo);
	    			System.out.println("菲林接口返回："+ json);
	    			Map<String, String> result = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
	    			if(StringUtils.equals("0", result.get("retCode"))){//成功返回1 失败返回0
	    				deleteInfo(curMgoodsorderinfo, strWareIds);
	    				return result.get("message");
	    			}
				} catch (Exception e) {
					deleteInfo(curMgoodsorderinfo, strWareIds);
					e.printStackTrace();
					return "远程调用菲林下单接口失败！请刷新重新。";
				}
			}
			strWareIds=null;
			lsDgoodssendinfo=null;
			mgoodssendinfo=null;
			dgoodssendinfo=null;
			return "";
		}
		catch(Exception ex)
		{
			if(curMgoodsorderinfo != null && strWareIds != null){
				try {
					deleteInfo(curMgoodsorderinfo, strWareIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ex.printStackTrace();
			return "数据保存失败！";
		}
	}
	
	public void deleteInfo(Mgoodsorderinfo curMgoodsorderinfo, String[] strWareIds) throws Exception{
		String strSql=" update mgoodsorderinfo set orderstate=1,downordercompid='',downorderstaffid='',downorderdate='',downordertime=''," +
				" sendbillno='', revicebillno='' ,headwareid='',headwarename='' " +
				" where ordercompid='"+curMgoodsorderinfo.getId().getOrdercompid()+"' and orderbillid='"+curMgoodsorderinfo.getId().getOrderbillid()+"' ";
		strSql=strSql+" update dgoodsorderinfo set ordergoodstype=1 where ordercompid='"+curMgoodsorderinfo.getId().getOrdercompid()+"' and orderbillid='"+curMgoodsorderinfo.getId().getOrderbillid()+"' ";
		for (String wareId : strWareIds) {
			String billid=curMgoodsorderinfo.getId().getOrderbillid()+"_"+wareId;
			if("03".equals(wareId) || "05".equals(wareId) || "06".equals(wareId)){
				strSql+=" delete from dgoodsouter where outerbillid in (select outerbillid from mgoodsouter where outerbillid='"+billid+"' and billflag=0) delete from mgoodsouter where outerbillid='"+billid+"' and billflag=0 ";
			}else{
				strSql+=" delete from dgoodssendinfo where sendbillid in (select sendbillid from mgoodssendinfo where sendbillid='"+billid+"' and sendstate=0) delete from mgoodssendinfo where sendbillid='"+billid+"' and sendstate=0 ";
			}
		}
		this.amn_Dao.executeSql(strSql);
	}
	

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.saveOrUpdate(curMaster);
		return true;
	}
	
	//获取主档信息
	public List<Mgoodsorderinfo> loadMgoodsorderinfo()
	{
		try
		{
			String strSql="select  mgoodsorderinfo From Mgoodsorderinfo mgoodsorderinfo ,Compchaininfo compchaininfo  " +
					" where ordercompid=relationcomp and curcomp='"+CommonTool.getLoginInfo("COMPID")+"' " +
					" and isnull(invalid,0)=0 and isnull(orderstate,0)=1 order by orderdate desc ";
			return (List<Mgoodsorderinfo>)this.amn_Dao.findByHql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取主档信息
	public Mgoodsorderinfo loadMgoodsorderinfoById(String strCompId,String strBillId)
	{
		try
		{
			String strSql="select  ordercompid,orderbillid,orderdate,ordertime,orderstate,orderstaffid, " +
					"  downordercompid,downorderstaffid,downorderdate,downordertime,sendbillno,revicebillno,ordersource," +
					"  storewareid,headwareid,orderbilltype,orderopationerid,orderopationdate " +
					"  From  mgoodsorderinfo,compchaininfo " +
					" where ordercompid=relationcomp and curcomp='"+strCompId+"' and orderbillid='"+strBillId+"'  ";
			 AnlyResultSet<Mgoodsorderinfo> analysis = new AnlyResultSet<Mgoodsorderinfo>()
				{
					public Mgoodsorderinfo anlyResultSet(ResultSet rs)
					{
						Mgoodsorderinfo returnValue = new Mgoodsorderinfo();
						try
						{
							if(rs != null && rs.next()==true)
							{
								returnValue.setId(new MgoodsorderinfoId(CommonTool.FormatString(rs.getString("ordercompid")),CommonTool.FormatString(rs.getString("orderbillid"))));
								returnValue.setBordercompid(CommonTool.FormatString(rs.getString("ordercompid")));
								returnValue.setBorderbillid(CommonTool.FormatString(rs.getString("orderbillid")));
								returnValue.setOrderdate(CommonTool.getDateMask(rs.getString("orderdate")));
								returnValue.setOrdertime(CommonTool.getTimeMask(rs.getString("ordertime"),1));
								returnValue.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
								returnValue.setOrderstaffid(CommonTool.FormatString(rs.getString("orderstaffid")));
								returnValue.setDownordercompid(CommonTool.FormatString(rs.getString("downordercompid")));
								returnValue.setDownorderstaffid(CommonTool.FormatString(rs.getString("downorderstaffid")));
								returnValue.setDownorderdate(CommonTool.getDateMask(rs.getString("downorderdate")));
								returnValue.setDownordertime(CommonTool.getTimeMask(rs.getString("downordertime"),1));
								returnValue.setSendbillno(CommonTool.FormatString(rs.getString("sendbillno")));
								returnValue.setRevicebillno(CommonTool.getDateMask(rs.getString("revicebillno")));
								returnValue.setOrdersource(CommonTool.FormatInteger(rs.getInt("ordersource")));
								returnValue.setStorewareid(CommonTool.FormatString(rs.getString("storewareid")));
								returnValue.setHeadwareid(CommonTool.FormatString(rs.getString("headwareid")));
								returnValue.setOrderbilltype(CommonTool.FormatInteger(rs.getInt("orderbilltype")));
								returnValue.setOrderopationerid(CommonTool.FormatString(rs.getString("orderopationerid")));
								returnValue.setOrderopationdate(CommonTool.getDateMask(rs.getString("orderopationdate")));
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
				Mgoodsorderinfo returnRecord= (Mgoodsorderinfo)this.amn_Dao.executeQuery_ex(strSql,analysis);
				returnRecord.setBordercompname(this.dataTool.loadCompNameById(CommonTool.FormatString(returnRecord.getBordercompid())));
				StringBuffer validatemsg=new StringBuffer();
				returnRecord.setOrderstaffname(this.dataTool.loadEmpNameById(CommonTool.FormatString(returnRecord.getBordercompid()), CommonTool.FormatString(returnRecord.getOrderstaffid()),validatemsg));
				validatemsg=null;
				analysis=null;
				return returnRecord;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//获取明细信息
	 public List<Dgoodsorderinfo> loadDgoodsorderinfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 
			 String strModeId=this.dataTool.loadSysParam(strCompId,"SP061");
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
			 String strSql=" select ordercompid,orderbillid,ordergoodsno,goodsname,ordergoodscount,ordergoodsunit,ordergoodsprice,ordergoodsamt,downordercount,nodowncount,norevicecount,ordergoodstype ,headwareno,headstockcount,ordermark,goodspricetype,goodssource " +
			 		" From  dgoodsorderinfo,goodsnameinfo where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' " +
			 		" and  ordergoodsno=goodsno  order by   goodspricetype ";
			 AnlyResultSet<List<Dgoodsorderinfo>> analysis = new AnlyResultSet<List<Dgoodsorderinfo>>()
				{
					public List<Dgoodsorderinfo> anlyResultSet(ResultSet rs)
					{
						List<Dgoodsorderinfo> returnValue = new ArrayList();
						Dgoodsorderinfo record=null;
						try
						{
							while(rs != null && rs.next()==true)
							{
								record=new Dgoodsorderinfo();
								record.setId(new DgoodsorderinfoId(CommonTool.FormatString(rs.getString("ordercompid")),CommonTool.FormatString(rs.getString("orderbillid")),0));
								record.setOrdergoodsno(CommonTool.FormatString(rs.getString("ordergoodsno")));
								record.setOrdergoodsname(CommonTool.FormatString(rs.getString("goodsname")));
								record.setOrdergoodsunit(CommonTool.FormatString(rs.getString("ordergoodsunit")));
								record.setOrdergoodscount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodscount"))));
								record.setOrdergoodsprice(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsprice"))));
								record.setOrdergoodsamt(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("ordergoodsamt"))));
								record.setDownordercount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("downordercount"))));
								record.setNodowncount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("nodowncount"))));
								record.setNorevicecount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("norevicecount"))));
								record.setHeadstockcount(CommonTool.FormatBigDecimal(new BigDecimal(rs.getDouble("headstockcount"))));
								record.setOrdergoodstype(CommonTool.FormatInteger(rs.getInt("ordergoodstype")));
								record.setHeadwareno(CommonTool.FormatString(rs.getString("headwareno")));
								record.setOrdermark(CommonTool.FormatString(rs.getString("ordermark")));
								record.setGoodspricetype(CommonTool.FormatString(rs.getString("goodspricetype")));
								record.setStrSendBillId(CommonTool.FormatString(rs.getString("orderbillid"))+"_"+CommonTool.FormatString(rs.getString("headwareno")));
								record.setGoodssource(CommonTool.FormatInteger(rs.getInt("goodssource")));
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
				List<Dgoodsorderinfo> ls= (List<Dgoodsorderinfo>)this.amn_Dao.executeQuery_ex(strSql,analysis);
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
	 public Mgoodsorderinfo addMastRecord()
	 {
		 Mgoodsorderinfo record=new Mgoodsorderinfo();
		 record.setId(new MgoodsorderinfoId(CommonTool.getLoginInfo("COMPID"),this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"mgoodsorderinfo", "orderbillid", "SP012")));
		 record.setOrderdate(CommonTool.getDateMask(CommonTool.getCurrDate()));
		 record.setOrdertime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 1));
		 record.setBordercompid(CommonTool.getLoginInfo("COMPID"));
		 record.setBorderbillid(record.getId().getOrderbillid());
		 record.setOrderstate(0);
		 record.setInvalid(0);
		 return record;
	 }
	 
	
	 
	 //取消复合入库单
	 public boolean handcancelInfo(String strCompId,String strBillId)
	 {
		 try
		 {
			 String strSql=" update mgoodsorderinfo set orderstate=5 where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' ";
			 strSql=strSql+" update dgoodsorderinfo set ordergoodstype=5 where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' ";
			 
			 return this.amn_Dao.executeSql(strSql);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return false;
		 }
	 }
	 
	 //获得所有当月所有员工的订购产品金额
	 public double loadStaffGoodsAmt(String strCompId)
	 {
		 String strSql=" select ordergoodsamt=sum(ordergoodsamt) from mgoodsorderinfo a,dgoodsorderinfo b" +
		 		" where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid " +
		 		" and a.ordercompid='"+strCompId+"' and a.orderbilltype=2 and  substring(orderdate,1,6)='"+CommonTool.getCurrDate().substring(0,6)+"' and  isnull(orderstate,0)=1 ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
			{
				public Double anlyResultSet(ResultSet rs)
				{
					double returnValue = 0;
					try
					{
						if(rs != null && rs.next()==true)
						{
							returnValue=CommonTool.FormatDouble(rs.getDouble("ordergoodsamt"));
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
			double returnOrderamt= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return returnOrderamt;
	 }
	 
	 public String loadStoreByOrders(String strCompId,String strBillId)
	 {
		 String strSql=" select headwareno, max(case when 1=1 then goodssource end) goodssource from dgoodsorderinfo where ordercompid='"+strCompId+"' and orderbillid='"+strBillId+"' group by headwareno ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		 {
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue = "";
					try
					{
						while(rs != null && rs.next()==true)
						{
							if(!returnValue.equals(""))
								returnValue=returnValue+";";
							returnValue=returnValue+CommonTool.FormatString(rs.getString("headwareno"));
							returnValue+="#"+CommonTool.FormatString(rs.getString("goodssource"));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  "";
					}
					return returnValue;
				}
		};
		String returnOrderware= (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnOrderware;
	 }
	 
	 public double loadOnloadGoodstock(String strCompId,String strGoodNo)
	 {
		 String strSql=" select downordercount=sum(downordercount) from mgoodsorderinfo a,dgoodsorderinfo b" +
	 		" where a.ordercompid=b.ordercompid and a.orderbillid=b.orderbillid and isnull(b.goodssource,0)=0 " +
	 		" and a.downordercompid='"+strCompId+"'  and isnull(b.ordergoodstype,0)=2 and  ordergoodsno='"+strGoodNo+"' ";
		 AnlyResultSet<Double> analysis = new AnlyResultSet<Double>()
		{
			public Double anlyResultSet(ResultSet rs)
			{
				double returnValue = 0;
				try
				{
					if(rs != null && rs.next()==true)
					{
						returnValue=CommonTool.FormatDouble(rs.getDouble("downordercount"));
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
		double returnGoodsStock= (Double)this.amn_Dao.executeQuery_ex(strSql,analysis);
		analysis=null;
		return returnGoodsStock;
	 }
	 
	 /**
	  * 根据供应商编号查询供应商名称
	  * @param supplierno
	  * @return
	  */
	 public String loadSupplierName(String supplierno){
		 String strSql=" select name from suppliermode where no='"+ supplierno +"' ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
			{
				public String anlyResultSet(ResultSet rs)
				{
					String returnValue ="";
					try{
						if(rs != null && rs.next()){
							returnValue=rs.getString("name");
						}				
					}catch(Exception e){
						e.printStackTrace();
						returnValue = "";
					}
					return returnValue;
				}
			};
			String suppliername = (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
			analysis=null;
			return suppliername;
	 }
	 /**
	  * 根据供应商编号查询供应商名称
	  * @param supplierno
	  * @return
	  */
	 public String loadSupplierLink(String supplierno){
		 String strSql=" select mobile from supplierlink where ismain=1 and supplierno='"+ supplierno +"' ";
		 AnlyResultSet<String> analysis = new AnlyResultSet<String>()
		 {
			 public String anlyResultSet(ResultSet rs)
			 {
				 String returnValue ="";
				 try{
					 if(rs != null && rs.next()){
						 returnValue=rs.getString("mobile");
					 }				
				 }catch(Exception e){
					 e.printStackTrace();
					 returnValue = "";
				 }
				 return returnValue;
			 }
		 };
		 String mobile = (String)this.amn_Dao.executeQuery_ex(strSql,analysis);
		 analysis=null;
		 return mobile;
	 }
	 
	 private String callWebService(String saleOrderInfo) throws ServiceException, RemoteException{
		// webservice路径  
         // 这里后面加不加 "?wsdl" 效果都一样的  
         String endpoint = "http://61.152.163.198:8088/services/AmaniWebService?wsdl";  
         //String endpoint = "http://123.122.97.226:8888/services/AmaniWebService?wsdl";//测试环境
         // 发送短信接口方法名  
         String operationName = "sendSaleOrder";  
         String targetNamespace = "http://service.amani.webService.portal.gwt.wangzi.haomin.com/";  
         // 定义service对象  
         org.apache.axis.client.Service service = new org.apache.axis.client.Service();  
         // 创建一个call对象  
         Call call = (Call) service.createCall();  
         // 设置目标地址，即webservice路径  
         call.setTargetEndpointAddress(endpoint);  
         // 设置操作名称，即方法名称  
         call.setOperationName(new QName(targetNamespace, operationName));  
         // 设置方法参数  
         call.addParameter(new QName(targetNamespace, "saleOrderInfo"), XMLType.XSD_STRING, ParameterMode.IN); 
         call.setReturnClass(String.class);  
         // 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值  
         call.setUseSOAPAction(true);  
         call.setSOAPActionURI(targetNamespace + operationName);
         Object obj = call.invoke(new Object[]{saleOrderInfo});
         String result = ObjectUtils.toString(obj);
         if(StringUtils.isBlank(result)){
        	 result = "{\"message\":\"菲林接口返回结果为空！\",\"retCode\":\"0\"}";
         }
         return result;
	 }
}

