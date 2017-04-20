#include "MyStack.h"
#include <string>
using namespace std;

/*Funkcja dodajaca element na stos*/
void MyStack::push(string new_value){
    element *new_top = new element; //tworzenie struktury nowego elementu
    new_top->value = new_value; //ustawienie wartosci nowego elementu na stosie
    if(top == NULL) new_top->prev = NULL; //jesli nie bylo wczesniej elementow to poprzednikiem nowego elementu jest NULL
    else new_top->prev = top; //jezeli na stosie znajduja sie elementy to ustaw poprzednika nowego elementu na akutalny top
    top = new_top; //nowym topem jest wlasnie dodany elelemnt
}

/*Funkcja zdejmujaca elelemnt ze stosu*/
string MyStack::pop(){
    if(top == NULL) return NULL; //jesli stos jest pusty
    string ret_val = top->value; //wartosc jaka bedziemy zwracac
    struct element *temp = top->prev; //wskaznik na poprzednika usuwanego elementu
    free(top); //usuwanie aktualnego topa
    top = temp; //nowym topem jest poprzednik wczesniejszego topa
    return ret_val; //zwroc odpowiednia wartosc
}

/*Funkcja zwracajaca 'true' jesli stos jest pusty, 'false' jesli cos sie w nim znajduje*/
bool MyStack::jest_pusty(){
    if(top == NULL) return true; //sprawdzenie czy na topie cos sie znajduje
    return false; //jesli sie cos znajduje zwroc false
}

string MyStack::daj_top(){
	if(top == NULL) return NULL;
	else return top->value;
}
