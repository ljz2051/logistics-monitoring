/**
 * 表单验证，只能是数字和字母
 * 
 */
jQuery.validator.addMethod("numAndchar",function(value,element,params){
	var tem = /^[A-Za-z0-9]+$/;
	return tem.test(value);
},"必须是数字或者字母");