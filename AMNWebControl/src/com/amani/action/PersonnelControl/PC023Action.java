package com.amani.action.PersonnelControl;

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

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.model.BlackList;
import com.amani.service.PersonnelControl.PC023Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc023")
public class PC023Action {
	@Autowired
	private PC023Service pc023Service;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<BlackList> lsDataSet;
	private OutputStream os;

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	// 查询短信
	@Action(value = "loadDataSet", results = {
			@Result(type = "json", name = "load_success"),
			@Result(type = "json", name = "load_failure") })
	public String loadDataSet() {
		// 根据短信接收时间与结束时间查询短信信息
		this.lsDataSet = this.pc023Service
				.loadDateSetByStartFromDateAndEndFromDate(strFromDate,
						strToDate);
		return SystemFinal.LOAD_SUCCESS;
	}

	@Action(value = "loadpc023Excel", results = {
			@Result(name = "load_success", location = "/PersonnelControl/PC023/pc023Excel.jsp"),
			@Result(name = "load_failure", location = "/PersonnelControl/PC023/pc023Excel.jsp") })
	public String loadpc023Excel() {
		// 根据短信接收时间与结束时间查询短信信息
		this.lsDataSet = this.pc023Service
				.loadDateSetByStartFromDateAndEndFromDate(strFromDate,
						strToDate);
		return SystemFinal.LOAD_SUCCESS;

	}

	public void createExcel() throws WriteException, IOException {
		try {
			// 创建excel工作簿
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			// 创建新的一页
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			// 构造表头

			WritableFont bold = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);// 设置字体种类和黑体显示

			WritableCellFormat titleFormate = new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);// 单元格的内容水平居中
			titleFormate
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直居中

			WritableCellFormat numberFormate = new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);// 单元格的内容水平右对齐
			numberFormate
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直居中

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
			WritableCellFormat totalNumberFormate = new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);// 单元格的内容水平右对齐
			totalNumberFormate
					.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直居中

			/** **创建要显示的内容** */
			// 标题
			Label head = new Label(0, 0, "短信查询报表", titleFormate);
			sheet.setRowView(0, 500, false);
			sheet.mergeCells(0, 0, 11, 0);
			sheet.addCell(head);

			// 制表日期：
			Label makeDate = new Label(0, 1, "制表日期：", titleFormate);
			sheet.addCell(makeDate);

			// 制表日期值
			Label makeDateData = new Label(1, 1, ""
					+ CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);

			Label str0 = new Label(0, 2, "手机号码", titleFormate);
			sheet.addCell(str0);

			Label str1 = new Label(1, 2, "接收日期", titleFormate);
			sheet.addCell(str1);

			Label str2 = new Label(2, 2, "内容", titleFormate);
			sheet.addCell(str2);

			if (this.lsDataSet != null && this.lsDataSet.size() > 0) {
				for (int i = 0; i < this.lsDataSet.size(); i++) {
					sheet.addCell(new Label(0, i + 3, ""
							+ this.lsDataSet.get(i).getMobilephone()));
					sheet.addCell(new Label(1, i + 3, ""
							+ this.lsDataSet.get(i).getOperdate()));
					sheet.addCell(new Label(2, i + 3, ""
							+ this.lsDataSet.get(i).getContent()));
				}
			}
			lsDataSet = null;
			workbook.write();
			workbook.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStrMessage() {
		return strMessage;
	}

	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}

	@JSON(serialize = false)
	public PC023Service getPc023Service() {
		return pc023Service;
	}

	@JSON(serialize = false)
	public void setPc023Service(PC023Service pc023Service) {
		this.pc023Service = pc023Service;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public List<BlackList> getLsDataSet() {
		return lsDataSet;
	}

	public void setLsDataSet(List<BlackList> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

}
