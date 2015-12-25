/**
 *  工程通用函数
 *  注意有Ajax 事件的话，必须包含该js文件
 */
 
/** Ajax 针对Form/某一参数 通用函数 **/
function sendRequest(requestUrl, responseMethod, formName) {
//	var params = Form.serialize( paraName );
	var params = Form.serialize( formName );
	sendRequestForParams(requestUrl, responseMethod, params);
	
}
/** Ajax 针对参数通用函数 **/
function sendRequestForParams(requestUrl, responseMethod, params) {
	var myAjax = new Ajax.Request(requestUrl, {method:"post", parameters:params, onComplete:eval(responseMethod), asynchronous:true});
}

/** Ajax 针对参数通用函数 **/
function sendRequestForParams_p(requestUrl, responseMethod, params) {
	var myAjax = new parent.Ajax.Request(requestUrl, {method:"post", parameters:params, onComplete:eval(responseMethod), asynchronous:true});
}

function fullStr( str )
{
		if( str.length == 1 )
		{
			str = "0" + str;
		}
		return str;
}
//去除空格
function trim(str)
{   
    return str.replace( /^\str*/, "" ).replace( /\str*$/, "" );
}
/** 检查radio , 设置check 处 **/
function handleRadio( radioName, radioValue )
{
	if( null == radioValue )
	{
		radioValue = 0 ;
	} 
	var radios = document.getElementsByName( radioName );
	for(var index=0; index<radios.length; index++) 
	{
		if( radios[index].value == radioValue )
		{
			radios[index].checked = "checked";
			break;
		}
	}
}

function loadRadiovalue( radioName )
{
	
	var radios = document.getElementsByName( radioName );
	for(var index=0; index<radios.length; index++) 
	{
		if( radios[index].checked == true )
		{
			return radios[index].value;
			break;
		}
	}
}

function checkNull(strValue)
{
	if(strValue == null)
	{
		return "";
	}
	else
	{
		return strValue;
	}
}
/** 判断字符串是否为null **/
function checkChar(strValue) 
{
	if(strValue == null)
	{
		return "";
	}
	else
	{
		return strValue;
	}
}

function beforloadSleep(nMillis)
{
	var   dt1   =   new   Date();   
    for(;;)   
    {   
  		var   dt2   =   new   Date();   
  		if((dt2.getTime()-dt1.getTime())   >=   nMillis)  
  		{ 
  			break;   
      	}
     }
}

function validatePrice(obj)
{
	if( ! isNumber(obj.value.toString().replace("-","")) )
    {
		
		obj.value=0;
		obj.focus();
		obj.select();
	}
}
/** 是否都是数字 **/
function isNumber(sNum) {
	  var   re   =   /^\d+(?=\.\d+$|$)/   
      return   re.test(sNum);
}


function ForDight(Dight,How) {
   Dight = Math.round(Dight*Math.pow(10,How))/Math.pow(10,How); 
   return Dight; 
}    


//员工下拉列表
   	function loadGridByStaffInfo(StaffInfo,curWriteStaff)
	{

			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
					{
						
						if(curWriteStaff!="" && StaffInfo[i].bstaffno.indexOf(curWriteStaff)==-1)
						{
							continue;
						}
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
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}

	//初始化员工下拉表区分烫染和美容美发师
   	function loadOtherGridByStaffInfo(StaffInfo,curWriteStaff,type)
	{
			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
					{
						if(curWriteStaff!="" && StaffInfo[i].bstaffno.indexOf(curWriteStaff)==-1)
						{
							continue;
						}
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
   
   //兼容在表格中的下拉
   	function loadGridChooseByStaffInfo(StaffInfo,curWriteStaff)
	{

			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(StaffInfo!=null && StaffInfo.length>0)
				{	
					for(var i=0;i<StaffInfo.length;i++)
					{
						
						if(curWriteStaff!="" && StaffInfo[i].bstaffno.indexOf(curWriteStaff)==-1)
						{
							continue;
						}
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
					if(strJson!="")
					{
						strJson=strJson+']';
						return JSON.parse(strJson);
					}
					return null;
					 
				}
			
			}catch(e){alert(e.message);}
	}
	
	//兼容在表格中的下拉(常用资料)
	function loadOtherGridByCommonInfo(items,lvl,curWriteStaff)
	{
		var returnValue='';
		var key="";
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
			if(curWriteStaff!="" && key.indexOf(curWriteStaff)==-1)
			{
				continue;
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
		return JSON.parse(returnValue);
	}
	
	//兼容在表格中的下拉(卡类型)
   	function loadGridChooseByCardType(lsCardTypeInfo,curWriteStaff)
	{

			try
			{
				var strJson = "";//'{ "name": "cxh", "sex": "man" }';
				var ccount=0;
				if(lsCardTypeInfo!=null && lsCardTypeInfo.length>0)
				{	
					for(var i=0;i<lsCardTypeInfo.length;i++)
					{
						
						if(curWriteStaff!="" && lsCardTypeInfo[i].id.cardtypeno.indexOf(curWriteStaff)==-1)
						{
							continue;
						}
						ccount=ccount*1+1;
						if(ccount==1)
						{
							strJson=strJson+'[';
						}
						else
						{
							strJson=strJson+',';
						}
						strJson=strJson+'{ "choose":"'+lsCardTypeInfo[i].id.cardtypeno+'", "text": "'+lsCardTypeInfo[i].id.cardtypeno+'_'+lsCardTypeInfo[i].cardtypename+'"}';
								
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
	
	function maskAmt(amt,precision)
	{
			var ret;
			
			var strAmt = amt.toString();
			var afterSplit = strAmt.split(".");
			var integ = afterSplit[0];
			var decimal = afterSplit[1];
			
			if(strAmt == "NaN" || strAmt=="undefined"||strAmt == null)
			{
				return "0";
			}
			
			if(precision == 0)
			{
				return integ;
			}
			if(typeof(decimal)=="undefined"||decimal == null)
			{
				ret = integ +".";
				for(var j=1;j<=precision;j++)
				{
					ret = ret + "0";
				}
				return ret;
			}
			
			var decimalLen = decimal.length;
	
			if(decimalLen == precision)
			{
				return amt;
			}
			else if(decimalLen < precision)
			{
				for(var i=1;i<=precision-decimalLen;i++)
				{
					decimal = decimal + "0"; 
				}
			}
			else if(decimalLen > precision)
			{
				try{
				decimal = decimal.substring(0,precision);
				}catch(Exception){alert(Exception.message);}
			}
			
			ret = integ+"."+decimal;
			
			return ret;
			
	}
	
	

//bankno为银行卡号 banknoInfo为显示提示信息的DIV或其他控件
function luhmCheck(bankno){
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    //计算Luhm值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    if(lastNum==luhm){
    	return true;
    }
    else{
    	return false;
    }        
}
	