var compTree;
var compTreeManager;
var billGrid=null;//录入单号表
var commoninfodivmaster=null;	//员工表
var commoninfodivdetial=null;	//学分表
var actGrid=null;//活动信息表
var scc005layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var actnoBox=null;
var actDialog=null;
var billId=null;
var showDialogmanager=null;
var useSourceDate=[{ choose: 0, text: '公益活动' }];//类别
//初始化属性
$(function (){
   try{  
	   	loadActData();//加载活动信息
   		//布局
        scc005layout= $("#scc005layout").ligerLayout({leftWidth: 220,  allowBottomResize: false, allowLeftResize: false});
   		var height = $(".l-layout-center").height();
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
      	
      	var postiontitle=parent.gainCommonInfoByCode("GZGW",0);
       	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
            columns: [
            { display: '工号', name: 'bstaffno',  width: 80 },
            { display: '姓名', name: 'staffname',  width: 90 },
            { display: '职位', name: 'position',  width: 110,align: 'left',
            	render: function (item){
              		for(var i=0;i<postiontitle.length;i++){
						if(postiontitle[i].bparentcodekey==item.position){	
							return postiontitle[i].parentcodevalue;								
					    }
					}
                    return '';
                } 
            }],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '280',
            height:height,
            clickToEdit: false,   enabledEdit: false,  
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                loadCurStaffinfo(data, rowindex, rowobj);
            }	   
        });
       	$("#commoninfodivmaster .l-grid-body-inner").css('width', 280);
       	
       	//录入单号
   		billGrid=$("#billGrid").ligerGrid({
            columns: [
            { display: '', name: 'salecompid', hide:true, isAllowHide:false},
            { display: '录入单号', 	name: 'salebillid', width:120,align: 'left'}
           ],  pageSize:25, 
            data:{Rows: null,Total:0},      
          	width: 120,
            height:height,
            enabledEdit: false, checkbox: false,
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
            	queryData(data);
            }
        });
   		$("#billGrid .l-grid-body-inner").css('width', 120);
   		
    	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
            columns: [
            { display: '姓名', 	name: 'staffname', width:70, align: 'left' },
            { display: '职位', name: 'position',  width: 60,align: 'left',
            	render: function (item){
              		for(var i=0;i<postiontitle.length;i++){
						if(postiontitle[i].bparentcodekey==item.position){	
							return postiontitle[i].parentcodevalue;								
					    }
					}
                    return '';
                } 
            },
            { display: '身份证', name: 'pccid', width:100, align: 'left'},
            { display: '手机',  name: 'mobilephone', width:70, align: 'left'},
            { display: '工号',  name: 'staffno', width:60, align: 'left'},
            { display: '活动名称',  name: 'actno', width:80, align: 'left', 
            	render: function (item){
            		for(var i=0;i<actnoBox.length;i++){
						if(actnoBox[i].no==item.actno){	
							return actnoBox[i].name;								
					    }
					}
            		return '';
                }},
            { display: '活动类型',  name: 'acttype', width:60, align: 'left',
            	render: function (item){
            		for(var i=0;i<useSourceDate.length;i++){
						if(useSourceDate[i].choose==item.acttype){	
							return useSourceDate[i].text;								
					    }
					}
            		return '';
                }},
            { display: '积分',  name: 'score', width:50, align: 'left'},
            { display: '备注',  name: 'remark', width:50, align: 'left'}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '600',
            height:height-180,
            clickToEdit: false,   enabledEdit: true,  checkbox:false,
            rownumbers: false,usePager: false
        });
    	
       	 $("#addBtn").ligerButton({
			 text: '新增', width: 90,
			 click: function (){
				if(curpagestate!=3){
	    		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行新增操作!");
	    		 	return;
	    		}
				curpagestate=1;
				$("#actno,#acttype").attr("disabled",false);
       			$("#score,#staffno").attr("readonly",false).focus();
       			var salecompid = compTree.getSelected().data.id;
				var params = {salecompid:salecompid};
				var requestUrl ="scc005/add.action"; 
				var responseMethod="addBill";		
				sendRequestForParams_p(requestUrl,responseMethod, params);
			 }
		 });
       	 $("#editBtn").ligerButton({
       		 text: '编辑', width: 90,
       		 click: function (){
       			if(curpagestate!=3){
        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行编辑操作!");
        		 	return ;
        		}
       			$("#actno,#acttype").attr("disabled",false);
       			$("#score,#staffno").attr("readonly",false).focus();
				curpagestate=2;
       		 }
       	 });
       	 $("#saveBtn").ligerButton({
       		 text: '保存', width: 90,
       		 click: function (){
       			var bill=null;
       			if(curpagestate==3){
        		 	$.ligerDialog.warn("未保存数据,无需保存!");
        		 	return;
        		}else if(curpagestate==2){
        			bill = billGrid.getSelectedRow();
        		}else{
        			bill = billGrid.getAdded()[0];
        		}
       			var data = commoninfodivdetial.getAdded();
       			if(data.length ==0){
       				$.ligerDialog.warn("请先载入列表，然后进行保存操作!");
       				return;
    			}
       			var result = new Array();
       			$.each(data, function(i, row){
       				var employee = {pk:{salecompid:bill.salecompid, salebillid:bill.salebillid,
       								staffno:row.staffno, actno:row.actno}, acttype:row.acttype,
       								score:(row.score==""?0:row.score), remark:row.remark};
       				result.push(employee);
       			});
       			var params = "remark="+JSON.stringify(result);
    	     	var requestUrl ="scc005/post.action"; 
				var responseMethod="postMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params);
       		 }
       	 });
       	$("#refBtn").ligerButton({
             text: '刷新', width: 90,
	         click: function (){
	        	 curpagestate=3;
	        	 loadBill();
	         }
         });
       	$("#actBtn").ligerButton({
       		text: '添加活动信息', width: 90,
       		click: function (){
	            f_open();
       		}
       	});
       	$("#listBtn").ligerButton({
       		text: '载入列表', width: 90,
       		click: function (){
       			addeditadd();
       		}
       	});
       	clearOption("position");
      	for(var i=0;i<postiontitle.length;i++){
			addOption(postiontitle[i].bparentcodekey,postiontitle[i].parentcodevalue,document.getElementById("position"));
		}
      	$.each(useSourceDate, function(i, type){
			addOption(type.choose, type.text, document.getElementById("acttype"));
		});
      	//$("#actno").ligerComboBox({url: 'scc005/loadAct.action', isMultiSelect: false,valueFieldID: 'no'});
      	//$("#acttype").ligerComboBox({ data: useSourceDate, isMultiSelect: false,valueFieldID: 'choose'});
       	f_selectNode();
       	loadBill();//加载部门下已有的录入单号
      	$("#pageloading").hide();
      	$("#staffno").keydown(function(e){
      		if(e.keyCode==13){
      			searchStaffInfo();
      		}
      	});
	}catch(e){alert(e.message);}
});
function f_selectNode(){
	var parm = function (data){
	   	return data.id== parent.localCompid;
	};		
	compTree.selectNode(parm);
}
//加载门店员工信息
function compSelect(note){
    try{
   		var params = "strCurCompId="+note.data.id;				
 		var requestUrl ="bc012/loadCurStaffInfo.action"; 
		var responseMethod="loadSysuserinfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params);
    }catch(e){alert(e.message);}
}
function loadSysuserinfoMessage(request){
	try{
		var responsetext = eval("(" + request.responseText + ")");
		var staffinfo = responsetext.lsStaffinfo;
   		if(staffinfo!=null && staffinfo.length>0){
   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: staffinfo,Total: staffinfo.length});
   			commoninfodivmaster.loadData(true);          
   			commoninfodivmaster.select(0);  	
   		}else{
   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
   			commoninfodivmaster.loadData(true);      
   		}
   	}catch(e){alert(e.message);}
}

//加载员工详细信息
function loadCurStaffinfo(data, rowindex, rowobj){
	var params = "strCurCompId="+data.id.compno;			
	params =params+ "&strCurStaffId="+data.bstaffno;		
 	var requestUrl ="bc012/loadCurStaff.action"; 
	var responseMethod="loadCurStaffinfoMessage";		
	sendRequestForParams_p(requestUrl,responseMethod,params);
}
function loadCurStaffinfoMessage(request){
	try{
		var responsetext = eval("(" + request.responseText + ")");
		if(responsetext.curStaffinfo!=null){
   			document.getElementById("staffno").value=checkNull(responsetext.curStaffinfo.id.staffno);
   			document.getElementById("position").value=checkNull(responsetext.curStaffinfo.position);
   			document.getElementById("staffname").value=checkNull(responsetext.curStaffinfo.staffname);
   			document.getElementById("mobilephone").value=checkNull(responsetext.curStaffinfo.mobilephone);
   			document.getElementById("pccid").value=checkNull(responsetext.curStaffinfo.pccid);
   		}
   	}catch(e){alert(e.message);}
}

function loadBill(){
	var params = {salecompid:parent.localCompid};
	var requestUrl ="scc005/load.action"; 
	var responseMethod="loadBillSet";		
	sendRequestForParams_p(requestUrl,responseMethod, params);
}

function loadBillSet(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.billSet;
	if(list != null && list.length>0){
		billGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		billGrid.loadData(true);   
		billGrid.select(0);          	
	}else{
		billGrid.options.data=$.extend(true, {},{Rows: null,Total:0});
		billGrid.loadData(true);
		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
		commoninfodivdetial.loadData(true);
	}
	$("#actno,#acttype").attr("disabled",true);
	$("#score,#staffno").attr("readonly",true);
}

function addBill(request){
	var responsetext = eval("(" + request.responseText + ")");
	var bill = responsetext.bill;
	if(bill != null && checkNull(bill.salebillid) != ""){
		var row = billGrid.getSelectedRow();
		billGrid.addRow({ 
			salecompid: compTree.getSelected().data.id,
			salebillid: bill.salebillid
        }, row, false);
		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
		commoninfodivdetial.loadData(true);
	}else{
		$.ligerDialog.error("添加录入单号失败，请刷新本界面后重试！");
	}
}

function queryData(data){
	var params = {salecompid:data.salecompid, salebillid:data.salebillid};
	var requestUrl = "scc005/query.action"; 
	var responseMethod="loadDataSet";		
	sendRequestForParams_p(requestUrl,responseMethod, params);
}

function loadDataSet(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
	if(list != null && list.length>0){
		commoninfodivdetial.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
	}else{
		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
	}
	commoninfodivdetial.loadData(true);
}

//载入信息
function addeditadd(){
	if(checkNull($("#staffno").val()) == "" || checkNull($("#pccid").val()) == ""){
		$.ligerDialog.warn("信息不完整，无法载入列表！");
		return;
	}
	if(checkNull($("#score").val()) == ""){
		$.ligerDialog.warn("分数不能为空！");
		return;
	}
	var row = commoninfodivdetial.getSelectedRow();
  	commoninfodivdetial.addRow({ 
		staffno: document.getElementById("staffno").value,
		position: document.getElementById("position").value,
		staffname: document.getElementById("staffname").value,
		mobilephone: document.getElementById("mobilephone").value,
		pccid: document.getElementById("pccid").value,
		actno: document.getElementById("actno").value,	
		acttype: document.getElementById("acttype").value,
		score: document.getElementById("score").value,
		remark: document.getElementById("remark").value
    }, row, false);
}

/*保存的回调函数*/
function postMessage(request){
   	try{
        var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(responsetext.sysStatus==1){	        		 
        	 $.ligerDialog.success(strMessage);
        	 curpagestate=3;
        	 loadBill();
        }else{
        	$.ligerDialog.error(strMessage);
        }
    }catch(e){
    	$.ligerDialog.error(e.message);
	}
}

function f_open(){
    actDialog = $.ligerDialog.open({
        height:500,
        width: 800,
        title : '添加活动信息',
        url: contextURL+ '/SchoolControl/SCC005/activity.jsp',
        showMax: false,
        showToggle: true,
        showMin: false,
        isResize: true,
        slide: false
    });
}

function loadActData(){
	 $.ajax({
         type: "get",
         url: contextURL+"/scc005/loadAct.action",
         async : false, 
         dataType: "json",
         success: function(data){
        	actnoBox = data.actSet;
    		if(actnoBox != null && actnoBox.length>0){
    			clearOption("actno");
    			$.each(actnoBox, function(i, act){
    				addOption(act.no, act.name, document.getElementById("actno"));
    			});
    		}else{
    			addOption("-1", "没有活动信息", document.getElementById("actno"));
    		}      
         }
     });
	/*var requestUrl ="scc005/loadAct.action"; 
	var responseMethod="loadActSet";		
	sendRequestForParams_p(requestUrl,responseMethod, null);*/
}

function loadActSet(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.actSet;
	if(list != null && list.length>0){
		clearOption("actno");
		$.each(list, function(i, act){
			addOption(act.no, act.name, document.getElementById("actno"));
		});
	}else{
		addOption("-1", "没有活动信息", document.getElementById("actno"));
	}
}

//回车查询用户信息
function searchStaffInfo(){
	var no = $("#staffno").val();
	if(checkNull(no) == ""){
		$.ligerDialog.warn("工号不能为空！");
		return;
	}
	var params ="staffno="+ no; 
    var requestUrl = contextURL+"/scc004/search.action";
	var responseMethod="loadDataSetMessage";		
	showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
	sendRequestForParams_p(requestUrl,responseMethod,params );
}

function loadDataSetMessage(request){
	var responsetext = eval("(" + request.responseText + ")");
	showDialogmanager.close();
	var staff = responsetext.staffinfo;
	if(staff != null && checkNull(staff.staffname) != ""){
		document.getElementById("position").value=checkNull(staff.position);
		document.getElementById("staffname").value=checkNull(staff.staffname);
		document.getElementById("mobilephone").value=checkNull(staff.mobilephone);
		document.getElementById("pccid").value=checkNull(staff.pccid);
    }else{
    	$('#staffname,#mobilephone,#pccid').val("");
	 	$.ligerDialog.warn("没有查到员工信息!");
	}
}