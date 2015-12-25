package com.amani.dll;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;


import com.amani.model.Personinfo;
import com.amani.tools.CommonTool;


public class FingerTool {

	//连接指纹仪
	public int CKT_RegisterNet_local( int sno,String addrSrv )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_RegisterNet");   
	    		n.setRetVal(Type.INT);    
	            int i = 0;   
	            n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.STRING, addrSrv);  
	            n.invoke();    
	    	    return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	//监听记录登入的考勤机序号
	public int CKT_NetDaemon_local( )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_NetDaemon");   
	    		n.setRetVal(Type.INT);  
	            n.invoke();      
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	//清楚指纹信息
	public int CKT_DeleteAllPersonInfo_local( int sno )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_DeleteAllPersonInfo");   
	    		n.setRetVal(Type.INT);    
	            int i = 0;   
	            n.setParameter(i++, Type.INT, "" + sno);   
	            n.invoke();    
	    	    return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	//创建一个线程监听本地指定的网络端口号
	public int 	CKT_NetDaemonWithPort_local( int port )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400.dll", "CKT_NetDaemonWithPort");   
	    		n.setRetVal(Type.INT);    
	            int i = 0;   
	            n.setParameter(i++, Type.INT, "" + port);   
	            n.invoke();    
	    	    return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}

	
	//清除考勤记录
	public int CKT_ClearClockingRecord_local( int sno,int type, int count )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_ClearClockingRecord");   
	    		n.setRetVal(Type.INT);    
	            int i = 0;   
	            n.setParameter(i++, Type.INT, "" + sno);   
	            n.setParameter(i++, Type.INT, "" + type);   
	            n.setParameter(i++, Type.INT, "" + count);   
	            n.invoke();    
	    	    return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	//下载指纹文件
	public int CKT_GetFPTemplateSaveFile_local(int sno,int personId,int fPid,String fpDateFilename )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_GetFPTemplateSaveFile");   
	    		n.setRetVal(Type.INT);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
		    	n.setParameter(i++, Type.INT, "" +personId);
		    	n.setParameter(i++, Type.INT, "" + fPid);  
		    	n.setParameter(i++, Type.STRING, fpDateFilename);
		        n.invoke();     
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	//读取指纹字符
	public String  CKT_GetFPRawData_local(int sno,int personId,int fPid)
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_GetFPRawData");   
	    		n.setRetVal(Type.STRING);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
		    	n.setParameter(i++, Type.INT, "" +personId);
		    	n.setParameter(i++, Type.INT, "" + fPid);  
		        n.invoke();   
		        System.out.print(n.getRetVal());
	            return n.getRetVal();    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return "";
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	
	//上传指纹文件
	public int CKT_PutFPTemplateLoadFile_local(int sno,int personId,int fPid,String fpDateFilename )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_PutFPTemplateLoadFile");   
	    		n.setRetVal(Type.INT);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
		    	n.setParameter(i++, Type.INT, "" +personId);
		    	n.setParameter(i++, Type.INT, "" + fPid);  
		    	n.setParameter(i++, Type.STRING, fpDateFilename);
		        n.invoke();     
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	
	//新增修改员工
	public int CKT_ModifyPersonInfo_local(int sno,Personinfo person )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_ModifyPersonInfo");   
	    		n.setRetVal(Type.INT);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getPersonID()));  
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(person.getPassword())); 
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getCardNo()));  
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(person.getUserName()));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getDept()));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getGroup()));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getKqOption()));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getFpMark()));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(person.getOther()));
	    		n.invoke();  
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	//新增员工
	public int CKT_ModifyPersonInfo_local(int sno,int personid,String password,int cardno,String personname,int dept,int group,int kqoption,int fpmark,int other )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_ModifyPersonInfo");   
	    		n.setRetVal(Type.INT);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(personid));  
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(password)); 
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(cardno));  
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(personname));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(dept));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(group));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(kqoption));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(fpmark));  
	    		n.setParameter(i++, Type.INT, "" + CommonTool.FormatInteger(other));
	    		n.invoke();  
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	
	
	//删除员工
	public int CKT_DeletePersonInfo_local(int sno,int person )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_DeletePersonInfo");   
	    		n.setRetVal(Type.INT);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.INT, "" + person);  
	    		n.setParameter(i++, Type.INT, "" + 255);
	    		n.invoke();  
	            return Integer.parseInt(n.getRetVal());    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return 0;
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	

	
	
	//获取考勤记录
	public String GetClockingRecordProgress_local(int sno )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_GetClockingRecordEx");   
	    		n.setRetVal(Type.STRING);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
		        n.invoke();     
	            return n.getRetVal().toString();    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return "";
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	public String GetClockingRecordProgress_localbyDate(int sno ,String strDate)
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_GetClockingRecordEx");   
	    		n.setRetVal(Type.STRING);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(strDate));  
		        n.invoke();     
	            return n.getRetVal().toString();    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return "";
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	public String GetClockingNewRecordEx_localbyDate(int sno ,String strDate)
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_GetClockingNewRecordEx");   
	    		n.setRetVal(Type.STRING);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
	    		n.setParameter(i++, Type.STRING, "" + CommonTool.FormatString(strDate));  
		        n.invoke();     
	            return n.getRetVal().toString();    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return "";
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	
	
	//获取考勤记录
	public String CKT_ListPersonInfoEx_local(int sno )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_ListPersonInfoEx");   
	    		n.setRetVal(Type.STRING);  
	    		int i=0;
	    		n.setParameter(i++, Type.INT, "" + sno);    
		        n.invoke();     
	            return n.getRetVal().toString();    
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return "";
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	
	//断开指纹仪
	public void CKT_Disconnect_local( )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400ex.dll", "CKT_Disconnect");   
	    		n.setRetVal(Type.VOID);  
	            n.invoke();      
	       }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    finally {  
	    	try
	    	{
	            if (n != null)    
	                n.dispose();    
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	       }    
	}
	
	//将指纹文件转化成字节
	public byte[] loadByteByFile(String strFilename)
	{
		try
		{
			File file=new File(strFilename);
			long length = file.length();
			byte[] bytes = new byte[(int) length];
			InputStream is = new FileInputStream(file);
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
					offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			is.close();
			file=null;
	
			// 创建目标文件
	    	System.out.println(bytes.length);
	    	
	    	return bytes;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//将指纹文件转化成字节
	public boolean loadFileByByte( byte[] bytes,String destFileName)
	{
		try
		{
			File file=new File(destFileName);
	    	System.out.println(file.exists());
	    	if (file.exists()) {
	    			System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
	    	}
	    	System.out.println(destFileName);
	    	if (file.createNewFile()) {
	    		System.out.println("创建单个文件" + destFileName + "成功！");
	    	} 
	    	else {
	    			System.out.println("创建单个文件" + destFileName + "失败！");
	    	}
	    	
            FileOutputStream wf=new FileOutputStream(destFileName);
            wf.write(bytes,0,bytes.length);
            file=null;
            wf.close();
            return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}
	
	public FingerTool(){
		
		System.out.println( System.getProperty("java.library.path"));
		System.loadLibrary("tc400ex"); 
		System.out.println("结果33333333333333");	
		System.loadLibrary("tc400"); 
		System.out.println("结果3333333333333");	
	}
	public static void main(String[] args) {
		
		FingerTool fingerTool=new FingerTool();
		System.out.println("开始连接.....................");
		int result=fingerTool.CKT_RegisterNet_local(99029, "10.1.1.252");
		System.out.println("结果"+result);
		System.out.println("开始监听.....................");
		result=fingerTool.CKT_NetDaemon_local();
//		System.out.println("结果"+result);
//		result=fingerTool.CKT_ModifyPersonInfo_local(99029,21031,"",0,"李云英",0,0,0,3,0);
//		System.out.println("结果"+result);
//		String strresult=fingerTool.CKT_ListPersonInfoEx_local(99008);
//		System.out.println("结果"+strresult);
//		System.out.println("开始下载考勤.....................");
//		String strResult=fingerTool.GetClockingRecordProgress_localbyDate(99045,"2014-03-02");
//		System.out.println(strResult);
//		
//		result=fingerTool.CKT_ModifyPersonInfo_local(99045,13680,"",0,"马玲芳",0,0,6,0,0);
//		System.out.println("结果"+result);
		
	
		
//			
//		System.out.println("开始清除指纹.....................");
//		result=fingerTool.CKT_ClearClockingRecord_local(99008,1,36);
//		System.out.println("结果"+result);	
		
		System.out.println("开始下载考勤.....................");
		String strResult=fingerTool.GetClockingRecordProgress_localbyDate(99029,"2014-06-12");
		System.out.println(strResult);
//		
//		System.out.println("开始清除员工.....................");
//		result=fingerTool.CKT_DeleteAllPersonInfo_local(36);
//		System.out.println("结果"+result);	

//		Personinfo person=new Personinfo();
//		person.setPersonID(21009);
//		person.setUserName("曹扬");
//		person.setKQOption(6);
//		person.setCardNo(0);
//		person.setDept(0);
//		person.setFPMark(0);
//		person.setOther(0);
//		person.setGroup(0);
//		//
		//int personid,String password,int cardno,String personname,int dept,int group,int kqoption,int fpmark,int other
//		System.out.println("开始创建员工.....................");
//		result=fingerTool.CKT_ModifyPersonInfo_local(99008,21009,"",0,"曹扬",0,0,6,0,1);
//		String destFileName="D:/finger/21009_0.anv";
//		System.out.println("开始上次指纹.....................");
//		result=fingerTool.CKT_PutFPTemplateLoadFile_local(99008,21009,0, destFileName);
//		System.out.println("结果"+result);
//		destFileName="D:/finger/21009_1.anv";
//		System.out.println("开始上次指纹.....................");
//		result=fingerTool.CKT_PutFPTemplateLoadFile_local(99008,21009,1, destFileName);
//		System.out.println("结果"+result);
	
//		List<PersonRecord> lsPersonRecord=fingerTool.loadDTOList(strResult, PersonRecord.class);
//
//		if(lsPersonRecord!=null)
//		{
//			for(int i=0;i<lsPersonRecord.size();i++)
//			{
//				System.out.println(lsPersonRecord.get(i).getId());
//				System.out.println(lsPersonRecord.get(i).getPersonID());
//				System.out.println(lsPersonRecord.get(i).getStat());
//				System.out.println(lsPersonRecord.get(i).getTime().substring(0,10));
//				System.out.println(lsPersonRecord.get(i).getTime().substring(11,18));
//				System.out.println(lsPersonRecord.get(i).getWorkTyte());
//			}
//		}*/
//	
////		System.out.println("结果"+result);
//		String strUploadFilename="D:/finger/10419_0.anv";
//		System.out.println("开始下载指纹.....................");
//		result=fingerTool.CKT_GetFPTemplateSaveFile_local(99001,12757,0,strUploadFilename);
//		System.out.println("结果"+result);
		
		//int personid,String password,int cardno,String personname,int dept,int group,int kqoption,int fpmark,int other
//		System.out.println("开始创建员工.....................");
//		result=fingerTool.CKT_ModifyPersonInfo_local(99001,10418,"",0,"张金婷",0,0,6,0,0);
//		System.out.println("结果"+result);

//
////		String destFileName="D:/finger/16.anv";
////		try {
////				byte[] bytes=fingerTool.loadByteByFile(strUploadFilename);
////				System.out.println(bytes.length);
////				字段  image           NULL,   --指纹文件
////				private byte[] 字段;
////				 <property name="字段" type="binary">
////		            <column name="字段" />
////		        </property>
////				boolean flag=fingerTool.loadFileByByte(bytes, destFileName);
////				System.out.println(flag);
////		} catch (Exception e) {
////			throw new RuntimeException(e);
////		}

//		
//		System.out.println("开始下载考勤.....................");
//		String strResult=fingerTool.GetClockingRecordProgress_localbyDate(99008,"2013-11-16");
//		System.out.println(strResult);
//		System.out.println(strResult.length());
//		System.out.println("开始下载员工.....................");
//		String strresult=fingerTool.CKT_ListPersonInfoEx_local(99008);
//		System.out.println("结果"+strresult);
//		String testSte="搓";
//		for(int i=0;i<20000;i++)
//		{
//			System.out.println(i);
//			testSte=testSte+"搓";
//		}
//		System.out.println(testSte);
		System.out.println("断开连接.....................");
		fingerTool.CKT_Disconnect_local();
	}
//
//	
	public  List loadDTOList(String jsonString ,Class obj){ 
		JSONArray array = JSONArray.fromObject(jsonString); 
		List list = new ArrayList(); 
		for(
				Iterator iter = array.iterator();
				iter.hasNext();){ 
				JSONObject jsonObject = (JSONObject)iter.next(); 
				list.add(JSONObject.toBean(jsonObject, obj)); 
		} 
	
		return list; 
		}
		//CKT_GetFPRawData 					读取指纹字节   		直接将字节当字符返回
		//CKT_PutFPRawData 					上传指纹数据	  		传字符到方法在转成字节
		//CKT_ListPersonInfoEx  			获取员工资料表  		返回Json格式的字符串
		//CKT_ClearClockingRecord  			考勤计数清零  			直接继承
		//CKT_DeleteAllPersonInfo 			删除所有用户信息  		直接继承
}
