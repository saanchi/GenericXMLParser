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
		if(args.length == 0) {
			System.out.println("DEMO");
		}
		String fileName = "xml/testXML2.xml";
		XMLStreamReader reader = factory.createXMLStreamReader(fileName,
				new FileInputStream(fileName));
		Map<String, Boolean> map = new HashMap<>();
		map.put("/hotels/location@id", true);
		map.put("/hotels/location/country", true);
		map.put("/hotels/location/state/city/name", true);
		map.put("/hotels/location/state/city/hotel/name", true);
		map.put("/hotels/location/state/city/hotel/id", true);
		map.put("/hotels/location/state/city/hotel/price/currency", true);
		map.put("/hotels/location/state/city/hotel/price/value", true);

		StaxParser parser = new StaxParser(reader,
				"/hotels/location/state/city/hotel", new ArrayList<String>(),
				map);
		parser.parse();
	}

}
