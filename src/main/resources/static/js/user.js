// 모듈화

const userObject = {
	
	init: function() {
		const btnSave = document.getElementById('btn-save'); // Save 영역
		const btnModify = document.getElementById('btn-modify'); // Modify 영역
		const btnDelete = document.getElementById('btn-delete'); // Delete 영역
		
		if(btnSave) {
			btnSave.addEventListener('click', (e)=> {
				e.preventDefault();
				this.insertUser();
			})
		}
		
		if(btnModify) { // Modify 영역
			btnModify.addEventListener('click', (e)=> {
				e.preventDefault();
				this.updateUser();
			})
		}
		
		if(btnDelete) { // Delete 영역
			btnDelete.addEventListener('click', (e)=> {
				e.preventDefault();
				this.deleteUser();
			})
		}
		
	},
	
	insertUser: function() {
		const user = {
			username : document.getElementById("username").value,
			password : document.getElementById("password").value,
			email : document.getElementById("email").value
		}
		
		fetch('/auth/join', {
			method : 'post',
			headers : {
				'Content-Type' : 'application/json; charset=UTF-8'
			},
			body : JSON.stringify(user)
		}) .then(response => response.json())
		.then(json => {
			
			if(json.status == 200) {
				alert(json.data);
				window.location.href = "/";
			} else {
				let msg = "";
				let errors = json.data;
				
				// 중복아이디 처리
				if(typeof errors == 'string')
					msg = errors;
				
				// 회원가입중 입력 유효성 검사
				if(errors.username != null) 
					msg += errors.username + '\n'
				if(errors.password != null)
					msg += errors.password + '\n'
				if(errors.email != null)
					msg += errors.email
				
				alert(msg);
			}
			

			
		}).catch(error => {
			console.error('회원가입 중 오류 발생', error);
		})
		
	},
	
	updateUser: function() {
		const user = {
			id: document.getElementById('id').value,
			username: document.getElementById('username').value,
			password: document.getElementById('password').value,
			email: document.getElementById('email').value
		}
		
	fetch('/auth/info', {
		method: 'POST',
		body: JSON.stringify(user),
		headers: {
			'Content-Type' : 'application/json; charset=utf-8'
		}
	}).then(response => response.json())
	.then(result => {
		alert(result.data);
		
		window.location.href="/auth/info";
	}).catch(error => {
		console.error("수정 요청 중 오류 발생", error)
	})
	
	},
	
	deleteUser: function() {
		const id = document.getElementById('id').value
				
		fetch(`/auth/user?id=${id}`, {
			method : 'delete'
		}).then(response => response.json())
		.then(result => {
			alert(result.data);
			
			window.location.href = "/";
		}).catch(error => {
			console.error("입력정보 오류", error);
		})
		
	}

	
	
}

userObject.init();