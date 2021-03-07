# Analisador-Lexico

Analisador léxico desenvolvido em java com base nas aulas do Prof. Isidro. 

Reconhece e associa tokens aos seguintes tipos:

IDENTIFICADOR
	(letra | "_")(letra | "_" | dígito)*

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
