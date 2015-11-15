# GenericXMLParser
A fast StaX based Generic XML Parser which translate data from XML files which have different schemas into a specific csv format.

## Objective
Quickly parse an xml and write to a tab separated file nodes provided by the user. No need to create Java beans for every new XML needed.

## Format
java GenericXMLParser {repeatElement} {node1} {node2} {node3} ...
All the node, repeatElement follows the full path from the root of XML e.g in the XML file
```
<employees>
  <employee id="101">
    <firstName>Leonardo</firstName>
  </employee>
</employees>
```
full Path of node "firstName is "/employees/employee/firstName"
full path of attribute "id" is "/employees/employee@id"

## Implementaion Details
It combines the best of StaX approach and the DOM approach of parsing XML. Using DOM based approach we preserve the tree structure of XML but unlike DOM based approach it does not store the whole XML file in memory but the information of only skeleton XML. Using StaX approach we fill the skeleton tree with nodes as parser traveres the them. Using StaX gives parser the flexibiliy to parse Gb's of file with a very small memory foot print.

## Use Cases:

### Case 1
Condiser a simple XML file. I need to extract employee list in a text file in the following format.
```
<employees>
  <employee id="101">
    <firstName>Leonardo</firstName>
    <lastName>di ser Piero da Vinci</lastName>
    <location>Vinci, Republic of Florence</location>
  </employee>
  <employee id="102">
    <firstName>Galileo</firstName>
    <lastName>Galilei</lastName>
    <location>Grand Duchy of Tuscany, Italy</location>
  </employee>
  <employee id="103" newid="0000">
    <firstName>Euclid</firstName>
    <lastName>of Alexandria</lastName>
    <location>Alexandria, Hellenistic Egypt</location>
    </employee>
</employees>
```

I can provide above nodes as inputs to the GenericXMLParser. 
java GenericXMLParser /employees/employee /employees/employee@id /employees/employee/firstName /employees/employee/lastName /employees/employee/location

101	di ser Piero da Vinci	Vinci, Republic of Florence	Leonardo	
102	Galilei	Grand Duchy of Tuscany, Italy	Galileo	
103	of Alexandria	Alexandria, Hellenistic Egypt	Euclid

### Case 2
Above was a very simple case for parsing. Lets take a complicated case in which we need to repeat some information from top of tree with the repeated nodes. e.g. lets say some hotel booking company wants to provide a feed of all its hotels across all its states and city. The XML will be organized with country, state and city information on their respective levels and hotel level information at the lowest level. To serialize this information in a text file with every hotel having not only its hotel information but also top level information such as city, country etc we can simply parse like :

java GenericXMLParser "/hotels/location/state/city/hotel" /hotels/location@id /hotels/location/country /hotels/location/state/city/name /hotels/location/state/city/hotel/name /hotels/location/state/city/hotel/id /hotels/location/state/city/hotel/price/currency /hotels/location/state/city/hotel/price/currency/value

Consider the XML file.
```
<hotels>
	<location id="111">
		<country>India</country>
		<state>
			<name>KA</name>
			<city>
				<name>Bangalore</name>
				<hotel>
					<name>hotel11</name>
					<id>11</id>
					<price>
						<currency>INR</currency>
						<value>1011</value>
					</price>
				</hotel>
				<hotel>
					<name>hotel12</name>
					<id>12</id>
					<price>
						<currency>INR</currency>
						<value>1012</value>
					</price>
				</hotel>
			</city>
			<city>
				<name>Mumbai</name>
				<hotel>
					<name>hotel21</name>
					<id>21</id>
					<price>
						<currency>INR</currency>
						<value>1021</value>
					</price>
				</hotel>
				<hotel>
					<name>hotel22</name>
					<id>22</id>
					<price>
						<currency>INR</currency>
						<value>1022</value>
					</price>
				</hotel>
			</city>
		</state>
	</location>
</hotels>
```
Output will be :
111	India	Bangalore	INR	hotel11	11	
111	India	Bangalore	INR	hotel12	12	
111	India	Mumbai	INR	hotel21	21	
111	India	Mumbai	INR	hotel22	22


## Improvements
Take order of input from User to print in the user provided input.
