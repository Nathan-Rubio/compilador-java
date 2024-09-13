package io.compiler.core.ast;

public class WriteCommand extends Command{
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WriteCommand(String content) {
		super();
		this.content = content;
	}

	public WriteCommand() {
		super();
	}
	
	@Override
	public String generateTarget() {
		// TODO Auto-generated method stub
		return "System.out.println("+content+");\n";
	}
	
	@Override
	public String generateTargetPython() {
		// TODO Auto-generated method stub
		return "	print("+content+")\n";
	}
}
