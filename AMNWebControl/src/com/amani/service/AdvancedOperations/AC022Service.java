package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.model.Projectinfo;
import com.amani.service.AMN_ModuleService;
@Service
public class AC022Service extends AMN_ModuleService{
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
	
	/**
	 * 获取项目
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Projectinfo> loadProjectinfo(){
		StringBuffer sql= new StringBuffer();
		sql.append(" SELECT projectinfo FROM Projectinfo projectinfo ");
//		sql.append(" SELECT projectinfo FROM Projectinfo projectinfo WHERE projectinfo.USEFLAG ='1' ");
//		sql.append(" AND projectinfo.PRJSALETYPE='1'");
		return this.amn_Dao.findByHql(sql.toString());
	}
	
	/**
	 * 判断微信券是否存在
	 * @param cardId
	 * @param cardAmt
	 * @param lsDnointernalcardinfo
	 * @return
	 */
	public boolean validateCard(String cardId, String type, StringBuffer prono){
		ResultSet rs=null;
		StringBuffer sql= new StringBuffer();
		try {
			if("0".equals(type)){
				sql.append("SELECT CARDID FROM WXCARDCONFIG WHERE CARDID='"+cardId+"' AND TYPE='0'");
			}else{
				sql.append("SELECT WG.CARDID FROM WXCARDCONFIG WG,WXCARDCONFIGDETAIL WL WHERE WG.CARDID");
				sql.append(" =WL.CARDID AND WG.TYPE ='1' AND WL.PRONO in ("+prono+")");
				sql.append(" and wg.cardid='"+cardId+"'");
			}
			rs=amn_Dao.executeQuery(sql.toString());
			if(rs.next()){
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			logger.error("AC022Service.java validateCard  error:" + e);
			return false;
		}finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (Exception e) {
					return false;
				}
			}
		}
	}
	
	/**
	 * 增加微信卡券
	 * @param cardId
	 * @param type
	 * @param prono
	 * @param cardAmt
	 * @return
	 */
	public boolean addCard(String cardId, String type, StringBuffer prono, String cardAmt){
		ResultSet rs=null;
		StringBuffer sql= new StringBuffer();
		try {
			if("0".equals(type)){
				sql.append("insert into WXCARDCONFIG (cardId,type,money) values('"+cardId+"','0','"+cardAmt+"')");
				rs=amn_Dao.executeQuery(sql.toString());
			}else{
				sql.append("select cardId from wxcardconfig where cardId='"+cardId+"' and type='1'");
				rs=amn_Dao.executeQuery(sql.toString());
				sql.setLength(0);
				if(rs.next()==false){
					sql.append("insert into WXCARDCONFIG (cardId,type,money) values('"+cardId+"','1','"+cardAmt+"');");
					rs=amn_Dao.executeQuery(sql.toString());
					sql.setLength(0);
				}
				for(String pro : (prono.toString().split(","))){
					sql.append("insert into wxcardconfigdetail(cardId,prono) values('"+cardId+"',"+pro+")");
					rs=amn_Dao.executeQuery(sql.toString());
					sql.setLength(0);
				}
			}
		} catch (SQLException e) {
			logger.error("AC022Service.java addCard  error:" + e);
			return false;
		}finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}
}
