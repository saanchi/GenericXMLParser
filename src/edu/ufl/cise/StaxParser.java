package edu.ufl.cise;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxParser {

	private XMLStreamReader reader;
	private String repeatNode;
	private List<String> printOrder;
	private Map<String, Boolean> nodesRequired;
	private TreeNode rootNode;
	private Stack<TreeNode> stack;

	public StaxParser(XMLStreamReader reader, String repeatNode,
			List<String> printOrder, Map<String, Boolean> nodesRequired) {
		this.reader = reader;
		this.repeatNode = repeatNode;
		this.printOrder = printOrder;
		this.nodesRequired = nodesRequired;
		stack = new Stack<TreeNode>();
	}

	public void parse() throws XMLStreamException {
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				processStartElement();
				break;

			case XMLStreamConstants.CHARACTERS:
				processNodeValue();
				break;

			case XMLStreamConstants.END_ELEMENT:
				processEndElement();
				break;

			case XMLStreamConstants.START_DOCUMENT:
				break;
			}
		}
	}

	/**
	 * Does following things : 1.) Read the element's path, values and
	 * attributes and creates a node. 2.) Add the node to stack. 3.) Attaches
	 * the node to its parent, if its not root and needs to be printed.
	 */
	private void processStartElement() {
		String nodePath = getCurrentNodePath();
		TreeNode prevNode = null;
		List<AttributeNode> attributeList = getAttributeList(nodePath);
		if (rootNode == null) { // Create root node if it does not exist
			rootNode = new TreeNode(nodePath, attributeList);
			stack.push(rootNode);
		} else {
			prevNode = stack.peek();
			TreeNode currNode = new TreeNode(nodePath, attributeList);
			// add the node to parent node
			prevNode.addChild(currNode);
			stack.push(currNode);
		}
	}

	/**
	 * Returns a list of {@link AttributeNode}
	 * 
	 * @param nodePath
	 * @return
	 */
	private List<AttributeNode> getAttributeList(String nodePath) {
		List<AttributeNode> attributeList = new ArrayList<>();
		for (int i = 0; i < reader.getAttributeCount(); i++) {
			String attributeName = reader.getAttributeLocalName(i);
			String attributeValue = reader.getAttributeValue(i);
			StringBuilder attributePathBuilder = new StringBuilder();
			attributePathBuilder.append(nodePath);
			attributePathBuilder.append("@");
			attributePathBuilder.append(attributeName);
			String attributePath = attributePathBuilder.toString();
			AttributeNode attributeNode = new AttributeNode(
					attributePath, attributeValue);
			attributeList.add(attributeNode);
		}
		return attributeList;
	}

	/**
	 * Gets the top element of stack and attaches the value to the node.
	 */
	private void processNodeValue() {
		TreeNode node = stack.peek();
		node.setValue(reader.getText().trim());
	}

	private void processEndElement() {
		TreeNode currNode = stack.pop();
		if (repeatNode.equalsIgnoreCase(currNode.getPath())) {
			System.out.println(rootNode.toString(nodesRequired));
			currNode = null;
		}
	}

	private String getCurrentNodePath() {
		StringBuilder pathBuilder = new StringBuilder();
		TreeNode prevNode = null;
		pathBuilder.append("/");
		pathBuilder.append(reader.getName());
		if (!stack.isEmpty()) {
			prevNode = stack.peek();
			pathBuilder.insert(0, prevNode.getPath());
		}
		return pathBuilder.toString();
	}

	public XMLStreamReader getReader() {
		return reader;
	}

	public void setReader(XMLStreamReader reader) {
		this.reader = reader;
	}

	public String getRepeatNode() {
		return repeatNode;
	}

	public void setRepeatNode(String repeatNode) {
		this.repeatNode = repeatNode;
	}

	public List<String> getPrintOrder() {
		return printOrder;
	}

	public void setPrintOrder(List<String> printOrder) {
		this.printOrder = printOrder;
	}

}
