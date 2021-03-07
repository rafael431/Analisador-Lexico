# Analisador-Lexico

Analisador léxico desenvolvido em Java para projeto da cadeira de Compiladores do Curso de Graduação da Universidade Católica de Pernambuco.

O código foi desenvolvido com base nas aulas do Prof. Isidro. 

Reconhece e associa tokens aos seguintes tipos:

IDENTIFICADOR
	(letra | "__")(letra | "__" | dígito)*

INTEIRO
	digito+

REAL
	digito+.digito+
	
CHAR
	'letra' | 'dígito'
	
OPERADOR RELACIONAL
 	<  |  >  |  <=  |  >=  |  ==  |  !=

OPERADOR ARITMÉTICO
	"+"  |  "-"  |  "*"  |  "/"  |  "="

CARACTER ESPECIAL
	")"  |  "("  |  "{"  |  "}"  |  ","  |  ";"

PALAVRA RESERVADA
	main  |  if  |  else  |  while  |  do  |  for  |  int  |  float  |  char
