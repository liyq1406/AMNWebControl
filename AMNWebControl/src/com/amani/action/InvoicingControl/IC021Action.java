package com.amani.action.InvoicingControl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
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
import com.amani.service.InvoicingControl.IC021Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic021")
public class IC021Action {
	@Autowired
	private IC021Service ic021Service;
	private String strCurCompId;
	private String beginDate;
	private String endDate;
	private String projectKind;
	private Map<String,List<CommonBean>> dataSet;
	private Map<String,Map<String,List<CommonBean>>> totalMap;
	private OutputStream os;
	private List<CommonBean>  comList;
	private String comName;
	//初始化index页面
	@Action(value="loadIndex", results={@Result(name ="load_success", location="/InvoicingControl/IC021/index.jsp")}) 
	public String loadIndex() {
		comList = ic021Service.loadCompanyData();
	   return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询菲灵产品订单
		@Action( value="query", results={@Result(type="json", name="load_success")})
		public String query() {
			try{
				dataSet = ic021Service.querySet(strCurCompId, beginDate, endDate,projectKind);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		//导出Excel查询
		@Action(value="loadExcel", results={@Result(name ="load_success", location="/InvoicingControl/IC021/ic021Excel.jsp")}) 
		public String loadExcel() {
			try{
						dataSet = ic021Service.querySet(strCurCompId, beginDate, endDate,projectKind);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					return SystemFinal.LOAD_SUCCESS;
				}
		public void createExcel() throws WriteException, IOException{
			try{
				int excelLength0=7;
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("供应商结账", 0);
				//构造表头
				sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
				WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
				
				WritableCellFormat titleFormate =new WritableCellFormat(bold);
				titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat title2Formate =new WritableCellFormat(bold);
				title2Formate.setAlignment(jxl.format.Alignment.LEFT);//单元格的内容水平向左
				title2Formate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
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
				Label head=new Label(0,0,"供应商结账",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				comName = new String(comName.getBytes("iso8859-1"),"UTF-8");
				String headTitle = "店号:   "+strCurCompId+"  店名："+comName+"   结算日期：    "+beginDate+"~"+endDate;
				Label label=new Label(0,2,headTitle,titleFormate);
				sheet.addCell(label);
				sheet.mergeCells(0, 2, 6, 2);
				if(dataSet!=null && dataSet.size()>0){
					int row=3;
					float zbhj = 0;
					float mdhj = 0;
					for(String key:dataSet.keySet()){
						List<CommonBean> list = dataSet.get(key);
						CommonBean cob = list.get(0);
						label=new Label(0,row,"单号",titleFormate);
						sheet.addCell(label);
						label=new Label(1,row,cob.getAttr12());
						sheet.addCell(label);
						label=new Label(2,row,cob.getAttr9());
						sheet.addCell(label);
						sheet.mergeCells(2, row, 3, row);
						label=new Label(4,row,cob.getAttr10());
						sheet.addCell(label);
						sheet.mergeCells(4, row, 5, row);
						label=new Label(6,row,cob.getAttr13(),titleFormate);
						sheet.addCell(label);
						label=new Label(0,row+1,"产品编号",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(0, row+1, 0, row+1);
						label=new Label(1,row+1,"产品名称",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(1, row+1, 1, row+1);
						label=new Label(2,row+1,"数量",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(2, row+1, 2, row+1);
						label=new Label(3,row+1,"单价",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(3, row+1, 3, row+1);
						label=new Label(4,row+1,"厂家结算金额",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(4, row+1, 4, row+1);
						label=new Label(5,row+1,"门店单价",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(5, row+1, 5, row+1);
						label=new Label(6,row+1,"门店结账金额",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(6, row+1, 6, row+1);
						float zbxj = 0;
						float mdxj = 0;
						for(CommonBean bean:list){
							sheet.addCell(new Label(0, row+2, bean.getAttr3()));
							sheet.addCell(new Label(1, row+2, bean.getAttr4()));
							sheet.addCell(new Label(2, row+2, bean.getAttr5()));
							sheet.addCell(new Label(3, row+2, bean.getAttr11()));
							sheet.addCell(new Label(4, row+2, bean.getAttr6()));
							sheet.addCell(new Label(5, row+2, bean.getAttr7()));
							sheet.addCell(new Label(6, row+2, bean.getAttr8()));
							zbxj = zbxj+Float.parseFloat(bean.getAttr6());
							mdxj = mdxj+Float.parseFloat(bean.getAttr8());
							row++;
						}
						row = row +2;
						label=new Label(0,row,"",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(0, row, 2, row);
						
						label=new Label(3,row,"小计",titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(3, row, 3, row);
						
						label=new Label(4,row,String.format("%.2f", Double.parseDouble(String.valueOf(zbxj))),title2Formate);
						sheet.addCell(label);
						sheet.mergeCells(4, row, 4, row);
						
						label=new Label(6,row,String.format("%.2f", Double.parseDouble(String.valueOf(mdxj))),title2Formate);
						sheet.addCell(label);
						sheet.mergeCells(6, row, 6, row);
						zbhj = zbhj+ zbxj;
						mdhj = mdhj+ mdxj;
						row++;
					}
					label=new Label(0,row,"合计",titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(0, row, 3, row);
						
					label=new Label(4,row,String.format("%.2f", Double.parseDouble(String.valueOf(zbhj))),title2Formate);
					sheet.addCell(label);
					sheet.mergeCells(4, row, 4, row);
					
					label=new Label(6,row,String.format("%.2f", Double.parseDouble(String.valueOf(mdhj))),title2Formate);
					sheet.addCell(label);
					sheet.mergeCells(6, row, 6, row);
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
		//导出Excel查询
		@Action(value="loadAllExcel", results={@Result(name ="load_success", location="/InvoicingControl/IC021/ic021AllExcel.jsp")}) 
		public String loadAllExcel() {
			try{
				dataSet = ic021Service.queryAllSet(beginDate, endDate,projectKind);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		public void createAllExcel() throws WriteException, IOException{
			try{
				int excelLength0=7;
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("(分状态)供应商结账", 0);
				//构造表头
				sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
				WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
				
				WritableCellFormat titleFormate =new WritableCellFormat(bold);
				titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat _titleFormate =new WritableCellFormat(bold);
				_titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				_titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				_titleFormate.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				WritableCellFormat numberFormate =new WritableCellFormat();
				numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat textFormate =new WritableCellFormat();
				textFormate.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
						WritableFont.NO_BOLD, false, 
						UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
				WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
				totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中

				/****创建要显示的内容***/
				//标题
				Label head=new Label(0,0,"(分状态)供应商结账",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				if(dataSet!=null && dataSet.size()>0){
					int row = 2;
					Label label=new Label(0,row,"结算日期：",titleFormate);
					sheet.addCell(label);
					label=new Label(1,row,(beginDate+"~"+endDate),titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(1, row, 6, row);
					row++;
					label=new Label(0,row,"产品编号",_titleFormate);
					sheet.addCell(label);
					label=new Label(1,row,"产品名称",_titleFormate);
					sheet.addCell(label);
					label=new Label(2,row,"数量",_titleFormate);
					sheet.addCell(label);
					label=new Label(3,row,"单价",_titleFormate);
					sheet.addCell(label);
					label=new Label(4,row,"厂家结算金额",_titleFormate);
					sheet.addCell(label);
					label=new Label(5,row,"门店单价",_titleFormate);
					sheet.addCell(label);
					label=new Label(6,row,"门店结账金额",_titleFormate);
					sheet.addCell(label);
					row++;
					label=new Label(0,row,"状态：",_titleFormate);
					sheet.addCell(label);
					label=new Label(1,row,"已收货",_titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(1, row, 6, row);
					row++;
					BigDecimal total = new BigDecimal(0),total1 = new BigDecimal(0);
					List<CommonBean> list = dataSet.get("four");
					for(CommonBean bean: list){
						sheet.addCell(new Label(0, row, bean.getAttr3(), textFormate));
						sheet.addCell(new Label(1, row, bean.getAttr4(), textFormate));
						sheet.addCell(new Label(2, row, bean.getAttr5(), textFormate));
						sheet.addCell(new Label(3, row, bean.getAttr11(), textFormate));
						sheet.addCell(new Label(4, row, bean.getAttr6(), textFormate));
						sheet.addCell(new Label(5, row, bean.getAttr7(), textFormate));
						sheet.addCell(new Label(6, row, bean.getAttr8(), textFormate));
						total = total.add(new BigDecimal(bean.getAttr6()));
						total1 = total1.add(new BigDecimal(bean.getAttr8()));
						row++;
					}
					label=new Label(0,row,"",textFormate);
					sheet.addCell(label);
					sheet.mergeCells(0, row, 2, row);
					label=new Label(3,row,"合计",_titleFormate);
					sheet.addCell(label);
					label=new Label(4,row, total.toString() ,_titleFormate);
					sheet.addCell(label);
					label=new Label(5,row,"",textFormate);
					sheet.addCell(label);
					label=new Label(6,row, total1.toString(),_titleFormate);
					sheet.addCell(label);
					row = 3;
					label=new Label(8,row,"产品编号",_titleFormate);
					sheet.addCell(label);
					label=new Label(9,row,"产品名称",_titleFormate);
					sheet.addCell(label);
					label=new Label(10,row,"数量",_titleFormate);
					sheet.addCell(label);
					label=new Label(11,row,"单价",_titleFormate);
					sheet.addCell(label);
					label=new Label(12,row,"厂家结算金额",_titleFormate);
					sheet.addCell(label);
					label=new Label(13,row,"门店单价",_titleFormate);
					sheet.addCell(label);
					label=new Label(14,row,"门店结账金额",_titleFormate);
					sheet.addCell(label);
					row++;
					label=new Label(8,row,"状态：",_titleFormate);
					sheet.addCell(label);
					label=new Label(9,row,"已发货",_titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(9, row, 14, row);
					row++;
					total = new BigDecimal(0);total1 = new BigDecimal(0);
					list = dataSet.get("three");
					for(CommonBean bean: list){
						sheet.addCell(new Label(8, row, bean.getAttr3(), textFormate));
						sheet.addCell(new Label(9, row, bean.getAttr4(), textFormate));
						sheet.addCell(new Label(10, row, bean.getAttr5(), textFormate));
						sheet.addCell(new Label(11, row, bean.getAttr11(), textFormate));
						sheet.addCell(new Label(12, row, bean.getAttr6(), textFormate));
						sheet.addCell(new Label(13, row, bean.getAttr7(), textFormate));
						sheet.addCell(new Label(14, row, bean.getAttr8(), textFormate));
						total = total.add(new BigDecimal(bean.getAttr6()));
						total1 = total1.add(new BigDecimal(bean.getAttr8()));
						row++;
					}
					label=new Label(8,row,"",textFormate);
					sheet.addCell(label);
					sheet.mergeCells(8, row, 10, row);
					label=new Label(11,row,"合计",_titleFormate);
					sheet.addCell(label);
					label=new Label(12,row, total.toString() ,_titleFormate);
					sheet.addCell(label);
					label=new Label(13,row,"",textFormate);
					sheet.addCell(label);
					label=new Label(14,row, total1.toString(),_titleFormate);
					sheet.addCell(label);
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
		//导出Excel查询
		@Action(value="loadCompanyExcel", results={@Result(name ="load_success", location="/InvoicingControl/IC021/ic021CompanyExcel.jsp")}) 
		public String loadCompanyExcel() {
			try{
				totalMap = ic021Service.queryCompanySet(beginDate, endDate,projectKind);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		public void createCompanyExcel() throws WriteException, IOException{
			try{
				int excelLength0=7;
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("(分门店)供应商结账", 0);
				//构造表头
				sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
				WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
				
				WritableCellFormat titleFormate =new WritableCellFormat(bold);
				titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat _titleFormate =new WritableCellFormat(bold);
				_titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
				_titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				_titleFormate.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				WritableCellFormat numberFormate =new WritableCellFormat();
				numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				WritableCellFormat textFormate =new WritableCellFormat();
				textFormate.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
						WritableFont.NO_BOLD, false, 
						UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
				WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
				totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
				totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
				
				/****创建要显示的内容***/
				//标题
				Label head=new Label(0,0,"(分门店)供应商结账",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				
				if(totalMap!=null && totalMap.size()>0){
					int row = 2, col=0;
					List<CommonBean> companySet = this.ic021Service.loadCompanyData();
					Label label=new Label(0,row,"结算日期：",titleFormate);
					sheet.addCell(label);
					label=new Label(1,row,(beginDate+"~"+endDate),titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(1, row, 6, row);
					for (Map.Entry<String, Map<String, List<CommonBean>>> entry : totalMap.entrySet()) {
						row=3;
						String key = entry.getKey();
						String compName = "";
						for (CommonBean commonBean : companySet) {
							if(StringUtils.equals(key, commonBean.getAttr1())){
								compName=key +" "+ commonBean.getAttr2();
								break;
							}
						}
						Map<String, List<CommonBean>> _dataSet = entry.getValue();
						label=new Label(col,row,"门店：",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+1,row, compName,_titleFormate);
						sheet.addCell(label);
						sheet.mergeCells(col+1, row, col+6, row);
						row++;
						label=new Label(col,row,"产品编号",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+1,row,"产品名称",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+2,row,"数量",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+3,row,"单价",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+4,row,"厂家结算金额",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+5,row,"门店单价",_titleFormate);
						sheet.addCell(label);
						label=new Label(col+6,row,"门店结账金额",_titleFormate);
						sheet.addCell(label);
						row++;
						List<CommonBean> list = _dataSet.get("four");
						if(list!=null && list.size()>0){
							label=new Label(col,row,"状态：",_titleFormate);
							sheet.addCell(label);
							label=new Label(col+1,row,"已收货",_titleFormate);
							sheet.addCell(label);
							sheet.mergeCells(col+1, row, col+6, row);
							row++;
							BigDecimal total = new BigDecimal(0),total1 = new BigDecimal(0);
							for(CommonBean bean: list){
								sheet.addCell(new Label(col+0, row, bean.getAttr3(), textFormate));
								sheet.addCell(new Label(col+1, row, bean.getAttr4(), textFormate));
								sheet.addCell(new Label(col+2, row, bean.getAttr5(), textFormate));
								sheet.addCell(new Label(col+3, row, bean.getAttr11(), textFormate));
								sheet.addCell(new Label(col+4, row, bean.getAttr6(), textFormate));
								sheet.addCell(new Label(col+5, row, bean.getAttr7(), textFormate));
								sheet.addCell(new Label(col+6, row, bean.getAttr8(), textFormate));
								total = total.add(new BigDecimal(bean.getAttr6()));
								total1 = total1.add(new BigDecimal(bean.getAttr8()));
								row++;
							}
							label=new Label(col,row,"",textFormate);
							sheet.addCell(label);
							sheet.mergeCells(col, row, col+2, row);
							label=new Label(col+3,row,"合计",_titleFormate);
							sheet.addCell(label);
							label=new Label(col+4,row, total.toString() ,_titleFormate);
							sheet.addCell(label);
							label=new Label(col+5,row,"",textFormate);
							sheet.addCell(label);
							label=new Label(col+6,row, total1.toString(),_titleFormate);
							sheet.addCell(label);
							row++;
						}
						list = _dataSet.get("three");
						if(list!=null && list.size()>0){
							label=new Label(col,row,"",_titleFormate);//空行
							sheet.addCell(label);
							sheet.mergeCells(col, row, col+6, row);
							row++;
							label=new Label(col,row,"状态：",_titleFormate);
							sheet.addCell(label);
							label=new Label(col+1,row,"已发货",_titleFormate);
							sheet.addCell(label);
							sheet.mergeCells(col+1, row, col+6, row);
							row++;
							BigDecimal total = new BigDecimal(0),total1 = new BigDecimal(0);
							for(CommonBean bean: list){
								sheet.addCell(new Label(col+0, row, bean.getAttr3(), textFormate));
								sheet.addCell(new Label(col+1, row, bean.getAttr4(), textFormate));
								sheet.addCell(new Label(col+2, row, bean.getAttr5(), textFormate));
								sheet.addCell(new Label(col+3, row, bean.getAttr11(), textFormate));
								sheet.addCell(new Label(col+4, row, bean.getAttr6(), textFormate));
								sheet.addCell(new Label(col+5, row, bean.getAttr7(), textFormate));
								sheet.addCell(new Label(col+6, row, bean.getAttr8(), textFormate));
								total = total.add(new BigDecimal(bean.getAttr6()));
								total1 = total1.add(new BigDecimal(bean.getAttr8()));
								row++;
							}
							label=new Label(col,row,"",textFormate);
							sheet.addCell(label);
							sheet.mergeCells(col, row, col+2, row);
							label=new Label(col+3,row,"合计",_titleFormate);
							sheet.addCell(label);
							label=new Label(col+4,row, total.toString() ,_titleFormate);
							sheet.addCell(label);
							label=new Label(col+5,row,"",textFormate);
							sheet.addCell(label);
							label=new Label(col+6,row, total1.toString(),_titleFormate);
							sheet.addCell(label);
						}
						_dataSet=null;
						col+=8;
					}
				}
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
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	
	public String getProjectKind() {
		return projectKind;
	}

	public void setProjectKind(String projectKind) {
		this.projectKind = projectKind;
	}

	@JSON(serialize=false)
	public IC021Service getIc021Service() {
		return ic021Service;
	}
	@JSON(serialize=false)
	public void setIc021Service(IC021Service ic021Service) {
		this.ic021Service = ic021Service;
	}

	public Map<String, List<CommonBean>> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Map<String, List<CommonBean>> dataSet) {
		this.dataSet = dataSet;
	}

	public List<CommonBean> getComList() {
		return comList;
	}

	public void setComList(List<CommonBean> comList) {
		this.comList = comList;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
	public Map<String, Map<String, List<CommonBean>>> getTotalMap() {
		return totalMap;
	}
	public void setTotalMap(Map<String, Map<String, List<CommonBean>>> totalMap) {
		this.totalMap = totalMap;
	}
}
