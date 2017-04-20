function nwd(a,h){
	var x=0, y=1, lastx=1, lasty=0, q, tempA, tempB, temp, temp1, temp2, temp3;
	if(a<h){
		temp = a,
		a = h,
		h = temp;
	}
	var a0=a, h0=h;
	for(var r="("+a+", "+h+")"; 0!=h;)
		tempA = a,
		tempB = h,
		q=Math.floor(a/h),
		temp1 = a%h,
		a=h,
		h=temp1,
		
		temp2=x,
		x=lastx-q*x,
		lastx=temp2,
		
		temp3=y,
		y=lasty-q*y,
		lasty=temp3,
		r+="<br/>"+tempA+", "+tempB+" ➠ "+tempA+" = "+tempB+" * "+Math.floor(tempA/tempB)+" + "+tempA%tempB+" &rarr; ["+lastx+", "+lasty+"]";
	return r+"<br/>"+a0+" * "+lastx+" + "+h0+" * "+lasty+" = NWD("+a0+", "+h0+") = "+(a0*lastx+h0*lasty)
}


function callNWD(){
	var e=document.getElementById("varX"), n=document.getElementById("varY"), t=Math.abs(parseInt(e.value,10)), a=Math.abs(parseInt(n.value,10));
	(isNaN(t)||1>t)&&(t=1),(isNaN(a)||1>a)&&(a=1),e.value=t,n.value=a,document.getElementById("trace").innerHTML=nwd(t,a)
}

/*
 * Wstawienie akcji do przyciskow
 */
 
  window.addEventListener('load', function(){
	document.getElementById("CALC").onclick=callNWD;
});