#ifndef MYSTACK_H
#define MYSTACK_H

#include <stdlib.h>
#include <string>
using namespace std;

class MyStack
{
    private:
        /*element w stosie, zawiera wartosc i swojego poprzednika*/
        struct element{
            element *prev; //poprzednik elementu
            string value; //wartosc w elemencie
        };
        element *top = NULL; //wskaznik na top'a, czyli element ktory zdejmiemy
    public:
        /*Funkcja dodajaca element na stos*/
        void push(string new_value); //nowym topem jest wlasnie dodany elelemnt
        /*Funkcja zdejmujaca elelemnt ze stosu*/
        string pop();
        /*Funkcja mowiaca czy stos jest pust*/
        bool jest_pusty();
	/*Funkcja zwracajaca wartość elementu bedacy na szczycie stosu*/
	string daj_top();
};

#endif // MYSTACK_H
