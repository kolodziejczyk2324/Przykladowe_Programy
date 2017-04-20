%{
#include <cstdio>
#include <iostream>
#include <math.h>
#include <vector>
#include <map>
#include <string>
#include <sstream>
#include <climits>
#include <math.h>
#include "Biblioteki/library.h"
#include "Biblioteki/MyStack.h"

#define STORE(X) code.push_back(concatStringInt("STORE ", X));
#define LOAD(X) code.push_back(concatStringInt("LOAD ", X));
#define ADD(X) code.push_back(concatStringInt("ADD ", X));
#define SUB(X) code.push_back(concatStringInt("SUB ", X));
#define PUT(X) code.push_back(concatStringInt("PUT ", X));
#define GET(X) code.push_back(concatStringInt("GET ", X));
#define ZERO(X) code.push_back(concatStringInt("ZERO ", X));
#define COPY(X) code.push_back(concatStringInt("COPY ", X));
#define JZERO(X, Y) code.push_back(concatStringInt("JZERO ", X)+string(" ")+Y);
#define JUMP(X) code.push_back("JUMP " + X);
#define JODD(X, Y) code.push_back(concatStringInt("JODD ", X)+string(" ")+Y);
#define SHL(X) code.push_back(concatStringInt("SHL ", X));
#define SHR(X) code.push_back(concatStringInt("SHR ", X));
#define INC(X) code.push_back(concatStringInt("INC ", X));
#define DEC(X) code.push_back(concatStringInt("DEC ", X));

#define PUSH_ETYK(X) etykiety.push(concatStringInt("E", X));
#define POP_AND_WRITE_ETYK code.push_back(etykiety.pop());

#define EQ_1 PUSH_ETYK(ety+1) PUSH_ETYK(ety+2) PUSH_ETYK(ety+1)\
	PUSH_ETYK(ety+2) PUSH_ETYK(ety) PUSH_ETYK(ety+1)\
	PUSH_ETYK(ety) ety+=3;
#define EQ_2 JZERO(1, etykiety.pop())\
	create_number(code, 0, 1);\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK
#define EQ_3 JZERO(1, etykiety.pop())\
	create_number(code, 0, 1);\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK\
	create_number(code, 1, 1);\
	POP_AND_WRITE_ETYK

#define NEQ_1 PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+1)\
	PUSH_ETYK(ety) ety+=2;
#define NEQ_2 JZERO(1, etykiety.pop())\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK
#define NEQ_3 POP_AND_WRITE_ETYK

#define MULT_MEM_1 PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety) ety+=2;\
	JZERO(1, etykiety.pop())
#define MULT_MEM_2 JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK
#define MULT_MEM_3 POP_AND_WRITE_ETYK\
	ZERO(1)\
	ZERO(0)\
	STORE(3)

#define XOET PUSH_ETYK(ety+1) PUSH_ETYK(ety)\
	PUSH_ETYK(ety+1) PUSH_ETYK(ety) ety+=2;\
	JZERO(1,etykiety.pop())\
	create_number(code,0,1);\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK\
	create_number(code,1,1);\
	POP_AND_WRITE_ETYK



#define FOR_1(X) check_iterator(X);\
	if( jestIterator() ){\
		create_number(code, iteratory[stack_iteratory.daj_top()].store, 0);\
		STORE(4)\
	}\
	struct iter_data v1 = { i++ };\
	iteratory[X] = v1;\
	stack_iteratory.push(X);\

#define FOR_2 reset_flags();

#define FOR_3 reset_flags();\
	struct iter_data v = { i++ };\
	iteratory[my_toUpperCase(stack_iteratory.daj_top())] = v;

//to dla inc
#define FOR_4 PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety)\
	PUSH_ETYK(ety+1) ety+=2;\
	ZERO(0)\
	STORE(4)\
	INC(1)\
	SUB(1)\
	create_number(code, iteratory[my_toUpperCase(stack_iteratory.daj_top())].store, 0);\
	STORE(1)\
	POP_AND_WRITE_ETYK\
	create_number(code, iteratory[my_toUpperCase(stack_iteratory.daj_top())].store, 0);

//to dla dec
/*roznica we wstawianiu etykiet na stos pomiedzy dec a inc to ta ze dodajemy dodatkowa etykiete
  (3 od lewej PUSH_ETYK(ety+2) ). Zawiera ona sprawdzenie czy nie chcemy dekrementowac
  zera. Jezeli tak jest to na pewno petla sie konczy. Jezeli bysmy tego nie zrobili petla by
  sie zapetlila.*/
#define DOWNTO_FOR_4 PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety)\
	PUSH_ETYK(ety+1) ety+=2;\
	create_number(code, 2, 0);\
	STORE(4)\
	LOAD(1)\
	DEC(0)\
	LOAD(0)\
	INC(1)\
	SUB(1)\
	create_number(code, iteratory[my_toUpperCase(stack_iteratory.daj_top())].store, 0);\
	STORE(1)\
	POP_AND_WRITE_ETYK\
	create_number(code, iteratory[my_toUpperCase(stack_iteratory.daj_top())].store, 0);\

//to dla kazdego
#define FOR_5 LOAD(1)\
	JZERO(1, etykiety.pop())\
	DEC(1)\
	STORE(1)

#define FOR_6 INC(4)\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK\
	iteratory.erase(my_toUpperCase(stack_iteratory.daj_top()));\
	iteratory.erase(stack_iteratory.daj_top());\
	i-=2;\
	stack_iteratory.pop();\
	if( jestIterator() ){\
		create_number(code, iteratory[stack_iteratory.daj_top()].store, 0);\
		LOAD(4)\
	}

#define DOWNTO_FOR_6 DEC(4)\
	JUMP(etykiety.pop())\
	POP_AND_WRITE_ETYK\
	iteratory.erase(my_toUpperCase(stack_iteratory.daj_top()));\
	iteratory.erase(stack_iteratory.daj_top());\
	i-=2;\
	stack_iteratory.pop();\
	if( jestIterator() ){\
		create_number(code, iteratory[stack_iteratory.daj_top()].store, 0);\
		LOAD(4)\
	}

using namespace std;

void yyerror(const char *s);
void check_identifier(string name, int isArray);
void check_double_declaration(string name);
void load_memory_to_register(int memory, int reg);
void save_register_to_memory(int reg, int memory);
void save_number_to_memory(unsigned long long int number, int memory);

void dodawanie_number_number(unsigned long long int a, unsigned long long int b);
void dodawanie_identifier_number(unsigned long long int num);
void dodawanie_number_identifier(unsigned long long int num);
void dodawanie_identifier_identifier();

void odejmowanie_number_number(unsigned long long int a, unsigned long long int b);
void odejmowanie_identifier_number(unsigned long long int num);
void odejmowanie_number_identifier(unsigned long long int num);
void odejmowanie_identifier_identifier(unsigned long long int a, unsigned long long int b);
void rowne_number_number(unsigned long long int a, unsigned long long int b);
void rowne_identifier_number(unsigned long long int num);
void rowne_identifier_identifier();


void wykonaj_mnozenie();
void mnozenie_przygotuj_rejesty_i_pamiec_identifier_number(unsigned long long int numb);
void mnozenie_przygotuj_rejesty_i_pamiec_identifier_identifier();
void mnozenie_number_number(unsigned long long int num1, unsigned long long int num2);
void mnozenie_identifier_number(unsigned long long int numb);
void mnozenie_number_identifier(unsigned long long int numb);
void mnozenie_identifier_identifier();

void dzielenie_number_number(unsigned long long int a, unsigned long long int b);
void dzielenie_identifier_number(unsigned long long int num, bool isModulo);
void dzielenie_number_identifier(unsigned long long int num);
void dzielenie_identifier_identifier();

void modulo_identifier_identifier();
void modulo_number_number(unsigned long long int a, unsigned long long int b);
void modulo_identifier_number(unsigned long long int num);
void modulo_number_identifier(unsigned long long int num);

bool jestIterator();
void check_iterator(string name);
void check_przypisanie_iterator();
void reset_flags();

void save_adress_to_register(int store, int reg);
void save_value_to_register(int store, int reg);

extern "C" int yylex();
extern int yylineno;

struct var_data{
	int store;
	int isArray;
};

struct iter_data{
	int store;
};

vector<string> code;
map<string, struct var_data> zmienne;
map<string, struct iter_data> iteratory;
MyStack etykiety;
MyStack stack_iteratory;

int i = 4;
int ety = 1;
int iter[2];

//CONST
const unsigned long long int add_check_const = ULLONG_MAX/2;
const unsigned long long int mult_check_const = sqrt(ULLONG_MAX)-1;

// FLAGI
int mem_to_save_id = 1;
int isIterator = 0;

%}
%union {
	unsigned long long int	ival;
        char*		sval;
}

%token <sval> IDENTIFIER
%token VAR _BEGIN END READ WRITE SKIP
%token IF THEN ELSE ENDIF
%token WHILE DO ENDWHILE FOR FROM TO ENDFOR DOWNTO
%token GT LT EQ NEQ GOET LOET
%token LB RB
%token SREDNIK 
%token PRZYPISANIE 
%token PLUS MINUS MULT DIV MOD
%token <ival>NUMBER

%start program
%%

program:	VAR vdeclarations _BEGIN commands END
	;

vdeclarations:	
	|	vdeclarations IDENTIFIER	{	check_double_declaration($2);
							struct var_data v = { i++, 0 };
							zmienne[$2] = v;				}
	|	vdeclarations IDENTIFIER 
		LB NUMBER RB			{	check_double_declaration($2);
							struct var_data v = { i, 1 };
							zmienne[$2] = v;
							i += $4;					}
	;
commands:	commands command
	|	command
	;
command:	READ identifier SREDNIK		{	check_przypisanie_iterator();
							load_memory_to_register(1,0); reset_flags();
							GET(1)
							STORE(1)				}					
	|	WRITE NUMBER SREDNIK		{	create_number(code, $2, 1);
							PUT(1)						}
	|	WRITE identifier SREDNIK	{	save_value_to_register(1,1);
							reset_flags();
							PUT(1)						} 
	|	identifier			{	check_przypisanie_iterator();
							load_memory_to_register(1, 1);
							save_register_to_memory(1, 3);
							reset_flags();					} 
		PRZYPISANIE exp SREDNIK		{	load_memory_to_register(3, 0);
							STORE(1)
							reset_flags();					}
        /***********************************************************************************************/
        /********** IF *********************************************************************************/
        /***********************************************************************************************/
        |       IF                              {       PUSH_ETYK(ety+1) PUSH_ETYK(ety)
                                                        PUSH_ETYK(ety+1) PUSH_ETYK(ety) ety+=2;         }
                condition                       {       JZERO(1, etykiety.pop()) reset_flags();		}
                THEN
                commands                        {       JUMP(etykiety.pop())
                                                        POP_AND_WRITE_ETYK				}
                ELSE
                commands
                ENDIF                           {       POP_AND_WRITE_ETYK				}
	|	WHILE				{	PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety)
							PUSH_ETYK(ety+1) ety+=2;
							POP_AND_WRITE_ETYK				}
		condition DO			{	reset_flags();
							JZERO(1, etykiety.pop())			}
		commands ENDWHILE		{	JUMP(etykiety.pop())
							POP_AND_WRITE_ETYK				}
	/***********************************************************************************************/
	/********** FOR ********************************************************************************/
	/***********************************************************************************************/	
	|	FOR IDENTIFIER FROM
		identifier			{	FOR_1($2)
							save_value_to_register(1, 4);	//przypisanie wartosci id do iteratora
							FOR_2						}
		for_continue
	|	FOR IDENTIFIER FROM
		NUMBER				{	FOR_1($2)
							create_number(code, $4, 4);	//przypisanie wartosci number do iteratora
							FOR_2						}
		for_continue
	|	SKIP SREDNIK			{ }		
	;
for_continue: 	TO identifier DO		{	FOR_3
							save_value_to_register(1, 1); //przypisanie wartosci tego co jest w store 1 do 1
							FOR_4
							FOR_5						}
		commands ENDFOR			{	FOR_6						}
	|	TO NUMBER DO			{	FOR_3
							create_number(code, $2, 1);
							FOR_4
							FOR_5						}
		commands ENDFOR			{	FOR_6						}
	|	DOWNTO identifier DO		{	FOR_3
							//save_value_to_register(1, 1);
							DOWNTO_FOR_4
							FOR_5					}
		commands ENDFOR			{	DOWNTO_FOR_6					}
	|	DOWNTO NUMBER DO		{	FOR_3
							save_number_to_memory($2, 0);
							save_number_to_memory(0, 1);
							DOWNTO_FOR_4
							FOR_5						}
		commands ENDFOR			{	DOWNTO_FOR_6					}
	;
exp:	        NUMBER                          {       create_number(code, $1, 1);                     }
        |       identifier                      {       save_value_to_register(1,1);                    }
        /***********************************************************************************************/
        /********* DODAWANIE ***************************************************************************/
        /***********************************************************************************************/
	|	NUMBER PLUS NUMBER		{	dodawanie_number_number($1, $3);		}
	|	identifier PLUS NUMBER		{	dodawanie_identifier_number($3);		}
	|	NUMBER PLUS identifier		{	dodawanie_number_identifier($1);		}
	|	identifier PLUS identifier	{	dodawanie_identifier_identifier();		}
	/************************************************************************************************/
	/******** ODEJMOWANIE ***************************************************************************/
	/************************************************************************************************/
	|	NUMBER MINUS NUMBER		{	odejmowanie_number_number($1, $3);		}
	|	identifier MINUS NUMBER		{	odejmowanie_identifier_number($3);		}
	|	NUMBER MINUS identifier		{	odejmowanie_number_identifier($1);		}
	|	identifier MINUS identifier	{	odejmowanie_identifier_identifier(1,2);		}
	/************************************************************************************************/
	/******** MNOZENIE ******************************************************************************/
	/************************************************************************************************/
	|	NUMBER MULT NUMBER		{	mnozenie_number_number($1, $3);			}
	|	identifier MULT NUMBER		{	mnozenie_identifier_number($3);			}
	|	NUMBER MULT identifier		{	mnozenie_number_identifier($1);			}
	|	identifier MULT identifier	{	mnozenie_identifier_identifier();		}
	/************************************************************************************************/
	/******** DZIELENIE *****************************************************************************/
	/************************************************************************************************/
	|	NUMBER DIV NUMBER		{	dzielenie_number_number($1, $3);		}
	|	identifier DIV NUMBER		{	dzielenie_identifier_number($3, false);		}
	|	NUMBER DIV identifier		{	dzielenie_number_identifier($1);		}
	|	identifier DIV identifier	{	dzielenie_identifier_identifier();		}
	/************************************************************************************************/
	/********* MODULO *******************************************************************************/
	/************************************************************************************************/
	|	NUMBER MOD NUMBER		{	modulo_number_number($1, $3);			}
	|	identifier MOD NUMBER		{	modulo_identifier_number($3);			}
	|	NUMBER MOD identifier		{	modulo_number_identifier($1);			}
	|	identifier MOD identifier	{	modulo_identifier_identifier();			}
	;
	/***********************************************************************************************/
	/********** WARUNKI ****************************************************************************/
	/***********************************************************************************************/
	/* warunki zwracaja w pierwszym rejestrze liczbe
	   w przypadku gdy liczba ta jest rowna 0 oznacza to falsz
	   gdy zas liczba jest rozna od 0 oznacza to prawde */
	/***********************************************************************************************/
	/**********    >    ****************************************************************************/
	/***********************************************************************************************/
condition:	NUMBER GT NUMBER		{	odejmowanie_number_number($1, $3);		}
        |       identifier GT NUMBER		{       odejmowanie_identifier_number($3);		}
        |       NUMBER GT identifier         	{       odejmowanie_number_identifier($1);		}
        |       identifier GT identifier     	{       odejmowanie_identifier_identifier(1,2);		}
	/***********************************************************************************************/
	/*********    <     ****************************************************************************/
	/***********************************************************************************************/
	/* robimy X LT Y to r1 <- Y-X */
	|	NUMBER LT NUMBER		{	odejmowanie_number_number($3, $1);	}
	|	identifier LT NUMBER		{	odejmowanie_number_identifier($3);	}
	|	NUMBER LT identifier		{	odejmowanie_identifier_number($1);	}
	|	identifier LT identifier	{	odejmowanie_identifier_identifier(2,1);	}
	/***********************************************************************************************/
	/*********    =    *****************************************************************************/
	/***********************************************************************************************/
	/* robimy X LT Y to X-Y = 0 = Y-X */
	|	NUMBER EQ NUMBER		{	rowne_number_number($1, $3);		}
	|	identifier EQ NUMBER		{	rowne_identifier_number($3);		}
	|	NUMBER EQ identifier		{	rowne_identifier_number($1);		}
	|	identifier EQ identifier	{	rowne_identifier_identifier();		}
	/***********************************************************************************************/
	/*********** >= ********************************************************************************/
	/***********************************************************************************************/
	/* robimy X GOET Y wtedy Y-X=0 zamien na 1, Y-X>0 zamien na 0 */
	|	NUMBER	GOET NUMBER		{	odejmowanie_number_number($3, $1);
                                                       	XOET				}
	|	identifier GOET NUMBER		{	odejmowanie_number_identifier($3);	//wieksze
							XOET				}
	|	NUMBER GOET identifier		{	odejmowanie_identifier_number($1);	//wieksze
							XOET				}
	|	identifier GOET identifier	{	odejmowanie_identifier_identifier(2,1);
							XOET				}
	/***********************************************************************************************/
        /*********** >= ********************************************************************************/
        /***********************************************************************************************/
        /* robimy X LOET Y wtedy X-Y=0 to zamien na 1, X-Y>0 to zamien na 0 */
	|	NUMBER LOET NUMBER		{	odejmowanie_number_number($1, $3); 	//mniejsze
							XOET				}
	|	identifier LOET NUMBER		{	odejmowanie_identifier_number($3);
							XOET				}
	|	NUMBER LOET identifier		{	odejmowanie_number_identifier($1);
							XOET				}
	|	identifier LOET identifier	{	odejmowanie_identifier_identifier(1,2);
							XOET				}
	/***********************************************************************************************/
        /*********** <> ********************************************************************************/
        /***********************************************************************************************/
        /* robimy X NEQ Y wtedy
                        { 0 : zwroc Y-X
                X-Y =   {
                        { >0 : wyskocz z ta wartoscia     */
	|	NUMBER NEQ NUMBER		{	NEQ_1
							odejmowanie_number_number($1, $3);
							NEQ_2
							odejmowanie_number_number($3, $1);
							NEQ_3				}
	|	identifier NEQ NUMBER		{	NEQ_1
							odejmowanie_identifier_number($3);
							NEQ_2
							odejmowanie_number_identifier($3);
							NEQ_3				}
	|	NUMBER NEQ identifier		{	NEQ_1
							odejmowanie_number_identifier($1);
							NEQ_2
							odejmowanie_identifier_number($1);
							NEQ_3				}
	|	identifier NEQ identifier	{	NEQ_1
							odejmowanie_identifier_identifier(1,2);
							NEQ_2
							odejmowanie_identifier_identifier(2,1);
							NEQ_3				}
							
	;
identifier:	IDENTIFIER			{	if(jestIterator() && iteratory.find($1) != iteratory.end()){ //sprawdz czy dostales iterator jako identyfikator
								create_number(code, mem_to_save_id, 0); //tutaj bedziemy zapisywac nasz iterator (jego adres lub wartosc)
								if($1==stack_iteratory.daj_top()){ //jesli iterator jest tym w ktorym aktualnie jestesmy
									STORE(4) //zapisujemy go do miejsca w pamieci 1 lub 2 bezposrednio z rejestru 4
									iter[mem_to_save_id - 1] = 1; //ustaw odpowiednia flage mowiaca ze w pamieci 1 lub 2 jest iterator z aktualnej petli
								}
								else{ //jesli iterator nie jest tym aktualnym z petli to jest on gdzies w pamieci, traktujemy go jak zmienna
									create_number(code, iteratory[$1].store, 1); //zapisz w pamieci 1 lub 2 adres tego iteratora
									STORE(1) //zapisz go
								}
								isIterator = 1; //ustaw flage mowiaca ze jest iterator, chroni nas to przed zmiana wartosci iteratora w petli
							}
							else{
								check_identifier($1, 0);
								create_number(code, mem_to_save_id, 0);
								create_number(code, zmienne[$1].store, 1);
								STORE(1)
							}
							mem_to_save_id++;				}
	|	IDENTIFIER LB IDENTIFIER RB	{	check_identifier($1, 1);
							if(jestIterator() && iteratory.find($3) != iteratory.end()){ //sprawdz czy dostales iterator jako identyfikator
								if($3==stack_iteratory.daj_top()){ //jesli iterator jest tym w ktorym aktualnie jestesmy
									create_number(code, 0, 0); //tutaj bedziemy zapisywac nasz iterator (jego wartosc)
									STORE(4) //zapisujemy go do miejsca w pamieci 0
								}
								else{ //jesli iterator nie jest tym aktualnym z petli to jest on gdzies w pamieci, traktujemy go jak zmienna
									create_number(code, iteratory[$3].store, 0); //zapisz w pamieci 0 adres tego iteratora
								}
							}
							else{
								check_identifier($3, 0);
								create_number(code, zmienne[$3].store, 0);
							}							
							create_number(code, zmienne[$1].store, 1);
							ADD(1)
							create_number(code, mem_to_save_id, 0);
							STORE(1)
							mem_to_save_id++;				}
	|	IDENTIFIER LB NUMBER RB		{	check_identifier($1, 1);
							create_number(code, $3, 1);
							ZERO(0)
							STORE(1)
							create_number(code, zmienne[$1].store, 1);
							ADD(1)
							create_number(code, mem_to_save_id, 0);
							STORE(1)
							mem_to_save_id++;				}
							
	;
%%

/**** DODATKOWE FUNKCJE ********************************************************************************/

void save_adress_to_register(int store, int reg){
	if(iter[store-1] == 1) //dzialamy tylko na reg rejestrze
		create_number(code, store, reg);
	else	//dzialamy tylko na 0 rejestrze
		load_memory_to_register(store, reg);
}

void save_value_to_register(int store, int reg){
	if(iter[store-1] == 1)
		create_number(code, store, 0);
	else
		load_memory_to_register(store,0);
	LOAD(reg)
}

/*
Funkcja generujaca asembler ktory dziala na rejestrze 0. Wczytuje on dana z komorki pamieci
'memory' do rejestru 'reg'.
*/
void load_memory_to_register(int memory, int reg){
	create_number(code, memory, 0);
	LOAD(reg)
}

void save_register_to_memory(int reg, int memory){
	create_number(code, memory, 0);
	STORE(reg)
}

void save_number_to_memory(unsigned long long int number, int memory){
	create_number(code, number, 1);
	create_number(code, memory, 0);
	STORE(1)
}

/****** FLAGI *******************************************************************************************/

void reset_flags(){
	iter[0] = 0;
	iter[1] = 0;
	mem_to_save_id = 1;
	isIterator = 0;
}

/****** CHECK *******************************************************************************************/
/********************************************************************************************************/
/*
Sprawdzenie czy zmienna o nazwie 'name' nie zostala zadeklarowana kolejny raz
Jezeli tak sie stalo, zakoncz program z odpowiednia wiadomoscia					
*/
void check_double_declaration(string name){
	if( zmienne.find(name) != zmienne.end() ){
                string err = string("Deklaracja istniejacej zmiennej \"") + string(name) + string("\"");
                yyerror(err.c_str());
        }
}

/* 
Sprawdzamy czy przeczytany identyfikator o nazwie 'name' zostal wczesniej zadeklarowany, oraz
czy jest on tablica (wtedy isArray = 1), czy jest zmienna (isArray = 0).
Jezeli ktorys z tych warunkow zawiedzie konczymy program z odpowiednim komunikatem.		
*/ 
void check_identifier(string name, int isArray){
	if( zmienne.find(name) == zmienne.end() ){
		string err = string("Niezadeklarowana zmienna \"") + string(name) + string("\"");
		yyerror(err.c_str());
	}
	else if( zmienne[name].isArray != isArray ){
		string err = string("Niewlasciwe uzycie identyfikatora \"") + string(name) +string("\"");
		yyerror(err.c_str());
	}
}

void check_iterator(string name){
	if( zmienne.find(name) != zmienne.end() ){
		string err = string("Wartośc zmiennej \"") + string(name) + string("\" uzyta jako iterator");
		yyerror(err.c_str());
	}
	else if( iteratory.find(name) != iteratory.end() ){
		string err = string("Iterator \"") + string(name) + string("\" uzyty dwukrotnie");
		yyerror(err.c_str());
	}
}

void check_przypisanie_iterator(){
	if( isIterator == 1 ){
		string err = string("Modyfikacja iteratora wewnatrz petli FOR");
		yyerror(err.c_str());
	}
	isIterator = 0;
}

/************************************************************************/
/***** OPERACJE *********************************************************/
/************************************************************************/

void dodawanie_number_number(unsigned long long int a, unsigned long long int b){
	if(a <= add_check_const && b <= add_check_const)
		create_number(code, a+b, 1);
	else{ //jezeli istnieje mozliwosc dostania liczby wiekszej niz unsigned long long
		ZERO(0)
		create_number(code, a, 1);
		STORE(1)
		create_number(code, b, 1);
		ADD(1)
	}									
}

void dodawanie_identifier_number(unsigned long long int num){
	if(num<=10){
		save_value_to_register(1,1);
		for(int i=0 ; i<num ; i++)
			INC(1)
	}
	else{
		save_adress_to_register(1, 0);
		create_number(code, num, 1);
		ADD(1)
	}
}

void dodawanie_number_identifier(unsigned long long int num){
	if(num<=10){
		save_value_to_register(1,1);
		for(int i=0 ; i<num ; i++)
			INC(1)
	}
	else{
		save_adress_to_register(1, 0);
		create_number(code, num, 1);
		ADD(1)
	}
}

void dodawanie_identifier_identifier(){
	save_value_to_register(1, 1);						
	save_adress_to_register(2,0);						
	ADD(1)
}

void odejmowanie_number_number(unsigned long long int a, unsigned long long int b){
	create_number(code, numbSub(a, b), 1);
}

void odejmowanie_identifier_number(unsigned long long int num){
	if(num<=10){
		save_value_to_register(1,1);
		for(int i=0 ; i<num ; i++)
			DEC(1)
	}
	else{
		save_number_to_memory(num, 0);
		save_value_to_register(1, 1);		
		ZERO(0)
		SUB(1)
	}
}

void odejmowanie_number_identifier(unsigned long long int num){
	save_adress_to_register(1, 0);
	create_number(code, num, 1);
	SUB(1)
}

/*zmienne a b oznaczaja ktory identifier jest odejmowany od ktorego, czyli:
	a=1, b=2 -> odejmujemy id1 - id2
	a=2, b=1 -> odejmujemy id2 - id1	*/
void odejmowanie_identifier_identifier(unsigned long long int a, unsigned long long int b){
	save_value_to_register(a,1);
	save_adress_to_register(b,0);
	SUB(1)
}

/****** MNOZENIE ********************************************************/

void mnozenie_przygotuj_rejesty_i_pamiec_number_number(unsigned long long int a, unsigned long long int b){
	MULT_MEM_1
	create_number(code, b, 2);	//A>B
	create_number(code, a, 3);	//A>B
	MULT_MEM_2
	create_number(code, b, 3);	//A<=B
	create_number(code, a, 2);	//A<=B
	MULT_MEM_3
}

void mnozenie_przygotuj_rejesty_i_pamiec_identifier_number(unsigned long long int numb){
	MULT_MEM_1
	create_number(code, numb, 2);	//A>B
	save_value_to_register(1, 3);	//A>B
	MULT_MEM_2
	save_value_to_register(1, 2);	//A<=B
	create_number(code, numb, 3);	//A<=B
	MULT_MEM_3
}

void mnozenie_przygotuj_rejesty_i_pamiec_number_identifier(unsigned long long int numb){
	MULT_MEM_1
	create_number(code, numb, 3);	//A>B
	save_value_to_register(1, 2);	//A>B
	MULT_MEM_2
	save_value_to_register(1, 3);	//A<=B
	create_number(code, numb, 2);	//A<=B
	MULT_MEM_3
}

void mnozenie_przygotuj_rejesty_i_pamiec_identifier_identifier(){
	MULT_MEM_1
	save_value_to_register(2, 2);	//A>B
	save_value_to_register(1, 3);	//A>B
	MULT_MEM_2
	save_value_to_register(1, 2);	//A<=B
	save_value_to_register(2, 3);	//A<=B
	MULT_MEM_3
}

void wykonaj_mnozenie(){
	PUSH_ETYK(ety+3) PUSH_ETYK(ety+2) PUSH_ETYK(ety+1) PUSH_ETYK(ety)
	PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+3) PUSH_ETYK(ety+2) ety+=4;
	POP_AND_WRITE_ETYK
	JZERO(2, etykiety.pop())
	JODD(2, etykiety.pop())
	JUMP(etykiety.pop())
	POP_AND_WRITE_ETYK
	ADD(1)
	POP_AND_WRITE_ETYK
	SHR(2)
	SHL(3)
	STORE(3)
	JUMP(etykiety.pop())
	POP_AND_WRITE_ETYK
}

void mnozenie_number_number(unsigned long long int num1, unsigned long long int num2){
	if(num1 <= mult_check_const && num2 <= mult_check_const)
		create_number(code, num1*num2, 1);
	else{
		odejmowanie_number_number(num1, num2);
		mnozenie_przygotuj_rejesty_i_pamiec_number_number(num1, num2);
		wykonaj_mnozenie();
	}
}

void mnozenie_identifier_number(unsigned long long int numb){
	int i = 0; //bedzie zawieral ilosc potencjalnych przesuniec SHL w przypadku gdy numb jest liczba 2^i, gdzie i>0
	if((i=is2expX(numb)) != 0){ //jesli num = 2^i to tylko przesuwamy
		save_value_to_register(1, 1);
		for(int j=0 ; j<i ; j++) 
			SHL(1)
	}
	else{
		odejmowanie_identifier_number(numb);
		mnozenie_przygotuj_rejesty_i_pamiec_identifier_number(numb);
		wykonaj_mnozenie();
	}
}

void mnozenie_number_identifier(unsigned long long int numb){
	int i = 0; //bedzie zawieral ilosc potencjalnych przesuniec SHL w przypadku gdy numb jest liczba 2^i, gdzie i>0
	if((i=is2expX(numb)) != 0){ //jesli num = 2^i to tylko przesuwamy
		save_value_to_register(1, 1);
		for(int j=0 ; j<i ; j++) 
			SHL(1)
	}
	else{
		odejmowanie_number_identifier(numb);
		mnozenie_przygotuj_rejesty_i_pamiec_number_identifier(numb);
		wykonaj_mnozenie();
	}
}

void mnozenie_identifier_identifier(){
	odejmowanie_identifier_identifier(1,2);
	mnozenie_przygotuj_rejesty_i_pamiec_identifier_identifier();
	wykonaj_mnozenie();
}

/****** DZIELENIE *******************************************************/


void dzielenie_przygotuj_rejesty_i_pamiec_identifier_number(unsigned long long int num){
	PUSH_ETYK(ety) ety+=1; //etykieta, majaca za zadanie wyskoczyc z dzielenia jezeli tylko bedziemy chcieli dzielic przez 0
	create_number(code, num, 1); //w r1 jest mianownik
	JZERO(1, etykiety.pop()) //sprawdz czy mianownik rowny 0, jesli tak wyskocz z calego dzielenia i zwroc 0, oraz reszte 0
	save_value_to_register(1, 2); //bedziemy pobierac wartosc z store 1 i w r2 jest licznik
	ZERO(0) 
	STORE(1) //w memory 0 jest mianownik
}

void dzielenie_przygotuj_rejesty_i_pamiec_number_identifier(unsigned long long int num){
	PUSH_ETYK(ety) ety+=1; //etykieta, majaca za zadanie wyskoczyc z dzielenia jezeli tylko bedziemy chcieli dzielic przez 0
	save_value_to_register(1, 1); //bedziemy pobierac wartosc z store 1 i w r1 jest mianownik
	JZERO(1, etykiety.pop()) //sprawdz czy mianownik rowny 0, jesli tak wyskocz z calego dzielenia i zwroc 0, oraz reszte 0
	create_number(code, num, 2); //w r2 jest licznik
	ZERO(0) 
	STORE(1) //w memory 0 jest mianownik
}

void dzielenie_przygotuj_rejesty_i_pamiec_identifier_identifier(){
	PUSH_ETYK(ety) ety+=1; //etykieta, majaca za zadanie wyskoczyc z dzielenia jezeli tylko bedziemy chcieli dzielic przez 0
	save_value_to_register(2, 1); //bedziemy pobierac wartosc z store 2 w r1 jest mianownik
	JZERO(1, etykiety.pop()) //sprawdz czy mianownik rowny 0, jesli tak wyskocz z calego dzielenia i zwroc 0, oraz reszte 0
	save_value_to_register(1, 2); //bedziemy pobierac wartosc z store 1w r2 jest licznik
	ZERO(0) 
	STORE(1) //w memory 0 jest mianownik
}

/* otrzymujemy w rejestrze 2 licznik ktory odwracamy i zapisujemy w rejestrze 3
   ustawiamy rowniez straznika na pierwszej pozycji od lewej, abysmy mogli przy
   dzieleniu identyfikowac koniec naszego licznika */
void dzielenie_odwroc_licznik(){
	create_number(code, 1, 3); //tworzymy straznika
	PUSH_ETYK(ety+2) PUSH_ETYK(ety+1) PUSH_ETYK(ety)
	PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+2) PUSH_ETYK(ety+1) ety+=3;
	POP_AND_WRITE_ETYK
	JZERO(2, etykiety.pop()) //warunek konca
	SHL(3) //juz przesowamy w lewno r3, moze dodamy 1
	JODD(2, etykiety.pop()) 
	SHR(2)			//w r2 przeczytalismy 0 na koncu, czyli
	JUMP(etykiety.pop())	//nic nie dodajemy
	POP_AND_WRITE_ETYK
	INC(3)			//w r2 przeczytalismy 1 na koncu
	SHR(2)			//czyli dodajemy 1 do r3
	JUMP(etykiety.pop())
	POP_AND_WRITE_ETYK
}

void wykonaj_dzielenie(){
	dzielenie_odwroc_licznik(); //odwracamy licznik, czyli r3 <= 1 + r3^R i dodajemy straznika
	PUSH_ETYK(ety+3) PUSH_ETYK(ety-4) PUSH_ETYK(ety+4) PUSH_ETYK(ety+2) PUSH_ETYK(ety+4) //druga etykieta odnosi sie do jumpa w przypadku mianownik=0 
	PUSH_ETYK(ety+2) PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety)
	PUSH_ETYK(ety+3) PUSH_ETYK(ety+4) ety+=5;
	ZERO(1) //zerujemy rejestr pierwszy
	POP_AND_WRITE_ETYK
	DEC(3)				//odejmij 1 od r3
	JZERO(3, etykiety.pop())	//sprawdz czy rowne 0
	INC(3)				//jezeli tak koniec dzielenia, jezeli nie dzielimy dalej
	SHL(1) //przesun wynij w lewo
	SHL(2)	//przesun r2 w lewo
	JODD(3, etykiety.pop())	//jezeli na koncu r3 jest 1
	JUMP(etykiety.pop())	//to dodaj 1 do r2, jezeli
	POP_AND_WRITE_ETYK	//nie to nic nie dodamy
	INC(2)			//i zostanie 0
	POP_AND_WRITE_ETYK	//
	SHR(3) //przesowanie odwroconego licznika w prawo, nie bedzie juz potrzebny
	INC(0)		//zapisanie r2
	STORE(2)	//do miejsca w pamieci nr 1
	ZERO(0)	//
	LOAD(2) //odejmij r2 <- mianownik - r2
	INC(0)  //dzieki temu gdy wyjdzie nam 0 wiemy ze mianownik byl <= od r2
	SUB(2)	//wtedy, dodamy 1 do wyniku, jesli wyjdzie cos != 0, to nie dodajemy
	JZERO(2,etykiety.pop()) 	//sprawdzenie wyniku odejmowania
	LOAD(2)			//zaladuj spowrotem
	ZERO(0)			//r2 do rejestru 2	MIANOWNIK > r2
	JUMP(etykiety.pop())	//
	POP_AND_WRITE_ETYK
	LOAD(2)			//odejmij r2-mianownik
	ZERO(0)			//i zwieksz wynik o 1	MIANOWNIK <= r2
	SUB(2)			//
	INC(1)			//
	JUMP(etykiety.pop())
	POP_AND_WRITE_ETYK 	//etykieta odpowiedzialna za przypadek X/0
	ZERO(1)			//ustawiamy wynik na 0
	ZERO(2)			//oraz modulo na 0
	POP_AND_WRITE_ETYK
}

void dzielenie_number_number(unsigned long long int a, unsigned long long int b){
	if(b == 0){
		ZERO(1)
		ZERO(2)
		return;
	}
	create_number(code, a/b, 1);
}

void dzielenie_identifier_number(unsigned long long int num, bool isModulo){
	int i = 0; //bedzie zawieral ilosc potencjalnych przesuniec SHR w przypadku gdy numb jest liczba 2^i, gdzie i>0
	if(!isModulo && (i=is2expX(num)) != 0){ //jesli num = 2^i to tylko przesuwamy, jezeli mamy modulo to nie wchodzimy
		save_value_to_register(1, 1);
		for(int j=0 ; j<i ; j++) 
			SHR(1)
	}
	else{
		dzielenie_przygotuj_rejesty_i_pamiec_identifier_number(num);
		wykonaj_dzielenie();
	}
}

void dzielenie_number_identifier(unsigned long long int num){
	dzielenie_przygotuj_rejesty_i_pamiec_number_identifier(num);
	wykonaj_dzielenie();
}

void dzielenie_identifier_identifier(){
	dzielenie_przygotuj_rejesty_i_pamiec_identifier_identifier();
	wykonaj_dzielenie();
}

/******* MODULO *******************************************************************/
/*Wykonujemy dzielenie, kopiujemy rejestr 2 do rejestru 1*/
void modulo_number_number(unsigned long long int a, unsigned long long int b){
	if(b == 0){
		ZERO(1)
		ZERO(2)
		return;
	}
	create_number(code, a%b, 1);
}

void modulo_identifier_number(unsigned long long int num){
	if(num == 2){
		PUSH_ETYK(ety+1) PUSH_ETYK(ety) PUSH_ETYK(ety+1) PUSH_ETYK(ety) ety+=2;
		save_value_to_register(1, 1);
		JODD(1, etykiety.pop())
		ZERO(1)
		JUMP(etykiety.pop())
		POP_AND_WRITE_ETYK
		ZERO(1)
		INC(1)
		POP_AND_WRITE_ETYK
	}
	else{
		dzielenie_identifier_number(num, true);
		ZERO(0)
		STORE(2)
		LOAD(1)
	}
}

void modulo_number_identifier(unsigned long long int num){
	dzielenie_number_identifier(num);
	ZERO(0)
	STORE(2)
	LOAD(1)
}

void modulo_identifier_identifier(){
	dzielenie_identifier_identifier();
	ZERO(0)
	STORE(2)
	LOAD(1)
}

/************************************************************************/
/***** WARUNKI **********************************************************/
/************************************************************************/
void rowne_number_number(unsigned long long int a, unsigned long long int b){
	EQ_1
	odejmowanie_number_number(a, b);	//odejmowanie
	EQ_2
	odejmowanie_number_number(b, a);	//odejmowanie
	EQ_3
}

void rowne_identifier_number(unsigned long long int num){
	EQ_1
	odejmowanie_identifier_number(num);	//odejmowanie
	EQ_2
	odejmowanie_number_identifier(num);	//odejmowanie
	EQ_3
}

void rowne_identifier_identifier(){
	EQ_1
	odejmowanie_identifier_identifier(1,2);	//odejmowanie
	EQ_2
	odejmowanie_identifier_identifier(2,1);	//odejmowanie
	EQ_3
}

/************************************************************************/
/****** ITERATORY *******************************************************/
/************************************************************************/

bool jestIterator(){
	return !stack_iteratory.jest_pusty();
}

/************************************************************************/
/***** PRZEBIEGI ********************************************************/
/************************************************************************/

void zapamietajPozycjeEtykiet(map<string, string> &ety_pos){
        for(int j=0 ; j<code.size(); j++){
                if(isPrefix("E", code[j])){
                        ostringstream ss;
                        ss << j;
                        ety_pos[code[j]] = ss.str();
                        code.erase(code.begin()+j);
                        j--;
                }
        }
}

void podmienEtykiety(map<string, string> &ety_pos){
       for(int j=0 ; j<code.size() ; j++)
                if(isPrefix("JUMP", code[j]) || isPrefix("JZERO", code[j]) || isPrefix("JODD", code[j]))
                        code[j] = replaceLastWord( code[j], ety_pos[ getLastWord(code[j]) ] );
}

void wypiszAssembler(){
	for(int j=0; j<code.size(); j++)
		cout << code[j] << endl;
	cout << "HALT" << endl;
}

/************************************************************************/
/******** MAIN **********************************************************/
/************************************************************************/

int main()
{
	iter[0] = 0;
	iter[1] = 0;
	yyparse();
	map<string, string> ety_pos;
	zapamietajPozycjeEtykiet(ety_pos);
	podmienEtykiety(ety_pos);
	wypiszAssembler();
}


void yyerror(const char *s){
        cout << "Blad. " << s << " w linii " << yylineno << endl;
        exit(0);
}
