
//显示模块标题，结合title.frag使用
function showTitle(title,imgPath,compId,compName)
{
	document.writeln('<table width="100%" cellpadding="0" cellspacing="0">');
	document.writeln('<tr><td width="621"><img src="'+imgPath+'" alt="'+title+'" /></td>');
	document.writeln('<td width="107"><span>'+compId+'-'+compName+'</span></td>');
	document.writeln('<td width="36" align="right"><input name="close" type="submit" class="close" id="close" onclick="parent.closeView()" onmouseover=" this.className=');
	document.write("'close1'" );
	document.write('" onmouseout="this.className=');
	document.write("'close'");
	document.write('" value="" /></td>');
	document.writeln('</tr></table>');
}
//从组织结构条中获得当前选择的用于查询条件的组织ID
//该方法结合search.frag文件使用
function loadCurCompId(lvl)
{
}
function getCurOrgFromSearchBar()
{
		var elemId = null;
		var i=2;
		var element;
		var ret="";
	try{	
		elemId = "level_"+i;
		element = document.getElementById(elemId);             
		while(typeof(element)!="undefined"&&element != null)
		{
			var seltext = element.options[element.selectedIndex].text;      
			var selvalue = element.options[element.selectedIndex].value;   
		                         
			if(seltext == "*"&&i==2)
			{
				ret = "001";
				return ret;
			}
			else if(seltext == "*"&&i!=2)
			{
				i--;
				tmpElemId = "level_"+i;
				tmpElem = document.getElementById(tmpElemId);
				var elemValue = tmpElem.options[tmpElem.selectedIndex].value;
				while(elemValue == "no")
				{
					i--;
					if(i>1)
					{
						tmpElemId = "level_"+i;
						tmpElem = document.getElementById(tmpElemId);
						elemValue = tmpElem.options[tmpElem.selectedIndex].value;
					}
					else if(i == 1)
					{
						ret = "001";
						return ret;
					}
					
				}
				ret = elemValue;//tmpElem.options[tmpElem.selectedIndex].value;
			
				return ret;
			}
			i++;                                            
			elemId = "level_"+i;                            
			element = document.getElementById(elemId);      
		}
	
		i--;
		tmpElemId = "level_"+i;
		tmpElem = document.getElementById(tmpElemId);
		ret = tmpElem.options[tmpElem.selectedIndex].value;
	}catch(Exception)	{alert(Exception.message);}
		return ret;
}

//在组织结构块，选择某个组织，联动获取并显示子组织列表
function selectSomeOrg(orgLevel,maxLevel)
{
		
		var selid = "level_"+orgLevel;
		var orgId = document.getElementById(selid).value;
		var param=null;
     	var requestUrl ="orgController/loadChildOrgs.action?orgLevel="+orgLevel+"&orgId="+orgId+"&maxLevel="+maxLevel;
		var responseMethod="processOrgResponse";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
	
}

function selectSomeOrgs1(orgLevel,maxLevel)
{
		
		var selid = "s1level_"+orgLevel;
		var orgId = document.getElementById(selid).value;
		var param=null;
     	var requestUrl ="orgController/loadChildOrgs.action?orgLevel="+orgLevel+"&orgId="+orgId+"&maxLevel="+maxLevel;
		var responseMethod="processOrgS1Response";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
	
}
//选择某个组织后的异步响应
function processOrgResponse(request)
{
		try{
			var action = eval( "("+request.responseText+")");//.parseJSON() ;
			
			var childLevel = action.orgLevel+1;
			var childid = "level_"+childLevel;
			var childOrgs =action.childOrgs;
			
			//patch bug
			var keys = new Array();
			for(var keyx in childOrgs)
			{
				keys.push(keyx);
			}
			keys.sort();
			//
			
			
			clearOption(childid);

			var sselect = document.getElementById(childid);
			addOption("*","*",sselect);
			for(var kk=0;kk<keys.length;kk++)
			{
				if(childOrgs.hasOwnProperty(keys[kk]))
				{ 
					var tmpKey = keys[kk];
					addOption(tmpKey,tmpKey+"-"+childOrgs[tmpKey.toString()],sselect);
				}
			}
			/*for(var key in childOrgs)
			{
				if(childOrgs.hasOwnProperty(key))
				{ 
					addOption(key,key+"-"+childOrgs[key.toString()],sselect);
				}
			}*/

			var maxLevel = action.maxLevel;
			for(var i=childLevel+1;i<=maxLevel;i++)
			{
				var elementid = "level_"+i;
				clearOption(elementid);
				elem = document.getElementById(elementid);
				addOption("*","*",elem);
			}	
			
		}
		catch(Exception)
		{
			alert(Exception.message);
			return;
		}
}

//选择某个组织后的异步响应
function processOrgS1Response(request)
{
		try{
			var action = eval( "("+request.responseText+")");//.parseJSON() ;
			
			var childLevel = action.orgLevel+1;
			var childid = "s1level_"+childLevel;
			var childOrgs =action.childOrgs;
			
			//patch bug
			var keys = new Array();
			for(var keyx in childOrgs)
			{
				keys.push(keyx);
			}
			keys.sort();
			//
			
			
			clearOption(childid);

			var sselect = document.getElementById(childid);
			addOption("*","*",sselect);
			for(var kk=0;kk<keys.length;kk++)
			{
				if(childOrgs.hasOwnProperty(keys[kk]))
				{ 
					var tmpKey = keys[kk];
					addOption(tmpKey,tmpKey+"-"+childOrgs[tmpKey.toString()],sselect);
				}
			}
			/*for(var key in childOrgs)
			{
				if(childOrgs.hasOwnProperty(key))
				{ 
					addOption(key,key+"-"+childOrgs[key.toString()],sselect);
				}
			}*/

			var maxLevel = action.maxLevel;
			for(var i=childLevel+1;i<=maxLevel;i++)
			{
				var elementid = "s1level_"+i;
				clearOption(elementid);
				elem = document.getElementById(elementid);
				addOption("*","*",elem);
			}	
			
		}
		catch(Exception)
		{
			alert(Exception.message);
			return;
		}
}
//设置查询条件参数值
	function setQueryParam()
	{

	}
//生成查询条件，可被重写
	function genQueryCondition()
	{
		
	}
//基本查询处理。此函数在执行查询和分页操作的时候会被调用
//此函数可被重写
	function baseQuery()
	{

	}
//执行查询操作，可被重写
	function query()
	{
	
	}
//查询第一页数据，可被重写
	function firstPage()
	{

	}
//查询前一页数据，可被重写
	function previousPage()
	{
	}
//查询下一页数据，可被重写
	function nextPage()
	{

	}
//查询最后一页数据，可被重写	
	function lastPage()
	{

	}
//发送异步查询请求，可被重写
	function sendQueryRequest(url)
	{
		try{
			var params = null;
			var myAjax = new Ajax.Request(
						url,
						{
							method:'post',
							parameters:params,
							onComplete:processQueryResponse,
							asynchronous:true
						});
		}
		catch(Exception)
		{
			return;
		}
	}
//处理异步提交查询之后的响应处理，可被重写
	function processQueryResponse(request)
	{
		
	}
	
//---------------------------------------------------------------------------------------------------------
//以下函数基本属于通用函数，暂时未制作成单独文件
//---------------------------------------------------------------------------------------------------------

//清除grid中的所有记录
//gridBodyId - grid的body部分的控件ID
function clearPreviousResult(gridBodyId)
{
	var grid = document.getElementById(gridBodyId);
	while(grid.childNodes.length>1)
	{
		grid.removeChild(grid.childNodes[1]);
	}
}
function clearPreviousResultEX(gridBodyId,fromIndex)
{
	var grid = document.getElementById(gridBodyId);
	while(grid.childNodes.length>fromIndex)
	{
		grid.removeChild(grid.childNodes[fromIndex]);
	}
}
//清除grid中的所有行，包括标题行
//gridBodyId - grid的body部分的控件ID
function deleteAllRows(gridBodyId)
{
	var i=1;
	var grid = document.getElementById(gridBodyId);
	while(grid.childNodes.length>=1)
	{	
		grid.removeChild(grid.childNodes[0]);
	}
}

//为grid（即table）增加一行记录
//result - 一条记录，Array类型
//gridBodyId - grid的body部分的控件ID
function addRow(result,gridBodyId)
{
	var cell;
	var row = document.createElement("tr");
	
	for(var i=0;i<result.length;i++)
	{
		cell = createCellWithText(result[i]);
		row.appendChild(cell);
	}
	document.getElementById(gridBodyId).appendChild(row);
}
//为grid增加列头
//gridBodyId - grid的body部分的控件ID
//captions - grid的列标题集合
function addGridColumnHeader(gridBodyId,captions)
{
	addRow(captions,gridBodyId);
}
//为grid（即table）增加一列
//gridId - grid的body部分的控件ID
function addColumn(gridId)
{
	var cell;
	var grid = document.getElementById(gridId);

	for(var i=0;i<grid.rows.length;i++)
	{
		var row = grid.rows[i];
		cell = createCellWithText("");
		row.appendChild(cell);
	}
}
//为grid（即table）增加columnCount列
//gridId - grid的body部分的控件ID
function addColumnEX(gridId,columnCount)
{
	for(var i=0;i<columnCount;i++)
	{
		addColumn(gridId);
	}
}

//在<select>控件中增加一个选择项
//key - 用于设置<option>中的value属性
//value - 用于设置<option>中的text显示文本
//selectElement - <select>对象
	function addOption(key,value,selectElement)
	{	
		var option = null;
		option = document.createElement("option");
		option.appendChild(document.createTextNode(value));
		option.setAttribute("value",key);
		selectElement.appendChild(option);
	}
//清除<select>控件中所有的选择项
//elementId - <select>对象ID
	function clearOption(elementId)
	{
		var selectElement = document.getElementById(elementId);
		while(selectElement.childNodes.length>0)
		{
			selectElement.removeChild(selectElement.childNodes[0]);
		}
	}
	
//创建一个带有文本的表格cell
function createCellWithText(text)
{
	var cell = document.createElement("td");
	var textNode = document.createTextNode(text);
	cell.appendChild(textNode);
		
	return cell;
}

function insertHtmlIntoCell(cell,html)
{
	cell.innerHTML = html;
}

//向表格中的单元格中插入HTML代码
////tableId - 表格ID
//columnIndex - 列的索引号，从0开始
//html - HTML文本
//startRowIndex - 起始行索引，从0开始
//endRowIndex -结束行索引
function insertHtmlIntoColumn(tableId,columnIndex,html,startRowIndex,endRowIndex)
{
	var grid = document.getElementById(tableId);
	
	if(startRowIndex<0 || endRowIndex>=grid.childNodes.length)
	{
		return ;
	}
	for(var i=startRowIndex;i<=endRowIndex&&i>=startRowIndex;i++)
	{
		var cell = grid.childNodes[i].cells[columnIndex];
		cell.innerHTML = html;
	}
}
//调整表格中的某列的对齐方式
//tableId - 表格ID
//columnIndex - 列的索引号，从0开始
function adjustAlign(tableId,columnIndex)
{
	var grid = document.getElementById(tableId);
	for(var i=1;i<grid.childNodes.length;i++)
	{
		var cell = grid.childNodes[i].cells[columnIndex];
		cell.setAttribute("align","right");
	}
	
}
function adjustColumnBgcolor(tableId,columnIndex,bgcolor)
{
	var grid = document.getElementById(tableId);
	for(var i=1;i<grid.childNodes.length;i++)
	{
		var cell = grid.childNodes[i].cells[columnIndex];
		//cell.setAttribute("bgcolor",bgcolor);
		cell.style.backgroundColor = bgcolor;
	}
}
function adjustColumnBgcolorEX(tableId,columnIndex,bgcolor,fromRow,toRow)
{
	var grid = document.getElementById(tableId);
	if(toRow==null||toRow=="undefined") toRow = grid.childNodes.length-1;
	for(var i=fromRow;i<=toRow;i++)
	{
		var cell = grid.childNodes[i].cells[columnIndex];
		//cell.setAttribute("bgcolor",bgcolor);
		cell.style.backgroundColor = bgcolor;
	}
}
function setColumnHeaders(gridId,captions)
{
	var grid = document.getElementById(gridId);
	var columnCount = grid.childNodes[0].cells.length;
	if(columnCount >= captions.length)
	{
		for(var i=0;i<captions.length;i++)
		{
			var columnHeader = grid.childNodes[0].cells[i];
			columnHeader.innerText = captions[i];
		}
	}
	else
	{
		for(var i=0;i<coumnCount;i++)
		{
			var columnHeader = grid.childNodes[0].cells[i];
			columnHeader.innerText = captions[i];
		}
	}
}


//调整表格中的某列的对齐方式
//tableId - 表格ID
//columnIndex - 列的索引号，从0开始
//align - 列的对齐方式：right,left,center
function adjustAlignEX(tableId,columnIndex,align,fromRow,toRow)
{
	var grid = document.getElementById(tableId);
	if(fromRow==null||fromRow=="undefined") fromRow = 1;
	if(toRow==null||toRow=="undefined") toRow = grid.childNodes.length-1;
	for(var i=fromRow;i<=toRow;i++)
	{
		var cell = grid.childNodes[i].cells[columnIndex];
		cell.setAttribute("align",align);
	}
	
}
//调整表格中的某行的对齐方式
//tableId - 表格ID
//rowIndex - 行的索引号，从0开始
//align - 行的对齐方式：right,left,center
function adjustRowAlign(tableId,rowIndex,align)
{
	var grid = document.getElementById(tableId);

	if(grid == "undefined"  || grid == null )
	{
		return;
	}
	if(rowIndex<0 || rowIndex>= grid.childNodes.length)
	{
		return;
	}
	var row = grid.rows[rowIndex];
	
	row.align = align;
}
//调整表格中的某行的背景色
//tableId - 表格ID
//rowIndex - 行的索引号，从0开始
//bgcolor - 行的背景色
function adjustBgcolor(tableId,rowIndex,bgcolor)
{
	var grid = document.getElementById(tableId); 
	if(grid == "undefined"  || grid == null )
	{
		return;
	}
	if(rowIndex<0 || rowIndex>= grid.childNodes.length)
	{
		return;
	}
	var row = grid.rows[rowIndex];
	
	row.style.backgroundColor = bgcolor;
}
//设置grid某行的样式 xxx此函数未测试成功
//gridId - grid 的元素ID
//rowIndex - 行的索引号，从0开始
//rowStyle - 行的样式
function setRowStyle(gridId,rowIndex,rowStyle)
{
	var grid = document.getElementById(gridId); 
	if(grid == "undefined"  || grid == null )
	{
		return;
	}
	if(rowIndex<0 || rowIndex>= grid.childNodes.length)
	{
		return;
	}
	var row = grid.rows[rowIndex];
	
	row.style = rowStyle;

}
//获得单选按钮选中的值
function getValueFromRadioGroup(radioElemName)
{
	var ret;
	var radioElems = document.getElementsByName(radioElemName);
	if(radioElems == null || radioElems == "undefined")
	{
		return;
	}
	for(var i=0;i<radioElems.length;i++)
	{
		if(radioElems[i].checked)
		{
			ret = radioElems[i].value;
			break;
		}
	}
	return ret;
}

//格式化界面上金额或数字的显示
//amt - 需要格式化的金额或数字
//precision - 小数点的个数
function maskAmt(amt,precision)
{
		var ret;
		
		var strAmt = amt.toString();
		var afterSplit = strAmt.split(".");
		var integ = afterSplit[0];
		var decimal = afterSplit[1];
		
		if(strAmt == "NaN" || strAmt=="undefined"||strAmt == null)
		{
			return "0";
		}
		
		if(precision == 0)
		{
			return integ;
		}
		if(typeof(decimal)=="undefined"||decimal == null)
		{
			ret = integ +".";
			for(var j=1;j<=precision;j++)
			{
				ret = ret + "0";
			}
			return ret;
		}
		
		var decimalLen = decimal.length;

		if(decimalLen == precision)
		{
			return amt;
		}
		else if(decimalLen < precision)
		{
			for(var i=1;i<=precision-decimalLen;i++)
			{
				decimal = decimal + "0"; 
			}
		}
		else if(decimalLen > precision)
		{
			try{
			decimal = decimal.substring(0,precision);
			}catch(Exception){alert(Exception.message);}
		}
		
		ret = integ+"."+decimal;
		
		return ret;
		
}
//判断字符串是否为NULL。如果为NULL，则返回空字符串
function isnull(str)
{
	if(str==null)
		str = "";
	return str;
}

function fullStr( str )
{
		if( str.length == 1 )
		{
			str = "0" + str;
		}
		return str;
}
//格式化日期
function maskDate( dat )
{	
		var date = dat.toString();
		if( date.length == 8 )
		{
			var temp = date.substring(0,4) + "-" + fullStr(date.substring(4,6)) + "-" + fullStr(date.substring(6,8)) ;
			return temp;
		}
		else
		{
			return date;
		}
}

function unMaskDate( dat)
{
	var date = dat.toString();
	var ret = date;
	if(date.length == 10)
	{
		var ret = date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
	}
	return ret;
}

//判断字符串是否为整数
function checkInteger(input)
{
	 var reg = /^[1-9]+[0-9]*]*$/  ; 
     if (!reg.test(input.rate.value))
     {
         return false;
     }
     return true;
}
//判断字符串是否为数字
function checkIntegerOrFloat(input)
{
	var reg = /^[0-9]+.?[0-9]*$/;     
     if (!reg.test(input.rate.value))
     {
         return false;
     }
     return true;
}
function isnullfornum(str)
{
	if(str==null||str=="")
		str = "0";
	return str;
} 
function getCurrDate()
{
	var currDate;
	var year,month,day;
	var intYear,intMonth,intDay;
	var today;
	today=new Date();
	intYear=today.getYear();
	intMonth=today.getMonth()+1;
	intDay=today.getDate();
	year = intYear; month = intMonth; day = intDay;
	
	currDate = year.toString()+formatSingularNumber(month.toString())+formatSingularNumber(day.toString());
	return currDate;
}