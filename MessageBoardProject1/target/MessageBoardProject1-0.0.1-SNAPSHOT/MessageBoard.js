function loadAllTopics() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				document.getElementById("view").innerHTML = xhr.responseText;
			} else {
				console.log("error");
			}
		}
	}
	xhr.open('post', '\PostServlet');
	xhr.send();
}

function loadDashboard() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				document.getElementById("view").innerHTML = xhr.responseText;
			} else {
				console.log("error");
			}
		}
	}
	xhr.open('get', '\PostServlet');
	xhr.send();
}

function loadReplies() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				document.getElementById("view").innerHTML = xhr.responseText;
			} else {
				console.log("error");
			}
		}
	}
	xhr.open('post', '\ReplyServlet');
	xhr.send();
}