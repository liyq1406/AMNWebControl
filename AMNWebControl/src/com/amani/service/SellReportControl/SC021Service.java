package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResult2Set;
import com.amani.bean.CommonNBBean;
import com.amani.service.AMN_ModuleService;
/**
 * NB悦碧诗数据
 */
@Service
public class SC021Service extends AMN_ModuleService{
	public CommonNBBean querySet(String strCurCompId, String beginDate, String endDate) throws Exception {
		String sql="exec upg_report_NBYBS '"+strCurCompId+"','"+beginDate+"','"+endDate+"' ";
		AnlyResult2Set<CommonNBBean> analysis = new AnlyResult2Set<CommonNBBean>() {
			public CommonNBBean anlyResult2Set(Map<String,String> hashMap,ResultSet rs2) {
				CommonNBBean returnValue = new CommonNBBean();
				List<Map<String,String>> projectDatas = new ArrayList<Map<String,String>>();//报表数据集
				List<String> columnNams = new ArrayList<String>();//获取数据列名集合
				Map<String,String> projectColumnNams = new HashMap<String,String>();//动态列名集合
				Map<String,String> allProjectColumnNams = new HashMap<String,String>();//动态列名集合
				String columnNames = "";
				String allcolumnNames = "";
				returnValue.setProjectName(hashMap);
				try {
					if(rs2 != null){
						 ResultSetMetaData metaData = rs2.getMetaData();  
						 for (int i = 0; i < metaData.getColumnCount(); i++) {  
							           // resultSet数据下标从1开始  
						  String columnName = metaData.getColumnName(i + 1); 
						  columnNams.add(columnName);
						  if(i==0){
							  allcolumnNames = columnName;
						  }else{
							  allcolumnNames = allcolumnNames+","+columnName;
						  }
						} 
						 allProjectColumnNams.put("allcolumnNames", allcolumnNames);
						 returnValue.setAllProjectColumnsNames(allProjectColumnNams);
						 for (int j = 6; j < metaData.getColumnCount(); j++) {  
					           // resultSet数据下标从1开始  
							  String kdlName = metaData.getColumnName(j + 1); 
							  String sylName = metaData.getColumnName(j + 2);
							  String kdlAndsyjNames = kdlName+","+sylName;
							  if(j==6){
								  columnNames = kdlAndsyjNames;
							  }else{
								  columnNames = columnNames+"-_-"+kdlAndsyjNames;
							  }
							  j++;
						 }
						 projectColumnNams.put("projectColumns", columnNames);
						 returnValue.setProjectColumnsNames(projectColumnNams);
					}
					while (rs2 != null && rs2.next()) {
						Map<String,String> datamap = new HashMap<String,String>();
						if(columnNams.size()>0){
							for(int j=0;j<columnNams.size();j++){
								String columnName = columnNams.get(j);
								String columndata = rs2.getString(columnName);
								datamap.put(columnName, columndata);
							}
						}
						projectDatas.add(datamap);
					}
					returnValue.setProjectDataList(projectDatas);
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		@SuppressWarnings("unchecked")
		CommonNBBean nbData = (CommonNBBean)this.amn_Dao.executeQuery_ex2(sql, analysis);
		analysis=null;
		return nbData;
	}
	@Override
	protected boolean deleteDetail(Object curMaster) {
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postDetail(Object details) {
		return false;
	}

	@Override
	public List<?> loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}
}
