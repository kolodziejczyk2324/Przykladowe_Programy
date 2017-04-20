function callEr() {
    var n = document.getElementById("varN"), t = Math.abs(parseInt(n.value, 10));
    (isNaN(t)||2>t)&&(t=2), (t>1000000)&&(t=1000000), n.value=t, document.getElementById("window").innerHTML=eratosthenes(t)
}
 
function eratosthenes(n) {
    var s, i, j, max;
    var A = new Array(n);
    max = Math.floor(Math.sqrt(n));
    s = "";
 
    for (i = 0; i <= n; i++) {
        A[i] = 1;
    }
 
    for (i = 2; i <= max; i++) {
        if (A[i] == 1) {
            for (j = i+i; j <= n; j = j+i) {
                A[j] = 0;
            }
        }
    }
 
    for (i = 2; i <= n; i++) {
          if (A[i] == 1) {
            s += i + "  ";
        }
    }
    return s;
}

 window.addEventListener('load', function(){
	document.getElementById("CALC").onclick=callEr;
});
