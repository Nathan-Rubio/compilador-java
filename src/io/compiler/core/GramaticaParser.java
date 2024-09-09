// Generated from Gramatica.g4 by ANTLR 4.13.2
package io.compiler.core;

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
		T__17=18, T__18=19, T__19=20, NUM=21, ID=22, STRING=23, OP=24, OP_REL=25, 
		OP_AT=26, WS=27, PV=28, VIRG=29;
	public static final int
		RULE_prog = 0, RULE_declara = 1, RULE_bloco = 2, RULE_cmd = 3, RULE_cmdLeitura = 4, 
		RULE_cmdEscrita = 5, RULE_cmdIf = 6, RULE_cmdWhile = 7, RULE_cmdDoWhile = 8, 
		RULE_cmdAtribuicao = 9, RULE_expr = 10, RULE_exprl = 11, RULE_termo = 12, 
		RULE_termol = 13, RULE_fator = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "declara", "bloco", "cmd", "cmdLeitura", "cmdEscrita", "cmdIf", 
			"cmdWhile", "cmdDoWhile", "cmdAtribuicao", "expr", "exprl", "termo", 
			"termol", "fator"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'$'", "'declare'", "'INT'", "'FLOAT'", "'STRING'", "'{'", "'}'", 
			"'leia'", "'('", "')'", "'escreva'", "'se'", "'entao'", "'senao'", "'enquanto'", 
			"'faca'", "'+'", "'-'", "'*'", "'/'", null, null, null, null, null, "':='", 
			null, "';'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "NUM", "ID", "STRING", 
			"OP", "OP_REL", "OP_AT", "WS", "PV", "VIRG"
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
			setState(30);
			match(T__0);
			setState(31);
			match(ID);
			 program.setName(_input.LT(-1).getText()); 
							 	stack.push(new ArrayList<Command>());	
						    
			setState(34); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(33);
				declara();
				}
				}
				setState(36); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
			setState(38);
			bloco();
			setState(39);
			match(T__0);
			 
					  		program.setSymbolTable(symbolTable); 
					  		program.setCommandList(stack.pop());
					 	
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
			setState(42);
			match(T__1);
			 currentDecl.clear(); 
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				{
				setState(44);
				match(T__2);
				currentType = Types.INT;
				}
				break;
			case T__3:
				{
				setState(46);
				match(T__3);
				currentType = Types.FLOAT;
				}
				break;
			case T__4:
				{
				setState(48);
				match(T__4);
				currentType = Types.STRING;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(52);
			match(ID);
			 currentDecl.add(new Var(_input.LT(-1).getText())); 
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VIRG) {
				{
				{
				setState(54);
				match(VIRG);
				setState(55);
				match(ID);
				 currentDecl.add(new Var(_input.LT(-1).getText())); 
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
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
			setState(65);
			match(T__5);
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4299008L) != 0)) {
				{
				{
				setState(66);
				cmd();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
			match(T__6);
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
			setState(80);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				cmdLeitura();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				cmdEscrita();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(76);
				cmdAtribuicao();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 4);
				{
				setState(77);
				cmdIf();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 5);
				{
				setState(78);
				cmdWhile();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 6);
				{
				setState(79);
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
			setState(82);
			match(T__7);
			setState(83);
			match(T__8);
			setState(84);
			match(ID);
			 if (!isDeclared(_input.LT(-1).getText())) {
							     	throw new GramaticaSemanticException("Undeclared Variable: "+_input.LT(-1).getText());
							  	}
							  	 symbolTable.get(_input.LT(-1).getText()).setInitialized(true);
							  	 Command cmdRead = new ReadCommand(symbolTable.get(_input.LT(-1).getText()));
							  	 stack.peek().add(cmdRead);						
							   
			setState(86);
			match(T__9);
			setState(87);
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
			setState(89);
			match(T__10);
			setState(90);
			match(T__8);
			{
			setState(91);
			termo();
			 
										Command cmdWrite = new WriteCommand(_input.LT(-1).getText()); 
									  	stack.peek().add(cmdWrite);
									
			}
			setState(94);
			match(T__9);
			setState(95);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP_REL() { return getToken(GramaticaParser.OP_REL, 0); }
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
			setState(98);
			match(T__11);
			 
						stack.push(new ArrayList<Command>()); 
						strExpr = "";
						currentIfCommand = new IfCommand(); 
					
			setState(100);
			match(T__8);
			setState(101);
			expr();
			setState(102);
			match(OP_REL);
			 strExpr += _input.LT(-1).getText(); 
			setState(104);
			expr();
			setState(105);
			match(T__9);
			 currentIfCommand.setExpression(strExpr); 
			setState(107);
			match(T__12);
			setState(108);
			bloco();
			 currentIfCommand.setTrueList(stack.pop()); 
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(110);
				match(T__13);
				 stack.push(new ArrayList<Command>()); 
				setState(112);
				bloco();
				 currentIfCommand.setFalseList(stack.pop()); 
				}
			}

			 stack.peek().add(currentIfCommand); 
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP_REL() { return getToken(GramaticaParser.OP_REL, 0); }
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
			setState(119);
			match(T__14);

						 	stack.push(new ArrayList<Command>());
						 	strExpr = "";
						 	currentWhileCommand = new WhileCommand();
						 
			setState(121);
			match(T__8);
			setState(122);
			expr();
			setState(123);
			match(OP_REL);
			 strExpr += _input.LT(-1).getText(); 
			setState(125);
			expr();
			setState(126);
			match(T__9);
			 currentWhileCommand.setExpression(strExpr); 
			setState(128);
			bloco();

						 	currentWhileCommand.setCommandList(stack.pop());
						 	stack.peek().add(currentWhileCommand);
						 
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OP_REL() { return getToken(GramaticaParser.OP_REL, 0); }
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
			match(T__15);

						 	stack.push(new ArrayList<Command>());
						 	strExpr = "";
						 	currentDoWhileCommand = new DoWhileCommand();
						 
			setState(133);
			bloco();
			 currentDoWhileCommand.setCommandList(stack.pop()); 
			setState(135);
			match(T__14);
			setState(136);
			match(T__8);
			setState(137);
			expr();
			setState(138);
			match(OP_REL);
			 strExpr += _input.LT(-1).getText(); 
			setState(140);
			expr();
			setState(141);
			match(T__9);
			 currentDoWhileCommand.setExpression(strExpr); 
			setState(143);
			match(PV);
			 stack.peek().add(currentDoWhileCommand); 
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
			setState(146);
			match(ID);
			 
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
							   
			setState(148);
			match(OP_AT);
			setState(149);
			expr();
			 currentAssignmentCommand.setExpression(strExpr); 
			setState(151);
			match(PV);

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
		enterRule(_localctx, 20, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			termo();
			setState(155);
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
		enterRule(_localctx, 22, RULE_exprl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16 || _la==T__17) {
				{
				{
				setState(157);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
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
							
				setState(159);
				termo();

								AbstractExpression topo = abstractStack.pop(); // desempilhei o termo
								System.out.println("Topo: " + topo);
					         	BinaryExpression root = (BinaryExpression) abstractStack.pop(); // preciso do componente binário
					         	System.out.println("Root: " + root);
					         	root.setRight(topo);
					         	abstractStack.push(root);
							
				}
				}
				setState(166);
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
		enterRule(_localctx, 24, RULE_termo);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			fator();
			 strExpr += _input.LT(-1).getText(); 
			setState(169);
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
		enterRule(_localctx, 26, RULE_termol);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18 || _la==T__19) {
				{
				{
				setState(171);
				_la = _input.LA(1);
				if ( !(_la==T__18 || _la==T__19) ) {
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
							
				setState(173);
				fator();
				 
								strExpr += _input.LT(-1).getText(); 
								bin.setRight(abstractStack.pop());
								abstractStack.push(bin);
								System.out.println("DEBUG - :" + bin.toJson());
							
				}
				}
				setState(180);
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
		enterRule(_localctx, 28, RULE_fator);
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(181);
				match(ID);

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
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				match(NUM);
				  
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
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(185);
				match(STRING);

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
		"\u0004\u0001\u001d\u00be\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0004\u0000#\b\u0000\u000b\u0000\f\u0000"+
		"$\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u00013\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0005\u0001:\b\u0001\n\u0001\f\u0001=\t\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0005\u0002D\b\u0002"+
		"\n\u0002\f\u0002G\t\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003Q\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006t\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b"+
		"\u00a3\b\u000b\n\u000b\f\u000b\u00a6\t\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00b1\b\r\n\r\f\r\u00b4"+
		"\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0003\u000e\u00bc\b\u000e\u0001\u000e\u0000\u0000\u000f\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000"+
		"\u0002\u0001\u0000\u0011\u0012\u0001\u0000\u0013\u0014\u00bd\u0000\u001e"+
		"\u0001\u0000\u0000\u0000\u0002*\u0001\u0000\u0000\u0000\u0004A\u0001\u0000"+
		"\u0000\u0000\u0006P\u0001\u0000\u0000\u0000\bR\u0001\u0000\u0000\u0000"+
		"\nY\u0001\u0000\u0000\u0000\fb\u0001\u0000\u0000\u0000\u000ew\u0001\u0000"+
		"\u0000\u0000\u0010\u0083\u0001\u0000\u0000\u0000\u0012\u0092\u0001\u0000"+
		"\u0000\u0000\u0014\u009a\u0001\u0000\u0000\u0000\u0016\u00a4\u0001\u0000"+
		"\u0000\u0000\u0018\u00a7\u0001\u0000\u0000\u0000\u001a\u00b2\u0001\u0000"+
		"\u0000\u0000\u001c\u00bb\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0001"+
		"\u0000\u0000\u001f \u0005\u0016\u0000\u0000 \"\u0006\u0000\uffff\uffff"+
		"\u0000!#\u0003\u0002\u0001\u0000\"!\u0001\u0000\u0000\u0000#$\u0001\u0000"+
		"\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0001"+
		"\u0000\u0000\u0000&\'\u0003\u0004\u0002\u0000\'(\u0005\u0001\u0000\u0000"+
		"()\u0006\u0000\uffff\uffff\u0000)\u0001\u0001\u0000\u0000\u0000*+\u0005"+
		"\u0002\u0000\u0000+2\u0006\u0001\uffff\uffff\u0000,-\u0005\u0003\u0000"+
		"\u0000-3\u0006\u0001\uffff\uffff\u0000./\u0005\u0004\u0000\u0000/3\u0006"+
		"\u0001\uffff\uffff\u000001\u0005\u0005\u0000\u000013\u0006\u0001\uffff"+
		"\uffff\u00002,\u0001\u0000\u0000\u00002.\u0001\u0000\u0000\u000020\u0001"+
		"\u0000\u0000\u000034\u0001\u0000\u0000\u000045\u0005\u0016\u0000\u0000"+
		"5;\u0006\u0001\uffff\uffff\u000067\u0005\u001d\u0000\u000078\u0005\u0016"+
		"\u0000\u00008:\u0006\u0001\uffff\uffff\u000096\u0001\u0000\u0000\u0000"+
		":=\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000"+
		"\u0000<>\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000>?\u0005\u001c"+
		"\u0000\u0000?@\u0006\u0001\uffff\uffff\u0000@\u0003\u0001\u0000\u0000"+
		"\u0000AE\u0005\u0006\u0000\u0000BD\u0003\u0006\u0003\u0000CB\u0001\u0000"+
		"\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001"+
		"\u0000\u0000\u0000FH\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000"+
		"HI\u0005\u0007\u0000\u0000I\u0005\u0001\u0000\u0000\u0000JQ\u0003\b\u0004"+
		"\u0000KQ\u0003\n\u0005\u0000LQ\u0003\u0012\t\u0000MQ\u0003\f\u0006\u0000"+
		"NQ\u0003\u000e\u0007\u0000OQ\u0003\u0010\b\u0000PJ\u0001\u0000\u0000\u0000"+
		"PK\u0001\u0000\u0000\u0000PL\u0001\u0000\u0000\u0000PM\u0001\u0000\u0000"+
		"\u0000PN\u0001\u0000\u0000\u0000PO\u0001\u0000\u0000\u0000Q\u0007\u0001"+
		"\u0000\u0000\u0000RS\u0005\b\u0000\u0000ST\u0005\t\u0000\u0000TU\u0005"+
		"\u0016\u0000\u0000UV\u0006\u0004\uffff\uffff\u0000VW\u0005\n\u0000\u0000"+
		"WX\u0005\u001c\u0000\u0000X\t\u0001\u0000\u0000\u0000YZ\u0005\u000b\u0000"+
		"\u0000Z[\u0005\t\u0000\u0000[\\\u0003\u0018\f\u0000\\]\u0006\u0005\uffff"+
		"\uffff\u0000]^\u0001\u0000\u0000\u0000^_\u0005\n\u0000\u0000_`\u0005\u001c"+
		"\u0000\u0000`a\u0006\u0005\uffff\uffff\u0000a\u000b\u0001\u0000\u0000"+
		"\u0000bc\u0005\f\u0000\u0000cd\u0006\u0006\uffff\uffff\u0000de\u0005\t"+
		"\u0000\u0000ef\u0003\u0014\n\u0000fg\u0005\u0019\u0000\u0000gh\u0006\u0006"+
		"\uffff\uffff\u0000hi\u0003\u0014\n\u0000ij\u0005\n\u0000\u0000jk\u0006"+
		"\u0006\uffff\uffff\u0000kl\u0005\r\u0000\u0000lm\u0003\u0004\u0002\u0000"+
		"ms\u0006\u0006\uffff\uffff\u0000no\u0005\u000e\u0000\u0000op\u0006\u0006"+
		"\uffff\uffff\u0000pq\u0003\u0004\u0002\u0000qr\u0006\u0006\uffff\uffff"+
		"\u0000rt\u0001\u0000\u0000\u0000sn\u0001\u0000\u0000\u0000st\u0001\u0000"+
		"\u0000\u0000tu\u0001\u0000\u0000\u0000uv\u0006\u0006\uffff\uffff\u0000"+
		"v\r\u0001\u0000\u0000\u0000wx\u0005\u000f\u0000\u0000xy\u0006\u0007\uffff"+
		"\uffff\u0000yz\u0005\t\u0000\u0000z{\u0003\u0014\n\u0000{|\u0005\u0019"+
		"\u0000\u0000|}\u0006\u0007\uffff\uffff\u0000}~\u0003\u0014\n\u0000~\u007f"+
		"\u0005\n\u0000\u0000\u007f\u0080\u0006\u0007\uffff\uffff\u0000\u0080\u0081"+
		"\u0003\u0004\u0002\u0000\u0081\u0082\u0006\u0007\uffff\uffff\u0000\u0082"+
		"\u000f\u0001\u0000\u0000\u0000\u0083\u0084\u0005\u0010\u0000\u0000\u0084"+
		"\u0085\u0006\b\uffff\uffff\u0000\u0085\u0086\u0003\u0004\u0002\u0000\u0086"+
		"\u0087\u0006\b\uffff\uffff\u0000\u0087\u0088\u0005\u000f\u0000\u0000\u0088"+
		"\u0089\u0005\t\u0000\u0000\u0089\u008a\u0003\u0014\n\u0000\u008a\u008b"+
		"\u0005\u0019\u0000\u0000\u008b\u008c\u0006\b\uffff\uffff\u0000\u008c\u008d"+
		"\u0003\u0014\n\u0000\u008d\u008e\u0005\n\u0000\u0000\u008e\u008f\u0006"+
		"\b\uffff\uffff\u0000\u008f\u0090\u0005\u001c\u0000\u0000\u0090\u0091\u0006"+
		"\b\uffff\uffff\u0000\u0091\u0011\u0001\u0000\u0000\u0000\u0092\u0093\u0005"+
		"\u0016\u0000\u0000\u0093\u0094\u0006\t\uffff\uffff\u0000\u0094\u0095\u0005"+
		"\u001a\u0000\u0000\u0095\u0096\u0003\u0014\n\u0000\u0096\u0097\u0006\t"+
		"\uffff\uffff\u0000\u0097\u0098\u0005\u001c\u0000\u0000\u0098\u0099\u0006"+
		"\t\uffff\uffff\u0000\u0099\u0013\u0001\u0000\u0000\u0000\u009a\u009b\u0003"+
		"\u0018\f\u0000\u009b\u009c\u0003\u0016\u000b\u0000\u009c\u0015\u0001\u0000"+
		"\u0000\u0000\u009d\u009e\u0007\u0000\u0000\u0000\u009e\u009f\u0006\u000b"+
		"\uffff\uffff\u0000\u009f\u00a0\u0003\u0018\f\u0000\u00a0\u00a1\u0006\u000b"+
		"\uffff\uffff\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u009d\u0001"+
		"\u0000\u0000\u0000\u00a3\u00a6\u0001\u0000\u0000\u0000\u00a4\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a5\u0001\u0000\u0000\u0000\u00a5\u0017\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a7\u00a8\u0003"+
		"\u001c\u000e\u0000\u00a8\u00a9\u0006\f\uffff\uffff\u0000\u00a9\u00aa\u0003"+
		"\u001a\r\u0000\u00aa\u0019\u0001\u0000\u0000\u0000\u00ab\u00ac\u0007\u0001"+
		"\u0000\u0000\u00ac\u00ad\u0006\r\uffff\uffff\u0000\u00ad\u00ae\u0003\u001c"+
		"\u000e\u0000\u00ae\u00af\u0006\r\uffff\uffff\u0000\u00af\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b0\u00ab\u0001\u0000\u0000\u0000\u00b1\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b3\u0001\u0000"+
		"\u0000\u0000\u00b3\u001b\u0001\u0000\u0000\u0000\u00b4\u00b2\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b6\u0005\u0016\u0000\u0000\u00b6\u00bc\u0006\u000e"+
		"\uffff\uffff\u0000\u00b7\u00b8\u0005\u0015\u0000\u0000\u00b8\u00bc\u0006"+
		"\u000e\uffff\uffff\u0000\u00b9\u00ba\u0005\u0017\u0000\u0000\u00ba\u00bc"+
		"\u0006\u000e\uffff\uffff\u0000\u00bb\u00b5\u0001\u0000\u0000\u0000\u00bb"+
		"\u00b7\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bc"+
		"\u001d\u0001\u0000\u0000\u0000\t$2;EPs\u00a4\u00b2\u00bb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}