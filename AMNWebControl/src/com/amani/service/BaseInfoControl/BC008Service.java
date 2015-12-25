package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Salaryrateinfo;
import com.amani.model.Syscommoninfomode;
import com.amani.model.SyscommoninfomodeId;
import com.amani.model.Sysparaminfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC008Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Sysparaminfo loadSysparamInfoByCompId(String strCompId)
	{
		try
		{
			Sysparaminfo returnValue=new Sysparaminfo();
			String columnValue="";
			Method [] methods=null;
			String strSql=" From Sysparaminfo sysparaminfo where compid='"+strCompId+"' ";
			List<Sysparaminfo> lsSysparaminfo=(List<Sysparaminfo> )this.amn_Dao.findByHql(strSql);
			if(lsSysparaminfo!=null && lsSysparaminfo.size()>0)
			{
				methods = returnValue.getClass().getMethods();
				for (Sysparaminfo sysparaminfo : lsSysparaminfo) 
				{
					columnValue = sysparaminfo.getId().getParamid();
					for(int i=0;i<methods.length;i++)
					{
						if(methods[i].getName().startsWith("set"))
						{
							if(methods[i].getName().length()==8 && columnValue.equalsIgnoreCase(methods[i].getName().substring(3,8)))
							{
								methods[i].invoke(returnValue, methods[i].getParameterTypes()[0].cast(sysparaminfo.getParamvalue()));
								break;
							}
						}
					}
				}
			}
			return returnValue;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public boolean postparamsInfoByCompId(Sysparaminfo params,String strCompId,Salaryrateinfo curRateInfo)
	{
		try
		{
			Field [] fields = params.getClass().getDeclaredFields();
			String param = "";
			String sql = "";
			String columnName = "";
			for(int i=0;i<fields.length;i++){
				Field field = fields[i];
				if(field.getName().startsWith("SP")){
					param = field.getName();
				}
				if(!param.equals(""))
				{
					Object value = ognl.Ognl.getValue(field.getName(), params);
					if(value != null )
					{
						sql = sql+ " update sysparaminfo set paramvalue="+CommonTool.quotedStr(value.toString())+" where compid = "+CommonTool.quotedStr(strCompId)+" and paramid = "+CommonTool.quotedStr(param);
					}
				}
			}
			if(curRateInfo!=null)
			{
				sql=sql+" delete salaryrateinfo where compno='"+strCompId+"' ";
				sql=sql+" insert salaryrateinfo(compno,cp_costrate_mr,cp_salaryrate_mr,cp_costrate_mf,cp_salaryrate_mf,cp_costrate_mj,cp_salaryrate_mj,cp_costrate_ks,cp_salaryrate_ks," +
						" kj_salaryrate_trsa,kj_salaryrate_trsb,kj_salaryrate_mrc,kj_salaryrate_mr,kj_salaryrate_mf,dh_costrate_trsa,dh_costrate_trsb,dh_costrate_mf,dh_salaryrate_trsa,dh_salaryrate_trsb,dh_salaryrate_mf," +
						" hz_salaryrate_qh,hz_salaryrate_jd,jfdh_salary_reward,jjdh_yeji_reward,jjfw_salary_reward,jsr_salary_reward,tjsr_salary_cost," +
						" mf_salaryrate_fiveup,mf_salaryrate_fivedown,olc_cost_yeji_fixed,olc_cost_salary_fixed,nlc_costrate_tr,yjk_costrate_mrmf,yjk_salaryrate_mrmf,yjk_costrate_tr," +
						" salaryrate_tra,salaryrate_trb,xjc_costrate_sjs,xjc_costrate_sx,xjc_costrate_zj,xjc_salary_fixed_db,xjc_salary_fixed_ndb,xjc_salary_fixed_nhg,mr_salary_fixed_tmk,mr_salary_fixed_ty ) " ;
				sql=sql+" values( '"+strCompId+"',"+curRateInfo.getCpcostratemr()+","+curRateInfo.getCpsalaryratemr()+","+curRateInfo.getCpcostratemf()+","+curRateInfo.getCpsalaryratemf()+","+curRateInfo.getCpcostratemj()+","+curRateInfo.getCpsalaryratemj()+","+curRateInfo.getCpcostrateks()+","+curRateInfo.getCpsalaryrateks()+", " +
						" "+curRateInfo.getKjsalaryratetrsa()+","+curRateInfo.getKjsalaryratetrsb()+","+curRateInfo.getKjsalaryratemrc()+","+curRateInfo.getKjsalaryratemr()+","+curRateInfo.getKjsalaryratemf()+","+curRateInfo.getDhcostratetrsa()+","+curRateInfo.getDhcostratetrsb()+","+curRateInfo.getDhcostratemf()+","+curRateInfo.getDhsalaryratetrsa()+","+curRateInfo.getDhsalaryratetrsb()+","+curRateInfo.getDhsalaryratemf()+"," +
						" "+curRateInfo.getHzsalaryrateqh()+","+curRateInfo.getHzsalaryratejd()+","+curRateInfo.getJfdhsalaryreward()+","+curRateInfo.getJjdhyejireward()+","+curRateInfo.getJjfwsalaryreward()+","+curRateInfo.getJsrsalaryreward()+","+curRateInfo.getTjsrsalarycost()+"," +
						" "+curRateInfo.getMfsalaryratefiveup()+","+curRateInfo.getMfsalaryratefivedown()+","+curRateInfo.getOlccostyejifixed()+","+curRateInfo.getOlccostsalaryfixed()+","+curRateInfo.getNlccostratetr()+","+curRateInfo.getYjkcostratemrmf()+","+curRateInfo.getYjksalaryratemrmf()+","+curRateInfo.getYjkcostratetr()+"," +
						" "+curRateInfo.getSalaryratetra()+","+curRateInfo.getSalaryratetrb()+","+curRateInfo.getXjccostratesjs()+","+curRateInfo.getXjccostratesx()+","+curRateInfo.getXjccostratezj()+","+curRateInfo.getXjcsalaryfixeddb()+","+curRateInfo.getXjcsalaryfixedndb()+","+curRateInfo.getXjcsalaryfixednhg()+","+curRateInfo.getMrsalaryfixedtmk()+","+curRateInfo.getMrsalaryfixedty()+" )";
			}
			if(!sql.equals(""))
				return this.amn_Dao.executeSql(sql);
			
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	public List<Syscommoninfomode> loadInfoModesByCompId(String strCompId)
	{
		String strSql=" select modeid,modetype,modename from syscommoninfomode where modesource in (select curcomp from compchaininfo where  relationcomp='"+strCompId+"' )";
		try
		{
			AnlyResultSet<List<Syscommoninfomode>> analysis = new AnlyResultSet<List<Syscommoninfomode>>() {
				public List<Syscommoninfomode> anlyResultSet(ResultSet rs) {
					List<Syscommoninfomode> returnValue = new ArrayList();
					Syscommoninfomode record=null;
					try {
						while (rs != null && rs.next() == true) {
							record=new Syscommoninfomode();
							record.setId(new SyscommoninfomodeId(CommonTool.FormatString(rs.getString("modeid")),CommonTool.FormatInteger(rs.getInt("modetype"))));
							record.setModename(rs.getString("modename"));
							returnValue.add(record);
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue =null;
					}
					return returnValue;
				}
			};
			return (List<Syscommoninfomode>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public Salaryrateinfo loadRateInfoByCompId(String strCompId)
	{
		String strSql=" select compno,cp_costrate_mr,cp_salaryrate_mr,cp_costrate_mf,cp_salaryrate_mf,cp_costrate_mj,cp_salaryrate_mj,cp_costrate_ks,cp_salaryrate_ks," +
						" kj_salaryrate_trsa,kj_salaryrate_trsb,kj_salaryrate_mrc,kj_salaryrate_mr,kj_salaryrate_mf,dh_costrate_trsa,dh_costrate_trsb,dh_costrate_mf,dh_salaryrate_trsa,dh_salaryrate_trsb,dh_salaryrate_mf," +
						" hz_salaryrate_qh,hz_salaryrate_jd,jfdh_salary_reward,jjdh_yeji_reward,jjfw_salary_reward,jsr_salary_reward,tjsr_salary_cost," +
						" mf_salaryrate_fiveup,mf_salaryrate_fivedown,olc_cost_yeji_fixed,olc_cost_salary_fixed,nlc_costrate_tr,yjk_costrate_mrmf,yjk_salaryrate_mrmf,yjk_costrate_tr," +
						" salaryrate_tra,salaryrate_trb,xjc_costrate_sjs,xjc_costrate_sx,xjc_costrate_zj,xjc_salary_fixed_db,xjc_salary_fixed_ndb,xjc_salary_fixed_nhg,mr_salary_fixed_tmk,mr_salary_fixed_ty" +
						" from salaryrateinfo where compno='"+strCompId+"'  ";
		try
		{
			AnlyResultSet<Salaryrateinfo> analysis = new AnlyResultSet<Salaryrateinfo>() {
				public Salaryrateinfo anlyResultSet(ResultSet rs) {
					Salaryrateinfo record = null;
					try {
							if (rs != null && rs.next() == true) {
								record=new Salaryrateinfo();
								record.setCompno(rs.getString("compno"));
								record.setCpcostratemr(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_costrate_mr")),3));
								record.setCpsalaryratemr(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_salaryrate_mr")),3));
								record.setCpcostratemf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_costrate_mf")),3));
								record.setCpsalaryratemf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_salaryrate_mf")),3));
								record.setCpcostratemj(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_costrate_mj")),3));
								record.setCpsalaryratemj(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_salaryrate_mj")),3));
								record.setCpcostrateks(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_costrate_ks")),3));
								record.setCpsalaryrateks(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("cp_salaryrate_ks")),3));
								record.setKjsalaryratetrsa(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("kj_salaryrate_trsa")),3));
								record.setKjsalaryratetrsb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("kj_salaryrate_trsb")),3));
								record.setKjsalaryratemrc(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("kj_salaryrate_mrc")),3));
								record.setKjsalaryratemr(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("kj_salaryrate_mr")),3));
								record.setKjsalaryratemf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("kj_salaryrate_mf")),3));
								record.setDhcostratetrsa(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_costrate_trsa")),3));
								record.setDhcostratetrsb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_costrate_trsb")),3));
								record.setDhcostratemf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_costrate_mf")),3));
								record.setDhsalaryratetrsa(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_salaryrate_trsa")),3));
								record.setDhsalaryratetrsb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_salaryrate_trsb")),3));
								record.setDhsalaryratemf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("dh_salaryrate_mf")),3));
								record.setHzsalaryrateqh(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("hz_salaryrate_qh")),3));
								record.setHzsalaryratejd(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("hz_salaryrate_jd")),3));
								record.setJfdhsalaryreward(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("jfdh_salary_reward")),3));
								record.setJjdhyejireward(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("jjdh_yeji_reward")),3));
								record.setJjfwsalaryreward(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("jjfw_salary_reward")),3));
								record.setJsrsalaryreward(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("jsr_salary_reward")),3));
								record.setTjsrsalarycost(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("tjsr_salary_cost")),3));
								record.setMfsalaryratefiveup(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("mf_salaryrate_fiveup")),3));
								record.setMfsalaryratefivedown(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("mf_salaryrate_fivedown")),3));
								record.setOlccostyejifixed(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("olc_cost_yeji_fixed")),3));
								record.setOlccostsalaryfixed(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("olc_cost_salary_fixed")),3));
								record.setNlccostratetr(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("nlc_costrate_tr")),3));
								record.setYjkcostratemrmf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("yjk_costrate_mrmf")),3));
								record.setYjksalaryratemrmf(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("yjk_salaryrate_mrmf")),3));
								record.setYjkcostratetr(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("yjk_costrate_tr")),3));
								record.setSalaryratetra(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("salaryrate_tra")),3));
								record.setSalaryratetrb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("salaryrate_trb")),3));
								record.setXjccostratesjs(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_costrate_sjs")),3));
								record.setXjccostratesx(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_costrate_sx")),3));
								record.setXjccostratezj(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_costrate_zj")),3));
								record.setXjcsalaryfixeddb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_salary_fixed_db")),3));
								record.setXjcsalaryfixedndb(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_salary_fixed_ndb")),3));
								record.setXjcsalaryfixednhg(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("xjc_salary_fixed_nhg")),3));
								record.setMrsalaryfixedtmk(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("mr_salary_fixed_tmk")),3));
								record.setMrsalaryfixedty(CommonTool.FormatBigDecimalByCount(new BigDecimal(rs.getDouble("mr_salary_fixed_ty")),3));
							}
					} catch (Exception e) {
						e.printStackTrace();
						record =null;
					}
					return record;
				}
			};
			return (Salaryrateinfo) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public boolean  handCopyInfo(String strCompId,String strCopyInfo)
	{
		try
		{
			String strCurCompId="";
			String strSql="";
			String[] lsComps=strCopyInfo.split(";");
			if(lsComps!=null && lsComps.length>0)
			{
				for(int i=0;i<lsComps.length;i++)
				{
					if(!lsComps[i].equals("") && !lsComps[i].equals(strCompId))
					{
						strCurCompId=lsComps[i];
						strSql=strSql+" delete salaryrateinfo where compno='"+strCurCompId+"' ";
						strSql=strSql+" insert salaryrateinfo(compno,cp_costrate_mr,cp_salaryrate_mr,cp_costrate_mf,cp_salaryrate_mf,cp_costrate_mj,cp_salaryrate_mj,cp_costrate_ks,cp_salaryrate_ks," +
								" kj_salaryrate_trsa,kj_salaryrate_trsb,kj_salaryrate_mrc,kj_salaryrate_mr,kj_salaryrate_mf,dh_costrate_trsa,dh_costrate_trsb,dh_costrate_mf,dh_salaryrate_trsa,dh_salaryrate_trsb,dh_salaryrate_mf," +
								" hz_salaryrate_qh,hz_salaryrate_jd,jfdh_salary_reward,jjdh_yeji_reward,jjfw_salary_reward,jsr_salary_reward,tjsr_salary_cost," +
								" mf_salaryrate_fiveup,mf_salaryrate_fivedown,olc_cost_yeji_fixed,olc_cost_salary_fixed,nlc_costrate_tr,yjk_costrate_mrmf,yjk_salaryrate_mrmf,yjk_costrate_tr," +
								" salaryrate_tra,salaryrate_trb,xjc_costrate_sjs,xjc_costrate_sx,xjc_costrate_zj,xjc_salary_fixed_db,xjc_salary_fixed_ndb,xjc_salary_fixed_nhg,mr_salary_fixed_tmk,mr_salary_fixed_ty )" +
								" select '"+strCurCompId+"',cp_costrate_mr,cp_salaryrate_mr,cp_costrate_mf,cp_salaryrate_mf,cp_costrate_mj,cp_salaryrate_mj,cp_costrate_ks,cp_salaryrate_ks," +
								" kj_salaryrate_trsa,kj_salaryrate_trsb,kj_salaryrate_mrc,kj_salaryrate_mr,kj_salaryrate_mf,dh_costrate_trsa,dh_costrate_trsb,dh_costrate_mf,dh_salaryrate_trsa,dh_salaryrate_trsb,dh_salaryrate_mf," +
								" hz_salaryrate_qh,hz_salaryrate_jd,jfdh_salary_reward,jjdh_yeji_reward,jjfw_salary_reward,jsr_salary_reward,tjsr_salary_cost," +
								" mf_salaryrate_fiveup,mf_salaryrate_fivedown,olc_cost_yeji_fixed,olc_cost_salary_fixed,nlc_costrate_tr,yjk_costrate_mrmf,yjk_salaryrate_mrmf,yjk_costrate_tr," +
								" salaryrate_tra,salaryrate_trb,xjc_costrate_sjs,xjc_costrate_sx,xjc_costrate_zj,xjc_salary_fixed_db,xjc_salary_fixed_ndb,xjc_salary_fixed_nhg,mr_salary_fixed_tmk,mr_salary_fixed_ty " +
								" from salaryrateinfo where compno='"+strCompId+"' " ;
					}
				}
			}
			if(!strSql.equals(""))
				return this.amn_Dao.executeSql(strSql);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
}
