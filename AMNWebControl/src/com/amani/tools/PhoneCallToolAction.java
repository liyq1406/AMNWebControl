package com.amani.tools;

import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;

import com.amani.dao.AMN_DaoImp;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/phoneCallTool")
public class PhoneCallToolAction {
	@Resource(name="amn_Dao")
	private AMN_DaoImp amn_Dao;
	private String callno;		//表示来电主叫号码
	private String calledno;	//表示来电被叫号码    即：客户拨打哪个号码进入呼叫中心系统的。
	private String agentnum;
	private String offertime;
	private String strMessage;
	private int callstate;  //呼叫等待状态

	public String getCallno() {
		return callno;
	}
	public void setCallno(String callno) {
		this.callno = callno;
	}
	public String getCalledno() {
		return calledno;
	}
	public void setCalledno(String calledno) {
		this.calledno = calledno;
	}
	public String getAgentnum() {
		return agentnum;
	}
	public void setAgentnum(String agentnum) {
		this.agentnum = agentnum;
	}
	public String getOffertime() {
		return offertime;
	}
	public void setOffertime(String offertime) {
		this.offertime = offertime;
	}
	public int getCallstate() {
		return callstate;
	}
	public void setCallstate(int callstate) {
		this.callstate = callstate;
	}
	//来电等待
	@Action( value="addcallwating", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
			 public String addcallwating(){
		boolean flag= addcallswating(callno, calledno, agentnum, offertime,callstate);
		if(flag==false)
			this.strMessage="获取失败";
		else
			this.strMessage="获取成功";
		 return SystemFinal.LOAD_SUCCESS;
		}
 
//添加呼叫等待信息
	public boolean addcallswating(String call_no,String called_no,String agent_num,String offer_time,Integer callstate){
		boolean bte=true;
		String sql="insert callswaiting(callon,calledon,agentnum,offertime,callstate) values("
			+CommonTool.quotedStr(call_no)
			+","
			+CommonTool.quotedStr(called_no)
			+","
			+CommonTool.quotedStr(agent_num)
			+","
			+CommonTool.quotedStr(offer_time)
			+","
			+CommonTool.FormatInteger(callstate) +")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	@JSON(serialize=false)
	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}
	@JSON(serialize=false)
	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
}
