	var commoninfodivmaster=null;	//产品入库主档列表
   	var commoninfodivdetial=null;	//产品入库明细列表
   	var scc006layout=null;
   	var curpagestate=3; // 1 add 2 modify 3 browse
	var curDetialRecord = null;
   	var showDialogmanager=null;
   	var staffPostionData=null;//工作岗位
   	var positionSet = new Array();
   	//初始化属性
    $(function ()
   	{
	   try
	   {  
	   		//布局
            scc006layout= $("#scc006layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
	   		var height = $(".l-layout-center").height()-5;
	   		staffPostionData=parent.gainCommonInfoByCodeByUse("GZGW",0,1);//工作岗位
	   		$.each(staffPostionData, function(i, common){
		   		positionSet.push({"binsercompid":0,"postion_no":common.bparentcodekey, "postion":common.parentcodevalue});
		   	});
           	commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { 	name: 'binsercompid', hide:true	},
                { display: '编号', 	name: 'postion_no', 	width:170,align: 'left'},
                { display: '职位', 	name: 'postion', 	width:170,align: 'left'}
                ],  pageSize:20, 
                data:{Rows: positionSet,Total:positionSet.length},      
                width: '340',
                height:height,
                clickToEdit: false,   enabledEdit: false,  
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj){
                    selecpostion(data, rowindex, rowobj);
                }	   
            });
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '指标类型', 	name: 'type', width:220, align: 'left' },
                { display: '指标数量', 	name: 'num', width:220, align: 'left' }
                ],  pageSize:20, 
                data:{Rows: null,Total:0},      
                width: '900',
                height: height-110,
                clickToEdit: false,   enabledEdit: true,  checkbox:false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj){
                    //curDetialRecord=data;
                } ,
                onRClickToSelect:true
            });
           	 $("#indexSelected").ligerComboBox({  
                 data: [
                        { text: '项目数指标', id: '项目数指标' },
                        { text: '疗程业绩指标', id: '疗程业绩指标' }
                    ], valueFieldID: 'indexSelected1'
                }); 
           	 
           	 $("#addBtn").ligerButton({
   				 text: '载入', width: 100,
   				 click: function (){
   					loadScore();
   				 }
   			 });
           	 
           	 $("#saveBtn").ligerButton({
           		 text: '保存', width: 100,
           		 click: function (){
           			 save();
           		 }
           	 });
          	$("#pageloading").hide(); 
          	$("#date").ligerDateEditor(
	            {
	                format: "yyyyMM",
	                labelWidth: 100,
	                labelAlign: 'center',
	                cancelable : false
	        });
          	commoninfodivmaster.select(0);
//          	$("#postion_no").ligerGetTextBoxManager().setDisabled();
//          	$("#postion").ligerGetTextBoxManager().setDisabled();
   		}catch(e){alert(e.message);}
    });
    
    function selecpostion(data, rowindex, rowobj){
    	$("#postion_no").val(data.postion_no);
    	$("#postion").val(data.postion)
    }
    function save(){
    	var postion_no = $("#postion_no").val();
    	var time = $("#date").val();
    	var type = $("#indexSelected1").val();
    	var num = $("#num").val();
    	var params = {"postion_no":postion_no,
	    			"time":time,
	    			"type":type,
	    			"num":num};
    	if(postion_no==""||time==""||type==""||num==""){
    		$.ligerDialog.warn("参数不能为空！");
    		return;
    	}
    	var requestUrl ="scc006/save.action"; 
    	var responseMethod = "saveCallBack";
    	sendRequestForParams_p(requestUrl,responseMethod, params);
    }
    function saveCallBack(request){
    	$.ligerDialog.success("保存成功");
    }
    
    function loadScore(){
    	var postion_no = $("#postion_no").val();
    	var time = $("#date").val();
    	var type = $("#indexSelected1").val();
    	var params = {"postion_no":postion_no,
	    			"time":time,
	    			"type":type
    				}
    	var requestUrl ="scc006/load.action"; 
    	var responseMethod = "loadData";
    	sendRequestForParams_p(requestUrl,responseMethod, params);
    }
    //加载指标
    function loadData(request){
    	var responsetext = eval("(" + request.responseText + ")");
    	var list = responsetext.listSet;
    	if(list != null && list.length>0){
    		//学分
    		commoninfodivdetial.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
    		commoninfodivdetial.loadData(true);   
    	}
    }