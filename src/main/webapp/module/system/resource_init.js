$(function($) {
	runPageLogic();

	initResourceGrid();
});

function runPageLogic(){
	$("#btnSearch").click(function(){
		var postData = $("#searchForm").form2json();
		$("#resourceGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: postData}}).trigger('reloadGrid');
	});
	
	$("#btnSave").click(function(){
		var $form = $("#dataForm");
		var actionUrl = g_contextPath +"/system/resource/save.shtml";
        $form.ajaxPostForm(actionUrl,
             function (response) {
        		$('#resource_modal_div').modal('hide');
				notifySuccess(response.singleMessage);
				$("#resourceGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: $("#searchForm").form2json()}}).trigger('reloadGrid');
             }
        );
	});
	
	
	$("#selectParentResource").tooltip({
		show: {
			effect: "slideDown",
			delay: 250
		}
	});
	
	$("#selectParentResource").click(function(){
		initTreeGrid();
		displayParentResourceInput();
		$("#resourceTreeGrid").jqGrid('setGridParam',{datatype:'json', postData : {}}).trigger('reloadGrid');
	});
}
//by using nestable control
function loadResourceTreeData(){
	var actionUrl = g_contextPath +"/system/resource/loadResourceTreeData.shtml";
	 ajaxGet(actionUrl,
       function (response) {
		 	//initTreeView(response.data);
		 	$('.dd-handle a').on('mousedown', function(e){
				e.stopPropagation();
			});
			
			$('[data-rel="tooltip"]').tooltip();
			 
       }
   );
}
function initTreeView(data){
	var dataSource = new DataSourceTree({data: eval("(" + data + ")")});
	$('#tree1').ace_tree({
		dataSource: dataSource ,
		multiSelect:false,
		loadingHTML:'<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
		'open-icon' : 'icon-minus',
		'close-icon' : 'icon-plus',
		'selectable' : true,
		'selected-icon' : 'icon-ok',
		'unselected-icon' : 'icon-remove'
	});
}
function initResourceGrid(){
	var $resourceGrid = $("#resourceGrid");
    var resourceGridPager = "#resourceGridPager";
    
    $resourceGrid.jqGrid({
		//direction: "rtl",
    	url: g_contextPath +"/system/resource/search.shtml",
    	postData : {searchForm: $("#searchForm").form2json()},
		//data: grid_data,
    	datatype: "local",
        mtype: "POST",
		colNames:['Resource name', 'Resource Type','Resource Content','Resource Desc', 'Resource Order','Modified Date','Modified By','id'],
		colModel:[
			{name:'resourceName',index:'resourceName', width:2},
			{name:'resourceType',index:'resourceType', width:2},
			{name:'resourceContent',width:4},
			{name:'resourceDesc',width:3},
			{name:'resourceOrder',width:1},
			{name:'modifiedDate',width:2},
			{name:'modifiedBy',width:1},
			{name:'resourceId',width:1,hidden:true},
		], 
		pager : resourceGridPager,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
		loadComplete : defaultGridLoadComplete,
		caption: "Resource List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $resourceGrid.jqGrid('getRowData', rowId);
            id = data.resourceId;
            alert(id);
            var findCodeTableUrl = g_contextPath +"/system/codeTable/id/findCodeTable.shtml";
            var actionUrl = findCodeTableUrl.replace("id", id);
            ajaxGet(actionUrl,
                function (response) {
            		loadDetails(response.data);
                }
            );

        }
	});

	$resourceGrid.jqGrid('navGrid', resourceGridPager, {
        //navbar options
        search: false,
        refresh: true,
        del:true,
    }, {}, {}, {
    	recreateForm: true,
		beforeShowForm : beforeDeleteCallback,
		onclickSubmit: function(options, rowId) {
			var ids = getSelStrByName($resourceGrid,"resourceId");
            options.url = "deleteResource.shtml?ids="+ids;
        },
        afterSubmit: afterDeleteSubmit,
    }, {
        //search form
        recreateForm: true,
        //afterShowSearch: defaultSearchFormAfterShowSearch,
        //afterRedraw: defaultSearchFormAfterRedraw
    }, {}).jqGrid('navButtonAdd', resourceGridPager, {
        caption: "",
        buttonicon: "icon-plus-sign purple",
        onClickButton: onNewRecord,
        position: "first",
        title: "New Record",
        cursor: "pointer"
    });
	
	function onNewRecord(){
		displayResourceInput();
	}

	function loadDetails(data){
		$("#dataForm #codeTableId").val(data.codeTableId);
		$("#dataForm #codeTableType").val(data.codeTableType);
		$("#dataForm #codeTableType").trigger('chosen:updated');
		$("#dataForm #code").val(data.code);
		$("#dataForm #description").val(data.description);
		displayInput();
	}
	
}
function initTreeGrid(){
	var $resourceTreeGrid = $("#resourceTreeGrid");
    var resourceTreeGridPager = "#resourceTreeGridPager";
   
    $resourceTreeGrid.jqGrid({
    	postData : {searchForm: $("#searchForm").form2json()},
    	url: g_contextPath +"/system/resource/searchResourceTree.shtml",
		//data: grid_data,
    	datatype: "local",
        mtype: "POST",
		colNames:['Node Name', 'Resource Content','Parent Node Name','Node Order','id'],
		colModel:[
			{name:'nodeName',index:'nodeName', width:2},
			{name:'resourceContent',index:'securityResource.resourceContent', width:4},
			{name:'parentNodeName',index:'parentResourceTree.nodeName',width:4},
			{name:'nodeOrder',index:'nodeOrder',width:1},
			{name:'treeId',width:1,hidden:true},
		],
		pager : resourceTreeGridPager,
		viewrecords : true,
		autowidth: false,
        width : $("#resource_modal_div .modal-content").width()*0.95,
		loadComplete : defaultGridLoadComplete,
		caption: "Resource Tree List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $resourceTreeGrid.jqGrid('getRowData', rowId);
            var id = data.treeId;
            var nodeName = data.nodeName;
            $("#parentResourceTreeId").val(id);
            $("#parentResource").text(nodeName);
        }
	});

    $resourceTreeGrid.jqGrid('navGrid', resourceTreeGridPager, {
        //navbar options
        search: true,
        refresh: true,
        del:false,
    }, {}, {}, {
    	recreateForm: true,
		beforeShowForm : beforeDeleteCallback,
		onclickSubmit: function(options, rowId) {
			
        },
        afterSubmit: afterDeleteSubmit,
    }, {
        //search form
        recreateForm: true,
    }, {});
    
	$(window).resize(function() {
		$(window).unbind("onresize");
		$resourceTreeGrid.jqGrid().setGridWidth($(".modal-content").width()*0.95);
		$(window).bind("onresize", this);
	});
}
function displayResourceInput() {
	 $('#resource_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};

function displayParentResourceInput() {
	 $('#parent_resource_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};