package com.amani.action;
import java.sql.ResultSet;
/**
 * 
 * @author LiuJie Jun 24, 2013 10:23:40 AM
 * @version: 1.0
 * @Copyright: AMN
 * @param <T>
 */
public interface AnlyResultSet<T> {
	public T anlyResultSet(ResultSet set);
}
