package edu.ufl.cise;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class GenericXMLParser {

	public static void main(String[] args) throws XMLStreamException,
			FileNotFoundException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		StaxParser parser = null;
		Map<String, Boolean> map = new HashMap<>();
		if (args.length == 0) {
			System.out.println("Error: Please provide necessary arguments");
			System.out.println("Usage: \n"
					+ "Arg1 : FilePath");
			System.out.println("Arg2 : Full path of repeat Element");
			System.out.println("Arg3 ... Argn : Space separated full path of nodes.");
			System.out.println("E.g : /Users/Xyz/abc.xml /root/node "
					+ "/root/node/element1" 
					+ "/root/node/element2");
			System.out.println("=============================================");
			//String fileName = "xml/invalid.xml";
			//XMLStreamReader reader = factory.createXMLStreamReader(fileName,
			//		new FileInputStream(fileName));
/*			map.put("/hotels/location@id", true);
			map.put("/hotels/location/country", true);
			map.put("/hotels/location/state/city/name", true);
			map.put("/hotels/location/state/city/hotel/name", true);
			map.put("/hotels/location/state/city/hotel/id", true);
			map.put("/hotels/location/state/city/hotel/price/currency", true);
			map.put("/hotels/location/state/city/hotel/price/value", true);
			String repeatElement = "/hotels/location/state/city/hotel";
			StaxParser parser = new StaxParser(reader,
					"/hotels/location/state/city/hotel",
					new ArrayList<String>(), map);
			String repeatElement = "/employees/employee";
			map.put("/employees/employee@id", true);
			map.put("/employees/employee/firstName", true);
			map.put("/employees/employee/lastName", true);
			map.put("/employees/employee/location", true);
			parser = new StaxParser(reader, repeatElement, new ArrayList<String>(), map);
			*/
		}
		else {
			String repeatElement  = null;
			String fileName = args[0];
			if(args.length < 2) {
				System.out.println("Error : Repeat element is mandatory.");
			}
			else  repeatElement = args[1];
			if(args.length < 3) {
				System.out.println("Error : Atleast one node should be present to be printed.");
			}
			for(int i=2; i<args.length; i++) {
				map.put(args[i], true);
			}
			XMLStreamReader reader = factory.createXMLStreamReader(fileName,
					new FileInputStream(fileName));
			parser = new StaxParser(reader, repeatElement, new ArrayList<String>(), map);
			parser.parse();
		}
	}

}
