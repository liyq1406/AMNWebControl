package com.amani.service.InvoicingControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC021Service  extends  AMN_ReportService{
	
	public Map<String,List<CommonBean>> querySet(String strCurCompId, String beginDate, String endDate,String projectKind){
		StringBuffer sql = new StringBuffer();//a.senddate,b.receiptsenddate,b.receiptdate,
		sql.append("with orders as(select a.ordercompid,c.compname,a.orderbill,a.orderdate,a.sendbillid,b.receiptbillid,") 
		.append("case when b.receiptstate=1 then 4 when b.receiptstate=0 or a.sendstate=1 then 3 when a.sendstate=0 then 2 else 1 end orderstate ")
		.append("from mgoodssendinfo a left join mgoodsreceipt b on a.sendbillid=b.receiptsendbillid and a.orderbill=b.receiptorderbillid, companyinfo c ")
		.append("where a.ordercompid=c.compno ")
		.append("and a.headwareid='"+ projectKind +"' and a.ordercompid in(select relationcomp from compchaininfo where curcomp='"+ strCurCompId +"') ")
		.append("and a.orderdate between '"+beginDate+"' and '"+endDate+"') ")
		.append("select a.*,d.sendgoodsno,e.goodsname,d.downordercount,e.purchaseprice,e.costamtbysale from orders a ")
		.append("left join dgoodssendinfo d on a.sendbillid=d.sendbillid, goodsinfo e where e.goodsno=d.sendgoodsno ")
		.append("order by orderstate desc, orderdate desc");
		AnlyResultSet<Map<String,List<CommonBean>>> analysis = new AnlyResultSet<Map<String,List<CommonBean>>>() {
			public Map<String,List<CommonBean>> anlyResultSet(ResultSet rs) {
				Map<String,List<CommonBean>> hashMap = new HashMap<String,List<CommonBean>>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record = new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("ordercompid")));	//ordercompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("compname")));  	//compname 公司名称
						//record.setAttr3(beginDate);  	//orderbillid 订单编号 
						record.setAttr3(ObjectUtils.toString(rs.getString("sendgoodsno")));//产品编码
						record.setAttr4(ObjectUtils.toString(rs.getString("goodsname")));//产品名称
						int  shcpCount = rs.getInt("downordercount");//收货产品数量
						float price = rs.getFloat("purchaseprice");//总部产品单价
						record.setAttr5(ObjectUtils.toString(shcpCount));
						//总部收货价格
						record.setAttr6(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(shcpCount*price))));
						float mdprice = rs.getFloat("costamtbysale");//门店产品单价
						record.setAttr7(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice))));
						//总部收货价格
						record.setAttr8(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice*shcpCount))));
						//收货订单号
						String shdd = ObjectUtils.toString(rs.getString("receiptbillid"));
						record.setAttr9(shdd);
						//收货订单号
						String fhdd = ObjectUtils.toString(rs.getString("sendbillid"));
						record.setAttr10(fhdd);
						record.setAttr11(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(price))));
						record.setAttr12(ObjectUtils.toString(rs.getString("orderbill")));
						String status = ObjectUtils.toString(rs.getString("orderstate"));
						if("2".equals(status)){
							record.setAttr13("已分单");
						}else if("3".equals(status)){
							record.setAttr13("已发货");
						}else if("4".equals(status)){
							record.setAttr13("已收货");
						}else{
							record.setAttr13("已作废");
						}
						if(hashMap.get(shdd)==null){
							List<CommonBean> returnValue = new ArrayList<CommonBean>();
							returnValue.add(record);
							hashMap.put(shdd, returnValue);
						}else{
							List<CommonBean> list = hashMap.get(shdd);
							list.add(record);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					hashMap =null;
				}
				return hashMap;
			}
		};
		@SuppressWarnings("unchecked")
		Map<String,List<CommonBean>> list = (Map<String,List<CommonBean>>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
		analysis=null;
		return list;
	}
	
	public Map<String,List<CommonBean>> queryAllSet(String beginDate, String endDate,String projectKind){
		String curcomp = CommonTool.getLoginInfo("COMPID");
		StringBuffer sql = new StringBuffer();
		sql.append("with orders as(select a.orderdate,a.sendbillid,") 
		.append("case when b.receiptstate=1 then 4 when b.receiptstate=0 or a.sendstate=1 then 3 else 1 end orderstate ")
		.append("from mgoodssendinfo a left join mgoodsreceipt b on a.sendbillid=b.receiptsendbillid and a.orderbill=b.receiptorderbillid ")
		.append("where a.headwareid='"+ projectKind +"' and a.ordercompid in(select relationcomp from compchaininfo where curcomp='"+ curcomp +"') ")
		.append("and (b.receiptstate in(1,0) or a.sendstate=1) and a.orderdate between '"+beginDate+"' and '"+endDate+"') ")
		.append("select a.*,d.sendgoodsno,e.goodsname,d.downordercount,e.purchaseprice,e.costamtbysale from orders a ")
		.append("left join dgoodssendinfo d on a.sendbillid=d.sendbillid, goodsinfo e where e.goodsno=d.sendgoodsno ")
		.append("order by orderstate desc, orderdate desc");
		AnlyResultSet<Map<String,List<CommonBean>>> analysis = new AnlyResultSet<Map<String,List<CommonBean>>>() {
			public Map<String,List<CommonBean>> anlyResultSet(ResultSet rs) {
				Map<String,List<CommonBean>> hashMap = new HashMap<String,List<CommonBean>>();
				List<CommonBean> four = new ArrayList<CommonBean>(), three = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record = new CommonBean();
						record.setAttr3(ObjectUtils.toString(rs.getString("sendgoodsno")));//产品编码
						record.setAttr4(ObjectUtils.toString(rs.getString("goodsname")));//产品名称
						int  shcpCount = rs.getInt("downordercount");//收货产品数量
						float price = rs.getFloat("purchaseprice");//总部产品单价
						record.setAttr5(ObjectUtils.toString(shcpCount));
						//总部收货价格
						record.setAttr6(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(shcpCount*price))));
						float mdprice = rs.getFloat("costamtbysale");//门店产品单价
						record.setAttr7(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice))));
						//总部收货价格
						record.setAttr8(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice*shcpCount))));
						record.setAttr11(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(price))));
						String status = ObjectUtils.toString(rs.getString("orderstate"));
						if("3".equals(status)){
							three.add(record);
						}else if("4".equals(status)){
							four.add(record);
						}
					}
					hashMap.put("three", three);
					hashMap.put("four", four);
				} catch (Exception e) {
					e.printStackTrace();
					hashMap =null;
				}
				return hashMap;
			}
		};
		@SuppressWarnings("unchecked")
		Map<String,List<CommonBean>> list = (Map<String,List<CommonBean>>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
		analysis=null;
		return list;
	}
	
	public Map<String, Map<String, List<CommonBean>>> queryCompanySet(String beginDate, String endDate,String projectKind){
		String curcomp = CommonTool.getLoginInfo("COMPID");
		StringBuffer sql = new StringBuffer();
		sql.append("with orders as(select a.ordercompid,a.orderdate,a.sendbillid,") 
		.append("case when b.receiptstate=1 then 4 when b.receiptstate=0 or a.sendstate=1 then 3 else 1 end orderstate ")
		.append("from mgoodssendinfo a left join mgoodsreceipt b on a.sendbillid=b.receiptsendbillid and a.orderbill=b.receiptorderbillid ")
		.append("where a.headwareid='"+ projectKind +"' and a.ordercompid in(select relationcomp from compchaininfo where curcomp='"+ curcomp +"') ")
		.append("and (b.receiptstate in(1,0) or a.sendstate=1) and a.orderdate between '"+beginDate+"' and '"+endDate+"') ")
		.append("select a.*,d.sendgoodsno,e.goodsname,d.downordercount,e.purchaseprice,e.costamtbysale from orders a ")
		.append("left join dgoodssendinfo d on a.sendbillid=d.sendbillid, goodsinfo e where e.goodsno=d.sendgoodsno ")
		.append("order by orderstate desc, orderdate desc");
		AnlyResultSet<Map<String, Map<String, List<CommonBean>>>> analysis = new AnlyResultSet<Map<String, Map<String, List<CommonBean>>>>() {
			public Map<String, Map<String, List<CommonBean>>> anlyResultSet(ResultSet rs) {
				Map<String, Map<String, List<CommonBean>>> hashMap = new HashMap<String, Map<String,List<CommonBean>>>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record = new CommonBean();
						record.setAttr3(ObjectUtils.toString(rs.getString("sendgoodsno")));//产品编码
						record.setAttr4(ObjectUtils.toString(rs.getString("goodsname")));//产品名称
						int  shcpCount = rs.getInt("downordercount");//收货产品数量
						float price = rs.getFloat("purchaseprice");//总部产品单价
						record.setAttr5(ObjectUtils.toString(shcpCount));
						//总部收货价格
						record.setAttr6(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(shcpCount*price))));
						float mdprice = rs.getFloat("costamtbysale");//门店产品单价
						record.setAttr7(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice))));
						//总部收货价格
						record.setAttr8(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(mdprice*shcpCount))));
						record.setAttr11(String.format("%.2f", Double.parseDouble(ObjectUtils.toString(price))));
						String status = ObjectUtils.toString(rs.getString("orderstate"));
						String key = ObjectUtils.toString(rs.getString("ordercompid"));
						Map<String, List<CommonBean>> compSet = hashMap.get(key);
						if(compSet==null){
							compSet = new HashMap<String, List<CommonBean>>();
							List<CommonBean> four = new ArrayList<CommonBean>(), three = new ArrayList<CommonBean>();
							compSet.put("three", three);
							compSet.put("four", four);
							hashMap.put(key, compSet);
						}
						if("3".equals(status)){
							compSet.get("three").add(record);
						}else if("4".equals(status)){
							compSet.get("four").add(record);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					hashMap =null;
				}
				return hashMap;
			}
		};
		@SuppressWarnings("unchecked")
		Map<String, Map<String, List<CommonBean>>> list = (Map<String, Map<String, List<CommonBean>>>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
		analysis=null;
		return list;
	}
	public List<CommonBean> loadCompanyData(){
		String curcomp = CommonTool.getLoginInfo("COMPID");
		String sql =" select  * from (select a.compname,a.compno from companyinfo a LEFT JOIN compchainstruct b on a.compno=b.curcompno where a.compstate=1 and b.complevel=4 ) as c left JOIN compchaininfo d" +
				" on c.compno = d.relationcomp  where d.curcomp = '"+curcomp+"'";
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
