package br.com.rafael.compilador.lexico;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import br.com.rafael.compilador.exceptions.*;


public class LexScanner {
	
	private char[] content;
	private int    estado;
	private int    pos;
	private int    line;
	private int    column;
	
	
	
	
	
	
	
	
	
	public LexScanner(String filename) {
		try {
			line = 1;
			column = 0;
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			System.out.println("DEBUG --------");
			System.out.println(txtConteudo);
			System.out.println("--------------");
			content = txtConteudo.toCharArray();
			pos=0;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Token nextToken(ArrayList reservadas) {
		char currentChar;
		Token token;
		String term="";
		if (isEOF()) {
			return null;
		}
		estado = 0;
		while (true) {
			currentChar = nextChar();
			column++;
			
			switch(estado) {
			//_____________________________________________________________________________________________________________________
			case 0:
				if ((isChar(currentChar))||(currentChar=='_')) {
					term += currentChar;
					estado = 1;
				}
				else if (isDigit(currentChar)) {
					estado = 2;
					term += currentChar;
				}
				else if (isSpace(currentChar)) {
					estado = 0;
				}
				else if (currentChar=='>') {
					term += currentChar;
					estado = 6;
				}
				else if ((currentChar=='<')){
					term += currentChar;
					estado = 7;
				}
				else if (currentChar == '=') {
					term += currentChar;
					estado = 8;
				}
				else if(isAritmetico(currentChar)) {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OP_ARIT);
					token.setText(term);
					return token;
				}
				else if(isEspecial(currentChar)) {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_ESPECIAL);
					token.setText(term);
					return token;
				}
				else if (currentChar =='!') {
					term += currentChar;
					estado = 9;
				}
				else if(currentChar == '\'') {
					term += currentChar;
					estado = 4;
				}
				else {
					throw new IsiLexicalException("Símbolo não reconhecido!");
				}
				break;
			case 1:
				if (isChar(currentChar) || isDigit(currentChar) || (currentChar =='_')) {
					estado = 1;
					term += currentChar;
				}
				else if (isSpace(currentChar) || isOperator(currentChar) || isEOF(currentChar)){
					if(reservadas.contains(term)) {
						token = new Token();
						token.setType(Token.TK_RESERVED);
						token.setText(term);
						return token;
					}
					else {
						if(!isEOF(currentChar)) {
							back();
						}
						token = new Token();
						token.setType(Token.TK_IDENT);
						token.setText(term);
						return token;
					}
					
				}
				else {
					throw new IsiLexicalException("Identificador mal formado!");
				}
				break;
			case 2:
				if (isDigit(currentChar)) {
					estado = 2;
					term += currentChar;
					
				}
				else if(currentChar == '.') {
					estado = 3;
					term += currentChar;
					
				}
				else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					token.setType(Token.TK_NUM_INT);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Número inteiro mal formado!");
				}
				break;
				
			case 3:
				if (isDigit(currentChar)) {
					estado = 3;
					term += currentChar;
				}
				else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					token.setType(Token.TK_NUM_REAL);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Número real mal formado!");
				}
				break;
				
			case 4:
				if (isChar(currentChar) || isDigit(currentChar)) {
					estado = 5;
					term += currentChar;
				}
				else {
					throw new IsiLexicalException("Char mal formado!");
				}
				break;
			case 5:
				if(currentChar == '\'') {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_CHAR);
					token.setText(term);
					return token;
				}
				else if(isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
				}
				else {
					throw new IsiLexicalException("Char mal formado!");
				}
				break;
				
			case 6:
				if(currentChar == '=') {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else if	(isSpace(currentChar) || isDigit(currentChar) || isEOF(currentChar)){
					if (!isEOF(currentChar)) {
						back();
					}
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Operador maior igual mal formado");
				}
			case 7:
				if(currentChar == '=') {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else if	(isSpace(currentChar) || isDigit(currentChar) || isEOF(currentChar)){
					if (!isEOF(currentChar)) {
						back();
					}
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Operador menor igual mal formado");
				}
			case 8:
				if(currentChar == '=') {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else if	(isSpace(currentChar) || isDigit(currentChar) || isEOF(currentChar)){
					if (!isEOF(currentChar)) {
						back();
					}
					token = new Token();
					token.setType(Token.TK_OP_ARIT);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Operador == mal formado");
				}
			case 9:
				if(currentChar == '=') {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else {
					throw new IsiLexicalException("Operador != mal formado");
				}
				
				
				
			}
		}
		
		
		
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	private boolean isEspecial(char c) {
		return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']' || c == ',' || c == ';';
		
	}
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c>='A' && c <= 'Z');
	}
	
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '=' || c == '!' || c == '+' || c == '-' || c == '*' || c == '/';
		
	}
	
	private boolean isAritmetico(char c) {
		return c == '+' || c == '-' || c == '/' || c == '*';
		
	}
	private boolean isSpace(char c) {
		if (c == '\n' || c== '\r') {
			line++;
			column=0;
		}
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
	
	private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	private boolean isEOF() {
		return pos >= content.length;
	}
	
    private void back() {
    	pos--;
    }
    
    private boolean isEOF(char c) {
    	return c == '\0';
    }
	
	
	
	
	
}


