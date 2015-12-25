	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var hzlcmaster = null;
	var commoninfodivdetial_pay=null;//支付方式
	var commoninfodivdetial_payFact=null;//支付方式
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurBillId="";
	var cc013layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var salecooperidmanager = null;
	//var mpackageinfomanager = null;
	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
   	var fourthsalerinidmanager = null;
   	var fifthsaleridmanager = null;
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var eighthsaleridmanager = null;
//   	var paymentmethodmanager = null;
   	var curOldCardRecord=null;
   	var lsStaffinfo=null;
   	var postState=0;
   	var lsMpackageinfo=null;
	var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
	var billChooseData = [{ choose: 0, text: '单据录入' }, { choose: 1, text: '已登记'}, { choose: 2, text: '已审核'}];
    $(function ()
   	{
	   try
	   {
	   		cc013layout= $("#cc013layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
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
                { display: '门店', 		name: 'bsalecompid', 		width:50	,align: 'left' },
                { display: '单号', 		name: 'bsalebillid', 		width:120	,align: 'left' },
                { display: '状态', 		name: 'salebillflag', 		width:85	,align: 'center' , 
	                editor: { type: 'select', data: billChooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.salebillflag == 0) return '单据录入';
	                    else if (item.salebillflag == 1) return '已登记';
	                    else if (item.salebillflag == 2) return '已审核';
	                }
	            }
	            ],  pageSize:20, 
                data: null,      
                width: '260',
                height:height-80,
                enabledEdit: false,  checkbox: false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
            
            hzlcmaster=$("#hzlcmaster").ligerGrid({
            	columns: [
            	{display: '名称', name: 'packagename', width:100, align: 'left'},
                {display: '金额', name: 'packageprice', width:70, align: 'right', type:'float', editor:{type:'float'}},
			    {display: '次数', name: 'salecount', width:50, align: 'right', editor:{type:'int'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 270,
                height:'420',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj){
                    curDetialRecord=data;
                }
            });
            
            commoninfodivdetial_pay=$("#commoninfodivdetial_pay").ligerGrid({
                columns: [
                { display: '支付方式', 	name: 'paymode',  		width:120,align: 'center' ,
	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:120},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.paymode)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
                { display: '支付金额', 	name: 'payamt', 		width:80,align: 'right', editor: { type: 'float' }},
                { display: '支付备注', 	name: 'payremark', 		width:160,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 320,
                height:'150',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false
            });
            
        
        	commoninfodivdetial_payFact=$("#commoninfodivdetial_payFact").ligerGrid({
                columns: [
                { display: '支付方式', 	name: 'paymode',  		width:120,align: 'center' ,
	             	editor: { type: 'select', data: null, valueField: 'choose',selectBoxWidth:120},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("ZFFS",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.paymode)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
                { display: '支付金额', 	name: 'payamt', 		width:80,align: 'right', editor: { type: 'float' }},
                { display: '支付备注', 	name: 'payremark', 		width:160,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 320,
                height:'150',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false
            });
            
            var salecooperidData=JSON.parse(parent.loadCommonControlDate("XMDW",0));
           	//合作项目选择
            salecooperidmanager = 
           		$("#salecooperid").ligerComboBox({ 
           					data: salecooperidData, 
           					isMultiSelect: false,
           					valueFieldID: 'factSalecooperid',
           					width:'120',
           					onSelected: function (newvalue){
           							if(newvalue=="009"||newvalue=="010"){
           								//$("#vipCardNo").hide();
           								//$("#readCurCardInfo").show();
           								$(".hzxk").show();
           								$("#salescardpayment").attr("readonly",true);
           								//document.getElementById("salescardpayment").value=""; 
           								//document.getElementById("paymentamount").value="";
           							}else{
           								//$("#readCurCardInfo").hide();
           								$(".hzxk").hide();
           								//$("#vipCardNo").show();
           								$("#salescardpayment").val(0);
           								//$("#packageno").val("");
           								//document.getElementById("salescardpayment").value=""; 
           								//document.getElementById("paymentamount").value="";
           							}
           						}
           				});	
           	 $("#confirmBillInfo").ligerButton(
	         {
	             text: '审核录入单信息', width: 140,
		         click: function ()
		         {
		             confirmBillInfo();
		         }
	         });
	         
	            document.getElementById("mainToolTable").width= cc013layout.centerWidth*1-cc013layout.leftWidth*1-200;
	            //读取卡号按钮 默认隐藏
	            $("#readCurCardInfo").ligerButton(
	       	         {
	       	             text: '读取卡号', width: 80,
	       		         click: function ()
	       		         {
	       		             readCurCardInfo();
	       		         }
	       	         }); 
	            $("#readCurCardInfo").hide(); 
	            //支付方式
//	            paymentmethod_sj = [{ text: '现金', id: '1' },
//	                                { text: '银行卡', id: '6' }]
//	            paymentmethodmanager= $("#paymentmethod").ligerComboBox({ isShowCheckBox: false,  url:'loadAutoStaff',autocomplete: true,   data: paymentmethod_sj
//					 , valueFieldID: 'paymentmethodid',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true }); 
          	$("#pageloading").hide(); 
            f_selectNode();
            
//            lsZw2=parent.gainCommonInfoByCode("ZFFS",0);
//            for(var i=0;i<lsZw2.length;i++)
//			{
//            	console.log(lsZw2[i].bparentcodekey+"||"+lsZw2[i].parentcodevalue)
//			}
            
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
     	var requestUrl ="cc013/loadMasterinfos.action"; 
		var responseMethod="loadMasterinfosMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,param );
    }
   
   function loadMasterinfosMessage(request)
   {
       	try
        {
 
        	var responsetext = eval("(" + request.responseText + ")");
    
	   		if(responsetext.lsMcooperatesaleinfo!=null && responsetext.lsMcooperatesaleinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcooperatesaleinfo,Total: responsetext.lsMcooperatesaleinfo.length});
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
			//合作项目套餐
			lsMpackageinfo=responsetext.lsMpackageinfo;
			hzlcmaster.options.data=$.extend(true, {},{Rows: lsMpackageinfo,Total: lsMpackageinfo.length});
			hzlcmaster.loadData(true);
			/*var mpackageinfodata = new Array();
			var mpackageinfos = responsetext.lsMpackageinfo;
			for(i = 0;i<mpackageinfos.length;i++){
				var tmp={ "id":mpackageinfos[i].id.packageno, "text": mpackageinfos[i].id.packageno+'_'+mpackageinfos[i].packagename};
				mpackageinfodata.push(tmp);
			}
            /*mpackageinfomanager = 
           		$("#mpackageinfoId").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', autocomplete: true, data: mpackageinfodata
  					 , valueFieldID: 'factcmpackageinfoId',label:'套餐',labelWidth:200,labelAlign:'center',width:'120',selectBoxHeight:'220',selectBoxWidth:'140',alwayShowInDown:true,
  					onSelected: function (newvalue){$("#packageno").val(newvalue);}});*/
		 	loadPayModeDate(responsetext.strSalePayMode);
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
        		 strCurBillId=data.bsalebillid;
       			 var params = "strCurCompId="+data.bsalecompid;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc013/loadCurMcooperatesaleinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");
								document.getElementById("lbBillId").innerHTML=checkNull(action.curMcooperatesaleinfo.id.salebillid);
								document.getElementById("salecompid").value=checkNull(action.curMcooperatesaleinfo.id.salecompid);
								document.getElementById("salebillid").value=checkNull(action.curMcooperatesaleinfo.id.salebillid);
								document.getElementById("saledate").value=checkNull(action.curMcooperatesaleinfo.saledate);
								document.getElementById("saletime").value=checkNull(action.curMcooperatesaleinfo.saletime);
								document.getElementById("slaepaymode").value=checkNull(action.curMcooperatesaleinfo.slaepaymode);
								document.getElementById("salecostproamt").value=checkNull(action.curMcooperatesaleinfo.salecostproamt);
								document.getElementById("salecostcardno").value=checkNull(action.curMcooperatesaleinfo.salecostcardno);
								document.getElementById("salecostcardtype").value=checkNull(action.curMcooperatesaleinfo.salecostcardtype);
								document.getElementById("salecostcardtypename").value=checkNull(action.curMcooperatesaleinfo.salecostcardtypename);
								document.getElementById("membername").value=checkNull(action.curMcooperatesaleinfo.membername);
								document.getElementById("salescardpayment").value=checkNull(action.curMcooperatesaleinfo.salescardpayment);
								
								document.getElementById("paymentamount").value=checkNull(action.curMcooperatesaleinfo.paymentamount);
								var temphone=checkNull(action.curMcooperatesaleinfo.memberphone);
								if(temphone!="")
								{
									document.getElementById("temphone").value=checkNull(temphone.substring(0,3)+"****"+temphone.substring(7,11));
								}
								else
								{
									document.getElementById("temphone").value="";
								}
						   		document.getElementById("memberphone").value=checkNull(temphone);
						   		document.getElementById("salemark").value=checkNull(action.curMcooperatesaleinfo.salemark);
						   		handleRadio("curMcooperatesaleinfo.salebillflag",checkNull(action.curMcooperatesaleinfo.salebillflag)); 
						   		document.getElementById("salecooperid_h").value=checkNull(action.curMcooperatesaleinfo.salecooperid);
						   		salecooperidmanager.selectValue(checkNull(action.curMcooperatesaleinfo.salecooperid));
						   		
						   		//mpackageinfomanager.selectValue(checkNull(action.curMcooperatesaleinfo.packageno));
					
						   		document.getElementById("firstsalerid_h").value=checkNull(action.curMcooperatesaleinfo.firstsalerid);
						   		firstsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.firstsalerid));
						   		document.getElementById("firstsaleamt").value=checkNull(action.curMcooperatesaleinfo.firstsaleamt);
						   		document.getElementById("secondsalerid_h").value=checkNull(action.curMcooperatesaleinfo.secondsalerid);
						   		secondsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.secondsalerid));
						   		document.getElementById("secondsaleamt").value=checkNull(action.curMcooperatesaleinfo.secondsaleamt);
						   		document.getElementById("thirdsalerid_h").value=checkNull(action.curMcooperatesaleinfo.thirdsalerid);
						   		thirdsalerinidmanager.selectValue(checkNull(action.curMcooperatesaleinfo.thirdsalerid));
						   		document.getElementById("thirdsaleamt").value=checkNull(action.curMcooperatesaleinfo.thirdsaleamt);
						   		
						   		document.getElementById("fourthsalerid_h").value=checkNull(action.curMcooperatesaleinfo.fourthsalerid);
						   		fourthsalerinidmanager.selectValue(checkNull(action.curMcooperatesaleinfo.fourthsalerid));
						   		document.getElementById("fourthsaleamt").value=checkNull(action.curMcooperatesaleinfo.fourthsaleamt);
						   
						   		document.getElementById("fifthsalerid_h").value=checkNull(action.curMcooperatesaleinfo.fifthsalerid);
						   		fifthsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.fifthsalerid));
						   		document.getElementById("fifthsaleamt").value=checkNull(action.curMcooperatesaleinfo.fifthsaleamt);
						   		document.getElementById("sixthsalerid_h").value=checkNull(action.curMcooperatesaleinfo.sixthsalerid);
						   		sixthsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.sixthsalerid));
						   		document.getElementById("sixthsaleamt").value=checkNull(action.curMcooperatesaleinfo.sixthsaleamt);
						   		document.getElementById("seventhsalerid_h").value=checkNull(action.curMcooperatesaleinfo.seventhsalerid);
						   		seventhsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.seventhsalerid));
						   		document.getElementById("seventhsaleamt").value=checkNull(action.curMcooperatesaleinfo.seventhsaleamt);
						   				
						   		document.getElementById("eighthsalerid_h").value=checkNull(action.curMcooperatesaleinfo.eighthsalerid);
						   		  			
						   		eighthsaleridmanager.selectValue(checkNull(action.curMcooperatesaleinfo.eighthsalerid));
						  
						   		document.getElementById("eighthsaleamt").value=checkNull(action.curMcooperatesaleinfo.eighthsaleamt);
						   		
						   		if(action.lsDpayinfo!=null && action.lsDpayinfo.length>0)
						   		{
						   			commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: action.lsDpayinfo,Total: action.lsDpayinfo.length});
					            	commoninfodivdetial_pay.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial_pay.loadData(true);  
					            	itemclick_addpay();
					            
						   		}
						   		if(action.lsDpayinfoFact!=null && action.lsDpayinfoFact.length>0)
						   		{
						   			commoninfodivdetial_payFact.options.data=$.extend(true, {},{Rows: action.lsDpayinfoFact,Total: action.lsDpayinfoFact.length});
					            	commoninfodivdetial_payFact.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial_payFact.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial_payFact.loadData(true);  
					            	itemclick_addpayFact();
						   		}
						   		var salebillflag = action.curMcooperatesaleinfo.salebillflag;
						   		readPage(salebillflag);
						   		if(salebillflag!=0){
						   			var params = {strCurCompId: data.bsalecompid, strCurBillId:strCurBillId};
						   			var url = contextURL+"/cc013/queryMedical.action"; 
						   			$.post(url, params, function(data){
						   				var list = data.lsMpackageinfo;
						   				if(list != null && list.length>0){
						   					hzlcmaster.options.data=$.extend(true, {},{Rows: list,Total: list.length});
						   			    }else{
						   			    	hzlcmaster.options.data=$.extend(true, {},{Rows: lsMpackageinfo,Total: lsMpackageinfo.length});
						   				}
						   				hzlcmaster.loadData(true);
						   			}).error(function(e){$.ligerDialog.error(e.message);});
						   		}
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
   			strpaymode[1]="6";
   			strpaymode[2]="15";
   			//strpaymode[1]="2";
   			//strpaymode[2]="6";
   			//strpaymode[3]="4";
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
				commoninfodivdetial_payFact.columns[0].editor.data=JSON.parse(returnValue);
			}
   		}
   	}
   	
   
  
     
     function readPage(billstate)
     {
    	 document.getElementById("account2Amt").value="";
     			if(billstate==0)
				{
					curpagestate=1;
					document.getElementById("salecooperid").readOnly="";
		     		document.getElementById("salecostproamt").readOnly="";
		     		document.getElementById("salecostcardno").readOnly="";
		     		document.getElementById("salemark").readOnly=""; 
		    		document.getElementById("membername").readOnly="";
    				document.getElementById("memberphone").readOnly="";
    				document.getElementById("temphone").readOnly="";
    				document.getElementById("firstsaleamt").readOnly=""; 
     				document.getElementById("secondsaleamt").readOnly=""; 
     				document.getElementById("thirdsaleamt").readOnly=""; 
					document.getElementById("fourthsaleamt").readOnly=""; 
					document.getElementById("fifthsaleamt").readOnly=""; 
					document.getElementById("sixthsaleamt").readOnly=""; 
					document.getElementById("seventhsaleamt").readOnly=""; 
					document.getElementById("eighthsaleamt").readOnly=""; 
					document.getElementById("salescardpayment").readOnly=""; 
					//document.getElementById("paymentamount").readOnly="";
					
			     	commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_pay.loadData(true);   
					commoninfodivdetial_payFact.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_payFact.loadData(true);  
					commoninfodivdetial_pay.options.clickToEdit=true;
			     	commoninfodivdetial_pay.options.enabledEdit=true;
			     	commoninfodivdetial_payFact.options.clickToEdit=false;
			     	commoninfodivdetial_payFact.options.enabledEdit=false;
			     	$("#account2Amt").attr("disabled",false); 
			     	salecooperidmanager.setEnabled();
			     	//mpackageinfomanager.setEnabled();
			     	firstsaleridmanager.setEnabled();
			   		secondsaleridmanager.setEnabled();
			   		thirdsalerinidmanager.setEnabled();
			   		fourthsalerinidmanager.setEnabled();
			   		fifthsaleridmanager.setEnabled();
			   		sixthsaleridmanager.setEnabled();
					seventhsaleridmanager.setEnabled();
					eighthsaleridmanager.setEnabled();
					
					itemclick_addpay();
					itemclick_addpayFact();
					hzlcmaster.options.data=$.extend(true, {},{Rows: lsMpackageinfo,Total: lsMpackageinfo.length});
					hzlcmaster.loadData(true);
		     	}
		       	else if(billstate==1  )
		       	{
		       		curpagestate=2;
		     		document.getElementById("salecooperid").readOnly="readOnly";
		     		document.getElementById("salecostproamt").readOnly="readOnly";
		     		document.getElementById("salecostcardno").readOnly="readOnly";
		     		document.getElementById("salemark").readOnly="readOnly"; 
		     		document.getElementById("membername").readOnly="readOnly";
    				document.getElementById("memberphone").readOnly="readOnly";
    				document.getElementById("temphone").readOnly="readOnly";
    				document.getElementById("firstsaleamt").readOnly="readOnly"; 
     				document.getElementById("secondsaleamt").readOnly="readOnly"; 
     				document.getElementById("thirdsaleamt").readOnly="readOnly"; 
					document.getElementById("fourthsaleamt").readOnly="readOnly"; 
					document.getElementById("fifthsaleamt").readOnly="readOnly"; 
					document.getElementById("sixthsaleamt").readOnly="readOnly"; 
					document.getElementById("seventhsaleamt").readOnly="readOnly"; 
					document.getElementById("eighthsaleamt").readOnly="readOnly";
					document.getElementById("salescardpayment").readOnly="readOnly"; 
					document.getElementById("paymentamount").readOnly="readOnly";
					commoninfodivdetial_payFact.options.clickToEdit=true;
			     	commoninfodivdetial_payFact.options.enabledEdit=true;
			     	commoninfodivdetial_pay.options.clickToEdit=false;
			     	commoninfodivdetial_pay.options.enabledEdit=false;
					salecooperidmanager.setDisabled();
					//mpackageinfomanager.setDisabled();
		     		firstsaleridmanager.setDisabled();
			   		secondsaleridmanager.setDisabled();
			   		thirdsalerinidmanager.setDisabled();
			   		fourthsalerinidmanager.setDisabled();
			   		fifthsaleridmanager.setDisabled();
			   		sixthsaleridmanager.setDisabled();
					seventhsaleridmanager.setDisabled();
					eighthsaleridmanager.setDisabled();
					$("#readCurCardInfo").hide();
					$("#vipCardNo").show();
					$("#account2Amt").attr("disabled",true); 
		     	}
       			else
       			{
       				curpagestate=3;
		     		document.getElementById("salecooperid").readOnly="readOnly";
		     		document.getElementById("salecostproamt").readOnly="readOnly";
		     		document.getElementById("salecostcardno").readOnly="readOnly";
		     		document.getElementById("salemark").readOnly="readOnly"; 
		     		document.getElementById("firstsaleamt").readOnly="readOnly"; 
     				document.getElementById("secondsaleamt").readOnly="readOnly"; 
     				document.getElementById("thirdsaleamt").readOnly="readOnly"; 
					document.getElementById("fourthsaleamt").readOnly="readOnly"; 
					document.getElementById("fifthsaleamt").readOnly="readOnly"; 
					document.getElementById("sixthsaleamt").readOnly="readOnly"; 
					document.getElementById("seventhsaleamt").readOnly="readOnly"; 
					document.getElementById("eighthsaleamt").readOnly="readOnly";
					document.getElementById("salescardpayment").readOnly="readOnly"; 
					document.getElementById("paymentamount").readOnly="readOnly";
					commoninfodivdetial_payFact.options.clickToEdit=true;
			     	commoninfodivdetial_payFact.options.enabledEdit=true;
			     	commoninfodivdetial_pay.options.clickToEdit=false;
			     	commoninfodivdetial_pay.options.enabledEdit=false;
					salecooperidmanager.setDisabled();
					//mpackageinfomanager.setDisabled();
		     		firstsaleridmanager.setDisabled();
			   		secondsaleridmanager.setDisabled();
			   		thirdsalerinidmanager.setDisabled();
			   		fourthsalerinidmanager.setDisabled();
			   		fifthsaleridmanager.setDisabled();
			   		sixthsaleridmanager.setDisabled();
					seventhsaleridmanager.setDisabled();
					eighthsaleridmanager.setDisabled();
					$("#readCurCardInfo").hide();
					$("#vipCardNo").show();
					$("#account2Amt").attr("disabled",true); 
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
   	
   function itemclick_addpayFact()
   	{
   			var i=0;
   			while(i<3)
   			{
	   			var row = commoninfodivdetial_payFact.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
					    //参数3:之前或者之后(非必填)
				commoninfodivdetial_payFact.addRow({ 
					                paymode: "",
					                payamt: "",
					                payremark: ""
					             
					            }, row, false);
				i++;
			}
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
     		var requestUrl ="cc013/searchBill.action";
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
	       	if(responsetext.lsMcooperatesaleinfo!=null && responsetext.lsMcooperatesaleinfo.length>0)
	   		{

	   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcooperatesaleinfo,Total: responsetext.lsMcooperatesaleinfo.length});
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
   	

    function validateCostcardno(obj)
    {
    	if(obj.value=="")
    	{
    
    		document.getElementById("membername").value="";
    		document.getElementById("temphone").value="";
    		document.getElementById("memberphone").value="";
    		document.getElementById("salecostcardtype").value="";
    		document.getElementById("salecostcardtypename").value="";
    		return;
    	}
    	
    	var requestUrl="";
    	requestUrl ="cc013/validateCostCardno.action";
		var params="strCurCompId="+document.getElementById("salecompid").value;
		params=params+"&strCurCardNo="+obj.value;
		var responseMethod="validateCostCardnoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
   
    function validateCostCardnoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		document.getElementById("membername").value=responsetext.curCardinfo.membername;
	        		var temphone=responsetext.curCardinfo.memberphone;
					document.getElementById("temphone").value=checkNull(temphone.substring(0,3)+"****"+temphone.substring(7,11));
    				document.getElementById("memberphone").value=responsetext.curCardinfo.memberphone;
    				document.getElementById("salecostcardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("salecostcardtypename").value=responsetext.curCardinfo.cardtypeName;
	        	}
	        	else
	        	{
	        		document.getElementById("salecostcardno").value="";
	        		document.getElementById("membername").value="";
    				document.getElementById("memberphone").value="";
    				document.getElementById("temphone").value="";
    				document.getElementById("salecostcardtype").value="";
    				document.getElementById("salecostcardtypename").value="";
    				
    				$.ligerDialog.warn(strMessage);
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    function validateSalecostproamt(obj)
    {
    	try
        	{
	        	var totalamt=obj.value*1;
				commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_pay.loadData(true);   
				itemclick_addpay("");
				var payrecord=commoninfodivdetial_pay.getRow(0)
			    commoninfodivdetial_pay.updateRow(payrecord,{paymode:'6',payamt: totalamt});
				changePrice();
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
       		if(document.getElementById("membername").value=="")
       		{
       			$.ligerDialog.warn("请先输入姓名!");
       			postState=0;
	    		return;
       		}
       		if(document.getElementById("memberphone").value=="")
       		{
       			$.ligerDialog.warn("请先输入手机号码!");
       			postState=0;
	    		return;
       		}
       		if($("#factSalecooperid").val()=="")
       		{
       			$.ligerDialog.warn("请选择合作单位!");
       			postState=0;
	    		return;
       		}
       		if(document.getElementById("salecostproamt").value=="")
       		{
       			$.ligerDialog.warn("确认项目额度!");
       			postState=0;
	    		return;
       		}
       		if(document.getElementById("slaepaymode").value=="")
			{
				$.ligerDialog.warn("确认支付方向!");
				postState=0;
	    		return;
			}
       		
			var shareTotalAmt=document.getElementById("firstsaleamt").value*1+document.getElementById("secondsaleamt").value*1+document.getElementById("thirdsaleamt").value*1+
							  document.getElementById("fourthsaleamt").value*1+document.getElementById("fifthsaleamt").value*1+document.getElementById("sixthsaleamt").value*1+
							  document.getElementById("seventhsaleamt").value*1+document.getElementById("eighthsaleamt").value*1 ;
			if(ForDight(shareTotalAmt*1,1) > ForDight(document.getElementById("salecostproamt").value*1,1))  
			{
					$.ligerDialog.error('分享金额不能超项目额度,请确认!');
					postState=0;
					return;
			} 
							
       		if(parent.hasFunctionRights( "CC013",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 postState=0;
        		 return;
        	}
       		
	       	//合作医疗套餐
       		var proNo = $("#factSalecooperid").val();
       		var jsonArr = new Array();
			if(proNo == "009"||proNo == "010"){
				var total=0;
				var salecostproamt=checkNull($("#salecostproamt").val())*1;
				var medical = hzlcmaster.getData();
				$.each(medical, function(i, row){
					var salecount = row.salecount;
					if(checkNull(salecount)!="" && salecount !=0){
						total += ForDight(row.packageprice*1,1);
						jsonArr.push({packageno: row.bpackageno, salecount:row.salecount, saleamt:row.packageprice, itemno:row.bcompid});
					}
				});
				medical=null;
				if(total!=0 && total*1!=salecostproamt){//不填写任何项目次数，则不校验
		    		$.ligerDialog.warn('套餐总金额必须等于项目额度！');
		    		jsonArr=null;
		    		postState=0;
		    		return;
		    	}
			}
			if(curpagestate!=1)
	   		{
	   			$.ligerDialog.warn("非新增单据,不可保存!");
	   			postState=0;
	   			return;
	   		}
	   		else
	   		{
	   	
		   		$.ligerDialog.confirm('确认保存当前合作项目录入单?', function (result)
				{
				    if( result==true)
		           	{
			       		 //------支付方式
						var strJsonParam_detial="";
						var curjosnparam="";
						var needReplaceStr="";
						var paymat=0;
						for (var rowid in commoninfodivdetial_pay.records)
						{
							var row =commoninfodivdetial_pay.records[rowid];
							curjosnparam=JSON.stringify(row);
							/*if(curjosnparam.indexOf("_id")>-1)
							{
							   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
							   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
							}*/	
							paymat=paymat*1+commoninfodivdetial_pay.records[rowid]['payamt']*1;
							         		   
							if(strJsonParam_detial!="")
							  	strJsonParam_detial=strJsonParam_detial+",";
							strJsonParam_detial= strJsonParam_detial+curjosnparam;
						} 
						if(paymat*1!=document.getElementById("salecostproamt").value){
							$.ligerDialog.warn("录入金额与支付金额不一致!");
							postState=0;
				    		return;
						}else {
							for (var rowid in commoninfodivdetial_pay.records)
							{
								var row =commoninfodivdetial_pay.records[rowid];
								if(row.paymode==4){
									$.ligerDialog.warn("此合作项目不能使用储值支付！");
									postState=0;
									return;
								}
							}
							
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
						var requestUrl ="cc013/post.action";
						var params=queryStringTmp;
						if(strJsonParam_detial!="")
						{
							params=params+"&strJsonParam=["+strJsonParam_detial+"]";
						}
						if(jsonArr.length!=0){
							params +="&strJsonParamFact="+JSON.stringify(jsonArr);
						}
						var responseMethod="editMessage";
					    sendRequestForParams_p(requestUrl,responseMethod,params ); 
		     	}else{
		     		postState=0;
		     	}
			}); 
			}
       }
       
        function editMessage(request)
        {
    		
        	try
			{
				postState=0;
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("录入成功,请刷新查看!");
	        		 	readPage(1);
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
		
		function confirmBillInfo()
		{
			if(parent.hasFunctionRights( "CC013",  "UR_SPECIAL_CHECK")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有审核权限,请确认!");
        		 return;
        	}
			if(curpagestate!=2)
	   		{
	   			$.ligerDialog.warn("该单据未保存登记或已被审核,不可此次操作!");
	   			return;
	   		}
	   		var strJsonParam_detial="";
			var curjosnparam="";
			var needReplaceStr="";
			var paymat=0;
			var flag=false;
			for (var rowid in commoninfodivdetial_payFact.records)
			{
				var row =commoninfodivdetial_payFact.records[rowid];
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
					needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
					curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/
				if((checkNull(row.paymode)=="" && checkNull(row.payamt)!="") || (checkNull(row.paymode)!="" && checkNull(row.payamt)=="")){
					flag=true;
					break;
				}
				paymat=paymat*1+commoninfodivdetial_payFact.records[rowid]['payamt']*1;
							         		   
				if(strJsonParam_detial!="")
					strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
			}
			if(flag){
				$.ligerDialog.warn("实际支付方式和金额不能为空!");
	    		return;
			}
			proNo = $("#factSalecooperid").val();
			if(proNo == "009"||proNo == "010"){
				var salescardpayment = $("#salescardpayment").val();
				var paymentamount = $("#paymentamount").val()==""?0:$("#paymentamount").val();
				//console.log(paymat+"||"+salescardpayment+"||"+paymentamount);
				if(paymat*1!=(salescardpayment*1+paymentamount*1)*1){
					$.ligerDialog.warn("录入金额与支付金额不一致!");
					postState=0;
		    		return;
				}
			}else if(paymat*1!=document.getElementById("salecostproamt").value){
						$.ligerDialog.warn("录入金额与最终支付金额不一致!");
				    	return;
			}
			var requestUrl ="cc013/confirm.action";
			var params = "strCurCompId="+document.getElementById("salecompid").value;
       		params =params+ "&strCurBillId="+document.getElementById("salebillid").value;
			if(strJsonParam_detial!="")
			{
					params=params+"&strJsonParamFact=["+strJsonParam_detial+"]";
			}
			var responseMethod="confirmMessage";
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		
		function confirmMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("审核成功,请刷新查看!");
	        		 	readPage(2);
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
		
		function changephone(obj)
		{
			$("#memberphone").val(obj.value);
		}
        
		/**修改添加 2015/6/9*/
		function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				var salecostcardno = document.getElementById("salecostcardno");
				salecostcardno.value=cardNo;
				validateCostcardnoByHZXK(salecostcardno);
	    	}
        } 
		function validateCostcardnoByHZXK(obj)
	    {
	    	if(obj.value=="")
	    	{
	    
	    		document.getElementById("membername").value="";
	    		document.getElementById("temphone").value="";
	    		document.getElementById("memberphone").value="";
	    		document.getElementById("salecostcardtype").value="";
	    		document.getElementById("salecostcardtypename").value="";
	    		return;
	    	}
	    	
	    	var requestUrl="";
	    	requestUrl ="cc013/validateCostCardnoByHZXK.action";
			var params="strCurCompId="+document.getElementById("salecompid").value;
			params=params+"&strCurCardNo="+obj.value;
			var responseMethod="validateCostCardnoMessageByHZXK";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
	    }
	   
	    function validateCostCardnoMessageByHZXK(request)
	    {
	    		
	        	try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	        		 
		        		document.getElementById("membername").value=responsetext.curCardinfo.membername;
		        		var temphone=responsetext.curCardinfo.memberphone;
						document.getElementById("temphone").value=checkNull(temphone.substring(0,3)+"****"+temphone.substring(7,11));
	    				document.getElementById("memberphone").value=responsetext.curCardinfo.memberphone;
	    				document.getElementById("salecostcardtype").value=responsetext.curCardinfo.cardtype;
		        		document.getElementById("salecostcardtypename").value=responsetext.curCardinfo.cardtypeName;
		        		document.getElementById("account2Amt").value=responsetext.curCardinfo.account2Amt;
		        		//折扣
		        		document.getElementById("slaeproerate").value=responsetext.curCardinfo.slaeproerate;
		        	}
		        	else
		        	{
		        		document.getElementById("salecostcardno").value="";
		        		document.getElementById("membername").value="";
	    				document.getElementById("memberphone").value="";
	    				document.getElementById("temphone").value="";
	    				document.getElementById("salecostcardtype").value="";
	    				document.getElementById("salecostcardtypename").value="";
	    				//折扣
	    				document.getElementById("slaeproerate").value=""
	    				$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	    }
	    
	    //变化价格
	    function changePrice(){
	    	proNo = $("#factSalecooperid").val()
	    	if(proNo == "009"||proNo == "010"){
	    		var salecostproamt = $("#salecostproamt").val();//额度
	    		var salescardpayment = $("#salescardpayment");//储值支付
	    		var paymentamount = $("#paymentamount");//现金或银行卡
	    		var slaeproerate = 1;//$("#slaeproerate").val();//储值折扣
	    		//if(paymentamount==null||paymentamount=="")
	    			//paymentamount = 0;
	    		//var salescardpaymentVal = ForDight((salecostproamt - paymentamount)*slaeproerate*1,1);
	    		//salescardpayment.val(salescardpaymentVal);
	    		paymentamount.val(salecostproamt);
	    		//
	    		commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_pay.loadData(true);   
				itemclick_addpay("");
				//var payrecord=commoninfodivdetial_pay.getRow(0)
			    //commoninfodivdetial_pay.updateRow(payrecord,{paymode:'4',payamt: salescardpaymentVal});
				
				var id = $("#paymentmethod").val();
				id=id==""?"6":id;
				var payrecord2=commoninfodivdetial_pay.getRow(0);
			    commoninfodivdetial_pay.updateRow(payrecord2,{paymode:id,payamt: salecostproamt});
	    	}
	    }
		/**修改添加 2015/6/9*/