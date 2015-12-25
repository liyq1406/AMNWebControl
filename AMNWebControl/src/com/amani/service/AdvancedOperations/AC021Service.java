package com.amani.service.AdvancedOperations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.amani.model.StaffHairdesignerinfo;
import com.amani.model.Staffinfo;
import com.amani.service.AMN_ModuleService;
@Service
public class AC021Service extends AMN_ModuleService{
	@Override
	protected boolean deleteDetail(Object curMaster) {
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}

	@Override
	protected boolean postDetail(Object details) {
		
		return true;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		this.amn_Dao.update(curMaster);
		curMaster=null;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<StaffHairdesignerinfo> loadImages(String strStaffNo,String auditState)
	{
		StringBuffer sqlt = new StringBuffer();
		sqlt.append(" select sho.id,sho.name,sho.staffopenid,sho.imageurl,");
		sqlt.append(" sho.content,sho.auditstate,sho.staffname,sho.staffno from StaffHairdesignerinfo ");
		sqlt.append(" sho where 1=1");
		sqlt.append(" and sho.auditstate = '" + auditState + "'");
		
		try {
		if(StringUtils.isNotBlank(strStaffNo)){
			StringBuffer sql = new StringBuffer();
			sql.append(" from Staffinfo where staffno = '" + strStaffNo + "'");
			Staffinfo so;
			List<Staffinfo> lsf = (List<Staffinfo>)this.amn_Dao.findByHql(sql.toString());
			so = lsf.get(0);
				
			sqlt.append(" and sho.manageno = '" + so.getManageno() + "'");
		}
		
		List<Object[]> res = (List<Object[]>) this.amn_Dao.findByHql(sqlt.toString());
		List<StaffHairdesignerinfo> ls = new ArrayList<StaffHairdesignerinfo>();
		
		for(Object[] o : res ){
			StaffHairdesignerinfo sh = new StaffHairdesignerinfo();
			sh.setId(Integer.valueOf(o[0].toString()));
			sh.setName(String.valueOf(o[1]));
			sh.setStaffopenid(String.valueOf(o[2]));
			sh.setImageurl(String.valueOf(o[3].toString()));
			sh.setContent(String.valueOf(o[4]));
			sh.setAuditstate(String.valueOf(o[5]));
			sh.setStaffname(String.valueOf(o[6]));
			sh.setStaffno(String.valueOf(o[7]));
			ls.add(sh);
		}
		
			return ls;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			return null;
		}
		
	}
	
	public boolean updateSelect(String strId, String auditstate){
		
		StringBuffer sql = new StringBuffer();
		sql.append(" update StaffHairdesignerinfo set auditstate= '" + auditstate + "'");
		sql.append(" where id in ("+strId.substring(0, strId.length()-1)+")");
		return  amn_Dao.executeSql(sql.toString());
	}
}
