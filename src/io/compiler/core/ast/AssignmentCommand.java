package io.compiler.core.ast;

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
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append(var.getId() + " = " + expression + ";\n");
		return str.toString();
	}
}
