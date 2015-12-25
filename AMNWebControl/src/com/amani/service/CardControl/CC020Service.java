package com.amani.service.CardControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.CommonBean;
import com.amani.service.AMN_ModuleService;

/**
 * 第三方支付
 */
@Service
public class CC020Service extends AMN_ModuleService{
	public List<CommonBean> querySet(String strCurCompId, String beginDate, String endDate) throws Exception {
		String sql="exec upg_compute_other_payinfo '"+strCurCompId+"','"+beginDate+"','"+endDate+"' ";
		AnlyResultSet<List<CommonBean>> analysis = new AnlyResultSet<List<CommonBean>>() {
			public List<CommonBean> anlyResultSet(ResultSet rs) {
				List<CommonBean> returnValue = new ArrayList<CommonBean>();
				CommonBean record=null;
				try {
					while (rs != null && rs.next()) {
						record=new CommonBean();
						record.setAttr1(ObjectUtils.toString(rs.getString("cscompid")));	//cscompid 公司编号
						record.setAttr2(ObjectUtils.toString(rs.getString("compname")));  	//compname 公司名称
						record.setAttr3(ObjectUtils.toString(rs.getString("dateRegion")));  	//dateRegion 日期范围
						record.setAttr4(ObjectUtils.toString(rs.getDouble("zfb_je")));  	//zfb_je 支付宝总额
						record.setAttr5(ObjectUtils.toString(rs.getDouble("wx_je")));  	//wx_je 微信总额
						record.setAttr6(ObjectUtils.toString(rs.getDouble("okk_je")));  	//okk_je OK卡总额
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
