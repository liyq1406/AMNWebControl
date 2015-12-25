package com.amani.tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.amani.model.Sysparaminfo;
import com.opensymphony.xwork2.ActionContext;

import java.text.DecimalFormat;

public class CommonTool {

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static Double getFlowNo(List lsCurr) {
		if (lsCurr == null || lsCurr.size() == 0) {
			return new Double(1f);
		} else {
			return new Double(lsCurr.size() + 1);
		}
	}

	public static String getAMNPassWord(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.length() != 8) {
			return "";
		} else {
			String strAMNPwd = "";
			String mon = strCurrDate.trim().substring(4, 6);
			if (Integer.parseInt(mon) == 11)
				mon = Integer.toString(Integer.parseInt(mon) + 1);
			else
				mon = Integer.toString((Integer.parseInt(mon) + 1) % 12);
			if (mon.trim().length() == 1)
				strAMNPwd = "qwe" + mon + "asd" + mon;
			else if (mon.trim().length() == 2)
				strAMNPwd = "qwe" + mon + "asd" + mon;

			return strAMNPwd;
		}
	}

	// 在日期加上固定天数
	// 参数: String strCurrDate 日期 并且日期格式
	// int iDay 要价的天数
	public static String datePlusDay(String strCurrDate, int iDay) {
		if (iDay == 0 || strCurrDate == null || strCurrDate.equals("")) {
			return strCurrDate;
		} else {
			SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyyMMdd");
			try {
				Date dCurrDate = objStdFormat.parse(strCurrDate);
				Long lDate = dCurrDate.getTime() + new Long(iDay) * 24 * 60
						* 60 * 1000;
				Date dCurrDate2 = new Date(lDate);
				return objStdFormat.format(dCurrDate2);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	// 时间相加 传入参数121212 time：时间 10分钟就输入 10
	public static String timePlusTime(String strCurrTime, int time) {
		if (time == 0 || strCurrTime == null || strCurrTime.equals("")) {
			return strCurrTime;
		} else {
			SimpleDateFormat objStdFormat = new SimpleDateFormat("HHmmss");
			try {
				Date dCurrDate = objStdFormat.parse(strCurrTime);
				Long lDate = dCurrDate.getTime() + new Long(time) * 60 * 1000;
				Date dCurrDate2 = new Date(lDate);
				return objStdFormat.format(dCurrDate2);
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
	}

	// 计算2个日期之间相差的天数
	public static int dateSubDate(String strFromDate, String strToDate) {
		int differnDays = 0;
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dFromDate = objStdFormat.parse(strFromDate);
			Date dToDate = objStdFormat.parse(strToDate);
			// 确保startday在endday之前
			if (dFromDate.after(dToDate)) {
				Date cal = dFromDate;
				dFromDate = dToDate;
				dToDate = cal;
			}
			// 分别得到两个时间的毫秒数
			Long sl = dFromDate.getTime();
			Long el = dToDate.getTime();
			Long ei = el - sl;
			// 根据毫秒数计算间隔天数
			differnDays = (int) (ei / (1000 * 60 * 60 * 24));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return differnDays;
	}

	// 获得当前的日期
	// 返回值：20081010
	public static String getCurrDate() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyyMMdd");
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}

	// 获得当前的时间
	// 返回值 :120000
	public static String getCurrTime() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("HHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取中国的时区
		objStdFormat.setTimeZone(timeZoneChina);// 设置系统时区
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}

	// 格式化日期格式
	// 参数 strCurrDate 要格式化的日期 格式应该是20080101
	// 返回值 如果传入的参数不是八位的话 就返回"" 2008-01-01
	public static String getDateMask(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")) {
			return "";
		} else if (strCurrDate.length() == 10) {
			return strCurrDate;
		} else if (strCurrDate.length() != 8) {
			return "";
		} else {
			return strCurrDate.substring(0, 4) + "-"
					+ strCurrDate.substring(4, 6) + "-"
					+ strCurrDate.substring(6, 8);
		}
	}

	public static String getDateMaskForMonth(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")) {
			return "";
		} else if (strCurrDate.length() == 7) {
			return strCurrDate;
		} else if (strCurrDate.length() != 6) {
			return "";
		} else {
			return strCurrDate.substring(0, 4) + "-"
					+ strCurrDate.substring(4, 6);
		}
	}

	// 去掉日期的格式化
	// 参数 strCurrDate 要格式化的日期 格式应该是2008-01-01
	// 返回值 20080101 如果传入的参数不是10位就返回"" 注意调用这个函数的时候应该先调用checkStdDate函数判断日期格式是否合法
	public static String setDateMask(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")) {
			return "";
		} else if (strCurrDate.length() == 8) {
			return strCurrDate;
		} else if (strCurrDate.length() != 10) {
			return "";
		} else {
			return strCurrDate.substring(0, 4) + strCurrDate.substring(5, 7)
					+ strCurrDate.substring(8, 10);
		}
	}

	public static String setDateMaskForMonth(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")
				|| strCurrDate.length() != 7) {
			return "";
		} else {
			return strCurrDate.substring(0, 4) + strCurrDate.substring(5, 7);
		}
	}

	// 检查日期格式是否合法
	// 传入的参数 strCurrDate 要坚持的日期格式 2008-01-01 注意是加"-"
	// 返回值：如果合法就返回true 否则就返回false
	public static boolean checkStdDate(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")) {
			return true;
		}
		if (strCurrDate.length() != 10) {
			return false;
		}
		strCurrDate = strCurrDate.substring(0, 4) + strCurrDate.substring(5, 7)
				+ strCurrDate.substring(8, 10);
		if (strCurrDate.compareTo("17530101") < 0
				|| strCurrDate.compareTo("99991231") > 0) {
			return false;
		}
		int iCurrYear = Integer.parseInt(strCurrDate.substring(0, 4));
		int iCurrMonth = Integer.parseInt(strCurrDate.substring(4, 6));
		int iCurrDay = Integer.parseInt(strCurrDate.substring(6, 8));
		boolean bFlag = false;
		int iDay = 0;
		if (((iCurrYear / 4 == 0) && (iCurrYear / 100 != 0))
				|| (iCurrYear / 400 == 0)) {
			bFlag = true;
		}
		if ((iCurrMonth == 1 || iCurrMonth == 3 || iCurrMonth == 5
				|| iCurrMonth == 7 || iCurrMonth == 8 || iCurrMonth == 10 || iCurrMonth == 12)
				&& (iCurrDay > 31 || iCurrDay < 1)) {
			return false;
		} else if ((iCurrMonth == 4 || iCurrMonth == 6 || iCurrMonth == 9 || iCurrMonth == 11)
				&& (iCurrDay > 30 || iCurrDay < 1)) {
			return false;
		} else if ((iCurrMonth == 2)
				&& ((bFlag == true && iDay > 29)
						|| (bFlag == false && iDay > 28) || iDay < 0)) {
			return false;
		}
		return true;
	}

	// 格式化时间
	// 参数:String strTime 要格式化的时间 格式应该是120000
	// int iType 要格式化的类型 如果这个值是1 那么返回的值是12:00:00
	// 如果是 2 那么就返回12:00
	public static String getTimeMask(String strTime, int iType) {
		if (iType == 1) {
			if (strTime == null || strTime.equals("")) {
				return "";
			} else if (strTime.length() == 8) {
				return strTime;
			} else {
				return strTime.substring(0, 2) + ":" + strTime.substring(2, 4)
						+ ":" + strTime.substring(4, 6);
			}
		} else if (iType == 2) {
			if (strTime == null || strTime.equals("")) {
				return "";
			} else if (strTime.length() == 5) {
				return strTime;
			} else {
				return strTime.substring(0, 2) + ":" + strTime.substring(2, 4);
			}
		} else {
			return "";
		}
	}

	// 去掉格式化时间
	// 参数:String strTime 要格式化的时间 格式应该是12:00:00
	// int iType 要格式化的类型 如果这个值是1 那么strTime的值是12:00:00
	// 如果是 2 那么strTime的值是12:00
	// 返回值 120000
	public static String setTimeMask(String strTime, int iType) {
		if (iType == 1) {
			if (strTime == null || strTime.equals("")) {
				return "";
			} else {
				if (strTime.length() != 8) {
					return strTime;
				}
				return strTime.substring(0, 2) + strTime.substring(3, 5)
						+ strTime.substring(6, 8);
			}
		} else if (iType == 2) {
			if (strTime == null || strTime.equals("")) {
				return "";
			} else {
				if (strTime.length() != 5) {
					return strTime;
				}
				return strTime.substring(0, 2) + strTime.substring(3, 5) + "00";
			}
		} else {
			return "";
		}
	}

	// 格式化金额
	// 参数 Double dValue 要格式化的金额
	// int iCal 格式化的小数位数
	// 返回值：如果dValue 是null 那么就返回 0 否则就返回他的格式化金额
	public static Double GetGymAmt(Double dValue, int iCal) {
		if (dValue == null) {
			return new Double(0);
		}
		BigDecimal b = new BigDecimal(dValue);
		b = b.setScale(iCal, BigDecimal.ROUND_HALF_UP);
		return new Double(b.doubleValue());
	}

	// 检查自动生成单号的是不是和公司相关
	public static boolean ifAutoKeyByComp(String strDefaultKey) {
		int iLen = strDefaultKey.length();
		strDefaultKey = strDefaultKey.toLowerCase();
		int iFlag = 0;
		for (int i = 0; i < iLen; i++) {
			if (strDefaultKey.substring(i, i + 1).equals("c"))
				iFlag++;
		}
		if (iFlag > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 检查自动生成单号的是不是和日期相关
	public static boolean ifAutoKeyByDate(String strDefaultKey) {
		int iFlag = 0;
		int iLen = strDefaultKey.length();
		strDefaultKey = strDefaultKey.toLowerCase();
		for (int i = 0; i < iLen; i++) {
			if (strDefaultKey.substring(i, i + 1).equals("y"))
				iFlag++;
			else if (strDefaultKey.substring(i, i + 1).equals("m"))
				iFlag++;
			else if (strDefaultKey.substring(i, i + 1).equals("d"))
				iFlag++;
		}
		if (iFlag > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 检查自动生成单号的是不是和关键字相关
	public static int checkAutoKey(String strDefaultKey) {
		int iFlag = 0;
		strDefaultKey = strDefaultKey.toLowerCase();
		int l = strDefaultKey.length();
		if (strDefaultKey == "")
			return -1;
		for (int i = 0; i < l; i++) {
			if (strDefaultKey.substring(i, i + 1).equals("x"))
				iFlag++;
		}
		if (iFlag > 0)
			return iFlag;
		else
			return 0;
	}

	// 在拼sql的时候，加''
	public static String quotedStr(String strValue) {
		return "'" + strValue + "'";
	}

	// 判断是否是散客
	// 如果是散客那么就返回true
	// 否则就返回false
	public static boolean isCashCustomer(String strValue) {
		if (strValue != null && strValue.length() >= 4
				&& strValue.substring(1, 4).equals("散客"))
			return true;
		else
			return false;
	}

	// 获得当前的月份的开始日期
	public static String getCurrFirstDayOfMonth(String strDate) {
		return strDate.substring(0, 7) + "-01";
	}

	// 获得当前的月份的最后一天
	public static String getCurrLastDayOfMonth(String strCurrDate) {
		int day_tab[][] = {
				{ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		int leap;
		if (Integer.parseInt(strCurrDate.substring(0, 4)) % 4 == 0
				&& Integer.parseInt(strCurrDate.substring(0, 4)) % 100 != 0
				|| Integer.parseInt(strCurrDate.substring(0, 4)) % 400 == 0) {
			leap = 1;
		} else {
			leap = 0;
		}
		int low = Integer.parseInt(strCurrDate.substring(5, 7));
		int strLastDay = day_tab[leap][low];

		StringBuffer result = new StringBuffer();
		result.append(strCurrDate.substring(0, 4));
		result.append("-");
		result.append(strCurrDate.substring(5, 7));
		result.append("-");
		result.append(strLastDay);
		return result.toString();
	}

	// func: 传入日期得到得到当前日期是星期几
	// Param: strDate--传入的日期
	// return:星期几
	// ------------------------------------------------------------------------------
	public static Integer getWeekDay(String strDate) {
		Integer nWeekDay = -1;
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat dataFromat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date CurrDate = dataFromat.parse(strDate);
			gc.setTime(CurrDate);
			nWeekDay = gc.get(Calendar.DAY_OF_WEEK);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nWeekDay;
	}

	public static String AMNCalNextDay(String strFromDate, int dPeriod,
			String strPeriodUnit) {
		String strRetDay = "";
		if (strPeriodUnit.equals("1")) {
			strRetDay = datePlusDay(strFromDate, 365 * dPeriod);
		}
		if (strPeriodUnit.equals("2")) {
			strRetDay = datePlusDay(strFromDate, 31 * dPeriod);
		}
		if (strPeriodUnit.equals("3")) {
			strRetDay = datePlusDay(strFromDate, dPeriod);
		}
		if (strPeriodUnit.equals("4")) {
			strRetDay = datePlusDay(strFromDate, 92 * dPeriod);
		}

		return strRetDay;
	}

	public static HashMap gainOrgList() {
		HashMap orgList = null;
		ActionContext ctx = ActionContext.getContext();
		Map application = ctx.getApplication();
		String key = "orgList";
		orgList = (HashMap) application.get(key);
		return orgList;
	}

	public static String gainOrgName(String orgCode) {
		HashMap orgList = gainOrgList();

		String orgName = (String) orgList.get(orgCode);
		return orgName;
	}

	/****************************************************************************
	 * GetConditionStr()
	 * 
	 * /**Category From~To处理函数
	 * 
	 * /**Description 根据From , Tot组织成相应Sql 检索语句.
	 * 
	 * /**Prototype void __fastcall GetConditionStr( AnsiString a_strFrom,
	 * AnsiString a_strTo, AnsiString a_strFieldName, AnsiString a_strOperation,
	 * int nDataType, AnsiString &a_strCondition)
	 * 
	 * /**Parameters a_strFrom : From String a_strTo : To String a_strFieldName
	 * : 当前检索字段名称 a_strOperation : 组合关系 int nDataType : a_strFrom,a_strTo 数据类型.
	 * CT_DATE_YMD : 日期类型YMD 格式 CT_DATE_YM : 日期类型YM 格式 CT_DATE_Y : 日期类型Y 格式
	 * CT_TIME : 时间类型Y 格式 CT_STRING : 字符串类型 CT_FLOAT : Float 字段 CT_INTEGER :
	 * Inter 字段
	 * 
	 * &a_strCondition : Sql 检索语句返回, 可以先转入已有语句再与目前语句组合.
	 * 
	 * /**Return Values No Return
	 * 
	 * /**Remarks: SQL 产生原则为: 1. From, To 都为空"", 不组织条件 2. From为"", SQL 条件为 < =To
	 * AND IsNull 3. To 为"", SQL 条件为 >= FORM 4. From , To都不为"", SQL 条件为 >=From
	 * AND <=To /
	 *****************************************************************************/

	public static void GetConditionStr(String a_strFrom, String a_strTo,
			String a_strFieldName, String a_strOperation, int nDataType,
			StringBuffer a_strCondition) {
		String strFrom = "";
		String strTo = ""; // get date text
		String strSingleCondition = ""; // current condition

		strFrom = a_strFrom;
		strTo = a_strTo;

		if (strFrom.equals("") || strTo.equals(""))
			return;
		if (strFrom.equals("''") || strTo.equals("''"))
			return;
		if (!strFrom.equalsIgnoreCase(quotedStr("*")))// != quotedStr("*"))
		{
			if (strFrom.equalsIgnoreCase(strTo))// == strTo)
			{
				if (strFrom.equalsIgnoreCase("''")
						|| strFrom.equalsIgnoreCase(""))
					strSingleCondition = "(" + a_strFieldName + "="
							+ quotedStr(strFrom) + " OR " + a_strFieldName
							+ " IS NULL)";
				else
					strSingleCondition = "(" + a_strFieldName + "="
							+ quotedStr(strFrom) + ")";
			} else if (strFrom.equalsIgnoreCase("''")
					|| strFrom.equalsIgnoreCase(""))
				strSingleCondition = "(" + a_strFieldName + "<="
						+ quotedStr(strTo) + " OR " + a_strFieldName
						+ " IS NULL)";
			else if (strTo.equalsIgnoreCase("''") || strTo.equalsIgnoreCase(""))
				strSingleCondition = "(" + a_strFieldName + ">="
						+ quotedStr(strFrom) + ")";
			else
				strSingleCondition = "(" + a_strFieldName + ">="
						+ quotedStr(strFrom) + " AND " + a_strFieldName + "<="
						+ quotedStr(strTo) + ")";
		}

		if (!strSingleCondition.equalsIgnoreCase("")) {
			if (!a_strCondition.toString().equalsIgnoreCase("")) {
				a_strCondition = a_strCondition.append(" ")
						.append(a_strOperation).append(" ")
						.append(strSingleCondition);
			} else
				a_strCondition = a_strCondition.append(strSingleCondition);
		}
		return;
	}

	// 获得当前的时间
	// 返回值 :120000
	public static String getAllCurrDate() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}

	// 格式化日期格式
	// 参数 strCurrDate 要格式化的日期 格式应该是20080101
	// 返回值 如果传入的参数不是八位的话 就返回"" 2008-01-01
	public static String getAllDateMask(String strCurrDate) {
		if (strCurrDate == null || strCurrDate.equals("")) {
			return "";
		} else if (strCurrDate.length() == 19) {
			return strCurrDate;
		} else if (strCurrDate.length() != 15) {
			return "";
		} else {
			return strCurrDate.substring(0, 4) + "-"
					+ strCurrDate.substring(4, 6) + "-"
					+ strCurrDate.substring(6, 8) + strCurrDate.substring(8, 9)
					+ strCurrDate.substring(9, 11) + ":"
					+ strCurrDate.substring(11, 13) + ":"
					+ strCurrDate.substring(13, 15);
		}
	}

	public static String isNullForString(String str) {
		return str == null ? "" : str;
	}

	public static String FormatString(Object strValue) {
		String ret = "";
		try {
			if (strValue != null)
				ret = (String) strValue;
		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		} finally {
			return ret;
		}
	}

	public static Integer FormatInteger(Object intValue) {
		Integer ret = Integer.valueOf(0);
		try {
			if (intValue != null)
				ret = (Integer) intValue;
		} catch (Exception e) {
			e.printStackTrace();
			ret = 0;
		} finally {
			return ret;
		}
	}

	public static Double FormatDouble(Object douValue) {
		Double ret = Double.valueOf(0.0D);
		try {
			if (douValue != null)
				ret = (Double) douValue;
		} catch (Exception e) {
			e.printStackTrace();
			ret = 0d;
		} finally {
			return ret;
		}
	}

	public static BigDecimal FormatBigDecimal(Object strValue) {
		BigDecimal ret = new BigDecimal("0");
		try {
			if (strValue != null)
				ret = ((BigDecimal) strValue).setScale(2,
						BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
			ret = new BigDecimal("0");
		} finally {
			return ret;
		}
	}

	public static BigDecimal FormatBigDecimalByCount(Object strValue, int ccount) {
		BigDecimal ret = new BigDecimal("0");
		try {
			if (strValue != null)
				ret = ((BigDecimal) strValue).setScale(ccount,
						BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
			ret = new BigDecimal("0");
		} finally {
			return ret;
		}
	}

	public static BigDecimal FormatBigDecimalZ(Object strValue) {
		BigDecimal ret = new BigDecimal("0");
		try {
			if (strValue != null)
				ret = ((BigDecimal) strValue).setScale(0,
						BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
			ret = new BigDecimal("0");
		} finally {
			return ret;
		}
	}

	public static BigDecimal FormatBigDecimalT(Object strValue) {
		BigDecimal ret = new BigDecimal("0");
		try {
			if (strValue != null)
				ret = ((BigDecimal) strValue).setScale(4,
						BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
			ret = new BigDecimal("0");
		} finally {
			return ret;
		}
	}

	// 获得某月的天数
	// 参数格式：2008-12-26
	public static int genDaysOfMonth(String strDate) throws Exception {
		int days = 0;
		if (strDate.length() != 10) {
			throw new Exception("日期格式不正确!");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		Date date1 = sdf.parse(strDate);
		calendar.setTime(date1); // 放入你的日期
		days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	public static String getRemoteIP() {
		String remoteIp = "";
		ServletContext servletContext = (ServletContext) ActionContext
				.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		HttpServletRequest httpServletRequest = (HttpServletRequest) ActionContext
				.getContext().get(ServletActionContext.HTTP_REQUEST);
		remoteIp = httpServletRequest.getRemoteAddr();

		return remoteIp;
	}

	public static String buildSearchCondition(String[] fields, String[] values,
			boolean bAccurate) {
		String strRet = "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] == null || values[i] == null)
				continue;
			if (fields[i].equalsIgnoreCase("")
					|| values[i].equalsIgnoreCase(""))
				continue;

			String filter = "";
			if (bAccurate)
				filter = fields[i] + "=" + quotedStr(values[i]);
			else
				filter = fields[i] + " like "
						+ quotedStr("%" + values[i] + "%");

			if (!buf.toString().equalsIgnoreCase("")) {
				filter = " and " + filter;
				buf.append(filter);
			} else {
				buf.append(filter);
			}
		}
		strRet = buf.toString();
		return strRet;
	}

	public static String genExceptionLogMsg(String exceptFrom,
			String methodName, String cause) {
		String strErrorMsg = "";
		strErrorMsg = "AMNEXCEPTION:TYPE(" + exceptFrom + ")&&METHOD("
				+ methodName + ")&&CAUSE BY:\n" + cause;
		return strErrorMsg;
	}

	public static String genExceptionLogMsg(String exceptFrom,
			String methodName, String cause, String strSql) {
		String strErrorMsg = "";
		strErrorMsg = "AMNEXCEPTION:TYPE(" + exceptFrom + ")&&METHOD("
				+ methodName + ")&&SQL(" + strSql + ")&&CAUSE BY:\n" + cause;
		return strErrorMsg;
	}

	public static String genExceptionLogMsg(String exceptFrom,
			String methodName, String cause, String compId, String billId,
			String date, String time, String cardId) {
		String strErrorMsg = "";
		String data = "";
		data = isNullForString(compId) + "$" + isNullForString(billId) + "$"
				+ isNullForString(date) + "$" + isNullForString(time) + "$"
				+ isNullForString(cardId);
		strErrorMsg = "AMNEXCEPTION:TYPE(" + exceptFrom + ")&&METHOD("
				+ methodName + ")&&DATA(" + data + ")&&CAUSE BY:\n" + cause;
		return strErrorMsg;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public static String encryptData(String input) throws Exception {

		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = "GAVINAPPLE".getBytes();
		DESKeySpec dks = new DESKeySpec(rawKeyData);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);

		Cipher c = Cipher.getInstance("DES");
		c.init(Cipher.ENCRYPT_MODE, key, sr);
		byte[] cipherByte = c.doFinal(input.getBytes());
		String dec = byteArr2HexStr(cipherByte);
		return dec;

	}

	/**
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String decryptData(String input) throws Exception {
		byte[] dec = hexStr2ByteArr(input);

		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = "GAVINAPPLE".getBytes();

		DESKeySpec dks = new DESKeySpec(rawKeyData);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		SecretKey key = keyFactory.generateSecret(dks);

		Cipher c = Cipher.getInstance("DES");
		c.init(Cipher.DECRYPT_MODE, key, sr);
		byte[] clearByte = c.doFinal(dec);

		return new String(clearByte);

	}

	// 获得序号
	// 参数：List lsCurr 当前的list
	// strFieldName 是当前的属性 例如：id.ggb02f
	public static Double getFlowNo_Ex(List lsCurr, String strFieldName)// get
	// flow
	// no
	{
		if (lsCurr == null || lsCurr.size() == 0) {
			return new Double(1f);
		} else {
			Double TemDoubleValue = 0d;
			for (int i = 0; i < lsCurr.size(); i++) {
				try {
					if (Double.parseDouble(BeanUtils.getProperty(lsCurr.get(i),
							strFieldName)) >= TemDoubleValue) {
						TemDoubleValue = Double.parseDouble(BeanUtils
								.getProperty(lsCurr.get(i), strFieldName));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return new Double(TemDoubleValue + 1);
		}
	}

	public static String getLoginUserId() {
		ActionContext ctx = ActionContext.getContext();
		return ctx.getSession().get("StrCurUserid").toString();
	}

	public static String getLoginInfo(String strValue) {
		return getLoginInfo(strValue, getUserInfo());
	}

	public static UserInformation getUserInfo() {
		try {
			ActionContext ctx = ActionContext.getContext();
			Map session = ctx.getSession();
			UserInformation userinfo = (UserInformation) session
					.get("userInfo");
			session = null;
			return userinfo;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getLoginInfo(String strValue, UserInformation userInfo) {
		if (userInfo == null)
			return "";
		if (strValue == null || strValue.equals("")) {
			return "";
		} else {
			if (strValue.equals("COMPID")) {
				return userInfo.getCompId();
			} else if (strValue.equals("USERID")) {
				return userInfo.getUserId();
			} else if (strValue.equals("COMPNAME")) {
				return userInfo.getCompName();
			} else if (strValue.equals("USERNAME")) {
				return userInfo.getUserName();
			} else {
				return "";
			}
		}
	}

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡
																	// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]
				if (index >= 0) {// 找到了
					mac = line.substring(index + "hwaddr".length() + 1).trim();// 取出mac地址并去除2边空格
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");// windows下的命令，显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical
																		// address]
				if (index >= 0) {// 找到了
					index = line.indexOf(":");// 寻找":"的位置
					if (index >= 0) {
						mac = line.substring(index + 1).trim();// 取出mac地址并去除2边空格
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	public static String getMACAddressEx() {

		String address = "";
		String os = getOSName();
		if (os.startsWith("windows")) {
			address = getWindowsMACAddress();
		} else {
			address = getUnixMACAddress();
		}

		if (address == null || address.equalsIgnoreCase("")) {
			address = "FF-FF-FF-FF-FF-FF";
		}
		// address = getMetaMachineCodeEX();
		return address;
	}

	public static String getMACAddress() {
		String address = "";
		ServletContext servletContext = (ServletContext) ActionContext
				.getContext().get(ServletActionContext.SERVLET_CONTEXT);
		address = (String) servletContext.getAttribute("server_mac");
		return address;
	}

	public String loadLoaclMACAddress() {
		String strMac = "";
		try {
			String ip = java.net.InetAddress.getLocalHost().getHostAddress();
			strMac = getMacAddressIP(ip);
			strMac = (strMac != null) ? strMac.replaceAll("-", "") : "";
		} catch (IOException ex) {
		}
		return strMac;
	}

	private String getMacAddressIP(String remotePcIP) {
		String str = "";
		String macAddress = "";
		try {
			Process pp = Runtime.getRuntime().exec("nbtstat -A " + remotePcIP);
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException ex) {
		}
		return macAddress;
	}

	// 获取前三个月的日期
	public static String getafterMonththree() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -3);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println("today:   " + dateFormat.format(date)
				+ "   3   months   after:   " + dateFormat.format(otherDate));
		return dateFormat.format(otherDate);
	}

	// 获取前六个月的日期
	public static String getafterMonthsix() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -6);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println("today:   " + dateFormat.format(date)
				+ "   6   months   after:   " + dateFormat.format(otherDate));
		return dateFormat.format(otherDate);
	}

	// 获取前九个月的日期
	public static String getafterMonthnine() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -9);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println("today:   " + dateFormat.format(date)
				+ "   9   months   after:   " + dateFormat.format(otherDate));
		return dateFormat.format(otherDate);
	}

	// 获取前一年月的日期
	public static String getafteryear() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -12);
		Date otherDate = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println("today:   " + dateFormat.format(date)
				+ "   12   months   after:   " + dateFormat.format(otherDate));
		return dateFormat.format(otherDate);
	}

	public static boolean checkStr(String strTmp) {
		boolean bRet = false;
		if (strTmp != null && "".equals(strTmp) == false
				&& "*".equals(strTmp) == false) {
			bRet = true;
		}
		return bRet;
	}

	public static byte[] loadByteByFile(String strFilename) {
		try {
			File file = new File(strFilename);
			if (!(file.exists())) {
				return null;
			}
			long length = file.length();
			byte[] bytes = new byte[(int) length];
			InputStream is = new FileInputStream(file);
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			is.close();
			file = null;

			// 创建目标文件
			System.out.println(bytes.length);

			return bytes;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean isDigit(String s) {
		Pattern p = Pattern.compile("\\d+\\.?\\d*");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public static String loadFixLenthString(int iRandLen) {
		String strRandCode = "";
		ArrayList list = new ArrayList();
		for (int i = 0; i < 10; i++)
			list.add(i);
		for (char c = 'A'; c <= 'Z'; c++)
			list.add(c);
		for (int i = 0; i < iRandLen; i++) {
			int mathInt;
			mathInt = (int) (Math.random() * list.size());
			strRandCode = strRandCode + list.get(mathInt);
			list.remove(mathInt);
		}
		return strRandCode;
	}

	public static boolean isEmpty(String str) {
		boolean b = false;
		if (str != null && !str.equals("") && !str.equalsIgnoreCase("null")) {
			b = true;
		}
		return b;
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static String addMonth(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, months);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(calendar.getTime());
	}
	
	
	public static String addMonth(String strDate,int months) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = dateFormat.parse(strDate);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return dateFormat.format(calendar.getTime());
	}

	public static String random(int len) {

		Random rand = new Random();
		int[] param = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result + "";
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getNowTime());
		//System.out.println();
	}
	
	public static String getNowTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return dateFormat.format(calendar.getTime());
	}
	
}
