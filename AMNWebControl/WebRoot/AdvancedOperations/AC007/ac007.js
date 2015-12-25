var masterGrid=null;
var detialGrid=null;
var dataLayout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var showModeDialog=null;
var uploader=null;
var curProRecord=null;
var cardData=null;
var stepGrid=null;
var useData=[{ choose: 0, text: '否' }, { choose: 1, text: '是'}];//是否
var catgoryData=[{ choose: 1, text: '到店' }, { choose: 2, text: '家居'}];//商品分类
var typeData=[{ choose: 1, text: '面部' }, { choose: 2, text: '身体'}, { choose: 3, text: '套餐'}];//商品类型
var numData=[{ choose: 0, text: '无限' }, { choose: 1, text: '一次' }, { choose: 2, text: '二次'}, { choose: 3, text: '三次'},
			{ choose: 4, text: '四次' }, { choose: 5, text: '五次' }, { choose: 10, text: '十次'}];//购买限制
//初始化属性
$(function(){
   try{  
   		//布局
        dataLayout= $("#dataLayout").ligerLayout({allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-28;
   		var width = $(".l-layout").width();
   		$("#prodiv").css("height", height+26);
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
             {display: '', name: 'id', hide:true, isAllowHide:false,width:1},
	         { display: '商品类型', 	name: 'type', width:110, align: 'left',
	        	  render: function (item){
	        		  var title = "";
	        		  $(typeData).each(function(i, row){
	        			  if(row.choose==item.type){
	        				  title = row.text;
	        				  return false;
	        			  }
	        		  });
	        		  return title;
	        	  }
	         },
            { display: '商品编号', 	name: 'cid', width:150, align: 'left'},
            { display: '商品名称', 	name: 'name', width:250, align: 'left'},
            { display: '价格', 	name: 'price', width:100, align: 'left'},
            { display: '市场价格', 	name: 'originalcost', width:100, align: 'left'},
            { display: '购买限制', 	name: 'num', width:100, align: 'left',
	        	  render: function (item){
	        		  var title = "无限";
	        		  $(numData).each(function(i, row){
	        			  if(row.choose==item.num){
	        				  title = row.text;
	        				  return false;
	        			  }
	        		  });
	        		  return title;
	        	  }
	         },
	         { display: '绑定卡券类型', 	name: 'card_id', width:150, align: 'left',
	        	 render: function (item){
	        		 var title = "";
	        		 $(typeData).each(function(i, row){
	        			 if(row.choose==item.card_id){
	        				 title = row.text;
	        				 return false;
	        			 }
	        		 });
	        		 return title;
	        	 }
	         },
            { display: '状态', name: 'isopen', width:90, align: 'left',
                render: function (item){
                	return item.isopen==0 ? "停用" : "正常";
            }},
	         { display: '编辑', isSort: false, width: 80, render: function (rowdata, rowindex, value){
            		var h = "<a href=\"javascript:editData('"+checkNull(rowdata.type)+"')\"><div style='margin-top:6px;'><img src='"+
            					contextURL+"/common/ligerui/ligerUI/skins/icons/edit.gif' /></div></a> ";
              		return h;
	         }}
            ],  pageSize:50,data:{Rows: null,Total:0},      
            width: width-6,height: height-30,
            rownumbers: true,usePager: true,
            onSelectRow : function (data, rowindex, rowobj){
            	curProRecord=data;
            },
            toolbar: { items: [
	            { text: '添加商品', click: addData, type:2, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
	            { text: '添加套餐', click: addData, type:3, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'}
          	]}
        });
       	$("#queryBtn").ligerButton({
       		 text: '查询', width: 100,
       		 click: function (){
       			loadProduct();
       		 }
       	});
       	$(typeData).each(function(i, row){
       		addOption(row.choose, row.text, document.getElementById("stype"));
		});
       	var url = contextURL+"/ac007/init.action"; 
       	$.post(url, null, function(data){
    		var objSet = data.dataSet;
    		if(objSet != null){
    			cardData = objSet[0];
    			if(cardData != null){
    				$(cardData).each(function(i, row){
    					addOption(row.card_id, row.title, document.getElementById("scard_id"));
    				});
    			}
    			loadProduct();//先加载初始化数据，然后加载商品
    		}else{
    			$.ligerDialog.error("数据加载失败！请刷新重试。");
    		}
    	}).error(function(e){$.ligerDialog.error("系统异常！请刷新重试。");});
       	$("#pageloading").hide();
	}catch(e){alert(e.message);}
});
//加载商品数据
function loadProduct(){
	var url = contextURL+"/ac007/load.action"; 
	var params ={catgory:1, type:$("#stype").val(), 
					id:$("#sid").val(), name:$("#sname").val(),card_id:$("#scard_id").val()};
	$.post(url, params, function(data){
		var list = data.proSet;
		if(list!=null && list.length>0){
			masterGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
			masterGrid.loadData(true);
			masterGrid.select(0);
		}else{
			masterGrid.options.data=$.extend(true, {}, null);
			masterGrid.loadData(true);
		}
	}).error(function(e){$.ligerDialog.error("系统异常！请刷新重试。");});
}
//添加商品
function addData(item){
	curProRecord=null;
	showModeDialog = $.ligerDialog.open({ height: 560, url: contextURL+'/AdvancedOperations/AC007/show'+(item.type=="3"?"Package":"ItemStep")+'.jsp', 
		width: 1024, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false,title:(item.type=="3"?"添加套餐":"添加商品"),
		buttons: [{text:'保存', onclick:function(item, dialog){doSubmit(dialog)}},{ text: '关闭', onclick: function (item, dialog) {dialog.close(); } } ] });
}
//编辑商品
function editData(type){
	if(curProRecord==null){
		curProRecord=masterGrid.getSelectedRow();
	}
	showModeDialog = $.ligerDialog.open({ height: 560, url: contextURL+'/AdvancedOperations/AC007/show'+(type=="3"?"Package":"ItemStep")+'.jsp', 
		width: 1024, showMax: false, showToggle: false,allowClose:true, showMin: false, isResize: false,title:(type=="3"?"编辑套餐":"编辑商品"),
		buttons: [{text:'保存', onclick:function(item, dialog){doSubmit(dialog)}},{ text: '关闭', onclick: function (item, dialog) {dialog.close(); } } ] });
}
//提交数据
function doSubmit(dialog){
	var _document = $(dialog.frame.document);
	var type = $("#type", _document).val();
	if (type!="3" && checkNull($("#cid", _document).val()) == "") {
		$.ligerDialog.warn("请输入商品编号！");
		return;
	}
	var name = $("#name", _document);
	if(checkNull(name.val())==""){
		$.ligerDialog.warn("请输入商品名称！");
		name.focus();
		return;
	}
	/*var card_id = $("#card_id", _document);
	if(card_id.val()=="-1"){
		$.ligerDialog.warn("请选择绑定卡券类型！");
		card_id.focus();
		return;
	}*/
	var content = $("#content", _document);
	if(checkNull(content.val())==""){
		$.ligerDialog.warn("请输入商品介绍！");
		content.focus();
		return;
	}
	if(type=="3"){
		var data = detialGrid.getData();
		if(data!=null && data.length>0){
			$("#strJson", _document).val(JSON.stringify(data));
		}else{
			$.ligerDialog.warn("选择套餐类型必须添加项目！");
			return;
		}
	}else{
		var data = stepGrid.getData();
		if(data!=null && data.length>0){
			$("#strJson", _document).val(JSON.stringify(data));
		}
	}
	var waitting = $.ligerDialog.waitting('正在保存中,请稍候...');
	var url = contextURL+"/ac007/post.action";
	var params = $('#dataForm', _document).serialize();
	$.post(url, params, function(data){
		waitting.close();
        if(data.strMessage==""){
        	$.ligerDialog.success("保存成功！");
        	showModeDialog.close();
        	loadProduct();
        }else{
        	$.ligerDialog.error(data.strMessage);
        }
	}).error(function(e){$.ligerDialog.error("系统异常！请刷新重试。");});
}