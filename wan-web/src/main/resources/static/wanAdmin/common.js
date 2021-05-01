$(document).ready(function() {
	$(document).on("keypress", "form#searchForm", function(event) { 
		refreshTable();
		return event.keyCode != 13;
	});
	
    $(document).on('click', '.file-browser', function() {
        var $browser = $(this);
        var file = $browser.closest('.file-group').find('[type="file"]');
        file.on( 'click', function(e) {
            e.stopPropagation();
        });
        file.trigger('click');
    });
    
    $(document).on('change', '.file-group [type="file"]', function() {
        var $this    = $(this);
        var $input   = $(this)[0];
        var $len     = $input.files.length;
        var $size = $input.files[0].size;
        var formFile = new FormData();
        var uploadType = $this.attr('file-type');
        
        if ($len == 0) {
            return false;
        } else if ($size > 1024 * 1024) {
        	notifyError('文件大小不能超过1M');
        	return false;
        } else {
        	if (uploadType == 'image' || uploadType == 'avatar') {
        		var fileAccaccept = config('upload_image_ext');
                if (fileAccaccept) {
                	var fileType      = $input.files[0].type;
                    var type          = (fileType.substr(fileType.lastIndexOf("/") + 1)).toLowerCase();
                    
                    if (!type || fileAccaccept.indexOf(type) == -1) {
                    	notifyError('您上传图片的类型不符合(' + fileAccaccept + ')');
                        return false;
                    }
                }
        	}
            formFile.append("file", $input.files[0]);
        }
        
        var data = formFile;
        var uploadUrl;
        if (uploadType == 'image') {
        	uploadUrl = 'file/uploadImage';
        } else if (uploadType == 'avatar') {
        	uploadUrl = 'file/uploadAvatar';
        } else {
        	uploadUrl = 'file/upload';
        }
        postFile({
        	url: uploadUrl,
            data: data,
            success: function (rs) {
                if (rs.code == 0) {
                    $this.closest('.file-group').find('.file-value').val(rs.data).change();
                    notifySuccess('上传成功');
                } else {
                    notifyError(rs.message);
                }
            }
        });
    });
    
    $('[data-provide="summernote"]').each(function() {
        var options = {
            dialogsInBody: true,
            lang: 'zh-CN',
            dialogsFade: true
        };
        
        var config = {};
        $.each( $(this).data(), function(key, value){
            key = key.replace(/-([a-z])/g, function(x){return x[1].toUpperCase();});

            if ( key == 'provide' ) {
                return;
            }

            config[key] = value;
        });
        
        options = $.extend(options, config);
        
        if ( options.toolbar ) {
            switch( options.toolbar.toLowerCase() ) {
              case 'slim':
                options.toolbar = [
                  // [groupName, [list of button]]
                  ['style', ['bold', 'underline', 'clear']],
                  ['color', ['color']],
                  ['para', ['ul', 'ol']],
                  ['insert', ['link', 'picture']]
                ];
                break;
    
              case 'full':
                options.toolbar = [
                  // [groupName, [list of button]]
                  ['para_style', ['style']],
                  ['style', ['bold', 'italic', 'underline', 'clear']],
                  ['font', ['strikethrough', 'superscript', 'subscript']],
                  ['fontsize', ['fontname', 'fontsize', 'height']],
                  ['color', ['color']],
                  ['para', ['ul', 'ol', 'paragraph', 'hr']],
                  ['table', ['table']],
                  ['insert', ['link', 'picture', 'video']],
                  ['do', ['undo', 'redo']],
                  ['misc', ['fullscreen', 'codeview', 'help']]
                ];
                break;
            }
        }
        $(this).summernote(options);
    });
});

/*Ajax请求*/
function myAjax(options) {
	var index;
	$.ajax({
        type: options.type,
        dataType: 'json',
        url: options.url,
        data: options.data,
        async: options.async == false ? false : true,
        beforeSend: function() {
        	index = layer.load();
        },
        success: function(result) {
        	if(options.success) {
        		options.success(result);
        	} else {
        		if (result.code == 0) {
    				notifySuccess('执行成功');
    			} else {
    				notifyWarning(result.message);
    			}
        	}
        },
        error: function() {
        	notifyError('发生异常');
        },
        complete: function() {
        	layer.close(index);
        }
    });
}

function ajaxPost(options) {
	options.type = 'POST';
	myAjax(options);
}

function ajaxGet(options) {
	options.type = 'GET';
	myAjax(options);
}

function postFile(options) {
	var index;
	$.ajax({
        type: 'POST',
        dataType: 'json',
        url: options.url,
        data: options.data,
		//上传文件无需缓存
	    cache: false,
	    //用于对data参数进行序列化处理 这里必须false
	    processData: false,
	    //必须
	    contentType: false, 
        beforeSend: function() {
        	index = layer.load();
        },
        success: function(result) {
        	if(options.success) {
        		options.success(result);
        	} else {
        		notifySuccess('执行成功');
        	}
        },
        error: function() {
        	notifyError('发生异常');
        },
        complete: function() {
        	layer.close(index);
        }
    });
}

/*消息通知*/
function notifyMsg(message, type) {
	$.notify(
		{message: message},
		{
			type: type,
			placement: {
		    	from: 'top',
		    	align: 'center'
		    }
		}
	);
}

function notifyInfo(message) {
	window.top.notifyMsg(message, 'info');
}

function notifySuccess(message) {
	window.top.notifyMsg(message, 'success');
}

function notifyWarning(message) {
	window.top.notifyMsg(message, 'warning');
}

function notifyError(message) {
	window.top.notifyMsg(message, 'danger');
}

/*layer弹窗*/
function layerOpen(title, url) {
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.5,
        area: ['90%', '90%'],
        content: url,
        btn: ['确 定', '关闭'],
        yes: function(index, layero) {
        	layero.find('iframe')[0].contentWindow.doCommonSubmit();
        },
        btn2: function(index, layero) {
        	layer.close(index);
        }
    });
}

/*表单验证*/
function validateForm(rules, messages) {
	$('#submitForm').validate({
		ignore: '.note-editor *',
		rules: rules,
		messages: messages,
	    errorPlacement: function errorPlacement(error, element) {
	        var $parent = $(element).closest('div:not(.custom-control)');
	        if ($parent.find('.invalid-feedback').length) {
	            return;
	        }
	        $(element).addClass('is-invalid');
	        $parent.append(error.addClass('invalid-feedback'));
	    },
	    highlight: function(element) {
	        var $el = $(element);
	        if ($el.hasClass('js-tags-input')) {
	            $el.next('.tagsinput').addClass('is-invalid');  // tags插件所隐藏的输入框没法实时验证，比较尴尬
	        }
	    },
	    unhighlight: function(element) {
	        $(element).next('.tagsinput').removeClass('is-invalid');
	        $(element).removeClass('is-invalid');
	    }
	});
}

/*表单提交*/
function doCommonSubmit() {
	var $form = $('#submitForm');
	
	if (!$form.valid()) {
		return;
	}
	
	ajaxPost({ 
		url: $form.attr('action'),
		data: $form.serialize(),
		success: function(r) {
			if (r.code == 0) {
				notifySuccess('执行成功');
				parent.layer.closeAll();
				parent.refreshTable();
			} else {
				notifyWarning(r.message);
			}
		}
	});
}

/*表格*/
function bootstrapTable(options) {
	$('#bootstrap-table').bootstrapTable({
	    classes: 'table table-bordered table-hover table-striped',
	    url: options.url,
	    method: 'get',
	    dataType : 'json',
	    uniqueId: 'id',
	    idField: 'id',             // 每行的唯一标识字段
	    //clickToSelect: true,     // 是否启用点击选中行
	    //showColumns: true,         // 是否显示所有的列
	    //showRefresh: true,         // 是否显示刷新按钮
	    
	    //showToggle: true,        // 是否显示详细视图和列表视图的切换按钮(clickToSelect同时设置为true时点击会报错)
	  
	    pagination: true,                    // 是否显示分页
	    sortOrder: 'desc',                    // 排序方式
	    queryParams: function(params) {
            var temp = {
                page: (params.offset / params.limit) + 1,
                limit: params.limit,         // 每页数据量
                sort: params.sort,           // 排序的列名
                sortOrder: params.order      // 排序方式'asc' 'desc'
            };
            $.extend(temp, options.queryParams, $('#searchForm').serializeObject())
            return temp;
        },                                   // 传递参数
	    sidePagination: 'server',            // 分页方式：client客户端分页，server服务端分页
	    pageNumber: 1,                       // 初始化加载第一页，默认第一页
	    pageSize: 10,                        // 每页的记录行数
	    pageList: [10, 25, 50, 100],         // 可供选择的每页的行数
	    //search: true,                      // 是否显示表格搜索，此搜索是客户端搜索
	    
	    //showExport: true,        // 是否显示导出按钮, 导出功能需要导出插件支持(tableexport.min.js)
	    //exportDataType: "basic", // 导出数据类型, 'basic':当前页, 'all':所有数据, 'selected':选中的数据
	  
	    columns: options.columns,
	    onLoadSuccess: function(data){
	        $("[data-toggle='tooltip']").tooltip();
	    },
	    responseHandler:function(rs){
	        //在ajax获取到数据，渲染表格之前，修改数据源
	        return {total: rs.total, rows: rs.list};
	    }
	});
}

function refreshTable(options) {
	if (options) {
		$('table').bootstrapTable('refresh', options);
	} else {
		$('table').bootstrapTable('refresh');
	}
}

function getSelectIds(selRows) {
    var ids = '';
    $.each(selRows, function(i) {
    	ids += this.id;
    	if (i < selRows.length - 1) {
    		ids += ',';
    	}
    });
    return ids;
}

/*增删改查*/
function add(title) {
	layerOpen(title, prefix + '/add');
}

function del(id) {
	layer.confirm('确定删除该条记录？', function(index) {
		ajaxPost({ 
    		url: prefix + '/remove/' + id,
    		success: function(r) {
    			if (r.code == 0) {
    				notifySuccess('执行成功');
    				refreshTable();
    			} else {
    				notifyWarning(r.message);
    			}
    		}
    	});
	    layer.close(index);
	});
}

function update(id, title) {
	layerOpen(title, prefix + '/edit/' + id);
}

function updateStatus(id, status) {
    var newstatus = (status == 1) ? 0 : 1; // 发送参数值跟当前参数值相反
    ajaxPost({
    	url: prefix + '/updateStatus',
    	data: {id: id, status: newstatus},
    	success: function(r) {
            if (r.code == 0) {
            	notifySuccess('执行成功');
            	$('table').bootstrapTable('updateCellByUniqueId', {id: id, field: 'status', value: newstatus});
            } else {
            	notifyWarning(r.message);
            	$('table').bootstrapTable('updateCellByUniqueId', {id: id, field: 'status', value: status}); // 因开关点击后样式是变的，失败也重置下
            }
        }
    });
}

function detail(id, title) {
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.5,
        area: ['90%', '90%'],
        content: prefix + '/detail/' + id,
        btn: ['关闭'],
        yes: function(index, layero) {
        	layer.close(index);
        }
    });
}

function removeCache() {
	ajaxGet({ url: prefix + '/removeCache' });
}

/*表单数据转JSON对象*/
$.fn.serializeObject = function() {  
    var o = {};  
    var a = this.serializeArray();  
    $.each(a, function() {  
        if (o[this.name]) {  
            if (!o[this.name].push) {  
                o[this.name] = [ o[this.name] ];  
            }  
            o[this.name].push(this.value || '');  
        } else {  
            o[this.name] = this.value || '';  
        }  
    });  
    return o;  
}

/*获取系统配置参数值*/
function config(configKey) {
	var configValue = '';
	ajaxGet({
		url: 'system/config/get/' + configKey,
		async: false,
		success: function(rs) {
			if (rs.code == 0) {
				configValue = rs.data;
			}
		}
	});
	return configValue;
}

function getDict(typeName, key) {
	var dataValue = '';
	ajaxGet({
		url: 'system/dict/data/get',
		data: {typeName: typeName, key: key},
		async: false,
		success: function(rs) {
			if (rs.code == 0) {
				dataValue = rs.data;
			}
		}
	});
	return dataValue;
}

/*获取地址参数*/
function getQueryVariable(variable) {
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}