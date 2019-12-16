package application;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml_test {
	
	 public static void main(String argv[]) throws TransformerException {

	        try {
	            String filepath = "monFichier.xml";
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse(filepath);

	            final Element racine = doc.getDocumentElement();
	    		System.out.println(racine.getNodeName());
	    		final NodeList racineNoeuds = racine.getChildNodes();

	    		 final Element tasks = (Element) ((Element) racineNoeuds).getElementsByTagName("Tasks").item(0);
	    		 final Element task = (Element) tasks.getElementsByTagName("Task").item(0);
	    		 System.out.println(task.getAttribute("id"));
	    		 final Attr lenght = task.getAttributeNode("lenght"); 
	    		 lenght.setValue("27585586");
	    		 
	    		 
	    		 TransformerFactory transformerFactory = TransformerFactory
	                     .newInstance();
	             Transformer transformer = transformerFactory.newTransformer();
	             DOMSource source = new DOMSource(doc);
	             StreamResult result = new StreamResult(new File(filepath));
	             transformer.transform(source, result);

	             System.out.println("Done");


	 } catch (ParserConfigurationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } catch (SAXException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     }
}}