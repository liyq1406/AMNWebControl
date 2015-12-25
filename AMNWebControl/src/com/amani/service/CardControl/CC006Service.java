package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;


import com.amani.model.Memberinfo;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class CC006Service  extends AMN_ModuleService{
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
		try
		{
			this.amn_Dao.delete(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		try
		{
			String strSql="insert memberinfoedit(memberno,membername,membermphone,memberpaperworkno,memberbirthday,edituserno,editusername,editdate,edittime) " +
					      " select memberno,membername,membermphone,memberpaperworkno,memberbirthday,'"+CommonTool.getLoginInfo("USERID")+"','"+CommonTool.getLoginInfo("USERNAME")+"','"+CommonTool.getCurrDate()+"','"+CommonTool.getCurrTime()+"' " +
					      " from memberinfo where memberno='"+((Memberinfo)curMaster).getId().getMemberno()+"' ";
			this.amn_Dao.executeSql(strSql);
			this.amn_Dao.saveOrUpdate(curMaster);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public Memberinfo loadMemberinfoByCompId(String strCompId,String strMemberId)
	{
		try
		{
			String strSql=" From Memberinfo memberinfo where membervesting='"+strCompId+"' and memberno='"+strMemberId+"' ";
			List<Memberinfo> lsMemberinfo=this.amn_Dao.findByHql(strSql);
			if(lsMemberinfo!=null && lsMemberinfo.size()>0)
			{
				return lsMemberinfo.get(0);
			}
			return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<Memberinfo> loadMemberinfo(String strCompId)
	{
		try
		{
			String strSql="select memberinfo From Memberinfo memberinfo where  membervesting='"+strCompId+"'  and substring(memberbirthday,5,4) = substring('"+CommonTool.getCurrDate()+"',5,4)";
			return this.amn_Dao.findByHql(strSql);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	

	public List<Memberinfo> searchMemberinfo(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey)
	{
		try
		{
			String strSql=" From Memberinfo memberinfo " +
					" where (memberno='"+searchMemberNoKey+"' or '"+searchMemberNoKey+"' ='' ) "+
					" and  (membername like '%"+searchMemberNameKey+"%' or '"+searchMemberNameKey+"' ='' ) "+
					" and  (membermphone='"+searchMemberPhoneKey+"' or '"+searchMemberPhoneKey+"' ='' ) "+
					" and  (memberpaperworkno='"+searchMemberPCIDKey+"' or '"+searchMemberPCIDKey+"' ='' ) ";
			return this.amn_Dao.findByPage(30, 0, strSql);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
}
