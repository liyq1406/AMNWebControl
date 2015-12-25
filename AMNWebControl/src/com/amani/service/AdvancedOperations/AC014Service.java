package com.amani.service.AdvancedOperations;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.MemberOrderInfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
import com.amani.tools.SysSendMsg;
@Service
public class AC014Service extends AMN_ReportService{
	@Autowired
	protected static SysSendMsg sysSendMsg;
	public SysSendMsg getSysSendMsg() {
		return sysSendMsg;
	}
	public void setSysSendMsg(SysSendMsg sysSendMsg) {
		this.sysSendMsg = sysSendMsg;
	}

	public List<MemberOrderInfo> loadDateSetByCompId(String strCompId,String strFromDate,String strToDate)
	{
		try
		{
			String strSql="select ordersid,orderconply,compname,compaddress,orderphone,ordertime,orderproject=case when ISNULL(orderproject,'')='300' then '美发项目'  when ISNULL(orderproject,'')='400' then '美容项目' when ISNULL(orderproject,'')='600' then '烫染护项目' else '' end   ," +
					" orderusermf,orderusermfname=b.staffname,orderusermr,orderusermrname=c.staffname,orderusertrh ,orderusertrhname=d.staffname " +
					" from  compchaininfo,companyinfo a,orders " +
					" left join staffinfo b on orderconply=b.compno and b.staffno=orderusermf " +
					" left join staffinfo c on orderconply=c.compno and c.staffno=orderusermr " +
					" left join staffinfo d on orderconply=d.compno and d.staffno=orderusertrh " +
					" where isnull(orderstate,0)<>2 and  curcomp='"+strCompId+"' and relationcomp=orderconply and  orderconply=a.compno  and replace(substring(isnull(ordertime,''),1,10),'-','') between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' ";
			
			AnlyResultSet<List<MemberOrderInfo>> analysis = new AnlyResultSet<List<MemberOrderInfo>>() {
				public List<MemberOrderInfo> anlyResultSet(ResultSet rs) {
					List<MemberOrderInfo> returnValue = new ArrayList();
					MemberOrderInfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new MemberOrderInfo();
							record.setOrdersid(CommonTool.FormatInteger(rs.getInt("ordersid")));
							record.setStrCompId(CommonTool.FormatString(rs.getString("orderconply")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
							record.setStrCompAddress(CommonTool.FormatString(rs.getString("compaddress")));
							record.setStrMemberPhone(CommonTool.FormatString(rs.getString("orderphone")));
							record.setStrOrderDate(CommonTool.FormatString(rs.getString("ordertime")));
							record.setStrOrderProject(CommonTool.FormatString(rs.getString("orderproject")));
							record.setStrMfEmpNo(CommonTool.FormatString(rs.getString("orderusermf"))+"-"+CommonTool.FormatString(rs.getString("orderusermfname")));
							record.setStrMrEmpNo(CommonTool.FormatString(rs.getString("orderusermr"))+"-"+CommonTool.FormatString(rs.getString("orderusermrname")));
							record.setStrTrhEmpNo(CommonTool.FormatString(rs.getString("orderusertrh"))+"-"+CommonTool.FormatString(rs.getString("orderusertrhname")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<MemberOrderInfo> ls= (List<MemberOrderInfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	
	public List<MemberOrderInfo> loadFactDateSetByCompId(String strCompId,String strFromDate,String strToDate)
	{
		try
		{
			String strSql="select ordersid,orderconply,compname,orderphone,orderfactdate,orderfacttime,orderfactproject,orderfactempid,orderdetail " +
					" from  compchaininfo,companyinfo a,orders "+
					" where isnull(orderstate,0)=2 and  curcomp='"+strCompId+"' and relationcomp=orderconply and  orderconply=a.compno  and replace(isnull(orderfactdate,''),'-','') between '"+CommonTool.setDateMask(strFromDate)+"' and '"+CommonTool.setDateMask(strToDate)+"' ";
			
			AnlyResultSet<List<MemberOrderInfo>> analysis = new AnlyResultSet<List<MemberOrderInfo>>() {
				public List<MemberOrderInfo> anlyResultSet(ResultSet rs) {
					List<MemberOrderInfo> returnValue = new ArrayList();
					MemberOrderInfo record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new MemberOrderInfo();
							record.setOrdersid(CommonTool.FormatInteger(rs.getInt("ordersid")));
							record.setStrCompId(CommonTool.FormatString(rs.getString("orderconply")));
							record.setStrCompName(CommonTool.FormatString(rs.getString("compname")));
							record.setStrMemberPhone(CommonTool.FormatString(rs.getString("orderphone")));
							record.setOrderfactdate(CommonTool.FormatString(rs.getString("orderfactdate")));
							record.setOrderfacttime(CommonTool.FormatString(rs.getString("orderfacttime")));
							record.setOrderfactproject(CommonTool.FormatString(rs.getString("orderfactproject")));
							record.setOrderfactempid(CommonTool.FormatString(rs.getString("orderfactempid")));
							record.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			List<MemberOrderInfo> ls= (List<MemberOrderInfo>) this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postFactOrders(int ordersid,String orderfactdate,String orderfacttime,String orderfactproject,String orderfactempid,String orderconfirmmsg,String orderdetail)
	{
		try
		{
			String strSql=" update orders set orderstate=2,orderfactdate='"+orderfactdate+"',orderfacttime='"+orderfacttime+"',orderfactproject='"+orderfactproject+"',orderfactempid='"+orderfactempid+"',orderconfirmmsg='"+orderconfirmmsg+"',orderdetail='"+orderdetail+"',confirmdate='"+CommonTool.getCurrDate()+"',confirmemp='"+CommonTool.getLoginInfo("USERID")+"'  " +
					" where ordersid="+ordersid+" ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	public String sendMsg(String destMobile,String msgText) throws Exception{
		return this.sysSendMsg.sendMsg("001", destMobile, msgText);
	}

}
