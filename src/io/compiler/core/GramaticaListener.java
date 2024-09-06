// Generated from Gramatica.g4 by ANTLR 4.13.2
package io.compiler.core;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Stack;
	import io.compiler.types.*;
	import io.compiler.core.exceptions.*;
	import io.compiler.core.ast.*;
	import io.compiler.runtime.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GramaticaParser}.
 */
public interface GramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(GramaticaParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(GramaticaParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declara}.
	 * @param ctx the parse tree
	 */
	void enterDeclara(GramaticaParser.DeclaraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declara}.
	 * @param ctx the parse tree
	 */
	void exitDeclara(GramaticaParser.DeclaraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#bloco}.
	 * @param ctx the parse tree
	 */
	void enterBloco(GramaticaParser.BlocoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#bloco}.
	 * @param ctx the parse tree
	 */
	void exitBloco(GramaticaParser.BlocoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(GramaticaParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(GramaticaParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdLeitura}.
	 * @param ctx the parse tree
	 */
	void enterCmdLeitura(GramaticaParser.CmdLeituraContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdLeitura}.
	 * @param ctx the parse tree
	 */
	void exitCmdLeitura(GramaticaParser.CmdLeituraContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdEscrita}.
	 * @param ctx the parse tree
	 */
	void enterCmdEscrita(GramaticaParser.CmdEscritaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdEscrita}.
	 * @param ctx the parse tree
	 */
	void exitCmdEscrita(GramaticaParser.CmdEscritaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdIf}.
	 * @param ctx the parse tree
	 */
	void enterCmdIf(GramaticaParser.CmdIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdIf}.
	 * @param ctx the parse tree
	 */
	void exitCmdIf(GramaticaParser.CmdIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdWhile}.
	 * @param ctx the parse tree
	 */
	void enterCmdWhile(GramaticaParser.CmdWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdWhile}.
	 * @param ctx the parse tree
	 */
	void exitCmdWhile(GramaticaParser.CmdWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdDoWhile}.
	 * @param ctx the parse tree
	 */
	void enterCmdDoWhile(GramaticaParser.CmdDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdDoWhile}.
	 * @param ctx the parse tree
	 */
	void exitCmdDoWhile(GramaticaParser.CmdDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void enterCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void exitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(GramaticaParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(GramaticaParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#exprl}.
	 * @param ctx the parse tree
	 */
	void enterExprl(GramaticaParser.ExprlContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#exprl}.
	 * @param ctx the parse tree
	 */
	void exitExprl(GramaticaParser.ExprlContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#termo}.
	 * @param ctx the parse tree
	 */
	void enterTermo(GramaticaParser.TermoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#termo}.
	 * @param ctx the parse tree
	 */
	void exitTermo(GramaticaParser.TermoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#termol}.
	 * @param ctx the parse tree
	 */
	void enterTermol(GramaticaParser.TermolContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#termol}.
	 * @param ctx the parse tree
	 */
	void exitTermol(GramaticaParser.TermolContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#fator}.
	 * @param ctx the parse tree
	 */
	void enterFator(GramaticaParser.FatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#fator}.
	 * @param ctx the parse tree
	 */
	void exitFator(GramaticaParser.FatorContext ctx);
}