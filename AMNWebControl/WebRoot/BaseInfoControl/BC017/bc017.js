
   	var compTree = null;//主明细	
   	var commoninfodiv01=null;//员工明细
	var commoninfodiv02=null;//员工明细
	var commoninfodiv03=null;//员工明细
	var commoninfodiv04=null;//员工明细
	var commoninfodiv05=null;//员工明细
	var commoninfodiv06=null;//员工明细
	var commoninfodiv07=null;//员工明细
	var commoninfodiv08=null;//员工明细
	var commoninfodiv09=null;//员工明细
	var commoninfodiv10=null;//员工明细
	var commoninfodiv11=null;//员工明细
	var commoninfodiv12=null;//员工明细
   	var bc017layout=null;
   	var strCurCompId="";
   	var strCurEmpInid="";
   	var strMonth="";
   	var curRecode=null;
   	var chooseData = [{ choose: 1, text: '有业绩' }, { choose: 2, text: '无业绩'}];
    $(function ()
   	{
	   try
	   {
	   		  //布局
            bc017layout= $("#bc017layout").ligerLayout({ allowBottomResize: false, allowLeftResize: false });
            var height = $(".l-layout-center").height();
            $("#smmonth").ligerDateEditor({ labelWidth: 100,format: "yyyy-MM", labelAlign: 'right',width:'100' });
            commoninfodiv01=$("#commoninfodiv01").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv01;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
             commoninfodiv02=$("#commoninfodiv02").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	
                	 curRecode=commoninfodiv02;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv03=$("#commoninfodiv03").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv03;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv04=$("#commoninfodiv04").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv04;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv05=$("#commoninfodiv05").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv05;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv06=$("#commoninfodiv06").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv06;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv07=$("#commoninfodiv07").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv07;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv08=$("#commoninfodiv08").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv08;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv09=$("#commoninfodiv09").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv09;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv10=$("#commoninfodiv10").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv10;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv11=$("#commoninfodiv11").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv11;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            commoninfodiv12=$("#commoninfodiv12").ligerGrid({
                columns: [
                { display: '门店', 			name: 'compname', 				width:54,align: 	'left'} ,
	            { display: '店长', 			name: 'staffname', 				width:70,align: 	'left'},
	            { display: '类型', 			name: 'stafftype', 				width:50,align: 	'left', 
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.stafftype == 1) return '有业绩';
	                    else if (item.stafftype == 2) return '无业绩';
	                }
	             },
	            { name: 'compid', width:1,hide:true} ,
	            { name: 'staffinid', width:1,hide:true} ,
	            { name: 'mmonth', width:1,hide:true}     
                ],  pageSize:30, 
                data:{Rows: null,Total:0},      
                width: '205',
                height:'350',
                enabledEdit: false,  checkbox: false ,rownumbers: false,usePager: false,
                onContextmenu : function (parm,e)
                {
                	 curRecode=commoninfodiv11;
                	 strCurCompId=parm.data.compid;
    				 strCurEmpInid=parm.data.staffinid;
    				 strMonth=parm.data.mmonth;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
            });
            
            
            menu = $.ligerMenu({ width: 120, items:
	            [
	            	{ text: '删除当前店长', click: deleteShoper, icon: 'delete' , img: contextURL+'/common/ligerui/ligerUI/skins/icons/candle.gif'}
	            ]
	            }); 
	            
            $("#createStoreWonerInfo").ligerButton(
	         {
	             text: '设置店长信息', width: 120,
		         click: function ()
		         {
		             createStoreWonerInfo();
		         }
	         });
	         
	         
	   		$("#pageloading").hide(); 
	   		loadCurYearInfo();
   		}catch(e){alert(e.message);}
    });
    
    
    function loadCurYearInfo()
    {
    	var requestUrl ="bc017/loadCurYearShoperInfo.action";
		var responseMethod="loadCurYearInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,"" ); 
    }
    
    function loadCurYearInfoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	if(responsetext.lsShopownerinfo01!=null && responsetext.lsShopownerinfo01.length>0)
	       	{
	       		commoninfodiv01.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo01,Total: responsetext.lsShopownerinfo01.length});
            	commoninfodiv01.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv01();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo02!=null && responsetext.lsShopownerinfo02.length>0)
	       	{
	       		commoninfodiv02.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo02,Total: responsetext.lsShopownerinfo02.length});
            	commoninfodiv02.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv02();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo03!=null && responsetext.lsShopownerinfo03.length>0)
	       	{
	       		commoninfodiv03.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo03,Total: responsetext.lsShopownerinfo03.length});
            	commoninfodiv03.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv03();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo04!=null && responsetext.lsShopownerinfo04.length>0)
	       	{
	       		commoninfodiv04.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo04,Total: responsetext.lsShopownerinfo04.length});
            	commoninfodiv04.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv04();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo05!=null && responsetext.lsShopownerinfo05.length>0)
	       	{
	       		commoninfodiv05.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo05,Total: responsetext.lsShopownerinfo05.length});
            	commoninfodiv05.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv05();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo06!=null && responsetext.lsShopownerinfo06.length>0)
	       	{
	       		commoninfodiv06.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo06,Total: responsetext.lsShopownerinfo06.length});
            	commoninfodiv06.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv06();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo07!=null && responsetext.lsShopownerinfo07.length>0)
	       	{
	       		commoninfodiv07.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo07,Total: responsetext.lsShopownerinfo07.length});
            	commoninfodiv07.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv07();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo08!=null && responsetext.lsShopownerinfo08.length>0)
	       	{
	       		commoninfodiv08.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo08,Total: responsetext.lsShopownerinfo08.length});
            	commoninfodiv08.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv08();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo09!=null && responsetext.lsShopownerinfo09.length>0)
	       	{
	       		commoninfodiv09.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo09,Total: responsetext.lsShopownerinfo09.length});
            	commoninfodiv09.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv09();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo10!=null && responsetext.lsShopownerinfo10.length>0)
	       	{
	       		commoninfodiv10.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo10,Total: responsetext.lsShopownerinfo10.length});
            	commoninfodiv10.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv10();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo11!=null && responsetext.lsShopownerinfo11.length>0)
	       	{
	       		commoninfodiv11.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo11,Total: responsetext.lsShopownerinfo11.length});
            	commoninfodiv11.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv11();
	       	}
	       	
	       	if(responsetext.lsShopownerinfo12!=null && responsetext.lsShopownerinfo12.length>0)
	       	{
	       		commoninfodiv12.options.data=$.extend(true, {},{Rows: responsetext.lsShopownerinfo12,Total: responsetext.lsShopownerinfo12.length});
            	commoninfodiv12.loadData(true);
	       	}
	       	else
	       	{
	       		adddiv12();
	       	}
	       	
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
    
    function  adddiv01()
    {
        	var row = commoninfodiv01.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv01.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv02()
    {
        	var row = commoninfodiv02.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv02.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv03()
    {
        	var row = commoninfodiv03.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv03.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv04()
    {
        	var row = commoninfodiv04.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv04.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv05()
    {
        	var row = commoninfodiv05.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv05.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv06()
    {
        	var row = commoninfodiv06.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv06.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv07()
    {
        	var row = commoninfodiv07.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv07.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv08()
    {
        	var row = commoninfodiv08.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv08.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv09()
    {
        	var row = commoninfodiv09.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv09.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv10()
    {
        	var row = commoninfodiv10.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv10.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv11()
    {
        	var row = commoninfodiv11.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv11.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
     }
     function  adddiv12()
    {
        	var row = commoninfodiv12.getSelectedRow();
				     //参数1:rowdata(非必填)
				    //参数2:插入的位置 Row Data 
				    //参数3:之前或者之后(非必填)
			commoninfodiv12.addRow({ 
				                compid:"",
				                staffinid:""
				            }, row, false);
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
		else if( key == 83 &&  event.altKey)
		{
			editCurRecord();
		}
		else if(key==115)//F4
		{
				
				 deletedetialRecord();
		}
	}
    
    
    
    
    function validateShandcompid(obj)
   	{
   		if(obj.value=="")
   		{
   			document.getElementById("compname").value="";
   		}
   		else
   		{
   			var requestUrl ="bc017/validateShandcompid.action";
       	 	var params="strCurCompId="+obj.value;
			var responseMethod="validateShandcompidMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 	 
   		}
   	}
   	
   	function validateShandcompidMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("compname").value=responsetext.strCurCompName;
	       	}
	       	else
	       	{
	       		
	       		document.getElementById("compid").value="";
	       		document.getElementById("compname").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function validateShandStoreWoner(obj)
   	{
   		if(document.getElementById("compid").value=="")
   		{
   			
   			document.getElementById("compid").select();
   			obj.value="";
   			document.getElementById("staffname").value="";
   			document.getElementById("staffinid").value="";
   			return;
   		}
   		var requestUrl ="bc017/validateShopWoner.action";
       	var params="strCurCompId="+document.getElementById("compid").value;
       	params=params+"&strCurEmpId="+obj.value;
		var responseMethod="validateShopWonerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
    
    
    function validateShopWonerMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		document.getElementById("staffname").value=responsetext.strCurEmpName;
	       		document.getElementById("staffinid").value=responsetext.strCurEmpInid;
	       	}
	       	else
	       	{
	       		
	       		document.getElementById("staffid").value="";
	       		document.getElementById("staffname").value="";
	       		document.getElementById("staffinid").value="";
	       		$.ligerDialog.warn(strMessage);
	       	}
        }
		catch(e)
		{
			alert(e.message);
		}
   	}
   	
   	function createStoreWonerInfo()
   	{
   		if(document.getElementById("smmonth").value=="")
   		{
   			$.ligerDialog.error("请确认设置月份");
   			return ;
   		}
   		
   		if(document.getElementById("compid").value=="")
   		{
   			$.ligerDialog.error("请确认设置门店");
   			return ;
   		}
   		
   		if(document.getElementById("staffid").value=="")
   		{
   			$.ligerDialog.error("请确认设置店长");
   			return ;
   		}
   		if(document.getElementById("stafftype").value=="")
   		{
   			$.ligerDialog.error("请确认设置店长业务类型");
   			return ;
   		}
	    $.ligerDialog.confirm('确认 店长类型', function (result)
		{
		     	if( result==true)
            	{
            		var requestUrl ="bc017/createStoreWonerInfo.action";
       				var params="strCurCompId="+document.getElementById("compid").value;
       				params=params+"&strCurEmpId="+document.getElementById("staffid").value;
       				params=params+"&strCurEmpName="+document.getElementById("staffname").value;
       				params=params+"&strCurEmpInid="+document.getElementById("staffinid").value;
       				params=params+"&strMonth="+document.getElementById("smmonth").value;
       				params=params+"&nstafftype="+document.getElementById("stafftype").value;
					var responseMethod="createStoreWonerInfoMessage";
					sendRequestForParams_p(requestUrl,responseMethod,params );
            	}
        });
   	}
   	
   	function createStoreWonerInfoMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	   
	       		var currecord=null;  
	       		var addMedth="";  		 
	       		if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="01")
	       		{
	       			currecord=commoninfodiv01;
	       			addMedth="adddiv01";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="02")
	       		{
	       			currecord=commoninfodiv02;
	       			addMedth="adddiv02";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="03")
	       		{
	       			currecord=commoninfodiv03;
	       			addMedth="adddiv03";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="04")
	       		{
	       			currecord=commoninfodiv04;
	       			addMedth="adddiv04";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="05")
	       		{
	       			currecord=commoninfodiv05;
	       			addMedth="adddiv05";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="06")
	       		{
	       			currecord=commoninfodiv06;
	       			addMedth="adddiv06";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="07")
	       		{
	       			currecord=commoninfodiv07;
	       			addMedth="adddiv07";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="08")
	       		{
	       			currecord=commoninfodiv08;
	       			addMedth="adddiv08";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="09")
	       		{
	       			currecord=commoninfodiv09;
	       			addMedth="adddiv09";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="10")
	       		{
	       			currecord=commoninfodiv10;
	       			addMedth="adddiv10";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="11")
	       		{
	       			currecord=commoninfodiv11;
	       			addMedth="adddiv11";
	       		}
	       		else if(document.getElementById("smmonth").value.replace("-","").substring(4,6)=="12")
	       		{
	       			currecord=commoninfodiv12;
	       			addMedth="adddiv12";
	       		}
	       		var gridlen=currecord.rows.length*1;
				if(gridlen==0)
				{
					eval(addMedth+"()");
					gridlen=gridlen+1;
				} 
				if(checkNull(currecord.getRow(0).compid)!="")
				{
					eval(addMedth+"()");
					gridlen=gridlen+1;
				}
				var curDetialRecord=currecord.getRow(gridlen-1);
				currecord.updateRow(curDetialRecord,{      compid: document.getElementById("compid").value
															,compname: document.getElementById("compname").value
															  ,staffname :document.getElementById("staffname").value
															   ,stafftype :document.getElementById("stafftype").value
															   ,staffinid:document.getElementById("staffinid").value
															   ,mmonth:document.getElementById("smmonth").value.replace("-","")
															}); 
				document.getElementById("compid").value="";
				document.getElementById("compname").value=""; 
       			document.getElementById("staffid").value="";
       			document.getElementById("staffname").value="";
       			document.getElementById("staffinid").value="";
       			document.getElementById("smmonth").value="";
       			document.getElementById("stafftype").value="";
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
   	
   	function deleteShoper()
   	{
   		var requestUrl ="bc017/deleteShopWoner.action";
       	var params="strCurCompId="+strCurCompId;
       	params=params+"&strCurEmpInid="+strCurEmpInid;
       	params=params+"&strMonth="+strMonth;
		var responseMethod="deleteShopWonerMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params );
   	}
   	
   	function deleteShopWonerMessage(request)
    {
	 	try
		{
	       	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		curRecode.deleteSelectedRow();
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