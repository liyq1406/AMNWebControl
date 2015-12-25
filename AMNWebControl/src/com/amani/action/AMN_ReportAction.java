package com.amani.action;

import org.apache.log4j.Logger;

import com.amani.tools.ValidateMessage;
import com.amani.tools.CommonTool;
import com.amani.tools.Pager;
import com.amani.tools.SystemFinal;

/**
 * 
 * @author LiuJie Jun 24, 2013 10:26:00 AM
 * @version: 1.0
 * @Copyright: AMN
 */
public abstract class AMN_ReportAction extends AMN_Action{
	protected String curComp = "";
	protected String dateFrom = "";
	protected String dateTo = "";
	protected Pager	 curPager ; //翻页
	protected String strMessage="";
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	protected boolean isSkipChain = false;//判断该页面是否从别的页面跳转而来
	protected String strModelName;
	protected String getCurComp() {
		return curComp;
	}

	protected void setCurComp(String curComp) {
		this.curComp = curComp;
	}

	protected Pager getCurPager() {
		return curPager;
	}

	protected void setCurPager(Pager curPager) {
		this.curPager = curPager;
	}

	protected String getDateFrom() {
		return dateFrom;
	}

	protected void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	protected String getDateTo() {
		return dateTo;
	}

	protected void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	protected boolean isSkipChain() {
		return isSkipChain;
	}

	protected void setSkipChain(boolean isSkipChain) {
		this.isSkipChain = isSkipChain;
	}

	protected String getStrMessage() {
		return strMessage;
	}

	protected void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	

}
