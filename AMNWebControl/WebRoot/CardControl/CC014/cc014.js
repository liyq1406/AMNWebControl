	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial_pay=null;//支付方式
	var commoninfodivdetial_cardPay=null;//卡拆分
	var commoninfodivdetial_combin=null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurBillId="";
	var cc014layout=null;
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
	var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
	var billChooseData = [{ choose: 0, text: '单据录入' }, { choose: 1, text: '已登记'}, { choose: 2, text: '已审核'}, { choose: 3, text: '已驳回'}, { choose: 4, text: '已退卡生效'}];
    $(function ()
   	{
	   try
	   {
	   		cc014layout= $("#cc014layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
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
                { display: '门店', 		name: 'bchangecompid', 		width:50	,align: 'left' },
                { display: '单号', 		name: 'bchangebillid', 		width:120	,align: 'left' }
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
            
            commoninfodivdetial_cardPay=$("#commoninfodivdetial_cardPay").ligerGrid({
                columns: [
                { display: '归属店', 		name: 'cardvesting', 		width:60	,align: 'left' },
                { display: '卡号', 		name: 'cardno', 			width:120	,align: 'left' },
                { display: '类型', 		name: 'cardtypename', 		width:140	,align: 'left' },
                { display: '姓名', 		name: 'membername', 		width:70	,align: 'left' },
                { display: '手机', 		name: 'memberphone', 		width:90	,align: 'left' },
                { display: '金额', 		name: 'cardamt', 			width:50	,align: 'left' },
                { name: 'cardtype', 			width:1	,hide:true }
	            ],  pageSize:20, 
                data: null,      
                width: '560',
                height:220,
                enabledEdit: false,  checkbox: false,rownumbers:false,usePager:false
            });
           
            $("#insCardInfo").ligerButton(
	        {
	             text: '初始会员卡', width: 100,
		         click: function ()
		         {
		             initNewCardInfo();
		         }
	        });
	         $("#rejectBackCard").ligerButton(
	        {
	             text: '驳回退卡', width: 140,
		         click: function ()
		         {
		             rejectBackCard();
		         }
	        });
	         $("#effectiveBackCard").ligerButton(
	        {
	             text: '退卡生效', width: 140,
		         click: function ()
		         {
		             effectiveBackCard();
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
     	var requestUrl ="cc014/loadMasterinfos.action"; 
		var responseMethod="loadMasterinfosMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
    }
   
   function loadMasterinfosMessage(request)
   {
       	try
        {
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMcardchangeinfo!=null && responsetext.lsMcardchangeinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcardchangeinfo,Total: responsetext.lsMcardchangeinfo.length});
            	commoninfodivmaster.loadData(true);  
            	commoninfodivmaster.select(0);          	
	   		}
	   		else
	   		{
	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivmaster.loadData(true);  
	   		}
	   
	   			lsStaffinfo= responsetext.lsStaffinfo;
	   		var lsStaffSelectData_sj=loadGridByStaffInfo(responsetext.lsStaffinfo,'');
			firstsaleridmanager= $("#firstsalerid").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff', autocomplete: true,  data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcfirstsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true }); 
			secondsaleridmanager= $("#secondsalerid").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff',autocomplete: true,   data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcsecondsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true }); 
			thirdsalerinidmanager= $("#thirdsalerid").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff', autocomplete: true,  data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcthirdsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220' ,selectBoxWidth:'140',alwayShowInDown:true}); 
			fourthsalerinidmanager= $("#fourthsalerid").ligerComboBox({ isShowCheckBox: false, url:'loadAutoStaff',autocomplete: true,    data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcfourthsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220' ,selectBoxWidth:'140',alwayShowInDown:true}); 
			fifthsaleridmanager= $("#fifthsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', autocomplete: true, data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcfifthsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220' ,selectBoxWidth:'140',alwayShowInDown:true}); 
			sixthsaleridmanager= $("#sixthsalerid").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff', autocomplete: true,  data: lsStaffSelectData_sj
	              					 , valueFieldID: 'factcsixthsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220' ,selectBoxWidth:'140',alwayShowInDown:true}); 
			seventhsaleridmanager= $("#seventhsalerid").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff',autocomplete: true,   data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcseventhsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true }); 
			eighthsaleridmanager= $("#eighthsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', autocomplete: true, data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factceighthsalerid',label:'请选员工',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true }); 
			
		 	//loadPayModeDate(responsetext.strSalePayMode);

	 	}
	 	catch(e){alert(e.message);}
    }
    
     function loadAutoStaff(curmanager,curWriteStaff)
    {
    	curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
    	curmanager.selectBox.show();
    }
  
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 strCurBillId=data.bchangebillid;
       			 var params = "strCurCompId="+data.bchangecompid;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc014/loadCurMcardchangeinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								
								document.getElementById("lbBillId").innerHTML=checkNull(action.curMcardchangeinfo.id.changebillid);
								document.getElementById("changecompid").value=checkNull(action.curMcardchangeinfo.id.changecompid);
								document.getElementById("changebillid").value=checkNull(action.curMcardchangeinfo.id.changebillid);
								document.getElementById("changedate").value=checkNull(action.curMcardchangeinfo.changedate);
								document.getElementById("changetime").value=checkNull(action.curMcardchangeinfo.changetime);
								document.getElementById("changebeforcardno").value=checkNull(action.curMcardchangeinfo.changebeforcardno);
								document.getElementById("changebeforcardtype").value=checkNull(action.curMcardchangeinfo.changebeforcardtype);
								document.getElementById("changebeforcardtypename").value=checkNull(action.curMcardchangeinfo.changebeforcardtypename);
								document.getElementById("changecardfrom").value=checkNull(action.curMcardchangeinfo.changecardfrom);
								document.getElementById("salarytype").value=checkNull(action.curMcardchangeinfo.salarytype)*1;
								document.getElementById("billinsertype").value=checkNull(action.curMcardchangeinfo.billinsertype);
								document.getElementById("membername").value=checkNull(action.curMcardchangeinfo.membername);
						   		document.getElementById("memberphone").value=checkNull(action.curMcardchangeinfo.memberphone);
						  
						   		document.getElementById("curaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curaccountkeepamt);
						   		document.getElementById("curaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curaccountdebtamt);
						   		document.getElementById("curproaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curproaccountkeepamt);
						   		document.getElementById("curproaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curproaccountdebtamt);
						   		document.getElementById("cursendaccountkeepamt").value=checkNull(action.curMcardchangeinfo.cursendaccountkeepamt);
						   		document.getElementById("curpointaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curpointaccountkeepamt);
						   		
						   		document.getElementById("rechargeremark").value=checkNull(action.curMcardchangeinfo.rechargeremark);
						   		document.getElementById("changetype").value=checkNull(action.curMcardchangeinfo.id.changetype);
						   		//handleRadio("curMcardchangeinfo.billflag",checkNull(action.curMcardchangeinfo.billflag)); 
						   		document.getElementById("cashfillamt").value=checkNull(action.curMcardchangeinfo.cashfillamt);
						   		document.getElementById("bankfillamt").value=checkNull(action.curMcardchangeinfo.bankfillamt);
						   		document.getElementById("keepamtfillamt").value=checkNull(action.curMcardchangeinfo.keepamtfillamt);
						   		document.getElementById("deductamt").value=checkNull(action.curMcardchangeinfo.deductamt);
						   		document.getElementById("changefillamt").value=checkNull(action.curMcardchangeinfo.changefillamt);
								document.getElementById("billflag").value=checkNull(action.curMcardchangeinfo.billflag)*1;
						   		document.getElementById("firstsalerid_h").value=checkNull(action.curMcardchangeinfo.firstsalerid);
						   		firstsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.firstsalerid));
						   		document.getElementById("firstsaleamt").value=checkNull(action.curMcardchangeinfo.firstsaleamt);
						   		document.getElementById("firstsalecashamt").value=checkNull(action.curMcardchangeinfo.firstsalecashamt);
						   		document.getElementById("secondsalerid_h").value=checkNull(action.curMcardchangeinfo.secondsalerid);
						   		secondsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.secondsalerid));
						   		document.getElementById("secondsaleamt").value=checkNull(action.curMcardchangeinfo.secondsaleamt);
						 		document.getElementById("secondsalecashamt").value=checkNull(action.curMcardchangeinfo.secondsalecashamt);
						   		document.getElementById("thirdsalerid_h").value=checkNull(action.curMcardchangeinfo.thirdsalerid);
						   		thirdsalerinidmanager.selectValue(checkNull(action.curMcardchangeinfo.thirdsalerid));
						   		document.getElementById("thirdsaleamt").value=checkNull(action.curMcardchangeinfo.thirdsaleamt);
						 		document.getElementById("thirdsalecashamt").value=checkNull(action.curMcardchangeinfo.thirdsalecashamt);
						   		document.getElementById("fourthsalerid_h").value=checkNull(action.curMcardchangeinfo.fourthsalerid);
						   		fourthsalerinidmanager.selectValue(checkNull(action.curMcardchangeinfo.fourthsalerid));
						   		document.getElementById("fourthsaleamt").value=checkNull(action.curMcardchangeinfo.fourthsaleamt);
						   		document.getElementById("fourthsalecashamt").value=checkNull(action.curMcardchangeinfo.fourthsalecashamt);
						   
						   		document.getElementById("fifthsalerid_h").value=checkNull(action.curMcardchangeinfo.fifthsalerid);
						   		fifthsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.fifthsalerid));
						   		document.getElementById("fifthsaleamt").value=checkNull(action.curMcardchangeinfo.fifthsaleamt);
						   		document.getElementById("fifthsalecashamt").value=checkNull(action.curMcardchangeinfo.fifthsalecashamt);
						   		document.getElementById("sixthsalerid_h").value=checkNull(action.curMcardchangeinfo.sixthsalerid);
						   		sixthsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.sixthsalerid));
						   		document.getElementById("sixthsaleamt").value=checkNull(action.curMcardchangeinfo.sixthsaleamt);
						   		document.getElementById("sixthsalecashamt").value=checkNull(action.curMcardchangeinfo.sixthsalecashamt);
						   		document.getElementById("seventhsalerid_h").value=checkNull(action.curMcardchangeinfo.seventhsalerid);
						   		seventhsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.seventhsalerid));
						   		document.getElementById("seventhsaleamt").value=checkNull(action.curMcardchangeinfo.seventhsaleamt);
						   		document.getElementById("seventhsalecashamt").value=checkNull(action.curMcardchangeinfo.seventhsalecashamt);		
						   		document.getElementById("eighthsalerid_h").value=checkNull(action.curMcardchangeinfo.eighthsalerid);
						   		  			
						   		eighthsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.eighthsalerid));
						  		document.getElementById("eighthsaleamt").value=checkNull(action.curMcardchangeinfo.eighthsaleamt);
						   		document.getElementById("eighthsalecashamt").value=checkNull(action.curMcardchangeinfo.eighthsalecashamt);
						   		if(action.lsDcardchangetocardinfo!=null && action.lsDcardchangetocardinfo.length>0)
						   		{
						   			commoninfodivdetial_cardPay.options.data=$.extend(true, {},{Rows: action.lsDcardchangetocardinfo,Total: action.lsDcardchangetocardinfo.length});
					            	commoninfodivdetial_cardPay.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial_cardPay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial_cardPay.loadData(true);  
					            	itemclick_addpaycard();
						   		}
						   		readPage(action.curMcardchangeinfo.billflag);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	//获取支付方式
   	function loadPayModeDate(strSalePayMode)
   	{
   		var strpaymode= new Array();
   		if(strSalePayMode!="")
   		{
   			var returnValue='';
   			var paymode="";
   			var paymodename="";
   			strpaymode[0]="1";
   			strpaymode[1]="2";
   			strpaymode[2]="6";
   			for(var i=0;i<strpaymode.length;i++)
   			{
   				paymode=strpaymode[i];
   				paymodename=parent.loadCommonControlValue("ZFFS",0,paymode);
   				if(returnValue!='')
				{
					returnValue=returnValue+',';
				}
				else
				{
					returnValue=returnValue+'[';
				}
				returnValue=returnValue+'{"choose": "'+paymode+'","text": "'+paymodename+'"}';
	   		}
	   		if(returnValue!='')
	   		{
				returnValue=returnValue+']';
				commoninfodivdetial_pay.columns[0].editor.data=JSON.parse(returnValue);
			}
   		}
   	}
   	
   		//初始化员工下拉表
   	function loadGridByStaffInfo(StaffInfo,type)
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
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
									strJson=strJson+'{ "id":"'+StaffInfo[i].bstaffno+'", "text": "'+StaffInfo[i].bstaffno+'_'+StaffInfo[i].staffname+'"}';
								
							
							
							
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
	
  
     
	
  
     
     function readPage(billstate)
     {
     		
		if(billstate==0)
		{
			curpagestate=1;
			//document.getElementById("changebeforcardno").readOnly="";
     		document.getElementById("cashfillamt").readOnly=""; 
     		document.getElementById("bankfillamt").readOnly=""; 
     		document.getElementById("firstsaleamt").readOnly=""; 
     		document.getElementById("secondsaleamt").readOnly=""; 
     		document.getElementById("thirdsaleamt").readOnly=""; 
			document.getElementById("fourthsaleamt").readOnly=""; 
			document.getElementById("fifthsaleamt").readOnly=""; 
			document.getElementById("sixthsaleamt").readOnly=""; 
			document.getElementById("seventhsaleamt").readOnly=""; 
			document.getElementById("eighthsaleamt").readOnly=""; 
			
     		/*document.getElementById("firstsalecashamt").readOnly=""; 
     		document.getElementById("secondsalecashamt").readOnly=""; 
     		document.getElementById("thirdsalecashamt").readOnly=""; 
			document.getElementById("fourthsalecashamt").readOnly=""; 
			document.getElementById("fifthsalecashamt").readOnly=""; 
			document.getElementById("sixthsalecashamt").readOnly=""; 
			document.getElementById("seventhsalecashamt").readOnly=""; 
			document.getElementById("eighthsalecashamt").readOnly=""; */
			document.getElementById("rechargeremark").readOnly=""; 
	     	
	     	//commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
			//commoninfodivdetial_pay.loadData(true);   
			//commoninfodivdetial_pay.options.clickToEdit=true;
	     	//commoninfodivdetial_pay.options.enabledEdit=true;
			firstsaleridmanager.setEnabled();
			secondsaleridmanager.setEnabled();
			thirdsalerinidmanager.setEnabled();
			fourthsalerinidmanager.setEnabled();
			fifthsaleridmanager.setEnabled();
			sixthsaleridmanager.setEnabled();
			seventhsaleridmanager.setEnabled();
			eighthsaleridmanager.setEnabled();
			itemclick_addpay();
     	}
       	if(billstate==1  )
       	{
       		curpagestate=3;
     		//document.getElementById("changebeforcardno").readOnly="readOnly";
     		document.getElementById("cashfillamt").readOnly="readOnly"; 
     		document.getElementById("bankfillamt").readOnly="readOnly"; 
		    document.getElementById("membername").readOnly="readOnly";
    		document.getElementById("memberphone").readOnly="readOnly";
    		document.getElementById("firstsaleamt").readOnly="readOnly"; 
     		document.getElementById("secondsaleamt").readOnly="readOnly"; 
     		document.getElementById("thirdsaleamt").readOnly="readOnly"; 
			document.getElementById("fourthsaleamt").readOnly="readOnly"; 
			document.getElementById("fifthsaleamt").readOnly="readOnly"; 
			document.getElementById("sixthsaleamt").readOnly="readOnly"; 
			document.getElementById("seventhsaleamt").readOnly="readOnly"; 
			document.getElementById("eighthsaleamt").readOnly="readOnly"; 
			/*document.getElementById("firstsalecashamt").readOnly="readOnly"; 
     		document.getElementById("secondsalecashamt").readOnly="readOnly"; 
     		document.getElementById("thirdsalecashamt").readOnly="readOnly"; 
			document.getElementById("fourthsalecashamt").readOnly="readOnly"; 
			document.getElementById("fifthsalecashamt").readOnly="readOnly"; 
			document.getElementById("sixthsalecashamt").readOnly="readOnly"; 
			document.getElementById("seventhsalecashamt").readOnly="readOnly"; 
			document.getElementById("eighthsalecashamt").readOnly="readOnly"; */
			document.getElementById("rechargeremark").readOnly="readOnly"; 
     		firstsaleridmanager.setDisabled();
			secondsaleridmanager.setDisabled();
			thirdsalerinidmanager.setDisabled();
			fourthsalerinidmanager.setDisabled();
			fifthsaleridmanager.setDisabled();
			sixthsaleridmanager.setDisabled();
			seventhsaleridmanager.setDisabled();
			eighthsaleridmanager.setDisabled();
	     	//commoninfodivdetial_pay.options.clickToEdit=false;
	     	//commoninfodivdetial_pay.options.enabledEdit=false;

     	}
       
     }
     
     
  
   	
    function itemclick_addpay()
   	{
   			var i=0;
   			while(i<3)
   			{
	   			var row = commoninfodivdetial_pay.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
					    //参数3:之前或者之后(非必填)
				commoninfodivdetial_pay.addRow({ 
					                paymode: "",
					                payamt: "",
					                payremark: ""
					             
					            }, row, false);
				i++;
			}
   	}
   	
   	function itemclick_addpaycard()
   	{
   		var row = commoninfodivdetial_cardPay.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
		commoninfodivdetial_cardPay.addRow({ 
					                cardvesting:'',
					                cardno:'',
					                cardtype: '',
					                cardtypename:'',
					                membername: '',
					                memberphone: '',
					                cardamt: ''
					             
   		});
   		
   	}
   	
   	
   	
   
  
   	function  searchCurRecord()
   	{
   		//var searchLossCardNo=document.getElementById("searchLossCardNo").value;
   		//var searchRecevieCardNo=document.getElementById("searchRecevieCardNo").value;
   		var strSearchContent=document.getElementById("strSearchContent").value;
   		if(strSearchContent=="" )
   		{	
			 $.ligerDialog.warn("请输入查询信息!");
     		 return;
     	}
     	else
     	{
     		var requestUrl ="cc014/searchBill.action";
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
	       	if(responsetext.lsMcardchangeinfo!=null && responsetext.lsMcardchangeinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcardchangeinfo,Total: responsetext.lsMcardchangeinfo.length});
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
   	
   	
    function validateChangebeforcardno(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("changebeforcardtype").value="";
    		document.getElementById("changebeforcardtypename").value="";
    		document.getElementById("membername").value="";
    		document.getElementById("memberphone").value="";
    		document.getElementById("curaccountkeepamt").value="";
    		document.getElementById("curaccountdebtamt").value="";
    		document.getElementById("curproaccountkeepamt").value="";
    		document.getElementById("curpointaccountkeepamt").value="";
    		document.getElementById("cursendaccountkeepamt").value="";
    		document.getElementById("curproaccountdebtamt").value="";
    		document.getElementById("changecardfrom").value="";
    		return;
    	}
    	var requestUrl ="cc014/validateTkCardno.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
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
	        		document.getElementById("changebeforcardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("changebeforcardtypename").value=responsetext.curCardinfo.cardtypeName;
	        		document.getElementById("membername").value=responsetext.curCardinfo.membername;
    				document.getElementById("memberphone").value=responsetext.curCardinfo.memberphone;
    				document.getElementById("curaccountkeepamt").value=responsetext.curCardinfo.account2Amt;
    				document.getElementById("curaccountdebtamt").value=responsetext.curCardinfo.account2debtAmt;
    				document.getElementById("curproaccountkeepamt").value=responsetext.curCardinfo.account4Amt;
    				document.getElementById("curproaccountdebtamt").value=responsetext.curCardinfo.account4debtAmt;
    				document.getElementById("changecardfrom").value=responsetext.curCardinfo.id.cardvesting;
    				document.getElementById("curpointaccountkeepamt").value=responsetext.curCardinfo.account6Amt;
    				document.getElementById("cursendaccountkeepamt").value=responsetext.curCardinfo.account3Amt;
    				document.getElementById("deductamt").value=ForDight(checkNull(responsetext.curCardinfo.account2Amt)*1+checkNull(responsetext.curCardinfo.account4Amt)*1,1);
	        	}
	        	else
	        	{
	        		 document.getElementById("changebeforcardno").value="";
	        		 document.getElementById("changebeforcardtype").value="";
	        		 document.getElementById("changebeforcardtypename").value="";
	        		 document.getElementById("membername").value="";
    				document.getElementById("memberphone").value="";
    				document.getElementById("curaccountkeepamt").value="";
    				document.getElementById("curaccountdebtamt").value="";
    				document.getElementById("curproaccountkeepamt").value="";
    				document.getElementById("curproaccountdebtamt").value="";
    				document.getElementById("changecardfrom").value="";
    				document.getElementById("deductamt").value=0;
	        		 $.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
      
    function validateOldCardInfo(obj)
    {
    
    	if(obj.value=="")
    	{
    		commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
			return;
    	}
    	else if (obj.value==document.getElementById("changebeforcardno").value)
    	{
    		$.ligerDialog.warn("被并卡号不能与目标卡重复!");
    		commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
			return;
    	}
    	else
    	{
    		if(document.getElementById("changebeforcardno").value=="")
    		{
    			$.ligerDialog.warn("请先输入目标卡号!");
    			commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
				return;
    		}
    		else
    		{
    			var existsflag=0;
    			for (var rowid in commoninfodivdetial_combin.records)
				{
					var row =commoninfodivdetial_combin.records[rowid];
					if(row.boldcardno==obj.value)
					{
						existsflag=existsflag+1;
					}
				} 
				if(existsflag>1)
				{
					$.ligerDialog.warn("输入卡号已在老卡列表中纯在!");
    				commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
					return;
				}
    			var  requestUrl ="cc014/validateOldCardno.action";
    			var params="strCurCompId="+document.getElementById("changecompid").value;
				params=params+"&strCurCardNo="+obj.value;
				params=params+"&iCurChangeType="+document.getElementById("changetype").value;
				var responseMethod="validateOldCardnoDetialMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
    		}
    	}
    }
    
     function validateOldCardnoDetialMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	
	        		if(document.getElementById("changetype").value==6 )
	        		{
	        			if(checkNull(responsetext.curCardinfo.membername)!=document.getElementById("membername").value)  
	        			{
	        				$.ligerDialog.warn("此会员卡号与目标卡会员姓名不一致,请核对后输入!");
    						commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
							return;
	        			}
	        		}
	        		if(document.getElementById("changetype").value==7 )
	        		{
	        			for (var rowid in commoninfodivdetial_combin.records)
						{
							var row =commoninfodivdetial_combin.records[rowid];
							
							if(row.oldcardname!="" && row.oldcardname!=checkNull(responsetext.curCardinfo.membername))
							{
								$.ligerDialog.warn("此会员卡号与并卡列表会员姓名不一致,请核对后输入!");
    							commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
								return;
							}
						} 
	        		}
	        		if(checkNull(responsetext.curCardinfo.cardsource)==1)  //收购卡
	        		{
	        		 	commoninfodivdetial_combin.updateRow(curOldCardRecord,{oldcardname: checkNull(responsetext.curCardinfo.membername),
	        															   oldcardtype: checkNull(responsetext.curCardinfo.cardtype),
	        															   oldcardtypename: checkNull(responsetext.curCardinfo.cardtypeName),
																		   totalaccountkeepamt: ForDight(checkNull(responsetext.curCardinfo.account4Amt)*1+checkNull(responsetext.curCardinfo.account5Amt)*1,1),
																		   totalaccountdebtamt: ForDight(checkNull(responsetext.curCardinfo.account4debtAmt)*1+checkNull(responsetext.curCardinfo.account5debtAmt)*1,1),
																		   curaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account5Amt),1),
																		   curaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account5debtAmt),1),
																		   proaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account4debtAmt),1),
																		   proaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account4Amt),1)});
					}
					else
					{
						commoninfodivdetial_combin.updateRow(curOldCardRecord,{oldcardname: checkNull(responsetext.curCardinfo.membername),
	        															   oldcardtype: checkNull(responsetext.curCardinfo.cardtype),
	        															   oldcardtypename: checkNull(responsetext.curCardinfo.cardtypeName),
																		   totalaccountkeepamt: ForDight(checkNull(responsetext.curCardinfo.account4Amt)*1+checkNull(responsetext.curCardinfo.account2Amt)*1,1),
																		   totalaccountdebtamt: ForDight(checkNull(responsetext.curCardinfo.account4debtAmt)*1+checkNull(responsetext.curCardinfo.account2debtAmt)*1,1),
																		   curaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account2Amt),1),
																		   curaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account2debtAmt),1),
																		   proaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account4debtAmt),1),
																		   proaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account4Amt),1)});
					}
					companylowAmt();
					itemclick_addoldcard();
	        	}
	        	else
	        	{
	        		 	$.ligerDialog.warn(strMessage);
	        		 	commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
	        		
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    function companylowAmt()
	{
	    
		var oldcardAmt=0;
		for (var rowid in commoninfodivdetial_combin.records)
		{
			var row =commoninfodivdetial_combin.records[rowid];
			if(row.boldcardno!="")
			{
				oldcardAmt=oldcardAmt+row.totalaccountkeepamt*1;
			}
		} 
		document.getElementById("changelowamt").value=ForDight(document.getElementById("combinlowamt").value*1-oldcardAmt*1,1);
		if(document.getElementById("changelowamt").value*1<0)
		{
			document.getElementById("changelowamt").value=0;
		}
	}
    
    function validateChangeaftercardno(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("changeaftercardtype").value="";
    		document.getElementById("changeaftercardtypename").value="";
    		
    		return;
    	}
    	if(document.getElementById("changebeforcardno").value=="")
    	{
    		$.ligerDialog.warn("请先输入老卡卡号!");
    		 document.getElementById("changeaftercardno").value="";
    		document.getElementById("changeaftercardtype").value="";
    		document.getElementById("changeaftercardtypename").value="";
    		return;
    	}
    	var requestUrl ="cc014/validateGsCardno.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurCardNo="+obj.value;
		params=params+"&iCurChangeType="+document.getElementById("changetype").value;
		params=params+"&strCurCardType="+document.getElementById("changebeforcardtype").value;
		params=params+"&strCurCardTypeName="+document.getElementById("changebeforcardtypename").value;
		params=params+"&curCardSaleCardAmt="+document.getElementById("curCardSaleCardAmt").value;
		var responseMethod="validateGsCardnoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
    }
    
    function validateGsCardnoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		document.getElementById("changeaftercardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("changeaftercardtypename").value=responsetext.curCardinfo.cardtypeName;
	        		document.getElementById("changelowamt").value=responsetext.lowestAmt;
				    if(document.getElementById("changelowamt").value*1<0)
					{
						document.getElementById("changelowamt").value=0;
					}
	        	}
	        	else
	        	{
	        		 document.getElementById("changeaftercardno").value="";
	        		 document.getElementById("changeaftercardtype").value="";
	        		 document.getElementById("changeaftercardtypename").value="";
	        		 document.getElementById("changelowamt").value=0;
	        		 $.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    
     function validateRechargeAmt(obj)
     {
        	try
        	{
	        	
	        	var totalamt=obj.value*1;
	        	if(totalamt> ForDight(document.getElementById("curaccountkeepamt").value*1+document.getElementById("curproaccountkeepamt").value*1,1))
	        	{
	        		$.ligerDialog.warn("退卡金额不能超过该卡剩余金额!");
	   				totalamt=0;
	   				obj.value=0;
	        	}
				//commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
				//commoninfodivdetial_pay.loadData(true);   
				//itemclick_addpay("");
				//var payrecord=commoninfodivdetial_pay.getRow(0)
			    //commoninfodivdetial_pay.updateRow(payrecord,{paymode:'6',payamt: totalamt});
		    }
		    catch(e)
		    {
		    	alert(e.message);
		    }
       }
       
       function editCurRecord()
       {
       		if(postState==1)
			{
				$.ligerDialog.error("正在保存中,请不要连续保存!");
				return ;
			}
			
		    postState=1;
		    if(document.getElementById("billflag").value!=0)
       		{
       			$.ligerDialog.error("该退卡单已保存,不能再次保存!");
       			postState=0;
	    		return;
       		}
       		if(document.getElementById("changebeforcardno").value=="")
       		{
       			$.ligerDialog.warn("请先输入退卡卡号!");
       			postState=0;
	    		return;
       		}
       		var shareTotalAmt=document.getElementById("firstsaleamt").value*1+document.getElementById("secondsaleamt").value*1+document.getElementById("thirdsaleamt").value*1+
							  document.getElementById("fourthsaleamt").value*1+document.getElementById("fifthsaleamt").value*1+document.getElementById("sixthsaleamt").value*1+
							  document.getElementById("seventhsaleamt").value*1+document.getElementById("eighthsaleamt").value*1 ;
			if(ForDight(shareTotalAmt*1,1) > ForDight(document.getElementById("cashfillamt").value*1+document.getElementById("bankfillamt").value*1,1))  
			{
					$.ligerDialog.error('分享金额不能高于现金退卡额度(现金退卡+银行卡退卡),请确认!');
					postState=0;
					return;
			} 
			
			if(ForDight(document.getElementById("curaccountkeepamt").value*1+document.getElementById("curproaccountkeepamt").value*1,1)< ForDight(document.getElementById("cashfillamt").value*1+document.getElementById("bankfillamt").value*1+document.getElementById("keepamtfillamt").value*1,1))  
			{
					$.ligerDialog.error('退卡额度不能超出卡剩余额度(储值金额+疗程金额),请确认!');
					postState=0;
					return;
			} 
			
			if(document.getElementById("salarytype").value==1 && document.getElementById("billinsertype").value==0)
	    	{
	    		$.ligerDialog.error("请确认退卡卡来源!");
	    		postState=0;
	    		return;
	    	}			
       		if(parent.hasFunctionRights( "CC014",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 postState=0;
        		 return;
        	}
			if(curpagestate==3)
	   		{
	   			$.ligerDialog.warn("非新增单据,不可保存!");
	   			postState=0;
	   			return;
	   		}
	   		else
	   		{
	   	
		   		$.ligerDialog.confirm('确认保存当前退卡单?', function (result)
				{
				    if( result==true)
		           	{
			       		 //------支付方式
						var strJsonParam_detial="";
						var curjosnparam="";
						var needReplaceStr="";
						var paymat=0;
						for (var rowid in commoninfodivdetial_cardPay.records)
						{
							var row =commoninfodivdetial_cardPay.records[rowid];
							curjosnparam=JSON.stringify(row);
							curjosnparam=curjosnparam.replace("%","");
							curjosnparam=curjosnparam.replace("#","");        		   
							if(strJsonParam_detial!="")
							  	strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
						} 
						
						document.getElementById("salecooperid_h").value=  $("#factSalecooperid").val();
						document.getElementById("firstsalerid_h").value=  $("#factcfirstsalerid").val();
						document.getElementById("secondsalerid_h").value= $("#factcsecondsalerid").val();	
						document.getElementById("thirdsalerid_h").value=  $("#factcthirdsalerid").val();	
						document.getElementById("fourthsalerid_h").value=  $("#factcfourthsalerid").val();
						document.getElementById("fifthsalerid_h").value=  $("#factcfifthsalerid").val();	
						document.getElementById("sixthsalerid_h").value=  $("#factcsixthsalerid").val();	
						document.getElementById("seventhsalerid_h").value=$("#factcseventhsalerid").val();	
						document.getElementById("eighthsalerid_h").value=$("#factcseventhsalerid").val();
						var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
						var requestUrl ="cc014/post.action";
						var params=queryStringTmp;
						if(strJsonParam_detial!="")
						{
							params=params+"&strJsonParam=["+strJsonParam_detial+"]";
						}
						
						var responseMethod="editMessage";
					    sendRequestForParams_p(requestUrl,responseMethod,params ); 
		     	}
		     	else
		     	{
		     		postState=0;
		     	}
			}); 
			}
       }
       
        function editMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("退卡成功,请到卡资料中核实!");
	        		 	readPage(1);
	        	}
	        	else
	        	{
	        		alert(strMessage);
	        	}
	        	postState=0;
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
        
     function checkStaffValuex(obj)
		{
			try
			{
				if(obj.id=="firstsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  firstsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			firstsaleridmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="secondsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  secondsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			secondsaleridmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="thirdsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  thirdsalerinidmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			thirdsalerinidmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="fourthsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  fourthsalerinidmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			fourthsalerinidmanager.selectValue('');
			    		}
			    	}
				}
				if(obj.value=="")
				{
					return;
				}
				if(obj.id=="fifthsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  fifthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			fifthsaleridmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="sixthsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  sixthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			sixthsaleridmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="seventhsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  seventhsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			seventhsaleridmanager.selectValue('');
			    		}
			    	}
				}
				else if(obj.id=="eighthsalerid")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  eighthsaleridmanager.selectValue(lsStaffinfo[i].bstaffno);
			    				  exists=1;
			    				  break;
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			eighthsaleridmanager.selectValue('');
			    		}
			    	}
				}
			}
			catch(e)
			{
				alert(e.message);
			}
		}
		
		 	 //刷新
  	 function freshCurRecord()
  	 {
  	 	f_selectNode();
  	 }
  	 
  	 
  	 function confirmBackCard()
  	 {
  	 	
	    var requestUrl ="cc014/confirmBackCard.action";
	    var params = "strCurCompId="+document.getElementById("changecompid").value;
        params =params+ "&strCurBillId="+document.getElementById("changebillid").value;
        params =params+ "&strCurCardNo="+document.getElementById("changebeforcardno").value;
       	var responseMethod="confirmBackCardMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
  	 }
  	 
  	  function confirmBackCardMessage(request)
      {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("审核成功!");
	        		 	readPage(1);
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
        
        
     function rejectBackCard()
     {
     	
	   	var requestUrl ="cc014/rejectBackCard.action";
	    var params = "strCurCompId="+document.getElementById("changecompid").value;
        params =params+ "&strCurBillId="+document.getElementById("changebillid").value;
        params =params+ "&strCurCardNo="+document.getElementById("changebeforcardno").value;
       	var responseMethod="rejectBackCardMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
     
    function rejectBackCardMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("驳回成功!");
	        		 	readPage(1);
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
     
     function effectiveBackCard()
     {
     	if(document.getElementsByName("curMcardchangeinfo.billflag")[2].checked==false)
     	{	        		 
	        $.ligerDialog.warn("该单不能此操作,请核查单据状态!");
	         return ;
	    }
	    var requestUrl ="cc014/effectiveBackCard.action";
	    var params = "strCurCompId="+document.getElementById("changecompid").value;
        params =params+ "&strCurBillId="+document.getElementById("changebillid").value;
        params =params+ "&strCurCardNo="+document.getElementById("changebeforcardno").value;
       	var responseMethod="effectiveBackCardMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
     
     function effectiveBackCardMessage(request)
     {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("退卡成功,请到会员卡资料核实!");
	        		 	readPage(1);
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
         	  
  	 //---------------------------------------------回车事件
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
				firstsaleridmanager .selectBox.hide();
   				secondsaleridmanager.selectBox.hide();
   				thirdsalerinidmanager.selectBox.hide();
   				fifthsaleridmanager.selectBox.hide();
   				sixthsaleridmanager.selectBox.hide();
   				seventhsaleridmanager.selectBox.hide();
			    fourthsalerinidmanager.selectBox.hide();
			    eighthsaleridmanager.selectBox.hide();
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("changebeforcardno").value=cardNo;
				validateChangebeforcardno(document.getElementById("changebeforcardno"));
	    	}
        } 
        
        function validateNewCardNo(obj)
        {
        	if(obj.value=="")
        	{
        		document.getElementById("returnCardType").value="";
        		document.getElementById("returnCardAmt").value="";
        		document.getElementById("returnCardTypename").value="";
        	}
        	else
        	{
        		if(document.getElementById("changebeforcardno").value=="")
        		{
        			$.ligerDialog.error("请读取退卡卡号!");
        			obj.value="";
        			document.getElementById("returnCardType").value="";
        			document.getElementById("returnCardAmt").value="";
        			document.getElementById("returnCardTypename").value="";
	        		return ;
        		}
        		var requestUrl ="cc014/validateNewCardno.action";
				var params="strCurCompId="+document.getElementById("changecompid").value;
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
	        		document.getElementById("returnCardType").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("returnCardTypename").value=responsetext.curCardinfo.cardtypeName;
	        	}
	        	else
	        	{
	        		document.getElementById("returnCardNo").value="";
        			document.getElementById("returnCardType").value="";
        			document.getElementById("returnCardTypename").value="";
        			document.getElementById("returnCardAmt").value="";
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
    	if(document.getElementById("returnCardNo").value=="")
    	{
    		$.ligerDialog.error("请输入新卡卡号!");
    		return;
    	}
    	if(document.getElementById("returnCardAmt").value*1==0)
    	{
    		$.ligerDialog.error("请输入新卡储值金额!");
    		return;
    	}
    	if(document.getElementById("returnCardAmt").value*1>document.getElementById("deductamt").value*1)
    	{
    		$.ligerDialog.error("新卡储值金额不能超过现有退卡额度!");
    		return;
    	}
    	
    	 $.ligerDialog.confirm('请确认'+document.getElementById("returnCardNo").value+'卡已插入读卡器中!', function (result)
		 {
			if( result==true)
			{     
				var CardControl=parent.document.getElementById("CardCtrl");
				CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
				var inserCardno=CardControl.ReadCard();
				if(checkNull(inserCardno)!="" )
				{
					if( checkNull(inserCardno)==document.getElementById("changebeforcardno").value)
					{
						$.ligerDialog.error("读卡器中的卡不能为退卡老卡!");
						return;
					}
					for (var rowid in commoninfodivdetial_cardPay.records)
					{
							var row =commoninfodivdetial_cardPay.records[rowid];
							if(checkNull(row.cardno)==checkNull(inserCardno))
							{
								$.ligerDialog.error("读卡器中的卡已在老卡列表中存在,请确认!");
								return;
							}
					} 
				}
				var initflag=CardControl.WriteCard(document.getElementById("returnCardNo").value);
				if(initflag==0)
				{
					$.ligerDialog.error('会员卡初始化失败,请确认读卡器状态!');
					return;
				}
				else
				{
					var gridlen=commoninfodivdetial_cardPay.rows.length*1;
					if(gridlen==0)
					{
						itemclick_addpaycard();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivdetial_cardPay.getRow(0).cardno)!="")
					{
						itemclick_addpaycard();
						gridlen=gridlen+1;
					}   
					var curpositionRecord=commoninfodivdetial_cardPay.getRow(gridlen-1);
					commoninfodivdetial_cardPay.updateRow(curpositionRecord,{cardvesting: document.getElementById("changecardfrom").value
															  			    ,cardno : document.getElementById("returnCardNo").value
															  			    ,cardtype : document.getElementById("returnCardType").value
															  			    ,cardtypename : document.getElementById("returnCardTypename").value
															  			    ,membername : document.getElementById("membername").value
															  			    ,memberphone : document.getElementById("memberphone").value
															  			    ,cardamt : document.getElementById("returnCardAmt").value*1});  
					document.getElementById("returnCardNo").value="";
					document.getElementById("returnCardType").value="";
					document.getElementById("returnCardTypename").value="";
					document.getElementById("deductamt").value=ForDight(document.getElementById("deductamt").value*1-document.getElementById("returnCardAmt").value*1,1);
					document.getElementById("keepamtfillamt").value=ForDight(document.getElementById("keepamtfillamt").value*1+document.getElementById("returnCardAmt").value*1,1);
					document.getElementById("returnCardAmt").value="";
				}
			}
		});
    }
    
    function validateSalarytype(obj)
    {
    	if(obj.value==0)
    	{
    		document.getElementById("billinsertype").disabled=true;
    		document.getElementById("firstsalecashamt").readOnly="readOnly"; 
     		document.getElementById("secondsalecashamt").readOnly="readOnly"; 
     		document.getElementById("thirdsalecashamt").readOnly="readOnly"; 
			document.getElementById("fourthsalecashamt").readOnly="readOnly"; 
			document.getElementById("fifthsalecashamt").readOnly="readOnly"; 
			document.getElementById("sixthsalecashamt").readOnly="readOnly"; 
			document.getElementById("seventhsalecashamt").readOnly="readOnly"; 
			document.getElementById("eighthsalecashamt").readOnly="readOnly";
			document.getElementById("firstsalecashamt").style.background="#EDF1F8";
     		document.getElementById("secondsalecashamt").style.background="#EDF1F8";
     		document.getElementById("thirdsalecashamt").style.background="#EDF1F8";
			document.getElementById("fourthsalecashamt").style.background="#EDF1F8";
			document.getElementById("fifthsalecashamt").style.background="#EDF1F8";
			document.getElementById("sixthsalecashamt").style.background="#EDF1F8";
			document.getElementById("seventhsalecashamt").style.background="#EDF1F8";
			document.getElementById("eighthsalecashamt").style.background="#EDF1F8";
			
    	}
    	else
    	{
    		document.getElementById("billinsertype").disabled=false;
    		document.getElementById("firstsalecashamt").readOnly=""; 
     		document.getElementById("secondsalecashamt").readOnly=""; 
     		document.getElementById("thirdsalecashamt").readOnly=""; 
			document.getElementById("fourthsalecashamt").readOnly=""; 
			document.getElementById("fifthsalecashamt").readOnly=""; 
			document.getElementById("sixthsalecashamt").readOnly=""; 
			document.getElementById("seventhsalecashamt").readOnly=""; 
			document.getElementById("eighthsalecashamt").readOnly="";
			document.getElementById("firstsalecashamt").style.background="#FFFFFF";
     		document.getElementById("secondsalecashamt").style.background="#FFFFFF";
     		document.getElementById("thirdsalecashamt").style.background="#FFFFFF";
			document.getElementById("fourthsalecashamt").style.background="#FFFFFF";
			document.getElementById("fifthsalecashamt").style.background="#FFFFFF";
			document.getElementById("sixthsalecashamt").style.background="#FFFFFF";
			document.getElementById("seventhsalecashamt").style.background="#FFFFFF";
			document.getElementById("eighthsalecashamt").style.background="#FFFFFF";
    	}
    }
    
    
    function validateCashfillamt(obj)
    {
    	if(obj.value*1>document.getElementById("deductamt").value*1)
    	{
    		$.ligerDialog.error("退卡总额不能超过现有退卡额度!");
    		return;
    	}
    	document.getElementById("deductamt").value=ForDight(document.getElementById("deductamt").value*1-obj.value*1,1);			
    }
    
    function validateBankfillamt(obj)
    {
    	if(obj.value*1>document.getElementById("deductamt").value*1)
    	{
    		$.ligerDialog.error("退卡总额不能超过现有退卡额度!");
    		return;
    	}
    	document.getElementById("deductamt").value=ForDight(document.getElementById("deductamt").value*1-obj.value*1,1);			
    }