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

function loadTopicReplies(String) {
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
	xhr.open('get', '\ReplyServlet');
	xhr.setRequestHeader("topic", String);
	xhr.send();
}

function viewProfile() {
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
	xhr.open('post', '\ProfileServlet');
	xhr.send();
}

function loadFlaggedTopics() {
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
	xhr.open('post', '\RegulateServlet');
	xhr.send();
}

function deleteTopic(topic) {
	let xhr = new XMLHttpRequest();
	xhr.open('post', '\DeleteServlet');
	xhr.setRequestHeader("topic", topic);
	xhr.send();
	
	document.getElementById("view").innerHTML = "Topic Deleted";
}

function deleteReply(topicID, replyID) {
	console.log(topicID + " " + replyID);
	let xhr = new XMLHttpRequest();
	xhr.open('put', '\DeleteServlet');
	xhr.setRequestHeader("topicID", topicID);
	xhr.setRequestHeader("replyID", replyID);
	xhr.send();
	
	document.getElementById("view").innerHTML = "Reply Deleted";
}

function deleteFlags(topic) {
	let xhr = new XMLHttpRequest();
	xhr.open('get', '\DeleteServlet');
	xhr.setRequestHeader("topic", topic);
	xhr.send();
	
	document.getElementById("view").innerHTML = "Flags Deleted";
}

function loadAllUsers() {
	console.log("button was pressed!");
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
	xhr.open('put', '\ProfileServlet');
	xhr.send();
}
