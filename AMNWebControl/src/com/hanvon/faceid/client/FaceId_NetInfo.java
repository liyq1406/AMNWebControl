/* ----------------------------------------------------------
 * 文件名称：FaceId_NetInfo.java
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
 *              汉王人脸通SDK客户端示例代码-网络配置信息
------------------------------------------------------------ */
package com.hanvon.faceid.client;

import java.net.InetAddress;

// 网络配置信息
public class FaceId_NetInfo {
    public InetAddress ip;        // IP地址
    public InetAddress netmask;   // 子网掩码
    public InetAddress gateway;   // 网关
    public String commukey;     // 通信密码

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(256);
        sb.append("ip=\"").append(FaceIdClient.ToIPString(ip)).append("\"");
        sb.append(" netmask=\"").append(FaceIdClient.ToIPString(netmask)).append("\"");
        sb.append(" gateway=\"").append(FaceIdClient.ToIPString(gateway)).append("\"");
        sb.append(" commukey=\"").append(commukey).append("\"");

        return sb.toString();
    }
}
