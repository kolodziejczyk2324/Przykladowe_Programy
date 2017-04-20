function sortuj()
{
	var elements = document.getElementById("elements").value;
	
	document.getElementById("sortResult").innerHTML = "";
	document.getElementById("stepByStep").innerHTML = "";
	
	stepCounter = 1;
	
	elements.trim();
	
	var arr = elements.split(" ");
	
	for( var i = 0; i < arr.length; i++ )
	{
		arr[ i ] = parseInt( arr[ i ] );
		if( isNaN( arr[ i ] ) )
		{
			document.getElementById("sortResult").innerHTML = "Proszę podać liczby!";
			return;
		}
	}
	
	var sorted = mergeSort( arr );
	
	document.getElementById("sortResult").innerHTML = sorted;
	document.getElementById("stepByStep").innerHTML += "<br>";
}

function mergeSort( arr )
{
    if( arr.length < 2 )
	{
		return arr;
	}
    
    var mid = Math.floor( arr.length / 2 );
    var subLeft = mergeSort( arr.slice( 0, mid ) );
    var subRight = mergeSort( arr.slice( mid ) );
	document.getElementById("stepByStep").innerHTML += "\nPobranie elementów " + subLeft + " i " + subRight + "<br>";
    
    return merge( subLeft, subRight );
}

function merge( a, b )
{
	var result = [];
	
    while( a.length > 0 && b.length > 0 )
	{
		document.getElementById("stepByStep").innerHTML += "\tPorównywanie " + a[ 0 ] + " i " + b[ 0 ] + "<br>";
		document.getElementById("stepByStep").innerHTML += "\tDodawanie " + ( (a[ 0 ] < b[ 0 ]) ? a[ 0 ] : b[ 0 ] ) + "<br>";
		result.push( a[0] < b[0] ? a.shift() : b.shift() );
	}
	
	document.getElementById("stepByStep").innerHTML += "\tPrzepisywanie reszty elementów: " + (a.length ? a : b) + "<br>";
	result = result.concat( a.length ? a : b );
	document.getElementById("stepByStep").innerHTML += "\t<u>Tablica wynikowa</u>: " + result + "<br>";
	return result;
}

 window.addEventListener('load', function(){
	document.getElementById("CALC").onclick=sortuj;
});
