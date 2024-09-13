// Generated from Gramatica.g4 by ANTLR 4.13.2
package io.compiler.core;

	// Importações de classes Java necessárias para a execução da linguagem compilada.
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Stack;
	import io.compiler.types.*;
	import io.compiler.core.exceptions.*;
	import io.compiler.core.ast.*;
	import io.compiler.runtime.*;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GramaticaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, NUM=22, BOOL=23, ID=24, STRING=25, 
		OP=26, OP_REL=27, OP_BOOL=28, OP_AT=29, WS=30, PV=31, VIRG=32;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "NUM", "BOOL", "ID", "STRING", "OP", 
			"OP_REL", "OP_BOOL", "OP_AT", "WS", "PV", "VIRG"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'$'", "'declare'", "'INT'", "'FLOAT'", "'STRING'", "'BOOLEAN'", 
			"'{'", "'}'", "'leia'", "'('", "')'", "'escreva'", "'se'", "'entao'", 
			"'senao'", "'enquanto'", "'faca'", "'+'", "'-'", "'*'", "'/'", null, 
			null, null, null, null, null, null, "':='", null, "';'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "NUM", "BOOL", 
			"ID", "STRING", "OP", "OP_REL", "OP_BOOL", "OP_AT", "WS", "PV", "VIRG"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


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


	public GramaticaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Gramatica.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000 \u00ea\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0004\u0015\u00a0\b\u0015\u000b\u0015\f\u0015\u00a1"+
		"\u0001\u0015\u0001\u0015\u0004\u0015\u00a6\b\u0015\u000b\u0015\f\u0015"+
		"\u00a7\u0003\u0015\u00aa\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003"+
		"\u0016\u00bb\b\u0016\u0001\u0017\u0001\u0017\u0005\u0017\u00bf\b\u0017"+
		"\n\u0017\f\u0017\u00c2\t\u0017\u0001\u0018\u0001\u0018\u0005\u0018\u00c6"+
		"\b\u0018\n\u0018\f\u0018\u00c9\t\u0018\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u00d8\b\u001a"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u00de\b\u001b"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u00c7"+
		"\u0000 \u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b"+
		"\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016"+
		"-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? \u0001"+
		"\u0000\u0006\u0001\u000009\u0001\u0000az\u0003\u000009AZaz\u0003\u0000"+
		"*+--//\u0002\u0000<<>>\u0003\u0000\t\n\r\r  \u00f4\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u0000"+
		"1\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001"+
		"\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000"+
		"\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000"+
		"?\u0001\u0000\u0000\u0000\u0001A\u0001\u0000\u0000\u0000\u0003C\u0001"+
		"\u0000\u0000\u0000\u0005K\u0001\u0000\u0000\u0000\u0007O\u0001\u0000\u0000"+
		"\u0000\tU\u0001\u0000\u0000\u0000\u000b\\\u0001\u0000\u0000\u0000\rd\u0001"+
		"\u0000\u0000\u0000\u000ff\u0001\u0000\u0000\u0000\u0011h\u0001\u0000\u0000"+
		"\u0000\u0013m\u0001\u0000\u0000\u0000\u0015o\u0001\u0000\u0000\u0000\u0017"+
		"q\u0001\u0000\u0000\u0000\u0019y\u0001\u0000\u0000\u0000\u001b|\u0001"+
		"\u0000\u0000\u0000\u001d\u0082\u0001\u0000\u0000\u0000\u001f\u0088\u0001"+
		"\u0000\u0000\u0000!\u0091\u0001\u0000\u0000\u0000#\u0096\u0001\u0000\u0000"+
		"\u0000%\u0098\u0001\u0000\u0000\u0000\'\u009a\u0001\u0000\u0000\u0000"+
		")\u009c\u0001\u0000\u0000\u0000+\u009f\u0001\u0000\u0000\u0000-\u00ba"+
		"\u0001\u0000\u0000\u0000/\u00bc\u0001\u0000\u0000\u00001\u00c3\u0001\u0000"+
		"\u0000\u00003\u00cc\u0001\u0000\u0000\u00005\u00d7\u0001\u0000\u0000\u0000"+
		"7\u00dd\u0001\u0000\u0000\u00009\u00df\u0001\u0000\u0000\u0000;\u00e2"+
		"\u0001\u0000\u0000\u0000=\u00e6\u0001\u0000\u0000\u0000?\u00e8\u0001\u0000"+
		"\u0000\u0000AB\u0005$\u0000\u0000B\u0002\u0001\u0000\u0000\u0000CD\u0005"+
		"d\u0000\u0000DE\u0005e\u0000\u0000EF\u0005c\u0000\u0000FG\u0005l\u0000"+
		"\u0000GH\u0005a\u0000\u0000HI\u0005r\u0000\u0000IJ\u0005e\u0000\u0000"+
		"J\u0004\u0001\u0000\u0000\u0000KL\u0005I\u0000\u0000LM\u0005N\u0000\u0000"+
		"MN\u0005T\u0000\u0000N\u0006\u0001\u0000\u0000\u0000OP\u0005F\u0000\u0000"+
		"PQ\u0005L\u0000\u0000QR\u0005O\u0000\u0000RS\u0005A\u0000\u0000ST\u0005"+
		"T\u0000\u0000T\b\u0001\u0000\u0000\u0000UV\u0005S\u0000\u0000VW\u0005"+
		"T\u0000\u0000WX\u0005R\u0000\u0000XY\u0005I\u0000\u0000YZ\u0005N\u0000"+
		"\u0000Z[\u0005G\u0000\u0000[\n\u0001\u0000\u0000\u0000\\]\u0005B\u0000"+
		"\u0000]^\u0005O\u0000\u0000^_\u0005O\u0000\u0000_`\u0005L\u0000\u0000"+
		"`a\u0005E\u0000\u0000ab\u0005A\u0000\u0000bc\u0005N\u0000\u0000c\f\u0001"+
		"\u0000\u0000\u0000de\u0005{\u0000\u0000e\u000e\u0001\u0000\u0000\u0000"+
		"fg\u0005}\u0000\u0000g\u0010\u0001\u0000\u0000\u0000hi\u0005l\u0000\u0000"+
		"ij\u0005e\u0000\u0000jk\u0005i\u0000\u0000kl\u0005a\u0000\u0000l\u0012"+
		"\u0001\u0000\u0000\u0000mn\u0005(\u0000\u0000n\u0014\u0001\u0000\u0000"+
		"\u0000op\u0005)\u0000\u0000p\u0016\u0001\u0000\u0000\u0000qr\u0005e\u0000"+
		"\u0000rs\u0005s\u0000\u0000st\u0005c\u0000\u0000tu\u0005r\u0000\u0000"+
		"uv\u0005e\u0000\u0000vw\u0005v\u0000\u0000wx\u0005a\u0000\u0000x\u0018"+
		"\u0001\u0000\u0000\u0000yz\u0005s\u0000\u0000z{\u0005e\u0000\u0000{\u001a"+
		"\u0001\u0000\u0000\u0000|}\u0005e\u0000\u0000}~\u0005n\u0000\u0000~\u007f"+
		"\u0005t\u0000\u0000\u007f\u0080\u0005a\u0000\u0000\u0080\u0081\u0005o"+
		"\u0000\u0000\u0081\u001c\u0001\u0000\u0000\u0000\u0082\u0083\u0005s\u0000"+
		"\u0000\u0083\u0084\u0005e\u0000\u0000\u0084\u0085\u0005n\u0000\u0000\u0085"+
		"\u0086\u0005a\u0000\u0000\u0086\u0087\u0005o\u0000\u0000\u0087\u001e\u0001"+
		"\u0000\u0000\u0000\u0088\u0089\u0005e\u0000\u0000\u0089\u008a\u0005n\u0000"+
		"\u0000\u008a\u008b\u0005q\u0000\u0000\u008b\u008c\u0005u\u0000\u0000\u008c"+
		"\u008d\u0005a\u0000\u0000\u008d\u008e\u0005n\u0000\u0000\u008e\u008f\u0005"+
		"t\u0000\u0000\u008f\u0090\u0005o\u0000\u0000\u0090 \u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0005f\u0000\u0000\u0092\u0093\u0005a\u0000\u0000\u0093"+
		"\u0094\u0005c\u0000\u0000\u0094\u0095\u0005a\u0000\u0000\u0095\"\u0001"+
		"\u0000\u0000\u0000\u0096\u0097\u0005+\u0000\u0000\u0097$\u0001\u0000\u0000"+
		"\u0000\u0098\u0099\u0005-\u0000\u0000\u0099&\u0001\u0000\u0000\u0000\u009a"+
		"\u009b\u0005*\u0000\u0000\u009b(\u0001\u0000\u0000\u0000\u009c\u009d\u0005"+
		"/\u0000\u0000\u009d*\u0001\u0000\u0000\u0000\u009e\u00a0\u0007\u0000\u0000"+
		"\u0000\u009f\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000"+
		"\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a9\u0001\u0000\u0000\u0000\u00a3\u00a5\u0005.\u0000\u0000"+
		"\u00a4\u00a6\u0007\u0000\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000"+
		"\u00a9\u00a3\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000"+
		"\u00aa,\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005v\u0000\u0000\u00ac\u00ad"+
		"\u0005e\u0000\u0000\u00ad\u00ae\u0005r\u0000\u0000\u00ae\u00af\u0005d"+
		"\u0000\u0000\u00af\u00b0\u0005a\u0000\u0000\u00b0\u00b1\u0005d\u0000\u0000"+
		"\u00b1\u00b2\u0005e\u0000\u0000\u00b2\u00b3\u0005i\u0000\u0000\u00b3\u00b4"+
		"\u0005r\u0000\u0000\u00b4\u00bb\u0005o\u0000\u0000\u00b5\u00b6\u0005f"+
		"\u0000\u0000\u00b6\u00b7\u0005a\u0000\u0000\u00b7\u00b8\u0005l\u0000\u0000"+
		"\u00b8\u00b9\u0005s\u0000\u0000\u00b9\u00bb\u0005o\u0000\u0000\u00ba\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ba\u00b5\u0001\u0000\u0000\u0000\u00bb.\u0001"+
		"\u0000\u0000\u0000\u00bc\u00c0\u0007\u0001\u0000\u0000\u00bd\u00bf\u0007"+
		"\u0002\u0000\u0000\u00be\u00bd\u0001\u0000\u0000\u0000\u00bf\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0\u00c1\u0001"+
		"\u0000\u0000\u0000\u00c10\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c3\u00c7\u0005\"\u0000\u0000\u00c4\u00c6\t\u0000\u0000"+
		"\u0000\u00c5\u00c4\u0001\u0000\u0000\u0000\u00c6\u00c9\u0001\u0000\u0000"+
		"\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001\u0000\u0000"+
		"\u0000\u00c8\u00ca\u0001\u0000\u0000\u0000\u00c9\u00c7\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0005\"\u0000\u0000\u00cb2\u0001\u0000\u0000\u0000"+
		"\u00cc\u00cd\u0007\u0003\u0000\u0000\u00cd4\u0001\u0000\u0000\u0000\u00ce"+
		"\u00d8\u0007\u0004\u0000\u0000\u00cf\u00d0\u0005<\u0000\u0000\u00d0\u00d8"+
		"\u0005=\u0000\u0000\u00d1\u00d2\u0005>\u0000\u0000\u00d2\u00d8\u0005="+
		"\u0000\u0000\u00d3\u00d4\u0005!\u0000\u0000\u00d4\u00d8\u0005=\u0000\u0000"+
		"\u00d5\u00d6\u0005=\u0000\u0000\u00d6\u00d8\u0005=\u0000\u0000\u00d7\u00ce"+
		"\u0001\u0000\u0000\u0000\u00d7\u00cf\u0001\u0000\u0000\u0000\u00d7\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d7\u00d3\u0001\u0000\u0000\u0000\u00d7\u00d5"+
		"\u0001\u0000\u0000\u0000\u00d86\u0001\u0000\u0000\u0000\u00d9\u00da\u0005"+
		"&\u0000\u0000\u00da\u00de\u0005&\u0000\u0000\u00db\u00dc\u0005|\u0000"+
		"\u0000\u00dc\u00de\u0005|\u0000\u0000\u00dd\u00d9\u0001\u0000\u0000\u0000"+
		"\u00dd\u00db\u0001\u0000\u0000\u0000\u00de8\u0001\u0000\u0000\u0000\u00df"+
		"\u00e0\u0005:\u0000\u0000\u00e0\u00e1\u0005=\u0000\u0000\u00e1:\u0001"+
		"\u0000\u0000\u0000\u00e2\u00e3\u0007\u0005\u0000\u0000\u00e3\u00e4\u0001"+
		"\u0000\u0000\u0000\u00e4\u00e5\u0006\u001d\u0000\u0000\u00e5<\u0001\u0000"+
		"\u0000\u0000\u00e6\u00e7\u0005;\u0000\u0000\u00e7>\u0001\u0000\u0000\u0000"+
		"\u00e8\u00e9\u0005,\u0000\u0000\u00e9@\u0001\u0000\u0000\u0000\n\u0000"+
		"\u00a1\u00a7\u00a9\u00ba\u00be\u00c0\u00c7\u00d7\u00dd\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}