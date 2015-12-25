var dataLayout=null;
var dataGrid=null;
var detailGrid=null;
$(function (){
   try{
	  	//布局
	   dataLayout= $("#dataLayout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
       	 height = $(".l-layout-center").height();
       	 centerWidth = dataLayout.centerWidth;
       	var postionSet=parent.gainCommonInfoByCode("GZGW",0);
       	//var postionSet=parent.gainCommonInfoByCodeByUse("GZGW",0,1);
       	$("#dataTab").ligerTab();
       	$("#m_begindate,#d_begindate,").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	$("#m_enddate,#d_enddate").ligerDateEditor({ labelWidth: 120,format: "yyyy-MM-dd", labelAlign: 'right',width:'120' });
       	//表格
       	dataGrid=$("#dataGrid").ligerGrid({
            columns: [
            {display:'门店', name:'attr1', width:150, align:'center' },
            {display:'姓名', name:'attr2', width:150, align:'center' },
            {display:'职位', name:'attr3', width:150, align:'center',
            	render: function (item){
            		var title = "";
            		$(postionSet).each(function(i, postion){
            			if(postion.bparentcodekey==item.attr3){	
							title = postion.parentcodevalue;
							return false;
					    }
            		});
                    return title;
                }	
            },
            {display:'学习课程', name:'attr4', width:220,  align:'center'},
            {display:'学习时间', name:'attr5', width:150, align:'center'}
            ],
            pageSize:50,data: null,      
            width: centerWidth,height:height-60,
            rownumbers:true, usePager:true
        });
       	//表格
       	detailGrid=$("#detailGrid").ligerGrid({
       		columns: [
	          {display:'学习课程', name:'attr1', width:250,  align:'center'},
	          {display:'学习时间', name:'attr2', width:180, align:'center'}
	          ],
	          pageSize:50,data: null,      
	          width: centerWidth,height:height-30,
	          rownumbers:false, usePager:false
       	});
        $("#m_search").ligerButton({
             text: '查询', width: 80,
	         click: function (){
	        	var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
	        	var url = contextURL+"/scc007/loadDataSet.action";
        		var params = {creditNo:$("#m_credit").val(), positionNo:$('#m_position').val(),
        				dateFrom:$('#m_begindate').val(), dateTo:$('#m_enddate').val()};
        		$.post(url, params, function(data){
        			showDialog.close();
        			var list = data.dataSet;
        			if(list != null && list.length>0){
        				dataGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
        			}else{
        				$.ligerDialog.warn("没有查到数据信息!");
        				dataGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
        			}
        			dataGrid.loadData(true);
        		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
	         }
         });
        $("#m_excel").ligerButton({
             text: '转Excel', width: 80,
	         click: function (){
        		var params = "creditNo="+$("#m_credit").val();
        		params +="&positionNo="+$('#m_position').val();
        		params +="&dateFrom="+$('#m_begindate').val();
        		params +="&dateTo="+$('#m_enddate').val();
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/scc007/loadExcel.action?'+params});
        		setTimeout(function () { excelDialog.close(); }, 10000);
	         }
         });
        $("#d_search").ligerButton({
        	text: '查询', width: 80,
        	click: function (){
        		if(checkNull($("#d_staffno").val())=="" && checkNull($("#d_staffname").val())==""){
        			$.ligerDialog.warn("请输入工号或姓名!");
        			return;
        		}
        		var showDialog = $.ligerDialog.waitting('正在查询中,请稍候...');
        		var url = contextURL+"/scc007/loadDetailData.action";
        		var params = {staffNo:$("#d_staffno").val(), strMessage:$('#d_staffname').val(),
        				dateFrom:$('#d_begindate').val(), dateTo:$('#d_enddate').val()};
        		$.post(url, params, function(data){
        			showDialog.close();
        			var list = data.dataSet;
        			if(list != null && list.length>0){
        				detailGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
        			}else{
        				$.ligerDialog.warn("没有查到数据信息!");
        				detailGrid.options.data=$.extend(true, {},{Rows: null,Total: 0});
        			}
        			detailGrid.loadData(true);
        		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
        	}
        });
        $("#d_excel").ligerButton({
        	text: '转Excel', width: 80,
        	click: function (){
        		if(checkNull($("#d_staffno").val())=="" && checkNull($("#d_staffname").val())==""){
        			$.ligerDialog.warn("请输入工号或姓名!");
        			return;
        		}
        		var params = "staffNo="+$("#d_staffno").val();
        		params +="&strMessage="+$('#d_staffname').val();
        		params +="&dateFrom="+$('#d_begindate').val();
        		params +="&dateTo="+$('#d_enddate').val();
        		var excelDialog=$.ligerDialog.open({url: contextURL+'/scc007/loadDetailExcel.action?'+params});
        		setTimeout(function () { excelDialog.close(); }, 10000);
        	}
        });
        var today = new Date();
		var intYear=today.getFullYear();
		var intMonth=today.getMonth()+1;
		var intDay=today.getDate();
		var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
		$('#m_begindate,#d_begindate').val(today);
		$("#m_enddate,#d_enddate").val(today);
		//加载职位信息
		clearOption("m_position");
		addOption("-1", "--------请选择--------", document.getElementById("m_position"));
		$(postionSet).each(function(i, postion){
			addOption(postion.bparentcodekey,postion.parentcodevalue,document.getElementById("m_position"));
		});
		//加载课程信息
		$.post(contextURL+"/scc002/load.action", null, function(data){
			var creditBox = data.listSet;
			if(creditBox != null && creditBox.length>0){
				clearOption("m_credit");
				addOption("-1", "--------请选择--------", document.getElementById("m_credit"));
	   			$.each(creditBox, function(i, create){
	   				addOption(create.no, create.name, document.getElementById("m_credit"));
	   			});
			}
		}).error(function(e){$.ligerDialog.error("获取数据失败，请重试！");});
        $("#pageloading").hide(); 
	}catch(e){alert(e.message);}
});