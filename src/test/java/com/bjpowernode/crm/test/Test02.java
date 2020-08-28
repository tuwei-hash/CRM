package com.bjpowernode.crm.test;

public class Test02 {

/*    function checkCode() {

		var ret=false;
		var code = $.trim($("#code").val());

		if (code == "") {
			$("#msg").html("编码为空");
			return;
		}
		$.ajax({
			url: 'settings/dictionary/type/checkTypeCode.do',
			data: {
				code: code
			},
			type: 'get',
			dataType: 'json',
			//async:false,
			success: function (data) {
				if (data.success) {
					$("#msg").html("编码已存在");
					ret=false;
				}else {
					$("#msg").html("");
					ret=true;
				}
			}
		});
		return ret;
    }*/


/*    $("#save").click(function () {

        var code=$.trim($("#code").val());
        var name=$.trim($("#name").val());
        var description=$.trim($("#description").val());

        if (checkCode()){

            $.ajax({
                    url:'settings/dictionary/type/saveType.do',
                    data:{
                code:code,
                        name:name,
                        description:description
            },
            type:'post',
                    dataType: 'json',
                    success:function (data) {
                window.location.href='redirect:settings/dictionary/type/toIndex.do';
            }
				});
        }*/

}
