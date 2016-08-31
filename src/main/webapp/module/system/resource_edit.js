$(function($) {
	runPageLogic();

	initResourceTreeGrid();
	initChildrenResourceTreeGrid();
});

function runPageLogic(){
	$("#btnSearch").click(function(){
		var postData = $("#searchForm").form2json();
		$("#resourceGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: postData}}).trigger('reloadGrid');
	});
	
	$("#selectParentNode").click(function(){
		displayParentNodeInput();
		initResourceTreeNodeGrid();
	});
	
	$("#btnSave").click(function(){
		var $form = $("#dataForm");
		var actionUrl = g_contextPath +"/system/resource/save.shtml";
        $form.ajaxPostForm(actionUrl,
             function (response) {
				notifySuccess(response.singleMessage);
             }
        );
	});
	
	$("#btnTreeSave").click(function(){
		var $form = $("#treeDataForm");
		var actionUrl = g_contextPath +"/system/resource/saveTree.shtml";
        $form.ajaxPostForm(actionUrl,
             function (response) {
        		$('#resource_tree_modal_div').modal('hide');
				notifySuccess(response.singleMessage);
				$("#resourceTreeGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: $("#searchForm").form2json()}}).trigger('reloadGrid');
             }
        );
	});
	
	var resourceOrderSpinner = defaultSpinner("resourceOrder");
	var treeNodeOrderSpinner = defaultSpinner("treeNodeOrder");
	
	$("#selectParentNode").tooltip({
		show: {
			effect: "slideDown",
			delay: 250
		}
	});
	
	$("#selectParentResource").click(function(){
		initTreeGrid();
		//displayParentResourceInput();
		$("#resourceTreeGrid").jqGrid('setGridParam',{datatype:'json', postData : {}}).trigger('reloadGrid');
	});
}

function initResourceTreeGrid(){
	var $resourceTreeGrid = $("#resourceTreeGrid");
    var resourceTreeGridPager = "#resourceTreeGridPager";
    
    $resourceTreeGrid.jqGrid({
    	url: g_contextPath +"/system/resource/searchResourceTree.shtml",
    	datatype: "local",
        mtype: "POST",
		colNames:['Node Name', 'Node Description','Parent Node Name','Node Order','id'],
		colModel:[
			{name:'nodeName',index:'nodeName', width:2},
			{name:'nodeDesc',index:'nodeDesc', width:1},
			{name:'parentNodeName',index:'parentResourceTree.nodeName',width:4},
			{name:'nodeOrder',width:1},
			{name:'treeId',width:1,hidden:true},
		], 
		pager : resourceTreeGridPager,
		multiselect: true,
        multiboxonly: true,
		loadComplete : defaultGridLoadComplete,
		caption: "Resource Tree List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $resourceTreeGrid.jqGrid('getRowData', rowId);
            id = data.treeId;
            $("#childrenResourceTreeGrid").jqGrid('setGridParam',{datatype:'json', postData : {parentTreeId: id}}).trigger('reloadGrid');
        }
	});

    $resourceTreeGrid.jqGrid('navGrid', resourceTreeGridPager, {
        //navbar options
        search: false,
        refresh: true,
        del:true,
    }, {}, {}, {
    	recreateForm: true,
		beforeShowForm : beforeDeleteCallback,
		onclickSubmit: function(options, rowId) {
			var ids = getSelStrByName($resourceTreeGrid,"treeId");
            options.url = g_contextPath +"/system/resource/deleteResourceTree.shtml?ids="+ids;
        },
        afterSubmit: afterDeleteSubmit,
    }, {
        //search form
        recreateForm: true,
        //afterShowSearch: defaultSearchFormAfterShowSearch,
        //afterRedraw: defaultSearchFormAfterRedraw
    }, {}).jqGrid('navButtonAdd', resourceTreeGridPager, {
        caption: "",
        buttonicon: "icon-pencil blue",
        onClickButton: onEditRecord,
        position: "first",
        title: "Edit Record",
        cursor: "pointer"
    }).jqGrid('navButtonAdd', resourceTreeGridPager, {
        caption: "",
        buttonicon: "icon-plus-sign purple",
        onClickButton: onNewRecord,
        position: "first",
        title: "New Record",
        cursor: "pointer"
    });
	
    $("#resourceTreeGrid").jqGrid('setGridParam',{datatype:'json', postData : {resourceId: $("#resourceId").val()}}).trigger('reloadGrid');
    
	function onNewRecord(){
		$("#parentNodeName").text("");
		displayResourceTreeInput();
	}
	function onEditRecord(){
		var ids = getSelStrByName($resourceTreeGrid,"treeId");
		if(ids==""){
			return ;
		}
		var id = ids.substring(0,ids.indexOf(","));
        var actionUrl = g_contextPath +"/system/resource/id/findResourceTree.shtml".replace("id", id);
        ajaxGet(actionUrl,
            function (response) {
        		loadResourceTreeDetails(response.data);
        		displayResourceTreeInput();
            }
        );
	}
	
	function loadResourceTreeDetails(data){
		$("#treeDataForm #treeId").val(data.treeId);
		$("#treeDataForm #treeNodeName").val(data.treeNodeName);
		$("#treeDataForm #treeNodeOrder").val(data.treeNodeOrder);
		$("#treeDataForm #treeNodeDesc").val(data.treeNodeDesc);
		$("#treeDataForm #parentNodeName").text(data.parentNodeName);
		$("#treeDataForm #parentId").val(data.parentId);
	}
}

function initChildrenResourceTreeGrid(){
	var $childrenResourceTreeGrid = $("#childrenResourceTreeGrid");
    var childrenResourceTreeGridPager = "#childrenResourceTreeGridPager";
    
    $childrenResourceTreeGrid.jqGrid({
    	url: g_contextPath +"/system/resource/searchChildrenTreeNode.shtml",
    	datatype: "local",
        mtype: "POST",
		colNames:['Node Name', 'Node Description','Resource Name','Node Order','id'],
		colModel:[
			{name:'nodeName',index:'nodeName', width:3},
			{name:'nodeDesc',index:'nodeDesc', width:3},
			{name:'resourceName',index:'securityResource.resourceName',width:3},
			{name:'nodeOrder',width:1},
			{name:'treeId',width:1,hidden:true},
		], 
		pager : childrenResourceTreeGridPager,
		//toppager: true,
		multiselect: false,
		//multikey: "ctrlKey",
        multiboxonly: true,
		loadComplete : defaultGridLoadComplete,
		caption: "Children Resource Tree List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $resourceTreeGrid.jqGrid('getRowData', rowId);
            id = data.resourceId;
            
        }
	});

    $childrenResourceTreeGrid.jqGrid('navGrid', childrenResourceTreeGridPager, {
        //navbar options
        search: false,
        refresh: true,
        del:false,
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
    }, {});
}

function initResourceTreeNodeGrid(){
	var $resourceTreeNodeGrid = $("#resourceTreeNodeGrid");
    var resourceTreeNodeGridPager = "#resourceTreeNodeGridPager";
   
    $resourceTreeNodeGrid.jqGrid({
    	url: g_contextPath +"/system/resource/searchTreeNode.shtml",
    	datatype: "local",
        mtype: "POST",
		colNames:['Node Name', 'Resource Name','Resource Content','Parent Node Name','Node Order','id'],
		colModel:[
			{name:'nodeName',index:'nodeName', width:3},
			{name:'resourceName',index:'securityResource.resourceName', width:3},
			{name:'resourceContent',index:'securityResource.resourceContent', width:4},
			{name:'parentNodeName',index:'parentResourceTree.nodeName',width:3},
			{name:'nodeOrder',index:'nodeOrder',width:2},
			{name:'treeId',width:1,hidden:true},
		],
		pager : resourceTreeNodeGridPager,
		viewrecords : true,
		autowidth: false,
        width : $("#resource_tree_modal_div .modal-content").width()*0.95,
		loadComplete : defaultGridLoadComplete,
		caption: "Resource Tree Node List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $resourceTreeNodeGrid.jqGrid('getRowData', rowId);
            var id = data.treeId;
            var nodeName = data.nodeName;
            $("#parentId").val(id);
            $("#parentNodeName").text(nodeName);
            $('#parent_node_modal_div').modal('hide');
        }
	});

    $resourceTreeNodeGrid.jqGrid('navGrid', resourceTreeNodeGridPager, {
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
		$resourceTreeNodeGrid.jqGrid().setGridWidth($("#resource_tree_modal_div .modal-content").width()*0.95);
		$(window).bind("onresize", this);
	});
	$("#resourceTreeNodeGrid").jqGrid('setGridParam',{datatype:'json', postData : {}}).trigger('reloadGrid');
}
function displayResourceTreeInput() {
	 $('#resource_tree_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};

function displayParentNodeInput() {
	 $('#parent_node_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};