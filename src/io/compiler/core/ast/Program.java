package io.compiler.core.ast;

import java.util.HashMap;
import java.util.List;

import io.compiler.types.*;

public class Program {
	private String name;
	private HashMap<String,Var> symbolTable;
	private List<Command> commandList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<String, Var> getSymbolTable() {
		return symbolTable;
	}
	public void setSymbolTable(HashMap<String, Var> symbolTable) {
		this.symbolTable = symbolTable;
	}
	public List<Command> getCommandList() {
		return commandList;
	}
	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}
	
	public String generateTarget() {
		StringBuilder str = new StringBuilder();
		str.append("import java.util.Scanner;\n");
		str.append("public class "+name+"{ \n");
		str.append("    public static void main(String args[]){ \n");
		str.append("    Scanner _scTrx = new Scanner(System.in);\n");
		for (String varId: symbolTable.keySet()) {
			Var var = symbolTable.get(varId);
			if (var.getType() == Types.INT) {
				str.append("    INT ");
			}
			else if (var.getType() == Types.FLOAT) {
				str.append("    FLOAT ");
			}
			else if (var.getType() == Types.BOOLEAN) {
				str.append("    BOOLEAN ");
			}
			else {
				str.append("    STRING ");
			}
			str.append(var.getId()+";\n");
		}
		
		for(Command cmd: commandList) {
			str.append(cmd.generateTarget());
		}
		str.append("    }");
		str.append("}");
		return str.toString();
	}
	
	public String generateTargetPython() {
	    StringBuilder str = new StringBuilder();
	    str.append("import sys\n"); // Importar módulos essenciais, se necessário
	    str.append("def main():\n");
	    
	    for (String varId: symbolTable.keySet()) {
	        Var var = symbolTable.get(varId);
	        if (var.getType() == Types.INT || var.getType() == Types.FLOAT) {
	            str.append("    " + var.getId() + " = 0\n"); // Inicializa com zero
	        }
	        else if (var.getType() == Types.BOOLEAN) {
	            str.append("    " + var.getId() + " = False\n"); // Inicializa como False
	        }
	        else {
	            str.append("    " + var.getId() + " = \"\"\n"); // Inicializa como string vazia
	        }
	    }
	    
	    for (Command cmd: commandList) {
	        str.append(cmd.generateTargetPython()); // Assumindo que você também implementará generateTargetPython em cada comando
	    }
	    
	    str.append("\nif __name__ == \"__main__\":\n");
	    str.append("    main()\n");
	    
	    return str.toString();
	}


}
