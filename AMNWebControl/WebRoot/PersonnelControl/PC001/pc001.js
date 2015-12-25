
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivstaff=null;
   	var commoninfodivthirth = null;
   	var curCompid="";
   	var curStaffid="";
   	var curPeosonid="";
	var strPersonid="";
   	var curProjectInfoDate=null;
   	var pc001layout=null;
    var showDialogmanager=null;
    var lsExcelDate=null;
    var excelDialog=null;
    var curLoginUserNo="";
    var curLoginUserName="";
    var strCurDate="";
    var curRecord=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            pc001layout= $("#pc001layout").ligerLayout({ leftWidth: 270, allowBottomResize: false, allowLeftResize: false });
            var height = $(".l-layout-center").height();
	   	
            commoninfodivstaff=$("#commoninfodivstaff").ligerGrid({
                columns: [
                { display: '员工工号', 			name: 'userno', 			width:100	,align: 'center' },
                { display: '员工名称', 			name: 'username', 			width:120	,align: 'center' }
	             
	            ],  pageSize:20, 
                data: null,      
                width: 250,
                height:'95%',
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager: false,	          
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecUserNo(data, rowindex, rowobj);
                } 
            });
            commoninfodivthirth=$("#commoninfodivthirth").ligerGrid({
                columns: [
                { display: '日期', 				name: 'workdate', 			width:80	,align: 'left' },
                { display: '星期', 				name: 'weekdate', 			width:60	,align: 'left' },
                { display: '门店编号', 			name: 'compno',  			width:60	,align: 'left'},
	            { display: '门店名称', 			name: 'compname', 	 		width:100	,align: 'left'},
	            { display: '员工编号',   			name: 'staffno',  			width:70	,align: 'left'},
	            { display: '员工名称',   			name: 'staffname',  		width:70	,align: 'left'},
	            { display: '上班时间',   			name: 'attime',  			width:80	,align: 'left'},
	            { display: '下班时间',   			name: 'pttime',  			width:80	,align: 'left'},
	            { display: '请假备注',   			name: 'leavemark',  		width:300	,align: 'left'},
	            { display: '审核日期',   			name: 'checkdate',  		width:80	,align: 'left',
	            	render: function (row) {  
     					var html ="<font color=\"red\">"+row.checkdate+"</font>";  
     					return html;  
 					}  
 				},
	            { display: '审核人',   			name: 'checkuseid',  		width:60	,align: 'left',
	            	render: function (row) {  
     					var html ="<font color=\"red\">"+row.checkuseid+"</font>";  
     					return html;  
 					}  }
	            
                ],  pageSize:20, 
                data:null,      
                width: '98%',
                height:'95%',
                clickToEdit: false,   enabledEdit: false, usePager: false,  checkbox: true,
                rownumbers:false,
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	curRecord=data;
                    loadHandData(data, rowindex, rowobj);
                },
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                toolbar: { items: [
	                { text: '部门经理审核', click: comfrimBill, img: contextURL+'/common/ligerui/ligerUI/skins/icons/ok.gif' }
					]
                } 
            });
            
            $("#handButton1").ligerButton(
	         {
	             text: '查询考勤', width: 80,
		         click: SelectFingerDataSets
		     });
		    $("#topdatabarCard").ligerToolBar({ items: [
      		    {text: '<input type="text" name="strDdate" id="strDdate"  readonly="true" style="width:120;" onchange="loadInfoByvalidate(this)"/>'}
            ]
            });
           $("#strDdate").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM", labelWidth:80, labelAlign: 'right' })
            //$("#strDdatelast").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString());
			document.getElementById("strDdate").value=today;
			//document.getElementById("strDdatelast").value=today;
			//addOption(parent.document.getElementById("strUserId").value,parent.document.getElementById("strUserName").value,document.getElementById("curLoginUserNo"));
          	$("#pageloading").hide(); 
          	addStaffRecord();
          	addUserRecord();
          	initUserNo();
   		}catch(e){alert(e.message);}
    });
    
    function initUserNo()
    {
        var params="curLoginUserNo="+parent.document.getElementById("strUserId").value ;
	    var myAjax = new parent.parent.Ajax.Request(
			 "pc001/loadCurMangerlist.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsMangerPers!=null && action.lsMangerPers.length>0)
								{
            						var btndate={Rows:action.lsMangerPers,Total: action.lsMangerPers.length};	
									commoninfodivstaff.options.data=$.extend(true, {},btndate);
            						commoninfodivstaff.loadData(true);	
								}
								var gridlen=commoninfodivstaff.rows.length*1;
								if(gridlen==0)
								{
									addUserRecord();
									gridlen=gridlen+1;
								} 
								if(checkNull(commoninfodivstaff.getRow(0).userno)!="")
								{
									addUserRecord();
									gridlen=gridlen+1;
								}
								var curDetialRecord=commoninfodivstaff.getRow(gridlen-1);
								commoninfodivstaff.updateRow(curDetialRecord,{userno: parent.document.getElementById("strUserId").value
																				,username:  parent.document.getElementById("strUserName").value
																			});	 
							},
							asynchronous:true
						});		
		
    }
    function addUserRecord()
    {
    		var row = commoninfodivstaff.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivstaff.addRow({ 
				userno:'',
				username:'' 
			}, row, false);
    }
    
    
    function addStaffRecord()
    {
    		var row = commoninfodivthirth.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivthirth.addRow({ 
				          
			}, row, false);
    }
    
    function addFingerRecord()
    {
    		var row = commoninfodivsecond.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivsecond.addRow({ 
				          
			}, row, false);
    }
    
    function loadSelecUserNo(data, rowindex, rowobj)
    {
    	curLoginUserNo=checkNull(data.userno);
    	curLoginUserName=checkNull(data.username);
    	SelectFingerDataSets();
    }
      //查询考勤
        function SelectFingerDataSets(){
        	
	    try{	    	
	    	if(curLoginUserNo=="" && document.getElementById("strDdate").value=="" )
		 	{
		 		$.ligerDialog.warn("请输入查询账户或查询时间");
		 		return ;
		 	}
		 	var params="curLoginUserNo="+curLoginUserNo ;
			params=params+"&strDdate="+document.getElementById("strDdate").value+"-01";
	        params=params+"&strDdatelast="+document.getElementById("strDdate").value+"-31";
			 var myAjax = new parent.parent.Ajax.Request(
			"pc001/SelectFingerDataSet.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(action.lsStaffMachineinfo!=null && action.lsStaffMachineinfo.length>0)
								{
									var btndate={Rows:action.lsStaffMachineinfo,Total: action.lsStaffMachineinfo.length};	
									commoninfodivthirth.options.data=$.extend(true, {},btndate);
            						commoninfodivthirth.loadData(true);	
            						
            						
								}
								else
								{
								    $.ligerDialog.warn("没有数据！");
									commoninfodivthirth.options.data=$.extend(true, {},{Rows:null,Total:0});
            						commoninfodivthirth.loadData(true);	
            						addStaffRecord();
								}
							},
							asynchronous:true
						});			  
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
	    
	    function loadHandData(data, rowindex, rowobj)
		{
			strCurDate=data.workdate;
			upDialogmanager=$.ligerDialog.open({ height: null, url: contextURL+'/PersonnelControl/PC001/handleave.jsp', width: 400, showMax: false, showToggle: false, showMin: false, isResize: false, title: '员工请假审核' });
		}
  
  		function handStaffLeaveMessage(request)
		{
		 			var responsetext = eval("(" + request.responseText + ")");
			        var strMessage=responsetext.strMessage;
			         if(checkNull(strMessage)=="")
			        {	        		 
			        	upDialogmanager.close();
			        	commoninfodivthirth.updateRow(curRecord,{leavemark:checkNull(responsetext.leavemark)}); 
			        	$.ligerDialog.success("标记请假成功!");
			        }
			        else
			        {
			        	$.ligerDialog.error(strMessage);
			        }
		}
		function comfrimBill()
		{
			if(checkedBfunctionno.length==0)
			{
				 $.ligerDialog.error("请选择需要审核的考勤明细!");
				 return;
			}
			var strNeedComfirmDate="";
			for(var i=0;i<checkedBfunctionno.length;i++)
			{
				strNeedComfirmDate=strNeedComfirmDate+checkedBfunctionno[i]+",";
			}
			var params="curLoginUserNo="+curLoginUserNo ;
			params=params+"&strNeedComfirmDate="+strNeedComfirmDate;
			 var myAjax = new parent.parent.Ajax.Request(
			 "pc001/comfrimBill.action",{
							method:'post',
							parameters:params,
							onComplete:function (request) {
								var action = eval( "("+request.responseText+")");
								if(checkNull(action.strMessage)=="")
						        {	        		 
						        	 $.ligerDialog.success("审核成功!");
						        }
						        else
						        {
						        	$.ligerDialog.error(strMessage);
						        }
								checkedBfunctionno= [];
							},
							asynchronous:true
						});		
		}
		
		
          	
	  
        //获取特殊权限多选的模块
		var checkedBfunctionno= [];       
        function f_onCheckAllRow(checked)
        {
        	for (var rowid in commoninfodivthirth.records)
            {
            	if(checked)
                    addCheckedBfunctionno(commoninfodivthirth.records[rowid]['workdate']);
                else
                    removeCheckedBfunctionno(commoninfodivthirth.records[rowid]['workdate']);
            }
        }
        
	   	function f_onCheckRow(checked, data)
        {
        	if (checked) 
            	addCheckedBfunctionno(data.workdate);
            else 
            	removeCheckedBfunctionno(data.workdate);
        }
	   
	   	function findCheckedBfunctionno(workdate)
        {
            for(var i =0;i<checkedBfunctionno.length;i++)
            {
                if(checkedBfunctionno[i] == workdate) return i;
            }
            return -1;
        }
        
        function addCheckedBfunctionno(workdate)
        {
            if(findCheckedBfunctionno(workdate) == -1)
                checkedBfunctionno.push(workdate);
        }
        function removeCheckedBfunctionno(workdate)
        {
            var i = findCheckedBfunctionno(workdate);
            if(i==-1) return;
            checkedBfunctionno.splice(i,1);
        }
        