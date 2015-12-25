
   	var compTreeManager;
   	var compTree;
   	var commoninfodivdetial_tmk=null;  //支付方式
   	var commoninfodivdetial=null; //项目明细
   	var commoninfodivmaster= null;
   	var commoninfodivdetial_dyq=null;
   	var strCurCompId="";
   	var strCurBillId="";
   	var nBillType=0;
   	var chooseSexData = [{ choose: 1, text: '男' }, { choose: 0, text: '女'}];
   	var cc016layout=null;
   	var fromValidate=null;
   	var firstsaleridmanager = null;
   	var secondsaleridmanager = null;
   	var thirdsalerinidmanager = null;
    var fourthsaleridmanager = null;
   	var fifthsaleridmanager = null;
   	
   	var sixthsaleridmanager = null;
   	var seventhsaleridmanager = null;
   	var eighthsaleridmanager = null;
   	var ninthsaleridmanager = null;
    var tenthsaleridmanager = null;
   	var lsProjectinfo = null;
   	var pageState=3;
   	var lsStaffinfo=null;
   	//var paymodeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("ZFFS",0));
   	var lcTypeChangeData=JSON.parse(parent.loadCommonControlDate_selectdree("JGCW",0));
   	var curProRecord = null;
   	var curMasterRecord = null;
   	var curDetialRecord = null;
   	var showDialogmanager=null;
   	var lsMpackageinfo=null;
   	var checkcustomerDialog=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            cc016layout= $("#cc016layout").ligerLayout({ leftWidth: 270,rightWidth: 250,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true });
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
       
           
     		commoninfodivmaster=$("#commoninfodivmaster").ligerGrid({
                columns: [
                { display: '业务类型', 	name: 'billTypeName', 		width:130,align: 'left'},
                { display: '开单数', 		name: 'billCount', 			width:50,align: 'right'},
                { name: 'billType', 	hide:true,	width:1}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 200,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                  	loadSelecMasterData(data, rowindex, rowobj);
                },
                toolbar: { items: [
	                { text: '<input type="text" name="searchdate" id="searchdate"  readonly="true" style="width:140;"/>' }
	                ]
                }	
            });
            commoninfodivdetial=$("#commoninfodivdetial").ligerGrid({
                columns: [
                	{ display: '单据编号', 	name: 'strBillId', 			width:110,align: 'left'},
                	{ display: '会员编号', 	name: 'strCardNo', 			width:100,align: 'left'},
                 	{ display: '会员姓名', 	name: 'strMemberName', 			width:80,align: 'left'},
                 	{ hide:true,name: 'changeseqno', width:1}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 300,
                height:height-20,
                enabledEdit: true, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curMasterRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                }
            });
            
            
            commoninfodivdetial_dyq=$("#commoninfodivdetial_dyq").ligerGrid({
                columns: [
                	{ display: '抵用券号', 	name: 'cardno', 			width:100,align: 'left'},
                	{ display: '类型', 				name: 'createcardtypename', 	width:80,align: 'left'},
                	{ name: 'createcardtype', 	width:1,hide: 'true'},
                	{ name: 'activinid', 	width:1,hide: 'true'},
                	{ name: 'activorand', 	width:1,hide: 'true'}
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 230,
                height:height-320,
                enabledEdit: false, checkbox: false,clickToEdit:false,
                rownumbers: true,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	 loadSelecDyCardData(data, rowindex, rowobj);
                },
                onContextmenu : function (parm,e)
                {
                	  menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
           menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前店长', click: deleteCurDyCardno, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	            }); 
             commoninfodivdetial_tmk=$("#commoninfodivdetial_tmk").ligerGrid({
                columns: [
                	{ display: '项目编号', 	name: 'ineritemno',  	width:140,align: 'left',
			 	 	editor: { type: 'select', data: null, url:'loadAutoProject',autocomplete: true, valueField: 'choose',onChanged : validateItem,selectBoxWidth:300,selectBoxHeight:300}
                	},
                	{ display: '项目名称', 	name: 'ineritemname', 	width:180,align: 'left'},
	           	 	{ display: '次数', 		name: 'entrycount', 		width:60,align: 'left' ,editor: { type: 'float' } },
	            	{ display: '金额', 		name: 'entryamt', 		width:60,align: 'left' ,editor: { type: 'float' } },
               		{ display: '备注', 		name: 'entryremark', 			width:120,align: 'left',editor: { type: 'text' }},
               		{ name: 'packageNo', 	width:1,hide: 'true'}//标识赠送为疗程项目或者产品
               ],  pageSize:25, 
                data:{Rows: null,Total:0},      
              	width: 600,
                height:height-320,
                enabledEdit: false, checkbox: false,
                rownumbers: false,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                      curDetialRecord = data;
                }	
            });
            
            
            var today = new Date();
			var intYear=today.getFullYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("searchdate").value=today;
			$("#searchdate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'140' ,onChangeDate:loadInfoByvalidate});
			
			$("#butsendCardZS").ligerButton(
			         {
			             text: '确认添加', width: 120, height: 120,
				         click: function ()
				         {
				        	 addPrj();
				         }
			         });
			
             $("#butsendCardPoint").ligerButton(
	         {
	             text: '确认赠送', width: 120, height: 120,
		         click: function ()
		         {
		             sendCardPoint();
		         }
	         });
            
           
         	f_selectNode();
         	addDetialRecord();
         	addDetialRecord_dyq();
   		}catch(e){alert(e.message);}
    });
    
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
		var parm = function (data)
		{
		   	return data.id== parent.localCompid;
		};		
		compTree.selectNode(parm);
	}
  
    //加载门店信息
    var today = new Date();
	var endday = new Date("2015/08/31");
	function compSelect(note)
    {
        try{

        	strCurCompId=note.data.id;
        	/*clearOption("sendpicflag");
            addOption("1","赠送积分",document.getElementById("sendpicflag"));
            addOption("2","赠送条码卡",document.getElementById("sendpicflag"));
            addOption("3","赠送日历",document.getElementById("sendpicflag"));
			if(strCurCompId=="006" || strCurCompId=="047" || strCurCompId=="033" || strCurCompId=="014" ||strCurCompId=="041" || strCurCompId=="046" )
			{
				 addOption("4","五一赠送活动",document.getElementById("sendpicflag"));
			}*/
       		var params = "strCurCompId="+note.data.id;			
       		params = params+"&strSearchDate="+document.getElementById("searchdate").value;	
       		var requestUrl ="cc016/loadMasterBillType.action"; 
			var responseMethod="loadMasterBillTypeMessage";				
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    
    
    
	  function loadInfoByvalidate(obj)
	  {
	  	if(obj!="")
	  	{
	  			var params = "strCurCompId="+strCurCompId;		
	       		params = params+"&strSearchDate="+obj;	
	       		var requestUrl ="cc016/loadMasterBillType.action"; 
				var responseMethod="loadMasterBillTypeMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
	  	}
	  	if(nBillType!=9){
	  		$("#face_tr").hide();
	  		$("#_faceamt").val("");
	  		$("#_courseamt").val("");
	  	}
	  }
 
	   function loadMasterBillTypeMessage(request)
	   {
	       		try
	        	{
	        		var responsetext = eval("(" + request.responseText + ")");
		    		if(responsetext.lsBillEdits!=null && responsetext.lsBillEdits.length>0)
			   		{
			   			commoninfodivmaster.options.data=$.extend(true, {},{Rows: responsetext.lsBillEdits,Total: responsetext.lsBillEdits.length});
		            	commoninfodivmaster.loadData(true);   
		            	commoninfodivmaster.select(0);         	
			   		}
			   		document.getElementById("sendPointRate1").value=responsetext.sendPointRate1;
			   		document.getElementById("sendPointRate2").value=responsetext.sendPointRate2;
			   		
			   		document.getElementById("picno").readOnly="readOnly";
		    		document.getElementById("picno").style.background="#EDF1F8";
		    		lsMpackageinfo=responsetext.lsMpackageinfo;
			   		/*clearOption("createcardtype");
			   		addOption(0,"",document.getElementById("createcardtype"));
					if(responsetext.lsMpackageinfo!=null && responsetext.lsMpackageinfo.length>0)
					{
						for(var i=0;i<responsetext.lsMpackageinfo.length;i++)
						{
							addOption(responsetext.lsMpackageinfo[i].id.packageno,responsetext.lsMpackageinfo[i].packagename,document.getElementById("createcardtype"));
						}
					}*/
		   		}catch(e){alert(e.message);}
	    }
    
	    
   
	   function loadSelecMasterData(data, rowindex, rowobj)
	   {
	   		 try{
	   		 	document.getElementById("sendcompid").value="";
	       		document.getElementById("sendbillid").value="";
	       		document.getElementById("sendtype").value="";
	       		document.getElementById("sourcebillid").value="";
	       		document.getElementById("sourcecardno").value="";
	       		document.getElementById("sourcedate").value="";
	       		document.getElementById("sourceamt").value="";
	       		document.getElementById("paycount").value="";
	       		document.getElementById("bsendbillid").value="";
	       		document.getElementById("membername").value="";
	       		document.getElementById("sendrateflag").value="";
	       		document.getElementById("sendpicflag").value=1;
	       		document.getElementById("createcardtype").value=0; 
	       		commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	commoninfodivdetial_tmk.loadData(true);   
	       	 	addDetialRecord();
	       	 	commoninfodivdetial_dyq.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	commoninfodivdetial_dyq.loadData(true); 
	        	addDetialRecord_dyq();
	        	nBillType=data.billType;
				var params = "strCurCompId="+strCurCompId;	
				params=params+"&strSearchDate="+document.getElementById("searchdate").value;			
				params=params+"&nBillType="+nBillType;
				var requestUrl ="cc016/loadMasterinfo.action"; 
				var responseMethod="loadMasterinfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
				$("#sendamt").val("");
				if(nBillType!=9){
			  		$("#face_tr").hide();
			  		$("#_faceamt").val("");
			  		$("#_courseamt").val("");
			  	}
	          }catch(e){alert(e.message);}
	   }
	   
	   function loadMasterinfoMessage(request)
	   {
	       	var responsetext = eval("(" + request.responseText + ")");
		   	if(responsetext.lsReEditBillInfo!=null && responsetext.lsReEditBillInfo.length>0)
			{
			  	commoninfodivdetial.options.data=$.extend(true, {},{Rows: responsetext.lsReEditBillInfo,Total: responsetext.lsReEditBillInfo.length});
		       	commoninfodivdetial.loadData(true);      
		       	commoninfodivdetial.select(0);      	
			}
			else
			{
				commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total: 0 });
		       	commoninfodivdetial.loadData(true);
		       	itemclick_adddetial();
			}
		   	//加载门店活动
			var params = {strCurCompId:strCurCompId,nBillType:nBillType,strSearchDate:$("#searchdate").val()};
			var url = contextURL+"/cc016/loadActivity.action"; 
			$.post(url, params, function(data){
				clearOption("sendpicflag");
				if(nBillType==9){
					addOption("0", "赠送美容积分",document.getElementById("sendpicflag"));
				}
				addOption("1", "赠送积分",document.getElementById("sendpicflag"));
				addOption("5", "赠送抵用券",document.getElementById("sendpicflag"));
				
				if(strCurCompId=="007"){
					if(today <= endday){
						addOption("10","余姚开业活动",document.getElementById("sendpicflag"));
					}
				}
				var list = data.activitySet;
				if(list != null && list.length>0){
					$(list).each(function(i, row){
						$("<option data-val='"+ row.activinid +"' value='"+ row.id +"'>"+ 
								row.activname +"</option>").appendTo("#sendpicflag"); 
					});
				}
			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});		
		}
	  
		function loadSelecDetialData(data, rowindex, rowobj)
		{
		    var params = "strCurCompId="+strCurCompId;	
				params=params+"&strCurBillId="+data.strBillId;	
				params=params+"&strSearchDate="+document.getElementById("searchdate").value;
				params=params+"&strCurCardNo="+data.strCardNo;
				params=params+"&changeseqno="+data.changeseqno;		
				params=params+"&nBillType="+nBillType;
				var requestUrl ="cc016/loadDetialinfo.action"; 
				var responseMethod="loadDetialinfoMessage";				
				sendRequestForParams_p(requestUrl,responseMethod,params );
		}
		
		function loadDetialinfoMessage(request)
	   {
	       	var responsetext = eval("(" + request.responseText + ")");
	       	if(responsetext.curSendpointcard!=null)
	       	{
	       		document.getElementById("sendcompid").value=responsetext.curSendpointcard.id.sendcompid;
	       		document.getElementById("sendbillid").value=responsetext.curSendpointcard.id.sendbillid;
	       		document.getElementById("sendtype").value=responsetext.curSendpointcard.id.sendtype;
	       		document.getElementById("sourcebillid").value=responsetext.curSendpointcard.sourcebillid;
	       		document.getElementById("sourcecardno").value=responsetext.curSendpointcard.sourcecardno;
	       		document.getElementById("sourcedate").value=checkNull(responsetext.curSendpointcard.sourcedate);
	       		document.getElementById("sourceamt").value=ForDight(responsetext.curSendpointcard.sourceamt,1);
	       		document.getElementById("paycount").value=ForDight(responsetext.curSendpointcard.sendamt,1);
	       		document.getElementById("bsendbillid").value=responsetext.curSendpointcard.bsendbillid;
	       		document.getElementById("membername").value=responsetext.curSendpointcard.membername;
	       		document.getElementById("sendrateflag").value=responsetext.curSendpointcard.sendrateflag;
	       		document.getElementById("changeseqno").value=responsetext.curSendpointcard.changeseqno
	       		document.getElementById("sendpicflag").value=1;
	       		document.getElementById("createcardtype").value=0; 
	       		document.getElementById("picno").readOnly="readOnly";
		    	document.getElementById("picno").style.background="#EDF1F8";
		    	/*document.getElementById("firstdateno").readOnly="readOnly";
		    	document.getElementById("firstdateno").style.background="#EDF1F8";*/
		    	document.getElementById("sendamt").readOnly="";
		    	document.getElementById("sendamt").style.background="#FFFFFF";
		    	commoninfodivdetial_tmk.options.enabledEdit=false;
		    	commoninfodivdetial_tmk.options.clickToEdit=false;
	       	}
		   	commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        commoninfodivdetial_tmk.loadData(true); 
	        commoninfodivdetial_dyq.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        commoninfodivdetial_dyq.loadData(true); 
	        commoninfodivdetial_dyq.options.enabledEdit=false;
		    commoninfodivdetial_dyq.options.clickToEdit=false;
	        addDetialRecord();
	        addDetialRecord_dyq();
	        document.getElementById("sendpicflag").disabled=false;
	       	document.getElementById("picno").readOnly="readOnly";
		  	document.getElementById("picno").style.background="#EDF1F8";
		  	if(nBillType==9){
		  		$("#sourcecardno").val(curMasterRecord.strCardNo);
		  		$("#sourcebillid").val(curMasterRecord.strBillId);
		  		var url = contextURL+"/cc016/loadFaceamt.action"; 
		  		var params = {strCurCompId:strCurCompId, strSearchDate:$("#searchdate").val(), strCurCardNo:$("#sourcecardno").val()};
		  		$.post(url, params, function(data){
		  			alert(document.getElementById("sourcedate").value);
		  			if(document.getElementById("sourcedate").value=="")
		  			{
		  				document.getElementById("sourcedate").value=$("#searchdate").val();
		  			}
		  			var amt = JSON.parse(data.strMessage);
		  			$("#_faceamt").val(ForDight(amt.cardamt, 1));
		  			//既购买疗程又购买年卡，则金额相加
	  				$("#_courseamt").val(ForDight(responsetext.curSendpointcard.sendamt*1+amt.yearamt*1, 1));
		  			$("#face_tr").show();
		  			document.getElementById("sendpicflag").value="0";
		  			var _faceamt = $("#_faceamt").val();
					if(_faceamt*1<=0){
						//$("#sendpicflag option:first").attr("selected", true).change();
						//$.ligerDialog.error("充值金额没有达到赠送要求");
						//return;
						$("#sendamt").val("");
					}
					$("#sendamt").css("background-color", "#FFFFFF").attr("readOnly", true);
			    	$("#picno").val("").css("background-color", "#EDF1F8").attr("readOnly", true);
			  		var _courseamt = $("#_courseamt").val();
			  		//如果疗程总金额大于充值金额 按充值金额的10%美容积分，如果充值金额大于疗程总金额 按疗程总金额的10%美容积分
			  		if(_courseamt*1>_faceamt*1){
			  			$("#sendamt").val(ForDight(_faceamt*0.1,0));
			  		}else{
			  			$("#sendamt").val(ForDight(_courseamt*0.1,0));
			  		}
				}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
		  	}
	        /*if(responsetext.curSendpointcard.id.sendtype==6)
	        {
	        	document.getElementById("sendpicflag").value=4;
	        	document.getElementById("sendpicflag").disabled=true;
	        	document.getElementById("sendamt").readOnly="readOnly";
		    	document.getElementById("sendamt").style.background="#EDF1F8";
		    	document.getElementById("firstdateno").readOnly="readOnly";
		    	document.getElementById("firstdateno").style.background="#EDF1F8";
		    	document.getElementById("picno").readOnly="readOnly";
		    	document.getElementById("picno").style.background="#EDF1F8";
		    	commoninfodivdetial_dyq.options.enabledEdit=true;
		    	commoninfodivdetial_dyq.options.clickToEdit=true;
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900004'
																	  	,ineritemname :'果酸(单次)'
																	  	,entryamt  : 980
																	  	,entrycount  : 1
																	   });
			    var ccount=1;
			    while(document.getElementById("sourceamt").value*1-ccount*1>0)
			    {
			    	addDetialRecord_dyq();
			    	ccount=ccount*1+1;
			    }
			    document.getElementById("createcardtype").value=3; 
	        }
			else if(responsetext.curSendpointcard.id.sendtype==7)
	        {
	        	document.getElementById("sendpicflag").value=4;
	        	document.getElementById("sendpicflag").disabled=true;
	        	document.getElementById("sendamt").readOnly="readOnly";
		    	document.getElementById("sendamt").style.background="#EDF1F8";
		    	document.getElementById("firstdateno").readOnly="readOnly";
		    	document.getElementById("firstdateno").style.background="#EDF1F8";
		    	document.getElementById("picno").readOnly="readOnly";
		    	document.getElementById("picno").style.background="#EDF1F8";
		    	commoninfodivdetial_dyq.options.enabledEdit=true;
		    	commoninfodivdetial_dyq.options.clickToEdit=true;
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
					commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900018'
																	  	,ineritemname :'基础面护(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
					addDetialRecord()
					gridlen=gridlen+1; 
					curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
					commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900019'
																	  	,ineritemname :'肩颈(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
			    var ccount=1;
			    var syCount=document.getElementById("sourceamt").value*1%3;
			    var zscount=maskAmt(document.getElementById("sourceamt").value*1/3,0);
			    if(syCount>0)
			    {
			    	zscount=zscount*1+1;
			    }
			    while(zscount*1-ccount*1>0)
			    {
			    	addDetialRecord_dyq();
			    	ccount=ccount*1+1;
			    }
			    document.getElementById("createcardtype").value=1; 
	        }*/
					
		}
		
		function validateSendFlag(obj)
		{
			//alert(obj.value);
			deleteRows();
			//document.getElementById("zstr").style.display="none";
			document.getElementById("check1").checked=false;
			$("#prjid").attr("readOnly",true);
			$("#prjnum").attr("readOnly",true);
			if(obj.value==1)
			{
		    	document.getElementById("sendamt").readOnly="";
		    	document.getElementById("sendamt").style.background="#FFFFFF";
		    	document.getElementById("picno").value="";
		    	document.getElementById("picno").readOnly="readOnly";
		    	document.getElementById("picno").style.background="#EDF1F8";
		    	//document.getElementById("createcardtype").disabled=true; 
		    	document.getElementById("createcardcount").disabled=true; 
		    	commoninfodivdetial_tmk.options.enabledEdit=false;
		    	commoninfodivdetial_tmk.options.clickToEdit=false;
		    	commoninfodivdetial_dyq.options.enabledEdit=false;
		   	 	commoninfodivdetial_dyq.options.clickToEdit=false; 
		    	document.getElementById("createcardtype").value=0;
		    	document.getElementById("createcardcount").value=0;
		    	//commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	//commoninfodivdetial_tmk.loadData(true);   
	        	addDetialRecord();	
			}
			else if(obj.value==2 || obj.value==4)
			{
				document.getElementById("sendamt").readOnly="readOnly";
		    	document.getElementById("sendamt").style.background="#EDF1F8";
		    	document.getElementById("firstdateno").readOnly="readOnly";
		    	document.getElementById("firstdateno").style.background="#EDF1F8";
		    	document.getElementById("picno").readOnly="";
		    	document.getElementById("picno").style.background="#FFFFFF";
		    	commoninfodivdetial_tmk.options.enabledEdit=true;
		    	commoninfodivdetial_tmk.options.clickToEdit=true;
			}
			else if(obj.value==3)
			{
				document.getElementById("picno").readOnly="readOnly";
		    	document.getElementById("picno").style.background="#EDF1F8";
		    	document.getElementById("sendamt").readOnly="readOnly";
		    	document.getElementById("sendamt").style.background="#EDF1F8";
		    	document.getElementById("firstdateno").readOnly="";
		    	document.getElementById("firstdateno").style.background="#FFFFFF";
		    	commoninfodivdetial_tmk.options.enabledEdit=false;
		    	commoninfodivdetial_tmk.options.clickToEdit=false;
			}
			
			if(obj.value==4)
			{
				commoninfodivdetial_tmk.options.enabledEdit=false;
		    	commoninfodivdetial_tmk.options.clickToEdit=false;
				commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	commoninfodivdetial_tmk.loadData(true);   
	       		addDetialRecord();	
				if(document.getElementById("sendtype").value*1<=5 && document.getElementById("sourceamt").value*1>=2000 && document.getElementById("sourceamt").value*1<5000)
				{
					var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     		if(gridlen==0)
					{
						addDetialRecord();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
					{
						addDetialRecord();
						gridlen=gridlen+1;
					}
					var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
					commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900018'
																	  	,ineritemname :'基础面护(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
					addDetialRecord()
					gridlen=gridlen+1; 
					curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
					commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900019'
																	  	,ineritemname :'肩颈(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
					document.getElementById("createcardtype").value=1; 
				}
				else if(document.getElementById("sendtype").value*1<=5 && document.getElementById("sourceamt").value*1>=5000)
				{
					var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     		if(gridlen==0)
					{
						addDetialRecord();
						gridlen=gridlen+1;
					} 
					if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
					{
						addDetialRecord();
						gridlen=gridlen+1;
					}
					var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
					commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900005'
																	  	,ineritemname :'氧动力紧致面护(单次)'
																	  	,entryamt  : 780
																	  	,entrycount  : 1
																	   });
					document.getElementById("createcardtype").value=2; 
				}
				
			}
			else if(obj.value==5)
			{
		    	document.getElementById("sendamt").readOnly="readOnly";
		    	document.getElementById("sendamt").style.background="#EDF1F8";
		    	document.getElementById("picno").readOnly="";
		    	document.getElementById("picno").style.background="#FFFFFF";
		    	//document.getElementById("createcardtype").disabled=false; 
		    	document.getElementById("createcardcount").disabled=false; 
		    	commoninfodivdetial_tmk.options.enabledEdit=false;
		    	commoninfodivdetial_tmk.options.clickToEdit=false;
		    	commoninfodivdetial_dyq.options.enabledEdit=false;
		    	commoninfodivdetial_dyq.options.clickToEdit=false; 
		    	//commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        	//commoninfodivdetial_tmk.loadData(true);   
	        	addDetialRecord();	
				document.getElementById("createcardtype").value=0;
				document.getElementById("createcardcount").value=1; 
			}
			else if(obj.value==6)
			{
				if(nBillType==6 || nBillType==7 || nBillType==8)
				{
					deleteRows();
					$.ligerDialog.error("该单据类型不能赠送项目");
					return;
				}
				deleteRows();
				$("#sendamt").val("0");
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
				//document.getElementById("zstr").style.display="block";
				document.getElementById("picno").readOnly=true;
				document.getElementById("sendamt").readOnly=true;
				var billamt=document.getElementById("sourceamt").value;
				var zsAmt=0;
				if(billamt*1>=288000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610059")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*12,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*12,0)
							  	,entrycount  : 12
							   });
						}
						if(parent.lsProjectinfo[i].id.prjno=="3610033")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*12,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*12,0)
							  	,entrycount  : 12
							   });
						}
					}
					$("#sendamt").val(zsAmt);
				}
				else if(billamt*1>=100000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610059")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0)
							  	,entrycount  : 6
							   });
						}
						if(parent.lsProjectinfo[i].id.prjno=="3610033")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0)
							  	,entrycount  : 6
							   });
						}
					}
					$("#sendamt").val(zsAmt);
				}
				else if(billamt*1>=50000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610011")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0);
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0)
							  	,entrycount  : 6
							   });
						}
						if(parent.lsProjectinfo[i].id.prjno=="3610017" || parent.lsProjectinfo[i].id.prjno=="3610028")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*6,0)
							  	,entrycount  : 6
							   });
						}
					}
					
					$("#sendamt").val(zsAmt);
				}
				else if(billamt*1>=20000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610011")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0);
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0)
							  	,entrycount  : 2
							   });
						}
						if(parent.lsProjectinfo[i].id.prjno=="3610017" || parent.lsProjectinfo[i].id.prjno=="3610028")
						{
							zsAmt+=parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2;
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0)
							  	,entrycount  : 2
							   });
						}
					}
					$("#sendamt").val(zsAmt);
				}
				else if(billamt*1>=10000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610011")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*1,0);
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*1,0)
							  	,entrycount  : 1
							   });
						}
						if(parent.lsProjectinfo[i].id.prjno=="3610017" || parent.lsProjectinfo[i].id.prjno=="3610028")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*1,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*1,0)
							  	,entrycount  : 1
							   });
						}
					}
					$("#sendamt").val(zsAmt);
				}
			}
			else if(obj.value==7)
			{
				deleteRows();
				$("#sendamt").val("0");
				var zsAmt=0;
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
				//document.getElementById("zstr").style.display="block";
				document.getElementById("picno").readOnly=true;
				document.getElementById("sendamt").readOnly=true;
				var billamt=document.getElementById("sourceamt").value;
				if(billamt>=10000 && billamt<20000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="4610011")
						{
							zsAmt=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0);
							
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0)
							  	,entrycount  : 2
							   });
							break;
						}
					}
					$("#sendamt").val(zsAmt);
				}
			}
			else if(obj.value==8)
			{
				deleteRows();
				$("#sendamt").val("0");
				var zsAmt=0;
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
				//document.getElementById("zstr").style.display="block";
				document.getElementById("picno").readOnly=true;
				document.getElementById("sendamt").readOnly=true;
				var billamt=document.getElementById("sourceamt").value;
				if(billamt>=10000 && billamt<20000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						if(parent.lsProjectinfo[i].id.prjno=="3610016")
						{
							zsAmt+=ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0);
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno: parent.lsProjectinfo[i].id.prjno
							  	,ineritemname :parent.lsProjectinfo[i].prjname
							  	,entryamt  : ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount*2,0)
							  	,entrycount  : 2
							   });
							break;
						}
					}
					$("#sendamt").val(zsAmt);
				}
			}
			else if(obj.value==9)
			{
				if($("#sourceamt").val()*1<6800)
				{
					$.ligerDialog.error("金额没有达到赠送要求");
					return;
				}
			}
			else if(obj.value==10)
			{
				if(nBillType==4 || nBillType==6 || nBillType==7 || nBillType==8)
				{
					$("#sendpicflag option:first").attr("selected", true);
					$.ligerDialog.error("该单据类型不能赠送项目");
					return;
				}
				if($("#sourceamt").val()*1<5000)
				{
					$("#sendpicflag option:first").attr("selected", true);
					$.ligerDialog.error("金额没有达到赠送要求");
					return;
				}
				$("#sendamt").val("0");
				var gridlen=commoninfodivdetial_tmk.rows.length*1;
				document.getElementById("picno").readOnly=true;
				document.getElementById("sendamt").readOnly=true;
				var billamt=document.getElementById("sourceamt").value;
				var zsAmt=0;
				if(billamt*1>=5000 && billamt*1<30000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						var pro = parent.lsProjectinfo[i];
						if(pro.id.prjno=="4600006" || pro.id.prjno=="4600012" || pro.id.prjno=="4600034" || pro.id.prjno=="4600088")
						{
							zsAmt+=ForDight(172,0);
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: pro.id.prjno
							  	,ineritemname :pro.prjname
							  	,entryamt  : ForDight(172,0)
							  	,entrycount  : 1
							   });
						}
					}
					$("#sendamt").val(zsAmt);
				}else if(billamt*1>=30000)
				{
					for(var i=0;i<parent.lsProjectinfo.length;i++)
					{
						var pro = parent.lsProjectinfo[i];
						if(pro.id.prjno=="3600049")//头皮护理
						{
							if(gridlen==0)
							{
								addDetialRecord();
								gridlen=gridlen+1;
							} 
							if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
							{
								addDetialRecord();
								gridlen=gridlen+1;
							}
							var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
							commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno: pro.id.prjno
							  	,ineritemname :pro.prjname
							  	,entryamt  : ForDight(2080,0)
							  	,entrycount  : 6
							   });
						}
					}
					$("#sendamt").val(ForDight(2080,0));
				}
			}else if(obj.value*1==0){
				var _faceamt = $("#_faceamt").val();
				if(_faceamt*1<=0){
					//$("#sendpicflag option:first").attr("selected", true).change();
					$.ligerDialog.error("充值金额没有达到赠送要求");
					return;
				}
				$("#sendamt").css("background-color", "#FFFFFF").attr("readOnly", true);
		    	$("#picno").val("").css("background-color", "#EDF1F8").attr("readOnly", true);
		  		var _courseamt = $("#_courseamt").val();
		  		//如果疗程总金额大于充值金额 按充值金额的10%美容积分，如果充值金额大于疗程总金额 按疗程总金额的10%美容积分
		  		if(_courseamt*1>_faceamt*1){
		  			$("#sendamt").val(ForDight(_faceamt*0.1,0));
		  		}else{
		  			$("#sendamt").val(ForDight(_courseamt*0.1,0));
		  		}
			}else{
				if(obj.value*1<10){
					return;//新活动编码从11开始
				}
				var paymat = ForDight($("#sourceamt").val()*1, 1);
				var paycount = ForDight($("#paycount").val()*1, 1);
				var activinid = $("#sendpicflag option:selected").attr('data-val');
				var params = {strCurCompId:strCurCompId, activinid:activinid};
				var url = contextURL+"/cc016/loadActivDetail.action"; 
				$.post(url, params, function(data){
					var minfo = data.mactivityinfo;
					var activamt = ForDight(minfo.activamt, 1);
					var activcount = ForDight(minfo.activcount, 1);
					var activorand = minfo.activorand;
					if(nBillType==7){//购买疗程
						if(checkNull(activorand)==""){
							var params = {strCurCompId:strCurCompId, activinid:activinid, strCurBillId:$("#bsendbillid").val()};
							var url = contextURL+"/cc016/validateItems.action"; 
							$.post(url, params, function(data){
								if(data.strMessage=="ERROR"){
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("疗程套数没有达到赠送要求");
									return;
								}
								loadGiveInfo(activinid, minfo);
							});
						}else{
							if(activorand=="1"){
								if(paymat<activcount && paycount<activamt){
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("疗程金额或者套数没有达到赠送要求");
									return;
								}
							}else{
								if(paymat<activcount || paycount<activamt){//疗程项目paymat、paycount取值属性相反
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("疗程金额和套数没有达到赠送要求");
									return;
								}
							}
							loadGiveInfo(activinid, minfo);
						}
					}else if(nBillType==8){//购买产品
						if(checkNull(activorand)==""){
							var bool = false;
							var params = {strCurCompId:strCurCompId, activinid:activinid, strCurBillId:$("#sourcebillid").val()};
							var url = contextURL+"/cc016/validateGoods.action"; 
							$.post(url, params, function(data){
								if(data.strMessage=="ERROR"){
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("消费套数没有达到赠送要求");
									return;
								}
								loadGiveInfo(activinid, minfo);
							});
						}else{
							if(activorand=="1"){
								if(paymat<activamt && paycount<activcount){
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("消费金额或者套数没有达到赠送要求");
									return;
								}
							}else{
								if(paymat<activamt || paycount<activcount){
									$("#sendpicflag option:first").attr("selected", true);
									$.ligerDialog.error("消费金额和套数没有达到赠送要求");
									return;
								}
							}
							loadGiveInfo(activinid, minfo);
						}
					}else{
						if(paymat < activamt){
							$("#sendpicflag option:first").attr("selected", true);
							$.ligerDialog.error("金额没有达到赠送要求");
							return;
						}
						loadGiveInfo(activinid, minfo);
					}
				}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
			}
		}
		
		function loadGiveInfo(activinid, minfo){
			//赠送项目和产品 1:或、2:且、3：只送产品
			if(minfo.activstate==2){
				commoninfodivdetial_dyq.addRow({cardno:'套餐A', 
						createcardtype:1, activinid:activinid, activorand:minfo.activstate});
				commoninfodivdetial_dyq.addRow({cardno:'套餐B', 
						createcardtype:2, activinid:activinid, activorand:minfo.activstate});
			}else if(minfo.activstate==3){
				commoninfodivdetial_dyq.addRow({cardno:'套餐C', 
					createcardtype:3, activinid:activinid, activorand:minfo.activstate});
			}else{
				commoninfodivdetial_dyq.addRow({cardno:'套餐A', 
					createcardtype:1, activinid:activinid, activorand:minfo.activstate});
				commoninfodivdetial_dyq.addRow({cardno:'套餐B', 
						createcardtype:2, activinid:activinid, activorand:minfo.activstate});
				commoninfodivdetial_dyq.addRow({cardno:'套餐C', 
					createcardtype:3, activinid:activinid, activorand:minfo.activstate});
			}
		}
		
		function validatecreatecardtype(obj)
		{
			
	        
	        /*if(obj.value==1)
	        {
	        	var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
	        	var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900018'
																	  	,ineritemname :'基础面护(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
				addDetialRecord()
				gridlen=gridlen+1; 
				curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900019'
																	  	,ineritemname :'肩颈(单次)'
																	  	,entryamt  : 360
																	  	,entrycount  : 1
																	   });
	        }
	        else if(obj.value==2)
	        {
	        	var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900005'
																	  	,ineritemname :'氧动力紧致面护(单次)'
																	  	,entryamt  : 780
																	  	,entrycount  : 1
																	   });
	        }
	         else if(obj.value==3)
	         {
	         	var gridlen=commoninfodivdetial_tmk.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
				{
					addDetialRecord();
					gridlen=gridlen+1;
				}
				var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: '4900004'
																	  	,ineritemname :'果酸(单次)'
																	  	,entryamt  : 980
																	  	,entrycount  : 1
																	   });
	         }*/
	
				if(obj.value=="")
	      		{
	      			return;
	      		}
	      		
	      		var requestUrl ="cc016/validatePackageNo.action";
				var responseMethod="validatePackageNoMessage";	
				var params="strCurPackageId="+obj.value;	
				params =params+ "&strCurCompId="+strCurCompId;
				showDialogmanager = $.ligerDialog.waitting('正在加载套餐中,请稍候...');
				sendRequestForParams_p(requestUrl,responseMethod,params ); 
	      }
	      
	      
	       function loadSelecDyCardData(data, rowindex, rowobj)
	       {
	       		if(checkNull(data.cardno)=="")
	       		{
	       			return;
	       		}
	       		showDialogmanager = $.ligerDialog.waitting('正在加载套餐中,请稍候...');
	       		if(checkNull(data.activinid)!=""){
	       			var params = {strCurCompId:strCurCompId, activinid:data.activinid, 
	       					activtype:data.createcardtype, activorand:data.activorand};
	       			var url = contextURL+"/cc016/loadGiveDetail.action"; 
	       			$.post(url, params, function(data){
	       				showDialogmanager.close();
	       				var list = data.dactivGive;
	       				var rows=commoninfodivdetial_tmk.rows;
	       				commoninfodivdetial_tmk.deleteRange(rows);
	       				if(list != null && list.length>0){
	       					$(list).each(function(i, row){
	       						commoninfodivdetial_tmk.addRow({ineritemno: row.activno,
	       							ineritemname:row.activname, entryamt:ForDight(row.givetotal, 0),//赠送活动唯一标识@赠送套餐标识@提成方式
	       							entrycount:ForDight(row.givecount, 0), entryremark:'', packageNo:(row.activinid+"@"+row.activtype+"@"+row.takeway)});
	       					});
	       				}
	       			}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
	       		}else{
	       			var requestUrl ="cc016/validatePackageNo.action";
	       			var responseMethod="validatePackageNoMessage";	
	       			var params="strCurPackageId="+data.createcardtype;	
	       			params =params+ "&strCurCompId="+strCurCompId;
	       			sendRequestForParams_p(requestUrl,responseMethod,params ); 
	       		}
	       }
	      function validatePackageNoMessage(request)
	     {
	    		
	        	try
				{
					showDialogmanager.close();
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	commoninfodivdetial_tmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        		commoninfodivdetial_tmk.loadData(true); 
		        	if(checkNull(strMessage)=="")
		        	{	   
		        		  	
						if(responsetext.lsDmpackageinfo!=null && responsetext.lsDmpackageinfo.length>0)
						{
							for(var i=0;i<responsetext.lsDmpackageinfo.length;i++)
							{
								var gridlen=commoninfodivdetial_tmk.rows.length*1;
						     	if(gridlen==0)
								{
									addDetialRecord();
									gridlen=gridlen+1;
								} 
								if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
								{
									addDetialRecord();
									gridlen=gridlen+1;
								}
					        	var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
								commoninfodivdetial_tmk.updateRow(curDetialRecord,{   ineritemno: responsetext.lsDmpackageinfo[i].bpackageprono
																					  	,ineritemname :responsetext.lsDmpackageinfo[i].bpackageproname
																					  	,entryamt  : ForDight(responsetext.lsDmpackageinfo[i].packageproamt,1)
																					  	,entrycount  : ForDight(responsetext.lsDmpackageinfo[i].packageprocount,1)
																					   });
							}
						}
						 
		        		
		        	}
		        	else
		        	{
	        			addDetialRecord();
		        		$.ligerDialog.warn(strMessage);
		        	}
	        	}
				catch(e)
				{
					alert(e.message);
				}
	      }
		
		function validatecreatecardcount(obj)
		{
			commoninfodivdetial_dyq.options.data=$.extend(true, {},{Rows: null,Total: 0});
	        commoninfodivdetial_dyq.loadData(true); 
	        addDetialRecord_dyq();
			var ccount=1;
			while(obj.value*1-ccount*1>0)
			{
			    	addDetialRecord_dyq();
			    	ccount=ccount*1+1;
			 }
		}
		
		function sendCardPoint()
		{
			
			if($("#sendpicflag").val()==6 || $("#sendpicflag").val()==7 || $("#sendpicflag").val()==8)
			{
				var sumAmt=0;
				for (var rowid in commoninfodivdetial_tmk.records)
				{
					var row =commoninfodivdetial_tmk.records[rowid];
					sumAmt=row['entryamt']*1;
				}
				if(sumAmt*1>$("#sendamt").val()*1)
				{
					$.ligerDialog.error("疗程总金额超过了赠送金额,请重新操作");
					return;
				}
			}
			else if($("#sendpicflag").val()==9)
			{
				if($("#sourceamt").val()*1<6800)
				{
					$.ligerDialog.error("金额没有达到赠送要求");
					return;
				}
			}else if($("#sendpicflag").val()==10)
			{
				if($("#sourceamt").val()*1<5000)
				{
					$.ligerDialog.error("金额没有达到赠送要求!");
					return;
				}
			}else if($("#sendpicflag").val()==0){//美容积分赠送
				if($("#_faceamt").val()*1<=0){
					$.ligerDialog.error("充值金额没有达到赠送要求!");
					return;
				}
				if($("#sendamt").val()*1==0){
					$.ligerDialog.error("请确认赠送的美容积分!");
					return ;
				}
			}
			else
			{
				if(document.getElementById("sourcecardno").value=="")
				{
					$.ligerDialog.error("请确认原始会员卡是否存在!");
					return ;
				}
				if(document.getElementById("sendpicflag").value==1)
				{
					if(document.getElementById("sendamt").value*1==0)
					{
						$.ligerDialog.error("请确认赠送的金额!");
						return ;
					}
					var sendRate=0;
					if(document.getElementById("sendrateflag").value==1)
					{
						sendRate=document.getElementById("sendPointRate2").value;
					}
					else
					{
						sendRate=document.getElementById("sendPointRate1").value;
					}
					if(ForDight(document.getElementById("sendamt").value*1,1)>ForDight(document.getElementById("sourceamt").value*1*sendRate*1,1))
					{
						$.ligerDialog.error("赠送金额超过了系统设定赠送上限!");
						return ;
					}
				
				}
			}
			
			//if(document.getElementById("sendpicflag").value=="3")
			//{
				//$.ligerDialog.error("赠送日历活动已在2014年1月18号停止,如有疑问请致电运营部!");
				//return ;
			//}
			document.getElementById("sendpicflag").disabled=false;
			lastconfirm();
			return;
			$.ligerDialog.confirm("赠送抵用券或积分需要经理卡验证,请插入经理卡", function (result)
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
	     			var params ="mangerCardNo="+cardNo;	
	     			var requestUrl ="cc016/handInsertShare.action";
   					var responseMethod="handInsertShareMessage";;
		   			sendRequestForParams_p(requestUrl,responseMethod,params)
		       	}
				else
				{
					return ;
				}
			});
	}
	
	 function handInsertShareMessage(request)
     {
     		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)=="")
	        	{	        	
	        		lastconfirm();
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
        
	function lastconfirm()
	{
			//document.getElementById("createcardtype").disabled=false; 
			var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
			if(document.getElementById("sendtype").value=="6")
				document.getElementById("sendpicflag").disabled=true;
			//document.getElementById("createcardtype").disabled=true;
			var strJsonParam_detial="";
			var curjosnparam="";
			var needReplaceStr="";
			for (var rowid in commoninfodivdetial_tmk.records)
			{
				var row =commoninfodivdetial_tmk.records[rowid];
				if(checkNull(row.ineritemno)=="")
				{
					continue;
				}
				curjosnparam=JSON.stringify(row);
				curjosnparam=curjosnparam.replace("%","");
				curjosnparam=curjosnparam.replace("#","");
				if(strJsonParam_detial!="")
						strJsonParam_detial=strJsonParam_detial+",";
				strJsonParam_detial= strJsonParam_detial+curjosnparam;
			} 
							
			var requestUrl ="cc016/sendCardPoint.action";
			var responseMethod="sendCardPointMessage";	
			showDialogmanager = $.ligerDialog.waitting('正在处理中,请稍候...');	
			var params=queryStringTmp;
			params=params+"&strJsonParam=["+strJsonParam_detial+"]";
			params=params+"&strSearchDate="+document.getElementById("searchdate").value;
			
			//if(document.getElementById("sendtype").value==6 || document.getElementById("sendtype").value==7)
			if(document.getElementById("sendpicflag").value==5)
			{
				if(commoninfodivdetial_dyq.rows.length*1>0 )
				{
					   commoninfodivdetial_dyq.endEdit();
				}
				strJsonParam_detial="";
				for (var rowid in commoninfodivdetial_dyq.records)
				{
					var row =commoninfodivdetial_dyq.records[rowid];
					if(checkNull(row.cardno)=="")
					{
						continue;
					}
					curjosnparam=JSON.stringify(row);
					curjosnparam=curjosnparam.replace("%","");
					curjosnparam=curjosnparam.replace("#","");
					if(strJsonParam_detial!="")
							strJsonParam_detial=strJsonParam_detial+",";
					strJsonParam_detial= strJsonParam_detial+curjosnparam;
				} 
				if(strJsonParam_detial!="")
				{
					params=params+"&strJsonDyqParam=["+strJsonParam_detial+"]";
				}
			}
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
		}
		
		 function sendCardPointMessage(request)
	     {
	    		
	        	try
				{
					showDialogmanager.close();
		        	var responsetext = eval("(" + request.responseText + ")");
		        	var strMessage=responsetext.strMessage;
		        	if(checkNull(strMessage)=="")
		        	{	        		 
		        		 $.ligerDialog.success("赠送成功,请到会员卡资料中查询确认!");
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
      
		function addDetialRecord()
	   	{
	   		var row = commoninfodivdetial_tmk.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
					    //参数3:之前或者之后(非必填)
			commoninfodivdetial_tmk.addRow({ 
					                ineritemno: "",
					                ineritemname: "",
					                entrycount: "",
					                entryamt: "",
					                entryremark:""
					            }, row, false);
	   	}
   		function addDetialRecord_dyq()
	   	{
	   		var row = commoninfodivdetial_dyq.getSelectedRow();
					     //参数1:rowdata(非必填)
					    //参数2:插入的位置 Row Data 
					    //参数3:之前或者之后(非必填)
			commoninfodivdetial_dyq.addRow({ 
					                strDyqCardNo: ""
					            }, row, false);
	   	}
   	
        function  itemclick_adddetial (item)
        {
        	var row = commoninfodivdetial.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivdetial.addRow({
				            }, row, false);
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
    
     function validateItem(obj)
   	{
   		if(obj.value=="")
   		{
   			commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
   		}
   		else
   		{
   			var existscount=0;
   			for (var rowid in commoninfodivdetial_tmk.records)
			{
				var row =commoninfodivdetial_tmk.records[rowid];
				if(obj.value==row.ineritemno)
				{
					existscount=existscount*1+1;
				}
			}  
			
			if(existscount>1)
			{
				commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
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
	       		commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno:'',ineritemname:'',entrycount:0,entryamt:0}); 
	       	}
	       	else
	       	{
	       		commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemname:responsetext.corpspicname,entrycount:1}); 
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function validatePicno(obj)
   	{
   		if(obj.value=="")
   		{
   			return ;
   		}
   		var requestUrl ="cc016/validatePicno.action";
   		var params = "strCurCompId="+strCurCompId;	
        params = params+"&strCurDyCardNo="+obj.value;
		var responseMethod="validatePicnoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 
   	}
   	
   	function validatePicnoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       	
	       		for (var rowid in commoninfodivdetial_dyq.records)
				{
					var row =commoninfodivdetial_dyq.records[rowid];
					if(checkNull(row.cardno)=="")
					{
						continue;
					}
					if(checkNull(row.cardno)==responsetext.curNointernalcardinfo.cardno)
					{
						$.ligerDialog.error("该券号已经在列表中存在,请确认!");
						document.getElementById("picno").value="";
						return;
					}
				} 
	   			var gridlen=commoninfodivdetial_dyq.rows.length*1;
		     	if(gridlen==0)
				{
					addDetialRecord_dyq();
					gridlen=gridlen+1;
				} 
				if(checkNull(commoninfodivdetial_dyq.getRow(0).cardno)!="")
				{
					addDetialRecord_dyq();
					gridlen=gridlen+1;
				}
	        	var curDetialRecord=commoninfodivdetial_dyq.getRow(gridlen-1);
				commoninfodivdetial_dyq.updateRow(curDetialRecord,{   cardno: responsetext.curNointernalcardinfo.cardno
																	 ,createcardtypename :responsetext.curNointernalcardinfo.createcardtypename
																	 ,createcardtype  : responsetext.curNointernalcardinfo.createcardtype
																	   });
	       	}
	       	else
	       	{
	       		
	       		$.ligerDialog.error(strMessage);
	       	}
	       	document.getElementById("picno").value="";
	       	document.getElementById("picno").select();
	       	document.getElementById("picno").focus();
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function deleteCurDyCardno()
   	{
   		commoninfodivdetial_dyq.deleteSelectedRow();
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
				addDetialRecord();
				window.event.keyCode = 505;
				window.event.returnValue=false;
			}
			else if(key==115)//F4
			{
				
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
				var fieldName = document.activeElement.name;
				var fieldId = document.activeElement.id ;
				if(fieldId=="prjid")
				{
					validateProjectNo(document.activeElement);
				}
				window.event.keyCode=9; //tab
				window.event.returnValue=true;
			}
			catch(e){alert(e.message);}
				
		}   
		
		
		function deleteRows()
		{
			$("#sendamt").val("");
			var rows=commoninfodivdetial_dyq.rows;
			commoninfodivdetial_dyq.deleteRange(rows);
			var rows=commoninfodivdetial_tmk.rows;
			commoninfodivdetial_tmk.deleteRange(rows);
		}
		
		function addOptiondivex(value,keyw)
	  	{
	   		var showvalue = value.replace(keyw, "<b><font color=\"blue\">" + keyw + "</font></b>");
	  		var realValue=value.substring(0,value.indexOf("-"));
	  		document.getElementById("keysList").innerHTML +="<div onmouseover=this.className=\"sman_selectedStyle\";document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\" onmouseout=this.className=\"\"; onmousedown=document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\";selectCall();document.getElementById(\""+objInputId+"\").onchange();>" + showvalue + "</div>" ;
	  	}
		
		function selectCall()
		{	
		    if(document.getElementById("prjid").value!="")
		 		validateProjectNo(document.getElementById("prjid"));
		}
		
		function validateProjectNo(obj)
		{
			var bFlag=false;
			for(var i=0;i<parent.lsProjectinfo.length;i++)
			{
				if(parent.lsProjectinfo[i].id.prjno==obj.value)
				{
					if(parent.lsProjectinfo[i].saleflag==1 && parent.lsProjectinfo[i].prjsaletype==1)
					{
						bFlag=true;
						$("#prjname").val(parent.lsProjectinfo[i].prjname);
						$("#prjnum").val(1);
						$("#prjamt").val(ForDight(parent.lsProjectinfo[i].ysaleprice/parent.lsProjectinfo[i].ysalecount,0));
						break;
					}
				}
			}
			if(!bFlag)
			{
				$.ligerDialog.error("输入的项目不存在或者已经停售");
				obj.value="";
				$("#prjname").val("");
				return;
			}
		}
		
		function isCheck(obj)
		{
			if(obj.checked)
			{
				deleteRows();
				$("#prjid").attr("readOnly",false);
				$("#prjnum").attr("readOnly",false);
			}
			else
			{
				$("#prjid").attr("readOnly",true);
				$("#prjnum").attr("readOnly",true);
			}
		}
		
		function addPrj()
		{
			if($("#prjid").val()=="")
			{
				$.ligerDialog.error("请输入疗程编号");
				return;
			}
			if($("#prjnum").val()*1<1)
			{
				$.ligerDialog.error("请输入疗程数量");
				return;
			}
			var gridlen=commoninfodivdetial_tmk.rows.length*1;
			if(gridlen==0)
			{
				addDetialRecord();
				gridlen=gridlen+1;
			} 
			if(checkNull(commoninfodivdetial_tmk.getRow(0).ineritemno)!="")
			{
				addDetialRecord();
				gridlen=gridlen+1;
			}
			var curDetialRecord=commoninfodivdetial_tmk.getRow(gridlen-1);
			commoninfodivdetial_tmk.updateRow(curDetialRecord,{ineritemno:$("#prjid").val()
			  	,ineritemname :$("#prjname").val()
			  	,entryamt  : ForDight($("#prjnum").val()*1*$("#prjamt").val(),0)
			  	,entrycount  : $("#prjnum").val()
			   });
			$("#prjid").val("");
			$("#prjnum").val("");
			$("#prjamt").val("");
			$("#prjname").val("");
		}
		
		function changenum(obj)
		{
			if(isNaN(obj.value))
			{
				obj.value="1";
			}
		}
		
		
        
       