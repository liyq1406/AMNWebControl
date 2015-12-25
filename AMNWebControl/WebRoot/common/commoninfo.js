var CommonData=null;//常用资料
var CommonDataGrid=null;//常用资料明细显示
var CommonparentDataByGroup=null;//二级明细
var Sysrolemode=null;//权限资料
var Sysmodeinfo=null;//权限资料
var Useroverall=null;//用户权限资料
var lsCity;//市相关信息
var lsProvine;//省相关 
var lsArea;//县相关
var StaffInfo = null;
var lsProjectinfo = null;
var lsGoodsinfo = null;
var urlCommonInfo = "downControl/loadCommonController.action";
var urlSysrolemode = "downControl/loadSysrolemodeController.action";
var complinkInfo=null;
var localCompid="";
var lsCompanyinfos=null;
var dSp074Price=0;
var dSp075Price=0;
var dSp076Price=0;
var dSp077Price=0;
var dSp078Price=0;
var dSp079Price=0;


var dSp081Price=0;
var dSp082Price=0;
var dSp083Price=0;
var dSp084Price=0;
var dSp085Price=0;
var dSp086Price=0;
var dSp087Price=0;
var dSp088Price=0;
var dSp089Price=0;
var dSp090Price=0;
var dSp091Price=0;
var dSp092Price=0;
var commtype=0;
var commprot=9600;
var prot=9600;
var password1=171;
var password2=130;
var password3=239;
//下载常用资料

function downloadBaseCommonInfo()
{
	try{
			var params = null;
			var myAjax = new parent.Ajax.Request(
						urlCommonInfo,
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");
								localCompid=action.strCurCompId;
								CommonData=action.lsCommonData;
								CommonparentDataByGroup=action.lsCommonparentDataByGroup;
								loadCommonDataGrid(action.lsCommonDataByGroup);
								lsCompanyinfos=action.lsCompanyinfos;
								loadCompanlinkDataGrid(action.lsCompanyinfos,action.lsCompchainstructs);
								StaffInfo=action.lsStaffinfoDown;
								lsProjectinfo=action.lsProjectinfoDown;
								lsGoodsinfo=action.lsGoodsinfoDown;
								lsCity=action.a3citys;//市相关信息
								lsProvine=action.a3provinces;//省相关 
								lsArea=action.a3areas;//县相关
								dSp074Price=action.DSp074Price;
								dSp075Price=action.DSp075Price;
								dSp076Price=action.DSp076Price;
								dSp077Price=action.DSp077Price;
								dSp078Price=action.DSp078Price;
								dSp079Price=action.DSp079Price;
								
								dSp081Price=action.DSp081Price;
								dSp082Price=action.DSp082Price;
								dSp083Price=action.DSp083Price;
								dSp084Price=action.DSp084Price;
								dSp085Price=action.DSp085Price;
								dSp086Price=action.DSp086Price;
								dSp087Price=action.DSp087Price;
								dSp088Price=action.DSp088Price;
								dSp089Price=action.DSp089Price;
								dSp090Price=action.DSp090Price;
								dSp091Price=action.DSp091Price;
								dSp092Price=action.DSp092Price;
								commtype=action.commtype;
								commprot=action.commprot;
								
							},
							asynchronous:true
						});
		}
		catch(e)
		{
			alert(e.message);

		}
}
//下载权限资料
function downloadBaseSysrolemode()
{
	try{
			var params = null;
			var myAjax = new parent.Ajax.Request(
						urlSysrolemode,
						{
							method:'post',
							parameters:params,
							onComplete:function (request) {							
								var action = eval( "("+request.responseText+")");
								Sysrolemode=action.lsSysrolemode;
								Useroverall=action.lsUseroverall;
								Sysmodeinfo=action.lsSysmodeinfo;
								loadHomeUI();
							},
							asynchronous:true
						});
		}
		catch(e)
		{
			alert(e.message);

		}
}

function loadHomeUI()
{

}
function loadCommonDataGrid(obj)
{
try
{
	CommonDataGrid = {Rows:obj,Total:obj.size()};
	
	}catch(e){alert(e.message);}
}

downloadBaseCommonInfo();
downloadBaseSysrolemode();

function hasFunctionRights( moduleName,  right) 
{

	var bReturnValue=false;
	if(Sysrolemode!=null && Sysrolemode.size()>0)
	{
		for(var i=0;i<Sysrolemode.size();i++)
		{
			
			if(Sysrolemode[i].id.functionno==moduleName)
			{
		
				if (right.toUpperCase()=="UR_MODIFY" && Sysrolemode[i].editpurview=="Y") 
				{
					bReturnValue =  true;
	
					break;
				}
				else if (right.toUpperCase()=="UR_DELETE" && Sysrolemode[i].invalidpurview=="Y") 
				{
					bReturnValue =  true;
					break;
				}
				else if (right.toUpperCase()=="UR_QUERY" && Sysrolemode[i].browsepurview=="Y") 
				{
					bReturnValue =  true;
					break;
				}
				else if (right.toUpperCase()=="UR_PRINT" && Sysrolemode[i].exportpurview=="Y") 
				{
					bReturnValue =  true;
					break;
				}
				else if (right.toUpperCase()=="UR_POST" && Sysrolemode[i].postpurview=="Y") 
				{
					bReturnValue =  true;
					break;
				}
				else if (right.toUpperCase()=="UR_SPECIAL_CHECK" && Sysrolemode[i].confirmpurview=="Y") 
				{
					bReturnValue =  true;
					break;
				}
			}							
		}
	}
	return bReturnValue;
}

 function loadCompanlinkDataGrid(obj,lsCompchainstructs)
	 {
			try
			{
				var fristLv="";
				var secondLv="";
				var secondComps=[];
				var thirthLv="";
				var forthLv="";
				var ccount=0;
		
				for(var i=0;i<lsCompchainstructs.length;i++)
				{
					if(lsCompchainstructs[i].parentcompno==localCompid)
					{
					  	secondComps.push(lsCompchainstructs[i].curcompno);
					 }
				}
				
				
				for(var i=0;i<obj.length;i++)
				{
					if(obj[i].compno==localCompid)  //--最上级 总部
					{
						fristLv='[{"id":"'+obj[i].compno+'","text":"'+obj[i].compno+'-'+obj[i].compname+'"';
					}
					for(var j=0;j<secondComps.length;j++) //--第二级 分公司
					{
						if(obj[i].compno==secondComps[j])
						{
							thirthLv="";
							if(j!=0)
								secondLv=secondLv+',';
							secondLv=secondLv+'{"id":"'+obj[i].compno+'","text":"'+obj[i].compno+'-'+obj[i].compname+'"';	
							for(k=0;k<lsCompchainstructs.length;k++) //--第三级 地区
							{
								if(lsCompchainstructs[k].parentcompno==secondComps[j])
								{
									if(thirthLv!="")
										thirthLv=thirthLv+',';
									thirthLv=thirthLv+'{"id":"'+lsCompchainstructs[k].curcompno+'","text":"'+lsCompchainstructs[k].curcompno+'-'+lsCompchainstructs[k].curcompname+'"';	
						  			forthLv="";
						  			for(l=0;l<lsCompchainstructs.length;l++) //--第四级 门店
									{
										if(lsCompchainstructs[k].curcompno==lsCompchainstructs[l].parentcompno)
										{
											if(forthLv!="")
												forthLv=forthLv+',';
											forthLv=forthLv+'{"id":"'+lsCompchainstructs[l].curcompno+'","text":"'+lsCompchainstructs[l].curcompno+'-'+lsCompchainstructs[l].curcompname+'"}';	
										}
									}
						  			if(forthLv!="")
									{
										thirthLv=thirthLv+',"children": ['+forthLv+']}';
									}	
									else
									{		
										thirthLv=thirthLv+'}';
									}
					 			}
							}
							if(thirthLv!="")
							{
								secondLv=secondLv+',"children": ['+thirthLv+']}';
							}	
							else
							{		
								secondLv=secondLv+'}';
							}
						}
					
					}
				}
				if(secondLv!="")
				{
					fristLv=fristLv+',"children": ['+secondLv+']}]';
				}
				else
				{
					fristLv=fristLv+'}]';
				}
				complinkInfo=JSON.parse(fristLv);
				
				}catch(e){alert(e.message);}
		}
	//常用基本资料结构体
	function LocalCommonInfo(infotype, bparentcodekey, parentcodevalue,bcodekey,codevalue) 
	{
		this.infotype = infotype;
		this.bparentcodekey = bparentcodekey;
		this.parentcodevalue = parentcodevalue;
		this.bcodekey = bcodekey;
		this.codevalue = codevalue;
	}
	//生成常用基本资料的下拉列表控件 lvl 0 parentcodekey 1 codekey
	function CommonControl(code,controlId,dom,width,lvl)
	{
		var items = gainCommonInfoByCode(code,lvl);
		dom.writeln("<select style='width:80px;' id='"+controlId+"' name='"+controlId+"'>");
		dom.writeln("<option value='' selected=\"true\"></option>");
		var key ="";
		var value="";
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			dom.writeln("<option value=''+key+''>"+key+"-"+value+"</option>");
	
		}
		dom.writeln("</select>");
	}
	
	
	function loadCommonControlValue(code,lvl,key)
	{
		var returnValue='';
		var items = gainCommonInfoByCode(code,lvl);
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				if(key==items[i].bparentcodekey)
					return items[i].parentcodevalue;
			}
			else
			{
				if(key==items[i].bcodekey)
					return items[i].codevalue;
			}
			
		}
	
	}
	
	function loadCommonControlDate(code,lvl)
	{
		var returnValue='';
		var items = gainCommonInfoByCode(code,lvl);
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			returnValue=returnValue+'{"id": "'+key+'","text": "'+value+'"}';
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
	
	
	function loadCommonControlDateCash(code,lvl)
	{
		var returnValue='';
		var items = gainCommonInfoByCode(code,lvl);
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				if(items[i].bparentcodekey!="1" 
				&& items[i].bparentcodekey!="2"
				&& items[i].bparentcodekey!="6"
				&& items[i].bparentcodekey!="14"
				&& items[i].bparentcodekey!="15")
				{
					continue;
				}
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			returnValue=returnValue+'{"id": "'+key+'","text": "'+value+'"}';
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
	
	function loadCommonControlDate_select(code,lvl)
	{
		var returnValue='';
		var items = gainCommonInfoByCode(code,lvl);
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+"-"+value+'"}';
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
	//带前缀
	function loadCommonControlDate_selectdree(code,lvl)
	{
		var returnValue='';
		var items = gainCommonInfoByCode(code,lvl);
		for(var i=0;i<items.length;i++)
		{
		    if(lvl*1==0)
			{
				key = items[i].bparentcodekey;
				value = items[i].parentcodevalue;
			}
			else
			{
				key = items[i].bcodekey;
				value = items[i].codevalue;
			}
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
		}
		if(returnValue!='')
			returnValue=returnValue+']';	
		return 	returnValue;
	}
	//---------------------------------------------------------------
	//以下为常用基本资料相关函数
	function gainCommonInfoByCode(code,lvl)
	{
		var items = new Array();
		if(lvl*1==0)
		{
			for(var i=0;i<CommonparentDataByGroup.length;i++)
			{
				if(CommonparentDataByGroup[i].binfotype == code)
				{
					var item = new LocalCommonInfo(	CommonparentDataByGroup[i].binfotype, 
												  	CommonparentDataByGroup[i].bparentcodekey, 
												  	CommonparentDataByGroup[i].parentcodevalue,
												  	CommonparentDataByGroup[i].bcodekey, 
												  	CommonparentDataByGroup[i].codevalue
												  );
					items.push(item);
				}
			}
		}
		else
		{
			for(var i=0;i<CommonData.length;i++)
			{
				if(CommonData[i].binfotype == code)
				{
					var item = new LocalCommonInfo(	CommonData[i].binfotype, 
												  	CommonData[i].bparentcodekey, 
												  	CommonData[i].parentcodevalue,
												  	CommonData[i].bcodekey, 
												  	CommonData[i].codevalue
												  );
					items.push(item);
				}
			}
		}
		
	
		return items;
	}
	
	function gainCommonInfoByCodeByUse(code,lvl,useflag)
	{
		var items = new Array();
		if(lvl*1==0)
		{
			for(var i=0;i<CommonparentDataByGroup.length;i++)
			{
				if(CommonparentDataByGroup[i].binfotype == code)
				{
					var item=null;
					if(useflag==0)  //所有
					{
						item= new LocalCommonInfo(	CommonparentDataByGroup[i].binfotype, 
												  	CommonparentDataByGroup[i].bparentcodekey, 
												  	CommonparentDataByGroup[i].parentcodevalue,
												  	CommonparentDataByGroup[i].bcodekey, 
												  	CommonparentDataByGroup[i].codevalue
												  );
						items.push(item);
					}
					else 
					{
						if(CommonparentDataByGroup[i].useflag==0 || CommonparentDataByGroup[i].useflag==useflag )
						{
							item= new LocalCommonInfo(	CommonparentDataByGroup[i].binfotype, 
												  	CommonparentDataByGroup[i].bparentcodekey, 
												  	CommonparentDataByGroup[i].parentcodevalue,
												  	CommonparentDataByGroup[i].bcodekey, 
												  	CommonparentDataByGroup[i].codevalue
												  );
							items.push(item);
						}
					} 
				}
			}
		}
		else
		{
			for(var i=0;i<CommonData.length;i++)
			{
				if(CommonData[i].binfotype == code)
				{
					var item = new LocalCommonInfo(	CommonData[i].binfotype, 
												  	CommonData[i].bparentcodekey, 
												  	CommonData[i].parentcodevalue,
												  	CommonData[i].bcodekey, 
												  	CommonData[i].codevalue
												  );
					items.push(item);
				}
			}
		}
		
	
		return items;
	}
	
	//获取员工姓名
	function loadStaffNameValue(staffId)
	{
		for(var i=0;i<StaffInfo.length;i++)
		{
		  if(staffId==StaffInfo[i].bstaffno)
			return StaffInfo[i].staffname;
		}
		return staffId;
	}
	
	 //初始化员工下拉表
   	function loadGridByStaffInfo(StaffInfo,type)
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
					{
							if(type==1)//业务人员
							{
								if(	StaffInfo[i].position!="00901" &&
									StaffInfo[i].position!="00902" &&
									StaffInfo[i].position!="00903" &&
									StaffInfo[i].position!="00904" &&
									StaffInfo[i].position!="008" 
								)
								{
									ccount=ccount*1+1;
									if(ccount==1)
									{
										strJson=strJson+'[';
									}
									else
									{
										strJson=strJson+',';
									}
									strJson=strJson+'{ "id":"'+StaffInfo[i].bstaffno+'", "text": "'+StaffInfo[i].bstaffno+'_'+StaffInfo[i].staffname+'"}';
								
								}
							}
							else
							{
								if(	StaffInfo[i].position=="00901" ||
									StaffInfo[i].position=="00902" ||
									StaffInfo[i].position=="00903" ||
									StaffInfo[i].position=="00904" ||
									StaffInfo[i].position=="008" 
								)
								{
									ccount=ccount*1+1;
									if(ccount==1)
									{
										strJson=strJson+'[';
									}
									else
									{
										strJson=strJson+',';
									}
									strJson=strJson+'{ "id":"'+StaffInfo[i].bstaffno+'", "text": "'+StaffInfo[i].bstaffno+'_'+StaffInfo[i].staffname+'"}';
								
								}
							}
							
							
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}
   
   
   //初始化员工下拉表
   	function loadGridByStaffInfo_grid(StaffInfo,type)
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
					{
							if(type==1)//业务人员
							{
								if(	StaffInfo[i].position!="00901" &&
									StaffInfo[i].position!="00902" &&
									StaffInfo[i].position!="00903" &&
									StaffInfo[i].position!="00904" &&
									StaffInfo[i].position!="008" 
								)
								{
									ccount=ccount*1+1;
									if(ccount==1)
									{
										strJson=strJson+'[';
									}
									else
									{
										strJson=strJson+',';
									}
									strJson=strJson+'{ "choose":"'+StaffInfo[i].bstaffno+'", "text": "'+StaffInfo[i].bstaffno+'_'+StaffInfo[i].staffname+'"}';
								
								}
							}
							else
							{
								if(	StaffInfo[i].position=="00901" ||
									StaffInfo[i].position=="00902" ||
									StaffInfo[i].position=="00903" ||
									StaffInfo[i].position=="00904" ||
									StaffInfo[i].position=="008" 
								)
								{
									ccount=ccount*1+1;
									if(ccount==1)
									{
										strJson=strJson+'[';
									}
									else
									{
										strJson=strJson+',';
									}
									strJson=strJson+'{ "choose":"'+StaffInfo[i].bstaffno+'", "text": "'+StaffInfo[i].bstaffno+'_'+StaffInfo[i].staffname+'"}';
								
								}
							}
							
							
					}
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}
	
	function loadProjectControlDate_select(curWriteItemNo)
	{
		try{
		var returnValue='';
		var key='';
		var value='';
		var projectcount=0;
		for(var i=0;i<lsProjectinfo.length;i++)
		{
			if(projectcount*1==10)
			{
				break;
			}
			if(curWriteItemNo!="" 
			 && lsProjectinfo[i].id.prjno.indexOf(curWriteItemNo)==-1
			 && checkNull(lsProjectinfo[i].prjabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1
			 && checkNull(lsProjectinfo[i].prjname).indexOf(curWriteItemNo.toUpperCase())==-1)
			{
				continue;
			}
		   	key = lsProjectinfo[i].id.prjno;
			value = lsProjectinfo[i].prjname;
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			
			projectcount=projectcount*1+1;
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';	
			return 	JSON.parse(returnValue);
		}
		else
		{	
			return null;
		}
		}catch(e){alert(e.message);}
	}
   
   
   	function loadGoodsControlDate_select(curWriteItemNo)
	{
		
		var returnValue='';
		var key='';
		var value='';
		var goodscount=0;
		for(var i=0;i<lsGoodsinfo.length;i++)
		{
			if(goodscount*1==10)
			{
				break;
			}
			if(curWriteItemNo!="" 
			&& lsGoodsinfo[i].id.goodsno.indexOf(curWriteItemNo)==-1
			&& checkNull(lsGoodsinfo[i].goodsabridge).toLowerCase().indexOf(curWriteItemNo.toLowerCase())==-1 )
			{
				continue;
			}
		   	key = lsGoodsinfo[i].id.goodsno;
			value = lsGoodsinfo[i].goodsname;
			if(returnValue!='')
			{
				returnValue=returnValue+',';
			}
			else
			{
				returnValue=returnValue+'[';
			}
			goodscount=goodscount*1+1;
			returnValue=returnValue+'{"choose": "'+key+'","text": "'+key+'_'+value+'"}';
		}
		if(returnValue!='')
		{
			returnValue=returnValue+']';	
			return 	JSON.parse(returnValue);
		}
		else
		{
			return null;
		}
	}
	