
var intIndex=0;arrList = new Array();
//初始化下拉列表项
//将初始化的列表转换成数组
function dearray(obj,itemindex)//定义array
{
	arrList = new Array();
	var ccount=0;
 	for(var i=0;i<obj.length;i++)
 	{ 
 	    if(itemindex==1)
 	    {
 	    	arrList[i]=obj[i].id.prjno+"-"+obj[i].prjname+"["+obj[i].saleprice+"]";
 	    }
 		else if(itemindex==2)
 	    {
 	    	if( checkNull(obj[i].useflag)==0 )
 	    	{
 	    		arrList[ccount]=obj[i].id.goodsno+"-"+obj[i].goodsname+"["+obj[i].storesalseprice+"]";
 	    		ccount=ccount*1+1;
 	    	}
 	    }
 	    else if(itemindex==5)
 	    {
 	    	if(checkNull(obj[i].appflag)==1 && checkNull(obj[i].useflag)==0 )
 	    	{
 	    		arrList[ccount]=obj[i].id.goodsno+"-"+obj[i].goodsname+"["+obj[i].storesalseprice+"]";
 	    		ccount=ccount*1+1;
 	    	}
 	    }
 	    else if(itemindex==3)
 	    {
 	    	if(checkNull(obj[i].prjsaletype)==1)
 	    	{
 	    		arrList[ccount]=obj[i].id.prjno+"-"+obj[i].prjname+"["+obj[i].saleprice+"]";
 	    		ccount=ccount*1+1;
 	    	}
 	    }
 	    else if(itemindex==6)
 	    {
 	 
 	    	if(checkNull(obj[i].prjsaletype)==1  && checkNull(obj[i].saleflag)==1)
 	    	{
 	    		arrList[ccount]=obj[i].id.prjno+"-"+obj[i].prjname+"["+obj[i].saleprice+"]";
 	    		ccount=ccount*1+1;
 	    	}
 	    }
 	    else if(itemindex==4)
 	    {
 	  
 	    	if(checkNull(obj[i].prjsaletype)!=1)
 	    	{
 	    		arrList[ccount]=obj[i].id.prjno+"-"+obj[i].prjname+"["+obj[i].saleprice+"]";
 	    		ccount=ccount*1+1;
 	    	}
 	    }
 	    else if(itemindex==8)
 	    {
 	    	arrList[ccount]=obj[i].id.packageno+"-"+obj[i].packagename+"["+obj[i].packageprice+"]";
	    	ccount=ccount*1+1;
 	    }
 	}
}

function itemsearchbeginPack(obj1,obj2,itemindex)
{
	//出现光标
 	inititem();
 	dearray(obj2,itemindex);
 	downList(arrList,obj1.id,itemindex,"");
}



function itemsearchbegin(obj,itemindex)//1 项目 2产品 3 会员资料
{
 try
 {

  	//出现光标
 	inititem();
  	if(itemindex==1)
  	{
  	 	dearray(parent.lsProjectinfo,itemindex)
  	}
  	else if(itemindex==2)
  	{
  		dearray(parent.lsGoodsinfo,itemindex)
  	}
  	else if(itemindex==5)
  	{
  		dearray(parent.lsGoodsinfo,itemindex)
  	}
  	else if(itemindex==3)
  	{
  		dearray(parent.lsProjectinfo,itemindex)
  	}
  	else if(itemindex==6)
  	{
  		dearray(parent.lsProjectinfo,itemindex)
  	}
  	else if(itemindex==4)
  	{
  		dearray(parent.lsProjectinfo,itemindex)
  	}
  	downList(arrList,obj.id,itemindex,"");
 
  }catch(e){alert(e.message);}
}

function itemsearchbegin_ware(obj,itemindex,wareNo)//1 项目 2产品 3 会员资料
{
 try
 {
	dearray_ware(parent.lsGoodsinfo,itemindex,wareNo)
  	downList(arrList,obj.id,itemindex,wareNo);
 
  }catch(e){alert(e.message);}
}

function dearray_ware(obj,itemindex,wareNo)//定义array
{
	arrList = new Array();
	var ccount=0;
 	for(var i=0;i<obj.length;i++)
 	{ 
 	    if(checkNull(obj[i].appflag)==1 && checkNull(obj[i].useflag)==0 && checkNull(obj[i].goodswarehouse)==wareNo )
 	    {
 	    		arrList[ccount]=obj[i].id.goodsno+"-"+obj[i].goodsname+"["+obj[i].storesalseprice+"]";
 	    		ccount=ccount*1+1;
 	    }
 	}
}

function inititem()
{
 if(arrList.constructor!=Array)
 {
  alert("downList初始化失败:第一个参数非数组!");
  return;
 }

 arrList.sort( function(a, b)
 {
  if(a.length>b.length)return 1;
  else if(a.length==b.length)return a.localeCompare(b);
  else return -1;
 }); 
}

function downList(arrList,objInputId,itemindex,wareNo)
{
 var curItemindex=itemindex;
 var curWareNo=wareNo;
 var objouter=document.getElementById("keysList"); //显示的DIV对象
 var objInput = document.getElementById(objInputId); //文本框对象
 var selectedIndex=-1;
 var intTmp; //循环用的
 
 if (objInput==null)
 {
  alert("downList初始化失败:没有找到"+objInputId+"文本框");
  return;
 }
 //文本框失去焦点
 objInput.onblur=function(){
  objouter.style.display="none";
 }
 //文本框按键抬起
 objInput.onkeyup=checkKeyCode;
 //文本框得到焦点
 objInput.onfocus=checkAndShow;

 //判断按下的安键
 function checkKeyCode(evt)
 {
  evt = evt || window.event;
  var keyCode = window.event ? evt.keyCode : evt.which;
  //var keyCode = String.fromCharCode(key);
  if (keyCode==40||keyCode==38)
  {//下上
   var isUp=false
   if(keyCode==40) isUp=true;
   chageSelection(isUp);
  }
  //else if (keyCode==13)
  //{//回车
   //outSelection(selectedIndex);
  //}
  else
  {

   checkAndShow(evt);
  }
  divPosition(evt);
 }

 function checkAndShow(evt)
 {
  var strInput = objInput.value;
  if (strInput!="")
  {
   divPosition(evt);
   selectedIndex=-1;
   objouter.innerHTML ="";
   var showcount=0;
   //if(curItemindex==1 && isNaN(objInput.value.replace("*",""))==false)
   if((curItemindex==1  || curItemindex==3 || curItemindex==6 || curItemindex==4) && objInput.value.indexOf("*")>(-1))
   {
   		var arrProList=loadProSeqno();
   		for (var intTmp=0;intTmp<arrProList.length;intTmp++)
   		{
			if (arrProList[intTmp].substr(0, strInput.length)==strInput)
		 	{
		    	showcount++;
		    	addOptiondiv(arrProList[intTmp],strInput);
		     	if(showcount>10)
		     	{
		     		//break;
		     	}
			}
		}
		objouter.style.display="";
   		return;
   }
   for (var intTmp=0;intTmp<arrList.length;intTmp++)
   {
	    if(curItemindex==1 || curItemindex==3 || curItemindex==6 || curItemindex==4 )  //项目
	    {
	    	if (arrList[intTmp].substr(0, strInput.length)==strInput
		    || toPinyin(arrList[intTmp]).indexOf(strInput.toUpperCase())>-1)
			{
			    	showcount++;
			     	addOptiondiv(arrList[intTmp],strInput);
			     	if(showcount>10)
			     	{
			     		break;
			     	}
			}
	    }
	    else if(curItemindex==2 || curItemindex==5  || curItemindex==7 )  //产品
	    {
	    	 if (arrList[intTmp].substr(0, strInput.length)==strInput
		    || toPinyin(arrList[intTmp]).indexOf(strInput.toUpperCase())>-1)
		    {
		    	showcount++;
		     	addOptiondiv(arrList[intTmp],strInput);
		     	if(showcount>10)
		     	{
		     		break;
		     	}
		    }
	    }
	    else if(curItemindex==8)
	    {
	    	if (arrList[intTmp].substr(0, strInput.length)==strInput
	    		    || toPinyin(arrList[intTmp]).indexOf(strInput.toUpperCase())>-1)
	    		    {
	    			
	    		    	showcount++;
	    		     	addOptiondiv(arrList[intTmp],strInput);
	    		     	if(showcount>10)
	    		     	{
	    		     		break;
	    		     	}
	    		    }
	    }
   
   }
   objouter.style.display="";
  }
  else
  {
   objouter.style.display="none";
  }
 
   
 	//想下拉列表里添加匹配项
 	function addOptiondiv(value,keyw)
  	{
   		var showvalue = value.replace(keyw, "<b><font color=\"blue\">" + keyw + "</font></b>");
  		var realValue=value.substring(0,value.indexOf("-"));
  		objouter.innerHTML +="<div onmouseover=this.className=\"sman_selectedStyle\";document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\" onmouseout=this.className=\"\"; onmousedown=document.getElementById(\""+objInputId+"\").value=\"" + realValue + "\";selectCall();document.getElementById(\""+objInputId+"\").onchange();>" + showvalue + "</div>" ;
  	}
 }//end checkAndShow()
  
  

  
 function selectCall()
 {
 	
 }
 
 
  
 function chageSelection(isUp)
 {
  if (objouter.style.display=="none")
  {
   objouter.style.display="";
  }
  else
  {
   if (isUp)
    selectedIndex++;
   else
    selectedIndex--;
  }
  
  var maxIndex = objouter.childNodes.length-1;
  if (selectedIndex<0){selectedIndex=0;}
  if (selectedIndex>maxIndex) {selectedIndex=maxIndex;}
  for (intTmp=0;intTmp<=maxIndex;intTmp++)
  {
   if (intTmp==selectedIndex)
   {
    objouter.childNodes[intTmp].className="sman_selectedStyle";
    //当上下键移动时，将选中的文本写到文本框中
    var selectItemValue=objouter.childNodes[intTmp].innerText;
    document.getElementById(objInputId).value=selectItemValue.substring(0,selectItemValue.indexOf("-"));
   }
   else
   {
    objouter.childNodes[intTmp].className="";
   }
  }
 }
 
 function outSelection(Index)
 {
  var selectItemValue=objouter.childNodes[Index].innerText;
  objInput.value = selectItemValue.substring(0,selectItemValue.indexOf("-"));
  objInput.onchange();
  objouter.style.display="none";
 }
 
 //显示下拉列表项
 function divPosition(evt)
 {
 	
  var e = objInput;
  var ie = (document.all)? true:false
  //定义列表区在不同浏览器中的位置
  if (ie)
  {
   var top = 0;
   var left = -2;
  }
  else
  {
   var top = 2;
   var left = 0;
  }
   
  while (e.offsetParent)
  {
   left += e.offsetLeft + (e.currentStyle?(parseInt(e.currentStyle.borderLeftWidth)).NaN0():0);
   top += e.offsetTop + (e.currentStyle?(parseInt(e.currentStyle.borderTopWidth)).NaN0():0);
   e = e.offsetParent;
  }
  
  left += e.offsetLeft + (e.currentStyle?(parseInt(e.currentStyle.borderLeftWidth)).NaN0():0);
  top += e.offsetTop + (e.currentStyle?(parseInt(e.currentStyle.borderTopWidth)).NaN0():0);
  objouter.style.top = (top + objInput.clientHeight) + "px";
  objouter.style.left = left + "px"; 
  divPositionself(evt);
 }

}//end downList()


function divPositionself(evt)
{
}

function getAbsoluteHeight(ob)
{
 return ob.offsetHeight;
}

function getAbsoluteWidth(ob)
{
 return ob.offsetWidth;
}

function getAbsoluteLeft(ob)
{
 var mendingLeft = ob .offsetLeft;
 while( ob != null && ob.offsetParent != null && ob.offsetParent.tagName != "BODY" )
 {
  mendingLeft += ob .offsetParent.offsetLeft;
  mendingOb = ob.offsetParent;
 }
 return mendingLeft;
}

function getAbsoluteTop(ob)
{
 var mendingTop = ob.offsetTop;
 while( ob != null && ob.offsetParent != null && ob.offsetParent.tagName != "BODY" )
 {
  mendingTop += ob.offsetParent.offsetTop;
  ob = ob.offsetParent;
 }
 return mendingTop;
}

Number.prototype.NaN0 = function()
{
 return isNaN(this)?0:this;
}

//拼音检索
