;!function(){
	
	if(window != top) {				    
	    top.location.href = window.location.href;
	}

	if (getQueryVariable('timeout') == '1') {
		layer.alert('登录超时，请重新登录');
	}
	if (getQueryVariable('expired') == '1') {
		layer.alert('账号在其他地方登录');
	}

	validateForm({
		'username': {
			required: true
		},
		'password': {
			required: true
		},
		'captcha': {
			required: true
		}
	});
	
}();

function doLogin() {
	if (!$('#submitForm').valid()) {
		return;
	}
	
	ajaxPost({
		url: 'doLogin',
        data: $('#submitForm').serialize(),
        success: function(r) {
            if (r.code == 0) {
                location.href = path + '/';
            } else {
            	$('#captcha').click();
            	$('input[name="captcha"]').val("");
            	notifyWarning(r.message);
            }
        }
	});
	
	return false;
}