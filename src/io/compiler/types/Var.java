package io.compiler.types;

public class Var {
	private String id;
	private boolean initialized;
	private Types type;
	private String value;  // Adiciona o campo para armazenar o valor
	
	public Var(String id, Types type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public Var(String id) {
		super();
		this.id = id;
	}
	
	public Var() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	// Adiciona os métodos get e set para o valor
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Var [id=" + id + ", type=" + type + ", initialized=" + initialized + ", value=" + value + "]";
	}
}
