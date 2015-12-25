	var compTreeManager;
   	var compTree;
	var commoninfodivmaster = null;
	var commoninfodivdetial = null;
	var strCurCompId = "";
	var strCurBillId="";
	var cc010layout=null;
	var curMasterInfoDate = null;
	var curpagestate =3;
	var curDetialRecord = null;
	var cardStateDialog=null;
	var postState=0;
	var curMaster=null;
	var curCardinfo=null;
	var cardStateData=JSON.parse(parent.loadCommonControlDate_select("HYZT",0));
	var billChooseData = [{ choose: 0, text: '已登记' }, { choose: 1, text: '已挂失'}, { choose: 2, text: '已解挂'}, { choose: 3, text: '已同意'}, { choose: 4, text: '已补卡'}, { choose: 5, text: '已驳回'}];
    $(function ()
   	{
	   try
	   {
	   		cc010layout= $("#cc010layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false,isLeftCollapse:true });
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
                { display: '门店', 			name: 'bchangecompid', 			width:50	,align: 'left' },
                { display: '挂失卡号', 		name: 'changebeforcardno', 		width:100	,align: 'left' },
                { display: '挂失日期', 		name: 'changedate',  			width:80	,align: 'left'},
                { display: '补卡卡号', 		name: 'changeaftercardno', 		width:100	,align: 'left' },
                { display: '补卡日期', 		name: 'financedate',  			width:80	,align: 'left'},
                { display: '状态', 			name: 'billflag', 			width:50	,align: 'left' , 
	                editor: { type: 'select', data: billChooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.billflag == 0) return '已登记';
	                    else if (item.billflag == 1) return '已挂失';
	                    else if (item.billflag == 2) return '已解挂';
	                    else if (item.billflag == 3) return '已同意';
	                    else if (item.billflag == 4) return '已补卡';
	                    else if (item.billflag == 5) return '已驳回';
	                }
	            },
                { name: 'bchangebillid',  		hide:true}
	            ],  pageSize:20, 
                data: null,      
                width: '500',
                height:height-80,
                enabledEdit: false,  checkbox: false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecDetialData(data, rowindex, rowobj);
                }   
            });
           
             commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                { display: '会员卡号', 		name: 'bcardno', 				width:100	,align: 'left'  },
                { display: '卡类', 			name: 'cardtype',  				width:60	,align: 'left'},
                { display: '状态', 			name: 'cardstate',  			width:60	,align: 'left' ,
                	editor: { type: 'select', data: cardStateData, valueField: 'choose'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("HYZT",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.cardstate)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '归属', 			name: 'bcardvesting',  			width:60	,align: 'center' },
               	{ display: '储值账户', 		name: 'account2Amt',  			width:60	,align: 'left'},
                { display: '疗程账户', 		name: 'account4Amt',  			width:60	,align: 'left'},
                { display: '姓名', 			name: 'membername',  			width:60	,align: 'left'},
                { display: '手机号码', 		name: 'showmemberphone',  			width:120	,align: 'left'},
                { display: '身份证号', 		name: 'memberpaperworkno',  	width:140	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: '780',
                height:200,
                enabledEdit: false,  checkbox: false,rownumbers:false,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                    loadSelecCardNoData(data, rowindex, rowobj);
                }   
            });
            
         
            $("#searchBill").ligerButton(
	         {
	             text: '查询单号', width: 100,
		         click: function ()
		         {
		             searchBill();
		         }
	         });
             $("#searchCard").ligerButton(
	         {
	             text: '查询卡号', width: 100,
		         click: function ()
		         {
		             searchCard();
		         }
	         });
             $("#changeoutCard").ligerButton(
	         {
	             text: '会员挂失', width: 100,
		         click: function ()
		         {
		             handlossCard();
		         }
	         });	
              $("#changeinCard").ligerButton(
	         {
	             text: '会员解挂', width: 100,
		         click: function ()
		         {
		             handReceiveCard();
		         }
	         });
	         $("#entryReceviceCard").ligerButton(
	         {
	             text: '同意补卡', width: 100,
		         click: function ()
		         {
		             entryReceviceCard();
		         }
	         });		
	         
	         
	        /*   $("#backConfirmCard").ligerButton(
	         {
	             text: '驳回补卡', width: 100,
		         click: function ()
		         {
		             handRejectCard();
		         }
	         });	
	           $("#confirmCard").ligerButton(
	         {
	             text: '同意补卡', width: 100,
		         click: function ()
		         {
		             handConfirmCard();
		         }
	         });	*/
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
     	var requestUrl ="cc010/loadMasterinfos.action"; 
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
            	addMasterRecord(); 
	   		}
	 	}
	 	catch(e){alert(e.message);}
    }
    
    

    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
        try{
        		 strCurBillId=data.bchangebillid;
       			 var params = "strCurCompId="+data.bchangecompid;
       			 params =params+ "&strCurBillId="+strCurBillId;
				 var myAjax = new parent.Ajax.Request(
						"cc010/loadCurMcardchangeinfo.action",
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");	
								document.getElementById("changecompid").value=checkNull(action.curMcardchangeinfo.id.changecompid);
								document.getElementById("changebillid").value=checkNull(action.curMcardchangeinfo.id.changebillid);
								document.getElementById("changetype").value=checkNull(action.curMcardchangeinfo.id.changetype);
								document.getElementById("changedate").value=checkNull(action.curMcardchangeinfo.changedate);
								document.getElementById("changetime").value=checkNull(action.curMcardchangeinfo.changetime);
								document.getElementById("changebeforcardno").value=checkNull(action.curMcardchangeinfo.changebeforcardno);
								document.getElementById("changebeforcardtype").value=checkNull(action.curMcardchangeinfo.changebeforcardtype);
								//document.getElementById("changebeforcardtypename").value=checkNull(action.curMcardchangeinfo.changebeforcardtypename);
								//document.getElementById("membername").value=checkNull(action.curMcardchangeinfo.membername);
						   		if(checkNull(action.curMcardchangeinfo.membername)!="" )
	   								document.getElementById("membername").value=checkNull(action.curMcardchangeinfo.membername).substring(0,1)+"**";
	   							else
	   								document.getElementById("membername").value="";
	   								
						   		document.getElementById("memberphone").value=checkNull(action.curMcardchangeinfo.memberphone);
						   		
						   		if(checkNull(action.curMcardchangeinfo.memberphone)!="" && checkNull(action.curMcardchangeinfo.memberphone).length==11)
	   								document.getElementById("showmemberphone").value=checkNull(action.curMcardchangeinfo.memberphone).substring(0,3)+"****"+checkNull(action.curMcardchangeinfo.memberphone).substring(7,11);
	   							else
	   								document.getElementById("showmemberphone").value="";
	   								
						   		document.getElementById("curaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curaccountkeepamt);
						   		document.getElementById("curaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curaccountdebtamt);
						   		document.getElementById("curproaccountkeepamt").value=checkNull(action.curMcardchangeinfo.curproaccountkeepamt);
						   		document.getElementById("curproaccountdebtamt").value=checkNull(action.curMcardchangeinfo.curproaccountdebtamt);
						   		document.getElementById("changeaftercardno").value=checkNull(action.curMcardchangeinfo.changeaftercardno);
						   		document.getElementById("changeaftercardtype").value=checkNull(action.curMcardchangeinfo.changeaftercardtype);
						   		//document.getElementById("changeaftercardtypename").value=checkNull(action.curMcardchangeinfo.changeaftercardtypename);
						   		document.getElementById("rechargeremark").value=checkNull(action.curMcardchangeinfo.rechargeremark);
						   		handleRadio("billflag",checkNull(action.curMcardchangeinfo.billflag));
						   		readPage(action.curMcardchangeinfo.billflag);
							},
							asynchronous:true
						});			  
          }catch(e){alert(e.message);}
   	}
   	
   	
   	function loadSelecCardNoData(data, rowindex, rowobj)
    {
    	if(curpagestate!=1)
     	{
     		$.ligerDialog.warn("该单据不在挂失状态,请刷新后选择相应挂失卡!");
			return ;
     	}
     	else
     	{
     		if(data.cardstate!=4 && data.cardstate!=5)
	     	{
	     		$.ligerDialog.warn("该卡号不在挂失状态,请刷选择相应挂失卡!");
				return ;
	     	}
     		document.getElementById("changebeforcardno").value=checkNull(data.bcardno);
     		document.getElementById("changebeforcardtype").value=checkNull(data.cardtype);
     		document.getElementById("membername").value=checkNull(data.membername);
     		document.getElementById("memberphone").value=checkNull(data.memberphone);
     		document.getElementById("showmemberphone").value=checkNull(data.showmemberphone);
     		if(checkNull(data.cardsource)==0)
     		{
     			document.getElementById("curaccountkeepamt").value=checkNull(data.account2Amt);
     			document.getElementById("curaccountdebtamt").value=checkNull(data.account2debtAmt);
     		}
     		else
     		{
     			document.getElementById("curaccountkeepamt").value=checkNull(data.account5Amt);
     			document.getElementById("curaccountdebtamt").value=checkNull(data.account5debtAmt);
     		}
     		document.getElementById("curproaccountkeepamt").value=checkNull(data.account4Amt);
     		document.getElementById("curproaccountdebtamt").value=checkNull(data.account4debtAmt);
     	}
   	}
   
     
     function readPage(billstate)
     {
		if(billstate==0)
		{
			curpagestate=1;
     		//document.getElementById("changebeforcardno").readOnly="";
     		document.getElementById("changeaftercardno").readOnly="readOnly";
     	}
       	if(billstate==1  )
       	{
       		curpagestate=2;
     		//document.getElementById("changebeforcardno").readOnly="readOnly";
     		document.getElementById("changeaftercardno").readOnly="";
     		document.getElementById("rechargeremark").readOnly=""; 
     	}
       	if(billstate==2 || billstate==3 || billstate==4 || billstate==5 )
       	{
       		curpagestate=3;
     		document.getElementById("changebeforcardno").readOnly="readOnly";
     		document.getElementById("changeaftercardno").readOnly="readOnly";
     		document.getElementById("rechargeremark").readOnly="readOnly"; 
     	}
     }
     
     function validatePackageno(obj)
     {
     	if(obj.value=="")
     		return;
     	var requestUrl ="cc010/validatePackageno.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&strCurPackageNo="+obj.value;
		var responseMethod="validatePackagenoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
   
   	function validatePackagenoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)!="")
	       	{	       		 
	       		document.getElementById("packageno").value="";
	       		document.getElementById("packageno").select();
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function itemclick_bmpackageInfo(item)
   	{
   		addDetialRecord();
   	}
   	
   	function addDetialRecord()
   	{
   		var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
		commoninfodivdetial.addRow({ 
				                bpackageprono: "",
				                bpackageproname: "",
				                packageproprice: "",
				                packageprocount: "",
				                packageproamt: ""
				            }, row, false);
   	}
   	
   	function validateProject(obj)
   	{
   		if(obj.value=="")
   		{
   			commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
   		}
   		else
   		{
   			var existscount=0;
   			for (var rowid in commoninfodivdetial.records)
			{
				var row =commoninfodivdetial.records[rowid];
				curjosnparam=JSON.stringify(row);
				if(obj.value==row.bpackageprono)
				{
					existscount=existscount*1+1;
				}
			}  
			if(existscount>1)
			{
				commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
				$.ligerDialog.warn("该项目编号已经存在于此套餐中!");
				return ;
			}
   			var requestUrl ="cc010/validateProjectNo.action";
        	var params="strCurCompId="+strCurCompId;
       	 	params=params+"&strProjectNo="+obj.value;
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
	       		commoninfodivdetial.updateRow(curDetialRecord,{bpackageprono:'',bpackageproname:'',packageproprice:0,
     				packageprocount:0,packageproamt:0}); 
	       	}
	       	else
	       	{
	       		commoninfodivdetial.updateRow(curDetialRecord,{bpackageproname: responsetext.strProjectName,packageproprice:ForDight(responsetext.projectPrice,1),
     				packageprocount:1,packageproamt:ForDight(responsetext.projectPrice,1)}); 
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
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
   	
   	function  searchBill()
   	{
   		var searchLossCardNo=document.getElementById("searchLossCardNo").value;
   		var searchRecevieCardNo=document.getElementById("searchRecevieCardNo").value;
   		if(searchLossCardNo=="" && searchRecevieCardNo=="" )
   		{	
			 $.ligerDialog.warn("请输入查询信息!");
     		 return;
     	}
     	else
     	{
     		var requestUrl ="cc010/searchBill.action";
	        var params="strCurCompId="+strCurCompId;
	        params=params+"&searchLossCardNo="+searchLossCardNo;
	        params=params+"&searchRecevieCardNo="+searchRecevieCardNo;
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
	       		commoninfodivmaster.options.data=$.extend(true, {},{Rows: null,Total: 0});
            	commoninfodivmaster.loadData(true); 
            	addMasterRecord();
	       	}
	       	if(responsetext.needLossFlag==1)
	       	{
	       		 	$.ligerDialog.error("该会员卡信息不存在,请确认!");
	       		 	document.getElementById("changecompid").value="";
					document.getElementById("changebillid").value="";
					document.getElementById("changetype").value="";
					document.getElementById("changedate").value="";
					document.getElementById("changetime").value="";
					document.getElementById("changebeforcardno").value="";
					document.getElementById("changebeforcardtype").value="";
					document.getElementById("membername").value="";
					document.getElementById("memberphone").value="";
					document.getElementById("showmemberphone").value="";
					document.getElementById("curaccountkeepamt").value="";
					document.getElementById("curaccountdebtamt").value="";
					document.getElementById("curproaccountkeepamt").value="";
					document.getElementById("curproaccountdebtamt").value="";
					document.getElementById("changeaftercardno").value="";
					document.getElementById("changeaftercardtype").value="";
					document.getElementById("rechargeremark").value="";
	       	}
	       	else if(responsetext.needLossFlag==2)
	       	{
	       		 //curMaster=responsetext.curMcardchangeinfo;
				 //curCardinfo=responsetext.curCardinfo;
				 $.ligerDialog.confirm("是否需要新增该卡的挂失单!", function (result)
				 {
						if( result==true)
						{
							 var gridlen=commoninfodivmaster.rows.length*1;
							if(gridlen==0)
							{
								addMasterRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivmaster.getRow(0).changebeforcardno)!="")
							{
								addMasterRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivmaster.getRow(gridlen-1);
							commoninfodivmaster.updateRow(curDetialRecord,{bchangecompid: checkNull(responsetext.curMcardchangeinfo.id.changecompid)
																		  ,bchangebillid:  checkNull(responsetext.curMcardchangeinfo.id.changebillid)
																		  ,changebeforcardno  : ''
																		  ,changedate : checkNull(responsetext.curMcardchangeinfo.changedate)
																		  ,billflag : 0
																		  });
						   document.getElementById("changecompid").value=checkNull(responsetext.curMcardchangeinfo.id.changecompid);
						   document.getElementById("changebillid").value=checkNull(responsetext.curMcardchangeinfo.id.changebillid);
						   document.getElementById("changetype").value=checkNull(responsetext.curMcardchangeinfo.id.changetype);
						   document.getElementById("changedate").value=checkNull(responsetext.curMcardchangeinfo.changedate);
						   document.getElementById("changetime").value=checkNull(responsetext.curMcardchangeinfo.changetime);
						   document.getElementById("changebeforcardno").value=checkNull(responsetext.curCardinfo.id.cardno);
							document.getElementById("changebeforcardtype").value=checkNull(responsetext.curCardinfo.cardtype);
							//document.getElementById("membername").value=checkNull(responsetext.curCardinfo.membername);
						   	document.getElementById("memberphone").value=checkNull(responsetext.curCardinfo.memberphone);
						   
						   	if(checkNull(responsetext.curCardinfo.membername)!="" )
	   							document.getElementById("membername").value=checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**";
	   						else
	   							document.getElementById("membername").value="";
	   								
						   	if(checkNull(responsetext.curCardinfo.memberphone)!="" && checkNull(responsetext.curCardinfo.memberphone).length==11)
	   							document.getElementById("showmemberphone").value=checkNull(responsetext.curCardinfo.memberphone).substring(0,3)+"****"+checkNull(responsetext.curCardinfo.memberphone).substring(7,11);
	   						else
	   							document.getElementById("showmemberphone").value="";
						   	document.getElementById("curaccountkeepamt").value=checkNull(responsetext.curCardinfo.account2Amt);
						   	document.getElementById("curaccountdebtamt").value=checkNull(responsetext.curCardinfo.account2debtAmt);
						   	document.getElementById("curproaccountkeepamt").value=checkNull(responsetext.curCardinfo.account4Amt);
						   	document.getElementById("curproaccountdebtamt").value=checkNull(responsetext.curCardinfo.account4debtAmt);
						   	document.getElementById("changeaftercardno").value="";
						   	document.getElementById("changeaftercardtype").value="";
						   	document.getElementById("rechargeremark").value="";
						   	handleRadio("billflag",0);
						}
				 });
	       	}
	       
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function addMasterRecord()
    {
    		var row = commoninfodivmaster.getSelectedRow();
			//参数1:rowdata(非必填)
			//参数2:插入的位置 Row Data 
			//参数3:之前或者之后(非必填)
			commoninfodivmaster.addRow({ 
				          bchangecompid:'',
				          bchangebillid:'',
				          changebeforcardno:'',
				          changedate:'',
				          changeaftercardno:'',
				          financedate:'',
				          billflag:''
			}, row, false);
    }
   	
   	function searchCard()
   	{
   		var searchCardNoKey =document.getElementById("searchCardNoKey").value;
		var searchMemberNameKey=document.getElementById("searchMemberNameKey").value;
		var searchMemberPhoneKey=document.getElementById("searchMemberPhoneKey").value;
		var searchMemberPcidKey=document.getElementById("searchMemberPcidKey").value;
   		if(searchCardNoKey=="" && searchMemberNameKey=="" && searchMemberPhoneKey=="" && searchMemberPcidKey=="")
   		{	
   			commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
            commoninfodivdetial.loadData(true);  
     		 $.ligerDialog.warn("请输入查询信息!");
     		return;
     	}
     	var requestUrl ="cc010/searchCardinfos.action";
        var params="strCurCompId="+strCurCompId;
        params=params+"&searchCardNoKey="+searchCardNoKey;
        params=params+"&searchMemberNameKey="+searchMemberNameKey;
        params=params+"&searchMemberPhoneKey="+searchMemberPhoneKey;
        params=params+"&searchMemberPcidKey="+searchMemberPcidKey;
		var responseMethod="searchCardinfosMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
   
   	function searchCardinfosMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var lsCardinfos=responsetext.lsCardinfos;
	       	if(lsCardinfos!=null && lsCardinfos.length>0)
	       	{	       		 
	       		commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsCardinfos,Total: responsetext.lsCardinfos.length});
	            commoninfodivdetial.loadData(true);   
	       	
	       	}
	       	else
	       	{
	       		 $.ligerDialog.warn("没有查询到相应会员信息!");
		   		 commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0});
	             commoninfodivdetial.loadData(true);   
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function handlossCard()
   	{
   		
   		if(document.getElementById("changebeforcardno").value=="")
   		{
   			 $.ligerDialog.warn("请确认挂失卡号!");
   			 return ;
   		}
   		if(document.getElementsByName("billflag")[0].checked==false)
   		{	
   			 $.ligerDialog.warn("该单据不在挂失状态!");
   			 return ;
   		}
   		cardStateDialog=$.ligerDialog.open({ height: null, url: contextURL+'/CardControl/CC010/handCardState.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '会员卡挂失' });
		
   	}
   	
   	 function handCardStateMessage(request)
     {
    	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        	
	        	cardStateDialog.close();
	        	var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
				var requestUrl ="cc010/handlossCard.action";
				var params=queryStringTmp;
				var responseMethod="handlossCardMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
        }
		catch(e)
		{
			alert(e.message);
		}
    }
   	
   	   
    function handlossCardMessage(request)
    {
    		
        	try
			{
        		
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		$.ligerDialog.success("挂失成功!");
	        		location.reload();
	        		$("#changeoutCard").ligerButton().setDisabled();
	        		handleRadio("billflag",1);
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
        
    function handReceiveCard()
   	{
   		if(document.getElementsByName("billflag")[1].checked==false)
   		{	
   			 $.ligerDialog.warn("该单据不在解挂状态!");
   			 return ;
   		}
   		$.ligerDialog.confirm("请将需要解挂的卡插入读卡器中!", function (result)
		{
				if( result==true)
				{
					var CardControl=parent.document.getElementById("CardCtrl");
					CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					var cardNo=CardControl.ReadCard();
					if(cardNo=="")
					{
						$.ligerDialog.error("请初始化卡号");
						return;
			    	}
			    	else if(cardNo!=document.getElementById("changebeforcardno").value)
			    	{
			    		$.ligerDialog.error("插入的卡号与挂失卡卡号不一致,请确认");
						return;
			    	}
					var requestUrl ="cc010/handReceiveCard.action";
					var params="strCurCompId="+document.getElementById("changecompid").value;
					params=params+"&strCurBillId="+document.getElementById("changebillid").value;
					params=params+"&strCurCardNo="+document.getElementById("changebeforcardno").value;
					params=params+"&handPhone="+document.getElementById("memberphone").value;
					var responseMethod="handReceiveCardMessage";				
					sendRequestForParams_p(requestUrl,responseMethod,params ); 
				}
		});
   		
   	}
   	
   	   
    function handReceiveCardMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		$.ligerDialog.success("解挂成功!");
	        		handleRadio("billflag",2);
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
        
    function entryReceviceCard()
    {
    	
    	if(document.getElementsByName("billflag")[1].checked==false)
   		{	
   			 $.ligerDialog.error("该单据不在补卡登记状态!");
   			 return ;
   		}
   		if(document.getElementById("changeaftercardno").value=="")
   		{
   			 $.ligerDialog.error("请确认补卡卡号!");
   			 return ;
   		}
   		if(document.getElementById("changeaftercardtype").value!=document.getElementById("changebeforcardtype").value)
	    {
	        			document.getElementById("changeaftercardno").value="";
	        		 	document.getElementById("changeaftercardtype").value="";
	        			$.ligerDialog.error("补卡新卡卡类型与老卡卡类型不一致!");
	        			return ;
	    }
	    if(strCurCompId!=document.getElementById("changecompid").value && strCurCompId!="001")
	    {
	    	 $.ligerDialog.error("非本店的挂失单不能操作,请确认!");
   			 return ;
	    }
   		var requestUrl ="cc010/entryReceviceCard.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurBillId="+document.getElementById("changebillid").value;
		params=params+"&strCurCardNo="+document.getElementById("changebeforcardno").value;
		params=params+"&strCurNewCardNo="+document.getElementById("changeaftercardno").value;
		params=params+"&strCurNewCardType="+document.getElementById("changeaftercardtype").value;
		params=params+"&handPhone="+document.getElementById("memberphone").value;
		params=params+"&strRemark="+document.getElementById("rechargeremark").value;
		var responseMethod="entryReceviceCardMessage";	
		$.ligerDialog.confirm('确认已经插入新卡', function (result)
		{
			if( result==true)
			{
				if(document.getElementById("changeaftercardno").value=="")
		   		{
		   			 $.ligerDialog.error("请确认补卡卡号!");
		   			 return ;
		   		}
		   		var CardControl=parent.document.getElementById("CardCtrl");
		   		CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
		   		var cardNo=CardControl.ReadCard();
		   		if(cardNo.length!=20 
						&& cardNo!="" 
						&& cardNo!=document.getElementById("changebeforcardno").value
						&& cardNo!=document.getElementById("changeaftercardno").value)
				{
		   			$.ligerDialog.error("读卡器中的卡和补卡卡号对应不上。请检查一下。或者不是新卡");
					return;
				}
		   		//CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
				var initflag=CardControl.WriteCard(document.getElementById("changeaftercardno").value);
				if(initflag==0)
				{
					$.ligerDialog.error('会员卡初始化失败,请确认读卡器状态!');
					return;
				}
				
				
				//补卡卡号 判断插入读卡器的卡不能是老卡
				var inserCardno=CardControl.ReadCard();
				if(checkNull(inserCardno)!="" && checkNull(inserCardno)==document.getElementById("changebeforcardno").value)
				{
					$.ligerDialog.error("遗失补卡读卡器中的卡不能为老卡!");
					return;
				}
				$("#entryReceviceCard").ligerButton().setDisabled();
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
			}
		});
    }
    
    function entryReceviceCardMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		//var CardControl=parent.document.getElementById("CardCtrl");
					//CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
					$.ligerDialog.success("IC卡初始化成功,登记成功");
					location.reload();
	        		handleRadio("billflag",3);
	        		readPage(3);
	        		sendMsg();
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
    
    
    function sendMsg()
    {
    	var requestUrl ="cc010/sendMsg.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurBillId="+document.getElementById("changebillid").value;
		params=params+"&strCurCardNo="+document.getElementById("changebeforcardno").value;
		params=params+"&strCurNewCardNo="+document.getElementById("changeaftercardno").value;
		params=params+"&strCurNewCardType="+document.getElementById("changeaftercardtype").value;
		params=params+"&handPhone="+document.getElementById("memberphone").value;
		params=params+"&strRemark="+document.getElementById("rechargeremark").value;
		var responseMethod="sendMsgMessage";
		sendRequestForParams_p(requestUrl,responseMethod,params); 
    }
    
    function sendMsgMessage(request)
    {
    	
    }
 
        
    function handConfirmCard()
   	{
   		if(document.getElementsByName("billflag")[3].checked==false)
   		{	
   			 $.ligerDialog.warn("该单据不在补卡状态!");
   			 return ;
   		}
   		if(document.getElementById("changeaftercardno").value=="")
   		{
   			 $.ligerDialog.warn("请确认补卡卡号!");
   			 return ;
   		}
   		var requestUrl ="cc010/handConfirmCard.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurBillId="+document.getElementById("changebillid").value;
		params=params+"&strCurCardNo="+document.getElementById("changebeforcardno").value;
		params=params+"&strCurNewCardNo="+document.getElementById("changeaftercardno").value;
		params=params+"&handPhone="+document.getElementById("memberphone").value;
		var responseMethod="handConfirmCardMessage";	
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
   	}
   	
   	   
    function handConfirmCardMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		$.ligerDialog.success("补卡成功!");
	        		handleRadio("billflag",4);
	        		readPage(4);
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
        
    function handRejectCard()
   	{
   		if(document.getElementsByName("billflag")[3].checked==false)
   		{	
   			 $.ligerDialog.warn("该单据不在补卡驳回状态!");
   			 return ;
   		}
   		if(document.getElementById("changeaftercardno").value=="")
   		{
   			 $.ligerDialog.warn("请确认补卡卡号!");
   			 return ;
   		}
   		var requestUrl ="cc010/handRejectCard.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurBillId="+document.getElementById("changebillid").value;
		params=params+"&strCurCardNo="+document.getElementById("changebeforcardno").value;
		params=params+"&strCurNewCardNo="+document.getElementById("changeaftercardno").value;
		params=params+"&strCurNewCardNo="+document.getElementById("changeaftercardno").value;
		var responseMethod="handRejectCardMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
   	}
   	
   	   
    function handRejectCardMessage(request)
    {
    		
        	try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        		 
	        		$.ligerDialog.success("驳回成功!");
	        		handleRadio("billflag",5);
	        		readPage(5);
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
    
    function validateChangeaftercardno(obj)
    {
    	if(obj.value=="")
    	{
    		document.getElementById("changeaftercardtype").value="";
    		return;
    	}
    	var requestUrl ="cc010/validateGsCardno.action";
		var params="strCurCompId="+document.getElementById("changecompid").value;
		params=params+"&strCurCardNo="+obj.value;
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
	        		if(document.getElementById("changeaftercardtype").value!=document.getElementById("changebeforcardtype").value)
	        		{
	        			document.getElementById("changeaftercardno").value="";
	        		 	document.getElementById("changeaftercardtype").value="";
	        			$.ligerDialog.error("补卡新卡卡类型与老卡卡类型不一致!");
	        			return ;
	        		}
	        		
	        	}
	        	else
	        	{
	        		 document.getElementById("changeaftercardno").value="";
	        		 document.getElementById("changeaftercardtype").value="";
	        		 $.ligerDialog.warn(strMessage);
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
    