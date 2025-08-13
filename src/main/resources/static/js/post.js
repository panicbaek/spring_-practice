$(document).ready(function() {
  $('#content').summernote({
	height: 400
  });
});

const postObject = {
	
	init : function() {
		const btnInsert = document.getElementById('btn-insert'); // 게시글등록
		
		if(btnInsert) {
			btnInsert.addEventListener('click', (e)=> {
				e.preventDefault();
				this.insertPost();
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
			alert(result.data);
			
			window.location.href="/";
		}).catch(error => {
			console.log(error);
		})
	}

	
}
postObject.init();