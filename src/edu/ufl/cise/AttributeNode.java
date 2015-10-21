package edu.ufl.cise;

public class AttributeNode {

	private String path;
	private String value;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public AttributeNode(String path, String value) {
		this.path = path;
		this.value = value;
	}
}
