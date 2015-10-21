package edu.ufl.cise;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ValidateXML {

	  public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
		    XMLInputFactory factory = XMLInputFactory.newInstance();
		    String fileName = "xml/employee.xml";
		    XMLStreamReader reader = factory.createXMLStreamReader(fileName,new FileInputStream(fileName));
		    		
		    while(reader.hasNext()){
		      reader.next();
		    }
	  }
	
	
}
