package com.amani.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.amani.action.AnlyResultSet;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.sun.rowset.CachedRowSetImpl;
/**
 * 
 * @author LiuJie Jul 1, 2013 3:23:21 PM
 * @version: 1.0
 * @Copyright: AMN
 */

//@Repository("amn_PADDao") 
@SuppressWarnings("unchecked")
public class AMN_JDBCDaoImp extends HibernateDaoSupport implements AMN_JDBCDao
{
	@SuppressWarnings("unused")
	//@Resource(name="sessionFactory_PAD")
    //private SessionFactory sessionFactory;

	private static Logger logger = Logger.getLogger(AMN_JDBCDaoImp.class.getName());
	
	private Class model;

	public Class getModel() {
		return model;
	}

	public void setModel(Class model) {
		this.model = model;
	}
	
	public AMN_JDBCDaoImp(){
	}
	
	public AMN_JDBCDaoImp( Class model ){
		this.model = model;
	}

	/**
	 * delete an persistent object mapping a record in database
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void delete(Object model) throws DataAccessException,RuntimeException,Exception
	{
		this.getHibernateTemplate().delete(model);
	}
	
	/**
	 * delete an persistent object by object id 
	 * @param modelId
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteById(Object modelId) throws Exception,DataAccessException,RuntimeException 
	{
		if(this.model==null)
		{
			return ;
		}
		this.getHibernateTemplate().delete(this.getHibernateTemplate().get(this.model, (Serializable)modelId));
	}
	/**
	 * @param modelId
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteById(Object modelId,Class model) throws Exception,DataAccessException,RuntimeException 
	{
		if(this.model==null)
		{
			return ;
		}
		this.getHibernateTemplate().delete(this.getHibernateTemplate().get(model, (Serializable)modelId));
	}
	/**
	 * delete object collection
	 * @param modelList
	 * @exception DataAccessException,RuntimeException
	 */
	public void deleteAll(List modelList)throws DataAccessException,RuntimeException,Exception
	{
		this.getHibernateTemplate().deleteAll(modelList);
	}
	/**
	 * delete data by sql 
	 * @param sqlStr
	 * @exception RuntimeException,DataAccessException
	 */
	public boolean deleteBySql(final String sqlStr)throws DataAccessException,RuntimeException,Exception
	{
		return (Boolean)this.getHibernateTemplate().execute(new HibernateCallback(){
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				Connection conn = session.connection();
				PreparedStatement statement = null;
				try{
					statement = conn.prepareStatement(sqlStr);
					statement.execute();
					if(!conn.getAutoCommit())
					{
						conn.commit();
					}
					return true;
				} 
				catch(Exception e)
				{			
					if(!conn.getAutoCommit())
					{
						conn.rollback();
					}
					String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"deleteBySql",sqlStr,e.getMessage());
					logger.error(errorMsg);
					//e.printStackTrace();
					return false;
				}
				finally
				{
					try{
						statement.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"deleteBySql",sqlStr,e.getMessage());
						logger.error(errorMsg);
						return false;
					}
				}
				
			}});
		
	}
	
	/**
	 * query data
	 * @return
	 * @exception DataAccessException,RuntimeException
	 */
	public List findAll() throws DataAccessException,RuntimeException,Exception
	{
		if(this.model==null)
		{
			return null;
		}
		return this.getHibernateTemplate().find("from " + this.model.getName());
	}
	
	public List findAll(Class model) throws DataAccessException,Exception{
		if(model==null)
		{
			return null;
		}
		return this.getHibernateTemplate().find("from " + model.getName());
	}

	/**
	 * Query data by object id
	 * @param modelId
	 * @return object
	 * @exception DataAccessException,RuntimeException
	 */
	public Object findById(Object modelId)
	{
		if(this.model==null)
		{
			return null;
		}
		return this.getHibernateTemplate().get( this.model , (Serializable)modelId);

	}
	
	public Object findById(Object modelId,Class model)
	{
		if(model==null)
		{
			return null;
		}
		return this.getHibernateTemplate().get( model , (Serializable)modelId);

	}
	
	/**
	 * Save an persistent object
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void  save(Object model) throws DataAccessException,RuntimeException
	{
		 this.getHibernateTemplate().save(model);
	}
	
	/**
	 * Update an persistent object
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void update(Object model) throws DataAccessException,RuntimeException
	{
		this.getHibernateTemplate().update(model);
	}
	
	/**
	 * Save or update an persistent object
	 * @param model
	 * @exception DataAccessException,RuntimeException
	 */
	public void saveOrUpdate(Object model) throws DataAccessException,RuntimeException
	{
		this.getHibernateTemplate().saveOrUpdate(model);
	}
	
	/**
	 * Save or update persistent object collection
	 * @param modelList
	 * @exception DataAccessException,RuntimeException
	 */
	public void saveOrUpdateAll(List modelList) throws DataAccessException,RuntimeException
	{
		this.getHibernateTemplate().saveOrUpdateAll(modelList);
	}
	
	/**
	 * Query data by query condition  
	 * @param params-- query fields
	 * @param values-- query values
	 * @exception DataAccessException,RuntimeException
	 */
	public List findByParams(String queryStr,String[] params,Object[] values) throws DataAccessException,RuntimeException
	{
		if(this.model==null)
		{
			return null;
		}
		return this.getHibernateTemplate().findByNamedParam(queryStr, params, values);
	}
	
	/**
	 * Query data by hql string
	 * @param hqlStr
	 * @return ���󼯺�
	 * @exception DataAccessException,RuntimeException
	 */
	public List findByHql(String hqlStr) throws DataAccessException,RuntimeException
	{
		return this.getHibernateTemplate().find(hqlStr); 
	}
	
	/**
	 * Query a page data
	 * @param int pageSize--page size
	 * @param int startRow-- start row's index in query page
	 * @return
	 * @exception DataAccessException,RuntimeException,HibernateException
	 */
	public List findByPage(final int pageSize, final int startRow) throws DataAccessException,RuntimeException,HibernateException 
	{
		final String tab = this.model.getName();
		return (List)this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List doInHibernate(Session session)
				throws HibernateException {
					Query queryObject = session.createQuery("from "+tab);
					queryObject.setFirstResult(startRow);
					queryObject.setMaxResults(pageSize);
					return queryObject.list();
				}
			});

	}
	
	public List findByPage(final int pageSize, final int startRow,Class model) throws DataAccessException,RuntimeException,HibernateException 
	{
		final String tab = model.getName();
		return (List)this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List doInHibernate(Session session)
				throws HibernateException {
					Query queryObject = session.createQuery("from "+tab);
					queryObject.setFirstResult(startRow);
					queryObject.setMaxResults(pageSize);
					return queryObject.list();
				}
			});

	}
	//执行标准的Sql//不用
	public Iterator findBySql(String strSql)
	{
		return null;
	}
	//注意这个函数是自己管理事务的，所以不可以包涵在另外一个事务中
	public boolean executeSql(final String strSql)
	{
		return (Boolean)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				Transaction tran = session.beginTransaction();
				Connection conn = session.connection();
				PreparedStatement statement = null;
				try{
					statement = conn.prepareStatement(strSql);					
					statement.execute();
					tran.commit();
					return true;
					
				} 
				catch(Exception e)
				{
					//e.printStackTrace();
					tran.rollback();
					String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeSql",strSql,e.getMessage());
					logger.error(errorMsg);
					return false;
				}
				finally
				{
					
					try
					{
						statement.close();
					}
					catch(SQLException ex)
					{
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeSql",strSql,ex.getMessage());
						logger.error(errorMsg);
					}
				}
			}});		
	}
//	注意这个函数是自己管理事务的，所以不可以包涵在另外一个事务中
	public ResultSet executeSqlNoTran(final String strSql)
	{
		return (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{

				CachedRowSet retRs = new CachedRowSetImpl();
				Transaction tran = session.beginTransaction();
				Connection conn = session.connection();
				PreparedStatement statement = null;
				ResultSet rs =null;
				try{
					statement = conn.prepareStatement(strSql);
					//tran.commit();
					rs = statement.executeQuery();
					tran.commit();
					retRs.populate(rs);
					
					return retRs;
				} 
				catch(Exception e)
				{
					//e.printStackTrace();
					tran.rollback();
					String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeSqlNoTran",strSql,e.getMessage());
					logger.error(errorMsg);
					retRs.close();
					return null;
				}
				finally{
					try{
						statement.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeSqlNoTran",strSql,e.getMessage());
						logger.error(errorMsg);
					}
					try{
						rs.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeSqlNoTran",strSql,e.getMessage());
						logger.error(errorMsg);
					}
					
				}
			}});		
	}
	//------------
	//注意这个函数是没有管理事务，所有要被保护在一个事务中
	public boolean executeStatement(final String strSql)throws DataAccessException,RuntimeException,Exception
	{
		return (Boolean)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				Connection conn = session.connection();
				PreparedStatement statement = null;
				try{
					statement = conn.prepareStatement(strSql);	
					statement.execute();
					
					if(!conn.getAutoCommit()){
						conn.commit();
					}
					
					return true;
					
				} 
				catch(Exception e)
				{
					String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeStatement",strSql,e.getMessage());
					logger.error(errorMsg);
					
					if(!conn.getAutoCommit())
					{
						conn.rollback();
					}
					
					return false;
				}
				finally{
					try{
						statement.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeStatement",strSql,e.getMessage());
						logger.error(errorMsg);
					}
				}
			}});		
	}	
	//执行标准SQL语句返回是是Result注意这个也是要是被包含在一个事物中的
	public ResultSet executeQuery( final String strSql)
	{
		
		return (ResultSet)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				CachedRowSet retRs = new CachedRowSetImpl();
				Connection conn = session.connection();
				
				PreparedStatement statement = null;
				
				ResultSet rs = null;
				try{
					statement = conn.prepareStatement(strSql);		
					
					rs = statement.executeQuery();
					if(!conn.getAutoCommit())
					{
						conn.commit();
					}
					retRs.populate(rs);
					//rs.close();
					return retRs;
				} 
				catch(Exception e)
				{
					String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeQuery",strSql,e.getMessage());
					logger.error(errorMsg);
					if(!conn.getAutoCommit())
					{
						conn.rollback();
					}
					retRs.close();			
					return null;
				}
				finally{
					try{
						statement.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeQuery",strSql,e.getMessage());
						logger.error(errorMsg);
					}
					try{
						rs.close();
					}catch(Exception e){
						String errorMsg = CommonTool.genExceptionLogMsg(SystemFinal.EXCEP_FROM_DAO,"executeQuery",strSql,e.getMessage());
						logger.error(errorMsg);
					}
				}
			}});	
		
	}
	
	
//	注意这个函数是自己管理事务的，所以不可以包涵在另外一个事务中
	public Object executeSqlNoTran_ex(final String strSql,final AnlyResultSet analysis)
	{
		return (Object)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				Transaction tran = session.beginTransaction();
				Connection conn = session.connection();
				PreparedStatement statement = null;
				ResultSet rs =null;
				try{
					statement = conn.prepareStatement(strSql);
					tran.commit();
					rs = statement.executeQuery();
					return analysis.anlyResultSet(rs);
				} 
				catch(Exception e)
				{
					e.printStackTrace();
					return null;
				}
				finally{
					try{
					    rs.close();	
					    statement.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}});
	}
	//执行标准SQL语句返回是是Result注意这个也是要是被包含在一个事物中的
	public Object executeQuery_ex(final String strSql,final AnlyResultSet analysis)
	{
		return (Object)this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException
			{
				Connection conn = session.connection();
				PreparedStatement statement = null;
				ResultSet rs = null;
				try{
					statement = conn.prepareStatement(strSql);
					rs = statement.executeQuery();
					return analysis.anlyResultSet(rs);
				} 
				catch(Exception e)
				{
					e.printStackTrace();
					conn.rollback();
					return null;
				}
				finally{
					try
					{
						rs.close();
						statement.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}});	
		
	}
	
	/**
	 * 获取按条件查询出的数据记录条数
	 * @return 记录条数
	 * @throws DataAccessException
	 * @throws RuntimeException
	 */
	//delete by lyj 这个计算总行数是效率很低而且没有考虑公司
	/*
	public int getRowsCount(String hql) throws DataAccessException,RuntimeException,Exception
	{
		return this.getHibernateTemplate().find(hql).size();		
	}
	*/
	/*按条件查询分页，返回一面数据*/
	public List findByPage(final int pageSize, final int startRow,final String hql) throws DataAccessException,RuntimeException,HibernateException,Exception 
	{
		//final String tab = this.model.getName();
		return (List)this.getHibernateTemplate().executeFind(new HibernateCallback()
		{
			public List doInHibernate(Session session)
				throws HibernateException {
					Query queryObject = session.createQuery(hql);
					queryObject.setFirstResult(startRow);
					queryObject.setMaxResults(pageSize);
					return queryObject.list();
				}
			});

	}
	//获得当前的总行数
	//需要传递的strSql 是标准的Sql 

	public int getRowsCount_Ex(String strSql){
		AnlyResultSet<Integer> analysis = new AnlyResultSet<Integer>()
		{
			public Integer anlyResultSet(ResultSet rs)
			{
				int returnValue = 0;
				try
				{
				if(rs != null && rs.next()==true)
				{
					returnValue =  rs.getInt(1);
				}
				else
				{
					returnValue =  0;
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					returnValue =  0;
				}
				return returnValue;
			}
		};
		return (Integer)this.executeQuery_ex(strSql,analysis);
	}
	
}
