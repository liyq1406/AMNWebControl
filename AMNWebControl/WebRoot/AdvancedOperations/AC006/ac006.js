   	var compTreeManager;
   	var compTree;
   	var strCurCompId="001";
   	var ac006layout=null;
   	var tgtypemanager=null;
   	var commoninfodivdetial=null;
   	var curDetialRecord=null;
   	var purchaserecardtypemanager=null;
   	var lsProjectinfo=null;
   	$(function ()
   	{
	   
	   		  //布局
            ac006layout= $("#ac006layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
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
          	$("#pageloading").hide(); 
          	$("#handInerCardInfo").ligerButton(
	         {
	             text: '登记卡段', width: 80,
		         click: function ()
		         {
		             handInerCardInfo();
		         }
	         });
	         
	          $("#handInerChangeVesting").ligerButton(
	         {
	             text: '更新归属', width: 100,
		         click: function ()
		         {
		             changeInnerVesting();
		         }
	         });
	         
	         
	       $("#innerCardBarTool").ligerToolBar({ items: [
      		    { text: '登记', click: handInerCardInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/myaccount.gif' },
          		{ text: '新归属:<input type="text"  name="strNewInerVesting" id="strNewInerVesting"  style="width:60;" /> ' },
	            { text: '修改归属', click: changeInnerVesting, img: contextURL+'/common/ligerui/ligerUI/skins/icons/config.gif' }
            ]
            });
            
	         $("#handTmOuterCardInfo").ligerButton(
	         {
	             text: '登记条码卡段', width: 100,
		         click: function ()
		         {
		             handTmCardInfo();
		         }
	         });
	          $("#handTmOuterChangeVesting").ligerButton(
	         {
	             text: '更新归属', width: 100,
		         click: function ()
		         {
		             handTmOuterChange();
		         }
	         });
	         
	          $("#handTgOuterCardInfo").ligerButton(
	         {
	             text: '登记团购卡', width: 120,
		         click: function ()
		         {
		             handTgCardInfo();
		         }
	         });
	         $("#handDyqOuterCardInfo").ligerButton(
	         {
	             text: '登记抵用券', width: 120,
		         click: function ()
		         {
		             handDyqCardInfo();
		         }
	         });
	          $("#handPurchasereCardInfo").ligerButton(
	         {
	             text: '登记收购卡', width: 120,
		         click: function ()
		         {
		             handPurchasereCardInfo();
		         }
	         });
	         var  tgtypeData=JSON.parse(parent.loadCommonControlDate("TGLX",0));
	         tgtypemanager = $("#corpssource").ligerComboBox({ data: tgtypeData, isMultiSelect: false,valueFieldID: 'factCorpssource',width:'120'});	
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '项目编号', 	name: 'ineritemno',  	width:140,align: 'left',
			 	 	editor: { type: 'select', data: null, url:'loadAutoProject',autocomplete: true, valueField: 'choose',onChanged : validateItem,selectBoxWidth:300,selectBoxHeight:300}
                },
                { display: '项目名称', 	name: 'ineritemname', 	width:180,align: 'left'},
	            { display: '次数', 	name: 'entrycount', 		width:60,align: 'left' ,editor: { type: 'float' } },
	            { display: '金额', 	name: 'entryamt', 		width:60,align: 'left' ,editor: { type: 'float' } }
                ],  pageSize:25, 
                data:{Rows: null,Total:0},      
                width: '520',
                height:'300',
                clickToEdit: true,   enabledEdit: true,  
                rownumbers: true,usePager: false  ,
                onSelectRow : function (data, rowindex, rowobj)
                {
                      curDetialRecord = data;
                }	   
            });
          	$("#enabledate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'150' });
          	$("#validate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'150' });
          	$("#memberbrithday").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'150' });
          	var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("enabledate").value=today;
      		f_selectNode();
   		
    });
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
		for(var i=0;i<lsProjectinfo.length;i++)
		{
			if(projectcount*1==10)
			{
				break;
			}
			if(curWriteItemNo!="" 
			 && lsProjectinfo[i].id.prjno.indexOf(curWriteItemNo)==-1
			 && checkNull(lsProjectinfo[i].prjabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1
			 && checkNull(toPinyin(lsProjectinfo[i].prjname)).indexOf(curWriteItemNo.toUpperCase())==-1)
			{
				continue;
			}
		   	key = lsProjectinfo[i].id.prjno;
			value = lsProjectinfo[i].prjname;
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
    
	function fullStr( str )
	{
			if( str.length == 1 )
			{
				str = "0" + str;
			}
			return str;
	}
    function f_selectNode()
	{
    	//alert(parent.localCompid);
    	//var data=new Object();
    	//data.id=parent.localCompid;
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
        	var strCurCompId=note.data.id;
        	document.getElementById("strInerHomeCompid").value=strCurCompId;
        	document.getElementById("strInerHomeCompName").value=note.data.text;
        	document.getElementById("cardvesting").value=strCurCompId;
        	document.getElementById("cardvestingname").value=note.data.text;
        	document.getElementById("strTmHomeCompid").value=strCurCompId;
        	document.getElementById("strTmHomeCompName").value=note.data.text;
        	document.getElementById("strDyqHomeCompid").value=strCurCompId;
        	document.getElementById("strDyqHomeCompName").value=note.data.text;
       		var params = "strCurCompId="+strCurCompId;				
     		var requestUrl ="ac006/loadInfo.action"; 
			var responseMethod="loadInfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params);
          }catch(e){alert(e.message);}
    }
    function loadInfoMessage(request)
    {
		try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		var returnValue='';
    		
    		if(responsetext.lsCardtypeinfos!=null && responsetext.lsCardtypeinfos.length>0)
	   		{
	   			clearOption("strInerCardType");
	   			addOption("","请选择",document.getElementById("strInerCardType"))
				addOption("","请选择",document.getElementById("registercardtype"))
				for(var i=0;i<responsetext.lsCardtypeinfos.length;i++)
				{
				    
					/*if(returnValue!='')
					{
						returnValue=returnValue+',';
					}
					else
					{
						returnValue=returnValue+'[';
					}*/
					//returnValue=returnValue+'{"id": "'+responsetext.lsCardtypeinfos[i].id.cardtypeno+'","text": "'+responsetext.lsCardtypeinfos[i].cardtypename+'"}';
					addOption(responsetext.lsCardtypeinfos[i].id.cardtypeno,responsetext.lsCardtypeinfos[i].id.cardtypeno+"-"+responsetext.lsCardtypeinfos[i].cardtypename,document.getElementById("strInerCardType"))
					addOption(responsetext.lsCardtypeinfos[i].id.cardtypeno,responsetext.lsCardtypeinfos[i].id.cardtypeno+"-"+responsetext.lsCardtypeinfos[i].cardtypename,document.getElementById("registercardtype"))
				}
	   		}
	   		 	
	   		/*if(returnValue!='')
	   		{
	   			
				returnValue=returnValue+']';	
				
		   		cinsertcardtypemanager= $("#strInerCardType").ligerComboBox({ 
	                data: JSON.parse(returnValue)
	                , valueFieldID: 'factcStrInerCardType',label:'请选卡类型',labelWidth:200,labelAlign:'center',width:'160',selectBoxHeight:'120',resize:false
	            }); 
	            
	           
	        }
	        else
	        {
	        	cinsertwaremanager= $("#strInerCardType").ligerComboBox({
	                data: null , valueFieldID: 'factcStrInerCardType',label:'选择卡类型',labelWidth:200,labelAlign:'center',width:'160',selectBoxHeight:'320'
	            }); 
	           
	        }*/
	        commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        commoninfodivdetial.loadData(true);   
	        var i=0;
	        while(i<10)
	        {
	        	addDetialRecord();
	        	i++;
	        }
	        this.lsProjectinfo=responsetext.lsProjectinfo;
	   	}
	   	catch(e){alert(e.message);}
    }
    //登记储值卡
    function handInerCardInfo()
    {
    	var strFromInerCardNo=document.getElementById("strFromInerCardNo").value;
    	var strToInerCardNo=document.getElementById("strToInerCardNo").value;
    	var strInerCardType=document.getElementById("strInerCardType").value;
    	if(strFromInerCardNo=="" || strToInerCardNo=="" ||  strInerCardType=="")
    	{
    		 $.ligerDialog.warn("储值卡录入信息不完整,不能登记!");
    		  return;
    	}
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromInerCardNo="+strFromInerCardNo;			
    	params=params+"&strToInerCardNo="+strToInerCardNo;	
    	params=params+"&strInerCardType="+strInerCardType;	
     	var requestUrl ="ac006/handInerCard.action"; 
		var responseMethod="handInerCardMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    

    
    function handInerCardMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn("录入成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	
   	function handPurchasereCardInfo()
    {
    	var registercardno=document.getElementById("registercardno").value;
    	var registercardtype=document.getElementById("registercardtype").value;
    	if(registercardno=="" || registercardtype=="")
    	{
    		 $.ligerDialog.warn("信息不完整,不能登记!");
    		  return;
    	}
    	var params = "cardvesting="+document.getElementById("cardvesting").value;	
    	params=params+"&registercardno="+registercardno;	
    	params=params+"&registercardtype="+registercardtype;	
    	params=params+"&membername="+document.getElementById("membername").value;
    	params=params+"&memberphone="+document.getElementById("memberphone").value;
    	params=params+"&memberbrithday="+document.getElementById("memberbrithday").value;
    	params=params+"&membersex="+document.getElementById("membersex").value;
    	params=params+"&cardbalance="+document.getElementById("cardbalance").value;
    	params=params+"&registerpcid="+document.getElementById("registerpcid").value;
     	var requestUrl ="ac006/handPurchasereCardInfo.action"; 
		var responseMethod="handPurchasereCardInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function handPurchasereCardInfoMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn("录入成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
    
   	
   	//登记条码卡
    function handTmCardInfo()
    {
    	var strFromTmCardNo=document.getElementById("strFromTmCardNo").value;
    	var strToTmCardNo=document.getElementById("strToTmCardNo").value;
    	var strTmCardBef=document.getElementById("strTmCardBef").value;
    	if(strFromTmCardNo=="" || strToTmCardNo=="" ||  strTmCardBef=="")
    	{
    		 $.ligerDialog.warn("条码卡录入信息不完整,不能登记!");
    		 return;
    	}
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromTmCardNo="+strFromTmCardNo;			
    	params=params+"&strToTmCardNo="+strToTmCardNo;	
    	params=params+"&strTmCardBef="+strTmCardBef;	
     	var requestUrl ="ac006/handTmCard.action"; 
		var responseMethod="handTmCardMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    function handTmCardMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn("录入成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	
   	
   	 	//登记条码卡
    function handTmOuterChange()
    {
    	var strFromTmCardNo=document.getElementById("strFromTmCardNo").value;
    	var strToTmCardNo=document.getElementById("strToTmCardNo").value;
    	var strTmCardBef=document.getElementById("strTmCardBef").value;
    	if(strFromTmCardNo=="" || strToTmCardNo=="" ||  strTmCardBef=="")
    	{
    		 $.ligerDialog.warn("条码卡录入信息不完整,不能登记!");
    		 return;
    	}
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strFromTmCardNo="+strFromTmCardNo;			
    	params=params+"&strToTmCardNo="+strToTmCardNo;	
    	params=params+"&strTmCardBef="+strTmCardBef;	
     	var requestUrl ="ac006/handTmOuterChange.action"; 
		var responseMethod="handTmOuterChangeMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    function handTmOuterChangeMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.success("更新成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	
   	function changeInnerVesting()
   	{
   		var strFromInnerCardNo=document.getElementById("strFromInerCardNo").value;
    	var strToInnerCardNo=document.getElementById("strToInerCardNo").value;
    	var strNewInerVesting=document.getElementById("strNewInerVesting").value;
    	if(strFromInnerCardNo=="" || strToInnerCardNo=="" || strNewInerVesting=="")
    	{
    		 $.ligerDialog.warn("卡号段信息不完整,不能修改!");
    		 return;
    	}
    	var params = "strCurCompId="+strNewInerVesting;	
    	params=params+"&strFromInerCardNo="+strFromInnerCardNo;			
    	params=params+"&strToInerCardNo="+strToInnerCardNo;
     	var requestUrl ="ac006/handInerChangeVesting.action"; 
		var responseMethod="handInerChangeVestingMessage";	
		$.ligerDialog.confirm('确认将会员卡 '+strFromInnerCardNo+' 至'+strToInnerCardNo +'的归属更新成'+strNewInerVesting, function (result)
		{
			if( result==true)
			{
				sendRequestForParams_p(requestUrl,responseMethod,params );
			}
		});  
					
   	}
   	
   	 function handInerChangeVestingMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.success("更新成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	//团购内容验证
   	function validateCorpspicno(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("corpspicname").value="";
   		}
   		else
   		{
   			var params = "strCurCompId="+strCurCompId;	
   			params=params+"&corpspicno="+obj.value;	
	    	params=params+"&corpstype="+document.getElementById("corpstype").value;
	     	var requestUrl ="ac006/validateCorpspicno.action"; 
			var responseMethod="validateCorpspicnoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
   		}
   	}
   	
   	function validateCorpspicnoMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        	}
	        	else
	        	{
	        		 document.getElementById("corpspicname").value=responsetext.corpspicname;
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	
   	
   	function handTgCardInfo()
   	{
   		var corpscardno=document.getElementById("corpscardno").value;
    	var corpstype=document.getElementById("corpstype").value;
    	var corpssource=$("#factCorpssource").val(); 
    	var corpspicno=document.getElementById("corpspicno").value;
    	var corpsamt=document.getElementById("corpsamt").value;
    	if(corpscardno=="" || corpspicno=="" ||  corpsamt=="" || corpsamt==0)
    	{
    		 $.ligerDialog.warn("团购卡录入信息不完整,不能登记!");
    		  return;
    	}
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&corpscardno="+corpscardno;			
    	params=params+"&corpstype="+corpstype;	
    	params=params+"&corpssource="+corpssource;	
    	params=params+"&corpspicno="+corpspicno;	
    	params=params+"&corpsamt="+corpsamt;	
     	var requestUrl ="ac006/handTgCard.action"; 
		var responseMethod="handTgCardMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	
   	function handTgCardMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn("录入成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}
   	
    function validateItem(obj)
   	{
   		if(obj.value=="")
   		{
   			commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
   		}
   		else
   		{
   			var existscount=0;
   			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				if(obj.value==row.ineritemno)
				{
					existscount=existscount*1+1;
				}
			}  
			
			if(existscount>1)
			{
				commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
				$.ligerDialog.warn("该项目编号已经存在于此项目抵用券中!");
				return ;
			}
   			var requestUrl ="ac006/validateCorpspicno.action";
        	var params = "strCurCompId="+strCurCompId;	
        	params=params+"&corpspicno="+obj.value;	
	    	params=params+"&corpstype=1 ";
			var responseMethod="validateProjectNoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
   		}
   	}
   	function validateProjectNoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		$.ligerDialog.warn(strMessage);
	       		commoninfodivdetial.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
	       	}
	       	else
	       	{
	       		commoninfodivdetial.updateRow(curDetialRecord,{ineritemname:responsetext.corpspicname,entrycount:1}); 
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function addDetialRecord()
   	{
   		var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				                ineritemno: "",
				                ineritemname: "",
				                entrycount: "",
				                entryamt: ""
				            }, row, false);
   	}
   	
   	function handDyqCardInfo()
   	{

    	var strDyqCardBef=document.getElementById("strDyqCardBef").value;
    	var strFromDyqCardNo=document.getElementById("strFromDyqCardNo").value;
    	var strToDyqCardNo=document.getElementById("strToDyqCardNo").value; 
    	var dyqType=document.getElementById("dyqType").value; 
    	var dyqCardAmt=document.getElementById("strDyqCardAmt").value; 
    	var validate=document.getElementById("validate").value; 
    	var enabledate=document.getElementById("enabledate").value; 
    	var createtype=document.getElementById("createtype").value; 
    	if(strDyqCardBef=="" || strFromDyqCardNo=="" ||  strToDyqCardNo=="" || strDyqCardAmt*1==0 || validate=="" || enabledate=="")
    	{
    		 $.ligerDialog.warn("抵用券录入信息不完整,不能登记!");
    		  return;
    	}
    	var params = "strCurCompId="+strCurCompId;	
    	params=params+"&strDyqCardBef="+strDyqCardBef;			
    	params=params+"&strFromDyqCardNo="+strFromDyqCardNo;	
    	params=params+"&strToDyqCardNo="+strToDyqCardNo;	
    	params=params+"&dyqType="+dyqType;	
    	params=params+"&dyqCardAmt="+dyqCardAmt;
    	params=params+"&validate="+validate;
    	params=params+"&enabledate="+enabledate;
    	params=params+"&createtype="+createtype;	
    	var curjosnparam="";
        var needReplaceStr="";
        var strJsonParam_detial="";
        //------卡号段列表
        for (var rowid in commoninfodivdetial.records)
		{
				var row =commoninfodivdetial.records[rowid];
				curjosnparam=JSON.stringify(row);
				/*if(curjosnparam.indexOf("_id")>-1)
				{
				   	needReplaceStr=curjosnparam.substring(curjosnparam.indexOf("_id")-3,curjosnparam.length-1);
				   	curjosnparam=curjosnparam.replace(needReplaceStr,"");
				}*/	            		   
				if(strJsonParam_detial!="")
				  	strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
		}            		 
		if(strJsonParam_detial!="")
		{
			 params=params+"&strJsonParam=["+strJsonParam_detial+"]";
		}
		var requestUrl ="ac006/handDyqCard.action"; 
		var responseMethod="handDyqCardMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	
   	function handDyqCardMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 
	        	}
	        	else
	        	{
	        		 $.ligerDialog.warn("录入成功");
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
   	}