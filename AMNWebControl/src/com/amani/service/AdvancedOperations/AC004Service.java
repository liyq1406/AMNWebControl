package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;

import com.amani.model.Storeconfirmflow;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class AC004Service  extends AMN_ModuleService{
	@Autowired
	private DESPlus desPlus;
	public DESPlus getDesPlus() {
		return desPlus;
	}

	public void setDesPlus(DESPlus desPlus) {
		this.desPlus = desPlus;
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Storeconfirmflow> loadMaster()
	{
		String strSql = "select appitemno,checkcommissioner,checkmanager,checkcommissionertext,checkmanagertext from storeconfirmflow  ";
		AnlyResultSet<List<Storeconfirmflow>> analysis = new AnlyResultSet<List<Storeconfirmflow> >() {
			public List<Storeconfirmflow> anlyResultSet(ResultSet rs) {
				List<Storeconfirmflow> returnValue = new ArrayList();
				Storeconfirmflow bean=null;
				try {
						while (rs != null && rs.next() == true) 
						{
							bean=new Storeconfirmflow();
							bean.setAppitemno(CommonTool.FormatString(rs.getString("appitemno")));
							bean.setCheckcommissioner(CommonTool.FormatString(rs.getString("checkcommissioner")));
							bean.setCheckmanager(CommonTool.FormatString(rs.getString("checkmanager")));
							bean.setCheckcommissionertext(CommonTool.FormatString(rs.getString("checkcommissionertext")));
							bean.setCheckmanagertext(CommonTool.FormatString(rs.getString("checkmanagertext")));
							returnValue.add(bean);
						} 
					} catch (Exception e) {
						e.printStackTrace();
					}
					return returnValue;
			}
		};
		List<Storeconfirmflow> ls= (List<Storeconfirmflow>) this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;
	}

	
	public boolean postMasterInfo(String appitemno,String checkcommissioner,String checkmanager,String checkcommissionertext,String checkmanagertext)
	{
		try
		{
			String strSql=" update storeconfirmflow set checkcommissioner='"+checkcommissioner+"' ,checkmanager='"+checkmanager+"',checkcommissionertext='"+checkcommissionertext+"' ,checkmanagertext='"+checkmanagertext+"' where appitemno='"+appitemno+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
}
