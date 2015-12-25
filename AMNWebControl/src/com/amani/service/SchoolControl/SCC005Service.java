package com.amani.service.SchoolControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.SchoolActinfo;
import com.amani.model.SchoolActivity;
import com.amani.model.SchoolUnionPK;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

/**
 * 涉外活动学分输入
 */
@Service
public class SCC005Service extends AMN_ModuleService{
	
	@SuppressWarnings("unchecked")
	public List<SchoolActivity> loadBillSet(String salecompid) throws Exception {
		String sql="select distinct salebillid, salecompid from schoolactivity where operationer='"+ CommonTool.getLoginInfo("USERID") 
					+"' and CONVERT(varchar(100), operationdate, 23)=CONVERT(varchar(100), GETDATE(), 23)";
		AnlyResultSet<List<SchoolActivity>> analysis = new AnlyResultSet<List<SchoolActivity>>() {
			public List<SchoolActivity> anlyResultSet(ResultSet rs) {
				List<SchoolActivity> returnValue = new ArrayList<SchoolActivity>();
				SchoolActivity record=null;
				try {
					while (rs != null && rs.next() == true) {
						record=new SchoolActivity();
						record.setSalecompid(CommonTool.FormatString(rs.getString("salecompid")));
						record.setSalebillid(CommonTool.FormatString(rs.getString("salebillid")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List<SchoolActivity> list= (List<SchoolActivity>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolActivity> loadDataSet(String salecompid, String salebillid) throws Exception {
		String sql="select a.salecompid, a.salebillid, a.staffno, a.actno, a.acttype, a.score, a.remark, b.staffname, "+
				"b.position, b.pccid, b.mobilephone from schoolactivity a left join staffinfo b on a.staffno=b.staffno "+
				"where a.state=1 and a.salecompid='"+ salecompid +"' and a.salebillid='"+ salebillid +"'";
			AnlyResultSet<List<SchoolActivity>> analysis = new AnlyResultSet<List<SchoolActivity>>() {
			public List<SchoolActivity> anlyResultSet(ResultSet rs) {
				List<SchoolActivity> returnValue = new ArrayList<SchoolActivity>();
				SchoolActivity record=null;
				try {
					while (rs != null && rs.next() == true) {
						record=new SchoolActivity();
						record.setSalecompid(CommonTool.FormatString(rs.getString("salecompid")));
						record.setSalebillid(CommonTool.FormatString(rs.getString("salebillid")));
						record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
						record.setActno(CommonTool.FormatString(rs.getString("actno")));
						record.setActtype(rs.getInt("acttype"));
						record.setScore(rs.getInt("score"));
						record.setRemark(CommonTool.FormatString(rs.getString("remark")));
						record.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
						record.setPosition(CommonTool.FormatString(rs.getString("position")));
						record.setPccid(CommonTool.FormatString(rs.getString("pccid")));
						record.setMobilephone(CommonTool.FormatString(rs.getString("mobilephone")));
						returnValue.add(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue =null;
				}
				return returnValue;
			}
		};
		List<SchoolActivity> list= (List<SchoolActivity>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
	}
	
	public SchoolActivity addBillRecord(String strCompId){
		SchoolActivity record=new SchoolActivity();
		SchoolUnionPK pk = new SchoolUnionPK();
		pk.setSalecompid(strCompId);
		record.setSalecompid(strCompId);
		String salebillid = this.dataTool.loadBillIdByRule(strCompId,"schoolactivity", "salebillid", "SP008");
		pk.setSalebillid(salebillid);
		record.setSalebillid(salebillid);
		return record;
	 }
	
	public int save(List<SchoolActivity> list){
		if(list != null && list.size()>0){
			String operationer = CommonTool.getLoginInfo("USERID");
			for (SchoolActivity schoolActivity : list) {
				schoolActivity.setOperationer(operationer);
				this.amn_Dao.save(schoolActivity);
			}
			return 1;
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	public List<SchoolActinfo> loadDataSet() throws Exception {
		return this.amn_Dao.findByHql("from SchoolActinfo where state=1");
	}
	
	public int saveAct(List<SchoolActinfo> list){
		if(list != null && list.size()>0){
			for (SchoolActinfo schoolActinfo : list) {
				this.amn_Dao.saveOrUpdate(schoolActinfo);
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