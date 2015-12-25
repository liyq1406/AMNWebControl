    var showDialogmanager;
    var callbao;
    var orderbao;
    var peiierbao;
    var referbao;
    var carretuningbao;
    $(function () { 
    	 carretuningbao=$("#carretuningbao").ligerGrid({
                columns: [
                { display: '类别', name:'billtype',width:130,totalSummary: {type: 'count',render: function (e) {
              	 return "<div>预约总数：" + e.count + "</div>";
        		  }}},
                { display: '姓名', name: 'membername',width:100},
                { display: '内容', name: 'cardreturningcontent',width:200 },
               	{ display: '备注', name:'cardreturningdetails',width:130},
                { name: 'cardreturningtime',hide:true,width:1}
                ],
                pageSize:30,
                frozen:false,
                width:800, 
                height:730,
                enabledEdit: true,
                groupColumnName:'cardreturningtime', groupColumnDisplay:'日期'
           	   }); 
    	
    	peiierbao=$("#peiierbao").ligerGrid({
                columns: [
                { display: '类别', name:'billtype',width:130,totalSummary: {type: 'count',render: function (e) {
              	 return "<div>预约总数：" + e.count + "</div>";
        		  }}},
                { display: '姓名', name: 'membername',width:100},
                { display: '内容', name: 'peiiercontent',width:200 },
               	{ display: '备注', name:'peiierdetails',width:130},
                { name: 'peiiertime',hide:true,width:1}
                ],
                pageSize:30,
                frozen:false,
                width:800, 
                height:730,
                enabledEdit: true,
                groupColumnName:'peiiertime', groupColumnDisplay:'日期'
           	   }); 
    	 callbao=$("#callbao").ligerGrid({
                columns: [
                { display: '来电号码', name: 'callon',width:90, totalSummary: {type: 'count',render: function (e) {
              	 return "<div>来电总数：" + e.count + "</div>";
        		  }}},
                { display: '接听号码', name: 'vallend',width:90},
                { display: '通话时长',name: 'hours',width:80,totalSummary: {type: 'sum,avg'
                	 }},
        		  { display: '通话时长', name: 'callhoues',width:110},
                { name: 'calltime',hide:true,width:1}
                ],
                 onSelectRow : function (data, rowindex, rowobj)
                {
    				 carretuningbaos(data.calluserid);
    				 peiierbaos(data.calluserid);
			  		 orderbaos(data.calluserid);
			  		 referbaos(data.calluserid);
                },
                pageSize:30,
                frozen:false,
                width:400, 
                height:693,
                enabledEdit: true,usePager:false,
                groupColumnName:'calltimes', groupColumnDisplay:'日期'
           	   }); 
    	 orderbao=$("#orderbao").ligerGrid({
                columns: [
                { display: '类别', name:'billtype',width:130,totalSummary: {type: 'count',render: function (e) {
              	 return "<div>预约总数：" + e.count + "</div>";
        		  }}},
                { display: '姓名', name: 'membername',width:100},
                { display: '内容', name: 'orderproject',width:200 },
               	{ display: '备注', name:'orderdetail',width:130},
                { name: 'ordertime',hide:true,width:1}
                ],
                pageSize:30,
                frozen:false,
                width:800,  
                height:730, 
                enabledEdit: true,
                groupColumnName:'ordertime', groupColumnDisplay:'日期'
           	   }); 
    	 
    	  referbao=$("#referbao").ligerGrid({
                columns: [
                { display: '类别', name:'billtype',width:130,totalSummary: {type: 'count',render: function (e) {
              	 return "<div>咨询总数：" + e.count + "</div>";
        		  }}}, 
                { display: '姓名', name: 'membername',width:80},
                { display: '项目咨询', name: 'refercomply',width:80},
                { display: '门店咨询', name: 'refercards',width:90 },
               	{ display: '会员卡咨询', name:'referproject',width:110},
               	{ display: '备注', name:'referdetails',width:130},
                { name: 'refertime',hide:true}
                ],
                pageSize:30,
                width:800, 
                height:730,
                enabledEdit: true,
                frozen:false,
                groupColumnName:'refertime', groupColumnDisplay:'日期' 
           	   }); 
            $("#navtab2").ligerTab(); 
            $("#callbaobar").ligerToolBar({ items: [
            { text:'电话报表' ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/database.gif'}]});
            $("#calltime").ligerDateEditor({width:120,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
            $("#calltimes").ligerDateEditor({width:120,format: "yyyy-MM-dd",labelWidth: 80, labelAlign: 'right' });
  		  initpage();
  		  selectbaos();
  		  carretuningbaos('');
  		  peiierbaos('');
  		  orderbaos('');
  		  referbaos('');
    });
      function carretuningbaos(calluserid){
	    	try{
	    		 var params= "calluserid="+calluserid;
				 var myAjax = new parent.Ajax.Request(
					"ac011/cardreturningbao.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallsWatingBean!=null && action.lsCallsWatingBean.length>0)
									{
										var btndate={Rows:action.lsCallsWatingBean,Total: action.lsCallsWatingBean.length};	
										carretuningbao.options.data=$.extend(true, {},btndate);
	            						carretuningbao.loadData(true);	
									}
									else
									{
										carretuningbao.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						carretuningbao.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
      function peiierbaos(calluserid){
	    	try{
	    		 var params= "calluserid="+calluserid;
				 var myAjax = new parent.Ajax.Request(
					"ac011/peiierbao.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallsWatingBean!=null && action.lsCallsWatingBean.length>0)
									{
										var btndate={Rows:action.lsCallsWatingBean,Total: action.lsCallsWatingBean.length};	
										peiierbao.options.data=$.extend(true, {},btndate);
	            						peiierbao.loadData(true);	
									}
									else
									{
										peiierbao.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						peiierbao.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
    function orderbaos(calluserid){
	    	try{
	    		 var params= "calluserid="+calluserid;
				 var myAjax = new parent.Ajax.Request(
					"ac011/orderbao.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallsWatingBean!=null && action.lsCallsWatingBean.length>0)
									{
										var btndate={Rows:action.lsCallsWatingBean,Total: action.lsCallsWatingBean.length};	
										orderbao.options.data=$.extend(true, {},btndate);
	            						orderbao.loadData(true);	
									}
									else
									{
										orderbao.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						orderbao.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
    
    function referbaos(calluserid){
	    	try{
	    		 var params= "calluserid="+calluserid;
				 var myAjax = new parent.Ajax.Request(
					"ac011/referbao.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallsWatingBean!=null && action.lsCallsWatingBean.length>0)
									{
										var btndate={Rows:action.lsCallsWatingBean,Total: action.lsCallsWatingBean.length};	
										referbao.options.data=$.extend(true, {},btndate);
	            						referbao.loadData(true);	
									}
									else
									{
										referbao.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						referbao.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
    	
    	function selectbaos(){
	    	try{
	    		 var params ="calltype="+document.getElementById("calls").value;
	    		 	 params=params+"&calluserid="+document.getElementById("calluseridss").value;
				 var myAjax = new parent.Ajax.Request(
					"ac011/selectbao.action",{
								method:'post',
								parameters:params,
								onComplete:function (request) {
									var action = eval( "("+request.responseText+")");
									if(action.lsCallingAdminBean!=null && action.lsCallingAdminBean.length>0)
									{
										var btndate={Rows:action.lsCallingAdminBean,Total: action.lsCallingAdminBean.length};	
										callbao.options.data=$.extend(true, {},btndate);
	            						callbao.loadData(true);	
									}
									else
									{
										callbao.options.data=$.extend(true, {},{Rows:null,Total:0});
	            						callbao.loadData(true);	
											}
								},
								asynchronous:true
							});			  
	    	   }catch(e){
	    		 alert(e.message);
	    	   }
		    }
    
	    	 function initpage()
       		{
       			var requestUrl = "ac011/initpage.action?";
				var responseMethod="initpageMessage";	
				sendRequestForParams_p(requestUrl,responseMethod,"" ); 
       		}
       		function initpageMessage(request){
				var action = eval( "("+request.responseText+")");
				lsCallingAdminBean=action.lsCallingAdminBean;
 				 changeMFPrjBooks(document.getElementById("calluseridss"));
 				 loadbycustoms();
			}
       		function changeMFPrjBooks(obj)
			{
			try{
				clearOption("calluseridss");
				addOption("","请选择",document.getElementById("calluseridss"));
				if(lsCallingAdminBean!=null && lsCallingAdminBean.length>0)
				{
					for(var i=0;i<lsCallingAdminBean.length;i++)
					{
							addOption(lsCallingAdminBean[i].calluserid,lsCallingAdminBean[i].staffname+"-"+lsCallingAdminBean[i].calluserid,document.getElementById("calluseridss"));
					}
				}	
			}
			catch(e)
			{
				alert(e.message);
				
			}
		}
       		 