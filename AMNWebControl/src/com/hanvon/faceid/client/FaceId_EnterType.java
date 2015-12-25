/* ----------------------------------------------------------
 * 文件名称：FaceId_EnterType.java
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
 *              汉王人脸通SDK客户端示例代码-定义考勤和开门验证方式
------------------------------------------------------------ */
package com.hanvon.faceid.client;

// 枚举类型，考勤和开门验证方式
public enum FaceId_EnterType
{   // 人脸
    Face { 
        @Override
        public String toString(){ return "face"; }
    },
    
    // 刷卡拍照
    CardAndPhoto { 
        @Override
        public String toString(){ return "card&photo"; }
    },
    
    // 人脸加刷卡 
    CardAndFace { 
        @Override
        public String toString(){ return "card&face"; }
    },
    
    // 刷卡
    Card { 
        @Override
        public String toString(){ return "card"; }
    }; 
    
    // 将字符串转换为枚举值
    public static FaceId_EnterType valueOfDescription(String description) throws IllegalArgumentException
    {
        for (FaceId_EnterType item : values())
        {
            if (item.toString().equals(description)) return item;
        }
        
        throw new IllegalArgumentException();
    }
}
