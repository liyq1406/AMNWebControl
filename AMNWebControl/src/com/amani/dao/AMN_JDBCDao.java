package com.amani.dao;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
/**
 * 
 * @author LiuJie Jul 1, 2013 3:24:28 PM
 * @version: 1.0
 * @Copyright: AMN
 */
@SuppressWarnings("unchecked")
public interface AMN_JDBCDao {
	public void setModel(Class model);
	public Class getModel();
	public void delete(Object model) throws Exception;
	
	/**
	 * @param modelId
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteById(Object modelId) throws Exception;
	/**
	 * @param modelId
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteById(Object modelId,Class model) throws Exception;
	/**
	 * @param modelList
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteAll(List modelList) throws Exception;
	/**
	 * @param sqlStr
	 * @exception RuntimeException,DataAccessException
	 */
	public boolean deleteBySql(final String sqlStr) throws Exception;
	
	/**
	 * @return
	 * @exception DataAccessException,RuntimeException
	 */
	public List findAll() throws Exception;
	
	public List findAll(Class model) throws Exception;
	
	/**
	 * @param modelId
	 * @return 
	 * @exception DataAccessException,RuntimeException
	 */
	public Object findById(Object modelId)throws Exception;
	
	
	public Object findById(Object modelId,Class model) throws Exception;
	
	/**
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void save(Object model) throws Exception;
	
	/**
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void update(Object model) throws Exception;
	
	/**
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void saveOrUpdate(Object model) throws Exception;
	/**
	 * @param modelList
	 * @exception DataAccessException,RuntimeException
	 */
	public void saveOrUpdateAll(List modelList) throws Exception;
	
	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public List findByParams(String queryStr,String[] params,Object[] values) throws Exception;
	
	/**
	 * 
	 * @param sqlStr
	 * @return
	 * @throws Exception
	 */
	public List findByHql(String sqlStr) throws Exception;
	
	
	/**
	 * 
	 * @param pageSize
	 * @param startRow
	 * @return
	 * @throws Exception
	 */
	public List findByPage(final int pageSize, final int startRow) throws Exception; 

	public List findByPage(final int pageSize, final int startRow,Class model) throws Exception;
	//执行标准的Sql
	public Iterator findBySql(String strSql) throws Exception;
	
	public ResultSet executeQuery(String strSql);
	
	public boolean executeStatement(final String strSql)throws DataAccessException,RuntimeException,Exception;
	
	
	public List findByPage(final int pageSize, final int startRow,final String hql) throws DataAccessException,RuntimeException,HibernateException,Exception ;

	public int getRowsCount_Ex(String strSql) throws DataAccessException,RuntimeException,Exception;
}
