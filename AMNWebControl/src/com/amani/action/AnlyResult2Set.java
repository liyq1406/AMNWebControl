package com.amani.action;

import java.sql.ResultSet;
import java.util.Map;

/**
 * 
 *
 * @version: 1.0
 * @Copyright: AMN
 * @param <T>
 */
public interface AnlyResult2Set<T> {
	public T anlyResult2Set(Map<String,String> hashMap,ResultSet set2);
}
