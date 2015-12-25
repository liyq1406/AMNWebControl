package com.amani.service.AdvancedOperations;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.amani.action.AnlyResultSet;
import com.amani.bean.DefaultBean;
import com.amani.bean.GbaBean;
import com.amani.bean.MissionBean;
import com.amani.bean.RevokBean;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.AsciiUtils;
import com.amani.tools.CommonTool;
import com.amani.tools.SysSendMsg;
@Service
public  class AC001Service extends AMN_ModuleService{

	@Autowired
	protected static SysSendMsg sysSendMsg;
	public SysSendMsg getSysSendMsg() {
		return sysSendMsg;
	}
	public void setSysSendMsg(SysSendMsg sysSendMsg) {
		this.sysSendMsg = sysSendMsg;
	}
	/**
	 * 如果时间小于当前时间则修改状态之后再查
	 * @throws DataAccessException
	 * @throws RuntimeException
	 * @throws Exception
	 */
	public List<MissionBean> selectMission(){
		String queryStr="";
		queryStr="update missioninfo set templatestate=1 where missionkey<='"+getCurrDatea()+"'";
	    queryStr=queryStr+ " select a.id,a.missionbillid,missionname,missionphone,missionkey,missiondetails,templatestate from missioninfo a,missiontails b where a.missionbillid=b.missionbillid and templatestate=0";
		AnlyResultSet<List<MissionBean>> analysis = new AnlyResultSet<List<MissionBean>>() {
			List<MissionBean> ls=new ArrayList<MissionBean>();
			public List<MissionBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						MissionBean bean=new MissionBean();
						 bean.setMissionId(CommonTool.FormatString(rs.getString("id")));
						 bean.setMissionbillid(CommonTool.FormatString(rs.getString("missionbillid")));
					 	 bean.setMissionkey(CommonTool.FormatString(rs.getString("missionkey")));
					 	 bean.setMissionphone(CommonTool.FormatString(rs.getString("missionphone")));
						 bean.setMissionname(CommonTool.FormatString(rs.getString("missionname")));
						 bean.setMissiondetails(CommonTool.FormatString(rs.getString("missiondetails")));
						 bean.setTemplatestate(CommonTool.FormatString(rs.getString("templatestate")));
						 ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<MissionBean> ls  =(List<MissionBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}
	
	/*
	 * 根据编号查询任务
	 */
	public List<MissionBean> selectMissionid(int missionid){
		String queryStr="select a.id,a.missionbillid,missionnames,missionphone,missionkey,missiondetails," +
				"templatestate from missioninfo a,missiontails b where a.missionbillid=b.missionbillid " +
				"and templatestate=0 and a.id='"+missionid+"'";
		AnlyResultSet<List<MissionBean>> analysis = new AnlyResultSet<List<MissionBean>>() {
			List<MissionBean> ls=new ArrayList<MissionBean>();
			public List<MissionBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						MissionBean bean=new MissionBean();
						 bean.setId(CommonTool.FormatInteger(rs.getInt("id")));
						 bean.setMissionId(CommonTool.FormatString(rs.getString("id")));
						 bean.setMissionbillid(CommonTool.FormatString(rs.getString("missionbillid")));
					 	 bean.setMissionkey(CommonTool.FormatString(rs.getString("missionkey")));
					 	 bean.setMissionphone(CommonTool.FormatString(rs.getString("missionphone")));
						 bean.setMissionname(CommonTool.FormatString(rs.getString("missionnames")));
						 bean.setMissiondetails(CommonTool.FormatString(rs.getString("missiondetails")));
						 bean.setTemplatestate(CommonTool.FormatString(rs.getString("templatestate")));
						 ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<MissionBean> ls  =(List<MissionBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}

	
	public MissionBean loadmissionByid(int missionid){
		try {
			String queryStr="select a.id,a.missionbillid,missionnames,missionphone,missionkey,missiondetails," +
			"templatestate from missioninfo a,missiontails b where a.missionbillid=b.missionbillid " +
			"and templatestate=0 and a.id='"+missionid+"'";
	AnlyResultSet<MissionBean> analysis = new AnlyResultSet<MissionBean>() {
		public MissionBean anlyResultSet(ResultSet rs) {
			MissionBean bean=new MissionBean();
			try {
				if(rs != null && rs.next()==true){
					 bean.setId(CommonTool.FormatInteger(rs.getInt("id")));
					 bean.setMissionId(CommonTool.FormatString(rs.getString("id")));
					 bean.setMissionbillid(CommonTool.FormatString(rs.getString("missionbillid")));
				 	 bean.setMissionkey(CommonTool.FormatString(rs.getString("missionkey")));
				 	 bean.setMissionphone(CommonTool.FormatString(rs.getString("missionphone")));
					 bean.setMissionname(CommonTool.FormatString(rs.getString("missionnames")));
					 bean.setMissiondetails(CommonTool.FormatString(rs.getString("missiondetails")));
					 bean.setTemplatestate(CommonTool.FormatString(rs.getString("templatestate")));
				}
		} catch (Exception e) {
			e.printStackTrace();
			bean=null;
		}
		return bean;
		}
	};
	MissionBean missiond=(MissionBean)this.amn_Dao.executeQuery_ex(queryStr, analysis);
	return missiond;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 查询
	 */
	public MissionBean selectmissionByid(int missionid){
		String sql="select * from missioninfo where id='"+missionid+"'";
		return (MissionBean) this.amn_Dao.findById(sql);
		
	}
	/*
	 * 添加任务
	 */
	public boolean addmission(String strBillId,String missionname,
			String missionkey,String missiondetails,String templatestate,String missionnames,String missionphone)
	{
		boolean bte=true;
		String strSql="";
			strSql="insert missioninfo(missionbillid,missionname,missionkey,missiondetails,templatestate) values("
					+CommonTool.quotedStr(strBillId)
					+","
					+CommonTool.quotedStr(missionname)
					+","
					+CommonTool.quotedStr(missionkey)
					+","
					+CommonTool.quotedStr(missiondetails)
					+","
					+CommonTool.quotedStr(templatestate)+ ")";
			strSql=strSql+" insert into missiontails(b.missionbillid,missionnames,missionphone) values("
					+CommonTool.quotedStr(strBillId)
					+","
					+CommonTool.quotedStr(missionnames)
					+","
					+CommonTool.quotedStr(missionphone)+ ")";
			strSql=strSql+"select a.missionbillid,missionnames,missionphone,missionname,missionkey,missiondetails,templatestate from missioninfo a,missiontails b where a.missionbillid=b.missionbillid and templatestate=0";
			try {
				bte=this.amn_Dao.executeSql(strSql);
			} catch (Exception e) {
				e.printStackTrace();
				bte=false;
			}
			return bte;
	}
	
	/**
	 * @param strTime 日期格式yyyymmddhhmmss
	 */
	@SuppressWarnings("static-access")
	
	//格式化日期时间 20131024111600
	public static String setTimeMask(String strTime) {
			if (strTime == null || strTime.equals("")) {
				return "";
			} else {
				if(strTime.length()!=19)
				{
					return strTime;
				}
				return strTime.substring(0, 4) + strTime.substring(5, 7)
						+ strTime.substring(8, 10)+strTime.substring(11,13)+strTime.substring(14,16)+strTime.substring(17,19);
			}
	}
	/**
	 * 定时批量发送短信息
	 * @param missionkey 时间(格式为yyyymmddhhmmss)
	 * @param destMobile 手机号码
	 * @param missiondetails 内容
	 * @return
	 * @throws Exception
	 */
	public String sendTimelyMsg(String missionkey,String destMobile,String missiondetails) throws Exception{
//		Timer timer=new Timer();
//		Date date=new Date();
////		System.out.println(date.toString());
//		timer.schedule(timerTaskVendor, date, 1000*60);
		return this.sysSendMsg.sendTimelyMessage(CommonTool.getLoginInfo("COMPID"),setTimeMask(missionkey),destMobile, missiondetails);
	}
	
	
	/**
	 * 如果已发送则修改状态
	 * @return  
	 * @throws DataAccessException
	 * @throws RuntimeException
	 * @throws Exception
	 */
	public boolean updatemissionstate(int id) throws DataAccessException, RuntimeException, Exception{
 			String strSql="update missioninfo set templatestate=2 where id='"+id+"'";
			return this.amn_Dao.executeSql(strSql);
	}
	
	/**
	 * 获得当前时间日期
	 * @return
	 */
	public static String getCurrDatea() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}
	/*
	 * 查询回复内容
	 */
	public List<RevokBean> selectrevokes(){
		String queryStr="select revokphone,revokdetails,revokdate from revokcallphone";
		AnlyResultSet<List<RevokBean>> analysis = new AnlyResultSet<List<RevokBean>>() {
			List<RevokBean> ls=new ArrayList<RevokBean>();
			public List<RevokBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						RevokBean bean=new RevokBean();
						 bean.setRevokphone(CommonTool.FormatString(rs.getString("revokphone")));
						 bean.setRevokdetails(CommonTool.FormatString(rs.getString("revokdetails")));
					     bean.setRevokdate(CommonTool.FormatString(rs.getString("revokdate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<RevokBean> ls  =(List<RevokBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}
	
	/*
	 * 查询回复内容getDateMask
	 */
	public List<RevokBean> selectrevoke(String time,String times){
		String queryStr="select revokphone,revokdetails,revokdate from revokcallphone where substring(isnull (revokdate,''),1,8) between " +
				"'"+CommonTool.setDateMask(time)+"' and '"+CommonTool.setDateMask(times)+"'";
		AnlyResultSet<List<RevokBean>> analysis = new AnlyResultSet<List<RevokBean>>() {
			List<RevokBean> ls=new ArrayList<RevokBean>();
			public List<RevokBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						RevokBean bean=new RevokBean();
						 bean.setRevokphone(CommonTool.FormatString(rs.getString("revokphone")));
						 bean.setRevokdetails(CommonTool.FormatString(rs.getString("revokdetails")));
					     bean.setRevokdate(CommonTool.FormatString(rs.getString("revokdate")));
						 ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<RevokBean> ls  =(List<RevokBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
//		Date date=new Date();
//		System.out.println(date.toString());
//		Timer timer1 = new Timer();   
//		timer1.schedule(timerTaskVendor, date, 80400000);
		
	}
	
	/*
	 * 回复添加信息
	 */
	public boolean addrevock() throws Exception{
		String strmessage=getUserInfos();
		strmessage=strmessage.replaceAll("\\|", "@@@");
		String[] strlength = strmessage.split("@@@");
		String[] reviceInfo=null;
		String strPhone="";
		String strContent="";
		String strReviceDate="";
		if(strlength.length>0)
		{
			for(int i=0;i<strlength.length;i++)
			{
				if(!CommonTool.FormatString(strlength[i]).equals(""))
				{
					reviceInfo=CommonTool.FormatString(strlength[i]).split(",");
					if(reviceInfo!=null && reviceInfo.length>0)
					{
						if(reviceInfo.length==3)
						{
							strPhone=reviceInfo[0];
							strContent=reviceInfo[1];
							strReviceDate=reviceInfo[2];
							System.out.println(strPhone+"-"+strContent+"-"+strReviceDate);
							boolean bte=true;
							String strSql="insert revokcallphone(revokphone,revokdetails,revokdate) values("
								+ CommonTool.quotedStr(strPhone)
								+ ","
								+ CommonTool.quotedStr(AsciiUtils.ascii2Native(strContent))
								+ ","
								+CommonTool.quotedStr(strReviceDate)
								+")";
							try {
								bte = this.amn_Dao.executeSql(strSql);
							} catch (Exception e) {
								e.printStackTrace();
								bte = false;
							}
							return bte;
							
						}
					}
				}
			}
		}
		return true;
	}
	
	public static String getUserInfos() throws Exception{
		return sysSendMsg.getReceivedMsg(CommonTool.getLoginInfo("COMPID"));
	}
	
	/*
	 * 据店号、卡类型、卡余额、疗程、生日、活跃期查询信息
	 */
	@SuppressWarnings("unchecked")
	public List<GbaBean> loadmessage(String compId,String cardtype, float cardmonney,float cardmonneys,
			 String birthday,String birthdays,String dataactivity,String cardtreatment){
		String queryStr="";
		if(dataactivity.equals("0"))
		{
			queryStr="  select membername,membermphone,cardinfo.cardno,cardtype " +
					" from memberinfo,cardaccount,cardinfo left join cardproaccount on cardinfo.cardno=cardproaccount.cardno and  ('"+cardtreatment+"'=''  or projectno in ("+loadContent(cardtreatment)+")) " +
					" where  cardinfo.cardno =cardnotomemberno  " +
					" 	and  cardinfo .cardno=cardaccount.cardno" +
					" 	and  (membervesting='"+compId+"' or '"+compId+"'='') and ('"+cardtype+"' = '' or cardtype in ("+loadContent(cardtype)+"))  " +
					" 	and ( accountbalance between '"+cardmonney+"' and '"+cardmonneys+"' ) and (substring(isnull (memberbirthday,''),5,4) between " +
					" '"+birthday+"' and '"+birthdays+"' or  '"+birthday+"'='')and isnull(membername,'')<>'' and  len(isnull(membermphone,''))=11";
			queryStr+= " group by  membername,membermphone,cardinfo.cardno,cardtype ";
		}
		else
		{
			queryStr="  select membername,membermphone,cardinfo.cardno,cardtype " +
			" from memberinfo,cardaccount , cardinfo left join cardproaccount on cardinfo.cardno=cardproaccount.cardno and  ('"+cardtreatment+"'=''  or projectno in ("+loadContent(cardtreatment)+"))" +
			" where cardinfo.cardno =cardnotomemberno and cardaccount.cardno=cardproaccount.cardno" +
			" and  (membervesting='"+compId+"' or '"+compId+"'='') and ('"+cardtype+"' = '' or cardtype in ("+loadContent(cardtype)+")) " +
			" and ( accountbalance between '"+cardmonney+"' and '"+cardmonneys+"' ) and (substring(isnull (memberbirthday,''),5,4) between " +
			" '"+birthday+"' and '"+birthdays+"' or  '"+birthday+"'='')and isnull(membername,'')<>'' and  len(isnull(membermphone,''))=11";
			if(dataactivity.equals("1")){
				queryStr+=" and  csdate between '"+CommonTool.getafterMonththree()+"' and '"+CommonTool.getCurrDate()+"' group by  membername,membermphone,cardinfo.cardno,cardtype";
			}else if(dataactivity.equals("2")){
				queryStr+=" and csdate between '"+CommonTool.getafterMonthsix()+"' and '"+CommonTool.getCurrDate()+"' group by  membername,membermphone,cardinfo.cardno,cardtype";
			}else if(dataactivity.equals("3")){
				queryStr+=" and csdate between '"+CommonTool.getafterMonthnine()+"' and '"+CommonTool.getCurrDate()+"' group by  membername,membermphone,cardinfo.cardno,cardtype";
			}else if(dataactivity.equals("4")){
				queryStr+=" and csdate between '"+CommonTool.getafteryear()+"' and '"+CommonTool.getCurrDate()+"' group by  membername,membermphone,cardinfo.cardno,cardtype";
			}else if(dataactivity.equals("0")){
				queryStr+="  group by  membername,membermphone,cardinfo.cardno,cardtype";
			}
		}
		AnlyResultSet<List<GbaBean>> analysis = new AnlyResultSet<List<GbaBean>>() 
	  {
			List<GbaBean> ls=new ArrayList<GbaBean>();
			public List<GbaBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						GbaBean bean=new GbaBean();
						 bean.setMenberName(CommonTool.FormatString(rs.getString("membername")));
						 if(bean.getMenberName().length()>1)
							 bean.setShowmenberName(bean.getMenberName().substring(0,1)+"**");
						 bean.setPhone(CommonTool.FormatString(rs.getString("membermphone")));
						 // bean.setShowphone(bean.getPhone());
						 bean.setShowphone(bean.getPhone().substring(0,3)+"****"+bean.getPhone().substring(8,11));
					     bean.setMemberNo(CommonTool.FormatString(rs.getString("cardno")));
					     if(bean.getMemberNo().length()>6)
					    	 bean.setShowmemberNo(bean.getMemberNo().substring(0,6)+"****");
					     bean.setCardClass(CommonTool.FormatString(rs.getString("cardtype")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<GbaBean> ls  =(List<GbaBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
	}
	
	public String loadMissionid(String strMisssionid){
		String strSql="select missionbillid from missioninfo where ISNULL(id,'')='"+strMisssionid+"'";
		try{
			AnlyResultSet<String >  analysis = new AnlyResultSet<String>() {
				public String anlyResultSet(ResultSet rs) {
					String returnValue = "";
					try {
						if (rs != null && rs.next() == true) {
							returnValue=CommonTool.FormatString(rs.getString("strMisssionid"));
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue ="";
					}
					return returnValue;
				}
			};
			return (String) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public String loadContent(String cardtreatment)
	{
		String strContent="";
		String proList[]=cardtreatment.split(";");   
		if(proList.length>0)
		{
			for(int i=0;i<proList.length;i++)
			{
				if(strContent.equals(""))
				{
					strContent="'"+proList[i]+"'";
				}	
				else
				{
					strContent=strContent+",'"+proList[i]+"'";
				}
			}
		}
		return strContent;
	}
	
	/*
	 * 查询默认手机发送号码
	 */
	public List<DefaultBean> selectDefault(){
		String queryStr="select defaultname,defaultphone from defaultlist";
			AnlyResultSet<List<DefaultBean>> analysis = new AnlyResultSet<List<DefaultBean>>() {
				List<DefaultBean> ls=new ArrayList<DefaultBean>();
				public List<DefaultBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							DefaultBean bean=new DefaultBean();
							 bean.setName(CommonTool.FormatString(rs.getString("defaultname")));
							 bean.setPhone(CommonTool.FormatString(rs.getString("defaultphone")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<DefaultBean> ls  =(List<DefaultBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	
	/*
	 * 添加默认手机发送号码
	 */
	public boolean addDefaultMessage(String name,String phone){
		boolean bte=true;
		String sql="insert defaultlist(defaultname,defaultphone) values("
			+CommonTool.quotedStr(name)
			+","
			+CommonTool.quotedStr(phone)+ ")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
		
	}
	
	public boolean addsemdmessagedetial( String compId, String sendbillid,
			   String name,String phone,String cardno,String cardType){
		boolean bte=true;
		String strSql="insert smgdetails(sendcompid,sendbillid,smgbernme,smgphone,cardno,cardtype) values(" 
			+CommonTool.getLoginInfo("COMPID")
			+ ","
			+ CommonTool.quotedStr(sendbillid)
			+ ","
			+ CommonTool.quotedStr(name) 
			+ ","
			+CommonTool.quotedStr(phone)
			+ ","
			+ CommonTool.quotedStr(cardno)
			+ ","
			+ CommonTool.quotedStr(cardType)+ ")";
		try {
			bte = this.amn_Dao.executeSql(strSql);

		} catch (Exception e) {
			e.printStackTrace();
			bte = false;
		}
		return bte;
	}
	//add sendmessage log
	public boolean addsendmessagelog( String compId, String sendbillid,int sendstate,
			String sendcontent) {
		boolean bRet = true;
		String strSql ="";
		strSql = "insert smgInfo(sendcompid,sendbillid,senddate,sendtime,sendstate,userid,sendcontent) values("
				+CommonTool.getLoginInfo("COMPID")
				+ ","
				+ CommonTool.quotedStr(sendbillid)
				+ ","
				+ CommonTool.quotedStr(CommonTool.getCurrDate()) 
				+ ","
				+CommonTool.quotedStr(CommonTool.getCurrTime())
				+ ","
				+sendstate
				+ ","
				+ CommonTool.getLoginInfo("COMPID")
				+ ","
				+ CommonTool.quotedStr(sendcontent) + ")";
		try {
			bRet = this.amn_Dao.executeSql(strSql);
		} catch (Exception e) {
			e.printStackTrace();
			bRet = false;
		}
		return bRet;
	}
	  public static float countWords(String str) {
	        if (str == null || str.length() <= 0) {
	            return 0;
	        }
	        float len = 0;
	        char c;
	        for (int i = str.length() - 1; i >= 0; i--) {
	            c = str.charAt(i);
	            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
	                // 字母, 数字
	                len+=1;
	            } else {
	                if (Character.isLetter(c)) { // 中文
	                    len++;
	                } else { // 符号或控制字符
	                    len+=1;
	                }
	            }
	        }
	        return len;
	    }
	
	  /**
	   * 批量发送短信
	   * @param destMobile
	   * @param msgText
	   * @return
	   * @throws Exception
	   */
	public String sendMsg(String destMobile,String msgText) throws Exception{
		return this.sysSendMsg.sendMsg(CommonTool.getLoginInfo("COMPID"), destMobile, msgText);
	}

		public boolean delete(Object curMaster) {
			// need to add log process
			try {
				if (deleteDetail(curMaster)) {
					if (deleteMaster(curMaster)) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
	
		@Override
		protected boolean postMaster(Object curMaster) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		protected boolean postDetail(Object details) {
			List<MissionBean> lsMisssionBean=(List<MissionBean>)details;
			if(lsMisssionBean!=null && lsMisssionBean.size()>0)
			{
				for(int i=0;i<lsMisssionBean.size();i++)
				{
						this.amn_Dao.saveOrUpdate(lsMisssionBean.get(i));
				}
			}
			return true;
		}

		@Override
		public List loadMasterDataSet(int pageSize, int startRow) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected boolean deleteDetail(Object curMaster) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		protected boolean deleteMaster(Object curMaster) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
