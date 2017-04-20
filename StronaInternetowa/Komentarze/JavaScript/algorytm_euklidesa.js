			function nwd(a,b){
				  var S;	
				  var c;
				  S = "";
				  while (b != 0){
					S = S + "(" + a + "," + b + ") --> \n"
					c = a;
					a = b;
					b = c % b;
				  }
				  S = S + a;
				  return S;
			}
			
			function show(){
				var x=document.getElementById("varX").value;
				var y=document.getElementById("varY").value;
				if (x<1 || x>Number.MAX_SAFE_INTEGER || y<1 || y>Number.MAX_SAFE_INTEGER || isNaN(x) || isNaN(y)){
					document.getElementById("varX").value=1;
					document.getElementById("varY").value=1;
					document.getElementById("trace").innerHTML="Proszę podać  wartość z zakresu <strong>NUMBER</strong>";
					
				}
				else{
					document.getElementById("trace").innerHTML=nwd(x,y);
				}
				
			}

window.addEventListener('load', function(){
	document.getElementById("CALC").onclick=show;
});