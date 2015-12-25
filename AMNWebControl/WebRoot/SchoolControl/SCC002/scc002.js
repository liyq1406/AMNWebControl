var masterGrid=null;//学校课程信息表
var detialGrid=null;//职位表
var scc002layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var useSourceDate=[{ choose: 0, text: '美容' }, { choose: 1, text: '美发'}];//类别
var staffPostionData=null;//工作岗位
var positionSet = new Array();
//初始化属性
$(function (){
   try{  
	   	var schoolList = JSON.parse($("#schoolSet").text());//获取合作学校
	   	staffPostionData=parent.gainCommonInfoByCodeByUse("GZGW",0,1);//工作岗位
	   	$.each(staffPostionData, function(i, common){
	   		positionSet.push({credit_no:0, postion:common.bparentcodekey, score:0});
	   	});
   		//布局
        scc002layout= $("#scc002layout").ligerLayout({ rightWidth: 600,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-30;
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
            {display: '', name: 'id', hide:true, isAllowHide:false},
            {display:'编号', name:'no', width:180, align:'left'},
            {display:'学校', name:'school_no', width:180, align:'left',
            	editor: {type: 'select', data: schoolList, valueField:'no', textField:'name'},//autocomplete:true 
            	render: function (item){
                    for(var i = 0; i < schoolList.length; i++){
                        if (schoolList[i]['no'] == item.school_no)
                            return schoolList[i]['name'];
                    }
                    return item.school_no;
                }},
            {display:'类别', name:'type', width:180, align:'left',
            	editor: {type: 'select', data: useSourceDate, valueField: 'choose'},
                render: function (item){
                	for(var i = 0; i < useSourceDate.length; i++){
                        if (useSourceDate[i].choose == item.type){
                        	return useSourceDate[i].text;
                        }
                    }
                	return item.type;
                }
            },
            {display:'课程名称', name:'name', width:260, align:'left', editor:{type:'text'}}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '800',
            height:height,
            clickToEdit: true,   enabledEdit: true,  
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
            	if(checkNull(data.no)!=""){
	            	var params={no: data.no};
	       			var requestUrl ="scc002/loadScore.action"; 
					var responseMethod="loadDetialData";		
					sendRequestForParams_p(requestUrl,responseMethod,params);
            	}
            }	   
   		});
   		$("#masterGrid .l-grid-body-inner").css('width', 800);
   		detialGrid=$("#detialGrid").ligerGrid({
            columns: [
            {display: '', name: 'credit_no', hide:true, isAllowHide:false},
            {display: '职位', name: 'postion', width:220, align: 'left',
	        	render: function (item){
	          		for(var i=0;i<staffPostionData.length;i++){
						if(staffPostionData[i].bparentcodekey==item.postion){	
							return staffPostionData[i].parentcodevalue;								
					    }
					}
	                return item.position;
	            }},
            {display: '学分', name: 'score', width:220, align: 'left', editor:{type:'text'}}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '440',
            height:height,
            clickToEdit: true,   enabledEdit: true,  checkbox:false,
            rownumbers: false,usePager: false
        });
       $("#detialGrid .l-grid-body-inner").css('width', 440).css('height', height-50);
       $("#searchButton").ligerButton({
    	   	text: '查询', width: 100,
    	   	click: function (){
    	   		var requestUrl ="scc002/query.action";
	        	var params = {name:$("#strSearch").val()};	
	        	var responseMethod="loadDataSet";		
	        	sendRequestForParams_p(requestUrl,responseMethod, params);
    	   	}
       });
       $("#addBtn").ligerButton({
			 text: '新增', width: 100,
			 click: function (){
				if(curpagestate!=3){
        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行新增操作!");
        		 	return ;
	    		}
				curpagestate=1;
				addNewRow();
				detialGrid.options.data=$.extend(true, {}, {Rows: positionSet,Total: positionSet.length});
				detialGrid.loadData(true);
			 }
       });
       $("#editBtn").ligerButton({
       		 text: '编辑', width: 100,
       		 click: function (){
       			if(curpagestate!=3){
        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行编辑操作!");
        		 	return ;
        		}
				curpagestate=2;
       		 }
       });
       $("#saveBtn").ligerButton({
       		 text: '保存', width: 100,
       		 click: function (){
       			var creditJson="", scoreJson=""; 
       			if(curpagestate==3){
        		 	$.ligerDialog.warn("未保存数据,无需保存!");
        		 	return;
        		}else if(curpagestate==2){
        			creditJson = JSON.stringify(masterGrid.getUpdated());
        			scoreJson = JSON.stringify(detialGrid.getUpdated());
        		}else{
        			creditJson = JSON.stringify(masterGrid.getAdded());
        			scoreJson = JSON.stringify(detialGrid.getData());
        		}
       			$("#pageloading").show();
       			var params="no="+ creditJson +"&name="+ scoreJson;
       			var requestUrl ="scc002/post.action"; 
				var responseMethod="postMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params);
       		 }
       });
       $("#pageloading").hide();
       loadData();
	}catch(e){alert(e.message);}
});

function loadData(){
	var requestUrl ="scc002/load.action"; 
	var responseMethod="loadDataSet";		
	sendRequestForParams_p(requestUrl,responseMethod, null);
}

function loadDataSet(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.listSet;
	if(list != null && list.length>0){
		masterGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		masterGrid.loadData(true);   
		masterGrid.select(0);          	
	}else{
		masterGrid.options.data=$.extend(true, {},{Rows: null,Total:0});
		masterGrid.loadData(true);
		addNewRow();
		curpagestate=1;
	}
}

/*保存的回调函数*/
function postMessage(request){
   	try{
        var responsetext = eval("(" + request.responseText + ")");
        var strMessage=responsetext.strMessage;
        if(responsetext.sysStatus==1){	        		 
        	 $.ligerDialog.success(strMessage);
        	 curpagestate=3;
        }else{
        	$.ligerDialog.error(strMessage);
        }
        loadData();
        curpagestate=3;
        $("#pageloading").hide();
    }catch(e){
    	$.ligerDialog.error(e.message);
	}
}

function addNewRow(){
    var row = masterGrid.getSelectedRow();
    //参数1:rowdata(非必填)
    //参数2:插入的位置 Row Data 
    //参数3:之前或者之后(非必填)
    masterGrid.addRow({ 
        no: "",
        name: ""
    }, row, false);
} 

function loadDetialData(request){
	var responsetext = eval("(" + request.responseText + ")");
	var list = responsetext.scoreList;
	if(list != null && list.length>0){
		detialGrid.options.data=$.extend(true, {}, {Rows: list,Total: list.length});
		detialGrid.loadData(true);   
	}else{
		detialGrid.options.data=$.extend(true, {},{Rows: null,Total:0});
		detialGrid.loadData(true);
	}
}