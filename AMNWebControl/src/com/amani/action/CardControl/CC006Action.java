package com.amani.action.CardControl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;


import com.amani.model.Memberinfo;


import com.amani.service.CardControl.CC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc006")
public class CC006Action extends AMN_ModuleAction{
	@Autowired
	private CC006Service cc006Service;
	private String strJsonParam;	
	private String strCurCompId;
	private String strCurMemberId;
    private Memberinfo curMemberinfo;
    private List<Memberinfo> lsMemberinfos;

    private String searchMemberCompIdKey;
    private String searchMemberNoKey;
    private String searchMemberNameKey;
    private String searchMemberPhoneKey;
    private String searchMemberPCIDKey;
	@JSON(serialize=false)
    public CC006Service getCc006Service() {
		return cc006Service;
	}
	@JSON(serialize=false)
	public void setCc006Service(CC006Service cc006Service) {
		this.cc006Service = cc006Service;
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
		return true;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
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
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	
	@Override
	public String getStrMessage() {
		// TODO Auto-generated method stub
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		// TODO Auto-generated method stub
		super.setStrMessage(strMessage);
	}
	@Action(value = "loadMemberinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
     @Result(name = "load_failure", type = "json")	
	}) 
	public String loadMemberinfo()
	{
		
		this.lsMemberinfos=this.cc006Service.loadMemberinfo(this.strCurCompId);
		if(lsMemberinfos!=null && lsMemberinfos.size()>0)
		{
			for( int i=0;i<lsMemberinfos.size();i++)
			{
				lsMemberinfos.get(i).setBmemberno(lsMemberinfos.get(i).getId().getMemberno());
				lsMemberinfos.get(i).setBmembervesting(lsMemberinfos.get(i).getId().getMembervesting());
				lsMemberinfos.get(i).setMemberbirthday(CommonTool.getDateMask(lsMemberinfos.get(i).getMemberbirthday()));
				if(this.cc006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC006", SystemFinal.UR_SPECIAL_CHECK)==false)
				{
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembername()).equals(""))
					{
						lsMemberinfos.get(i).setMembername(CommonTool.FormatString(lsMemberinfos.get(i).getMembername().substring(0, 1))+"**");
					}
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).equals("")
					&& CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).length()==11)
					{
						lsMemberinfos.get(i).setMembermphone(CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(0, 3))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(7, 11)));
					}
					else if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).equals("")
					  && CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).length()==8)
					{
								lsMemberinfos.get(i).setMembermphone(CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(0, 2))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(6, 8)));
					}
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).equals("")
						&& CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).length()==18)
					{
								lsMemberinfos.get(i).setMemberpaperworkno(CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(0, 4))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(16, 18)));
					}
					else if(!CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).equals("")
							&& CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).length()==15)
					{
									lsMemberinfos.get(i).setMemberpaperworkno(CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(0, 4))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(13, 15)));
					}
					if(!CommonTool.setDateMask(lsMemberinfos.get(i).getMemberbirthday()).equals(""))
					{
						lsMemberinfos.get(i).setMemberbirthday("****"+CommonTool.getDateMask(lsMemberinfos.get(i).getMemberbirthday()).substring(4,10));
					}
				}
			}
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	@Action(value = "loadCurMember",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMember()
	{
		this.curMemberinfo=this.cc006Service.loadMemberinfoByCompId(strCurCompId,this.strCurMemberId);
		curMemberinfo.setMemberbirthday(CommonTool.getDateMask(curMemberinfo.getMemberbirthday()));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "searchMemberInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String searchMemberInfos()
	{
		this.lsMemberinfos=this.cc006Service.searchMemberinfo(searchMemberCompIdKey, searchMemberNoKey, searchMemberNameKey, searchMemberPhoneKey, searchMemberPCIDKey);
		if(lsMemberinfos!=null && lsMemberinfos.size()>0)
		{
			for( int i=0;i<lsMemberinfos.size();i++)
			{
				lsMemberinfos.get(i).setBmemberno(lsMemberinfos.get(i).getId().getMemberno());
				lsMemberinfos.get(i).setBmembervesting(lsMemberinfos.get(i).getId().getMembervesting());
				lsMemberinfos.get(i).setMemberbirthday(CommonTool.getDateMask(lsMemberinfos.get(i).getMemberbirthday()));
				if(this.cc006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC006", SystemFinal.UR_SPECIAL_CHECK)==false)
				{
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembername()).equals(""))
					{
						lsMemberinfos.get(i).setMembername(CommonTool.FormatString(lsMemberinfos.get(i).getMembername().substring(0, 1))+"**");
					}
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).equals("")
					&& CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).length()==11)
					{
						lsMemberinfos.get(i).setMembermphone(CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(0, 3))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(7, 11)));
					}
					else if(!CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).equals("")
					  && CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone()).length()==8)
					{
								lsMemberinfos.get(i).setMembermphone(CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(0, 2))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMembermphone().substring(6, 8)));
					}
					if(!CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).equals("")
						&& CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).length()==18)
					{
								lsMemberinfos.get(i).setMemberpaperworkno(CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(0, 4))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(16, 18)));
					}
					else if(!CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).equals("")
							&& CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno()).length()==15)
					{
									lsMemberinfos.get(i).setMemberpaperworkno(CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(0, 4))+"****"+CommonTool.FormatString(lsMemberinfos.get(i).getMemberpaperworkno().substring(13, 15)));
					}
					if(!CommonTool.setDateMask(lsMemberinfos.get(i).getMemberbirthday()).equals(""))
					{
						lsMemberinfos.get(i).setMemberbirthday("****"+CommonTool.getDateMask(lsMemberinfos.get(i).getMemberbirthday()).substring(4,10));
					}
				}
			}
			
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
            @Result(name = "post_failure", type = "json")	
         }) 
	@Override
	public String post()
	{
		this.strMessage="";
		if(this.cc006Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC006", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		curMemberinfo.setCardnotomemberno(curMemberinfo.getId().getMemberno());
		curMemberinfo.setMemberbirthday(CommonTool.setDateMask(curMemberinfo.getMemberbirthday()));
		boolean flag=this.cc006Service.post(this.curMemberinfo, null);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
		curMemberinfo=null;
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
           @Result(name = "delete_failure", type = "json")	
        }) 
	@Override
	public String delete()
	{
		this.strMessage="";
		
		return SystemFinal.DELETE_SUCCESS;
	}
	public String getStrCurMemberId() {
		return strCurMemberId;
	}
	public void setStrCurMemberId(String strCurMemberId) {
		this.strCurMemberId = strCurMemberId;
	}
	public Memberinfo getCurMemberinfo() {
		return curMemberinfo;
	}
	public void setCurMemberinfo(Memberinfo curMemberinfo) {
		this.curMemberinfo = curMemberinfo;
	}
	public List<Memberinfo> getLsMemberinfos() {
		return lsMemberinfos;
	}
	public void setLsMemberinfos(List<Memberinfo> lsMemberinfos) {
		this.lsMemberinfos = lsMemberinfos;
	}
	public String getSearchMemberCompIdKey() {
		return searchMemberCompIdKey;
	}
	public void setSearchMemberCompIdKey(String searchMemberCompIdKey) {
		this.searchMemberCompIdKey = searchMemberCompIdKey;
	}
	public String getSearchMemberNoKey() {
		return searchMemberNoKey;
	}
	public void setSearchMemberNoKey(String searchMemberNoKey) {
		this.searchMemberNoKey = searchMemberNoKey;
	}
	public String getSearchMemberNameKey() {
		return searchMemberNameKey;
	}
	public void setSearchMemberNameKey(String searchMemberNameKey) {
		this.searchMemberNameKey = searchMemberNameKey;
	}
	public String getSearchMemberPhoneKey() {
		return searchMemberPhoneKey;
	}
	public void setSearchMemberPhoneKey(String searchMemberPhoneKey) {
		this.searchMemberPhoneKey = searchMemberPhoneKey;
	}
	public String getSearchMemberPCIDKey() {
		return searchMemberPCIDKey;
	}
	public void setSearchMemberPCIDKey(String searchMemberPCIDKey) {
		this.searchMemberPCIDKey = searchMemberPCIDKey;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	
	
}
