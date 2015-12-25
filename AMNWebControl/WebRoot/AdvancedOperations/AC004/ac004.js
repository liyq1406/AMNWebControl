
   	var compTree = null;//主明细	
   	var compTreeManager = null;//主明细	
   	var commoninfodivsecond=null;//员工明细
   	var curCompid="";
   	var curStaffid="";
   	var chooseData = [{ choose: 1, text: '换卡' }, { choose: 2, text: '补卡'}, { choose: 3, text: '退卡'}
   					, { choose: 4, text: '开卡额度'}, { choose: 5, text: '充值额度'}, { choose: 6, text: '修改会员资料'}
   					, { choose: 7, text: '退疗程'}, { choose: 8, text: '项目变价'}, { choose: 9, text: '产品变价'}];

   	var curGoodsInfoDate=null;
   	var ac004layout=null;
   	var curDetialRecord=null;
    $(function ()
   	{
	   try
	   {
	   		  //布局
            ac004layout= $("#ac004layout").ligerLayout({ leftWidth: 270, allowBottomResize: false, allowLeftResize: false });
            var height = $(".l-layout-center").height();
        	commoninfodivsecond=$("#commoninfodivsecond").ligerGrid({
                columns: [
                { display: '申请项目', name: 'appitemno',  width: 120,align: 'left' ,  
	                editor: { type: 'select', data: chooseData, valueField: 'choose' },
	                render: function (item)
	                {
	                    if (item.appitemno == 1) return '换卡';
	                    else if (item.appitemno == 2) return '补卡';
	                    else if (item.appitemno == 3) return '退卡';
	                    else if (item.appitemno == 4) return '开卡额度';
	                    else if (item.appitemno == 5) return '充值额度';
	                    else if (item.appitemno == 6) return '修改会员资料';
	                    else if (item.appitemno == 7) return '退疗程';
	                    else if (item.appitemno == 8) return '项目变价';
	                    else if (item.appitemno == 9) return '产品变价';
	                      return '';
	                }
	            },
                { display: '审核专员', name: 'checkcommissionertext', width:600,align: 'left'},
                { display: '审核经理', name: 'checkmanagertext', width:300,align: 'left'},
                { name: 'checkcommissioner', width:1,hide:true},
                { name: 'checkmanager', width:1,hide:true}
                ],  pageSize:20, 
                data:null,      
                width: ac004layout.centerWidth*1+ac004layout.leftWidth*1-30,
                height:'300',
                clickToEdit: false,   enabledEdit: false,  
                rownumbers:true,usePager: false,
                onSelectRow : function (data, rowindex, rowobj)
                {
                	curDetialRecord=data;
                    loadSelecDetialData(data, rowindex, rowobj);
                },
                toolbar: { items: [
                { text: '修改流程审核人员', click: updateMastInfo, img: contextURL+'/common/ligerui/ligerUI/skins/icons/modify.gif' }
                ]}     
            });
            loadInfo();
		    for(var i=0;i<parent.StaffInfo.length;i++)
			{
				addOption(parent.StaffInfo[i].bstaffno,parent.StaffInfo[i].bstaffno+"-"+parent.StaffInfo[i].staffname,document.getElementById("labCommissioner"));
				addOption(parent.StaffInfo[i].bstaffno,parent.StaffInfo[i].bstaffno+"-"+parent.StaffInfo[i].staffname,document.getElementById("labManager"));
			}	
          	$("#pageloading").hide(); 
   		}catch(e){alert(e.message);}
    });
    
    
	//加载审批信息信息
	function loadInfo()
    {
        try{
        	var params = "";				
     		var requestUrl ="ac004/loadDataInfos.action"; 
			var responseMethod="loadDataInfosMessage";		
			sendRequestForParams_p(requestUrl,responseMethod,params );
          }catch(e){alert(e.message);}
    }
    
    function loadDataInfosMessage(request)
    {
    	try
        {
    		var responsetext = eval("(" + request.responseText + ")");
	   		if(responsetext.lsStoreconfirmflow!=null && responsetext.lsStoreconfirmflow.length>0)
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: responsetext.lsStoreconfirmflow,Total: responsetext.lsStoreconfirmflow.length});
            	commoninfodivsecond.loadData(true); 
            	commoninfodivsecond.select(0);           	
	   		}
	   		else
	   		{
	   			commoninfodivsecond.options.data=$.extend(true, {},{Rows: null,Total:0});
            	commoninfodivsecond.loadData(true);      
	   		}
	   		
	   	}catch(e){alert(e.message);}
    }
    
    function loadComonInfo()
    {
    	for(var i=0;i<document.getElementById("labCommissioner").length;i++)
    	{
    		if(document.getElementById("labCommissioner")[i].selected==true)
    		{
    			var inserflag=1;
    			for(var j=0;j<document.getElementById("strcommissioner").length;j++)
    			{
    				if(document.getElementById("labCommissioner")[i].value==document.getElementById("strcommissioner")[j].value)
    				{
    					inserflag=0;
    					break;
    				}
    				inserflag=1;
    			}
    			if(inserflag==1)
    			{
    				addOption(document.getElementById("labCommissioner")[i].value,document.getElementById("labCommissioner")[i].innerText,document.getElementById("strcommissioner"));
    			}
    		}
    	}
    	var endCommisser="";
    	var tendCommisser="";
    	for(var j=0;j<document.getElementById("strcommissioner").length;j++)
    	{
    		tendCommisser=tendCommisser+document.getElementById("strcommissioner")[j].innerText+"  ";
    		endCommisser=endCommisser+document.getElementById("strcommissioner")[j].value+",";
    	}
    	commoninfodivsecond.updateRow(curDetialRecord,{checkcommissionertext: tendCommisser,checkcommissioner :endCommisser});  
    }
    
    function rloadComonInfo()
    {
    	
    	for(var i=0;i<document.getElementById("strcommissioner").length;i++)
    	{
    		if(document.getElementById("strcommissioner")[i].selected==true)
    		{
    			document.getElementById("strcommissioner").remove(i);
    			i--;
    		}
    	}
    	var endCommisser="";
    	var tendCommisser="";
    	for(var j=0;j<document.getElementById("strcommissioner").length;j++)
    	{
    		tendCommisser=tendCommisser+document.getElementById("strcommissioner")[j].innerText+"  ";
    		endCommisser=endCommisser+document.getElementById("strcommissioner")[j].value+",";
    	}
    	commoninfodivsecond.updateRow(curDetialRecord,{checkcommissionertext: tendCommisser,checkcommissioner :endCommisser});  
    }
    
    
    function loadManager()
    {
    	for(var i=0;i<document.getElementById("labManager").length;i++)
    	{
    		if(document.getElementById("labManager")[i].selected==true)
    		{
    			var inserflag=1;
    			for(var j=0;j<document.getElementById("strmanager").length;j++)
    			{
    				if(document.getElementById("labManager")[i].value==document.getElementById("strmanager")[j].value)
    				{
    					inserflag=0;
    					break;
    				}
    				inserflag=1;
    			}
    			if(inserflag==1)
    			{
    				addOption(document.getElementById("labManager")[i].value,document.getElementById("labManager")[i].innerText,document.getElementById("strmanager"));
    			}
    		}
    	}
    	var endCommisser="";
    	var tendCommisser="";
    	for(var j=0;j<document.getElementById("strmanager").length;j++)
    	{
    		tendCommisser=tendCommisser+document.getElementById("strmanager")[j].innerText+"  ";
    		endCommisser=endCommisser+document.getElementById("strmanager")[j].value+",";
    	}
    	commoninfodivsecond.updateRow(curDetialRecord,{checkmanagertext: tendCommisser,checkmanager :endCommisser});  
    }
    
    function rloadManager()
    {
    	
    	for(var i=0;i<document.getElementById("strmanager").length;i++)
    	{
    		if(document.getElementById("strmanager")[i].selected==true)
    		{
    			document.getElementById("strmanager").remove(i);
    			i--;
    		}
    	}
    	var endCommisser="";
    	var tendCommisser="";
    	for(var j=0;j<document.getElementById("strmanager").length;j++)
    	{
    		tendCommisser=tendCommisser+document.getElementById("strmanager")[j].innerText+"  ";
    		endCommisser=endCommisser+document.getElementById("strmanager")[j].value+",";
    	}
    	commoninfodivsecond.updateRow(curDetialRecord,{checkmanagertext: tendCommisser,checkmanager :endCommisser});  
    }
    
    function loadSelecDetialData(data, rowindex, rowobj)
    {
    	var staffnameComm="";
    	var staffnamemanger="";
    	clearOption("strcommissioner");
    	clearOption("strmanager");
    	if(checkNull(data.checkcommissioner)!="")
    	{
    		staffnameComm=checkNull(data.checkcommissioner).split(",");
    		staffnamemanger=checkNull(data.checkmanager).split(",");
    		if(staffnameComm!="" && staffnameComm.length>0)
    		{
    			for(var i=0;i<staffnameComm.length;i++)
    			{
    				for(var j=0;j<parent.StaffInfo.length;j++)
					{
						if(parent.StaffInfo[j].bstaffno==staffnameComm[i])
						{
							addOption(parent.StaffInfo[j].bstaffno,parent.StaffInfo[j].bstaffno+"-"+parent.StaffInfo[j].staffname,document.getElementById("strcommissioner"));
						}
					}	
    			}
    		}
    		
    		if(staffnamemanger!="" && staffnamemanger.length>0)
    		{
    			for(var i=0;i<staffnamemanger.length;i++)
    			{
    				for(var j=0;j<parent.StaffInfo.length;j++)
					{
						if(parent.StaffInfo[j].bstaffno==staffnamemanger[i])
						{
							addOption(parent.StaffInfo[j].bstaffno,parent.StaffInfo[j].bstaffno+"-"+parent.StaffInfo[j].staffname,document.getElementById("strmanager"));
						}
					}	
    			}
    		}
    	}
    	
    }
    
    function updateMastInfo()
    {
    	var appitemno=curDetialRecord.appitemno;
    	var checkcommissioner=curDetialRecord.checkcommissioner;
    	var checkmanager=curDetialRecord.checkmanager;
    	var checkcommissionertext=curDetialRecord.checkcommissionertext;
    	var checkmanagertext=curDetialRecord.checkmanagertext;
    	var requestUrl ="ac004/updateMastInfo.action";
        var params="appitemno="+appitemno;
        params = params+"&checkcommissioner="+checkcommissioner;
        params = params+"&checkmanager="+checkmanager;
        params = params+"&checkcommissionertext="+checkcommissionertext;
        params = params+"&checkmanagertext="+checkmanagertext;
		var responseMethod="updateMastInfoMessage";		
		sendRequestForParams_p(requestUrl,responseMethod,params ); 	
        
   	}
   	function updateMastInfoMessage(request)
    {	
    	 	var responsetext = eval("(" + request.responseText + ")");
	       	var strMessage=responsetext.strMessage;
	       	if(checkNull(strMessage)=="")
	       	{	       		 
	       		  $.ligerDialog.success("修改成功!");
	       	}
	       	else
	       	{
	       		$.ligerDialog.error(strMessage);
	       	}
    }