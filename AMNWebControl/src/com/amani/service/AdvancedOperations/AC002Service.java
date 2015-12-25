package com.amani.service.AdvancedOperations;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.amani.bean.CallsWatingBean;
import org.springframework.stereotype.Service;
import com.amani.action.AnlyResultSet;
import com.amani.service.AMN_ModuleService;
import com.amani.tools.CommonTool;

@Service
public class AC002Service extends AMN_ModuleService{
	public static List<CallsWatingBean> loadFileInfos(String timees,String phones)
    {
	 try {
            URL url = new URL("http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/127.0.0.1/"+timees);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strline = null;
            StringBuffer sbufline = new StringBuffer();
            while((strline = br.readLine()) != null)
            {
            	sbufline.append(strline+"/n");
            }
            String callCenterLine=sbufline.toString();
            sbufline=null;
            Pattern p = Pattern.compile("<tt>([^</tt>]*)</tt>");
            Matcher m = p.matcher(callCenterLine);
            List<String> ls=new ArrayList<String>();
            while(m.find()){
            	ls.add(m.group());
            }
            List<CallsWatingBean> lss=new ArrayList<CallsWatingBean>();
            CallsWatingBean bean=new CallsWatingBean();
            for(int i=0;i<ls.size();i++)
            {
            	if(ls.get(i).indexOf(phones)>-1)
            	{ 
	            	bean.setFilename(CommonTool.FormatString(ls.get(i))) ;
	            	bean.setFilesize(CommonTool.FormatString(ls.get(i+1)));
	            	lss.add(bean);
            	}  
            	i++;
			}
            ls=null;
            br.close();
            return lss;
			 } catch (Exception e) {
					e.printStackTrace();
					return null;
		}
    }
	//获得当前微信预约信息
	public List<CallsWatingBean> loadAllCallsByUseIds(String callbillid){
			 String queryStr="update callwaiting set callbillid='"+callbillid+"',callstate=1,offertime='"+getCurrDate()+"' where callstate=5";
	         queryStr=queryStr+" select callbillid,callon,callstate  from callwaiting where calluserid='微信' order by callid desc";
		AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
		  	List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	/**
	  * 显示录音消息
	 * @throws IOException 
	  */
	 public static List<CallsWatingBean> loadFileInfo(String time,String phone)
	    {
		 try {
	            URL url = new URL("http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/127.0.0.1/"+time);
	            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	            String strline = null;
	            StringBuffer sbufline = new StringBuffer();
	            while((strline = br.readLine()) != null)
	            {
	            	sbufline.append(strline+"/n");
	            }
	            String callCenterLine=sbufline.toString();
	            sbufline=null;
	            Pattern p = Pattern.compile("<tt>([^</tt>]*)</tt>");
	            Matcher m = p.matcher(callCenterLine);
	            List<String> ls=new ArrayList<String>();
	            while(m.find()){
	            	ls.add(m.group());
	            }
	            List<CallsWatingBean> lss=new ArrayList<CallsWatingBean>();
	            CallsWatingBean bean=new CallsWatingBean();
	            for(int i=0;i<ls.size();i++)
	            {
	            	if(ls.get(i).indexOf(phone)>-1)
	            	{ 
		            	bean.setFilename(CommonTool.FormatString(ls.get(i))) ;
		            	bean.setFilesize(CommonTool.FormatString(ls.get(i+1)));
		            	lss.add(bean);
	            	}  
	            	i++;
				}
	            ls=null;
	            br.close();
	            return lss;
				 } catch (Exception e) {
						e.printStackTrace();
						return null;
			}
	    }
	 /**
		 * 检查是否有来电
		 * @return
		 * @throws Exception
		 */
		public boolean isCallmessage() throws Exception {
		    String strSql = "select * from orders  where orderstate=0";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
				public Boolean anlyResultSet(ResultSet rs) {
					boolean returnValue = false;
					try {
						if (rs != null && rs.next() == true) {
							returnValue = true;
						} else {
							returnValue = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue = false;
					}
					return returnValue;
				}
			};
			return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
		 /**
		 * 检查是否有来电（短信）
		 * @return
		 * @throws Exception
		 */
		public boolean isMessage() throws Exception {
			String strSql="select * from callwaiting  where callstate=2 and agentnum= '短信' and calluserid='"+CommonTool.getLoginInfo("USERID")+"'";
			AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
				public Boolean anlyResultSet(ResultSet rs) {
					boolean returnValue = false;
					try {
						if (rs != null && rs.next() == true) {
							returnValue = true;
						} else {
							returnValue = false;
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnValue = false;
					}
					return returnValue;
				}
			};
			return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
		}
	 public List<CallsWatingBean> selectorders(String callbillid){
			String queryStr="select * from orders  where orderstate=0";
				   queryStr=queryStr+"update orders set callbillid='"+callbillid+"',orderstate=1 where orderstate=0";
			AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setOrdertime(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOrdertimes(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOrderconply(CommonTool.FormatString(rs.getString("orderconply")));
							bean.setOrderproject(CommonTool.FormatString(rs.getString("orderproject")+"["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]"));
							bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
							bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
							bean.setOrdersid(CommonTool.FormatInteger(rs.getInt("ordersid")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
		}
	 public CallsWatingBean loadcallwei(){
	    	String strSql="select * from orders  where orderstate=0";
	    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
				public CallsWatingBean anlyResultSet(ResultSet rs) {
					CallsWatingBean bean=new CallsWatingBean();
					try {
						if(rs!=null&&rs.next()){
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOrderconply(CommonTool.FormatString(rs.getString("orderconply")));
							bean.setOrderproject(CommonTool.FormatString(rs.getString("orderproject")+"["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]"));
							bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
							bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
							bean.setOrdersid(CommonTool.FormatInteger(rs.getInt("ordersid")));
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return bean;
				}
			};
			CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return result;
		}
	 //获得所有未处理的短信单据
	 public List<CallsWatingBean> messageundisposedcall(String s){
		 //默认查未结案的
		 String strSql="select * from callwaiting  where callstate<>3 and callbillid<>''  and agentnum ='短信' and calluserid='"+CommonTool.getLoginInfo("USERID")+"' order by callbillid desc";
		 //0表示查询所有
		 if("all".equalsIgnoreCase(s)){
			 strSql = "select * from callwaiting  where agentnum ='短信' and callbillid<>'' and calluserid='"+CommonTool.getLoginInfo("USERID")+"' order by callbillid desc";
			 //1表示查询未结案
		 }else if("open".equalsIgnoreCase(s)){
			 strSql = "select * from callwaiting  where callstate<>3 and callbillid<>'' and agentnum ='短信' and calluserid='"+CommonTool.getLoginInfo("USERID")+"' order by callbillid desc";
			//2表示查询已结案
		 }else if("close".equalsIgnoreCase(s)){
			 strSql = "select * from callwaiting  where callstate=3  and callbillid<>'' and agentnum ='短信' and calluserid='"+CommonTool.getLoginInfo("USERID")+"' order by callbillid desc";
		 }
		 //callstate=3时表示已结案
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
	
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));//单据编号
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));//登陆工号
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));//呼入电话号码
							bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("callstate")));
							bean.setIsmessages("3");//用来判断是从短信窗口进入
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
				analysis=null;
				return ls;
		}

	 //得到所有未处理的短信，并且将没单据号的添加单据号
	public List<CallsWatingBean> getAllOpenMessageundisposedcall() throws Exception {
		 String strSql="select * from callwaiting  where callstate<>3 and agentnum= '短信' and calluserid='"+CommonTool.getLoginInfo("USERID")+"'";
		 String sql ="";
		 ResultSet rs = this.amn_Dao.executeQuery(strSql);
		 List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
		 	while(rs!=null&&rs.next()){
				CallsWatingBean bean=new CallsWatingBean();
				String callbillid = "";
				callbillid = this.dataTool.loadBillIdByRule(CommonTool.getLoginInfo("COMPID"),"callwaiting", "callbillid","SP012");
				sql = sql+" update callwaiting set callbillid='"+callbillid+"' ,callstate=1  where callid = '"+rs.getInt("callid")+"' ";
				bean.setCallbillid(callbillid);//单据编号
				bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));//登陆工号
				bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));//呼入电话号码
				bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("callstate")));
				bean.setBilltype(CommonTool.FormatString(rs.getString("calltype")));//类型（1 表示满意 ，2 表示不满意）
				bean.setIsmessages("3");//用来判断是从短信窗口进入
				ls.add(bean);
		 	}
		 	if(sql.indexOf("update")>-1){
		 		this.amn_Dao.executeSql(sql);
		 	}
			return ls;
	}
	public CallsWatingBean loadcallbycall(String memberno){
    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
    			"from callwaiting" +
    			"	left join memberinfo on callon=membermphone" +
    			"	left join cardinfo on memberno=cardno" +
    			"	left join cardtypenameinfo on cardtypeno=cardtype" +
    			"   where memberno='"+memberno+"' ";
    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
			public CallsWatingBean anlyResultSet(ResultSet rs) {
				CallsWatingBean bean=new CallsWatingBean();
				try {
					if(rs!=null&&rs.next()){
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
	}
	/**
	 * 一个号码多张卡
	 * @param callbillno
	 * @return
	 */
	public List<CallsWatingBean> selectbyphone(String call_no){
		String strSql;
		strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
		"from callwaiting" +
		"	left join memberinfo on callon=membermphone" +
		"	left join cardinfo on memberno=cardno" +
		"	left join cardtypenameinfo on cardtypeno=cardtype" +
		"   where callon='"+call_no+"' ";
		AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setCardtypename(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMemberno(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
		}

	
	//挂失
	public boolean cardon(String membernotocard){
		boolean bte=true;
		String sql;
		sql="update cardinfo set cardstate=9 where membernotocard='"+membernotocard+"'";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	//解挂
	public boolean cardons(String membernotocard){
		boolean bte=true;
		String sql;
		sql="update cardinfo set cardstate=5 where membernotocard='"+membernotocard+"'";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}

	/**
	 * 修改状态
	 * @param callbillid
	 * @return
	 */
	public boolean updatabillid(String callbillid){
		
		boolean bte=true;
		String queryStr="";
		try {
		if(callbillid.equals("0")){
		 queryStr=" update callwaiting set callbillid='"+callbillid+"' where callbillid=0 and calluserid='"+CommonTool.getLoginInfo("USERID")+"' ";
		 bte=this.amn_Dao.executeSql(queryStr);
		}
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	public List<CallsWatingBean> selectbycard(int cardstate,String membername,String membermphone,String cardtype,String cardno){
	String strSql;
	strSql="select cardstate,membername,membermphone,cardtype,a.cardno,accountbalance,membernotocard from cardinfo a,memberinfo b,cardaccount c where memberno=membernotocard and c.cardno=memberno and (membername='"+membername+"' or '"+membername+"'='') and (a.cardno='"+cardno+"' or '"+cardno+"'='') and (membermphone='"+membermphone+"' or '"+membermphone+"'='')";
	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
		List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
		public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
			try {
				while(rs!=null&&rs.next()){
					CallsWatingBean bean=new CallsWatingBean();
					bean.setCardstate(CommonTool.FormatInteger(rs.getInt("cardstate")));
					bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
					bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
					bean.setCardtype(CommonTool.FormatString(rs.getString("cardtype")));
					bean.setCardno(CommonTool.FormatString(rs.getString("cardno")));
					bean.setCardaccount(CommonTool.FormatString(rs.getString("accountbalance")));
					bean.setMembernotocard(CommonTool.FormatString(rs.getString("membernotocard")));
					ls.add(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return ls;
		}
	};
	List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return ls;
	}
	public List<CallsWatingBean> undisposedcalls(String timms){
    	String strSql;
    	strSql="select a.callid,c.callon,billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,b.orderusermf,b.orderusertrh,b.orderusermr from callwaiting a, orders b,callwaiting c where a.callon=c.callon and c.callbillid=b.callbillid  and (substring(isnull (b.ordertime,''),1,10)=replace('"+timms+"','-','_') or replace('"+timms+"','-','_')='' )";
    	strSql=strSql+" union select a.callid,c.callon,billtype='咨询', c.calluserid,b.callbillid,ordertime=b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')+isnull(refercards,'')+isnull(referproject,''),orderusermf='',orderusertrh='',orderusermr='' from callwaiting a,refer b,callwaiting c where  a.callon=c.callon and c.callbillid=b.callbillid   and (substring(isnull (b.refertime,''),1,10)=replace('"+timms+"','-','_') or replace('"+timms+"','-','_')='' )";	
    	strSql=strSql+"union  select a.callid,c.callon,billtype='投诉', c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,'')+isnull(peiierdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''from callwaiting a,peiier b,callwaiting c where a.callon=c.callon and c.callbillid=b.callbillid   and (substring(isnull (b.peiiertime,''),1,10)=replace('"+timms+"','-','_') or replace('"+timms+"','-','_')='' )";
    	strSql=strSql+"union  select a.callid,c.callon,billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,'')+isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr='' from callwaiting a,cardreturning b,callwaiting c where a.callon=c.callon and c.callbillid=b.callbillid  and (substring(isnull (b.cardreturningtime,''),1,10)=replace('"+timms+"','-','_') or replace('"+timms+"','-','_')='' )";
    	strSql=strSql+"order by a.callid desc";
    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			String strtProject="";
			String strProjectName="";
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
						strtProject =CommonTool.FormatString(rs.getString("orderproject"));
						bean.setBillofUser("");
						strProjectName="";
						if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
						{
							bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
							String[] lsprj=strtProject.split(";");
							
							if(lsprj!=null && lsprj.length>0)
							{
								for(int i=0;i<lsprj.length;i++)
								{
									if(lsprj[i].toString().equals("300"))
									{
										strProjectName=strProjectName+"[美发项目]";
									}
									else if(lsprj[i].toString().equals("600"))
									{
										strProjectName=strProjectName+"[烫染项目]";
									}
									else if(lsprj[i].toString().equals("400"))
									{
										strProjectName=strProjectName+"[美容项目]";
									}

								}
							}
							bean.setOrderproject(CommonTool.FormatString(strProjectName));
						}
						bean.setOrderproject(strtProject);
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
	}
	
	
	public List<CallsWatingBean> selectByparam(String memname,String userid,String billstate,String times){
		String  queryStr="";
		queryStr=queryStr+" select billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,orderdetail,b.orderusermf,b.orderusertrh,b.orderusermr from callwaiting a, orders b,callwaiting c left join memberinfo d on  callon=d.membermphone  where  a.callon=c.callon and c.callbillid=b.callbillid and (d.membername='"+memname+"' or '"+memname+"'='') and (c.calluserid='"+userid+"' or '"+userid+"'='') and (substring(isnull (b.ordertime,''),1,10)=replace('"+times+"','-','_') or replace('"+times+"','-','_')='' ) and  (b.orderstate='"+billstate+"' or '"+billstate+"'='')";
		queryStr=queryStr+" union select billtype='预约',calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,orderdetail,b.orderusermf,b.orderusertrh,b.orderusermr from orders b where calluserid='微信'";
		queryStr=queryStr+" union select billtype='咨询', c.calluserid,b.callbillid,ordertime=b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')+isnull(refercards,'')+isnull(referproject,''),orderdetail=isnull(referdetails,''),orderusermf='',orderusertrh='',orderusermr='' from callwaiting a,refer b,callwaiting c left join memberinfo d on callon=d.membermphone  where  a.callon=c.callon and c.callbillid=b.callbillid and (d.membername='"+memname+"' or '"+memname+"'='') and (c.calluserid='"+userid+"' or '"+userid+"'='') and (substring(isnull (b.refertime,''),1,10)=replace('"+times+"','-','_') or replace('"+times+"','-','_')='' ) and  (b.feferstate='"+billstate+"' or '"+billstate+"'='')";
		queryStr=queryStr+" union select billtype='投诉',c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,''),orderdetail=isnull(peiierdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''from callwaiting a,peiier b,callwaiting c left join memberinfo d on  callon=d.membermphone  where a.callon=c.callon and c.callbillid=b.callbillid and (d.membername='"+memname+"' or '"+memname+"'='') and (c.calluserid='"+userid+"' or '"+userid+"'='') and (substring(isnull(b.peiiertime,''),1,10)=replace('"+times+"','-','_') or replace('"+times+"','-','_')='' ) and  (b.peiierstate='"+billstate+"' or '"+billstate+"'='')";
		queryStr=queryStr+" union select billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,''),orderdetail=isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr='' from callwaiting a,cardreturning b,callwaiting c left join memberinfo d on  callon=d.membermphone  where  a.callon=c.callon and c.callbillid=b.callbillid and (d.membername='"+memname+"' or '"+memname+"'='') and (c.calluserid='"+userid+"' or '"+userid+"'='') and (substring(isnull (b.cardreturningtime,''),1,10)=replace('"+times+"','-','_') or replace('"+times+"','-','_')='' ) and (b.cardreturningstate='"+billstate+"' or '"+billstate+"'='')";
		    AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			String strtProject="";
			String strProjectName="";
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						strtProject =CommonTool.FormatString(rs.getString("orderproject"));
						bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
						bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
						strProjectName="";
						if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
						{
							String[] lsprj=strtProject.split(";");
							
							if(lsprj!=null && lsprj.length>0)
							{
								for(int i=0;i<lsprj.length;i++)
								{
									if(lsprj[i].toString().equals("300"))
									{
										strProjectName=strProjectName+"[美发项目]";
									}
									else if(lsprj[i].toString().equals("600"))
									{
										strProjectName=strProjectName+"[烫染项目]";
									}
									else if(lsprj[i].toString().equals("400"))
									{
										strProjectName=strProjectName+"[美容项目]";
									}

								}
							}
							bean.setOrderproject(CommonTool.FormatString(strProjectName));
						}
						bean.setOrderproject(strtProject);
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
}
	
	
	/**
	 * 所有未处理单据
	 * @return
	 */
	public List<CallsWatingBean> undisposedcall(){
    	String strSql;
    	strSql="select a.callid,c.callon,billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,b.orderusermf,b.orderusertrh,b.orderusermr from callwaiting a, orders b,callwaiting c where  orderstate <>3 and a.callon=c.callon and c.callbillid=b.callbillid ";
    	strSql=strSql+"	union select a.callid,c.callon,billtype='咨询', c.calluserid,b.callbillid,ordertime=b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')+isnull(refercards,'')+isnull(referproject,''),orderusermf='',orderusertrh='',orderusermr='' from callwaiting a,refer b,callwaiting c where b.feferstate <> 3 and a.callon=c.callon and c.callbillid=b.callbillid  "; 
    	strSql=strSql+"	union  select a.callid,c.callon,billtype='投诉', c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,'')+isnull(peiierdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''from callwaiting a,peiier b,callwaiting c where peiierstate <> 3 and a.callon=c.callon and c.callbillid=b.callbillid  ";
    	strSql=strSql+"	union  select a.callid,c.callon,billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,'')+isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr='' from callwaiting a,cardreturning b,callwaiting c where cardreturningstate <> 3 and a.callon=c.callon and c.callbillid=b.callbillid";  
    	strSql=strSql+"  order by a.callid desc";
    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			String strtProject="";
			String strProjectName="";
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
						strtProject =CommonTool.FormatString(rs.getString("orderproject"));
						bean.setBillofUser("");
						strProjectName="";
						if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
						{
							bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
							String[] lsprj=strtProject.split(";");
							
							if(lsprj!=null && lsprj.length>0)
							{
								for(int i=0;i<lsprj.length;i++)
								{
									if(lsprj[i].toString().equals("300"))
									{
										strProjectName=strProjectName+"[美发项目]";
									}
									else if(lsprj[i].toString().equals("600"))
									{
										strProjectName=strProjectName+"[烫染项目]";
									}
									else if(lsprj[i].toString().equals("400"))
									{
										strProjectName=strProjectName+"[美容项目]";
									}

								}
							}
							bean.setOrderproject(CommonTool.FormatString(strProjectName));
						}
						bean.setOrderproject(strtProject);
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
	List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			return ls;
	}
	
	/**
	 * 查询预约信息
	 * @return
	 */
	public List<CallsWatingBean> selectOrder(String callbillno){
	    	String queryStr;
	    	queryStr=" select b.calluserid,ordertime,orderconply,orderproject,orderdetail,orderstate,ordersid,orderusermf,orderusertrh,orderusermr from orders b where b.callbillid='"+callbillno+"'";
	    	queryStr=queryStr+" select * from" +" callwaiting a,orders b where a.callbillid=b.callbillid and b.callbillid='"+callbillno+"'";
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setOrdertime(setTimeMaskes(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOrderconply(CommonTool.FormatString(rs.getString("orderconply")));
							bean.setOrderproject(CommonTool.FormatString(rs.getString("orderproject")+"["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]"));
							bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
							bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
							bean.setOrdersid(CommonTool.FormatInteger(rs.getInt("ordersid")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	/**
	 * 添加投诉类容
	 * @param callbillid
	 * @param calluserid
	 * @param peiiertime
	 * @param peiiercontent
	 * @param peiierdetails
	 * @param peiierstate
	 * @return
	 */
	public boolean addpeiier(String callbillid,String peiiertime,String peiiercontent,String peiierdetails,
			String peiierstate){
		boolean bte=true;
		String sql="insert peiier (callbillid,calluserid,peiiertime,peiiercontent,peiierdetails,peiierstate) values(" 
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(peiiercontent)
			+","
			+CommonTool.quotedStr(peiierdetails)
			+","
			+CommonTool.FormatInteger(1) +")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	//添加投诉（结案）
	public boolean addpeiiers(String callbillid,String peiiertime,String peiiercontent,String peiierdetails,
			String peiierstate){
		boolean bte=true;
		String sql="insert peiier (callbillid,calluserid,peiiertime,peiiercontent,peiierdetails,peiierstate) values(" 
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(peiiercontent)
			+","
			+CommonTool.quotedStr(peiierdetails)
			+","
			+CommonTool.FormatInteger(0) +")";
		sql=sql+"	update peiier set peiierstate=3 where peiierstate=0";
		sql=sql+"	update peiier set peiierstate=3 where callbillid='"+callbillid+"' and peiierstate=1";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	//获得当前坐席的来电信息
	public List<CallsWatingBean> loadAllCallsByUseId(){
	    	String queryStr="select callbillid,callon,callstate  from callwaiting where calluserid='"+CommonTool.getLoginInfo("USERID")+"' and agentnum <>  '短信' order by callid desc";
		AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
		  	List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	//添加退卡信息
	public boolean addcardreturn(String callbillid,String cardreturningcontent,String cardreturningdetails){
		boolean bte=true;
		String sql;
		sql="insert cardreturning(callbillid,calluserid,cardreturningtime,cardreturningcontent,cardreturningdetails,cardreturningstate) values("
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(cardreturningcontent)
			+","
			+CommonTool.quotedStr(cardreturningdetails)
			+","
			+CommonTool.FormatInteger(1) +")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	//添加退卡信息(结案)
	public boolean addcardreturns(String callbillid,String cardreturningcontent,String cardreturningdetails){
		boolean bte=true;
		String sql;
		sql="insert cardreturning(callbillid,calluserid,cardreturningtime,cardreturningcontent,cardreturningdetails,cardreturningstate) values("
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(cardreturningcontent)
			+","
			+CommonTool.quotedStr(cardreturningdetails)
			+","
			+CommonTool.FormatInteger(0) +")";
		sql=sql+" update cardreturning set cardreturningstate=3 where cardreturningstate=0";
		sql=sql+" update cardreturning set cardreturningstate=3 where cardreturningstate=1 and callbillid='"+callbillid+"'";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	/**
	 * 查询退卡信息
	 * @return
	 */
	public List<CallsWatingBean> selectcards(String callbillno){
	    	String queryStr;
	    	queryStr=" select cardreturningid,callon,b.callbillid,b.calluserid,cardreturningtime,cardreturningcontent,cardreturningdetails,cardreturningstate  from callwaiting a,cardreturning b where a.callbillid=b.callbillid and b.callbillid='"+callbillno+"'";
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCardreturningid(CommonTool.FormatInteger(rs.getInt("cardreturningid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setCardreturningtime(setTimeMaskes(CommonTool.FormatString(rs.getString("cardreturningtime"))));
							bean.setCardreturningcontent(CommonTool.FormatString(rs.getString("cardreturningcontent")));
							bean.setCardreturningdetails(CommonTool.FormatString(rs.getString("cardreturningdetails")));
							bean.setCardreturningstate(CommonTool.FormatInteger(rs.getInt("cardreturningstate")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	//添加预约信息
	public boolean addorders(String callbillid,String orderconply,String orderphone,String orderusermf,String orderusertrh,String orderusermr,String orderproject,String ordertime,
			String ordertimes,String orderdetail,String complydetail){
		boolean bte=true;
		String sql;
		sql="insert orders(calluserid,callbillid,orderphone,orderconply,orderusermf,orderusertrh,orderusermr,orderproject,ordertime,ordertimes,orderdetail,complydetail,orderstate) values(" 
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(orderphone)
			+","
			+CommonTool.quotedStr(orderconply)
			+","
			+CommonTool.quotedStr(orderusermf)
			+","
			+CommonTool.quotedStr(orderusertrh)
			+","
			+CommonTool.quotedStr(orderusermr)
			+","
			+CommonTool.quotedStr(orderproject)
			+","
			+CommonTool.quotedStr(ordertime)
			+","
			+CommonTool.quotedStr(ordertimes)
			+","
			+CommonTool.quotedStr(orderdetail)
			+","
			+CommonTool.quotedStr(complydetail)
			+","
			+CommonTool.FormatInteger(3) +")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	
	
	//确认预约
	public boolean updatorderss(String ordertimess,String complydetails){
		boolean bte=true;
		String sql;
		sql="update orders set ordertimes='"+ordertimess+"',complydetail='"+complydetails+"' where orderstate=3 ";
		sql=sql+"update orders set orderstate=4 where orderstate=3";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	//确认短息结案单据
	public boolean submitMessage(String callbillid){
		boolean bte=true;
		String  sql="update callwaiting set callstate='3' where agentnum = '短信' and callbillid='"+callbillid+"'";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	public CallsWatingBean callsWatingBeanes(){
		try{
		String strSql = "select callon,calledon,agentnum,offertime,callstate from callwaiting";
		 AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>()
			{
				public CallsWatingBean anlyResultSet(ResultSet rs)
				{
					CallsWatingBean bean = new CallsWatingBean();
					try
					{
						if(rs != null && rs.next()==true)
						{
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
							bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
							bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
							bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
							bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						}				
					}
					catch(Exception e)
					{
						e.printStackTrace();
						bean =  null;
					}
					return bean;
				}
			};
			CallsWatingBean returnRecord= (CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql,analysis);
			return returnRecord;
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		return null;
	}
		
	}
	
	/**
	 * 检查是否有来电
	 * @return
	 * @throws Exception
	 */
	public boolean isCallExist() throws Exception {
	    String strSql = "select callon,calledon,agentnum,offertime from callwaiting where  callstate=0  and calluserid='"+CommonTool.getLoginInfo("USERID")+"' ";
		AnlyResultSet<Boolean> analysis = new AnlyResultSet<Boolean>() {
			public Boolean anlyResultSet(ResultSet rs) {
				boolean returnValue = false;
				try {
					if (rs != null && rs.next() == true) {
						returnValue = true;
					} else {
						returnValue = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					returnValue = false;
				}
				return returnValue;
			}
		};
		return (Boolean) this.amn_Dao.executeQuery_ex(strSql, analysis);
	}
	
	public boolean selects(){
		boolean bte=true;
		String qur="update callwaiting set callstate=2 where callstate=1 ";
		try {
			bte=this.amn_Dao.executeSql(qur);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	
	public List<CallsWatingBean> selectuser(){
	    	String queryStr;
	    	queryStr="select staffno,staffname from staffinfo,sysuserinfo where frominnerno=manageno and  userno="+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"));
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCalluserid(CommonTool.FormatString(rs.getString("staffno")));
							bean.setStaffname(CommonTool.FormatString(rs.getString("staffname")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	public CallsWatingBean selectcall(){
    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
		"from callwaiting" +
		"	left join memberinfo on callon=membermphone" +
		"	left join cardinfo on memberno=cardno" +
		"	left join cardtypenameinfo on cardtypeno=cardtype" +
		"   where calluserid='"+CommonTool.getLoginInfo("USERID")+"'";
    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
			public CallsWatingBean anlyResultSet(ResultSet rs) {
				CallsWatingBean bean=new CallsWatingBean();
				try {
					
					if(rs!=null&&rs.next()){
						
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
}
	//查询来电信息
	public CallsWatingBean selectcallswatingbytime(){
	    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
			"from callwaiting" +
			"	left join memberinfo on callon=membermphone" +
			"	left join cardinfo on memberno=cardno" +
			"	left join cardtypenameinfo on cardtypeno=cardtype" +
			"   where calluserid='"+CommonTool.getLoginInfo("USERID")+"' and  callstate=0 ";
	    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
				public CallsWatingBean anlyResultSet(ResultSet rs) {
					CallsWatingBean bean=new CallsWatingBean();
					try {
						
						if(rs!=null&&rs.next()){
							
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
							bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
							bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
							bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
							bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
							bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
							bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
							bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
							bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
							bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
							bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
							bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
							
							
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return bean;
				}
			};
			CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
			analysis=null;
			strSql="  update callwaiting set callstate=1 	where  calluserid='"+CommonTool.getLoginInfo("USERID")+"' and callstate=0   ";
			this.amn_Dao.executeSql(strSql);
			return result;
	}
	/**
	 * 查询投诉信息
	 * @return
	 */
	public List<CallsWatingBean> selectpeiier(String callbillno){
	    	String queryStr;
	    	queryStr="select callon,b.calluserid,peiiercontent,peiierdetails,peiiertime,peiierstate,peiierid from callwaiting" +
	    			" a,peiier b where a.callbillid=b.callbillid and b.callbillid='"+callbillno+"'";
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setPeiierid(CommonTool.FormatInteger(rs.getInt("peiierid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setPeiiercontent(CommonTool.FormatString(rs.getString("peiiercontent")));
							bean.setPeiierdetails(CommonTool.FormatString(rs.getString("peiierdetails")));
							bean.setPeiiertime(setTimeMaskes(CommonTool.FormatString(rs.getString("peiiertime"))));
							bean.setPeiierstate(CommonTool.FormatInteger(rs.getInt("peiierstate")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	
	
	
	/**
	 * 查询咨询信息
	 * @return
	 */
	public List<CallsWatingBean> selectRefer(String callbillno){
	    	String queryStr;
	    	queryStr="select callon,b.calluserid,refertime,refercomply,refercards,referproject,referdetails,feferstate,referid  from " +
	    			"callwaiting a,refer b where a.callbillid=b.callbillid and b.callbillid='"+callbillno+"'";
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setRefertime(setTimeMaskes(CommonTool.FormatString(rs.getString("refertime"))));
							bean.setRefercomply(CommonTool.FormatString(rs.getString("refercomply")));
							bean.setRefercards(CommonTool.FormatString(rs.getString("refercards")));
							bean.setReferproject(CommonTool.FormatString(rs.getString("referproject")));
							bean.setReferdetails(CommonTool.FormatString(rs.getString("referdetails")));
							bean.setFeferstate(CommonTool.FormatInteger(rs.getInt("feferstate")));
							bean.setReferid(CommonTool.FormatInteger(rs.getInt("referid")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	
	public CallsWatingBean loadcallswatingbytimes(String callbillno){
    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
    			"from callwaiting" +
    			"	left join memberinfo on callon=membermphone" +
    			"	left join cardinfo on memberno=cardno" +
    			"	left join cardtypenameinfo on cardtypeno=cardtype" +
    			"   where callbillid='"+callbillno+"' ";
    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
			public CallsWatingBean anlyResultSet(ResultSet rs) {
				CallsWatingBean bean=new CallsWatingBean();
				try {
					
					if(rs!=null&&rs.next()){
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
						
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
}
	
	public CallsWatingBean loadcallswatingbytime(String callbillno){
    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,calledon,agentnum,offertime,callstate,callmark,memberno,cardtype,membername,membermphone " +
    			"from callwaiting" +
    			"	left join memberinfo on callon=membermphone" +
    			"	left join cardinfo on memberno=cardno" +
    			"	left join cardtypenameinfo on cardtypeno=cardtype" +
    			"   where calluserid='"+CommonTool.getLoginInfo("USERID")+"' and  callbillid='"+callbillno+"' ";
    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
			public CallsWatingBean anlyResultSet(ResultSet rs) {
				CallsWatingBean bean=new CallsWatingBean();
				try {
					
					if(rs!=null&&rs.next()){
						
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
}
	
	public CallsWatingBean loadMessages(String callbillno){
    	String strSql="select callbillid,calluserid,calltype,cardtypename,callid,callon,offertime,callstate,agentnum,callmark,memberno,cardtype,membername,membermphone " +
    			"from callwaiting" +
    			"	left join memberinfo on callon=membermphone" +
    			"	left join cardinfo on memberno=cardno" +
    			"	left join cardtypenameinfo on cardtypeno=cardtype" +
    			"   where callbillid='"+callbillno+"' ";
    	AnlyResultSet<CallsWatingBean> analysis = new AnlyResultSet<CallsWatingBean>() {
			public CallsWatingBean anlyResultSet(ResultSet rs) {
				CallsWatingBean bean=new CallsWatingBean();
				try {
					
					if(rs!=null&&rs.next()){
						bean.setIsmessages("3");//用来判断是从短信窗口进入
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setStaffname(CommonTool.getLoginInfo("USERNAME"));
						bean.setCalltype(CommonTool.FormatInteger(rs.getInt("calltype")));
						bean.setCallid(CommonTool.FormatInteger(rs.getInt("callid")));
						bean.setCall_no(CommonTool.FormatString(rs.getString("callon")));
//						bean.setCalled_no(CommonTool.FormatString(rs.getString("calledon")));
						bean.setAgent_num(CommonTool.FormatString(rs.getString("agentnum")));
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("offertime"))));
						bean.setCall_state(CommonTool.FormatInteger(rs.getInt("callstate")));
						bean.setCallmark(CommonTool.FormatString(rs.getString("callmark")));
						bean.setMembernotocard(CommonTool.FormatString(rs.getString("memberno")));
						bean.setCardtype(CommonTool.FormatString(rs.getString("cardtypename")));
						bean.setMembername(CommonTool.FormatString(rs.getString("membername")));
						bean.setMembermphone(CommonTool.FormatString(rs.getString("membermphone")));
						
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return bean;
			}
		};
		CallsWatingBean result  =(CallsWatingBean)this.amn_Dao.executeQuery_ex(strSql, analysis);
		analysis=null;
		return result;
}
	
	
	// 获得当前的日期
	// 返回值：20081010
	public static String getCurrDate() {
		SimpleDateFormat objStdFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		Date CurrDate = new Date(System.currentTimeMillis());
		return objStdFormat.format(CurrDate);
	}
	public static void main(String[] args) {
		System.out.println(getCurrDate());
	}
	
	
	//查询来电信息
	public List<CallsWatingBean> selectcallswating(String callbillid){
			String queryStr= "update callwaiting set callbillid='"+callbillid+"' where callstate=0 and calluserid='"+CommonTool.getLoginInfo("USERID")+"' ";
			this.amn_Dao.executeSql(queryStr);
	    	queryStr="select billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,orderdetail,b.orderusermf,b.orderusertrh,b.orderusermr " +
	    			"from callwaiting a,orders b,callwaiting c" +
	    			" where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callstate=0  " +
	    			" and a.callon=c.callon and c.callbillid=b.callbillid";
	    	queryStr=queryStr+" union select billtype='咨询', c.calluserid,b.callbillid,ordertime=b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')" +
	    			"+isnull(refercards,'')+isnull(referproject,''),orderdetail=isnull(referdetails,''),orderusermf='',orderusertrh='',orderusermr='' " +
	    			"from callwaiting a,refer b,callwaiting c" +
	    			" where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callstate=0  " +
	    			" and a.callon=c.callon and c.callbillid=b.callbillid";
	    	queryStr=queryStr+" union select billtype='投诉', c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,''),orderdetail=isnull(peiierdetails,'')," +
	    			"orderusermf='',orderusertrh='',orderusermr='' from callwaiting a,peiier b,callwaiting c  " +
	    			"where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callstate=0   and a.callon=c.callon and c.callbillid=b.callbillid";
	    	queryStr=queryStr+" union select billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,''), " +
	    			"orderdetail=isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''from callwaiting a,cardreturning b,callwaiting c " +
	    			"where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and  a.callstate=0  and a.callon=c.callon and c.callbillid=b.callbillid";
	    	AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
				List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
				String strtProject="";
				String strProjectName="";
				public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
					try {
						while(rs!=null&&rs.next()){
							CallsWatingBean bean=new CallsWatingBean();
							bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
							bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
							bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
							bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
							strtProject =CommonTool.FormatString(rs.getString("orderproject"));
							strProjectName="";
							if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
							{
								
								String[] lsprj=strtProject.split(";");
								if(lsprj!=null && lsprj.length>0)
								{
									for(int i=0;i<lsprj.length;i++)
									{
										if(lsprj[i].toString().equals("300"))
										{
											strProjectName=strProjectName+"[美发项目]";
										}
										else if(lsprj[i].toString().equals("600"))
										{
											strProjectName=strProjectName+"[烫染项目]";
										}
										else if(lsprj[i].toString().equals("400"))
										{
											strProjectName=strProjectName+"[美容项目]";
										}

									}
								}
								bean.setOrderproject(CommonTool.FormatString(strProjectName));
							}
							bean.setOrderproject(strtProject);
							bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
							bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
							bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
							ls.add(bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return ls;
				}
			};
			List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
			analysis=null;
			return ls;
	}
	//mgx start
	public List<CallsWatingBean> loadMessageCall(String callbillid){
		String  queryStr="select billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,orderdetail,b.orderusermf,b.orderusertrh,b.orderusermr " +
			"from callwaiting a,orders b,callwaiting c" +
			" where  a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callbillid='"+callbillid+"' and a.callon=c.callon and c.callbillid=b.callbillid";
		
			queryStr=queryStr+" union select billtype='咨询', c.calluserid,b.callbillid,ordertime=b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')" +
			"+isnull(refercards,'')+isnull(referproject,''),orderdetail=isnull(referdetails,''),orderusermf='',orderusertrh='',orderusermr='' " +
			"from callwaiting a,refer b,callwaiting c" +
			" where  a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callbillid='"+callbillid+"' and  a.callon=c.callon and c.callbillid=b.callbillid";
			
			queryStr=queryStr+" union select billtype='投诉', c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,''),orderdetail=isnull(peiierdetails,'')," +
			"orderusermf='',orderusertrh='',orderusermr='' from callwaiting a,peiier b,callwaiting c  " +
			"where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callbillid='"+callbillid+"' and  a.callon=c.callon and c.callbillid=b.callbillid";
			
			queryStr=queryStr+" union select billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,''), " +
			"orderdetail=isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''from callwaiting a,cardreturning b,callwaiting c " +
			" where a.calluserid='"+CommonTool.getLoginInfo("USERID")+"' and a.callbillid='"+callbillid+"' and  a.callon=c.callon and c.callbillid=b.callbillid";
		    AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			String strtProject="";
			String strProjectName="";
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setIsmessages("3");//用来判断是从短信窗口进入
						bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						strtProject =CommonTool.FormatString(rs.getString("orderproject"));
						bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
						bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
						strProjectName="";
						if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
						{
							String[] lsprj=strtProject.split(";");
							
							if(lsprj!=null && lsprj.length>0)
							{
								for(int i=0;i<lsprj.length;i++)
								{
									if(lsprj[i].toString().equals("300"))
									{
										strProjectName=strProjectName+"[美发项目]";
									}
									else if(lsprj[i].toString().equals("600"))
									{
										strProjectName=strProjectName+"[烫染项目]";
									}
									else if(lsprj[i].toString().equals("400"))
									{
										strProjectName=strProjectName+"[美容项目]";
									}

								}
							}
							bean.setOrderproject(CommonTool.FormatString(strProjectName));
						}
						bean.setOrderproject(strtProject);
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
}
	//mgx end
	public List<CallsWatingBean> loadcallswating(String callbillid){
		String  queryStr="select billtype='预约',c.calluserid,b.callbillid,b.ordertime,b.orderstate,b.orderproject,orderdetail,b.orderusermf,b.orderusertrh,b.orderusermr " +
						"from callwaiting a, orders b,callwaiting c" +
								" where a.callbillid='"+callbillid+"' and a.callon=c.callon and c.callbillid=b.callbillid";
		    	queryStr=queryStr+" union select billtype='咨询', c.calluserid,b.callbillid,b.refertime,orderstate=b.feferstate,orderproject=isnull(refercomply,'')+isnull(refercards,'')+isnull(referproject,''),orderdetail=isnull(referdetails,''),orderusermf='',orderusertrh='',orderusermr='' " +
						"from callwaiting a,refer b,callwaiting c" +
						" where  a.callbillid='"+callbillid+"' and a.callon=c.callon and c.callbillid=b.callbillid ";
		    	queryStr=queryStr+"  union select billtype='投诉', c.calluserid,b.callbillid,ordertime=b.peiiertime,orderstate=b.peiierstate,orderproject=isnull(peiiercontent,''),orderdetail=isnull(peiierdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''" +
		    			"from callwaiting a,peiier b,callwaiting c " +
		    			"where a.callbillid='"+callbillid+"' and a.callon=c.callon and c.callbillid=b.callbillid";
		    	queryStr=queryStr+"  union select billtype='退卡', c.calluserid,b.callbillid,ordertime=b.cardreturningtime,orderstate=b.cardreturningstate,orderproject=isnull(cardreturningcontent,''),orderdetail=isnull(cardreturningdetails,''),orderusermf='' ,orderusertrh='',orderusermr=''" +
		    			"from callwaiting a,cardreturning b,callwaiting c " +
		    			"where a.callbillid='"+callbillid+"' and a.callon=c.callon and c.callbillid=b.callbillid";
		    AnlyResultSet<List<CallsWatingBean>> analysis = new AnlyResultSet<List<CallsWatingBean>>() {
			List<CallsWatingBean> ls=new ArrayList<CallsWatingBean>();
			String strtProject="";
			String strProjectName="";
			public List<CallsWatingBean> anlyResultSet(ResultSet rs) {
				try {
					while(rs!=null&&rs.next()){
						CallsWatingBean bean=new CallsWatingBean();
						bean.setBilltype(CommonTool.FormatString(rs.getString("billtype")));
						bean.setCalluserid(CommonTool.FormatString(rs.getString("calluserid")));
						bean.setCallbillid(CommonTool.FormatString(rs.getString("callbillid")));
						strtProject =CommonTool.FormatString(rs.getString("orderproject"));
						bean.setOrderdetail(CommonTool.FormatString(rs.getString("orderdetail")));
						bean.setBillofUser("["+CommonTool.FormatString(rs.getString("orderusermf"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusertrh"))+"]"+"["+CommonTool.FormatString(rs.getString("orderusermr"))+"]");
						strProjectName="";
						if(!strtProject.equals("") && CommonTool.FormatString(rs.getString("billtype")).equals("预约"))
						{
							String[] lsprj=strtProject.split(";");
							
							if(lsprj!=null && lsprj.length>0)
							{
								for(int i=0;i<lsprj.length;i++)
								{
									if(lsprj[i].toString().equals("300"))
									{
										strProjectName=strProjectName+"[美发项目]";
									}
									else if(lsprj[i].toString().equals("600"))
									{
										strProjectName=strProjectName+"[烫染项目]";
									}
									else if(lsprj[i].toString().equals("400"))
									{
										strProjectName=strProjectName+"[美容项目]";
									}

								}
							}
							bean.setOrderproject(CommonTool.FormatString(strProjectName));
						}
						bean.setOrderproject(strtProject);
						bean.setOffer_time(setTimeMask(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOffer_timeses(setTimeMasks(CommonTool.FormatString(rs.getString("ordertime"))));
						bean.setOrderstate(CommonTool.FormatInteger(rs.getInt("orderstate")));
						ls.add(bean);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return ls;
			}
		};
		List<CallsWatingBean> ls  =(List<CallsWatingBean> )this.amn_Dao.executeQuery_ex(queryStr, analysis);
		analysis=null;
		return ls;
}
	
	
	
	public boolean handCallMark(String strCallBillno,String strMark)
	{
		try
		{
			String strSql=" update callwaiting set callmark='"+strMark+"'  where callbillid='"+strCallBillno+"' ";
			return this.amn_Dao.executeSql(strSql);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加咨询信息
	 * @param callbillid
	 * @param refercomply
	 * @param refercards
	 * @param referproject
	 * @param referdetails
	 * @return
	 */
	public boolean addrefer(String callbillid,String refercomply,String refercards,String referproject,String referdetails){
		boolean bte=true;
		String sql="insert refer(callbillid,calluserid,refertime,refercomply,refercards,referproject,referdetails,feferstate) values(" 
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(refercomply)
			+","
			+CommonTool.quotedStr(refercards)
			+","
			+CommonTool.quotedStr(referproject)
			+","
			+CommonTool.quotedStr(referdetails)
			+","
			+CommonTool.FormatInteger(1) +")";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
	}
	
	//添加咨询信息（结案）
	public boolean addrefers(String callbillid,String refercomply,String refercards,String referproject,String referdetails){
		boolean bte=true;
		String sql="insert refer(callbillid,calluserid,refertime,refercomply,refercards,referproject,referdetails,feferstate) values(" 
			+CommonTool.quotedStr(callbillid)
			+","
			+CommonTool.quotedStr(CommonTool.getLoginInfo("USERID"))
			+","
			+CommonTool.quotedStr(getCurrDate())
			+","
			+CommonTool.quotedStr(refercomply)
			+","
			+CommonTool.quotedStr(refercards)
			+","
			+CommonTool.quotedStr(referproject)
			+","
			+CommonTool.quotedStr(referdetails)
			+","
			+CommonTool.FormatInteger(0) +")";
		sql=sql+" update refer set feferstate=3 where feferstate=0";
		sql=sql+" update refer set feferstate=3 where feferstate=1 and callbillid='"+callbillid+"'";
		try {
			bte=this.amn_Dao.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			bte=false;
		}
		return bte;
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
	public static String setTimeMask(String strTime) {
		if (strTime == null || strTime.equals("")) {
			return "";
		} else {
			if(strTime.length()!=19)
			{
				return strTime;
			}
			return strTime.substring(0, 4) +"-"+ strTime.substring(5, 7)
					+"-"+strTime.substring(8, 10);
			}
		}
	public static String setTimeMasks(String strTime) {
		if (strTime == null || strTime.equals("")) {
			return "";
		} else {
			if(strTime.length()!=19)
			{
				return strTime;
			}
			return  strTime.substring(11,13)
					+":"+strTime.substring(14,16)+":"+strTime.substring(17,19);
			}
		}
	public static String setTimeMaskes(String strTime) {
		if (strTime == null || strTime.equals("")) {
			return "";
		} else {
			if(strTime.length()!=19)
			{
				return strTime;
			}
			return  strTime.substring(0, 4) +"-"+ strTime.substring(5, 7)
			+"-"+strTime.substring(8, 10)+"  "+strTime.substring(11,13)
					+":"+strTime.substring(14,16)+":"+strTime.substring(17,19);
			}
		}
	public static String settime(String strTime) {
		if (strTime == null || strTime.equals("")) {
			return "";
		} else {
			if(strTime.length()!=19)
			{
				return strTime;
			}
			return  strTime.substring(0, 4) +"_"+ strTime.substring(5, 7)
			+"_"+strTime.substring(8, 10)+"_"+strTime.substring(11,13)
					+"_"+strTime.substring(14,16)+"_"+strTime.substring(17,19);
			}
		}
}
