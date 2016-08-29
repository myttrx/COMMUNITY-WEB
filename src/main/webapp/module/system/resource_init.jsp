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
			<li class="active">系统资源管理</li>
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
				<form class="form-horizontal" role="form" id="searchForm" name="searchForm">

					<div class="row">
						<div class="form-group form-group-sm">
							<label class="control-label col-md-3 col-lg-2">Resource Name</label>
							<div class="col-md-3 col-lg-2">
								<input type="text" value="" id="resourceName" name="resourceName" class="form-control">
							</div>
							<label class="control-label col-md-3 col-lg-2" for="form-field-1">Resource Type</label>
							<div class="col-sm-4">
								<select class="width-60 chosen-select" id="resourceType" name="resourceType" data-placeholder="Please Choose One"> ${resourceTypeOpts }
								</select>
							</div>
						</div>
					</div>


					<div class="row">
						<div class="form-group form-group-sm">
							<label class="control-label col-md-3 col-lg-2">Resource Content</label>
							<div class="col-md-3 col-lg-2">
								<input type="text" value="" id="resourceContent" name="resourceContent" class="form-control">
							</div>
							<label class="control-label col-md-3 col-lg-2">Resource Description</label>
							<div class="col-md-3 col-lg-2">
								<input type="text" value="" id="resourceDesc" name="resourceDesc" class="form-control">
							</div>
						</div>
					</div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-purple btn-sm" type="button" id="btnSearch">
								<spring:message code="btn.search" />
							</button>
							&nbsp; &nbsp; &nbsp;
							<button type="reset" class="btn btn-light btn-sm">
								<spring:message code="btn.reset" />
							</button>
						</div>
					</div>

				</form>

				<table id="resourceGrid" class="jqGrid">

				</table>

				<div id="resourceGridPager"></div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->

	<div class="modal fade" id="resource_modal_div" tabindex="-1" role="dialog" aria-labelledby="duplicate_case_label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only"></span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Security Resource</h4>
				</div>
				<div class="modal-body">
					<form:form id="dataForm" modelAttribute="resourceModel" class="form-horizontal" role="form">
						<fieldset></fieldset>
						<div class="row">
							<div class="form-group form-group-sm">
								<label class="control-label col-md-3 col-lg-2">Resource Name<spring:message code="mandatorySymbol" /></label>
								<div class="col-md-3 col-lg-3">
									<form:input path="resourceName" type="text" class="form-control col-xs-10 col-sm-12" placeholder="Resource Name" id="resourceName" maxlength="200" />
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
								<div class="col-md-9 col-lg-9">
									<form:input path="resourceContent" type="text" class="form-control col-xs-10 col-sm-12" placeholder="Resource Content" id="resourceContent" maxlength="100" />
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<div class="form-group form-group-sm">
								<label class="control-label col-md-3 col-lg-2">Resource Order<spring:message code="mandatorySymbol" /></label>
								<div class="col-md-3 col-lg-3">
									<form:input path="resourceOrder" type="text" class="ui-spinner-input" placeholder="1" id="resourceOrder" maxlength="19" />
								</div>
								<label class="control-label col-md-2 col-lg-3" for="form-field-1">Resource Description</label>
								<div class="col-md-3 col-lg-3">
									<form:input path="resourceDesc" type="text" class="form-control col-xs-10 col-sm-12" placeholder="Resource Description" id="resourceDesc" maxlength="300" />
								</div>
							</div>
						</div>

					</form:form>

				</div>
				<div class="modal-footer">
					<button class="btn btn-info btn-sm" type="button" id="btnSave">
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


	<div class="modal fade" id="nestable_parent_resource_modal_div" tabindex="-1" role="dialog" aria-labelledby="duplicate_case_label" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only"></span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Security Tree Resource</h4>
				</div>
				<div class="modal-body">

					<!-- 					<table id="resourceTreeGrid" class="jqGrid"> -->

					<!-- 					</table> -->

					<!-- 					<div id="resourceTreeGridPager"></div> -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->

							<div class="row">
								<div class="col-sm-6">
									<div class="dd" id="nestable">
										<ol class="dd-list">

											<li class="dd-item" data-id="2">
												<div class="dd-handle">Item 2</div>

												<ol class="dd-list">
													<li class="dd-item" data-id="3">
														<div class="dd-handle">
															Item 3 <a data-rel="tooltip" data-placement="left" title="Change Event Date" href="#" class="badge badge-primary radius-5 tooltip-info pull-right white no-hover-underline"> <i class="bigger-120 icon-calendar"></i>
															</a>
														</div>
													</li>

													<li class="dd-item" data-id="4">
														<div class="dd-handle">
															<span class="orange">Item 4</span> <span class="lighter grey"> &nbsp; with some description </span>
														</div>
													</li>

													<li class="dd-item" data-id="5">
														<div class="dd-handle">
															Item 5
															<div class="pull-right action-buttons">
																<a class="blue" href="#"> <i class="icon-pencil bigger-130"></i>
																</a> <a class="red" href="#"> <i class="icon-trash bigger-130"></i>
																</a>
															</div>
														</div>

														<ol class="dd-list">
															<li class="dd-item item-orange" data-id="6">
																<div class="dd-handle">Item 6</div>
															</li>

															<li class="dd-item item-red" data-id="7">
																<div class="dd-handle">Item 7</div>
															</li>

															<li class="dd-item item-blue2" data-id="8">
																<div class="dd-handle">Item 8</div>
															</li>
														</ol>
													</li>

													<li class="dd-item" data-id="9">
														<div class="dd-handle btn-yellow no-hover">Item 9</div>
													</li>

													<li class="dd-item" data-id="10">
														<div class="dd-handle">Item 10</div>
													</li>
												</ol>
											</li>


										</ol>
									</div>
								</div>

								<div class="vspace-sm-16"></div>


							</div>

							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<div class="modal-footer">
					<button class="btn btn-info btn-sm" type="button" id="test">
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
	<!-- inline scripts related to this page -->
	<script src="${g_contextPath}/module/system/resource_init.js"></script>
</body>
</html>