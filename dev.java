package application;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class dev {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
	
		File file  = new File("E:\\worksapce\\projet_fx_1\\src\\workflow\\process_2.bpmn"); // handler to your ZIP file
		File file2 = new File("E:\\worksapce\\projet_fx_1\\src\\workflow\\process_2.bpmn"); // destination dir of your file
		boolean success = file.renameTo(file2);
		System.out.println(success);
		final DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
		 final DocumentBuilder builder =  factory.newDocumentBuilder();
		 final Document document= builder.parse(file2);
		 //final Document document= builder.parse(new File(fichier));
		 final Element racine = document.getDocumentElement();
		System.out.println(racine.getNodeName());

}
}
