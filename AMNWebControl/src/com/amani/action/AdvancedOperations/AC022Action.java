package com.amani.action.AdvancedOperations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Dnointernalcardinfo;
import com.amani.model.Projectinfo;
import com.amani.service.AdvancedOperations.AC022Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@SuppressWarnings("serial")
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac022")
public class AC022Action extends AMN_ModuleAction{
	
	@Autowired
	private AC022Service ac022Service;
	private String strCurCompId;
	private List<Projectinfo> lsProjectinfo;
	private String strJsonParam;
	private List<Dnointernalcardinfo> lsDnointernalcardinfo;
	private String cardId;
	private String cardAmt;
	private String type;
	
	@Action(value = "loadInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadInfo()
	{
		try{
			this.lsProjectinfo=this.ac022Service.loadProjectinfo();
			List<Projectinfo> temp = new ArrayList<Projectinfo>();
			for (Projectinfo project : lsProjectinfo) {//过滤不是疗程的项目
				if(CommonTool.FormatInteger(project.getPrjsaletype())==1 && CommonTool.FormatInteger(project.getUseflag())==1){
					temp.add(project);
				}
			}
			lsProjectinfo = temp;
			
			/*this.lsProjectinfo=this.ac022Service.getDataTool().loadProjectinfoByCompId(strCurCompId,1);
			List<Projectinfo> temp = new ArrayList<Projectinfo>();
			for (Projectinfo project : lsProjectinfo) {//过滤不是疗程的项目
				if(CommonTool.FormatInteger(project.getPrjsaletype())==2){
					temp.add(project);
				}
			}
			lsProjectinfo = temp;*/
		}
		catch(Exception e)
		{
//			logger.error("AC022Action.java loadInfo  error:" + e);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "addWxCard",  results = { 
			@Result(name = "post_success", type = "json"),
			@Result(name = "post_failure", type = "json")	   })
	public String addWxCard()
	{
		try{
			StringBuffer prono = new StringBuffer();
			if(StringUtils.isNotBlank(strJsonParam)){
				this.lsDnointernalcardinfo=this.ac022Service.getDataTool().loadDTOList(strJsonParam, Dnointernalcardinfo.class);
				for(Dnointernalcardinfo o : lsDnointernalcardinfo){
					if(StringUtils.isNotBlank(prono.toString())){
						prono.append(",'"+o.getIneritemno()+"'");
					}else{
						prono.append("'"+o.getIneritemno()+"'");
					}
				}
			}
			boolean checkFlag=this.ac022Service.validateCard(cardId,type,prono);
			if(checkFlag==false)
			{
				this.strMessage="录入微信券号段已经在系统中存在,请确认!";
				return SystemFinal.POST_FAILURE;
			}else{
				if(this.ac022Service.addCard(cardId,type,prono,cardAmt)){
					return SystemFinal.POST_SUCCESS;
				}else{
					return SystemFinal.POST_FAILURE;
				}
			}
		}
		catch(Exception e)
		{
			return SystemFinal.POST_FAILURE;
		}
		
	}
	
	@Override
	protected boolean addActive() {
		return false;
	}

	@Override
	protected boolean afterAdd() {
		return false;
	}

	@Override
	protected boolean afterDelete() {
		return false;
	}

	@Override
	protected boolean afterLoad() {
		return false;
	}

	@Override
	protected boolean afterPost() {
		
		return true;
	}

	@Override
	protected boolean beforeAdd() {
		return false;
	}

	@Override
	protected boolean beforeDelete() {
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		return true;
	}

	@Override
	protected boolean beforePost() {
		
		return true;
	}

	@Override
	protected boolean deleteActive() {
		return true;
	}
	
	@Override
	protected boolean loadActive() {
		return true;
	}
    
	@Override
	protected boolean postActive() {
	
		return true;
	}
	
	@JSON(serialize=false)
	public AC022Service getac022Service() {
		return ac022Service;
	}
	@JSON(serialize=false)
	public void setac022Service(AC022Service ac021Service) {
		this.ac022Service = ac021Service;
	}

	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}

	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}

	public String getStrCurCompId() {
		return strCurCompId;
	}

	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	public AC022Service getAc022Service() {
		return ac022Service;
	}

	public void setAc022Service(AC022Service ac022Service) {
		this.ac022Service = ac022Service;
	}

	public String getStrJsonParam() {
		return strJsonParam;
	}

	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	public List<Dnointernalcardinfo> getLsDnointernalcardinfo() {
		return lsDnointernalcardinfo;
	}

	public void setLsDnointernalcardinfo(
			List<Dnointernalcardinfo> lsDnointernalcardinfo) {
		this.lsDnointernalcardinfo = lsDnointernalcardinfo;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardAmt() {
		return cardAmt;
	}

	public void setCardAmt(String cardAmt) {
		this.cardAmt = cardAmt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}

	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
}
