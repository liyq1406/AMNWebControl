var commoninfodivmaster=null;//职位表
var commoninfodivdetial=null;//可培训课程
var commoninfodivother=null;//必修课程
var commoninfodivsmall=null;//年度最低学分
var scc003layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var curDetialRecord = null;
var showDialogmanager=null;
var staffPostionData=null;//工作岗位
var positionSet = new Array();
//var schoolCredit = null;
var postion_no = null;
var bxk = null;
var xxk = null;
//初始化属性
$(function ()
{
   try
   {  
   		
        scc003layout= $("#scc003layout").ligerLayout({ rightWidth: 600,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-3;
   		staffPostionData=parent.gainCommonInfoByCodeByUse("GZGW",0,1);//工作岗位
   		$.each(staffPostionData, function(i, common){
	   		positionSet.push({"credit_no":common.bparentcodekey, "postion":common.parentcodevalue});
	   	});
       	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
            columns: [
	            {name:'binsercompid', hide:true},
	            {display:'职位编号', name:'credit_no', width:220, align:'left'},
	            {display:'职位名称', name:'postion', width:220, align:'left'}
            ],  
            pageSize:20, 
            data:{Rows: positionSet,Total:0},      
            width: '440',
            height:height,
            clickToEdit: false,
            enabledEdit: false,  
            rownumbers: false,
            usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                loadSelecDetialData(data, rowindex, rowobj);
            }	   
        });
       $("#commoninfodivmaster .l-grid-body-inner").css('width', 440);
       
       commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
            columns: [
                {display:'可培训课程', columns: [
					 {display: '编号', name: 'no', width:185, align: 'left'},
			         {display: '名称', name: 'name', width:185, align: 'left'}
				]}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '400',
            height:height-200,
            clickToEdit: true,   enabledEdit: true,  checkbox:true,
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                //curDetialRecord=data;
            }, onRClickToSelect:true,
            isChecked:xxk_isChecked
        });
       $("#commoninfodivdetial .l-grid-body-inner").css('width', 370).css('height', height-250);
       
       commoninfodivother=$("#commoninfodivother").ligerGrid({
    	   	columns: [
				{display:'必修课程', columns: [
					 {display: '编号', name: 'no', width:185, align: 'left'},
			         {display: '名称', name: 'name', width:185, align: 'left'}
				]}
	         ],  pageSize:20, 
	         data:{Rows: null,Total:0},      
	         width: '400',
	         height:height-200,
	         clickToEdit: true,   enabledEdit: true,  checkbox:true,
	         rownumbers: false,usePager: false,
	         onSelectRow : function (data, rowindex, rowobj){
	        	 //curDetialRecord=data;
	         }, onRClickToSelect:true,
	         isChecked:bxk_isChecked
       });
       $("#commoninfodivother .l-grid-body-inner").css('width', 370).css('height', height-250);
       commoninfodivsmall=$("#commoninfodivsmall").ligerGrid({
    	   columns: [
	         {display: '年份', name: 'syear', width:300, align: 'left',editor: { type: 'text' }},
	         {display: '年度最低学分', name: 'score', width:300, align: 'left',editor: { type: 'text' }}
	         ],  pageSize:20, 
	         data:{Rows: null,Total:0},      
	         width: '600',
	         height: '197',
	         clickToEdit: true,   enabledEdit: true,  checkbox:false,
	         rownumbers: false,usePager: false,
	         onSelectRow : function (data, rowindex, rowobj){
	        	 //curDetialRecord=data;
	         }, onRClickToSelect:true
       });
       $("#commoninfodivsmall .l-grid-body-inner").css('width', 600).css('height', 147);
       $("#searchButton").ligerButton({
    	   	text: '查询', width: 100,
    	   	click: function (){
	             //searchOrderBill();
    	   	}
       });
       $("#addBtn").ligerButton({
			 text: '新增', width: 100,
			 click: function (){
				 
			 }
       });
       $("#editBtn").ligerButton({
       		 text: '编辑', width: 100,
       		 click: function (){
       			addNewRow();
       		 }
       });
       $("#saveBtn").ligerButton({
       		 text: '保存', width: 100,
       		 click: function (){
       			 save();
       		 }
       });
       $("#pageloading").hide();
       commoninfodivmaster.select(0);
       
	}catch(e){
		alert(e.message);
	}
});
//加载学分
function loadScore(credit_no){
	var requestUrl ="scc003/loadScore.action"; 
	var responseMethod = "loadScoreData";
	sendRequestForParams_p(requestUrl,responseMethod, {"credit_no":credit_no});
}
//加载学分回调函数
function loadScoreData(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
	if(list != null && list.length>0){
		//学分
		commoninfodivsmall.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		commoninfodivsmall.loadData(true); 
	}else{
		commoninfodivsmall.options.data=$.extend(true, {}, {Rows: null,Total: 0});
		commoninfodivsmall.loadData(true);
	}
}
//职位点击事件
function loadSelecDetialData(data, rowindex, rowobj){
	postion_no = data.credit_no;
	loadData(data.credit_no);
	loadScore(postion_no);
}
//加载必修选修课程数据
function loadData(credit_no){
	var requestUrl ="scc003/load.action"; 
	var responseMethod = "loadDataSet";
	sendRequestForParams_p(requestUrl,responseMethod, "credit_no="+credit_no);
}

function loadDataSet(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
//	schoolCredit = list;
	bxk = list[1];
	xxk = list[2];
	if(list != null && list.length>0){
		//必修
		commoninfodivdetial.options.data=$.extend(true, {}, {Rows: list[0],Total: list[0].length});
		commoninfodivdetial.loadData(true);   
		//选修
		commoninfodivother.options.data=$.extend(true, {}, {Rows: list[0],Total: list[0].length});
		commoninfodivother.loadData(true);   
	}else{
		//必修
		commoninfodivdetial.options.data=$.extend(true, {}, {Rows: null,Total: 0});
		commoninfodivdetial.loadData(true);   
		//选修
		commoninfodivother.options.data=$.extend(true, {}, {Rows: null,Total: 0});
		commoninfodivother.loadData(true);
	}
}
function save(){
	need= commoninfodivdetial.getCheckedRows();
	must = commoninfodivother.getCheckedRows();
	commoninfodivsmall.endEdit();
	score = commoninfodivsmall.options.data.Rows;
	scoreStr = JSON.stringify(score)
	var m = new Array();
	var n = new Array();
	var listSet = new Array();
	$(must).each(function(){
		m.push(this.no);
	})
	$(need).each(function(){
		n.push(this.no);
	})
	var params = {"credit_no":postion_no,"listSet":m,"listSet2":n,"listScroe":scoreStr}
	var requestUrl ="scc003/save.action"; 
	var responseMethod = "saveCallBack";
	sendRequestForParams_p(requestUrl,responseMethod, params);
}

function saveCallBack(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
//	schoolCredit = list;
	if(list != null && list.length>0){
		if(list[0]="true"){
			$.ligerDialog.success("保存成功");
		}else{
			$.ligerDialog.error("保存失败");
		}
	}
}

function bxk_isChecked(rowdata)
{
	for(i = 0 ;i< bxk.length;i++){
	    if (rowdata.no == bxk[i].schoolcreditno) 
	        return true;
	}
    return false;
}

function xxk_isChecked(rowdata)
{
	for(i = 0 ;i< xxk.length;i++){
	    if (rowdata.no == xxk[i].schoolcreditno) 
	        return true;
	}
    return false;
}

function addNewRow() {
	commoninfodivsmall.addEditRow();
}