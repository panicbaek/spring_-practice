$(document).ready(function() {
  $('#content').summernote({
	height: 400
  });
});

const postObject = {
	
	init : function() {
		const btnInsert = document.getElementById('btn-insert'); // 게시글등록
		const btnUpdate = document.getElementById('btn-update'); // 게시글수정
		const btnDelete = document.getElementById('btn-delete'); // 게시글삭제
		
		if(btnInsert) {
			btnInsert.addEventListener('click', (e)=> {
				e.preventDefault();
				this.insertPost();
			})
		}
		
		if(btnUpdate) {
			btnUpdate.addEventListener('click', (e)=> {
				e.preventDefault();
				
				if(confirm("수정 하시겠습니까?"))
					this.updatePost();
			})
		}
		
		if(btnDelete) {
			btnDelete.addEventListener('click', (e)=> {
				e.preventDefault();
				
				if(confirm("정말 삭제하시겠습니까?"))
					this.deletePost();
			})
		}
		
	},
	
	
	insertPost: function() {
		const post = {
			title : document.getElementById('title').value,
			content : document.getElementById('content').value
		}
		
		fetch('/post', {
			method : 'POST',
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body: JSON.stringify(post)
		}).then(response => response.json())
		.then(result => {
			
			if(result.status == 200) {
				alert(result.data);
				
				window.location.href="/";
			} else {
				let msg = "";

				for(let key in result.data) {
					msg += result.data[key] + '\n';
				}
				
				alert(msg);
			}

		}).catch(error => {
			console.log(error);
		})
	},
	
	updatePost: function() {
		const post = {
			id: document.getElementById('id').value,
			title: document.getElementById('title').value,
			content: document.getElementById('content').value
		}
		
		fetch('/post', {
			method: "PUT",
			headers: {
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body: JSON.stringify(post)
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			window.location.href = "/"
		}).catch(error => {
			console.error("수정 요청 중 오류 발생");
		})
		
	},
	
	deletePost: function() {
		const id = document.querySelector('.id').innerText;
		
		fetch(`/post/${id}`, {
			method: "DELETE",
			headers: {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			window.location.href="/"
		}).catch(error => {
			console.error("삭제 요청 중 오류 발생");
		})
	}
	

	
}
postObject.init();