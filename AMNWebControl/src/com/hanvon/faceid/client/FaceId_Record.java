/* ----------------------------------------------------------
 * 文件名称：FaceId_Record.java
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
 *              汉王人脸通SDK客户端示例代码-定义考勤记录信息类
------------------------------------------------------------ */
package com.hanvon.faceid.client;

import java.util.Date;
import java.awt.Image;
import java.util.BitSet;


// 考勤记录信息
public class FaceId_Record {
    public Date time;       // 考勤记录时间
    public String id;       // 员工工号
    public String name;     // 员工姓名
    public FaceId_Authority authority;      // 员工考勤开门权限
    public BitSet source = new BitSet(4);   // 考勤记录归属 （bit0-FromDoor，开门有效记录；bit1-FromCheck，考勤有效记录）
    public FaceId_RecordMode type;      // 考勤方式，人脸考勤和摄像考勤
    public Image photo;     // 拍照照片（JPEG格式）
}
