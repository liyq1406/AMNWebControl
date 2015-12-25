package com.amani.service;


import java.util.List;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.amani.dao.*;
import com.amani.service.AMN_Service;
import com.amani.tools.DataTool;

/**
 * 
 * @author LiuJie Jun 24, 2013 2:30:04 PM
 * @version: 1.0
 * @Copyright: AMN
 */
@Service
public abstract class AMN_ModuleService extends AMN_Service 
{
	@Autowired
	protected AMN_DaoImp amn_Dao;
	
	@Autowired
	protected AMN_PADDaoImp amn_PADDao;
	
	@Autowired
	protected AMN_JDBCDaoImp amn_JDBCDao;
	
	@Autowired
	protected DataTool dataTool;

	protected String errorMsg;

	protected Logger logger = Logger.getLogger(this.getClass().getName());

	protected static boolean SSH = true;

	public void setDataTool(DataTool dataTool) {
		this.dataTool = dataTool;
	}



	public AMN_DaoImp getAmn_Dao() {
		return amn_Dao;
	}



	public void setAmn_Dao(AMN_DaoImp amn_Dao) {
		this.amn_Dao = amn_Dao;
	}



	public boolean delete(Object curMaster) {
		// need to add log process
		try {
			if (deleteDetail(curMaster)) {
				if (deleteMaster(curMaster)) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	protected abstract boolean deleteDetail(Object curMaster);

	protected abstract boolean deleteMaster(Object curMaster);

	public boolean post(Object curMaster, Object details) {
		boolean bRet = false;
		// need to add log process
		try {
			if (!beforePost(curMaster, details)) {
				return false;
			}
			if (postDetail(details)) {
				if (postMaster(curMaster)) {
					// As SSH(Struts-Spring-Hibernate) architecture,it must be
					// return true here
					// Because "post" method is managed by
					// "transactionInterceptor"
					// So,before finished excute "post" method,dataset has not
					// commited and you can not
					// to execute "afterPost" method
					if (SSH) {
						bRet = true;
					} else {
						bRet = commitDataSet();
						if (bRet) {
							bRet = afterPost(curMaster, details);
						} else {
							this.setErrorMsg("Post dataset failure!");
							bRet = false;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bRet = false;
		}
		return bRet;
	}

	public boolean beforePost(Object curMaster, Object details) {

		return true;
	}

	public boolean afterPost(Object curMaster, Object details) {
		return true;
	}

	protected boolean commitDataSet() {
		// If not manage by tool as "Hibernate" or "Spring" and so on,
		// you must be manage transaction and post dataset by yourself
		// to add code here to commit your dataset
		// Note: you need to add field "curMaster" and field "details"
		// in this class , as follow ,put "curMaster" parameter
		// into "curMaster" field in "postMaster" method and put
		// "details" parameter into "details" field in "postDetail"
		// method. At last ,commit "curMaster" and "details" in
		// this method(commitDataSet).
		return true;
	}

	protected abstract boolean postMaster(Object curMaster);

	protected abstract boolean postDetail(Object details);

	public abstract List loadMasterDataSet(int pageSize, int startRow);

	public DataTool getDataTool() {
		return dataTool;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

}
