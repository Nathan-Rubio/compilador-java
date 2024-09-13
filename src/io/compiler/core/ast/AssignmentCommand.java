package io.compiler.core.ast;

import io.compiler.types.Types;
import io.compiler.types.Var;

public class AssignmentCommand extends Command{
	
	private Var var;
	private String expression;
	
	public AssignmentCommand() {
		super();
	}

	public AssignmentCommand(Var var, String expression) {
		super();
		this.var = var;
		this.expression = expression;
	}

	public Var getVar() {
		return var;
	}

	public void setVar(Var var) {
		this.var = var;
	}
	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public String generateTarget() {
	    StringBuilder str = new StringBuilder();
	    String expr = expression;

	    // Substitui "verdadeiro" por "true" e "falso" por "false"
	    if (var.getType() == Types.BOOLEAN) {
	        expr = expr.replace("verdadeiro", "true").replace("falso", "false");
	    }

	    str.append(var.getId() + " = " + expr + ";\n");
	    return str.toString();
	}

	
	@Override
	public String generateTargetPython() {
	    StringBuilder str = new StringBuilder();
	    String expr = expression;

	    // Substitui "verdadeiro" por "True" e "falso" por "False"
	    if (var.getType() == Types.BOOLEAN) {
	        expr = expr.replace("verdadeiro", "True").replace("falso", "False");
	    }

	    str.append("	" + var.getId() + " = " + expr + "\n");
	    return str.toString();
	}

}
