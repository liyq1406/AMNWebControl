package com.amani.service.BOOSREP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.jotm.jta.jeremie.TSHandler;
import org.springframework.stereotype.Service;

import com.amani.bean.BS001Bean;
import com.amani.service.AMN_ReportService;
import com.amani.tools.CommonTool;

@Service
public class BS001Service extends AMN_ReportService {
	public List<BS001Bean> loadBS001(String strCompId,String strFromDate,String strToDate)
	{
		String strSql="exec upg_comp_operate_info '"+strCompId+"','"+CommonTool.setDateMask(strFromDate)+"','"+CommonTool.setDateMask(strToDate)+"'";
		ResultSet rs=null;
		List<BS001Bean> lsBeans=new ArrayList<BS001Bean>();
		try {
			rs=amn_Dao.executeQuery(strSql);
			if(rs!=null)
			{
				while(rs.next())
				{
					BS001Bean bs001Bean=new BS001Bean();
					bs001Bean.setBeautyA(CommonTool.GetGymAmt(rs.getDouble("beautyA"),0)+"");
					bs001Bean.setBeautyB(CommonTool.GetGymAmt(rs.getDouble("beautyB"),0)+"");
					bs001Bean.setBeautyBigClient(CommonTool.GetGymAmt(rs.getDouble("beautyBigClient"),0)+"");
					bs001Bean.setBeautyC(CommonTool.GetGymAmt(rs.getDouble("beautyC"),0)+"");
					bs001Bean.setBeautyGoods(CommonTool.GetGymAmt(rs.getDouble("beautyGoods"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("beautyGoodsAmt"),0));
					bs001Bean.setBeautyManger(CommonTool.GetGymAmt(rs.getDouble("beautyManger"),0)+"");
					bs001Bean.setBeautyperf(CommonTool.GetGymAmt(rs.getDouble("beautyperf"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("beautyperflvl"),0)+"%");
					bs001Bean.setBeautyPro(CommonTool.GetGymAmt(rs.getDouble("beautyPro"),0)+"/"+rs.getDouble("beautyProAmt"));
					bs001Bean.setBeautyrealperf(CommonTool.GetGymAmt(rs.getDouble("beautyrealperf"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("beautyrealperflvl"),0)+"%");
					bs001Bean.setBeautyWage(CommonTool.GetGymAmt(rs.getDouble("beautyWage"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("beautyWagelvl"),0));
					bs001Bean.setBeautyYearCard(CommonTool.GetGymAmt(rs.getDouble("beautyYearCard"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("beautyYearCardAmt"),0));
					bs001Bean.setCompid(rs.getString("compid"));
					bs001Bean.setCompname(rs.getString("compname"));
					bs001Bean.setDkccount(CommonTool.GetGymAmt(rs.getDouble("dkccount"),0)+"");
					bs001Bean.setDtBC(CommonTool.GetGymAmt(rs.getDouble("dtBC"),0)+"");
					bs001Bean.setDtManager(CommonTool.GetGymAmt(rs.getDouble("dtManager"),0)+"");
					bs001Bean.setHairfxs(CommonTool.GetGymAmt(rs.getDouble("hairfxs"),0)+"");
					bs001Bean.setHairGoods(CommonTool.GetGymAmt(rs.getDouble("hairGoods"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairGoodsAmt"),0));
					bs001Bean.setHairperf(CommonTool.GetGymAmt(rs.getDouble("hairperf"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairperflvl"),0)+"%");
					bs001Bean.setHairPro(CommonTool.GetGymAmt(rs.getDouble("hairPro"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairProAmt"),0));
					bs001Bean.setHairrealperf(CommonTool.GetGymAmt(rs.getDouble("hairrealperf"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairrealperflvl"),0)+"%");
					bs001Bean.setHairWage(CommonTool.GetGymAmt(rs.getDouble("hairWage"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairWagelvl"),0)+"%");
					bs001Bean.setTrWage(CommonTool.GetGymAmt(rs.getDouble("trWage"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("trWagelvl"),0)+"%");
					bs001Bean.setTrItemCount(CommonTool.GetGymAmt(rs.getDouble("trItemCount"),0)+"");
					bs001Bean.setTrdd(CommonTool.GetGymAmt(rs.getDouble("trdd"),0)+"");
					bs001Bean.setTrCD(CommonTool.GetGymAmt(rs.getDouble("trCD"),0)+"");
					bs001Bean.setTrAB(CommonTool.GetGymAmt(rs.getDouble("trAB"),0)+"");
					bs001Bean.setSumWage(CommonTool.GetGymAmt(rs.getDouble("sumWage"),0)+"");
					bs001Bean.setSumrealperf(CommonTool.GetGymAmt(rs.getDouble("sumrealperf"),0)+"");
					bs001Bean.setSumperf(CommonTool.GetGymAmt(rs.getDouble("sumperf"),0)+"");
					bs001Bean.setSumClient(CommonTool.GetGymAmt(rs.getDouble("sumClient"),0)+"");
					bs001Bean.setNoBusiness(CommonTool.GetGymAmt(rs.getDouble("noBusiness"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("noBusinesslvl"),0)+"%");
					bs001Bean.setManageWage(CommonTool.GetGymAmt(rs.getDouble("manageWage"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("manageWagelvl"),0)+"%");
					bs001Bean.setHklvl(CommonTool.GetGymAmt(rs.getDouble("hklvl"),0)+"%");
					bs001Bean.setHcItemCount(CommonTool.GetGymAmt(rs.getDouble("hcItemCount"),0)+"");
					bs001Bean.setHairZS(CommonTool.GetGymAmt(rs.getDouble("hairZS"),0)+"");
					bs001Bean.setHairYearCard(CommonTool.GetGymAmt(rs.getDouble("hairYearCard"),0)+"/"+CommonTool.GetGymAmt(rs.getDouble("hairYearCardAmt"),0));
					lsBeans.add(bs001Bean);
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
		return lsBeans;
	}
}
