#include "library.h"
#include <stdio.h>
#include <iostream>

using namespace std;

int main(){
	int j = 0;
	for(unsigned long long int i=0 ; i<5000 ; i++)
		if ( (j=is2expX(i)) != 0 ) cout << i << " " << j << endl;
}
