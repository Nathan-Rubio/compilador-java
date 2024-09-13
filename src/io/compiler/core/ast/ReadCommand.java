package io.compiler.core.ast;

import io.compiler.types.Types;
import io.compiler.types.Var;

public class ReadCommand extends Command {

	private Var var;

	public ReadCommand() {
		super();
	}

	public ReadCommand(Var var) {
		super();
		this.var = var;
	}

	public Var getVar() {
		return var;
	}

	public void setVar(Var var) {
		this.var = var;
	}

	@Override
	public String generateTarget() {
	    String inputCommand;

	    if (var.getType() == Types.INT) {
	        inputCommand = "_scTrx.nextInt();";
	    } else if (var.getType() == Types.FLOAT) {
	        inputCommand = "_scTrx.nextDouble();";  // Usar nextDouble() para FLOAT
	    } else if (var.getType() == Types.BOOLEAN) {
	        inputCommand = "_scTrx.nextBoolean();";  // Usar nextBoolean() para BOOLEAN
	    } else {
	        inputCommand = "_scTrx.nextLine();";  // Usar nextLine() para STRING e outros
	    }

	    return var.getId() + " = " + inputCommand + "\n";
	}

	
	@Override
	public String generateTargetPython() {
	    String inputCommand;

	    if (var.getType() == Types.INT) {
	        inputCommand = "int(input())";  // Converte a entrada para inteiro
	    } else if (var.getType() == Types.FLOAT) {
	        inputCommand = "float(input())";  // Converte a entrada para float
	    } else if (var.getType() == Types.BOOLEAN) {
	        inputCommand = "input().lower() in ['true', '1', 't', 'yes']";  // Verifica se a entrada é um valor booleano
	    } else {
	        inputCommand = "input()";  // Deixa a entrada como string
	    }

	    return "	" + var.getId() + " = " + inputCommand + "\n";
	}

}