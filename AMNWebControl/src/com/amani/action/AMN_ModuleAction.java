package com.amani.action;


import org.apache.log4j.Logger;

import com.amani.tools.ValidateMessage;
import com.amani.tools.Pager;
import com.amani.tools.CommonTool;

import com.amani.tools.SystemFinal;
/**
 * 
 * @author LiuJie Jun 24, 2013 10:25:27 AM
 * @version: 1.0
 * @Copyright: AMN
 */
public abstract class AMN_ModuleAction extends AMN_Action
{
	protected Pager		curPage; //翻页
	protected String 	strMessage=""; //返回的消息
	protected int		curStatus = 3; //单据的状态 
	protected int		iSeqNo = -1; //明细的索引
	protected int		sysStatus = 1;//系统状态
	protected String    strCurDetail;//当前是那个tabpane
	protected String    strCompId;
	protected String	strCompName;
	protected String    strUserId;
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	protected String    strModelName;
	
	public String getStrCompName() {
		return CommonTool.getLoginInfo("COMPNAME");
	}
	public String getStrCompId() {
		return CommonTool.getLoginInfo("COMPID");
	}
	public String getStrUserId() {
		return CommonTool.getLoginInfo("USERID");
	}
	
	public Pager getCurPage() {
		return curPage;
	}
	public void setCurPage(Pager curPage) {
		this.curPage = curPage;
	}
	public int getCurStatus() {
		return curStatus;
	}
	public void setCurStatus(int curStatus) {
		this.curStatus = curStatus;
	}
	public int getISeqNo() {
		return iSeqNo;
	}
	public void setISeqNo(int seqNo) {
		iSeqNo = seqNo;
	}
	public String getStrCurDetail() {
		return strCurDetail;
	}
	public void setStrCurDetail(String strCurDetail) {
		this.strCurDetail = strCurDetail;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public int getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(int sysStatus) {
		this.sysStatus = sysStatus;
	}
	public String post()
	{

		this.setStrMessage("");
		if(beforePost()==false)
		{
			return SystemFinal.POST_FAILURE;
		}
		
		if(postActive()==true)
		{	
			if(afterPost()==false)
			{
				if(CommonTool.FormatString(this.strMessage).equals(""))
				{
					this.strMessage = ValidateMessage.strMessage_Right_04;
				}
				return SystemFinal.POST_FAILURE;
			}
			else
			{
				return SystemFinal.POST_SUCCESS;
			}
		}
		else
		{
			if(CommonTool.FormatString(this.strMessage).equals(""))
			{
				this.strMessage = ValidateMessage.strMessage_Right_04;
			}
			return SystemFinal.POST_FAILURE;
		}
	}

	protected abstract boolean beforePost();
	protected abstract boolean afterPost();
	protected abstract boolean postActive();
	public String delete()
	{
		this.setStrMessage("");
		if(beforeDelete()==false)
		{
			return SystemFinal.DELETE_FAILURE;
		}
		
		if(deleteActive()==true)
		{
			if(afterDelete()==false)
			{	
				if(CommonTool.FormatString(this.strMessage).equals(""))
				{
					this.strMessage = ValidateMessage.strMessage_Right_05;
				}
				return SystemFinal.DELETE_FAILURE;
			}
			else
			{
				if(CommonTool.FormatString(this.strMessage).equals(""))
				{
					this.strMessage = ValidateMessage.strMessage_Right_05;
				}
				return SystemFinal.DELETE_SUCCESS;
			}
		}
		else
		{
			if(CommonTool.FormatString(this.strMessage).equals(""))
			{
				this.strMessage = ValidateMessage.strMessage_Right_05;
			}
			return SystemFinal.DELETE_SUCCESS;
		}
	}

	protected abstract boolean beforeDelete();
	protected abstract boolean afterDelete();
	protected abstract boolean deleteActive();
	public String load()
	{
		this.setStrMessage("");
		if(beforeLoad()==false)
		{
			return SystemFinal.LOAD_FAILURE;
		}
		if(loadActive()==true)
		{
			if(afterLoad() == false)
			{
				return SystemFinal.LOAD_FAILURE;
			}
			else
			{
				return SystemFinal.LOAD_SUCCESS;
			}
		}
		else
		{

			this.strMessage = ValidateMessage.strMessage_Right_06;
			return SystemFinal.LOAD_FAILURE;
		}
		
	}

	protected abstract boolean beforeLoad();
	protected abstract boolean afterLoad();
	protected abstract boolean loadActive();
	public String add()
	{
		this.setStrMessage("");
		if(beforeAdd()==false)
		{
			return SystemFinal.ADD_FAILURE;
		}
		
		if(addActive()==true)
		{
			if(afterAdd()==false)
			{
				return SystemFinal.ADD_FAILURE;
			}
			else
			{
				return SystemFinal.ADD_SUCCESS;
			}
		}
		else
		{
			return SystemFinal.ADD_FAILURE;
		}
	}

	protected abstract boolean beforeAdd();
	protected abstract boolean afterAdd();
	protected abstract boolean addActive();
	public String getStrModelName() {
		return strModelName;
	}

	public void setStrModelName(String strModelName) {
		this.strModelName = strModelName;
	}
}
