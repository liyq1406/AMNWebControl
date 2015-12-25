
   	var sc008layout=null;
   	var commoninfodivTradeDate=null;
	var showDialogmanager=null;
   	$(function ()
   	{
	   try
	   {
	   		  //布局
            sc008layout= $("#sc008layout").ligerLayout({ allowBottomResize: true, allowLeftResize: true });
           	var height = $(".l-layout-center").height();
           	var centerWidth = sc008layout.centerWidth
       
           	$("#strFromDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
            $("#strToDate").ligerDateEditor({ labelWidth: 100, labelAlign: 'right',width:'120' });
           //--------销售条码卡
            commoninfodivTradeDate=$("#commoninfodivTradeDate").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compid', 			width:60	,align: 'left' ,frozen:true},
                { display: '名称', 			name: 'compname', 			width:110	,align: 'left' ,frozen:true,isSort: true},
                { display: '业绩部门', 		columns:[
                         {display:'美容虚业绩',name:'beautyperf',width:100,align:'right'},
                         {display:'美发虚业绩',name:'hairperf',width:100,align:'right'},
                         {display:'总虚业绩',name:'sumperf',width:100,align:'right'},
                         {display:'耗卡率',name:'hklvl',width:100,align:'right'},
                         {display:'大卡成交',name:'dkccount',width:100,align:'right'},
                         {display:'美容实业绩',name:'beautyrealperf',width:100,align:'right'},
                         {display:'美发实业绩',name:'hairrealperf',width:100,align:'right'},
                         {display:'总实业绩',name:'sumrealperf',width:100,align:'right'}
                ]},
                { display: '人员组成', 		columns: [
                         {display:'美容',columns:[
                                   {display:'A类',name:'beautyA',width:100,align:'right'},
                                   {display:'B类',name:'beautyB',width:100,align:'right'},
                                   {display:'C类',name:'beautyC',width:100,align:'right'}
                                                ]},
                          {display:'烫染',columns:[
                                   {display:'一级/二级',name:'trAB',width:100,align:'right'},
                                   {display:'三级/四级',name:'beautyCD',width:100,align:'right'}
                                                                                    ]},
                          {display:'美发',columns:[
                                   {display:'总监/首席',name:'hairZS',width:100,align:'right'},
                                   {display:'发型师',name:'hairfxs',width:100,align:'right'}
                               ]},
                          {display:'管理',columns:[
                                   {display:'美容经理/顾问',name:'beautyManger',width:100,align:'right'},
                                   {display:'烫染督导',name:'trdd',width:100,align:'right'}
                          ]},
                          {display:'大堂',columns:[
                                                 {display:'A类',name:'dtManager',width:100,align:'right'},
                                                 {display:'B/C/实习',name:'dtBC',width:100,align:'right'}
                                        ]}
                ]},
                { display: '工资部分',columns:[
                           {display:'美容部工资',name:'beautyWage',width:100,align:'right'},
                           {display:'美发部工资',name:'hairWage',width:100,align:'right'},
                           {display:'烫染部工资',name:'trWage',width:100,align:'right'},
                           {display:'管理层工资',name:'manageWage',width:100,align:'right'},
                           {display:'非业务人员',name:'noBusiness',width:100,align:'right'},
                           {display:'合计',name:'sumWage',width:100,align:'right'}
                           
                           
                                           ]},
                {display: '客单/项目',columns:[
						{display:'总客单',name:'sumClient',width:100,align:'right'},
						{display:'美容大项客单',name:'beautyBigClient',width:100,align:'right'},
						{display:'烫染项目数',name:'trItemCount',width:100,align:'right'},
						{display:'护理项目数',name:'hcItemCount',width:100,align:'right'}
                ]},
                {display: '疗程成交',columns:[
						{display:'美容疗程',name:'beautyPro',width:100,align:'right'},
						{display:'美发疗程',name:'hairPro',width:100,align:'right'},
						{display:'美容年卡',name:'beautyYearCard',width:100,align:'right'},
						{display:'美发年卡',name:'hairYearCard',width:100,align:'right'}
                ]},
                {display: '产品成交',columns:[
						{display:'美容产品',name:'beautyGoods',width:100,align:'right'},
						{display:'美发产品',name:'hairGoods',width:100,align:'right'}
                                          ]}
	            ],  pageSize:20, 
                data: null,      
                width: centerWidth,
                height:height-100,
                enabledEdit: true,  checkbox:false,rownumbers: true,usePager:false
               
            });
            document.getElementById("showTable").width=centerWidth;
            $("#searchButton").ligerButton(
	         {
	             text: '查询日报表', width: 120,
		         click: function ()
		         {
		             loadDataSet();
		         }
	         });
	        var today = new Date();
			var intYear=today.getYear();
			var intMonth=today.getMonth()+1;
			var intDay=today.getDate();
			var today = intYear.toString()+"-"+fullStr(intMonth.toString())+"-"+fullStr(intDay.toString());
			document.getElementById("strFromDate").value=today;
			document.getElementById("strToDate").value=today;
            $("#pageloading").hide(); 
            addtrade();
   		}catch(e){alert(e.message);}
    });
    
    function  addtrade()
        {
        	var row = commoninfodivTradeDate.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodivTradeDate.addRow({ 
				                shopId: "",
				                shopName: "",
				                dateReport: ""
				             
				            }, row, false);
        }
    //登记储值卡
    function loadDataSet()
    {	
   
    	var strCurCompId=getCurOrgFromSearchBar();

    	//var strSearchType=document.getElementById("strSearchType").value;
    	var strFromDate=document.getElementById("strFromDate").value;
    	var strToDate=document.getElementById("strToDate").value;
    	
    	var params = "strCompId="+strCurCompId;	
    	params=params+"&strFromDate="+strFromDate;	
    	params=params+"&strToDate="+strToDate;	
     	var requestUrl ="bs001/query.action"; 
		var responseMethod="queryMessage";		
		 showDialogmanager = $.ligerDialog.waitting('正在查询中,请稍候...');	
		sendRequestForParams_p(requestUrl,responseMethod,params );
    
    }
    
    function queryMessage(request)
		{
			var responsetext = eval("(" + request.responseText + ")");
			if(responsetext.lsBeans!=null && responsetext.lsBeans.length>0)
           	{
            		commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: responsetext.lsBeans,Total: responsetext.lsBeans.length});
            		commoninfodivTradeDate.loadData(true);
            }
			else
			{
				 	$.ligerDialog.warn("没有查到营业信息!");
		   			commoninfodivTradeDate.options.data=$.extend(true, {},{Rows: null,Total: 0});
	            	commoninfodivTradeDate.loadData(true);
	            	addtrade(); 
			}
			showDialogmanager.close();
		}
