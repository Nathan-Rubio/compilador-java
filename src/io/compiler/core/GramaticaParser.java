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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GramaticaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, NUM=22, BOOL=23, ID=24, STRING=25, 
		OP=26, OP_REL=27, OP_BOOL=28, OP_AT=29, WS=30, PV=31, VIRG=32;
	public static final int
		RULE_prog = 0, RULE_declara = 1, RULE_bloco = 2, RULE_cmd = 3, RULE_cmdLeitura = 4, 
		RULE_cmdEscrita = 5, RULE_cmdIf = 6, RULE_cmdWhile = 7, RULE_cmdDoWhile = 8, 
		RULE_cmdAtribuicao = 9, RULE_bool_expr = 10, RULE_relational_expr = 11, 
		RULE_expr = 12, RULE_exprl = 13, RULE_termo = 14, RULE_termol = 15, RULE_fator = 16;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "declara", "bloco", "cmd", "cmdLeitura", "cmdEscrita", "cmdIf", 
			"cmdWhile", "cmdDoWhile", "cmdAtribuicao", "bool_expr", "relational_expr", 
			"expr", "exprl", "termo", "termol", "fator"
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

	@Override
	public String getGrammarFileName() { return "Gramatica.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public GramaticaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public BlocoContext bloco() {
			return getRuleContext(BlocoContext.class,0);
		}
		public List<DeclaraContext> declara() {
			return getRuleContexts(DeclaraContext.class);
		}
		public DeclaraContext declara(int i) {
			return getRuleContext(DeclaraContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__0);
			setState(35);
			match(ID);
			 program.setName(_input.LT(-1).getText()); 
							 	stack.push(new ArrayList<Command>());	
						    
			setState(38); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(37);
				declara();
				}
				}
				setState(40); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
			setState(42);
			bloco();
			setState(43);
			match(T__0);
			 
					  		program.setSymbolTable(symbolTable); 
					  		program.setCommandList(stack.pop());
					  		// Verifica se há variáveis não utilizadas
			                checkUnusedVariables();
					 	
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclaraContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(GramaticaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GramaticaParser.ID, i);
		}
		public TerminalNode PV() { return getToken(GramaticaParser.PV, 0); }
		public List<TerminalNode> VIRG() { return getTokens(GramaticaParser.VIRG); }
		public TerminalNode VIRG(int i) {
			return getToken(GramaticaParser.VIRG, i);
		}
		public DeclaraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declara; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterDeclara(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitDeclara(this);
		}
	}

	public final DeclaraContext declara() throws RecognitionException {
		DeclaraContext _localctx = new DeclaraContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declara);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(T__1);
			 currentDecl.clear(); 
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				{
				setState(48);
				match(T__2);
				currentType = Types.INT;
				}
				break;
			case T__3:
				{
				setState(50);
				match(T__3);
				currentType = Types.FLOAT;
				}
				break;
			case T__4:
				{
				setState(52);
				match(T__4);
				currentType = Types.STRING;
				}
				break;
			case T__5:
				{
				setState(54);
				match(T__5);
				currentType = Types.BOOLEAN;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(58);
			match(ID);
			 currentDecl.add(new Var(_input.LT(-1).getText())); 
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VIRG) {
				{
				{
				setState(60);
				match(VIRG);
				setState(61);
				match(ID);
				 currentDecl.add(new Var(_input.LT(-1).getText())); 
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(PV);
			 updateType(); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlocoContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public BlocoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bloco; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterBloco(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitBloco(this);
		}
	}

	public final BlocoContext bloco() throws RecognitionException {
		BlocoContext _localctx = new BlocoContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_bloco);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__6);
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 16986624L) != 0)) {
				{
				{
				setState(72);
				cmd();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdContext extends ParserRuleContext {
		public CmdLeituraContext cmdLeitura() {
			return getRuleContext(CmdLeituraContext.class,0);
		}
		public CmdEscritaContext cmdEscrita() {
			return getRuleContext(CmdEscritaContext.class,0);
		}
		public CmdAtribuicaoContext cmdAtribuicao() {
			return getRuleContext(CmdAtribuicaoContext.class,0);
		}
		public CmdIfContext cmdIf() {
			return getRuleContext(CmdIfContext.class,0);
		}
		public CmdWhileContext cmdWhile() {
			return getRuleContext(CmdWhileContext.class,0);
		}
		public CmdDoWhileContext cmdDoWhile() {
			return getRuleContext(CmdDoWhileContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmd(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_cmd);
		try {
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				cmdLeitura();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				cmdEscrita();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				cmdAtribuicao();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				cmdIf();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 5);
				{
				setState(84);
				cmdWhile();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 6);
				{
				setState(85);
				cmdDoWhile();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdLeituraContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public TerminalNode PV() { return getToken(GramaticaParser.PV, 0); }
		public CmdLeituraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdLeitura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdLeitura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdLeitura(this);
		}
	}

	public final CmdLeituraContext cmdLeitura() throws RecognitionException {
		CmdLeituraContext _localctx = new CmdLeituraContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_cmdLeitura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__8);
			setState(89);
			match(T__9);
			setState(90);
			match(ID);
			 if (!isDeclared(_input.LT(-1).getText())) {
							     	throw new GramaticaSemanticException("Undeclared Variable: "+_input.LT(-1).getText());
							  	}
							  	 symbolTable.get(_input.LT(-1).getText()).setInitialized(true);
							  	 Command cmdRead = new ReadCommand(symbolTable.get(_input.LT(-1).getText()));
							  	 stack.peek().add(cmdRead);						
							   
			setState(92);
			match(T__10);
			setState(93);
			match(PV);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdEscritaContext extends ParserRuleContext {
		public TerminalNode PV() { return getToken(GramaticaParser.PV, 0); }
		public TermoContext termo() {
			return getRuleContext(TermoContext.class,0);
		}
		public CmdEscritaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdEscrita; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdEscrita(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdEscrita(this);
		}
	}

	public final CmdEscritaContext cmdEscrita() throws RecognitionException {
		CmdEscritaContext _localctx = new CmdEscritaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cmdEscrita);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__11);
			setState(96);
			match(T__9);
			{
			setState(97);
			termo();
			 
										Command cmdWrite = new WriteCommand(_input.LT(-1).getText()); 
									  	stack.peek().add(cmdWrite);
									
			}
			setState(100);
			match(T__10);
			setState(101);
			match(PV);
			 rightType = null; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdIfContext extends ParserRuleContext {
		public Bool_exprContext bool_expr() {
			return getRuleContext(Bool_exprContext.class,0);
		}
		public List<BlocoContext> bloco() {
			return getRuleContexts(BlocoContext.class);
		}
		public BlocoContext bloco(int i) {
			return getRuleContext(BlocoContext.class,i);
		}
		public CmdIfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdIf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdIf(this);
		}
	}

	public final CmdIfContext cmdIf() throws RecognitionException {
		CmdIfContext _localctx = new CmdIfContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_cmdIf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__12);
			 
						stack.push(new ArrayList<Command>()); 
						strExpr = "";
						currentIfCommand = new IfCommand();
						setEvaluationMode(false);
					
			setState(106);
			match(T__9);
			setState(107);
			bool_expr();
			setState(108);
			match(T__10);
			 currentIfCommand.setExpression(strExpr); 
			setState(110);
			match(T__13);
			setState(111);
			bloco();
			 currentIfCommand.setTrueList(stack.pop()); 
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(113);
				match(T__14);
				 stack.push(new ArrayList<Command>()); 
				setState(115);
				bloco();
				 currentIfCommand.setFalseList(stack.pop()); 
				}
			}

			 
						stack.peek().add(currentIfCommand); 
						setEvaluationMode(true);
					
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdWhileContext extends ParserRuleContext {
		public Bool_exprContext bool_expr() {
			return getRuleContext(Bool_exprContext.class,0);
		}
		public BlocoContext bloco() {
			return getRuleContext(BlocoContext.class,0);
		}
		public CmdWhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdWhile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdWhile(this);
		}
	}

	public final CmdWhileContext cmdWhile() throws RecognitionException {
		CmdWhileContext _localctx = new CmdWhileContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cmdWhile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__15);

						 	stack.push(new ArrayList<Command>());
						 	strExpr = "";
						 	currentWhileCommand = new WhileCommand();
						 	abstractStack.clear();
						 	rightType = null;
						 	setEvaluationMode(false);
						 
			setState(124);
			match(T__9);
			setState(125);
			bool_expr();
			setState(126);
			match(T__10);
			 currentWhileCommand.setExpression(strExpr); 
			setState(128);
			bloco();

						 	currentWhileCommand.setCommandList(stack.pop());
						 	stack.peek().add(currentWhileCommand);
						 	setEvaluationMode(true);
						 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdDoWhileContext extends ParserRuleContext {
		public BlocoContext bloco() {
			return getRuleContext(BlocoContext.class,0);
		}
		public Bool_exprContext bool_expr() {
			return getRuleContext(Bool_exprContext.class,0);
		}
		public TerminalNode PV() { return getToken(GramaticaParser.PV, 0); }
		public CmdDoWhileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdDoWhile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdDoWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdDoWhile(this);
		}
	}

	public final CmdDoWhileContext cmdDoWhile() throws RecognitionException {
		CmdDoWhileContext _localctx = new CmdDoWhileContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cmdDoWhile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__16);

						 	stack.push(new ArrayList<Command>());
						 	strExpr = "";
						 	currentDoWhileCommand = new DoWhileCommand();
						 	setEvaluationMode(false);
						 
			setState(133);
			bloco();
			 
						 	   		currentDoWhileCommand.setCommandList(stack.pop());
						 	   		strExpr = "";
						 	   
			setState(135);
			match(T__15);
			setState(136);
			match(T__9);
			setState(137);
			bool_expr();
			setState(138);
			match(T__10);
			 currentDoWhileCommand.setExpression(strExpr); 
			setState(140);
			match(PV);
			 
						 	stack.peek().add(currentDoWhileCommand); 
						 	setEvaluationMode(true);
						 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmdAtribuicaoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public TerminalNode OP_AT() { return getToken(GramaticaParser.OP_AT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PV() { return getToken(GramaticaParser.PV, 0); }
		public CmdAtribuicaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdAtribuicao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterCmdAtribuicao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitCmdAtribuicao(this);
		}
	}

	public final CmdAtribuicaoContext cmdAtribuicao() throws RecognitionException {
		CmdAtribuicaoContext _localctx = new CmdAtribuicaoContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cmdAtribuicao);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			match(ID);
			 
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
							   
			setState(145);
			match(OP_AT);
			setState(146);
			expr();
			 currentAssignmentCommand.setExpression(strExpr); 
			setState(148);
			match(PV);

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
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Bool_exprContext extends ParserRuleContext {
		public List<Relational_exprContext> relational_expr() {
			return getRuleContexts(Relational_exprContext.class);
		}
		public Relational_exprContext relational_expr(int i) {
			return getRuleContext(Relational_exprContext.class,i);
		}
		public List<TerminalNode> OP_BOOL() { return getTokens(GramaticaParser.OP_BOOL); }
		public TerminalNode OP_BOOL(int i) {
			return getToken(GramaticaParser.OP_BOOL, i);
		}
		public Bool_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterBool_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitBool_expr(this);
		}
	}

	public final Bool_exprContext bool_expr() throws RecognitionException {
		Bool_exprContext _localctx = new Bool_exprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_bool_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			relational_expr();
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP_BOOL) {
				{
				{
				setState(152);
				match(OP_BOOL);
				 strExpr += _input.LT(-1).getText(); 
				setState(154);
				relational_expr();
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Relational_exprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP_REL() { return getToken(GramaticaParser.OP_REL, 0); }
		public Relational_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relational_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterRelational_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitRelational_expr(this);
		}
	}

	public final Relational_exprContext relational_expr() throws RecognitionException {
		Relational_exprContext _localctx = new Relational_exprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_relational_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			expr();
			setState(161);
			match(OP_REL);
			 strExpr += _input.LT(-1).getText(); 
			setState(163);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public TermoContext termo() {
			return getRuleContext(TermoContext.class,0);
		}
		public ExprlContext exprl() {
			return getRuleContext(ExprlContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			termo();
			setState(166);
			exprl();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprlContext extends ParserRuleContext {
		public List<TermoContext> termo() {
			return getRuleContexts(TermoContext.class);
		}
		public TermoContext termo(int i) {
			return getRuleContext(TermoContext.class,i);
		}
		public ExprlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterExprl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitExprl(this);
		}
	}

	public final ExprlContext exprl() throws RecognitionException {
		ExprlContext _localctx = new ExprlContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_exprl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17 || _la==T__18) {
				{
				{
				setState(168);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				 
								strExpr += _input.LT(-1).getText(); 
					            BinaryExpression bin = new BinaryExpression(_input.LT(-1).getText().charAt(0));
					            bin.setLeft(abstractStack.pop());
					            abstractStack.push(bin);
							
				setState(170);
				termo();

								AbstractExpression topo = abstractStack.pop(); // desempilhei o termo
					         	BinaryExpression root = (BinaryExpression) abstractStack.pop(); // preciso do componente binário
					         	root.setRight(topo);
					         	abstractStack.push(root);
							
				}
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermoContext extends ParserRuleContext {
		public FatorContext fator() {
			return getRuleContext(FatorContext.class,0);
		}
		public TermolContext termol() {
			return getRuleContext(TermolContext.class,0);
		}
		public TermoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterTermo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitTermo(this);
		}
	}

	public final TermoContext termo() throws RecognitionException {
		TermoContext _localctx = new TermoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_termo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			fator();
			 strExpr += _input.LT(-1).getText(); 
			setState(180);
			termol();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermolContext extends ParserRuleContext {
		public List<FatorContext> fator() {
			return getRuleContexts(FatorContext.class);
		}
		public FatorContext fator(int i) {
			return getRuleContext(FatorContext.class,i);
		}
		public TermolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterTermol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitTermol(this);
		}
	}

	public final TermolContext termol() throws RecognitionException {
		TermolContext _localctx = new TermolContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_termol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19 || _la==T__20) {
				{
				{
				setState(182);
				_la = _input.LA(1);
				if ( !(_la==T__19 || _la==T__20) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				 
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
							
				setState(184);
				fator();
				 
								strExpr += _input.LT(-1).getText(); 
								bin.setRight(abstractStack.pop());
								abstractStack.push(bin);
								//System.out.println("DEBUG - :" + bin.toJson());
							
				}
				}
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FatorContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GramaticaParser.ID, 0); }
		public TerminalNode NUM() { return getToken(GramaticaParser.NUM, 0); }
		public TerminalNode STRING() { return getToken(GramaticaParser.STRING, 0); }
		public TerminalNode BOOL() { return getToken(GramaticaParser.BOOL, 0); }
		public FatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).enterFator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GramaticaListener ) ((GramaticaListener)listener).exitFator(this);
		}
	}

	public final FatorContext fator() throws RecognitionException {
		FatorContext _localctx = new FatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fator);
		try {
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				match(ID);

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
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				match(NUM);
				  
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
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(196);
				match(STRING);

							if (rightType == null) {
								rightType = Types.STRING;
							}
							else{
								if (rightType.getValue() < Types.STRING.getValue()){			                    
					            	rightType = Types.STRING;    	
							    }
							}
						
				}
				break;
			case BOOL:
				enterOuterAlt(_localctx, 4);
				{
				setState(198);
				match(BOOL);

							if (rightType == null) {
								rightType = Types.BOOLEAN;
							}
						
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001 \u00cb\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0004\u0000\'\b\u0000\u000b\u0000\f\u0000(\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u00019\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0005\u0001@\b\u0001\n\u0001\f\u0001C\t\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0005\u0002J\b\u0002\n\u0002"+
		"\f\u0002M\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003W\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006w\b\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0005\n\u009c"+
		"\b\n\n\n\f\n\u009f\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0005\r\u00ae\b\r\n\r\f\r\u00b1\t\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0005\u000f\u00bc\b\u000f\n\u000f\f\u000f\u00bf\t\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u00c9\b\u0010\u0001\u0010\u0000\u0000\u0011\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \u0000\u0002\u0001\u0000\u0012\u0013\u0001\u0000\u0014\u0015\u00cb\u0000"+
		"\"\u0001\u0000\u0000\u0000\u0002.\u0001\u0000\u0000\u0000\u0004G\u0001"+
		"\u0000\u0000\u0000\u0006V\u0001\u0000\u0000\u0000\bX\u0001\u0000\u0000"+
		"\u0000\n_\u0001\u0000\u0000\u0000\fh\u0001\u0000\u0000\u0000\u000ez\u0001"+
		"\u0000\u0000\u0000\u0010\u0083\u0001\u0000\u0000\u0000\u0012\u008f\u0001"+
		"\u0000\u0000\u0000\u0014\u0097\u0001\u0000\u0000\u0000\u0016\u00a0\u0001"+
		"\u0000\u0000\u0000\u0018\u00a5\u0001\u0000\u0000\u0000\u001a\u00af\u0001"+
		"\u0000\u0000\u0000\u001c\u00b2\u0001\u0000\u0000\u0000\u001e\u00bd\u0001"+
		"\u0000\u0000\u0000 \u00c8\u0001\u0000\u0000\u0000\"#\u0005\u0001\u0000"+
		"\u0000#$\u0005\u0018\u0000\u0000$&\u0006\u0000\uffff\uffff\u0000%\'\u0003"+
		"\u0002\u0001\u0000&%\u0001\u0000\u0000\u0000\'(\u0001\u0000\u0000\u0000"+
		"(&\u0001\u0000\u0000\u0000()\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000"+
		"\u0000*+\u0003\u0004\u0002\u0000+,\u0005\u0001\u0000\u0000,-\u0006\u0000"+
		"\uffff\uffff\u0000-\u0001\u0001\u0000\u0000\u0000./\u0005\u0002\u0000"+
		"\u0000/8\u0006\u0001\uffff\uffff\u000001\u0005\u0003\u0000\u000019\u0006"+
		"\u0001\uffff\uffff\u000023\u0005\u0004\u0000\u000039\u0006\u0001\uffff"+
		"\uffff\u000045\u0005\u0005\u0000\u000059\u0006\u0001\uffff\uffff\u0000"+
		"67\u0005\u0006\u0000\u000079\u0006\u0001\uffff\uffff\u000080\u0001\u0000"+
		"\u0000\u000082\u0001\u0000\u0000\u000084\u0001\u0000\u0000\u000086\u0001"+
		"\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:;\u0005\u0018\u0000\u0000"+
		";A\u0006\u0001\uffff\uffff\u0000<=\u0005 \u0000\u0000=>\u0005\u0018\u0000"+
		"\u0000>@\u0006\u0001\uffff\uffff\u0000?<\u0001\u0000\u0000\u0000@C\u0001"+
		"\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000"+
		"BD\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000DE\u0005\u001f\u0000"+
		"\u0000EF\u0006\u0001\uffff\uffff\u0000F\u0003\u0001\u0000\u0000\u0000"+
		"GK\u0005\u0007\u0000\u0000HJ\u0003\u0006\u0003\u0000IH\u0001\u0000\u0000"+
		"\u0000JM\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000KL\u0001\u0000"+
		"\u0000\u0000LN\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000NO\u0005"+
		"\b\u0000\u0000O\u0005\u0001\u0000\u0000\u0000PW\u0003\b\u0004\u0000QW"+
		"\u0003\n\u0005\u0000RW\u0003\u0012\t\u0000SW\u0003\f\u0006\u0000TW\u0003"+
		"\u000e\u0007\u0000UW\u0003\u0010\b\u0000VP\u0001\u0000\u0000\u0000VQ\u0001"+
		"\u0000\u0000\u0000VR\u0001\u0000\u0000\u0000VS\u0001\u0000\u0000\u0000"+
		"VT\u0001\u0000\u0000\u0000VU\u0001\u0000\u0000\u0000W\u0007\u0001\u0000"+
		"\u0000\u0000XY\u0005\t\u0000\u0000YZ\u0005\n\u0000\u0000Z[\u0005\u0018"+
		"\u0000\u0000[\\\u0006\u0004\uffff\uffff\u0000\\]\u0005\u000b\u0000\u0000"+
		"]^\u0005\u001f\u0000\u0000^\t\u0001\u0000\u0000\u0000_`\u0005\f\u0000"+
		"\u0000`a\u0005\n\u0000\u0000ab\u0003\u001c\u000e\u0000bc\u0006\u0005\uffff"+
		"\uffff\u0000cd\u0001\u0000\u0000\u0000de\u0005\u000b\u0000\u0000ef\u0005"+
		"\u001f\u0000\u0000fg\u0006\u0005\uffff\uffff\u0000g\u000b\u0001\u0000"+
		"\u0000\u0000hi\u0005\r\u0000\u0000ij\u0006\u0006\uffff\uffff\u0000jk\u0005"+
		"\n\u0000\u0000kl\u0003\u0014\n\u0000lm\u0005\u000b\u0000\u0000mn\u0006"+
		"\u0006\uffff\uffff\u0000no\u0005\u000e\u0000\u0000op\u0003\u0004\u0002"+
		"\u0000pv\u0006\u0006\uffff\uffff\u0000qr\u0005\u000f\u0000\u0000rs\u0006"+
		"\u0006\uffff\uffff\u0000st\u0003\u0004\u0002\u0000tu\u0006\u0006\uffff"+
		"\uffff\u0000uw\u0001\u0000\u0000\u0000vq\u0001\u0000\u0000\u0000vw\u0001"+
		"\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000xy\u0006\u0006\uffff\uffff"+
		"\u0000y\r\u0001\u0000\u0000\u0000z{\u0005\u0010\u0000\u0000{|\u0006\u0007"+
		"\uffff\uffff\u0000|}\u0005\n\u0000\u0000}~\u0003\u0014\n\u0000~\u007f"+
		"\u0005\u000b\u0000\u0000\u007f\u0080\u0006\u0007\uffff\uffff\u0000\u0080"+
		"\u0081\u0003\u0004\u0002\u0000\u0081\u0082\u0006\u0007\uffff\uffff\u0000"+
		"\u0082\u000f\u0001\u0000\u0000\u0000\u0083\u0084\u0005\u0011\u0000\u0000"+
		"\u0084\u0085\u0006\b\uffff\uffff\u0000\u0085\u0086\u0003\u0004\u0002\u0000"+
		"\u0086\u0087\u0006\b\uffff\uffff\u0000\u0087\u0088\u0005\u0010\u0000\u0000"+
		"\u0088\u0089\u0005\n\u0000\u0000\u0089\u008a\u0003\u0014\n\u0000\u008a"+
		"\u008b\u0005\u000b\u0000\u0000\u008b\u008c\u0006\b\uffff\uffff\u0000\u008c"+
		"\u008d\u0005\u001f\u0000\u0000\u008d\u008e\u0006\b\uffff\uffff\u0000\u008e"+
		"\u0011\u0001\u0000\u0000\u0000\u008f\u0090\u0005\u0018\u0000\u0000\u0090"+
		"\u0091\u0006\t\uffff\uffff\u0000\u0091\u0092\u0005\u001d\u0000\u0000\u0092"+
		"\u0093\u0003\u0018\f\u0000\u0093\u0094\u0006\t\uffff\uffff\u0000\u0094"+
		"\u0095\u0005\u001f\u0000\u0000\u0095\u0096\u0006\t\uffff\uffff\u0000\u0096"+
		"\u0013\u0001\u0000\u0000\u0000\u0097\u009d\u0003\u0016\u000b\u0000\u0098"+
		"\u0099\u0005\u001c\u0000\u0000\u0099\u009a\u0006\n\uffff\uffff\u0000\u009a"+
		"\u009c\u0003\u0016\u000b\u0000\u009b\u0098\u0001\u0000\u0000\u0000\u009c"+
		"\u009f\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d"+
		"\u009e\u0001\u0000\u0000\u0000\u009e\u0015\u0001\u0000\u0000\u0000\u009f"+
		"\u009d\u0001\u0000\u0000\u0000\u00a0\u00a1\u0003\u0018\f\u0000\u00a1\u00a2"+
		"\u0005\u001b\u0000\u0000\u00a2\u00a3\u0006\u000b\uffff\uffff\u0000\u00a3"+
		"\u00a4\u0003\u0018\f\u0000\u00a4\u0017\u0001\u0000\u0000\u0000\u00a5\u00a6"+
		"\u0003\u001c\u000e\u0000\u00a6\u00a7\u0003\u001a\r\u0000\u00a7\u0019\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a9\u0007\u0000\u0000\u0000\u00a9\u00aa\u0006"+
		"\r\uffff\uffff\u0000\u00aa\u00ab\u0003\u001c\u000e\u0000\u00ab\u00ac\u0006"+
		"\r\uffff\uffff\u0000\u00ac\u00ae\u0001\u0000\u0000\u0000\u00ad\u00a8\u0001"+
		"\u0000\u0000\u0000\u00ae\u00b1\u0001\u0000\u0000\u0000\u00af\u00ad\u0001"+
		"\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u001b\u0001"+
		"\u0000\u0000\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b2\u00b3\u0003"+
		" \u0010\u0000\u00b3\u00b4\u0006\u000e\uffff\uffff\u0000\u00b4\u00b5\u0003"+
		"\u001e\u000f\u0000\u00b5\u001d\u0001\u0000\u0000\u0000\u00b6\u00b7\u0007"+
		"\u0001\u0000\u0000\u00b7\u00b8\u0006\u000f\uffff\uffff\u0000\u00b8\u00b9"+
		"\u0003 \u0010\u0000\u00b9\u00ba\u0006\u000f\uffff\uffff\u0000\u00ba\u00bc"+
		"\u0001\u0000\u0000\u0000\u00bb\u00b6\u0001\u0000\u0000\u0000\u00bc\u00bf"+
		"\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00bd\u00be"+
		"\u0001\u0000\u0000\u0000\u00be\u001f\u0001\u0000\u0000\u0000\u00bf\u00bd"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c1\u0005\u0018\u0000\u0000\u00c1\u00c9"+
		"\u0006\u0010\uffff\uffff\u0000\u00c2\u00c3\u0005\u0016\u0000\u0000\u00c3"+
		"\u00c9\u0006\u0010\uffff\uffff\u0000\u00c4\u00c5\u0005\u0019\u0000\u0000"+
		"\u00c5\u00c9\u0006\u0010\uffff\uffff\u0000\u00c6\u00c7\u0005\u0017\u0000"+
		"\u0000\u00c7\u00c9\u0006\u0010\uffff\uffff\u0000\u00c8\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c2\u0001\u0000\u0000\u0000\u00c8\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c9!\u0001\u0000\u0000"+
		"\u0000\n(8AKVv\u009d\u00af\u00bd\u00c8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}