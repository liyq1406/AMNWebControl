package com.amani.service.InvoicingControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class IC020Service extends  AMN_ReportService{
	
	public List<CommonBean> querySet(String strCurCompId, String beginDate, String endDate,String projectKind,String status){
		StringBuffer sql = new StringBuffer();
		sql.append("with orders as(select a.ordercompid compid,c.compname,a.orderbill,a.orderdate,a.senddate,b.receiptsenddate,b.receiptdate,") 
		.append("case when b.receiptstate=1 then 4 when b.receiptstate=0 or a.sendstate=1 then 3 when a.sendstate=0 then 2 else 1 end orderstate ")
		.append("from mgoodssendinfo a left join mgoodsreceipt b on a.sendbillid=b.receiptsendbillid and a.orderbill=b.receiptorderbillid, companyinfo c ")
		.append("where a.ordercompid=c.compno ")
		.append("and a.headwareid='"+ projectKind +"' and a.ordercompid in(select relationcomp from compchaininfo where curcomp='"+ strCurCompId +"') ")
		.append("and a.orderdate between '"+beginDate+"' and '"+endDate+"') select * from orders ");
		if(!"-1".equals(status)){
			sql.append("where orderstate="+status);
		}
		sql.append(" order by orderdate");
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("compid")));	//ordercompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("compname")));  	//compname 公司名称
						record.setAttr3(ObjectUtils.toString(rs.getString("orderbill")));  	//orderbillid 订单编号 
						record.setAttr4(CommonTool.getDateMask(rs.getString("orderdate")));  	//orderdate 订货日期
						record.setAttr5(CommonTool.getDateMask(rs.getString("senddate")));  	//downorderdate 下单日期
						record.setAttr6(CommonTool.getDateMask(rs.getString("receiptsenddate")));  	//senddate 发货日期
						record.setAttr7(CommonTool.getDateMask(rs.getString("receiptdate")));  	//receiptdate 收货日期
						String status = ObjectUtils.toString(rs.getString("orderstate"));
						if("2".equals(status)){
							record.setAttr8("已分单");  	//ordergoodstype 订单状态
						}else if("3".equals(status)){
							record.setAttr8("已发货");  	//ordergoodstype 订单状态
						}else if("4".equals(status)){
							record.setAttr8("已收货");  	//ordergoodstype 订单状态
						}else{
							record.setAttr8("已作废");
						}
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
		List<CommonBean> list = (List<CommonBean>) this.amn_Dao.executeQuery_ex(sql.toString(), analysis);
		analysis=null;
		return list;
	}
}
