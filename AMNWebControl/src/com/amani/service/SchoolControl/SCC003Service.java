package com.amani.service.SchoolControl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.amani.model.SchoolCredit;
import com.amani.model.SchoolLowestScore;
import com.amani.model.SchoolcreditPostion;
import com.amani.service.AMN_ModuleService;
import com.google.gson.Gson;

/**
 * 职位选修课必修课设定
 */
@Service
public class SCC003Service extends AMN_ModuleService{

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

	@SuppressWarnings("unchecked")
	public List<SchoolCredit> loadDataSet() {
		return this.amn_Dao.findByHql("from SchoolCredit where state=1");
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolcreditPostion> loadPostionSchoolcredit(int type,String postion) throws DataAccessException,RuntimeException {
		String queryStr = "from SchoolcreditPostion where type="+type+" and postion ='"+postion+"'";
		return this.amn_Dao.findByHql(queryStr);
	}

	public String save(String credit_no, List listSet, List listSet2,String listScroe) {
		String res = "true";
		try{
			amn_Dao.deleteBySql("delete from SchoolcreditPostion where postion = '" + credit_no+"'");
			if(listSet!=null&&listSet.size()>0){
				String credits[] = listSet.get(0).toString().split(",");
				for(String credit : credits){
					if(credit.length()>0){
						SchoolcreditPostion aPostionSchoolcredit = new SchoolcreditPostion(credit_no,credit,1);
						amn_Dao.save(aPostionSchoolcredit);
					}
				}
			}
			if(listSet2!=null&&listSet2.size()>0){
				String credits[] = listSet2.get(0).toString().split(",");
				for(String credit : credits){
					if(credit.length()>0){
						SchoolcreditPostion aPostionSchoolcredit = new SchoolcreditPostion(credit_no,credit,2);
						amn_Dao.save(aPostionSchoolcredit);
					}
				}
			}
			amn_Dao.deleteBySql("delete from SchoolLowestScore where postion_no='"+credit_no+"'");
			Gson gson = new Gson();
			List scoreList = gson.fromJson(listScroe, List.class);
			for(Object t :scoreList){
				Map map = gson.fromJson(t.toString(), Map.class);
				SchoolLowestScore aSchoolLowestScore = new SchoolLowestScore(credit_no,map.get("syear"),map.get("score"));
				amn_Dao.saveOrUpdate(aSchoolLowestScore);
			}
		}catch(Exception e){
			e.printStackTrace();
			res = "false";
		}
		return res;
	}

	public List<SchoolLowestScore> loadScore(String credit_no) throws DataAccessException, Exception {
		return amn_Dao.findByHql("from SchoolLowestScore where state = 1 and postion_no='"+credit_no+"'");
	}
}