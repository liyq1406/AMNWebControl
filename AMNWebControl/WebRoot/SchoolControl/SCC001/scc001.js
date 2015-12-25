var masterGrid=null;	//合作学校列表
var detialGrid=null;	//课程列表
var scc001layout=null;
var curpagestate=3; // 1 add 2 modify 3 browse
var strCurSupperId = "";
//初始化属性
$(function(){
   try{  
   		//布局
        scc001layout= $("#scc001layout").ligerLayout({ rightWidth: 400,  allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
   		var height = $(".l-layout-center").height()-28;
   		masterGrid=$("#masterGrid").ligerGrid({
            columns: [
            {display: '', name: 'id', hide:true, isAllowHide:false},
            {display: '学校名称', name: 'name', 	width:340,align: 'center'},
            {display: '', name: 'website', hide:true, isAllowHide:false},
            {display: '', name: 'address', hide:true, isAllowHide:false},
            {display: '', name: 'telephone', hide:true, isAllowHide:false},
            {display: '', name: 'contacts', hide:true, isAllowHide:false},
            {display: '', name: 'mobile', hide:true, isAllowHide:false},
            {display: '', name: 'describe', hide:true, isAllowHide:false},
            {display: '', name: 'remark', hide:true, isAllowHide:false}
            ],  pageSize:20, 
            data:{Rows:null, Total:0},
            width: '340',
            height:height,
            clickToEdit: false,   enabledEdit: false,  
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                loadDetialData(data, rowindex, rowobj);
            }	   
        });
       	$("#masterGrid .l-grid-body-inner").css('width', 340);
       	detialGrid=$("#detialGrid").ligerGrid({
            columns: [
            { display: '已合作课程', 	name: 'insergoodsno', width:110, align: 'left' },
            { display: '培训人数', 	name: 'insergoodsname', width:150, align: 'left' },
            { display: '合格人次', name: 'inserunit', width:150, align: 'left'},
            { display: '合格率',  name: 'insercount', width:150, align: 'left'}
            ],  pageSize:20, 
            data:{Rows: null,Total:0},      
            width: '900',
            height:'500',
            clickToEdit: true,   enabledEdit: true,  checkbox:false,
            rownumbers: false,usePager: false,
            onSelectRow : function (data, rowindex, rowobj){
                //curDetialRecord=data;
            } , onRClickToSelect:true
        });
       	 $("#searchButton").ligerButton({
             text: '查询', width: 100,
	         click: function (){
	        	 strCurSupperId = "";
	        	 var requestUrl ="scc001/query.action";
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
				strCurSupperId="";
				$('#dataTable input,#dataTable textarea').val("").attr("readonly",false);
				$("#name").focus();
			 }
		 });
       	 $("#editBtn").ligerButton({
       		 text: '编辑', width: 100,
       		 click: function (){
       			if(curpagestate!=3 || checkNull(strCurSupperId) == ""){
        		 	$.ligerDialog.warn("请保存当前信息或刷新本界面后在进行编辑操作!");
        		 	return ;
        		}
       			curpagestate=2;
       			$('#dataTable input,#dataTable textarea').attr("readonly",false);
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
       				$.ligerDialog.error("学校名字不能为空！");
       				return;
       			}
       			var telephone = $("#telephone").val();
       			if (checkNull(telephone) == "") {
       				$.ligerDialog.error("电话不能为空！");
       				return;
       			}
       			var contacts = $("#contacts").val();
       			if (checkNull(contacts) == "") {
       				$.ligerDialog.error("联系人不能为空！");
       				return;
       			}
       			var mobile = $("#mobile").val();
       			if (checkNull(mobile) == "") {
       				$.ligerDialog.error("联系人电话不能为空！");
       				return;
       			}
       			//var params = $('#dataForm').serialize();
       			var params = {name:name, website:$("#website").val(), 
       					address:$("#address").val(), telephone:telephone, contacts:contacts, mobile:mobile, 
       					describe:$("#describe").val(), remark:$("#remark").val(), state:1};	
       			if(checkNull(strCurSupperId) != ""){
       				params.id = strCurSupperId;
       			}
    	     	var requestUrl ="scc001/post.action"; 
				var responseMethod="postMessage";		
				sendRequestForParams_p(requestUrl,responseMethod,params);
       		 }
       	 });
      	$("#pageloading").hide();
      	loadData();
	}catch(e){alert(e.message);}
});

function loadData(){
	var requestUrl ="scc001/load.action"; 
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
		var row = masterGrid.getSelectedRow();
	    //参数1:rowdata(非必填)
	    //参数2:插入的位置 Row Data 
	    //参数3:之前或者之后(非必填)
		masterGrid.addRow({name: ""}, row, false);
		$('#dataTable input,#dataTable textarea').val("");
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
    }catch(e){
    	$.ligerDialog.error(e.message);
	}
}

function loadDetialData(data, rowindex, rowobj){
	if(checkNull(data.id)!=""){
		strCurSupperId = data.id;
		$("#name").val(checkNull(data.name));
		$("#website").val(checkNull(data.website));
		$("#address").val(checkNull(data.address));
		$("#telephone").val(checkNull(data.telephone));
		$("#contacts").val(checkNull(data.contacts));
		$("#mobile").val(checkNull(data.mobile));
		$("#describe").val(checkNull(data.describe));
		$("#remark").val(checkNull(data.remark));
	}
}