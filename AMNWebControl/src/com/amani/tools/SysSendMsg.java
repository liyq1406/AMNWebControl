package com.amani.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amani.bean.sendMsgBean;
import com.jianzhou.sdk.BusinessService;
@Repository("sysSendMsg")
public class SysSendMsg {
@Autowired
protected DataTool dataTool;
	/**
	 * 批量发送信息 destMobile 	多个手机号码用 ; 分割 建议一次提交1000左右的号码
	 * @return
	 */
	public  String sendMsg(String strCompId,String destMobile,String msgText) throws Exception{
		msgText=msgText+"【阿玛尼护肤造型】";
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue= loadSendMsg(bs.sendBatchMessage(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP069")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP070")),destMobile,msgText));
		bs=null;
		return returnValue;
	}
	
	
	public  String sendFastMsg(String strCompId,String destMobile,String msgText) throws Exception{
		msgText=msgText+"【阿玛尼护肤造型】";
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue= loadSendMsg(bs.sendBatchMessage(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP110")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP111")),destMobile,msgText));
		bs=null;
		return returnValue;
	}
	
	/**
	 * 定时批量发送短信 destMobile 	多个手机号码用 ; 分割 建议一次提交1000左右的号码
	 * sendDateTime 定时发送的日期和时间 yyyymmddhhmmss
	 * @return
	 */
	public  String sendMsgByTime(String strCompId,String destMobile,String msgText,String sendDateTime) throws Exception{
		msgText=msgText+"【阿玛尼护肤造型】";
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue =loadSendMsg(bs.sendTimelyMessage(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP069")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP070")),sendDateTime,destMobile,msgText));
		bs=null;
		return returnValue;
	}
	
	
	/**
	 * 定时批量发送短信 sendDateTime 定时发送的日期和时间yyyymmddhhmmss
	 *  destMobile 	目标手机号，小灵通要加区号；多个手机号码用;分割建议一次提交1000左右的号码，多次提交
	 * @return
	 */
	public  String sendTimelyMessage(String strCompId,String sendDateTime,String destMobile,String msgText) throws Exception{
		msgText=msgText+"【阿玛尼护肤造型】";
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue = loadSendMsg(bs.sendTimelyMessage(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP069")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP070")),sendDateTime,destMobile,msgText));
		bs=null;
		return returnValue;
	}
	
	/**
	 * 定时批量发送个性短信 destMobile 	多个手机号码用 || 分割 建议一次提交1000左右的号码
	 * 多个内容用||分隔 号码和内容||分隔数量必须相等  相同内容禁止使用此方法
	 * @return
	 */
	public  String sendMsgByPerson(String strCompId,String destMobile,String msgText) throws Exception{
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue = loadSendMsg(bs.sendPersonalMessages(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP069")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP070")),destMobile,msgText));
		bs=null;
		return returnValue;
	}
		
	public  String loadSendMsg(int sendType)
	{
		if(sendType>=0)
		{
			return "正常发送!";
		}
		else if(sendType==-1)
		{
			return "余额不足!";
		}
		else if(sendType==-2)
		{
			return "帐号或密码错误!";
		}
		else if(sendType==-3)
		{
			return "连接服务商失败!";
		}
		else if(sendType==-4)
		{
			return "超时!";
		}
		else if(sendType==-5)
		{
			return "其他错误!";
		}
		else if(sendType==-6)
		{
			return "短信内容为空!";
		}
		else if(sendType==-7)
		{
			return "目标号码为空!";
		}
		else if(sendType==-8)
		{
			return "用户通道设置不对，需要设置三个通道!";
		}
		else if(sendType==-9)
		{
			return "捕获未知异常!";
		}
		else if(sendType==-10)
		{
			return "超过最大定时时间限制!";
		}
		else if(sendType==-11)
		{
			return "目标号码在黑名单里!";
		}
		else if(sendType==-12)
		{
			return "消息内容包含禁用词语!";
		}
		else if(sendType==-13)
		{
			return "没有权限使用该网关!";
		}
		else if(sendType==-14)
		{
			return "找不到对应的Channel ID!";
		}
		else if(sendType==-17)
		{
			return "没有提交权限!";
		}
		else if(sendType==-20)
		{
			return "超速提交(一般为每秒一次提交)!";
		}
		return "";
	}
	/**
		 * 接收用户信息(包括余额等)
		 * @return
	*/
	public  String getUserInfo(String strCompId) throws Exception{
			BusinessService bs=new BusinessService();
			bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
			String returnValue = bs.getUserInfo(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP069")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP070")));
			bs=null;
			return returnValue; 
	}
	/**
		 * 接收上一行回复短信息
		 * @throws Exception
		 *
	 */
	//每条回复内容以”|”号分隔.第一为回复号码,第二为回复内容,第三个为回复日期.内容为unicode码,如\u4f60\u597d\u002c\u6d4b\u8bd5
	public  String getReceivedMsg(String strCompId) throws Exception{
			BusinessService bs=new BusinessService();
			bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
			String returnValue = bs.getReceivedMsg(CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP110")),CommonTool.FormatString(this.getDataTool().loadSysParam(strCompId,"SP111")), 0);
			bs=null;
			return returnValue;
	}
	
	public String getReceivedMsg(String strUserId,String strPwd)
	{
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
		String returnValue = bs.getReceivedMsg(strUserId,strPwd,0);
		bs=null;
		return returnValue;
	}

	public DataTool getDataTool() {
		return dataTool;
	}

	public void setDataTool(DataTool dataTool) {
		this.dataTool = dataTool;
	}
	
	/*public static void main(String[] args) {
		
		String strSendAMessage="亲爱的";
		String strSendBMessage=",”新手上路，你型我塑”阿玛尼大学生公益活动将于2013年10月24日正式举行，凭此短信和学生证前往武定店可享受相应服务，地址：静安区武定路1129号(近延平路) .消费时请出示该服务编号：";
		//String strSendBMessage=",”新手上路，你型我塑”阿玛尼大学生公益活动将于2013年10月24日正式举行，凭此短信和学生证前往常德店可享受相应服务，地址：普陀区长德路1168号 .消费时请出示该服务编号：";
		//String strSendBMessage=",”新手上路，你型我塑”阿玛尼大学生公益活动将于2013年10月24日正式举行，凭此短信和学生证前往宝龙店可享受相应服务，地址：闵行区漕宝路1467弄静安新城7区18号 .消费时请出示该服务编号：";
		String strSendCMessage="。切勿遗失或删除。备注：因活动报名异常火爆，请务必提前与客服预约并配合门店安排，以免造成混乱。客服热线：4006622818";
		String strSenPhone="";
		String strSendMessage="";
		SysSendMsg sysSend=new SysSendMsg();
		List<sendMsgBean> lsBean=sysSend.loadCompCoord();
		if(lsBean!=null && lsBean.size()>0)
		{
			for(int i=0;i<lsBean.size();i++)
			{
				if(strSenPhone.equals(""))
					strSenPhone=lsBean.get(i).getStrPhone();
				else
					strSenPhone=strSenPhone+"||"+lsBean.get(i).getStrPhone();
				
				if(strSendMessage.equals(""))
					strSendMessage=strSendAMessage+lsBean.get(i).getStrName()+strSendBMessage+lsBean.get(i).getStrSeqno()+strSendCMessage;
				else
					strSendMessage=strSendMessage+"||"+strSendAMessage+lsBean.get(i).getStrName()+strSendBMessage+lsBean.get(i).getStrSeqno()+strSendCMessage;
			}
		}
		System.out.println(strSenPhone);
		System.out.println(strSendMessage);
		
		BusinessService bs=new BusinessService();
		bs.setWebService("http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService");
//		String returnValue = sysSend.loadSendMsg(bs.sendPersonalMessages(CommonTool.FormatString("sdk_amani"),"163116118",strSenPhone,strSendMessage));
//		System.out.println(returnValue);
		bs=null;
		
	}*/

	
	public List<sendMsgBean> loadCompCoord()
	 {
		 try {

				WeixinTool wxTool = new WeixinTool();
				Connection con = null;
				ResultSet rs = null;
				Statement stmt = null;
				List<sendMsgBean> lsSendMsgBean=null;
				try {

					con = wxTool.getConnectionAmn();
					stmt = con.createStatement();
					lsSendMsgBean=new ArrayList();
					sendMsgBean record=null;
					String strSql = "select name,phone,seqno from wdd ";
					rs = wxTool.loadResultSet(con, stmt, strSql);
					while (rs != null && rs.next()) {
						record=new sendMsgBean();
						record.setStrName(CommonTool.FormatString(rs.getString("name")));
						record.setStrPhone(CommonTool.FormatString(rs.getString("phone")));
						record.setStrSeqno(CommonTool.FormatString(rs.getString("seqno")));
						lsSendMsgBean.add(record);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (stmt != null)
							stmt.close();
						stmt = null;
						if (rs != null)
							rs.close();
						rs = null;
						wxTool.closeConnection(con);
						con = null;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				return lsSendMsgBean;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}

	 }
	


}
