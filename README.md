# COMPILADOR JAVA

Nathan Rubio - 11201922233

## ITENS DA GRAMÁTICA

* 4 Tipos de Variáveis (INT, FLOAT, STRING, BOOLEAN)
* Comandos para Leitura e Escrita
* If Else
* While
* Do While
* Atribuição de Variáveis
* Execução de operações aritméticas
* Verificação de Declaração de Variável
* Verificação do uso de Variável
* Verificação da Inicialização da Variável
* Inserção de operadores relacionais e booleanos (&& e ||)

## Variáveis na seção @members

```
// Tabela de símbolos para armazenar variáveis declaradas
	private HashMap<String,Var> symbolTable = new HashMap<String,Var>();

	// Lista temporária para armazenar variáveis sendo declaradas
	private ArrayList<Var> currentDecl = new ArrayList<Var>();

	// Variável para armazenar o tipo atual de uma variável ou expressão
	private Types currentType;

	// Tipos usados para verificar o tipo do lado esquerdo e direito em atribuições
	private Types leftType=null, rightType=null;

	// String que armazena a expressão atual
	private String strExpr = "";

	// Programa principal que será compilado e executado
	private Program program = new Program();

	// Pilha de comandos, usada para organizar blocos de comandos como if, while, etc
	private Stack<ArrayList<Command>> stack = new Stack<ArrayList<Command>>();

	// Variáveis auxiliares para armazenar os comandos atuais (If, While, DoWhile, etc.)
	private IfCommand currentIfCommand;
	private WhileCommand currentWhileCommand;
	private DoWhileCommand currentDoWhileCommand;
	private AssignmentCommand currentAssignmentCommand;

	// Pilha para expressões abstratas (como operações matemáticas)
	private Stack<AbstractExpression> abstractStack = new Stack<AbstractExpression>();
	private AbstractExpression topo = null;

	// Modo de avaliação das expressões (utilizado para separar avaliação de compilação)
	private boolean evaluationMode = true;
```

## Métodos em @members

```
// Método para atualizar o tipo das variáveis declaradas no bloco atual
	public void updateType(){
        for(Var v: currentDecl){
            v.setType(currentType);
            symbolTable.put(v.getId(), v);
        }
    }
	
	// Método para exibir todas as variáveis da tabela de símbolos
	public void exibirVar() {
		System.out.println("Exibindo variáveis:");
		for (String id: symbolTable.keySet()) {
			System.out.println(symbolTable.get(id));
		}
	}
	
	// Verifica se uma variável foi declarada
	public boolean isDeclared(String id) {
		return symbolTable.get(id) != null;
	}
	
	// Retorna o programa atual
	public Program getProgram(){
		return this.program;
	}
	
	// Avalia a expressão no topo da pilha de expressões
	public double generateValue(){
       if (topo == null){
          topo = abstractStack.pop();
       }
       return topo.evaluate();
    }
    
    // Gera o JSON da expressão no topo da pilha de expressões
    public String generateJSON(){
    	if (topo == null){
          topo = abstractStack.pop();
       }
       return topo.toJson();
    }
    
    // Verifica se há variáveis declaradas mas não utilizadas
    public void checkUnusedVariables() {
	    for (Var var : symbolTable.values()) {
	        if (!var.isInitialized()) {
	            System.out.println("Warning: Variable " + var.getId() + " declared but never used.");
	        }
    	}
	}
	
	// Define o modo de avaliação (true para avaliação, false para compilação)
	public void setEvaluationMode(boolean mode) {
        this.evaluationMode = mode;
    }

	// Retorna o estado atual do modo de avaliação
    public boolean isEvaluationMode() {
        return this.evaluationMode;
    }
```

## Regras Léxicas
```
NUM     : [0-9]+ ('.' [0-9]+)?                  ; // Números inteiros e reais.
BOOL    : 'verdadeiro' | 'falso'                ; // Booleanos
ID      : [a-z] ([a-z] | [A-Z] | [0-9])*        ; // Identificadores (variáveis)
STRING  : '"' .*? '"'                           ; // Strings
OP      : '+' | '-' | '*' | '/'                 ; // Operadores aritméticos
OP_REL  : '<' | '>' | '<=' | '>=' | '!=' | '==' ; // Operadores lógicos (relacionais)
OP_BOOL : '&&' | '||'                           ; // Operadores booleanos (&& para "e" e || para "ou")
OP_AT   : ':='                                  ; // Operador de atribuição
WS      : (' ' | '\t' | '\r' | '\n') -> skip    ; // Espaços em branco (ignorados)
PV      : ';'                                   ; // Ponto e vírgula
VIRG    : ','                                   ; // Vírgula
```

## Regras Semânticas
* __prog:__ É o início do programa, simbolizado por dois '$' abrindo e fechando.
* __declara:__ É a parte da declaração de variáveis, declarando primeiramente o tipo da variável e depois a variável em si.
* __bloco:__ É a parte onde é feito o a execução das instruções de fato, representada pelas '{}' depois da declaração das variáveis.
* __cmd:__ Define os diferentes comandos que podem ser executados na gramática.
* __cmdLeitura:__ Comando para a leitura de uma variável
* __cmdEscrita:__ Comando para a escrita de uma variável
* __cmdIf:__ Comando para a execução de um If Else
* __cmdWhile:__ Comando para a execução de um While
* __cmdDoWhile:__ Comando para a execução de um Do While
* __cmdAtribuicao:__ Comando para a atribuição de uma variável a uma expressão.

```
// Início do programa
// Define o nome do programa, e inclui declarações e bloco principal de comandos
prog    :	'$' ID { program.setName(_input.LT(-1).getText()); 
				 	stack.push(new ArrayList<Command>());	
			    }
			declara+ 
			bloco 
		    '$' 
		    
		  	{ 
		  		program.setSymbolTable(symbolTable); 
		  		program.setCommandList(stack.pop());
		  		// Verifica se há variáveis não utilizadas
                        checkUnusedVariables();
		 	}
		  	;

// Declaração de variável
// Permite a declaração de variáveis de diferentes tipos
declara :	'declare' { currentDecl.clear(); }
			( 
			'INT' {currentType = Types.INT;}
			|
			'FLOAT' {currentType = Types.FLOAT;}
			|
			'STRING' {currentType = Types.STRING;}
			|
			'BOOLEAN' {currentType = Types.BOOLEAN;}
			)
		
			ID { currentDecl.add(new Var(_input.LT(-1).getText())); }
			(VIRG ID { currentDecl.add(new Var(_input.LT(-1).getText())); })*
			PV { updateType(); } ;

// Bloco de comandos
// Define um bloco de comandos que pode incluir várias instruções
bloco   : '{' cmd* '}' ;

// Comandos possíveis para o bloco
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
			setEvaluationMode(false);
		} 
		'(' 
		bool_expr
		')' 
		{ currentIfCommand.setExpression(strExpr); }
			 
		'entao' bloco { currentIfCommand.setTrueList(stack.pop()); } 
			 
		(
		'senao' { stack.push(new ArrayList<Command>()); }
		bloco { currentIfCommand.setFalseList(stack.pop()); }
		)?  
		{ 
			stack.peek().add(currentIfCommand); 
			setEvaluationMode(true);
		}
		;
			 
// Comando para o While			 
cmdWhile   : 'enquanto'
			 {
			 	stack.push(new ArrayList<Command>());
			 	strExpr = "";
			 	currentWhileCommand = new WhileCommand();
			 	abstractStack.clear();
			 	rightType = null;
			 	setEvaluationMode(false);
			 }
			 '(' 
			 bool_expr 
			 ')'
			 { currentWhileCommand.setExpression(strExpr); }
			 
			 bloco 
			 {
			 	currentWhileCommand.setCommandList(stack.pop());
			 	stack.peek().add(currentWhileCommand);
			 	setEvaluationMode(true);
			 }
			 ;

// Comando para o Do while
cmdDoWhile : 'faca'
			 {
			 	stack.push(new ArrayList<Command>());
			 	strExpr = "";
			 	currentDoWhileCommand = new DoWhileCommand();
			 	setEvaluationMode(false);
			 } 
			 bloco { 
			 	   		currentDoWhileCommand.setCommandList(stack.pop());
			 	   		strExpr = "";
			 	   }
			 'enquanto'
			 '('
			 bool_expr
			 ')'
			 { currentDoWhileCommand.setExpression(strExpr); }
			 PV 
			 { 
			 	stack.peek().add(currentDoWhileCommand); 
			 	setEvaluationMode(true);
			 }
			 ;

// Comando para a atribuição em uma Expressão
// Variavel := Expressão;
cmdAtribuicao : ID { 
			 		 strExpr = "";
			 		 currentAssignmentCommand = new AssignmentCommand();
			 		 
					 abstractStack.clear();

					 if (!isDeclared(_input.LT(-1).getText())) {
					 	throw new GramaticaSemanticException("Undeclared Variable "+_input.LT(-1).getText());
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
			 		//System.out.println("Left  Side Expression Type = "+leftType);
                	//System.out.println("Right Side Expression Type = "+rightType);
                	
                	// Verifica compatibilidade de tipos
                	if (leftType.getValue() < rightType.getValue()){
                   		throw new GramaticaSemanticException("Type Mismatching on Assignment");
                	}
					
					if (isEvaluationMode()) {
						String formattedResult;
					    if (leftType == Types.INT || leftType == Types.FLOAT) {
					        // Realiza avaliação para tipos numéricos
					        double result = abstractStack.pop().evaluate();
					        if (leftType == Types.INT) {
					            formattedResult = Integer.toString((int) result);
					        } else {
					            formattedResult = Double.toString(result);
					        }
					    } else if (leftType == Types.BOOLEAN || leftType == Types.STRING) {
					        // Atribui diretamente o valor da expressão para BOOLEAN ou STRING
					        formattedResult = strExpr;
					    } else {
					        throw new GramaticaSemanticException("Unsupported type for assignment");
					    }
					    
					    // Atribui o valor à variável
					    var.setValue(formattedResult);
				    }
				    System.out.println("ID: " + var);
					stack.peek().add(currentAssignmentCommand);
			 	}
		   		;
```

## Expressões

Para os comandos com o uso de expressões condicionais é utilizado o bool_expr no início que utiliza-se dos operadores booleanos '&&' e '||' em conjunto com outros operadores relacionais ">", "<", "==", etc...

### Funcionamento Básico:

* __expr:__ Retorna um termo e um exprl

* __exprl:__ Retorna uma operação de soma/subtração e um termo, zero ou mais vezes

* __termo:__ Retorna um fator e um termol

* __termol:__ Retorna uma operação de multiplicação/divisão e um fator, zero ou mais vezes

* __fator:__ Retorna um ID, ou seja uma das variáveis declaradas anteriormente, ou qualquer elemento dos tipos declarados anteriormente, NUM (O tratamento de INT e FLOAT é feito na atribuição), STRING e BOOL

```
// Expressão Booleana
// Avalia expressões lógicas
bool_expr : relational_expr (OP_BOOL { strExpr += _input.LT(-1).getText(); } relational_expr)* ;

relational_expr : expr OP_REL { strExpr += _input.LT(-1).getText(); } expr ;

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
	         	BinaryExpression root = (BinaryExpression) abstractStack.pop(); // preciso do componente binário
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
				//System.out.println("DEBUG - :" + bin.toJson());
			}
		 )*
		 ;
		 
// Fator da expressão			
fator :	ID  {
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
            UnaryExpression element = new UnaryExpression(Double.parseDouble(symbolTable.get(_input.LT(-1).getText()).getValue())); // Empilha
            abstractStack.push(element);
		}   
		| NUM {  
			if (rightType == null) {			
 				rightType = Types.INT;
			}
			else{
				if (rightType.getValue() < Types.INT.getValue()){			                    			                   
			     	rightType = Types.INT;
		        }
			}
			UnaryExpression element = new UnaryExpression(Double.parseDouble(_input.LT(-1).getText())); // Empilha
         	abstractStack.push(element);
		}
		| STRING  {
			if (rightType == null) {
				rightType = Types.STRING;
			}
			else{
				if (rightType.getValue() < Types.STRING.getValue()){			                    
	            	rightType = Types.STRING;    	
			    }
			}
		}
		| BOOL {
			if (rightType == null) {
				rightType = Types.BOOLEAN;
			}
		}
		;
```

## COMPILAÇÃO

A compilação do programa é feita usando no MainClass.java, retornando a saída do programa e um programa equivalente na linguagem Java e também na linguagem Python. Pode ser utilizado os arquivos input.in e input2.in, já feitos, baseados na gramática utilizada.

## AVALIADOR DE EXPRESSÕES ARITMÉTICAS

Foi incorporado ao MainClass a execução de um simples avaliador de expressões aritméticas, utilizando o arquivo expression.in que contém apenas a expressão a ser avaliada com o resultado da avaliação sendo retornado com a execução do arquivo.
