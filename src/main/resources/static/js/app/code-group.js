$(document).ready(function(){

	// 목록조회
	$("#codeGroupListBtn").on("click", function() {
		console.log("codeGroupListBtn");
		$.ajax({
			type : "GET",
			url : "/codegroups",
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
	$("#codeGroupReadBtn").on("click", function() {
		console.log("codeGroupReadBtn");
		$.ajax({
			type : "GET",
			url : /codegroups/ + $("#groupCode").val(),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				alert(JSON.stringify(data));
				
				$("#groupName").val(data.groupName);
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
			groupCode : $("#groupCode").val(),
			groupName : $("#groupName").val()
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

	// 수정
	$("#codeGroupModifyBtn").on("click", function() {
		var groupCodeVal = $("#groupCode").val();
		
		var codeGroupObject = {
			groupCode : groupCodeVal,
			groupName : $("#groupName").val()
		};
			
		$.ajax({
			type : "PUT",
			url : "/codegroups/" + groupCodeVal,
			//url : "/codegroups",
			data : JSON.stringify(codeGroupObject),
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
    $("#codeGroupDeleteBtn").on("click", function() {
        $.ajax({
            type : "DELETE",
            url : /codegroups/ + $("#groupCode").val(),
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
	$("#codeGroupResetBtn").on("click", function() {
		$("#groupCode").val("");
		$("#groupName").val("");
	});
})