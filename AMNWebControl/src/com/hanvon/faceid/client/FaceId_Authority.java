/* ----------------------------------------------------------
 * 文件名称：FaceId_Authority.java
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
 *      V1.0	2013年08月03日
 *              汉王人脸通SDK客户端示例代码-定义员工考勤和开门权限
------------------------------------------------------------ */
package com.hanvon.faceid.client;

// 枚举类型，员工权限
public enum FaceId_Authority {
    // 考勤加开门权限
    Both {
        @Override 
        public String toString() { return "0X0"; }
    },
    
    // 仅考勤权限
    Attendance {
        @Override 
        public String toString() { return "0X55"; }
    },
    
    // 仅开门权限
    Access {
        @Override 
        public String toString() { return "0XAA"; }
    },
    
    // 其它权限
    Others {
        @Override 
        public String toString() { return "0XFF"; }
    };  
    
    // 将字符串转换为枚举值
    public static FaceId_Authority valueOfDescription(String description) throws IllegalArgumentException
    {
        for (FaceId_Authority item : values())
        {
            if (item.toString().equals(description)) return item;
        }
        
        throw new IllegalArgumentException();
    }
}
