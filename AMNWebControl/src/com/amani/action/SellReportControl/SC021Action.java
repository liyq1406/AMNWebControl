package com.amani.action.SellReportControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.amani.bean.CommonNBBean;
import com.amani.service.SellReportControl.SC021Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.utils.http.HttpResponse;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc021")
public class SC021Action {
	@Autowired
	private SC021Service sc021Service;
	private String strCurCompId;
	private String beginDate;
	private String endDate;
	private CommonNBBean nbDatas;
	private OutputStream os;
	//查询NB悦碧诗数据
		@Action( value="query", results={@Result(type="json", name="load_success")})
		public String query() {
			try{
				nbDatas = sc021Service.querySet(strCurCompId, beginDate, endDate);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		
		
		//导出Excel查询
		@Action(value="loadExcelNB", results={@Result(name ="load_success", location="/SellReportControl/SC021/sc021Excel.jsp")}) 
		public String loadExcel() {
			try{
				nbDatas = sc021Service.querySet(strCurCompId, beginDate, endDate);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		public void createExcel() throws WriteException, IOException{
			try{
				int excelLength0=6;
				Map<String,String>  columnNamesMap = nbDatas.getAllProjectColumnsNames();
				if(columnNamesMap!=null){
					String[] columnArrayCount= columnNamesMap.get("allcolumnNames").split(",");
					excelLength0 = columnArrayCount.length;
				}
				
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("NB悦碧诗数据", 0);
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
				Label head=new Label(0,0,"NB悦碧诗数据",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				Label label = null;
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				if(columnNamesMap!=null){
					label=new Label(0,2,"编号",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(0, 2, 0, 3);
					
					label=new Label(1,2,"门店",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(1, 2, 1, 3);
					
					label=new Label(2,2,"日期",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, 2, 2, 3);
					
					label=new Label(3,2,"疗程人数",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(3, 2, 3, 3);
					
					label=new Label(4,2,"总客单量",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(4, 2, 4, 3);
					label=new Label(5,2,"总实业绩",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(5, 2, 5, 3);
				}else{
					label=new Label(0,2,"编号",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(0, 2, 0, 2);
					
					label=new Label(1,2,"门店",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(1, 2, 1, 2);
					
					label=new Label(2,2,"日期",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, 2, 2, 2);
					
					label=new Label(3,2,"疗程人数",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(3, 2, 3, 2);
					
					label=new Label(4,2,"总客单量",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(4, 2, 4, 2);
					label=new Label(5,2,"总实业绩",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(5, 2, 5, 2);
				}
				Map<String,String> projectName = nbDatas.getProjectName();
				String projectNames = projectName.get("projectName");
				if(projectNames!=null){
					String[] projectNamesArray = projectNames.split(",");
					for(int i = 0;i<projectNamesArray.length;i++){
						int count = 6+i*2;
						label=new Label(count,2,projectNamesArray[i],titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(count, 2, count+1, 2);
						label=new Label(count,3,"客单量",titleFormate);
						sheet.addCell(label);
						label=new Label(count+1,3,"实业绩",titleFormate);
						sheet.addCell(label);
					}
				}
				
				if(projectNames!=null){
					int row=4;
					List<Map<String,String>> projectData = nbDatas.getProjectDataList();
					String[] columnArray = nbDatas.getAllProjectColumnsNames().get("allcolumnNames").split(",");
					for (Map<String,String> map : projectData) {
					    for(int j = 0;j<columnArray.length;j++){
					    	sheet.addCell(new Label(j, row, map.get(columnArray[j])));
					    }
						row++;
					}
				}
				
				nbDatas=null;
				workbook.write();
				workbook.close();
				os.flush();
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
		public OutputStream getOs() {
			return os;
		}
		public void setOs(OutputStream os) {
			this.os = os;
		}
		
		public CommonNBBean getNbDatas() {
			return nbDatas;
		}

		public void setNbDatas(CommonNBBean nbDatas) {
			this.nbDatas = nbDatas;
		}

		@JSON(serialize=false)
		public SC021Service getSc021Service() {
			return sc021Service;
		}
		@JSON(serialize=false)
		public void setSc021Service(SC021Service sc021Service) {
			this.sc021Service = sc021Service;
		}
}
