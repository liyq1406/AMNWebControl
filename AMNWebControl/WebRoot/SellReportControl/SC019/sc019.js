var sc019layout=null;
var homeGrid1=null;
var homeGrid2=null;
var homeGrid3=null;
var homeGrid4=null;
var faceGrid1=null;
var faceGrid2=null;
var faceGrid3=null;
var faceGrid4=null;
var faceGrid5=null;
var faceGrid6=null;
var faceGrid7=null;
var hairGrid1=null;
var hairGrid2=null;
var hairGrid3=null;
var hairGrid4=null;
var hairGrid5=null;
var hairGrid6=null;
var vipGrid=null;
var detailGrid=null;
var showDialogmanager=null;
var detialTab=null;
$(function ()
{
   try
   {
	  	//布局
        sc019layout= $("#sc019layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	var height = $(".l-layout").height();
       	var width = $(".l-layout").width();
       	var centerWidth = width-18;
    	$("#h_date").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
    	$("#f_date").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
    	$("#m_date").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
    	$("#v_date").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
    	$("#d_date").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
       	$("#detialTab").ligerTab();
       	detialTab = $("#detialTab").ligerGetTabManager();
       	//通用表格数据表头
    	var columns=[{display:'经营数据', name:'attr1', align:'left', width: 150}];
    	//顾客分层管理
    	var c_columns=[{display:'顾客分层管理', name:'attr1', align:'left', width: 150},
    	               {display:'2014年', name:'attr2', align:'left', width: 100},
    	               {display:'目标', name:'attr3', align:'left', width: 100}];
    	//美容师人均产出表
    	var m_columns=[{display:'美容师人均产出', name:'attr1', align:'left', width: 150},
    	               {display:'人数', name:'attr2', align:'right', width:100}];
    	//美容部员工业绩表
    	var y_columns=[{display:'员工业绩', name:'attr1', align:'left', width: 150},
    	               {display:'职级', name:'attr2', align:'right', width:100},
    	               {display:'总实业绩', name:'attr3', align:'right', width:100},
    	               {display:'总客单', name:'attr4', align:'right', width:100},
    	               {display:'客单价', name:'attr5', align:'right', width:100}];
    	//美发部员工业绩表
    	var yg_columns = y_columns.slice(0);
    	yg_columns.push({display:'烫染占比', name:'attr6', width:100, align:'right'});
    	yg_columns.push({display:'护理占比', name:'attr7', width:100, align:'right'});
    	
    	//营销节奏
    	var yy_columns =[{display:'营销节奏', name:'attr1', width:150, align:'left' },
				{display:'2014年实际达成', name:'attr2', width:200, align:'right'},
				{display:'2015年计划目标', name:'attr3', width:200, align:'right'},
				{display:'增长百分比', name:'attr4', width:180, align:'right'},
				{display:'营销活动计划', name:'attr5', width:300, align:'right'}];
    	for(var i=1; i<=12; i++){
    		var title=i+"月";
    		var attr="month"+i;
    		var field = {display: title, name: attr, align:'right', width: 100};
    		columns.push(field);
    		c_columns.push(field);
    		m_columns.push(field);
    		y_columns.push(field);
    		yg_columns.push(field);
    	}
    	var attr2={display:'合计', name:'attr2', width:100, align:'right'};
    	var attr3={display:'2015年目标', name:'attr3', width:100, align:'right'};
    	var attr4={display:'完成百分比', name:'attr4', width:100, align:'right'};
    	columns.push(attr2);
    	columns.push(attr3);
    	columns.push(attr4);
    	
       	//经营数据表1
       	homeGrid1=$("#homeGrid1").ligerGrid({
            columns: columns, pageSize:50, data: null,     
            width:centerWidth,height:'150',usePager:false
        });
       	
       	//经营数据表2
       	homeGrid2=$("#homeGrid2").ligerGrid({
       		columns: columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
       	homeGrid2.changeHeaderText('attr2', "月均");
       	homeGrid2.changeHeaderText('attr3', "公司平均值");
       	homeGrid2.changeHeaderText('attr4', "差异量");
       	
       	//美发人员产出表
    	homeGrid3=$("#homeGrid3").ligerGrid({
       		columns: columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	homeGrid3.changeHeaderText('attr1', "美发人员产出");
       	homeGrid3.changeHeaderText('attr2', "月均");
       	homeGrid3.changeHeaderText('attr3', "公司平均值");
       	homeGrid3.changeHeaderText('attr4', "差异量");
       	
       	//营销节奏表
    	homeGrid4=$("#homeGrid4").ligerGrid({
       		columns: yy_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	
    	/*********************************美容部**************************************/
    	//美容部经营数据表1
    	faceGrid1=$("#faceGrid1").ligerGrid({
            columns: columns, pageSize:50, data: null,     
            width:centerWidth,height:'150',usePager:false
        });
    	
    	//美容部经营数据表2
    	faceGrid2=$("#faceGrid2").ligerGrid({
       		columns: columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	faceGrid2.changeHeaderText('attr2', "月均");
    	faceGrid2.changeHeaderText('attr3', "市场平均值");
    	faceGrid2.changeHeaderText('attr4', "差异量");
    	
    	//项目信息表
    	faceGrid3=$("#faceGrid3").ligerGrid({
       		columns: columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	faceGrid3.changeHeaderText('attr1', "项目信息");
       	
    	//顾客分层管理表
    	c_columns.push({display:'合计', name:'attr4', width:100, align:'right'});
    	faceGrid4=$("#faceGrid4").ligerGrid({
    		columns: c_columns, pageSize:50, data: null,     
    		width:centerWidth,height:'150',usePager:false
    	});
    	
    	//美容师人均产出表
    	m_columns.push({display:'月均', name:'attr3', width:100, align:'right'});
    	m_columns.push({display:'市场平均值', name:'attr4', width:100, align:'right'});
    	m_columns.push({display:'差异量', name:'attr5', width:100, align:'right'});
    	faceGrid5=$("#faceGrid5").ligerGrid({
       		columns: m_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	
       	//员工业绩表
    	faceGrid6=$("#faceGrid6").ligerGrid({
       		columns: y_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	
       	//营销节奏表
    	faceGrid7=$("#faceGrid7").ligerGrid({
       		columns: yy_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	
    	/*********************************美发部**************************************/
    	//美发部经营数据表1
    	hairGrid1=$("#hairGrid1").ligerGrid({
            columns: columns, pageSize:50, data: null,     
            width:centerWidth,height:'150',usePager:false
        });
    	
    	//美发部经营数据表2
    	hairGrid2=$("#hairGrid2").ligerGrid({
       		columns: columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	hairGrid2.changeHeaderText('attr2', "月均");
    	hairGrid2.changeHeaderText('attr3', "市场平均值");
    	hairGrid2.changeHeaderText('attr4', "差异量");
       	
    	//美发师人均产出表
    	hairGrid3=$("#hairGrid3").ligerGrid({
       		columns: m_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	hairGrid3.changeHeaderText('attr1', "美发师人均产出");
    	
    	//顾客分层管理表
    	hairGrid4=$("#hairGrid4").ligerGrid({
    		columns: c_columns, pageSize:50, data: null,     
    		width:centerWidth,height:'150',usePager:false
    	});
    	
       	//员工业绩表
    	hairGrid5=$("#hairGrid5").ligerGrid({
       		columns: yg_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	hairGrid5.changeHeaderText('attr1', "美发师人均产出");
    	
       	//营销节奏表
    	hairGrid6=$("#hairGrid6").ligerGrid({
       		columns: yy_columns, pageSize:50, data: null,     
       		width:centerWidth,height:'150',usePager:false
       	});
    	
        $("#h_search").ligerButton({
             text: '查询', width: 110,
	         click: function(){
	             loadDataSet("h", null, 1);
	             loadDataSet("h", homeGrid1, 2);
	             loadDataSet("h", homeGrid2, 3);
	             loadDataSet("h", homeGrid3, 4);
	             loadDataSet("h", homeGrid4, 5);
	         }
         });
        $("#h_excel").ligerButton({
             text: '转Excel', width: 110,
	         click: function(){
	             loadDataSetExcel("h", 1);
	         }
         });
        
        $("#f_search").ligerButton({
            text: '查询', width: 110,
	         click: function(){
	             loadDataSet("f", null, 1);
	             loadDataSet("f", faceGrid1, 2);
	             loadDataSet("f", faceGrid2, 3);
	             loadDataSet("f", faceGrid3, 4);
	             loadDataSet("f", faceGrid4, 5);
	             loadDataSet("f", faceGrid5, 6);
	             loadDataSet("f", faceGrid6, 7);
	             loadDataSet("f", faceGrid7, 8);
	         }
        });
        $("#f_excel").ligerButton({
            text: '转Excel', width: 110,
	         click: function(){
	             loadDataSetExcel("f", 2);
	         }
        });
        
        $("#m_search").ligerButton({
            text: '查询', width: 110,
	         click: function(){
	        	 loadDataSet("m", null, 1);
	             loadDataSet("m", hairGrid1, 2);
	             loadDataSet("m", hairGrid2, 3);
	             loadDataSet("m", hairGrid3, 4);
	             loadDataSet("m", hairGrid4, 5);
	             loadDataSet("m", hairGrid5, 6);
	             loadDataSet("m", hairGrid6, 7);
	         }
        });
        $("#m_excel").ligerButton({
            text: '转Excel', width: 110,
	         click: function(){
	             loadDataSetExcel("m", 3);
	         }
        });
        var today = new Date();
		var intYear=today.getFullYear();
		var intMonth=today.getMonth();
		var today = intYear.toString()+"-"+fullStr(intMonth.toString());
		loadCompSet();//初始化门店下拉框
		$('#h_date').val(today);
		$('#f_date').val(today);
		$('#m_date').val(today);
		$('#v_date').val(today);
		$('#d_date').val(today);
		/*********************************会员分层*************************************/
		vipGrid=$("#vipGrid").ligerGrid({
            columns: [
            {display:'编号', name:'attr1', width:70, align:'left'},
            {display:'名称', name:'attr2', width:120, align:'left'},
            {display:'部门', name:'attr3', width:100, align:'left'},
            {display:'会员数量', columns:
            	[
            	 	{display:'新增会员', name:'attr4', width:70, align:'right'},
            	 	{display:'在册会员', name:'attr5', width:70, align:'right'},
            	 	{display:'常到会员', name:'attr6', width:70, align:'right'},
					{display:'有效会员', name:'attr7', width:70, align:'right'},
					{display:'沉睡会员', name:'attr8', width:70, align:'right'},
					{display:'流失会员', name:'attr9', width:70, align:'right'}
				]
            },
            {display:'顾客分层管理', columns:
            	[
            	 {display:'大客户', name:'attr10', width:70, align:'right'},
            	 {display:'总金额', name:'month1', width:70, align:'right'},
            	 {display:'A+', name:'month2', width:70, align:'right'},
            	 {display:'总金额', name:'month3', width:70, align:'right'},
            	 {display:'A', name:'month4', width:70, align:'right'},
            	 {display:'总金额', name:'month5', width:70, align:'right'},
            	 {display:'B', name:'month6', width:70, align:'right'},
            	 {display:'总金额', name:'month7', width:70, align:'right'},
            	 {display:'C', name:'month8', width:70, align:'right'},
            	 {display:'总金额', name:'month9', width:70, align:'right'},
            	 {display:'D', name:'month10', width:70, align:'right'},
            	 {display:'总金额', name:'month11', width:70, align:'right'},
            	 {display:'E', name:'month12', width:70, align:'right'},
            	 {display:'总金额', name:'attr11', width:70, align:'right'}
            	 ]
            }],  pageSize:50, 
            data: null,      
            width: width,
            height:height-100,
            rownumbers: false,usePager: true
        });
		$("#v_search").ligerButton({
            text: '查询', width: 110,
	         click: function(){
	        	loadDataSet("v", vipGrid, 0);
	         }
        });
        $("#v_excel").ligerButton({
            text: '转Excel', width: 110,
	         click: function(){
	        	 loadDataSetExcel("v", 4);
	         }
        });
        /*********************************会员明细*************************************/
		detailGrid=$("#detailGrid").ligerGrid({
            columns: [
	            {display:'门店', name:'attr1', width:70, align:'left'},
	            {display:'序号', name:'attr2', width:70, type:'int', align:'left'},
	            {display:'姓名', name:'attr3', width:100, align:'left'},
	            {display:'性别', name:'attr4', width:100, align:'left'},
	            {display:'年龄', name:'attr5', width:100, type:'int', align:'left'},
	            {display:'经济情况', name:'attr6', width:100, align:'left'},
	            {display:'注册时间', name:'attr7', width:100, type:'int', align:'left'},
	            {display:'去年消费金额', name:'attr8', width:100, type:'int', align:'right'},
	            {display:'消费项目', name:'attr9', width:100, align:'left'},
	            {display:'去年层级', name:'attr10', width:100, align:'left'},
	            {display:'今年消费金额', name:'month1', width:100, type:'int', align:'right'},
	            {display:'今年计划', name:'month2', width:100, align:'left'},
	            {display:'目标层级', name:'month3', width:100, align:'left'},
	            {display:'现有项目', name:'month4', width:100, align:'left',
	            render: function (item){
                    return  item.month4.replace(/;/g, "</div><div style='text-align:left;margin:5px 3px;'>");
                }},
	            {display:'疗程金额', name:'month5', width:100, align:'right', 
                render: function (item){
                    return  item.month5.replace(/;/g, "</div><div style='text-align:right;margin:5px 3px;'>");
                }},
	            {display:'规划项目', name:'month6', width:100, align:'left'},
	            {display:'预计出单时间', name:'month7', width:100, align:'left'},
	            {display:'可能障碍', name:'month8', width:100, align:'left'},
	            {display:'攻克方法', name:'month9', width:100, align:'left'},
	            {display:'负责美容师', name:'month10', width:100, align:'left'},
	            {display:'其它备注', name:'month11', width:100, align:'left'}
            ],pageSize:30, 
            data: null,      
            width: width,
            height:height-100,
            usePager: true,
            fixedCellHeight:false
        });
		$("#d_search").ligerButton({
            text: '查询', width: 110,
	         click: function(){
	        	 loadDataSet("d", detailGrid, 0);
	         }
        });
        $("#d_excel").ligerButton({
            text: '转Excel', width: 110,
	         click: function(){
	        	 loadDataSetExcel("d", 5);
	         }
        });
        $("#pageloading").hide();
	}catch(e){alert(e.message);}
});
//加载门店
function loadCompSet(){
	var url = contextURL+"/sc019/loadCompanySet.action";
	$.post(url, null, function(data){
		var list = data.compSet;
		var homeComp=document.getElementById("h_company");
		clearOption("h_company");
		var faceComp=document.getElementById("f_company");
		clearOption("f_company");
		var hairComp=document.getElementById("m_company");
		clearOption("m_company");
		var vipComp=document.getElementById("v_company");
		clearOption("v_company");
		var detailComp=document.getElementById("d_company");
		clearOption("d_company");
		if(list != null && list.length>0){
			$.each(list, function(i, comp){
				var compno = comp.compno;
				var compname = comp.compname;
				addOption(compno, compname, homeComp);
				addOption(compno, compname, faceComp);
				addOption(compno, compname, hairComp);
				addOption(compno, compname, vipComp);
				addOption(compno, compname, detailComp);
			});
		}
	}).error(function(e){$.ligerDialog.error(e.message);});
}

//加载数据集
function loadDataSet(prefix, grid, sysStatus){	
	var showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
	var url = contextURL+"/sc019/load"+prefix+"Set.action";
	var strCurCompId=$("#"+prefix+"_company").val();
	var strDate=$("#"+prefix+"_date").val();
	var params ={strCurCompId:strCurCompId, strDate:strDate, sysStatus:sysStatus};
	$.post(url, params, function(data){
		showDialogmanager.close();
		var list = data.lsDataSet;
		if(sysStatus==1){
			if(list != null && list.length>0){
				var comp = list[0];
				$("#"+prefix+"_comp").html(checkNull(comp.attr1));
				$("#"+prefix+"_time").html(checkNull(comp.attr2));
				if(prefix=="h" || prefix=="f"){
					$("#"+prefix+"_room").html(checkNull(comp.attr3));
					$("#"+prefix+"_pmp").html(checkNull(comp.attr4));
					$("#"+prefix+"_cons").html(checkNull(comp.attr5));
					$("#"+prefix+"_face").html(checkNull(comp.attr6));
				}
				if(prefix=="h" || prefix=="m"){
					$("#"+prefix+"_area").html(checkNull(comp.attr7));
					$("#"+prefix+"_haird").html(checkNull(comp.attr9));
					$("#"+prefix+"_perm").html(checkNull(comp.attr8));
					$("#"+prefix+"_lobby").html(checkNull(comp.attr10));
				}
			}
		}else{
			if(list != null && list.length>0){
				grid.options.data=$.extend(true, {},{Rows: list,Total: list.length});
			}else{
				grid.options.data=$.extend(true, {},{Rows: null,Total: 0});
			}
			grid.loadData(true);
		}
	}).error(function(e){$.ligerDialog.error(e.message);});
}

//导出excel
function loadDataSetExcel(prefix, sysStatus){
	var strCurCompId=$("#"+prefix+"_company").val();
	var strDate=$("#"+prefix+"_date").val();
	var params = "strCurCompId="+strCurCompId;
	params +="&strDate="+strDate;
	params +="&sysStatus="+sysStatus;
	var excelDialog=$.ligerDialog.open({url: contextURL+'/sc019/loadSC019Excel.action?'+params});
	var time = sysStatus==0 ? 15000 : 30000;
	setTimeout(function () { excelDialog.close(); }, time);
}