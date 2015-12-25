package com.amani.service.InvoicingControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ReportService;
@Service
public class IC022Service extends  AMN_ReportService{
	public List<CommonBean> querySet(String beginDate, String endDate,String goodNo,String comId){
		String goodSql = "";
		String comSql = "";
	    if("0".equals(goodNo)){
	    	goodSql = " where 1=1 and b.receiptsupplier='WZ'  GROUP BY a.receiptcompid,a.receiptbillid,a.receiptgoodsno,b.receiptdate,b.receiptorderbillid ";
	    }else{
	    	goodSql = " where a.receiptgoodsno in('"+goodNo+"') and b.receiptsupplier='WZ' GROUP BY a.receiptcompid,a.receiptbillid,a.receiptgoodsno,b.receiptdate,b.receiptorderbillid";
	    }
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer.append(" with sh as (SELECT t2.receiptcompid,d.compname,t2.receiptbillid,t2.receiptgoodscount,t2.receiptdate,t2.receiptgoodsno,t2.purchaseprice,t2.costamtbysale,t2.receiptorderbillid ")
				 .append(" FROM( SELECT t1.receiptcompid,t1.receiptbillid,t1.receiptgoodscount,t1.receiptdate,t1.receiptgoodsno,t1.receiptorderbillid,c.purchaseprice,c.costamtbysale FROM( ")
				 .append(" SELECT a.receiptcompid,a.receiptbillid,sum(a.receiptgoodscount) as receiptgoodscount,a.receiptgoodsno,b.receiptdate,b.receiptorderbillid")
				 .append(" 	FROM dgoodsreceipt a LEFT JOIN mgoodsreceipt b ON b.receiptbillid = a.receiptbillid")
				 .append(" 	"+goodSql+" ) t1")
				 .append(" 	LEFT JOIN goodsinfo c ON t1.receiptgoodsno = c.goodsno ) t2")
				 .append(" LEFT JOIN companyinfo d ON d.compno = t2.receiptcompid ),")
				 .append(" shAndDh as(select h.* from sh h LEFT JOIN mgoodsorderinfo m on h.receiptorderbillid=m.orderbillid where m.orderdate BETWEEN '"+beginDate+"' AND '"+endDate+"' ),")
				 .append(" mendian as(select relationcomp from compchaininfo where curcomp ='"+comId+"' ),")
				 .append(" flgood as (select goodsno,goodsname from Goodsinfo where goodspricetype='03' and goodssupplier='003' and goodsappsource=1)")
				 .append(" select s.*,g.goodsname from shAndDh s LEFT JOIN mendian m on s.receiptcompid = m.relationcomp LEFT JOIN goodsnameinfo g on g.goodsno=s.receiptgoodsno ")
				 .append(" where s.receiptgoodsno in(select goodsno from flgood)");
		
		String sql =sqlbuffer.toString();
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						int  downordercount=rs.getInt("receiptgoodscount");//成交量
						float purchaseprice=rs.getFloat("purchaseprice");//产品进价(标准单位)	--总部进货价(入库)
						float costamtbysale=rs.getFloat("costamtbysale");//costamtbysale 销售成本(标准单位)   --门店进货价(总部出库/发货)
						//总部收货价格
						String purchaseAmt= String.format("%.2f", Double.parseDouble(ObjectUtils.toString(downordercount*purchaseprice)));
						String costAmt= String.format("%.2f", Double.parseDouble(ObjectUtils.toString(downordercount*costamtbysale)));//门店进货金额
						record.setAttr1(ObjectUtils.toString(rs.getString("receiptcompid")));	//ordercompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("receiptbillid"))+"-"+ObjectUtils.toString(rs.getString("receiptorderbillid")));  	//orderbillid 订单号
						record.setAttr3(ObjectUtils.toString(downordercount));  	//downordercount 下单数量
						record.setAttr4(ObjectUtils.toString(purchaseprice));  	//产品进价(标准单位)	--总部进货价(入库)
						record.setAttr5(purchaseAmt);  	//厂家结算金额
						record.setAttr6(ObjectUtils.toString(costamtbysale));//costamtbysale 销售成本(标准单位)   --门店进货价(总部出库/发货)
						record.setAttr7(costAmt);  	//门店结算金额
						record.setAttr8(ObjectUtils.toString(rs.getString("goodsname")));
						record.setAttr9(ObjectUtils.toString(rs.getString("compname")));  //compname 门店名称
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
  public List<CommonBean> queryGoodsList(){
		String sql ="select goodsno,goodsname from Goodsinfo where goodspricetype='03' and goodssupplier='003' and goodsappsource=1";
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("goodsno")));	//goodsno 产品编号
						record.setAttr2(ObjectUtils.toString(rs.getString("goodsname")));  	//goodsname 产品名称
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

