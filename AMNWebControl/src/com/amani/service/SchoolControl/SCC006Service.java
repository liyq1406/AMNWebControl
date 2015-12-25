package com.amani.service.SchoolControl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.model.SchoolIndex;
import com.amani.service.AMN_ModuleService;

/**
 * 学分指标设定
 */
@Service
public class SCC006Service extends AMN_ModuleService{

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

	public void save(SchoolIndex aSchoolIndex) {
		amn_Dao.saveOrUpdate(aSchoolIndex);
	}

	public List<SchoolIndex> load(SchoolIndex aSchoolIndex) {
		amn_Dao.setModel(SchoolIndex.class);
//		String[] params = new String[]{"postion_no", "time","type"};
//		Object[] values = new Object[]{aSchoolIndex.getPostion_no(), aSchoolIndex.getTime(),aSchoolIndex.getType()};
		String hql = "from SchoolIndex where postion_no = '"+aSchoolIndex.getPostion_no()+"'" +
				" and time = '"+aSchoolIndex.getTime()+"'" +
				" and type = '"+aSchoolIndex.getType()+"'";
		return amn_Dao.findByHql(hql);
	}

}