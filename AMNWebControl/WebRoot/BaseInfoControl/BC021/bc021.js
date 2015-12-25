var compTree=null;
var compTreeManager=null;
var masterGrid=null;
var itemGrid=null;
var itemGridClassify=null;
var productGrid=null;
var productGridClassify=null;
var giveItemGrid=null;
var giveItemOneGrid=null;
var giveProductGrid=null;
var dataLayout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var showAddDialog=null;
var showligerDialog=null;
var layoutHeight=null;
var strCurCompId=null;
//初始化属性
$(function(){
   try{  
   		//布局
        dataLayout= $("#dataLayout").ligerLayout({leftWidth: 220,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
        layoutHeight = $(".l-layout-center").height();
   		var centerWidth = dataLayout.centerWidth;
   		//门店树形结构
		compTree=$("#companyTree").ligerTree({
       		data: parent.complinkInfo,
       		idFieldName :'id',           		
       		onSelect: compSelect,
       		nodeWidth: 220,
       		slide: false,
       		checkbox: false,
       		height:'100%'
      	});
      	compTreeManager = $("#companyTree").ligerGetTreeManager();
      	
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
            {display: '', name: 'activinid', hide:true, isAllowHide:false},
            {display: '', name: 'activcompid', hide:true, isAllowHide:false},
            { display: '单号', 	name: 'activbillid', width:150, align: 'left'},
            { display: '活动门店', 	name: 'compname', width:150, align: 'left'},
            { display: '活动名称', 	name: 'activname', width:200, align: 'left'},
            { display: '活动类型', 	name: 'activtype', width:120, align: 'left', 
            	render: function (item){
            		if(item.activtype=="1"){
            			return "卡异动";
            		}else if(item.activtype=="2"){
            			return "购买疗程"
            		}else{
            			return "购买产品";
            		}
             	}
            },
            { display: '开始时间', 	name: 'startdate', width:120, align: 'left', type:'date', editor: {type:'date', onChanged:updateDate}},
            { display: '结束时间', 	name: 'enddate', width:120, align: 'left', type:'date', editor: {type:'date', onChanged:updateDate}},
            { display: '状态', name: 'activstate', width:100, align: 'left', 
                render: function (item){
                	return item.activstate==1 ? "正常" : "作废";
                }}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: centerWidth,
            height: layoutHeight-10,
            enabledEdit:true, checkbox:true,rownumbers: false,usePager: false,
            toolbar: { items: [
	            { text: '新增', click: openAddDialog, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	       		{ text: '作废', click: updateState, img: contextURL+'/common/ligerui/ligerUI/skins/icons/delete.gif' },
	       		{ text: '启用', click: updateState, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' },
	       		{ text: '查看', click: openActivity, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif' }
	       	]}
   		});
       	f_selectNode();
      	$("#pageloading").hide();
	}catch(e){alert(e.message);}
});
function f_selectNode(){
	var parm = function (data){
	   	return data.id== parent.localCompid;
	};		
	compTree.selectNode(parm);
}
//加载门店活动信息
function compSelect(note){
	if(note){
		strCurCompId=note.data.id;
	}
	var params = {strCurCompId:strCurCompId};
	var url = contextURL+"/bc021/load.action"; 
	$.post(url, params, function(data){
		var list = data.dataSet;
		if(list != null && list.length>0){
			masterGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		}else{
			masterGrid.options.data=$.extend(true, {}, null);
		}
		masterGrid.loadData(true);
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}

function openAddDialog(){
	showAddDialog = $.ligerDialog.open({ height:layoutHeight-50, url: contextURL+'/BaseInfoControl/BC021/showAddActivity.jsp', 
		width: 1200,showMax: false, showToggle: false, allowClose:false, showMin: false, isResize: false, title:"新增活动",
		buttons: [{text:'保存', onclick:function(item, dialog){saveActivity();} }, { text: '取消', onclick: function (item, dialog) {dialog.close(); }} ]});
}

function openActivity(){
	var rowSet = masterGrid.getSelectedRows();
	if(rowSet==null || rowSet.length==0 || rowSet.length>1){
		$.ligerDialog.warn("请选择一项查看！");
		return;
	}
	showligerDialog = $.ligerDialog.open({ height:layoutHeight-50, url: contextURL+'/BaseInfoControl/BC021/showActivity.jsp', 
		width: 1200,showMax: false, showToggle: false, showMin: false, isResize: false, title:"查看活动",_activinid:rowSet[0].activinid,
		buttons: [{ text: '关闭', onclick: function (item, dialog) {dialog.close();}} ]});
}

function saveActivity(){
	if(curpagestate==1){
	 	$.ligerDialog.warn("正在保存中，不能重复操作！");
	 	return;
	}
	var _frame = $(showAddDialog.frame.document);
	var activityname = $("#activityname", _frame).val();
	if (checkNull(activityname) == "") {
		$.ligerDialog.warn("活动名称不能为空！");
		return;
	}
	var params = {activcompid:strCurCompId, activname:activityname};
	var activtype = $("input:radio[name='typebox']:checked", _frame).val();
	params.activtype=activtype;
	if(activtype=="1"){//卡异动
		var cardamt = $("#cardamt", _frame);
		if (checkNull(cardamt.val()) == "") {
			$.ligerDialog.warn("卡异动金额不能为空！");
			return;
		}
		if(!/^\d+(\.\d{1,2})?$/.test(cardamt.val())){
			$.ligerDialog.warn("请输入正确的数字格式！");
			cardamt.val("");
			return;
		}
		params.activamt=cardamt.val();
	}else if(activtype=="2"){//购买疗程
		var bool=checkGrid(itemGrid);
		var classifyData=itemGridClassify.getData()[0];
		var datano = classifyData.activno;
		if((bool && checkNull(datano)=="") || (!bool && checkNull(datano)!="")){
			$.ligerDialog.warn("购买疗程请填写项目或者分类！");
			return;
		}
		var itemamt = $("#itemamt", _frame);
		if(!itemamt.attr("disabled")){
			if(checkNull(itemamt.val())!="" && !/^\d+(\.\d{1,2})?$/.test(itemamt.val())){
				$.ligerDialog.warn("请输入正确的数字格式！");
				itemamt.val("");
				return;
			}
			var itemcount = $("#itemcount", _frame);
			if(checkNull(itemcount.val())!="" && !/^\d+$/.test(itemcount.val())){
				$.ligerDialog.warn("请输入正确的数字格式！");
				itemcount.val("");
				return;
			}
			var itemorand = $("input:radio[name='itemorand']:checked", _frame).val();
			if(checkNull(itemamt.val())=="" || checkNull(itemcount.val())==""){
				$.ligerDialog.warn("请填写疗程的金额和套数！");
				return;
			}
			params.activorand=itemorand;
			params.activamt=checkNull(itemamt.val());
			params.activcount=checkNull(itemcount.val());
		}
		if(bool){
			params.activJson=JSON.stringify(classifyData);
		}else{
			params.activJson=JSON.stringify(itemGrid.getData());
		}
	}else {//购买产品
		var bool=checkGrid(productGrid);
		var classifyData=productGridClassify.getData()[0];
		var datano = classifyData.activno;
		if((bool && checkNull(datano)=="") || (!bool && checkNull(datano)!="")){
			$.ligerDialog.warn("购买产品请填写项目或者分类！");
			return;
		}
		var goodsamt = $("#proamt", _frame);
		if(!goodsamt.attr("disabled")){
			if(checkNull(goodsamt.val())!="" && !/^\d+(\.\d{1,2})?$/.test(goodsamt.val())){
				$.ligerDialog.warn("请输入正确的数字格式！");
				goodsamt.val("");
				return;
			}
			var goodscount = $("#procount", _frame);
			if(checkNull(goodscount.val())!="" && !/^\d+$/.test(goodscount.val())){
				$.ligerDialog.warn("请输入正确的数字格式！");
				goodscount.val("");
				return;
			}
			var goodsorand = $("input:radio[name='proorand']:checked", _frame).val();
			if(checkNull(goodsamt.val())=="" || checkNull(goodscount.val())==""){
				$.ligerDialog.warn("请填写产品的金额和数量！");
				return;
			}
			params.activorand=goodsorand;
			params.activamt=checkNull(goodsamt.val());
			params.activcount=checkNull(goodscount.val());
		}
		if(bool){
			params.activJson=JSON.stringify(classifyData);
		}else{
			params.activJson=JSON.stringify(productGrid.getData());
		}
	}
	//赠送项目和产品
	var giveorand = $("input:radio[name='giveorand']:checked", _frame).val();
	params.giveorand = giveorand;
	var flag=checkGrid(giveItemGrid);
	var flag1=checkGrid(giveItemOneGrid);
	var flag2=checkGrid(giveProductGrid);
	if(giveorand=="1"){
		if(flag && flag1 && flag2){
			$.ligerDialog.warn("请填写赠送的项目或产品！");
			return;
		}
	}else if(giveorand=="2"){
		if(flag2 && (flag || flag1)){
			$.ligerDialog.warn("请填写赠送的项目和产品！");
			return;
		}
	}else{
		if(flag2 || !flag || !flag1){
			$.ligerDialog.warn("只能填写赠送产品！");
			return;
		}
	}
	if(!flag){
		params.giveAJson=JSON.stringify(giveItemGrid.getData());
	}
	if(!flag1){
		params.giveBJson=JSON.stringify(giveItemOneGrid.getData());
	}
	if(!flag2){
		params.giveCJson=JSON.stringify(giveProductGrid.getData());
	}
	curpagestate=1;
	var waitting = $.ligerDialog.waitting('正在保存中,请稍候...');
	var url = contextURL+"/bc021/post.action"; 
	$.post(url, params, function(data){
		waitting.close();
		showAddDialog.close();
		curpagestate=3;
        if(data.strMessage=="SUCCESS"){
        	$.ligerDialog.success("保存成功！");
        	compSelect(null);
        }else{
        	$.ligerDialog.error("数据保存失败，请重试！");
        }
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}
//检查表格数据
function checkGrid(grid){
	var bool = true;
	var dataSet=grid.getData();
	$(dataSet).each(function(i, row){
		if(checkNull(row.activno)!=""){
			bool=false;
			return false;
		}
	});
	return bool;
}

//更新活动日期
function updateDate(obj){
	var row = obj.record;
	var params={activbillid: row.activbillid, activinid: row.activinid};
	if(obj.column.name=="startdate"){
		if(checkNull(row.startdate)!="" && checkNull(row.enddate)!=""){
			var end = row.enddate;;
			if(typeof(row.enddate)=="string"){
				end = new Date(end.replace(/-/g,"/"));
			}
			if(row.startdate>end){
				$.ligerDialog.warn("开始时间不能大于结束时间！");
				masterGrid.updateCell('startdate', row.enddate, row);
				return;
			}
		}
		params.startdate=formatDate(row.startdate);
	}else{
		if(checkNull(row.startdate)!="" && checkNull(row.enddate)!=""){
			var start = row.startdate;
			if(typeof(row.startdate)=="string"){
				start = new Date(start.replace(/-/g,"/"));
			}
			if(row.enddate<start){
				$.ligerDialog.warn("结束时间不能小于开始时间！");
				masterGrid.updateCell('enddate', row.startdate, row);
				return;
			}
		}
		params.enddate=formatDate(row.enddate);
	}
	var url = contextURL+"/bc021/updateDate.action";
	$.post(url, params, function(data){
		
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}
//作废
function updateState(item){
	var rowSet = masterGrid.getSelectedRows();
	if(rowSet==null || rowSet.length==0){
		$.ligerDialog.warn("请选择列表中的活动！");
		return;
	}
	var params = {activJson:JSON.stringify(rowSet), activstate:(item.text=="作废"?0:1)};
	var url = contextURL+"/bc021/updateState.action"; 
	$.post(url, params, function(data){
		if(data.strMessage=="SUCCESS"){
        	$.ligerDialog.success("更新成功！");
        	compSelect(null);
        }else{
        	$.ligerDialog.error("数据更新失败，请重试！");
        }
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}

function formatDate(_date){
	if(checkNull(_date)!=""){
		var _month = _date.getMonth()+1;
		var _day = _date.getDate();
		return _date.getFullYear()+(_month<10?("0"+_month):(_month+""))+(_day<10?("0"+_day):(_day+""));
	}
	return "";
}