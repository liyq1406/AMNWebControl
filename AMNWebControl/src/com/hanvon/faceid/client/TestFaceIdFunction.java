package com.hanvon.faceid.client;

import java.util.ArrayList;
import java.util.List;

import com.hanvon.faceid.sdk.FaceId;


public class TestFaceIdFunction {
	public static void main(String[] args) {
		try
		{
			System.out.println("234234");
		List<FaceId_Employee> ls=new ArrayList();
		System.out.println("234234x");
		FaceId faceid=new FaceId();
		System.out.println("234234xxxxx");
		faceid.Connect("10.1.1.21",9922);
		FaceIdClient client=new FaceIdClient();
		client.GetAllEmployee(faceid, ls);
		System.out.print("考勤机人数"+ls.size());
		faceid.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
	}
}
