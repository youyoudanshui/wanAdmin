getUnreadNotices();

var websocket = null;

if('WebSocket' in window) {
    websocket = new WebSocket('ws://' + document.domain + path + '/webSocket');
}else {
	setInterval('getUnreadNotices();', 1000 * 60 * 60);
}

websocket.onopen = function(event) {
    console.log('建立连接');
}

websocket.onclose = function(event) {
    console.log('连接关闭');
}

websocket.onmessage = function(event) {
    console.log('收到消息:' + event.data)

    if (event.data == 'getUnreadNotices') {
    	getUnreadNotices();
    }
}

websocket.onerror = function() {
	console.log('websocket通信发生错误！');
	
	setInterval('getUnreadNotices();', 1000 * 60 * 60);
}

window.onbeforeunload = function() {
    websocket.close();
}

function getUnreadNotices() {
	ajaxGet({ 
		url: 'system/user/notice/unread',
		success: function(r) {
			if (r.code == 0) {
				var data = r.data;
				if (data.count > 0) {
					$('#noticeCount').show();
					$('#noticeCount').html(data.count);
					$('#noticeMsg').html('你有 ' + data.count + ' 条未读消息');
					
					var notices = '';
					for (var i = 0; i < data.list.length; i ++) {
						notices += '<a href="javascript:void(0)" onclick="openNoticeTab()" class="dropdown-item" title="' + data.list[i].title + '">' + data.list[i].title + '</a>';
					}
					$('#noticeList').html(notices);
				} else {
					$('#noticeCount').hide();
					$('#noticeCount').html('');
					$('#noticeMsg').html('');
					$('#noticeList').html('<div class="noticeBlank">暂没有新消息</div>');
				}
			} else {
				notifyWarning(r.message);
			}
		}
	});
}

function openNoticeTab() {
	$(document).data('multitabs').create({
        iframe : true,
        title : '系统通知',
        url : 'system/user/notice'
    }, true);
	$('li.dropdown-notice').removeClass('show');
	$('li.dropdown-notice .dropdown-menu').removeClass('show');
}