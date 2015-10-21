package edu.ufl.cise;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class SimpleStaxParser {
  public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    String fileName = "xml/employee.xml";
    fileName = "/Users/sanchitgupta/Downloads/pagecounts-20151001-000000";
    XMLStreamReader reader = factory.createXMLStreamReader(fileName,new FileInputStream(fileName));
    Stack<String> pathStack = new Stack<String>();		
    HashMap<String, Boolean> map = new HashMap<>();
    		
    while(reader.hasNext()){
      int event = reader.next();

      switch(event){
        case XMLStreamConstants.START_ELEMENT: 
        //System.out.println("START "  + reader.getName() + " " );
        	if(!pathStack.isEmpty()) {
        		StringBuilder temp = new StringBuilder();
        		temp.append(pathStack.peek() + "/" + reader.getName());
        		if(!map.containsKey(temp.toString())) {
        			map.put(temp.toString(), true);
        			System.out.println(temp.toString());
        		}
    			pathStack.push(temp.toString());
        	}
        	else{
        		StringBuilder temp = new StringBuilder();
        		temp.append( "/" );
        		temp.append(reader.getName());
        		pathStack.push(temp.toString());
        		map.put(temp.toString(), true);
    			System.out.println(temp.toString());
        	}
        
          break;

        case XMLStreamConstants.CHARACTERS:
          break;

        case XMLStreamConstants.END_ELEMENT:
        	pathStack.pop();
          break;

        case XMLStreamConstants.START_DOCUMENT:
        	break;
      }

    }

  }
}
