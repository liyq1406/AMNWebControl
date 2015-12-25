package com.amani.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.dao.AMN_DaoImp;
import com.amani.tools.DataTool;
import com.amani.tools.SystemInformation;
import com.amani.tools.UserInformation;
/**
 * 
 * @author LiuJie Jun 24, 2013 2:31:57 PM
 * @version: 1.0
 * @Copyright: AMN
 */
@Service
public class InterfaceService extends AMN_ModuleService
{
	
	
	@Autowired
	protected AMN_DaoImp amn_Dao;
	

	
	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
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
	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}
	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}	
}
