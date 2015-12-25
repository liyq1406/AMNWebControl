
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var curCompid="";
   	var cc011layout=null;
   	var commoninfodivdetial=null;
   	var commoninfodivdetial_pq=null;
   	var commoninfodivdetial_Pro=null;
   	var commoninfodivdetial_Tm=null;
   	var commoninfodivdetial_Dy=null;
   	var commoninfodivdetial_cardInfo=null;
   	var commoninfodivdetial_payInfo =null;
   	var commoninfodivthirth = null;
   	var commoninfodivfouth = null;
   	var pageState=3;
   	var chooseItemData = [{ choose: '1', text: '项目' }, { choose: '2', text: '产品'}];
   	var servicetypeChangeData=JSON.parse(parent.loadCommonControlDate_select("FWLB",0));
   	var paymodeChangeData=null;
   	var curRowIndex=0;
   	var lsStaffSelectData=loadCommonDataGridByStaffInfo();
   	var curRecord=null;
   	var curEmpManger=null;
   	var curitemManger=null;
   	var addRecordFlag=0;  //0 允许新增 1 不允许新增
   	var lsDnointernalcardinfo = null;
   	var paramtotiaomacardinfo='';
   	var showDialogmanager=null;
   	var fastTab=null;
    var projectdilog=null;
    var projectdilogText="";
    var staffdilog=null;
    var staffdilogText="";
   	//初始化员工下拉表
   	function loadCommonDataGridByStaffInfo()
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
				{	
					for(var i=0;i<parent.StaffInfo.length;i++)
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
							strJson=strJson+'{ "choose":"'+parent.StaffInfo[i].bstaffno+'", "text": "'+parent.StaffInfo[i].bstaffno+'_'+parent.StaffInfo[i].staffname+'"}';
						
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
	
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc011layout= $("#cc011layout").ligerLayout({ rightWidth: 280,  allowBottomResize: false, allowLeftResize: false });
             $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取信息', width: 140,
		         click: function ()
		         {
		             editCurDetailInfo();
		         }
	         });
	       
	         $("#ratebutton").ligerButton(
	         {
	             text: '经理打折', width: 70,
		         click: function ()
		         {
		             //editCurDetailInfo();
		         }
	         }); 
           var obj = { width: '100%', height: 400, resizable:false,draggable:false };
        	obj.colModel = [
				{ title: "类型", width: 60,  dataIndx:"bcsinfotype" , align: "center",
				 	editor: function (ui) {  
				 	   var arr = [ '项目', '产品'];     
				       dropDownEditorItemType(ui,arr);     
				   }, getEditCellData: saveCell
				},
        		{ title: "项目/产品", width: 160, dataType: "string", align: "center", dataIndx:"csitemname" },
        		{ title: "支付方式", width:85, dataType: "string", align: "center" , dataIndx:"cspaymode"},
        		{ title: "单价", width: 50, dataType: "float", align: "center", dataIndx:"csdiscount" , editable: false},
        		{ title: "折扣", width: 50, dataType: "float", align: "center", dataIndx:"csdiscount" , editable: false},
        		{ title: "数量", width: 50, dataType: "float", align: "center", dataIndx:"csitemcount" },
        		{ title: "金额", width: 60, dataType: "float", align: "center", dataIndx:"csitemamt" , editable: false},
        		{ title: "大工", width: 80, dataType: "string", align: "center", dataIndx:"csfirstsaler"},
        		{ title: "类型", width: 60, dataType: "string", align: "center", dataIndx:"csfirsttype"},
        		{ title: "分享", width: 50, dataType: "float", align: "center", dataIndx:"csfirstshare"},
        		{ title: "中工", width: 80, dataType: "string", align: "center", dataIndx:"cssecondsaler"},
        		{ title: "类型", width: 60, dataType: "string", align: "center", dataIndx:"cssecondtype"},
        		{ title: "分享", width: 50, dataType: "float", align: "center", dataIndx:"cssecondshare"},
        		{ title: "小工", width: 80, dataType: "string", align: "center", dataIndx:"csthirdsaler"},
        		{ title: "类型", width: 60, dataType: "string", align: "center", dataIndx:"csthirdtype"},
        		{ title: "分享", width: 50, dataType: "float", align: "center", dataIndx:"csthirdshare"}];
        		obj.dataModel = { data: null };
				obj.cellSave=changeCell;
				obj.numberCell = false;     
				obj.flexWidth = true; 
				obj.scrollModel = {horizontal : false}; 
				obj.bottomVisible = false; 
				obj.title = ""; 
        	commoninfodivdetial_pq=$("#commoninfodivdetial_pq").pqGrid(obj);
        
        
        	 var dropDownEditorItemType = function (ui, arr) 
        	 {         
        	 		var $cell = ui.$cell, data = ui.data, rowIndx = ui.rowIndxPage, colIndx = ui.colIndx;         
        	 		var dataCell = $.trim(data[rowIndx][colIndx]); 
        	 		alert(dataCell);   
        			var str = "";
        	 		for (var i = 0; i < arr.length; i++) 
        	 		{
        	 			if (dataCell == arr[i])
        	 				str += "<option selected>" + arr[i] + "</option>";
        	 			else
        			 		str += "<option>" + arr[i] + "</option>";  
        			}
        	 		var $sel = $("<select>" + str + "</select>").appendTo($cell);
        	 }
        	var saveCell = function (ui) 
        	{
        		var $cell = ui.$cell;
        		alert($cell.children().val());
        		return $cell.children().val();
        	}
            
            commoninfodivdetial_cardInfo=$("#commoninfodivdetial_cardInfo").ligerGrid({
                columns: [
                { 
                	display: '账户信息', 	name: 'strcardinfo',  	width:195,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '200',
                height:'240',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false
            });
           commoninfodivdetial_payInfo=$("#commoninfodivdetial_payInfo").ligerGrid({
                columns: [
                { 
                	display: '支付信息', 	name: 'strpayinfo',  	width:195,align: 'left' }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '200',
                height:'240',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,fixedCellHeight:false
            });
            commoninfodivdetial_Pro=$("#commoninfodivdetial_Pro").ligerGrid({
                columns: [
                { display: '疗程编号', 	name: 'bprojectno',  	width:100,align: 'left' },
                { display: '疗程名称', 	name: 'bprojectname', 		width:153,align: 'left'},
	            { display: '余次', 	name: 'lastcount', 			width:40,align: 'left'},
	            { display: '消费次数', 	name: 'curcostcount', 			width:60,align: 'center',editor: { type: 'int' }},
	            { display: '备注', 		name: 'proremark', 		width:165,align: 'left'},
	            { 		name: 'bproseqno', 	hide:true, 		width:1},
	            { 		name: 'lastamt', 	hide:true, 		width:1}
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '560',
                height:'240',
                enabledEdit: true, checkbox:true,
                rownumbers: false,usePager: false,
                onCheckRow: f_onCheckRow,
                onCheckAllRow: f_onCheckAllRow,
                onAfterEdit: comCostProAfterEdit
            });
        	$("#toptoolbarCard").ligerToolBar({ items: [
      		    { text: '读卡', click: editCurDetailInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif' }
            ]
            });
            
										
									
      		$("#toptoolbar").ligerToolBar({ items: [
      		    {text: '流水号:&nbsp;<label id="lbBillId"></label>'},
                { text: '日期:&nbsp;<label id="lbCostDate"></label>' },
                { text: '时间:&nbsp;<label id="lbCostTime"></label>' },
                { text: '卡号:&nbsp;<font color="red"><label id="lbCardNo">&nbsp;&nbsp;&nbsp;散客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></font>'} ,
                { text: '<input type="radio" id="csersex0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csersex" value="0"/>女<input type="radio" id="csersex1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csersex" value="1"/>男'} ,
                { text: '<input type="radio" id="csertype0" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csertype" value="0"/>新客<input type="radio" id="csertype1" style="border:0px solid #99CC33; font-size:12px;line-height:14px; color:#333;"	name="curMconsumeinfo.csertype" value="1"/>老客'} 
                	 	
            ]
            });
            $("#toptoolbardetial").ligerToolBar({ items: [
      		     { text: '选定消费疗程', click: itemclick_proInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/right.gif' },
	             { line: true },
	             { text: '团购卡号:&nbsp;<input type="text"  name="curMconsumeinfo.tuangoucardno" id="tuangoucardno" maxlength="20" readonly="true" style="width:140;"  onchange="validateTuangoucardno(this)"/>' },
	             { line: true },
	             { text: '条码卡号:&nbsp;<input type="text"  name="curMconsumeinfo.tiaomacardno" id="tiaomacardno" maxlength="20" readonly="true" style="width:140;"  onchange="validateTiaomacardno(this)"/>' },
	             { line: true },
	             { text: '抵用券:&nbsp;<input type="text"  name="curMconsumeinfo.diyongcardno" id="diyongcardno" maxlength="20" readonly="true" style="width:140;" onchange="validateDiyongcardno(this)" />' },
	             { text: '额度:&nbsp;<label id="lbdyAmt"></label>' }
	               	 	
            ]
            });
           $("#toptoolbarserch").ligerToolBar({ items: [
           		{ text:' <input type="text" name="strSearchBillId" style="width:120" id="strSearchBillId"/>',click: searchCurRecord, img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'},
                { click: editCurRecord,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save-disabled.gif' },	 	
                { click: viewTicketReport,img: contextURL+'/common/ligerui/ligerUI/skins/icons/print.gif' }
            ]
            });
           //---------------------------------------------右侧面板营业分析 start
           	$("#fastTab").ligerTab();
            fastTab = $("#fastTab").ligerGetTabManager();
            
				$("#dataAnalysis").ligerTab({ onBeforeSelectTabItem: function (tabid)
	            {
	                dataAnalysis_before( tabid);
	            }, onAfterSelectTabItem: function (tabid)
	            {
	                dataAnalysis_after( tabid);
	            } 
	            });
	        //---------------------------------------------右侧面板营业分析 end
	        //-------------------------日历
				$(".datepicker").datePicker({
					inline:true,
					selectMultiple:false	
					
				}); 
				document.getElementById("lbfastItem3").innerHTML=eval("(parent.dSp076Price)");
				document.getElementById("lbfastItem4").innerHTML=eval("(parent.dSp077Price)");
				document.getElementById("lbfastItem5").innerHTML=eval("(parent.dSp078Price)");
				document.getElementById("lbfastItem6").innerHTML=eval("(parent.dSp079Price)");
          	$("#pageloading").hide(); 
          
         	addConsumeInfo();
         	addProRecord_cardInfo();
         	addProRecord();
         	addProRecord_payInfo();
   		}catch(e){alert(e.message);}
    });
    
    
    function changeCell()
    {
    }
              
    function loadAutoStaff(curmanager,curWriteStaff)
	{	
		curmanager.setData(loadGridChooseByStaffInfo(parent.StaffInfo,curWriteStaff));
		curmanager.selectBox.show();
		curEmpManger=curmanager;
	 }
    
    function loadAutoProject(curmanager,curWriteitemname)
	{	
		if(curRecord.bcsinfotype==1)
			curmanager.setData(parent.loadProjectControlDate_select(curWriteitemname));
		else
			curmanager.setData(parent.loadGoodsControlDate_select(curWriteitemname));
		curmanager.selectBox.show();
		curitemManger=curmanager;
	}
    
    
   function loadPayModeDate(strSalePayMode)
   {
   		
   }
   
    
     //默认新增消费单
    function addConsumeInfo()
    {
    	pageState=1;
     	var requestUrl ="cc011/add.action"; 
		var responseMethod="addMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
    }
   
   function addMessage(request)
   {
       		try
        	{
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
	   		{
	   			commoninfodivdetial_pq.pqGrid( { dataModel: { data: responsetext.lsDconsumeinfos } } );          	
	   		}
	   		else
	   		{
	   			commoninfodivdetial_pq.pqGrid( { dataModel: { data: null } } );
	   		}
	   		
	        loadCurMaster(responsetext.curMconsumeinfo);
			document.getElementById("strPayMode1").value="";
			document.getElementById("dPayAmt1").value=0;
			document.getElementById("strPayMode2").value="";
			document.getElementById("dPayAmt2").value=0;
			document.getElementById("strPayMode3").value="";
			document.getElementById("dPayAmt3").value=0;
			document.getElementById("strPayMode4").value="";
			document.getElementById("dPayAmt4").value=0;
			document.getElementById("strPayMode5").value="";
			document.getElementById("dPayAmt5").value=0;
			document.getElementById("strPayMode6").value="";
			document.getElementById("dPayAmt6").value=0;
			handPayList();
			loadPayModeDate(responsetext.strSalePayMode);
			addRecordFlag=0;
			
	   		}catch(e){alert(e.message);}
    }
    
    function loadCurMaster(curMaster)
    {
    
    	var rowslength=commoninfodivdetial_cardInfo.rows.length;
    	for (var rowid =0;rowid<rowslength*1;rowid++)
		{
			commoninfodivdetial_cardInfo.deleteRow(0); 
		}
		addProRecord_cardInfo("会员姓名:"+checkNull(curMaster.csname));
    	addProRecord_cardInfo("会员类型:"+checkNull(curMaster.cscardtypeName));
    			
    	document.getElementById("lbBillId").innerHTML=checkNull(curMaster.id.csbillid);
    	document.getElementById("lbCostDate").innerHTML=checkNull(curMaster.csdate);
    	document.getElementById("lbCostTime").innerHTML=checkNull(curMaster.csstarttime);
    	document.getElementById("lbCardNo").innerHTML=checkNull(curMaster.cscardno);
    	document.getElementById("lbCostTime").innerHTML=checkNull(curMaster.csstarttime);
    	document.getElementById("lbdyAmt").innerHTML=0;
    	document.getElementById("cscompid").value=checkNull(curMaster.id.cscompid);
    	document.getElementById("csbillid").value=checkNull(curMaster.id.csbillid);
    	document.getElementById("cscardno").value=checkNull(curMaster.cscardno);
    	document.getElementById("cscardtype").value=checkNull(curMaster.cscardtype);
    	document.getElementById("cscardtypeName").value=checkNull(curMaster.cscardtypeName);
    	document.getElementById("csname").value=checkNull(curMaster.csname);
    	document.getElementById("cscurkeepamt").value=checkNull(curMaster.cscurkeepamt);
    	document.getElementById("csdate").value=checkNull(curMaster.csdate);
    	document.getElementById("csstarttime").value=checkNull(curMaster.csstarttime); 
    	document.getElementById("cscurdepamt").value=checkNull(curMaster.cscurdepamt);
    	document.getElementById("csmanualno").value=checkNull(curMaster.csmanualno);
    	document.getElementById("tuangoucardno").value=checkNull(curMaster.tuangoucardno);
    	document.getElementById("tiaomacardno").value=checkNull(curMaster.tiaomacardno);
    	document.getElementById("diyongcardno").value=checkNull(curMaster.diyongcardno);
    	document.getElementById("cardpointamt").value=0;
    	document.getElementById("cardHomeSource").value="";
    	document.getElementById("diyongcardnoamt").value="0";
    	handleRadio("curMconsumeinfo.csersex",checkNull(curMaster.csersex));
    	handleRadio("curMconsumeinfo.csertype",checkNull(curMaster.csertype));
		if(pageState==1)
		{
			pageWriteState();
		}
		else
		{
			pageReadState();
		}
    }
    
    function pageWriteState()
    {
	    try
	    {
	    	//document.getElementById("cscardno").readOnly="";
	    	document.getElementById("csmanualno").readOnly="";
			document.getElementById("tuangoucardno").readOnly="";
			document.getElementById("tiaomacardno").readOnly="";
			document.getElementById("diyongcardno").readOnly="";
	    	//commoninfodivdetial.options.clickToEdit=true;
	    	commoninfodivdetial_pq.pqGrid( { editable: true } );          	
	    	commoninfodivdetial_Pro.options.clickToEdit=true;
    	}catch(e){alert(e.message);}
	}
    
    function pageReadState()
    {
    	try
	    {
	    	//document.getElementById("cscardno").readOnly="readOnly";
	    	document.getElementById("csmanualno").readOnly="readOnly";
	    	document.getElementById("tuangoucardno").readOnly="readOnly";
			document.getElementById("tiaomacardno").readOnly="readOnly";
			document.getElementById("diyongcardno").readOnly="readOnly";
			//commoninfodivdetial.options.clickToEdit=false;
			commoninfodivdetial_pq.pqGrid( { editable: false } );  
			commoninfodivdetial_Pro.options.clickToEdit=false;
    	}catch(e){alert(e.message);}
    }
    function editCurDetailInfo()
    {
    	
    	
    	var CardControl=parent.document.getElementById("CardCtrl");
		CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
		var cardNo=CardControl.ReadCard();
		if(cardNo!="")
		{
			document.getElementById("billflag").value=1;
			document.getElementById("cscardno").value=cardNo;
			document.getElementById("lbCardNo").innerHTML=cardNo;
    		validateCscardno(document.getElementById("cscardno"));
    	}
    	document.getElementById("billflag").value="1";
    }
    //-----------------------------------验证卡号
    function validateCscardno(obj)
    {
    	var rowslength=commoninfodivdetial_cardInfo.rows.length;
    	for (var rowid =0;rowid<rowslength*1;rowid++)
		{
			commoninfodivdetial_cardInfo.deleteRow(0); 
		}
    	if(obj.value=="")
    	{
    		document.getElementById("cscardtype").value="";
    		document.getElementById("cscardtypeName").value="";
    		document.getElementById("csname").value="";
    		document.getElementById("cscurkeepamt").value=0;
    		document.getElementById("cscurdepamt").value=0;
    		document.getElementById("cardsource").value="";
    		document.getElementById("cardHomeSource").value="";
    	}
    	else if(obj.value=="散客")
    	{
    		document.getElementById("cscardtype").value="";
    		document.getElementById("cscardtypeName").value="无";
    		document.getElementById("csname").value="散客";
    		document.getElementById("cscurkeepamt").value=0;
    		document.getElementById("cscurdepamt").value=0;
    		document.getElementById("cardsource").value="";
    		document.getElementById("cardHomeSource").value="";  
    		handleRadio("curMconsumeinfo.csertype",0); 
    		//initDetialGrid();
	   		commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
            commoninfodivdetial_Pro.loadData(true);  	
    	}
    	else
    	{
    		var requestUrl ="cc011/validateCscardno.action"; 
			var responseMethod="validateCscardnoMessage";	
			var params="strCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    
    function validateCscardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("cscardno").value="";
	   			document.getElementById("cscardtype").value="";
    			document.getElementById("cscardtypeName").value="";
    			document.getElementById("csname").value="";
    			document.getElementById("cscurkeepamt").value=0;
    			document.getElementById("cscurdepamt").value=0;
    			document.getElementById("cardsource").value="";
    			document.getElementById("cardHomeSource").value="";
	   		}
	   		else
	   		{
	   			document.getElementById("cscardtype").value=checkNull(responsetext.curCardinfo.cardtype);
    			document.getElementById("cscardtypeName").value=checkNull(responsetext.curCardinfo.cardtypeName);
    			addProRecord_cardInfo("会员姓名:"+checkNull(responsetext.curCardinfo.membername));
    			addProRecord_cardInfo("会员类型:"+checkNull(responsetext.curCardinfo.cardtypeName));
    			document.getElementById("csname").value=checkNull(responsetext.curCardinfo.membername);
    			if(checkNull(responsetext.curCardinfo.cardsource)==1)
    			{
    				document.getElementById("cardsource").value="收购卡";
    				document.getElementById("cscurkeepamt").value=checkNull(responsetext.curCardinfo.account5Amt);
    				document.getElementById("cscurdepamt").value=checkNull(responsetext.curCardinfo.account5debtAmt);
    				addProRecord_cardInfo("收购账户:"+checkNull(responsetext.curCardinfo.account5Amt));
    			}
    			else
    			{
    				document.getElementById("cardsource").value="内部卡";
    				document.getElementById("cscurkeepamt").value=checkNull(responsetext.curCardinfo.account2Amt);
    				document.getElementById("cscurdepamt").value=checkNull(responsetext.curCardinfo.account2debtAmt);
    				addProRecord_cardInfo("储值账户:"+checkNull(responsetext.curCardinfo.account2Amt));
    			}
    			
    			document.getElementById("cardpointamt").value=checkNull(responsetext.curCardinfo.account3Amt);
    			addProRecord_cardInfo("积分账户:"+checkNull(responsetext.curCardinfo.account3Amt));
    			document.getElementById("cardHomeSource").value=checkNull(responsetext.curCardinfo.id.cardvesting);
    			handleRadio("curMconsumeinfo.csersex",checkNull(responsetext.curCardinfo.membersex));
  
    			handleRadio("curMconsumeinfo.csertype",1);
    			if(responsetext.lsCardproaccount!=null && responsetext.lsCardproaccount.length>0)
		   		{
		   			commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: responsetext.lsCardproaccount,Total: responsetext.lsCardproaccount.length});
	            	commoninfodivdetial_Pro.loadData(true);            	
		   		}
		   		else
		   		{
		   			commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
	            	commoninfodivdetial_Pro.loadData(true);  
	            	addProRecord();
		   		}
	   		}
	   	
	   	}catch(e){alert(e.message);}
    }
    

//---------------------------------------------获得服务类型 项目或产品
function validateItemType(record)
{
	var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno:'',csitemcount:0,
     				csunitprice:0,csitemamt:0,csdiscount:0,cspaymode:'',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:''});  
    showTextByinfoType(rowdata,1);	
}
//---------------------------------------------验证项目编号
function validateItem(record)
{
	var curItemValue="";
	if(curitemManger!=null && curitemManger.inputText.val()!="")
		curItemValue=curitemManger.inputText.val();
	else
		curItemValue=curRecord.csitemname;
	curitemManger=null;	
	var requestUrl ="cc011/validateItem.action"; 
	var responseMethod="validateItemMessage";
	if(curItemValue=="" )
	{
		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
		return ;
	}
	if( curItemValue.indexOf('_')!=-1)
	{
		curItemValue=curItemValue.substring(0,curItemValue.indexOf('_'));
	}
	var params="itemType="+curRecord.bcsinfotype;
	var params=params+"&strCurItemId="+curItemValue;	
	var params=params+"&strCardType="+document.getElementById("cscardtype").value;	
	sendRequestForParams_p(requestUrl,responseMethod,params );
	
}

function validateItemMessage(request)
{
	try
	{
     var responsetext = eval("(" + request.responseText + ")");
     var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
     if(curRecord.bcsinfotype==1)
     {
     	var curProjectinfo=responsetext.curProjectinfo;
     	if(curProjectinfo==null)
     	{
     		$.ligerDialog.warn("输入的项目编码不存在!");
     		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
     	}
     	else
     	{
     		if(document.getElementById("cscardno").value=="散客")
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname:curProjectinfo.prjname,csitemno: curProjectinfo.id.prjno,csitemcount:1,
     				csunitprice:curProjectinfo.saleprice,csitemamt:curProjectinfo.saleprice,csdiscount:1,cspaymode:1});
     		}
     		else
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname:curProjectinfo.prjname,csitemno: curProjectinfo.id.prjno,csitemcount:1,
     				csunitprice:curProjectinfo.saleprice,csitemamt:ForDight(curProjectinfo.saleprice*1*dCostProjectRate*1,1),csdiscount:dCostProjectRate,cspaymode:4});
     		}
     		//commoninfodivdetial.updateCell(1,curProjectinfo.prjname,curRecord);
     	
     	}
     }
     else
     {
     	var curGoodsinfo=responsetext.curGoodsinfo;
     	if(curGoodsinfo==null)
     	{
     		$.ligerDialog.warn("输入的产品编码不存在!");
     		commoninfodivdetial.updateRow(curRecord,{csitemname:'',csitemno: ''});
     	}
     	else
     	{
     		if(document.getElementById("cscardno").value=="散客")
     		{
     				commoninfodivdetial.updateRow(curRecord,{csitemname: curGoodsinfo.goodsname,csitemno: curGoodsinfo.id.goodsno,csitemcount:1,
     				csunitprice: curGoodsinfo.standprice,csitemamt: curGoodsinfo.standprice,csdiscount:1,cspaymode:1});
     		}
     		else
     		{
     				
     				commoninfodivdetial.updateRow(curRecord,{csitemname: curGoodsinfo.goodsname,csitemno: curGoodsinfo.id.goodsno,csitemcount:1,
     				csunitprice: curGoodsinfo.standprice,csitemamt: curGoodsinfo.standprice,csdiscount:4,cspaymode:1});
     		}
     	
     	}
     }
     handPayList();
     }catch(e){alert(e.message);}
     showTextByinfoType(curRecord,1);
}
  //----------------------------------------------------验证分享员工Start 
function validateFristSaleType(obj)
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
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{csfirstsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{csfirstsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{csfirsttype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{csfirsttype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{csfirsttype:''});
	}
	//initDetialGrid();
}
function validateSecondSaleType(obj)
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
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{cssecondsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{cssecondsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondtype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{cssecondtype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{cssecondtype:''});
	}
	//initDetialGrid();
}
function validateThirdSaleType(obj)
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
	    	for(var i=0;i<parent.StaffInfo.length;i++)
	    	{
	    		if(curempValue==parent.StaffInfo[i].bstaffno)
	    		{
	    			commoninfodivdetial.updateRow(curRecord,{csthirdsaler: parent.StaffInfo[i].bstaffno});
	    			exists=1;
	    			break;
	    		}
	    			
	    	}
	    	if(exists==0)
	    	{
	    		commoninfodivdetial.updateRow(curRecord,{csthirdsaler:''});
	    	}
	    }
		if(curRecord.bcsinfotype==1)
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdtype:2});
		}
		else
		{
			commoninfodivdetial.updateRow(curRecord,{csthirdtype:""});
		}
	}
	else
	{
		commoninfodivdetial.updateRow(curRecord,{csthirdtype:''});
	}
	//initDetialGrid();
}
  //----------------------------------------------------验证分享员工End 
//------------------------------------------------------验证分享金额Start
function validateFristSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("大工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{csfirstshare:""});
	}
}
function validateSecondSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("中工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{cssecondshare:""});
	}
}
function validateThirdSaleShare(obj)
{
	if(obj.value!="" && obj.value*1!=0 && obj.value>curRecord.csitemamt)
	{
		$.ligerDialog.warn("中工的分享金额超过了   "+curRecord.csitemname+"   的总金额!");
		commoninfodivdetial.updateRow(curRecord,{csthirdshare:""});
	}
}
//------------------------------------------------------验证分享金额End
//------------------------------------------------------验证数量Start
function validateCostCount(obj)
{
	var costPrice=checkNull(curRecord.csunitprice)*1;
	var costdiscount=checkNull(curRecord.csdiscount)*1;
	commoninfodivdetial.updateRow(curRecord,{csitemamt:ForDight(checkNull(obj.value)*1*costPrice*costdiscount,1)});
	handPayList();
}
//------------------------------------------------------验证数量End
//------------------------------------------------------验证金额Start
function validateCostAmt(obj)
{
	handPayList();
}
//------------------------------------------------------验证金额End
//------------------------------------------------------验证支付方式Start
function validatePaycode(obj)
{
	var curItem=checkNull(curRecord.csitemname);
	if(obj.value!="" && curItem=="")
	{
		commoninfodivdetial.updateRow(curRecord,{cspaymode: ""});
		$.ligerDialog.warn("选择支付方式前请先确定消费项目 !");
	}
	if(obj.value=="11" || obj.value=="12" || obj.value=="13")
	{
		commoninfodivdetial.updateRow(curRecord,{cspaymode: ""});
		$.ligerDialog.warn("该支付方式不能手动选择,请重新输入!");
	}
	handPayList();
}

function handPayList()
{
	var paycode1="";
	var payamt1=0;
	var paycode2="";
	var payamt2=0;
	var paycode3="";
	var payamt3=0;
	var paycode4="";
	var payamt4=0;
	var paycode5="";
	var payamt5=0;
	var paycode6="";
	var payamt6=0;
	var curPaycode="";
	var curPayamt=0;
	var curProjectAmt=0;
	var curGoodsAmt=0;
	var totalCash=0;
	/*for (var rowid in commoninfodivdetial.records)
	{
		var row =commoninfodivdetial.records[rowid]; 
		curPaycode=checkNull(row.cspaymode);
		curPayamt=checkNull(row.csitemamt)*1;
		if(checkNull(row.cspaymode)=="1")
		{
			totalCash=totalCash*1+curPayamt*1;
		}
		if(checkNull(row.csitemname)!="" && curPaycode!="" && curPayamt!=0)
		{
			if(paycode1=="" || paycode1==curPaycode)
			{
				paycode1=curPaycode;
				payamt1=payamt1*1+curPayamt;
			}
			else if( paycode2=="" || paycode2==curPaycode )
			{
				paycode2=curPaycode;
				payamt2=payamt2*1+curPayamt;
			}
			else if( paycode3=="" || paycode3==curPaycode )
			{
				paycode3=curPaycode;
				payamt3=payamt3*1+curPayamt;
			}
			else if( paycode4=="" || paycode4==curPaycode )
			{
				paycode4=curPaycode;
				payamt4=payamt4*1+curPayamt;
			}
			else if( paycode5=="" || paycode5==curPaycode )
			{
				paycode5=curPaycode;
				payamt5=payamt5*1+curPayamt;
			}
			else if( paycode6=="" || paycode6==curPaycode )
			{
				paycode6=curPaycode;
				payamt6=payamt6*1+curPayamt;
			}
			if(checkNull(row.bcsinfotype)==1)
			 	curProjectAmt=curProjectAmt*1+curPayamt*1;
			 else
			 	curGoodsAmt=curGoodsAmt*1+curPayamt*1;
		}
	}*/
	var rowslength=commoninfodivdetial_payInfo.rows.length;
    for (var rowid =0;rowid<rowslength*1;rowid++)
	{
			commoninfodivdetial_payInfo.deleteRow(0); 
	}
	//第一支付
	if(paycode1!="")
	{
		//document.getElementById("paycode1_div").style.display="block";
		//document.getElementById("paycode1_img").src=contextURL+"/common/funtionimage/paycode"+paycode1+".jpg"
		//document.getElementById("payamt1_lb").innerHTML=ForDight(payamt1*1,1);
		document.getElementById("strPayMode1").value=paycode1;
		document.getElementById("dPayAmt1").value=payamt1;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode1)+":"+ForDight(payamt1*1,1));
	}
	else
	{
		//document.getElementById("paycode1_div").style.display="none";
		//document.getElementById("paycode1_img").src=contextURL+"/common/funtionimage/paycode"+paycode1+".jpg"
		//document.getElementById("payamt1_lb").innerHTML=0;
		document.getElementById("strPayMode1").value="";
		document.getElementById("dPayAmt1").value=0;
	}
	//第二支付
	if(paycode2!="")
	{
		//document.getElementById("paycode2_div").style.display="block";
		//document.getElementById("paycode2_img").src=contextURL+"/common/funtionimage/paycode"+paycode2+".jpg"
		//document.getElementById("payamt2_lb").innerHTML=ForDight(payamt2*1,1);
		document.getElementById("strPayMode2").value=paycode2;
		document.getElementById("dPayAmt2").value=payamt2;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode2)+":"+ForDight(payamt2*1,1));
	}
	else
	{
		//document.getElementById("paycode2_div").style.display="none";
		//document.getElementById("paycode2_img").src=contextURL+"/common/funtionimage/paycode"+paycode2+".jpg"
		//document.getElementById("payamt2_lb").innerHTML=0;
		document.getElementById("strPayMode2").value="";
		document.getElementById("dPayAmt2").value=0;
	}
	//第三支付
	if(paycode3!="")
	{
		//document.getElementById("paycode3_div").style.display="block";
		//document.getElementById("paycode3_img").src=contextURL+"/common/funtionimage/paycode"+paycode3+".jpg"
		//document.getElementById("payamt3_lb").innerHTML=ForDight(payamt3*1,1);
		document.getElementById("strPayMode3").value=paycode3;
		document.getElementById("dPayAmt3").value=payamt3;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode3)+":"+ForDight(payamt3*1,1));
	}
	else
	{
		//document.getElementById("paycode3_div").style.display="none";
		//document.getElementById("paycode3_img").src=contextURL+"/common/funtionimage/paycode"+paycode3+".jpg"
		//document.getElementById("payamt3_lb").innerHTML=0;
		document.getElementById("strPayMode3").value="";
		document.getElementById("dPayAmt3").value=0;
	}
	//第四支付
	if(paycode4!="")
	{
		//document.getElementById("paycode4_div").style.display="block";
		//document.getElementById("paycode4_img").src=contextURL+"/common/funtionimage/paycode"+paycode4+".jpg"
		//document.getElementById("payamt4_lb").innerHTML=ForDight(payamt4*1,1);
		document.getElementById("strPayMode4").value=paycode4;
		document.getElementById("dPayAmt4").value=payamt4;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode4)+":"+ForDight(payamt4*1,1));
	}
	else
	{
		//document.getElementById("paycode4_div").style.display="none";
		//document.getElementById("paycode4_img").src=contextURL+"/common/funtionimage/paycode"+paycode4+".jpg"
		//document.getElementById("payamt4_lb").innerHTML=0;
		document.getElementById("strPayMode4").value="";
		document.getElementById("dPayAmt4").value=0;
	}
	//第五支付
	if(paycode5!="")
	{
		//document.getElementById("paycode5_div").style.display="block";
		//document.getElementById("paycode5_img").src=contextURL+"/common/funtionimage/paycode"+paycode5+".jpg"
		//document.getElementById("payamt5_lb").innerHTML=ForDight(payamt5*1,1);
		document.getElementById("strPayMode5").value=paycode5;
		document.getElementById("dPayAmt5").value=payamt5;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode5)+":"+ForDight(payamt5*1,1));
	}
	else
	{
		//document.getElementById("paycode5_div").style.display="none";
		//document.getElementById("paycode5_img").src=contextURL+"/common/funtionimage/paycode"+paycode5+".jpg"
		//document.getElementById("payamt5_lb").innerHTML=0;
		document.getElementById("strPayMode5").value="";
		document.getElementById("dPayAmt5").value=0;
	}
	//第六支付
	if(paycode6!="")
	{
		//document.getElementById("paycode6_div").style.display="block";
		//document.getElementById("paycode6_img").src=contextURL+"/common/funtionimage/paycode"+paycode6+".jpg"
		//document.getElementById("payamt6_lb").innerHTML=ForDight(payamt6*1,1);
		document.getElementById("strPayMode6").value=paycode6;
		document.getElementById("dPayAmt6").value=payamt6;
		addProRecord_payInfo(parent.loadCommonControlValue("ZFFS",0,paycode6)+":"+ForDight(payamt6*1,1));
	}
	else
	{
		//document.getElementById("paycode6_div").style.display="none";
		//document.getElementById("paycode6_img").src=contextURL+"/common/funtionimage/paycode"+paycode6+".jpg"
		//document.getElementById("payamt6_lb").innerHTML=0;
		document.getElementById("strPayMode6").value="";
		document.getElementById("dPayAmt6").value=0;
	}
	addProRecord_payInfo("");
	addProRecord_payInfo("<font color=\"red\">项目总额</font>:&nbsp;&nbsp;&nbsp;&nbsp;"+ForDight(curProjectAmt*1,1));
	addProRecord_payInfo("<font color=\"red\">产品总额:</font>&nbsp;&nbsp;&nbsp;&nbsp;"+ForDight(curGoodsAmt*1,1));
	addProRecord_payInfo("");
	addProRecord_payInfo("<font color=\"blue\" size=\"4\"><b>应收现金:</font>&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"4\">"+ForDight(totalCash*1,1))+"</font></b>";
	
	//document.getElementById("dProjectAmt").innerHTML=ForDight(curProjectAmt*1,1);
	//document.getElementById("dGoodsAmt").innerHTML=ForDight(curGoodsAmt*1,1);
	//document.getElementById("dTotalsAmt").innerHTML=ForDight(curGoodsAmt*1+curProjectAmt*1,1);
	
}
//------------------------------------------------------验证支付方式End
//------------------------------------------------------获取疗程明细
	function f_onCheckAllRow(checked)
    {
            for (var rowid in commoninfodivdetial_Pro.records)
            {
                if(checked)
                    addCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno']);
                else
                    removeCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno']);
            }
    }

  	function f_onCheckRow(checked, data)
  	{
       	if (checked) 
       	{
          	addCheckedBcodekey(data.bproseqno);
      	}
	 	else 
	 	{
         	removeCheckedBcodekey(data.bproseqno);
  		}
	}
   	var checkedBcodekey= [];
        
   	function findCheckedBcodekey(bproseqno)
   	{
    	for(var i =0;i<checkedBcodekey.length;i++)
       	{
       		if(checkedBcodekey[i] == bproseqno) return i;
       	}
     	return -1;
  	}
    function addCheckedBcodekey(bproseqno)
    {
     	if(findCheckedBcodekey(bproseqno) == -1)
         	checkedBcodekey.push(bproseqno);
    }
    function removeCheckedBcodekey(bproseqno)
    {
    	var i = findCheckedBcodekey(bproseqno);
  		if(i==-1) return;
    		checkedBcodekey.splice(i,1);
    }
   	function itemclick_proInfo(item)
    {
    	try
    	{
    		var detiallength=commoninfodivdetial.rows.length*1;
	    	for (var rowid in commoninfodivdetial_Pro.records)
			{
				if(findCheckedBcodekey(commoninfodivdetial_Pro.records[rowid]['bproseqno'])!=-1)
				{	
					var row =commoninfodivdetial_Pro.records[rowid]; 
					if(checkNull(row.curcostcount)=="" || row.curcostcount==0)
					{
						continue;
					}
					if(detiallength==0)
					{
						addRecord();
						detiallength=detiallength+1;
					}
					else if( commoninfodivdetial.getRow(0)['csitemname']!="")
					{
						addRecord();
						detiallength=detiallength+1;
					}
					var curlastcount=ForDight(row.lastcount,1);
					var curlastamt=ForDight(row.lastamt,1);
					var curprice=ForDight(curlastamt/curlastcount,1)
					var curcostamt=ForDight(curprice*1*row.curcostcount*1,1);
					curRecord=commoninfodivdetial.getRow(detiallength-1);
					var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname: row.bprojectname,csitemno:row.bprojectno,csitemcount:ForDight(row.curcostcount,1),
     				csunitprice:curprice,csitemamt:curcostamt,csdiscount:1,cspaymode:'9',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:'',csproseqno:row.bproseqno});  
   	 				
				}
			}
			var detialprolen=commoninfodivdetial_Pro.rows.length*1;
			for (var i=0;i<detialprolen;i++)
			{
				if(findCheckedBcodekey(commoninfodivdetial_Pro.getRow(i)['bproseqno'])!=-1)
				{
					if(checkNull(commoninfodivdetial_Pro.getRow(i)['curcostcount'])=="" || checkNull(commoninfodivdetial_Pro.getRow(i)['curcostcount'])==0)
					{
						continue;
					}
					commoninfodivdetial_Pro.deleteRow(i);
					detialprolen=detialprolen-1;
				}
			}
			initDetialGrid();
			handPayList();
		}catch(e){alert(e.message);}
    }    
    
      
    function comCostProAfterEdit(e)
	{
		if(e.record.curcostcount!="" && e.record.lastcount *1 < e.record.curcostcount)
		{
			$.ligerDialog.warn("当次疗程消耗次数不能超过剩余次数!");
			commoninfodivdetial_Pro.updateRow(e.record,{curcostcount: 0}); 
		}
		
		

	}



function initDetialGrid()
{
	/*for (var rowid in commoninfodivdetial.records)
	{
		var row =commoninfodivdetial.records[rowid];
		if(checkNull(row.cspaymode)==9 || checkNull(row.cspaymode)==16 || checkNull(row.cspaymode)==13 || checkNull(row.cspaymode)==11)
			showTextByinfoType(row,2);     
		else
			showTextByinfoType(row,1);   		 
	}*/
}
function comsumAfterEdit(e)
{

	initDetialGrid();
	
}

function comsumbeforeEdit(e)
{
	
}
function showTextByinfoType(rowdata,readType)
{
	try
	{
	return;
	var column  =null ;

	if(rowdata.bcsinfotype==1)
	{
		column=commoninfodivdetial.columns[9];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		
		/*column=commoninfodivdetial.columns[9];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.toggleCol(column, false);*/

	}
	else
	{
	
		/*column=commoninfodivdetial.columns[9];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[12];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[15];
		commoninfodivdetial.toggleCol(column, false);
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.toggleCol(column, true);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.toggleCol(column, true);*/
		column=commoninfodivdetial.columns[8];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[11];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		column=commoninfodivdetial.columns[14];
		commoninfodivdetial.setCellEditing(rowdata, column, true);
		
	}
	
		if(readType==2)//项目,支付方式,数量金额屏蔽
		{
			column=commoninfodivdetial.columns[0];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[1];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[2];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[5];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
			column=commoninfodivdetial.columns[6];
			commoninfodivdetial.setCellEditing(rowdata, column, true);
		}
	}
	catch(e){alert(e.message);}
}

//-----------------------------------项目明细按钮
    function itemclick_serviceInfo(item)
    {
    	try
    	{
	    	if(item.text=="增加")
		    {
		       addRecord();
		    }
		    else if(item.text=="删除")
		    {
		       deleteCurRecord();
		    } 	
		       
    	}
    	catch(e){alert(e.message);}
    }
    
    function addRecord()
    {
    		if(addRecordFlag==1)
    		{
    			$.ligerDialog.warn("明细不可新增,请确认!");
    			return ;
    		}
    		var row = commoninfodivdetial.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({ 
				bcsinfotype: "1",
				csitemno:"",
				csitemunit:"",
				csitemcount:0,
				csunitprice:0,
				csdiscount:0,
				csdisprice:0,
				csitemamt:0,
				cspaymode:"",
				csfirstsaler:"",
				csfirsttype:"",
				csfirstinid:"",
				csfirstshare:"",
				cssecondsaler:"",
				cssecondtype:"",
				cssecondinid:"",
				cssecondshare:"",
				csthirdsaler:"",
				csthirdtype:"",
				csthirdinid:"",
				csthirdshare:"",
				csitemname: "" ,
				csproseqno:""              
				}, row, false);
		initDetialGrid();
		curEmpManger=null;
		curitemManger=null;
    }
    
    function addProRecord()
    {
    		var row = commoninfodivdetial_Pro.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_Pro.addRow({ 
				          
			}, row, false);
    }
    function addProRecord_cardInfo(strInfo)
    {
    		var row = commoninfodivdetial_cardInfo.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_cardInfo.addRow({ 
				    strcardinfo: strInfo       
			}, row, false);
    }
    
    function addProRecord_payInfo(strInfo)
    {
    		var row = commoninfodivdetial_payInfo.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivdetial_payInfo.addRow({ 
				    strpayinfo: strInfo       
			}, row, false);
    }
   
    function deleteCurRecord()
    {
    	$.ligerDialog.confirm('确认删除当前选中行?', function (result)
		{
		    if( result==true)
           	{
				commoninfodivdetial.deleteSelectedRow();
			}
		});  
    	
    }
    
    //-----------------------------------保存消费信息
    function editCurRecord()
    {
    	if(parent.hasFunctionRights( "CC011",  "UR_POST")!=true)
        {
        	$.ligerDialog.warn("该用户没有修改权限,请确认!");
        	return;
        }
   		if(pageState==3)
   		{
   			$.ligerDialog.warn("非新增单据,不可保存!");
   			return;
   		}
   		else
   		{
   			
   			$.ligerDialog.confirm('确认保存当前消费单?', function (result)
			{
			    if( result==true)
	           	{
	   				var queryStringTmp=$('#consumCenterForm').serialize();//serialize('#detailForm');
	
					var requestUrl ="cc011/post.action";
					var params=queryStringTmp;
					var strJsonParam_five="";
					var curjosnparam ="";
					var needReplaceStr="";
					var keepPayamt=0;
					var diyongPayamt=0;
					var jifenPayamt=0;
					for (var rowid in commoninfodivdetial.records)
					{
						 	var row =commoninfodivdetial.records[rowid];
						 	if(row['cspaymode']=="4" || row['cspaymode']=='5')
						 	{
						 		keepPayamt=keepPayamt*1+row['csitemamt']*1;
						 	}
						 	else if(row['cspaymode']=="12" )
						 	{
						 		diyongPayamt=diyongPayamt*1+row['csitemamt']*1;
						 	}
						 	else if(row['cspaymode']=="7" )
						 	{
						 		jifenPayamt=jifenPayamt*1+row['csitemamt']*1;
						 	}
						 	curjosnparam=JSON.stringify(row);
							if(curjosnparam.indexOf("_id")>-1)
						  	{
						     	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
						      	curjosnparam=curjosnparam.replace(needReplaceStr,"");
						    }	            		   
						    if(strJsonParam_five!="")
						      	strJsonParam_five=strJsonParam_five+",";
						    strJsonParam_five= strJsonParam_five+curjosnparam;        		 
					 }	
					 if(ForDight(keepPayamt*1,1)>ForDight(document.getElementById("cscurkeepamt").value*1,1))
					 {
					 	$.ligerDialog.warn("消耗账户金额不能超过现有账户余额!");
					 	return ;
					 }
					 if(ForDight(jifenPayamt*1,1)>ForDight(document.getElementById("cardpointamt").value*1,1))
					 {
					 	$.ligerDialog.warn("消耗积分金额不能超过现有积分余额!");
					 	return ;
					 }
					 if(ForDight(diyongPayamt*1,1)>ForDight(document.getElementById("diyongcardnoamt").value*1,1))
					 {
					 	$.ligerDialog.warn("消耗抵用券金额不能超过现有抵用券额度!");
					 	return ;
					 }
					 if(strJsonParam_five!="")
					 {
					 	 params = params+"&strJsonParam=["+strJsonParam_five+"]";
					 }
					 if(paramtotiaomacardinfo!="")
					 {
					 	 params = params+"&paramtotiaomacardinfo=["+paramtotiaomacardinfo+"]";
					 }
					 var responseMethod="editMessage";		
					 showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
					 sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
			}); 
   		}
    }
    
     function editMessage(request)
     {
    		
        	try
			{
				showDialogmanager.close();
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 $.ligerDialog.success("保存成功!");
	        		 viewTicketReport();
	        		 addConsumeInfo();
	        	}
	        	else
	        	{
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
   
   	//刷新界面
   	function freshCurRecord()
   	{
   		addConsumeInfo();
   	}
	//查询单据
	function searchCurRecord()
	{
		if(document.getElementById("strSearchBillId").value!="")
		{
			var requestUrl ="cc011/searchCurRecord.action"; 
			var responseMethod="searchCurRecordMessage";	
			var params="strSearchBillId="+document.getElementById("strSearchBillId").value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
	}
	function searchCurRecordMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	commoninfodivdetial_Pro.options.data=$.extend(true, {},{Rows: null,Total:0});
	            commoninfodivdetial_Pro.loadData(true);  
	            addProRecord();
	        	var curMconsumeinfo=responsetext.curMconsumeinfo;
	        	if(curMconsumeinfo==null)
	        	{	        		 
	        		 $.ligerDialog.success("无相关数据!");
	        		/*commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivdetial.loadData(true);  */
	        	}
	        	else
	        	{
	        		pageState=3;
	        		loadCurMaster(responsetext.curMconsumeinfo);
	        		/*if(responsetext.lsDconsumeinfos!=null && responsetext.lsDconsumeinfos.length>0)
			   		{
			   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsDconsumeinfos,Total: responsetext.lsDconsumeinfos.length});
		            	commoninfodivdetial.loadData(true);            	
			   		}
			   		commoninfodivdetial.options.clickToEdit=false;*/
			   		commoninfodivdetial_pq.pqGrid( { dataModel: { data: responsetext.lsDconsumeinfos } } );          	
			   		commoninfodivdetial_pq.pqGrid( { editable:false } );          	
			   		handPayList();
			        addRecordFlag=1;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function validateDiyongcardno(obj)
      {
      		if(obj.value=="")
	      	{
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateDiyongcardno.action"; 
			var responseMethod="validateDiyongcardnoMessage";	
			var params="strDiYongCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
      function validateDiyongcardnoMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		lsDnointernalcardinfo=responsetext.lsDnointernalcardinfo;
	        		if(lsDnointernalcardinfo!=null && lsDnointernalcardinfo.length>0)
	        		{
	        			document.getElementById("diyongcardnoamt").value=0;
	        			document.getElementById("lbdyAmt").innerHTML=0;
	        			$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showDiyongCardItems.jsp', title:'项目抵用券疗程明细',height: 500,width: 650, buttons: [ { text: '确定', onclick: function (item, dialog) { loadDiyongCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
					}
					else
					{
						document.getElementById("diyongcardnoamt").value=ForDight(responsetext.DDiyongAmt,1);
						document.getElementById("lbdyAmt").innerHTML=ForDight(responsetext.DDiyongAmt,1);
					}
	        	}
	        	else
	        	{
	        		document.getElementById("diyongcardno").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      function validateTiaomacardno(obj)
      {	
      		if(obj.value=="")
	      	{
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateTiaoMaCardNo.action"; 
			var responseMethod="validateTiaoMaCardNoMessage";	
			var params="strTiaoMaCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
      function validateTiaoMaCardNoMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		lsDnointernalcardinfo=responsetext.lsDnointernalcardinfo;
	        		if(lsDnointernalcardinfo!=null)
	        		{
	        			$.ligerDialog.open({ url: contextURL+'/CardControl/CC011/showTiaoMaCardItems.jsp', title:'条码卡疗程明细',height: 500,width: 750, buttons: [ { text: '确定', onclick: function (item, dialog) { loadTiaoMaCardInfo(dialog); } }, { text: '取消', onclick: function (item, dialog) { paramtotiaomacardinfo="";dialog.close(); } } ] });
					}
	        	}
	        	else
	        	{
	        		document.getElementById("tiaomacardno").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
        function loadDiyongCardInfo(dialog)
        {
        	document.getElementById("diyongcardno").readOnly="readOnly";
        	var row=commoninfodivfouth.getSelected();
        	if(row!=null)
        	{	
        		var lastCount=row.lastcount;
            	var lastAmt=row.lastamt;
            	var curprice=ForDight(lastAmt/lastCount,1)
        		var detiallength=commoninfodivdetial.rows.length*1;
        		if(detiallength==0)
				{
					addRecord();
					detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
					detiallength=detiallength+1;
				}
        		curRecord=commoninfodivdetial.getRow(detiallength-1);
				commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname: row.ineritemname,csitemno: row.ineritemno,csitemcount:ForDight(lastCount,1),
     				csunitprice:curprice,csitemamt: lastAmt,csdiscount:1,cspaymode:'11',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:''});  
   	 		}
        	dialog.close();
            initDetialGrid();
        }
    	function loadTiaoMaCardInfo(dialog)
    	{
    		document.getElementById("tiaomacardno").readOnly="readOnly";
    		paramtotiaomacardinfo="";
    		var curjosnparam ="";
			var needReplaceStr="";
			var rows = commoninfodivthirth.getCheckedRows();
            var str = "";
            var lastCount=0;
			var lastAmt=0;
			var costCount=0;
			var curprice = 0;
			var curcostamt=0;
			var detiallength=commoninfodivdetial.rows.length*1;
            $(rows).each(function ()
            {
            	lastCount=this.lastcount;
            	lastAmt=this.lastamt;
            	costcount=this.costcount;
            	if(costcount*1>lastCount*1)
            	{
            		$.ligerDialog.warn("取用疗程数量不能超过该疗程的剩余数量！");
            		paramtotiaomacardinfo="";
            		document.getElementById("tiaomacardno").value="";
            		document.getElementById("tiaomacardno").readOnly="";
            		return;
            	}
            	
            	if(detiallength==0)
				{
						addRecord();
						detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
						addRecord();
						detiallength=detiallength+1;
				}
				curprice=ForDight(lastAmt/lastCount,1)
				curcostamt=ForDight(curprice*1*costcount*1,1);
				commoninfodivthirth.updateRow(this,{costamt:ForDight(curcostamt*1,1)});
				curRecord=commoninfodivdetial.getRow(detiallength-1);
				commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname: this.ineritemname,csitemno:this.ineritemno,csitemcount:ForDight(costcount,1),
     				csunitprice:curprice,csitemamt:curcostamt,csdiscount:1,cspaymode:'13',csfirstsaler:'',csfirsttype:'',csfirstshare:'',
     				cssecondsaler:'',cssecondtype:'',cssecondshare:'',csthirdsaler:'',csthirdtype:'',csthirdshare:''});  
   	 				
   	 			curjosnparam=JSON.stringify(this);
                if(curjosnparam.indexOf("_id")>-1)
				{
					needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}
				 if(paramtotiaomacardinfo!="")
					paramtotiaomacardinfo=paramtotiaomacardinfo+",";
				paramtotiaomacardinfo= paramtotiaomacardinfo+curjosnparam; 	 
            });
            dialog.close();
            initDetialGrid();
    	}
      function validateTuangoucardno(obj)
      {
	      	if(obj.value=="")
	      	{
	      		return ;
	      	}
	      	var requestUrl ="cc011/validateTuanGouCardNo.action"; 
			var responseMethod="validateTuanGouCardNoMessage";	
			var params="strTuanGouCardNo="+obj.value;			
			sendRequestForParams_p(requestUrl,responseMethod,params );
      }
     function validateTuanGouCardNoMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		var curCorpsbuyinfo=responsetext.curCorpsbuyinfo;
	        		commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            		commoninfodivdetial.loadData(true);  
            		addRecord();
            		curRecord=commoninfodivdetial.getRow(0);
            		commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,csitemname:curCorpsbuyinfo.corpspicname,csitemno: curCorpsbuyinfo.corpspicno,csitemcount:1,
     				csunitprice:curCorpsbuyinfo.corpsamt,csitemamt:curCorpsbuyinfo.corpsamt,csdiscount:1,cspaymode:16});
	        		document.getElementById("tuangoucardno").readOnly="readOnly";
	        	
	        		var column=commoninfodivdetial.columns[0];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[1];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[2];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[5];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					column=commoninfodivdetial.columns[6];
					commoninfodivdetial.setCellEditing(curRecord, column, true);
					commoninfodivdetial_Pro.options.clickToEdit=false;
					addRecordFlag=1;
					handPayList();
	        	}
	        	else
	        	{
	        		document.getElementById("tuangoucardno").value="";
	        		$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
      }
      
      	//打印小票
      	function viewTicketReport()
		{
			var ticketId=document.getElementById("csbillid").value;
			var shopId=document.getElementById("cscompid").value;
			if(ticketId == "" || ticketId == null||shopId==""||shopId==null)
			{
			    return;
			}
			try
			{
				document.getElementById("strSearchBillId").value = ticketId;
				var params = "ticketId=" + ticketId +"& shopId="+shopId +"& memberCardId="+document.getElementById("cscardno").value+" & billDate="+document.getElementById("csdate").value;
				var requestUrl ="cc011/viewTicketReport.action";
		    	var responseMethod = "viewTicketReportMessage";
		    	sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
			catch(e)
			{
				alert(e.message)
			}
		}
		
		function viewTicketReportMessage( request )
		{
			try
			{
				var responsetext = eval("(" + request.responseText + ")");
				Stand_CheckPrintControl();//检查是否有打印控件
				Stand_InitPrint("收银模块_小票打印作业");
				Stand_SetPrintStyle("FontName","新宋体");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Alignment",2);
				Stand_SetPrintStyle("HOrient",2);
				Stand_SetPrintStyle("Bold",1);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(responsetext.companName)+")");
				Stand_SetPrintStyle("FontSize",9);
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
				Stand_SetPrintStyle("FontSize",11);
				Stand_SetPrintStyle("Bold",0);
				var index=151;
				document.getElementById("billDat").innerHTML=checkNull(responsetext.billDate); 
				if(checkNull(responsetext.memberCardId).indexOf("散客")==-1)
					document.getElementById("memberCardId").innerHTML="***"+checkNull(responsetext.memberCardId).substring(checkNull(responsetext.memberCardId).length-3,checkNull(responsetext.memberCardId).length);
				else
					document.getElementById("memberCardId").innerHTML="现金";
				document.getElementById("billnop").innerHTML=checkNull(responsetext.ticketId).substring(checkNull(responsetext.ticketId).length-3,checkNull(responsetext.ticketId).length);
				clearPreviousResult();
				var table=document.getElementById("info");
				var texts=document.getElementById("texts");
				var text2=document.getElementById("text2");
				var text3=document.getElementById("text3");
				var text4=document.getElementById("text4");
				var text5=document.getElementById("text5");
				var bRet=false;
				var amt =0;
				var num=0;
				var sumNum=0;
				
				var tr=document.createElement("tr");
				var tr1=document.createElement("tr");
				var ttd1=document.createElement("td");
				var ttd2=document.createElement("td");
				var ttd3=document.createElement("td");
				var ttd4=document.createElement("td");
				ttd1.innerHTML="项目名称";
				ttd2.innerHTML="数量";
				ttd3.innerHTML="金额";
				ttd4.innerHTML="大工";
				ttd1.align="left";
				ttd2.align="left";
				ttd3.align="left";
				ttd4.align="left";
				tr.appendChild(ttd1);
				tr.appendChild(ttd2);
				tr.appendChild(ttd3);
				tr.appendChild(ttd4);
				table.appendChild(tr);
						
				if(responsetext.costListProj != null)
				{
					for(var i=0;i<responsetext.costListProj.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.costListProj[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
					
						td3.innerHTML=checkNull(responsetext.costListProj[i].costNumber);
						num=num*1+checkNull(responsetext.costListProj[i].costNumber)*1;
						if(checkNull(responsetext.costListProj[i].paymode)!="9")
						{
							td4.innerHTML=checkNull(responsetext.costListProj[i].costMoney);
							amt=amt*1+checkNull(responsetext.costListProj[i].costMoney)*1;
						}
						else
						{
							td4.innerHTML="次";
							if(responsetext.lsPrintCardproaccount != null && responsetext.lsPrintCardproaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardproaccount.length;j++)
								{
									if((checkNull(responsetext.costListProj[i].projectNo)==checkNull(responsetext.lsPrintCardproaccount[j].bprojectno))
									&&(checkNull(responsetext.costListProj[i].csproseqno)==0 || checkNull(responsetext.costListProj[i].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[j].bproseqno)))
						    		{
						    			td4.innerHTML=td4.innerHTML+"(余"+maskAmt(responsetext.lsPrintCardproaccount[j].lastcount,0)+"次)";
						    		}
								}
							}
							else
							{
								  td4.innerHTML=td4.innerHTML+"(余0次)";
							}
						}
						td2.innerHTML=checkNull(responsetext.costListProj[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						tr1.appendChild(td2);
						table.appendChild(tr1);
						bRet=true;
						
					}
				}
				if(responsetext.CostListProd != null)
				{
					for(var i=0;i<responsetext.CostListProd.length;i++)
					{
						var tr=document.createElement("tr");
						var tr1=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						td.innerHTML=checkNull(responsetext.CostListProd[i].projectName);
						tr.appendChild(td);
						table.appendChild(tr);
						var td1=document.createElement("td");
						var td3=document.createElement("td");
						var td4=document.createElement("td");
						var td2=document.createElement("td");
						td1.innerHTML="";
						td2.innerHTML="";
						td3.innerHTML=checkNull(responsetext.CostListProd[i].costNumber);
						num=num*1+checkNull(responsetext.CostListProd[i].costNumber)*1;
						amt=amt*1+checkNull(responsetext.CostListProd[i].costMoney)*1;
						td4.innerHTML=checkNull(responsetext.CostListProd[i].costMoney);
						td2.innerHTML=checkNull(responsetext.CostListProd[i].strFristStaffNo);
						tr1.appendChild(td1);
						tr1.appendChild(td2);
						tr1.appendChild(td3);
						tr1.appendChild(td4);
						table.appendChild(tr1);
						bRet=true;
					}
				}
				if(bRet)
				{
					var tr1=document.createElement("tr");
					var td1=document.createElement("td");
					var td2=document.createElement("td");
					var td3=document.createElement("td");
					td1.innerHTML="合计";
					td2.innerHTML=num;
					td3.innerHTML=amt;
					tr1.appendChild(td1);
					tr1.appendChild(td2);
					tr1.appendChild(td3);
					table.appendChild(tr1);
					
				}
				var strbank="";
				var pay4flag=0;
				var pay7flag=0;
				var payAflag=0;
				bRet=false;
				if(responsetext.payTypeList != null)
				{
					for(var i=0;i<responsetext.payTypeList.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td1=document.createElement("td");
						var td2=document.createElement("td");
						td1.setAttribute("colSpan",2);
						td2.setAttribute("colSpan",2);
						if(checkNull(responsetext.payTypeList[i].paymodename).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.payTypeList[i].paymodename).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						
						if(checkNull(responsetext.payTypeList[i].paymode)=="4"
						|| checkNull(responsetext.payTypeList[i].paymode)=="7"
						|| checkNull(responsetext.payTypeList[i].paymode)=="A" )
						{
							if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
							{
								for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
								{
									if((checkNull(responsetext.payTypeList[i].paymode)=="4"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="7"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
									||
										(checkNull(responsetext.payTypeList[i].paymode)=="A"
										&& checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
									)
									{
											td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+"&nbsp;&nbsp;"+maskAmt(responsetext.payTypeList[i].payamt,1);
											td2.innerHTML="&nbsp;"+"余:&nbsp;&nbsp;"+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
											if(checkNull(responsetext.payTypeList[i].paymode)=="4")
											{
												pay4flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="7")
											{
												pay7flag=1;
											}
											if(checkNull(responsetext.payTypeList[i].paymode)=="A")
											{
												payAflag=1;
											}
									}
								}
							}
						}
						else
						{
							td1.innerHTML=checkNull(responsetext.payTypeList[i].paymodename)+strbank+"&nbsp;&nbsp;"+maskAmt(responsetext.payTypeList[i].payamt,1);
							td2.innerHTML="&nbsp;"+"余:&nbsp;&nbsp;0";
						}
						tr.appendChild(td1);
						tr.appendChild(td2);
						text2.appendChild(tr);
						bRet=true;
					}
				
				}
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var j=0;j<responsetext.lsPrintCardaccount.length;j++)
					{
						if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[j].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;";
						}
						if(
						   	(
								(pay4flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==2)
							||  (pay7flag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==3)
							||  (payAflag==0 && checkNull(responsetext.lsPrintCardaccount[j].accounttype)==5)
							)
						    && checkNull(responsetext.lsPrintCardaccount[j].accountbalance)*1>0)
						{
							var tr=document.createElement("tr");
							var td1=document.createElement("td");
							var td2=document.createElement("td");
							td1.setAttribute("colSpan",2);
							td2.setAttribute("colSpan",2);
							td1.innerHTML=checkNull(responsetext.lsPrintCardaccount[j].accounttypeText)+strbank+"&nbsp;&nbsp;0";
							td2.innerHTML="&nbsp;"+"余:&nbsp;&nbsp;"+maskAmt(responsetext.lsPrintCardaccount[j].accountbalance,1);
							tr.appendChild(td1);
							tr.appendChild(td2);
							text2.appendChild(tr);
						}
					}
				}
				
				
				/*bRet=false;
				if(responsetext.lsPrintCardaccount != null && responsetext.lsPrintCardaccount.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardaccount.length;i++)
					{
						strbank="";
						var tr=document.createElement("tr");
						var td=document.createElement("td");
						td.setAttribute("colSpan",4);
						if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==2)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==3)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==4)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						else if(checkNull(responsetext.lsPrintCardaccount[i].accounttypeText).length==5)
						{
							strbank="&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						td.innerHTML=checkNull(responsetext.lsPrintCardaccount[i].accounttypeText)+strbank+"余额"+maskAmt(responsetext.lsPrintCardaccount[i].accountbalance,1);
						tr.appendChild(td);
						text3.appendChild(tr);
						bRet=true;
					}
				
				}*/
				var costprj="";
				var costsqno=0;
				bRet=false;
				/*if(responsetext.lsPrintCardproaccount != null 
				&& responsetext.lsPrintCardproaccount.length>0
				&& responsetext.costListProj != null 
				&& responsetext.costListProj.length>0)
				{
					for(var i=0;i<responsetext.lsPrintCardproaccount.length;i++)
					{
						
						for(var j=0;j<responsetext.costListProj.length;j++)
						{
							if(costprj==checkNull(responsetext.costListProj[j].projectName) 
							&& costsqno==checkNull(responsetext.costListProj[j].csproseqno))
							{
								continue;
							}
							if(checkNull(responsetext.costListProj[j].paymode)=="9"
								&&(checkNull(responsetext.costListProj[j].projectNo)==checkNull(responsetext.lsPrintCardproaccount[i].bprojectno))
								&&(checkNull(responsetext.costListProj[j].csproseqno)==0 || checkNull(responsetext.costListProj[j].csproseqno)==checkNull(responsetext.lsPrintCardproaccount[i].bproseqno)))
						    	{
									var tr=document.createElement("tr");
									var td=document.createElement("td");
									var td1=document.createElement("td");
									td.setAttribute("colSpan",2);
									td1.setAttribute("colSpan",2);
									td.innerHTML=checkNull(responsetext.lsPrintCardproaccount[i].bprojectname);
									td1.innerHTML="剩余"+maskAmt(responsetext.lsPrintCardproaccount[i].lastcount,0)+"次";
									tr.appendChild(td);
									tr.appendChild(td1);
									text4.appendChild(tr);
									bRet=true;
									costprj=checkNull(responsetext.costListProj[j].projectName);
									costsqno=checkNull(responsetext.costListProj[j].csproseqno);
								}
								
						}
						
					}
					if(bRet)
					{
						//addHr(text4,4);
					}
				}*/
				
				/*var tr4=document.createElement("tr");
				var td4=document.createElement("td");
				td4.innerHTML="流水号:&nbsp;&nbsp;"+checkNull(responsetext.ticketId);
				tr4.appendChild(td4);
				text5.appendChild(tr4);*/
				
				var tr3=document.createElement("tr");
				var td3=document.createElement("td");
				td3.innerHTML="操作人:&nbsp;&nbsp;"+checkNull(responsetext.cashMemberName)+"&nbsp;&nbsp;打印日期:&nbsp;&nbsp;"+checkNull(responsetext.printDate);
				tr3.appendChild(td3);
				text5.appendChild(tr3);
				
			
				
				var tr5=document.createElement("tr");
				var td5=document.createElement("td");
				//td5.setAttribute("rowSpan",3);
				td5.innerHTML="贵宾签名:";
				tr5.appendChild(td5);
				text5.appendChild(tr5);
				
				
				var tr10=document.createElement("tr");
				var td10=document.createElement("td");
				//td10.setAttribute("",3);
				td10.innerHTML="&nbsp;";
				tr10.appendChild(td10);
				text5.appendChild(tr10);
				
		
				
				
				
				
				var tr8=document.createElement("tr");
				var td8=document.createElement("td");
				td8.setAttribute("align","center");
				td8.innerHTML=checkNull(responsetext.companAddr)+"["+checkNull(responsetext.companTel)+"]";
				tr8.appendChild(td8);
				text5.appendChild(tr8);
				
				var tr7=document.createElement("tr");
				var td7=document.createElement("td");
				td7.setAttribute("align","center");
				td7.innerHTML="www.chinamani.com";
				tr7.appendChild(td7);
				text5.appendChild(tr7);
				
				var tr9=document.createElement("tr");
				var td9=document.createElement("td");
				td9.setAttribute("align","center");
				td9.innerHTML="4006622818";
				tr9.appendChild(td9);
				text5.appendChild(tr9);
				var printContent = document.getElementById("printContent").innerHTML;
				Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,68,0,230,800,printContent);
				Stand_Print();
			}
			catch(e)
			{
				alert(e.message);
			}
		}
		

		//清楚打印内容
		function clearPreviousResult()
		{
			var tblPrjs = document.getElementById("info");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjs = document.getElementById("text2");
			while(tblPrjs.childNodes.length>0)
			{
				tblPrjs.removeChild(tblPrjs.childNodes[0]);
			}
			var tblPrjsb = document.getElementById("text3");
			while(tblPrjsb.childNodes.length>0)
			{
				tblPrjsb.removeChild(tblPrjsb.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text4");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
			var tblPrjsc = document.getElementById("text5");
			while(tblPrjsc.childNodes.length>0)
			{
				tblPrjsc.removeChild(tblPrjsc.childNodes[0]);
			}
		}
		//增加行
	    function addHr(obj,num)
		{
			var tr=document.createElement("tr");
			var td=document.createElement("td");
			td.setAttribute("colSpan",num);
			td.innerHTML="<div style='border-bottom: 1px dashed #000000; width: 100%;'>"
			tr.appendChild(td);
			obj.appendChild(tr);
		}
	      //---------------------------------------------回车事件
		function hotKeyOfSelf(key)
		{
			if(key==13)//回车
			{
				hotKeyEnter();
			}
			else if(key==114 )//F3
			{
			
				addRecord();
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
		
		function  hotKeyEnter()
		{
		
			try
			{
						window.event.keyCode=9; //tab
						window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}  
	
		
		function validateProjectNo(obj)
		{
				if(obj.value=="")
				{
					document.getElementById("wCostItemName").value="";
				}
				else
				{
					
					var requestUrl ="cc011/validateItem.action"; 
					var responseMethod="validateFastItemMessage";
					var params="itemType=1";
					var params=params+"&strCurItemId="+obj.value;	
					var params=params+"&strCardType="+document.getElementById("cscardtype").value;	
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
		}
		
		function validateFastItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
		     	var curProjectinfo=responsetext.curProjectinfo;
		     	if(curProjectinfo==null)
		     	{
		     		document.getElementById("fastItem6").focus();
			 		document.getElementById("fastItem6").select();
		     		$.ligerDialog.warn("输入的项目编码不存在!");
		     		document.getElementById("wCostItemNo").value="";
		     		document.getElementById("wCostItemName").value="";
		     		document.getElementById("itemprice").value="";
		     		document.getElementById("itempay").value="";
		     		document.getElementById("itemdiscount").value="";
		     	}
		     	else
		     	{
		     		document.getElementById("itemprice").value=checkNull(curProjectinfo.saleprice);
		     		document.getElementById("wCostItemName").value=checkNull(curProjectinfo.prjname);
		     		if(document.getElementById("billflag").value=="0")
     				{
     					document.getElementById("itemdiscount").value=1;
     					document.getElementById("itempay").value="1";
     				}
     				else if(document.getElementById("billflag").value=="1")
     				{
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="4";
     				}
     				else
     				{
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="A";
     				}
     				document.getElementById("wCostFirstEmpNo").focus();
			 		document.getElementById("wCostFirstEmpNo").select();
		     	}
		     }catch(e){alert(e.message);}
		}
		
		function validateFristEmpNo(obj)
		{
				if(obj.value=="")
				{
					document.getElementById("wCostFirstEmpName").value="";
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					document.getElementById("wCostFirstEmpName").value=parent.StaffInfo[i].staffname;
			 					document.getElementById("wCostSecondEmpNo").focus();
			 					document.getElementById("wCostSecondEmpNo").select();
			 					vflag=1;
			 					break;
			 				}
			
						}
					}
					if(vflag==0)
					{
						document.getElementById("wCostItemName").focus();
			 		    document.getElementById("wCostItemName").select();
						$.ligerDialog.error("该员工编号不存在,请确认！");
						document.getElementById("wCostFirstEmpName").value="";
						document.getElementById("wCostFirstEmpNo").value="";
						
					}
				}
		}
		
		function validateSecondEmpNo(obj)
		{
				if(obj.value=="")
				{
					document.getElementById("wCostSecondEmpName").value="";
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					document.getElementById("wCostSecondEmpName").value=parent.StaffInfo[i].staffname;
			 					document.getElementById("wCostthirthdEmpNo").focus();
			 					document.getElementById("wCostthirthdEmpNo").select();
			 					vflag=1;
			 					break;
			 				}
			
						}
					}
					if(vflag==0)
					{
						document.getElementById("wCostFirstEmpName").focus();
			 		    document.getElementById("wCostFirstEmpName").select();
						$.ligerDialog.error("该员工编号不存在,请确认！");
						document.getElementById("wCostSecondEmpName").value="";
						document.getElementById("wCostSecondEmpNo").value="";
						
					}
				}
		}
		
		function validateThirthdEmpNo(obj)
		{
				if(obj.value=="")
				{
					document.getElementById("wCostthirthdEmpName").value="";
				}
				else
				{
					var vflag=0;
					if(parent.StaffInfo!=null && parent.StaffInfo.length>0)
					{
						for(var i=0;i<parent.StaffInfo.length;i++)
						{
							if(parent.StaffInfo[i].bstaffno==obj.value)
			 				{
			 					document.getElementById("wCostthirthdEmpName").value=parent.StaffInfo[i].staffname;
			 					document.getElementById("wCostCount").focus();
			 					document.getElementById("wCostCount").select();
			 					vflag=1;
			 					break;
			 				}
			
						}
					}
					if(vflag==0)
					{
						document.getElementById("wCostSecondEmpName").focus();
			 		    document.getElementById("wCostSecondEmpName").select();
						$.ligerDialog.error("该员工编号不存在,请确认！");
						document.getElementById("wCostthirthdEmpName").value="";
						document.getElementById("wCostthirthdEmpNo").value="";
						
					}
				}
		}
		
		function validateFastCostCount(obj)
		{
			if(document.getElementById("wCostItemNo").value=="")
			{
				document.getElementById("fastItem6").focus();
			 	document.getElementById("fastItem6").select();
		     	$.ligerDialog.warn("请先输入项目编码!");
		     	return ;
			}
			if(obj.value*1>=0)
			{
				fastLoadPriject(document.getElementById("wCostItemNo").value,
								document.getElementById("wCostItemName").value,
								document.getElementById("wCostCount").value,
								document.getElementById("itemprice").value,
								document.getElementById("itemprice").value*1*document.getElementById("wCostCount").value*1*document.getElementById("itemdiscount").value*1,
								document.getElementById("itemdiscount").value,
								document.getElementById("itempay").value,
								document.getElementById("wCostFirstEmpNo").value,
								document.getElementById("wCostSecondEmpNo").value,
								document.getElementById("wCostthirthdEmpNo").value);
			}
		}
		
		//快速选择项目
	    function validateFastItemCheck(obj,objtype,objPrice)
	    {
	    	var paymode="1";
	    	if(document.getElementById("billflag").value=="0")
     		{
     			paymode="1";
     		}
     		else if(document.getElementById("billflag").value=="1")
     		{
     			paymode="4";
     		}
     		else
     		{
     			paymode="A";
     		}
	    	if(obj.checked==true)
	    	{
	    		if(objtype==1)
	    		{
	    			
	    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
					{
						for(var i=0;i<parent.lsProjectinfo.length;i++)
						{
							if(parent.lsProjectinfo[i].id.prjno==obj.value)
			 				{
			 					fastLoadPriject(obj.value,
			 									parent.lsProjectinfo[i].prjname,1,
			 									parent.lsProjectinfo[i].saleprice,
			 									parent.lsProjectinfo[i].saleprice,1,paymode,"","","");
			 				}
			
						}
					}
	    		}
	    		else
	    		{
	    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
					{
						for(var i=0;i<parent.lsProjectinfo.length;i++)
						{
							if(parent.lsProjectinfo[i].id.prjno==obj.value)
			 				{
			 					fastLoadPriject(obj.value,
			 									parent.lsProjectinfo[i].prjname,1,
			 									eval("(parent." + objPrice + ")") ,
			 									eval("(parent." + objPrice + ")") ,1,paymode,"","","");
			 				}
			
						}
					}
	    		}
	    	}
	    }
		function fastLoadPriject(projectno,projectname,fcount,curprice,curcostamt,discount,paymode,firstemp,secondemp,thirdemp)
	    {
	    	try
	    	{
	    		var detiallength=commoninfodivdetial.rows.length*1;
		    	if(detiallength==0)
				{
					addRecord();
					detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
					detiallength=detiallength+1;
				}
				curRecord=commoninfodivdetial.getRow(detiallength-1);
				var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemno: projectno,
																	 csitemname: projectname,
																	 csitemcount: ForDight(fcount,1),
	     															 csunitprice: ForDight(curprice,1),
	     															 csitemamt:  ForDight(curcostamt,1),
	     															 csdiscount: ForDight(discount,1),
	     															 cspaymode: paymode,
	     															 csfirstsaler: firstemp,
	     															 csfirsttype:'2',
	     															 csfirstshare:'',
	     															 cssecondsaler: secondemp,
	     															 cssecondtype:'2',
	     															 cssecondshare:'',
	     															 csthirdsaler: thirdemp,
	     															 csthirdtype:'2',
	     															 csthirdshare:''});  
	   	 		initDetialGrid();
				handPayList();
				document.getElementById("wCostItemNo").value="";
		     	document.getElementById("wCostItemName").value="";
		     	document.getElementById("itemprice").value="";
		     	document.getElementById("itempay").value="";
		     	document.getElementById("itemdiscount").value="";
				document.getElementById("wCostFirstEmpName").value="";
				document.getElementById("wCostFirstEmpNo").value="";
				document.getElementById("wCostSecondEmpName").value="";
				document.getElementById("wCostSecondEmpNo").value="";
				document.getElementById("wCostthirthdEmpName").value="";
				document.getElementById("wCostthirthdEmpNo").value="";
				document.getElementById("wCostCount").value=0;
				document.getElementById("wCostItemNo").focus();
				document.getElementById("wCostItemNo").select();
			}catch(e){alert(e.message);}
	    }
	
	   
    //-----------------------------------右侧面板营业分析 start
    		function dataAnalysis_before(tabid)
			{
				
				
			}
			function dataAnalysis_after(tabid)
			{
				if(tabid=="tabitem1")//点击营业分析显示事件
				{
					
				}
				else//点击单据分析显示事件
				{
					
				}
			}
    //---------------------------------------------右侧图形报表 start
				
				function drawBarChart(cTitle, cXTitle, cYTitle, cXValue, cYValue,topdiv) {
				// cTitle 标题；cXTitle x轴标题, cYTitle y轴标题, cXValue x轴值, cYValue y轴值；
				var yValues = cYValue.split(',');
				var yArray = new Array();
				for (i = 0; i < yValues.length; i++) {
					yArray[i] = formatFloat(Number(yValues[i]), 2);
				};
	
				var chart = new Highcharts.Chart({
							chart : {
								renderTo : 'pic_div',
								defaultSeriesType : 'bar'
							},
							title : {
								text : cTitle
							},
							xAxis : {
								categories : cXValue.split(','),
								title : {
									text : cXTitle
								}
							},
							yAxis : {
								min : 0,
								title : {
									text : cYTitle,
									align : 'high'
								}
							},
							tooltip : {
								formatter : function() {
									return '' + this.series.name + ': ' + this.y;
								}
							},
							plotOptions : {
								bar : {
									dataLabels : {
										enabled : true
									}
								}
							},
							legend : {
								enabled : false
							},
							credits : {
								enabled : false
							},
							series : [{
										name : cYTitle,
										data : yArray
									}]
						});
			
				
			}
			
			function formatFloat(src, pos) {
				return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
			}
			
		
			
			function changeBarChartByDate(date)
			{	
				var strYear=date.getFullYear();
				var strMonth=date.getMonth()*1+1;
				if(strMonth*1<10)
					strMonth="0"+strMonth;
				var strDay=date.getDate();
				if(strDay*1<10)
					strDay="0"+strDay;
			
				var requestUrl ="cc011/loadTradeAmt.action"; 
				var responseMethod="loadTradeAmtMessage";	
				var params="strSearchDate="+strYear+strMonth+strDay;			
				 showDialogmanager = $.ligerDialog.waitting('正在加载中,请稍候...');
				sendRequestForParams_p(requestUrl,responseMethod,params );
				
			}	
			
			 function loadTradeAmtMessage(request)
		     {
		    		
		        	try
					{
						showDialogmanager.close();
			        	var responsetext = eval("(" + request.responseText + ")");
			        	var curTradeAnalysis=responsetext.curTradeAnalysis;
			        	if(curTradeAnalysis==null)
			        	{	     
			        		    		 
			        		 $.ligerDialog.success("无记录");
			        		
			        	}
			        	else
			        	{
			        		drawBarChart('', '', '金额', '现金,银行卡,指付通,Ok卡,团购卡,储值消费,疗程消费,项目-券,现金-券,条码卡,美容业绩,美发业绩,烫染业绩,美甲业绩,总虚业绩,总实业绩', 
							''+curTradeAnalysis.tradecashamt+','+curTradeAnalysis.tradebankamt+','+curTradeAnalysis.tradefingeramt+','+
							''+curTradeAnalysis.tradeokcardamt+','+curTradeAnalysis.tradetgcardamt+','+curTradeAnalysis.tradeczcardamt+','+
							''+curTradeAnalysis.tradelccardamt+','+curTradeAnalysis.tradeprojdyamt+','+curTradeAnalysis.tradecashdyamt+','+
							''+curTradeAnalysis.tradetmcardamt+','+curTradeAnalysis.trademrfakeamt+','+curTradeAnalysis.trademffakeamt+','+
							''+curTradeAnalysis.tradetrfakeamt+','+curTradeAnalysis.tradefgfakeamt+','+curTradeAnalysis.tradetotalamt+','+curTradeAnalysis.traderealtotalamt+'','#pic_show_div');
			        	}
		        	}
					catch(e)
					{
						alert(e.message);
					}
		      } 
			function initload()
			{
				try
				{
					drawBarChart('', '', '金额', '现金,银行卡,指付通,Ok卡,团购卡,储值消费,疗程消费,项目-券,现金-券,条码卡,美容业绩,美发业绩,烫染业绩,美甲业绩,总虚业绩,总实业绩', 
					'0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0','#pic_show_div');
				}
				catch(e)
				{
					alert(e.message);
				}
			
			}
//---------------------------------------------右侧图形报表 end


	function loadProjectInfo(obj)
	{
		projectdilogText=obj;
		projectdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/ProjectInfo.html', width: 750, isResize:true, title: '项目列表' });
    }
    
    function loadStaffInfo(obj)
	{
		staffdilogText=obj;
		staffdilog=$.ligerDialog.open({ height: 600, url: contextURL+'/common/commonDilog/StaffInfo.html', width: 750, isResize:true, title: '员工列表' });
    }