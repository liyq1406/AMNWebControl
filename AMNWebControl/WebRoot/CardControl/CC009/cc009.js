	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial_pay=null;//支付方式
	var commoninfodivdetial_combin=null;
	var commoninfodivdetial_oldcustomer=null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurBillId="";
	var cc009layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
   	var fifthsaleridmanager = null;
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var curOldCardRecord=null;
   	var lsStaffinfo=null;
   	var readCurCardInfo=null;
   	var showDialogmanager=null;
   	var postState=0;
	var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
	var billChooseData = [{ choose: 0, text: '折扣转卡' }, { choose: 1, text: '收购转卡'}, { choose: 2, text: '竞争转卡'}, { choose: 3, text: '换卡'}, { choose: 6, text: '老卡并老卡'}, { choose: 7, text: '老卡并新卡'}];
    $(function ()
   	{
	   try
	   {
	   		cc009layout= $("#cc009layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
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
                { display: '类型', 		name: 'bchangetype', 			width:60	,align: 'left', 
	                editor: { type: 'select', data: billChooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.bchangetype == 0) return '折扣转卡';
	                    else if (item.bchangetype == 1) return '收购转卡';
	                    else if (item.bchangetype == 2) return '竞争转卡';
	                    else if (item.bchangetype == 3) return '换卡';
	                    else if (item.bchangetype == 6) return '老卡并老卡';
	                    else if (item.bchangetype == 7) return '老卡并新卡';
	                }
	            },
                { display: '老卡卡号', 		name: 'changebeforcardno', 		width:95	,align: 'left' },
                { display: '新卡卡号', 		name: 'changeaftercardno', 		width:95	,align: 'left' },
                { name: 'bchangebillid',  		hide:true},
                { name: 'bchangecompid',  		hide:true}
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
            
            commoninfodivdetial_pay=$("#commoninfodivdetial_pay").ligerGrid({
                columns: [
                { display: '支付方式', 	name: 'paymode',  		width:100,align: 'center' ,
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
                { display: '支付金额', 	name: 'payamt', 		width:60,align: 'right', editor: { type: 'float' }},
                { display: '支付备注', 	name: 'payremark', 		width:100,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 270,
                height:'270',
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                toolbar: { items: [
	                { text: '增加支付方式', click: itemclick_addpay, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }	
            });
            
             commoninfodivdetial_oldcustomer=$("#commoninfodivdetial_oldcustomer").ligerGrid({
                columns: [
                { display: '工号', 	name: 'staffno', 		width:80,align: 'left'},
                { display: '姓名', 	name: 'staffname', 		width:80,align: 'left'},
                { display: '单数', 	name: 'costCount', 		width:40,align: 'right'}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 230,
                height:'270',
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
                toolbar: { items: [
	                { text: '增加分享老客', click: addcustomer, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }	
            });
            
            commoninfodivdetial_combin=$("#commoninfodivdetial_combin").ligerGrid({
                columns: [
                { display: '会员卡号', 	name: 'boldcardno',  		width:100,align: 'center', editor: { type: 'text',onChanged : validateOldCardInfo}},
                { display: '会员姓名', 	name: 'oldcardname', 		width:60,align: 'right'},
                { display: '卡类型', 		name: 'oldcardtypename', 		width:70,align: 'center'},
                { display: '总余额', 		name: 'totalaccountkeepamt', 		width:60,align: 'center'},
                { display: '总欠款', 		name: 'totalaccountdebtamt', 		width:50,align: 'center'},
                { display: '真实名字',name: 'realName',width:1},
                { name: 'oldcardtype',  			hide:true, 		width:1},
                { name: 'curaccountkeepamt',  		hide:true, 		width:1},
                { name: 'curaccountdebtamt',  		hide:true, 		width:1},
                { name: 'proaccountdebtamt',  		hide:true, 		width:1},
                { name: 'proaccountkeepamt',  		hide:true, 		width:1}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 370,
                height:'150',
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curOldCardRecord = data;
                },
                toolbar: { items: [
	                { text: '读取老卡', click: readOldCard, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                }                
            });
            document.getElementById("mainToolTable").width= cc009layout.centerWidth*1-cc009layout.leftWidth*1-180;
            $("#searchBill").ligerButton(
	         {
	             text: '查询单号', width: 100,
		         click: function ()
		         {
		             searchBill();
		         }
	         });
	          $("#confirmCard").ligerButton(
	         {
	             text: '确认转卡', width: 100,
		         click: function ()
		         {
		             handConfirmCard();
		         }
	         });	
            readCurCardInfo= $("#readCurCardInfo").ligerButton(
	         {
	             text: '读取卡号', width: 80,
		         click: function ()
		         {
		             readOldCardInfo();
		         }
	         });
	         if(parent.localCompid=="001")
	         {
	         	addOption("3","会员卡换卡",document.getElementById("changetype"));
	         }
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
     	var requestUrl ="cc009/loadMasterinfos.action"; 
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
	   		    if(strCurCompId==parent.document.getElementById("strCompId").value)
		        {
		        	lsStaffinfo=parent.StaffInfo;
		       
		        }
		        else
		        {
		        	lsStaffinfo=responsetext.lsStaffinfo;
		        }
			/*  var lsStaffSelectData_sj=loadOtherGridByStaffInfo(lsStaffinfo,'',1);
			    var lsStaffSelectData_tr=loadOtherGridByStaffInfo(lsStaffinfo,'',2);

				firstsaleridmanager= $("#firstsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcfirstsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				secondsaleridmanager= $("#secondsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcsecondsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				thirdsalerinidmanager= $("#thirdsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcthirdsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				fifthsaleridmanager= $("#fifthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcfifthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				sixthsaleridmanager= $("#sixthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcsixthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				seventhsaleridmanager= $("#seventhsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcseventhsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				*/
				lsProjectinfo=	responsetext.lsProjectinfo;		
		 
		 	loadPayModeDate(responsetext.strSalePayMode);
		 	itemclick_addcustomer();
		 	document.getElementById("strShareCondition").value=responsetext.strShareCondition;
	 	}
	 	catch(e){alert(e.message);}
    }
    
    
          
    function loadAutoStaff(curmanager,curWriteStaff)
    {
    	if(curmanager.options.valueFieldID == "factcfirstsalerid"
    	|| curmanager.options.valueFieldID == "factcsecondsalerid"
    	|| curmanager.options.valueFieldID == "factcthirdsalerid")
    	{
    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,1));
    	}
    	else
    	{
    		curmanager.setData(loadOtherGridByStaffInfo(lsStaffinfo,curWriteStaff,2));
    	}
    	curmanager.selectBox.show();
    }
    
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 strCurBillId=data.bchangebillid;
       			 var params = "strCurCompId="+data.bchangecompid;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc009/loadCurMcardchangeinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								if(action.strSP114==1)
						   		{
						   			document.getElementById("isxnj").disabled=false;
						   		}
						   		else
						   		{
						   			document.getElementById("isxnj").disabled=true;
						   		}
								document.getElementById("randomno").value="";
								document.getElementById("lbBillId").innerHTML=checkNull(action.curMcardchangeinfo.id.changebillid);
								document.getElementById("changecompid").value=checkNull(action.curMcardchangeinfo.id.changecompid);
								document.getElementById("changebillid").value=checkNull(action.curMcardchangeinfo.id.changebillid);
								document.getElementById("changedate").value=checkNull(action.curMcardchangeinfo.changedate);
								document.getElementById("changetime").value=checkNull(action.curMcardchangeinfo.changetime);
								document.getElementById("changebeforcardno").value=checkNull(action.curMcardchangeinfo.changebeforcardno);
								document.getElementById("changebeforcardtype").value=checkNull(action.curMcardchangeinfo.changebeforcardtype);
								document.getElementById("changebeforcardtypename").value=checkNull(action.curMcardchangeinfo.changebeforcardtypename);
								document.getElementById("membername").value=checkNull(action.curMcardchangeinfo.membername);
						   		document.getElementById("memberphone").value=checkNull(action.curMcardchangeinfo.memberphone);
						   		document.getElementById("isxnj").value=checkNull(action.curMcardchangeinfo.isxnj);
						   		if(checkNull(action.curMcardchangeinfo.membername)!="")
	   								document.getElementById("lbmembername").value=checkNull(action.curMcardchangeinfo.membername).substring(0,1)+"**";
	   							else	
	   								document.getElementById("lbmembername").value="";
	   							if(checkNull(action.curMcardchangeinfo.memberphone)!="" && checkNull(action.curMcardchangeinfo.memberphone).length==11)
	   								document.getElementById("lbmemberphone").value=checkNull(action.curMcardchangeinfo.memberphone).substring(0,3)+"****"+checkNull(action.curMcardchangeinfo.memberphone).substring(7,11);
	   							else
	   								document.getElementById("lbmemberphone").value="";
	   							document.getElementById("bankcostno").value=checkNull(action.curMcardchangeinfo.bankcostno);
						   		document.getElementById("curaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curaccountkeepamt);
						   		document.getElementById("curaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curaccountdebtamt);
						   		document.getElementById("curproaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curproaccountkeepamt);
						   		document.getElementById("curproaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curproaccountdebtamt);
						   		document.getElementById("changeaftercardno").value=checkNull(action.curMcardchangeinfo.changeaftercardno);
						   		document.getElementById("changeaftercardtype").value=checkNull(action.curMcardchangeinfo.changeaftercardtype);
						   		document.getElementById("changeaftercardtypename").value=checkNull(action.curMcardchangeinfo.changeaftercardtypename);
						   		document.getElementById("rechargeremark").value=checkNull(action.curMcardchangeinfo.rechargeremark);
						   		document.getElementById("changetype").value=checkNull(action.curMcardchangeinfo.id.changetype);
						   		document.getElementById("changelowamt").value=checkNull(action.curMcardchangeinfo.changelowamt);
						   		document.getElementById("changefillamt").value=checkNull(action.curMcardchangeinfo.changefillamt);
						   		document.getElementById("firstsalerid").value=checkNull(action.curMcardchangeinfo.firstsalerid);
						   		document.getElementById("billinsertype").value=checkNull(action.curMcardchangeinfo.billinsertype)*1;
								
						   		document.getElementById("firstsalername").value="";
						   		if(document.getElementById("firstsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("firstsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("firstsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 }  		
	   	
						   		//firstsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.firstsalerid));
						   		document.getElementById("firstsaleamt").value=checkNull(action.curMcardchangeinfo.firstsaleamt);
						   		document.getElementById("firstsalecashamt").value=checkNull(action.curMcardchangeinfo.firstsalecashamt);
						   		document.getElementById("secondsalerid").value=checkNull(action.curMcardchangeinfo.secondsalerid);
						   		document.getElementById("secondsalername").value="";
						   		if(document.getElementById("secondsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("secondsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("secondsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 }  	
						   		//secondsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.secondsalerid));
						   		document.getElementById("secondsaleamt").value=checkNull(action.curMcardchangeinfo.secondsaleamt);
						   		document.getElementById("secondsalecashamt").value=checkNull(action.curMcardchangeinfo.secondsalecashamt);
						   		document.getElementById("thirdsalerid").value=checkNull(action.curMcardchangeinfo.thirdsalerid);
						   		document.getElementById("thirdsalername").value="";
						   		if(document.getElementById("thirdsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("thirdsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("thirdsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 } 
						   		//thirdsalerinidmanager.selectValue(checkNull(action.curMcardchangeinfo.thirdsalerid));
						   		document.getElementById("thirdsaleamt").value=checkNull(action.curMcardchangeinfo.thirdsaleamt);
						   		document.getElementById("thirdsalecashamt").value=checkNull(action.curMcardchangeinfo.thirdsalecashamt);
						   		document.getElementById("fifthsalerid").value=checkNull(action.curMcardchangeinfo.fifthsalerid);
						   		document.getElementById("fifthsalername").value="";
						   		if(document.getElementById("fifthsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("fifthsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("fifthsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 }  
						   		//fifthsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.fifthsalerid));
						   		document.getElementById("fifthsaleamt").value=checkNull(action.curMcardchangeinfo.fifthsaleamt);
						   		document.getElementById("fifthsalecashamt").value=checkNull(action.curMcardchangeinfo.fifthsalecashamt);
						   		document.getElementById("sixthsalerid").value=checkNull(action.curMcardchangeinfo.sixthsalerid);
						   		document.getElementById("sixthsalername").value="";
						   		if(document.getElementById("sixthsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("sixthsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("sixthsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 }  
						   		//sixthsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.sixthsalerid));
						   		document.getElementById("sixthsaleamt").value=checkNull(action.curMcardchangeinfo.sixthsaleamt);
						   		document.getElementById("sixthsalecashamt").value=checkNull(action.curMcardchangeinfo.sixthsalecashamt);
						   		document.getElementById("seventhsalerid").value=checkNull(action.curMcardchangeinfo.seventhsalerid);
						   		document.getElementById("seventhsalername").value="";
						   		if(document.getElementById("seventhsalerid").value!="")
						   		{
						   			for(var i=0;i<lsStaffinfo.length;i++)
								    {
								    	if(document.getElementById("seventhsalerid").value==lsStaffinfo[i].bstaffno)
								    	{
								    		document.getElementById("seventhsalername").value=checkNull(lsStaffinfo[i].staffname);
								    	}
								    }
								 }  
						   		document.getElementById("beautyManagerName").value="";
								document.getElementById("consultantName").value="";
								document.getElementById("consultantName1").value="";
						   		document.getElementById("beautyManager").value=checkNull(action.curMcardchangeinfo.beautyManager);
								document.getElementById("consultant").value=checkNull(action.curMcardchangeinfo.consultant);
								document.getElementById("consultant1").value=checkNull(action.curMcardchangeinfo.consultant1);
								
								for(var i=0;i<lsStaffinfo.length;i++)
						   		{
						   			if(document.getElementById("beautyManager").value!="" 
									  	&& document.getElementById("beautyManager").value==lsStaffinfo[i].bstaffno)
									{
									    document.getElementById("beautyManagerName").value=lsStaffinfo[i].staffname;
									}
								    if(document.getElementById("consultant").value!="" 
									  	&& document.getElementById("consultant").value==lsStaffinfo[i].bstaffno)
									{
									    document.getElementById("consultantName").value=lsStaffinfo[i].staffname;
									}
								    if(document.getElementById("consultant1").value!="" 
									  	&& document.getElementById("consultant1").value==lsStaffinfo[i].bstaffno)
									{
									    document.getElementById("consultantName1").value=lsStaffinfo[i].staffname;
									}
						   		}
								
						   		//seventhsaleridmanager.selectValue(checkNull(action.curMcardchangeinfo.seventhsalerid));
						   		document.getElementById("seventhsaleamt").value=checkNull(action.curMcardchangeinfo.seventhsaleamt);
						   		document.getElementById("seventhsalecashamt").value=checkNull(action.curMcardchangeinfo.seventhsalecashamt);
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
						   		if(action.lsDcardchangeinfo!=null && action.lsDcardchangeinfo.length>0)
						   		{
						   			commoninfodivdetial_combin.options.data=$.extend(true, {},{Rows: action.lsDcardchangeinfo,Total: action.lsDcardchangeinfo.length});
					            	commoninfodivdetial_combin.loadData(true);      
					            	//commoninfodivdetial_combin.select(0);      	
						   		}
						   		else
						   		{
						   			commoninfodivdetial_combin.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial_combin.loadData(true);  
					            	itemclick_addoldcard();
					            	//commoninfodivdetial_combin.select(0);     
						   		}
						   		readPage(action.curMcardchangeinfo.billflag);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
   	function validateInserType(obj)
   	{
   		if(obj.value==3)
   		{
   			document.getElementById("firstsalecashamt").value="0";
   			document.getElementById("secondsalecashamt").value="0";
   			document.getElementById("thirdsalecashamt").value="0";
   			document.getElementById("fifthsalecashamt").value="0";
   			document.getElementById("sixthsalecashamt").value="0";
   			document.getElementById("seventhsalecashamt").value="0";
   			
   			document.getElementById("firstsalecashamt").readOnly="";
   			document.getElementById("secondsalecashamt").readOnly="";
   			document.getElementById("thirdsalecashamt").readOnly="";
   			document.getElementById("fifthsalecashamt").readOnly="";
   			document.getElementById("sixthsalecashamt").readOnly="";
   			document.getElementById("seventhsalecashamt").readOnly="";
   			
   			document.getElementById("firstsalecashamt").style.background="#FFFFFF";
   			document.getElementById("secondsalecashamt").style.background="#FFFFFF";
   			document.getElementById("thirdsalecashamt").style.background="#FFFFFF";
   			document.getElementById("fifthsalecashamt").style.background="#FFFFFF";
   			document.getElementById("sixthsalecashamt").style.background="#FFFFFF";
   			document.getElementById("seventhsalecashamt").style.background="#FFFFFF";
   		}
   		else
   		{
   			document.getElementById("firstsalecashamt").value="0";
   			document.getElementById("secondsalecashamt").value="0";
   			document.getElementById("thirdsalecashamt").value="0";
   			document.getElementById("fifthsalecashamt").value="0";
   			document.getElementById("sixthsalecashamt").value="0";
   			document.getElementById("seventhsalecashamt").value="0";
   			document.getElementById("firstsalecashamt").readOnly="readOnly";
   			document.getElementById("secondsalecashamt").readOnly="readOnly";
   			document.getElementById("thirdsalecashamt").readOnly="readOnly";
   			document.getElementById("fifthsalecashamt").readOnly="readOnly";
   			document.getElementById("sixthsalecashamt").readOnly="readOnly";
   			document.getElementById("seventhsalecashamt").readOnly="readOnly";
   			
   			document.getElementById("firstsalecashamt").style.background="#EDF1F8";
   			document.getElementById("secondsalecashamt").style.background="#EDF1F8";
   			document.getElementById("thirdsalecashamt").style.background="#EDF1F8";
   			document.getElementById("fifthsalecashamt").style.background="#EDF1F8";
   			document.getElementById("sixthsalecashamt").style.background="#EDF1F8";
   			document.getElementById("seventhsalecashamt").style.background="#EDF1F8";
   		}
   		if(obj.value!="1")
   		{
   			document.getElementById("isxnj").value="0";
   		}
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
   			strpaymode=strSalePayMode.split(";");
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
							if(type==1)//业务人员
							{
								if(	StaffInfo[i].position!="00901" &&
									StaffInfo[i].position!="00902" &&
									StaffInfo[i].position!="00903" &&
									StaffInfo[i].position!="00904" &&
									StaffInfo[i].position!="008" 
								)
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
							}
							else
							{
								if(	StaffInfo[i].position=="00901" ||
									StaffInfo[i].position=="00902" ||
									StaffInfo[i].position=="00903" ||
									StaffInfo[i].position=="00904" ||
									StaffInfo[i].position=="008" 
								)
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
							}
							
							
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
	
 	 function  itemclick_addcustomer()
        {
        	var row = commoninfodivdetial_oldcustomer.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_oldcustomer.addRow({ 
				                staffno: "",
				                staffname: "",
				                costCount:0
				            }, row, false);
        }
        
        
        function addcustomer()
        {
        	addStaffNo="";
   			addstaffName="";
        	addcustomerDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC009/handShareStaffno.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '新增美发部卡金分享人' });
        
        }
        
         function handInsertShareMessage(request)
        {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		var gridlen=commoninfodivdetial_oldcustomer.rows.length*1;
					if(gridlen==0)
					{
						itemclick_addcustomer();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivdetial_oldcustomer.getRow(0).staffno)!="")
					{
						itemclick_addcustomer();
						gridlen=gridlen+1;
					}   
					var curCustomerDetialRecord=commoninfodivdetial_oldcustomer.getRow(gridlen-1);
					commoninfodivdetial_oldcustomer.updateRow(curCustomerDetialRecord,{staffno: addStaffNo
															  ,staffname : addstaffName
															  ,costCount  : 0});  
	        		addcustomerDialog.close();
	        	}
	        	else
	        	{
	        		$.ligerDialog.error(strMessage);
	        	}
	        	addStaffNo="";
   				addstaffName="";
        	}
			catch(e)
			{
				alert(e.message);
			}
        }
     
     function readPage(billstate)
     {
     	   var  changetype= document.getElementById("changetype");
     	   if(changetype.value==6 || changetype.value==7)
   			{
   				readCurCardInfo.setValue("目标卡号");
   				//document.getElementById("lboldcardtype").innerHTML="目标卡类型"
   				document.getElementById("newCardTr").style.display="none";
   				document.getElementById("oldcarddetial_id").style.display="block";
   			}
   			else
   			{
   				readCurCardInfo.setValue("原卡卡号")
   				//document.getElementById("lboldcardtype").innerHTML="原卡类型"
   				document.getElementById("newCardTr").style.display="block";
   				document.getElementById("oldcarddetial_id").style.display="none";
   			}
   			if(changetype.value==2 )
   			{
   				document.getElementById("lbmembername").readOnly=""; 
     			document.getElementById("lbmemberphone").readOnly=""; 
   			}
   			else
   			{
   				document.getElementById("lbmembername").readOnly="readOnly"; 
     			document.getElementById("lbmemberphone").readOnly="readOnly"; 
   			}
   		
		if(billstate==0)
		{
			curpagestate=1;
			
     		document.getElementById("changeaftercardno").readOnly="";
     		document.getElementById("changefillamt").readOnly=""; 
     		document.getElementById("firstsaleamt").readOnly=""; 
     		document.getElementById("secondsaleamt").readOnly=""; 
     		document.getElementById("thirdsaleamt").readOnly=""; 
     		
     		document.getElementById("firstsalecashamt").readOnly=""; 
     		document.getElementById("secondsalecashamt").readOnly=""; 
     		document.getElementById("thirdsalecashamt").readOnly=""; 
     		
     		
    		document.getElementById("combinlowamt").value="0";
			commoninfodivdetial_combin.options.data=$.extend(true, {},{Rows: null,Total: 0});
			commoninfodivdetial_combin.loadData(true);   
			//commoninfodivdetial_combin.options.clickToEdit=true;
	     	//commoninfodivdetial_combin.options.enabledEdit=true;
	     	
	     	commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
			commoninfodivdetial_pay.loadData(true);   
			commoninfodivdetial_pay.options.clickToEdit=true;
	     	commoninfodivdetial_pay.options.enabledEdit=true;
	   
	     	/*firstsaleridmanager.setEnabled();
	   		secondsaleridmanager.setEnabled();
	   		thirdsalerinidmanager.setEnabled();
	   		fifthsaleridmanager.setEnabled();
	   		sixthsaleridmanager.setEnabled();
			seventhsaleridmanager.setEnabled();*/
			document.getElementById("firstsalerid").value="";
	     	document.getElementById("firstsalerid").readOnly="";
	     	document.getElementById("secondsalerid").value="";
	     	document.getElementById("secondsalerid").readOnly="";
	     	document.getElementById("thirdsalerid").value="";
	     	document.getElementById("thirdsalerid").readOnly="";
	     	document.getElementById("fifthsalerid").value="";
	     	document.getElementById("fifthsalerid").readOnly="";
	     	document.getElementById("sixthsalerid").value="";
	     	document.getElementById("sixthsalerid").readOnly="";
	     	document.getElementById("seventhsalerid").value="";
	     	document.getElementById("seventhsalerid").readOnly="";
			itemclick_addpay();
			itemclick_addoldcard();
     	}
       	if(billstate==1)
       	{
       		curpagestate=3;
     		
     		document.getElementById("changeaftercardno").readOnly="readOnly";
     		document.getElementById("changebeforcardno").readOnly="readOnly";
     		document.getElementById("changefillamt").readOnly="readOnly"; 
     		document.getElementById("firstsaleamt").readOnly="readOnly"; 
     		document.getElementById("secondsaleamt").readOnly="readOnly"; 
     		document.getElementById("thirdsaleamt").readOnly="readOnly"; 


			document.getElementById("firstsalecashamt").readOnly="readOnly"; 
     		document.getElementById("secondsalecashamt").readOnly="readOnly"; 
     		document.getElementById("thirdsalecashamt").readOnly="readOnly"; 
     		
     		/*firstsaleridmanager.setDisabled();
	   		secondsaleridmanager.setDisabled();
	   		thirdsalerinidmanager.setDisabled();
	   		fifthsaleridmanager.setDisabled();
	   		sixthsaleridmanager.setDisabled();
			seventhsaleridmanager.setDisabled();*/
			document.getElementById("firstsalerid").readOnly="readOnly";
	     	document.getElementById("secondsalerid").readOnly="readOnly";
	     	document.getElementById("thirdsalerid").readOnly="readOnly";
	     	document.getElementById("fifthsalerid").readOnly="readOnly";
	     	document.getElementById("sixthsalerid").readOnly="readOnly";
	     	document.getElementById("seventhsalerid").readOnly="readOnly";
	     	commoninfodivdetial_pay.options.clickToEdit=false;
	     	commoninfodivdetial_pay.options.enabledEdit=false;
	     	//commoninfodivdetial_combin.options.clickToEdit=false;
	     	//commoninfodivdetial_combin.options.enabledEdit=false;
     	}
     }
     
     
  
   	
   	function itemclick_addpay()
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
   	}
   	
   	function itemclick_addoldcard()
   	{
   			if(commoninfodivdetial_combin.rows.length*1>0 && checkNull(curOldCardRecord.boldcardno)=="")
    		{
    			return ;
    		}
    		var row = commoninfodivdetial_combin.getSelectedRow();
    		var detiallength=0;
    		if(commoninfodivdetial_combin.rows.length*1==0)
    		{
    			detiallength=-1;
    		}
    		else
    		{
    			detiallength=row['__index']*1;
    		}
   			
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_combin.addRow({ 
				                boldcardno: "",
				                oldcardname: "",
				                oldcardtype: "",
				                oldcardtypename: "",
				                totalaccountkeepamt: "",
				                totalaccountdebtamt: "",
				             	curaccountkeepamt: "",
				                curaccountdebtamt: "",
				                proaccountkeepamt: "",
				                proaccountdebtamt: ""
				            }, row, false);
		  commoninfodivdetial_combin.select(detiallength*1+1);
   	}
   
   	
    function validateCount(obj)
   	{
   		if(obj.value=="" || obj.value==0)
   		{
   			obj.value=1;
   		}
   		var oneprice=curDetialRecord.packageproprice;

   		commoninfodivdetial.updateRow(curDetialRecord,{packageproamt:ForDight(oneprice*1*obj.value*1,1)}); 
	      
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
     		var requestUrl ="cc009/searchBill.action";
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
   	
   	function changeType(obj)
   	{
   			if(obj.value==6 || obj.value==7)
   			{
   				readCurCardInfo.setValue("目标卡号");
   				//document.getElementById("lboldcardtype").innerHTML="目标卡类型"
   				document.getElementById("newCardTr").style.display="none";
   				document.getElementById("oldcarddetial_id").style.display="block";
   			}
   			else
   			{
   				readCurCardInfo.setValue("原卡卡号");
   				//document.getElementById("lboldcardtype").innerHTML="原卡类型"
   				document.getElementById("newCardTr").style.display="block";
   				document.getElementById("oldcarddetial_id").style.display="none";
   			}
   			
   			if(obj.value==1 ||obj.value==2 ||obj.value==3 || obj.value==7 )
   			{
   				
   				document.getElementById("changebeforcardno").readOnly=""; 
   				document.getElementById("lbmembername").readOnly=""; 
     			document.getElementById("lbmemberphone").readOnly=""; 
     			document.getElementById("curaccountkeepamt").readOnly="";
    			document.getElementById("curaccountdebtamt").readOnly="";
   			}
   			else
   			{
   				document.getElementById("changebeforcardno").readOnly="readOnly"; 
   				document.getElementById("lbmembername").readOnly="readOnly"; 
     			document.getElementById("lbmemberphone").readOnly="readOnly";
     			document.getElementById("curaccountkeepamt").readOnly="readOnly";
    			document.getElementById("curaccountdebtamt").readOnly="readOnly"; 
   			}
   			if(obj.value==3 )
   			{
   				document.getElementById("changefillamt").readOnly="readOnly"; 
   			}
   			else
   			{
   				document.getElementById("changefillamt").readOnly=""; 
   			}
   			document.getElementById("randomno").value="";
   			document.getElementById("changebeforcardno").value="";
	      	document.getElementById("changebeforcardtype").value="";
	      	document.getElementById("changebeforcardtypename").value="";
	      	document.getElementById("membername").value="";
    		document.getElementById("memberphone").value="";
	      	document.getElementById("lbmembername").value="";
    		document.getElementById("lbmemberphone").value="";
    		document.getElementById("curaccountkeepamt").value="";
    		document.getElementById("curaccountdebtamt").value="";
    		document.getElementById("curproaccountkeepamt").value="";
    		document.getElementById("curproaccountdebtamt").value="";
    		document.getElementById("combinlowamt").value="0";	
    		commoninfodivdetial_combin.options.data=$.extend(true, {},{Rows: null,Total: 0});
			commoninfodivdetial_combin.loadData(true);  
			itemclick_addoldcard();
   	}
   	
   	  function readOldCardInfo()
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
        
    function validateChangebeforcardno(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("changebeforcardtype").value="";
    		document.getElementById("changebeforcardtypename").value="";
    		document.getElementById("membername").value="";
    		document.getElementById("memberphone").value="";
    		document.getElementById("lbmembername").value="";
    		document.getElementById("lbmemberphone").value="";
    		document.getElementById("curaccountkeepamt").value="";
    		document.getElementById("curaccountdebtamt").value="";
    		document.getElementById("curproaccountkeepamt").value="";
    		document.getElementById("curproaccountdebtamt").value="";
    		document.getElementById("combinlowamt").value="0";
    		return;
    	}
    	if(document.getElementById("changetype").value==2)
    	{
    		return;
    	}
    	var requestUrl="";
    	if(document.getElementById("changetype").value==7)
    	{
    		 requestUrl ="cc009/validateGsCardno.action";
    		 
    	}
    	else
    	{
    		requestUrl ="cc009/validateOldCardno.action";
    	}
    	//并卡
    	if(document.getElementById("changetype").value==6 || document.getElementById("changetype").value==7)
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
				if(existsflag>0)
				{
					$.ligerDialog.warn("输入卡号已在老卡列表中存在!");
					obj.value="";
					document.getElementById("changebeforcardtype").value="";
    				document.getElementById("changebeforcardtypename").value="";
    				document.getElementById("membername").value="";
    				document.getElementById("memberphone").value="";
    				document.getElementById("lbmembername").value="";
    				document.getElementById("lbmemberphone").value="";
    				document.getElementById("curaccountkeepamt").value="";
    				document.getElementById("curaccountdebtamt").value="";
    				document.getElementById("curproaccountkeepamt").value="";
    				document.getElementById("curproaccountdebtamt").value="";
    				document.getElementById("combinlowamt").value="0";
    				return;
				}
    	}
    
    	
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurCardNo="+obj.value;
		params=params+"&iCurChangeType="+document.getElementById("changetype").value;
		var responseMethod="validateOldCardnoMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
   
    function validateOldCardnoMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        
	        		if(document.getElementById("changetype").value=="0"		 
	        		|| document.getElementById("changetype").value=="1"	
	        		|| document.getElementById("changetype").value=="2"	
	        		|| document.getElementById("changetype").value=="3"	)
	        		{
	        			$.ligerDialog.success("请插入新卡卡号");
	        		}
	        		document.getElementById("changebeforcardtype").value=responsetext.curCardinfo.cardtype;
	        		document.getElementById("changebeforcardtypename").value=responsetext.curCardinfo.cardtypeName;
	        		document.getElementById("membername").value=responsetext.curCardinfo.membername;
    				document.getElementById("memberphone").value=responsetext.curCardinfo.memberphone;
    				
    				if(checkNull(responsetext.curCardinfo.membername)!="")
	   					document.getElementById("lbmembername").value=checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**";
	   				else	
	   					document.getElementById("lbmembername").value="";
	   				if(checkNull(responsetext.curCardinfo.memberphone)!="" && checkNull(responsetext.curCardinfo.memberphone).length==11)
	   					document.getElementById("lbmemberphone").value=checkNull(responsetext.curCardinfo.memberphone).substring(0,3)+"****"+checkNull(responsetext.curCardinfo.memberphone).substring(7,11);
	   				else
	   					document.getElementById("lbmemberphone").value="";
	   				
    				if(document.getElementById("changetype").value==1)
    				{
    					document.getElementById("curaccountkeepamt").value=responsetext.curCardinfo.account5Amt;
    					document.getElementById("curaccountdebtamt").value=responsetext.curCardinfo.account5debtAmt;
    				}
    				else
    				{
    					document.getElementById("curaccountkeepamt").value=responsetext.curCardinfo.account2Amt;
    					document.getElementById("curaccountdebtamt").value=responsetext.curCardinfo.account2debtAmt;
    				}
    				document.getElementById("curproaccountkeepamt").value=responsetext.curCardinfo.account4Amt;
    				document.getElementById("curproaccountdebtamt").value=responsetext.curCardinfo.account4debtAmt;
    				document.getElementById("curCardSaleCardAmt").value=responsetext.curCardinfo.DSaleLowAmt;
    				if(document.getElementById("changetype").value==6 )
    				{
    					document.getElementById("combinlowamt").value=checkNull(responsetext.curCardinfo.DFillLowAmt);
    				}
    				else if(document.getElementById("changetype").value==7 )
    				{
    					document.getElementById("combinlowamt").value=checkNull(responsetext.curCardinfo.DSaleLowAmt);
    				}
    				else
    				{
    						document.getElementById("combinlowamt").value="0";
    				}
    				if(responsetext.lsStaffinfoSimple!=null && responsetext.lsStaffinfoSimple.length>0)
					{
						commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: responsetext.lsStaffinfoSimple,Total: responsetext.lsStaffinfoSimple.length});
						commoninfodivdetial_oldcustomer.loadData(true);            	
					}
					else
					{
						commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: null,Total: 0});
						commoninfodivdetial_oldcustomer.loadData(true);
						itemclick_addcustomer();
					}
	        	}
	        	else
	        	{
	        		document.getElementById("changebeforcardno").value="";
	        		document.getElementById("changebeforcardtype").value="";
	        		document.getElementById("changebeforcardtypename").value="";
	        	 	document.getElementById("membername").value="";
    				document.getElementById("memberphone").value="";
    				document.getElementById("lbmembername").value="";
    				document.getElementById("lbmemberphone").value="";
    				document.getElementById("curaccountkeepamt").value="";
    				document.getElementById("curaccountdebtamt").value="";
    				document.getElementById("curproaccountkeepamt").value="";
    				document.getElementById("curproaccountdebtamt").value="";
    				document.getElementById("combinlowamt").value="0";
	        		$.ligerDialog.warn(strMessage);
	        		commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: null,Total: 0});
					commoninfodivdetial_oldcustomer.loadData(true);
					itemclick_addcustomer();
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
      
      
    function readOldCard()
    {
    	var CardControl=parent.document.getElementById("CardCtrl");
		CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
		validateOldCardInfo(CardControl.ReadCard());
    }
      
      
    function validateOldCardInfo(cardNo)
    {
    
    	if(cardNo=="")
    	{
    		commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
			return;
    	}
    	else if (cardNo==document.getElementById("changebeforcardno").value)
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
					if(row.boldcardno==cardNo)
					{
						existsflag=existsflag+1;
					}
				} 
				if(existsflag>0)
				{
					$.ligerDialog.warn("输入卡号已在老卡列表中存在!");
    				commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
					return;
				}
    			var  requestUrl ="cc009/validateOldCardno.action";
    			var params="strCurCompId="+document.getElementById("changecompid").value;
				params=params+"&strCurCardNo="+cardNo;
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
							if(checkNull(row.realName)!="" && checkNull(row.realName)!=checkNull(responsetext.curCardinfo.membername))
							{
								$.ligerDialog.warn("此会员卡号与并卡列表会员姓名不一致,请核对后输入!");
    							commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:'',oldcardname: '',oldcardtype:'',oldcardtypename:'',totalaccountkeepamt:'',totalaccountdebtamt:'',curaccountkeepamt:'',curaccountdebtamt:'',proaccountdebtamt:'',proaccountkeepamt:''});
								return;
							}
						} 
	        		}
	        		if(checkNull(responsetext.curCardinfo.cardsource)==1)  //收购卡
	        		{
	        		 	commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:checkNull(responsetext.curCardinfo.id.cardno),
	        		 														oldcardname: checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**",
	        															   oldcardtype: checkNull(responsetext.curCardinfo.cardtype),
	        															   oldcardtypename: checkNull(responsetext.curCardinfo.cardtypeName),
																		   totalaccountkeepamt: ForDight(checkNull(responsetext.curCardinfo.account5Amt)*1,1),
																		   totalaccountdebtamt: ForDight(checkNull(responsetext.curCardinfo.account5debtAmt)*1,1),
																		   curaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account5Amt),1),
																		   curaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account5debtAmt),1),
																		   proaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account4debtAmt),1),
																		   proaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account4Amt),1)});
					}
					else
					{
						commoninfodivdetial_combin.updateRow(curOldCardRecord,{boldcardno:checkNull(responsetext.curCardinfo.id.cardno),
																			oldcardname: checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**",
	        															   oldcardtype: checkNull(responsetext.curCardinfo.cardtype),
	        															   realName:checkNull(responsetext.curCardinfo.membername),
	        															   oldcardtypename: checkNull(responsetext.curCardinfo.cardtypeName),
																		   totalaccountkeepamt: ForDight(checkNull(responsetext.curCardinfo.account2Amt)*1,1),
																		   totalaccountdebtamt: ForDight(checkNull(responsetext.curCardinfo.account2debtAmt)*1,1),
																		   curaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account2Amt),1),
																		   curaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account2debtAmt),1),
																		   proaccountdebtamt:ForDight(checkNull(responsetext.curCardinfo.account4debtAmt),1),
																		   proaccountkeepamt:ForDight(checkNull(responsetext.curCardinfo.account4Amt),1)});
					}
					companylowAmt();
					itemclick_addoldcard();
					if(responsetext.lsStaffinfoSimple!=null && responsetext.lsStaffinfoSimple.length>0)
					{
						for(var i=0;i<responsetext.lsStaffinfoSimple.length;i++)
						{
							var gridlen=commoninfodivdetial_oldcustomer.rows.length*1;
							if(gridlen==0)
							{
								itemclick_addcustomer();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_oldcustomer.getRow(0).staffno)!="")
							{
								itemclick_addcustomer();
								gridlen=gridlen+1;
							}   
							var curCustomerDetialRecord=commoninfodivdetial_oldcustomer.getRow(gridlen-1);
							commoninfodivdetial_oldcustomer.updateRow(curCustomerDetialRecord,{staffno: responsetext.lsStaffinfoSimple[i].staffno
															  ,staffname : responsetext.lsStaffinfoSimple[i].staffname
															  ,costCount  : responsetext.lsStaffinfoSimple[i].costCount}); 
						}
						            	
					}
					
					
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
    	var requestUrl ="cc009/validateGsCardno.action";
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
				    document.getElementById("cardsendamtflag").value=checkNull(responsetext.curCardinfo.sendamtflag);
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
				commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_pay.loadData(true);   
				itemclick_addpay("");
				var payrecord=commoninfodivdetial_pay.getRow(0)
			    commoninfodivdetial_pay.updateRow(payrecord,{paymode:'6',payamt: totalamt});
		    }
		    catch(e)
		    {
		    	alert(e.message);
		    }
       }
       
       function editCurRecord()
       {
       		if(document.getElementById("changebeforcardno").value=="")
       		{
       			$.ligerDialog.warn("请先输入老卡卡号!");
	    		return;
       		}
       		if(document.getElementById("changetype").value!=6  && document.getElementById("changetype").value!=7 )
       		{
	       		if(document.getElementById("changeaftercardno").value=="")
	       		{
	       			$.ligerDialog.warn("请先输入新卡卡号!");
		    		return;
	       		}
       		}
       		if(document.getElementById("changetype").value==3)
       		{
       			if(document.getElementById("changebeforcardtype").value!=document.getElementById("changeaftercardtype").value)
       			{
       				$.ligerDialog.warn("换卡老卡类型必须与新卡类型一致!");
	    			return;
       			}
       		}
       		if(document.getElementById("strShareCondition").value=="1"
			&& document.getElementById("billinsertype").value=="0")
			{
				$.ligerDialog.error('请选择充值主办方!');
				return;
			}
       		
	    	if(document.getElementById("changetype").value==7 || document.getElementById("changetype").value==6)
	    	{
	    		if( commoninfodivdetial_combin.rows.length==0 
	    		|| (commoninfodivdetial_combin.rows.length==1  && checkNull(curOldCardRecord.boldcardno)==""))
	    		{
	    			$.ligerDialog.error("请先在右下方读取老卡列表,再插入或读取目标卡!");
	    			obj.value="";
	    			return;
	    		}
	    		
	    	}
    	
       		if(ForDight(document.getElementById("changefillamt").value*1,1)<ForDight(document.getElementById("changelowamt").value*1,1))  
			{
					$.ligerDialog.error('转卡额度不能低于最低充值额度,请确认!');
					return;
			} 
       		if(document.getElementById("fifthsaleamt").value*1>0 && document.getElementById("fifthsaleamt").value*1!=document.getElementById("firstsaleamt").value)
			{
				if(document.getElementById("fifthsaleamt").value*1!=10000 ||document.getElementById("firstsaleamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第一销售分享金额不匹配");
					return true;
				}
			}
			if(document.getElementById("sixthsaleamt").value*1>0 && document.getElementById("sixthsaleamt").value*1!=document.getElementById("secondsaleamt").value)
			{
				if(document.getElementById("sixthsaleamt").value*1!=10000 ||document.getElementById("secondsaleamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第二销售分享金额不匹配");
					return true;
				}
			}
							
			if(document.getElementById("seventhsaleamt").value*1>0 && document.getElementById("seventhsaleamt").value*1!=document.getElementById("thirdsaleamt").value)
			{
				if(document.getElementById("seventhsaleamt").value*1!=10000 ||document.getElementById("thirdsaleamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第三销售分享金额不匹配");
					return true;
				}
			}
		
			
			if(document.getElementById("fifthsalecashamt").value*1>0 && document.getElementById("fifthsalecashamt").value*1!=document.getElementById("firstsalecashamt").value)
			{
				if(document.getElementById("fifthsalecashamt").value*1!=10000 ||document.getElementById("firstsalecashamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第一销售分享业绩不匹配");
					return true;
				}
			}
			if(document.getElementById("sixthsalecashamt").value*1>0 && document.getElementById("sixthsalecashamt").value*1!=document.getElementById("secondsalecashamt").value)
			{
				if(document.getElementById("sixthsalecashamt").value*1!=10000 ||document.getElementById("secondsalecashamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第二销售分享业绩不匹配");
					return true;
				}
			}
							
			if(document.getElementById("seventhsalecashamt").value*1>0 && document.getElementById("seventhsalecashamt").value*1!=document.getElementById("thirdsalecashamt").value)
			{
				if(document.getElementById("seventhsalecashamt").value*1!=10000 ||document.getElementById("thirdsalecashamt").value*1<10000)
				{
					$.ligerDialog.error("烫染师与第三销售分享业绩不匹配");
					return true;
				}
			}
			
			if(document.getElementById("strShareCondition").value=="1")
			{
				var hairTotalAmt=0;
				var beatyTotalAmt=0;
								
				for(var i=0;i<lsStaffinfo.length;i++)
			    {
			    	if(document.getElementById("firstsalerid").value==lsStaffinfo[i].bstaffno)
			    	{
			    		if(lsStaffinfo[i].department=="004")
			    			hairTotalAmt=hairTotalAmt*1+document.getElementById("firstsaleamt").value*1;
			    		else if(lsStaffinfo[i].department=="003")
			    			beatyTotalAmt=beatyTotalAmt*1+document.getElementById("firstsaleamt").value*1;
			    	}
			    					
			    	if(document.getElementById("secondsalerid").value==lsStaffinfo[i].bstaffno)
			    	{
			    		if(lsStaffinfo[i].department=="004")
			    			hairTotalAmt=hairTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			    		else if(lsStaffinfo[i].department=="003")
			    			beatyTotalAmt=beatyTotalAmt*1+document.getElementById("secondsaleamt").value*1;
			    	}
			    					
			    	if(document.getElementById("thirdsalerid").value==lsStaffinfo[i].bstaffno)
			    	{
			    		if(lsStaffinfo[i].department=="004")
			    			hairTotalAmt=hairTotalAmt*1+document.getElementById("thirdsaleamt").value*1;
			    		else if(lsStaffinfo[i].department=="003")
			    			beatyTotalAmt=beatyTotalAmt*1+document.getElementById("thirdsaleamt").value*1;
			    	}
			    }
				if(ForDight(hairTotalAmt*1,1) > ForDight(document.getElementById("changefillamt").value*1,1))
				{
					$.ligerDialog.error('美发部分享金额不能超过售卡额度,请确认!');
					postState=0;
					return;
				} 
				if(ForDight(beatyTotalAmt*1,1) > ForDight(document.getElementById("changefillamt").value*1,1))  
				{
					$.ligerDialog.error('美容部分享金额不能超过售卡额度,请确认!');
					postState=0;
					return;
				} 
			}
			else
			{
				var shareTotalAmt=document.getElementById("firstsaleamt").value*1+document.getElementById("secondsaleamt").value*1+document.getElementById("thirdsaleamt").value*1;
				if(ForDight(shareTotalAmt*1,1) > ForDight(document.getElementById("changefillamt").value*1,1))  
				{
					$.ligerDialog.error('分享金额不能超转卡充值额度,请确认!');
					return;
				} 
			}
			
			var shareTotalCashAmt=document.getElementById("firstsalecashamt").value*1+document.getElementById("secondsalecashamt").value*1+document.getElementById("thirdsalecashamt").value*1;
			if(ForDight(shareTotalCashAmt*1,1) > ForDight(document.getElementById("changefillamt").value*1,1))  
			{
				$.ligerDialog.error('分享业绩不能超过转卡额度,请确认!');
				postState=0;
				return;
			}	
			if(document.getElementById("billinsertype").value==1 && ForDight(hairTotalAmt*1,1)>30000)
			{
				$.ligerDialog.error('美容部办卡美发部分享业绩不能超过30000,请确认!');
				postState=0;
				return;
			}
			
										
       		if(parent.hasFunctionRights( "CC009",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}
			if(curpagestate==3)
	   		{
	   			$.ligerDialog.warn("非新增单据,不可保存!");
	   			return;
	   		}
	   		else
	   		{
	   	
		   		$.ligerDialog.confirm('确认保存当前转卡单?', function (result)
				{
				    if( result==true)
		           	{
		           		if(postState==1)
						 {
						 	$.ligerDialog.error("正在保存中,请不要连续保存!");
						 	return ;
						 }
	   					postState=1;
			       		 //------支付方式
						var strJsonParam_detial="";
						var curjosnparam="";
						var needReplaceStr="";
						var paymat=0;
						for (var rowid in commoninfodivdetial_pay.records)
						{
							var row =commoninfodivdetial_pay.records[rowid];
							if(checkNull(row.paymode)=="6" && checkNull(row.payamt)*1 >0 && document.getElementById("bankcostno").value=="" )
							{
								document.getElementById("bankcostno").focus();
								$.ligerDialog.error('使用银行卡支付必须填写刷卡凭证号!');
								postState=0;
							 	return;
							}
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
						if(paymat*1!=document.getElementById("changefillamt").value)
						{
							$.ligerDialog.warn("转卡金额与支付金额不一致!");
							postState=0;
				    		return;
						}
						var strJsonParamOldCard="";
						curjosnparam="";
						needReplaceStr="";
						for (var rowid in commoninfodivdetial_combin.records)
						{
							var row =commoninfodivdetial_combin.records[rowid];
							curjosnparam=JSON.stringify(row);
							if(curjosnparam.indexOf("_id")>-1)
							{
							   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
							   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
							}	
							if(strJsonParamOldCard!="")
							  	strJsonParamOldCard=strJsonParamOldCard+",";
							strJsonParamOldCard= strJsonParamOldCard+curjosnparam;
						} 
						
						/*document.getElementById("firstsalerid_h").value=  $("#factcfirstsalerid").val();
						document.getElementById("secondsalerid_h").value= $("#factcsecondsalerid").val();	
						document.getElementById("thirdsalerid_h").value=  $("#factcthirdsalerid").val();	
						document.getElementById("fifthsalerid_h").value=  $("#factcfifthsalerid").val();	
						document.getElementById("sixthsalerid_h").value=  $("#factcsixthsalerid").val();	
						document.getElementById("seventhsalerid_h").value=$("#factcseventhsalerid").val();	*/
						var requestUrl ="cc009/post.action";
						var params="error=0";
						if( document.getElementById("changetype").value==2 )
			   			{
			   				document.getElementById("membername").value=document.getElementById("lbmembername").value; 
			     			document.getElementById("memberphone").value=document.getElementById("lbmemberphone").value; 
			   			}
						if(strJsonParam_detial!="")
						{
							params=params+"&strJsonParam=["+strJsonParam_detial+"]";
						}
						if(strJsonParamOldCard!="")
						{
							params=params+"&strJsonParamOldCard=["+strJsonParamOldCard+"]";
						}
						var responseMethod="editMessage";
					    showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
					    var targetCardNo="";
						if(document.getElementById("changetype").value!=6  && document.getElementById("changetype").value!=7 )
			       		{
				       		targetCardNo=document.getElementById("changeaftercardno").value;
			       		}
			       		else
			       		{
			       			targetCardNo=document.getElementById("changebeforcardno").value;
			       		}
						//微信扫码不需要提示
						var bool = true;
						var isRandom = checkNull($("#randomno").val())=="";
						if(isRandom){
							$.ligerDialog.confirm('请确认目标卡'+targetCardNo+'已插入读卡器中!', function (result){
								bool = result;
							});
						}
					    if(bool)
					    {   
					    	var CardControl=parent.document.getElementById("CardCtrl");
					    	CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					    	//折扣转卡 判断插入读卡器的卡不能是老卡
					    	if(document.getElementById("changetype").value==0)
					    	{	
					    		var inserCardno=CardControl.ReadCard();
					    		if(checkNull(inserCardno)!="" && checkNull(inserCardno)==document.getElementById("changebeforcardno").value)
					    		{
					    			$.ligerDialog.error("折扣转卡读卡器中的卡不能为老卡!");
					    			postState=0;
					    			showDialogmanager.close();
					    			return;
					    		}
					    	}
					    	//判断读出的卡不能是初始化的卡 0,1
					    	if(document.getElementById("changetype").value==0
					    			|| document.getElementById("changetype").value==1)   //折扣转卡
					    	{	
					    		var inserCardno=CardControl.ReadCard();
					    		if(inserCardno.length!=20 
					    				&& inserCardno!="" 
					    					&& inserCardno!=document.getElementById("changeaftercardno").value)
					    		{
					    			$.ligerDialog.error('会员卡初始化失败,请确认读卡器中的卡为未初始化的新卡!');
					    			postState=0;
					    			showDialogmanager.close();
					    			return;
					    		}
					    	}
					    	else if(document.getElementById("changetype").value==7)  //并卡
					    	{	
					    		var inserCardno=CardControl.ReadCard();
					    		if(isRandom && inserCardno.length!=20 
					    				&& inserCardno!="" 
					    					&& inserCardno!=document.getElementById("changebeforcardno").value)
					    		{
					    			$.ligerDialog.error('会员卡初始化失败,请确认读卡器中的卡为未初始化的新卡!');
					    			postState=0;
					    			showDialogmanager.close();
					    			return;
					    		}
					    	}
					    	//老卡并老卡 插入读卡器中的卡必须为老卡
					    	if(document.getElementById("changetype").value==6)
					    	{	
					    		var inserCardno=CardControl.ReadCard();
					    		if(isRandom && checkNull(inserCardno)!=document.getElementById("changebeforcardno").value)
					    		{
					    			$.ligerDialog.error("老卡并老卡读卡器中的卡不是"+document.getElementById("changebeforcardno").value+",请确认读卡器中的卡");
					    			postState=0;
					    			showDialogmanager.close();
					    			return;
					    		}
					    	}
					    	
					    	var cardoption=1;	
					    	if(document.getElementById("changetype").value!="9")
					    	{
					    		if(document.getElementById("changetype").value==6 || document.getElementById("changetype").value==7)
					    		{
					    			if(isRandom){
					    				cardoption=CardControl.WriteCard(document.getElementById("changebeforcardno").value);
					    			}
					    		}
					    		else
					    		{
					    			cardoption=CardControl.WriteCard(document.getElementById("changeaftercardno").value);
					    		}	
					    		
					    	}
					    	if(cardoption==1)
					    	{
					    		if(document.getElementById("cardsendamtflag").value*1==1
					    				&& document.getElementById("changefillamt").value*1>=20000)
					    		{
					    			$.ligerDialog.confirm('是否赠送卡金', function (result)
					    					{
					    				if( result==true)
					    				{
					    					document.getElementById("sendamtflag").value=1;
					    					var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
					    					queryStringTmp=queryStringTmp.replace(/\+/g," ");
					    					params=params+"&"+queryStringTmp;
					    					sendRequestForParams_p(requestUrl,responseMethod,params );
					    				}
					    				else
					    				{
					    					document.getElementById("sendamtflag").value=0;
					    					var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
					    					queryStringTmp=queryStringTmp.replace(/\+/g," ");
					    					params=params+"&"+queryStringTmp;
					    					
					    					sendRequestForParams_p(requestUrl,responseMethod,params );
					    				}
					    					});
					    		}
					    		else
					    		{
					    			document.getElementById("sendamtflag").value=0;
					    			var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
					    			queryStringTmp=queryStringTmp.replace(/\+/g," ");
					    			params=params+"&"+queryStringTmp;
					    			sendRequestForParams_p(requestUrl,responseMethod,params ); 
					    		}
					    	} 
					    	else
					    	{
					    		$.ligerDialog.error("IC卡初始化失败,请确认!");
					    		postState=0;
					    		showDialogmanager.close();
					    	}  
					    }
					    else
					    {
					    	postState=0;
					    	showDialogmanager.close();
					    }
						
		     	}
			}); 
			}
       }
       
        function editMessage(request)
        {
    		
        	try
			{
				postState=0;
				showDialogmanager.close();
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		 	$.ligerDialog.success("转卡成功,请到卡资料中核实!");
	        		 	
	        		 	readPage(1);
	        		 	viewTicketReport();
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
        
        
       function checkStaffValuex(obj)
		{
			try
			{
				var fxsShare=0;
				if(obj.id=="firstsalerid")
				{
					document.getElementById("fifthsaleamt").value=0;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  	if(lsStaffinfo[i].position!="00901" &&
									lsStaffinfo[i].position!="00902" &&
									lsStaffinfo[i].position!="00903" &&
									lsStaffinfo[i].position!="00904" &&
									lsStaffinfo[i].position!="008" )
									{
			    				  		//美发部的分享人与消费老客挂钩
										if(document.getElementById("strShareCondition").value=="1" 
										&& lsStaffinfo[i].department=="004" && obj.value.indexOf("300")<0)
			    						{
			    							  	for (var rowid in commoninfodivdetial_oldcustomer.records)
												{
													var row =commoninfodivdetial_oldcustomer.records[rowid];
													if(checkNull(row.staffno)==obj.value)
													{
														document.getElementById("firstsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				  						exists=1;
													}
												} 
			    						}
			    						else
			    						{
			    							document.getElementById("firstsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				  			exists=1;
			    						}
			    						break;
			    				  	}
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("firstsalername").value="";
			    			obj.select();
			    			obj.focus()
			    			if(document.getElementById("strShareCondition").value=="1" )
			    				$.ligerDialog.warn("输入的员工编号不存在或不在分享老客列表中!");
			    			else
			    				$.ligerDialog.warn("输入的员工编号不存在!");
			    		}
			    	}
				}
				else if(obj.id=="secondsalerid")
				{
					document.getElementById("sixthsaleamt").value=0;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				if(lsStaffinfo[i].position!="00901" &&
									lsStaffinfo[i].position!="00902" &&
									lsStaffinfo[i].position!="00903" &&
									lsStaffinfo[i].position!="00904" &&
									lsStaffinfo[i].position!="008" )
									{
			    				  		//美发部的分享人与消费老客挂钩
										if(document.getElementById("strShareCondition").value=="1" 
										&& lsStaffinfo[i].department=="004" && obj.value.indexOf("300")<0)
			    						{
			    							  	for (var rowid in commoninfodivdetial_oldcustomer.records)
												{
													var row =commoninfodivdetial_oldcustomer.records[rowid];
													if(checkNull(row.staffno)==obj.value)
													{
														document.getElementById("secondsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				  						exists=1;
													}
												} 
			    						}
			    						else
			    						{
			    				  		 	document.getElementById("secondsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				 		 	exists=1;
			    				 		}
			    				 		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("secondsalername").value="";
			    			obj.select();
			    			obj.focus()
			    			if(document.getElementById("strShareCondition").value=="1" )
			    				$.ligerDialog.warn("输入的员工编号不存在或不在分享老客列表中!");
			    			else
			    				$.ligerDialog.warn("输入的员工编号不存在!");
			    		}
			    	}
				}
				else if(obj.id=="thirdsalerid")
				{
					document.getElementById("seventhsaleamt").value=0;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				 if(lsStaffinfo[i].position!="00901" &&
									lsStaffinfo[i].position!="00902" &&
									lsStaffinfo[i].position!="00903" &&
									lsStaffinfo[i].position!="00904" &&
									lsStaffinfo[i].position!="008" )
									{
			    				 	 	//美发部的分享人与消费老客挂钩
										if(document.getElementById("strShareCondition").value=="1" 
										&& lsStaffinfo[i].department=="004" && obj.value.indexOf("300")<0)
			    						{
			    							  	for (var rowid in commoninfodivdetial_oldcustomer.records)
												{
													var row =commoninfodivdetial_oldcustomer.records[rowid];
													if(checkNull(row.staffno)==obj.value)
													{
														document.getElementById("thirdsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				  						exists=1;
													}
												} 
			    						}
			    						else
			    						{
			    				 	 		document.getElementById("thirdsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				  			exists=1;
			    				  		}
			    				  		break;
			    				     }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("thirdsalername").value="";
			    			obj.select();
			    			obj.focus()
			    			if(document.getElementById("strShareCondition").value=="1" )
			    				$.ligerDialog.warn("输入的员工编号不存在或不在分享老客列表中!");
			    			else
			    				$.ligerDialog.warn("输入的员工编号不存在!");
			    		}
			    	}
				}else if(obj.id=="beautyManager")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00101" ||  lsStaffinfo[i].position=="00104")
									{
			    				 		document.getElementById("beautyManagerName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("beautyManagerName").value="";
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是经理/顾问");
			    		}
			    	}
				}
				else if(obj.id=="consultant")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00101" ||  lsStaffinfo[i].position=="00104")
									{
			    				 		document.getElementById("consultantName").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("consultantName").value="";
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是经理/顾问");
			    		}
			    	}
				}else if(obj.id=="consultant1")
				{
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00101" ||  lsStaffinfo[i].position=="00104")
									{
			    				 		document.getElementById("consultantName1").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("consultantName1").value="";
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是经理/顾问");
			    		}
			    	}
				}
				if(obj.value=="")
				{
					return;
				}
				if(obj.id=="fifthsalerid")
				{
					fxsShare=document.getElementById("firstsaleamt").value*1;
					if(fxsShare>10000 && document.getElementById("firstsalerid").value!="")
							fxsShare=10000;
					fxsCashShare=document.getElementById("firstsalecashamt").value*1;
					if(fxsCashShare>10000 && document.getElementById("firstsalerid").value!="")
							fxsCashShare=10000;
					document.getElementById("fifthsaleamt").value=fxsShare;
					document.getElementById("fifthsalecashamt").value=fxsCashShare;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				 if(lsStaffinfo[i].position=="00901" ||
									lsStaffinfo[i].position=="00902" ||
									lsStaffinfo[i].position=="00903" ||
									lsStaffinfo[i].position=="00904" ||
									lsStaffinfo[i].position=="008" )
									{
			    				 		document.getElementById("fifthsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("fifthsalername").value="";
			    			document.getElementById("fifthsaleamt").value=0;
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是烫染师!");
			    		}
			    	}
				}
				else if(obj.id=="sixthsalerid")
				{
					fxsShare=document.getElementById("secondsaleamt").value*1;
					if(fxsShare>10000 && document.getElementById("secondsalerid").value!="")
							fxsShare=10000;
					fxsCashShare=document.getElementById("secondsalecashamt").value*1;
					if(fxsCashShare>10000 && document.getElementById("secondsalerid").value!="")
							fxsCashShare=10000;
					document.getElementById("sixthsaleamt").value=fxsShare;
					document.getElementById("sixthsalecashamt").value=fxsCashShare;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00901" ||
									lsStaffinfo[i].position=="00902" ||
									lsStaffinfo[i].position=="00903" ||
									lsStaffinfo[i].position=="00904" ||
									lsStaffinfo[i].position=="008" )
									{
			    				 		document.getElementById("sixthsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("sixthsalername").value="";
			    			document.getElementById("sixthsaleamt").value=0;
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是烫染师!");
			    		}
			    	}
				}
				else if(obj.id=="seventhsalerid")
				{
					fxsShare=document.getElementById("thirdsaleamt").value*1;
					if(fxsShare>10000 && document.getElementById("thirdsalerid").value!="")
							fxsShare=10000;
					fxsCashShare=document.getElementById("thirdsalecashamt").value*1;
					if(fxsCashShare>10000 && document.getElementById("thirdsalerid").value!="")
							fxsCashShare=10000;
					document.getElementById("seventhsaleamt").value=fxsShare;
					document.getElementById("seventhsalecashamt").value=fxsCashShare;
					if(obj.value!="" && obj.value.indexOf('_')==-1)
			    	{
			    		var exists=0;
			    		for(var i=0;i<lsStaffinfo.length;i++)
			    		{
			    			if(obj.value==lsStaffinfo[i].bstaffno)
			    			{
			    				  if(lsStaffinfo[i].position=="00901" ||
									lsStaffinfo[i].position=="00902" ||
									lsStaffinfo[i].position=="00903" ||
									lsStaffinfo[i].position=="00904" ||
									lsStaffinfo[i].position=="008" )
									{
			    				 		document.getElementById("seventhsalername").value=checkNull(lsStaffinfo[i].staffname);
			    				 		exists=1;
			    				  		break;
			    				  }
			    			}
			    			
			    		}
			    		if(exists==0)
			    		{
			    			obj.value="";
			    			document.getElementById("seventhsalername").value="";
			    			document.getElementById("seventhsaleamt").value=0;
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是烫染师!");
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
				/*firstsaleridmanager .selectBox.hide();
   				secondsaleridmanager.selectBox.hide();
   				thirdsalerinidmanager.selectBox.hide();
   				fifthsaleridmanager.selectBox.hide();
   				sixthsaleridmanager.selectBox.hide();
   				seventhsaleridmanager .selectBox.hide();*/
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		function viewTicketReport()
		{
			if(document.getElementById("changebeforcardno").value=="")
			{
				$.ligerDialog.warn("请确认转卡卡号后在打印!");
				return;
			}
		  	var requestUrl ="cc009/viewTicketReport.action"; 
			var responseMethod="viewTicketReportMessage";	
			var params =  "strCurCompId=" + document.getElementById("changecompid").value + "&strCurBillId=" + document.getElementById("changebillid").value;		
			if(document.getElementById("changetype").value==6 || document.getElementById("changetype").value==7)
   		    {
			   params =params+ "&strCardNo="+document.getElementById("changebeforcardno").value;
			}
			else
			{
				params =params+ "&strCardNo="+document.getElementById("changeaftercardno").value;
			}	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function viewTicketReportMessage(request)
		{
		  try
		  {
		    var responsetext = eval("(" + request.responseText + ")");
		  	Stand_CheckPrintControl();//检查是否有打印控件
			Stand_InitPrint("转卡模块_小票打印作业");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Alignment",2);
			Stand_SetPrintStyle("HOrient",2);
			Stand_SetPrintStyle("Bold",1);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(parent.document.getElementById("strCompName").value)+")");
			Stand_SetPrintStyle("FontSize",9);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Bold",0);
		   document.getElementById("currdate_print").innerHTML=responsetext.printDate;
		   
		   if(document.getElementById("changetype").value==6 || document.getElementById("changetype").value==7)
   		   {
   		   		document.getElementById("memberOldCardId_print").innerHTML=document.getElementById("changeaftercardno").value;
   				document.getElementById("memberCardId_print").innerHTML=document.getElementById("changebeforcardno").value;
		   		document.getElementById("memberCardType_print").innerHTML= document.getElementById("changebeforcardtypename").value;
		   }
		   else
		   {
		   		document.getElementById("memberOldCardId_print").innerHTML=document.getElementById("changebeforcardno").value;
		   		document.getElementById("memberCardId_print").innerHTML=document.getElementById("changeaftercardno").value;
		   		document.getElementById("memberCardType_print").innerHTML= document.getElementById("changeaftercardtypename").value;
		   }
   		   document.getElementById("keepAmountOld_print").innerHTML=maskAmt(checkNull(document.getElementById("curaccountkeepamt").value*1),2);
		   document.getElementById("keepAmount_print").innerHTML=maskAmt(checkNull(document.getElementById("changefillamt").value*1),2);
		   document.getElementById("debtAmount_print").innerHTML=0;
		   document.getElementById("incardAmount_print").innerHTML=maskAmt(checkNull(responsetext.curCardinfo.account2Amt),2);
		   document.getElementById("cardDebtAmount_print").innerHTML=maskAmt(checkNull(responsetext.curCardinfo.account2debtAmt),2);
		   document.getElementById("tradebillId_print").innerHTML=responsetext.strCurBillId;
		   document.getElementById("clerkName_print").innerHTML=responsetext.cashMemberName ;
		   document.getElementById("printTime_print").innerHTML=responsetext.printTime;
		   document.getElementById("telephone_print").innerHTML=responsetext.companTel;
		   document.getElementById("address_print").innerHTML =responsetext.companAddr;
		   clearPreviousResult_report();
		   if(responsetext.payTypeList != null)
		   {
					for(var i=0;i<responsetext.payTypeList.length;i++)
					{
						addRowReport(checkNull(responsetext.payTypeList[i].paymodename),maskAmt(responsetext.payTypeList[i].payamt,1));
					}
		   }
			
		    var printContent = document.getElementById("printContent").innerHTML;
		    Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,75,0,230,800,printContent);
			Stand_Print();
		  }
		  catch(e){alert(e.message);}
		}
		
		function addRowReport(name,amt)
	     {
		    var row = document.createElement("tr");
		    var cell = createCellWithText(checkNull(name)+":");
		    row.appendChild(cell);
		    cell = createCellWithText("　　"+checkNull(amt));
		    row.appendChild(cell);
		    document.getElementById("consumeDetail").appendChild(row);
	     }
		function clearPreviousResult_report()
	    {
		    var tblPrjs = document.getElementById("consumeDetail");
		    while(tblPrjs.childNodes.length>0)
		     {
			    tblPrjs.removeChild(tblPrjs.childNodes[0]);
		     }
	    }
		
		function xnchange(obj)
		{
			if(document.getElementById("billinsertype").value!="1" && obj.value=="1")
			{
				obj.value="0";
				$.ligerDialog.warn("只有美容类型的才能分享虚拟卡金!");
			}
		}
		//检查微信扫码是否存在
		function checkRandomno(){
	    	var randomno=$("#randomno").val();
	    	if(checkNull(randomno)==""){
	    		return;
	    	}
	    	var params = {"strRandomno":randomno}
	    	var url = contextURL+"/cc011/checkWXRandomno.action"; 
	    	$.post(url, params, function(data){
	    		if(data.strMessage!=""){
	    			$.ligerDialog.warn(data.strMessage);
	    			$("#randomno").val("");
	    		}else{
	    			document.getElementById("changebeforcardno").value=data.wxbandcard.cardno;
	    			validateChangebeforcardno(document.getElementById("changebeforcardno"));
	    		}
	    	}).error(function(e){$.ligerDialog.error("读取信息失败，请重试！");});
	    }
		