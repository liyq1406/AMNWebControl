package com.amani.action.CardControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.CommonBean;
import com.amani.service.CardControl.CC020Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc020")
public class CC020Action {
	@Autowired
	private CC020Service cc020Service;
	private String strCurCompId;
	private String beginDate;
	private String endDate;
	private List<CommonBean> dataSet;
	private OutputStream os;
	//查询第三方支付
		@Action( value="query", results={@Result(type="json", name="load_success")})
		public String query() {
			try{
				dataSet = cc020Service.querySet(strCurCompId, beginDate, endDate);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		//导出Excel查询
		@Action(value="loadExcel", results={@Result(name ="load_success", location="/CardControl/CC020/cc020Excel.jsp")}) 
		public String loadExcel() {
			try{
				dataSet = cc020Service.querySet(strCurCompId, beginDate, endDate);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		public void createExcel() throws WriteException, IOException{
			try{
				int excelLength0=6;
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("第三方支付对账表", 0);
				//构造表头
				sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
				WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
				
				WritableCellFormat titleFormate =new WritableCellFormat(bold);
				titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat numberFormate =new WritableCellFormat();
				numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
						WritableFont.NO_BOLD, false, 
						UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
				WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
				totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中


				/****创建要显示的内容***/
				//标题
				Label head=new Label(0,0,"第三方支付对账表",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				
				Label label=new Label(0,2,"门店编号",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(0, 2, 0, 2);
				label=new Label(1,2,"门店名称",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(1, 2, 1, 2);
				
				label=new Label(2,2,"日期范围",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(2, 2, 2, 2);
				label=new Label(3,2,"支付宝金额",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(3, 2, 3, 2);
				label=new Label(4,2,"微信金额",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(4, 2, 4, 2);
				label=new Label(5,2,"OK卡金额",titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(5, 2, 5, 2);
				if(dataSet!=null && dataSet.size()>0){
					int row=3;
					for (CommonBean bean : dataSet) {
						sheet.addCell(new Label(0, row, bean.getAttr1()));
						sheet.addCell(new Label(1, row, bean.getAttr2()));
						sheet.addCell(new Label(2, row, bean.getAttr3()));
						sheet.addCell(new Label(3, row, bean.getAttr4(), numberFormate));
						sheet.addCell(new Label(4, row, bean.getAttr5(), numberFormate));
						sheet.addCell(new Label(5, row, bean.getAttr6(), numberFormate));
						row++;
					}
				}
				dataSet=null;
				workbook.write();
				workbook.close();
				os.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		public String getStrCurCompId() {
			return strCurCompId;
		}
		public void setStrCurCompId(String strCurCompId) {
			this.strCurCompId = strCurCompId;
		}

		public String getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(String beginDate) {
			if(StringUtils.isEmpty(beginDate)){
				beginDate=CommonTool.getCurrDate();
			}else{
				beginDate=StringUtils.replace(beginDate, "-", "");
			}
			this.beginDate = beginDate;
		}
		
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			if(StringUtils.isEmpty(endDate)){
				endDate=CommonTool.getCurrDate();
			}else{
				endDate=StringUtils.replace(endDate, "-", "");
			}
			this.endDate = endDate;
		}
		
		public List<CommonBean> getDataSet() {
			return dataSet;
		}
		public void setDataSet(List<CommonBean> dataSet) {
			this.dataSet = dataSet;
		}

		public OutputStream getOs() {
			return os;
		}
		public void setOs(OutputStream os) {
			this.os = os;
		}
		
		@JSON(serialize=false)
		public CC020Service getCc020Service() {
			return cc020Service;
		}
		@JSON(serialize=false)
		public void setCc020Service(CC020Service cc020Service) {
			this.cc020Service = cc020Service;
		}
		
		
}
