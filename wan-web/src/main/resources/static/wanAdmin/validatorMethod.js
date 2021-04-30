;!function(){
	
	// 检验角色名
	jQuery.validator.addMethod('rolename', function(value, element) {
	    return this.optional(element) || (value.indexOf('ROLE_') == 0);
	}, '须包含ROLE_前缀');

	// 检验summernote富文本框
	jQuery.validator.addMethod('noteRequired', function(value, element) {
		return this.optional(element) || (value != '<p><br></p>');
	}, '这是必填字段');
	
}();