const replyObject = {
	
	init : function() {
		const btnInsert = document.getElementById('btn-save-reply'); // 댓글 등록
		
		
		if(btnInsert) {
			btnInsert.addEventListener('click', (e)=> {
				e.preventDefault();
				this.insertReply();
			})
		}
	
		
	},
	
	insertReply: function() {
		const id = document.getElementById('postId').value
		const reply = {
			content : document.getElementById('reply-content').value
		}
		
		fetch(`/reply/${id}`, {
			method : 'POST',
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body : JSON.stringify(reply)
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			window.location.href=`/post/${id}`
		}).catch(error=> {
			console.error(error);
		})
	},
	

}

const replyBtns = document.querySelectorAll('.btn-delete-reply');

for(let i=0; i<replyBtns.length; i++) {
	replyBtns[i].addEventListener('click', (e)=> {
		const id = e.target.dataset.replyId
		
		fetch(`/reply/${id}`, {
			method: 'DELETE'
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			location.reload();
		}).catch(error=> {
			console.log(error);
		})
	})
}

replyObject.init();