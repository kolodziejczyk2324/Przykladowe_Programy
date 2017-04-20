function toggleMenu(){
	var el = document.getElementById("NAVLIST");
	if (el.style.display != 'block'){
		el.style.display = 'block';
	} else {
		el.style.display = 'none';
	}
}
	
var lwidth=window.innerWidth;
window.addEventListener('resize', function(event){
		drawLogo(); //rysowanie loga przy kazdym zmianie rozmiaru strony
		if(window.innerWidth>1200){
			var nav = document.getElementById('NAVLIST');
			nav.style.display="block";
		}
		else{if(lwidth>1200){
			var nav = document.getElementById('NAVLIST');
			nav.style.display="none";
		};
	}
	lwidth=window.innerWidth;
});


function alignMyDivs(){
	var els = [document.getElementById("PSEUDOKOD_PANEL"), 
	document.getElementById("JAVASCRIPT_PANEL")];
  
  //Niech przegladarka sama przeliczy potrzebne wysokości
  for (var i=0;i<els.length;i++){
	els[i].style.height = "auto";
  }
  //Wyznaczamy maksimum z wysokości
  var h = 0;
  for (i=0; i<els.length; i++){
	h = Math.max(h,els[i].clientHeight);
  }
  
  //Ustawiamy sami wysokości
  for (var i=0;i<els.length;i++){
	els[i].style.height = h + "px";
  }
}

function drawLogo(){
	var canvas = document.getElementById("MY_CANVAS");
	var ctx = canvas.getContext("2d");
	var center = canvas.height / 2;
	ctx.clearRect(0, 0, canvas.width, canvas.height); //czyszczenie pola z canvasem
	ctx.translate(center, center);
	var radius = center * 0.90;
	

	for(var i=0 ; i<4; i++){
		ctx.rotate(Math.PI/2);
		ctx.beginPath();
		ctx.arc(1,  -radius*0.80, radius*0.25, 0 , 2*Math.PI);
		ctx.fillStyle = "#DE4A2A";
		ctx.fill();
		
		ctx.strokeStyle = "#DE4A2A";
		ctx.beginPath();
		ctx.lineWidth = radius*0.10;
		var h = 10;
		var startX = 	radius*0.35; 
		var startY = 	radius*(-0.80);
		var endX = 	radius*0.80;
		var endY = 	radius*(-0.35);
		ctx.moveTo(startX, startY);
		//ctx.lineTo(endX, endY);
		ctx.quadraticCurveTo((endX-startX)/2+startX+h,(startY-endY)/2+endY-h,endX, endY);
		ctx.stroke();
		//rysowanie trojkata
		var b = 6; //bok trojkata
		ctx.beginPath();
		ctx.moveTo(endX+b/2,endY+b);
		ctx.lineTo(endX-2*b,endY);
		ctx.lineTo(endX+2*b,endY-b);
		ctx.fill();
	}
		
	ctx.translate(-center, -center);
}


window.addEventListener('load', function(){
	document.getElementById("showMenu").onclick = toggleMenu;
	alignMyDivs();
	drawLogo();
	window.onresize = alignMyDivs;
});