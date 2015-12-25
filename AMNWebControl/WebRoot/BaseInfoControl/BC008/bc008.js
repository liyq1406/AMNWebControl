
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var mainform =null;
   	var projectXMLBData=JSON.parse(parent.loadCommonControlDate_select("XMLB",0));
   	var projectJGCWData=JSON.parse(parent.loadCommonControlDate_select("JGCW",0));
   	var projectXMTJData=JSON.parse(parent.loadCommonControlDate_select("XMTJ",0));
   	var chooseData = [{ choose: 1, text: '启' }, { choose: 2, text: '禁'}];
   	var curProjectInfoDate=null;
   	var BC008layout=null;
   	var BC008Tab =null;
   	var eidtPrjmodemanager=null;
   	var userPrimodemanager=null;
   	var eidtGoodsmodemanager=null;
   	var userGoodsmodemanager=null;
   	var eidtCardTypemodemanager=null;
   	var userCardTypemodemanager=null;
   	var userSaralymodemanager=null;
   	var saleCardmodemanager =null;
   	var fillCardmodemanager =null;
   	var cosumCardmodemanager =null;
   	var changeCardmodemanager =null;
   	var upDialogmanager=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            BC008layout= $("#bc008layout").ligerLayout({ leftWidth: 270,rightWidth: 245,  allowBottomResize: false, allowLeftResize: false ,isLeftCollapse:true});
             var height = $(".l-layout-center").height();
	   		//门店树形结构
   			compTree=$("#companyTree").ligerTree(
        	{
           		data: parent.complinkInfo,
           		idFieldName :'id',           		
           		onSelect: compSelect,
           		nodeWidth: 140,
           		checkbox: false,
           		height:'100%'
          	});
          	compTreeManager = $("#companyTree").ligerGetTreeManager();
          
           	$("#BC008Tab").ligerTab({
             	onAfterSelectTabItem: function (tabid)
            	{
            		if(tabid=="tabitem1")
            		{
            			if(pageState==3)
            			{
            				initEideFun();
            			}
            		}
               	 	else if(tabid=="tabitem2")
               	 	{
               	 		if(pageState==3)
            			{
               	 			initDisableFun();
               	 		}
               	 	}
            	} 
            });
            BC008Tab = $("#BC008Tab").ligerGetTabManager();
           	$("#toptoolbar").ligerToolBar({ items: [
                 { text: '保存', click: itemclick ,img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif'},
            	 { text: '分发提成设定',click: copyRateInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/settings.gif' }
			]
            });
            
          	$("#pageloading").hide(); 
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
        try{
        	curCompid=note.data.id;
       		var params = "strCurCompId="+note.data.id;				
     		var requestUrl ="bc008/loadparamsInfo.action"; 
			var responseMethod="loadparamsInfoMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    function loadparamsInfoMessage(request)
    {
    	try
        {
         
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.curSysparam!=null )
	   		{
	   			handleRadio("curSysparam.SP001",		responsetext.curSysparam.SP001);
	   			handleRadio("curSysparam.SP002",		responsetext.curSysparam.SP002);
	   			handleRadio("curSysparam.SP003",		responsetext.curSysparam.SP003);
	   			handleRadio("curSysparam.SP004",		responsetext.curSysparam.SP004);
	   			handleRadio("curSysparam.SP005",		responsetext.curSysparam.SP005);
			  	document.getElementById("SP006").value=	responsetext.curSysparam.SP006;
			  	document.getElementById("SP007").value=	responsetext.curSysparam.SP007;
			  	document.getElementById("SP008").value=	responsetext.curSysparam.SP008;
			  	document.getElementById("SP009").value=	responsetext.curSysparam.SP009;
			  	document.getElementById("SP010").value=	responsetext.curSysparam.SP010;
			  	document.getElementById("SP011").value=	responsetext.curSysparam.SP011;
			  	document.getElementById("SP012").value=	responsetext.curSysparam.SP012;
			  	document.getElementById("SP013").value=	responsetext.curSysparam.SP013;
			  	document.getElementById("SP014").value=	responsetext.curSysparam.SP014;
			  	handleRadio("curSysparam.SP015",		responsetext.curSysparam.SP015);
			  	handleRadio("curSysparam.SP016",		responsetext.curSysparam.SP016);
			  	handleRadio("curSysparam.SP017",		responsetext.curSysparam.SP017);
			  	handleRadio("curSysparam.SP018",		responsetext.curSysparam.SP018);
			  	document.getElementById("SP019").value=	responsetext.curSysparam.SP019;
			  	document.getElementById("SP020").value=	responsetext.curSysparam.SP020;
			  	document.getElementById("SP021").value=	responsetext.curSysparam.SP021;
			  	document.getElementById("SP022").value=	responsetext.curSysparam.SP022;
			  	document.getElementById("SP023").value=	responsetext.curSysparam.SP023;
			  	document.getElementById("SP024").value=	responsetext.curSysparam.SP024;
			  	document.getElementById("SP025").value=	responsetext.curSysparam.SP025;
			  	document.getElementById("SP026").value=	responsetext.curSysparam.SP026;
			  	document.getElementById("SP027").value=	responsetext.curSysparam.SP027;
			  	document.getElementById("SP028").value=	responsetext.curSysparam.SP028;
			  	document.getElementById("SP029").value=	responsetext.curSysparam.SP029;
			  	document.getElementById("SP030").value=	responsetext.curSysparam.SP030;
			  	document.getElementById("SP031").value=	responsetext.curSysparam.SP031;
			  	document.getElementById("SP032").value=	responsetext.curSysparam.SP032;
			  	document.getElementById("SP033").value=	responsetext.curSysparam.SP033;
			  	document.getElementById("SP034").value=	responsetext.curSysparam.SP034;
			  	document.getElementById("SP035").value=	responsetext.curSysparam.SP035;
			  	document.getElementById("SP036").value=	responsetext.curSysparam.SP036;
			  	document.getElementById("SP037").value=	responsetext.curSysparam.SP037;
			  	document.getElementById("SP038").value=	responsetext.curSysparam.SP038;
			  	document.getElementById("SP039").value=	responsetext.curSysparam.SP039;
			  	document.getElementById("SP040").value=	responsetext.curSysparam.SP040;
			  	document.getElementById("SP069").value=	responsetext.curSysparam.SP069;
			  	document.getElementById("SP070").value=	responsetext.curSysparam.SP070;
			  	document.getElementById("SP072").value=	responsetext.curSysparam.SP072;
			  	document.getElementById("SP073").value=	responsetext.curSysparam.SP073;
			  	document.getElementById("SP074").value=	responsetext.curSysparam.SP074;
			  	document.getElementById("SP075").value=	responsetext.curSysparam.SP075;
			  	document.getElementById("SP076").value=	responsetext.curSysparam.SP076;
			  	document.getElementById("SP077").value=	responsetext.curSysparam.SP077;
			  	document.getElementById("SP078").value=	responsetext.curSysparam.SP078;
			  	document.getElementById("SP079").value=	responsetext.curSysparam.SP079;
			  	document.getElementById("SP080").value=	responsetext.curSysparam.SP080;
			  	document.getElementById("SP081").value=	responsetext.curSysparam.SP081;
			  	document.getElementById("SP082").value=	responsetext.curSysparam.SP082;
			  	document.getElementById("SP083").value=	responsetext.curSysparam.SP083;
			  	document.getElementById("SP084").value=	responsetext.curSysparam.SP084;
			  	document.getElementById("SP085").value=	responsetext.curSysparam.SP085;
			  	document.getElementById("SP086").value=	responsetext.curSysparam.SP086;
			  	document.getElementById("SP087").value=	responsetext.curSysparam.SP087;
			  	document.getElementById("SP088").value=	responsetext.curSysparam.SP088;
			  	document.getElementById("SP089").value=	responsetext.curSysparam.SP089;
			  	document.getElementById("SP090").value=	responsetext.curSysparam.SP090;
			  	document.getElementById("SP091").value=	responsetext.curSysparam.SP091;
			  	document.getElementById("SP092").value=	responsetext.curSysparam.SP092;
			  	document.getElementById("SP093").value=	responsetext.curSysparam.SP093;
			  	document.getElementById("SP096").value=	responsetext.curSysparam.SP096;
			  	document.getElementById("SP100").value=	responsetext.curSysparam.SP100;
			  	document.getElementById("SP101").value=	responsetext.curSysparam.SP101;
			  	document.getElementById("SP110").value=	responsetext.curSysparam.SP110;
			  	document.getElementById("SP111").value=	responsetext.curSysparam.SP111;
			  	handleRadio("curSysparam.SP041",		responsetext.curSysparam.SP041);
			  	handleRadio("curSysparam.SP042",		responsetext.curSysparam.SP042);
			  	handleRadio("curSysparam.SP043",		responsetext.curSysparam.SP043);
			  	handleRadio("curSysparam.SP044",		responsetext.curSysparam.SP044);
			  	handleRadio("curSysparam.SP045",		responsetext.curSysparam.SP045);
			  	handleRadio("curSysparam.SP046",		responsetext.curSysparam.SP046);
			  	handleRadio("curSysparam.SP047",		responsetext.curSysparam.SP047);
			  	handleRadio("curSysparam.SP048",		responsetext.curSysparam.SP048);
			  	handleRadio("curSysparam.SP049",		responsetext.curSysparam.SP049);
			  	handleRadio("curSysparam.SP050",		responsetext.curSysparam.SP050);
			  	handleRadio("curSysparam.SP051",		responsetext.curSysparam.SP051);
			  	handleRadio("curSysparam.SP052",		responsetext.curSysparam.SP052);
			  	handleRadio("curSysparam.SP053",		responsetext.curSysparam.SP053);
			  	handleRadio("curSysparam.SP054",		responsetext.curSysparam.SP054);
			  	handleRadio("curSysparam.SP055",		responsetext.curSysparam.SP055);
			  	handleRadio("curSysparam.SP056",		responsetext.curSysparam.SP056);
			  	handleRadio("curSysparam.SP057",		responsetext.curSysparam.SP057);
			  	handleRadio("curSysparam.SP071",		responsetext.curSysparam.SP071);
			  	handleRadio("curSysparam.SP094",		responsetext.curSysparam.SP094);
			  	handleRadio("curSysparam.SP095",		responsetext.curSysparam.SP095);
			  	handleRadio("curSysparam.SP097",		responsetext.curSysparam.SP097);
			  	handleRadio("curSysparam.SP098",		responsetext.curSysparam.SP098);
			  	handleRadio("curSysparam.SP099",		responsetext.curSysparam.SP099);
			  	handleRadio("curSysparam.SP102",		responsetext.curSysparam.SP102);
			  	handleRadio("curSysparam.SP103",		responsetext.curSysparam.SP103);
			  	handleRadio("curSysparam.SP104",		responsetext.curSysparam.SP104); 
			  	handleRadio("curSysparam.SP105",		responsetext.curSysparam.SP105);
			  	handleRadio("curSysparam.SP106",		responsetext.curSysparam.SP106);
			  	handleRadio("curSysparam.SP107",		responsetext.curSysparam.SP107);
			  	handleRadio("curSysparam.SP108",		checkNull(responsetext.curSysparam.SP108));
			  	handleRadio("curSysparam.SP109",		checkNull(responsetext.curSysparam.SP109));
			  	handleRadio("curSysparam.SP112",		checkNull(responsetext.curSysparam.SP112));
			  	handleRadio("curSysparam.SP113",		checkNull(responsetext.curSysparam.SP113));
			  	handleRadio("curSysparam.SP114",		checkNull(responsetext.curSysparam.SP114));
			  	document.getElementById("SP115").value=	responsetext.curSysparam.SP115;
			  	document.getElementById("SP116").value=	responsetext.curSysparam.SP116;
			  	handleRadio("curSysparam.SP117",		responsetext.curSysparam.SP117);
			  	document.getElementById("SP118").value=	responsetext.curSysparam.SP118;
			  	
			  	document.getElementById("cpcostratemr").value=checkNull(responsetext.curSalaryrateinfo.cpcostratemr);
			  	document.getElementById("cpsalaryratemr").value=checkNull(responsetext.curSalaryrateinfo.cpsalaryratemr);
			 	document.getElementById("cpcostratemf").value=checkNull(responsetext.curSalaryrateinfo.cpcostratemf);
				document.getElementById("cpsalaryratemf").value=checkNull(responsetext.curSalaryrateinfo.cpsalaryratemf);
				document.getElementById("cpcostratemj").value=checkNull(responsetext.curSalaryrateinfo.cpcostratemj);
				document.getElementById("cpsalaryratemj").value=checkNull(responsetext.curSalaryrateinfo.cpsalaryratemj);
				document.getElementById("cpcostrateks").value=checkNull(responsetext.curSalaryrateinfo.cpcostrateks);
				document.getElementById("cpsalaryrateks").value=checkNull(responsetext.curSalaryrateinfo.cpsalaryrateks);
	
				document.getElementById("kjsalaryratetrsa").value=checkNull(responsetext.curSalaryrateinfo.kjsalaryratetrsa);
				document.getElementById("kjsalaryratetrsb").value=checkNull(responsetext.curSalaryrateinfo.kjsalaryratetrsb);
				document.getElementById("kjsalaryratemrc").value=checkNull(responsetext.curSalaryrateinfo.kjsalaryratemrc);
				document.getElementById("kjsalaryratemr").value=checkNull(responsetext.curSalaryrateinfo.kjsalaryratemr);
				document.getElementById("kjsalaryratemf").value=checkNull(responsetext.curSalaryrateinfo.kjsalaryratemf);
				
				
				document.getElementById("dhcostratetrsa").value=checkNull(responsetext.curSalaryrateinfo.dhcostratetrsa);
				document.getElementById("dhcostratetrsb").value=checkNull(responsetext.curSalaryrateinfo.dhcostratetrsb);
				document.getElementById("dhcostratemf").value=checkNull(responsetext.curSalaryrateinfo.dhcostratemf);
				document.getElementById("dhsalaryratetrsa").value=checkNull(responsetext.curSalaryrateinfo.dhsalaryratetrsa);
				document.getElementById("dhsalaryratetrsb").value=checkNull(responsetext.curSalaryrateinfo.dhsalaryratetrsb);
				document.getElementById("dhsalaryratemf").value=checkNull(responsetext.curSalaryrateinfo.dhsalaryratemf);
	
				document.getElementById("hzsalaryrateqh").value=checkNull(responsetext.curSalaryrateinfo.hzsalaryrateqh);
				document.getElementById("hzsalaryratejd").value=checkNull(responsetext.curSalaryrateinfo.hzsalaryratejd);
				document.getElementById("jfdhsalaryreward").value=checkNull(responsetext.curSalaryrateinfo.jfdhsalaryreward);
				document.getElementById("jjdhyejireward").value=checkNull(responsetext.curSalaryrateinfo.jjdhyejireward);
				document.getElementById("jjfwsalaryreward").value=checkNull(responsetext.curSalaryrateinfo.jjfwsalaryreward);
				document.getElementById("jsrsalaryreward").value=checkNull(responsetext.curSalaryrateinfo.jsrsalaryreward);
				document.getElementById("tjsrsalarycost").value=checkNull(responsetext.curSalaryrateinfo.tjsrsalarycost);
		
				document.getElementById("mfsalaryratefiveup").value=checkNull(responsetext.curSalaryrateinfo.mfsalaryratefiveup);
				document.getElementById("mfsalaryratefivedown").value=checkNull(responsetext.curSalaryrateinfo.mfsalaryratefivedown);
				document.getElementById("olccostyejifixed").value=checkNull(responsetext.curSalaryrateinfo.olccostyejifixed);
				document.getElementById("olccostsalaryfixed").value=checkNull(responsetext.curSalaryrateinfo.olccostsalaryfixed);
				document.getElementById("nlccostratetr").value=checkNull(responsetext.curSalaryrateinfo.nlccostratetr);
				
				document.getElementById("yjkcostratemrmf").value=checkNull(responsetext.curSalaryrateinfo.yjkcostratemrmf);
				document.getElementById("yjksalaryratemrmf").value=checkNull(responsetext.curSalaryrateinfo.yjksalaryratemrmf);
				document.getElementById("yjkcostratetr").value=checkNull(responsetext.curSalaryrateinfo.yjkcostratetr);
				
				document.getElementById("salaryratetra").value=checkNull(responsetext.curSalaryrateinfo.salaryratetra);
				document.getElementById("salaryratetrb").value=checkNull(responsetext.curSalaryrateinfo.salaryratetrb);
				
				document.getElementById("xjccostratesjs").value=checkNull(responsetext.curSalaryrateinfo.xjccostratesjs);
				document.getElementById("xjccostratesx").value=checkNull(responsetext.curSalaryrateinfo.xjccostratesx);
				document.getElementById("xjccostratezj").value=checkNull(responsetext.curSalaryrateinfo.xjccostratezj);

				document.getElementById("xjcsalaryfixeddb").value=checkNull(responsetext.curSalaryrateinfo.xjcsalaryfixeddb);
				document.getElementById("xjcsalaryfixedndb").value=checkNull(responsetext.curSalaryrateinfo.xjcsalaryfixedndb);
				document.getElementById("xjcsalaryfixednhg").value=checkNull(responsetext.curSalaryrateinfo.xjcsalaryfixednhg);
				
				document.getElementById("mrsalaryfixedtmk").value=checkNull(responsetext.curSalaryrateinfo.mrsalaryfixedtmk);
				document.getElementById("mrsalaryfixedty").value=checkNull(responsetext.curSalaryrateinfo.mrsalaryfixedty);
	   		}
	   		
	   		if(responsetext.lsinfomodes!=null)
	   		{
	   				var lsinfomodes=responsetext.lsinfomodes;
	   				//项目模板
	   				   var eidtPrjmodeData=JSON.parse(loadCommoninforDate(lsinfomodes,1));
		              userPrimodemanager = $("#bSP059").ligerComboBox({ data: eidtPrjmodeData, isMultiSelect: false,valueFieldID: 'factSP059',width:'180',selectBoxHeight:'220'});	
		              //产品模板
		      		 var eidtGoodsmodeData=JSON.parse(loadCommoninforDate(lsinfomodes,2));
		     	     userGoodsmodemanager = $("#bSP061").ligerComboBox({ data: eidtGoodsmodeData, isMultiSelect: false,valueFieldID: 'factSP061',width:'180',selectBoxHeight:'220'});	
		    	   	 //卡类型模板
		           	 var eidtCardTypemodeData=JSON.parse(loadCommoninforDate(lsinfomodes,3));
				     userCardTypemodemanager = $("#bSP063").ligerComboBox({ data: eidtCardTypemodeData, isMultiSelect: false,valueFieldID: 'factSP063',width:'180',selectBoxHeight:'220'});	
					//薪资模板
					 //var eidtSalarymodeData=JSON.parse(parent.loadCommonControlDate("XZMB",0));
		   			 //userSaralymodemanager = $("#bSP064").ligerComboBox({ data: eidtSalarymodeData, isMultiSelect: false,valueFieldID: 'factSP064',width:'120',selectBoxHeight:'220'});	
					document.getElementById("SP059").value=	responsetext.curSysparam.SP059;
				  	document.getElementById("SP061").value=	responsetext.curSysparam.SP061;
				  	document.getElementById("SP063").value=	responsetext.curSysparam.SP063;
					document.getElementById("SP064").value=	responsetext.curSysparam.SP064;
					userPrimodemanager.selectValue(checkNull(responsetext.curSysparam.SP059));
				  	userGoodsmodemanager.selectValue(checkNull(responsetext.curSysparam.SP061));
				  	userCardTypemodemanager.selectValue(checkNull(responsetext.curSysparam.SP063));
				  	//userSaralymodemanager.selectValue(checkNull(responsetext.curSysparam.SP064));
	   		}
	   		var paymodeData=JSON.parse(parent.loadCommonControlDate("ZFFS",0));
		   	saleCardmodemanager = $("#bSP065").ligerComboBox({ data: paymodeData, isMultiSelect: true,valueFieldID: 'factSP065',width:'420',selectBoxHeight:'220'});	
			fillCardmodemanager = $("#bSP066").ligerComboBox({ data: paymodeData, isMultiSelect: true,valueFieldID: 'factSP066',width:'420',selectBoxHeight:'220'});	
			cosumCardmodemanager = $("#bSP067").ligerComboBox({ data: paymodeData, isMultiSelect: true,valueFieldID: 'factSP067',width:'420',selectBoxHeight:'220'});	
			changeCardmodemanager = $("#bSP068").ligerComboBox({ data: paymodeData, isMultiSelect: true,valueFieldID: 'factSP068',width:'420',selectBoxHeight:'220'});	
			document.getElementById("SP065").value=	responsetext.curSysparam.SP065;
	   		document.getElementById("SP066").value=	responsetext.curSysparam.SP066;
	   		document.getElementById("SP067").value=	responsetext.curSysparam.SP067;
	   		document.getElementById("SP068").value=	responsetext.curSysparam.SP068;
	   		saleCardmodemanager.selectValue(checkNull(responsetext.curSysparam.SP065));	
	   		fillCardmodemanager.selectValue(checkNull(responsetext.curSysparam.SP066));	
	   		cosumCardmodemanager.selectValue(checkNull(responsetext.curSysparam.SP067));	
	   		changeCardmodemanager.selectValue(checkNull(responsetext.curSysparam.SP068));	
	   	}catch(e){alert(e.message);}
    }
    
    function loadCommoninforDate(lsinfomodes,type)
	{
		var returnValue='';
		var key="";
		var value="";
		for(var i=0;i<lsinfomodes.length;i++)
		{
			if(type==checkNull(lsinfomodes[i].id.modetype))
		    {
		    	if(returnValue!='')
				{
					returnValue=returnValue+',';
				}
				else
				{
					returnValue=returnValue+'[';
				}
				key = lsinfomodes[i].id.modeid;
				value = lsinfomodes[i].modename;
				returnValue=returnValue+'{"id": "'+key+'","text": "'+value+'"}';
			}
			
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
  
    
    function itemclick(item)
    {
    	try
    	{
	    	 $.ligerDialog.confirm('确认 '+curCompid+' 系统参数资料 '+item.text+' 操作', function (result)
		     {
		     		if( result==true)
            		{
            			if(parent.hasFunctionRights( "BC008",  "UR_POST")!=true)
			        	{
			        		 $.ligerDialog.warn("该用户没有保存权限,请确认!");
			        		 return;
			        	}
			          	editCurRecord();
			        }
		     });  
    	}
    	catch(e){alert(e.message);}
    }
    
     //保存
	 function editCurRecord()
	 {
	 	try
	 	{
	 	  // document.getElementById("SP058").value=$("#factSP058").val();
		    document.getElementById("SP059").value=$("#factSP059").val();
		   // document.getElementById("SP060").value=$("#factSP060").val();
		    document.getElementById("SP061").value=$("#factSP061").val();
		    //document.getElementById("SP062").value=$("#factSP062").val();
		    document.getElementById("SP063").value=$("#factSP063").val();
		    document.getElementById("SP064").value=$("#factSP064").val();
		    document.getElementById("SP065").value=$("#factSP065").val();
		    document.getElementById("SP066").value=$("#factSP066").val();
		    document.getElementById("SP067").value=$("#factSP067").val();
		    document.getElementById("SP068").value=$("#factSP068").val();
		    var queryStringTmp=$('#detailForm').serialize();//serialize('#detailForm');
	 		var params = "strCurCompId="+curCompid+"&"+queryStringTmp;			
     		var requestUrl ="bc008/postparamsInfo.action"; 
			var responseMethod="postparamsInfoMessage";		
		    sendRequestForParams_p(requestUrl,responseMethod,params );
	 	}
	 	catch(e){alert(e.message);}
	 	
	 }
	 function serialize(objs)
	 {
	 	var parmString = $(objs).serialize();
	 	var parmArray = parmString.split("&");
	 	var parmStringNew="";
	 	$.each(parmArray,function(index,data){   
       		var li_pos = data.indexOf("=");    
         	if(li_pos >0){   
            var name = data.substring(0,li_pos);   
            var value = escape(decodeURIComponent(data.substr(li_pos+1)));   
            var parm = name+"="+value;   
            parmStringNew = parmStringNew=="" ? parm : parmStringNew + '&' + parm;   
         	}   
    	});  
    	return parmStringNew; 
	 }
	 
	
    function postparamsInfoMessage(request)
    {
    	try
        {
         	var responsetext = eval("(" + request.responseText + ")");
	        var strMessage=responsetext.strMessage;
	        if(checkNull(strMessage)=="")
	        {	        		 
	        	curSecondCommonKey="";
	        	curSecondCommonValue="";
	        	$.ligerDialog.success("更新成功!");
	       	}
	        else
	        {
	        	alert(strMessage);
	        }
	   	}
	   	catch(e){alert(e.message);}
	}
  	function hotKeyOfSelf(key)
	{
			 if( key == 83 &&  event.altKey)
			{
				editCurRecord();
			}
	
	}
	
	function copyRateInfo()
    {
    	upDialogmanager=$.ligerDialog.open({ height: 600, url: contextURL+'/BaseInfoControl/BC008/handBandCompNo.jsp', width: 300, showMax: false, showToggle: false, showMin: false, isResize: false, title: '选择拷贝门店' });
	}
	
	function copyShopRateSetToOtherCompId(strBandComps)
	{
			var requestUrl ="bc008/copyShopRateSetInfos.action";
        	var params="strCurCompId="+curCompid;	
        	params =params+ "&strBandComps="+strBandComps;	
			var responseMethod="copyShopRateSetInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params ); 
     }
        
     function copyShopRateSetInfosMessage(request)
     {
    	var responsetext = eval("(" + request.responseText + ")");
	    var strMessage=responsetext.strMessage;
	    if(checkNull(strMessage)=="")
	    {	       		 
	        	$.ligerDialog.success("拷贝成功!");
	        	upDialogmanager.close();
	    }
	    else
	    {
	        	$.ligerDialog.warn(strMessage);
	    }
	        
     }