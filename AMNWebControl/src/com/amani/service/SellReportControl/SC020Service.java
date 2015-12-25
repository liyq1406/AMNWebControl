package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ModuleService;

/**
 * 客单量报表
 */
@Service
public class SC020Service extends AMN_ModuleService{
	
	public List<CommonBean> querySet(String strCurCompId, String beginDate, String endDate) throws Exception {
		String sql="exec upg_compute_billcount '"+strCurCompId+"','"+beginDate+"','"+endDate+"' ";
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("cscompid")));	//cscompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("compname")));  	//compname 公司名称
						record.setAttr3(ObjectUtils.toString(rs.getString("kdzs")));  	//kdzs 美容客单总数
						record.setAttr4(ObjectUtils.toString(rs.getString("mrkds")));  	//mrkds 美容客单总数
						record.setAttr5(ObjectUtils.toString(rs.getString("mfkds")));  	//mfkds 美发客单总数
						record.setAttr6(ObjectUtils.toString(rs.getString("tfkds")));  	//tfkds 烫发客单总数
						record.setAttr7(ObjectUtils.toString(rs.getString("rfkds")));  	//rfkds 染发客单总数
						record.setAttr8(ObjectUtils.toString(rs.getString("hlkds")));   //hlkds 护理客单总数
						record.setAttr9(ObjectUtils.toString(rs.getString("trhhj")));  	//trhhj 烫染护客单总数
						record.setAttr10(ObjectUtils.toString(rs.getString("xjkdzs")));  	//trhhj 现金客单总数
						record.setAttr11(ObjectUtils.toString(rs.getString("xjmrkds")));  	//trhhj 美容现金客单总数
						record.setAttr12(ObjectUtils.toString(rs.getString("xjmfkds")));  	//trhhj 美发现金客单总数
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		@SuppressWarnings("unchecked")
		List<CommonBean> list = (List<CommonBean>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
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