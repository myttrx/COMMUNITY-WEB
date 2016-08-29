 //global settings
//for jqgrid
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
    multiboxonly: false,
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
    multipleGroup: false,
    showQuery: false,
    closeOnEscape: true,
    closeAfterSearch: true,
    caption: "Advanced Search"
});

//for blockUI
$.blockUI.defaults.message = '<h2><img src="'+g_contextPath+'/assets/images/loading.gif" />&nbsp;&nbsp;Processing...</h2>';
$.blockUI.defaults.baseZ = 2000;
$.blockUI.defaults.fadeIn = 0;
$.blockUI.defaults.fadeOut = 0;
$(document).ajaxStart(function () {
    $.blockUI();
}).ajaxStop(function () {
    $.unblockUI();
});

function afterDeleteSubmit(response, postData) {
	var result = eval('(' + response.responseText+ ')');
	if(!result.success){
		notifyError(result.singleMessage);
	}else{
		notifySuccess(result.singleMessage);
	}
	return [true, ''];
};

function getSelStrByName($grid,fieldName){
	var selectedIDs = $grid.getGridParam("selarrrow");
	var result = "";
	for (var i = 0; i < selectedIDs.length; i++) {
		var rowDatas =  $grid.jqGrid('getRowData', selectedIDs[i]);
		result += rowDatas[fieldName]+",";
	}
	return result;
}

function beforeDeleteCallback(e) {
    var form = $(e[0]);
    if (form.data('styled')) return false;

    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    styleDeleteForm(form);

    form.data('styled', true);
};

function styleDeleteForm(form) {
	var buttons = form.next().find('.EditButton .fm-button');
	buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
	buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
	buttons.eq(1).prepend('<i class="icon-remove"></i>')
};

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

$.fn.form2json = function(){
	var serializedParams = this.serialize();
	serializedParams = serializedParams.replace(/\+/g," ");
	serializedParams =  decodeURIComponent(serializedParams); 
	//serializedParams = decodeURIComponent(serializedParams,true);
	var obj = paramString2obj(serializedParams);
	return JSON.stringify(obj);
};

$.fn.serializeObject = function() {
	var obj={};
    var serializedParams = this.serialize();
    obj = paramString2obj(serializedParams);
	return obj;
};

/*******************************************************************************
 * @serializedParams looks like "prop1=value1&prop2=value2". Nested property
 *                   like 'prop.subprop=value' is also supported
 ******************************************************************************/
function paramString2obj (serializedParams) {
	
	var obj={};
	function evalThem (str) {
		var attributeName = str.split("=")[0];
		var attributeValue = str.split("=")[1];
		if(!attributeValue){
			return ;
		}
		
		var array = attributeName.split(".");
		for (var i = 1; i < array.length; i++) {
			var tmpArray = Array();
			tmpArray.push("obj");
			for (var j = 0; j < i; j++) {
				tmpArray.push(array[j]);
			};
			var evalString = tmpArray.join(".");
			// alert(evalString);
			if(!eval(evalString)){
				eval(evalString+"={};");				
			}
		};
    
		if(typeof(obj[attributeName])!='undefined'){
			if(!(obj[attributeName] instanceof Array)){
				var oldValue = obj[attributeName];
				var newValue = attributeValue;
				obj[attributeName]=new Array();
				obj[attributeName].push(oldValue);
				obj[attributeName].push(newValue);
			}else{
				obj[attributeName].push(attributeValue);
			}
		}else{
			eval("obj."+attributeName+"='"+attributeValue.replace(/\r\n/ig,"\\n")+"';");
		}
		
	};

	var properties = serializedParams.split("&");
	for (var i = 0; i < properties.length; i++) {
		evalThem(properties[i]);
	};

	return obj;
}

$.fn.ajaxPostForm = function (url, success, fail) {
    if (!this.length) {
        log('ajaxPostForm: skipping submit process - no element selected');
        return this;
    }
    var $form = this;

    $form.ajaxSubmit({
        url: url,
        type: 'POST',
        dataType: 'json',
        success: function (data, status, xhr) {
            if (data.success) {
            	removeValidationSummary();
                success(data);
            } else if ($.isFunction(fail)) {
                fail(data);
            } else if (data.tag === "ValidationError") {
                processServerSideValidationError(data, $form);
            } else {
            	notifyError(data.singleMessage);
            }
        },
        error: function (xhr, status, error) {
            notifyError(error);
        }
    });
}

function ajaxGet(url, data, success, fail, type) {
    // shift arguments if data argument was omitted
    if ($.isFunction(data)) {
        type = type || fail || success;
        fail = success;
        success = data;
        data = undefined;
    }

    return $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        data: data,
        success: function (data, status, xhr) {
            if (data.success) {
                success(data);
            } else if (data.tag === "ValidationError") {
                processServerSideValidationError(data);
            } else if ($.isFunction(fail)) {
                fail(data);
            } else {
            	notifyError(data.singleMessage);
            }
        },
        error: function (xhr, status, error) {
        	notifyError(error);
        }
    });
};

function notifyError(message) {
	$.notify({
		// options
		icon: 'icon-warning-sign',
		position:'static',
		message: '<span class="bigger"><strong>'+message+'</strong></span>' 
	},{
		// settings
		type: 'danger',
		delay:'2000',
		placement: {
			from: "top",
			align: "center"
		},
		z_index: 2031,
	});
};

function notifySuccess(message) {
	$.notify({
		// options
		icon: 'icon-ok',
		position:'static',
		message: '<span class="bigger"><strong>'+message+'</strong></span>'
	},{
		// settings
		type: 'success',
		delay:'2000',
		placement: {
			from: "top",
			align: "right"
		},
		z_index: 2031,
		animate: {
			enter: 'animated fadeInRight',
			exit: 'animated fadeOutRight'
		},
	});
};

function processServerSideValidationError(response, form, summaryElement) {
    var $list,
        data = (response && response.errors) ? response : null;
    if (!data) return false;
    $list = summaryElement || getValidationSummary($(form));
    $list.html('');
    $.each(data.errors, function (index, item) {
    	 var fieldName = item.field;
    	 var $form = $(form), field = $form.find(':input[name="' + fieldName + '"]');
    	 var $val, errorList = "", fieldId = field.length ? field[0].id.replace('.', '\\.') : "";
    	 $form.find(".has-error").removeClass("has-error");
         if (fieldName) {
             $val = $form.find(".field-validation-valid,.field-validation-error")
                         .first('[data-valmsg-for="' + fieldName + '"]')
                         .removeClass("field-validation-valid")
                         .addClass("field-validation-error");
             $form.find("#" + fieldId).addClass("input-validation-error");
             //$form.find("#" + fieldId).parentsUntil("div.form-group").parent().addClass("has-error");
         }
         if (!item.defaultMessage.length) return;
         if ($val && $val.length) $val.text(item.defaultMessage);
         if (fieldId) {
             errorList += '<li><a title="click to view" href="javascript:setFocus(\'#' + form[0].id + '\',\'#' + fieldId + '\');" class="alert-warning">' + item.defaultMessage + '</a></li>';
         } else {
             errorList += '<li>' + val + '</li>';
         }
         $list.append(errorList);
    });
    if ($list.find("li:first").length) {
        $list.closest("div").show();
        scrollTo($list.parent().attr('id'));
    }
    return true;
};

function scrollTo(id) {
    var navbarHeight = $('#navbar').height();
    $('html,body').animate({
        scrollTop: $("#" + id).offset().top - navbarHeight
    }, 'fast');
};

function getValidationSummary($form) {
    var $el;
    if (typeof $form != 'undefined')
        $el = $form.find('.alert.alert-block.alert-warning.validation-summary-errors');

    if (typeof $el == 'undefined' || $el.length == 0) {
        var $fieldset = $form.find('fieldset:first');
        $el = $('<div id="validationSummary" class="alert alert-block alert-warning validation-summary-errors" data-valmsg-summary="true"><div><strong>Please fix the following errors.</strong></div><ol></ol></div>')
                 .hide()
                 .insertBefore($fieldset)
                 .find('ol');
    } else {
        $el = $el.hide().find('ol');
    }
    return $el;
};

function setFocus(form,ele) {
	$(form+' '+ele).focus();
};

function removeValidationSummary(){
	$('#validationSummary').remove();
}

function initCustomDataApi() {
    $("button[data-href]").click(function (e) {
        e.preventDefault();
        redirectTo($(this).data("href"));
    });

    $(".chosen-select").each(function (e) {
        var $this = $(this), placeholder = $this.prop('placeholder') || " Please select ";

        $this.chosen({
            placeholder: placeholder,
            allowClear: true
        }).focus(function () {
            $(this).chosen('focus');
        });
    });
    
    $("button[type='reset']").click(function (e) {
    	clearForm("#"+this.form.id);
    });
    //adapter the screen width
    $(".jqGrid").each(function (e) {
    	var $grid_selector = $(this);
    	$(window).resize(function() {
			$(window).unbind("onresize");
			$grid_selector.jqGrid().setGridWidth($(".page-content").width());
			$(window).bind("onresize", this);
		});
    });
    
	
};

function styleButton() {
    var $btnSearch = $('.btn-purple');
    $btnSearch.prepend('<i class="icon-search icon-on-right bigger-110"></i>');
    var $btnReset = $('.btn-light');
    $btnReset.prepend('<i class="icon-undo icon-on-right bigger-110"></i>');
    var $btnSave = $('.btn-info');
    $btnSave.prepend('<i class="icon-save icon-on-right bigger-110"></i>');
    var $btnRemove = $('.btn-grey');
    $btnRemove.prepend('<i class="icon-remove icon-on-right bigger-110"></i>');
};

//for modal
//when the modal is close , clear the validation message ,and clear the form
function modalHiddenCallback(){
	$(".modal").on('hidden.bs.modal', function () {
		$('#validationSummary').remove();
		var div = $(this);
		$(this).find("form").each(function(i){
			clearForm($(this));
		});
	});
}
function clearForm(formSelector) {
    var $form = $(formSelector);
    $form.find('input').val('');
    $form.find(".chosen-select").val('');
    $form.find(".chosen-select").trigger('chosen:updated');//update the option
    //$form.find("label.checkbox-inline input[type=hidden]:first-child").checkboxVal(false);
    $form.find('textarea').text('');
    $form.find('textarea').val('');
};

function redirectTo(href) {
    $.blockUI();
    location.href = href;
}


function defaultSpinner(control){
	var spinner = $("#"+control).spinner({
		  min: 0,
		  create: function(event, ui) {
				//add custom classes and icons
				$(this)
				.next().addClass('btn btn-success').html('<i class="icon-plus"></i>')
				.next().addClass('btn btn-danger').html('<i class="icon-minus"></i>')
				//larger buttons on touch devices
				if(ace.click_event == "tap") $(this).closest('.ui-spinner').addClass('ui-spinner-touch');
			}
	});
	$("#"+control).autotab('filter', 'number');
	return spinner;
}

$(function () {
	initCustomDataApi();
	styleButton();
	modalHiddenCallback();
});