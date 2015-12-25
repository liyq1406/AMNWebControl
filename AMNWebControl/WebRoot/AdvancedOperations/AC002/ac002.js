    var  curStaffInfos=null;
	var  receiptses=null;  
	var  usercallinfo=null;
	var  Simplequery; //简单查询
    var  callhistory;// 来电历史
    var  refershow;//咨询
    var  peiiershow;//投诉
    var  undisposedcall;//所有未结案单据
    var  messageundisposedcall;//所有未处理的短信
    var  navtab = null;
    var  ac002Tab=null;
    var  curDetialRecord=null;
    var  ac002detailTab=null;
    var  selectlus;
    var  reports;
	var  default_view =0;  
    var  showDialogmanager=null;
    var  chooseData = [{ choose: '0', text: '未处理' }, { choose: '1', text: '已处理'}];
    var  orderchooseData = [{ choose: '0', text: '总部登记' }, { choose: '1', text: '门店确认'}, { choose: '2', text: '总部受理'}, { choose: '3', text: '已结案'}];
	var menu=null;
	var menubillno="";
	var confirmClosing = null;
	var confirmClosingNo = "";
	var isChrome = navigator.userAgent.toLowerCase().match(/chrome/) != null; 
	$(function () {  
    	 	try{
    	 
    	 	reports=$("#reports").ligerGrid({
                columns: [
                { display: '姓名', name: 'membername',width:45},
                { display: '卡号', name: 'memberno',width:90},
                { display: '卡类型', name: 'cardtypename',width:80}
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:100,
                enabledEdit: true,
                rownumbers:true,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	loadCall(data.memberno);
                } 
          	    });
          	    menu = $.ligerMenu({ width: 120, items:
	            [
	            { text: '查看录音', click: loadCallBillMenu, icon: 'add' }
	            ]
	            }); 
	            confirmClosing = $.ligerMenu({ width: 120, items:
	            [
	            
	            { text: '确认结案', click: submitMessage, icon: 'add' },
	            { text: '显示所有短信', click: showAllSMS, icon: 'add' },
	            { text: '显示已结案短信', click: showClosedSMS, icon: 'add' },
	            { text: '显示未结案短信', click: showOpenSMS, icon: 'add' },
	            ]
	            });  
	               
    	 	 receiptses=$("#receipts").ligerGrid({
                columns: [
                { display: '主叫', name: 'call_no',width:300} 
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:'400',
                enabledEdit: true,
                rownumbers:true,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	loadCallBill(data.callbillid);
                },
                onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {
                	 menubillno=parm.data.callbillid;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
                
           		}); 
    	 	 undisposedcall=$("#undisposedcall").ligerGrid({
                columns: [
                { display: '主叫', name: 'call_no',width:100},
                { display: '类别', name:'billtype',width:70},
                { display: '处理状态', name: 'orderstate',width:130,
               		 render: function (item)
	              	{
	                  if (item.orderstate == 0) return '总部登记';
	                  else if (item.orderstate == 1) return '处理中';
 	                  else if (item.orderstate == 2) return '处理中';
	                  else if (item.orderstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:'370',
                enabledEdit: true,
                rownumbers:true,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	loadCallBills(data.callbillid);
                } ,
                onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {    isms = parm.data.ssmessages;
                	 menubillno=parm.data.callbillid;
                     menu.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
           	   });
           	   
           	    messageundisposedcall=$("#messageundisposedcall").ligerGrid({
                columns: [
                { display: '主叫', name: 'call_no',width:100},
                { display: '是否满意', name:'billtype',width:70},
                { display: '处理状态', name: 'orderstate',width:130,
               		 render: function (item)
	              	{
	                  if (item.orderstate == 0) return '处理中';
	                  else if (item.orderstate == 1) return '处理中';
 	                  else if (item.orderstate == 2) return '处理中';
	                  else if (item.orderstate == 3) return '已结案';
	                }
	            }
                ],
                data:{Rows:null,Total: 0},
                pageSize:10,
                width:'100%', 
                height:'370',
                enabledEdit: true,
                rownumbers:true,usePager:false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	loadMessage(data.callbillid);
                } ,
                onRClickToSelect:true,
                onContextmenu : function (parm,e)
                {
                	 confirmClosingNo=parm.data.callbillid;
                     confirmClosing.show({ top: e.pageY, left: e.pageX });
                     return false;
                } 
           	   });  
    	 	callhistory=$("#callhistory").ligerGrid({
                columns: [
                {display:  '类别', name:'billtype',width:50},
                { display: '接听坐席', name: 'calluserid',width:80  ,isSort: false,editor: {type: 'text',width:100 ,readonly:true }},
                { display: '日期', name: 'offer_time',width:100,isSort: false ,editor: {type: 'text',width:100 ,readonly:true }},
                { display: '时间', name: 'offer_timeses',width:100, isSort: false,editor: {type: 'text',width:100 ,readonly:true }},
                { display: '内容', name: 'billProject',width:100, isSort: false,editor: { type: 'text',width:370,readonly:true }},
               	{ display: '备注',  name:'orderdetail',width:120, isSort: false,editor: { type: 'textarea',height:60,width:640,readonly:true }},
                { display: '状态', name: 'orderstate',width:60,
               		 render: function (item)
	              	{
	                  if (item.orderstate == 0) return '总部登记';
	                  else if (item.orderstate == 1) return '处理中';
 	                  else if (item.orderstate == 2) return '处理中';
	                  else if (item.orderstate == 3) return '已结案';
	                }
	            }
                ],
                detailToEdit: true, 
                showTableToggleBtn:true,
                frozen:false,
                pageSize:10,
                width:'100%', 
                height:'470',
                enabledEdit: true,
                rownumbers:true,usePager:false,
                toolbar: { items: [
                { text: '新增单据',click:function (){navtab.addTabItem({ text:'新增单据',url: contextURL+'/AdvancedOperations/AC002/addcallbillid.jsp?atype=1&itemtype=0 ' });},img: contextURL+'/common/ligerui/ligerUI/skins/icons/add.gif'},
                { text: '跟进',click:function (){navtab.addTabItem({ text:'新增单据',url:  contextURL+'/AdvancedOperations/AC002/addcallbillid.jsp?atype=2&itemtype='+curDetialRecord.callbillid });},img: contextURL+'/common/ligerui/ligerUI/skins/icons/process.gif'}]},
                onSelectRow : function (data, rowindex, rowobj)
                {
                	$("#txtrowindex").val(rowindex);
                	curDetialRecord=data;
                	if('undefined' != data.ismessages && data.ismessages == '3' ){
                		loadMessages(data.callbillid);
                	}else{
                		loadUserBillInfos(data.callbillid);
                		
                	}
                } 
          		 });
    	 $.ligerDefaults.Grid.editors['text'] = {
            create: function (container, editParm)
            {
                var input = $("<input type='text'  readonly='true' style='border:0;background:transparent;' />");
                container.append(input);
                container.width('auto').height('auto');
                return input;
            },
            getValue: function (input){ return input.val();},
            setValue: function (input, value){input.val(value);},
            resize: function (input, width, height, editParm)
            {
                var column = editParm.column;
                input.width(column.editor.width);
                input.height(column.editor.height);
            }
            };
    	$.ligerDefaults.Grid.editors['textarea'] = {
            create: function (container, editParm)
            {
                var input = $("<textarea  readonly='true' />");
                container.append(input);
                container.width('auto').height('auto');
                return input;
            },
            getValue: function (input, editParm){return input.val();},
            setValue: function (input, value, editParm){input.val(value);},
            resize: function (input, width, height, editParm)
            {
            	var column = editParm.column;
                input.width(column.editor.width);
                input.height(column.editor.height);
            }
       	   };
    	    $("#navtab2").ligerTab();
    	    $("#navtab1").ligerTab({height:740});
    	    navtab = $("#navtab1").ligerGetTabManager();
            $("#navtab1").ligerTab();
            ac002Tab=$("#navtab1").ligerGetTabManager();
            $("#tab").ligerTab();
            ac002detailTab=$("#tab").ligerGetTabManager();
            $("#showl").ligerToolBar({ items: [
            	{ text:'查询' ,click:selectluss,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'}]});
            $("#selecttime").ligerToolBar({ items: [
            	{ text:'查询' ,click:selecttime,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'}]});
            $("#select").ligerToolBar({ items: [
            	{ text:'查询' ,click:selectby,img: contextURL+'/common/ligerui/ligerUI/skins/icons/search.gif'}]});
            $("#topburmee").ligerToolBar({ items: [
            	{ text:'客服信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/memeber.gif'}]});
            $("#topburcall").ligerToolBar({ items: [
            	{ text:'来电信息' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/customers.gif'}]});
         	$("#editCurMarkInfo").ligerToolBar({ items: [
         		{ text:'补充类容',img: contextURL+'/common/ligerui/ligerUI/skins/icons/comment.gif'},
            	{ text:'追加描述' ,click:editCurMarkInfo,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'}]});
	     	$("#topburrs").ligerToolBar({ items: [
            	{ text:'保存顾客预约单' ,click:addorder,img: contextURL+'/common/ligerui/ligerUI/skins/icons/save.gif'}]});
            $("#times").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
            $("#timms").ligerDateEditor({ showTime:true,width:160,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
            $("#tims").ligerDateEditor({ showTime:true,width:160,format: "yyyyMMdd",labelWidth: 80, labelAlign: 'right' });
            $("#calltime").ligerDateEditor({ showTime:true,width:160,format: "yyyyMMdd",labelWidth: 80, labelAlign: 'right' });
    	    selectalls();
	 		 }catch(e){alert(e.message);}
        });

	 function luAddRows(filename){ //增加一条明细
  
      	var grid=document.getElementById("shwoluying");
	  	var maxRow = grid.rows.length-1;
	  	var trRow = maxRow +1;
      	var newTr = grid.insertRow();
      	newTr.className = "tr";  
      	newTr.id= trRow + "_tr" ; 
      	newTr.style.background="#efefef";
      	newTr.style.background="transparent";
      	for(var i=0;i<1;i++){
        	newTr.insertCell(i);
     	}
      	filename=filename.replace("<tt>","");
 		filename=filename.replace("</tt>","");
  	    var params=encodeURI(document.getElementById("calltime").value);
  	    	newTr.cells[0].innerHTML="<a style='text-decoration:none;' href=\"http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/"+params+"/"+filename+"\" >"+filename+"</a>";
	}
	
	function showLuRecord(lus){
		try
		{
			var grid=document.getElementById("luRecord");
	  		var maxRow = grid.rows.length-1;
		  	for(var i=0;i<maxRow*1+1;i++)
		  	{
		  		grid.deleteRow(0);
		  	}
		  	if(lus!=null && lus.length>0)
			{
				for(var i=0;i<lus.length;i++)
				{
					 luAddRow(lus[i].filename,lus[i].filesize);
				}
			}
		}
		catch(e)
		{
			alert(e.message);
		}
	}
   function luAddRow(filename,filesize){ //增加一条明细
  
      	var grid=document.getElementById("luRecord");
	  	var maxRow = grid.rows.length-1;
	  	var trRow = maxRow +1;
      	var newTr = grid.insertRow();
      	newTr.className = "tr";  
      	newTr.id= trRow + "_tr" ; 
      	newTr.style.background="#efefef";
      	newTr.style.background="transparent";
      	for(var i=0;i<2;i++){
        	newTr.insertCell(i);
     	}
      	filename=filename.replace("<tt>","");
 		filename=filename.replace("</tt>","");
 		var time=document.getElementById("offer_time").value.substring(0,10).replace("-","");
 		 	time.replace("-","");
  	    var params=time.replace("-","");
     		newTr.cells[0].innerHTML="<a style='text-decoration:none;' href=\"http://10.1.1.4:8180/ucallcenter/record/monitor/127.0.0.1/"+params+"/"+filename+"\" >"+filename.substring(0,30)+".mp3"+"</a>";
	     	newTr.cells[1].innerHTML=filesize;
	     	newTr.cells[0].innerHTML.height="25";
	     	newTr.cells[1].style.width="60";
	     	newTr.cells[1].align="right";
	}
	
	function selectluss(time,phone){
		try{
			   var times=document.getElementById("offer_time").value.substring(0,10).replace("-","");
			       times.replace("-","");
			   var params="time="+times.replace("-","");
			   	   params=params+"&phone="+document.getElementById("call_no").value;
			   showDialogmanager =  $.ligerDialog.waitting('正在下载中,请稍候...');
			   var requestUrl =contextURL+"/ac002/seleu.action?"; 
					 $.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
							 showDialogmanager.close();
							 showLuRecord(data.lsCallsWatingBean);
							}
						});			  
			}catch(e){alert(e.message);}
		}
	
	//短信弹框
	function showSMS(){
		$('.theme-popover-masks').show();
		$('.theme-popover-masks').height($(document).height());
		$('.theme-popovers-ms').slideDown(200);
	}
	function showMS(){
		$('.theme-popover-masks').hide();
		$('.theme-popovers-ms').slideUp(200);
	}
	/**
	 * 微信弹框
	 */
	function showcalls(){
			$('.theme-popover-masks').show();
			$('.theme-popover-masks').height($(document).height());
			$('.theme-popovers').slideDown(200);
	}
	function showhies(){
		$('.theme-popover-masks').hide();
		$('.theme-popovers').slideUp(200);
	}
	//mgx start

	window.setInterval('checkLoadMessage()',20000);
	function checkLoadMessage()
	    	 {
	    		try
	    		{
	    			var requestUrl =contextURL+"/ac002/checkLoadMessage.action"; 
					 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',
						success: function (data) { 
						strCheckFlag=data.strCheckFlag;
						
						if(checkNull(strCheckFlag)=="N" )
	       				{
	       				var l = data.lsmessageundisposedcall.length;
	       				document.getElementById("call_nos_ms").value=checkNull("您有"+l+"条未处理完的短信");
	       				var datastr ='';
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
						showSMS();
					}else{
						return;
					}
				}
			});
		}catch(e){alert(e.message)}        
	 }
	//mgx end 
	//window.setInterval('checkLoadMsgs()',100000);
	function checkLoadMsgs()
	    	 {
	    		try
	    		{
	    			var requestUrl =contextURL+"/ac002/checkmessage.action"; 
					 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',
						success: function (data) { 
						strCheckFlag=data.strCheckFlag;
						if(checkNull(strCheckFlag)=="N" )
	       				{
						showcalls();
						var datastr='';
						if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
							{
								for(var i=0;i<data.lsCallsWatingBean.length;i++)
								{
									if(datastr!='')
									{
										datastr=datastr+','
									}
									datastr=datastr+'{"billtype":"'+'预约'+'",' +
									'"calluserid":"'+data.lsCallsWatingBean[i].calluserid+'",' +
									'"callbillid":"'+data.lsCallsWatingBean[i].callbillid+'",' +
									'"offer_time":"'+data.lsCallsWatingBean[i].ordertime+'",' +
									'"offer_timeses":"'+data.lsCallsWatingBean[i].ordertimes+'",' +
									'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'","orderstate":"'+
									data.lsCallsWatingBean[i].orderstate+'"}';
								}
								if(datastr!='')
								{
									datastr='['+datastr+']';
								}
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
							    callhistory.loadData(true);	
								}
								else
								{ 
									callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
								    callhistory.loadData(true);	
								}
						if(data.lsCallsWatingBeanes!=null && data.lsCallsWatingBeanes.length>0)
						{
							datastr = '';
							for(var i=0;i<data.lsCallsWatingBeanes.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsCallsWatingBeanes[i].call_no+'","callbillid":"'+data.lsCallsWatingBeanes[i].callbillid+'"}';
							}
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
							receiptses.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBeanes.length});
						    receiptses.loadData(true);	
						}
						else
						{ 
							receiptses.options.data=$.extend(true, {},{Rows:null,Total:0});
						    receiptses.loadData(true);	
						}
							if(data.lsundisposedcall!=null && data.lsundisposedcall.length>0)
						{
							datastr = '';	
							for(var i=0;i<data.lsundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsundisposedcall[i].call_no+'",' +
								'"orderstate":"'+data.lsundisposedcall[i].orderstate+'","billtype":"'+
								data.lsundisposedcall[i].billtype+'","callbillid":"'+data.lsundisposedcall[i].callbillid+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
							undisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsundisposedcall.length});
						    undisposedcall.loadData(true);	
						}
						else
						{ 
							undisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    undisposedcall.loadData(true);	
						}
						//未结案的短信单据
						//alert(data.lsmessageundisposedcall);
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							datastr = '';
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
					}else{
						return;
					}
				}
			});
		}catch(e){alert(e.message)}        
	 }
	 window.setInterval('checkLoadMsg()',10000);
	 function checkLoadMsg()
	    	 {
	    		try
	    		{
	    			var requestUrl =contextURL+"/ac002/checkcalldate.action"; 
					 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',
						success: function (data) { 
						strCheckFlag=data.strCheckFlag;
						if(checkNull(strCheckFlag)=="N" )
	       				{
							selecttimess();
							document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
							document.getElementById("membernames").value=checkNull(data.callsWatingBean.membername);
				            document.getElementById("membernotocards").value=checkNull(data.callsWatingBean.membernotocard);
				            document.getElementById("cardtypes").value=checkNull(data.callsWatingBean.cardtype);
				            document.getElementById("call_nos").value=checkNull(data.callsWatingBean.call_no);
				            document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
	          		 		document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
	           				document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
	           				document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				document.getElementById("calltypemark").value=checkNull(data.callsWatingBean.calltypemark);
						var datastr='';
						if(data.lsCallsWatingBeanes!=null && data.lsCallsWatingBeanes.length>0)
						{
							for(var i=0;i<data.lsCallsWatingBeanes.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsCallsWatingBeanes[i].call_no+'","callbillid":"'+data.lsCallsWatingBeanes[i].callbillid+'"}';
							}
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
							receiptses.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBeanes.length});
						    receiptses.loadData(true);	
						}
						else
						{ 
							receiptses.options.data=$.extend(true, {},{Rows:null,Total:0});
						    receiptses.loadData(true);	
						    addreceiptsrow();
						}
						if(data.lsundisposedcall!=null && data.lsundisposedcall.length>0)
						{
							datastr = '';
							for(var i=0;i<data.lsundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsundisposedcall[i].call_no+'",' +
								'"orderstate":"'+data.lsundisposedcall[i].orderstate+'","billtype":"'+
								data.lsundisposedcall[i].billtype+'","callbillid":"'+data.lsundisposedcall[i].callbillid+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
							undisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsundisposedcall.length});
						    undisposedcall.loadData(true);	
						}
						else
						{ 
							undisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    undisposedcall.loadData(true);	
						}
						
						//未结案的短信单据
						//alert(data.lsmessageundisposedcall);
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							datastr = '';
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
						showDefaultView(); //展开方式
						    $('.service_menu li.hover dl dd').show();
						    scrollAd('#online_service_bar');
						    //当页面大小改变时
						    $(window).scroll(function() {
						        scrollAd('#online_service_bar');
						    });
					}else{
						return;
					}
				}
			});
		}catch(e){alert(e.message)}        
	 }
	    	
function loadCall(memberno)
{
		var    requestUrl =contextURL+"/ac002/loadbycar.action?";
		var    params="memberno="+memberno;
			   $.ajax({
					type: 'POST',
					url: requestUrl+params,
					data: null,
					dataType: 'json',
					success: function (data) { 
			   			document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
           				document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
           				document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
           				document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
           				document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
          		 		document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
           				document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
           				document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
           				var reg=new RegExp("<br>","g");
           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
			       	}
				});
			}
	//来电显示会员卡和卡类别信息
	 function selecttimess(){
		 	try{
				  var params="call_no="+document.getElementById("call_no").value;
				  if(document.getElementById("call_no").value=="")
				  	return ;
				  var requestUrl =contextURL+"/ac002/selectbyphone.action?"+params;
			    	   $.ajax({
				                type: 'POST',
				                url: requestUrl,
				                data: null,
				                dataType: 'json',
				                success: function (data) { 
								if(data.lsphone!=null && data.lsphone.length>0)
								{
									var btndate={Rows:data.lsphone,Total: data.lsphone.length};	
									reports.options.data=$.extend(true, {},btndate);
            						reports.loadData(true);	
								}
								else
								{
									$.ligerDialog.warn("该电话没有会员卡！");
									reports.options.data=$.extend(true, {},{Rows:null,Total:0});
            						reports.loadData(true);
								}
				                }
				           	 	});
    	   }catch(e){
    		 alert(e.message);
    	   }
	    }
	  //添加历史电话空白行
	   function  addcallhistory()
	  {
		  var row=callhistory.getSelectedRow();
				  callhistory.addRow({ 
					  		   callhistorytype:"",
					           callhistoryno: "",
					           callhistorycontent:"",
					           callhistorytime:"",
					           callhistorystate:""
					                }, row, false);
	  	}

	function   editCurMarkInfo()
	{
			var    requestUrl =contextURL+"/ac002/editCurMarkInfo.action?";
			var    params="callbillid="+document.getElementById("callbillid").value;
			var    reg=new RegExp("\r\n","g");
				   params=params+"&callmark="+encodeURI(document.getElementById("callmark").value.replace(reg,"<br>"));
			$.ajax({
								type: 'POST',
								url: requestUrl+params,
								data: null,
								dataType: 'json',
								success: function (data) { 
						   	    alert(data.strMessage);
						       	}
							});
	}
	//////////////////////////////start//////////////////////////////////
	//确认短信结案单据
	function submitMessage()
	{
		var    requestUrl =contextURL+"/ac002/submitMessage.action?";
		var    params="callbillid="+confirmClosingNo;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
						var datastr ='';
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
				       	}
					});

	}
	//显示所有短信
	function showAllSMS()
	{
		var    requestUrl =contextURL+"/ac002/showAllSMS.action?";
		var    params="callbillid="+confirmClosingNo;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
						var datastr ='';
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
				       	}
					});

	}
	//显示已结案短信
	function showClosedSMS()
	{
		var    requestUrl =contextURL+"/ac002/showClosedSMS.action?";
		var    params="callbillid="+confirmClosingNo;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
						var datastr ='';
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
				       	}
					});

	}
	//显示已结案短信
	function showOpenSMS()
	{
		var    requestUrl =contextURL+"/ac002/showOpenSMS.action?";
		var    params="callbillid="+confirmClosingNo;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
						var datastr ='';
						if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
						{
							for(var i=0;i<data.lsmessageundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
								'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
							}
							
							if(datastr!='')
							{
								datastr='['+datastr+']';
							}
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
						    messageundisposedcall.loadData(true);
						}
						else
						{ 
						    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						    messageundisposedcall.loadData(true);	
						}
				       	}
					});

	}
	//////////////////////////////end//////////////////////////////////
	function loadCallBillMenu()
	{
		var    requestUrl =contextURL+"/ac002/loadCallBills.action?";
		var    params="callbillid="+menubillno;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			   document.getElementById("calluserid").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("staffname").value=checkNull(data.callsWatingBean.staffname);
					           document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
					           document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
					           document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
					           document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
					           document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
					           document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
					           document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
	         				var datastr='';
	        				try{
	         				if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
							{
								for(var i=0;i<data.lsCallsWatingBean.length;i++)
								{
									
									if(datastr!='')
									{
										datastr=datastr+','
									}
									datastr=datastr+'{"billtype":"'+data.lsCallsWatingBean[i].billtype+'","callbillid":"'+data.lsCallsWatingBean[i].callbillid+'","calluserid":"'+data.lsCallsWatingBean[i].calluserid+'","billProject":"'+data.lsCallsWatingBean[i].orderproject+data.lsCallsWatingBean[i].billofUser+'","offer_time":"'+data.lsCallsWatingBean[i].offer_time+'","offer_timeses":"'+data.lsCallsWatingBean[i].offer_timeses+'",' +
									'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'",' +
									'"orderstate":"'+data.lsCallsWatingBean[i].orderstate+'"}';
								}
								if(datastr!='')
								{
									datastr='['+datastr+']';
								}
			
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
							    callhistory.loadData(true);	
							}
							else
							{ 
								callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
							    callhistory.loadData(true);	
							}
							}catch(e){alert(e.message);}
				   			selectluss(data.time,data.phone);
				   			selecttimess();
				       	}
					});
					
		
	}
	function loadCallBills(callbill)
	{
			var    requestUrl =contextURL+"/ac002/loadCallBills.action?";
			var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			   document.getElementById("calluserid").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("staffname").value=checkNull(data.callsWatingBean.staffname);
					           document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
					           document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
					           document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
					           document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
					           document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
					           document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
					           document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
	         				var datastr='';
	        				try{
	         				if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
							{
								for(var i=0;i<data.lsCallsWatingBean.length;i++)
								{
									
									if(datastr!='')
									{
										datastr=datastr+','
									}
									datastr=datastr+'{"billtype":"'+data.lsCallsWatingBean[i].billtype+'","callbillid":"'+data.lsCallsWatingBean[i].callbillid+'","calluserid":"'+data.lsCallsWatingBean[i].calluserid+'","billProject":"'+data.lsCallsWatingBean[i].orderproject+data.lsCallsWatingBean[i].billofUser+'","offer_time":"'+data.lsCallsWatingBean[i].offer_time+'","offer_timeses":"'+data.lsCallsWatingBean[i].offer_timeses+'",' +
									'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'",' +
									'"orderstate":"'+data.lsCallsWatingBean[i].orderstate+'"}';
								}
								if(datastr!='')
								{
									datastr='['+datastr+']';
								}
			
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
							    callhistory.loadData(true);	
							}
							else
							{ 
								callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
							    callhistory.loadData(true);	
							}
							}catch(e){alert(e.message);}
				   			
				   			selecttimess();
				       	}
					});
	}
	//-----------------------------mgx
	function loadMessage(callbill)
	{
	//alert("--"+callbill);
			var    requestUrl =contextURL+"/ac002/loadMessages.action?";
			var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			   document.getElementById("calluserid").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("staffname").value=checkNull(data.callsWatingBean.staffname);
					           document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
					           document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
					           document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
					           document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
					           document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
					           document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
					           document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
	         				var datastr='';
	        				try{
	         				if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
							{
								for(var i=0;i<data.lsCallsWatingBean.length;i++)
								{
									
									if(datastr!='')
									{
										datastr=datastr+','
									}
									datastr=datastr+'{"billtype":"'+data.lsCallsWatingBean[i].billtype+'","callbillid":"'+data.lsCallsWatingBean[i].callbillid+'","calluserid":"'+data.lsCallsWatingBean[i].calluserid+'","billProject":"'+data.lsCallsWatingBean[i].orderproject+data.lsCallsWatingBean[i].billofUser+'","offer_time":"'+data.lsCallsWatingBean[i].offer_time+'","offer_timeses":"'+data.lsCallsWatingBean[i].offer_timeses+'",' +
									'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'",' +
									'"ismessages":"'+data.lsCallsWatingBean[i].ismessages+'",' +
									'"orderstate":"'+data.lsCallsWatingBean[i].orderstate+'"}';
								}
								if(datastr!='')
								{
									datastr='['+datastr+']';
								}
			
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
							    callhistory.loadData(true);	
							}
							else
							{ 
								callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
							    callhistory.loadData(true);	
							}
							}catch(e){alert(e.message);}
				   			
				   			selecttimess();
				       	}
					});
	}
	
	function loadMessages(callbill)
	{
	//alert("--"+callbill);
			var    requestUrl =contextURL+"/ac002/loadMessages.action?";
			var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			   document.getElementById("calluserid").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("staffname").value=checkNull(data.callsWatingBean.staffname);
					           document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
					           document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
					           document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
					           document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
					           document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
					           document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
					           document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
					           document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
				   			selecttimess();
				       	}
					});
	}
	function loadCallBill(callbill)
	{
			var    requestUrl =contextURL+"/ac002/loadCallBill.action?";
			var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
							document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
							document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
	           				document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
	           				document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
	           				document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
	          		 		document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
	           				document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
	           				document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
	         				var datastr='';
	        				try{
	         				if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
							{
								for(var i=0;i<data.lsCallsWatingBean.length;i++)
								{
									
									if(datastr!='')
									{
										datastr=datastr+','
									}
									datastr=datastr+'{"billtype":"'+data.lsCallsWatingBean[i].billtype+'","callbillid":"'+data.lsCallsWatingBean[i].callbillid+'","calluserid":"'+data.lsCallsWatingBean[i].calluserid+'","billProject":"'+data.lsCallsWatingBean[i].orderproject+data.lsCallsWatingBean[i].billofUser+'","offer_time":"'+data.lsCallsWatingBean[i].offer_time+'","offer_timeses":"'+data.lsCallsWatingBean[i].offer_timeses+'",' +
									'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'",' +
									'"orderstate":"'+data.lsCallsWatingBean[i].orderstate+'"}';
								}
								if(datastr!='')
								{
									datastr='['+datastr+']';
								}
			
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
							    callhistory.loadData(true);	
							}
							else
							{ 
								callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
							    callhistory.loadData(true);	
							}
							}catch(e){alert(e.message);}
				   			
				   			selecttimess();
						}
					});
	}


	function loadUserBillInfo(callbill)
	{
		var    requestUrl =contextURL+"/ac002/loadCallBill.action?";
		var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
	           				document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
	           				document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
	           				document.getElementById("offer_time").value=checkNull(opener.callsWatingBean.offer_time)+" "+checkNull(opener.callsWatingBean.offer_timeses);
	           				document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
	          		 		document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
	           				document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
	           				document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
				       	}
				});
	}
	function loadUserBillInfos(callbill)
	{
		var    requestUrl =contextURL+"/ac002/loadCallBills.action?";
		var    params="callbillid="+callbill;
			$.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
				   			document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
	           				document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
	           				document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
	           				document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
	           				document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
	          		 		document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
	           				document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
	           				document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
	           				var reg=new RegExp("<br>","g");
	           				document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
	           				selecttimess();
				       	}
			 });
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
							    params=params+"&orderproject="+projects;
							    params=params+"&orderusermf="+document.getElementById("orderusermf").value;
							    params=params+"&orderusertrh="+document.getElementById("orderusertrh").value;
							    params=params+"&orderusermr="+document.getElementById("orderusermr").value;
					            params=params+"&orderdetail="+document.getElementById("orderdetail").value;
					          	params=params+"&callbillid="+document.getElementById("callbillid").value;
					          	params=params+"&calluserid="+document.getElementById("calluserid_show").value;
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
 	function fllowBill(){ac002Tab.selectTabItem("tabitem1");}
 
	function addpeiier(){
				var row=peiiershow.getSelectedRow();
			 	peiiershow.addRow({ 
						           call_no: "",
						           peiiercontent: "",
						           peiierdetails:"",
						           peiiertime: "" 
						                }, row, false);
			}
	function addrefers()
	{
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
		//根据时间查询
	function selecttime(){
	    		 var jsonParam=""
	    		 var params=" "
				 var requestUrl =contextURL+"/ac002/selectBytim.action?"
				 var params=params+"timms="+encodeURI(document.getElementById("timms").value);
					 $.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
					   var datastr='';
				       try{
						if(data.lsundisposedcall!=null && data.lsundisposedcall.length>0)
						{
							for(var i=0;i<data.lsundisposedcall.length;i++)
							{
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"call_no":"'+data.lsundisposedcall[i].call_no+'","orderstate":"'+data.lsundisposedcall[i].orderstate+'","billtype":"'+data.lsundisposedcall[i].billtype+'","callbillid":"'+data.lsundisposedcall[i].callbillid+'"}';
							}
							if(datastr!='')
							{
								datastr='['+datastr+']';
								undisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsundisposedcall.length});
						        undisposedcall.loadData(true);	
							}
							else
							{ 
								 $.ligerDialog.warn("您查询的没有记录！");
								undisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
							    undisposedcall.loadData(true);
								}
								
						}
						else
						{ 
							 $.ligerDialog.warn("您查询的没有记录！");
							 undisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
						     undisposedcall.loadData(true);
						}
						}catch(e){alert(e.message);}
			   			
			       	}
				});
		}
 		//根据字段查询信息
  function selectby(){
	    		 var jsonParam=""
	    		 var params=" "
				 var requestUrl =contextURL+"/ac002/selectByparams.action?"
				 var params=params+"memname="+encodeURI(document.getElementById("memname").value);
					 params=params+"&userid="+encodeURI(document.getElementById("userid").value);
					 params=params+"&billstate="+encodeURI(document.getElementById("billstate").value);
					 params=params+"&times="+encodeURI(document.getElementById("times").value);
					 $.ajax({
						type: 'POST',
						url: requestUrl+params,
						data: null,
						dataType: 'json',
						success: function (data) { 
						var datastr='';
				 try{
         				if(data.lsCallsWatingBean!=null && data.lsCallsWatingBean.length>0)
						{
							for(var i=0;i<data.lsCallsWatingBean.length;i++)
							{
								
								if(datastr!='')
								{
									datastr=datastr+','
								}
								datastr=datastr+'{"billtype":"'+data.lsCallsWatingBean[i].billtype+'","callbillid":"'+data.lsCallsWatingBean[i].callbillid+'","calluserid":"'+data.lsCallsWatingBean[i].calluserid+'","billProject":"'+data.lsCallsWatingBean[i].orderproject+data.lsCallsWatingBean[i].billofUser+'",' +
								'"offer_time":"'+data.lsCallsWatingBean[i].offer_time+'","offer_timeses":"'+data.lsCallsWatingBean[i].offer_timeses+'",' +
								'"orderdetail":"'+data.lsCallsWatingBean[i].orderdetail+'","orderstate":"'+data.lsCallsWatingBean[i].orderstate+'"}';
							}
							if(datastr!='')
							{
								datastr='['+datastr+']';
								callhistory.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBean.length});
						        callhistory.loadData(true);	
							}else
						    { 
								 $.ligerDialog.warn("您查询的没有记录！");
								callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
							    callhistory.loadData(true);	
							    addcallhistory();
						  }
		
						}
						else
						{ 
							 $.ligerDialog.warn("您查询的没有记录！");
							 callhistory.options.data=$.extend(true, {},{Rows:null,Total:0});
						     callhistory.loadData(true);	
						     addcallhistory();
						}
						}catch(e){alert(e.message);}
			   			
			       	}
				});
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
   function selectalls(){
				 var requestUrl =contextURL+"/ac002/selectall.action";
				 $.ajax({
						type: 'POST',
						url: requestUrl,
						data: null,
						dataType: 'json',
						success: function (data) {
				   		    document.getElementById("calluserid").value=checkNull(data.callsWatingBean.calluserid);
				            document.getElementById("staffname").value=checkNull(data.callsWatingBean.staffname);
				            document.getElementById("calluserid_show").value=checkNull(data.callsWatingBean.calluserid);
				            document.getElementById("callbillid").value=checkNull(data.callsWatingBean.callbillid);
				            document.getElementById("call_no").value=checkNull(data.callsWatingBean.call_no);
				            document.getElementById("offer_time").value=checkNull(data.callsWatingBean.offer_time)+" "+checkNull(data.callsWatingBean.offer_timeses);
				            document.getElementById("agent_num").value=checkNull(data.callsWatingBean.agent_num);
				            document.getElementById("membername").value=checkNull(data.callsWatingBean.membername);
				            document.getElementById("membernotocard").value=checkNull(data.callsWatingBean.membernotocard);
				            document.getElementById("cardtype").value=checkNull(data.callsWatingBean.cardtype);
				            var reg=new RegExp("<br>","g");
				            document.getElementById("callmark").value=checkNull(data.callsWatingBean.callmark).replace(reg,"\r\n");
            			    selecttimess();
            			    addcallhistory(); 
				  var datastr=''; 
			      if(data.lsCallsWatingBeanes!=null && data.lsCallsWatingBeanes.length>0)
					{
						for(var i=0;i<data.lsCallsWatingBeanes.length;i++)
						{
							if(datastr!='')
							{
								datastr=datastr+','
							}
							datastr=datastr+'{"call_no":"'+data.lsCallsWatingBeanes[i].call_no+'","callbillid":"'+data.lsCallsWatingBeanes[i].callbillid+'"}';
						}
						if(datastr!='')
						{
							datastr='['+datastr+']';
						}
						receiptses.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsCallsWatingBeanes.length});
					    receiptses.loadData(true);	
					}
					else
					{ 
						receiptses.options.data=$.extend(true, {},{Rows:null,Total:0});
					    receiptses.loadData(true);	
					}
					
					if(data.lsundisposedcall!=null && data.lsundisposedcall.length>0)
					{
						datastr='';
						for(var i=0;i<data.lsundisposedcall.length;i++)
						{
							if(datastr!='')
							{
								datastr=datastr+','
							}
							datastr=datastr+'{"call_no":"'+data.lsundisposedcall[i].call_no+'","orderstate":"'+data.lsundisposedcall[i].orderstate+'",' +
							'"billtype":"'+data.lsundisposedcall[i].billtype+'","callbillid":"'+data.lsundisposedcall[i].callbillid+'"}';
						}
						
						if(datastr!='')
						{
							datastr='['+datastr+']';
						}
						undisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsundisposedcall.length});
					    undisposedcall.loadData(true);	
					    
					}
					else
					{ 
						undisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
					    undisposedcall.loadData(true);	
					}
					
					//未结案的短信单据
					//alert(data.lsmessageundisposedcall);
					if(data.lsmessageundisposedcall!=null && data.lsmessageundisposedcall.length>0)
					{
						datastr='';
						for(var i=0;i<data.lsmessageundisposedcall.length;i++)
						{
							if(datastr!='')
							{
								datastr=datastr+','
							}
							datastr=datastr+'{"call_no":"'+data.lsmessageundisposedcall[i].call_no+'","orderstate":"'+data.lsmessageundisposedcall[i].orderstate+'",' +
							'"billtype":"不满意","callbillid":"'+data.lsmessageundisposedcall[i].callbillid+'","ismessages":"'+data.lsmessageundisposedcall[i].ismessages+'"}';
						}
						
						if(datastr!='')
						{
							datastr='['+datastr+']';
						}
					    messageundisposedcall.options.data=$.extend(true, {},{Rows:JSON.parse(datastr),Total: data.lsmessageundisposedcall.length});
					    messageundisposedcall.loadData(true);
					}
					else
					{ 
					    messageundisposedcall.options.data=$.extend(true, {},{Rows:null,Total:0});
					    messageundisposedcall.loadData(true);	
					}
				}
		      });
			}
	//门店确认信息弹屏
	       	// window.setInterval('checkcheckordersdate()',6000);
	    function checkcheckordersdate(){
		       	try
		    		{
		    			var requestUrl =contextURL+"/ac002/checkordersdate.action"; 
						var responseMethod="checkorderMessage";				
						sendRequestForParams_p(requestUrl,responseMethod,"" );
					}catch(e){alert(e.message)}     
	       	}
	   function checkorderMessage(request)
	   		{
	   		    var responsetext = eval("(" + request.responseText + ")");
	       		var strCheckFlag=responsetext.strCheckFlag;
	       		lsCallsWatingBean=responsetext.lsCallsWatingBean;
	       		lsCallsWatingBeans=responsetext.lsCallsWatingBeans;
	       		lsCallsWatingBeanes=responsetext.lsCallsWatingBeanes;
				var requestUrl ="AC/AC002/complyfeedback.jsp";
			    if(checkNull(strCheckFlag)=="N" )
	       		{
	       		 window.open(requestUrl,'newwindow','height=500,width=40,top=40,left2=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') ;
	       		}
	       	}
	       	function Closealert(type)
	       	{
	       		if(type==true)
	       		{
	       			window.open("<%=request.getContextPath()%>"+"/AdvancedOperations/AC002/alertCall.jsp");
	       			window.close();
	       		}
	       		else
	       		{
	       			window.close();
	       		}
	       	}	
//显示展开或收缩状态
function showDefaultView() {
        $('#online_service_fullbar').show();
}
//显示展开或收缩状态
function showDefaultViewms() {
        $('#online_service_fullbar_ms').show();
}

//为ID为close的DIV添加点击事件
function bindCloseBtn(obj) {
	 $(obj).click(function() {
	   $('#online_service_bar').hide();
	   });
}

 

//定义一个名字为scrollAD的函数
function scrollAd(obj) {
    //定义位移为floatdiv的高度加上滚动条的顶部距离
    var offset = $(obj).height() + $(document).scrollTop() +150;
    //为floatdiv添加动画为TOP位移offset的高度，持续0.8秒。
    
    $(obj).stop().animate({ top: offset }, 1000);
}
 
