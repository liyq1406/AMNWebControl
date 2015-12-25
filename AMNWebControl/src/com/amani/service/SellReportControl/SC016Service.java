package com.amani.service.SellReportControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amani.bean.CardSaleInfo;
import com.amani.service.AMN_ReportService;

@Service
public class SC016Service extends AMN_ReportService{
	public List<CardSaleInfo> loadData(String strCompId,String strFromDate,String strToDate,String strCardClass,double fromamt,double toamt)
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append(" select cardno,membername,membermphone,amt,cardtypename ");
		buffer.append(" from(select cardno,cardtype,sum(changeamt) amt ");
		buffer.append(" from(select cardno,cardtype from cardinfo with(nolock),compchaininfo with(nolock) ");
		buffer.append(" where cardvesting=relationcomp ");
		buffer.append(" and (cardtype='"+strCardClass+"' or '"+strCardClass+"'='*' or '"+strCardClass+"'='') ");
		buffer.append(" and curcomp='"+strCompId+"') a,cardaccountchangehistory ");
		buffer.append(" where cardno=changecardno ");
		buffer.append(" and chagedate between '"+strFromDate+"' and '"+strToDate+"' ");
		buffer.append(" and changebilltype='sy' ");
		buffer.append(" and changeaccounttype in(2,4) ");
		buffer.append(" group by cardno,cardtype ");
		buffer.append(" having isnull(sum(changeamt),0) between "+fromamt+" and "+toamt+") b left join memberinfo on(memberno=cardno) left join  cardtypenameinfo on(cardtype=cardtypeno) ");
		ResultSet rs=null;
		List<CardSaleInfo> lsCardSaleInfos=new ArrayList<CardSaleInfo>();
		try {
			rs=amn_Dao.executeQuery(buffer.toString());
			if(rs!=null)
			{
				while(rs.next())
				{
					CardSaleInfo cardSaleInfo=new CardSaleInfo();
					cardSaleInfo.setStrCardNo(rs.getString("cardno"));
					cardSaleInfo.setStrCardClass(rs.getString("cardtypename"));
					cardSaleInfo.setStrCardName(rs.getString("membername"));
					cardSaleInfo.setAmt(rs.getDouble("amt"));
					cardSaleInfo.setStrCardPhone(rs.getString("membermphone"));
					lsCardSaleInfos.add(cardSaleInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lsCardSaleInfos;
	}
	
}
