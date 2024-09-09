grammar Gramatica;

@header {
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Stack;
	import io.compiler.types.*;
	import io.compiler.core.exceptions.*;
	import io.compiler.core.ast.*;
	import io.compiler.runtime.*;
}

@members {
	private HashMap<String,Var> symbolTable = new HashMap<String,Var>();
	private ArrayList<Var> currentDecl = new ArrayList<Var>();
	private Types currentType;
	private Types leftType=null, rightType=null;
	private String strExpr = "";
	private Program program = new Program();
	private Stack<ArrayList<Command>> stack = new Stack<ArrayList<Command>>();
	private IfCommand currentIfCommand;
	private WhileCommand currentWhileCommand;
	private DoWhileCommand currentDoWhileCommand;
	private AssignmentCommand currentAssignmentCommand;
	
	private Stack<AbstractExpression> abstractStack = new Stack<AbstractExpression>();
	private AbstractExpression topo = null;
	
	public void updateType(){
        System.out.println("Updating types for variables in currentDecl");
        for(Var v: currentDecl){
            System.out.println("Setting type " + currentType + " for variable " + v.getId());
            v.setType(currentType);
            symbolTable.put(v.getId(), v);
        }
    }
	
	public void exibirVar() {
		System.out.println("Exibindo variáveis:");
		for (String id: symbolTable.keySet()) {
			System.out.println(symbolTable.get(id));
		}
	}
	
	public boolean isDeclared(String id) {
		return symbolTable.get(id) != null;
	}
	
	public Program getProgram(){
		return this.program;
	}
	
	public double generateValue(){
       if (topo == null){
          topo = abstractStack.pop();
       }
       return topo.evaluate();
    }
    
    public String generateJSON(){
    	if (topo == null){
          topo = abstractStack.pop();
       }
       return topo.toJson();
    }
}

// Regras Léxicas
NUM     : [0-9]+ ('.' [0-9]+)?                  ; // Números inteiros e reais
ID      : [a-z] ([a-z] | [A-Z] | [0-9])*        ; // Identificadores
STRING  : '"' .*? '"'                           ; // Strings
OP      : '+' | '-' | '*' | '/'                 ; // Operadores aritméticos
OP_REL  : '<' | '>' | '<=' | '>=' | '!=' | '==' ; // Operadores lógicos
OP_AT   : ':='                                  ; // Operador de atribuição
WS      : (' ' | '\t' | '\r' | '\n') -> skip    ; // Espaços em branco (ignorados)
PV      : ';'                                   ; // Ponto e vírgula
VIRG    : ','                                   ; // Vírgula



// Regras Sintáticas
// Início do programa
prog    :	'$' ID { program.setName(_input.LT(-1).getText()); 
				 	stack.push(new ArrayList<Command>());	
			    }
			declara+ 
			bloco 
		    '$' 
		    
		  	{ 
		  		program.setSymbolTable(symbolTable); 
		  		program.setCommandList(stack.pop());
		 	}
		  	;

// Declaração de variável
declara :	'declare' { currentDecl.clear(); }
			( 
			'INT' {currentType = Types.INT;}
			|
			'FLOAT' {currentType = Types.FLOAT;}
			|
			'STRING' {currentType = Types.STRING;}
			)
		
			ID { currentDecl.add(new Var(_input.LT(-1).getText())); }
			(VIRG ID { currentDecl.add(new Var(_input.LT(-1).getText())); })*
			PV { updateType(); } ;

// Bloco de comandos
bloco   : '{' cmd* '}' ;

// Tipos de comandos possíveis para o bloco
cmd     : 	cmdLeitura
        	| cmdEscrita
        	| cmdAtribuicao
        	| cmdIf
        	| cmdWhile
        	| cmdDoWhile
        	;

// Comando para Leitura
cmdLeitura : 	'leia' 
				'(' 
				ID { if (!isDeclared(_input.LT(-1).getText())) {
				     	throw new GramaticaSemanticException("Undeclared Variable: "+_input.LT(-1).getText());
				  	}
				  	 symbolTable.get(_input.LT(-1).getText()).setInitialized(true);
				  	 Command cmdRead = new ReadCommand(symbolTable.get(_input.LT(-1).getText()));
				  	 stack.peek().add(cmdRead);						
				   }  
				')' PV;



// Comando para Escrita
cmdEscrita : 	'escreva' 
				'(' 
				( 
					termo { 
							Command cmdWrite = new WriteCommand(_input.LT(-1).getText()); 
						  	stack.peek().add(cmdWrite);
						}
				) 
				')' PV { rightType = null; };



// Comando para o If Else
cmdIf :	'se' 
		{ 
			stack.push(new ArrayList<Command>()); 
			strExpr = "";
			currentIfCommand = new IfCommand(); 
		} 
		'(' 
		expr 
		OP_REL { strExpr += _input.LT(-1).getText(); } 
		expr 
		')' 
		{ currentIfCommand.setExpression(strExpr); }
			 
		'entao' bloco { currentIfCommand.setTrueList(stack.pop()); } 
			 
		(
		'senao' { stack.push(new ArrayList<Command>()); }
		bloco { currentIfCommand.setFalseList(stack.pop()); }
		)?  { stack.peek().add(currentIfCommand); };
			
			
			 
// Comando para o While			 
cmdWhile   : 'enquanto'
			 {
			 	stack.push(new ArrayList<Command>());
			 	strExpr = "";
			 	currentWhileCommand = new WhileCommand();
			 }
			 '(' 
			 expr 
			 OP_REL { strExpr += _input.LT(-1).getText(); } 
			 expr 
			 ')' 
			 { currentWhileCommand.setExpression(strExpr); }
			 
			 bloco 
			 {
			 	currentWhileCommand.setCommandList(stack.pop());
			 	stack.peek().add(currentWhileCommand);
			 }
			 ;



// Comando para o Do while
cmdDoWhile : 'faca'
			 {
			 	stack.push(new ArrayList<Command>());
			 	strExpr = "";
			 	currentDoWhileCommand = new DoWhileCommand();
			 } 
			 bloco { currentDoWhileCommand.setCommandList(stack.pop()); }
			 'enquanto'
			 '('
			 expr
			 OP_REL { strExpr += _input.LT(-1).getText(); } 
			 expr
			 ')'
			 { currentDoWhileCommand.setExpression(strExpr); }
			 PV 
			 { stack.peek().add(currentDoWhileCommand); }
			 ;



// Comando para a atribuição em uma Expressão
// Variavel := Expressão;
cmdAtribuicao : ID { 
			 		 strExpr = "";
			 		 currentAssignmentCommand = new AssignmentCommand();
			 		 
					 abstractStack.clear();

					 if (!isDeclared(_input.LT(-1).getText())) {
					 	throw new GramaticaSemanticException("Undeclared Variable"+_input.LT(-1).getText());
				   	 }
				   	 Var var = symbolTable.get(_input.LT(-1).getText()); // Pega o ID
				  	 var.setInitialized(true); // Inicializa o ID
				     leftType = var.getType(); // Pega o tipo do ID
				     rightType = null;
				     currentAssignmentCommand.setVar(var);							
				   }
			 	OP_AT 
			 	expr { currentAssignmentCommand.setExpression(strExpr); } 
			 	PV
			 	
			 	{
			 		System.out.println("Left  Side Expression Type = "+leftType);
                	System.out.println("Right Side Expression Type = "+rightType);
                	if (leftType.getValue() < rightType.getValue()){
                   		throw new GramaticaSemanticException("Type Mismatching on Assignment");
                	}
					
					double result = abstractStack.pop().evaluate();
					String formattedResult;
					if (leftType == Types.INT) {
					    formattedResult = Integer.toString((int) result);
					} else {
					    formattedResult = Double.toString(result);
					}
					
					var.setValue(formattedResult);
                	System.out.println("ID: " + var);
                	stack.peek().add(currentAssignmentCommand);
			 	}
		   		;




// Expressão
expr		:  termo exprl 			
			;



// Expressão Linha
exprl : (
			('+' | '-') { 
				strExpr += _input.LT(-1).getText(); 
	            BinaryExpression bin = new BinaryExpression(_input.LT(-1).getText().charAt(0));
	            bin.setLeft(abstractStack.pop());
	            abstractStack.push(bin);
			}
			
			termo {
				AbstractExpression topo = abstractStack.pop(); // desempilhei o termo
				System.out.println("Topo: " + topo);
	         	BinaryExpression root = (BinaryExpression) abstractStack.pop(); // preciso do componente binário
	         	System.out.println("Root: " + root);
	         	root.setRight(topo);
	         	abstractStack.push(root);
			}
		)*
		;
	
	
	
// Termo	
termo : fator { strExpr += _input.LT(-1).getText(); } termol ;




// Termo Linha
termol : (
			('*' | '/') { 
				strExpr += _input.LT(-1).getText();
				BinaryExpression bin = new BinaryExpression(_input.LT(-1).getText().charAt(0));
				if (abstractStack.peek() instanceof UnaryExpression) { // o que tem no topo é um operador "simples"
					bin.setLeft(abstractStack.pop()); // desempilho já tornando ele filho da multiplicacao
				}
				else{
				    BinaryExpression father = (BinaryExpression)abstractStack.pop();
				    if (father.getOperation() == '-' || father.getOperation() == '+'){
				    	bin.setLeft(father.getRight());
				    	father.setRight(bin);
				    }
				    else{
				        bin.setLeft(father);
				        abstractStack.push(bin);			       
				   	}
				}
			}
			
			fator { 
				strExpr += _input.LT(-1).getText(); 
				bin.setRight(abstractStack.pop());
				abstractStack.push(bin);
				System.out.println("DEBUG - :" + bin.toJson());
			}
		 )*
		 ;
		 



// Fator da expressão			
fator :	ID  {
			System.out.println("value: " + _input.LT(-1).getText());
			System.out.println("rightType: " + rightType); 
			if (!isDeclared(_input.LT(-1).getText())) {
            	throw new GramaticaSemanticException("Undeclared Variable: "+_input.LT(-1).getText());
            }
            if (!symbolTable.get(_input.LT(-1).getText()).isInitialized()) {
            	throw new GramaticaSemanticException("Variable "+_input.LT(-1).getText()+" has no value assigned");
            }
           	if (rightType == null) {
            	rightType = symbolTable.get(_input.LT(-1).getText()).getType();
            }   
            else {
            	if (symbolTable.get(_input.LT(-1).getText()).getType().getValue() > rightType.getValue()) {
                	rightType = symbolTable.get(_input.LT(-1).getText()).getType(); 
            	}
            }
            // Aqui você deve empilhar a expressão associada ao ID
            UnaryExpression element = new UnaryExpression(Double.parseDouble(symbolTable.get(_input.LT(-1).getText()).getValue()));
            System.out.println("Atribuindo value " + _input.LT(-1).getText() + " para o tipo " + rightType);
            abstractStack.push(element);
		}   
		| NUM {  
			System.out.println("value: " + _input.LT(-1).getText());
			System.out.println("rightType: " + rightType); 
			if (rightType == null) {			
 				rightType = Types.INT;
			}
			else{
				if (rightType.getValue() < Types.INT.getValue()){			                    			                   
			     	rightType = Types.INT;
		        }
			}
			UnaryExpression element = new UnaryExpression(Double.parseDouble(_input.LT(-1).getText()));
	     	System.out.println("Atribuindo value " + _input.LT(-1).getText() + " para o tipo " + rightType);
         	abstractStack.push(element);
		}
		| STRING  {
			System.out.println("value: " + _input.LT(-1).getText());
			System.out.println("rightType: " + rightType); 
			if (rightType == null) {
				rightType = Types.STRING;
			}
			else{
				if (rightType.getValue() < Types.STRING.getValue()){			                    
	            	rightType = Types.STRING;    	
			    }
			}
			System.out.println("String");
		}
		;