package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SC019Bean;
import com.amani.model.Companyinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC019Service extends AMN_ReportService{
	
	private static DecimalFormat df = new DecimalFormat("#.##");
	private static DecimalFormat per = new DecimalFormat("#.##%");
	@SuppressWarnings("unchecked")
	public List<Companyinfo> loadCompanyData(){
		String curcomp = CommonTool.getLoginInfo("COMPID");
		String strSql ="select a from Companyinfo a, Compchainstruct b, Compchaininfo c " +
				"where a.compno=b.curcompno and b.complevel=4 and b.curcompno=c.id.relationcomp and c.id.curcomp ='"+curcomp+"' order by a.compno";
		return this.amn_Dao.findByHql(strSql);
	}
	//加载门店
	public List<SC019Bean> loadDateSet(String strCompId, String strDate, Integer sysStatus){
		try{	
			String sql="exec P_YYFX_MDJYZB_Y '"+ strDate +"','"+ strCompId +"',"+ sysStatus;
			AnlyResultSet<List<SC019Bean>> analysis = null;
			switch(sysStatus){
				case 1:
					analysis = getGridOne();
					break;
				case 2:
					analysis = getGridTwo();
					break;
				case 3:
					analysis = getGridThree();
					break;
				case 4:
					analysis = getGridFour();
					break;
				case 5:
					analysis = getGridFive();
					break;
			}
			@SuppressWarnings("unchecked")
			List<SC019Bean> ls= (List<SC019Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	//加载美容部
	public List<SC019Bean> loadFaceSet(String strCompId, String strDate, Integer sysStatus){
		try{	
			String sql="exec P_YYFX_MDJYZB_Y_MRB '"+ strDate +"','"+ strCompId +"',"+ sysStatus;
			AnlyResultSet<List<SC019Bean>> analysis = null;
			switch(sysStatus){
				case 1:
					analysis = getFaceGridOne();
					break;
				case 2:
					analysis = getGridTwo();
					break;
				case 3:
					analysis = getGridThree();
					break;
				case 4:
					analysis = getFaceGridFour();
					break;
				case 5:
					analysis = getFaceGridFive();
					break;
				case 6:
					analysis = getFaceGridSix();
					break;
				case 7:
					analysis = getFaceGridSeven();
					break;
				case 8:
					analysis = getGridFive();
					break;
			}
			@SuppressWarnings("unchecked")
			List<SC019Bean> ls= (List<SC019Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	//加载美发部
	public List<SC019Bean> loadHairSet(String strCompId, String strDate, Integer sysStatus){
		try{	
			String sql="exec P_YYFX_MDJYZB_Y_MFB '"+ strDate +"','"+ strCompId +"',"+ sysStatus;
			AnlyResultSet<List<SC019Bean>> analysis = null;
			switch(sysStatus){
				case 1:
					analysis = getHairGridOne();
					break;
				case 2:
					analysis = getGridTwo();
					break;
				case 3:
					analysis = getGridThree();
					break;
				case 4:
					analysis = getHairGridFour();
					break;
				case 5:
					analysis = getFaceGridFive();
					break;
				case 6:
					analysis = getHairGridSix();
					break;
				case 7:
					analysis = getGridFive();
					break;
			}
			@SuppressWarnings("unchecked")
			List<SC019Bean> ls= (List<SC019Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * 门店基础信息
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getGridOne(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("mdmc"), "")); //门店名称:mdmc
						record.setAttr2(ObjectUtils.toString(rs.getString("kysj"), "")); //开业时间:kysj
						record.setAttr3(ObjectUtils.toString(rs.getString("fjgs"), "")); //房间个数:fjgs
						record.setAttr4(ObjectUtils.toString(rs.getString("mrjl_sl"), "")); //美容经理数量：mrjl_sl
						record.setAttr5(ObjectUtils.toString(rs.getString("mrgw_sl"), "")); //美容顾问数量：mrgw_sl
						record.setAttr6(ObjectUtils.toString(rs.getString("mrs_sl"), "")); //美容师数量：mrs_sl
						record.setAttr7(format(rs.getString("mdmj"))); 						//门店面积:mdmj
						record.setAttr8(ObjectUtils.toString(rs.getString("trs_sl"), "")); //烫染师数量：trs_sl
						record.setAttr9(ObjectUtils.toString(rs.getString("mfs_sl"), "")); //美发师数量：mfs_sl
						record.setAttr10(ObjectUtils.toString(rs.getString("dtjl_sl"), ""));//大堂经理数量：dtjl_sl
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	/**
	 * 经营信息1
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getGridTwo(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("jysj"), "")); //经营数据:jysj
						record.setAttr2(format(rs.getString("hj_yj"))); //合计 hj_yj
						record.setAttr3(format(rs.getString("mb"))); //目标 mb
						record.setAttr4(percent(rs.getString("wcbfb"))); //完成百分比 wcbfb
						record.setMonth1(format(rs.getString("yf1_yj")));//1月~12月 yf1_yj~yf12_yj
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 经营信息2
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getGridThree(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("jysjmc"), "")); //经营数据:jysj
						record.setAttr2(format(rs.getString("yj_yj"))); //月均 yj_yj
						record.setAttr3(format(rs.getString("gspjz"))); //公司平均值 gspjz
						record.setAttr4(format(rs.getString("cyl"))); //差异量 cyl
						record.setMonth1(format(rs.getString("yf1_yj")));//1月~12月 yf1_yj~yf12_yj
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 美发人员产出
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getGridFour(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("mfrycc"), "")); //美容人员产出:mfrycc
						record.setAttr2(format(rs.getString("yj_yj"))); //月均 yj_yj
						record.setAttr3(format(rs.getString("gspjz"))); //公司平均值 gspjz
						record.setAttr4(format(rs.getString("cyl"))); //差异量 cyl
						record.setMonth1(format(rs.getString("yf1_yj")));//1月~12月 yf1_yj~yf12_yj
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 营销节奏
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getGridFive(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("yxjz"), ""));//营销节奏:yxjz
						record.setAttr2(format(rs.getString("qnsjdc"))); //去年实际达成:qnsjdc
						record.setAttr3(format(rs.getString("jnmb"))); //今年目标:jnmb
						record.setAttr4(percent(rs.getString("zzbfb")));  //增长百分比:zzbfb
						record.setAttr4(format(rs.getString("yxhdjh")));  //营销活动计划:yxhdjh
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	public static String format(String value){
		if(StringUtils.isNotBlank(value)){
			return df.format(Float.valueOf(value));
		}
		return "";
	}
	
	public static String percent(String value){
		if(StringUtils.isNotBlank(value)){
			return per.format(Float.valueOf(value));
		}
		return "";
	}
	
	/**
	 * 美发部门店基础信息
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getFaceGridOne(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("mdmc"), "")); //门店名称:mdmc
						record.setAttr2(ObjectUtils.toString(rs.getString("kysj"), "")); //开业时间:kysj
						record.setAttr3(ObjectUtils.toString(rs.getString("fjgs"), "")); //房间个数:fjgs
						record.setAttr4(ObjectUtils.toString(rs.getString("mrjl_sl"), "")); //美容经理数量：mrjl_sl
						record.setAttr5(ObjectUtils.toString(rs.getString("mrgw_sl"), "")); //美容顾问数量：mrgw_sl
						record.setAttr6(ObjectUtils.toString(rs.getString("mrs_sl"), "")); //美容师数量：mrs_sl
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 项目信息
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getFaceGridFour(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("xmxx"), "")); //项目信息:xmxx
						record.setAttr2(format(rs.getString("hj"))); //合计 hj_yj
						record.setAttr3(format(rs.getString("dnmb"))); //当年目标dnmb
						record.setAttr4(percent(rs.getString("wcbfb"))); //完成百分比 wcbfb
						record.setMonth1(format(rs.getString("yf1_yj")));//1月~12月 yf1_yj~yf12_yj
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	/**
	 * 顾客分层管理
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getFaceGridFive(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("khfl"), "")); //项目信息:khfl
						record.setAttr2(ObjectUtils.toString(rs.getString("qngs"))); //去年个数:qngs
						record.setAttr3(ObjectUtils.toString(rs.getString("mb"))); //目标：mb
						record.setAttr4(ObjectUtils.toString(rs.getString("hj_gs"))); //合计：hj_gs
						record.setMonth1(ObjectUtils.toString(rs.getString("yf1_gs")));//1月~12月 yf1_gs~yf12_gs
						record.setMonth2(ObjectUtils.toString(rs.getString("yf2_gs")));
						record.setMonth3(ObjectUtils.toString(rs.getString("yf3_gs")));
						record.setMonth4(ObjectUtils.toString(rs.getString("yf4_gs")));
						record.setMonth5(ObjectUtils.toString(rs.getString("yf5_gs")));
						record.setMonth6(ObjectUtils.toString(rs.getString("yf6_gs")));
						record.setMonth7(ObjectUtils.toString(rs.getString("yf7_gs")));
						record.setMonth8(ObjectUtils.toString(rs.getString("yf8_gs")));
						record.setMonth9(ObjectUtils.toString(rs.getString("yf9_gs")));
						record.setMonth10(ObjectUtils.toString(rs.getString("yf10_gs")));
						record.setMonth11(ObjectUtils.toString(rs.getString("yf11_gs")));
						record.setMonth12(ObjectUtils.toString(rs.getString("yf12_gs")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 美容师人均产出
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getFaceGridSix(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("mrrycc"), "")); //美容人员产出：mrrycc，
						record.setAttr2(format(rs.getString("yj_rs")));		  //人数
						record.setAttr3(format(rs.getString("yj_yj")));       //月均：yj_yj
						record.setAttr4(format(rs.getString("gspjz")));       //公司平均值：gspjz
						record.setAttr5(format(rs.getString("cyl")));      //差异量：cyl
						record.setMonth1(format(rs.getString("yf1_yj")));    //1~12月：yf1_yj~yf2_yj，
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 美容部员工业绩
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getFaceGridSeven(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("staffname"), "")); //员工姓名：staffname，
						record.setAttr2(ObjectUtils.toString(rs.getString("zj")));      //职级：zj，
						record.setAttr3(format(rs.getString("zsyj")));      //总实业绩：zsyj，
						record.setAttr4(format(rs.getString("zkds")));        //总客单数：zkds，
						record.setAttr5(format(rs.getString("zkdj")));        //总客单价：zkdj，
						record.setMonth1(format(rs.getString("yf1_zkdj")));    //1月~12月：yf1_zkdj~yf12_zkdj
						record.setMonth2(format(rs.getString("yf2_zkdj")));    
						record.setMonth3(format(rs.getString("yf3_zkdj")));
						record.setMonth4(format(rs.getString("yf4_zkdj")));
						record.setMonth5(format(rs.getString("yf5_zkdj")));
						record.setMonth6(format(rs.getString("yf6_zkdj")));
						record.setMonth7(format(rs.getString("yf7_zkdj")));
						record.setMonth8(format(rs.getString("yf8_zkdj")));
						record.setMonth9(format(rs.getString("yf9_zkdj")));
						record.setMonth10(format(rs.getString("yf10_zkdj")));
						record.setMonth11(format(rs.getString("yf11_zkdj")));
						record.setMonth12(format(rs.getString("yf12_zkdj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	/**
	 * 美发部门店基础信息
	 * @author Administrator 
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getHairGridOne(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("mdmc"), "")); //门店名称:mdmc
						record.setAttr2(ObjectUtils.toString(rs.getString("kysj"), "")); //开业时间:kysj
						record.setAttr7(format(rs.getString("mdmj"))); 						//门店面积:mdmj
						record.setAttr8(ObjectUtils.toString(rs.getString("trs_sl"), "")); //烫染师数量：trs_sl
						record.setAttr9(ObjectUtils.toString(rs.getString("mfs_sl"), "")); //美发师数量：mfs_sl
						record.setAttr10(ObjectUtils.toString(rs.getString("dtjl_sl"), ""));//大堂经理数量：dtjl_sl
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	/**
	 * 美发师人均产出
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getHairGridFour(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						String str = rs.getString("yj_rs");
						System.out.println(str);
						record.setAttr1(ObjectUtils.toString(rs.getString("mfrycc"), "")); //美容人员产出：mfrycc，
						record.setAttr2(format(rs.getString("yj_rs")));		  //人数
						record.setAttr3(format(rs.getString("yj_yj")));       //月均：yj_yj
						record.setAttr4(format(rs.getString("gspjz")));       //公司平均值：gspjz
						record.setAttr5(format(rs.getString("cyl")));      //差异量：cyl
						record.setMonth1(format(rs.getString("yf1_yj")));    //1~12月：yf1_yj~yf2_yj，
						record.setMonth2(format(rs.getString("yf2_yj")));
						record.setMonth3(format(rs.getString("yf3_yj")));
						record.setMonth4(format(rs.getString("yf4_yj")));
						record.setMonth5(format(rs.getString("yf5_yj")));
						record.setMonth6(format(rs.getString("yf6_yj")));
						record.setMonth7(format(rs.getString("yf7_yj")));
						record.setMonth8(format(rs.getString("yf8_yj")));
						record.setMonth9(format(rs.getString("yf9_yj")));
						record.setMonth10(format(rs.getString("yf10_yj")));
						record.setMonth11(format(rs.getString("yf11_yj")));
						record.setMonth12(format(rs.getString("yf12_yj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	/**
	 * 美发部员工业绩
	 * @return
	 */
	public AnlyResultSet<List<SC019Bean>> getHairGridSix(){
		AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
			public List<SC019Bean> anlyResultSet(ResultSet rs) {
				List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
				SC019Bean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new SC019Bean();
						record.setAttr1(ObjectUtils.toString(rs.getString("staffname"), "")); //员工姓名：staffname，
						record.setAttr2(ObjectUtils.toString(rs.getString("zj")));      //职级：zj，
						record.setAttr3(format(rs.getString("zsyj")));      //总实业绩：zsyj，
						record.setAttr4(format(rs.getString("zkds")));        //总客单数：zkds，
						record.setAttr5(format(rs.getString("zkdj")));        //总客单价：zkdj，
						record.setAttr6(format(rs.getString("trzb")));		//烫染占比：trzb
						record.setAttr7(format(rs.getString("hlzb")));		//护理占比：hlzb
						record.setMonth1(format(rs.getString("yf1_zkdj")));    //1月~12月：yf1_zkdj~yf12_zkdj
						record.setMonth2(format(rs.getString("yf2_zkdj")));    
						record.setMonth3(format(rs.getString("yf3_zkdj")));
						record.setMonth4(format(rs.getString("yf4_zkdj")));
						record.setMonth5(format(rs.getString("yf5_zkdj")));
						record.setMonth6(format(rs.getString("yf6_zkdj")));
						record.setMonth7(format(rs.getString("yf7_zkdj")));
						record.setMonth8(format(rs.getString("yf8_zkdj")));
						record.setMonth9(format(rs.getString("yf9_zkdj")));
						record.setMonth10(format(rs.getString("yf10_zkdj")));
						record.setMonth11(format(rs.getString("yf11_zkdj")));
						record.setMonth12(format(rs.getString("yf12_zkdj")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		return analysis;
	}
	
	/**
	 * 会员分层
	 * @return
	 */
	public List<SC019Bean> loadVipSet(String strCompId, String strDate){
		String sql="exec P_YYFX_MDJYZB_HYFC '"+ strDate +"','"+ strCompId+"'";
		try{
			AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
				public List<SC019Bean> anlyResultSet(ResultSet rs) {
					List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
					SC019Bean record=null;
					try {
						while (rs != null && rs.next()) {
							record=new SC019Bean();
							record.setAttr1(ObjectUtils.toString(rs.getString("compid")));	//公司编号：compid,
							record.setAttr2(ObjectUtils.toString(rs.getString("compname")));   //公司名：compname,
							record.setAttr3(ObjectUtils.toString(rs.getString("bm")));     //部门：bm,
							record.setAttr4(ObjectUtils.toString(rs.getString("zxhy")));     //新增会员：zxhy,
							record.setAttr5(ObjectUtils.toString(rs.getString("zchy")));     //在册会员：zchy,
							record.setAttr6(ObjectUtils.toString(rs.getString("cdhy")));	 //常到会员：cdhy,
							record.setAttr7(ObjectUtils.toString(rs.getString("yxhy")));	 //有效会员：yxhy,
							record.setAttr8(ObjectUtils.toString(rs.getString("cshy")));	 //沉睡会员：cshy,
							record.setAttr9(ObjectUtils.toString(rs.getString("lshy")));	 //流失会员：lshy,
							record.setAttr10(ObjectUtils.toString(rs.getString("khlx_dkh")));	//大客户：khlx_dkh,
							record.setMonth1(format(rs.getString("khlx_dkh_je")));              	//大客户金额：khlx_dkh_je,
							record.setMonth2(ObjectUtils.toString(rs.getString("khlx_AJ")));              //A+客户：khlx_AJ,
							record.setMonth3(format(rs.getString("khlx_AJ_je")));              	//A+客户金额：khlx_AJ_je,
							record.setMonth4(ObjectUtils.toString(rs.getString("khlx_A")));              //A客户：khlx_A,
							record.setMonth5(format(rs.getString("khlx_A_je")));              	//A客户金额：khlx_A_je,
							record.setMonth6(ObjectUtils.toString(rs.getString("khlx_B")));              //B客户：khlx_B,
							record.setMonth7(format(rs.getString("khlx_B_je")));              	//B客户金额：khlx_B_je,
							record.setMonth8(ObjectUtils.toString(rs.getString("khlx_C")));              //C客户：khlx_C,
							record.setMonth9(format(rs.getString("khlx_C_je")));              	//C客户金额：khlx_C_je,
							record.setMonth10(ObjectUtils.toString(rs.getString("khlx_D")));            //D客户：khlx_D,
							record.setMonth11(format(rs.getString("khlx_D_je")));            	//D客户金额：khlx_D_je
							record.setMonth12(ObjectUtils.toString(rs.getString("khlx_E")));            //E客户：khlx_E,
							record.setAttr11(format(rs.getString("khlx_E_je")));            	//E客户金额：khlx_E_je
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<SC019Bean> ls= (List<SC019Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 会员明细
	 * @return
	 */
	public List<SC019Bean> loadDetailSet(String strCompId, String strDate){
		String sql="exec P_YYFX_MDJYZB_Y_HYMX '"+ strDate +"','"+ strCompId+"'";
		try{
			AnlyResultSet<List<SC019Bean>> analysis = new AnlyResultSet<List<SC019Bean>>() {
				public List<SC019Bean> anlyResultSet(ResultSet rs) {
					List<SC019Bean> returnValue = new ArrayList<SC019Bean>();
					SC019Bean record=null;
					try {
						while (rs != null && rs.next()) {
							record=new SC019Bean();
							record.setAttr1(ObjectUtils.toString(rs.getString("compname")));     //门店	compname
							record.setAttr2(ObjectUtils.toString(rs.getString("xh")));     //序号	xh
							record.setAttr3(ObjectUtils.toString(rs.getString("membername"))); //姓名	membername
							record.setAttr4(ObjectUtils.toString(rs.getString("hy_xb")));       //性别	hy_xb
							record.setAttr5(ObjectUtils.toString(rs.getString("hy_nl")));       //年龄	hy_nl
							record.setAttr6(ObjectUtils.toString(rs.getString("jjzk")));	   //经济情况	jjzk
							record.setAttr7(ObjectUtils.toString(rs.getString("zcsj")));	   //注册时间	zcsj
							record.setAttr8(format(rs.getString("qnxfje")));	   //去年消费金额	qnxfje
							record.setAttr9(ObjectUtils.toString(rs.getString("xfxm")));	   //消费项目	xfxm
							record.setAttr10(ObjectUtils.toString(rs.getString("qncj")));	//去年层级	qncj
							record.setMonth1(format(rs.getString("jnxfje")));             //今年消费金额	jnxfje
							record.setMonth2(ObjectUtils.toString(rs.getString("jnxfjh")));   //今年计划	jnxfjh
							record.setMonth3(ObjectUtils.toString(rs.getString("mbcj")));              //目标层级	mbcj
							record.setMonth4(ObjectUtils.toString(rs.getString("xyxm")));    //现有项目	xyxm
							record.setMonth5(ObjectUtils.toString(rs.getString("lcje")));               //疗程金额	lcje
							record.setMonth6(ObjectUtils.toString(rs.getString("ghxm")));    //规划项目	ghxm
							record.setMonth7(ObjectUtils.toString(rs.getString("yjcdsj")));              	//预计出单时间	yjcdsj
							record.setMonth8(ObjectUtils.toString(rs.getString("knza")));    //可能障碍	knza
							record.setMonth9(ObjectUtils.toString(rs.getString("gkff")));              	//攻克方法	gkff
							record.setMonth10(ObjectUtils.toString(rs.getString("fzmrs")));   //负责美容师	fzmrs
							record.setMonth11(ObjectUtils.toString(rs.getString("qtbz")));            	//其它备注	qtbz
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			@SuppressWarnings("unchecked")
			List<SC019Bean> ls= (List<SC019Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
}
