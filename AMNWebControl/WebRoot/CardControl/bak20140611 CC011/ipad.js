function loadPadInfo()
{
	window.setInterval('loadpadBill()',10000);
}

function loadpadBill()
{
	 
		var requestUrl ="cc011/loadPadBillMaster.action"; 
		var responseMethod="loadPadBillMasterMessage";				
		sendRequestForParams_p(requestUrl,responseMethod,"" );
		
}

var curpadindex=0;
var lsSpadDconsumeInfo=null;
var curfirstemp="";
var cursecondemp="";
var curthirdemp="";
var curisnew="2";
var curisnew2="2";
var curisnew3="2";

function loadPadBillMasterMessage(request)
{
     try
     {
        commoninfodivdetial_padInfo.options.data=$.extend(true, {},{Rows: null,Total:0});
        commoninfodivdetial_padInfo.loadData(true);
        var responsetext = eval("(" + request.responseText + ")");
    	if(responsetext.lsSpadMconsumeInfo!=null && responsetext.lsSpadMconsumeInfo.length>0)
	   	{
	   		commoninfodivdetial_padInfo.options.data=$.extend(true, {},{Rows: responsetext.lsSpadMconsumeInfo,Total: responsetext.lsSpadMconsumeInfo.length});
            commoninfodivdetial_padInfo.loadData(true);     	
	   	}
	   	else
	   	{
            addProRecord_padInfo();  
	   	}
	 }catch(e){alert(e.message);}
} 

function loadPadDetialInfo(data)
{
	//alert(data.custom);
	//alert(data.smallno);
		if(document.getElementById("cscardno").value=="散客")
		{
			$.ligerDialog.confirm('确认当前消费单为散客单?', function (result)
			{
			    if( result==true)
	           	{
					var requestUrl ="cc011/loadPadBillDetial.action"; 
					var responseMethod="loadPadBillDetialMessage";
					var params="strPadBillId="+data.smallno;	
					params=params+"&strPadKeyId="+data.custom;		
					sendRequestForParams_p(requestUrl,responseMethod,params );
				}
				
			}); 
		}
		else
		{
			var requestUrl ="cc011/loadPadBillDetial.action"; 
			var responseMethod="loadPadBillDetialMessage";
			var params="strPadBillId="+data.smallno;	
			params=params+"&strPadKeyId="+data.custom;		
			sendRequestForParams_p(requestUrl,responseMethod,params );
		}
			
		
}

function loadPadBillDetialMessage(request)
{
     try
     {
     	lsSpadDconsumeInfo=null;
        curpadindex=0;
        commoninfodivdetial.options.data=$.extend(true, {},{Rows: null,Total:0});
        commoninfodivdetial.loadData(true);  
        var responsetext = eval("(" + request.responseText + ")");
    	if(responsetext.lsSpadDconsumeInfo!=null && responsetext.lsSpadDconsumeInfo.length>0)
	   	{
	   		lsSpadDconsumeInfo=responsetext.lsSpadDconsumeInfo;
	   		loadDetial(curpadindex);
	   	}
	   	
	 }catch(e){alert(e.message);}
} 


function loadDetial(i)
{
	if(i<lsSpadDconsumeInfo.length)
	{
		addRecord();
		if(checkNull(lsSpadDconsumeInfo[i].sortid)==1)
		{	
	   		validatePadProjectNo(lsSpadDconsumeInfo[i].code,lsSpadDconsumeInfo[i].amount,
	   		lsSpadDconsumeInfo[i].price,lsSpadDconsumeInfo[i].employeeno,
	   		lsSpadDconsumeInfo[i].employeeno2,lsSpadDconsumeInfo[i].employeeno3,
	   		lsSpadDconsumeInfo[i].isnew,lsSpadDconsumeInfo[i].isnew2,lsSpadDconsumeInfo[i].isnew3);
		}
		else
		{
			curpadindex=curpadindex*1+1;
			loadDetial(curpadindex);
		}
	}
}

	function loadSpadProCost(curCostProSeqno,firstemp,secondemp,thirdemp,isnew,isnew2,isnew3)
	{
			var curcostcount=1;
		 	var detiallength=commoninfodivdetial.rows.length*1;
		    var seqnoflag=0
			for (var rowid in commoninfodivdetial_Pro.records)
			{
				var row =commoninfodivdetial_Pro.records[rowid]; 
				if(row.bproseqno!=curCostProSeqno)
				{
					continue;
				}
				if(detiallength==0)
				{
					addRecord();
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
				}
				var curlastcount=ForDight(row.lastcount,1);
				var curlastamt=ForDight(row.lastamt,1);
				var curprice=ForDight(curlastamt/curlastcount,1)
				var curcostamt=ForDight(curprice*1*curcostcount*1,1);
				var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemname: row.bprojectname,csitemno:row.bprojectno,csitemcount:ForDight(curcostcount,1),
	     		csunitprice:curprice,csdisprice :curprice,csitemamt:ForDight(curcostamt,0),csdiscount:1,cspaymode:'9',csfirstsaler:firstemp,csfirsttype:isnew,csfirstshare:'',
	     		cssecondsaler:secondemp,cssecondtype:isnew2,cssecondshare:'',csthirdsaler:thirdemp,csthirdtype:isnew3,csthirdshare:'',csproseqno:row.bproseqno});  
	   	 	}
	   	 	handPayList();
	   	 	curpadindex=curpadindex*1+1;
		    loadDetial(curpadindex);
	}
	
	  function handFastLoad_spad(obj,objtype,objPrice,pricetype,firstemp,secondemp,thirdemp)
	  {
	    		var paymode="1";
		    	if(document.getElementById("billflag").value=="0")
	     		{
	     			paymode="1";
	     		}
	     		else if(document.getElementById("billflag").value=="1")
	     		{
	     			paymode="4";
	     		}
	     		else
	     		{
	     			paymode="A";
	     		}
		    	if(objtype==1)
		    	{
		    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
							for(var i=0;i<parent.lsProjectinfo.length;i++)
							{
								if(parent.lsProjectinfo[i].id.prjno==obj)
				 				{
				 					fastLoadPriject_pad(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									eval("(parent." + objPrice + ")")*1 ,
				 									eval("(parent." + objPrice + ")")*1*document.getElementById("dXjcDiscount").value*1 ,document.getElementById("dXjcDiscount").value*1,paymode,firstemp,secondemp,thirdemp);
				 				}
				
							}
						}
		    	}
		    	else
		    	{
		    			if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
						{
							for(var i=0;i<parent.lsProjectinfo.length;i++)
							{
								if(parent.lsProjectinfo[i].id.prjno==obj)
				 				{
				 					if(pricetype==1)
				 					{
				 						fastLoadPriject_pad(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									eval("(parent." + objPrice + ")") ,
				 									eval("(parent." + objPrice + ")") ,1,paymode,firstemp,secondemp,thirdemp);
				 				    }
				 				    else
				 				    {
				 				    	if(objPrice*1==0)
				 				    	{
				 				    		$.ligerDialog.error("不能添加单价为0 的项目！");
				 				    	}
				 				    	else
				 				    	{
				 				    		fastLoadPriject_pad(obj,
				 									parent.lsProjectinfo[i].prjname,1,
				 									objPrice ,
				 									objPrice ,1,paymode,firstemp,secondemp,thirdemp);
				 						}
				 				    }
				 				}
				
							}
						}
		    	}
	}
	
	function fastLoadPriject_pad(projectno,projectname,fcount,curprice,curcostamt,discount,paymode,firstemp,secondemp,thirdemp)
	{
	    	try
	    	{
	    		
	    		var detiallength=commoninfodivdetial.rows.length*1;
		    	if(detiallength==0)
				{
					addRecord();
					//detiallength=detiallength+1;
				}
				else if( commoninfodivdetial.getRow(0)['csitemname']!="")
				{
					addRecord();
					//detiallength=detiallength+1;
				}
				//curRecord=commoninfodivdetial.getRow(detiallength-1);
				var rowdata=commoninfodivdetial.updateRow(curRecord,{csitemno: projectno,
																	 csitemname: projectname,
																	 csitemcount: ForDight(fcount,1),
	     															 csunitprice: ForDight(curprice,1),
	     															 csdisprice: ForDight(curprice,1),
	     															 csitemamt:  ForDight(curcostamt,0),
	     															 csdiscount: ForDight(discount,2),
	     															 cspaymode: paymode,
	     															 csfirstsaler: firstemp,
	     															 csfirsttype:'2',
	     															 csfirstshare:'',
	     															 cssecondsaler: secondemp,
	     															 cssecondtype:'2',
	     															 cssecondshare:'',
	     															 csthirdsaler: thirdemp,
	     															 csthirdtype:'2',
	     															 csthirdshare:'',
	     															 csproseqno:0});  
	   	 		handPayList();
				curpadindex=curpadindex*1+1;
				loadDetial(curpadindex);
			}catch(e){alert(e.message);}
	}
	    
	function validatePadProjectNo(projectNo,amount,price,firstemp,secondemp,thirdemp,isnew,isnew2,isnew3)
	{
				if(projectNo.indexOf("_")>0 &&  document.getElementById("cscardno").value=="散客") //疗程消费
				{
					$.ligerDialog.error("散客单不能消费疗程项目,请确认!");
		     		return;	
				}
				if(projectNo.indexOf("_")>0)
				{
					var proSeqno=projectNo.substring(0,projectNo.indexOf("_"));
					loadSpadProCost(proSeqno,firstemp,secondemp,thirdemp,isnew,isnew2,isnew3);
					
		     		return;
				}
				if(projectNo=="302" || projectNo=="303" || projectNo=="305" || projectNo=="306")
				{
					var paymode="1";
			    	if(document.getElementById("billflag").value=="0")
		     		{
		     			paymode="1";
		     		}
		     		else if(document.getElementById("billflag").value=="1")
		     		{
		     			paymode="4";
		     		}
		     		else
		     		{
		     			paymode="A";
		     		}
					if(parent.lsProjectinfo!=null && parent.lsProjectinfo.length>0)
					{
						for(var i=0;i<parent.lsProjectinfo.length;i++)
						{
							if(parent.lsProjectinfo[i].id.prjno==projectNo)
							{
		     					commoninfodivdetial.updateRow(curRecord,{csitemno: parent.lsProjectinfo[i].id.prjno,
																	 csitemname: parent.lsProjectinfo[i].prjname,
																	 csitemcount: ForDight(amount,1),
	     															 csunitprice: ForDight(parent.lsProjectinfo[i].saleprice,1),
	     															 csdisprice: ForDight(parent.lsProjectinfo[i].saleprice,1),
	     															 csitemamt:  ForDight(price,0),
	     															 csdiscount: 1,
	     															 cspaymode: paymode,
	     														
	     															 csfirstshare:'',
	     															
	     															 cssecondshare:'',
	     															 csfirstsaler: firstemp,
	     															 cssecondsaler: secondemp,
	     															 csthirdsaler: thirdemp,
	     															 csfirsttype:isnew,
	     															 cssecondtype:isnew2,
	     															 csthirdtype:isnew3,
	     															 csthirdshare:'',
	     															 csproseqno:0});  
	   	 					
		     					break;
							}
						}
					}
					handPayList();
		    		curpadindex=curpadindex*1+1;
		     		loadDetial(curpadindex);
		    		return;	
				}
				
				curfirstemp=firstemp;
				cursecondemp=secondemp;
				curthirdemp=thirdemp;
				isnew=curisnew;
				isnew2=curisnew2;
				isnew3=curisnew3;
				var requestUrl ="cc011/validateItem.action"; 
				var responseMethod="validatePadFastItemMessage";
				var params="itemType=1";
				var params=params+"&strCurItemId="+projectNo;	
				var params=params+"&strCardType="+document.getElementById("cscardtype").value;
				params=params+"&cardUseType="+document.getElementById("billflag").value;		
				sendRequestForParams_p(requestUrl,responseMethod,params );
	}


		function validatePadFastItemMessage(request)
		{
			try
			{
		     	var responsetext = eval("(" + request.responseText + ")");
		     	var dCostProjectRate=ForDight(responsetext.DCostProjectRate,2);
		     	var curProjectinfo=responsetext.curProjectinfo;
		     	if(curProjectinfo==null)
		     	{
		     		$.ligerDialog.error("输入的项目编码不存在!");
		     		
		     	}
		     	else
		     	{
		     		var dstandprice=ForDight(checkNull(responsetext.dstandprice),2)*1;   //标准价
		     		var donecountprice=ForDight(checkNull(responsetext.donecountprice),2)*1; //单次价
		     		var donepageprice=ForDight(checkNull(responsetext.donepageprice),2)*1; //散客价
		     		var dmemberprice=ForDight(checkNull(responsetext.dmemberprice),2)*1; //会员价
		     		var dcuxiaoprice=ForDight(checkNull(responsetext.dcuxiaoprice),2)*1; //促销价
		     	    var facttyprice=0;//实际促销价
		     		document.getElementById("itemprice").value=checkNull(curProjectinfo.saleprice);
		     		document.getElementById("wCostItemName").value=checkNull(curProjectinfo.prjname);
		     		document.getElementById("wCostPCount").value=1;
		     		if(document.getElementById("billflag").value=="0")
     				{
     					facttyprice=donepageprice;
     					document.getElementById("itemdiscount").value=1;
     					document.getElementById("itempay").value="1";
     				}
     				else if(document.getElementById("billflag").value=="1")
     				{
     					facttyprice=dmemberprice;
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="4";
     				}
     				else
     				{
     					facttyprice=dmemberprice;
     					document.getElementById("itemdiscount").value=dCostProjectRate;
     					document.getElementById("itempay").value="A";
     				}
     				
     				
     				document.getElementById("wCostPCount").focus();
			 		document.getElementById("wCostPCount").select();
			 		
			 		var usePriceFlag=0;
			 		var csdisprice=dstandprice*1;
			 		var factcostprice=ForDight(dstandprice*1*dCostProjectRate*1,1);
		     		if(dcuxiaoprice*1>0 )
		     		{
		     			factcostprice=dcuxiaoprice*1;
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice> ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{
		     				factcostprice= ForDight(dstandprice*1*dCostProjectRate*1,1);
		     			}
		     			if(dcuxiaoprice*1>0 && dstandprice*1>0 && dcuxiaoprice<= ForDight(dstandprice*1*dCostProjectRate*1,1) )
		     			{	
		     				dCostProjectRate=1;
		     				factcostprice= ForDight(dcuxiaoprice*1,1);
		     				csdisprice=factcostprice;
		     			}
		     		}
		     		else
		     		{
		     			
		     			if(facttyprice*1>0 && donecountprice*1==0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=facttyprice*1;
		     			}
		     			else if(facttyprice*1==0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				factcostprice=donecountprice*1;
		     			}
		     			else if(facttyprice*1>0 && donecountprice*1>0)
		     			{
		     				dCostProjectRate=1;
		     				usePriceFlag=1;
		     			}
		     		}
		     		if(usePriceFlag==0)
		     		{
				 		var curNewRecord=commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,
				 														 csitemno: curProjectinfo.id.prjno,
																		 csitemname: checkNull(curProjectinfo.prjname),
																		 csitemcount: 1,
		     															 csunitprice: checkNull(dstandprice)*1,
		     															 csdisprice: checkNull(csdisprice)*1,
		     															 csitemamt:  ForDight(factcostprice*1,0),
		     															 csdiscount: ForDight(dCostProjectRate,2),
		     															 cspaymode: document.getElementById("itempay").value,
		     															 csfirstsaler: curfirstemp,
	     															 	 cssecondsaler: cursecondemp,
	     															 	 csthirdsaler: curthirdemp,
	     																 csfirsttype:curisnew,
	     															 	 cssecondtype:curisnew2,
	     															 	 csthirdtype:curisnew3,
		     															 csproseqno:0,shareflag:0}); 
			     		handPayList();
			     		if(checkNull(curProjectinfo.needhairflag)==1)
			     		{
			     			$.ligerDialog.confirmXJC('若需要洗头,请确认 [洗头] 类型,若不用直接关闭窗口', function (result)
							{
								var factnedamt=ForDight(20*1*document.getElementById("dXjcDiscount").value*1,0);
						   	 	if( result==true)
				           		{
				           			commoninfodivdetial.updateRow(curNewRecord,{ csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0),csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
									handFastLoad_spad("300",2,factnedamt,2,"","","");
								}
								else
				           		{
				           			commoninfodivdetial.updateRow(curNewRecord,{csdisprice:  ForDight(checkNull(curNewRecord.csdisprice)*1-20,0), csitemamt:  ForDight(checkNull(curNewRecord.csitemamt)*1-factnedamt,0),shareflag:1 });  
									handFastLoad_spad("3002",2,factnedamt,2,"","","");
									
								}
							}); 
			     		}
			     		else
			     		{
			     			curpadindex=curpadindex*1+1;
							loadDetial(curpadindex);
			     		}
			     		
		     		}
		     		else
		     		{
		     			$.ligerDialog.confirmXPrice('请选择价格类型', function (result)
						{
						   	 	if( result==true)
				           		{	
				           			factcostprice=facttyprice*1;
								}
								else
				           		{
				           			factcostprice=donecountprice*1;
									
								}
								factcostprice=factcostprice
								var curNewRecord=commoninfodivdetial.updateRow(curRecord,{bcsinfotype:1,
				 														 csitemno: curProjectinfo.id.prjno,
																		 csitemname: checkNull(curProjectinfo.prjname),
																		 csitemcount: 1,
		     															 csunitprice: checkNull(dstandprice)*1,
		     															 csdisprice: checkNull(factcostprice)*1,
		     															 csitemamt:  ForDight(factcostprice*1,0),
		     															 csdiscount: ForDight(dCostProjectRate,1),
		     															 cspaymode: document.getElementById("itempay").value,
		     															 csfirstsaler: curfirstemp,
	     															 	 cssecondsaler: cursecondemp,
	     															 	 csthirdsaler: curthirdemp,
	     																 csfirsttype:curisnew,
	     															 	 cssecondtype:curisnew2,
	     															 	 csthirdtype:curisnew3,
		     															 csproseqno:0,shareflag:0});  
		  											 
			     				handPayList();
							    curpadindex=curpadindex*1+1;
							    loadDetial(curpadindex);
						}); 
		     		}
		     		if(checkNull(curProjectinfo.editflag)==1)
		     		{
		     			$.ligerDialog.prompt('输入实际消费金额','', function (yes,value) { if(yes) loadEditPrice(value) });
		     		}
		     		
		     	}
		     }catch(e){alert(e.message);}
	
		}
