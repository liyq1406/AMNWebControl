var commoninfodivmaster = null;
var showDialogmanager = null;
var mpackInfo = null;
var curitemManger = null;
var addYardDialog = null;
$(function() {
	var intMonth=document.getElementById("accountdate").value.substring(4,6)*1;
	/*
	 * commoninfodivmaster=$("#yearselldetal_div").ligerGrid({ columns: [ {
	 * display: '疗程名称', name: 'itemno', width:60 ,align: 'left'}, { display:
	 * '套数', name: 'num', width:60 ,align: 'left',editor:{type:'int'}}, {
	 * display: '金额', name: 'amt', width:60 ,align:
	 * 'left',editor:{type:'float'}}, { display: '分享人1', name: 'firstempno',
	 * width:60 ,align: 'left',editor:{type:'text'}}, { display: '分享人2', name:
	 * 'firstempno', width:60 ,align: 'left',editor:{type:'text'}}, { display:
	 * '分享人3', name: 'firstempno', width:60 ,align:
	 * 'left',editor:{type:'text'}}, { display: '烫染师', name: 'firstempno',
	 * width:60 ,align: 'left',editor:{type:'text'}} ], data: null, height:320,
	 * rownumbers: false, usePager: false, enabledEdit: true, checkbox: false
	 * });
	 */

	$("#layout1").ligerLayout({
		leftWidth : 1,
		allowLeftCollapse : false,
		allowRightCollapse : false
	});
	// $("#layout1").ligerLayout({ leftWidth: 200,height:'50%'});
	document.getElementById("cap").SwitchWatchOnly();
	// document.getElementById("cap").start();
	// document.getElementById("cap").formatCount;
	// document.getElementById("cap").setCurrentFormat(25);
	document.getElementById("curstate").value = "1";
	// document.getElementById("cap").resizePicture(768,1024);
	// setTimeout("document.getElementById('cap').selectRect(0.3,0.25,0.6,0.8);",1000);
	$.get(contextURL + "/cc018/loadPack.action", {}, function(data) {
		mpackInfo = data.lsPacks;
	});
	
	
	
	if(intMonth!=3)
	{
		document.getElementById("ishd").style.display="none";
	}

});

function itemsearchbeginInid(obj1, itemindex) {

	// 出现光标
	try {
		itemsearchbeginPack(obj1, mpackInfo, itemindex);
	} catch (e) {
		alert(e.message);
	}

}

function validateCscardno(cardNo) {
	if(checkNull(cardNo)==""){
		var CardControl = parent.document.getElementById("CardCtrl");
		CardControl.Init(parent.commtype, parent.prot, parent.password1,
				parent.password2, parent.password3);
		cardNo = CardControl.ReadCard();
	}
	var requestUrl = contextURL + "/cc011/validateCscardno.action";
	var responseMethod = "validateCscardnoMessage";
	var params = "strCardNo=" + cardNo;
	params = params + "&cardUseType=1";
	sendRequestForParams_p(requestUrl, responseMethod, params);
}

function validateCscardnoMessage(request) {
	var responsetext = eval("(" + request.responseText + ")");
	var strmessage = checkNull(responsetext.strMessage);
	if (strmessage != "") {
		$("#cardno").val("");
		$("#cardtype").val("");
		$("#phone").val("");
		$("#temphone").val("");
		// $("#oldphone").val("");
		$("#menbername").val("");
		$("#storeAmt").val("");
		$("#zsyeamt").val("");
		$("#slaeproerate").val("");
		$.ligerDialog.error(strmessage);
		// $("#cid").val("");
		$("#randomno").val("");
	} else {
		$("#storeAmt").val(responsetext.curCardinfo.account2Amt);
		$("#cardno").val(checkNull(responsetext.strCardNo));
		$("#cardtype").val(checkNull(responsetext.curCardinfo.cardtypeName));
		$("#zsyeamt").val(responsetext.curCardinfo.account6Amt);
		var temphone = checkNull(responsetext.curCardinfo.memberphone);
		/*
		 * if(temphone!="") {
		 * document.getElementById("temphone").value=checkNull(temphone.substring(0,3)+"****"+temphone.substring(7,11)); }
		 */
		// $("#phone").val(checkNull(responsetext.curCardinfo.memberphone));
		// $("#oldphone").val(checkNull(responsetext.curCardinfo.memberphone));
		$("#menbername").val(
				checkNull(responsetext.curCardinfo.membername.substring(0, 1)
						+ "**"));
		$("#slaeproerate")
				.val(checkNull(responsetext.curCardinfo.slaeproerate));

		// $("#cid").val(checkNull(responsetext.curCardinfo.memberpaperworkno));
		/*
		 * if(checkNull(responsetext.curCardinfo.memberphone)!="") {
		 * $.get(contextURL+"/cc018/loadYearCard.action",{"strPhone":responsetext.curCardinfo.memberphone},function(data){
		 * if(data.strMessage=="") { document.getElementById("cap").stop();
		 * document.getElementById("cap").loadBase64String(data.yearcardinof.img);
		 * $("#strImage").val(data.yearcardinof.img); } }); }
		 */

	}
}

function lsDkageinfos() {
	var packNo = $("#packno").val();
	if (packNo == "") {
		return;
	}
	
	showDialogmanager = $.ligerDialog.waitting("正在查询,请稍候...");
	var prjName = "";
	var ischeck=document.getElementById("ischeck").checked;
	document.getElementById("ischeck").checked=false;
	$.get(contextURL + "/cc018/loadDmpack.action",{"strPackNo" : packNo},
					function(data) {
						var tab = $("#tabItem");
						var trHTML = "";
						var amt = 0;
						if (data.lsDkageinfos.length > 0) {
							for ( var j = 0; j < data.lsDkageinfos.length; j++) {
								var trs = tab.find("tr");
								prjName = "";
								for ( var i = 0; i < parent.lsProjectinfo.length; i++) {
									if (data.lsDkageinfos[j].id.packageprono == parent.lsProjectinfo[i].id.prjno) {
										prjName = parent.lsProjectinfo[i].prjname;
										break;
									}
								}
								
								if(data.mapMpackageinfo.ratetype==1)
								{
									amt = ((data.lsDkageinfos[j].packageproamt * 1) - ($(
									"#zsdkamt").val() * 1)-$("#dyqamt").val()*1);
								}
								else
								{
									amt = ((data.lsDkageinfos[j].packageproamt * 1)-($("#dyqamt").val()*1)-$("#zsdkamt").val() * 1)*($("#slaeproerate").val() * 1);
									//amt = (data.lsDkageinfos[j].packageproamt * 1)-$("#zsdkamt").val() * 1)*($("#slaeproerate").val() * 1))-($("#dyqamt").val()*1);
								}
								
								if(amt<0)
								{
									$.ligerDialog.error("赠送抵扣的金额大于疗程金额");
									showDialogmanager.close();
									break;
								}
								
								if(document.getElementById("dyqno").value.substring(0,2)=="NS")
								{
									if(data.lsDkageinfos[j].id.packageprono.substring(0, 1)!=4 && (data.lsDkageinfos[j].id.packageno.substring(0,3)!="201" || data.lsDkageinfos[j].id.packageno.substring(0,3)!="202"))
									{
										$.ligerDialog.error("NS开头的抵用券只能抵用美容年卡和半年卡");
										showDialogmanager.close();
										return;
									}
								}
								
								if($("#dyqno").val().indexOf("JF")==0)
								{
									  var sumamt=0;
									  //var trs=$("#tabItem").find("tr");
									  if(data.lsDkageinfos[j].id.packageno!="201022" && data.lsDkageinfos[j].id.packageno!="201023" && data.lsDkageinfos[j].id.packageno!="201024"
										  && data.lsDkageinfos[j].id.packageno!="201025" && data.lsDkageinfos[j].id.packageno!="202003" && data.lsDkageinfos[j].id.packageno!="202004"
											  && data.lsDkageinfos[j].id.packageno!="202005" && data.lsDkageinfos[j].id.packageno!="202006" )
										  {
										  		$.ligerDialog.error("JF开头的抵用券只能抵用悦碧施年卡和半年卡");
										  		showDialogmanager.close();
										  		return;
										  }
										  for(var k=0;k<parent.lsProjectinfo.length;k++)
										  {
											  if(data.lsDkageinfos[j].id.packageprono==parent.lsProjectinfo[k].id.prjno && parent.lsProjectinfo[k].prjreporttype=='24')
											  {
												  sumamt+=data.lsDkageinfos[j].packageproamt*1;
											  }
										  }
									  
						    		  if(sumamt*1<8000)
						    		  {
						    			  $.ligerDialog.error("兑换悦碧施项目金额要达到8000以上才能使用该抵用券！");
						    			  showDialogmanager.close();
						    			  return;
						    		  }
								}
								
										
								trHTML += "<tr><td>" + prjName
										+ "<input name=\"lsyeaList["
										+ trs.length + "].packno\" value=\""
										+ packNo + "\" type=\"hidden\"></td>";
								
								if(ischeck && data.lsDkageinfos[j].id.packageprono.substring(0, 1) == 3 && (data.lsDkageinfos[j].id.packageno.substring(0,3)=="201" ||data.lsDkageinfos[j].id.packageno.substring(0,3)=="202"))
								{
									trHTML += "<td>"
										+ (data.lsDkageinfos[j].packageprocount*1+1)
										+ "<input name=\"lsyeaList["
										+ trs.length
										+ "].num\" type=\"hidden\" value=\""
										+ (data.lsDkageinfos[j].packageprocount*1+1)
										+ "\"></td>";
									
								}
								else
								{
									trHTML += "<td>"
										+ data.lsDkageinfos[j].packageprocount
										+ "<input name=\"lsyeaList["
										+ trs.length
										+ "].num\" type=\"hidden\" value=\""
										+ data.lsDkageinfos[j].packageprocount
										+ "\"></td>";
									//trHTML += "<td>"+data.lsDkageinfos[j].packageproamt+"</td>";
								}
								
								trHTML += "<td>"+data.lsDkageinfos[j].packageproamt+"</td>";
								trHTML += "<td>" + amt
										+ "<input name=\"lsyeaList["
										+ trs.length + "].amt\" id=\"amt"
										+ trs.length
										+ "\" type=\"hidden\" value=\"" + amt
										+ "\"></td>";

								if (data.lsDkageinfos[j].id.packageprono
										.substring(0, 1) == 3) {
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].firstempno\" id=\"firstempno"
											+ trs.length
											+ "\" size='10' onchange=\"checkEmpNo(this,'fisrst"
											+ trs.length + "','firstinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"fisrst" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].firstperf\" id=\"firstperf"
											+ trs.length
											+ "\" size='5' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].sendempno\" id=\"sendempno"
											+ trs.length
											+ "\" readonly='true' size='10' onchange=\"checkEmpNo(this,'send"
											+ trs.length + "','sendinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"send" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].sendperf\" id=\"sendperf"
											+ trs.length
											+ "\" size='5' readonly='true' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].threeempno\" id=\"threeempno"
											+ trs.length
											+ "\" readonly='true' size='10' onchange=\"checkEmpNo(this,'three"
											+ trs.length + "','threeinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"three" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].threeperf\" id=\"threeperf"
											+ trs.length
											+ "\" size='5' readonly='true' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
								} else {
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].firstempno\" id=\"firstempno"
											+ trs.length
											+ "\" size='10' onchange=\"checkEmpNo(this,'fisrst"
											+ trs.length + "','firstinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"fisrst" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].firstperf\" id=\"firstperf"
											+ trs.length
											+ "\" size='5' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].sendempno\" id=\"sendempno"
											+ trs.length
											+ "\" size='10' onchange=\"checkEmpNo(this,'send"
											+ trs.length + "','sendinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"send" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].sendperf\" id=\"sendperf"
											+ trs.length
											+ "\" size='5' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].threeempno\" id=\"threeempno"
											+ trs.length
											+ "\"  size='10' onchange=\"checkEmpNo(this,'three"
											+ trs.length + "','threeinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"three" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].threeperf\" id=\"threeperf"
											+ trs.length
											+ "\" size='5' onchange=\"checkperf(this,'"
											+ trs.length + "')\"></td>";
								}
								if (data.lsDkageinfos[j].id.packageprono
										.substring(0, 1) == 4) {
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].fourempno\" id=\"fourempno"
											+ trs.length
											+ "\" size='10' onchange=\"checkTrEmpNo(this,'four"
											+ trs.length + "','fourinid"
											+ trs.length
											+ "')\" readonly='true'></td>";
									trHTML += "<td id=\"four" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].fourperf\" id=\"fourperf"
											+ trs.length
											+ "\" size='5' readonly='true' onchange=\"checkperf(this,'"
											+ trs.length + "','fourinid"
											+ trs.length + "')\"></td>";
								} else {
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].fourempno\" id=\"fourempno"
											+ trs.length
											+ "\" size='10' onchange=\"checkTrEmpNo(this,'four"
											+ trs.length + "','fourinid"
											+ trs.length + "')\"></td>";
									trHTML += "<td id=\"four" + trs.length
											+ "\"></td>";
									trHTML += "<td><input name=\"lsyeaList["
											+ trs.length
											+ "].fourperf\" id=\"fourperf"
											+ trs.length
											+ "\" size='5' readonly='true' onchange=\"checkperf(this,'four"
											+ trs.length + "','fourinid"
											+ trs.length + "')\"></td>";
								}
								trHTML += "<td><input name=\"lsyeaList["
										+ trs.length + "].probz\" id=\"probz"
										+ trs.length
										+ "\" readonly='true' value=\""
										+ $("#bz").val() + "\"></td>";
								trHTML += "<input name=\"lsyeaList["
										+ trs.length
										+ "].firstinid\" id=\"firstinid"
										+ trs.length + "\" type=\"hidden\">";
								trHTML += "<input name=\"lsyeaList["
										+ trs.length
										+ "].sendinid\" id=\"sendinid"
										+ trs.length + "\" type=\"hidden\">";
								trHTML += "<input name=\"lsyeaList["
										+ trs.length
										+ "].threeinid\" id=\"threeinid"
										+ trs.length + "\" type=\"hidden\">";
								trHTML += "<input name=\"lsyeaList["
										+ trs.length
										+ "].fourinid\" id=\"fourinid"
										+ trs.length + "\" type=\"hidden\">";
								trHTML += "<input name=\"lsyeaList["
									+ trs.length
									+ "].cashdyq\" id=\"cashdyq" 
									+ trs.length + "\" type=\"hidden\" value=\""+$("#dyqno").val()+"\">";
								trHTML += "<input name=\"lsyeaList["
										+ trs.length + "].itemno\" id=\"itemno"
										+ trs.length + "\" value=\""
										+ data.lsDkageinfos[j].id.packageprono
										+ "\" type=\"hidden\"></td>";
								
								trHTML += "</tr>";
							}
							$("#packno").val("");
							$("#zsamt").val($("#zsamt").val() * 1 + $("#zsdkamt").val()* 1);
							showDialogmanager.close();
							$("#packname").val("");
							$("#oldproamt").val("");
							$("#zsdkamt").val("");
							$("#dyqdkamt").val("");
							$("#bz").val("");
							$("#dyqno").val("");
							$("#dyqamt").val("");
							document.getElementById("packno").focus();
							tab.append(trHTML);
							getSumAmt();
						}
					});
}

function post() {

	if ($("#curstate").val() != "1") {
		$.ligerDialog.error("单据是浏览状态，请刷新之后保存");
		return;
	}
	if ($("#cardno").val() == "") {
		$.ligerDialog.error("会员卡号不能为空");
		return;
	}
	if ($("#name").val() == "") {
		$.ligerDialog.error("名字不能为空");
		return;
	}
	if ($("#phone").val() == "") {
		$.ligerDialog.error("手机号码不能为空");
		return;
	}
	if ($("#strImage").val() == "") {
		$.ligerDialog.error("请拍一张图片");
		return;
	}
	if ($("#totalamt").val() * 1 != ($("#storedamt").val() * 1
			+ $("#cashamt").val() * 1 + $("#zsamt").val() * 1)) {
		$.ligerDialog.error("支付不平，请调整");
		return;
	}
	var tab = $("#tabItem");
	var trs = tab.find("tr");
	for ( var i = 0; i < trs.length; i++) {
		if ($("#firstempno" + i).val() != "" && $("#fourempno" + i).val() != "") {
			$("#fourperf" + i).val($("#firstperf" + i).val());
		}
	}
	$("#postbill").attr("disabled", true);
	$("#phone").val($.trim($("#phone").val()));
	var strCardNo = $("#randomno").val();
	 if(strCardNo!=""){//微信扫码弹出输入验证码
		 var url = contextURL+"/cc018/sendWechatPwd.action"; 
		 var _params = {"randomno":strCardNo,"_random": Math.random()};
		 $.ajax({
            type:"POST", url:url, data:_params, dataType:"json",
            success: function(data){
           	 if(checkNull(data.strMessage)=="true"){
					var showWechatPwd = $.ligerDialog.open({ height:170, url: contextURL+'/CardControl/CC011/checkWechatPwd.jsp', 
		    			width: 360,showMax: false, showToggle: false, allowClose:false, showMin: false, isResize: false, title:"输入微信验证码",
	    				buttons:[{text:'确定', onclick:function(item, dialog){
	    					var _frame = $(dialog.frame.document);
	    			    	var pwd_code = $("#pwd_code", _frame).val();
	    			    	if(checkNull(pwd_code)==""){
	    						$.ligerDialog.warn("请输入验证码！");
	    						postState=0;
	    					}else{
	    						$("#pwd_code", _frame).attr("readonly",true);
	    						var url = contextURL+"/cc018/validateWechatPwd.action";
	    						var _params = {"randomno": pwd_code,"_random": Math.random()};
	    						$.ajax({
	   				             type:"POST", url:url, data:_params, dataType:"json",
	   				             	success: function(data){
	   				             		if(checkNull(data.strMessage)!=""){
	   				             			$.ligerDialog.warn(data.strMessage);
	   				             			$("#pwd_code", _frame).attr("readonly",false);
	   				             			postState=0;
	   				             		}else{
	   				             			showWechatPwd.close();
	   				             			postData();
	   				             		} 
	   				             	},
	   				             	error: function(XMLHttpRequest, textStatus, errorThrown){
	   				             		$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
	   				             	}
	    						});
	    					}
	    				}}, {text:'取消', onclick:function(item, dialog){postState=0;dialog.close();}}]
					});
				}else{
					$.ligerDialog.error(data.strMessage);
					postState=0;
				}         
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	$.ligerDialog.error("系统异常，请刷新或关闭页面重试！");
            }
        });
	 }else{
		 postData();
	 }
}

function postData(){
	showDialogmanager = $.ligerDialog.waitting('正在保存中,请稍候...');
	$.post(contextURL + "/cc018/post.action", $("#frm").serialize(), function(data) {
		showDialogmanager.close();
		if (data.strMessage != "") {
			$.ligerDialog.error(data.strMessage);
			$("#postbill").attr("disabled", false);
		} else {
			printBill();
			alert("保存成功");
			location.reload();
		}
	}, "json");
}

function startCamp() {
	document.getElementById("cap").stop();
	document.getElementById("cap").clear();
	document.getElementById("cap").start();
}

function Camp(obj) {
	if (obj.value == "拍照") {
		document.getElementById("cap").cap();
		document.getElementById("cap").cutSelected();
		$("#strImage").val(document.getElementById("cap").jpegBase64Data);
		document.getElementById("cap").savetoFile("d://test.jpg");
		obj.value = "重拍";
	} else {
		// document.getElementById("cap").stop();
		document.getElementById("cap").clear();
		document.getElementById("cap").start();
		$("#strImage").val("");
		// document.getElementById("cap").resizePicture(1024,768);
		setTimeout(
				"document.getElementById('cap').selectRect(0.3,0.25,0.6,0.8);",
				1000);
		obj.value = "拍照";
	}
}

function getSumAmt() {
	var tab = $("#tabItem");
	var trs = tab.find("tr");
	var totalamt = 0;
	for ( var i = 0; i < trs.length; i++) {
		totalamt += $("#amt" + i + "").val() * 1;
	}
	totalamt = totalamt * 1 + ($("#zsamt").val() * 1);
	if ($("#cashamt").val() * 1 >= totalamt * 1) {
		$("#cashamt").val(ForDight(totalamt, 2));
	}
	$("#storedamt").val(
			ForDight(totalamt * 1 - $("#cashamt").val() * 1 - $("#zsamt").val()
					* 1, 2));
	$("#totalamt").val(ForDight(totalamt, 2));
}

function stopCamp() {
	document.getElementById("cap").stop();
}

function clearCamp() {
	document.getElementById("cap").clear();
}

function checkEmpNo(obj, id, inid) {
	var isFlag = false;
	var staffname = "";
	var strinid = "";
	for ( var i = 0; i < parent.StaffInfo.length; i++) {
		if (parent.StaffInfo[i].bstaffno == obj.value) {
			staffname = parent.StaffInfo[i].staffname;
			strinid = parent.StaffInfo[i].manageno;
			isFlag = true;
		}
	}
	if (!isFlag) {
		$.ligerDialog.error("输入的员工不存在");
		obj.value = "";
		$("#" + id + "").html("");
		$("#" + inid + "").val("");
	} else {
		$("#" + id + "").html(staffname);
		$("#" + inid + "").val(strinid);
	}
}

function checkTrEmpNo(obj, id, inid) {
	var isFlag = false;
	var staffname = "";
	var strinid = "";
	for ( var i = 0; i < parent.StaffInfo.length; i++) {
		if (parent.StaffInfo[i].bstaffno == obj.value
				&& (parent.StaffInfo[i].position == "008"
						|| parent.StaffInfo[i].position == "00901"
						|| parent.StaffInfo[i].position == "00902"
						|| parent.StaffInfo[i].position == "00903" || parent.StaffInfo[i].position == "00904")) {
			staffname = parent.StaffInfo[i].staffname;
			strinid = parent.StaffInfo[i].manageno;
			isFlag = true;
		}
	}
	if (!isFlag) {
		$.ligerDialog.error("输入的员工不存在或者不是烫染师职位");
		obj.value = "";
		$("#" + id + "").html("");
		$("#" + inid + "").val("");
	} else {

		$("#" + id + "").html(staffname);
		$("#" + inid + "").val(strinid);
	}
}

function changeCashAmt(obj) {
	if (isNaN(obj.value)) {
		obj.value = "0";
	}
	var totalamt = ($("#totalamt").val() * 1) - ($("#zsamt").val() * 1);
	if (obj.value * 1 >= totalamt * 1) {
		$("#cashamt").val(ForDight(totalamt, 2));
		$("#storedamt").val("0");
		// $("#zsamt").val("0");
	} else {
		$("#storedamt").val(ForDight(totalamt * 1 - obj.value * 1, 2));
	}
}

function changeStoreAmt(obj) {
	if (isNaN(obj.value)) {
		obj.value = "0";
	}
	var totalamt = ($("#totalamt").val() * 1) - ($("#zsamt").val() * 1);
	if (obj.value * 1 >= totalamt * 1) {
		$("#storedamt").val(ForDight(totalamt, 2));
		$("#cashamt").val("0");
	} else {
		$("#storedamt").val(ForDight(totalamt * 1 - obj.value * 1, 2));
	}
}

function changeZSAmt(obj) {
	if (isNaN(obj.value)) {
		obj.value = "0";
	}
	var totalamt = $("#totalamt").val();
	var cashamt = $("#cashamt").val();
	var storedamt = $("#storedamt").val();
	if (obj.value * 1 >= totalamt * 1) {
		$("#zsamt").val(ForDight(totalamt, 2));
		$("#cashamt").val("0");
		$("#storedamt").val("0");
	} else {
		$("#storedamt").val(
				ForDight(totalamt * 1 - obj.value * 1 - cashamt * 1, 2));
	}
}

function loadYearCard() {
	if ($("#idtphone").val() == "") {
		return;
	}
	if ($("#idtphone").val() == $("#phone").val()) {
		$.ligerDialog.error("不能介绍自己");
		return;
	}
	$.get(contextURL + "/cc018/loadYearCard.action", {
		"strPhone" : $("#idtphone").val()
	}, function(data) {
		if (data.strMessage != "") {
			$.ligerDialog.error(data.strMessage);
			$("#idtname").val("");
			$("#idtphone").val("");
		} else {
			$("#idtname").val(data.yearcardinof.name);
			// document.getElementById("cap").stop();
			// document.getElementById("cap").loadBase64String(data.yearcardinof.img);
		}
	});
}

function ischeck() {
	$("#phone").attr("readonly", false);
	$("#temphone").attr("readonly", false);
	$("#oldphone").attr("readonly", true);
	$("#name").attr("readonly", false);
	$("#name").val("");
	$("#phone").val("");
	$("#temphone").val("");
	$("#oldphone").val("");
	document.getElementById("cap").clear();
	document.getElementById("cap").start();
	$("#strImage").val("");
	$("#custphone").val("拍照");
	setTimeout("document.getElementById('cap').selectRect(0.3,0.25,0.6,0.8);",
			1000);
}

function sechbill() {
	$("#curstate").val("2");
	var strSearchContent = $("#strSearchContent").val();
	if (strSearchContent == "") {
		return;
	}
	$("#postbill").attr("disabled", true);
	$.get(contextURL + "/cc018/sechBill.action", {
		"billid" : strSearchContent
	}, function(data) {
		if (data.strMessage != "") {
			$.ligerDialog.error(data.strMessage);
		} else {
			$("#curstate").val("2");
			claerTable();
			document.getElementById("cap").stop();
			document.getElementById("cap").clear();
			if (data.curMaster.img != null && data.curMaster.img != "") {
				document.getElementById("cap").loadBase64String(
						data.curMaster.img);
			}
			$("#cardno").val(data.curMaster.cardno);
			$("#readCard").attr("disabled", true);
			$("#cardtype").val(data.curMaster.cardtype);
			var temphone = checkNull(data.curMaster.phone);
			if (temphone != "") {
				document.getElementById("temphone").value = checkNull(temphone
						.substring(0, 3)
						+ "****" + temphone.substring(7, 11));
			}
			$("#phone").val(data.curMaster.phone);
			$("#oldphone").val(data.curMaster.oldphone);
			// $("#cid").val(data.curMaster.cid);
			$("#packno").attr("disabled", true);
			$("#storedamt").attr("readonly", true);
			$("#cashamt").attr("readonly", true);
			$("#name").val(data.curMaster.name.substring(0, 1) + "**");
			$("#cashpaycode").attr("disabled", true);
			$("#cashpaycode").val(data.curMaster.cashpaycode);
			$("#cashamt").val(data.curMaster.cashamt);
			$("#storedamt").val(data.curMaster.storedamt);
			$("#idtphone").val(data.curMaster.idtphone);
			if ($("#idtname").val() != "") {
				$("#idtname")
						.val(data.curMaster.idtname.substring(0, 1) + "**");
			} else {
				$("#idtname").val();
			}
			$("#billid").val(data.curMaster.id.billid);
			$("#zsamt").val(data.curMaster.zsamt);
			$("#storeAmt").val("");
			$("#totalamt").val(
					data.curMaster.cashamt * 1 + data.curMaster.storedamt * 1);
			var tab = $("#tabItem");
			var tabHTML = "";
			for ( var i = 0; i < data.lsyeaList.length; i++) {
				var lsyear = data.lsyeaList[i];
				tabHTML += "<tr><td>" + lsyear.itemName + "</td>";
				tabHTML += "<td>" + lsyear.num + "</td>";
				tabHTML += "<td>&nbsp;</td>";
				tabHTML += "<td>" + lsyear.amt + "</td>";
				tabHTML += "<td>" + lsyear.firstempno + "</td>";
				tabHTML += "<td>" + lsyear.firstempname + "</td>";
				tabHTML += "<td>" + lsyear.firstperf * 1 + "</td>";
				tabHTML += "<td>" + lsyear.sendempno + "</td>";
				tabHTML += "<td>" + lsyear.sendempname + "</td>";
				tabHTML += "<td>" + lsyear.sendperf * 1 + "</td>";
				tabHTML += "<td>" + lsyear.threeempno + "</td>";
				tabHTML += "<td>" + lsyear.threeempname + "</td>";
				tabHTML += "<td>" + lsyear.threeperf * 1 + "</td>";
				tabHTML += "<td>" + lsyear.fourempno + "</td>";
				tabHTML += "<td>" + lsyear.fourempname + "</td>";
				tabHTML += "<td>" + lsyear.fourperf * 1 + "</td>";
				tabHTML += "<td>" + lsyear.probz + "</td>";
			}
			tab.append(tabHTML);
		}
	});
}

function claerTable() {
	var tab = $("#tabItem");
	tab.empty();
}

function checkperf(obj, idx) {
	if (isNaN(obj.value)) {
		obj.value = "0";
	}
	if (obj.id.indexOf("firstperf") != -1) {
		$("#fourperf" + idx).val($("#firstperf" + idx).val());
	}
	var sumperf = $("#firstperf" + idx).val() * 1 + $("#sendperf" + idx).val()
			* 1 + $("#threeperf" + idx).val() * 1;

	if (sumperf * 1 > $("#amt" + idx).val() * 1) {
		$.ligerDialog.error("员工分享的总金额不能大于项目的总金额");
		obj.value = "0";
	}
}

function hotKeyOfSelf(key) {
	if (key == 13)// 回车
	{
		hotKeyEnter();
	}
}

function hotKeyEnter() {

	try {
		var fieldName = document.activeElement.name;
		var fieldId = document.activeElement.id;
		if (fieldId == "packno") {
			checkPack();
			window.event.keyCode = 9; // tab
			window.event.returnValue = true;
		} else if (fieldId == "bz") {
			addRows();
		} else {
			window.event.keyCode = 9; // tab
			window.event.returnValue = true;
			/*
			 * if(curitemManger!=null) { curitemManger.selectBox.hide(); }
			 * if(curEmpManger!=null) { curEmpManger.selectBox.hide(); }
			 */
		}
	} catch (e) {
		alert(e.message);
	}
}

function printBill() {
	if ($("#billid").val() == "") {
		return;
	}
	$
			.get(
					contextURL + "/cc018/printBill.action",
					{
						"billid" : $("#billid").val()
					},
					function(data) {
						if (data.strMessage != "") {
							$.ligerDialog.error(data.strMessage);
							return;
						}
						try {
							Stand_CheckPrintControl();// 检查是否有打印控件
							Stand_InitPrint("年卡销售_小票打印");
							Stand_SetPrintStyle("FontSize", 11);
							Stand_SetPrintStyle("Alignment", 2);
							Stand_SetPrintStyle("HOrient", 2);
							Stand_SetPrintStyle("Bold", 1);
							Stand_AddPrintContent(
									StandPRINT_CONTENT_TYPE_TEXT,
									31,
									15,
									351,
									25,
									"阿玛尼护肤造型"
											+ "("
											+ checkNull(parent.document
													.getElementById("strCompName").value)
											+ ")");
							Stand_SetPrintStyle("FontSize", 9);
							Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_TEXT,
									55, 15, 351, 25, "一切为你 想你所想");
							Stand_SetPrintStyle("FontSize", 11);
							Stand_SetPrintStyle("Bold", 0);
							document.getElementById("currdate_print").innerHTML = data.printDate;
							document.getElementById("memberCardId_print").innerHTML = data.curMaster.cardno;
							document.getElementById("keepAmount_print").innerHTML = maskAmt(
									checkNull(data.keepAmount_print), 2);
							document.getElementById("tradebillId_print").innerHTML = data.curMaster.id.billid;
							// document.getElementById("clerkName_print").innerHTML=data.curMaster.name
							// ;
							document.getElementById("telephone_print").innerHTML = data.companyinfo.compphone;
							document.getElementById("printTime_print").innerHTML = data.printTime;
							document.getElementById("address_print").innerHTML = data.companyinfo.compaddress;
							clearPreviousResult_report();
							if (data.lsyeaList != null) {
								for ( var i = 0; i < data.lsyeaList.length; i++) {
									addRowReport(
											checkNull(data.lsyeaList[i].itemName),
											maskAmt(data.lsyeaList[i].num, 1),
											maskAmt(
													checkNull(data.lsyeaList[i].amt) * 1,
													1));
								}
							}
							var printContent = document
									.getElementById("printContent").innerHTML;
							Stand_AddPrintContent(StandPRINT_CONTENT_TYPE_HTML,
									70, 0, 260, 800, printContent);
							// Stand_Preview();
							Stand_Print();
						} catch (e) {
							alert(e.message);
						}
						// Stand_Print();

						// catch(e){alert(e.message);}
					});
}

function clearPreviousResult_report() {
	var tblPrjs = document.getElementById("changeDetail");
	while (tblPrjs.childNodes.length > 0) {
		tblPrjs.removeChild(tblPrjs.childNodes[0]);
	}
}

function addRowReport(name, count, amt) {
	var row = document.createElement("tr");
	var cell = createCellWithText(checkNull(name) + ":");
	cell.style.fontSize = "12px";
	row.appendChild(cell);

	cell = createCellWithText(checkNull(amt));
	cell.style.fontSize = "12px";
	row.appendChild(cell);

	cell = createCellWithText(checkNull(count));
	cell.style.fontSize = "12px";
	row.appendChild(cell);

	document.getElementById("changeDetail").appendChild(row);
}

function changePhone() {
	if ($("#oldphone").val() != "") {
		$("#phone").val($("#oldphone").val());
		$("#temphone").val($("#oldphone").val());
	}
	checkPhone();
}

function tempChange() {
	if ($("#temphone").val() != "") {
		$("#phone").val($("#temphone").val());
	}
	checkPhone();
}

function checkPhone() {
	if ($("#phone").val() == "") {
		return;
	}
	if (isMobil($("#phone").val()) == false) {
		$.ligerDialog.error("手机号码格式不正确");
		$("#temphone").val("");
		$("#phone").val("");
		$("#name").val("");
		return;
	}
	showDialogmanager = $.ligerDialog.waitting("正在查询,请稍候...");
	$.get(contextURL + "/cc018/checkPhone.action", {
		"curMaster.phone" : $("#phone").val(), "_stamp": Math.random()
	}, function(data) {
		if (data.strMessage != "") {
			//$.ligerDialog.error(data.strMessage);
			$.ligerDialog.confirm('该年卡不存在，是否新增年卡资料', function(result) {
				if (result == true) {
					addYardDialog = $.ligerDialog
							.open({
								height : null,
								url : contextURL
										+ '/CardControl/CC018/addYearInfo.jsp',
								width : 560,
								height : 768,
								showMax : false,
								showToggle : false,
								showMin : false,
								isResize : false,
								title : '年卡新增'
							});
				} else {
					$("#temphone").val("");
					$("#phone").val("");
					$("#name").val("");
					$("#idtphone").val("");
					$("#idtname").val("");
				}
			});
			//document.getElementById("cap").clear();
			//document.getElementById("cap").start();
			//$("#strImage").val("");
			//$("#custphone").val("拍照");
			//setTimeout("document.getElementById('cap').selectRect(0.3,0.25,0.6,0.8);",1000);
		} else {
			document.getElementById("cap").clear();
			document.getElementById("cap").stop();
			if (data.yearcardinof.img != null && data.yearcardinof.img != "") {
				document.getElementById("cap").loadBase64String(
						data.yearcardinof.img);
				$("#strImage").val(data.yearcardinof.img);
				$("#name").val(data.yearcardinof.name);
			}
		}
		showDialogmanager.close();
	});
}

function checkPack() {
	if ($("#packno").val() == "") {
		return;
	}
	$.getJSON(contextURL + "/cc018/checkPack.action", {
		"strPackNo" : $("#packno").val()
	}, function(data) {
		if (data.strMessage != "") {
			$.ligerDialog.error(data.strMessage);
			$("#packno").val("");
		} else {
			$("#packname").val(data.mapMpackageinfo.packagename);
		}
	});
}

function selectCall() {
	checkPack();
}

function addRows() {
	if ($("#cardno").val() == "") {
		$.ligerDialog.error("请先读取卡号");
		return;
	}
	if ($("#packno").val() == "") {
		document.getElementById("packno").focus();
		return;
	}
	var isExist = false;
	var packno = $("#packno").val();
	if(packno=="201047" || packno=="201048" || packno=="201049"){
		$("input[name$='.packno']").each(function(){
			if($(this).val()==packno){
				isExist=true;
			}
		});
	}
	if(isExist){
		$.ligerDialog.error("已经购买过相关的套餐，不能重复购买！");
		return;
	}
	var params = {"strPackNo": $("#packno").val(),"curMaster.phone":$("#phone").val(), "_random": Math.random()};
	var url = contextURL+"/cc018/checkYearPackNo.action"; 
	$.post(url, params, function(data){
		if(checkNull(data.strMessage)==""){
			lsDkageinfos();
		}else{
			$.ligerDialog.error(data.strMessage);
		}
	}).error(function(e){$.ligerDialog.error("系统异常，请重试！");});
	//$("#packno").select();
}

function isMobil(s) {
	var patrn = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
	if (!patrn.exec(s))
		return false;
	return true;
}

function checkAmt(obj)
{
	if (isNaN(obj.value)) {
		obj.value = "0";
	}
}

function validateFastDyNo(obj)
{
		if(obj.value.substring(0,3)=="LOR")
		{
			$.ligerDialog.warn("LOR开头的抵用券不能使用!");
			return;
		}
		var iexiste=0;
		//验证抵用券是否在列表中已经存在
		var trs=$("#tabItem").find("tr");
		for(var i=0;i<trs.length;i++)
		{
			if($("#cashdyq"+i).val()==obj.value)
			{
				iexiste=1;
				break;
			}
		}
		if(iexiste>0)
		{
			$.ligerDialog.warn("输入的券编号已经存在于抵用的列表中,请确认!");
			obj.value="";
			return;
		}
		/*for (var rowid in commoninfodivdetial.records)
		{
			var usercardno=checkNull(commoninfodivdetial.records[rowid]['nointernalcardno']);
			if(obj.value==usercardno)
			{
				iexiste=iexiste*1+1;
			}
		}
		if(iexiste>1)
		{
				$.ligerDialog.warn("输入的券编号已经存在于抵用的列表中,请确认!");
				document.getElementById("fastCashDkAmt").select();
				document.getElementById("fastCashDkAmt").focus();
				commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
				return;
		}*/
		var requestUrl ="cc018/validateNointernalcardno.action"; 
		var responseMethod="validateNointernalcardnoMessage";
		var params="strDiyqNo="+obj.value;	
		sendRequestForParams_p(requestUrl,responseMethod,params );
}


function validateNointernalcardnoMessage(request)
{
   	try
    {
    	var responsetext = eval("(" + request.responseText + ")");
		var strmessage=	checkNull(responsetext.strMessage);
   		if(strmessage!="")
   		{
   			$.ligerDialog.warn(strmessage);
   			document.getElementById("dyqno").select();
			document.getElementById("dyqno").focus();
   			//commoninfodivdetial.updateRow(curRecord,{nointernalcardno:'',changebydyqamt:'0'});
   		}
   		else
   		{
   			document.getElementById("dyqamt").value=responsetext.diyqAmt;
   		}


   	}catch(e){alert(e.message);}
}

//检查微信扫码是否存在
function checkRandomno(){
	var randomno=$("#randomno").val();
	if(checkNull(randomno)==""){
		return;
	}
	validateCscardno(randomno);
	/*
	var params = {"strRandomno":randomno}
	var url = contextURL+"/cc011/checkWXRandomno.action"; 
	$.post(url, params, function(data){
		if(data.strMessage!=""){
			$.ligerDialog.warn(data.strMessage);
			$("#randomno").val("");
		}else{
			$("#changecardno").val(data.wxbandcard.cardno);
			validateCscardno(data.wxbandcard.cardno);
		}
	}).error(function(e){$.ligerDialog.error("读取信息失败，请重试！");});*/
}

function destory_activex(){
	var active_object_id='cap'; //activex的控件id
	var activex_obj=document.getElementById(active_object_id);
	var parent_element=activex_obj.parentElement; //找到控件的父元素
	//删除activex父元素的所有子元素 
	while (parent_element.children.length>0){
		parent_element.removeChild(parent_element.children[0]);
	}
}