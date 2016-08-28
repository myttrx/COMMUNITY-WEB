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
			<li class="active"><a href="#">系统资源管理</a></li>
			<li class="active">系统资源编辑</li>
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
		<!-- /.page-header -->

		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form:form id="dataForm" modelAttribute="resourceModel" class="form-horizontal" role="form">
					<fieldset></fieldset>
					<form:input path="resourceId" type="text" id="resourceId" cssStyle="display: none;"/>
					<div class="row">
						<div class="form-group form-group-sm">
							<label class="control-label col-md-3 col-lg-2">Resource Name<spring:message code="mandatorySymbol" /></label>
							<div class="col-md-3 col-lg-3">
								<form:input path="resourceName" type="text" class="col-xs-10 col-sm-12" placeholder="Resource Name" id="resourceName" maxlength="200" />
							</div>
							<label class="control-label col-md-2 col-lg-3" for="form-field-1">Resource Type<spring:message code="mandatorySymbol" /></label>
							<div class="col-sm-4">
								<form:select path="resourceType" class="width-70 chosen-select" id="resourceType" data-placeholder="Please Choose One">
										${resourceTypeOpts }
								</form:select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-group form-group-sm">
							<label class="control-label col-md-3 col-lg-2">Resource Content<spring:message code="mandatorySymbol" /></label>
							<div class="col-md-3 col-lg-3">
								<form:input path="resourceContent" type="text" class="col-xs-10 col-sm-12" placeholder="Resource Content" id="resourceContent" maxlength="100" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-group form-group-sm">
							<label class="control-label col-md-3 col-lg-2">Resource Order<spring:message code="mandatorySymbol" /></label>
							<div class="col-md-3 col-lg-3">
								<form:input path="resourceOrder" type="text" class="col-xs-10 col-sm-12" placeholder="1" id="resourceOrder" maxlength="19" />
							</div>
							<label class="control-label col-md-2 col-lg-3" for="form-field-1">Resource Description</label>
							<div class="col-md-3 col-lg-3">
								<form:input path="resourceDesc" type="text" class="col-xs-10 col-sm-12" placeholder="Resource Description" id="resourceDesc" maxlength="300" />
							</div>
						</div>
					</div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-purple btn-sm" type="button" id="btnSave">
								<spring:message code="btn.save" />
							</button>
							&nbsp; &nbsp; &nbsp;
							<button type="reset" class="btn btn-light btn-sm">
								<spring:message code="btn.reset" />
							</button>
						</div>
					</div>

				</form:form>

				<table id="resourceTreeGrid" class="jqGrid">

				</table>

				<div id="resourceTreeGridPager"></div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->

	<div class="modal fade" id="resource_tree_modal_div" tabindex="-1" role="dialog" aria-labelledby="duplicate_case_label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only"></span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Security Resource Tree</h4>
				</div>
				<div class="modal-body">
					<form:form id="treeDataForm" modelAttribute="resourceTreeModel" class="form-horizontal" role="form">
						<fieldset></fieldset>
						<form:input path="resourceId" type="text" id="resourceId" cssStyle="display: none;"/>
						<div class="row">
							<div class="form-group form-group-sm">
								<label class="control-label col-md-3 col-lg-2">Node Name<spring:message code="mandatorySymbol" /></label>
								<div class="col-md-3 col-lg-3">
									<form:input path="treeNodeName" type="text" class="col-xs-10 col-sm-12" placeholder="Node Name" id="treeNodeName" maxlength="100" />
								</div>
								<label class="control-label col-md-2 col-lg-3" for="form-field-1">parentId</label>
								<div class="col-md-3 col-lg-3">
									<form:input path="parentId" type="text" class="col-xs-10 col-sm-12" placeholder="Parent Id" id="parentId" maxlength="19" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group form-group-sm">
								<label class="control-label col-md-3 col-lg-2">Node Order<spring:message code="mandatorySymbol" /></label>
								<div class="col-md-3 col-lg-3">
									<form:input path="treeNodeOrder" type="text" class="col-xs-10 col-sm-12" placeholder="1" id="treeNodeOrder" maxlength="19" />
								</div>
								<label class="control-label col-md-2 col-lg-3" for="form-field-1">Node Description</label>
								<div class="col-md-3 col-lg-3">
									<form:input path="treeNodeDesc" type="text" class="col-xs-10 col-sm-12" placeholder="Node Description" id="treeNodeDesc" maxlength="300" />
								</div>
							</div>
						</div>

					</form:form>

				</div>
				<div class="modal-footer">
					<button class="btn btn-info btn-sm" type="button" id="btnTreeSave">
						<spring:message code="btn.save" />
					</button>
					&nbsp;
					<button type="button" class="btn btn-grey btn-sm" data-dismiss="modal">
						<spring:message code="btn.close" />
					</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 
	<div class="form-group" style="">
		<label for="sp_numbering_plan" class="control-label col-md-3 col-lg-2">Parent Resource<spring:message code="mandatorySymbol" /></label>
		<div class="col-md-3 col-lg-6">
			<p class="form-control-static">
				<em id="parentResource"></em> <a class="pink" id="selectParentResource" href="#" title="Select Parent Resource"> <i class="icon-hand-right"></i> Select Parent Resource
				</a>
			</p>

		</div>
	</div>
 -->
 
 <!-- 
	<div class="modal fade" id="parent_resource_modal_div" tabindex="-1" role="dialog" aria-labelledby="duplicate_case_label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only"></span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Security Tree Resource</h4>
				</div>
				<div class="modal-body">
					<table id="resourceTreeGrid" class="jqGrid">
					</table>
					<div id="resourceTreeGridPager"></div>
				</div>

			</div>
		</div>
	</div>
 -->

	<!-- inline scripts related to this page -->
	
	<script src="${g_contextPath}/module/system/resource_edit.js"></script>
</body>
</html>