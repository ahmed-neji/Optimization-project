package application;
import java.io.File;

import javax.xml.bind.ParseConversionEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.IOException;



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

import java.io.FileWriter;


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;

//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Page3  {
	public static int nb;
	public static TextField num_tache_t;
	static long c_length ;
	static long c_fileSize;
	static long c_outputSize ;
	static int c_pesNumber;
	static Cloudlet[] cloudlet = new Cloudlet[Déclaration.tas];
	static int ahmed=0;
     
	
public static void display(String title) throws TransformerException {
	
		
		
		// paramétre d'affichage 
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(500);
		window.setHeight(800);
		
		
		Label num_tache = new Label();
		num_tache.setText("Task n° ");
		
		
		 //num_tache_t = new TextField();
		
		
		Label Length = new Label();
		Length.setText("Length");
		
		Label f_size = new Label();
		f_size.setText("File Size");
		
		Label out_size = new Label();
		out_size.setText("Output Size ");
		
		Label f_pes = new Label();
		f_pes.setText("Pes_Number");
		
		Label temps = new Label();
		temps.setText("Time ");
		
		TextField lenght_t = new TextField();
		TextField size_t = new TextField();
		TextField out_size_t = new TextField();
		TextField pes_t = new TextField();
		TextField temps_t= new TextField();
		
		
		Label l_mach_t = new Label();
		l_mach_t.setText("List of executed ressources ");
		
		ListView<String> list_v = new ListView<String>();
		
		ComboBox<String> choix = new ComboBox<String>();
		choix.setPromptText("Choose Ressourses ");
		choix.setOnAction(e -> {
			list_v.getItems().add(choix.getSelectionModel().getSelectedItem());
			
		});
		
		
		CheckBox o_cloud = new CheckBox();
		o_cloud.setText("Only Cloud Ressource ");
		o_cloud.setOnAction(e -> {
			choix.getItems().clear();
			for (int k =0 ; k<Déclaration.nb_c;k++)
			{
				String n = Déclaration.list_1.get(k).getName();
				choix.getItems().add(n);
			}
		});
		
		CheckBox o_fog = new CheckBox();
		o_fog.setText("Only Fog Ressource ");
		o_fog.setOnAction(e -> {
			choix.getItems().clear();
			for (int k =0 ; k<Déclaration.nb_f;k++)
			{
				String n = Déclaration.list_2.get(k).getName();
				choix.getItems().add(n);
			}
		});
		
		CheckBox o_cf = new CheckBox();
		o_cf.setText("Fog And Cloud Ressource ");
		o_cf.setOnAction(e -> {
			choix.getItems().clear();
			for (int k =0 ; k<Déclaration.nb_f;k++)
			{
				String n = Déclaration.list_2.get(k).getName();
				choix.getItems().add(n);
			}
			for (int k =0 ; k<Déclaration.nb_c;k++)
			{
				String n = Déclaration.list_1.get(k).getName();
				choix.getItems().add(n);
			}
		});
		
		Label choix_l = new Label();
		choix_l.setText("Add A Ressouce ");
		
		
		
		
		
		
		Button next = new Button();
		next.setText("Next");
		next.setOnAction(e -> {
			if (ahmed>=Déclaration.tas)
			{
				System.out.println("les tache sont fini ");
				System.out.println("\n");
				for (int k =0;k<Déclaration.i;k++)
				{
					System.out.println("----------------------------------------------");
					for(int j=0;j<4;j++)
						{System.out.print(Déclaration.caracteristique_task[k][j]); System.out.print("      |      ");}
					System.out.println("/n");
				}
				System.out.println("les ressources exucutable de chaque tache : \n ");
				for (int k=0;k<Déclaration.tas;k++)
				{
					System.out.println("-------------------------------------------------- ");
	     			for(int x=0;x<Déclaration.list.size();x++)
	     				{System.out.print(Déclaration.task_run_ [k][x]); System.out.print("       |      ");}
				}
				Execute_Interface.display("Execute");
				window.close();
			}
			
			
			
			
			
			
			else
			{
				    	 
				    	
				int id_run = ahmed+1;
		    	int lenght_ = Integer.parseInt(lenght_t.getText());
	    		int fileSize_ = Integer.parseInt(size_t.getText());
	    		int outputSize_ = Integer.parseInt(out_size_t.getText());
	    		int nbOfPes_ = Integer.parseInt(pes_t.getText());
	    		Déclaration.caracteristique_task[ahmed][0] = lenght_;
	    		Déclaration.caracteristique_task[ahmed][1] = fileSize_;
	    		Déclaration.caracteristique_task[ahmed][2] = outputSize_;
	    		Déclaration.caracteristique_task[ahmed][3] = nbOfPes_;
	     		Déclaration.temps_execute[ahmed]=Integer.parseInt(temps_t.getText()); 
	    		
	     	// pour ecrire sur le fichier xml générer par le bpmn
	     		try {
		            String filepath = "monFichier.xml";
		            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse(filepath);

		            final Element racine = doc.getDocumentElement();
		    		System.out.println(racine.getNodeName());
		    		final NodeList racineNoeuds = racine.getChildNodes();

		    		 final Element tasks = (Element) ((Element) racineNoeuds).getElementsByTagName("Tasks").item(0);
		    		 final Element task = (Element) tasks.getElementsByTagName("Task").item(ahmed);
		    		 System.out.println(task.getAttribute("id"));
		    		 
		    		 
		    		 final Attr lenght = task.getAttributeNode("lenght"); 
		    		 lenght.setValue(lenght_t.getText());
		    		 
		    		 final Attr size = task.getAttributeNode("fileSize"); 
		    		 size.setValue(size_t.getText());
		    		 
		    		 
		    		 final Attr out_size_ = task.getAttributeNode("outputSize"); 
		    		 out_size_.setValue(out_size_t.getText());
		    		 
		    		 
		    		 final Attr nbpes = task.getAttributeNode("nbOfPes"); 
		    		 nbpes.setValue(pes_t.getText());
		    		 
		    		 final Attr time = task.getAttributeNode("Time"); 
		    		 time.setValue(temps_t.getText());
		    		 
		    		 
		    		 TransformerFactory transformerFactory = TransformerFactory
		                     .newInstance();
		             Transformer transformer = transformerFactory.newTransformer();
		             DOMSource source = new DOMSource(doc);
		             StreamResult result = new StreamResult(new File(filepath));
		             transformer.transform(source, result);

		             System.out.println("Done");


		 } catch (ParserConfigurationException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (SAXException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (IOException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     		
	     		
	     		
	     		
	     		
	     		
	     		
	     		
					c_length = lenght_;
					c_fileSize = fileSize_;
					c_outputSize = outputSize_;
					c_pesNumber = nbOfPes_;
					
					
					UtilizationModel utilizationModel = new UtilizationModelFull();

					cloudlet[ahmed] = new Cloudlet(id_run, c_length, c_pesNumber, c_fileSize, c_outputSize, utilizationModel, utilizationModel, utilizationModel);
					cloudlet[ahmed].setUserId(1);
					Déclaration.list_task.add(cloudlet[ahmed]);
					System.out.println("affichage  de task du tableau  = "+cloudlet[ahmed].getCloudletLength());
					System.out.println("nobre de task = "+Déclaration.list_task.size());
					System.out.println("longeur du cloudlet numéro "+ahmed+" = "  +Déclaration.list_task.get(ahmed).getCloudletLength());
	     		
						
			     		
			     		int j=list_v.getItems().size();
			     		//System.out.println("size de ressouce selectionnée = "+j);
			     		for (int k=0;k<j;k++) // k<j
			     		{
			     			for (int x=0;x<Déclaration.list.size();x++)
			     			{
			     				if(Déclaration.list.get(x).getName().equals(list_v.getItems().get(k)))
			     				{
			     					String tt = Déclaration.list.get(x).getType();
			     					//System.out.println("nom du ressource selectionnée "+tt);
			     					
			     					if(tt.equals("Fog"))
			     					{
			     						for (int z=0;z<Déclaration.nb_f;z++)
			     						{
			     							if(Déclaration.list_2.get(z).getName().equals(list_v.getItems().get(k)))
			     								Déclaration.task_run_[ahmed][Déclaration.list_2.get(z).getId()]=1;
			     							
			     						}
			     					}
			     					else
			     					{
			     						
				     						for (int z=0;z<Déclaration.nb_c;z++)
				     						{
				     							//System.out.println( "  varible 1 = "+Déclaration.i+"   variable 2 =   "+Déclaration.list_1.get(z).getId());
				     							if(Déclaration.list_1.get(z).getName().equals(list_v.getItems().get(k)))
				     								Déclaration.task_run_[ahmed][Déclaration.list_1.get(z).getId()]=1;
				     							
				     						}
				     					
			     					}
			     				}
			     			}
			     		}
			     		
			     		
			     		
			     		list_v.getItems().clear();
			     		choix.getItems().clear();
			     		
				    	lenght_t.clear();
			    	    size_t.clear();
			    		out_size_t.clear();
			    		pes_t.clear();
			    		temps_t.clear();
			 
			    		System.out.println(" \n ressource exucutable de tache n°"+(ahmed+1)+" :  \n");
						for (int cc=0;cc<Déclaration.list.size();cc++)
						{System.out.print(+Déclaration.task_run_[ahmed][cc]);System.out.print("         ");}
						if (ahmed<Déclaration.tas-1)
						ahmed++;
						else {
							ahmed=ahmed+2;
							window.close();
							Execute_Interface.display("Execute");
						}
							
			     		
			     		num_tache_t.setText(String.valueOf(ahmed+1));
			     		o_cf.setSelected(false);
			     		o_cloud.setSelected(false);
			     		o_fog.setSelected(false);
			     		
			     		
			     	
			     		
			    		
			  	
			}
		});
		
		
		
		
		
		
		Button pred = new Button();
		pred.setText("Previous");
		pred.setOnAction(e -> {
			
			ahmed--;
			if(ahmed>=0 && Déclaration.caracteristique_task[ahmed+1][0]==0)
			{
			num_tache_t.setText(String.valueOf(ahmed));
			lenght_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][0]));
			size_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][1]));
			out_size_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][2]));
			pes_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][3]));
			temps_t.setText(String.valueOf(Déclaration.temps_execute[ahmed]));
			list_v.getItems().clear();
			int nb=0;
			for(int k=0;k<Déclaration.nb_c;k++)
			{
				if(Déclaration.task_run_[ahmed][k]==1)
				{
					String tt=Déclaration.list_1.get(k).getName();
					list_v.getItems().add(nb,tt);
					nb++;
				}
			}
			for(int k=0;k<Déclaration.nb_f;k++)
			{
				if(Déclaration.task_run_[ahmed][k+Déclaration.nb_c]==1)
				{
					String tt=Déclaration.list_2.get(k).getName();
					list_v.getItems().add(nb,tt);
					nb++;
				}
			}
			
			
			}
			 if(ahmed>=0 && Déclaration.caracteristique_task[ahmed+1][0]>0)
			{
				num_tache_t.setText(String.valueOf(ahmed));
				lenght_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][0]));
				size_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][1]));
				out_size_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][2]));
				pes_t.setText(String.valueOf(Déclaration.caracteristique_task[ahmed][3]));
				temps_t.setText(String.valueOf(Déclaration.temps_execute[ahmed]));
				list_v.getItems().clear();
				int nb=0;
				for(int k=0;k<Déclaration.nb_c;k++)
				{
					if(Déclaration.task_run_[ahmed][k]==1)
					{
						String tt=Déclaration.list_1.get(k).getName();
						list_v.getItems().add(nb,tt);
						nb++;
					}
				}
				for(int k=0;k<Déclaration.nb_f;k++)
				{
					if(Déclaration.task_run_[ahmed][k+Déclaration.nb_c]==1)
					{
						String tt=Déclaration.list_2.get(k).getName();
						list_v.getItems().add(nb,tt);
						nb++;
					}
				}
				Déclaration.list_task.remove(ahmed+1);
				Déclaration.caracteristique_task[ahmed+1][0]=0;
				Déclaration.caracteristique_task[ahmed+1][1]=0;
				Déclaration.caracteristique_task[ahmed+1][2]=0;
				Déclaration.caracteristique_task[ahmed+1][3]=0;
				Déclaration.temps_execute[ahmed+1]=0;
				
				for(int k=0;k<Déclaration.nb_c;k++)
				{
					Déclaration.task_run_[ahmed+1][k]=0;
				}
				for(int k=0;k<Déclaration.nb_f;k++)
				{
					Déclaration.task_run_[ahmed+1][k+Déclaration.nb_c]=0;
				}
				
				
				
				try {
		            String filepath = "monFichier.xml";
		            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		            Document doc = docBuilder.parse(filepath);

		            final Element racine = doc.getDocumentElement();
		    		System.out.println(racine.getNodeName());
		    		final NodeList racineNoeuds = racine.getChildNodes();

		    		 final Element tasks = (Element) ((Element) racineNoeuds).getElementsByTagName("Tasks").item(0);
		    		 final Element task = (Element) tasks.getElementsByTagName("Task").item(ahmed+1);
		    		 System.out.println(task.getAttribute("id"));
		    		 
		    		 
		    		 final Attr lenght = task.getAttributeNode("lenght"); 
		    		 lenght.setValue("");
		    		 
		    		 final Attr size = task.getAttributeNode("fileSize"); 
		    		 size.setValue("");
		    		 
		    		 
		    		 final Attr out_size_ = task.getAttributeNode("outputSize"); 
		    		 out_size_.setValue("");
		    		 
		    		 
		    		 final Attr nbpes = task.getAttributeNode("nbOfPes"); 
		    		 nbpes.setValue("");
		    		 
		    		 final Attr time = task.getAttributeNode("Time"); 
		    		 time.setValue("");
		    		 
		    		 
		    		 TransformerFactory transformerFactory = TransformerFactory
		                     .newInstance();
		             Transformer transformer = transformerFactory.newTransformer();
		             DOMSource source = new DOMSource(doc);
		             StreamResult result = new StreamResult(new File(filepath));
		             transformer.transform(source, result);

		             System.out.println("Done");


		 } catch (ParserConfigurationException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (SAXException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (IOException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	     } catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     		
				
			}
			 if(ahmed<0)
			 {
				 Déclaration.list_task.remove(ahmed+1);
					Déclaration.caracteristique_task[ahmed+1][0]=0;
					Déclaration.caracteristique_task[ahmed+1][1]=0;
					Déclaration.caracteristique_task[ahmed+1][2]=0;
					Déclaration.caracteristique_task[ahmed+1][3]=0;
					Déclaration.temps_execute[ahmed+1]=0;
					
					for(int k=0;k<Déclaration.nb_c;k++)
					{
						Déclaration.task_run_[ahmed+1][k]=0;
					}
					for(int k=0;k<Déclaration.nb_f;k++)
					{
						Déclaration.task_run_[ahmed+1][k+Déclaration.nb_c]=0;
					}
					
					
					
					try {
			            String filepath = "monFichier.xml";
			            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			            Document doc = docBuilder.parse(filepath);

			            final Element racine = doc.getDocumentElement();
			    		System.out.println(racine.getNodeName());
			    		final NodeList racineNoeuds = racine.getChildNodes();

			    		 final Element tasks = (Element) ((Element) racineNoeuds).getElementsByTagName("Tasks").item(0);
			    		 final Element task = (Element) tasks.getElementsByTagName("Task").item(ahmed+1);
			    		 System.out.println(task.getAttribute("id"));
			    		 
			    		 
			    		 final Attr lenght = task.getAttributeNode("lenght"); 
			    		 lenght.setValue("");
			    		 
			    		 final Attr size = task.getAttributeNode("fileSize"); 
			    		 size.setValue("");
			    		 
			    		 
			    		 final Attr out_size_ = task.getAttributeNode("outputSize"); 
			    		 out_size_.setValue("");
			    		 
			    		 
			    		 final Attr nbpes = task.getAttributeNode("nbOfPes"); 
			    		 nbpes.setValue("");
			    		 
			    		 final Attr time = task.getAttributeNode("Time"); 
			    		 time.setValue("");
			    		 
			    		 
			    		 TransformerFactory transformerFactory = TransformerFactory
			                     .newInstance();
			             Transformer transformer = transformerFactory.newTransformer();
			             DOMSource source = new DOMSource(doc);
			             StreamResult result = new StreamResult(new File(filepath));
			             transformer.transform(source, result);

			             System.out.println("Done");


			 } catch (ParserConfigurationException e1) {
		         // TODO Auto-generated catch block
		         e1.printStackTrace();
		     } catch (SAXException e1) {
		         // TODO Auto-generated catch block
		         e1.printStackTrace();
		     } catch (IOException e1) {
		         // TODO Auto-generated catch block
		         e1.printStackTrace();
		     } catch (TransformerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     		lenght_t.clear();size_t.clear();out_size_t.clear();pes_t.clear();temps_t.clear();list_v.getItems().clear();
					window.close();
					Page2.display("Ressources","List of Ressources");
			 }
			
		});
		
		Button save = new Button();
		save.setText("save");
		
		
		
		
		
		
		
		
		
		
		
		
		
		//prosition des prop
		num_tache.setLayoutX(130);   num_tache_t.setLayoutX(200);    num_tache_t.setMaxWidth(50);    num_tache_t.setMinWidth(50);
		num_tache.setLayoutY(50);    num_tache_t.setLayoutY(45);
		
		Length.setLayoutX(50);    lenght_t.setLayoutX(150);   lenght_t.setMaxHeight(20);   lenght_t.setMaxWidth(200);
		Length.setLayoutY(100);   lenght_t.setLayoutY(95);    lenght_t.setMinWidth(200);
		
		f_size.setLayoutX(50);    size_t.setLayoutX(150);     size_t.setMaxHeight(20);     size_t.setMaxWidth(200);
        f_size.setLayoutY(150);   size_t.setLayoutY(145);     size_t.setMinWidth(200);
		
		out_size.setLayoutX(50);  out_size_t.setLayoutX(150);  out_size_t.setMaxHeight(20);  out_size_t.setMaxWidth(200);
		out_size.setLayoutY(200); out_size_t.setLayoutY(195);  out_size_t.setMinWidth(200);
		
		f_pes.setLayoutX(50);     pes_t.setLayoutX(150);       pes_t.setMaxHeight(20);       pes_t.setMaxHeight(200);
		f_pes.setLayoutY(250);    pes_t.setLayoutY(245);       pes_t.setMinWidth(200);
		
		temps.setLayoutX(50);     temps_t.setLayoutX(150);       temps_t.setMaxHeight(20);       temps_t.setMaxHeight(200);
		temps.setLayoutY(300);    temps_t.setLayoutY(295);       temps_t.setMinWidth(200);
		
		
		l_mach_t.setLayoutX(30);  l_mach_t.setLayoutY(370);
		
		list_v.setLayoutX(50);    list_v.setMaxHeight(200);     list_v.setMaxWidth(150);
		list_v.setLayoutY(410);   list_v.setMinHeight(200);     list_v.setMinWidth(150);
		
		o_fog.setLayoutX(250);
		o_fog.setLayoutY(420);
		
		o_cloud.setLayoutX(250);
		o_cloud.setLayoutY(450);
		
		o_cf.setLayoutX(250);
		o_cf.setLayoutY(480);
		
		choix_l.setLayoutX(280);   choix.setLayoutX(250);  choix.setMaxWidth(200);   choix.setMinWidth(200);
		choix_l.setLayoutY(530);   choix.setLayoutY(560);
		
		save.setLayoutX(80);       save.setMaxWidth(340);      save.setMinWidth(340);
		save.setLayoutY(630);
		
		pred.setLayoutX(30);       pred.setMaxWidth(100);      pred.setMinWidth(100);
		pred.setLayoutY(700);
		
		next.setLayoutX(370);       next.setMaxWidth(100);      next.setMinWidth(100);
		next.setLayoutY(700);
		
		
		
		
		Group g = new Group();
	    g.getChildren().addAll(num_tache,num_tache_t,Length,f_pes,f_size,out_size,lenght_t,size_t,out_size_t,pes_t,temps_t,
	    		
	    		l_mach_t,list_v,o_cf,o_cloud,o_fog,temps,choix,choix_l,pred,next);
	    Scene scene = new Scene(g);
		window.setScene(scene);
		window.show();

}
}
