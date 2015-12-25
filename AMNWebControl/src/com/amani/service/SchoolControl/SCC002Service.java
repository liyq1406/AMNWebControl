package com.amani.service.SchoolControl;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.model.SchoolCredit;
import com.amani.model.SchoolInfo;
import com.amani.model.SchoolScore;
import com.amani.model.SchoolScorePK;
import com.amani.service.AMN_ModuleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 课程学分设定
 */
@Service
public class SCC002Service extends AMN_ModuleService{
	
	@SuppressWarnings("unchecked")
	public List<SchoolInfo> initDataSet() {
		return this.amn_Dao.findByHql("from SchoolInfo where state=1");
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolCredit> loadDataSet() throws Exception {
		return this.amn_Dao.findByHql("from SchoolCredit where state=1");
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolCredit> queryDataSet(String name) throws Exception {
		this.amn_Dao.setModel(SchoolCredit.class);
		String queryStr = "from SchoolCredit a where a.state=1 and (a.no=:no or a.name like :name)";
		String[] params = new String[]{"no", "name"};
		Object[] values = new Object[]{name, "%"+name+"%"};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolScore> getPostionScore(String credit_no) {
		this.amn_Dao.setModel(SchoolScore.class);
		String queryStr = "from SchoolScore a where a.pk.credit_no=:credit_no";
		String[] params = new String[]{"credit_no"};
		Object[] values = new Object[]{credit_no};
		return this.amn_Dao.findByParams(queryStr, params, values);
	}
	
	/**
	 * 保存学校信息
	 * @param schoolInfo
	 * @return
	 */
	public int save(SchoolCredit schoolcredit){
		if(schoolcredit != null){
			Gson gson = new Gson();
			//抽取课程数据
			String credit_no = "";
			String creditJson = StringUtils.substringBetween(schoolcredit.getNo(), "[", "]");
			if(StringUtils.isNotBlank(creditJson)){
				SchoolCredit credit = gson.fromJson(creditJson, new TypeToken<SchoolCredit>(){}.getType());
				if(StringUtils.isNotBlank(credit.getNo())){
					this.amn_Dao.update(credit);
				}else{
					String no = ObjectUtils.toString(this.amn_Dao.saveByKey(credit));
					credit_no = no.length()>1 ? ("0"+no) : ("00"+no); 
				}
			}
			//抽取分数数据
			if(!StringUtils.equals("[]", schoolcredit.getName())){
				String[] search = new String[]{"\"credit_no", ",\"score"};
				String[] replacement = new String[]{"pk:{\"credit_no", "},\"score"};
				String scoreJson = StringUtils.replaceEach(schoolcredit.getName(), search, replacement);
				List<SchoolScore> score = gson.fromJson(scoreJson, new TypeToken<List<SchoolScore>>(){}.getType());
				for (SchoolScore schoolScore : score) {
					SchoolScorePK pk = schoolScore.getPk();
					if(StringUtils.equals("0", pk.getCredit_no())){
						pk.setCredit_no(credit_no);
					}
					this.amn_Dao.saveOrUpdate(schoolScore);
				}
			}
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