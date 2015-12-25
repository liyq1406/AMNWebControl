package com.amani.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.amani.bean.FaceId_EmployeeBean;
import com.amani.bean.FaceId_RecordBean;
import com.amani.model.Cardaccountchangehistory;
import com.amani.model.Cardinfo;
import com.amani.model.Cardproaccount;
import com.amani.model.Cardtransactionhistory;
import com.amani.model.Companyinfo;
import com.amani.model.Memberinfo;
import com.amani.model.Staffinfo;


@WebService
public interface ICommonService {
 
		@WebMethod(operationName="loadCompNameById")// 获取名店名称
		@WebResult(name = "result")
		public String loadCompNameById();  
		
		
		@WebMethod(operationName="loadCardinfo")//获取卡信息
		@WebResult(name = "result")
		public Cardinfo loadCardinfo(String strCardNo);  
		
		@WebMethod(operationName="loadCardinfobyCardNo")//获取卡信息(compid,cardno)
		@WebResult(name = "result")
		public Cardinfo loadCardinfobyCardNo(String strCompId,String strCardNo);  
		
		@WebMethod(operationName="loadTransHistoryByCardNo")//获取卡交易历史
		@WebResult(name = "result")
		public Cardtransactionhistory  loadTransHistoryByCardNo(String strCardNo,String strBillType,String strBillId,String strAccountType);  
		
		
		@WebMethod(operationName="loadTransHistoryByCardNo")//获取卡交易历史
		@WebResult(name = "result")
		public List<Cardtransactionhistory> loadTransHistoryByCardNo(String strCardNo);  
		
		
		@WebMethod(operationName="loadProInfosByCardNo")//根据会员卡号获得会员疗程信息
		@WebResult(name = "result")
		public List<Cardproaccount> loadProInfosByCardNo(String strCompId,String strCardNo);  
		
		@WebMethod(operationName="loadCardaccountchangehistory")//获取账户列表
		@WebResult(name = "result")
		public List<Cardaccountchangehistory> loadCardaccountchangehistory(String strCardNo);  
		
		@WebMethod(operationName="searchCardinfo")//收索卡基本信息
		@WebResult(name = "result")
		public List<Cardinfo> searchCardinfo(String searchMemberCompIdKey,String searchMemberNoKey, String searchMemberNameKey,String searchMemberPhoneKey,String searchMemberPCIDKey); 
		
		@WebMethod(operationName="validataCardType")//判断卡类型
		@WebResult(name = "result")
		public boolean validataCardType();  
		
		
		
		@WebMethod(operationName="loadMemberNoByPhone")
		@WebResult(name = "result")
		public boolean loadMemberNoByPhone(String strPhoneNo,String strUserCardno);  
		
		
		
		@WebMethod(operationName="sendMessageTone")//发微信验证码
		@WebResult(name = "result")
		public boolean sendMessageTone(String strPhoneNo,String strSeqno); 
		
		@WebMethod(operationName="loadMemberinfoByCompId")//获取会员信息
		@WebResult(name = "result")
		public Memberinfo loadMemberinfoByCompId(String strCompId,String strMemberId); 
		
		@WebMethod(operationName="loadCompanyName")//获取门店名称
		@WebResult(name = "result")
		public List<Companyinfo> loadCompanyName();
		
		@WebMethod(operationName="loadCompCoord")//获取门店坐标
		@WebResult(name = "result")
		 public List<Companyinfo> loadCompCoord();
		
		@WebMethod(operationName="loadCompStaff")
		@WebResult(name = "result")
		 public List<Staffinfo> loadCompStaff(String strCompId);
		
		
		@WebMethod(operationName="Addyuyueinfo")//写入预约信息到主数据库
		@WebResult(name = "result")
		public boolean Addyuyueinfo(String userid,String orderconply,String orderphone,String orderuser,String orderusertrh,
				String orderproject,String ordertime,String ordertimes,String complydetail);
		
		@WebMethod(operationName="UpdatAaccountdebts")//会员获取积分
		@WebResult(name = "result")
		public boolean UpdatAaccountdebts(String strCardNo,double strAccountbalance,String strCardvesting);
		
		
		@WebMethod(operationName="loadOrderInfo")//生成预约信息
		@WebResult(name = "result")
		public boolean loadOrderInfo(String strSql);
		
		
		@WebMethod(operationName="loadCardInfoBySpad")//锦囊PAD需要的接口(获取会员信息)
		@WebResult(name = "result")
		public String loadCardInfoBySpad(String strCardNo,String strMemberPhone);
		
		@WebMethod(operationName="downLoadSpadBillInfo")//锦囊PAD需要的接口(获取小单信息)
		@WebResult(name = "result")
		//public int downLoadSpadBillInfo(String strMBillInfo,String strDBillInfo);
		public int downLoadSpadBillInfo(String strBillInfo) ;
		
		@WebMethod(operationName="downLoadSpadStaffInfo")//锦囊PAD需要的接口(获取员工信息)
		@WebResult(name = "result")
		public String downLoadSpadStaffInfo(String strCompId);
		
		
		@WebMethod(operationName="downLoadSpadProjectClass")//锦囊PAD需要的接口(获取项目大类)
		@WebResult(name = "result")
		public String downLoadSpadProjectClass();
		
		@WebMethod(operationName="downLoadSpadPostionInfo")//锦囊PAD需要的接口(获取职位)
		@WebResult(name = "result")
		public String downLoadSpadPostionInfo();
		
		@WebMethod(operationName="downLoadSpadGoodsClass")//锦囊PAD需要的接口(获取项目大类)
		@WebResult(name = "result")
		public String downLoadSpadGoodsClass();
		
		@WebMethod(operationName="downLoadSpadProjectInfo")//锦囊PAD需要的接口(获取项目)
		@WebResult(name = "result")
		public String downLoadSpadProjectInfo(String strCompId);
		
		@WebMethod(operationName="downLoadSpadGoodsInfo")//锦囊PAD需要的接口(获取产品)
		@WebResult(name = "result")
		public String downLoadSpadGoodsInfo(String strCompId);
		
		/********************考勤机********************/
		@WebMethod(operationName="CKT_RegisterNet_local")//连接考勤机
		@WebResult(name = "result")
		public int CKT_RegisterNet_local( long sno,String addrSrv );
		
		
		/********************考勤机********************/
		@WebMethod(operationName="CKT_NetDaemon_local")//监听考勤机
		@WebResult(name = "result")
		public int CKT_NetDaemon_local(  );
		
		/********************考勤机********************/
		@WebMethod(operationName="CKT_GetFPTemplateSaveFile_local")//下载考勤
		@WebResult(name = "result")
		public int CKT_GetFPTemplateSaveFile_local(long sno,int personId,int fPid,String fpDateFilename );
		
		@WebMethod(operationName="CKT_GetFPRawData_local")//读取指纹字符
		@WebResult(name = "result")
		public String  CKT_GetFPRawData_local(long sno,int personId,int fPid);
	
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//上传指纹文件
		@WebResult(name = "result")
		public int CKT_PutFPTemplateLoadFile_local(long sno,int personId,int fPid,String fpDateFilename );
		
		
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//新增员工
		@WebResult(name = "result")
		public int CKT_ModifyPersonInfo_local(long sno,int personid,String password,int cardno,String personname,int dept,int group,int kqoption,int fpmark,int other );
		
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//删除员工
		@WebResult(name = "result")
		public int CKT_DeletePersonInfo_local(long sno,int person );
		
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//获取考勤
		@WebResult(name = "result")
		public String GetClockingRecordProgress_localbyDate(long sno ,String strDate);
		
		
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//断开指纹仪
		@WebResult(name = "result")
		public void CKT_Disconnect_local( );
		
		@WebMethod(operationName="CKT_PutFPTemplateLoadFile_local")	//获取考勤记录
		@WebResult(name = "result")
		public String CKT_ListPersonInfoEx_local(long sno );
		
		/******************人脸考勤********************/
		@WebMethod(operationName="loadFaceIpByComeId")// 获取门店考勤机Ip
		@WebResult(name = "result")
		public String loadFaceIpByComeId(String strCompid);  
		
		@WebMethod(operationName="loadEmpFaceInfo")// 获取人脸数据
		@WebResult(name = "result")
		public FaceId_EmployeeBean loadEmpFaceInfo(String strFaceIp,String strFaceId);  
		
		@WebMethod(operationName="uploadEmpFaceInfo")// 上传人脸数据存档
		@WebResult(name = "result")
		public boolean uploadEmpFaceInfo(String strFromFaceIp,String strFromFaceId,String strToFaceIp,String strToEmpId,String strEmpName);  
		
		
		@WebMethod(operationName="postEmpFaceInfo")// 人脸数据存档
		@WebResult(name = "result")
		public boolean postEmpFaceInfo(String strFaceIp,String strFaceId);  
		
		
		@WebMethod(operationName="deleteEmpFaceInfo")// 人脸数据删除
		@WebResult(name = "result")
		public boolean deleteEmpFaceInfo(String strFaceIp,String strFaceId);  
		
		
		@WebMethod(operationName="loadAllEmpFaceInfo")// 获取所有人脸数据
		@WebResult(name = "result")
		public List<FaceId_EmployeeBean> loadAllEmpFaceInfo(String strFaceIp);  
		
		
		@WebMethod(operationName="loadEmpKqInfo")// 获取所有人脸考勤数据
		@WebResult(name = "result")
		public List<FaceId_RecordBean> loadEmpKqInfo(String strFaceIp,String strFromDate,String strToDate);
		
		@WebMethod(operationName="uploadEmpFaceInfoAllInfo")// 上传人脸数据存档
		@WebResult(name = "result")
		public boolean uploadEmpFaceInfoAllInfo(String strFaceIp,String strEmpId,String strEmpName,String strCardNum,String strCalid, String[] face_data);  
		
		@WebMethod(operationName="updateFaceOpenRight")	//更新开门权限
		@WebResult(name = "result")
		public boolean updateFaceOpenRight(String strFromFaceIp,String strFromFaceId,String strFaceId,String strToFaceIp);
}
