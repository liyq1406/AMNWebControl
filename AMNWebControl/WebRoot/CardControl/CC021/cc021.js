var compTree=null;
var dataLayout=null;
var masterGrid=null;
var payGrid=null;
var curpagestate=3; // 1 add 2 modify 3 browse
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
		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);//支付方式
      	payGrid=$("#payGrid").ligerGrid({
            columns: [
            { display: '支付方式', 	name: 'paymode',  		width:120,align: 'center' ,
             	editor: { type: 'select', data: null, valueField:'choose', selectBoxWidth:105},
	            	render: function (item){
	              		for(var i=0;i<lsZw.length;i++){
							if(lsZw[i].bparentcodekey==item.paymode){	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	        },
            { display: '支付金额', 	name: 'payamt', 		width:100,align: 'right', editor: { type: 'float' }},
            { display: '支付备注', 	name: 'payremark', 		width:150,align: 'left',editor: { type: 'text'}}
            ],  pageSize:25, 
            data:{Rows: null,Total:0},      
          	width: 430, height:180,
            enabledEdit: true, checkbox: false,
            rownumbers: false,usePager: false,
            toolbar: { items: [
                { text: '增加支付方式', click: function(){payGrid.addRow();}, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }]
            }	
        });
      	
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
            {display: '', name: 'csitemno', hide:true, isAllowHide:false, width:1},
            {display: '', name: 'csunitprice', hide:true, isAllowHide:false, width:1},
            { display: '产品条码', 	name: 'goodsbarno', width:180, align: 'left',editor:{type:'text',onChanged:loadGoods}},
            { display: '产品名称', 	name: 'csitemname', width:200, align: 'left'},
            { display: '产品价格', 	name: 'csitemamt', width:150, align: 'left',editor:{type:'float', onChanged:checkGoodsPrice}}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: centerWidth,
            height: layoutHeight-10,
            enabledEdit:true, rownumbers: false,usePager: false,
            toolbar: { items: [
	            { text: '新增', click: function(){masterGrid.addRow();}, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	       	]}
   		});
   		$("#saveBtn").ligerButton({
      		 text: '保存', width: 100,
      		 click: function (){
      			if(curpagestate==1){
	       		 	$.ligerDialog.warn("正在保存中,不能重复保存!");
	       		 	return;
	       		}
      			var csinfotype = $("#csinfotype").val();
      			var cspayamt = $("#cspayamt");
      			if(checkNull(cspayamt.val())==""){
      				$.ligerDialog.warn("金额不能为空！");
      				cspayamt.focus();
      				return;
      			}
      			if(csinfotype=="2"){
      				var goodsdata = masterGrid.getData();
      				if(goodsdata==null || goodsdata.length==0){
      					$.ligerDialog.warn("产品明细列表不能为空！");
      					return;
      				}
      				var flag=false;
      				$(goodsdata).each(function(i, goods){
      					if(checkNull(goods.goodsbarno)=="" || checkNull(goods.csitemamt)==""){
      						flag=true;
      						return false;
      					}
      				});
      				if(flag){
      					$.ligerDialog.warn("产品条码和价格不能为空！");
      					return;
      				}
      				$("#strMessage").val(JSON.stringify(goodsdata));
      			}
      			var isempty=false;
      			var paydata = payGrid.getData();
      			$(paydata).each(function(i, pay){
      				if(checkNull(pay.paymode)=="" || checkNull(pay.payamt)==""){
      					isempty=true;
      					return false;
      				}
      			});
      			if(isempty){
      				$.ligerDialog.warn("支付方式和支付金额不能为空！");
  					return;
      			}
      			$("#strJsonParam").val(JSON.stringify(paydata));
  				curpagestate=1;
      			var params = $("#detailForm").serialize();
      			var url = contextURL+"/cc021/post.action"; 
      			$.post(url, params, function(data){
      				curpagestate=3;
      				if(checkNull(data.strMessage)==""){
      					$.ligerDialog.success("保存成功！");
      					setTimeout(function(){
      						window.location.reload();
      					}, 2000);
      				}else{
      					$.ligerDialog.error(data.strMessage);
      				}
      			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
      		 }
   		});
   		payGrid.addRow();
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
	$("#strCurCompId").val(strCurCompId);
	var params = {strCurCompId:strCurCompId};
	var url = contextURL+"/cc021/loadBill.action"; 
	$.post(url, params, function(data){
		var billId = data.strCurBillId;
		if(checkNull(billId)!=""){
			$("#csbillid").val(billId);
			var payData = new Array();
			var paySet = data.strJsonParam.split(";");
			$(paySet).each(function(i, pay){
				var payname=parent.loadCommonControlValue("ZFFS",0,pay);
				payData.push({choose:pay, text:payname});
			});
			payGrid.columns[0].editor.data=payData;
		}else{
			$.ligerDialog.error("退款单号生成失败，请重新刷新页面！");
		}
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}
//加载产品信息
function loadGoods(obj){
	if(checkNull(obj.value) == ""){
		return;
	}
	var params = {"goodsinfo.goodsbarno":obj.value, _random:Math.random()};
	var url = contextURL+"/cc021/loadGoods.action"; 
	$.post(url, params, function(data){
		var goods = data.goodsinfo;
		var row={csitemno:'',goodsbarno:'',csitemname:'',csunitprice:''};
		if(checkNull(data.strMessage)=="" && goods!=null){
			row.csitemno=goods.bgoodsno;
			row.goodsbarno=goods.goodsbarno;
			row.csitemname=goods.goodsname;
			row.csunitprice=goods.storesalseprice;
	    }else{
	    	$.ligerDialog.warn(data.strMessage);
	    }
		masterGrid.updateRow(obj.record, row);
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
}
//价格只能小于等于销售门店价格
function checkGoodsPrice(obj){
	if(obj.value*1>obj.record.csunitprice*1){
		$.ligerDialog.warn('价格只能小于等于销售门店价格！');
		masterGrid.updateCell('csitemamt', "0", obj.record);
		return;
	}
}
