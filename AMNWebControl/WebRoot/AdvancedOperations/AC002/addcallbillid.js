	var showDialogmanager=null;
	var  curStaffInfos=null;
	var  receiptses=null;  
	var  usercallinfo=null;
	var  Simplequery; //简单查询
    var  callhistory;// 来电历史
    var  refershow;//咨询
    var  peiiershow;//投诉
    var  cardreturnshow;//退卡
    var  orders;//预约
    var  navtab = null;
    var  rowis="";
    var  detailTab=null;
    var  report=null;//挂失
    var  orderchooseData = [{ choose: '0', text: '总部登记' }, { choose: '1', text: '门店确认'}, { choose: '2', text: '总部受理'}, { choose: '3', text: '已结案'}];
    $(function () {  
    		try{
    			report=$("#report").ligerGrid({
                columns: [
                { display: '卡号', name: 'cardno',width:130},
                { display: '姓名', name: 'membername',width:100},
                { display: '手机号', name: 'membermphone',width:110},
                { display: '卡类型', name: 'cardtype',width:90},
                { display:'卡余额',name:'cardaccount',width:100},
                { display: '状态',    name: 'cardstate',width:100,
                	render: function (item)
	              	{
	                  if (item.cardstate == 4) return '正常使用中';
	                  else if (item.cardstate == 5) return '解挂';
	                  else if (item.cardstate == 9) return '已挂失';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:500,
                enabledEdit: true,
                rownumbers:true,usePager:false
           });
    			
    		orders=$("#orders").ligerGrid({
                columns: [
                { display: '预约门店', name: 'orderconply',width:80},
                { display: '预约项目',    name: 'orderproject',width:150},
                { display: '预约时间', name: 'ordertime',width:130},
                { display: '备注', name: 'orderdetail',width:130},
                { display: '处理人',   name: 'calluserid',width:70},
                { display: '状态',    name: 'orderstate',width:80,
                	render: function (item)
	              	{
	                  if (item.orderstate == 0) return '总部登记';
	                  else if (item.orderstate == 1) return '处理中';
	                  else if (item.orderstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:200,
                enabledEdit: true,
                rownumbers:true,usePager:false,
                 toolbar: { items: [
                 { text: '预约历史',img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'}
               ]
             }
           });

    	  cardreturnshow=$("#cardreturnshow").ligerGrid({
                columns: [
                { display: '退卡内容', name: 'cardreturningcontent',width:100},
                { display: '备注',    name: 'cardreturningdetails',width:200},
                { display: '退卡时间', name: 'cardreturningtime',width:130},
                { display: '处理人',   name: 'calluserid',width:90},
                { display: '状态',    name: 'cardreturningstate',width:80,
                	render: function (item)
	              	{
	                  if (item.cardreturningstate == 0) return '总部登记';
	                  else if (item.cardreturningstate == 1) return '处理中';
	                  else if (item.cardreturningstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:200,
                enabledEdit: true,
                rownumbers:true,usePager:false,
                toolbar: { items: 
                [
                 { text: '投诉历史',img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'}
                ]
              }
           });
 		 peiiershow=$("#peiiershow").ligerGrid({
                columns: [
                { display: '投诉内容', name: 'peiiercontent',width:130},
                { display: '投诉其他', name: 'peiierdetails',width:200},
                { display: '投诉时间', name: 'peiiertime',width:150},
                { display: '处理人', name: 'calluserid',width:90},
                { display: '状态',    name: 'peiierstate',width:70,
                	render: function (item)
	              	{
	                  if (item.peiierstate == 0) return '总部登记';
	                  else if (item.peiierstate == 1) return '处理中';
	                  else if (item.peiierstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:200,
                enabledEdit: true,
                rownumbers:true,usePager:false,
                toolbar: { items: [
                 { text: '投诉历史',img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'}]
						}
           });
    	 	 
          refershow=$("#refershow").ligerGrid({
                columns: [
                { display: '咨询门店', name: 'refercomply',width:110},
                { display: '咨询项目', name: 'refercards',width:140},
                { display: '咨询会员卡', name: 'referproject',width:100},
                { display: '咨询时间', name: 'refertime',width:150},
                { display: '处理人', name: 'calluserid',width:70},
                { display: '状态',    name: 'feferstate',width:70,
                	render: function (item)
	              	{
	                  if (item.feferstate == 0) return '总部登记';
	                  else if (item.feferstate == 1) return '处理中';
	                  else if (item.feferstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:200,
                enabledEdit: true,
                rownumbers:true,usePager:false,
                toolbar: { items: [
                 { text: '咨询历史',img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'} 
               ]
             }
           });
            $("#tab").ligerTab();
              detailTab=$("#tab").ligerGetTabManager();
            $("#gua").ligerToolBar({ items: [
            	{ text:'会员信息',click:selectreport,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search2.gif'},
            	{ text:'挂失' ,click:cardon,img: contextURL+'/common/ligerui/ligerUI/skins/icons/lock.gif'},
            	{ text:'解挂' ,click:cardons,img: contextURL+'/common/ligerui/ligerUI/skins/icons/config.gif'}]});
            $("#topburmee").ligerToolBar({ items: [
            	{ text:'客服信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'}]});
            $("#topburcall").ligerToolBar({ items: [
            	{ text:'来电信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/customers.gif'}]});
			$("#topburr").ligerToolBar({ items: [
            	{ text:'暂存单据' ,click:addpeiiera,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'},
            	{ text:'单据结案' ,click:addpeiieras,img: contextURL+'/common/ligerui/ligerUI/skins/icons/calendar.gif'}]});
			$("#cardbur").ligerToolBar({ items: [
            	{ text:'暂存单据' ,click:addcardreturn,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'},
            	{ text:'单据结案' ,click:addcardreturns,img: contextURL+'/common/ligerui/ligerUI/skins/icons/calendar.gif'}]});
	     	$("#topbur").ligerToolBar({ items: [
            	{ text:'暂存单据' ,click:addrefer,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'},
            	{ text:'单据结案' ,click:addrefers,img: contextURL+'/common/ligerui/ligerUI/skins/icons/calendar.gif'}]});
	     	$("#topburrs").ligerToolBar({ items: [
            	{ text:'保存单据' ,click:addorder,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'}]});
            $("#ordertime").ligerDateEditor({ showTime:true,width:200,format: "yyyy-MM-dd hh:mm:ss",labelWidth: 80, labelAlign: 'right' });
	 		if(document.getElementById("stratype").value==2)
	 		{
	 			detailTab.selectTabItem("tabitem1");
	 		}
	 		 }catch(e){alert(e.message);}
	 		 selectrefer();
	 		 selectcard();
	 		 selecpeiier();
	 		 selectorder();
        });
     /**
     * 投诉结案
     * @param {Object} id
     */
     function peiieron()
     {
    	    var rows = peiiershow.getCheckedRows();
            var str = "";
	            $(rows).each(function ()
	            {
	                str= this.peiierid;
	            });
	     	var params="peiierid="+str;
		    var requestUrl =contextURL+"/ac002/peiieron.action?"+params;
			$.ajax({
				    type: 'POST',
				    url: requestUrl,
				    data: null,
				    dataType: 'json',
				    success: function (data)
				    { 
			    	alert(data.strMessage);
				    }
			     });
	 }
     /**
     *投诉结案
     * @param {Object} id
     */
     function referons(){
    	    var rows = refershow.getCheckedRows();
            var str = "";
	     	var params="referid="+str;
		    var requestUrl =contextURL+"/ac002/referon.action?"+params;
		    $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
			    		   			alert(data.strMessage);
				                }
			    	   });
		$(rows).each(function () {str= this.referid;});
       }
     /**
     * 挂失
     * @param {Object} id
     */
     function cardon(){
    	    var rows = report.getCheckedRows();
            var str = "";
            $(rows).each(function (){str= this.membernotocard;});
     	    var  params="membernotocard="+str;
	        var  requestUrl =contextURL+"/ac002/cardon.action?"+params;
		    $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) 
				                { 
			    		   			alert(data.strMessage);
				                }
			    	});
			 }
      /**
     * 解挂
     * @param {Object} id
     */
     function cardons(){
    	    var rows = report.getCheckedRows();
            var str = "";
            $(rows).each(function (){str= this.membernotocard;});
     	    var  params="membernotocard="+str;
	        var  requestUrl =contextURL+"/ac002/cardons.action?"+params;
		    $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data)
				                { 
			    		   			alert(data.strMessage);
				                }
			    	});
 		}
     /**
     * 退卡结案
     * @param {Object} id
     */
     function referon(){
    	    var rows = cardreturnshow.getCheckedRows();
            var str = "";
            $(rows).each(function (){str= this.cardreturningid;});
     	    var  params="cardreturningid="+str;
	        var requestUrl =contextURL+"/ac002/closurecard.action?"+params;
			$.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) 
				                { 
			    		   			alert(data.strMessage);
				                }
			    	   });
 		}
      /**
     * 预约结案
     * @param {Object} id
     */
     function orderson(){
    	    var rows = orders.getCheckedRows();
            var str = "";
            $(rows).each(function (){str= this.ordersid;});
     	    var  params="ordersid="+str;
	        var  requestUrl =contextURL+"/ac002/orderson.action?"+params;
			$.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
			    		   			alert(data.strMessage);
				                }
			    	   });
 			}
	/**
	 * 挂失信息
	 */
     function selectreport(){ 
	    	try{
			    var     params="cardno="+encodeURI(document.getElementById("cardno").value);
			   		    params=params+"&membername="+encodeURI(document.getElementById("membername").value);
			      		params=params+"&membermphone="+encodeURI(document.getElementById("membermphone").value);
			      		if(checkNull($("#cardno").val())==""
						 && checkNull($("#membername").val())==""
						 && checkNull(document.getElementById("membermphone").value)==""
						 )
						 {
						 	$.ligerDialog.warn("请确认查询信息!");
						 	return ;
						 }
			    showDialogmanager =  $.ligerDialog.waitting('正在查询中,请稍候...');
			    var     requestUrl =contextURL+"/ac002/selectcar.action?"+params;
			    	    $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
			    	    	    showDialogmanager.close();
								if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
								{
									var btndate={Rows:data.lsCallsWatingBean,Total: data.lsCallsWatingBean.length};	
									report.options.data=$.extend(true, {},btndate);
            						report.loadData(true);	
								}
								else
								{
									 $.ligerDialog.warn("请确认查询条件！");
									report.options.data=$.extend(true, {},{Rows:null,Total:0});
            						report.loadData(true);
								}
				                }
				           	 	});
    	  	 }catch(e){alert(e.message);
    	   }
	    }
    /**
     * 退卡结案
     * @param {Object} id
     */
     function closurecard(){
    	    var rows = cardreturnshow.getCheckedRows();
            var str = "";
            $(rows).each(function (){str= this.cardreturningid;});
     	    var  params="cardreturningid="+str;
	        var  requestUrl =contextURL+"/ac002/closurecard.action?"+params;
			 $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
			    		   			alert(data.strMessage);
				                }
			    	   });
		 }
    //添加退卡信息
    function addcardreturn(){ 
	 try
		     {
		    	var str=document.getElementsByName("cardcontent");
		     	var cardcontent=str.length;
			    var checkstr="";
				for (i=0;i<cardcontent;i++)
					{
						  if(str[i].checked == true)
						{
							   checkstr+=str[i].value+" ";
						 }
				   }
				     var    requestUrl =contextURL+"/ac002/addcardreturn.action?";
				   	 var    params="cardreturningcontent="+encodeURI(checkstr);
						    params=params+"&cardreturningdetails="+encodeURI(document.getElementById("carddetails").value);
						    params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
				 	   		 $.ajax({
			                type: 'POST',
			                url: requestUrl+params,
			                data: null,
			                dataType: 'json',
			                success: function (data) { 
			                	alert(data.strMessage);
			                }
			           	 	});
            				document.getElementById("carddetails").disabled=true;
            				document.getElementById("card1").disabled=true;
            				document.getElementById("card2").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
    
     function addcardreturns(){ 
		 try
		     {
		    	var str=document.getElementsByName("cardcontent");
		     	var cardcontent=str.length;
			    var checkstr="";
				for (i=0;i<cardcontent;i++)
					{
						  if(str[i].checked == true)
						{
							   checkstr+=str[i].value+" ";
						 }
				   }
				     var    requestUrl =contextURL+"/ac002/addcardreturns.action?";
				   	 var    params="cardreturningcontent="+encodeURI(checkstr);
						    params=params+"&cardreturningdetails="+encodeURI(document.getElementById("carddetails").value);
						    params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
				 	   		 $.ajax({
			                type: 'POST',
			                url: requestUrl+params,
			                data: null,
			                dataType: 'json',
			                success: function (data) { 
			                	alert(data.strMessage);
			                }
			           	 	});
            				document.getElementById("carddetails").disabled=true;
            				document.getElementById("card1").disabled=true;
            				document.getElementById("card2").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
 //添加当日单据空白行
	  function  addreceiptsrow()
	  {
		  	var row=receiptses.getSelectedRow();
		 	receiptses.addRow({ 
					           call_no: "",
					           call_state:""
					           }, row, false);
	  	}
	  
 	//添加单据
 	function addorder(){
				 try
		         {
		         	 var    requestUrl =contextURL+"/ac002/addorders.action?";
				   	 var    params="orderconply="+getCurOrgFromSearchBar();
				   	 		if(document.getElementById("ordertime").value=="")
				   	 		{
				   	 			$.ligerDialog.warn("请确认预约时间");
				   	 			return ;
				   	 		}
				   	 	    params=params+"&ordertime="+document.getElementById("ordertime").value;
				   	 	    var projects="";
				   	 	    if(document.getElementById("orderprojectmf").checked==true)
				   	 	    {
				   	 	    	 projects=projects+"300";
				   	 	    }
				   	 	    if(document.getElementById("orderprojecttrh").checked==true)
				   	 	    {
				   	 	    	if(projects=="")
				   	 	    	 	projects=projects+"600";
				   	 	    	 else
				   	 	    	 	projects=projects+";600";
				   	 	    }
				   	 	    if(document.getElementById("orderprojectmr").checked==true)
				   	 	    {
				   	 	    	if(projects=="")
				   	 	    	 	projects=projects+"400";
				   	 	    	 else
				   	 	    	 	projects=projects+";400";
				   	 	    }
						    params=params+"&orderproject="+encodeURI(projects);
						    params=params+"&orderusermf="+ document.getElementById("orderusermf").value ;
						    params=params+"&orderphone="+ parent.document.getElementById("call_no").value;
						    params=params+"&orderusertrh="+ document.getElementById("orderusertrh").value ;
						    params=params+"&orderusermr="+ document.getElementById("orderusermr").value ;
				            params=params+"&orderdetail="+ document.getElementById("orderdetail").value;
				            params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
				            $.ajax({
			                type: 'POST',
			                url: requestUrl+params,
			                data: null,
			                dataType: 'json',
			                success: function (data) { 
			                	alert(data.strMessage);
			                			}
			           	 	});
				            document.getElementById("orderusermf").disabled=true;
				            document.getElementById("orderusertrh").disabled=true;
				            document.getElementById("orderusermr").disabled=true;
				            document.getElementById("orderdetail").disabled=true;
				            document.getElementById("ordertime").disabled=true;
				            document.getElementById("orderprojectmf").disabled=true;
				            document.getElementById("orderprojecttrh").disabled=true;
				            document.getElementById("orderprojectmr").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
 	function fllowBill() { ac002Tab.selectTabItem("tabitem1"); }
 
	function addpeiier(){
			var row=peiiershow.getSelectedRow();
		 	peiiershow.addRow({ 
					           call_no: "",
					           peiiercontent: "",
					           peiierdetails:"",
					           peiiertime: "" 
					                }, row, false);
			}
	function addrefers(){
			var row=refershow.getSelectedRow();
		 	refershow.addRow({ 
					           callon: "",
					           refertime: "",
					           calluserid:"",
					           refercomply: "",
					           refercards:"",
					           referproject:"",
					           referdetails:""
					                }, row, false);
			}
		
    function checkbox()
			{
			var stres=document.getElementsByName("referproject");
			var str=document.getElementsByName("refercomply");
			var strs=document.getElementsByName("refercards");
			var referproject=stres.length;
			var refercomply=str.length;
			var refercards=strs.length;
			var chestr1="";
			var chestr2="";
			var chestr3="";
			for (i=0;i<referproject;i++)
			{
			  if(stres[i].checked == true)
			  {
			   chestr1+=stres[i].value+",";
			  }
			}
			for (i=0;i<refercomply;i++)
			{
			  if(str[i].checked == true)
			  {
			   chestr2+=str[i].value+",";
			  }
			}
			for (i=0;i<refercards;i++)
			{
			  if(strs[i].checked == true)
			  {
			   chestr3+=strs[i].value+",";
			  }
			}
		}
			
	 //添加咨询单据
 	function addrefer(){
		  try
		     {
		    var stres=document.getElementsByName("referproject");
			var str=document.getElementsByName("refercomply");
			var strs=document.getElementsByName("refercards");
			var referproject=stres.length;
			var refercomply=str.length;
			var refercards=strs.length;
			var chestr1="";
			var chestr2="";
			var chestr3="";
						for (i=0;i<referproject;i++)
						{
						  if(stres[i].checked == true)
						  {
						   chestr1+=stres[i].value+" ";
						  }
						}
						for (i=0;i<refercomply;i++)
						{
						  if(str[i].checked == true)
						  {
						   chestr2+=str[i].value+" ";
						  }
						}
						for (i=0;i<refercards;i++)
						{
						  if(strs[i].checked == true)
						  {
						   chestr3+=strs[i].value+" ";
						  }
						}
				     var    requestUrl =contextURL+"/ac002/addrefer.action?";
				   	 var    params="refercomply="+encodeURI(chestr2);
				   	 	    params=params+"&refercards="+encodeURI(chestr3);
						    params=params+"&referproject="+encodeURI(chestr1);
						    params=params+"&referdetails="+encodeURI(document.getElementById("referdetails").value);
						    params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
					 	    $.ajax({
				                type: 'POST',
				                url: requestUrl+params,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
				                	alert(data.strMessage);
				                }
				           	 	});
		            		document.getElementById("referproject").disabled=true;
		            		document.getElementById("refercomply").disabled=true;
		            		document.getElementById("refercomply1").disabled=true;
		            		document.getElementById("refercomply2").disabled=true;
		            		document.getElementById("refercomply3").disabled=true;
		            		document.getElementById("refercards").disabled=true;
		            		document.getElementById("refercards1").disabled=true;
		            		document.getElementById("refercards2").disabled=true;
		            		document.getElementById("refercards3").disabled=true;
		            		document.getElementById("referdetails").disabled=true;
		            		document.getElementById("refercards4").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
 	function addrefers(){
		  try
		     {
					    var stres=document.getElementsByName("referproject");
						var str=document.getElementsByName("refercomply");
						var strs=document.getElementsByName("refercards");
						var referproject=stres.length;
						var refercomply=str.length;
						var refercards=strs.length;
						var chestr1="";
						var chestr2="";
						var chestr3="";
						for (i=0;i<referproject;i++)
						{
						  if(stres[i].checked == true)
						  {
						   chestr1+=stres[i].value+" ";
						  }
						}
						for (i=0;i<refercomply;i++)
						{
						  if(str[i].checked == true)
						  {
						   chestr2+=str[i].value+" ";
						  }
						}
						for (i=0;i<refercards;i++)
						{
						  if(strs[i].checked == true)
						  {
						   chestr3+=strs[i].value+" ";
						  }
						}
				      var   requestUrl =contextURL+"/ac002/addrefers.action?";
				   	  var   params="refercomply="+encodeURI(chestr2);
				   	 	    params=params+"&refercards="+encodeURI(chestr3);
						    params=params+"&referproject="+encodeURI(chestr1);
						    params=params+"&referdetails="+encodeURI(document.getElementById("referdetails").value);
						    params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
					 	    $.ajax({
				                type: 'POST',
				                url: requestUrl+params,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
				                	alert(data.strMessage);
				                }
				           	 	});
		            		document.getElementById("referproject").disabled=true;
		            		document.getElementById("refercomply").disabled=true;
		            		document.getElementById("refercomply1").disabled=true;
		            		document.getElementById("refercomply2").disabled=true;
		            		document.getElementById("refercomply3").disabled=true;
		            		document.getElementById("refercards").disabled=true;
		            		document.getElementById("refercards1").disabled=true;
		            		document.getElementById("refercards2").disabled=true;
		            		document.getElementById("refercards3").disabled=true;
		            		document.getElementById("referdetails").disabled=true;
		            		document.getElementById("refercards4").disabled=true;
	      			}catch(e){alert(e.message);}
	   	 }
	function checkbox()
				{
				var str=document.getElementsByName("peiiercontent");
			     	var peiiercontent=str.length;
				    var checkstr="";
					for (i=0;i<peiiercontent;i++)
						{
							  if(str[i].checked == true)
							{
								   checkstr+=str[i].value+" ";
							 }
					   }
				}
	  	 
	  	  //添加单据
	 function addpeiiera(){
		  try
		     {
		    	var str=document.getElementsByName("peiiercontent");
		     	var peiiercontent=str.length;
			    var checkstr="";
				for (i=0;i<peiiercontent;i++)
					{
						  if(str[i].checked == true)
						{
							   checkstr+=str[i].value+" ";
						 }
				   }
				var    requestUrl =contextURL+"/ac002/addpeiier.action?";
				var    params="peiiercontent="+encodeURI(checkstr);
					   params=params+"&peiierdetails="+encodeURI(document.getElementById("peiierdetails").value);
					   params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
				 	   $.ajax({
			                type: 'POST',
			                url: requestUrl+params,
			                data: null,
			                dataType: 'json',
			                success: function (data) { 
			                	alert(data.strMessage);
			                }
			           	 	});
            				document.getElementById("peiierdetails").disabled=true;
            				document.getElementById("peiierids").disabled=true;
            				document.getElementById("peiieridss").disabled=true;
            				document.getElementById("peiieridses").disabled=true;
            				document.getElementById("peiierbeizhu").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
   	  //添加单据(结案)
 	function addpeiieras(){
		  try
		     {
			    	var str=document.getElementsByName("peiiercontent");
			     	var peiiercontent=str.length;
				    var checkstr="";
					for (i=0;i<peiiercontent;i++)
						{
							  if(str[i].checked == true)
							{
								   checkstr+=str[i].value+" ";
							 }
					   }
				     var    requestUrl =contextURL+"/ac002/addpeiiers.action?";
				   	 var    params="peiiercontent="+encodeURI(checkstr);
						    params=params+"&peiierdetails="+encodeURI(document.getElementById("peiierdetails").value);
						    params=params+"&callbillid="+parent.document.getElementById("callbillid").value;	
				 	   		 $.ajax({
			                type: 'POST',
			                url: requestUrl+params,
			                data: null,
			                dataType: 'json',
			                success: function (data) { 
			                	alert(data.strMessage);
			                }
			           	 	});
            				document.getElementById("peiierdetails").disabled=true;
            				document.getElementById("peiierids").disabled=true;
            				document.getElementById("peiieridss").disabled=true;
            				document.getElementById("peiieridses").disabled=true;
            				document.getElementById("peiierbeizhu").disabled=true;
	      		}catch(e){alert(e.message);}
	    }
 
	    //显示投诉历史
 	function selecpeiier(){
	    	try{
			     var   params="callbillid="+document.getElementById("stritemtype").value;
			     var   requestUrl =contextURL+"/ac002/selectpeiier.action?"+params;
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lstpeiier!=null && data.lstpeiier.length>0)
								{
									var btndate={Rows:data.lstpeiier,Total:data.lstpeiier.length};	
									peiiershow.options.data=$.extend(true, {},btndate);
            						peiiershow.loadData(true);	
								}
								else
								{
									peiiershow.options.data=$.extend(true, {},{Rows:null,Total:0});
            						peiiershow.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   		}catch(e){  alert(e.message);
    	   }
	    }
 
	 //显示退卡历史
	 function selectcard(){
	    	try{
			   var   params="callbillid="+document.getElementById("stritemtype").value;
			   var   requestUrl =contextURL+"/ac002/selectcard.action?"+params;
			    	 $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lscardreturn!=null && data.lscardreturn.length>0)
								{
									var btndate={Rows:data.lscardreturn,Total: data.lscardreturn.length};	
									cardreturnshow.options.data=$.extend(true, {},btndate);
            						cardreturnshow.loadData(true);	
								}
								else
								{
									cardreturnshow.options.data=$.extend(true, {},{Rows:null,Total:0});
            						cardreturnshow.loadData(true);	
								}
							},
							asynchronous:true
						});			  
    	   		}catch(e){ alert(e.message);
    	   }
	    }
 
 	//显示预约
 	function selectorder(){ 
	    	try{
			    var     params="callbillid="+document.getElementById("stritemtype").value;
			    var     requestUrl =contextURL+"/ac002/selectorder.action?"+params;
			    	    $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lsorders!=null && data.lsorders.length>0)
								{
									var btndate={Rows:data.lsorders,Total: data.lsorders.length};	
									orders.options.data=$.extend(true, {},btndate);
            						orders.loadData(true);	
								}
								else 
								{
									orders.options.data=$.extend(true, {},{Rows:null,Total:0});
            						orders.loadData(true);
								}
				                }
				           	 	});
    	   		}catch(e){  alert(e.message);
    	   }
	    }
 
	     //显示咨询历史
 	function selectrefer(){ 
	    	try{
			    var     params="callbillid="+document.getElementById("stritemtype").value;
			    var     requestUrl =contextURL+"/ac002/selectrefer.action?"+params;
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lsrefer!=null && data.lsrefer.length>0)
								{
									var btndate={Rows:data.lsrefer,Total: data.lsrefer.length};	
									refershow.options.data=$.extend(true, {},btndate);
            						refershow.loadData(true);	
								}
								else
								{
									refershow.options.data=$.extend(true, {},{Rows:null,Total:0});
            						refershow.loadData(true);
								}
				                }
				           	 	});
    	   				}catch(e){ alert(e.message);
    	   }
	    }
 	function changeMFPrjBook(obj)
	{
		try{
			clearOption("orderusermf");
			if(curStaffInfos!=null && curStaffInfos.length>0)
			{
				for(var i=0;i<curStaffInfos.length;i++)
				{
					if(curStaffInfos[i].department=="004")
					{
						addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusermf"));
					}
				}
			}	
			
		}
		catch(e)
		{
			alert(e.message);
			
		}
	}


	function changeTRHBook(obj)
	{
		document.getElementById("orderprojectmf").checked=true;
		clearOption("orderusertrh");
		try{
			if(curStaffInfos!=null && curStaffInfos.length>0)
			{
				for(var i=0;i<curStaffInfos.length;i++)
				{
					if(curStaffInfos[i].department=="006")
					{
						addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusertrh"));
					}
				}
			}	
			
		}
		catch(e)
		{
			alert(e.message);
		}
		changeMFPrjBook(document.getElementById("orderprojectmf"));
	}

	function changeMRBook(obj)
	{
		clearOption("orderusermr");
		try{
			if(curStaffInfos!=null && curStaffInfos.length>0)
			{
				for(var i=0;i<curStaffInfos.length;i++)
				{
					if(curStaffInfos[i].department=="003")
					{
						addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusermr"));
					}
				}
			}	
			
		}
		catch(e)
		{
			alert(e.message);
			
		}
	}


  
	//在组织结构块，选择某个组织，联动获取并显示子组织列表
	function selectSomeOrg(orgLevel,maxLevel)
	{
		var selid = "level_"+orgLevel;
		var orgId = document.getElementById(selid).value;
		var param=null;
     	var requestUrl =contextURL+"/orgController/loadChildOrgs.action?orgLevel="+orgLevel+"&orgId="+orgId+"&maxLevel="+maxLevel;
		var responseMethod="processOrgResponse";				
		try
		{
		$.ajax({
                type: 'POST',
                url: requestUrl,
                data: null,
                dataType: 'json',
                success: function (data) { 
						 processOrgResponse(data);
                }
            });}catch(e){alert(e.message);}
	}
	//选择某个组织后的异步响应
	function processOrgResponse(action)
	{
		try{
			var childLevel = action.orgLevel+1;
			var childid = "level_"+childLevel;
			var childOrgs =action.childOrgs;
			var keys = new Array();
			for(var keyx in childOrgs)
			{
				keys.push(keyx);
			}
			keys.sort();
			clearOption(childid);
			var sselect = document.getElementById(childid);
			    addOption("*","*",sselect);
			for(var kk=0;kk<keys.length;kk++)
			{
				if(childOrgs.hasOwnProperty(keys[kk]))
				{ 
					var tmpKey = keys[kk];
					addOption(tmpKey,tmpKey+"-"+childOrgs[tmpKey.toString()],sselect);
				}
			}
			var maxLevel = action.maxLevel;
			for(var i=childLevel+1;i<=maxLevel;i++)
			{
				var elementid = "level_"+i;
				clearOption(elementid);
				elem = document.getElementById(elementid);
				addOption("*","*",elem);
			}	
			
		}
		catch(e)
		{
			alert(e.message);
			
		}
	}
	function loadCurCompId(lvl)
	{
		if(lvl==4)
		{
			var    requestUrl =contextURL+"/ac002/loadCurStaffinfo.action?";
			var    params="orderconply="+getCurOrgFromSearchBar();
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			curStaffInfos=data.curStaffInfos;
				   			
				       	}
					});
		}
	
	}
	function changeMFPrjBook(obj)
	{
			try{
				clearOption("orderusermf");
				addOption("","请选择",document.getElementById("orderusermf"));
				if(curStaffInfos!=null && curStaffInfos.length>0)
				{
					for(var i=0;i<curStaffInfos.length;i++)
					{
						if(curStaffInfos[i].department=="004")
						{
							addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusermf"));
						}
					}
				}	
				
			}
			catch(e)
			{
				alert(e.message);
				
			}
	}
	function changeTRHBook(obj)
	{
			document.getElementById("orderprojectmf").checked=true;
			clearOption("orderusertrh");
			addOption("","请选择",document.getElementById("orderusertrh"));
			try{
				if(curStaffInfos!=null && curStaffInfos.length>0)
				{
					for(var i=0;i<curStaffInfos.length;i++)
					{
						if(curStaffInfos[i].department=="006")
						{
							addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusertrh"));
						}
					}
				}	
				
			}
			catch(e)
			{
				alert(e.message);
				
			}
			changeMFPrjBook(document.getElementById("orderprojectmf"));
	}
	function changeMRBook(obj)
	{
			clearOption("orderusermr");
			addOption("","请选择",document.getElementById("orderusermr"));
			try{
				if(curStaffInfos!=null && curStaffInfos.length>0)
				{
					for(var i=0;i<curStaffInfos.length;i++)
					{
						if(curStaffInfos[i].department=="003")
						{
							addOption(curStaffInfos[i].bstaffno,curStaffInfos[i].bstaffno+"-"+curStaffInfos[i].staffname,document.getElementById("orderusermr"));
						}
					}
				}	
				
			}
			catch(e)
			{
				alert(e.message);
				
			}
	}