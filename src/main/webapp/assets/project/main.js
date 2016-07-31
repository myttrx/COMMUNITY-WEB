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
		delay:'1000',
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

$(function () {
	initCustomDataApi();
	styleButton();
	modalHiddenCallback();
});