package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.bean.SC018Bean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;
@Service
public class SC018Service extends AMN_ReportService{
	
	public List<SC018Bean> loadDateSetByCompId(String strCompId, String strDate)
	{
		try
		{	
			String sql="exec P_XMDYYFX_HYTJB '"+ strDate +"','"+ strCompId +"'";
			AnlyResultSet<List<SC018Bean>> analysis = new AnlyResultSet<List<SC018Bean>>() {
				public List<SC018Bean> anlyResultSet(ResultSet rs) {
					List<SC018Bean> returnValue = new ArrayList<SC018Bean>();
					DecimalFormat df = new DecimalFormat("#.##");//0.00%
					SC018Bean record=null;
					try {
						while (rs != null && rs.next()) {
							record=new SC018Bean();
							record.setCompid(CommonTool.FormatString(rs.getString("compid")));
							record.setCompname(CommonTool.FormatString(rs.getString("compname")));
							record.setDqny(CommonTool.FormatString(rs.getString("dqny")));
							String hkl = ObjectUtils.toString(rs.getString("hkl"), "0");
							Double d = StringUtils.equals("-0.0", hkl) ? 0 : Double.valueOf(hkl);
							record.setHkl(df.format(d)+"%");
							record.setKdzs(CommonTool.FormatString(rs.getString("kdzs")));
							record.setXkdj(CommonTool.FormatString(rs.getString("xkdj")));
							record.setSkdj(CommonTool.FormatString(rs.getString("skdj")));
							record.setMdhy_hyzs(CommonTool.FormatString(rs.getString("mdhy_hyzs")));
							record.setMdhy_xzhy(CommonTool.FormatString(rs.getString("mdhy_xzhy")));
							record.setMdhy_cdhy(CommonTool.FormatString(rs.getString("mdhy_cdhy")));
							record.setMdhy_yxhy(CommonTool.FormatString(rs.getString("mdhy_yxhy")));
							record.setMdhy_cshy(CommonTool.FormatString(rs.getString("mdhy_cshy")));
							record.setMdhy_lshy(CommonTool.FormatString(rs.getString("mdhy_lshy")));
							record.setMr_kdzs(CommonTool.FormatString(rs.getString("mr_kdzs")));
							record.setMr_xkdj(CommonTool.FormatString(rs.getString("mr_xkdj")));
							record.setMr_skdj(CommonTool.FormatString(rs.getString("mr_skdj")));
							record.setMrhy_hyzs(CommonTool.FormatString(rs.getString("mrhy_hyzs")));
							record.setMrhy_xzhy(CommonTool.FormatString(rs.getString("mrhy_xzhy")));
							record.setMrhy_cdhy(CommonTool.FormatString(rs.getString("mrhy_cdhy")));
							record.setMrhy_yxhy(CommonTool.FormatString(rs.getString("mrhy_yxhy")));
							record.setMrhy_cshy(CommonTool.FormatString(rs.getString("mrhy_cshy")));
							record.setMrhy_lshy(CommonTool.FormatString(rs.getString("mrhy_lshy")));
							record.setMf_kdzs(CommonTool.FormatString(rs.getString("mf_kdzs")));
							record.setMf_xkdj(CommonTool.FormatString(rs.getString("mf_xkdj")));
							record.setMf_skdj(CommonTool.FormatString(rs.getString("mf_skdj")));
							record.setMfhy_hyzs(CommonTool.FormatString(rs.getString("mfhy_hyzs")));
							record.setMfhy_xzhy(CommonTool.FormatString(rs.getString("mfhy_xzhy")));
							record.setMfhy_cdhy(CommonTool.FormatString(rs.getString("mfhy_cdhy")));
							record.setMfhy_yxhy(CommonTool.FormatString(rs.getString("mfhy_yxhy")));
							record.setMfhy_cshy(CommonTool.FormatString(rs.getString("mfhy_cshy")));
							record.setMfhy_lshy(CommonTool.FormatString(rs.getString("mfhy_lshy")));
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
			List<SC018Bean> ls= (List<SC018Bean>) this.amn_Dao.executeQuery_ex(sql, analysis);
			analysis=null;
			return ls;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
