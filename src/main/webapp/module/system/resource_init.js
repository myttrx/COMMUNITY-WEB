$(function($) {
	runPageLogic();
	initResourceGrid();
});

function runPageLogic(){
	$("#btnSearch").click(function(){
		var postData = $("#searchForm").form2json();
		$("#resourceGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: postData}}).trigger('reloadGrid');
	});
	
	
	//var spinner = $("#resourceOrder").spinner();
	defaultSpinner("resourceOrder");
	
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
            var actionUrl = g_contextPath +"/system/resource/id/edit.shtml".replace("id", id);
            redirectTo(actionUrl);
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
}

function displayResourceInput() {
	 $('#resource_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};
