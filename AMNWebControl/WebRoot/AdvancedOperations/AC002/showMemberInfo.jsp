<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String strUserId=request.getParameter("strUserId");
%>

<%@include file="/include/sysfinal.jsp"%>
<!DOCTYPE html>
<html >
<head>
     <title>会员信息</title>
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="msapplication-tap-highlight" content="no">
	<meta name="format-detection" content="telephone=no"> 
	<link rel="stylesheet" href="<%=ContextPath%>/CSS/chui.ios.css">
	<script src="<%=ContextPath%>/JS/chocolatechip-3.0.3.js"></script>
	<script src="<%=ContextPath%>/JS/chui-3.0.3.js"></script>
	<script type="text/javascript" src="<%=ContextPath%>/common/common.js"></script>
	<script type="text/javascript">
		$(function() {
		try
	 	{ 
			$('.segmented').UIPanelToggle('#toggle-panels',function(){$.noop;});
			var requestUrl ="bandUserInfo/loadCardInfoByUserId.action?strUserId="+document.getElementById("strUserId").value;
		    $.ajax({
                type: 'POST',
                url: requestUrl,
                data: null,
                dataType: 'json',
                success: function (data) { 
                loadCardInfo(data.strCardInfos);
                }
            });
       
			}
				catch(e){alert(e.message)}
		});
		
		function loadCardInfo(strCardInfos)
		{	
			var cards=strCardInfos.split(";"); //字符分割  
			var popoverContent = "<ul class='list'>";    
			for (i=0;i<cards.length ;i++ )    
		    {    
				popoverContent =popoverContent+ "<li><h3>"+cards[i]+"</h3></li>";
		    } 
			popoverContent=popoverContent+"</ul>";
			var fillPopover1 = function(popover) {
            	// Populate Popover with content:
					$('.popover').find('section').append(popoverContent);
					popoverEventHandler();
            };
           
		 	var popoverEventHandler = function() {
					// Attach event to catch user interaction.
					// Use singletap to allow user to scroll content.
					$('.popover').on('singletap', function(e) {
						var results;
						if (this.id === 'cardNoPopover') {
							results = '#cardinfo';
						}
						
						var listItem;
					
						if (e.target.nodeName === 'LI') {
							$(results).html(e.target.textContent.trim());
							validateCardNo(e.target.textContent.trim());
						} else {
							listItem = $(e.target).closest('li')[0];
							$(results).html(listItem.textContent.trim());
							validateCardNo(listItem.textContent.trim());
						}
						
						$.UIPopoverClose();
					});
				};
            // Initialize Popover:
            $('#selectCardNo').UIPopover({
            	id: 'cardNoPopover', 
            	title: "账户列表", 
            	callback: fillPopover1
            });	
            
            if(cards.length>0)
            {
            	document.getElementById("cardinfo").innerHTML=cards[0];
            	validateCardNo(cards[0]);
            }	
		}
		
		function validateCardNo(curCardNo)
		{
			var requestUrl ="cardInfoAction/loadCurCardInfo.action?strCurCompId=001&strCurCardNo="+curCardNo;
		    $.ajax({
                type: 'POST',
                url: requestUrl,
                data: null,
                dataType: 'json',
                success: function (data) { 
                   showCardInfo(data.curCardinfo,data.lsCardtransactionhistory,data.lsCardproaccount,data.lsCardaccountchangehistory);
                }
            });
		}
		
		function showCardInfo(curCardinfo,lsCardtransactionhistory,lsCardproaccount,lsCardaccountchangehistory)
		{
			if(curCardinfo!=null)
			{
				document.getElementById("membername").innerHTML=checkNull(curCardinfo.membername);
				document.getElementById("cardinfo").innerHTML=checkNull(curCardinfo.id.cardno)+"-"+checkNull(curCardinfo.cardtypeName);
				if(checkNull(curCardinfo.memberphone)!="" && checkNull(curCardinfo.memberphone).length==11)
				{
					document.getElementById("memberphone").innerHTML=checkNull(curCardinfo.memberphone).substring(0,3)+"****"+checkNull(curCardinfo.memberphone).substring(checkNull(curCardinfo.memberphone).length-4,checkNull(curCardinfo.memberphone).length);
				}
				else
				{
					document.getElementById("memberphone").innerHTML="未登记"
				}
				if(checkNull(curCardinfo.memberpaperworkno)!="" && checkNull(curCardinfo.memberpaperworkno).length>=15)
				{
					document.getElementById("memberpaperworkno").innerHTML=checkNull(curCardinfo.memberpaperworkno).substring(0,4)+"********"+checkNull(curCardinfo.memberpaperworkno).substring(checkNull(curCardinfo.memberpaperworkno).length-4,checkNull(curCardinfo.memberpaperworkno).length);
				}
				else
				{
					document.getElementById("memberpaperworkno").innerHTML="未登记";
				}
				if(checkNull(curCardinfo.cardsource)==1)//收购卡
				{
					document.getElementById("accountAmt").innerHTML=checkNull(curCardinfo.account5Amt);
				}
				else
				{
					document.getElementById("accountAmt").innerHTML=checkNull(curCardinfo.account2Amt);
				}
				document.getElementById("account3Amt").innerHTML=checkNull(curCardinfo.account3Amt);
				
			}
			var grid=document.getElementById("transHistory");
	  		var maxRow = grid.rows.length-1;
		  	for(var i=0;i<maxRow*1+1;i++)
		  	{
		  		grid.deleteRow(0);
		  	}
			if(lsCardtransactionhistory.length!=null)
			{
				for(var i=0;i<lsCardtransactionhistory.length;i++)
				{
					 transactionAddRow(lsCardtransactionhistory[i]);
				}
			}
			grid=document.getElementById("cardProCount");
	  		maxRow = grid.rows.length-1;
		  	for(var i=0;i<maxRow*1+1;i++)
		  	{
		  		grid.deleteRow(0);
		  	}
		  	if(lsCardproaccount.length!=null)
			{
				for(var i=0;i<lsCardproaccount.length;i++)
				{
					 proaccountAddRow(lsCardproaccount[i]);
				}
			}
			grid=document.getElementById("cardAccountHistory");
	  		maxRow = grid.rows.length-1;
		  	for(var i=0;i<maxRow*1+1;i++)
		  	{
		  		grid.deleteRow(0);
		  	}
		  	if(lsCardaccountchangehistory.length!=null)
			{
				for(var i=0;i<lsCardaccountchangehistory.length;i++)
				{
					 cardaccounthistoryAddRow(lsCardaccountchangehistory[i]);
				}
			}
			
		}
		
		
	function transactionAddRow(transHistory){ //增加一条明细
  
      	var grid=document.getElementById("transHistory");
	  	var maxRow = grid.rows.length-1;
	  	var trRow = maxRow +1;
      	var newTr = grid.insertRow();
      	newTr.className = "tr";  
      	newTr.id= trRow + "_tr" ; 
      	newTr.style.background="#efefef";
      	newTr.style.background="transparent";
      	for(var i=0;i<4;i++){
        	newTr.insertCell(i);
     	}
     	newTr.cells[0].innerHTML=transHistory.transactiondate;
     	//1-卡销售 2-疗程销售 3-项目 4-产品销售 - 5卡充值 - 6疗程充值
     	if(transHistory.transactiontype==1)
     		newTr.cells[1].innerHTML="卡销售";
     	else if(transHistory.transactiontype==2)
     		newTr.cells[1].innerHTML="疗程销售";
     	else if(transHistory.transactiontype==3)
     		newTr.cells[1].innerHTML="项目服务";
     	else if(transHistory.transactiontype==4)
     		newTr.cells[1].innerHTML="产品销售";
     	else if(transHistory.transactiontype==5)
     		newTr.cells[1].innerHTML="卡充值";
     	else if(transHistory.transactiontype==6)
     		newTr.cells[1].innerHTML="疗程充值";
     
     	newTr.cells[2].innerHTML=transHistory.codename;
     	newTr.cells[3].innerHTML=transHistory.price+"&nbsp;&nbsp;";
     		newTr.cells[3].align="right";
     	
	}
	
	function proaccountAddRow(proaccount){ //增加一条明细
  
      	var grid=document.getElementById("cardProCount");
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
     	newTr.cells[0].innerHTML=proaccount.bprojectname;
     	newTr.cells[1].innerHTML=proaccount.lastcount+"&nbsp;&nbsp;";
     	newTr.cells[1].align="right";
	}
	
	function cardaccounthistoryAddRow(cardAccountHistory){ //增加一条明细
  
      	var grid=document.getElementById("cardAccountHistory");
	  	var maxRow = grid.rows.length-1;
	  	var trRow = maxRow +1;
      	var newTr = grid.insertRow();
      	newTr.className = "tr";  
      	newTr.id= trRow + "_tr" ; 
      	for(var i=0;i<4;i++){
        	newTr.insertCell(i);
     	}
     	newTr.style.background="#efefef";
     	newTr.style.background="transparent";	
     	newTr.cells[0].innerHTML=cardAccountHistory.changecompname;
     	newTr.cells[1].innerHTML=cardAccountHistory.chagedate;
     	newTr.cells[2].innerHTML=cardAccountHistory.changetypeName;
     	newTr.cells[3].innerHTML=cardAccountHistory.changeamt+"&nbsp;&nbsp;";
     	newTr.cells[0].align="center";
     	newTr.cells[1].align="center";
     	newTr.cells[2].align="center";
     	newTr.cells[3].align="right";
	}
	</script>
	<style>
    .touming
    {
    	background:transparent;/*背景透明*/
		border-top:none;/*取消上部线条*/
		border-left:none;/*取消左部线条*/
		border-right:none;/*取消右部线条*/
		border-bottom:none;
		outline:none;
    }
    </style>
</head>
<body>

	<input type="hidden" name="strUserId"  id="strUserId" value="<%=strUserId %>"/>
	<nav>
        <a class='button show-popover' id="selectCardNo"  href="javascript:void(null)">
   <font size="3">账户列表:&nbsp;&nbsp;&nbsp;&nbsp;</font>
        </a>
        <h1><label id="cardinfo"></label></h1>
	</nav>
	<article id="main" class="current">
		<section>
			<div class='horizontal centered'>
				<div class='segmented'>
					<a class='button'><span class="comp">基本资料</span></a>
					<a class='button'>疗程明细</a>
					<a class='button'>账户历史</a>
				</div>
			</div>
			<div id="toggle-panels">
		
				<div>
					<ul class='list' >
					<li>
                    会员姓名: &nbsp;<label  id="membername"></label > &nbsp;    
                      </li>  
					<li>
                    手机号码: &nbsp;<label id="memberphone" ></label>    
			      </li>
                   
                   <li>
				     <label for="IdCard">证件编号: &nbsp;&nbsp;</label><label id="memberpaperworkno"></label>
                   </li>	   
                   
                    <li>
                      	存储账户:
                       <label id="accountAmt"></label>
                   		 &nbsp; &nbsp;积分账户:
                       <label  id="account3Amt"></label>
                   </li>
                  
                      
                </ul>
                  
                  <ul class="list">
                      <li>
                       <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;background: #CCCCCC; background:transparent;"  >
			         	
			         		<tr style="background: #efefef;  background:transparent; padding:2px 0 0px 0; text-align:center;">
			                <td align="center">交易日期</td>
			                <td align="center">交易类型</td>
			                <td align="center">交易名称</td>
			                <td align="center">金额</td> 
			            	</tr> 
			              <tbody id="transHistory" >
			             </tbody>
			              </table>
                      </li>
                  </ul>
                  
				 </div>
				<div>
					<ul class='list'>
					<li class='comp'>
					<div>				
						 <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;background: #CCCCCC;background:transparent;"   >
				       <tr style="background: #efefef;background:transparent; padding:2px 0 0px 0; text-align:center;">
			                    	<td align="center">名称</td>
				           		<td align="center">剩余次数</td>
				           	 
				         </tr>
			          	<tbody id="cardProCount" >
						 </tbody>    
       					 </table>
      			 </div>
      			 </li>
    			 </ul>	
                    </div>
                    <div>
                        <ul class='list'>
                            <li class='comp'>
                            <table width="100%" border="0" cellspacing="1" cellpadding="0" style="font-size:12px;line-height:28px;background: #CCCCCC;background:transparent; "  >
							<tr style="background: #efefef;background:transparent; padding:2px 0 0px 0; text-align:center;">
			                  	<td  align="center">门 店</td>
							    <td align="center">日  期</td>
							    <td  align="center">类 型</td>
							    <td  align="center">金 额</td>
							  </tr>
							 <tbody id="cardAccountHistory" >
						 	</tbody> 
							</table>
						</li>
					</ul>	
				</div>
			</div>
	
          
		</section>
	</article>
</body>
</html>
