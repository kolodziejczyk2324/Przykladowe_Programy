<script>
function send_Vote(vote,afterfunc)
{
	var element = document.getElementById("pageadr");
	
	var xhttp;
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
    } else {
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var cc=getCookie("Podstawowe_Algorytmy_PAGE");
	
	xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
				var parser = new DOMParser();
				var xmlDoc = parser.parseFromString(xhttp.responseText,"text/xml");
				var status=xmlDoc.getElementsByTagName("stat")[0].childNodes[0].nodeValue;
				var msg=xmlDoc.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
				afterfunc();
            }
     };
	
	xhttp.open("GET", "voteservice.php?c="+cc+"&v="+vote+"&p="+element.innerHTML, true);
	xhttp.send();
	
}
function takeres(func)
{
	var element2 = document.getElementById("pageadr");
			var xhttp;
			if (window.XMLHttpRequest) {
				xhttp = new XMLHttpRequest();
			} else {
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
				var parser = new DOMParser();
				var xmlDoc = parser.parseFromString(xhttp.responseText,"text/xml");
				var status=xmlDoc.getElementsByTagName("stat")[0].childNodes[0].nodeValue;
				var msg=xmlDoc.getElementsByTagName("dsc")[0].childNodes[0].nodeValue;
				func(status,msg);
            }
		};
	
			xhttp.open("GET", "voteservice.php?w=ref&p="+element2.innerHTML, true);
			xhttp.send();
}
function refreshvote(refbut,txt,interwal)
{
	var element = document.getElementById(refbut);
	if(element.style.color=="white")
	{
		clearInterval(interwal);
		element.style.color="grey";
	}
	else
	{
		element.style.color="white";
		return interwal = setInterval(function(){	
			takeres(function(stat,msg){
				document.getElementById(txt).innerHTML=msg;
			});
			}
		,1000);
	}
}
function upvote(voteup,votedown,wyn)
{
	send_Vote("u",function(){
		var element = document.getElementById(voteup);
	
		var old_element = element;
		var new_element = old_element.cloneNode(true);
		old_element.parentNode.replaceChild(new_element, old_element)
	
		var element2 = document.getElementById(votedown);
		element2.style.color="grey";
		var old_element = element2;
		var new_element = old_element.cloneNode(true);
		old_element.parentNode.replaceChild(new_element, old_element)
		
		takeres(function(stat,msg){
				document.getElementById(wyn).innerHTML=msg;
			});
	});
}
function downvote(voteup,votedown,wyn)
{
	send_Vote("d",function(){
		var element = document.getElementById(votedown);
		
		var old_element = element;
		var new_element = old_element.cloneNode(true);
		old_element.parentNode.replaceChild(new_element, old_element)
		
		var element2 = document.getElementById(voteup);
		element2.style.color="grey";
		var old_element = element2;
		var new_element = old_element.cloneNode(true);
		old_element.parentNode.replaceChild(new_element, old_element)
		
		takeres(function(stat,msg){
				document.getElementById(wyn).innerHTML=msg;
			});
});
}
window.addEventListener("load", function(event){
	var timeout;
	var ref = document.getElementById("refresh");
	ref.addEventListener("click", function(){timeout=refreshvote("refresh","wyn",timeout)});
	});
	
	var down = document.getElementById("downvote");
	down.addEventListener("click", function(){downvote("upvote","downvote","wyn")});
	var up =document.getElementById("upvote");
	
	up.addEventListener("click", function(){upvote("upvote","downvote","wyn");});
	
	
</script>