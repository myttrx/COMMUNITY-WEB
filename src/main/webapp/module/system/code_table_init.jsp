<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="system.title" /></title>
<%@include file="/WEB-INF/common/global.jsp"%>

</head>
<body>

	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">

			<li><i class="icon-home home-icon"></i> <a href="#">Home</a></li>
			<li><a href="#">Tables</a></li>
			<li class="active">jqGrid plugin</li>
		</ul>
		<!-- .breadcrumb -->

		<div class="nav-search" id="nav-search">
			<form class="form-search">
				<span class="input-icon"> <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" /> <i class="icon-search nav-search-icon"></i>
				</span>
			</form>
		</div>
		<!-- #nav-search -->
	</div>

	<div class="page-content">
		<div class="page-header">
			<h1>
				jqGrid <small> <i class="icon-double-angle-right"></i> Dynamic tables and grids using jqGrid plugin
				</small>
			</h1>
		</div>
		<!-- /.page-header -->

		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="alert alert-info">
					<i class="icon-hand-right"></i> Please note that demo server is not configured to save the changes, therefore you may get an error message.
					<button class="close" data-dismiss="alert">
						<i class="icon-remove"></i>
					</button>
				</div>
				<form class="form-horizontal" role="form" id="searchForm" name="searchForm">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">Code Table Type</label>
						<div class="col-sm-4">
							<select class="width-40 chosen-select" id="codeTableType" name="codeTableType" data-placeholder="Please Choose One">
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="form-field-1" class="col-sm-3 control-label no-padding-right">Code</label>

						<div class="col-sm-4">
							<input type="text" class="col-xs-10 col-sm-5" placeholder="Code" id="code" name="code">
						</div>
					</div>

					<div class="form-group">
						<label for="form-field-1" class="col-sm-3 control-label no-padding-right">Description</label>

						<div class="col-sm-4">
							<input type="text" class="col-xs-10 col-sm-5" placeholder="Description" id="description" name="description">
						</div>
					</div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-purple btn-sm" type="button" id="btnSearch">
								<i class="icon-search icon-on-right bigger-110">Search</i>
							</button>
							&nbsp; &nbsp; &nbsp;
							<button type="reset" class="btn btn-sm"><i class="icon-undo bigger-110"></i> Reset</button>
						</div>
					</div>

				</form>

				<table id="codeTableGrid"></table>

				<div id="gridPager"></div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->

	<!-- inline scripts related to this page -->
	<script src="${g_contextPath}/module/system/code_table_init.js"></script>
</body>
</html>