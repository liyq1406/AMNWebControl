package com.amani.service.BaseInfoControl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amani.action.AnlyResultSet;
import com.amani.model.CardchangeruleId;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Cardchangerule;
import com.amani.model.Sysuserinfo;

import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;
import com.amani.tools.DESPlus;
@Service
public class BC007Service  extends AMN_ModuleService{
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
	
	public List<Cardchangerule> loadCardchangeruleByCompId(String strCompId,String strCardType)
	{
		try
		{
			 String strModeId=this.getDataTool().loadSysParam(strCompId,"SP063");
		     String strFristModeId="";//第一级模板Id
			 String strSecondModeId="";//第2级模板ID
			 String strThirthModeId="";//第三级模板Id
			 //先定位模板门店的连锁级别(暂时支持4级连锁)
			 int compLvl=this.getDataTool().loadCompLvl(strCompId);
		     if(compLvl==2)
			 {
				strFristModeId=this.getDataTool().loadparentModeId(strModeId);
			 }
			 else if(compLvl==3)
			 {
				strSecondModeId=this.getDataTool().loadparentModeId(strModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.getDataTool().loadparentModeId(strSecondModeId);
			 }
			 else if(compLvl==4)
			 {
				strThirthModeId=this.getDataTool().loadparentModeId(strModeId);
				if(!strThirthModeId.equals(""))
					strSecondModeId=this.getDataTool().loadparentModeId(strThirthModeId);
				if(!strSecondModeId.equals(""))
					strFristModeId=this.getDataTool().loadparentModeId(strSecondModeId);
			 }
			String strSql=" select b.cardtypename,a.rulemodeid,a.cardtypeno,a.tocardtypeno,a.changeamt From  cardchangerule a,cardtypeinfo b, compchaininfo " +
					" where a.rulemodeid='"+this.dataTool.loadSysParam(strCompId,"SP063")+"' and a.cardtypeno='"+strCardType+"' " +
					" and a.tocardtypeno=b.cardtypeno and b.cardtypemodeid=a.rulemodeid and b.cardtypesource=a.cardtypesource " +
					" and b.cardtypemodeid in ('"+strModeId+"','"+strFristModeId+"','"+strSecondModeId+"','"+strThirthModeId+"') and curcomp=  b.cardtypesource and relationcomp='"+strCompId+"' ";
			AnlyResultSet<List<Cardchangerule>> analysis = new AnlyResultSet<List<Cardchangerule>>()
			{
				public List<Cardchangerule> anlyResultSet(ResultSet rs)
				{
					List<Cardchangerule> returnValue = new ArrayList();
					Cardchangerule record=null;
					try
					{
						while(rs != null && rs.next()==true)
						{
							record=new Cardchangerule();
							record.setId(new CardchangeruleId(CommonTool.FormatString(rs.getString("rulemodeid")),CommonTool.FormatString(rs.getString("cardtypeno")),0));
							record.setTocardtypeno(CommonTool.FormatString(rs.getString("tocardtypeno")));
							record.setTocardtypename(CommonTool.FormatString(rs.getString("cardtypename")));
							record.setChangeamt(new BigDecimal(CommonTool.FormatDouble(rs.getDouble("changeamt"))));
							returnValue.add(record);
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						returnValue =  null;
					}
					return returnValue;
				}
			};
			return (List<Cardchangerule>)this.amn_Dao.executeQuery_ex(strSql,analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public List<Cardtypeinfo> loadCardtypeinfoByCompId(String strCompId)
	{
		try
		{
			//String strSql=" select cardtypeinfo From Cardtypeinfo cardtypeinfo,Compchaininfo compchaininfo where cardtypemodeid='"+this.dataTool.loadSysParam(strCompId,"SP063")+"' and curcomp=  cardtypesource and relationcomp='"+strCompId+"' ";
			//return this.amn_Dao.findByHql(strSql);
			return this.dataTool.loadCardType(strCompId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}
