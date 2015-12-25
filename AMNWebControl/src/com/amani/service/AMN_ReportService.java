package com.amani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.dao.AMN_DaoImp;
import com.amani.dao.AMN_JDBCDaoImp;
import com.amani.dao.AMN_PADDaoImp;
import com.amani.tools.DataTool;

@Service
public class AMN_ReportService  extends AMN_Service {
	@Autowired
	protected AMN_DaoImp amn_Dao;
	
	@Autowired
	protected AMN_PADDaoImp amn_PADDao;
	
	@Autowired
	protected AMN_JDBCDaoImp amn_JDBCDao;
	
	@Autowired
	protected DataTool dataTool;

	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}

	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}

	public AMN_PADDaoImp getAmn_PADDao() {
		return amn_PADDao;
	}

	public void setAmn_PADDao(AMN_PADDaoImp amn_PADDao) {
		this.amn_PADDao = amn_PADDao;
	}

	public AMN_JDBCDaoImp getAmn_JDBCDao() {
		return amn_JDBCDao;
	}

	public void setAmn_JDBCDao(AMN_JDBCDaoImp amn_JDBCDao) {
		this.amn_JDBCDao = amn_JDBCDao;
	}

	public DataTool getDataTool() {
		return dataTool;
	}

	public void setDataTool(DataTool dataTool) {
		this.dataTool = dataTool;
	}

}
