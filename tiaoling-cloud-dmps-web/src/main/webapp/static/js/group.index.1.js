$(function() {

	// remove
	$('.remove').on('click', function(){
		var groupKey = $(this).attr('groupKey');

		ComConfirm.show("确认删除分组?", function(){
			$.ajax({
				type : 'POST',
				url : base_url + '/conf/group/delete',
				data : {"groupKey":groupKey},
				dataType : "json",
				success : function(data){
					if (data.code == 200) {
						ComAlert.show(1, '删除成功', function () {
							window.location.reload();
						});
					} else {
						if (data.msg) {
							ComAlert.show(2, data.msg);
						} else {
							ComAlert.show(2, '删除失败');
						}
					}
				},
			});
		});
	});

	// jquery.validate 自定义校验 “英文字母开头，只含有英文字母、数字和下划线”
	jQuery.validator.addMethod("myValid01", function(value, element) {
		var length = value.length;
		var valid = /^[a-z][a-zA-Z0-9-]*$/;
		return this.optional(element) || valid.test(value);
	}, "限制以小写字母开头，由小写字母、数字和中划线组成");

	$('.add').on('click', function(){
		$('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var addModalValidate = $("#addModal .form").validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : true,
		rules : {
			groupKey : {
				required : true,
				rangelength:[1,100],
				myValid01 : true
			},
			groupName : {
				required : true,
				rangelength:[1, 100]
			}
		},
		messages : {
			groupKey : {
				required :"请输入“GroupKey”",
				rangelength:"GroupKey长度1~100",
				myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
			},
			groupName : {
				required :"请输入“分组名”",
				rangelength:"长度限制为1~100"
			},
			order : {
				required :"请输入“排序”",
				digits: "请输入整数",
				range: "取值范围为1~1000"
			}
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
			label.remove();
		},
		errorPlacement : function(error, element) {
			element.parent('div').append(error);
		},
		submitHandler : function(form) {
			$.post(base_url + "/conf/group/add",  $("#addModal .form").serialize(), function(data, status) {
				if (data.code == "200") {
					$('#addModal').modal('hide');
					setTimeout(function () {
						ComAlert.show(1, "新增成功", function(){
							window.location.reload();
						});
					}, 315);
				} else {
					if (data.msg) {
						ComAlert.show(2, data.msg);
					} else {
						ComAlert.show(2, "新增失败");
					}
				}
			});
		}
	});
	$("#addModal").on('hide.bs.modal', function () {
		$("#addModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#addModal .form .form-group").removeClass("has-error");
	});

	$('.update').on('click', function(){
		$("#updateModal .form input[name='groupKey']").val($(this).attr("groupKey"));
		$("#updateModal .form input[name='groupName']").val($(this).attr("groupName"));

		$('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var updateModalValidate = $("#updateModal .form").validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : true,
		rules : {
			groupKey : {
				required : true,
				rangelength:[1,100],
				myValid01 : true
			},
			groupName : {
				required : true,
				rangelength:[1, 100]
			}
		},
		messages : {
			groupKey : {
				required :"请输入“GroupKey”",
				rangelength:"GroupKey长度限制为1~100",
				myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
			},
			groupName : {
				required :"请输入“分组名”",
				rangelength:"长度限制为1~100"
			}
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
			label.remove();
		},
		errorPlacement : function(error, element) {
			element.parent('div').append(error);
		},
		submitHandler : function(form) {
			$.post(base_url + "/conf/group/update",  $("#updateModal .form").serialize(), function(data, status) {
				if (data.code == "200") {
					$('#addModal').modal('hide');
					setTimeout(function () {
						ComAlert.show(1, "更新成功", function(){
							window.location.reload();
						});
					}, 315);
				} else {
					if (data.msg) {
						ComAlert.show(2, data.msg);
					} else {
						ComAlert.show(2, "更新失败");
					}
				}
			});
		}
	});
	$("#updateModal").on('hide.bs.modal', function () {
		$("#updateModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#updateModal .form .form-group").removeClass("has-error");
	});

	
});
