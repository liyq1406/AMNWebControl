package com.amani.action.AdvancedOperations;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.Call;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.amani.action.AMN_ModuleAction;
import com.amani.bean.CallsWatingBean;
import com.amani.model.Companyinfo;
import com.amani.model.Staffinfo;
import com.amani.service.AdvancedOperations.AC002Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac002")
public class AC002Action extends AMN_ModuleAction{
	@Autowired
	private  AC002Service ac002Service;
	private List<CallsWatingBean> lsCallsWatingBean;
	private List<CallsWatingBean> lsCallsWatingBeans;
	private List<CallsWatingBean> lsCallsWatingBeanes;
	private List<CallsWatingBean> lslsCallsWating;
	private List<CallsWatingBean> lslsCallsByUser;
	private List<CallsWatingBean> lstpeiier;
	private List<CallsWatingBean> lsundisposedcall;
	private List<CallsWatingBean> lsmessageundisposedcall;
	private List<Companyinfo> lsCompanyinfo;
	private CallsWatingBean callsWatingBean;
	private CallsWatingBean callsWatingBeans;
	private CallsWatingBean callsWatingBeanes;
	private List<Staffinfo>	curStaffInfos;
	private List<CallsWatingBean> lsrefer;
	private List<CallsWatingBean> lscardreturn;
	private List<CallsWatingBean> lsorders;
	private List<CallsWatingBean> lsphone;
	private String callon;
	private String callbillno;
	
	/**
	 * 查询录音信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "seleu",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String seleu() throws Exception{
		this.lsCallsWatingBean=this.ac002Service.loadFileInfo(this.time,this.phone);
		return "load_success";
	}	
	@Action(value = "selectall",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectall() throws Exception{
		//来电会员信息
		this.callsWatingBean=this.ac002Service.selectcall();
		//查询坐席当天接听的所有电话
		this.lsCallsWatingBeanes=this.ac002Service.loadAllCallsByUseId();
		//所有未处理单据
		this.lsundisposedcall=this.ac002Service.undisposedcall();
		//所有未处理的短息单据
		this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("open");
		return "load_success";
	}	
	/**
	 * 短信弹屏
	 * @return
	 * @throws Exception
	 */
	@Action(value = "checkLoadMessage",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String checkLoadMessage() throws Exception
	{
		this.strCheckFlag="Y";
		//查询是否有未处理的短信
		boolean isstate=this.ac002Service.isMessage();
		if(isstate==true)
		{
			this.strCheckFlag="N";//有来电未处理（短信）
//			this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"orders", "callbillid","SP012");
			//来电信息主档
//			this.lsCallsWatingBean=this.ac002Service.selectorders(callbillid);
			//查询坐席当天接听的所有电话
//			this.lsCallsWatingBeanes=this.ac002Service.loadAllCallsByUseIds(callbillid);
			//所有未处理单据
//			this.lsundisposedcall=this.ac002Service.undisposedcall();
			//所有未处理的短息单据
			this.lsmessageundisposedcall=this.ac002Service.getAllOpenMessageundisposedcall();
		}
		return "load_success";
	}	
	/**
	 * 微信弹屏
	 * @return
	 * @throws Exception
	 */
	@Action(value = "checkmessage",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String checkmessage() throws Exception
	{
		this.strCheckFlag="Y";
		boolean isstate=this.ac002Service.isCallmessage();
		if(isstate==true)
		{
			this.strCheckFlag="N";//有来电未处理
			this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"orders", "callbillid","SP012");
			//来电信息主档
			this.lsCallsWatingBean=this.ac002Service.selectorders(callbillid);
			//查询坐席当天接听的所有电话
			this.lsCallsWatingBeanes=this.ac002Service.loadAllCallsByUseIds(callbillid);
			//所有未处理单据
			this.lsundisposedcall=this.ac002Service.undisposedcall();
			//所有未处理的短息单据
			this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("open");
		}
		return "load_success";
	}
	
	/**
	 * 来电弹屏
	 * @return
	 * @throws Exception
	 */
	@Action(value = "checkcalldate",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String checkcalldate() throws Exception
	{
		this.strCheckFlag="Y";
		boolean isstate=this.ac002Service.isCallExist();
		if(isstate==true)
		{
			this.strCheckFlag="N";//有来电未处理
			this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
			//来电信息主档
			this.lsCallsWatingBean=this.ac002Service.selectcallswating(callbillid);
			//来电会员信息
			this.callsWatingBean=this.ac002Service.selectcallswatingbytime();
			//查询坐席当天接听的所有电话
			this.lsCallsWatingBeanes=this.ac002Service.loadAllCallsByUseId();
			//所有未处理单据
			this.lsundisposedcall=this.ac002Service.undisposedcall();
		}
		return "load_success";
	}	
	
	@Action(value = "selectbyphone",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectbyphone() throws Exception{
		this.lsphone=this.ac002Service.selectbyphone(this.call_no);
		return "load_success";
	}	
	

	
	@Action(value = "loadbycar",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadbycar() 
	{
		this.callsWatingBean=this.ac002Service.loadcallbycall(memberno);
//		this.lsCallsWatingBean=this.ac002Service.loadcallswating(callbillid);
		return "load_success";
	}	
	
	/**
	 * 挂失
	 * @return
	 * @throws Exception
	 */
	@Action(value = "cardon",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String cardon() throws Exception
	{

		this.strMessage="";
		boolean flag=this.ac002Service.cardon(this.membernotocard);
		if(flag==false)
			this.strMessage="挂失失败";
		else
			this.strMessage="该单据已挂失！";
		return "load_success";
	}	
	/**
	 * 解挂
	 * @return
	 * @throws Exception
	 */
	@Action(value = "cardons",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String cardons() throws Exception
	{

		this.strMessage="";
		boolean flag=this.ac002Service.cardons(this.membernotocard);
		if(flag==false)
			this.strMessage="解挂失败";
		else
			this.strMessage="该单据已解挂！";
		return "load_success";
	}	
	 
	/**
	 * 挂失查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectcar",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectcar() throws Exception
	{
		this.lsCallsWatingBean=this.ac002Service.selectbycard(cardstate, membername, membermphone, cardtype, cardno);
		return "load_success";
	}
	
	
	/**
	 * 根据时间查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectBytim",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectBytim() throws Exception
	{

		this.lsundisposedcall=this.ac002Service.undisposedcalls(timms);
		return "load_success";
	}
	
	
	
	/**
	 * 根据字段查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectByparams",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectByparams() throws Exception
	{

		this.lsCallsWatingBean=this.ac002Service.selectByparam(memname, userid, billstate, times);
		return "load_success";
	}
	
	/**
	 * 查询预约
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectorder",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectorder() throws Exception
	{

		this.lsorders=this.ac002Service.selectOrder(callbillid);
		return "load_success";
	}	
	
	/**
	 * 添加投诉信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addpeiier",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addpeiier() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addpeiier(callbillid, peiiertime, peiiercontent, peiierdetails, peiierstate);
		if(flag==false)
			this.strMessage="添加投诉失败";
		else
			this.strMessage="添加投诉成功";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}	
	/**
	 * 添加投诉信息(结案)
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addpeiiers",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addpeiiers() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addpeiiers(callbillid, peiiertime, peiiercontent, peiierdetails, peiierstate);
		if(flag==false)
			this.strMessage="添加投诉结案失败";
		else
			this.strMessage="添加投诉结案成功";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}	
	/**
	 * 添加退卡信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addcardreturn",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addcardreturn() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addcardreturn(callbillid, cardreturningcontent, cardreturningdetails);
		if(flag==false)
			this.strMessage="添加退卡失败！";
		else
			this.strMessage="添加退卡成功！";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}
	/**
	 * 添加退卡信息（结案）
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addcardreturns",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addcardreturns() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addcardreturns(callbillid, cardreturningcontent, cardreturningdetails);
		if(flag==false)
			this.strMessage="添加退卡结案失败！";
		else
			this.strMessage="添加退卡结案成功！";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}	
	/**
	 * 添加预约信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addorders",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addorders() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addorders(callbillid, orderconply,orderphone, orderusermf,orderusertrh,orderusermr ,orderproject, ordertime, ordertimes, orderdetail, complydetail);
		if(flag==false)
			this.strMessage="添加预约失败";
		else
			this.strMessage="添加预约成功";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
		
	}	

	/**
	 * 确认单据
	 * @return
	 * @throws Exception
	 */
	@Action(value = "uporders",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String uporders() throws Exception
	{
		this.ac002Service.updatorderss(ordertimess, complydetails);
		return "load_success";
	}	
	
	/**
	 * 确认短息结案单据
	 * @return
	 * @throws Exception
	 */
	@Action(value = "submitMessage",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String submitMessage() throws Exception
	{
		this.ac002Service.submitMessage(callbillid);
		//所有未处理的短息单据
		this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("open");
		return "load_success";
	}
	
	/**
	 * 显示所有短信
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showAllSMS",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String showAllSMS() throws Exception
	{
		this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("all");
		return "load_success";
	}
	/**
	 * 显示已结案短信
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showClosedSMS",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String showClosedSMS() throws Exception
	{
		//所有未处理的短息单据
		this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("close");
		return "load_success";
	}
	/**
	 * 显示未结案短信
	 * @return
	 * @throws Exception
	 */
	@Action(value = "showOpenSMS",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String showOpenSMS() throws Exception
	{
		//所有未处理的短息单据
		this.lsmessageundisposedcall=this.ac002Service.messageundisposedcall("open");
		return "load_success";
	}
	
	
	//获得员工信息
	@Action(value = "loadCurStaffinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadCurStaffinfo() 
	{
		this.curStaffInfos=this.ac002Service.getDataTool().loadEmpsByCompId(this.orderconply, 1);
		return "load_success";
	}	
	
	@Action(value = "editCurMarkInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String editCurMarkInfo() 
	{
		this.strMessage="";
		boolean flag=this.ac002Service.handCallMark(this.callbillid, this.callmark);
		if(flag==false)
		{
			this.strMessage="追加失败!";
		}
		else
		{
			this.strMessage="追加成功!";
		}
		return "load_success";
	}	
	@Action(value = "loadCallBills",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadCallBills() 
	{
		//未处理信息
		this.callsWatingBean=this.ac002Service.loadcallswatingbytimes(callbillid);
		this.lsCallsWatingBean=this.ac002Service.loadcallswating(callbillid);
		return "load_success";
	}
	@Action(value = "loadMessages",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadMessages() 
	{
		//未处理的短信信息
		//this.callsWatingBean=this.ac002Service.loadMessages(callbillid);
		this.callsWatingBean=this.ac002Service.loadcallswatingbytimes(callbillid);
		this.lsCallsWatingBean=this.ac002Service.loadMessageCall(callbillid);
		return "load_success";
	}	
	
	@Action(value = "loadCallBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadCallBill() 
	{
		//来电会员信息
		//this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.callsWatingBean=this.ac002Service.loadcallswatingbytime(callbillid);
		this.lsCallsWatingBean=this.ac002Service.loadcallswating(callbillid);
		return "load_success";
	}	
	

	 @Action( value="load", results={ @Result( name="load_success", location="/AdvancedOperations/AC002/alertCall.jsp"),
			 @Result( name="load_failure", location="/AdvancedOperations/AC002/alertCall.jsp")})
			public String load() {
				return SystemFinal.LOAD_FAILURE;
			}
	 
	
	 
	/**
	 * 查询来电
	 * @return
	 */
	@Action( value="selectcallswating", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectcallswating() {
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	/**
	 * 添加咨询信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addrefer",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addrefer() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addrefer(callbillid,refercomply, refercards, referproject, referdetails);
		if(flag==false)
			this.strMessage="添加咨询失败!";
		else
			this.strMessage="添加咨询成功!";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}	
	/**
	 * 添加咨询信息(结案)
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addrefers",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addrefers() throws Exception
	{
		this.strMessage="";
		boolean flag=this.ac002Service.addrefers(callbillid,refercomply, refercards, referproject, referdetails);
		if(flag==false)
			this.strMessage="添加咨询结案失败!";
		else
			this.strMessage="添加咨询结案成功!";
		this.callbillid=this.ac002Service.getDataTool().loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
		this.ac002Service.updatabillid(callbillid);
		return "load_success";
	}	

	/**
	 * 查询咨询信息
	 * @return
	 */
	@Action( value="selectrefer", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectrefer() {
		this.lsrefer=this.ac002Service.selectRefer(callbillid);
		return SystemFinal.LOAD_SUCCESS;
	}
	/**
	 * 查询退卡信息
	 * @return
	 */
	@Action( value="selectcard", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectcard() {
		this.lscardreturn=this.ac002Service.selectcards(callbillid);
		return SystemFinal.LOAD_SUCCESS;
	}
	/**
	 * 查询咨询信息
	 * @return
	 */
	@Action( value="selectpeiier", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectpeiier() {
		this.lstpeiier=this.ac002Service.selectpeiier(callbillid);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}
	public List<CallsWatingBean> getLscardreturn() {
		return lscardreturn;
	}
	public void setLscardreturn(List<CallsWatingBean> lscardreturn) {
		this.lscardreturn = lscardreturn;
	}
	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}
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

	public List<CallsWatingBean> getLsCallsWatingBean() {
		return lsCallsWatingBean;
	}

	public void setLsCallsWatingBean(List<CallsWatingBean> lsCallsWatingBean) {
		this.lsCallsWatingBean = lsCallsWatingBean;
	}

	
	public CallsWatingBean getCallsWatingBean() {
		return callsWatingBean;
	}

	public void setCallsWatingBean(CallsWatingBean callsWatingBean) {
		this.callsWatingBean = callsWatingBean;
	}

	public String getStrCheckFlag() {
		return strCheckFlag;
	}

	public List<CallsWatingBean> getLsCallsWatingBeans() {
		return lsCallsWatingBeans;
	}

	public void setLsCallsWatingBeans(List<CallsWatingBean> lsCallsWatingBeans) {
		this.lsCallsWatingBeans = lsCallsWatingBeans;
	}

	public void setStrCheckFlag(String strCheckFlag) {
		this.strCheckFlag = strCheckFlag;
	}
	
	public String getOrdertimess() {
		return ordertimess;
	}

	public void setOrdertimess(String ordertimess) {
		this.ordertimess = ordertimess;
	}

	public String getComplydetails() {
		return complydetails;
	}

	public void setComplydetails(String complydetails) {
		this.complydetails = complydetails;
	}

	public int getOrdersid() {
		return ordersid;
	}
	public void setOrdersid(int ordersid) {
		this.ordersid = ordersid;
	}
	public String getCallbillid() {
		return callbillid;
	}
	public void setCallbillid(String callbillid) {
		this.callbillid = callbillid;
	}
	public String getOrderconply() {
		return orderconply;
	}
	public void setOrderconply(String orderconply) {
		this.orderconply = orderconply;
	}
	public String getOrderuser() {
		return orderuser;
	}
	public void setOrderuser(String orderuser) {
		this.orderuser = orderuser;
	}
	public String getOrderproject() {
		return orderproject;
	}
	public void setOrderproject(String orderproject) {
		this.orderproject = orderproject;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getOrdertimes() {
		return ordertimes;
	}
	public void setOrdertimes(String ordertimes) {
		this.ordertimes = ordertimes;
	}
	public String getOrderdetail() {
		return orderdetail;
	}
	public void setOrderdetail(String orderdetail) {
		this.orderdetail = orderdetail;
	}
	public String getComplydetail() {
		return complydetail;
	}
	public void setComplydetail(String complydetail) {
		this.complydetail = complydetail;
	}

	public List<CallsWatingBean> getLsCallsWatingBeanes() {
		return lsCallsWatingBeanes;
	}

	public void setLsCallsWatingBeanes(List<CallsWatingBean> lsCallsWatingBeanes) {
		this.lsCallsWatingBeanes = lsCallsWatingBeanes;
	}

	public List<CallsWatingBean> getLslsCallsWating() {
		return lslsCallsWating;
	}

	public void setLslsCallsWating(List<CallsWatingBean> lslsCallsWating) {
		this.lslsCallsWating = lslsCallsWating;
	}

	public List<Companyinfo> getLsCompanyinfo() {
		return lsCompanyinfo;
	}

	public void setLsCompanyinfo(List<Companyinfo> lsCompanyinfo) {
		this.lsCompanyinfo = lsCompanyinfo;
	}

	public List<Staffinfo> getCurStaffInfos() {
		return curStaffInfos;
	}

	public void setCurStaffInfos(List<Staffinfo> curStaffInfos) {
		this.curStaffInfos = curStaffInfos;
	}

	public String getCallmark() {
		return callmark;
	}

	public void setCallmark(String callmark) {
		this.callmark = callmark;
	}

	public String getCalluserid() {
		return calluserid;
	}

	public void setCalluserid(String calluserid) {
		this.calluserid = calluserid;
	}

	public List<CallsWatingBean> getLslsCallsByUser() {
		return lslsCallsByUser;
	}

	public void setLslsCallsByUser(List<CallsWatingBean> lslsCallsByUser) {
		this.lslsCallsByUser = lslsCallsByUser;
	}


	public String getRefertime() {
		return refertime;
	}

	public void setRefertime(String refertime) {
		this.refertime = refertime;
	}

	public String getRefercomply() {
		return refercomply;
	}

	public void setRefercomply(String refercomply) {
		this.refercomply = refercomply;
	}

	public String getRefercards() {
		return refercards;
	}

	public void setRefercards(String refercards) {
		this.refercards = refercards;
	}

	public String getReferproject() {
		return referproject;
	}

	public void setReferproject(String referproject) {
		this.referproject = referproject;
	}

	public String getReferdetails() {
		return referdetails;
	}

	public void setReferdetails(String referdetails) {
		this.referdetails = referdetails;
	}
	@JSON(serialize=false)
	public AC002Service getAc002Service() {
		return ac002Service;
	}
	@JSON(serialize=false)
	public void setAc002Service(AC002Service ac002Service) {
		this.ac002Service = ac002Service;
	}

	public List<CallsWatingBean> getLsrefer() {
		return lsrefer;
	}

	public void setLsrefer(List<CallsWatingBean> lsrefer) {
		this.lsrefer = lsrefer;
	}

	public String getPeiiertime() {
		return peiiertime;
	}

	public void setPeiiertime(String peiiertime) {
		this.peiiertime = peiiertime;
	}

	public String getPeiiercontent() {
		return peiiercontent;
	}

	public void setPeiiercontent(String peiiercontent) {
		this.peiiercontent = peiiercontent;
	}

	public String getPeiierdetails() {
		return peiierdetails;
	}

	public void setPeiierdetails(String peiierdetails) {
		this.peiierdetails = peiierdetails;
	}

	public String getPeiierstate() {
		return peiierstate;
	}

	public void setPeiierstate(String peiierstate) {
		this.peiierstate = peiierstate;
	}

	public List<CallsWatingBean> getLstpeiier() {
		return lstpeiier;
	}

	public void setLstpeiier(List<CallsWatingBean> lstpeiier) {
		this.lstpeiier = lstpeiier;
	}
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CallsWatingBean getCallsWatingBeans() {
		return callsWatingBeans;
	}

	public void setCallsWatingBeans(CallsWatingBean callsWatingBeans) {
		this.callsWatingBeans = callsWatingBeans;
	}

	public CallsWatingBean getCallsWatingBeanes() {
		return callsWatingBeanes;
	}

	public void setCallsWatingBeanes(CallsWatingBean callsWatingBeanes) {
		this.callsWatingBeanes = callsWatingBeanes;
	}
	private String cardreturningtime;
	private String cardreturningcontent;
	private String cardreturningdetails;
	private String cardreturningstate;
	public String getCardreturningtime() {
		return cardreturningtime;
	}

	public void setCardreturningtime(String cardreturningtime) {
		this.cardreturningtime = cardreturningtime;
	}

	public String getCardreturningcontent() {
		return cardreturningcontent;
	}

	public void setCardreturningcontent(String cardreturningcontent) {
		this.cardreturningcontent = cardreturningcontent;
	}

	public String getCardreturningdetails() {
		return cardreturningdetails;
	}

	public void setCardreturningdetails(String cardreturningdetails) {
		this.cardreturningdetails = cardreturningdetails;
	}

	public String getCardreturningstate() {
		return cardreturningstate;
	}

	public void setCardreturningstate(String cardreturningstate) {
		this.cardreturningstate = cardreturningstate;
	}
	private String refertime ;
	private String refercomply ;
	private String refercards ;
	private String referproject;
	private String referdetails;
	private String callno;		//表示来电主叫号码
	private String calledno;	//表示来电被叫号码    即：客户拨打哪个号码进入呼叫中心系统的。
	private String agentnum;
	private String offertime;
	private int callstate;  //呼叫等待状态
	private String strCheckFlag="Y";// 表示没有处理 N表示处理来电信息，并且将信息改为1
	private int ordersid;
	private String calluserid;
	private String callbillid;
	private String orderconply;
	private String orderuser;
	private String orderusermr;
	private String orderusertrh;
	private String orderusermf;
	private String orderproject;
	private String ordertime;
	private String ordertimes;
	private String orderdetail;
	private String  complydetail;
	private String ordertimess;
	private String complydetails;
	private String callmark;
	private String strMessage;
	private String peiiertime;
	private String peiiercontent;
	private String peiierdetails;
	private String peiierstate;
	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	public String getOrderusermr() {
		return orderusermr;
	}

	public void setOrderusermr(String orderusermr) {
		this.orderusermr = orderusermr;
	}

	public String getOrderusertrh() {
		return orderusertrh;
	}

	public void setOrderusertrh(String orderusertrh) {
		this.orderusertrh = orderusertrh;
	}

	public String getOrderusermf() {
		return orderusermf;
	}

	public List<CallsWatingBean> getLsorders() {
		return lsorders;
	}
	public void setLsorders(List<CallsWatingBean> lsorders) {
		this.lsorders = lsorders;
	}
	public void setOrderusermf(String orderusermf) {
		this.orderusermf = orderusermf;
	}
	private int closId;

	public int getClosId() {
		return closId;
	}
	private int cardreturningid;


	public int getCardreturningid() {
		return cardreturningid;
	}



	public void setCardreturningid(int cardreturningid) {
		this.cardreturningid = cardreturningid;
	}



	public List<CallsWatingBean> getLsundisposedcall() {
		return lsundisposedcall;
	}



	public void setLsundisposedcall(List<CallsWatingBean> lsundisposedcall) {
		this.lsundisposedcall = lsundisposedcall;
	}

	private String referelse;
	private String cardelse;
	private String peiierelse;
	

	public String getReferelse() {
		return referelse;
	}



	public void setReferelse(String referelse) {
		this.referelse = referelse;
	}



	public String getCardelse() {
		return cardelse;
	}



	public void setCardelse(String cardelse) {
		this.cardelse = cardelse;
	}



	public String getPeiierelse() {
		return peiierelse;
	}



	public void setPeiierelse(String peiierelse) {
		this.peiierelse = peiierelse;
	}

	private String memname;
	private String userid;
	private String billstate;
	private String times;

	public String getMemname() {
		return memname;
	}



	public void setMemname(String memname) {
		this.memname = memname;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getBillstate() {
		return billstate;
	}



	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}



	public String getTimes() {
		return times;
	}



	public void setTimes(String times) {
		this.times = times;
	}



	public void setClosId(int closId) {
		this.closId = closId;
	}
	private String timms;

	public String getTimms() {
		return timms;
	}

	public void setTimms(String timms) {
		this.timms = timms;
	}
	private int cardstate;
	private String membername;
	private String membermphone;
	private String cardtype;
	private String cardno;

	public int getCardstate() {
		return cardstate;
	}

	public void setCardstate(int cardstate) {
		this.cardstate = cardstate;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMembermphone() {
		return membermphone;
	}

	public void setMembermphone(String membermphone) {
		this.membermphone = membermphone;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	private int referid;
	private int peiierid;
	private String  membernotocard;
	public int getReferid() {
		return referid;
	}
	public void setReferid(int referid) {
		this.referid = referid;
	}
	public int getPeiierid() {
		return peiierid;
	}
	public void setPeiierid(int peiierid) {
		this.peiierid = peiierid;
	}
	public String getMembernotocard() {
		return membernotocard;
	}
	public void setMembernotocard(String membernotocard) {
		this.membernotocard = membernotocard;
	}
	public List<CallsWatingBean> getLsphone() {
		return lsphone;
	}
	public void setLsphone(List<CallsWatingBean> lsphone) {
		this.lsphone = lsphone;
	}
	private String call_no;
	public String getCallon() {
		return callon;
	}
	public void setCallon(String callon) {
		this.callon = callon;
	}
	public String getCall_no() {
		return call_no;
	}
	public void setCall_no(String callNo) {
		call_no = callNo;
	}
	private String memberno;
	public String getCallbillno() {
		return callbillno;
	}

	public void setCallbillno(String callbillno) {
		this.callbillno = callbillno;
	}

	public String getMemberno() {
		return memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}
	
	private String time;

	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	private String phone;

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String orderphone;

	public String getOrderphone() {
		return orderphone;
	}

	public void setOrderphone(String orderphone) {
		this.orderphone = orderphone;
	}
	private String timees;
	private String phones;

	public String getTimees() {
		return timees;
	}
	public void setTimees(String timees) {
		this.timees = timees;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	private String data;

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	/**
	 *  @BareFieldName : lsmessageundisposedcall
	 *
	 *  @return  the lsmessageundisposedcall
	 *
	 *
	 **/
	
	public List<CallsWatingBean> getLsmessageundisposedcall() {
		return lsmessageundisposedcall;
	}
	/**
	 *  @BareFieldName : lsmessageundisposedcall
	 *
	 *  @return  the lsmessageundisposedcall
	 *
	 *  @param lsmessageundisposedcall the lsmessageundisposedcall to set
	 *
	 **/
	
	public void setLsmessageundisposedcall(
			List<CallsWatingBean> lsmessageundisposedcall) {
		this.lsmessageundisposedcall = lsmessageundisposedcall;
	}
	
}
