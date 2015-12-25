/* ----------------------------------------------------------
 * 文件名称：FaceId_OnOffSchedule.java
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
 *              汉王人脸通SDK客户端示例代码-设备定时开关机任务计划
------------------------------------------------------------ */
package com.hanvon.faceid.client;

// 设备定时开关机任务计划
public class FaceId_OnOffSchedule {
    public boolean turn_on_status;  // 定时开机状态
    public String turn_on_time;     // 定时开机时间
    public boolean turn_off_status; // 定时关机状态
    public String turn_off_time;    // 定时关机时间

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(256);

        // 定时开机状态
        if(turn_on_status)
            sb.append("turn_on_status=\"enable\"");
        else
            sb.append("turn_on_status=\"disable\"");

        // 定时开机时间
        sb.append(" turn_on_time=\"").append(turn_on_time).append("\"");

        // 定时关机状态
        if(turn_off_status)
            sb.append("turn_off_status=\"enable\"");
        else
            sb.append("turn_off_status=\"disable\"");

        // 定时关机时间
        sb.append(" turn_off_time=\"").append(turn_off_time).append("\"");

        return sb.toString();
    }
}
