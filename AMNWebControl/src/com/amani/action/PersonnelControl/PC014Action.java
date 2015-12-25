package com.amani.action.PersonnelControl;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.IntentionBean;
import com.amani.model.Intention;
import com.amani.model.Intentiondetail;
import com.amani.service.PersonnelControl.PC014Service;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc014")
public class PC014Action extends AMN_ModuleAction{
	@Autowired
	private PC014Service pc014Service;
	private Intention intention;
	private List<Intentiondetail> lsIntentiondetail;
	private List<Intention> lsIntentions;
	private List<IntentionBean> lsIntentionBean;
	private String strCompId;
	private String strEmpId;
	private int iProjectno;
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrEmpId() {
		return strEmpId;
	}
	public void setStrEmpId(String strEmpId) {
		this.strEmpId = strEmpId;
	}
	public int getIProjectno() {
		return iProjectno;
	}
	public void setIProjectno(int projectno) {
		iProjectno = projectno;
	}
	@Action(value = "validateInserper",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String validateInserper()
	{
		try
		{
			this.strMessage="";
			StringBuffer validateMsg=new StringBuffer();
			this.instaffname=this.pc014Service.getDataTool().loadEmpNameById(this.intcomplyno, this.strCurEmpId, validateMsg);
			this.incardno=this.pc014Service.loadEmpNameById(this.intcomplyno, this.strCurEmpId, validateMsg);
			this.intposition=this.pc014Service.loadEmpNameByIds(this.intcomplyno, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	/**
	 * 添加培训
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addintention",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addintention() throws Exception{
		try{
				this.intention=this.pc014Service.addintetionBean();
				return "load_success";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return "load_success";
			}
	}
	/**
	 * 添加培训基本信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addintentions",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String addintentions() throws Exception{
		try{
		 //this.pc014Service.post(this.intention, this.lsIntentiondetail);
		 this.lsIntentiondetail=this.pc014Service.getDataTool().loadDTOList(strJsonParam,Intentiondetail.class);
		 if(lsIntentiondetail!=null && lsIntentiondetail.size()>0)
			{
			 this.pc014Service.addintention(intcomplyno,intbillid,intdproject,intdstage,intdstarttime,intdendtime,intdata, lsIntentiondetail);
				
			}
			}catch(Exception ex){ex.printStackTrace();}
		 return "load_success";
	}
	/**
	 * 查询所有会员信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectintention",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectintention() throws Exception{
		this.lsIntentions=this.pc014Service.selectintents();
		if(lsIntentions==null || lsIntentions.size()==0)
		{
			lsIntentions=new ArrayList();
			lsIntentions.add(this.pc014Service.addintetionBean());
			intention=this.pc014Service.addintetionBean();
		}
		return "load_success";
	}
	/**
	 * 查询会员培训信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectintentions",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectintentions() throws Exception{
	
		this.intention=this.pc014Service.loadintention(intbillid);
		if(intention!=null && intention.getIntbillid()!=null)
		{
			this.lsIntentiondetail=this.pc014Service.selectintentdetails(intbillid);
		}
		else
		{
			intention=this.pc014Service.addintetionBean();
		}
		return "load_success";
	}
	
	
	@Action(value = "searchTntentionInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String searchTntentionInfo() throws Exception{
	
		this.lsIntentiondetail=this.pc014Service.loadsearchInfo(strCompId, strEmpId,iProjectno );
		return "load_success";
	}

	/**
	 * 根据条件
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selecbyparmar",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selecbyparmar() throws Exception{
		this.lsIntentionBean=this.pc014Service.selectbyparams(intcomplyno, intstuno, incardno, instaffno);
		return "load_success";
	}
	private int intid ;//培训编号	
	private String intcomplyno ;//公司编号
	private String	intbillid;//单号
	private String	intstuno;//学生手册号码
	private String incardno;//身份证号码
	private String instaffno;//员工编号
	private String instaffname;//员工姓名
	private String intposition;//职位
	private String intbirthday;//出生日期
	private String intuser;//登记人
	private String intdata;//登记日期
	private String intbillids;
	private int intpositions ;//建议可担当岗位（0：初级技师，1：高级技师，2，预备发型师，3：发型师，4：首席，5：总监，6：美发经理）
	/**
	 * 培训经历明细
	 */
	private int	intdid;//流水号	
	private String intdcomplyno;//公司编号
	private String intdbillid;//对应单号
	private String intdwaite;//预留
	private int intdproject;//岗位课程（0：初级技师，1：高级技师，2，预备发型师，3：发型师，4：首席，5：总监，6：美发经理，7：选修课）
	private int intdstage;//阶段（0：无，1：第一阶段,2:第二阶段，3：第三阶段，4：第四阶段，其它任意填）
	private String intdstarttime;//培训开始时间
	private String intdendtime;//培训结束时间
	private int intdscore;//成绩（0：不合格，1：合格）
	private String intdproname;//选修课名字
	private String intdpunish;//奖罚情况
	private String intdremark;//备注
	public int getIntid() {
		return intid;
	}
	
	public String getIntbillids() {
		return intbillids;
	}

	public void setIntbillids(String intbillids) {
		this.intbillids = intbillids;
	}

	public void setIntid(int intid) {
		this.intid = intid;
	}
	public String getIntcomplyno() {
		return intcomplyno;
	}
	public void setIntcomplyno(String intcomplyno) {
		this.intcomplyno = intcomplyno;
	}
	public String getIntbillid() {
		return intbillid;
	}
	public void setIntbillid(String intbillid) {
		this.intbillid = intbillid;
	}
	public String getIntstuno() {
		return intstuno;
	}
	public void setIntstuno(String intstuno) {
		this.intstuno = intstuno;
	}
	public String getIncardno() {
		return incardno;
	}
	public void setIncardno(String incardno) {
		this.incardno = incardno;
	}
	public String getInstaffno() {
		return instaffno;
	}
	public void setInstaffno(String instaffno) {
		this.instaffno = instaffno;
	}
	public String getInstaffname() {
		return instaffname;
	}
	public void setInstaffname(String instaffname) {
		this.instaffname = instaffname;
	}
	public String getIntposition() {
		return intposition;
	}
	public void setIntposition(String intposition) {
		this.intposition = intposition;
	}
	public String getIntbirthday() {
		return intbirthday;
	}
	public void setIntbirthday(String intbirthday) {
		this.intbirthday = intbirthday;
	}
	public String getIntuser() {
		return intuser;
	}
	public void setIntuser(String intuser) {
		this.intuser = intuser;
	}
	public String getIntdata() {
		return intdata;
	}
	public void setIntdata(String intdata) {
		this.intdata = intdata;
	}
	public int getIntpositions() {
		return intpositions;
	}
	public void setIntpositions(int intpositions) {
		this.intpositions = intpositions;
	}
	public int getIntdid() {
		return intdid;
	}
	public void setIntdid(int intdid) {
		this.intdid = intdid;
	}
	public String getIntdcomplyno() {
		return intdcomplyno;
	}
	public void setIntdcomplyno(String intdcomplyno) {
		this.intdcomplyno = intdcomplyno;
	}
	public String getIntdbillid() {
		return intdbillid;
	}
	public void setIntdbillid(String intdbillid) {
		this.intdbillid = intdbillid;
	}
	public String getIntdwaite() {
		return intdwaite;
	}
	public void setIntdwaite(String intdwaite) {
		this.intdwaite = intdwaite;
	}
	public int getIntdproject() {
		return intdproject;
	}
	public void setIntdproject(int intdproject) {
		this.intdproject = intdproject;
	}
	public int getIntdstage() {
		return intdstage;
	}
	public void setIntdstage(int intdstage) {
		this.intdstage = intdstage;
	}
	public String getIntdstarttime() {
		return intdstarttime;
	}
	public void setIntdstarttime(String intdstarttime) {
		this.intdstarttime = intdstarttime;
	}
	public String getIntdendtime() {
		return intdendtime;
	}
	public void setIntdendtime(String intdendtime) {
		this.intdendtime = intdendtime;
	}
	public int getIntdscore() {
		return intdscore;
	}
	public void setIntdscore(int intdscore) {
		this.intdscore = intdscore;
	}
	public String getIntdproname() {
		return intdproname;
	}
	public void setIntdproname(String intdproname) {
		this.intdproname = intdproname;
	}
	public String getIntdpunish() {
		return intdpunish;
	}
	public void setIntdpunish(String intdpunish) {
		this.intdpunish = intdpunish;
	}
	public String getIntdremark() {
		return intdremark;
	}
	public void setIntdremark(String intdremark) {
		this.intdremark = intdremark;
	}
	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
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
	@JSON(serialize=false)
	public PC014Service getPc014Service() {
		return pc014Service;
	}
	@JSON(serialize=false)
	public void setPc014Service(PC014Service pc014Service) {
		this.pc014Service = pc014Service;
	}

	public Intention getIntention() {
		return intention;
	}

	public void setIntention(Intention intention) {
		this.intention = intention;
	}
	public List<Intentiondetail> getLsIntentiondetail() {
		return lsIntentiondetail;
	}
	public void setLsIntentiondetail(List<Intentiondetail> lsIntentiondetail) {
		this.lsIntentiondetail = lsIntentiondetail;
	}
	private String strJsonParam;
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public List<Intention> getLsIntentions() {
		return lsIntentions;
	}
	public void setLsIntentions(List<Intention> lsIntentions) {
		this.lsIntentions = lsIntentions;
	}
	public List<IntentionBean> getLsIntentionBean() {
		return lsIntentionBean;
	}
	public void setLsIntentionBean(List<IntentionBean> lsIntentionBean) {
		this.lsIntentionBean = lsIntentionBean;
	}
	private String strCurEmpId;
	public String getStrCurEmpId() {
		return strCurEmpId;
	}
	public void setStrCurEmpId(String strCurEmpId) {
		this.strCurEmpId = strCurEmpId;
	}
	private String  strMessage;
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	
	
}
