package com.amani.service.SchoolControl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.model.SchoolInfo;
import com.amani.service.AMN_ModuleService;

/**
 * 合作学校设定
 */
@Service
public class SCC001Service extends AMN_ModuleService{
	
	@SuppressWarnings("unchecked")
	public List<SchoolInfo> loadDataSet() throws Exception {
		return this.amn_Dao.findByHql("from SchoolInfo where state=1");
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolInfo> queryDataSet(String name) throws Exception {
		this.amn_Dao.setModel(SchoolInfo.class);
		String queryStr = "from SchoolInfo where state=1 and name like :nameVal";
		String[] params = new String[]{"nameVal"};
		Object[] values = new Object[]{"%"+name+"%"};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	public int save(SchoolInfo schoolInfo){
		if(schoolInfo != null){
			this.amn_Dao.saveOrUpdate(schoolInfo);
			return 1;
		}
		return 0;
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