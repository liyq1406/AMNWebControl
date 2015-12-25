<title></title>
<link rel="stylesheet" type="text/css"
	href="../../lib/ligerUI/skins/Aqua/css/ligerui-all.css">
	<script type="text/javascript"
		src="../../lib/jquery/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../../lib/ligerUI/js/core/base.js"></script>
	<script type="text/javascript"
		src="../../lib/ligerUI/js/plugins/ligerLayout.js"></script>
	<style type="text/css">
</style>
	<script type="text/javascript">
		$(function() {
			$("#layout1").ligerLayout({
				leftWidth : 200,
				allowLeftCollapse : false,
				allowRightCollapse : false
			});
		});
	</script>
	<style type="text/css">
body {
	padding: 10px;
	margin: 0;
}

#layout1 {
	width: 100%;
	margin: 40px;
	height: 400px;
	margin: 0;
	padding: 0;
}

#accordion1 {
	height: 270px;
}

h4 {
	margin: 20px;
}
</style>
	<div style="height: 586px;" id="layout1" class="l-layout"
		ligeruiid="layout1">
		<div style="left: 0px; top: 53px; width: 200px; height: 478px;"
			class="l-layout-left">
			<div class="l-layout-header">
				<div class="l-layout-header-inner"></div>
			</div>
			<div class="l-layout-content" position="left"></div>
		</div>
		<div style="left: 203px; top: 53px; width: 1469px; height: 478px;"
			class="l-layout-center">
			<div class="l-layout-header">标题</div>
			<div style="height: 453px;" class="l-layout-content" title=""
				position="center">
				<h4>$("#layout1").ligerLayout({ leftWidth: 200 });</h4>
				固定layout的左边的宽度为200px
			</div>
		</div>
		<div style="left: 1675px; top: 53px; width: 170px; height: 478px;"
			class="l-layout-right">
			<div class="l-layout-header">
				<div class="l-layout-header-inner"></div>
			</div>
			<div class="l-layout-content" position="right"></div>
		</div>
		<div style="top: 0px; height: 50px;" class="l-layout-top">
			<div class="l-layout-content" position="top"></div>
		</div>
		<div style="top: 534px; height: 50px;" class="l-layout-bottom">
			<div class="l-layout-content" position="bottom"></div>
		</div>
		<div class="l-layout-lock"></div>
		<div style="left: 200px; top: 53px; height: 478px; display: block;"
			class="l-layout-drophandle-left"></div>
		<div style="left: 1672px; top: 53px; height: 478px; display: block;"
			class="l-layout-drophandle-right"></div>
		<div style="top: 50px; width: 1845px; display: block;"
			class="l-layout-drophandle-top"></div>
		<div style="top: 531px; width: 1845px; display: block;"
			class="l-layout-drophandle-bottom"></div>
		<div class="l-layout-dragging-xline"></div>
		<div class="l-layout-dragging-yline"></div>
		<div style="height: 586px;" class="l-dragging-mask"></div>
		<div style="top: 53px; height: 478px; display: none;"
			class="l-layout-collapse-left">
			<div class="l-layout-collapse-left-toggle"></div>
		</div>
		<div style="top: 53px; height: 478px; display: none;"
			class="l-layout-collapse-right">
			<div class="l-layout-collapse-right-toggle"></div>
		</div>
	</div>
	<div style="display: none;"></div>