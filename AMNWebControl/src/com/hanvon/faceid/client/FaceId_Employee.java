/* ----------------------------------------------------------
 * 文件名称：FaceId_Employee.java
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
 *              汉王人脸通SDK客户端示例代码-定义员工信息类
------------------------------------------------------------ */
package com.hanvon.faceid.client;


// 员工信息
public class FaceId_Employee {
    public String id;           // 员工工号
    public String name;         // 员工姓名
    public String calid;        // 工卡标识符
    public String card_num;     // 刷卡号码
    public FaceId_Authority authority;  // 员工考勤开门权限
    public FaceId_EnterType check;      // 考勤验证方式   
    public FaceId_EnterType opendoor;   // 开门验证方式
    public String[] face_data;          // 员工人脸模板数据

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("id=\"").append(id).append("\"");                 // 员工工号
        sb.append(" name=\"").append(name).append("\"");            // 员工姓名
        sb.append(" calid=\"").append(calid).append("\"");          // 工卡标识符
        sb.append(" card_num=\"").append(card_num).append("\"");    // 刷卡号码        
        sb.append(" authority=\"").append(authority.toString()).append("\"");       // 考勤开门权限        
        sb.append(" check_type=\"").append(check.toString()).append("\"");          // 考勤验证方式        
        sb.append(" opendoor_type=\"").append(opendoor.toString()).append("\"");    // 开门验证方式
        
        // 员工人脸模板数据
        if (face_data != null)
        {
            for (String item : face_data)
            {
                sb.append(" face_data=\"").append(item).append("\"");
            }
        }

        return sb.toString();
    }
}
