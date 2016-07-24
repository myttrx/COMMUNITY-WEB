
//global settings for jqgrid
$.extend(jQuery.jgrid.defaults, {
    autowidth: true,
    height: "100%",
    ignoreCase: true,
    rownumbers: true,
    viewrecords: true,
    rowNum: 10,
    rowList: [10, 20, 30],
    altRows: true,
    toppager: false,
    multiselect: false,
    //multikey: "ctrlKey",
    multiboxonly: false
});
$.extend(jQuery.jgrid.nav, {
    editicon: 'icon-pencil blue',
    addicon: 'icon-plus-sign purple',
    delicon: "icon-trash red",
    searchicon: 'icon-search orange',
    refreshicon: 'icon-refresh green',
    viewicon: "icon-zoom-in grey",
    edit: false,
    add: false,
    del: false,
    view: false,
    search: false,
    refresh: false
});
$.extend(jQuery.jgrid.search, {
    sopt: ['eq', 'bw', 'ew', 'cn'],
    multipleSearch: true,
    multipleGroup: true,
    showQuery: false,
    closeOnEscape: true,
    closeAfterSearch: true,
    caption: "Advanced Search"
});


function defaultGridLoadComplete(grid) {
    setTimeout(function() {
        styleCheckbox(grid);
        updateActionIcons(grid);
        updatePagerIcons(grid);
        enableTooltips(grid);
    }, 0);
};

//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
/**
	$(table).find('input:checkbox').addClass('ace')
	.wrap('<label />')
	.after('<span class="lbl align-top" />')


	$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
	.find('input.cbox[type=checkbox]').addClass('ace')
	.wrap('<label />').after('<span class="lbl align-top" />');
*/
};

//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
	/**
	var replacement = 
	{
		'ui-icon-pencil' : 'icon-pencil blue',
		'ui-icon-trash' : 'icon-trash red',
		'ui-icon-disk' : 'icon-ok green',
		'ui-icon-cancel' : 'icon-remove red'
	};
	$(table).find('.ui-pg-div span.ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
	*/
};

//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
	var replacement = {
		'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
		'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
		'ui-icon-seek-next' : 'icon-angle-right bigger-140',
		'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
};

function enableTooltips(table) {
	$('.navtable .ui-pg-button').tooltip({container:'body'});
	$(table).find('.ui-pg-div').tooltip({container:'body'});
};

