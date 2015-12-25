	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivchangepro=null;//支付方式
	var commoninfodivdetialpro=null;//卡拆分
	var commoninfodivdetial_combin=null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurBillId="";
	var cc017layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
   	var fourthsalerinidmanager = null;
   	var fifthsaleridmanager = null;
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var eighthsaleridmanager = null;
   	var curOldCardRecord=null;
   	var lsStaffinfo=null;
   	var postState=0;
   	var curEmpManger=null;
   	var curRecord=null;
   	var curCRecord=null;
	var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
	var billChooseData = [{ choose: 0, text: '单据录入' }, { choose: 1, text: '已登记'}, { choose: 2, text: '已审核'}, { choose: 3, text: '已驳回'}, { choose: 4, text: '已退卡生效'}];
    $(function ()
   	{
	   try
	   {
	   		cc017layout= $("#cc017layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
            var height = $(".l-layout-center").height();
            //门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		slide: false,
           		checkbox: false,
           		height:'100%'
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
	   		  
            commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '门店', 		name: 'breturncompid', 		width:50	,align: 'left' },
                { display: '单号', 		name: 'breturnbillid', 		width:120	,align: 'left' }
	            ],  pageSize:20, 
                data: null,      
                width: '180',
                height:height-80,
                enabledEdit: false,  checkbox: false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
            
           commoninfodivdetialpro=$("#commoninfodivdetialpro").ligerGrid({
                columns: [
                { display: '转新卡', 			name: 'returnflag',  		width:60,align: 'left',render: checkboxRender},
                { display: '疗程编号', 		name: 'changeproid',  		width:100,align: 'left' },
                { display: '疗程名称', 		name: 'changeproname', 		width:180,align: 'left'},
	            { display: '剩余次数', 		name: 'lastcount', 			width:80,align: 'left'},
	            { display: '剩余金额', 		name: 'lastamt', 			width:80,align: 'left'},
	            { display: '退换次数', 		name: 'changeprocount', 	width:80,align: 'center',editor: { type: 'int' ,onChanged: validateCurCostCount}},
	            { display: '退换金额', 		name: 'changeproamt', 		width:80,align: 'left'},
	            { display: '第一销售', 		name: 'firstsalerid', 		width:90,align: 'center',
	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,onChanged: validateFristSale,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.firstsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'firstsaleamt', 			width:60,align: 'right',editor: { type: 'float' }},
	            { display: '第一烫染', 		name: 'secondsalerid', 		width:90,align: 'center',
	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true, valueField: 'choose',selectBoxWidth:150,onChanged : validateTrShareAmt},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.secondsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'secondsaleamt', 			width:60,align: 'right'},
	            { display: '备注', 		name: 'changemark', 			width:130,align: 'left'},
	            {	name: 'breturnseqno', 	hide:true,width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'300',
                enabledEdit: true, checkbox:false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curRecord = data;
                }  
            });
           
           commoninfodivchangepro=$("#commoninfodivchangepro").ligerGrid({
                columns: [
                { display: '疗程编号', 		name: 'changeproid',  		width:100,align: 'left' },
                { display: '疗程名称', 		name: 'changeproname', 		width:180,align: 'left'},
	            { display: '兑换次数', 		name: 'changeprocount', 	width:80,align: 'center'},
	            { display: '兑换金额', 		name: 'changeproamt', 		width:80,align: 'left'},
	            { display: '第一销售', 		name: 'firstsalerid', 		width:90,align: 'center',
	            	editor: { type: 'select', data: null,  url:'loadAutoStaff',autocomplete: true,valueField: 'choose',selectBoxWidth:150,alwayShowInDown:true},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.firstsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'firstsaleamt', 			width:60,align: 'right',editor: { type: 'float' }},
	            { display: '第一烫染', 		name: 'secondsalerid', 		width:90,align: 'center',
	            	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:150},
	            	render: function (item)
	              	{
	              		for(var i=0;i<lsStaffinfo.length;i++)
						{	
								if(lsStaffinfo[i].bstaffno==item.secondsalerid)
								{
									return lsStaffinfo[i].staffname;
								}
						}
	                    return '';
	                } 
	            },
	            { display: '分享', 		name: 'secondsaleamt', 			width:60,align: 'right'},
	            { display: '备注', 		name: 'changemark', 			width:230,align: 'left'},
	            {	name: 'bproseqno', 	hide:true,width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '100%',
                height:'200',
                enabledEdit: false, checkbox:false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curCRecord = data;
                }  
            });
            
              $("#toptoolbardetial").ligerToolBar({ items: [
      		     { text: '编号:&nbsp;<input type="text"  name="fastProId" id="fastProId" maxlength="20"  style="width:100;"   onfocus="itemsearchbegin(this,3);"/> '},
      		     { text: '次数:&nbsp;<input type="text"  name="fastProCount" id="fastProCount" maxlength="20"  style="width:40;"   onchange="validatePrice(this);validateCCount(this);"/> '},
      		     { text: '金额:&nbsp;<input type="text"  name="fastProAmt"   id="fastProAmt"   maxlength="20"  style="width:60;"   onchange="validatePrice(this);validateCAmt(this);"/> '},
      		     { text: '销售:&nbsp;<input type="text"  name="fastFirstEmp" id="fastFirstEmp" style="width:80;" onchange="validateFastFirstEmp(this)" />'},
      		     { text: '分享:&nbsp;<input type="text"  name="fastFristShare" id="fastFristShare"  style="width:60;" onchange="validatePrice(this);validateFastFristShare(this)" />'},
      		     { text: '烫染:&nbsp;<input type="text"  name="fastSecondEmp" id="fastSecondEmp"  style="width:80;" onchange="validateFastSecondEmp(this)" />' },
	             { text: '备注:&nbsp;<input type="text"  name="fastMask" id="fastDyNo" style="width:250;" maxlength="200" onchange="validatefastMask(this)"  onblur="item_adddetial()"/>' }
	             	
	          ]
            });
            $("#insCardInfo").ligerButton(
	        {
	             text: '初始会员卡', width: 100,
		         click: function ()
		         {
		             initNewCardInfo();
		         }
	        });
	        
            
           $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取卡号', width: 80,
		         click: function ()
		         {
		             readCurCardInfo();
		         }
	         });
          	$("#pageloading").hide(); 
            f_selectNode();
   		}
   		catch(e){alert(e.message);}
    });
    
    function f_selectNode()
	{
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
	}
    //加载门店信息
	function compSelect(note)
    {
    	strCurCompId=note.data.id;
    	var param="strCurCompId="+strCurCompId;
     	var requestUrl ="cc017/loadMasterinfos.action"; 
		var responseMethod="loadMasterinfosMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
    }
   
   function loadMasterinfosMessage(request)
   {
       	try
        {
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMreturnproinfo!=null && responsetext.lsMreturnproinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMreturnproinfo,Total: responsetext.lsMreturnproinfo.length});
            	commoninfodivmaster.loadData(true);  
            	commoninfodivmaster.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
	   
	   		lsStaffinfo= responsetext.lsStaffinfo;
	   		commoninfodivdetialpro.columns[7].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
	   		commoninfodivdetialpro.columns[9].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
	   		commoninfodivchangepro.columns[4].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);
	   		commoninfodivchangepro.columns[6].editor.data=loadCommonDataGridByStaffInfo(lsStaffinfo);

	 	}
	 	catch(e){alert(e.message);}
    }
    
     function loadCommonDataGridByStaffInfo(lsStaffinfo)
		{
				try
				{
					var strJson = "";//'{ "name": "cxh", "sex": "man" }';
					var ccount=0;
					if(lsStaffinfo!=null && lsStaffinfo.length>0)
					{	
						for(var i=0;i<lsStaffinfo.length;i++)
						{
								ccount=ccount*1+1;
								if(ccount==1)
								{
									strJson=strJson+'[';
								}
								else
								{
									strJson=strJson+',';
								}
								strJson=strJson+'{ "choose":"'+lsStaffinfo[i].bstaffno+'", "text": "'+lsStaffinfo[i].bstaffno+'_'+lsStaffinfo[i].staffname+'"}';
							
						}
						if(strJson!="")
						{
							strJson=strJson+']';
							return JSON.parse(strJson);
						}
						return null;
						 
					}
				
				}catch(e){alert(e.message);}
		}
		
    function loadAutoStaff(curmanager,curWriteStaff)
	{	
		
		curmanager.setData(loadGridChooseByStaffInfo(lsStaffinfo,curWriteStaff));
		curmanager.selectBox.show();
		curEmpManger=curmanager;
	 }
   function loadAutoProject(curmanager,curWriteitemname)
	{	
		curmanager.setData(loadProjectControlDate_selectL(curWriteitemname));
		curmanager.selectBox.show();
		curitemManger=curmanager;
	}
	
	function loadProjectControlDate_selectL(curWriteItemNo)
	{
		
		try{
		var returnValue='';
		var key='';
		var value='';
		var projectcount=0;
		for(var i=0;i<parent.lsProjectinfo.length;i++)
		{
			if(projectcount*1==10)
			{
				break;
			}
			if(curWriteItemNo!="" 
			 && parent.lsProjectinfo[i].id.prjno.indexOf(curWriteItemNo)==-1
			 && checkNull(parent.lsProjectinfo[i].prjabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1
			 && checkNull(toPinyin(parent.lsProjectinfo[i].prjname)).indexOf(curWriteItemNo.toUpperCase())==-1)
			{
				continue;
			}
		   	key = parent.lsProjectinfo[i].id.prjno;
			value = parent.lsProjectinfo[i].prjname;
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			
			projectcount=projectcount*1+1;
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';	
			return 	JSON.parse(returnValue);
		}
		else
		{	
			return null;
		}
		}catch(e){alert(e.message);}
	}
	
  
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 strCurBillId=data.breturnbillid;
       			 var params = "strCurCompId="+data.breturncompid;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc017/loadCurMcardchangeinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								document.getElementById("lbBillId").innerHTML=checkNull(action.curMreturnproinfo.id.returnbillid);
								document.getElementById("returncompid").value=checkNull(action.curMreturnproinfo.id.returncompid);
								document.getElementById("returnbillid").value=checkNull(action.curMreturnproinfo.id.returnbillid);
								document.getElementById("returndate").value=checkNull(action.curMreturnproinfo.returndate);
								document.getElementById("returntime").value=checkNull(action.curMreturnproinfo.returntime);
								document.getElementById("returncardno").value=checkNull(action.curMreturnproinfo.returncardno);
								document.getElementById("cardvesting").value=checkNull(action.curMreturnproinfo.cardvesting);
								document.getElementById("cardtype").value=checkNull(action.curMreturnproinfo.cardtype);
								document.getElementById("cardtypename").value=checkNull(action.curMreturnproinfo.cardtypename);
								document.getElementById("membername").value=checkNull(action.curMreturnproinfo.membername);
								document.getElementById("memberphone").value=checkNull(action.curMreturnproinfo.memberphone);
								document.getElementById("curkeepamt").value=checkNull(action.curMreturnproinfo.curkeepamt);
								document.getElementById("curkeepproamt").value=checkNull(action.curMreturnproinfo.curkeepproamt);
								document.getElementById("cursendamt").value=checkNull(action.curMreturnproinfo.cursendamt);
								document.getElementById("curpointamt").value=checkNull(action.curMreturnproinfo.curpointamt);
								document.getElementById("changecardno").value=checkNull(action.curMreturnproinfo.changecardno);
								document.getElementById("opencardtype").value=checkNull(action.curMreturnproinfo.opencardtype);
								document.getElementById("opencardtypename").value=checkNull(action.curMreturnproinfo.opencardtypename);
								document.getElementById("opentotalamt").value=checkNull(action.curMreturnproinfo.opentotalamt);
								document.getElementById("returnkeeptotalamt").value=checkNull(action.curMreturnproinfo.returnkeeptotalamt);
								document.getElementById("changetotalamt").value=checkNull(action.curMreturnproinfo.changetotalamt);
								document.getElementById("costproamt").value=checkNull(action.curMreturnproinfo.costproamt);
								if(action.lsDreturnproinfoA!=null && action.lsDreturnproinfoA.length>0)
						   		{
					
						   			commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: action.lsDreturnproinfoA,Total: action.lsDreturnproinfoA.length});
					            	commoninfodivdetialpro.loadData(true);  
					            	commoninfodivdetialpro.select(0);          	
						   		}
						   		else
						   		{
						   			commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivdetialpro.loadData(true);  
					            	addProBackInfo();
						   		}
						   		
						   		if(action.lsDreturnproinfoB!=null && action.lsDreturnproinfoB.length>0)
						   		{
					
						   			commoninfodivchangepro.options.data=$.extend(true, {},{Rows: action.lsDreturnproinfoB,Total: action.lsDreturnproinfoB.length});
					            	commoninfodivchangepro.loadData(true);  
					            	commoninfodivchangepro.select(0);          	
						   		}
						   		else
						   		{
						   			commoninfodivchangepro.options.data=$.extend(true, {},{Rows: null,Total:0});
					            	commoninfodivchangepro.loadData(true);  
					            	addProChangeInfo();
						   		}
								
								if(document.getElementById("returncardno").value!="")
								{
									readPage(1);
								}
								else
								{
									readPage(0);
								}
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
     
     function readPage(billstate)
     {
     		
		if(billstate==0)
		{
			curpagestate=1;
     	}
       	if(billstate==1  )
       	{
       		curpagestate=3;
     	}
       
     }
     
     
  
   	
    function addProBackInfo()
   	{	
	   	if(commoninfodivdetialpro.rows.length*1>0 && checkNull(curRecord.changeproid)=="")
    	{
    			return ;
    	}
    	var detiallength=0;
    	var row = commoninfodivdetialpro.getSelectedRow();
    	if(commoninfodivdetialpro.rows.length*1==0)
    	{
    		detiallength=-1;
    	}
    	else
    	{
    		detiallength=commoninfodivdetialpro.rows.length*1-1;
    	}		
		commoninfodivdetialpro.addRow({ 
					changeproid:'',
					lastcount:0,
					lastamt:0,
					changeprocount:0,
					changeproamt:0,
					changebyaccountamt:0,
					firstsalerid:'',
					firstsalerinid:'',
					firstsaleamt:'',
					secondsalerid:'',
					secondsalerinid:'',
					secondsaleamt:''
			}, row, false);
		
		commoninfodivdetialpro.select(detiallength*1+1);
	
	
   	}
   	function  addProChangeInfo()
   	{
   		if(commoninfodivchangepro.rows.length*1>0 && checkNull(curCRecord.changeproid)=="")
    	{
    			return ;
    	}
    	var detiallength=0;
    	var row = commoninfodivchangepro.getSelectedRow();
    	if(commoninfodivchangepro.rows.length*1==0)
    	{
    		detiallength=-1;
    	}
    	else
    	{
    		detiallength=commoninfodivchangepro.rows.length*1-1;
    	}		
		commoninfodivchangepro.addRow({
					changeproid:'',
					lastcount:0,
					lastamt:0,
					changeprocount:0,
					changeproamt:0,
					changebyaccountamt:0,
					firstsalerid:'',
					firstsalerinid:'',
					firstsaleamt:'',
					secondsalerid:'',
					secondsalerinid:'',
					secondsaleamt:'', 
					changemark:''
		}, row, false);
		commoninfodivchangepro.select(detiallength*1+1);
		document.getElementById("fastProId").value="";
		document.getElementById("fastProCount").value="";
		document.getElementById("fastProAmt").value="";
		document.getElementById("fastFirstEmp").value="";
		document.getElementById("fastSecondEmp").value="";
		document.getElementById("fastFristShare").value=0;
		document.getElementById("fastMask").value="";
		document.getElementById("fastProId").select();
		document.getElementById("fastProId").focus();
   	}

  
   	function  searchCurRecord()
   	{
   		var strSearchContent=document.getElementById("strSearchContent").value;
   		if(strSearchContent=="" )
   		{	
			 $.ligerDialog.warn("请输入查询信息!");
     		 return;
     	}
     	else
     	{
     		var requestUrl ="cc017/searchBill.action";
	        var params="strCurCompId="+strCurCompId;
	        params=params+"&strSearchContent="+ strSearchContent;
			var responseMethod="searchBillMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
     	}
   	}
   	
   	function searchBillMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	if(responsetext.lsMreturnproinfo!=null && responsetext.lsMreturnproinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMreturnproinfo,Total: responsetext.lsMreturnproinfo.length});
            	commoninfodivmaster.loadData(true);  
            	commoninfodivmaster.select(0);          	
	   		}
	       	else
	       	{
	       		 $.ligerDialog.warn("没有查询到相应单据信息!");
		   		
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
         	  
  	 //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114)//F3
			{
				addProChangeInfo();
				window.event.keyCode = 505;
				window.event.returnValue=false;
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
		
		
		function deleteCurRecord()
	    {
	    	$.ligerDialog.confirm('确认删除当前选中行?', function (result)
			{
			    if( result==true)
	           	{
					commoninfodivchangepro.deleteSelectedRow();
					initChangeDetialGrid();
					document.getElementById("fastProId").value="";
					document.getElementById("fastProCount").value="";
					document.getElementById("fastProAmt").value="";
					document.getElementById("fastFirstEmp").value="";
					document.getElementById("fastSecondEmp").value="";
					document.getElementById("fastFristShare").value=0;
					document.getElementById("fastMask").value="";
					document.getElementById("fastProId").select();
					document.getElementById("fastProId").focus();
				}
			});  
	    	
	    }
		function  hotKeyEnter()
		{
		
			try
			{
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="fastProId")
				{
						validateProNo(document.activeElement);
				}
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}   
		
		function selectCall()
		{
			validateProNo(document.getElementById("fastProId"));
		}
		
		
		function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("returncardno").value=cardNo;
				validatecardno(document.getElementById("returncardno"));
	    	}
        } 
        
        function validatecardno(obj)
    	{
    	if(obj.value=="")
    	{
    		document.getElementById("cardvesting").value="";
    		document.getElementById("cardtype").value="";
    		document.getElementById("cardtypename").value="";
    		document.getElementById("membername").value="";
    		document.getElementById("memberphone").value="";
    		document.getElementById("curkeepamt").value="";
    		document.getElementById("curkeepproamt").value="";
    		document.getElementById("cursendamt").value="";
    		document.getElementById("curpointamt").value="";
    		return;
    	}
    	var requestUrl ="cc017/validateTkCardno.action";
		var params="strCurCompId="+document.getElementById("returncompid").value;
		params=params+"&strCurCardNo="+obj.value;
		var responseMethod="validateTkCardnoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
   
    function validateTkCardnoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		document.getElementById("cardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("cardtypename").value=responsetext.curCardinfo.cardtypeName;
	        		document.getElementById("membername").value=responsetext.curCardinfo.membername;
    				document.getElementById("memberphone").value=responsetext.curCardinfo.memberphone;
    				document.getElementById("curkeepamt").value=responsetext.curCardinfo.account2Amt;
    				document.getElementById("curkeepproamt").value=responsetext.curCardinfo.account4Amt;
    				document.getElementById("cardvesting").value=responsetext.curCardinfo.id.cardvesting;
    				document.getElementById("cursendamt").value=responsetext.curCardinfo.account6Amt;
    				document.getElementById("curpointamt").value=responsetext.curCardinfo.account3Amt;
    				if(responsetext.lsDreturnproinfoA!=null && responsetext.lsDreturnproinfoA.length>0)
					{
						commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: responsetext.lsDreturnproinfoA,Total: responsetext.lsDreturnproinfoA.length});
					    commoninfodivdetialpro.loadData(true);  
					    commoninfodivdetialpro.select(0);          	
					}
					else
					{
						 commoninfodivdetialpro.options.data=$.extend(true, {},{Rows: null,Total:0});
					     commoninfodivdetialpro.loadData(true);  
					     addProBackInfo();
					}  		
	        	}
	        	else
	        	{
	        		document.getElementById("cardvesting").value="";
    				document.getElementById("cardtype").value="";
    				document.getElementById("cardtypename").value="";
    				document.getElementById("membername").value="";
    				document.getElementById("memberphone").value="";
    				document.getElementById("curkeepamt").value="";
    				document.getElementById("curkeepproamt").value="";
    				document.getElementById("cursendamt").value="";
    				document.getElementById("curpointamt").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
        
        function validateNewCardNo(obj)
        {
        	if(obj.value=="")
        	{
        		document.getElementById("opencardtype").value="";
        		document.getElementById("opencardtypename").value="";
        	}
        	else
        	{
        		if(document.getElementById("returncardno").value=="")
        		{
        			$.ligerDialog.error("请读取退卡卡号!");
        			obj.value="";
        			document.getElementById("opencardtype").value="";
        			document.getElementById("opencardtypename").value="";
	        		return ;
        		}
        		var requestUrl ="cc017/validateNewCardno.action";
				var params="strCurCompId="+document.getElementById("returncompid").value;
				params=params+"&strCurCardNo="+obj.value;
				var responseMethod="validateNewCardnoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
        	}
        }
        
        
    
   
    function validateNewCardnoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		document.getElementById("opencardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("opencardtypename").value=responsetext.curCardinfo.cardtypeName;
	        	}
	        	else
	        	{
	        		document.getElementById("changecardno").value="";
        			document.getElementById("opencardtype").value="";
        			document.getElementById("opencardtypename").value="";
	        		$.ligerDialog.error(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    function initNewCardInfo()
    {
    	if(document.getElementById("opencardtype").value=="")
    	{
    		$.ligerDialog.error("请输入新卡卡号!");
    		return;
    	}
    	var selectProFlag=0;
    	for (var rowid in commoninfodivdetialpro.records)
		{
			var row =commoninfodivdetialpro.records[rowid];
			if(checkNull(row.returnflag)==true)
			{
				selectProFlag=1;
				break;
			}
		}
    	if(selectProFlag==0)
    	{
    		$.ligerDialog.error("请选择需要转入新卡的疗程!");
    		return;
    	}
    	 $.ligerDialog.confirm('请确认'+document.getElementById("changecardno").value+'卡已插入读卡器中!', function (result)
		 {
			if( result==true)
			{     
				var CardControl=parent.document.getElementById("CardCtrl");
				CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
				var inserCardno=CardControl.ReadCard();
				if(checkNull(inserCardno)!="" )
				{
					if( checkNull(inserCardno)==document.getElementById("returncardno").value)
					{
						$.ligerDialog.error("读卡器中的卡不能为退卡老卡!");
						return;
					}
				}
				var initflag=CardControl.WriteCard(document.getElementById("changecardno").value);
				if(initflag==0)
				{
					$.ligerDialog.error('会员卡初始化失败,请确认读卡器状态!');
					return;
				}
				else
				{
					for (var rowid in commoninfodivdetialpro.records)
					{
						var row =commoninfodivdetialpro.records[rowid];
						if(checkNull(row.returnflag)==true)
						{
							document.getElementById("opentotalamt").value=document.getElementById("opentotalamt").value*1+checkNull(row.lastamt)*1;	 
						}
					}
				}
			}
		});
    }
    
    
    
   	//验证抵用疗程次数
   	function validateCurCostCount(obj)
   	{
   		try
   		{
   			if(obj.value=="" || obj.value==0)
	   		{
	   			commoninfodivdetialpro.updateRow(curRecord,{changeprocount:'0',changeproamt: '0'});
	   			return;
	   		}
	   		var lastcount=ForDight(curRecord.lastcount*1,1);
	   		var lastamt=ForDight(curRecord.lastamt,1);
	   		if(ForDight(curRecord.lastcount,1)<ForDight(obj.value*1,1))
	   		{
	   			$.ligerDialog.warn("退换次数不能超过本疗程的剩余次数!");
	   			commoninfodivdetialpro.updateRow(curRecord,{changeprocount:'0',changeproamt: '0'});
	   			return;
	   		}
	   		var oneprice=ForDight(lastamt/lastcount,2);

	   		commoninfodivdetialpro.updateRow(curRecord,{changeproamt: ForDight(oneprice*1*obj.value*1,0)});
   		}
   		catch(e){alert(e.message);}
   		initDetialGrid();
   	}
   	
   	
	function validateFristSale(obj)
	{
		try
		{
		var curempValue="";
		if(curEmpManger!=null && curEmpManger.inputText.val()!="")
			curempValue=curEmpManger.inputText.val();
		else
			curempValue=obj.value;
		curEmpManger=null;
		if(curempValue!="")
		{
			if(curempValue!="" && curempValue.indexOf('_')==-1)
	    	{
	
	    		var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(curempValue==lsStaffinfo[i].bstaffno)
		    		{
		    			commoninfodivdetialpro.updateRow(curRecord,{firstsalerid: lsStaffinfo[i].bstaffno});
		    			exists=1;
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		commoninfodivdetialpro.updateRow(curRecord,{firstsalerid:''});
		    	}
		    }
		}
		}catch(e){alert(e.message);}
		initDetialGrid();
	}
	
	
	function validateTrShareAmt(obj)
	{
		if(obj.value=="")
		{
			commoninfodivdetialpro.updateRow(curRecord,{secondsaleamt:'0'});
			return ;
		}
		var firstsalerid=curRecord.firstsalerid;
		var firstsaleamt=curRecord.firstsaleamt;
		if(firstsalerid!="")
		{
			commoninfodivdetialpro.updateRow(curRecord,{secondsaleamt :firstsaleamt});
		}
		initDetialGrid();
	}
	
	

	
	function checkboxRender(rowdata, rowindex, value, column)
	{
	    var iconHtml = '<div class="chk-icon';
	    if (value) iconHtml += " chk-icon-selected";
	    iconHtml += '"';
	    iconHtml += ' rowid = "' + rowdata['__id'] + '"';
	    iconHtml += ' gridid = "' + this.id + '"';
	    iconHtml += ' columnname = "' + column.name + '"';
	    iconHtml += '></div>';
	    return iconHtml;
	}
	//是否类型的模拟复选框的点击事件
	$("div.chk-icon").live('click', function ()
	{
	    var grid = $.ligerui.get($(this).attr("gridid"));
	    var rowdata = grid.getRow($(this).attr("rowid"));
	    var columnname = $(this).attr("columnname");
	    var checked = rowdata[columnname];
	    
		if(checked!=true)
		{
			document.getElementById("returnkeeptotalamt").value=document.getElementById("returnkeeptotalamt").value*1-checkNull(rowdata.changeproamt)*1;
			grid.updateRow(rowdata,{changeprocount:0,changeproamt:0,changebyaccountamt:0,firstsalerid:'',firstsaleamt:'',secondsalerid:'',secondsaleamt:''});
			var column=grid.columns[5];
			grid.setCellEditing(rowdata, column, true);
			column=grid.columns[7];
			grid.setCellEditing(rowdata, column, true);
			column=grid.columns[8];
			grid.setCellEditing(rowdata, column, true);
			column=grid.columns[9];
			grid.setCellEditing(rowdata, column, true);
			
		}
		else
		{
			var column=grid.columns[5];
			grid.setCellEditing(rowdata, column, false);
			column=grid.columns[7];
			grid.setCellEditing(rowdata, column, false);
			column=grid.columns[8];
			grid.setCellEditing(rowdata, column, false);
			column=grid.columns[9];
			grid.setCellEditing(rowdata, column, false);
		}
	    grid.updateCell(columnname, !checked, rowdata);
	});
	
	

	function initDetialGrid()
	{
		document.getElementById("returnkeeptotalamt").value=0;
		for (var rowid in commoninfodivdetialpro.records)
		{
			var row =commoninfodivdetialpro.records[rowid];
			if(checkNull(row.returnflag)==true)
			{
				var column=commoninfodivdetialpro.columns[5];
				commoninfodivdetialpro.setCellEditing(row, column, true);
				column=commoninfodivdetialpro.columns[7];
				commoninfodivdetialpro.setCellEditing(row, column, true);
				column=commoninfodivdetialpro.columns[8];
				commoninfodivdetialpro.setCellEditing(row, column, true);
				column=commoninfodivdetialpro.columns[9];
				commoninfodivdetialpro.setCellEditing(row, column, true); 
			}  
			else
			{
				var column=commoninfodivdetialpro.columns[5];
				commoninfodivdetialpro.setCellEditing(row, column, false);
				column=commoninfodivdetialpro.columns[7];
				commoninfodivdetialpro.setCellEditing(row, column, false);
				column=commoninfodivdetialpro.columns[8];
				commoninfodivdetialpro.setCellEditing(row, column, false);
				column=commoninfodivdetialpro.columns[9];
				commoninfodivdetialpro.setCellEditing(row, column, false);   
			}	
			document.getElementById("returnkeeptotalamt").value=document.getElementById("returnkeeptotalamt").value*1+checkNull(row.changeproamt)*1;	 
		}
		document.getElementById("returnkeeptotalamt").value= ForDight(document.getElementById("returnkeeptotalamt").value*1-document.getElementById("changetotalamt").value*1-document.getElementById("costproamt").value*1,1);
		
	}
	
	
	function validateFastFirstEmp(obj)
	{
		if(obj.value=="")
		{
			commoninfodivchangepro.updateRow(curCRecord,{firstsalerid: "",firstsaleamt:0});
		}
		else
		{
				var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(obj.value==lsStaffinfo[i].bstaffno)
		    		{
		    			commoninfodivchangepro.updateRow(curCRecord,{firstsalerid: obj.value});
		    			exists=1;
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		$.ligerDialog.warn("第一销售不存在!");
		    		commoninfodivchangepro.updateRow(curCRecord,{firstsalerid: "",firstsaleamt:0});
		    	}
		}
	}
	
	function validateFastFristShare(obj)
	{
		if(checkNull(curCRecord.secondsalerid)!="")
		{
			commoninfodivchangepro.updateRow(curCRecord,{firstsaleamt:obj.value*1,secondsaleamt :obj.value*1});
		}
		else
		{
			commoninfodivchangepro.updateRow(curCRecord,{firstsaleamt:obj.value*1});
		}
	}
	function validateFastSecondEmp(obj)
	{
		if(obj.value=="")
		{
			commoninfodivchangepro.updateRow(curCRecord,{secondsalerid: "",secondsaleamt :0});
			
		}
		var firstsalerid=curCRecord.firstsalerid;
		var firstsaleamt=curCRecord.firstsaleamt;
		if(firstsalerid!="")
		{
				var exists=0;
		    	for(var i=0;i<lsStaffinfo.length;i++)
		    	{
		    		if(obj.value==lsStaffinfo[i].bstaffno 
		    		&& ( lsStaffinfo[i].position=="008" || lsStaffinfo[i].position=="00901" || lsStaffinfo[i].position=="00902" ||lsStaffinfo[i].position=="00903" || lsStaffinfo[i].position=="00904"))
		    		{
		    			commoninfodivchangepro.updateRow(curCRecord,{secondsalerid: obj.value,secondsaleamt :firstsaleamt});
		    			exists=1;
		    			
		    			break;
		    		}
		    			
		    	}
		    	if(exists==0)
		    	{
		    		$.ligerDialog.warn("第一烫染不存在!");
		    		document.getElementById("fastFirstEmp").focus();
	  				document.getElementById("fastFirstEmp").select();
		    		commoninfodivchangepro.updateRow(curCRecord,{secondsalerid: "",secondsaleamt :0});
		    	}
			
		}
		else
		{
			commoninfodivchangepro.updateRow(curCRecord,{secondsalerid: "",secondsaleamt :0});
		}
	}
	
	function validatefastMask(obj)
	{
		commoninfodivchangepro.updateRow(curCRecord,{changemark:obj.value});
	}
	function validateCCount(obj)
	{
		commoninfodivchangepro.updateRow(curCRecord,{changeprocount :obj.value});
	}
	function validateCAmt(obj)
	{
		commoninfodivchangepro.updateRow(curCRecord,{changeproamt :obj.value});
		initChangeDetialGrid();
	}
	
	function validateTCostAmt(obj)
	{
		initDetialGrid();
	}
	
	function validateProNo(obj)
		{
			if(document.getElementById("returncardno").value=="")
			{
				$.ligerDialog.error("请确认兑换的卡号!");
				return ;
			}
			if(obj.value=="")
			{
				return ;
			}
			else
			{
				var requestUrl ="cc017/validateItem.action"; 
				var responseMethod="validateItemMessage";
				var params="strCurItemId="+obj.value;;	
				params =params+ "&strCurCompId="+strCurCompId;
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		}
		
		function validateItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var curProjectinfo=responsetext.curProjectinfo;
		     	if(curProjectinfo==null)
		   	 	{
		     		$.ligerDialog.warn("输入的项目编码不存在!");
		     	}
				else
		  		{
		  			addProChangeInfo();
		  			commoninfodivchangepro.updateRow(curCRecord,{changeproid:curProjectinfo.id.prjno,changeproname:curProjectinfo.prjname,changeprocount:curProjectinfo.ysalecount,changeproamt:ForDight(curProjectinfo.ysaleprice*1,1)});
					document.getElementById("fastProCount").value=checkNull(curProjectinfo.ysalecount)*1;
					document.getElementById("fastProAmt").value=ForDight(checkNull(curProjectinfo.ysaleprice)*1,1);
				}
				initChangeDetialGrid();
		     }catch(e){alert(e.message);}
		
		}
		
		function initChangeDetialGrid()
		{
			document.getElementById("changetotalamt").value=0;
			for (var rowid in commoninfodivchangepro.records)
			{
				var row =commoninfodivchangepro.records[rowid];
				if(checkNull(row.changeproid)!="")	
					document.getElementById("changetotalamt").value=ForDight(document.getElementById("changetotalamt").value*1+checkNull(row.changeproamt)*1,1);	 
			}
			initDetialGrid();
		}
		
		function editCurRecord()
		{
			initChangeDetialGrid();
			if(document.getElementById("returncardno").value=="")
			{
				$.ligerDialog.error("请确认兑换的卡号!");
				return ;
			}
			if(document.getElementById("returnkeeptotalamt").value*1<0)
			{
				$.ligerDialog.error("返回储值账户的金额不能小于0!");
				return ;
			}
			if(document.getElementById("costproamt").value*1<0)
			{
				$.ligerDialog.error("扣除成本金额不能小于0!");
				return ;
			}
			var changecardflag=0;
			for (var rowid in commoninfodivdetialpro.records)
			{
				var row =commoninfodivdetialpro.records[rowid];
				if(checkNull(row.returnflag)==true)
				{
					changecardflag=1;
					break;
				}
			}
			if(changecardflag==1)
			{
				if(document.getElementById("changecardno").value==""
				 || document.getElementById("opentotalamt").value*1==0 )
				 {
				 		$.ligerDialog.error("勾选了转换新卡的疗程,请输入新开卡号并初始化新卡!");
						return ;
				 }
			}
			if(document.getElementById("returnkeeptotalamt").value*1==0
			&& document.getElementById("changetotalamt").value*1==0
			&& document.getElementById("costproamt").value*1==0
			&& document.getElementById("opentotalamt").value*1==0 )
			{
				$.ligerDialog.error("无兑换操作,无需保存!");
				return ;
			}
			
			if(curpagestate==3)
			{
				$.ligerDialog.error("该退疗程不是新增单,无需保存!");
				return ;
			}
			if(postState==1)
			{
				$.ligerDialog.error("正在保存中,请不要连续保存!");
				return ;
			}
			postState=1;
			var params="";
			var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
			queryStringTmp=queryStringTmp.replace(/\+/g," ");
			var params=queryStringTmp;
			
			var curjosnparam="";
			var needReplaceStr="";
			var strProJsonParam="";
			
			for (var rowid in commoninfodivdetialpro.records)
			{
				var row =commoninfodivdetialpro.records[rowid];
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strProJsonParam!="")
					strProJsonParam=strProJsonParam+",";
				strProJsonParam= strProJsonParam+curjosnparam;    		 
			}	
			if(strProJsonParam!="")
			{
					params = params+"&strJsonParam=["+strProJsonParam+"]";
			}
			
			curjosnparam="";
			needReplaceStr="";
			strProJsonParam="";
			for (var rowid in commoninfodivchangepro.records)
			{
				var row =commoninfodivchangepro.records[rowid];
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strProJsonParam!="")
					strProJsonParam=strProJsonParam+",";
				strProJsonParam= strProJsonParam+curjosnparam;    		 
			}	
			if(strProJsonParam!="")
			{
					params = params+"&strChangeJsonParam=["+strProJsonParam+"]";
			}
			$.ligerDialog.confirm('确认保存当前退疗程单?', function (result)
			{
			    if( result==true)
	           	{
	           		var requestUrl ="cc017/post.action";
					var responseMethod="editMessage";		
					showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
	           	}
	         });
			
		}
		
		 function editMessage(request)
     	{
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		$.ligerDialog.success("操作成功,请至会员卡资料中重新读卡");
	        		
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	postState=3;
	        	curpagestate=3;
	        	showDialogmanager.close();
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
        
         function loadProSeqno(strInput)
		 {
		 	
		 }
		 
        