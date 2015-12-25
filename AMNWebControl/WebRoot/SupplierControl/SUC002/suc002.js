var orderList=null;//訂單表
var orderListSet = null;
var orderDetailList=null;//訂單明細表
var suc002layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var curDetialRecord = null;
var showDialogmanager = null;
var sendcompid = null;
var sendbillid = null;
var cancel = null;
var delivery = null;
//初始化属性
$(function ()
{
   try
   {  
	   suc002layout= $("#suc002layout").ligerLayout({ rightWidth: 450,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-3;
   		orderList=$("#orderList").ligerGrid({
            columns: [
	            {name:'bsendcompid', hide:true, isAllowHide:false},
	            {name:'headwareid', hide:true, isAllowHide:false},
	            {name:'ordertime', hide:true, isAllowHide:false},
	            {name:'ordercompid', hide:true, isAllowHide:false},
	            {name:'orderbill', hide:true, isAllowHide:false},
	            {name:'orderbilltype', hide:true, isAllowHide:false},
	            {name:'storestaffid', hide:true, isAllowHide:false},
	            {name:'storeaddress', hide:true, isAllowHide:false},
	            {name:'ordercompname', hide:true, isAllowHide:false},//开票信息
	            {name:'storewarename', hide:true, isAllowHide:false},//门店电话
	            {display:'单号', name:'bsendbillid', width:150, align:'left'},
	            {display:'门店', name:'bbsendcompname', width:150, align:'left'},//门店名称
	            {display:'订货日期', name:'orderdate', width:150, align:'left'}
            ],  
            pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '450',
            height:height,
            clickToEdit: false,
            enabledEdit: false,  
            rownumbers: false,
            usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                loadDetialData(data, rowindex, rowobj);
            }	   
        });
       $("#orderList .l-grid-body-inner").css('width', 450);
       loadOrder();
       orderDetailList=$("#orderDetailList").ligerGrid({
            columns: [
                {display: '产品编号', name: 'sendgoodsno', width:100, align: 'left'},
                {display: '产品名称', name: 'sendgoodsname', width:200, align: 'left'},
                {display: '单位', name: 'sendgoodsunit', width:50, align: 'center'},
                {display: '申请量', name: 'ordergoodscount', width:100, align: 'right'},
                {display: '下单量', name: 'downordercount', width:100, align: 'right', type:'int'},
			    {display: '实发数', name: 'sendgoodscount', width:100, align: 'right', editor:{type:'int',onChanged:validateDownordercount},
			    	render: function (row) {
			    		var count = row.downordercount;
     					if(checkNull(count)!=""){
     						row.sendgoodscount=count;
     						return count;
     					}
     					return 0;  
 					}},
 				{name:'sendgoodprice', hide:true, isAllowHide:false},
 				{name:'sendgoodsamt', hide:true, isAllowHide:false}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            height:600,
            clickToEdit: true,   enabledEdit: true,  checkbox:false,
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                curDetialRecord=data;
            }
        });
      // $("#orderDetailList .l-grid-body-inner").css('width', 700);
       
       cancel = $("#lost").ligerButton({
     		 text: '发货单作废', width: 100,
     		 click: function (){
     			if(checkNull(sendbillid) == ""){
     				$.ligerDialog.warn("操作无效！");
     				return;
     			}
     			$.ligerDialog.confirm('确认是否作废？', function(result){
					if(result){
						var url = contextURL+"/suc002/cancel.action"; 
						var params = {"bsendcompid":sendcompid,"bsendbillid":sendbillid};
						$.post(url, params, function(data){
							if(checkNull(data.strMessage)!=""){
								$.ligerDialog.success(data.strMessage);
								sendcompid="";
								sendbillid="";
								$("table input").val("");
								orderDetailList.options.data=$.extend(true, {}, {Rows: null,Total: 0});
								orderDetailList.loadData(true);
								loadOrder();
							}else{
								$.ligerDialog.error("操作失败！");
							}
						}).error(function(e){$.ligerDialog.error(e.message);});
				    }
				 });
     		 }
       });
       $("#print").ligerButton({
     		 text: '打印', width: 100,
     		 click: function (){
     			 
     		 }
       });
       delivery = $("#send").ligerButton({
     		 text: '发货', width: 100,
     		 click: function (){
     			if(checkNull(sendbillid) == ""){
     				$.ligerDialog.warn("操作无效！");
     				return;
     			}
     			$.ligerDialog.confirm('确认是否发货？', function(result){
					if(result){
						var url = contextURL+"/suc002/delivery.action";
						var data = orderDetailList.getData();
		                if(data==null || data.length==0){
		                	$.ligerDialog.warn("数据异常，请刷新重试！");
		                	return;
		                }
		     			$("#storestaffname").val(JSON.stringify(data));
		     			var params = $('#dataForm').serialize();
		     			$.post(url, params, function(data){
		     				if(checkNull(data.strMessage)!=""){
			     				$.ligerDialog.success(data.strMessage);
			     				sendcompid="";
			     				sendbillid="";
			     				$("table input").val("");
			     				orderDetailList.options.data=$.extend(true, {}, {Rows: null,Total: 0});
			     				orderDetailList.loadData(true);
			     				loadOrder();
		     				}else{
								$.ligerDialog.error("操作失败！");
							}
		     			}).error(function(e){$.ligerDialog.error(e.message);});     	
				    }
				 });
     		 }
     });
       
	}catch(e){
		alert(e.message);
	}
});
//加载订单
function loadOrder(){
	var requestUrl ="/AMNWebControl/suc002/load.action"; 
	var responseMethod = "loadOrderData";
	sendRequestForParams_p(requestUrl,responseMethod, null);
}
//加载订单回调函数
function loadOrderData(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
	if(list != null && list.length>0){
		//订单
		orderList.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		orderList.loadData(true); 
		orderList.select(0);
	}else{
		orderList.options.data=$.extend(true, {}, {Rows: null,Total: 0});
		orderList.loadData(true);
	}
	$("#pageloading").hide();
}
//加载明细信息
function loadDetialData(data, rowindex, rowobj){
	$("#bsendcompid").val(data.bsendcompid);
	$("#bsendbillid").val(data.bsendbillid);
	$("#bbsendcompname").val(data.bbsendcompname);//门店名称
	$("#orderdate").val(data.orderdate);
	$("#ordertime").val(data.ordertime);
	$("#headwareid").val(data.headwareid);
	$("#ordercompid").val(data.ordercompid);
	$("#orderbill").val(data.orderbill);
	$("#orderbilltype").val(data.orderbilltype);
	$("#storestaffid").val(data.storestaffid);
	$("#storeaddress").val(data.storeaddress);
	$("#ordercompname").val(data.ordercompname);//开票信息
	$("#storewarename").val(data.storewarename);//门店电话
	$("#senddate").val(CurentTime());
	orderDetailList.options.data=$.extend(true, {}, {Rows: null,Total: 0});
	orderDetailList.loadData(true);
	var requestUrl ="/AMNWebControl/suc002/loadDetail.action"; 
	var responseMethod = "loadDetailData";
	sendcompid = data.bsendcompid;
	sendbillid = data.bsendbillid;
	sendRequestForParams_p(requestUrl,responseMethod, {"bsendcompid":sendcompid,"bsendbillid":sendbillid});
}

//加载订单详细信息回调函数
function loadDetailData(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
	if(list != null && list.length>0){
		orderListSet = list;
		//订单
		orderDetailList.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		orderDetailList.loadData(true); 
	}else{
		orderDetailList.options.data=$.extend(true, {}, {Rows: null,Total: 0});
		orderDetailList.loadData(true);
	}
	$("#pageloading").hide();
}

function f_search()
{
   orderList.options.data = $.extend(true, {}, orderListSet);
   orderList.loadData(f_getWhere());
}
function f_getWhere(){
	if (!orderList) return null;
	var clause = function (rowdata, rowindex){
		var key = $("#txtKey").val();
		return rowdata.orderbillid.indexOf(key) > -1;
	   };
	return clause; 
}
function CurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var clock = year+"-";
   
    if(month < 10)
        clock += "0";
   
    clock += month+"-" ;
   
    if(day < 10)
        clock += "0";
       
    clock += day ;
    return(clock); 
}

function validateDownordercount(obj)
{	var ordergoodsprice=checkNull(curDetialRecord.ordergoodsprice)*1;
	var downordercount=checkNull(curDetialRecord.downordercount)*1;
	if(obj.value*1>downordercount)
	{
		$.ligerDialog.warn('实发数不能超过下单量!');
		orderDetailList.updateRow(curDetialRecord,{sendgoodsamt:0,sendgoodscount: ForDight(downordercount,1)});
		return;
	}
	orderDetailList.updateRow(curDetialRecord,{sendgoodsamt: ForDight(ordergoodsprice*1*obj.value*1,1)});
}