package com.amani.service.PersonnelControl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.amani.bean.PC022Bean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class PC022Service extends AMN_ReportService{
	
	public List<PC022Bean> loadDateSetByCompId(String strCompId,String strFromDate)
	{ 
		try
		{
				strFromDate =  strFromDate.replace("-","");
				String strSql = "exec upg_monthkq_amn '"+strCompId+"' ,'"+strFromDate+"'";
				ResultSet resultSet = this.amn_Dao.executeQuery(strSql);
				Map map = new HashMap();
				PC022Bean bean = null;
				while (resultSet != null && resultSet.next() == true ) {
					String empno = resultSet.getString("empno");//工号
					String empname = resultSet.getString("empname");//名称
					String day = resultSet.getString("sdate");//天
					String strdate =resultSet.getString("strdate2");
					String enddate =resultSet.getString("enddate2");

					if(CommonTool.isNumeric(strdate)){
						strdate = strdate.substring(8,12);
						strdate = CommonTool.getTimeMask(strdate, 2);
					}
					if(CommonTool.isNumeric(enddate)){
						enddate = enddate.substring(8,12);
						enddate = CommonTool.getTimeMask(enddate, 2);
					}

					if(CommonTool.isNumeric(day)){
						day = day.substring(6,8);
					}

					if(map.containsKey(empno)){
						bean =(PC022Bean)map.get(empno);
						bean = setData(bean,day,strdate,enddate);
					}else{
						bean = new PC022Bean();
						bean.setBstaffno(empno);
						bean.setStaffname(empname);
						bean = setData(bean,day,strdate,enddate);
						map.put(empno, bean);
					}
				}
				
				List<PC022Bean> ls = new ArrayList();
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Entry) it.next();
					PC022Bean pc = (PC022Bean)(entry.getValue());
					pc.setData1(pc.getMorningData1() +" | "+pc.getAfternoonData1());
					pc.setData2(pc.getMorningData2() +" | "+pc.getAfternoonData2());
					pc.setData3(pc.getMorningData3() +" | "+pc.getAfternoonData3());
					pc.setData4(pc.getMorningData4() +" | "+pc.getAfternoonData4());
					pc.setData5(pc.getMorningData5() +" | "+pc.getAfternoonData5());
					pc.setData6(pc.getMorningData6() +" | "+pc.getAfternoonData6());
					pc.setData7(pc.getMorningData7() +" | "+pc.getAfternoonData7());
					pc.setData8(pc.getMorningData8() +" | "+pc.getAfternoonData8());
					pc.setData9(pc.getMorningData9() +" | "+pc.getAfternoonData9());
					pc.setData10(pc.getMorningData10() +" | "+pc.getAfternoonData10());
					pc.setData11(pc.getMorningData11() +" | "+pc.getAfternoonData11());
					pc.setData12(pc.getMorningData12() +" | "+pc.getAfternoonData12());
					pc.setData13(pc.getMorningData13() +" | "+pc.getAfternoonData13());
					pc.setData14(pc.getMorningData14() +" | "+pc.getAfternoonData14());
					pc.setData15(pc.getMorningData15() +" | "+pc.getAfternoonData15());
					pc.setData16(pc.getMorningData16() +" | "+pc.getAfternoonData16());
					pc.setData17(pc.getMorningData17() +" | "+pc.getAfternoonData17());
					pc.setData18(pc.getMorningData18() +" | "+pc.getAfternoonData18());
					pc.setData19(pc.getMorningData19() +" | "+pc.getAfternoonData19());
					pc.setData20(pc.getMorningData20() +" | "+pc.getAfternoonData20());
					pc.setData21(pc.getMorningData21() +" | "+pc.getAfternoonData21());
					pc.setData22(pc.getMorningData22() +" | "+pc.getAfternoonData22());
					pc.setData23(pc.getMorningData23() +" | "+pc.getAfternoonData23());
					pc.setData24(pc.getMorningData24() +" | "+pc.getAfternoonData24());
					pc.setData25(pc.getMorningData25() +" | "+pc.getAfternoonData25());
					pc.setData26(pc.getMorningData26() +" | "+pc.getAfternoonData26());
					pc.setData27(pc.getMorningData27() +" | "+pc.getAfternoonData27());
					pc.setData28(pc.getMorningData28() +" | "+pc.getAfternoonData28());
					if((CommonTool.isEmpty(pc.getMorningData29()))){
						pc.setData29(pc.getMorningData29() +" | "+pc.getAfternoonData29());
					}
					if((CommonTool.isEmpty(pc.getMorningData30()))){
						pc.setData30(pc.getMorningData30() +" | "+pc.getAfternoonData30());
					}
					
					if((CommonTool.isEmpty(pc.getMorningData31()))){
						pc.setData31(pc.getMorningData31() +" | "+pc.getAfternoonData31());
					}
			
					ls.add(pc);
				}
			return ls;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	

	private PC022Bean setData(PC022Bean bean,String day,String mData,String aDate){
		if("01".equals(day)){
			bean.setMorningData1(mData);
			bean.setAfternoonData1(aDate);
		}else if("02".equals(day)){
			bean.setMorningData2(mData);
			bean.setAfternoonData2(aDate);
		}else if("03".equals(day)){
			bean.setMorningData3(mData);
			bean.setAfternoonData3(aDate);
		}else if("04".equals(day)){
			bean.setMorningData4(mData);
			bean.setAfternoonData4(aDate);
		}else if("05".equals(day)){
			bean.setMorningData5(mData);
			bean.setAfternoonData5(aDate);
		}else if("06".equals(day)){
			bean.setMorningData6(mData);
			bean.setAfternoonData6(aDate);
		}else if("07".equals(day)){
			bean.setMorningData7(mData);
			bean.setAfternoonData7(aDate);
		}else if("08".equals(day)){
			bean.setMorningData8(mData);
			bean.setAfternoonData8(aDate);
		}else if("09".equals(day)){
			bean.setMorningData9(mData);
			bean.setAfternoonData9(aDate);
		}else if("10".equals(day)){
			bean.setMorningData10(mData);
			bean.setAfternoonData10(aDate);
		}else if("11".equals(day)){
			bean.setMorningData11(mData);
			bean.setAfternoonData11(aDate);
		}else if("12".equals(day)){
			bean.setMorningData12(mData);
			bean.setAfternoonData12(aDate);
		}else if("13".equals(day)){
			bean.setMorningData13(mData);
			bean.setAfternoonData13(aDate);
		}else if("14".equals(day)){
			bean.setMorningData14(mData);
			bean.setAfternoonData14(aDate);
		}else if("15".equals(day)){
			bean.setMorningData15(mData);
			bean.setAfternoonData15(aDate);
		}else if("16".equals(day)){
			bean.setMorningData16(mData);
			bean.setAfternoonData16(aDate);
		}else if("17".equals(day)){
			bean.setMorningData17(mData);
			bean.setAfternoonData17(aDate);
		}else if("18".equals(day)){
			bean.setMorningData18(mData);
			bean.setAfternoonData18(aDate);
		}else if("19".equals(day)){
			bean.setMorningData19(mData);
			bean.setAfternoonData19(aDate);
		}else if("20".equals(day)){
			bean.setMorningData20(mData);
			bean.setAfternoonData20(aDate);
		}else if("21".equals(day)){
			bean.setMorningData21(mData);
			bean.setAfternoonData21(aDate);
		}else if("22".equals(day)){
			bean.setMorningData22(mData);
			bean.setAfternoonData22(aDate);
		}else if("23".equals(day)){
			bean.setMorningData23(mData);
			bean.setAfternoonData23(aDate);
		}else if("24".equals(day)){
			bean.setMorningData24(mData);
			bean.setAfternoonData24(aDate);
		}else if("25".equals(day)){
			bean.setMorningData25(mData);
			bean.setAfternoonData25(aDate);
		}else if("26".equals(day)){
			bean.setMorningData26(mData);
			bean.setAfternoonData26(aDate);
		}else if("27".equals(day)){
			bean.setMorningData27(mData);
			bean.setAfternoonData27(aDate);
		}else if("28".equals(day)){
			bean.setMorningData28(mData);
			bean.setAfternoonData28(aDate);
		}else if("29".equals(day)){
			bean.setMorningData29(mData);
			bean.setAfternoonData29(aDate);
		}else if("30".equals(day)){
			bean.setMorningData30(mData);
			bean.setAfternoonData30(aDate);
		}else if("31".equals(day)){
			bean.setMorningData31(mData);
			bean.setAfternoonData31(aDate);
		}
		return bean;
	}
}
