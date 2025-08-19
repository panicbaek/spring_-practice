const uploadForm = document.getElementById('uploadForm');

uploadForm.addEventListener('submit', (e) => {
	e.preventDefault();
	
	// input id 가져옴
	const fileInput = document.getElementById('fileInput');
	const file = fileInput.files[0];
	
	const fd = new FormData();
	fd.append('file', file);
	
	fetch('/api/images', {
		method:'post',
		body:fd
	}).then(response => response.json())
	.then(result => {
		alert('이미지등록 성공')
		
		location.reload();
	}).catch(error => {
		alert('이미지등록 실패')
		console.error(error);
	})
	
})