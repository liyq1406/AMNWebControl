package com.amani.action.AdvancedOperations;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.DefaultBean;
import com.amani.bean.GbaBean;
import com.amani.bean.MissionBean;
import com.amani.bean.RevokBean;
import com.amani.bean.SelectMsgBean;
import com.amani.bean.SendMessages;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Projectinfo;
import com.amani.service.AdvancedOperations.AC001Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac001")
public class AC001Action extends AMN_ModuleAction{
	@Autowired
	private  AC001Service ac001Service;
	private List<GbaBean> lsCardInfos;
	private MissionBean missionBean;
	private OutputStream os;
	private SendMessages messages;
	private SelectMsgBean selectMsgBean;
	private GbaBean gbaBean;
	private List<MissionBean> lsMissionBean;
	private List<Cardtypeinfo> lsCardtypeInfo; 
	private List<DefaultBean> lsDefaultBean;
	private List<Projectinfo> lsProjectinfo;
	private List<RevokBean> lsRevokBean;
	private String callno;		//表示来电主叫号码
	private String calledno;	//表示来电被叫号码    即：客户拨打哪个号码进入呼叫中心系统的。
	private String agentnum;
	private String offertime;
	private int callstate;  //呼叫等待状态
	
	/*
	 * 查询任务
	 */
	@Action( value="selectmission", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectmission() {
		this.lsMissionBean=this.ac001Service.selectMission();
		System.out.println(this.lsMissionBean);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//根据Id查询任务
	@Action( value="selectmissionid", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectmissionid() {
		this.missionBean=this.ac001Service.loadmissionByid(this.id);
		this.lsMissionBean=this.ac001Service.selectMissionid(this.id);
		System.out.println(this.missionBean);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//停用
	@Action( value="updatemissionstates", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String updatemissionstates() throws DataAccessException, RuntimeException, Exception {
		this.ac001Service.updatemissionstate(this.id);
		this.ac001Service.selectMission();
		return SystemFinal.LOAD_SUCCESS;
	}
	/*
	 * 添加任务
	 */
	@Action( value="addmission", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String addmission() {
		this.sendbillid=this.ac001Service.getDataTool().
		loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"missioninfo", "missionbillid","SP012");
		ac001Service.addmission(sendbillid, missionname,missionkey, missiondetails, templatestate, missionnames, missionphone);
		return SystemFinal.LOAD_SUCCESS;
	}
	/**
	 * 单个定时发送
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action( value="sendtimelyMessages", results={ @Result( type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String sendtimelyMessages() throws Exception{
	    this.lsMissionBean=this.ac001Service.selectMission();	
		this.strMessage=this.ac001Service.sendTimelyMsg(missionkey, missionphone, missiondetails);
//		try
//		{
//	    this.lsMissionBean=this.ac001Service.selectMission();	
//		String strSendPhoneInfos="";
//		String strmessgae="";
//		String strsendtime="";
//		lsMissionBean=this.ac001Service.getDataTool().loadDTOList(memeberPhone, MissionBean.class);
//		List lsSendMsg=new ArrayList();
//		if(lsMissionBean!=null && lsMissionBean.size()>0)
//		{
//			int ccount=0;
//			for(int i=0;i<lsMissionBean.size();i++)
//			{
//				if(!CommonTool.FormatString(lsMissionBean.get(i).getMissionphone()).equals(""))
//				{
//					ccount=ccount+1;
//					if(ccount==500)
//					{
//						ccount=0;
//						lsSendMsg.add(strSendPhoneInfos);
//						strSendPhoneInfos="";
//					}
//					if(!strSendPhoneInfos.equals(""))
//						strSendPhoneInfos=strSendPhoneInfos+";";
//					strsendtime=CommonTool.FormatString(lsMissionBean.get(i).getMissionkey());
//					strSendPhoneInfos=strSendPhoneInfos+CommonTool.FormatString(lsMissionBean.get(i).getMissionphone());
//					strmessgae=CommonTool.FormatString(lsMissionBean.get(i).getMissiondetails());
//				}
//			}
//		}
//		if(!strsendtime.equals(""))
//			lsSendMsg.add(strsendtime);
//		if(!strSendPhoneInfos.equals(""))
//			lsSendMsg.add(strSendPhoneInfos);
//		if(!strmessgae.equals(""))
//			lsSendMsg.add(strmessgae);
//		if(lsSendMsg!=null && lsSendMsg.size()>0)
//		{
//			for(int i=0;i<lsSendMsg.size();i++)
//			{
//				this.strMessage=this.ac001Service.sendTimelyMsg(strsendtime,lsSendMsg.get(1).toString(),lsSendMsg.get(2).toString());
//				System.out.println(this.strMessage);
//			}
//		}
//		}catch(Exception ex){ex.printStackTrace();}
		 return SystemFinal.LOAD_SUCCESS;
	}
	
	/*
	 * 查询回复
	 */
	@Action( value="selecrevoke", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selecrevoke() throws Exception{
		 this.ac001Service.addrevock();
		 this.lsRevokBean=this.ac001Service.selectrevokes();
		 return SystemFinal.LOAD_SUCCESS;
	 }
	/*
	 * 根据查询回复
	 */
	@Action( value="selecrevokes", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selecrevokes() throws Exception{
		 this.lsRevokBean=this.ac001Service.selectrevoke(time, times);
		 return SystemFinal.LOAD_SUCCESS;
	 }
	
	@Action(value = "initpage",  results = { 
			 @Result(name = "load_success", type = "json"),
         @Result(name = "load_failure", type = "json")	
      }) 
    public String initpage()
	{
		try
		{
			this.lsCardtypeInfo=this.ac001Service.getDataTool().loadCardType(CommonTool.getLoginInfo("COMPID"));
			if(lsCardtypeInfo!=null && lsCardtypeInfo.size()>0)
			{
				for(int i=0;i<lsCardtypeInfo.size();i++)
				{
					lsCardtypeInfo.get(i).setBcardtypeno(lsCardtypeInfo.get(i).getId().getCardtypeno());
				}
			}
			lsProjectinfo=this.ac001Service.getDataTool().loadProinfoByCompId(CommonTool.getLoginInfo("COMPID"));
			if(lsProjectinfo!=null && lsProjectinfo.size()>0)
			{
				for(int i=0;i<lsProjectinfo.size();i++)
				{
					lsProjectinfo.get(i).setBprjno(lsProjectinfo.get(i).getId().getPrjno());
				}
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	public List<Cardtypeinfo> getLsCardtypeInfo() {
		return lsCardtypeInfo;
	}
	public void setLsCardtypeInfo(List<Cardtypeinfo> lsCardtypeInfo) {
		this.lsCardtypeInfo = lsCardtypeInfo;
	}
	
	 @Action( value="load", results={ @Result( name="load_success", location="/AdvancedOperations/AC002/index.jsp"),
	 @Result( name="load_failure", location="/AdvancedOperations/AC002/index.jsp")})
	public String load() {
		 
		return SystemFinal.LOAD_FAILURE;
	}
	
	 /*
	  * 根据店号、卡类型、卡余额、疗程、生日、活跃期查询信息
	  */
	@Action( value="btuselectBean", results={ @Result( type="json", name="load_success"),
		 @Result( type="json", name="load_failure")})
	public String btuselectBean() {
		this.lsCardInfos= this.ac001Service.loadmessage(this.compId,this.cardtype,this.cardmonney,this.cardmonneys,
				this.birthday,this.birthdays,this.dataactivity,this.cardtreatment);
		return SystemFinal.LOAD_SUCCESS;
	}

//	//任务停用
//	@Action( value="missionstop", results={ @Result( type="json", name="load_success"),
//			 @Result( type="json", name="load_failure")})
//	public String missionstop() throws DataAccessException, RuntimeException, Exception {
//		this.ac001Service.updatemissionstates();
//		return SystemFinal.LOAD_SUCCESS;
//	}
//	
	
	/*
	 * 查询默认发送短信
	 */
	@Action( value="selectDefault", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String selectDefault() {
		this.lsDefaultBean=this.ac001Service.selectDefault();
		System.out.println(this.lsDefaultBean);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	/*
	 * 添加默认发送短信
	 */
	@Action( value="addDefault", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String addDefault() {
		ac001Service.addDefaultMessage(name, phone);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	/*
	 * 发送短信息
	 */
	@Action( value="sendMessage", results={ @Result( type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String sendMessage() throws Exception{
		try
		{
		 int len=Math.round(ac001Service.countWords(msgText));
		 System.out.println("发送短信的字数为："+len);
		this.strMessage="";
		String strSendPhoneInfos="";
	
		if(msgText.equals(""))
		{
			this.strMessage="请确认发送的短信内容！";
			 return SystemFinal.LOAD_SUCCESS;
		}
		lsCardInfos=this.ac001Service.getDataTool().loadDTOList(memeberPhone, GbaBean.class);
		lsDefaultBean=this.ac001Service.getDataTool().loadDTOList(phone, DefaultBean.class);
		List lsSendMsg=new ArrayList();
		if(lsCardInfos!=null && lsCardInfos.size()>0)
		{
			int ccount=0;
			for(int i=0;i<lsCardInfos.size();i++)
			{
				if(!CommonTool.FormatString(lsCardInfos.get(i).getPhone()).equals(""))
				{
					ccount=ccount+1;
					if(ccount==500)
					{
						ccount=0;
						lsSendMsg.add(strSendPhoneInfos);
						strSendPhoneInfos="";
					}
					if(!strSendPhoneInfos.equals(""))
						strSendPhoneInfos=strSendPhoneInfos+";";
					strSendPhoneInfos=strSendPhoneInfos+CommonTool.FormatString(lsCardInfos.get(i).getPhone());
				}
			}
		}
		if(!strSendPhoneInfos.equals(""))
			lsSendMsg.add(strSendPhoneInfos);
		strSendPhoneInfos="";
		if(lsDefaultBean!=null&&lsDefaultBean.size()>0)
		{
			for (int i = 0; i < lsDefaultBean.size(); i++) {
				if(!CommonTool.FormatString(lsDefaultBean.get(i).getPhone()).equals(""))
				{
					if(!strSendPhoneInfos.equals(""))
						strSendPhoneInfos=strSendPhoneInfos+";";
					strSendPhoneInfos=strSendPhoneInfos+CommonTool.FormatString(lsDefaultBean.get(i).getPhone());
				}
			}
		}
		if(!strSendPhoneInfos.equals(""))
			lsSendMsg.add(strSendPhoneInfos);
		if(lsSendMsg!=null && lsSendMsg.size()>0)
		{
			for(int i=0;i<lsSendMsg.size();i++)
			{
				this.strMessage=this.ac001Service.sendMsg(lsSendMsg.get(i).toString(), msgText);
				System.out.println(i+"轮");
			}
		}
		this.sendbillid=this.ac001Service.getDataTool().
		loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"smgInfo", "sendbillid","SP012");
		ac001Service.addsendmessagelog(userId,sendbillid,sendstate,msgText);
		 ac001Service.addsemdmessagedetial(compId,sendbillid,memeberName, strSendPhoneInfos, cardno,msgText);
		//ac001Service.addDefaultMessage(name, phone);
		
		}catch(Exception ex){ex.printStackTrace();}
		 return SystemFinal.LOAD_SUCCESS;
	}

	
	
	
	/*
	 * 来电等待
	 */
	@Action( value="addcallwating", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
			 public String addcallwating(){
		 addcallswating(callno, calledno, agentnum, offertime,callstate);
		 return SystemFinal.LOAD_SUCCESS;
		}
 
	/*
	 * 添加呼叫等待信息
	 */
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
			bte=this.ac001Service.getAmn_Dao().executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	public List<MissionBean> getLsMissionBean() {
		return lsMissionBean;
	}
	public void setLsMissionBean(List<MissionBean> lsMissionBean) {
		this.lsMissionBean = lsMissionBean;
	}
	public MissionBean getMissionBean() {
		return missionBean;
	}
	public void setMissionBean(MissionBean missionBean) {
		this.missionBean = missionBean;
	}
	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterLoad() {
		return false;
	}
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}
	private String strBillId;
	private String missionname;
	private String missiontype;
	private String missionkey;
	private String missiondetails;
	private String templatestate;
	private String missionnames;
	private String missionphone;
	private String userId;
	private String compId;
	private int sendstate;
	private String sendbillid;
	private String cardtype;
	private String cardno;
	private float cardmonney;
	private float cardmonneys;
	private String cardtreatment;
	private String birthday;
	private String  dataactivity;
	private String msgText;
	private String memeberName;
	private String memeberPhone;
	private String strJsonParam;
	private String time;
	private String times;
	private String missionid;
	
	
	
	public String getMissionid() {
		return missionid;
	}
	public void setMissionid(String missionid) {
		this.missionid = missionid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public float getCardmonney() {
		return cardmonney;
	}
	public void setCardmonney(float cardmonney) {
		this.cardmonney = cardmonney;
	}
	public float getCardmonneys() {
		return cardmonneys;
	}
	public void setCardmonneys(float cardmonneys) {
		this.cardmonneys = cardmonneys;
	}
	public String getCardtreatment() {
		return cardtreatment;
	}
	public void setCardtreatment(String cardtreatment) {
		this.cardtreatment = cardtreatment;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getDataactivity() {
		return dataactivity;
	}
	public void setDataactivity(String dataactivity) {
		this.dataactivity = dataactivity;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	public int getSendstate() {
		return sendstate;
	}
	public void setSendstate(int sendstate) {
		this.sendstate = sendstate;
	}
	public String getSendbillid() {
		return sendbillid;
	}
	public void setSendbillid(String sendbillid) {
		this.sendbillid = sendbillid;
	}
	public GbaBean getGbaBean() {
		return gbaBean;
	}
	public void setGbaBean(GbaBean gbaBean) {
		this.gbaBean = gbaBean;
	}
	public SelectMsgBean getSelectMsgBean() {
		return selectMsgBean;
	}
	public void setSelectMsgBean(SelectMsgBean selectMsgBean) {
		this.selectMsgBean = selectMsgBean;
	}
	public SendMessages getMessages() {
		return messages;
	}
	public void setMessages(SendMessages messages) {
		this.messages = messages;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public List<GbaBean> getlsCardInfos() {
		return lsCardInfos;
	}
	public void setlsCardInfos(List<GbaBean> lsCardInfos) {
		this.lsCardInfos = lsCardInfos;
	}
	public String getMemeberName() {
		return memeberName;
	}
	public void setMemeberName(String memeberName) {
		this.memeberName = memeberName;
	}
	public String getMemeberPhone() {
		return memeberPhone;
	}
	public void setMemeberPhone(String memeberPhone) {
		this.memeberPhone = memeberPhone;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	private String birthdays;

	public String getBirthdays() {
		return birthdays;
	}
	public void setBirthdays(String birthdays) {
		this.birthdays = birthdays;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}
	private String name;
	private String phone;
	private String missioinmessges;
	
	public String getMissioinmessges() {
		return missioinmessges;
	}
	public void setMissioinmessges(String missioinmessges) {
		this.missioinmessges = missioinmessges;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStrBillId() {
		return strBillId;
	}
	public void setStrBillId(String strBillId) {
		this.strBillId = strBillId;
	}
	public String getMissionname() {
		return missionname;
	}
	public void setMissionname(String missionname) {
		this.missionname = missionname;
	}
	public String getMissiontype() {
		return missiontype;
	}
	public void setMissiontype(String missiontype) {
		this.missiontype = missiontype;
	}
	public String getMissionkey() {
		return missionkey;
	}
	public void setMissionkey(String missionkey) {
		this.missionkey = missionkey;
	}
	public String getMissiondetails() {
		return missiondetails;
	}
	public void setMissiondetails(String missiondetails) {
		this.missiondetails = missiondetails;
	}
	public String getTemplatestate() {
		return templatestate;
	}
	public void setTemplatestate(String templatestate) {
		this.templatestate = templatestate;
	}
	public String getMissionnames() {
		return missionnames;
	}
	public void setMissionnames(String missionnames) {
		this.missionnames = missionnames;
	}
	public String getMissionphone() {
		return missionphone;
	}
	public void setMissionphone(String missionphone) {
		this.missionphone = missionphone;
	}
	
	public List<RevokBean> getLsRevokBean() {
		return lsRevokBean;
	}
	public void setLsRevokBean(List<RevokBean> lsRevokBean) {
		this.lsRevokBean = lsRevokBean;
	}
	public List<DefaultBean> getLsDefaultBean() {
		return lsDefaultBean;
	}
	public void setLsDefaultBean(List<DefaultBean> lsDefaultBean) {
		this.lsDefaultBean = lsDefaultBean;
	}
	private String sendDateTime;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(String sendDateTime) {
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
	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}
	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
	@JSON(serialize=false)
	public AC001Service getAc001Service() {
		return ac001Service;
	}
	@JSON(serialize=false)
	public void setAc001Service(AC001Service ac001Service) {
		this.ac001Service = ac001Service;
	}
}
