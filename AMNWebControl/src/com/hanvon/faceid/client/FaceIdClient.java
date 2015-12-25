/* ---------------------------------------------------------- 
 * 文件名称：FaceIdClient.java 
 * 作者：秦建辉 
 *  
 * QQ：36748897 
 *  
 * 博客：http://www.firstsolver.com/wordpress/ 
 *  
 * 开发环境： 
 *      NetBeans IDE 7.3 
 *      JDK 7u25 
 *       
 * 版本历史：
 *      V1.2.1	2014年03月15日
 *              修正获取考勤记录时丢失照片的错误

 *      V1.2	2013年08月05日
 *              汉王人脸通SDK客户端示例代码-设备管理类命令
 * 
 *      V1.1	2013年08月04日
 *              汉王人脸通SDK客户端示例代码-记录管理类命令
 * 
 *      V1.0    2013年08月03日 
 *              汉王人脸通SDK客户端示例代码-员工管理类命令 
------------------------------------------------------------ */
package com.hanvon.faceid.client; 

import com.hanvon.faceid.sdk.*; 
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List; 
import javax.imageio.ImageIO;
  
public class FaceIdClient {     
    // 设备通信字符集编码为简体中文GB2312 
    public static final String DeviceCharset = "GB2312"; 
    
    /* ------------------------------------------------------------------------
     * 员工管理类命令
     * ------------------------------------------------------------------------ */
    // 获取员工工号列表 
    public static FaceId_ErrorCode GetEmployeeID(FaceId tcpClient, List<String> EmployeeIdList) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetEmployeeID()", output, DeviceCharset); 
        if (ErrorCode.equals(FaceId_ErrorCode.Success)) 
        {   // 获取所有数据项 
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer); 
            if (ItemCollection != null) 
            {                   
                for (FaceId_Item item : ItemCollection) 
                { 
                    if (item.name.equals("id")) EmployeeIdList.add(item.value); 
                } 
            } 
        } 
  
        return ErrorCode; 
    } 
      
    // 获取指定工号员工的详细信息 
    public static FaceId_ErrorCode GetEmployee(FaceId tcpClient, String EmployeeId, FaceId_Employee Employee) 
    {   
        // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer();  
        FaceId_ErrorCode ErrorCode = tcpClient.Execute(new StringBuilder().append("GetEmployee(id=\"").append(EmployeeId).append("\")").toString(), output, DeviceCharset); 
        if (ErrorCode.equals(FaceId_ErrorCode.Success)) 
        {   // 获取所有数据项 
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer); 
            if (ItemCollection != null) 
            { 
                List<String> TemplateList = new ArrayList();   // 人脸模板列表 
                for(FaceId_Item item : ItemCollection) 
                { 
                    if (item.name.equals("face_data")) {
                    	// 18个人脸模板数据                            
                            TemplateList.add(item.value);
                            break;
                    }
                    else if (item.name.equals("id")){            // 员工工号
                            Employee.id = item.value;           
                            break;
                    }
                    else if (item.name.equals("name")){             // 员工姓名
                            Employee.name = item.value;         
                            break;
                    }
                    else if (item.name.equals( "calid")){            // 工卡标识符
                            Employee.calid = item.value;        
                            break;
                    }
                    else if (item.name.equals( "card_num")){         // 刷卡号码
                            Employee.card_num = item.value;     
                            break;
                    }
                    else if (item.name.equals(  "authority")){        // 考勤开门权限
                            Employee.authority = FaceId_Authority.valueOfDescription(item.value);   
                            break;
                    }
                    else if (item.name.equals(  "check_type")){       // 考勤验证方式
                            Employee.check = FaceId_EnterType.valueOfDescription(item.value);       
                            break;
                    }
                    else if (item.name.equals(  "opendoor_type")){    // 开门验证方式
                            Employee.opendoor = FaceId_EnterType.valueOfDescription(item.value);    
                            break;
                    }
                } 
  
                if (TemplateList.size() > 0) 
                { 
                    Employee.face_data = TemplateList.toArray(new String[TemplateList.size()]); 
                } 
            } 
        } 
  
        return ErrorCode; 
    } 
  
    // 获取所有员工信息 
    public static FaceId_ErrorCode GetAllEmployee(FaceId tcpClient, List<FaceId_Employee> EmployeeList) 
    {   // 获取员工工号列表 
        List<String> EmployeeIdList = new ArrayList();  
        FaceId_ErrorCode ErrorCode = GetEmployeeID(tcpClient, EmployeeIdList); 
        if (ErrorCode.equals(FaceId_ErrorCode.Success)) 
        {   // 获取员工信息 
            if (!EmployeeIdList.isEmpty()) 
            {   // 员工信息列表             
                for (String EmployeeId : EmployeeIdList) 
                { 
                    FaceId_Employee Employee = new FaceId_Employee(); 
                    ErrorCode = GetEmployee(tcpClient, EmployeeId, Employee);    // 获取员工信息 
                    if (ErrorCode.equals(FaceId_ErrorCode.Success)) 
                    { 
                        EmployeeList.add(Employee); // 加入员工信息列表 
                    } 
                    else
                    {   // 出现错误，终止操作 
                        break; 
                    } 
                }                      
            } 
        } 
  
        return ErrorCode; 
    } 
  
    // 设置员工信息 
    public static FaceId_ErrorCode SetEmployee(FaceId tcpClient, FaceId_Employee Employee) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(new StringBuilder().append("SetEmployee(").append(Employee.toString()).append(")").toString(), output, DeviceCharset); 
    } 
  
    // 删除指定工号员工 
    public static FaceId_ErrorCode DeleteEmployee(FaceId tcpClient, String EmployeeId) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(new StringBuilder().append("DeleteEmployee(id=\"").append(EmployeeId).append("\")").toString(), output, DeviceCharset); 
    } 
  
    // 删除所有员工 
    public static FaceId_ErrorCode DeleteAllEmployee(FaceId tcpClient) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("DeleteAllEmployee()", output, DeviceCharset); 
    } 
  
    // 以覆盖方式更新工号和姓名对照表 
    public static FaceId_ErrorCode SetNameTable(FaceId tcpClient, FaceId_Item[] table) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 判断对照表是否为空 
        if (table.length == 0) return FaceId_ErrorCode.ArgumentNullException; 
  
        // 生成命令字符串 
        StringBuilder sb = new StringBuilder(); 
        sb.append("SetNameTable("); 
        for (FaceId_Item item : table) 
        { 
            sb.append(item.name).append("=\"").append(item.value).append("\" "); 
        } 
        sb.setCharAt(sb.length() - 1, ')'); 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(sb.toString(), output, DeviceCharset); 
    } 
  
    // 以追加方式更新工号姓名对照表 
    public static FaceId_ErrorCode AddNameTable(FaceId tcpClient, FaceId_Item[] table) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 判断对照表是否为空 
        if (table.length == 0) return FaceId_ErrorCode.ArgumentNullException; 
  
        // 生成命令字符串 
        StringBuilder sb = new StringBuilder(); 
        sb.append("AddNameTable("); 
        for (FaceId_Item item : table) 
        { 
            sb.append(item.name).append("=\"").append(item.value).append("\" "); 
        } 
        sb.setCharAt(sb.length() - 1, ')'); 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(sb.toString(), output, DeviceCharset); 
    } 
  
    // 检测设备是否包含员工数据 
    public static FaceId_ErrorCode DetectEmployeeData(FaceId tcpClient) 
    {   // 判断连接是否已经建立 
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException; 
  
        // 执行设备端命令 
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("DetectEmployeeData()", output, DeviceCharset); 
    } 
    
    /* ------------------------------------------------------------------------
     * 记录管理类命令
     * ------------------------------------------------------------------------ */
    // 从考勤机接收指定时间内的考勤记录
    public static FaceId_ErrorCode GetRecord(FaceId tcpClient, Date start_time, Date end_time, List<FaceId_Record> RecordList) throws ParseException, IOException
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 日期格式转换器
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");   
        
        // 生成命令字符串
        StringBuilder sb = new StringBuilder(256);
        sb.append("GetRecord(");
        if (start_time != null)
        {
            sb.append("start_time=\"").append(dateFormat.format(start_time)).append("\"");
        }

        if (end_time != null)
        {
            if (start_time != null) sb.append(" ");
            sb.append("end_time=\"").append(dateFormat.format(end_time)).append("\"");
        }
        sb.append(")");

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute(sb.toString(), output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {   
            FaceId_Item[] items = FaceId_Item.GetAllItems(output.answer);
            if (items != null)
            {
                FaceId_Record RecordItem = null;
                for (FaceId_Item item : items)
                {
                    if(RecordItem == null)
                    {
                        if(item.name.equals("time"))
                        {
                            RecordItem = new FaceId_Record();   // 创建新的考勤记录
                            RecordItem.time = dateFormat.parse(item.value);
                        }
                    }
                    else
                    {
                    	if (item.name.equals( "time")){
                    		// 考勤记录时间
                                RecordList.add(RecordItem);         // 保存上一条考勤记录
                                RecordItem = new FaceId_Record();   // 创建新的考勤记录
                                RecordItem.time = dateFormat.parse(item.value);
                                break;
                    	}
                    	else if (item.name.equals( "id")){         // 考勤记录时间
                                RecordItem.id = item.value;
                                break;
                    	}
                    	else if (item.name.equals(  "name")){       // 员工姓名
                                RecordItem.name = item.value;
                                break;
                    	}
                    	else if (item.name.equals(  "authority")){  // 员工考勤开门权限
                                RecordItem.authority = FaceId_Authority.valueOfDescription(item.value);
                                break;
                    	}
                    	else if (item.name.equals(  "card_src")){   // 考勤记录归属
                            	if (item.name.equals("from_door")){
                                        RecordItem.source.set(0);
                                        break;
                        		}
                            	else if (item.name.equals( "from_check")){
                                        RecordItem.source.set(1);
                                        break;
                                }
                                break;
                    	}
                    	else if (item.name.equals(   "type")){        // 考勤方式
                                RecordItem.type = FaceId_RecordMode.valueOfDescription(item.value);
                                break;
                    	}
                        else if (item.name.equals(  "photo")){       // 拍照照片（JPEG格式）                                
                                RecordItem.photo = ImageIO.read(new ByteArrayInputStream(Base64.decodeBase64(item.value.replace('<', '/'))));
                                break;
                        }
                    }                    
                } // End for
                
                // 保存上一条考勤记录
                if(RecordItem != null) RecordList.add(RecordItem);            
            }
        }

        return ErrorCode;
    }

    // 删除所有记录
    public static FaceId_ErrorCode DeleteAllRecord(FaceId tcpClient)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("DeleteAllRecord()", output, DeviceCharset);
    }

    // 删除某个时间前的所有记录
    public static FaceId_ErrorCode DeleteAllRecord(FaceId tcpClient, Date time)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 生成命令字符串
        StringBuilder sb = new StringBuilder(256);
        sb.append("DeleteAllRecord(");
        if (time != null)
        {
            sb.append("time=\"").append(new SimpleDateFormat("yyyy-M-d H:m:s").format(time)).append("\"");
        }
        sb.append(")");

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(sb.toString(), output, DeviceCharset);
    }
    
    /* ------------------------------------------------------------------------
     * 设备管理类命令
     * ------------------------------------------------------------------------ */
    // 初始化考勤机到出厂状态
    public static FaceId_ErrorCode InitDevice(FaceId tcpClient)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("InitDevice()", output, DeviceCharset);
    }

    // 初始化考勤机的管理员设置到出厂状态
    public static FaceId_ErrorCode InitDeviceAdmin(FaceId tcpClient)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("InitDeviceAdmin()", output, DeviceCharset);
    }

    // 设置考勤机配置、状态信息
    public static FaceId_ErrorCode SetDeviceInfo(FaceId tcpClient, Date dt)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 生成命令字符串
        String Command = new StringBuilder().append("SetDeviceInfo(time=\"").append(new SimpleDateFormat("yyyy-M-d H:m:s").format(dt)).append("\")").toString();

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(Command, output, DeviceCharset);
    }

    // 读取考勤机配置、状态信息
    public static FaceId_ErrorCode GetDeviceInfo(FaceId tcpClient, FaceId_DeviceInfo DeviceInfo) throws ParseException, UnknownHostException
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetDeviceInfo()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer);
            if (ItemCollection != null)
            {
                for (FaceId_Item item : ItemCollection)
                {
                    if (item.name.equals("time"))
                    {
                          	DeviceInfo.time = new SimpleDateFormat("yyyy-M-d H:m:s").parse(item.value);
                          	break;
                    }
                    else   if (item.name.equals("volume"))// 音量大小 
                    {
                    	 	DeviceInfo.volume = Integer.parseInt(item.value);
                    	 	break;
                    }
                    else   if (item.name.equals("edition"))// 固件版本 
                    {
                    	DeviceInfo.edition = item.value;
                        break;
                    }
                    else   if (item.name.equals("weigen"))// 韦根类型  
                    {
                    	DeviceInfo.wiegand = Integer.parseInt(item.value);
                        break;
                    }
                    else   if (item.name.equals("ip"))// 设备IP   
                    {
                    	 DeviceInfo.ip = IPAddressParse(item.value.substring(0, 12));
                         break;
                    }
                    else   if (item.name.equals("gateway"))// 设备网关   
                    {
                    	  DeviceInfo.gateway = IPAddressParse(item.value);
                          break;
                    }
                    else   if (item.name.equals("netmask"))// 设备子网掩码   
                    {
                    	DeviceInfo.netmask = IPAddressParse(item.value);
                        break;
                    }
                    else   if (item.name.equals("mac")) // 网卡地址 
                    {
                    	 DeviceInfo.mac = item.value;
                         break;
                    }
                    else   if (item.name.equals("max_managernum")) // 最大管理员数量  
                    {
                    	 DeviceInfo.max_managernum = Integer.parseInt(item.value);
                         break;
                    }
                    else   if (item.name.equals("managernum")) // 实际管理员数量 
                    {
                    	 DeviceInfo.managernum = Integer.parseInt(item.value);
                         break;
                    }
                    else   if (item.name.equals("max_faceregist"))// 最大人脸登记员工数  
                    {
                    	DeviceInfo.max_faceregist = Integer.parseInt(item.value);
                        break;
                    }
                    else   if (item.name.equals("real_faceregist")) // 实际人脸登记员工数   
                    {
                    	DeviceInfo.real_faceregist = Integer.parseInt(item.value);
                        break;
                    }
                    else   if (item.name.equals("max_facerecord")) // 最大人脸考勤记录数 
                    {
                    	DeviceInfo.max_facerecord = Integer.parseInt(item.value);
                        break;
                    }
                    else   if (item.name.equals("real_facerecord")) // 实际人脸考勤记录数
                    {
                    	DeviceInfo.real_facerecord = Integer.parseInt(item.value);
                        break;
                    }
                       
                }
            }
        }
        return ErrorCode;
    }

    // 获取网络配置信息
    public static FaceId_ErrorCode GetNetInfo(FaceId tcpClient, FaceId_NetInfo NetInfo) throws UnknownHostException
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetNetInfo()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer);
            if (ItemCollection != null)
            {
                for (FaceId_Item item : ItemCollection)
                {
                    if (item.name.equals("ip")) {  
                            NetInfo.ip = IPAddressParse(item.value);
                            break;
                    }
                    else if (item.name.equals("gateway")) { 
                    	  NetInfo.gateway = IPAddressParse(item.value);
                          break;
                    }
                    else if (item.name.equals("netmask")) {  
                    	  NetInfo.netmask = IPAddressParse(item.value);
                          break;
                    }
                    else if (item.name.equals("commukey")) {  
                    	 NetInfo.commukey = item.value;
                         break;
                    } 
                }
            }
        }

        return ErrorCode;
    }

    // 设置网络配置信息
    public static FaceId_ErrorCode SetNetInfo(FaceId tcpClient, FaceId_NetInfo NetInfo)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(new StringBuilder().append("SetNetInfo(").append(NetInfo.toString()).append(")").toString(), output, DeviceCharset);
    }

    // 获取人脸通网卡MAC地址和机器序列号
    public static FaceId_ErrorCode GetMAC_SN(FaceId tcpClient, FaceId_MacSn MacSn)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetMAC_SN()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer);
            if (ItemCollection != null)
            {
                for (FaceId_Item item : ItemCollection)
                {
                    if (item.name.equals("mac")) {                       
                            MacSn.mac = item.value;
                            break;
                    }
                    else  if (item.name.equals("sn")) {                     
                            MacSn.sn = item.value;
                            break;
                    }
                }
            }
        }

        return ErrorCode;
    }

    // 检测设备是否存在
    public static FaceId_ErrorCode DetectDevice(FaceId tcpClient)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("DetectDevice()", output, DeviceCharset);
    }

    // 重启设备
    public static FaceId_ErrorCode RestartDevice(FaceId tcpClient)
    {
        // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute("RestartDevice()", output, DeviceCharset);
    }

    // 获取人脸通设备时间
    public static FaceId_ErrorCode GetDateTime(FaceId tcpClient, DateWrapper holder) throws ParseException
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetDateTime()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            int Style = Integer.parseInt(FaceId_Item.GetKeyValue(output.answer, "style"));
            String datetime = FaceId_Item.GetKeyValue(output.answer, "date") + " " + FaceId_Item.GetKeyValue(output.answer, "time");
            switch(Style)
            {
                case 1:
                    holder.dt = new SimpleDateFormat("yyyy-M-d H:m:s").parse(datetime);
                    break;
                case 2:
                    holder.dt = new SimpleDateFormat("yyyy/M/d H:m:s").parse(datetime);                    
                    break;                
                case 3:
                    holder.dt = new SimpleDateFormat("yyyy.M.d H:m:s").parse(datetime);                    
                    break;
                case 4:
                    holder.dt = new SimpleDateFormat("M-d-yyyy H:m:s").parse(datetime);                    
                    break;
                case 5:
                    holder.dt = new SimpleDateFormat("M/d/yyyy H:m:s").parse(datetime);                    
                    break;
                case 6:
                    holder.dt = new SimpleDateFormat("M.d.yyyy H:m:s").parse(datetime);                    
                    break;
                case 7:
                    holder.dt = new SimpleDateFormat("d-M-yyyy H:m:s").parse(datetime);                    
                    break;
                case 8:
                    holder.dt = new SimpleDateFormat("d/M/yyyy H:m:s").parse(datetime);                    
                    break;
                case 9:
                    holder.dt = new SimpleDateFormat("d.M.yyyy H:m:s").parse(datetime);                    
                    break; 
            }
        }

        return ErrorCode;
    }

    // 设置人脸通设备时间
    public static FaceId_ErrorCode SetDateTime(FaceId tcpClient, Date dt)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer();
        return tcpClient.Execute(new StringBuilder().append("SetDateTime(date=\"").append(new SimpleDateFormat("yyyy-M-d").format(dt)).append("\" time=\"").append(new SimpleDateFormat("H:m:s").format(dt)).append("\")").toString(), output, DeviceCharset);
    }
 
    // 获取音量大小
    public static FaceId_ErrorCode GetVolume(FaceId tcpClient, IntegerWrapper holder)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetVolume()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            holder.value =  Integer.parseInt(FaceId_Item.GetKeyValue(output.answer, "volume"));
        }

        return ErrorCode;
    }

    // 设置音量大小
    public static FaceId_ErrorCode SetVolume(FaceId tcpClient, int volume)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(new StringBuilder().append("SetVolume(volume=\"").append(Integer.toString(volume)).append("\")").toString(), output, DeviceCharset);
    }

    // 获取定时开关机设置
    public static FaceId_ErrorCode GetOnOffSchedule(FaceId tcpClient, FaceId_OnOffSchedule schedule)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetOnOffSchedule()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer);
            if (ItemCollection != null)
            {
                for (FaceId_Item item : ItemCollection)
                {
                    if(item.name.equals("turn_on_status")) 
                    {
                    	schedule.turn_on_status = item.value.equals("enable");
                        break;
                    }
                    else if(item.name.equals("turn_on_time")) 
                    {
                    	 schedule.turn_on_time = item.value;
                         break;
                    }
                    else if(item.name.equals("turn_off_status")) 
                    {
                    	 schedule.turn_off_status = item.value.equals("enable");
                         break;
                    }
                    else if(item.name.equals("turn_off_time")) 
                    {
                    	schedule.turn_off_time = item.value;
                        break;
                    }
                }
            }
        }

        return ErrorCode;
    }

    // 设置定时开关机参数
    public static FaceId_ErrorCode SetOnOffSchedule(FaceId tcpClient, FaceId_OnOffSchedule schedule)
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer();
        return tcpClient.Execute(new StringBuilder().append("SetOnOffSchedule(").append(schedule.toString()).append(")").toString(), output, DeviceCharset);
    }

    // 获取设备套接字客户端状态
    public static FaceId_ErrorCode GetClientStatus(FaceId tcpClient, FaceId_ClientInfo ClientInfo) throws UnknownHostException
    {   // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        FaceId_ErrorCode ErrorCode = tcpClient.Execute("GetClientStatus()", output, DeviceCharset);
        if (ErrorCode.equals(FaceId_ErrorCode.Success))
        {
            FaceId_Item[] ItemCollection = FaceId_Item.GetAllItems(output.answer);
            if (ItemCollection != null)
            {
                for (FaceId_Item item : ItemCollection)
                {
                    if (item.name.equals("status")) {
                        
                            ClientInfo.status = item.value.equals("enable");
                            break;
                    }
                    else if(item.name.equals( "hostip"))
                    {
                            ClientInfo.hostip = IPAddressParse(item.value);
                            break;
                    }
                }
            }
        }

        return ErrorCode;
    }

    // 设置客户端状态
    public static FaceId_ErrorCode SetClientStatus(FaceId tcpClient, FaceId_ClientInfo ClientInfo)
    {
        // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer();
        return tcpClient.Execute(new StringBuilder().append("SetClientStatus(").append(ClientInfo.toString()).append(")").toString(), output, DeviceCharset);
    }

    // 设置公司名称
    public static FaceId_ErrorCode SetCompanyName(FaceId tcpClient, String company)
    {
        // 判断连接是否已经建立
        if (!tcpClient.isConnected()) return FaceId_ErrorCode.SocketException;

        // 执行设备端命令
        FaceIdAnswer output = new FaceIdAnswer(); 
        return tcpClient.Execute(new StringBuilder().append("SetCompanyName(\"company=\"").append(company).append("\")").toString(), output, DeviceCharset);
    }

    // 将系统IP地址转换成人脸通要求的IP地址字符串
    static String ToIPString(InetAddress address)
    {
        StringBuilder sb = new StringBuilder(32);
        byte[] bytes = address.getAddress();
        for (byte b : bytes) sb.append(String.format("%1$03d", (b + 256) % 256));

        return sb.toString();
    }

    // 将人脸通格式的IP地址字符串转换成系统IP地址
    static InetAddress IPAddressParse(String ipString) throws UnknownHostException
    {
        byte[] Address = new byte[ipString.length() / 3];
        for (int i = 0; i < Address.length; i++)
        {
            Address[i] = (byte)Integer.parseInt(ipString.substring(i * 3, i * 3 + 3));
        }

        return InetAddress.getByAddress(Address);
    }
}
