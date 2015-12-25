
   	var sc002layout=null;
   	var commoninfodivAllDate=null;
   	var commoninfodivCashDate=null;
	var commoninfodivBillDate=null;
	 var showDialogmanager=null;
	var threePayment=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc002layout= $("#sc002layout").ligerLayout({rightWidth: 320, allowBottomResize: false, allowLeftResize: false,isRightCollapse:true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc002layout.centerWidth;
       	    var rightWidth = sc002layout.rightWidth
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           
            commoninfodivAllDate=$("#commoninfodivAllDate").ligerGrid({
                columns: [
                { display: '款项', 				name: 'tradeTitle', 		width:350	,align: 'left'},
                { display: '金额', 				name: 'tradeAmt',  			width:250	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: 640,
                height:height-100,
                enabledEdit: true,  checkbox: false,rownumbers: false,usePager:false
               
            });
            
            commoninfodivCashDate=$("#commoninfodivCashDate").ligerGrid({
                columns: [
                { display: '日期', 				name: 'tradedate', 			width:80	,align: 'left' },
                { display: '当日现金', 			name: 'totalcashamt',  		width:60	,align: 'right'},
	            { display: '现金产品', 			name: 'staffcashamt', 		width:60	,align: 'right'},
	            { display: '现金合作项目',   		name: 'hezuocashamt',  		width:80	,align: 'right'}
	            ],  pageSize:20, 
                data: null,      
                width: 310,
                height: height-70,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager:false
               
            });
            /*注释中间明细表格
           commoninfodivBillDate=$("#commoninfodivBillDate").ligerGrid({
                columns: [
                { display: '编号', 				name: 'strCompId',  		width:50	,align: 'left'},
         		{ display: '名称', 				name: 'strCompName',  		width:130	,align: 'left'},
	            { display: '现金客单总额', 			name: 'cashBillAmt', width:90,align: 'right' },
		        { display: '现金客单数', 			name: 'cashBillCount', width:70,align: 'center' },
		        { display: '现金客单价', 			name: 'cashBillPrice', width:70,align: 'right' },
	         	{ display: '销卡总额', 			name: 'cardBillAmt', width:70,align: 'right' },
		        { display: '销卡客单数', 			name: 'cardBillCount', width:70,align: 'center' },
		        { display: '销卡客单价', 			name: 'cardBillPrice', width:70,align: 'right' },
	           	{ display: '总客单总额', 			name: 'totalBillAmt', width:70,align: 'right' },
		        { display: '总客单数', 			name: 'totalBillCount', width:70,align: 'center' },
		        { display: '总客单价', 			name: 'totalBillPrice', width:70,align: 'right' },
	           	{ display: '美容大项客单数', 		name: 'mrPrjBillCount',  			width:90	,align: 'center'},
	           	{ display: '美发大项客单数', 		name: 'mfPrjBillCount',  			width:90	,align: 'center'}
	            ],  pageSize:24, 
                data: null,      
                width: 1070,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: false,usePager: true
               
            });*/
            threePayment=$("#threePayment").ligerGrid({
                columns: [
                { display: '支付方式', 				name: 'paymethod', 		width:160	,align: 'left'},
                { display: '金额', 				name: 'payamt',  			width:140	,align: 'left'}
	            ],  pageSize:20, 
                data: null,      
                width: 350,
                height:height-100,
                enabledEdit: false,  checkbox: false,rownumbers: false,usePager:false
            });
            document.getElementById("showTable").width=centerWidth*1-rightWidth*1;
            $("#searchButton").ligerButton(
	         {
	             text: '查询日记账', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	         
	         $("#printButton").ligerButton(
	         {
	             text: '打印日记账', width: 120,
		         click: function ()
		         {
		             viewTicketReport();
		         }
	         });
	         
	      
            
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;

            $("#pageloading").hide();
            addAllDate(); 
            addCashDate();
            /*注释中间明细表格
            addBillDate();*/
   		}catch(e){alert(e.message);}
    });
    
    function  addAllDate()
    {
        	var row = commoninfodivAllDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivAllDate.addRow({ 
				                tradeTitle: "",
				                tradeAmt: ""
				            }, row, false);
    }
    
    function  addCashDate()
    {
        	var row = commoninfodivCashDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivCashDate.addRow({ 
				                compId: "",
				                compName: ""
				            }, row, false);
    }
 
    function  addBillDate()
    {
        	var row = commoninfodivBillDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivBillDate.addRow({ 
				                compName: ""
				            }, row, false);
    }
    
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	var strFromDate=document.getElementById("strFromDate").value;
    	var params = "strCurCompId="+strCurCompId;				
    	params=params+"&strFromDate="+strFromDate;	
    	var requestUrl ="sc002/loadDataSet.action"; 
		var responseMethod="loadDataSetMessage";	
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function loadDataSetMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			document.getElementById("dateFromReport").innerHTML=checkNull(responsetext.strFromDate);
 			document.getElementById("dateToReport").innerHTML=checkNull(responsetext.strFromDate);
 			document.getElementById("printDate").innerHTML=checkNull(responsetext.strPrintDate);
			if(responsetext.lsTradedailydata!=null && responsetext.lsTradedailydata.length>0)
           	{
            		commoninfodivAllDate.options.data=$.extend(true, {},{Rows: responsetext.lsTradedailydata,Total: responsetext.lsTradedailydata.length});
            		commoninfodivAllDate.loadData(true);
            		for(var i=0;i<responsetext.lsTradedailydata.length*1;i++)
            		{
            			loadPageValue( responsetext.lsTradedailydata[i].valueflag, responsetext.lsTradedailydata[i].tradeAmt)
            		}
            }
			else
			{
				 	$.ligerDialog.warn("没有查到营业信息!");
		   			commoninfodivAllDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivAllDate.loadData(true);
	            	addAllDate(); 
			}
			if(responsetext.lsTradedatedata!=null && responsetext.lsTradedatedata.length>0)
           	{
            		commoninfodivCashDate.options.data=$.extend(true, {},{Rows: responsetext.lsTradedatedata,Total: responsetext.lsTradedatedata.length});
            		commoninfodivCashDate.loadData(true);
            		
            }
			else
			{
				 	commoninfodivCashDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivCashDate.loadData(true);
	            	addCashDate(); 
			}
			if(responsetext.lsThreePayment!=null && responsetext.lsThreePayment.length>0)
           	{
				threePayment.options.data=$.extend(true, {},{Rows: responsetext.lsThreePayment,Total: 0});
				threePayment.loadData(true);
            		
            }
			else
			{
				threePayment.options.data=$.extend(true, {},{Rows: null,Total: 0});
				threePayment.loadData(true);
			}
			/*注释中间明细表格
				if(responsetext.lsTradeBillCount!=null && responsetext.lsTradeBillCount.length>0)
           	{
            		commoninfodivBillDate.options.data=$.extend(true, {},{Rows: responsetext.lsTradeBillCount,Total: responsetext.lsTradeBillCount.length});
            		commoninfodivBillDate.loadData(true);
            }
			else
			{
				 	commoninfodivBillDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivBillDate.loadData(true);
	            	addBillDate(); 
			}*/
			showDialogmanager.close();
		}
		
		function loadPageValue( flag, value)
		{
			 if(flag==2)
 				document.getElementById("factTotal").innerHTML=checkNull(value);
 			else if(flag==3)
				document.getElementById("staffProd").innerHTML=checkNull(value);
			else if(flag==4)
 				document.getElementById("cashService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==5)
 				document.getElementById("cashProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==10)
 				document.getElementById("cashHzPrj").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==7)
 				document.getElementById("cashCardTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==11)
 				document.getElementById("cashTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==12)
 				document.getElementById("creditService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==13)
 				document.getElementById("creditProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==15)
 				document.getElementById("creditTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==19)
 				document.getElementById("creditTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==18)
 				document.getElementById("bankHzPrj").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==20)
 				document.getElementById("checkService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==21)
 				document.getElementById("checkProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==23)
 				document.getElementById("checkTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==27)
 				document.getElementById("checkTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==28)
 				document.getElementById("zftService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==29)
 				document.getElementById("zftProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==31)
 				document.getElementById("zftTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==35)
 				document.getElementById("zftTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==36)
 				document.getElementById("okcService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==37)
 				document.getElementById("okcProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==38)
 				document.getElementById("okcTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==40)
 				document.getElementById("okcTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==41)
 				document.getElementById("tgkService").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==42)
 				document.getElementById("tgkProd").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==43)
 				document.getElementById("tgkTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==44)
 				document.getElementById("tgkTotal").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==45)
 				document.getElementById("totalCardTrans").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==46)
 				document.getElementById("cardSalesServices").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==49)
 				document.getElementById("cardSalesprod").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==48)
 				document.getElementById("acquisitionCardServices").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==55)
 				document.getElementById("manageSigning").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==50)
 				document.getElementById("integralPay").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==47)
 				document.getElementById("proPay").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==9)
 				document.getElementById("cashChange").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==17)
 				document.getElementById("creditChange").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==51)
 				document.getElementById("cashDyPay").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==52)
 				document.getElementById("prjDyPay").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==53)
 				document.getElementById("tmkPay").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==6)
 				document.getElementById("cashBackCardView").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==14)
 				document.getElementById("creditBackCardView").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==22)
 				document.getElementById("checkBackCardView").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==30)
 				document.getElementById("zftBackCardView").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==8)
 				document.getElementById("strCashByTMCard").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==16)
 				document.getElementById("strBankByTMCard").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==24)
 				document.getElementById("strcheckByTMCard").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==32)
 				document.getElementById("strFingerByTMCard").innerHTML=maskAmt(checkNull(value),2);
 			else if(flag==39)
 				document.getElementById("strOKPqypwyByTMCard").innerHTML=maskAmt(checkNull(value),2);
 				
		}
		
		 function viewTicketReport()
		{
		  try
		  {
		  	Stand_CheckPrintControl();//检查是否有打印控件
			Stand_InitPrint("日记账模块_小票打印作业");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Alignment",2);
			Stand_SetPrintStyle("HOrient",2);
			Stand_SetPrintStyle("Bold",1);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,31,15,351,25,"阿玛尼护肤造型"+"("+checkNull(parent.document.getElementById("strCompName").value)+")");
			Stand_SetPrintStyle("FontSize",9);
			Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,55,15,351,25,"一切为你 想你所想");
			Stand_SetPrintStyle("FontSize",11);
			Stand_SetPrintStyle("Bold",0);
		    var printContent = document.getElementById("printContent").innerHTML;
		    Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,110,0,230,2000,printContent);
			Stand_Print();
		  }
		  catch(e){alert(e.message);}
		}
