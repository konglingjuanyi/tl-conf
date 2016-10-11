<!DOCTYPE html>
<html>
<head>
  	<title>跳羚分布式综合管理平台</title>
  	<#import "/common/common.ftl" as netCommon>
	<@netCommon.commonStyle />
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && "off" == cookieMap["adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>使用教程<small></small></h1>
			<!--
			<ol class="breadcrumb">
				<li><a><i class="fa fa-dashboard"></i>调度中心</a></li>
				<li class="active">使用教程</li>
			</ol>
			-->
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="callout callout-info">
				<h4>简介：TL-MANAGER</h4>
				<br>
				<p>
					<a target="_blank" href="http://git.rfdoa.cn/cloud/tl-conf">git地址</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<iframe src="https://ghbtns.com/github-btn.html?user=yuhonglie&repo=tl-conf&type=star&count=true" frameborder="0" scrolling="0" width="170px" height="20px" style="margin-bottom:-5px;"></iframe>
					<br><br>
					<a target="_blank" href="http://git.rfdoa.cn/cloud/tl-conf">cnblog地址</a>
					<br><br>
					<a >技术交流群(仅作技术交流)：659108878</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<#--<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=4686e3fe01118445c75673a66b4cc6b2c7ce0641528205b6f403c179062b0a52">-->
						<#--<img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="《tl-javaer》" title="《tl-javaer》">-->
					<#--</a>-->
				</p>
				<p></p>
            </div>
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
</body>
</html>
