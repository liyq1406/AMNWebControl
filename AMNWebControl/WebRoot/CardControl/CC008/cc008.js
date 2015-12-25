
   	var compTreeManager;
   	var compTree;
   	var commoninfodivdetial_pay=null;//支付方式
   	var commoninfodivdetial_pro=null;//疗程明细
   	var commoninfodivmaster= null;
   	var strCurCompId="";
   	var strCurBillId="";
   	var chooseSexData = [{ choose: 1, text: '男' }, { choose: 0, text: '女'}];
   	var cc008layout=null;
   	var fromValidate=null;
   	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
   	var fifthsaleridmanager = null;
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var commoninfodivdetial_oldcustomer=null;
   	var lsProjectinfo = null;
   	var lsStaffinfo=null;
   	var pageState=3;
   	var addcustomerDialog=null;
   	var addStaffNo="";
   	var addstaffName="";
   	var showDialogPayMehod=null;
   	//var paymodeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("ZFFS",0));
   	var lcTypeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("JGCW",0));
   	var curProRecord = null;
   	var curMasterRecord = null;
   	var accountmodeManage =null;
   	var curCardinfo = null;
   	var showDialogmanager=null;
   	//支付宝 微信支付总额
   	var payTotalBank=0;
	//支付宝 微信支付参数
   	var threeParams="";
   	var postState=0;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc008layout= $("#cc008layout").ligerLayout({ leftWidth: 270,rightWidth: 250,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true});
            //门店树形结构
   			var height = $(".l-layout-center").height();
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
          	$("#pageloading").hide(); 
          	//commoninfodivdetial_pay
          	commoninfodivdetial_pro=$("#commoninfodivdetial_pro").ligerGrid({
                columns: [
                { display: '疗程编号', 	name: 'saleproid',  		width:100,align: 'left',editor: { type: 'text',onChanged: validateProId} },
                { display: '疗程名称', 	name: 'saleproname', 		width:140,align: 'left'},
                { display: '疗程类型', 	name: 'saleprotype', 		width:100,align: 'center'  ,
	             	editor: { type: 'select', data: lcTypeChangeData, valueField: 'choose',selectBoxWidth:105,onChanged: validateProId},
		            	render: function (item)
		              	{
		              		var lsZw=parent.gainCommonInfoByCode("JGCW",0);
		              		for(var i=0;i<lsZw.length;i++)
							{
								if(lsZw[i].bparentcodekey==item.saleprotype)
								{	
									return lsZw[i].parentcodevalue;								
							    }
							}
		                    return '';
		                } 
		        },
		        { display: '购买疗程数', 		name: 'saleprocardcount', 		width:80,align: 'center',editor: { type: 'int' ,onChanged: validatesaleprocardcount } },
	            { display: '购买项目次数', 	name: 'saleprocount', 		width:90,align: 'center'},
	            { display: '赠送项目次数', 	name: 'sendprocount', 		width:90,align: 'center',editor: { type: 'int' }},
	            { display: '购买金额', 	name: 'saleproamt', 		width:80,align: 'center'},
	            { display: '备注', 		name: 'saleproremark', 		width:160,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: cc008layout.centerWidth*1+cc008layout.leftWidth*1-230,
                height:height-405,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                toolbar: { items: [
	                { text: '新增购买疗程', click: itemclick_addpro, img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif' }
	                ]
                },
                onSelectRow : function (data, rowindex, rowobj)
                {
                    curProRecord = data;
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
                { display: '支付金额', 	name: 'payamt', 		width:80,align: 'right', editor: { type: 'float' }},
                { display: '支付备注', 	name: 'payremark', 		width:150,align: 'left',editor: { type: 'text'}}
	            ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 350,
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
            
     		commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '续卡卡号', 	name: 'rechargecardno',  		width:100,align: 'left' },
                { display: '续卡单号', 	name: 'brechargebillid', 		width:120,align: 'left'}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 250,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curMasterRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
             var  accountmodeData=JSON.parse('[{"id": "2","text": "储值账户"}]');
           	 accountmodeManage = $("#rechargeaccounttype").ligerComboBox({ data: accountmodeData, isMultiSelect: false,valueFieldID: 'factRechargeaccounttype',width:'140' });           	 
           	
            document.getElementById("mainToolTable").width= cc008layout.centerWidth*1-cc008layout.leftWidth*1-160;
            $.metadata.setType("attr", "validate");
            fromValidate = $("form").validate({
                //调试状态，不会提交数据的
                debug: true,
                errorPlacement: function (lable, element)
                {

                    if (element.hasClass("l-textarea"))
                    {
                        element.addClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", lable.html()).ligerTip();
                },
                success: function (lable)
                {
                    var element = $("#" + lable.attr("for"));
                    if (element.hasClass("l-textarea"))
                    {
                        element.removeClass("l-textarea-invalid");
                    }
                    else if (element.hasClass("l-text-field"))
                    {
                        element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                },
                submitHandler: function ()
                {
                    alert("Submitted!");
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
      		f_selectNode();
      		
   		}catch(e){alert(e.message);}
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
        try{
        	strCurCompId=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
       		var requestUrl ="cc008/loadMasterinfo.action"; 
			var responseMethod="loadMasterinfoMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
 
   function loadMasterinfoMessage(request)
   {
       		try
        	{
 
	        	var responsetext = eval("(" + request.responseText + ")");
		   		if(responsetext.lsMcardrechargeinfo!=null && responsetext.lsMcardrechargeinfo.length>0)
		   		{
		   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcardrechargeinfo,Total: responsetext.lsMcardrechargeinfo.length});
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
				/*var lsStaffSelectData_sj=loadOtherGridByStaffInfo(lsStaffinfo,'',1);
				var lsStaffSelectData_tr=loadOtherGridByStaffInfo(lsStaffinfo,'',2);
				firstsaleridmanager= $("#firstsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcfirstsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				secondsaleridmanager= $("#secondsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcsecondsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				thirdsalerinidmanager= $("#thirdsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_sj
	               					 , valueFieldID: 'factcthirdsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				fifthsaleridmanager= $("#fifthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcfifthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220' ,alwayShowInDown:true}); 
				sixthsaleridmanager= $("#sixthsalerid").ligerComboBox({ isShowCheckBox: false,    url:'loadAutoStaff',data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcsixthsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				seventhsaleridmanager= $("#seventhsalerid").ligerComboBox({ isShowCheckBox: false,   url:'loadAutoStaff', data: lsStaffSelectData_tr
	               					 , valueFieldID: 'factcseventhsalerid',autocomplete: true,label:'请选员工',labelWidth:200,labelAlign:'center',width:'140',selectBoxHeight:'220',alwayShowInDown:true }); 
				*/
				lsProjectinfo=	responsetext.lsProjectinfo;		
		 
				loadPayModeDate(responsetext.strSalePayMode);
				document.getElementById("strShareCondition").value=responsetext.strShareCondition;
				if(commoninfodivmaster.rows.length>0)
				{
					commoninfodivmaster.select(0);
				}
	   		
	   		}catch(e){alert(e.message);}
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
   function loadSelecDetialData(data, rowindex, rowobj)
   {
        try{
        		 strCurCompId=data.brechargecompid;
        		 strCurBillId=data.brechargebillid;
        		 var params = "strCurCompId="+strCurCompId;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc008/loadCurMaster.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var responsetext = eval( "("+request.responseText+")");	
								var curmaster=responsetext.curmcardrechargeinfo;
								if(responsetext.strSP114==1)
						   		{
						   			document.getElementById("isxnj").disabled=false;
						   		}
						   		else
						   		{
						   			document.getElementById("isxnj").disabled=true;
						   		}
								loadcurmaster(curmaster);
								if(responsetext.lsDsalecardproinfos!=null && responsetext.lsDsalecardproinfos.length>0)
						   		{
						   			commoninfodivdetial_pro.options.data=$.extend(true, {},{Rows: responsetext.lsDsalecardproinfos,Total: responsetext.lsDsalecardproinfos.length});
					            	commoninfodivdetial_pro.loadData(true);            	
						   		}
								if(responsetext.lsDpayinfo!=null && responsetext.lsDpayinfo.length>0)
						   		{
						   			commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: responsetext.lsDpayinfo,Total: responsetext.lsDpayinfo.length});
					            	commoninfodivdetial_pay.loadData(true);            	
						   		}
						   		else
						   		{
						   			commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            	commoninfodivdetial_pay.loadData(true);
						   			itemclick_addpay();
						   		}
						   		commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: null,Total: 0});
					            commoninfodivdetial_oldcustomer.loadData(true);
						   		itemclick_addcustomer();
						},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   }
   
  
   	function loadcurmaster(curmaster)
   	{
   		try
		{
   			document.getElementById("randomno").value="";
			document.getElementById("lbBillId").innerHTML=curmaster.id.rechargebillid;
	   		document.getElementById("rechargecompid").value=checkNull(curmaster.id.rechargecompid);
	   		document.getElementById("rechargebillid").value=checkNull(curmaster.id.rechargebillid);
	   		document.getElementById("rechargedate").value=checkNull(curmaster.rechargedate);
	   		document.getElementById("rechargetime").value=checkNull(curmaster.rechargetime);
	   		
	   		document.getElementById("rechargecardno").value=checkNull(curmaster.rechargecardno);
	   		document.getElementById("rechargecardtype").value=checkNull(curmaster.rechargecardtype);
	   		document.getElementById("rechargecardtypeName").value=checkNull(curmaster.rechargecardtypeName);
	   		document.getElementById("membername").value=checkNull(curmaster.membername);
	   		document.getElementById("bankcostno").value=checkNull(curmaster.bankcostno); 
	   		document.getElementById("curcardamt").value=checkNull(curmaster.curcardamt);
	   		document.getElementById("curcarddebtamt").value=checkNull(curmaster.curcarddebtamt);
	   		document.getElementById("rechargekeepamt").value=checkNull(curmaster.rechargekeepamt);
	   		document.getElementById("rechargeaccounttype_h").value=checkNull(curmaster.rechargeaccounttype);
	   		accountmodeManage.selectValue(checkNull(curmaster.rechargeaccounttype));
	   		document.getElementById("firstsalerid").value=checkNull(curmaster.firstsalerid);
	   		document.getElementById("firstsalername").value="";
	   		document.getElementById("isxnj").value=checkNull(curmaster.isxnj);
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
	   		//firstsaleridmanager.selectValue(checkNull(curmaster.firstsalerid));
	   		document.getElementById("firstsaleamt").value=checkNull(curmaster.firstsaleamt);
	   		document.getElementById("firstsalecashamt").value=checkNull(curmaster.firstsalecashamt);
	   		document.getElementById("secondsalerid").value=checkNull(curmaster.secondsalerid);
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
	   		//secondsaleridmanager.selectValue(checkNull(curmaster.secondsalerid));
	   		document.getElementById("secondsaleamt").value=checkNull(curmaster.secondsaleamt);
	   		document.getElementById("secondsalecashamt").value=checkNull(curmaster.secondsalecashamt);
	   		document.getElementById("thirdsalerid").value=checkNull(curmaster.thirdsalerid);
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
	   		//thirdsalerinidmanager.selectValue(checkNull(curmaster.thirdsalerid));
	   		document.getElementById("thirdsaleamt").value=checkNull(curmaster.thirdsaleamt);
	   		document.getElementById("thirdsalecashamt").value=checkNull(curmaster.thirdsalecashamt);
	   		document.getElementById("fifthsalerid").value=checkNull(curmaster.fifthsalerid);
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
	   		//fifthsaleridmanager.selectValue(checkNull(curmaster.fifthsalerid));
	   		document.getElementById("fifthsaleamt").value=checkNull(curmaster.fifthsaleamt);
	   		document.getElementById("fifthsalecashamt").value=checkNull(curmaster.fifthsalecashamt);
	   		document.getElementById("sixthsalerid").value=checkNull(curmaster.sixthsalerid);
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
	   		//sixthsaleridmanager.selectValue(checkNull(curmaster.sixthsalerid));
	   		document.getElementById("sixthsaleamt").value=checkNull(curmaster.sixthsaleamt);
	   		document.getElementById("sixthsalecashamt").value=checkNull(curmaster.sixthsalecashamt);
	   		document.getElementById("seventhsalerid").value=checkNull(curmaster.seventhsalerid);
	   		document.getElementById("seventhsalername").value="";
	   		
	   		document.getElementById("beautyManagerName").value="";
			document.getElementById("consultantName").value="";
			document.getElementById("consultantName1").value="";
	   		document.getElementById("beautyManager").value=checkNull(curmaster.beautyManager);
			document.getElementById("consultant").value=checkNull(curmaster.consultant);
			document.getElementById("consultant1").value=checkNull(curmaster.consultant1);
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
	   		//seventhsaleridmanager.selectValue(checkNull(curmaster.seventhsalerid));
	   		document.getElementById("seventhsaleamt").value=checkNull(curmaster.seventhsaleamt);
	   		document.getElementById("seventhsalecashamt").value=checkNull(curmaster.seventhsalecashamt);
	   		document.getElementById("rechargeremark").value=checkNull(curmaster.rechargeremark);
	   		document.getElementById("billinsertype").value=checkNull(curmaster.billinsertype)*1;
	   		
	   		
			
	   		
	   		handleRadio("rechargetype",checkNull(curmaster.rechargetype));
	   		if(checkNull(curmaster.rechargecardno)=="")			
	   		{
	   			writePage();
	   		}
	   		else
	   		{
	   			readPage();
	   		}
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
   	
   	function addSaleInfo()
   	{
   			var params = "strCurCompId="+strCurCompId;				
       		var requestUrl ="cc008/add.action"; 
			var responseMethod="addMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	function addMessage(request)
   	{
   		var responsetext = eval("(" + request.responseText + ")");
   		var row = commoninfodivmaster.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivmaster.addRow({ 
				                rechargecardno: "",
				                brechargebillid:responsetext.curmcardrechargeinfo.id.rechargebillid
				            }, row, false);
   		loadcurmaster(responsetext.curmcardrechargeinfo);
    	writePage();
   	}
    function writePage()
    {
    	
    	pageState=1;
     	//document.getElementById("rechargecardno").value="";
     	//document.getElementById("rechargecardno").readOnly="";
     	document.getElementById("rechargekeepamt").value="";
     	document.getElementById("rechargekeepamt").readOnly="";
     	document.getElementById("curcarddebtamt").value="";
     	document.getElementById("curcarddebtamt").readOnly="";
     	document.getElementById("firstsaleamt").value="0";
     	document.getElementById("secondsaleamt").value="0";
     	document.getElementById("thirdsaleamt").value="0";
     	document.getElementById("fifthsaleamt").value="0";
     	document.getElementById("sixthsaleamt").value="0";
     	document.getElementById("seventhsaleamt").value="0";
     	
     	document.getElementById("firstsalecashamt").value="0";
     	document.getElementById("secondsalecashamt").value="0";
     	document.getElementById("thirdsalecashamt").value="0";
     	document.getElementById("fifthsalecashamt").value="0";
     	document.getElementById("sixthsalecashamt").value="0";
     	document.getElementById("seventhsalecashamt").value="0";
     	
     	
     	document.getElementById("firstsaleamt").readOnly="";
     	document.getElementById("secondsaleamt").readOnly="";
     	document.getElementById("thirdsaleamt").readOnly="";
     	
     	
     	document.getElementById("firstsalecashamt").readOnly="";
     	document.getElementById("secondsalecashamt").readOnly="";
     	document.getElementById("thirdsalecashamt").readOnly="";
     	
     	
     	document.getElementById("rechargeremark").value="";
     	document.getElementById("rechargeremark").readOnly="";
     	document.getElementById("membername").value="";
     	document.getElementById("bankcostno").value="";
     	document.getElementById("bankcostno").readOnly="";
     	document.getElementById("rechargecardtype").value="";
     	document.getElementById("rechargecardtypeName").value="";
     	document.getElementById("loeweAmt").value="0";
     	accountmodeManage.selectValue(2);
     	//document.getElementById("fifthsaleamt").readOnly="";
     	//document.getElementById("sixthsaleamt").readOnly="";
     	//document.getElementById("seventhsaleamt").readOnly="";
     	commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetial_pay.loadData(true);   
		itemclick_addpay();
		commoninfodivdetial_pro.options.data=$.extend(true, {},{Rows: null,Total: 0});
		commoninfodivdetial_pro.loadData(true); 
     	commoninfodivdetial_pay.options.clickToEdit=true;
     	commoninfodivdetial_pay.options.enabledEdit=true;
     	commoninfodivdetial_pro.options.clickToEdit=true;
     	commoninfodivdetial_pro.options.enabledEdit=true;
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
    }
    function readPage()
    {
		document.getElementById("bankcostno").readOnly="readOnly";
     	document.getElementById("rechargecardno").readOnly="readOnly";
     	document.getElementById("rechargekeepamt").readOnly="readOnly";
     	document.getElementById("firstsaleamt").readOnly="readOnly";
     	document.getElementById("secondsaleamt").readOnly="readOnly";
     	document.getElementById("thirdsaleamt").readOnly="readOnly";
     	
     	document.getElementById("firstsalecashamt").readOnly="readOnly";
     	document.getElementById("secondsalecashamt").readOnly="readOnly";
     	document.getElementById("thirdsalecashamt").readOnly="readOnly";
     	
     	
     	document.getElementById("rechargeremark").readOnly="readOnly";
     	document.getElementById("firstsalerid").readOnly="readOnly";
     	document.getElementById("secondsalerid").readOnly="readOnly";
     	document.getElementById("thirdsalerid").readOnly="readOnly";
     	document.getElementById("fifthsalerid").readOnly="readOnly";
     	document.getElementById("sixthsalerid").readOnly="readOnly";
     	document.getElementById("seventhsalerid").readOnly="readOnly";
     	//document.getElementById("fifthsaleamt").readOnly="readOnly";
     	//document.getElementById("sixthsaleamt").readOnly="readOnly";
     	//document.getElementById("seventhsaleamt").readOnly="readOnly";
   		/*firstsaleridmanager.setDisabled();
   		secondsaleridmanager.setDisabled();
   		thirdsalerinidmanager.setDisabled();
   		fifthsaleridmanager.setDisabled();
   		sixthsaleridmanager.setDisabled();
		seventhsaleridmanager.setDisabled();*/
     	pageState=3;
     	commoninfodivdetial_pay.options.clickToEdit=false;
     	commoninfodivdetial_pay.options.enabledEdit=false;
     	commoninfodivdetial_pro.options.clickToEdit=false;
     	commoninfodivdetial_pro.options.enabledEdit=false;
    }
    
    
    function validateSalecardno(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("rechargecardtype").value="";
    		document.getElementById("rechargecardtypeName").value="";
    		document.getElementById("membername").value="";
    		document.getElementById("curcardamt").value=0;
    		document.getElementById("curcarddebtamt").value=0;
    		document.getElementById("loeweAmt").value=0;
    		accountmodeManage.selectValue(2);
    	}
    	else
    	{
    		var requestUrl ="cc008/validateSalecardno.action"; 
			var responseMethod="validateSalecardnoMessage";	
			var params="strCardNo="+obj.value;	
			params =params+ "&strCurCompId="+strCurCompId;		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    
    function validateSalecardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.warn(strmessage);
	   			document.getElementById("rechargecardno").value="";
	   			document.getElementById("rechargecardtype").value="";
	   			document.getElementById("rechargecardtypeName").value="";
    			document.getElementById("membername").value="";
    			document.getElementById("curcardamt").value=0;
    			document.getElementById("curcarddebtamt").value=0;
    			document.getElementById("loeweAmt").value=0;
    			accountmodeManage.selectValue(2);
    			commoninfodivdetial_oldcustomer.options.data=$.extend(true, {},{Rows: null,Total: 0});
				commoninfodivdetial_oldcustomer.loadData(true);
				itemclick_addcustomer();
	   		}
	   		else
	   		{
	   			curCardinfo=responsetext.curCardinfo;
	   			accountmodeManage.selectValue(2);
	   			document.getElementById("rechargecardtype").value=checkNull(responsetext.curCardinfo.cardtype);
    			document.getElementById("rechargecardtypeName").value=checkNull(responsetext.curCardinfo.cardtypeName);
    			//document.getElementById("membername").value=checkNull(responsetext.curCardinfo.membername);
    			if(checkNull(responsetext.curCardinfo.membername)!="" )
	   				document.getElementById("membername").value=checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**";
	   			else
	   				document.getElementById("membername").value="";
    			document.getElementById("curcardamt").value=checkNull(responsetext.curCardinfo.account2Amt);
    			document.getElementById("curcarddebtamt").value=checkNull(responsetext.curCardinfo.account2debtAmt);
    			document.getElementById("cardsendamtflag").value=checkNull(responsetext.curCardinfo.sendamtflag);
    			if(checkNull(responsetext.curCardinfo.account2debtAmt)>0)
    			{
    				handleRadio("rechargetype",6);
    				document.getElementById("loeweAmt").value=0;
    			}
    			else
    			{
    				handleRadio("rechargetype",0);
    				document.getElementById("loeweAmt").value=checkNull(responsetext.curCardinfo.DFillLowAmt);
    			}
    			document.getElementById("rechargekeepamt").focus();
    			document.getElementById("rechargekeepamt").select();
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
	   	
	   	}catch(e){alert(e.message);}
    }
   
   	//验证账户
     function validateAccount(obj)
     {
     	if(obj.value=="")
     	{
     		document.getElementById("curcardamt").value=0;
    		document.getElementById("curcarddebtamt").value=0;
    		accountmodeManage.selectValue(2);
     	}
     	if(document.getElementById("rechargecardno").value=="")
     	{
     		return;
     	}

     	if($("#factRechargeaccounttype").val()==2)
     	{
     		document.getElementById("curcardamt").value=checkNull(curCardinfo.account2Amt);
    		document.getElementById("curcarddebtamt").value=checkNull(curCardinfo.account2debtAmt);
     	}
     	else if($("#factRechargeaccounttype").val()==3)
     	{
     		document.getElementById("curcardamt").value=checkNull(curCardinfo.account3Amt);
    		document.getElementById("curcarddebtamt").value=checkNull(curCardinfo.account3debtAmt);
     	}
     	else if($("#factRechargeaccounttype").val()==3)
     	{
     		document.getElementById("curcardamt").value=checkNull(curCardinfo.account3Amt);
    		document.getElementById("curcarddebtamt").value=checkNull(curCardinfo.account3debtAmt);
     	}
     	else if($("#factRechargeaccounttype").val()==4)
     	{
     		document.getElementById("curcardamt").value=checkNull(curCardinfo.account4Amt);
    		document.getElementById("curcarddebtamt").value=checkNull(curCardinfo.account4debtAmt);
     	}
     	else if($("#factRechargeaccounttype").val()==5)
     	{
     		document.getElementById("curcardamt").value=checkNull(curCardinfo.account5Amt);
    		document.getElementById("curcarddebtamt").value=checkNull(curCardinfo.account5debtAmt);
     	}
     	if(checkNull(document.getElementById("curcarddebtamt").value)>0)
    	{
    			handleRadio("rechargetype",6);
    	}
    	else
    	{
    				handleRadio("rechargetype",0);
    	}
     }
  
  	 //刷新
  	 function freshCurRecord()
  	 {
  	 	f_selectNode();
  	 }
     
     //保存
	 function editCurRecord()
        { 
        	if(parent.hasFunctionRights( "CC008",  "UR_POST")!=true)
        	{
        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
        		 return;
        	}
			if(pageState==3)
	   		{
	   			$.ligerDialog.warn("非新增单据,不可保存!");
	   			return;
	   		}
	   		else
	   		{
	   			 if(postState==1)
				 {
					$.ligerDialog.error("正在保存中,请不要连续保存!");
					return ;
				}
		   		postState=1;
		   		$.ligerDialog.confirm('确认保存当前续卡单?', function (result)
				{
				    if( result==true)
		           	{
			        	try
						{
							 if(fromValidate.element($("#rechargecardno"))==false ||
								fromValidate.element($("#rechargekeepamt"))==false 
							 )
							 {
							 	$.ligerDialog.error('填入信息有误,请确认!');
							 	postState=0;
							 	return;
							 }
							if(document.getElementById("strShareCondition").value=="1"
							 && document.getElementById("billinsertype").value=="0")
							 {
							 	$.ligerDialog.error('请选择充值主办方!');
							 	postState=0;
							 	return;
							 }
							document.getElementById("rechargeaccounttype_h").value=  $("#factRechargeaccounttype").val();
							
							if(document.getElementsByName("rechargetype")[0].checked==true)	
								document.getElementById("rechargetype_h").value= 0;
							else
								document.getElementById("rechargetype_h").value= 6;
							
							var requestUrl ="cc008/post.action";
							var params="error=0";
							 //------支付方式
							var strJsonParam_detial="";
							var curjosnparam="";
							var needReplaceStr="";
							var paymat=0;
							if(commoninfodivdetial_pay.rows.length*1>0)
							{
								   commoninfodivdetial_pay.endEdit();
							}	
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
									}	*/
									paymat=paymat*1+commoninfodivdetial_pay.records[rowid]['payamt']*1;
							          		   
									if(strJsonParam_detial!="")
									  	strJsonParam_detial=strJsonParam_detial+",";
									strJsonParam_detial= strJsonParam_detial+curjosnparam;
							} 
					  
							if(strJsonParam_detial!="")
							{
								 params=params+"&strJsonParam_pay=["+strJsonParam_detial+"]";
							}
							var prototalamt=0;
							strJsonParam_detial="";
					        for (var rowid in commoninfodivdetial_pro.records)
							{
									var row =commoninfodivdetial_pro.records[rowid];
									curjosnparam=JSON.stringify(row);
									if(curjosnparam.indexOf("_id")>-1)
									{
									   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
									   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
									}	  
									prototalamt=prototalamt*1+commoninfodivdetial_pro.records[rowid]['saleproamt']*1;
							                    		   
									if(strJsonParam_detial!="")
									  	strJsonParam_detial=strJsonParam_detial+",";
									strJsonParam_detial= strJsonParam_detial+curjosnparam;
							}        		 
							if(strJsonParam_detial!="")
							{
								 params=params+"&strJsonParam_pro=["+strJsonParam_detial+"]";
							}
						
							if(ForDight(paymat*1,1) != ForDight(document.getElementById("rechargekeepamt").value*1,1)+ForDight(prototalamt*1,1))  
							{
								$.ligerDialog.error('支付明细总额与续卡总额不一致,请确认!');
								postState=0;
							 	return;
							} 
							if(ForDight(document.getElementById("loeweAmt").value*1,1) > ForDight(document.getElementById("rechargekeepamt").value*1,1))  
							{
								$.ligerDialog.error('开卡金额不能低于最低售卡额度,请确认!');
								postState=0;
							 	return;
							}   
							if(document.getElementById("fifthsaleamt").value*1>0 && document.getElementById("fifthsaleamt").value*1!=document.getElementById("firstsaleamt").value)
							{
								if(document.getElementById("fifthsaleamt").value*1!=10000 ||document.getElementById("firstsaleamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第一销售分享金额不匹配");
									postState=0;
									return true;
								}
							}
							
							if(document.getElementById("sixthsaleamt").value*1>0 && document.getElementById("sixthsaleamt").value*1!=document.getElementById("secondsaleamt").value)
							{
								if(document.getElementById("sixthsaleamt").value*1!=10000 ||document.getElementById("secondsaleamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第二销售分享金额不匹配");
									postState=0;
									return true;
								}
							}
							
							if(document.getElementById("seventhsaleamt").value*1>0 && document.getElementById("seventhsaleamt").value*1!=document.getElementById("thirdsaleamt").value)
							{
								if(document.getElementById("seventhsaleamt").value*1!=10000 ||document.getElementById("thirdsaleamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第三销售分享金额不匹配");
									postState=0;
									return true;
								}
							}
							
							if(document.getElementById("fifthsalecashamt").value*1>0 && document.getElementById("fifthsalecashamt").value*1!=document.getElementById("firstsalecashamt").value)
							{
								if(document.getElementById("fifthsalecashamt").value*1!=10000 ||document.getElementById("firstsalecashamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第一销售分享业绩不匹配");
									postState=0;
									return true;
								}
							}
							
							if(document.getElementById("sixthsalecashamt").value*1>0 && document.getElementById("sixthsalecashamt").value*1!=document.getElementById("secondsalecashamt").value)
							{
								if(document.getElementById("sixthsalecashamt").value*1!=10000 ||document.getElementById("secondsalecashamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第二销售分享业绩不匹配");
									postState=0;
									return true;
								}
							}
							
							if(document.getElementById("seventhsalecashamt").value*1>0 && document.getElementById("seventhsalecashamt").value*1!=document.getElementById("thirdsalecashamt").value)
							{
								if(document.getElementById("seventhsalecashamt").value*1!=10000 ||document.getElementById("thirdsalecashamt").value*1<10000)
								{
									$.ligerDialog.error("烫染师与第三销售分享业绩不匹配");
									postState=0;
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
								if(ForDight(hairTotalAmt*1,1) > ForDight(document.getElementById("rechargekeepamt").value*1,1)+ForDight(prototalamt*1,1))  
								{
									$.ligerDialog.error('美发部分享金额不能超过售卡额度,请确认!');
									postState=0;
							 		return;
								} 
								if(ForDight(beatyTotalAmt*1,1) > ForDight(document.getElementById("rechargekeepamt").value*1,1)+ForDight(prototalamt*1,1))  
								{
									$.ligerDialog.error('美容部分享金额不能超过售卡额度,请确认!');
									postState=0;
							 		return;
								} 
							}
							else
							{
								var shareTotalAmt=document.getElementById("firstsaleamt").value*1+document.getElementById("secondsaleamt").value*1+document.getElementById("thirdsaleamt").value*1;
								if(ForDight(shareTotalAmt*1,1) > ForDight(document.getElementById("rechargekeepamt").value*1,1)+ForDight(prototalamt*1,1))  
								{
									$.ligerDialog.error('分享金额不能超过售卡额度,请确认!');
									postState=0;
							 		return;
								} 
							}
							
							
							if(document.getElementById("billinsertype").value==1 && ForDight(hairTotalAmt*1,1)>30000)
							{
								$.ligerDialog.error('美容部办卡美发部分享业绩不能超过30000,请确认!');
								postState=0;
							 	return;
							}
							var shareTotalCashAmt=document.getElementById("firstsalecashamt").value*1+document.getElementById("secondsalecashamt").value*1+document.getElementById("thirdsalecashamt").value*1;
							if(ForDight(shareTotalCashAmt*1,1) > ForDight(document.getElementById("rechargekeepamt").value*1,1)+ForDight(prototalamt*1,1))  
							{
								$.ligerDialog.error('分享业绩不能超过充值额度,请确认!');
								postState=0;
							 	return;
							}	
							
							var responseMethod="editMessage";
							
							if(document.getElementById("cardsendamtflag").value*1==1
						    && document.getElementById("rechargekeepamt").value*1>=20000)
						    {
						    	 $.ligerDialog.confirm('是否赠送卡金', function (result)
				        		 {
				        		 	if( result==true)
				           			{
				           						document.getElementById("sendamtflag").value=1;
												var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
												queryStringTmp=queryStringTmp.replace(/\+/g," ");
												params=params+"&"+queryStringTmp;
												checkWechatCode(requestUrl,responseMethod,params);
												/*showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
												sendRequestForParams_p(requestUrl,responseMethod,params );*/
				           			}
				           			else
				           			{
				           				document.getElementById("sendamtflag").value=0;
										var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
										queryStringTmp=queryStringTmp.replace(/\+/g," ");
										params=params+"&"+queryStringTmp;
										checkWechatCode(requestUrl,responseMethod,params);		
										/*showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
										sendRequestForParams_p(requestUrl,responseMethod,params );*/
				           			}
				        		 });
						    }
						    else
						    {
						    	document.getElementById("sendamtflag").value=0;
								var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
								queryStringTmp=queryStringTmp.replace(/\+/g," ");
								params=params+"&"+queryStringTmp;
								checkWechatCode(requestUrl,responseMethod,params);		
								/*showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
								sendRequestForParams_p(requestUrl,responseMethod,params );*/ 
							}
						}
						catch(e)
						{
							alert(e.message);
						}
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
				showDialogmanager.close();
				postState=0;
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        			viewTicketReport();
	        		 	curSecondCommonKey="";
	        		 	curSecondCommonValue="";
	        		 	$.ligerDialog.success("更新成功!");
	        		 	commoninfodivmaster.updateRow(curMasterRecord,{rechargecardno :document.getElementById("rechargecardno").value});
						addSaleInfo();
	        		 	
	        	
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
        
      
        
        function  itemclick_addpay (item)
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
        
        
        function itemclick_addpro(item)
        {
        	var row = commoninfodivdetial_pro.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial_pro.addRow({ 
				                saleproid: "",
				                saleproname: "",
				                saleprotype: "4",
				                saleprocardcount: "",
				             	saleprocount: "",
				             	sendprocount: "",
				             	saleproamt: "",
				             	saleproremark:""
				            }, row, false);
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
		
		
		function validateProId(obj)
		{
			var projectno=curProRecord.saleproid;
			var projecttype=curProRecord.saleprotype*1;
			if(projectno=="")
			{
				commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:'',saleproname: '',saleprotype:'4',saleprocardcount:'',saleprocount:'',sendprocount:'',saleproamt:'',saleproremark:''});
			}
			else
			{
				var changeFlag=0;
				if(lsProjectinfo!=null && lsProjectinfo.length>0)
				{
					for(var i=0;i<lsProjectinfo.length;i++)
					{
						if(projectno==lsProjectinfo[i].id.prjno  )
						{
							if(projecttype==0)
								commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:lsProjectinfo[i].id.prjno,saleproname: lsProjectinfo[i].prjname,saleprocardcount:'1',saleprocount:1,sendprocount:'0',saleproamt:lsProjectinfo[i].saleprice});
							else if(projecttype==1)
									commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:lsProjectinfo[i].id.prjno,saleproname: lsProjectinfo[i].prjname,saleprocardcount:'1',saleprocount:lsProjectinfo[i].msalecount,sendprocount:'0',saleproamt:lsProjectinfo[i].msaleprice});
							else if(projecttype==2)
									commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:lsProjectinfo[i].id.prjno,saleproname: lsProjectinfo[i].prjname,saleprocardcount:'1',saleprocount:lsProjectinfo[i].rsalecount,sendprocount:'0',saleproamt:lsProjectinfo[i].rsaleprice});
							else if(projecttype==3)
									commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:lsProjectinfo[i].id.prjno,saleproname: lsProjectinfo[i].prjname,saleprocardcount:'1',saleprocount:lsProjectinfo[i].hsalecount,sendprocount:'0',saleproamt:lsProjectinfo[i].hsaleprice});
							else if(projecttype==4)
									commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:lsProjectinfo[i].id.prjno,saleproname: lsProjectinfo[i].prjname,saleprocardcount:'1',saleprocount:lsProjectinfo[i].ysalecount,sendprocount:'0',saleproamt:lsProjectinfo[i].ysaleprice});
							
							changeFlag=1;
							validateRechargeAmt();
							break;
						}
					}
				}
				if(changeFlag==0)
				{
					$.ligerDialog.warn("输入的项目编码不存在!");
					commoninfodivdetial_pro.updateRow(curProRecord,{saleproid:'',saleproname: '',saleprotype:'4',saleprocardcount:'',saleprocount:'',sendprocount:'',saleproamt:'',saleproremark:''});
				}
			}
			//lsProjectinfo
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
			    			document.getElementById("fifthsalecashamt").value=0;
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
			    			document.getElementById("sixthsalecashamt").value=0;
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
			    			document.getElementById("seventhsalecashamt").value=0;
			    			obj.select();
			    			obj.focus()
			    			$.ligerDialog.warn("输入的员工编号不存在或不是烫染师!");
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
			}
			catch(e)
			{
				alert(e.message);
			}
		}
		
		//查询单据
		function searchCurRecord()
		{
			if(document.getElementById("strSearchBillId").value!="")
			{
				var requestUrl ="cc008/searchCurRecord.action"; 
				var responseMethod="searchCurRecordMessage";	
				var params="strSearchBillId="+document.getElementById("strSearchBillId").value;		
				params =params+ "&strCurCompId="+strCurCompId;	
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		}
		function searchCurRecordMessage(request)
	     {
	    		
	        	try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	if(responsetext.lsMcardrechargeinfo==null || responsetext.lsMcardrechargeinfo.length==0)
		        	{	        		 
		        		 $.ligerDialog.warn("无相关数据!");
		        		commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total:0});
	            		commoninfodivmaster.loadData(true);  
		        	}
		        	else
		        	{
		        		if(responsetext.lsMcardrechargeinfo!=null && responsetext.lsMcardrechargeinfo.length>0)
				   		{
				   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsMcardrechargeinfo,Total: responsetext.lsMcardrechargeinfo.length});
			            	commoninfodivmaster.loadData(true);            	
				   		}
				        if(commoninfodivmaster.rows.length>0)
						{
							commoninfodivmaster.select(0);
						}
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
	      
	      
	      //验证团购卡号
	      function validateCorpscardno(obj)
	      {
	      		if(obj.value=="")
	      		{
	      			commoninfodivdetial_pay.options.clickToEdit=true;
     				commoninfodivdetial_pay.options.enabledEdit=true;
     				commoninfodivdetial_pro.options.clickToEdit=true;
     				commoninfodivdetial_pro.options.enabledEdit=true;
	      			return ;
	      		}
	      		if(document.getElementById("salecardtype").value=="")
	      		{
	      			$.ligerDialog.warn("请确认充值卡类型!");
	      			document.getElementById("corpscardno").value="";
	      			document.getElementById("corpscardno").readOnly="";
	      			commoninfodivdetial_pay.options.clickToEdit=true;
     				commoninfodivdetial_pay.options.enabledEdit=true;
     				commoninfodivdetial_pro.options.clickToEdit=true;
     				commoninfodivdetial_pro.options.enabledEdit=true;
	      			return ;
	      		}
	      		var requestUrl ="cc008/validateCorpscardno.action"; 
				var responseMethod="validateCorpscardnoMessage";	
				var params="strCardType="+document.getElementById("salecardtype").value;		
				params =params+ "&strCorpscardno="+obj.value;	
				sendRequestForParams_p(requestUrl,responseMethod,params );
	      }
	      function validateCorpscardnoMessage(request)
          {
    		
	        	try
				{
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	        		 
		        	
		        		document.getElementById("saletotalamt").value=responsetext.cropsCardAmt;
		        		document.getElementById("corpscardno").readOnly="readOnly";
		        		document.getElementById("saletotalamt").readOnly="readOnly";
		        		document.getElementById("salecardno").readOnly="readOnly";
		        		commoninfodivdetial_pay.options.data=$.extend(true, {},{Rows: null,Total: 0});
						commoninfodivdetial_pay.loadData(true);   
						commoninfodivdetial_pro.options.data=$.extend(true, {},{Rows: null,Total: 0});
						commoninfodivdetial_pro.loadData(true); 
						itemclick_addpay("");
						var payrecord=commoninfodivdetial_pay.getRow(0)
		        		commoninfodivdetial_pay.updateRow(payrecord,{paymode:'16',payamt: responsetext.cropsCardAmt});
						commoninfodivdetial_pay.options.clickToEdit=false;
     					commoninfodivdetial_pay.options.enabledEdit=false;
     					commoninfodivdetial_pro.options.clickToEdit=false;
     					commoninfodivdetial_pro.options.enabledEdit=false;
		        	}
		        	else
		        	{
		        		document.getElementById("corpscardno").value="";
		        		document.getElementById("corpscardno").readOnly="";
		        		commoninfodivdetial_pay.options.clickToEdit=true;
     					commoninfodivdetial_pay.options.enabledEdit=true;
     					commoninfodivdetial_pro.options.clickToEdit=true;
     					commoninfodivdetial_pro.options.enabledEdit=true;
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
          }
          
         function validatesaleprocardcount(obj)
          {
	          	if(obj.value=="" || obj.value==0)
	          	{
	          		obj.value=1;
	          	}
	          	var dsaleprocount=checkNull(curProRecord.saleprocount)*1;
	          	var dsaleproamt=checkNull(curProRecord.saleproamt)*1;
	          	commoninfodivdetial_pro.updateRow(curProRecord,{saleprocount:ForDight(obj.value*1*dsaleprocount,1),saleproamt:ForDight(obj.value*1*dsaleproamt,1)});
				
          		validateRechargeAmt();
          }
        
        //验证充值金额
        function validateRechargeAmt()
        {
        	try
        	{
	        	
	        	var totalamt=0;
	        	for (var rowid in commoninfodivdetial_pro.records)
				{
					totalamt=totalamt*1+commoninfodivdetial_pro.records[rowid]['saleproamt']*1;
				} 
				totalamt=ForDight(totalamt*1+document.getElementById("rechargekeepamt").value*1,1);       
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
	     function readCurCardInfo()
        {
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("rechargecardno").value=cardNo;
				validateSalecardno(document.getElementById("rechargecardno"));
	    	}
        } 
        
        function viewTicketReport()
		{
			if(document.getElementById("rechargecardno").value=="")
			{
				$.ligerDialog.warn("请确认充值卡号后在打印!");
				return;
			}
		  	var requestUrl ="cc008/viewTicketReport.action"; 
			var responseMethod="viewTicketReportMessage";	
			var params =  "strCurCompId=" + document.getElementById("rechargecompid").value + "&strCurBillId=" + document.getElementById("rechargebillid").value;		
			params =params+ "&strCardNo="+document.getElementById("rechargecardno").value;	
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function viewTicketReportMessage(request)
		{
		  try
		  {
		    var responsetext = eval("(" + request.responseText + ")");
		  	Stand_CheckPrintControl();//检查是否有打印控件
			Stand_InitPrint("充值模块_小票打印作业");
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
		   document.getElementById("memberCardId_print").innerHTML=responsetext.strCardNo;
		   document.getElementById("memberCardType_print").innerHTML= document.getElementById("rechargecardtypeName").value;
		   document.getElementById("keepAmount_print").innerHTML=maskAmt(checkNull(document.getElementById("rechargekeepamt").value*1),2);
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
	    	/*if(checkNull(randomno)==""){
	    		return;
	    	}*/
	    	document.getElementById("rechargecardno").value=randomno;
			validateSalecardno(document.getElementById("rechargecardno"));
	    	/*var params = {"strRandomno":randomno}
	    	var url = contextURL+"/cc011/checkWXRandomno.action"; 
	    	$.post(url, params, function(data){
	    		if(data.strMessage!=""){
	    			$.ligerDialog.warn(data.strMessage);
	    			$("#randomno").val("");
	    		}else{
	    			document.getElementById("rechargecardno").value=data.wxbandcard.cardno;
					validateSalecardno(document.getElementById("rechargecardno"));
	    		}
	    	}).error(function(e){$.ligerDialog.error("读取信息失败，请重试！");});*/
	    }
		//微信扫码验证微信验证码
		var showWechatPwd = null;
		function checkWechatCode(requestUrl,responseMethod,params){
	 		 if($("#randomno").val()!=""){//微信扫码弹出输入验证码
				 var url = contextURL+"/cc008/sendWechatPwd.action"; 
				 var _params = {"strCardNo":$("#rechargecardno").val(),"_random": Math.random()};
				 $.ajax({
		             type:"POST", url:url, data:_params, dataType:"json",
		             success: function(data){
		            	 if(checkNull(data.strMessage)=="true"){
							showWechatPwd = $.ligerDialog.open({ height:170, url: contextURL+'/CardControl/CC011/checkWechatPwd.jsp', 
				    			width: 360,showMax: false, showToggle: false, allowClose:false, showMin: false, isResize: false, title:"输入微信验证码",
			    				buttons:[{text:'确定', onclick:function(item, dialog){
			    					var _frame = $(dialog.frame.document);
			    			    	var pwd_code = $("#pwd_code", _frame).val();
			    			    	if(checkNull(pwd_code)==""){
			    						$.ligerDialog.warn("请输入验证码！");
			    						postState=0;
			    					}else{
			    						$("#pwd_code", _frame).attr("readonly",true);
			    						var url = contextURL+"/cc008/validateWechatPwd.action";
			    						var _params = {"strCardNo": pwd_code,"_random": Math.random()};
			    						$.ajax({
			   				             type:"POST", url:url, data:_params, dataType:"json",
			   				             	success: function(data){
			   				             		if(checkNull(data.strMessage)!=""){
			   				             			$.ligerDialog.warn(data.strMessage);
			   				             			$("#pwd_code", _frame).attr("readonly",false);
			   				             			postState=0;
			   				             		}else{
			   				             			showWechatPwd.close();
													params =params+ "&strCardNo="+$("#rechargecardno").val();
													for (var rowid in commoninfodivdetial_pay.records){
														 var row =commoninfodivdetial_pay.records[rowid];
															if(checkNull(row.paymode)=="15"){
																payTotalBank=payTotalBank*1+commoninfodivdetial_pay.records[rowid]['payamt']*1;
																isThreePay = true;
															}
													}
													if(isThreePay){
														//alert(paymat);
														threeParams = params;
														showDialogPayMehod=$.ligerDialog.open({ height: 140, url: contextURL+'/CardControl/CC011/showPayMethod.jsp', width: 520, showMax: false, showToggle: false,allowClose:false, showMin: false, isResize: false, title: '选择支付方式' });
													}else{
														showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
														sendRequestForParams_p(requestUrl,responseMethod,params );
													}
			   				             		} 
			   				             	},
			   				             	error: function(XMLHttpRequest, textStatus, errorThrown){
			   				             		$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
			   				             	}
			    						});
			    					}
			    				}}, {text:'取消', onclick:function(item, dialog){postState=0;dialog.close();}}]
							});
						}else{
							$.ligerDialog.error(data.strMessage);
							postState=0;
						}         
		             },
		             error: function(XMLHttpRequest, textStatus, errorThrown){
		            	$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
		             }
		         });
			 }else{
				var isThreePay = false;
				for (var rowid in commoninfodivdetial_pay.records){
					 var row =commoninfodivdetial_pay.records[rowid];
						if(checkNull(row.paymode)=="15"){
							payTotalBank=payTotalBank*1+commoninfodivdetial_pay.records[rowid]['payamt']*1;
							isThreePay = true;
						}
				}
				if(isThreePay){
					threeParams = params;
					showDialogPayMehod=$.ligerDialog.open({ height: 140, url: contextURL+'/CardControl/CC011/showPayMethod.jsp', width: 520, showMax: false, showToggle: false,allowClose:false, showMin: false, isResize: false, title: '选择支付方式' });
				}else{
					showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
			 }
		}
		
		//微信支付、支付宝支付
	    function payMethod(paymethod){
	    	showDialogPayMehod.close();
	    	if(paymethod=="1" || paymethod=="2"){
	    		var _title = null, _paymethod=null;
	    		if(paymethod=="1"){
	    			_title ="支付宝支付"
	    				_paymethod='ali';
	    		}else{
	    			_title = "微信支付";
	    			_paymethod="wechat";
	    		}
	    		showDialogpayment = $.ligerDialog.open({ height:30, url: contextURL+'/CardControl/CC011/showPayment.jsp', 
	    			width: 460,showMax: false, showToggle: false, allowClose:false, showMin: false, isResize: false, title:_title, paymethod:_paymethod});
	    		postState=0;
	    	}else if(paymethod=="3"){
	    		showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');	
	    		var requestUrl ="cc008/post.action";
	    		var responseMethod="editMessage";
	    		var _params = "&scanpaytype=3&totalBank="+payTotalBank;
	    		sendRequestForParams_p(requestUrl,responseMethod,threeParams+_params);
	    	}else{
	    		postState=0;
	    	}
	    }
	    var showDialogPay=null,waitDialogPay=null,showDialogRe=null,intval=null,
	    	t_auth_code=null,t_paymethod=null,t_outTradeNo=null;
	    function handPayment(paymethod, auth_code){
	    	t_paymethod = paymethod;
	    	t_auth_code = auth_code;
	    	if(checkNull(auth_code)==""){
	    		$.ligerDialog.error("获取支付条码失败，请刷新或关闭页面重试！");
	    		return;
	    	}
	    	//payTotalBank="0.01";//测试支付时使用
	    	showDialogPay = $.ligerDialog.waitting('正在支付中,请稍候...');
	    	var params = {"strCurBillId":$("#rechargebillid").val(),"auth_code":auth_code,"totalBank":payTotalBank,"_random": Math.random()};
			var url = contextURL+"/cc011/"+ paymethod +"Pay.action"; 
			$.post(url, params, function(data){
				t_outTradeNo=data.outTradeNo;
				showDialogpayment.close();
				showDialogPay.close();
				if(checkNull(data.strMessage)=="OK"){
					postOrders();//提交消费单据
				}else if(checkNull(data.strMessage)=="WAITING"){
					$.extend($.ligerDefaults.DialogString, {ok: '取消单据'});
					waitDialogPay = $.ligerDialog.alert('客人正在输入密码,请稍候...', function(){
						clearInterval(intval);//等待状态中，可主动撤销支付订单
						payReverse();
					});
					$.extend($.ligerDefaults.DialogString, {ok: '确定'});
					intval=setInterval(function(){//轮询支付单据状态
						payState();
					},1500);
				}else if(checkNull(data.strMessage)=="ERROR"){//系统异常查询单据状态
					payState();
				}else{
					$.ligerDialog.error(data.strMessage);
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
	    }
	    //轮询支付订单状态
	    function payState(){
	    	var params = {"outTradeNo":t_outTradeNo,"_random": Math.random()};
			var url = contextURL+"/cc011/"+ t_paymethod +"State.action"; 
			$.post(url, params, function(data){
				if(checkNull(data.strMessage)=="OK"){
					clearInterval(intval);
					waitDialogPay.close();
					postOrders();
				}else if(checkNull(data.strMessage)=="WAITING"){
					//为等待状态，则继续轮询
				}else if(checkNull(data.strMessage)=="ERROR"){
					waitDialogPay.close();
					var dialog = $.ligerDialog.waitting('单据异常！系统准备撤销订单，请稍候...');
					setTimeout(function(){
						dialog.close();
						payReverse();
					}, 3000);
				}else{
					clearInterval(intval);
					waitDialogPay.close();
					$.ligerDialog.error(data.strMessage);
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
	    }
	    //主动撤销支付订单
	    function payReverse(){
	    	if(waitDialogPay){
	    		waitDialogPay.close();
	    	}
	    	showDialogRe = $.ligerDialog.waitting('单据撤销中,请稍候...');
	    	var params = {"strCurBillId":$("#rechargebillid").val(),"outTradeNo":t_outTradeNo,"totalBank":payTotalBank,"_random": Math.random()};
			var url = contextURL+"/cc011/"+ t_paymethod +"Reverse.action"; 
			$.post(url, params, function(data){
				showDialogRe.close();
				if(checkNull(data.strMessage)=="OK"){
					$.ligerDialog.success("单据撤销成功！请重新扫描条码支付。");
				}else if(checkNull(data.strMessage)=="ERROR"){
					callPayReverse();
				}else{
					$.ligerDialog.error(data.strMessage);
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
	    }
	    function callPayReverse(){
	    	showDialogRe = $.ligerDialog.waitting("单据撤销状态查询中，请稍候...");
	    	var params = {"outTradeNo":t_outTradeNo,"_random": Math.random()};
			var url = contextURL+"/cc011/"+ t_paymethod +"State.action"; 
			$.post(url, params, function(data){
				showDialogRe.close();
				if(checkNull(data.strMessage)=="OK"){
					$.ligerDialog.success("单据撤销成功！请重新扫描条码支付。");
				}else if(checkNull(data.strMessage)=="ERROR"){
					callPayReverse();
				}else{
					$.ligerDialog.error(data.strMessage);
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");});
	    }
	    //保存消费信息
	    function postOrders(){
	    	showDialogmanager = $.ligerDialog.waitting('支付完成，正在保存中,请稍候...');	
	    	var requestUrl =contextURL+"/cc008/post.action";
			var responseMethod="editMessage";
			var _params = "&scanpaytype="+(t_paymethod=="ali"?"1":"2");
				_params += "&auth_code="+t_auth_code+"&outTradeNo="+t_outTradeNo+"&totalBank="+payTotalBank;
			sendRequestForParams_p(requestUrl,responseMethod,threeParams+_params); 
	    }