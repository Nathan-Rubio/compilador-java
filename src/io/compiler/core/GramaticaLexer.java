// Generated from Gramatica.g4 by ANTLR 4.13.2
package io.compiler.core;

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
		T__17=18, T__18=19, T__19=20, NUM=21, ID=22, STRING=23, OP=24, OP_REL=25, 
		OP_AT=26, WS=27, PV=28, VIRG=29;
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
			"T__17", "T__18", "T__19", "NUM", "ID", "STRING", "OP", "OP_REL", "OP_AT", 
			"WS", "PV", "VIRG"
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
		"\u0004\u0000\u001d\u00c5\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017"+
		"\u0002\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a"+
		"\u0002\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0004\u0014\u0092"+
		"\b\u0014\u000b\u0014\f\u0014\u0093\u0001\u0014\u0001\u0014\u0004\u0014"+
		"\u0098\b\u0014\u000b\u0014\f\u0014\u0099\u0003\u0014\u009c\b\u0014\u0001"+
		"\u0015\u0001\u0015\u0005\u0015\u00a0\b\u0015\n\u0015\f\u0015\u00a3\t\u0015"+
		"\u0001\u0016\u0001\u0016\u0005\u0016\u00a7\b\u0016\n\u0016\f\u0016\u00aa"+
		"\t\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0003\u0018\u00b9\b\u0018\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0001\u001c\u0001\u001c\u0001\u00a8\u0000\u001d\u0001\u0001\u0003"+
		"\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011"+
		"\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010"+
		"!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a"+
		"5\u001b7\u001c9\u001d\u0001\u0000\u0006\u0001\u000009\u0001\u0000az\u0003"+
		"\u000009AZaz\u0003\u0000*+--//\u0002\u0000<<>>\u0003\u0000\t\n\r\r  \u00cd"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000"+
		"\u00009\u0001\u0000\u0000\u0000\u0001;\u0001\u0000\u0000\u0000\u0003="+
		"\u0001\u0000\u0000\u0000\u0005E\u0001\u0000\u0000\u0000\u0007I\u0001\u0000"+
		"\u0000\u0000\tO\u0001\u0000\u0000\u0000\u000bV\u0001\u0000\u0000\u0000"+
		"\rX\u0001\u0000\u0000\u0000\u000fZ\u0001\u0000\u0000\u0000\u0011_\u0001"+
		"\u0000\u0000\u0000\u0013a\u0001\u0000\u0000\u0000\u0015c\u0001\u0000\u0000"+
		"\u0000\u0017k\u0001\u0000\u0000\u0000\u0019n\u0001\u0000\u0000\u0000\u001b"+
		"t\u0001\u0000\u0000\u0000\u001dz\u0001\u0000\u0000\u0000\u001f\u0083\u0001"+
		"\u0000\u0000\u0000!\u0088\u0001\u0000\u0000\u0000#\u008a\u0001\u0000\u0000"+
		"\u0000%\u008c\u0001\u0000\u0000\u0000\'\u008e\u0001\u0000\u0000\u0000"+
		")\u0091\u0001\u0000\u0000\u0000+\u009d\u0001\u0000\u0000\u0000-\u00a4"+
		"\u0001\u0000\u0000\u0000/\u00ad\u0001\u0000\u0000\u00001\u00b8\u0001\u0000"+
		"\u0000\u00003\u00ba\u0001\u0000\u0000\u00005\u00bd\u0001\u0000\u0000\u0000"+
		"7\u00c1\u0001\u0000\u0000\u00009\u00c3\u0001\u0000\u0000\u0000;<\u0005"+
		"$\u0000\u0000<\u0002\u0001\u0000\u0000\u0000=>\u0005d\u0000\u0000>?\u0005"+
		"e\u0000\u0000?@\u0005c\u0000\u0000@A\u0005l\u0000\u0000AB\u0005a\u0000"+
		"\u0000BC\u0005r\u0000\u0000CD\u0005e\u0000\u0000D\u0004\u0001\u0000\u0000"+
		"\u0000EF\u0005I\u0000\u0000FG\u0005N\u0000\u0000GH\u0005T\u0000\u0000"+
		"H\u0006\u0001\u0000\u0000\u0000IJ\u0005F\u0000\u0000JK\u0005L\u0000\u0000"+
		"KL\u0005O\u0000\u0000LM\u0005A\u0000\u0000MN\u0005T\u0000\u0000N\b\u0001"+
		"\u0000\u0000\u0000OP\u0005S\u0000\u0000PQ\u0005T\u0000\u0000QR\u0005R"+
		"\u0000\u0000RS\u0005I\u0000\u0000ST\u0005N\u0000\u0000TU\u0005G\u0000"+
		"\u0000U\n\u0001\u0000\u0000\u0000VW\u0005{\u0000\u0000W\f\u0001\u0000"+
		"\u0000\u0000XY\u0005}\u0000\u0000Y\u000e\u0001\u0000\u0000\u0000Z[\u0005"+
		"l\u0000\u0000[\\\u0005e\u0000\u0000\\]\u0005i\u0000\u0000]^\u0005a\u0000"+
		"\u0000^\u0010\u0001\u0000\u0000\u0000_`\u0005(\u0000\u0000`\u0012\u0001"+
		"\u0000\u0000\u0000ab\u0005)\u0000\u0000b\u0014\u0001\u0000\u0000\u0000"+
		"cd\u0005e\u0000\u0000de\u0005s\u0000\u0000ef\u0005c\u0000\u0000fg\u0005"+
		"r\u0000\u0000gh\u0005e\u0000\u0000hi\u0005v\u0000\u0000ij\u0005a\u0000"+
		"\u0000j\u0016\u0001\u0000\u0000\u0000kl\u0005s\u0000\u0000lm\u0005e\u0000"+
		"\u0000m\u0018\u0001\u0000\u0000\u0000no\u0005e\u0000\u0000op\u0005n\u0000"+
		"\u0000pq\u0005t\u0000\u0000qr\u0005a\u0000\u0000rs\u0005o\u0000\u0000"+
		"s\u001a\u0001\u0000\u0000\u0000tu\u0005s\u0000\u0000uv\u0005e\u0000\u0000"+
		"vw\u0005n\u0000\u0000wx\u0005a\u0000\u0000xy\u0005o\u0000\u0000y\u001c"+
		"\u0001\u0000\u0000\u0000z{\u0005e\u0000\u0000{|\u0005n\u0000\u0000|}\u0005"+
		"q\u0000\u0000}~\u0005u\u0000\u0000~\u007f\u0005a\u0000\u0000\u007f\u0080"+
		"\u0005n\u0000\u0000\u0080\u0081\u0005t\u0000\u0000\u0081\u0082\u0005o"+
		"\u0000\u0000\u0082\u001e\u0001\u0000\u0000\u0000\u0083\u0084\u0005f\u0000"+
		"\u0000\u0084\u0085\u0005a\u0000\u0000\u0085\u0086\u0005c\u0000\u0000\u0086"+
		"\u0087\u0005a\u0000\u0000\u0087 \u0001\u0000\u0000\u0000\u0088\u0089\u0005"+
		"+\u0000\u0000\u0089\"\u0001\u0000\u0000\u0000\u008a\u008b\u0005-\u0000"+
		"\u0000\u008b$\u0001\u0000\u0000\u0000\u008c\u008d\u0005*\u0000\u0000\u008d"+
		"&\u0001\u0000\u0000\u0000\u008e\u008f\u0005/\u0000\u0000\u008f(\u0001"+
		"\u0000\u0000\u0000\u0090\u0092\u0007\u0000\u0000\u0000\u0091\u0090\u0001"+
		"\u0000\u0000\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u0093\u0091\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u009b\u0001"+
		"\u0000\u0000\u0000\u0095\u0097\u0005.\u0000\u0000\u0096\u0098\u0007\u0000"+
		"\u0000\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000"+
		"\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000"+
		"\u0000\u0000\u009a\u009c\u0001\u0000\u0000\u0000\u009b\u0095\u0001\u0000"+
		"\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c*\u0001\u0000\u0000"+
		"\u0000\u009d\u00a1\u0007\u0001\u0000\u0000\u009e\u00a0\u0007\u0002\u0000"+
		"\u0000\u009f\u009e\u0001\u0000\u0000\u0000\u00a0\u00a3\u0001\u0000\u0000"+
		"\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001\u0000\u0000"+
		"\u0000\u00a2,\u0001\u0000\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a8\u0005\"\u0000\u0000\u00a5\u00a7\t\u0000\u0000\u0000\u00a6"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a9\u0001\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a9"+
		"\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0005\"\u0000\u0000\u00ac.\u0001\u0000\u0000\u0000\u00ad\u00ae"+
		"\u0007\u0003\u0000\u0000\u00ae0\u0001\u0000\u0000\u0000\u00af\u00b9\u0007"+
		"\u0004\u0000\u0000\u00b0\u00b1\u0005<\u0000\u0000\u00b1\u00b9\u0005=\u0000"+
		"\u0000\u00b2\u00b3\u0005>\u0000\u0000\u00b3\u00b9\u0005=\u0000\u0000\u00b4"+
		"\u00b5\u0005!\u0000\u0000\u00b5\u00b9\u0005=\u0000\u0000\u00b6\u00b7\u0005"+
		"=\u0000\u0000\u00b7\u00b9\u0005=\u0000\u0000\u00b8\u00af\u0001\u0000\u0000"+
		"\u0000\u00b8\u00b0\u0001\u0000\u0000\u0000\u00b8\u00b2\u0001\u0000\u0000"+
		"\u0000\u00b8\u00b4\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000"+
		"\u0000\u00b92\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005:\u0000\u0000\u00bb"+
		"\u00bc\u0005=\u0000\u0000\u00bc4\u0001\u0000\u0000\u0000\u00bd\u00be\u0007"+
		"\u0005\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00c0\u0006"+
		"\u001a\u0000\u0000\u00c06\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005;\u0000"+
		"\u0000\u00c28\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005,\u0000\u0000\u00c4"+
		":\u0001\u0000\u0000\u0000\b\u0000\u0093\u0099\u009b\u009f\u00a1\u00a8"+
		"\u00b8\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}