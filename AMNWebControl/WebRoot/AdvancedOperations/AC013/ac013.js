
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//卡类型明细
   	var commoninfodivdetial=null;//转卡规则明细
   	var curCompid="";
   	var curbillid="";
   	var curBillCompid="";
	var curDetialRecord=null;
    var chooseData = [{ choose: 1, text: '已预约' }, { choose: 0, text: '未预约'}];
    var dchooseData = [{ choose: 1, text: '大工' }, { choose: 2, text: '中工'}, { choose: 3, text: '小工'}];
   	var curappflowDate=null;
   	var ac013layout=null;
   	var newStoreflowinfo=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac013layout= $("#ac013layout").ligerLayout({ rightWidth: 320,  allowBottomResize: false, allowLeftResize: false });
            var height = $(".l-layout-center").height();
	   		
          	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '门店', 				name: 'bcscompid',  width:90,align: 'left' ,minWidth: 60},
	            { display: '单号', 				name: 'bcsbillid', 	width:120,align: 'left' },
	            { display: '消费日期', 			name: 'financedate', 	width:120,align: 'left' },
	            { display: '会员名称', 			name: 'csname', 	width:100,align: 'left' },
	            { display: '手机号码', 			name: 'strMemberPhone', 	width:120,align: 'left' },
	            { display: '卡号', 				name: 'cscardno', 		width:100,align: 'left' },
	            { display: '是否预约', 			name: 'reservationflag',  width:60,align: 'left' , 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.reservationflag == 1) return "<font color=\"blue\">"+'已预约'+"</font>";
	                      return '未预约';
	                }
	            },
	            { display: '预约工号', 				name: 'reserveStaffinfo', 	width:200,align: 'left' }
                ],  pageSize:20, 
                data:null,      
                width: ac013layout.centerWidth*1+ac013layout.leftWidth*1+ac013layout.rightWidth*1-30,
                height:'95%',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager:true,  
  
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curDetialRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
        	
        	
        	commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '项目名称', 				name: 'itemname', 		width:120,align: 'left' },
	            { display: '类型', 					name: 'stafftype', 		width:60,align: 'left'  , 
	                editor: { type: 'select', data: dchooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '大工';
	                    else if (item.stafftype ==2) return '中工';
	                     else return '小工';
	                }},
	            { display: '员工名称', 				name: 'staffname', 		width:80,align: 'left' },
	           	{ name: 'itemno', 		width:1,hide: true },
	            { name: 'staffno', 		width:1,hide: true },
	            { name: 'csseqno', 	width:1,hide: true }
                ],  pageSize:20, 
                data:null,      
                width: 310,
                height:'400',
                clickToEdit: false,   enabledEdit: false,checkbox:true,
                rownumbers: false,usePager: false,
                onCheckRow:f_onCheckRow_thirth,
                onCheckAllRow: f_onCheckAllRow_thirth,
                isChecked: mokuai_isChecked_thirth 
            });
            
             $("#searchButton").ligerButton(
	         {
	             text: '查询预约单', width: 100,
		         click: function ()
		         {
		            searchRecord();
		         }
	         });
	         
	           $("#confirmCurRootInfo").ligerButton(
	         {
	             text: '标记预约单', width: 160,
		         click: function ()
		         {
		            editCurRecord();
		         }
	         });
            $("#strSearchDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'90' }); 
          	$("#pageloading").hide(); 
          	   var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strSearchDate").value=today;
          	addRecord();
          	addDRecord()
   		}catch(e){alert(e.message);}
    });
    
    
    function searchRecord()
    {
    	if(document.getElementById("strSearchDate").value=="")
    	{
    		$.ligerDialog.warn("请确认查询日期!");
    		return ;
    	}
    	var strCurCompId=getCurOrgFromSearchBar();
    	var params = "strCurCompId="+strCurCompId;			
    	params =params+ "&strSearchDate="+document.getElementById("strSearchDate").value;		
    	params =params+ "&iSearchState="+document.getElementById("iSearchState").value;
    	params =params+ "&strMemberNo="+document.getElementById("strMemberNo").value; 
    	params =params+ "&strStaffNo="+document.getElementById("strStaffNo").value; 
    	
     	var requestUrl ="ac013/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
   
    function loadDataSetMessage(request)
    {
    	try
        {  		
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsMconsumeinfo!=null && responsetext.lsMconsumeinfo.length>0)
	   		{
	   	
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsMconsumeinfo,Total: responsetext.lsMconsumeinfo.length});
            	commoninfodivsecond.loadData(true);  
           		
	   		}
	   		else
	   		{	   	
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true); 
            	addRecord();     
	   		}
	   	}catch(e){alert(e.message);}
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        document.getElementById("cscompid").value=data.bcscompid;
        document.getElementById("csbillid").value=data.bcsbillid;
        document.getElementById("financedate").value=data.financedate;
        document.getElementById("csname").value=data.csname;
        document.getElementById("strMemberPhone").value=data.strMemberPhone;
        document.getElementById("cscardno").value=data.cscardno;
    	 checkedseqno_thirth= [];
	   	 checkedBfunctionno_thirth= [];
    	var params = "strCurCompId="+data.bcscompid;			
    	params =params+ "&strCurBillId="+data.bcsbillid;		
     	var requestUrl ="ac013/loadDetialInfo.action"; 
		var responseMethod="loadDetialInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    	
    }
    function loadDetialInfoMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsDetialInfo!=null && responsetext.lsDetialInfo.length>0)
	   		{
	   	
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDetialInfo,Total: responsetext.lsDetialInfo.length});
            	commoninfodivdetial.loadData(true);  
           		
	   		}
	   		else
	   		{	   	
	   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivdetial.loadData(true); 
            	addDRecord();     
	   		}
	   	}catch(e){alert(e.message);}
    }
  
     
      
     function addRecord()
     {
     		if(parent.hasFunctionRights( "AC013",  "UR_MODIFY")!=true)
	        {
	        	$.ligerDialog.error("该用户没有编辑权限,请确认!");
	        }
	        else
	        {
	        	   var row = commoninfodivsecond.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivsecond.addRow({ 
				                bbillid: "",
				                appflowtype: "",
				                appflowcode: "",
				                appflowstore: "",       
				                appflowvalue: "",       
				                startdate: "", 
				                enddate: "", 
				                appflowstate: ""
				             
				            }, row, false);
				
	        }
     }
     
     
      function addDRecord()
     {
     		 var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
				   commoninfodivdetial.addRow({ 
				                itemno: "",
				                stafftype: "",
				                staffname: ""
				            }, row, false);
     }
    
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "AC013",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}

        	try
			{
				 document.getElementById("reserveStaffinfo").value="";
				 if(checkedBfunctionno_thirth.length==0)
				 {
				 	//$.ligerDialog.error("请确认预约员工!");
        		 	//return;
				 }
			     for(var i=0;i<checkedBfunctionno_thirth.length;i++)
			     {
			     	document.getElementById("reserveStaffinfo").value=checkedBfunctionno_thirth[i]+","+document.getElementById("reserveStaffinfo").value;
			     }
			   
				var requestUrl ="ac013/postDetial.action";
				var params = "strCurCompId="+document.getElementById("cscompid").value ;			
    			params =params+ "&strCurBillId="+document.getElementById("csbillid").value;	
				params =params+ "&reserveStaffinfo="+document.getElementById("reserveStaffinfo").value;	
				var responseMethod="postDetialMessage";	
				
				var curjosnparam="";
		        var needReplaceStr="";
		        var strJsonParam_detial="";
		        //------卡号段列表
		        for (var rowid in commoninfodivdetial.records)
				{
						var row =commoninfodivdetial.records[rowid];
						for(var i=0;i<checkedseqno_thirth.length;i++)
						{
							if(row.csseqno==checkedseqno_thirth[i])
							{
								for(var j=0;j<checkedBfunctionno_thirth.length;j++)
								{
									if(row.staffno==checkedBfunctionno_thirth[j])
									{
										curjosnparam=JSON.stringify(row);
										curjosnparam=curjosnparam.replace("%","");
										curjosnparam=curjosnparam.replace("#","");
								         		   
										if(strJsonParam_detial!="")
								  			strJsonParam_detial=strJsonParam_detial+",";
										strJsonParam_detial= strJsonParam_detial+curjosnparam;
									}
								}
							}
						}
						
				}            		 
				if(strJsonParam_detial!="")
				{
					 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
				}
			
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
			catch(e)
			{
				alert(e.message);
			}
            
        }
        
        function postDetialMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 $.ligerDialog.success("标示成功!");
	        		
	        	}
	        	else
	        	{
	        		alert(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        
        	
	function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114)//F3
			{
				addRecord();
			}
			else if(key==115)//F4
			{
				 deleteCurRecord();
			}
			else if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
		}
		
		function  hotKeyEnter()
		{
		
			try
			{
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
				
			}
			catch(e){alert(e.message);}
				
		}  
		function mokuai_isChecked_thirth(rowdata)
	   	{
	   		 if (checkNull(rowdata.showFlag)==1) 
	   		 {
	   		 	f_onCheckRow_thirth(true, rowdata);
                return true;
             }
            return false;
	   	}
		function f_onCheckAllRow_thirth(checked)
        {
            for (var rowid in commoninfodivdetial.records)
            {
                if(checked)
                {
                    addCheckedBfunctionno_thirth(commoninfodivdetial.records[rowid]['staffno']);
                    addCheckedSeqno_thirth(commoninfodivdetial.records[rowid]['csseqno']);
                }
                else
                {
                    removeCheckedBfunctionno_thirth(commoninfodivdetial.records[rowid]['staffno']);
                    removeCheckedBSeqno_thirth(commoninfodivdetial.records[rowid]['csseqno']);
                }
            }
        }
        
        function f_onCheckRow_thirth(checked, data)
        {
        	if (checked) 
        	{
            	addCheckedBfunctionno_thirth(data.staffno);
            	addCheckedSeqno_thirth(data.csseqno);
            }
            else 
            {
            	removeCheckedBfunctionno_thirth(data.staffno);
            	removeCheckedBSeqno_thirth(data.csseqno);
            }
        }
        var checkedseqno_thirth= [];
	   	var checkedBfunctionno_thirth= [];
	   	function findCheckedBfunctionno_thirth(staffno)
        {
            for(var i =0;i<checkedBfunctionno_thirth.length;i++)
            {
                if(checkedBfunctionno_thirth[i] == staffno) return i;
            }
            return -1;
        }
        
        function addCheckedBfunctionno_thirth(staffno)
        {
            if(findCheckedBfunctionno_thirth(staffno) == -1)
                checkedBfunctionno_thirth.push(staffno);
        }
        function removeCheckedBfunctionno_thirth(staffno)
        {
            var i = findCheckedBfunctionno_thirth(staffno);
            if(i==-1) return;
            checkedBfunctionno_thirth.splice(i,1);
        }
        
        function findCheckedSeqno_thirth(csseqno)
        {
            for(var i =0;i<checkedseqno_thirth.length;i++)
            {
                if(checkedseqno_thirth[i] == csseqno) return i;
            }
            return -1;
        }
        
        function addCheckedSeqno_thirth(csseqno)
        {
            if(findCheckedSeqno_thirth(csseqno) == -1)
                checkedseqno_thirth.push(csseqno);
        }
        function removeCheckedBSeqno_thirth(csseqno)
        {
            var i = findCheckedSeqno_thirth(csseqno);
            if(i==-1) return;
            checkedseqno_thirth.splice(i,1);
        }