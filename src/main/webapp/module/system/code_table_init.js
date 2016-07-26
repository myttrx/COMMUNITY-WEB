$(function($) {
	
	$("#btnSearch").click(function(){
		var postData = $("#searchForm").form2json();
		$("#codeTableGrid").jqGrid('setGridParam',{datatype:'json', postData : {searchForm: postData}}).trigger('reloadGrid');
	});
	
	$("#codeTableType").chosen(); 

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
			{name:'codeTableId', width:1},
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
		editurl: "/dummy.html",//nothing is saved
		caption: "Code Table List",
	});

	//enable search/filter toolbar
    //$grid_selector.jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
				$(cell).find('input[type=checkbox]')
					.wrap('<label class="inline" />')
					.addClass('ace ace-switch ace-switch-5')
					.after('<span class="lbl"></span>');
		}, 0);
	}
	//enable datepicker
	function pickDate(cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell).find('input[type=text]')
					.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
		}, 0);
	}


	$grid_selector.jqGrid('navGrid', pager_selector, {
        //navbar options
        search: true,
        refresh: true,
    }, {}, {}, {}, {
        //search form
        recreateForm: true,
        //afterShowSearch: defaultSearchFormAfterShowSearch,
        //afterRedraw: defaultSearchFormAfterRedraw
    }, {}).jqGrid('navButtonAdd', pager_selector, {
        caption: "",
        buttonicon: "icon-plus-sign purple",
        //onClickButton: onImportRecord,
        position: "first",
        title: "New Record",
        cursor: "pointer"
    });
	
	function style_edit_form(form) {
		//enable datepicker on "sdate" field and switches for "stock" field
		form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
			.end().find('input[name=stock]')
				  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

		//update buttons classes
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
		buttons.eq(1).prepend('<i class="icon-remove"></i>')
		
		buttons = form.next().find('.navButton a');
		buttons.find('.ui-icon').remove();
		buttons.eq(0).append('<i class="icon-chevron-left"></i>');
		buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
	}

	function style_delete_form(form) {
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
		buttons.eq(1).prepend('<i class="icon-remove"></i>')
	}
	
	function style_search_filters(form) {
		form.find('.delete-rule').val('X');
		form.find('.add-rule').addClass('btn btn-xs btn-primary');
		form.find('.add-group').addClass('btn btn-xs btn-success');
		form.find('.delete-group').addClass('btn btn-xs btn-danger');
	}
	function style_search_form(form) {
		var dialog = form.closest('.ui-jqdialog');
		var buttons = dialog.find('.EditTable')
		buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
		buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
		buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
	}
	
	function beforeDeleteCallback(e) {
		var form = $(e[0]);
		if(form.data('styled')) return false;
		
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_delete_form(form);
		
		form.data('styled', true);
	}
	
	function beforeEditCallback(e) {
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_edit_form(form);
	}

	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');


});