   var CC004DetialTab = null;
   var cc004layout=null;
   var compTreeManager;
   var compTree;
   var strCurCompId ="";
   var commoninfodivCorps = null;
   var commoninfodivdiyq = null;
   var commoninfodivtmk = null;
   var f_detailPanel=null;
   var f_callback=null;
   var commoninfodivsaletmk = null;
   var fromValidate=null;
   var payMode1manager=null;
   var payMode2manager=null;
   var userempmanager1=null;
   var userempmanager2=null;
   var lsStaffinfo = null;
   var strDYCardNo="";
   var strOldValidate="";
   var tglxData=JSON.parse(parent.loadCommonControlDate_select("TGLX",0));
   var chooseData = [{ choose: 1, text: '需要密码' }, { choose: 0, text: '不需密码'}];
   $(function ()
   	{
	   try
	   {
	   		  //布局
            cc004layout= $("#cc004layout").ligerLayout({ leftWidth: 270,  allowBottomResize: false, allowLeftResize: false });
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
          	
          	//------条码卡查询
            commoninfodivtmk=$("#commoninfodivtmk").ligerGrid({
                columns: [
                { display: '登记门店', 		name: 'bcardvesting', 		width:60	,align: 'left' },
                { display: '条码卡号', 		name: 'bcardno', 			width:90	,align: 'left' },
                { display: '来源', 			name: 'entrytypeText',  	width:80	,align: 'left'},
	            { display: '状态', 			name: 'cardstateText',  	width:60	,align: 'left'},
	            { display: '有效日期',   		name: 'lastvalidate',  	width:80	,align: 'center'},
	            { display: '消费密码', 		name: 'uespassward',  	width:80	,align: 'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.uespassward == 1) return '需要密码';
	                      return '不需密码';
	                }}
	             ],  pageSize:20, 
                data: null,      
                width: '500',
                height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:false,
                detail: { onShowDetail: showTmkDetialinfo}
            });
            
            //--------销售条码卡
            commoninfodivsaletmk=$("#commoninfodivsaletmk").ligerGrid({
                columns: [
                { display: '项目编码', 		name: 'saleproid', 			width:100	,align: 'left' },
                { display: '项目名称', 		name: 'saleproanme', 		width:130	,align: 'left' },
                { display: '销售次数', 		name: 'saleprocount',  		width:60	,align: 'left'},
	            { display: '销售金额', 		name: 'saleproamt', 	 	width:60	,align: 'left'},
	            { display: '备注',   		name: 'saleremark',  		width:120	,align: 'center',editor: { type: 'text'}}
	             ],  pageSize:20, 
                data: null,      
                width: '520',
                height:height-302,
                enabledEdit: true,  checkbox:false,rownumbers:false,usePager:false
               
            });
            
          	//------抵用券查询
            commoninfodivdiyq=$("#commoninfodivdiyq").ligerGrid({
                columns: [
                { display: '登记门店', 		name: 'bcardvesting', 		width:60	,align: 'left' },
                { display: '抵用券号', 		name: 'bcardno', 		width:110	,align: 'left' },
                { display: '抵用面值', 		name: 'cardfaceamt', 		width:60	,align: 'left'},
	            { display: '抵用类型', 		name: 'carduseflagText',  	width:80	,align: 'left'},
	            { display: '状态', 			name: 'cardstateText',  		width:60	,align: 'left'},
	            { display: '启用日期',   		name: 'enabledate',  			width:80	,align: 'left'},
	            { display: '使用日期',   		name: 'usedate',  			width:80	,align: 'left'},
	            { display: '有效日期',   		name: 'lastvalidate',  	width:80	,align: 'center'},
	            { display: '抵用项目',   		name: 'useinproject',  		width:110	,align: 'center'},
	            { display: '券标示',   		name: 'createcardtypename',  		width:110	,align: 'center'}
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:false,usePager:true,
                detail: { onShowDetail: showDetialinfo},
                onDblClickRow : function (data, rowindex, rowobj)
                {
                	strDYCardNo=data.bcardno;
                	strOldValidate=data.lastvalidate;
                	$.ligerDialog.open({ url:contextURL+'/CardControl/CC004/retvalidate.jsp', width:250, title: '设置密码' });
                } 
            });
            
          	//------团购卡查询
            commoninfodivCorps=$("#commoninfodivCorps").ligerGrid({
                columns: [
                { display: '团购号', 		name: 'corpscardno', 		width:110	,align: 'left' },
                { display: '团购商家', 	name: 'corpssource', 		width:80	,align: 'left' ,
                	editor: { type: 'select', data: tglxData, valueField: 'choose',selectBoxHeight:'220'},
	            	render: function (item)
	              	{
	              		var lsZw=parent.gainCommonInfoByCode("TGLX",0);
	              		for(var i=0;i<lsZw.length;i++)
						{
							if(lsZw[i].bparentcodekey==item.corpssource)
							{	
								return lsZw[i].parentcodevalue;								
						    }
						}
	                    return '';
	                } 
	            },
                { display: '团购类型', 	name: 'corpstypeText', 		width:60	,align: 'left'},
	            { display: '团购编号/类型', 	name: 'corpspicno',  	width:80	,align: 'left'},
	            { display: '团购内容', 	name: 'corpspicname',  		width:150	,align: 'left'},
	            { display: '金额',   	name: 'corpsamt',  			width:50	,align: 'left'},
	            { display: '登记日期',   	name: 'operationdate',  	width:80	,align: 'center'},
	            { display: '登记人',   	name: 'operationer',  		width:70	,align: 'center'},
	            { display: '状态',  	 	name: 'corpssateText',  	width:50	,align: 'center'},
	            { display: '使用日期',   	name: 'useindate',  		width:80	,align: 'right'},
	            { display: '使用门店',   	name: 'useincompid',  		width:60	,align: 'right'},
	            { display: '使用单号',  	name: 'useinbillno',  		width:110	,align: 'right'}
	            ],  pageSize:20, 
                data: null,      
                width: '100%',
                height:height-120,
                enabledEdit: false,  checkbox:false,rownumbers:true,usePager:true
            });
            
          	$("#pageloading").hide(); 
      		$("#CC004DetialTab").ligerTab();
            CC004DetialTab = $("#CC004DetialTab").ligerGetTabManager();
            $("#searchFromDateKey").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#searchToDateDateKey").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#searchFromDateKeyDYQ").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#searchToDateDateKeyDYQ").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#searchCorpsInfo").ligerButton(
	         {
	             text: '查询团购卡信息', width: 120,
		         click: function ()
		         {
		             searchCorpsRecord();
		         }
	         });
	          $("#searchDyqInfo").ligerButton(
	         {
	             text: '查询抵用券信息', width: 120,
		         click: function ()
		         {
		             searchDyqRecord();
		         }
	         });
	          $("#searchTmkInfo").ligerButton(
	         {
	             text: '查询条码卡信息', width: 120,
		         click: function ()
		         {
		             searchTmkRecord();
		         }
	         });
	        $("#slaeTmkInfo").ligerButton(
	         {
	             text: '销售条码卡', width: 100,
		         click: function ()
		         {
		             saleTmkRecord();
		         }
	         });
	          $("#readCurCardInfo").ligerButton(
	         {
	             text: '读卡', width: 60,
		         click: function ()
		         {
		             readCurCardInfo();
		         }
	         });
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
             var payModeData=JSON.parse(parent.loadCommonControlDateCash("ZFFS",0));
           	 payMode1manager = $("#firstpaymode").ligerComboBox({ data: payModeData, isMultiSelect: false,valueFieldID: 'factfirstpaymode',width:'140',onBeforeSelect:function(newdata){
           		if(document.getElementById("ishz").value=="2")
           		{
           			if(newdata!="1" && newdata!="6")
           			{
           				$.ligerDialog.warn("合作项目只能用现金和银行卡来支付");
           				return false;
           			}
           		}
           	 }});	
             payMode2manager = $("#secondpaymode").ligerComboBox({ data: payModeData, isMultiSelect: false,valueFieldID: 'factsecondpaymode',width:'140',onBeforeSelect:function(newdata){
            	 if(document.getElementById("ishz").value=="2")
            	 {
            		if(newdata!="1" && newdata!="6")
            		{
            			$.ligerDialog.warn("合作项目只能用现金和银行卡来支付");
            			return false;
            		}
            	}
             }});	
           	addcommoninfodivtmk();
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
        strCurCompId=note.data.id;
        addSaleTmk();
    }
    
      function  addcommoninfodivtmk()
        {
        	var row = commoninfodivtmk.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivtmk.addRow({ 
				             
				            }, row, false);
        }
        
    function addSaleTmk()
    {
    	var params = "strCurCompId="+strCurCompId;				
     	var requestUrl ="cc004/add.action"; 
		var responseMethod="addMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    function addMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
    		var curMaster=responsetext.curMaster;
	        document.getElementById("salebillid").value=curMaster.id.salebillid;    
	        document.getElementById("saledate").value=curMaster.saledate;    
	        document.getElementById("saletime").value=curMaster.saletime;     
	        document.getElementById("operationer").value=curMaster.operationer;  
	        document.getElementById("barcodecardno").value=""; 
	        document.getElementById("firstpayamt").value=""; 
	        document.getElementById("secondpayamt").value=""; 
	        document.getElementById("firstsaleamt").value=""; 
	        document.getElementById("secondsaleamt").value=""; 
	        document.getElementById("packageNo").value=""; 
	        document.getElementById("packageName").value=""; 
	        document.getElementById("saleamt").value=""; 
	        document.getElementById("usecardno").value=""; 
	        document.getElementById("saleMonths").value=""; 
	        document.getElementById("usecardtypename").value=""; 
	        document.getElementById("usecardtype").value=""; 
	        document.getElementById("lbmembername").value=""; 
	        document.getElementById("lbmemberphone").value=""; 
	        document.getElementById("curkeepAmt").value=""; 
	        document.getElementById("usecardpayamt").value=""; 
	        
	        commoninfodivsaletmk.options.data=$.extend(true, {},{Rows: null,Total:0});
			commoninfodivsaletmk.loadData(true);  
			lsStaffinfo= responsetext.lsStaffinfo;
	   		if(lsStaffinfo!=null && lsStaffinfo.length>0)
	   		{
		   		
			   		userempmanager1= $("#firstsaleempid").ligerComboBox({ isShowCheckBox: false, 
		                  url:'loadAutoStaff',  data: loadGridByStaffInfo(lsStaffinfo,'')
		                , autocomplete: true,width:'140', valueFieldID: 'factfirstsaleempid'  
		            }); 
		            userempmanager2= $("#secondsaleempid").ligerComboBox({ 
		                  url:'loadAutoStaff',  data: loadGridByStaffInfo(lsStaffinfo,'')
		                , autocomplete: true,width:'140', valueFieldID: 'factsecondsaleempid' 
                
		            }); 
		        	
		         
	        }
	        else
	        {
	        	userempmanager1= $("#firstsaleempid").ligerComboBox({ isShowCheckBox: false, 
		                url:'loadAutoStaff',  data: null , valueFieldID: 'factfirstsaleempid',label:'选择姓名',labelWidth:200,labelAlign:'center',width:'140'
		            }); 
		        userempmanager2= $("#secondsaleempid").ligerComboBox({ isShowCheckBox: false, 
		               url:'loadAutoStaff',   data: null , valueFieldID: 'factsecondsaleempid',label:'选择姓名',labelWidth:200,labelAlign:'center',width:'140'
		            }); 
	        }
	        payMode1manager.selectValue("");
	        payMode2manager.selectValue("");
	        userempmanager1.selectValue("");
	        userempmanager2.selectValue("");
	        document.getElementById("barcodecardsource").innerHTML="";
	        clearOption("packageNo");
	        addOption("","",document.getElementById("packageNo"));
	        if(responsetext.lsMpackageinfo!=null && responsetext.lsMpackageinfo.length>0)
			{
				for(var i=0;i<responsetext.lsMpackageinfo.length;i++)
				{
					addOption(responsetext.lsMpackageinfo[i].id.packageno,responsetext.lsMpackageinfo[i].packagename,document.getElementById("packageNo"));
				}
			}
	   	}
	   	catch(e){alert(e.message);}
    }
    
    
    function loadAutoStaff(curmanager,curWriteStaff)
    {
    	curmanager.setData(loadGridByStaffInfo(lsStaffinfo,curWriteStaff));
    	curmanager.selectBox.show();
    }
  
    function searchCorpsRecord()
    {
    	var searchCorpsTypeKey=document.getElementById("searchCorpsTypeKey").value;
    	var searchCorpsNoKey=document.getElementById("searchCorpsNoKey").value;
    	var searchCorpsStateKey=document.getElementById("searchCorpsStateKey").value;
    	var searchFromDateKey=document.getElementById("searchFromDateKey").value;
    	var searchToDateDateKey=document.getElementById("searchToDateDateKey").value;
    	var requestUrl ="cc004/searchCorpsinfos.action";
    	var  params="strCurCompId="+strCurCompId;
        	 params=params+"&searchCorpsTypeKey="+searchCorpsTypeKey;
        	 params=params+"&searchCorpsNoKey="+searchCorpsNoKey;
        	 params=params+"&searchCorpsStateKey="+searchCorpsStateKey;
        	 params=params+"&searchFromDateKey="+searchFromDateKey;
        	 params=params+"&searchToDateDateKey="+searchToDateDateKey;
		var responseMethod="searchCorpsRecordMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
    	
    }
    
    function searchCorpsRecordMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	if(responsetext.lsCorpsbuyinfo!=null && responsetext.lsCorpsbuyinfo.length>0)
		   		{
		   			commoninfodivCorps.options.data=$.extend(true, {},{Rows: responsetext.lsCorpsbuyinfo,Total: responsetext.lsCorpsbuyinfo.length});
	            	commoninfodivCorps.loadData(true);   
	            	commoninfodivCorps.select(0);      	
		   		}
		   		else
		   		{
		   			 $.ligerDialog.warn("没有查到相应的团购信息!");
		   			commoninfodivCorps.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivCorps.loadData(true);   
		   		}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
     function searchDyqRecord()
    {
  
    	var searchDyqNoKey=document.getElementById("searchDyqNoKey").value;
    
    	var searchDyqStateKey=document.getElementById("searchDyqStateKey").value;
    	var searchFromDateKey=document.getElementById("searchFromDateKeyDYQ").value;
    	var searchToDateDateKey=document.getElementById("searchToDateDateKeyDYQ").value;
    
    	var requestUrl ="cc004/searchDyqinfos.action";
    	var  params="strCurCompId="+strCurCompId;
        	 params=params+"&searchDyqNoKey="+searchDyqNoKey;
        	 params=params+"&searchDyqStateKey="+searchDyqStateKey;
        	 params=params+"&searchFromDateKey="+searchFromDateKey;
        	 params=params+"&searchToDateDateKey="+searchToDateDateKey;
        	 params+="&searchBillId="+$("#searchBillId").val();
		var responseMethod="searchDyqinfosMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
    	
    }
    
    function searchDyqinfosMessage(request)
    {
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	if(responsetext.lsNointernalcardinfo!=null && responsetext.lsNointernalcardinfo.length>0)
		   		{
		   			commoninfodivdiyq.options.data=$.extend(true, {},{Rows: responsetext.lsNointernalcardinfo,Total: responsetext.lsNointernalcardinfo.length});
	            	commoninfodivdiyq.loadData(true);   
	            	commoninfodivdiyq.select(0);      	
		   		}
		   		else
		   		{
		   			 $.ligerDialog.warn("没有查到相应的抵用券信息!");
		   			commoninfodivdiyq.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivdiyq.loadData(true);   
		   		}
        	}
			catch(e)
			{
				alert(e.message);
			}
    }
    
    //--根据账户历史显示交易历史
   		function showDetialinfo(row, detailPanel,callback)
		{
			f_detailPanel=detailPanel;
			f_callback=callback;
			loadDetiaByCardNo(strCurCompId,row.bcardno);
		}
		
		function loadDetiaByCardNo(strCurCompId,cardno)
		{
			
			var requestUrl ="cc004/searchDyqDetialinfos.action"; 
			var responseMethod="searchDyqDetialinfosMessage";		
			var param="strCurCompId="+strCurCompId;	
			param=param+"&strDyqCardNo="+cardno;
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function searchDyqDetialinfosMessage(request)
	    {
	    	try
	    	{
	    	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsDnointernalcardinfo!=null && responsetext.lsDnointernalcardinfo.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '代码', name: 'ineritemno' ,width: 100 },
	                            { display: '名称', name: 'ineritemname' ,width: 130 },
	                            { display: '登记数量', name: 'entrycount' ,width: 60 },
	                            { display: '消耗数量', name: 'usecount' ,width: 60 },
	                            { display: '登记金额', name: 'entryamt' ,width: 80 },
	                            { display: '消耗金额', name: 'useamt' ,width: 80 }
	                            ], isScroll: false, showToggleColBtn: false, width: '90%', 
	                 data:{Rows: responsetext.lsDnointernalcardinfo,Total: responsetext.lsDnointernalcardinfo.length}
	                 , showTitle: false, rownumbers:true,columnWidth: 100
	                 , onAfterShowData: f_callback,frozen:false,usePager:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
	   	
	   	function searchTmkRecord()
	   	{
	   		var searchTmkNoKey=document.getElementById("searchTmkNoKey").value;
    		var searchTmkStateKey=document.getElementById("searchTmkStateKey").value;
    		var requestUrl ="cc004/searchTmkinfos.action";
    		var params="strCurCompId="+strCurCompId;
        	 	params=params+"&searchTmkNoKey="+searchTmkNoKey;
        	 	params=params+"&searchTmkStateKey="+searchTmkStateKey;
			var responseMethod="searchTmkinfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	
	   	}
	   	
	   	function searchTmkinfosMessage(request)
    	{
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	if(responsetext.lsNointernalcardinfo!=null && responsetext.lsNointernalcardinfo.length>0)
		   		{
		   			commoninfodivtmk.options.data=$.extend(true, {},{Rows: responsetext.lsNointernalcardinfo,Total: responsetext.lsNointernalcardinfo.length});
	            	commoninfodivtmk.loadData(true);   
	            	commoninfodivtmk.select(0);      	
		   		}
		   		else
		   		{
		   			 $.ligerDialog.warn("没有查到相应的条码卡信息!");
		   			commoninfodivtmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivtmk.loadData(true);   
		   		}
        	}
			catch(e)
			{
				alert(e.message);
			}
    	}
    	
    	function showTmkDetialinfo(row, detailPanel,callback)
    	{
			f_detailPanel=detailPanel;
			f_callback=callback;
			loadTmkDetiaByCardNo(strCurCompId,row.bcardno);
		}
		
		function loadTmkDetiaByCardNo(strCurCompId,cardno)
		{
			
			var requestUrl ="cc004/searchTmkDetialinfos.action"; 
			var responseMethod="searchTmkDetialinfosMessage";		
			var param="strCurCompId="+strCurCompId;	
			param=param+"&strTmkCardNo="+cardno;
			sendRequestForParams_p(requestUrl,responseMethod,param );
		}
		
		function searchTmkDetialinfosMessage(request)
	    {
	    	try
	    	{
	    	var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsDnointernalcardinfo!=null && responsetext.lsDnointernalcardinfo.length>0)
	   		{
	   			var detailGrid = document.createElement('div'); 
	            $(f_detailPanel).append(detailGrid);
	            $(detailGrid).css('margin',10).ligerGrid({
	                columns:
	                            [
	                            { display: '名称', 		name: 'ineritemname' ,width: 110 },
	                            { display: '购买', 		name: 'entrycount' ,width: 30 },
	                          	{ display: '消耗', 		name: 'usecount' ,width: 30 },
	                          	{ display: '余数', 		name: 'lastcount' ,width: 30 },
	                          	{ display: '购买', 		name: 'entryamt' ,width: 40 },
	                            { display: '消耗', 		name: 'useamt' ,width: 40 },
	                            { display: '余额', 		name: 'lastamt' ,width: 40 },
	                            { display: '消费单号', 	name: 'costbillno' ,width: 110 }
	                            ], isScroll: false, showToggleColBtn: false, width: '100%', 
	                 data:{Rows: responsetext.lsDnointernalcardinfo,Total: responsetext.lsDnointernalcardinfo.length}
	                 , showTitle: false, rownumbers:false,columnWidth: 100
	                 , onAfterShowData: f_callback,frozen:false,usePager:false
	            });
	   		}
	   		}catch(e){alert(e.message)}
	   	}
	   	
	   	function validateBarcodecardno(obj)
	   	{
	   		if(obj.value=="")
	   		{
	   			document.getElementById("barcodecardsource").innerHTML="";
	   			return ;
	   		}
	   		var requestUrl ="cc004/validateBarcodecardno.action"; 
			var responseMethod="validateBarcodecardnoMessage";		
			var param="strCurCompId="+strCurCompId;	
			param=param+"&strTmkCardNo="+obj.value;
	
			sendRequestForParams_p(requestUrl,responseMethod,param );
	   	}
    	
    	function validateBarcodecardnoMessage(request)
    	{
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("barcodecardsource").innerHTML="";
	        		 document.getElementById("barcodecardno").value="";
	        	}
	        	else
	        	{
	        		document.getElementById("barcodecardsource").innerHTML=responsetext.strTmkEntryType
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    	}
    	
    	//验证条码套餐编号
    	function validatePackageNo(obj)
    	{
    		if(obj.value=="")
    		{
    			document.getElementById("packageName").value="";
    		}
    		else
    		{
    			var requestUrl ="cc004/validatePackageNo.action"; 
				var responseMethod="validatePackageNoMessage";		
				var param="strCurCompId="+strCurCompId;	
				param=param+"&strCurPackageNo="+obj.value;
				sendRequestForParams_p(requestUrl,responseMethod,param );
    		}
    	}
    	
    	function validatePackageNoMessage(request)
    	{
    		try
			{
	        	var responsetext = eval("(" + request.responseText + ")");
	        	var strMessage=responsetext.strMessage;
	        	if(checkNull(strMessage)!="")
	        	{
	        		 $.ligerDialog.warn(strMessage);
	        		 document.getElementById("packageNo").value="";
	        		 document.getElementById("packageName").value="";
	        		 document.getElementById("ishz").value="";
	        	}
	        	else
	        	{
	        	
	        		//document.getElementById("packageName").value=responsetext.strCurPackageName;
	        		document.getElementById("saleMonths").value=responsetext.curUseMonths;
	        		document.getElementById("ishz").value=checkNull(responsetext.mpackageinfo.ishz);
	        		var lsDmpackageinfo=responsetext.lsDmpackageinfo;
	        		if(lsDmpackageinfo!=null && lsDmpackageinfo.length>0)
	        		{
	        			commoninfodivsaletmk.options.data=$.extend(true, {},{Rows: null,Total: 0});
						commoninfodivsaletmk.loadData(true); 
	        			var saletmkRecord=null;
	        			var saletmkamt=0;
	        			saletmklength=commoninfodivsaletmk.rows.length*1;
	        			for(var i=0;i<lsDmpackageinfo.length;i++)
	        			{
	        				addSaletmk();
	        				saletmkRecord=commoninfodivsaletmk.getRow(saletmklength);
	        				commoninfodivsaletmk.updateRow(saletmkRecord,
	        				{saleproid:lsDmpackageinfo[i].bpackageprono,saleproanme: lsDmpackageinfo[i].bpackageproname,saleprocount: lsDmpackageinfo[i].packageprocount,saleproamt:lsDmpackageinfo[i].packageproamt}); 
	        				saletmklength=saletmklength*1+1;
	        				saletmkamt=saletmkamt*1+lsDmpackageinfo[i].packageproamt*1;
	        			}
	        		}
	        		document.getElementById("saleamt").value=saletmkamt*1
	        	}
        	}
			catch(e)
			{
				alert(e.message);
			}
    	}
    	
    	function addSaletmk()
    	{
    		var row = commoninfodivsaletmk.getSelectedRow();
			commoninfodivsaletmk.addRow({ 
				                saleproid: "",
				                saleproanme: "",
				                saleprocount: "",
				                saleproamt: "",
				                saleremark: ""
				            }, row, false);
    	}
    	
    	function saleTmkRecord()
    	{
    		
    		if(document.getElementById("barcodecardno").value=="")
    		{
    			 $.ligerDialog.warn("请输入条码卡号");
    			 return ;
    		}
    		var totalAmt=document.getElementById("saleamt").value;
    		var curkeepAmt=document.getElementById("curkeepAmt").value;
    		var usecardpayamt=document.getElementById("usecardpayamt").value;
    		
    		if(curkeepAmt*1<usecardpayamt*1)
    		{
    			$.ligerDialog.warn("兑换储值账户金额不能超过原储值余额!");
    			 return ;
    		}
    		var pay1amt=document.getElementById("firstpayamt").value;
    		var pay2amt=document.getElementById("secondpayamt").value;
    		if(pay1amt*1+pay2amt*1+usecardpayamt*1!=totalAmt*1)
    		{
    			$.ligerDialog.warn("支付总额与条码卡总额不一致");
    			 return ;
    		}
    		var sale1amt=document.getElementById("firstsaleamt").value;
    		var sale2amt=document.getElementById("secondsaleamt").value;
    		if(sale1amt*1+sale2amt*1>totalAmt*1)
    		{
    			$.ligerDialog.warn("销售总额不能超过条码卡总额!");
    			 return ;
    		}
    		document.getElementById("firstpaymode_h").value=$("#factfirstpaymode").val();
    		document.getElementById("secondpaymode_h").value=$("#factsecondpaymode").val();
    		var firstsaleempid= $("#factfirstsaleempid").val();
    		var secondsaleempid= $("#factsecondsaleempid").val();
    		
    		if(firstsaleempid=="")
    			firstsaleempid=document.getElementById("firstsaleempid").value;
    		if(secondsaleempid=="");
    			secondsaleempid=document.getElementById("factsecondsaleempid").value;
    			
    		document.getElementById("firstsaleempid_h").value=firstsaleempid;
    		document.getElementById("secondsaleempid_h").value=secondsaleempid;
    		var queryStringTmp=$('#tmkForm').serialize();
			var requestUrl ="cc004/post.action";
			var params=queryStringTmp;
			
			var curjosnparam="";
	        var needReplaceStr="";
	        var strJsonParam_detial="";
	        //------卡号段列表
	        for (var rowid in commoninfodivsaletmk.records)
			{
					var row =commoninfodivsaletmk.records[rowid];
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
			params=params+"&strCurCompId="+strCurCompId;	
			var responseMethod="postMessage";
			sendRequestForParams_p(requestUrl,responseMethod,params );
    }
    
    function postMessage(request)
    {
    		
       	try
		{
	        var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("保存成功!");
	        	 addSaleTmk();
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
    
    
    function retNewDateMessage(request)
    {
    		var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	 $.ligerDialog.success("设置成功!");
	        }
	        else
	        {
	        	$.ligerDialog.error(strMessage);
	        }
      
    }
    
    function validateEmpIdFirst(obj)
    {

    	if(obj.value!="" && obj.value.indexOf('_')==-1)
    	{
    		var exists=0;
    		for(var i=0;i<lsStaffinfo.length;i++)
    		{
    			if(obj.value==lsStaffinfo[i].bstaffno)
    			{
    				 userempmanager1.selectValue(lsStaffinfo[i].bstaffno);
    				  exists=1;
    				  break;
    			}
    			
    		}
    		if(exists==0)
    		{
    			userempmanager1.selectValue('');
    		}
    	
    	}
    }
    function validateEmpIdSecond(obj)
    {

    	if(obj.value!="" && obj.value.indexOf('_')==-1)
    	{
    		var exists=0;
    		for(var i=0;i<lsStaffinfo.length;i++)
    		{
    			if(obj.value==lsStaffinfo[i].bstaffno)
    			{
    				 userempmanager2.selectValue(lsStaffinfo[i].bstaffno);
    				  exists=1;
    				  break;
    			}
    			
    		}
    		if(exists==0)
    		{
    			userempmanager2.selectValue('');
    		}
    	
    	}
    }
    function hotKeyOfSelf(key)
	{
	   if(key==13)//回车
		{
			window.event.keyCode=9; //tab
			window.event.returnValue=true;
   			userempmanager1.selectBox.hide();
   			userempmanager2.selectBox.hide();
		}
	}
	
	function readCurCardInfo()
    {
    		if(document.getElementById("barcodecardno").value=="")
    		{
    			$.ligerDialog.error("请确认输入条码卡号");
    			return;
    		}
        	var CardControl=parent.document.getElementById("CardCtrl");
			CardControl.Init(parent.commtype,parent.prot,parent.password1,parent.password2,parent.password3);
			var cardNo=CardControl.ReadCard();
			if(cardNo!="")
			{
				document.getElementById("usecardno").value=cardNo;
				validateUsecardno(document.getElementById("usecardno"));
	    	}
     } 
     
      function validateUsecardno(obj)
    {
    	if(obj.value=="")
    	{
     		document.getElementById("usecardtype").value="";
     		document.getElementById("usecardtypename").value="";
     		document.getElementById("lbmembername").value="";
     		document.getElementById("lbmemberphone").value="";
     		document.getElementById("curkeepAmt").value="0";
     		document.getElementById("usecardpayamt").value="0";
    	}
    	else
    	{
    		var requestUrl ="cc004/validateUsecardno.action"; 
			var responseMethod="validateUsecardnoMessage";	
			var params="strCardNo="+obj.value;	
			params =params+ "&strCurCompId="+strCurCompId;		
			sendRequestForParams_p(requestUrl,responseMethod,params );
    	}
    }
    
    function validateUsecardnoMessage(request)
   	{
       	try
        {
        	var responsetext = eval("(" + request.responseText + ")");
    		var strmessage=	checkNull(responsetext.strMessage);
	   		if(strmessage!="")
	   		{
	   			$.ligerDialog.error(strmessage);
	   			document.getElementById("usecardno").value="";
	   			document.getElementById("usecardtype").value="";
     			document.getElementById("usecardtypename").value="";
     			document.getElementById("lbmembername").value="";
     			document.getElementById("lbmemberphone").value="";
     			document.getElementById("curkeepAmt").value="0";
     			document.getElementById("usecardpayamt").value="0";
	   		}
	   		else
	   		{
	   			document.getElementById("usecardtype").value=checkNull(responsetext.curCardinfo.cardtype);;
     			document.getElementById("usecardtypename").value=checkNull(responsetext.curCardinfo.cardtypeName);
     			if(checkNull(responsetext.curCardinfo.membername)!="")
	   				document.getElementById("lbmembername").value=checkNull(responsetext.curCardinfo.membername).substring(0,1)+"**";
	   			else	
	   				document.getElementById("lbmembername").value="";
	   			if(checkNull(responsetext.curCardinfo.memberphone)!="" && checkNull(responsetext.curCardinfo.memberphone).length==11)
	   				document.getElementById("lbmemberphone").value=checkNull(responsetext.curCardinfo.memberphone).substring(0,3)+"****"+checkNull(responsetext.curCardinfo.memberphone).substring(7,11);
	   			else
	   				document.getElementById("lbmemberphone").value="";
     			document.getElementById("curkeepAmt").value=checkNull(responsetext.curCardinfo.account2Amt);
	   		}
	   	
	   	}catch(e){alert(e.message);}
    }
    
    function paychange(obj)
    {
    	if(document.getElementById("ishz").value=="1")
    	{
    		if(obj.value!="1" && obj.value!="6")
    		{
    			$.ligerDialog.warn("合作项目套餐只能用现金和银行卡支付");
    			return;
    		}
    	}
    }
        