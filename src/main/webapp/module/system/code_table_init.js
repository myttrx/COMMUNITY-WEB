$(function($) {
	
	$("#btnSearch").click(function(){
		var postData = $("#searchForm").form2json();
		$("#codeTableGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: postData}}).trigger('reloadGrid');
	});
	
	$("#btnSave").click(function(){
		var $form = $("#dataForm");
		var actionUrl = g_contextPath +"/system/codeTable/save.shtml";
        $form.ajaxPostForm(actionUrl,
             function (response) {
        		$('#code_table_modal_div').modal('hide');
				notifySuccess(response.singleMessage);
				$("#codeTableGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: $("#searchForm").form2json()}}).trigger('reloadGrid');
             }
        );
	});
	

	var $grid_selector = $("#codeTableGrid");
    var pager_selector = "#gridPager";
    
    $grid_selector.jqGrid({
		//direction: "rtl",
    	url: g_contextPath +"/system/codeTable/search.shtml",
    	postData : {searchForm: $("#searchForm").form2json()},
		//data: grid_data,
    	datatype: "local",
        mtype: "POST",
		colNames:[' Id ', 'Type','Code','Description', 'Modified Date'],
		colModel:[
			{name:'codeTableId',index:'id', width:1,hidden:true},
			{name:'type', width:1},
			{name:'code',width:1},
			{name:'description',width:2},
			{name:'modifiedDate',width:1}
		], 
		pager : pager_selector,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
		//loadComplete : defaultGridLoadComplete,
		caption: "Code Table List",
        ondblClickRow: function (rowId, iRow, iCol, e) {
            var data = $grid_selector.jqGrid('getRowData', rowId);
            id = data.codeTableId;
            var findCodeTableUrl = g_contextPath +"/system/codeTable/id/findCodeTable.shtml";
            var actionUrl = findCodeTableUrl.replace("id", id);
            ajaxGet(actionUrl,
                function (response) {
            		loadDetails(response.data);
                }
            );

        }
	});

	$grid_selector.jqGrid('navGrid', pager_selector, {
        //navbar options
        search: true,
        refresh: true,
        del:true,
    }, {}, {}, {
    	recreateForm: true,
		beforeShowForm : beforeDeleteCallback,
		onclickSubmit: function(options, rowId) {
			var ids = getSelStrByName($grid_selector,"codeTableId");
            options.url = "deleteCodeTable.shtml?ids="+ids;
        },
        afterSubmit: afterDeleteSubmit,
    }, {
        //search form
        recreateForm: true,
        //afterShowSearch: defaultSearchFormAfterShowSearch,
        //afterRedraw: defaultSearchFormAfterRedraw
    }, {}).jqGrid('navButtonAdd', pager_selector, {
        caption: "",
        buttonicon: "icon-plus-sign purple",
        onClickButton: onNewRecord,
        position: "first",
        title: "New Record",
        cursor: "pointer"
    });
	
	function onNewRecord(){
		displayInput();
	}

	function loadDetails(data){
		$("#dataForm #codeTableId").val(data.codeTableId);
		$("#dataForm #codeTableType").val(data.codeTableType);
		$("#dataForm #codeTableType").trigger('chosen:updated');
		$("#dataForm #code").val(data.code);
		$("#dataForm #description").val(data.description);
		displayInput();
	}
});

function displayInput() {
	 $('#code_table_modal_div').modal({ 
		 backdrop: 'static', 
		 show: true, 
		 keyboard:false,
	 });
};