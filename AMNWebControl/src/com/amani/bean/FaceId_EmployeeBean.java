package com.amani.bean;


public class FaceId_EmployeeBean {
	 	private  String id;           // 员工工号
	 	private String name;         // 员工姓名
	 	private String calid;        // 工卡标识符
	 	private String card_num;     // 刷卡号码
	 	private String[] face_data;          // 员工人脸模板数据
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCalid() {
			return calid;
		}
		public void setCalid(String calid) {
			this.calid = calid;
		}
		public String getCard_num() {
			return card_num;
		}
		public void setCard_num(String card_num) {
			this.card_num = card_num;
		}
		public String[] getFace_data() {
			return face_data;
		}
		public void setFace_data(String[] face_data) {
			this.face_data = face_data;
		}
}
