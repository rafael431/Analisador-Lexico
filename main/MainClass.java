package br.com.rafael.compilador.main;


import java.util.ArrayList;

import br.com.rafael.compilador.exceptions.IsiLexicalException;
import br.com.rafael.compilador.lexico.LexScanner;
import br.com.rafael.compilador.lexico.Token;

public class MainClass {

	public static void main(String[] args) {
		
		ArrayList<String> reservadas = new ArrayList<String>(9);
		reservadas.add("main");
		reservadas.add("if");
		reservadas.add("else");
		reservadas.add("while");
		reservadas.add("do");
		reservadas.add("for");
		reservadas.add("int");
		reservadas.add("float");
		reservadas.add("char");
		
		try {
			LexScanner sc = new LexScanner("D:\\Rafael 2021\\UNICAP\\2021.1\\COMPILADORES\\input.txt");
			Token token = null;
			
			do {
				token = sc.nextToken(reservadas);
				
				if(token != null) {
					System.out.println(token);
				}
				
			} while (token != null);
			
		} catch(IsiLexicalException ex) {
			System.out.println("Erro léxico: "+ ex.getMessage());
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		
		}
		

	}

}
