package io.compiler.types;

public enum Types {
	INT(1),
	FLOAT(2),
	STRING(3),
	BOOLEAN(4);
	
	private int value;
	
	private Types(int valueNumber) {
		this.value = valueNumber;
	}
	
	public Integer getValue() {
		return this.value;
	}
}
