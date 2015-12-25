/* ----------------------------------------------------------
 * 文件名称：FaceId_DeviceInfo.java
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
 *      V1.0	2013年08月04日
 *              汉王人脸通SDK客户端示例代码-设备信息
------------------------------------------------------------ */
package com.hanvon.faceid.client;

import java.util.Date;
import java.net.InetAddress;

// 设备信息
public class FaceId_DeviceInfo {
    public Date time;           // 设备系统时间
    public int volume;          // 考勤机音量大小（1=低 2=中 3=高）
    public String edition;      // 固件版本号
    public int wiegand;         // 韦根类型
    public InetAddress ip;      // 设备IP地址
    public InetAddress gateway; // 网关
    public InetAddress netmask; // 子网掩码
    public String mac;          // 网卡Mac地址

    public int max_managernum;  // 最大管理员数量
    public int managernum;      // 实际管理员数量

    public int max_faceregist;  // 最大人脸登记员工数
    public int real_faceregist; // 实际人脸登记员工数

    public int max_facerecord;  // 最大人脸考勤记录数
    public int real_facerecord; // 实际人脸考勤记录数
}
