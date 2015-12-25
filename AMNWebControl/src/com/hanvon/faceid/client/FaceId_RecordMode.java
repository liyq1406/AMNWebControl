/* ----------------------------------------------------------
 * 文件名称：FaceId_RecordMode.java
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
 *              汉王人脸通SDK客户端示例代码-定义考勤方式
------------------------------------------------------------ */
package com.hanvon.faceid.client;

// 枚举类型，考勤方式
public enum FaceId_RecordMode {
    // 人脸考勤
    Face { 
        @Override
        public String toString(){ return "face"; }
    },

    // 摄像考勤
    Photo { 
        @Override
        public String toString(){ return "photo"; }
    }; 
    
    // 将字符串转换为枚举值
    public static FaceId_RecordMode valueOfDescription(String description) throws IllegalArgumentException
    {
        for (FaceId_RecordMode item : values())
        {
            if (item.toString().equals(description)) return item;
        }
        
        throw new IllegalArgumentException();
    }
}
