/* ----------------------------------------------------------
 * 文件名称：FaceId_ClientInfo.java
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
 *              汉王人脸通SDK客户端示例代码-设备套接字客户端信息
------------------------------------------------------------ */
package com.hanvon.faceid.client;

import java.net.InetAddress;

// 设备套接字客户端信息
public class FaceId_ClientInfo {
    public boolean status;      // 设备套接字客户端状态
    public InetAddress hostip;  // 套接字连接的服务器IP地址

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(256);

        // 设备套接字客户端状态
        if(status)
            sb.append("status=\"enable\"");
        else
            sb.append("status=\"disable\"");

        // 套接字连接的服务器IP地址
        sb.append(" hostip=\"").append(FaceIdClient.ToIPString(hostip)).append("\"");

        return sb.toString();
    }
}
