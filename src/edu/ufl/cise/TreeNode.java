package edu.ufl.cise;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeNode {

	private String path;
	private String value;
	private Map<String, TreeNode> childNodes;
	private List<AttributeNode> attributes;

	public TreeNode(String path, String value) {
		this.path = path;
		this.value = value;
	}

	public TreeNode(String path) {
		this.path = path;
	}

	public TreeNode(String path, List<AttributeNode> attributeList) {
		this.path = path;
		this.attributes = attributeList;
		childNodes = new HashMap<String, TreeNode>();
	}

	public void addChild(TreeNode currNode) {
		if (childNodes.containsKey(currNode.getPath())) {
			TreeNode child = childNodes.get(currNode.getPath());
			child = null;
			childNodes.put(currNode.getPath(), currNode);
		}
		childNodes.put(currNode.getPath(), currNode);
	}

	public String toString(Map<String, Boolean> nodesRequired) {
		StringBuilder sb = new StringBuilder();
		if (nodesRequired.containsKey(path)) {
			sb.append(value + "\t");
		}
		Iterator<AttributeNode> itr = attributes.iterator();
		while (itr.hasNext()) {
			AttributeNode attributeNode = itr.next();
			String attributePath = attributeNode.getPath();
			if (nodesRequired.containsKey(attributePath))
				sb.append(attributeNode.getValue() + "\t");
		}
		Iterator<String> itr1 = childNodes.keySet().iterator();
		while (itr1.hasNext()) {
			TreeNode node = childNodes.get(itr1.next());
			sb.append(node.toString(nodesRequired));
		}
		return sb.toString();
	}

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

	public Map<String, TreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(Map<String, TreeNode> childNodes) {
		this.childNodes = childNodes;
	}

	public List<AttributeNode> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeNode> attributes) {
		this.attributes = attributes;
	}

}
