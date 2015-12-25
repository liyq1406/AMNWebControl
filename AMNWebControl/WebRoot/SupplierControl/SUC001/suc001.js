var masterGrid=null;	//联系人表
var detialGrid=null;	//进货价表
var suc001layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var strCurSupperId = "";
var useData=[{ choose: 0, text: '否' }, { choose: 1, text: '是'}];//主要联系人
//初始化属性
$(function(){
   try{  
   		//布局
        suc001layout= $("#suc001layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-28;
   		$("#startdate").ligerDateEditor({ labelWidth: 200,format: "yyyy-MM-dd", labelAlign: 'right',width:'200' });
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
            {display: '', name: 'id', hide:true, isAllowHide:false},
            {display: '', name: 'supplierno', hide:true, isAllowHide:false},
            { display: '姓名', 	name: 'name', width:150, align: 'left', editor:{type:'text'}},
            { display: '手机', 	name: 'mobile', width:200, align: 'left', editor:{type:'text'}},
            { display: '主要联系人', name: 'ismain', width:150, align: 'left', 
            	editor: {type: 'select', data: useData, valueField: 'choose'},
                render: function (item){
                	return item.ismain==0 ? "否" : "是";
                }}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '800',
            height: height-160,
            clickToEdit: true,   enabledEdit: true,  checkbox:false,
            rownumbers: false,usePager: false,
            toolbar: { items: [
	            { text: '增加', click: addDataRow, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' },
	       		{ text: '保存', click: saveLink, img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif' }
          	]}
        });
       	$("#masterGrid .l-grid-body-inner").css('width', 800);
       	
       	detialGrid=$("#detialGrid").ligerGrid({
            columns: [
			{ display: '', name: 'supplierno', hide:true, isAllowHide:false},          
			{ display: '', name: 'linkno', hide:true, isAllowHide:false},
            { display: '编码', 	name: 'goodsno', width:150, align: 'left' },
            { display: '名称', 	name: 'goodsname', width:200, align: 'left' },
            { display: '进货价', name: 'price', width:140, align: 'left'}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '500',
            height: height-160,
            clickToEdit: false,   enabledEdit: false,  checkbox:false,
            rownumbers: false,usePager: false
        });
       	 $("#editBtn").ligerButton({
       		 text: '编辑', width: 100,
       		 click: function (){
       			if(curpagestate!=3){
        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行编辑操作!");
        		 	return ;
        		}
       			curpagestate=2;
       			$("#dataTable input").attr("readonly", false);
       			$("#name").focus();
       		 }
       	 });
       	 $("#saveBtn").ligerButton({
       		 text: '保存', width: 100,
       		 click: function (){
       			if(curpagestate==3){
        		 	$.ligerDialog.warn("未保存数据,无需保存!");
        		 	return;
        		}
       			var name = $("#name").val();
       			if (checkNull(name) == "") {
       				$.ligerDialog.warn("供应商名称不能为空！");
       				return;
       			}
       			var login = $("#login").val();
       			if (checkNull(login) == "") {
       				$.ligerDialog.warn("登陆编号不能为空！");
       				return;
       			}
       			var waitting = $.ligerDialog.waitting('正在保存中,请稍候...');
				var url = contextURL+"/suc001/post.action"; 
				var params = $('#dataForm').serialize();
				$.post(url, params, function(data){
					waitting.close();
					var strMessage=data.strMessage;
			        if(data.sysStatus==1){
			        	curpagestate=3;
			        	$("#dataTable input").attr("readonly", true);
			        	$.ligerDialog.success(strMessage);
			        }else{
			        	$.ligerDialog.error(strMessage);
			        }
				}).error(function(e){$.ligerDialog.error(e.message);});
       		 }
       	 });
       	$("#addBtn").ligerButton({
			 text: '新增', width: 100,
			 click: function (){
				var goodsno = $("#goodsno").val();
       			if (checkNull(goodsno) == "") {
       				$.ligerDialog.warn("产品编号不能为空！");
       				return;
       			}
       			var price = $("#price").val();
       			if (checkNull(price) == "") {
       				$.ligerDialog.warn("进货价不能为空！");
       				return;
       			}
       			var goodsname = $("#goodsname").val();
       			if (checkNull(goodsname) == "") {
       				$.ligerDialog.warn("无此产品信息，不能添加！");
       				return;
       			}
       			var waitting = $.ligerDialog.waitting('正在新增中,请稍候...');
       			var url = contextURL+"/suc001/addto.action";
       			var goods = {supplierno:supplierno, goodsno:goodsno, goodsname:goodsname, price:price, linkno:$("#linkno").val()};
				var params = {name: JSON.stringify(goods)};
       			$.post(url, params, function(data){
       				waitting.close();
					var strMessage=data.strMessage;
			        if(data.sysStatus==1){
			        	detialGrid.addRow({
			        		linkno:$("#linkno").val(),
			        		supplierno:supplierno,
			        		goodsno: goodsno,
			        		goodsname: goodsname,
			        		price: price
			        	}, null, false);
			        	$.ligerDialog.success(strMessage);
			        }else{
			        	$.ligerDialog.error(strMessage);
			        }
				}).error(function(e){$.ligerDialog.error(e.message);});
			 }
       	});
       	$("#goodsno").keydown(function(e){
      		if(e.keyCode==13){
      			searchGoodsInfo();
      		}
      	}).change(function(){
      		$('#goodsname').val("");
      	});
      	$("#pageloading").hide();
      	loadLinkman();
      	loadProlist();
	}catch(e){alert(e.message);}
});

//加载联系人数据
function loadLinkman(){
	var url = contextURL+"/suc001/load.action"; 
	$.post(url, null, function(data){
		var list = data.listSet;
		if(list != null && list.length>0){
			masterGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
			masterGrid.loadData(true);
			clearOption("linkno");
   			$.each(list, function(i, link){
   				addOption(link.id, link.name, document.getElementById("linkno"));
   			});
		}
	}).error(function(e){$.ligerDialog.error(e.message);});
}
//添加联系人
function addDataRow(item){
	var row = masterGrid.getSelectedRow();
	masterGrid.addRow({
		supplierno:supplierno,
		name: "",
		mobile: "",
		ismain: 0
    }, row, false);
}
//保存联系人
function saveLink(){
	var add = masterGrid.getAdded();
	var update = masterGrid.getUpdated();
	if(add.length == 0 && update.length == 0){
		$.ligerDialog.warn("未保存数据,无需保存!");
	}else{
		var waitting = $.ligerDialog.waitting('正在保存中,请稍候...');
		var url = contextURL+"/suc001/add.action"; 
		var addJson = add.length == 0 ? "" : JSON.stringify(add);
		var updateJson = update.length == 0 ? "" : JSON.stringify(update);
		var params = {no:addJson, name:updateJson, _stamp:Math.random()};
		$.post(url, params, function(data){
			waitting.close();
			var strMessage=data.strMessage;
	        if(data.sysStatus==1){	        		 
	        	 $.ligerDialog.success(strMessage);
	        	 loadLinkman();
	        }else{
	        	$.ligerDialog.error(strMessage);
	        }
		}).error(function(e){$.ligerDialog.error(e.message);});
	}
}
//加载产品列表
function loadProlist(){
	var url = contextURL+"/suc001/query.action"; 
	$.post(url, null, function(data){
		var list = data.priceSet;
		if(list != null && list.length>0){
			detialGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
			detialGrid.loadData(true);
		}
	}).error(function(e){$.ligerDialog.error(e.message);});
}
//回车查询产品信息
function searchGoodsInfo(){
	var no = $('#goodsno').val();
	if(checkNull(no) == ""){
		$.ligerDialog.warn("产品编码不能为空！");
		return;
	}
	var params = {no:no};
	var url = contextURL+"/suc001/search.action"; 
	$.post(url, params, function(data){
		var goods = data.goods;
		if(goods != null && checkNull(goods.goodsname) != ""){
			$("#goodsname").val(goods.goodsname);
	    }else{
	    	$('#goodsname,#price').val("");
		 	$.ligerDialog.warn("没有查到产品信息!");
		}
	}).error(function(e){$.ligerDialog.error(e.message);});
}