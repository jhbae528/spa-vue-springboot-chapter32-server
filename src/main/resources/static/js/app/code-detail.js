$(document).ready(function(){
	// 목록조회
	$("#codeDetailListBtn").on("click", function() {
		console.log("codeDetailListBtn");
		$.ajax({
			type : "GET",
			url : "/codedetails",
			contentType : "application/json; charsetUTF-8",
			success : function(data) {
				console.log(data);
				
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
	$("#codeDetailReadBtn").on("click", function() {
		console.log("codeDetailReadBtn");
		$.ajax({
			type : "GET",
			url : "/codedetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				alert(JSON.stringify(data));
				
				$("#codeGroupCode").val(data.groupCode);
				$("#codeValue").val(data.codeValue);
				$("#codeName").val(data.codeName);
			},
			error: function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
				+ "message:" + xhr.responseText + "\n"
				+ "error:" + error);
			}
		});
	});
	// 등록
	$("#codeDetailRegisterBtn").on("click", function() {
		var codeGroupObject = {
			groupCode : $("#codeGroupCode").val(),
			codeValue : $("#codeValue").val(),
			codeName : $("#codeName").val()
		};

		alert(JSON.stringify(codeGroupObject));
		
		$.ajax({
			type : "POST",
			url : "/codedetails",
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
	// 수정
	$("#codeDetailModifyBtn").on("click", function() {
		var groupCodeVal = $("#codeGroupCode").val();
		var codeValueVal = $("#codeValue").val();
		
		var codeDetailObject = {
		    groupCode : groupCodeVal,
			codeValue : codeValueVal,
			codeName : $("#codeName").val()
		};
			
		$.ajax({
			type : "PUT",
			url : "/codedetails/" + groupCodeVal + "/" + codeValueVal,
			data : JSON.stringify(codeDetailObject),
			contentType : "application/json; charset=UTF-8",
			success : function() {
				alert("Modified");
			},
			error: function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
				+ "message:" + xhr.responseText + "\n"
				+ "error:" + error);
			}
		});
	});
    // 삭제
	$("#codeDetailDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/codedetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
			contentType : "application/json; charset=UTF-8",
			success : function() {
				alert("Deleted");
			},
			error: function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
				+ "message:" + xhr.responseText + "\n"
				+ "error:" + error);
			}
		});
	});
	// 입력값 리셋
	$("#codeDetailResetBtn").on("click", function() {
		$("#codeGroupCode").val("");
		$("#codeValue").val("");
		$("#codeName").val("");
	});
	
	// 화면 초기값 로딩
	$.getJSON("/codes/codeGroup", function(list) {
		$(list).each(function(){
			var str = "<option value='" + this.value + "'>" + this.label + "</option>";
			$("#codeGroupCode").append(str);
		});
	});
});