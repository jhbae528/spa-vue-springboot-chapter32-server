$(document).ready(function(){
	// 목록조회
	$("#codeGroupListBtn").on("click", function() {
		var codeGroupObject = {
			groupCode : $("groupCode").val(),
			groupName : $("groupName").val(),
		};
		
		alert(JSON.stringify(codeGroupObject));
		
		$.ajax({
			type : "POST",
			url : "/codegroups",
			data : JSON.stringify(codeGroupObject),
			contentType : "application/json; charset=UTF-8",
			success : function() {
				alert("Created");
			},
			error: function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
				+ "message:" + xhr.responseText + "\n"
				+ "error:" + error);
			}
		});
	});
	// 상세조회
	$("#codeGroupReadBtn").on("click", function() {
		
	});
	// 등록
	$("#codeGroupRegisterBtn").on("click", function() {
		
	});
	// 삭제
	$("#codeGroupDeleteBtn").on("click", function() {
		
	});
	// 수정
	$("#codeGroupModifyBtn").on("click", function() {
		
	});
	// 입력값 리셋
	$("#codeGroupResetBtn").on("click", function() {
		
	});
})