package com.amani.service.BaseInfoControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amani.bean.BC021Bean;
import com.amani.model.Commoninfo;
import com.amani.model.Dactivitygive;
import com.amani.model.Dactivityinfo;
import com.amani.model.Mactivitygive;
import com.amani.model.Mactivityinfo;
import com.amani.model.Projectinfo;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 门店活动设定
 */
@Service
public class BC021Service extends AMN_ReportService{
	
	public List<Mactivityinfo> loadSet(String activcompid) throws Exception {
		List<Mactivityinfo> list = new ArrayList<Mactivityinfo>();
		String sql = "select a.*, b.compname from mactivityinfo a,companyinfo b where a.activcompid=b.compno and activcompid='"+ activcompid +"'";
		ResultSet rs = this.amn_Dao.executeQuery(sql);
		while (rs.next()) {
			Mactivityinfo info = new Mactivityinfo();
			info.setActivbillid(rs.getString("activbillid"));
			info.setActivcompid(rs.getString("activcompid"));
			info.setActivname(rs.getString("activname"));
			info.setActivinid(rs.getString("activinid"));
			info.setActivtype(rs.getInt("activtype"));
			info.setStartdate(CommonTool.getDateMask(rs.getString("startdate")));
			info.setEnddate(CommonTool.getDateMask(rs.getString("enddate")));
			info.setActivstate(rs.getInt("activstate"));
			info.setCompname(rs.getString("compname"));
			list.add(info);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> loadAll(String strCompId, String activinid){
		List<Object> result = new ArrayList<Object>();
		String hql="from Mactivityinfo where activcompid='"+ strCompId +"' and activinid='"+ activinid +"'";
		List<Mactivityinfo> list = this.amn_Dao.findByHql(hql);
		Mactivityinfo mactivityinfo = list==null||list.size()==0 ? new Mactivityinfo() : list.get(0);
		//活动赠送主档
		hql = "from Mactivitygive where activcompid='"+ strCompId +"' and activinid='"+ activinid +"'";
		List<Mactivitygive> mgive = this.amn_Dao.findByHql(hql);
		int activorand = list==null||list.size()==0 ? 1 : mgive.get(0).getActivorand();
		mactivityinfo.setActivstate(activorand);//赠送主档逻辑字段临时使用活动主档状态字段代替
		result.add(mactivityinfo);
		//活动明细
		hql = "from Dactivityinfo where activinid='"+ activinid +"'";
		List<Dactivityinfo> dactivity = this.amn_Dao.findByHql(hql);
		result.add(dactivity);
		//赠送明细
		hql="from Dactivitygive where activcompid='"+ strCompId +"' and activinid='"+ activinid +"'";
		List<Dactivitygive> dgive = this.amn_Dao.findByHql(hql);
		result.add(dgive);
		return result;
	}
	
	//提交数据
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public boolean postData(BC021Bean activity) throws Exception {
		Gson gson = new Gson();
		//活动主档
		String activbillid = this.dataTool.loadBillIdByRule(activity.getActivcompid(),"mactivityinfo", "activbillid", "SP008");
		String activinid = UUID.randomUUID().toString();
		Mactivityinfo minfo = new Mactivityinfo();
		minfo.setActivinid(activinid);
		minfo.setActivbillid(activbillid);
		minfo.setActivcompid(activity.getActivcompid());
		minfo.setActivname(activity.getActivname());
		minfo.setActivtype(activity.getActivtype());
		minfo.setActivamt(activity.getActivamt());
		minfo.setActivorand(activity.getActivorand());
		minfo.setActivcount(activity.getActivcount());
		this.amn_Dao.save(minfo);
		//活动明细表
		String activJson = activity.getActivJson();
		if(StringUtils.isNotBlank(activJson)){
			List<Dactivityinfo> list = gson.fromJson(activJson, new TypeToken<List<Dactivityinfo>>(){}.getType());
			for (Dactivityinfo dactivityinfo : list) {
				dactivityinfo.setActivinid(activinid);
			}
			this.amn_Dao.saveOrUpdateAll(list);
		}
		//活动赠送主档
		Mactivitygive mgive = new Mactivitygive();
		mgive.setActivinid(activinid);
		mgive.setActivcompid(activity.getActivcompid());
		mgive.setActivorand(activity.getGiveorand());
		this.amn_Dao.save(mgive);
		//活动赠送明细
		List<Dactivitygive> dgive = new ArrayList<Dactivitygive>();
		String giveAJson = activity.getGiveAJson();
		if(StringUtils.isNotBlank(giveAJson)){//A项目
			dgive = gson.fromJson(giveAJson, new TypeToken<List<Dactivitygive>>(){}.getType());
			for (Dactivitygive dactivitygive : dgive) {
				dactivitygive.setActivtype(1);
				dactivitygive.setActivinid(activinid);
				dactivitygive.setActivcompid(activity.getActivcompid());
			}
		}
		String giveBJson = activity.getGiveBJson();
		if(StringUtils.isNotBlank(giveBJson)){//B项目
			List<Dactivitygive> temp = gson.fromJson(giveBJson, new TypeToken<List<Dactivitygive>>(){}.getType());
			for (Dactivitygive dactivitygive : temp) {
				dactivitygive.setActivtype(2);
				dactivitygive.setActivinid(activinid);
				dactivitygive.setActivcompid(activity.getActivcompid());
				dgive.add(dactivitygive);
			}
		}
		String giveCJson = activity.getGiveCJson();
		if(StringUtils.isNotBlank(giveCJson)){//C产品
			List<Dactivitygive> temp = gson.fromJson(giveCJson, new TypeToken<List<Dactivitygive>>(){}.getType());
			for (Dactivitygive dactivitygive : temp) {
				dactivitygive.setActivtype(3);
				dactivitygive.setActivinid(activinid);
				dactivitygive.setActivcompid(activity.getActivcompid());
				dgive.add(dactivitygive);
			}
		}
		this.amn_Dao.saveOrUpdateAll(dgive);
		return true;
	}
	
	public boolean updateDate(BC021Bean activity) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update mactivityinfo set ");
		if(StringUtils.isNotBlank(activity.getStartdate())){
			sql.append("startdate='"+ activity.getStartdate() +"'");
		}else{
			sql.append("enddate='"+ activity.getEnddate() +"'");
		}
		sql.append(" where activbillid='"+ activity.getActivbillid() +"' and activinid='"+ activity.getActivinid() +"'");
		return this.amn_Dao.executeSql(sql.toString());
	}
	
	public boolean updateState(BC021Bean activity) throws Exception {
		Gson gson = new Gson();
		StringBuffer sql = new StringBuffer();
		String activJson = activity.getActivJson();
		if(StringUtils.isNotBlank(activJson)){
			List<Mactivityinfo> list = gson.fromJson(activJson, new TypeToken<List<Mactivityinfo>>(){}.getType());
			for (Mactivityinfo mactivityinfo : list) {
				sql.append("update mactivityinfo set activstate="+ activity.getActivstate() +" where")
				.append(" activbillid='"+ mactivityinfo.getActivbillid() +"' and activinid='"+ mactivityinfo.getActivinid() +"' ");
			}
		}
		return this.amn_Dao.executeSql(sql.toString());
	}
	
	//获得疗程项目
	@SuppressWarnings("unchecked")
	public Projectinfo loadIteminfo(String strCompId, String strProjectId){
		List<Projectinfo> list = null;
		try{
			String strModeId=this.dataTool.loadSysParam(strCompId,"SP059");
	    	String strFristModeId="";//第一级模板Id
			String strSecondModeId="";//第2级模板ID
			String strThirthModeId="";//第三级模板Id
			//先定位模板门店的连锁级别(暂时支持4级连锁)
			int compLvl=this.dataTool.loadCompLvl(strCompId);
			if(compLvl==2)
			{
				strFristModeId=this.dataTool.loadparentModeId(strModeId);
			}
			else if(compLvl==3)
			{
				strSecondModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			else if(compLvl==4)
			{
				strThirthModeId=this.dataTool.loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.dataTool.loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.dataTool.loadparentModeId(strSecondModeId);
			}
			String strSql=" select projectinfo From Projectinfo projectinfo,Compchaininfo compchaininfo where prjmodeId in ('"+
					strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"')  and curcomp=  prisource and relationcomp='"+
					strCompId+"' and isnull(prjsaletype,0)=1 and prjno = '"+strProjectId+"' and isnull(useflag,1)=1 and isnull(saleflag,1)=1";
			list = this.amn_Dao.findByHql(strSql);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list == null||list.size()==0 ? new Projectinfo() : list.get(0);
	}
	
	//查询分类
	@SuppressWarnings("unchecked")
	public Commoninfo loadCommonInfo(String strCodeno, String strCodekey){
		List<Commoninfo> list=null;
		String strSql=" From Commoninfo commoninfo where isnull(infotype,'')='"+strCodeno+"' and isnull(parentcodekey,'')='"+strCodekey+"'";
		try{
			list = this.amn_Dao.findByHql(strSql);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list == null||list.size()==0 ? new Commoninfo() : list.get(0);
	}
}