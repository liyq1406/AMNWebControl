package com.amani.service.SchoolControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.SchoolEmployee;
import com.amani.model.SchoolUnionPK;
import com.amani.model.Staffinfo;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

/**
 * 课程学分输入
 */
@Service
public class SCC004Service extends AMN_ModuleService{
	
	@SuppressWarnings("unchecked")
	public List<SchoolEmployee> loadBillSet(String salecompid) throws Exception {
		String sql="select distinct salebillid, salecompid from schoolemployee where operationer='"+ CommonTool.getLoginInfo("USERID") 
					+"' and CONVERT(varchar(100), operationdate, 23)=CONVERT(varchar(100), GETDATE(), 23)";
		AnlyResultSet<List<SchoolEmployee>> analysis = new AnlyResultSet<List<SchoolEmployee>>() {
			public List<SchoolEmployee> anlyResultSet(ResultSet rs) {
				List<SchoolEmployee> returnValue = new ArrayList<SchoolEmployee>();
				SchoolEmployee record=null;
				try {
					while (rs != null && rs.next() == true) {
						record=new SchoolEmployee();
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
		List<SchoolEmployee> list= (List<SchoolEmployee>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolEmployee> loadDataSet(String salecompid, String salebillid) throws Exception {
		String sql="select a.salecompid, a.salebillid, a.staffno, a.credit, a.ispass, a.remark, b.staffname, "+
				"b.position, b.pccid, b.mobilephone from schoolemployee a left join staffinfo b on a.staffno=b.staffno "+
				"where a.state=1 and a.salecompid='"+ salecompid +"' and a.salebillid='"+ salebillid +"'";
			AnlyResultSet<List<SchoolEmployee>> analysis = new AnlyResultSet<List<SchoolEmployee>>() {
			public List<SchoolEmployee> anlyResultSet(ResultSet rs) {
				List<SchoolEmployee> returnValue = new ArrayList<SchoolEmployee>();
				SchoolEmployee record=null;
				try {
					while (rs != null && rs.next() == true) {
						record=new SchoolEmployee();
						record.setSalecompid(CommonTool.FormatString(rs.getString("salecompid")));
						record.setSalebillid(CommonTool.FormatString(rs.getString("salebillid")));
						record.setStaffno(CommonTool.FormatString(rs.getString("staffno")));
						record.setCredit(CommonTool.FormatString(rs.getString("credit")));
						record.setIspass(rs.getInt("ispass"));
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
		List<SchoolEmployee> list= (List<SchoolEmployee>) this.amn_Dao.executeQuery_ex(sql, analysis);
		analysis=null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Staffinfo> searchUser(String staffno) throws Exception {
		return this.amn_Dao.findByHql("from Staffinfo a where a.id.staffno='"+ staffno +"'");
	} 
	
	public SchoolEmployee addBillRecord(String strCompId){
		SchoolEmployee record=new SchoolEmployee();
		SchoolUnionPK pk = new SchoolUnionPK();
		pk.setSalecompid(strCompId);
		record.setSalecompid(strCompId);
		String salebillid = this.dataTool.loadBillIdByRule(strCompId,"schoolemployee", "salebillid", "SP008");
		pk.setSalebillid(salebillid);
		record.setSalebillid(salebillid);
		return record;
	 }
	
	public int save(List<SchoolEmployee> list){
		if(list != null && list.size()>0){
			String operationer = CommonTool.getLoginInfo("USERID");
			for (SchoolEmployee schoolEmployee : list) {
				schoolEmployee.setOperationer(operationer);
				this.amn_Dao.save(schoolEmployee);
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