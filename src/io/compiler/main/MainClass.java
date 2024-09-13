package io.compiler.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import io.compiler.core.GramaticaLexer;
import io.compiler.core.GramaticaParser;
import io.compiler.core.ast.Program;

public class MainClass {
	public static void main(String[] args) {
		try {
			GramaticaLexer lexer;
			GramaticaParser parser;
			
			lexer = new GramaticaLexer(CharStreams.fromFileName("input2.in"));
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			parser = new GramaticaParser(tokenStream);
			
			GramaticaLexer lexerExpression;
			GramaticaParser parserExpression;
			
			lexerExpression = new GramaticaLexer(CharStreams.fromFileName("expression.in"));
			CommonTokenStream tokenStreamExpression = new CommonTokenStream(lexerExpression);
			parserExpression = new GramaticaParser(tokenStreamExpression);
			
			System.out.println("Compilador");
			parser.prog();
			System.out.println("Compilação Bem Sucedida");
			parser.exibirVar();
			
			Program program = parser.getProgram();
			
			System.out.println("Starting Expression Analysis");
			parserExpression.expr();
			System.out.println("Analysis Finished!");
			System.out.println("Expression value " + parserExpression.generateValue());
			
			try {
				File f = new File(program.getName()+".java");
				FileWriter fr = new FileWriter(f);
				PrintWriter pr = new PrintWriter(fr);
				pr.println(program.generateTarget());
				pr.close();
				
				File fPy = new File(program.getName()+".py");
				FileWriter frPy = new FileWriter(fPy);
				PrintWriter prPy = new PrintWriter(frPy);
				prPy.println(program.generateTargetPython());
				prPy.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
