package com.amani.dll;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.pointers.memory.MemoryBlock;

import com.amani.bean.PersonRecord;
import com.amani.model.Personinfo;
import com.amani.tools.CommonTool;
import com.amani.tools.DataTool;



public class CopyOfFingerTool {

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
	//配置考勤机序号
	public int CKT_ModifyDeviceSno_local( int readno,int wirteno )
	{    
		JNative n = null;    
	    try {          
	    		n = new JNative("tc400.dll", "CKT_ModifyDeviceSno");   
	    		n.setRetVal(Type.INT);    
	            int i = 0;   
	            n.setParameter(i++, Type.INT, "" + readno);    
	    		n.setParameter(i++, Type.INT, "" +wirteno);  
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
	public CopyOfFingerTool(){
		System.loadLibrary("tc400ex"); 
		System.out.println( System.getProperty("java.library.path"));

	}
	public static void main(String[] args) {
		
		CopyOfFingerTool fingerTool=new CopyOfFingerTool();
		System.out.println("开始连接.....................");
		int result=fingerTool.CKT_RegisterNet_local(99001, "10.1.1.19");
		System.out.println("99001结果"+result);
		System.out.println("开始监听.....................");
		result=fingerTool.CKT_NetDaemon_local();
		System.out.println("结果"+result);	
		System.out.println("开始创建员工.....................");
		Personinfo person=new Personinfo();
		person.setPersonID(25);
		person.setUserName("刘杰");
		person.setPassword("");
		person.setKqOption(0);
		person.setCardNo(0);
		person.setDept(0);
		person.setFpMark(0);
		person.setOther(0);
		person.setGroup(0);
		result=fingerTool.CKT_ModifyPersonInfo_local(99001,person);
		System.out.println("结果"+result);
		
		/*result=fingerTool.CKT_RegisterNet_local(99008, "10.1.1.20");
		System.out.println("99008结果"+result);
		
		
	
		
		System.out.println("开始下载考勤.....................");
		String strResult=fingerTool.GetClockingRecordProgress_local(99001);
		System.out.println(strResult);
		List<PersonRecord> lsPersonRecord=fingerTool.loadDTOList(strResult, PersonRecord.class);

		if(lsPersonRecord!=null)
		{
			for(int i=0;i<lsPersonRecord.size();i++)
			{
				System.out.println(lsPersonRecord.get(i).getId());
				System.out.println(lsPersonRecord.get(i).getPersonID());
				System.out.println(lsPersonRecord.get(i).getStat());
				System.out.println(lsPersonRecord.get(i).getTime());
				System.out.println(lsPersonRecord.get(i).getWorkTyte());
			}
		}
		
		String strUploadFilename="D:/finger/"+0+".anv";
		System.out.println("开始下载指纹.....................");
		result=fingerTool.CKT_GetFPTemplateSaveFile_local(99008,5,0,strUploadFilename);
		System.out.println("结果"+result);
		*/
		
		
	
		
		/*System.out.println("删除员工.....................");
		result=fingerTool.CKT_DeletePersonInfo_local(99008,5);
		System.out.println("结果"+result);
		
		
	
		
		strUploadFilename="D:/finger/"+0+".anv";
		System.out.println("开始上传指纹.....................");
		result=fingerTool.CKT_PutFPTemplateLoadFile_local(99008,18,0,strUploadFilename);
		System.out.println("结果"+result);
		
		*/
		System.out.println("断开连接.....................");
		fingerTool.CKT_Disconnect_local();
	}
	
	
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

}
