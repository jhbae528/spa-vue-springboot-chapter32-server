$(document).ready(function(){
	// 목록조회
	$("#codeGroupListBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codegroups",
			contentType : "application/json; charseUTF-8",
			success : function(data) {
				consol.log(data);
				
				alert(JSON.stringify(data));
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
		$.ajax({
			type : "GET",
			urk : /codegroups/ + $("groupCode").val(),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				consol.log(data);
				
				alert(JSON.stringify(data));
				
				$("groupName").val(data.grpupName);
			},
			error: function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
				+ "message:" + xhr.responseText + "\n"
				+ "error:" + error);
			}
		});
	});
	// 등록
	$("#codeGroupRegisterBtn").on("click", function() {
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