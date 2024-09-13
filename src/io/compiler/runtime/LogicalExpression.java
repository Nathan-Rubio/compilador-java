package io.compiler.runtime;

public class LogicalExpression extends AbstractExpression{
	private String operation;
	private AbstractExpression left;
	private AbstractExpression right;
	
	public LogicalExpression(String operation, AbstractExpression left, AbstractExpression right) {
		super();
		this.operation = operation;
		this.left = left;
		this.right = right;
	}
	
	public LogicalExpression(String operation) {
		super();
		this.operation = operation;
	}
	
	public LogicalExpression() {
		super();
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public AbstractExpression getLeft() {
		return left;
	}
	
	public void setLeft(AbstractExpression left) {
		this.left = left;
	}
	
	public AbstractExpression getRight() {
		return right;
	}
	
	public void setRight(AbstractExpression right) {
		this.right = right;
	}
	
	@Override
    public double evaluate() {
        boolean leftVal = (left.evaluate() != 0);
        boolean rightVal = (right.evaluate() != 0);
        switch (operation) {
            case "&&":
                return leftVal && rightVal ? 1 : 0;
            case "||":
                return leftVal || rightVal ? 1 : 0;
            default:
                throw new UnsupportedOperationException("Unknown logical operation " + operation);
        }
    }
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return "{ \"operation\": \""+this.operation+"\"," +
				   "\"left\": "+left.toJson()+","+
		           "\"right\": "+right.toJson() +
		           "}";
	}
}
