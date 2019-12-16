package application;


import application.Déclaration;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import java.io.File;
//import java.io.IOException;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;

//import java.awt.Window;
//import java.sql.Savepoint;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.function.DoubleToLongFunction;
//import application.*;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.MyVm;
import org.hana.pso.Fog;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Page2 {
	
	
	public static void display(String title, String messege ) {
		
		
		MyVm[] vm =new MyVm[10];
		//LinkedList<MyVm> list_1 = new LinkedList<MyVm>();
		Fog[] fd = new Fog[10];
		//LinkedList<Fog> list_2 = new LinkedList<Fog>();
		Machines[] ms= new Machines[20];
		//LinkedList<Machines> list = new LinkedList<Machines>();
		
		
		
		
		// paramétre d'affichage 
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(500);
		window.setHeight(800);
		
		Label label = new Label();
		label.setText(messege);
		
		ListView<String> list_vm=new ListView<String>();
		
		
		
		// propriété des machines
		Label l_type = new Label();   ComboBox<String> type = new ComboBox<String>();                     
		l_type.setText("Type : ");    type.getItems().addAll("Cloud","Fog"); type.setPromptText("choose your machine type" );
		
		Label l_name = new Label();   TextField name = new TextField();
		l_name.setText("Name : ");    
		
		Label l_size = new Label();   TextField size = new TextField();
		l_size.setText("Size : ");
		
		Label l_Ram = new Label();    TextField ram = new TextField();
		l_Ram.setText("Ram : ");
		
		Label l_mips = new Label();    TextField mips = new TextField();
		l_mips.setText("MIPS : ");
		
		Label l_bw = new Label();      TextField bw = new TextField();
		l_bw.setText("BandWidth  : ");
		
		Label l_pesnb = new Label();   TextField pesNb = new TextField();
		l_pesnb.setText("PesNumber : ");
		
		Label l_cost = new Label();    TextField cost = new TextField();
		l_cost.setText("Cost Hour : ");
		
		
		
		
		
		
		
		// les controles 
		Button remove = new Button();
		remove.setText("Remove");
		remove.setOnAction(e -> {
			String value = list_vm.getSelectionModel().getSelectedItem();
			if (value.length()==0)
			{}
			else
			{
				/*System.out.println(value);
				System.out.println(list.get(list_vm.getSelectionModel().getSelectedIndex()).getName() +" neji");
				System.out.println(list.get(list_vm.getSelectionModel().getSelectedIndex()).getId());
				int aa =list.get(list_vm.getSelectionModel().getSelectedIndex()).getId();
				String aat=list.get(list_vm.getSelectionModel().getSelectedIndex()).getType();
				System.out.println(list.get(list_vm.getSelectionModel().getSelectedIndex()).getType());
				
				System.out.println(" machine fog = "+ list_2.size());
				System.out.println(" machine cloud = "+ list_1.size());
				System.out.println(" machine  = "+ list.size());
			
				if(aat.equals("Fog"))
					System.out.println(list_2.get(aa).getName());
					
				*/
				
				
					if (Déclaration.list.get(list_vm.getSelectionModel().getSelectedIndex()).getName().equals(value))
					{
						int id_remove=Déclaration.list.get(list_vm.getSelectionModel().getSelectedIndex()).getId();
						if (Déclaration.list.get(list_vm.getSelectionModel().getSelectedIndex()).getType().equals("Fog")) 
						{
							Déclaration.list_2.remove(Déclaration.list_2.get(id_remove));
							fd[id_remove]=null;
						}
					
						else
						{
							Déclaration.list_1.remove(Déclaration.list_1.get(id_remove));
							vm[id_remove]=null;
						}
						
						Déclaration.list.remove(Déclaration.list.get(list_vm.getSelectionModel().getSelectedIndex()));
						ms[list_vm.getSelectionModel().getSelectedIndex()]=null;
						list_vm.getItems().remove(list_vm.getSelectionModel().getSelectedIndex());
						list_vm.refresh();
					
					}
			}
			
		});
		
		
		
		
		
		
		
		
		
		Button pred = new Button();
		pred.setText("Previous");
		pred.setOnAction(e -> {
			
				Déclaration.list_1.clear();
				Déclaration.list_2.clear();
				Déclaration.list.clear();
				list_vm.getItems().clear();
				name.clear();size.clear();ram.clear();bw.clear();pesNb.clear();cost.clear();mips.clear();
				
				Main.launch();
				
				
				
		
			window.close();	});
		
		
		Button next = new Button();
		next.setText("Next");
		next.setOnAction(e -> {
			
			for(int a=0;a<Déclaration.tas;a++)
				for(int b=0;b<Déclaration.list.size();b++)
					Déclaration.task_run_[a][b]=0;
			System.out.println("nb de ressources : "+Déclaration.list.size());
			System.out.println("nb de fog = "+Déclaration.nb_f);
			System.out.println("nb de cloud = "+Déclaration.nb_c);
			System.out.println("nb de fog de liste = "+Déclaration.list_2.size());
			System.out.println("nb de cloud  de liste = "+Déclaration.list_1.size());
			System.out.println("nb de tasks = "+Déclaration.tas);
			for(int c=0;c<Déclaration.nb_c;c++)
				System.out.println("id du cloud "+c+" est : "+Déclaration.list_1.get(c).getId());
			for(int c=0;c<Déclaration.nb_f;c++)
				System.out.println("id du fog "+c+" est : "+Déclaration.list_2.get(c).getId());
			
			for (int k=0;k<Déclaration.tas;k++)
			{System.out.println("---------------------------------------------------");
     			for(int x=0;x<Déclaration.list.size();x++)
     				{System.out.print(Déclaration.task_run_ [k][x]);System.out.print("      |       ");}}
			
			
			
			
			/// déclaration + fichier xml 
			try {
				Ahmed.display(Déclaration.path_workflow, Déclaration.name_WF_file);
				
			 Déclaration.factory = DocumentBuilderFactory.newInstance();
			 Déclaration.builder =  Déclaration.factory.newDocumentBuilder();
			 Déclaration.document= Déclaration.builder.parse(new File("monFichier.xml"));
			 Déclaration.racine = Déclaration.document.getDocumentElement();
			 Déclaration.racineNoeuds = Déclaration.racine.getChildNodes();
			 Déclaration.nbRacineNoeuds = Déclaration.racineNoeuds.getLength();
			 Déclaration.tasks = (Element) Déclaration.racine.getElementsByTagName("Tasks").item(0);
			 Déclaration.tas=  Déclaration.tasks.getElementsByTagName("Task").getLength();		   		 
		     int ll = Déclaration.tas;
		     Déclaration.caracteristique_task= new int [ll][4];
		     Déclaration.temps_execute = new int [Déclaration.tas];
		     Déclaration.i=0;
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		
			// pour ecrire sur le fichier xml générer par le bpmn
			int ahmed=0;
 			while (ahmed<Déclaration.list.size())
 			{
     		try {
     			
     		
	            String filepath = "monFichier.xml";
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse(filepath);

	            final Element racine = doc.getDocumentElement();
	    		System.out.println(racine.getNodeName());
	    		final NodeList racineNoeuds = racine.getChildNodes();

	    		 final Element ressources = (Element) ((Element) racineNoeuds).getElementsByTagName("Ressources").item(0);
	    		 final Element ressource = (Element) ressources.getElementsByTagName("Ressource").item(ahmed);
	    		 System.out.println(ressource.getAttribute("id"));
	    		 
	    		 
	    		 final Attr id = ressource.getAttributeNode("ID"); 
	    		 id.setValue(Déclaration.list.get(ahmed).getName());
	    		 
	    		 final Attr type_ = ressource.getAttributeNode("Type"); 
	    		 type_.setValue(Déclaration.list.get(ahmed).getType());
	    		 
	    		 
	    		 final Attr size_ = ressource.getAttributeNode("Size"); 
	    		 size_.setValue((String.valueOf(Déclaration.list.get(ahmed).getSize_c())));
	    		 
	    		 
	    		 final Attr ram_ = ressource.getAttributeNode("RAM"); 
	    		 ram_.setValue(String.valueOf(Déclaration.list.get(ahmed).getRam_c()));
	    		 
	    		 final Attr mips_ = ressource.getAttributeNode("MIPS"); 
	    		 mips_.setValue(String.valueOf(Déclaration.list.get(ahmed).getMips_c()));
	    		 
	    		 final Attr bw_ = ressource.getAttributeNode("BandWidth"); 
	    		 bw_.setValue(String.valueOf(Déclaration.list.get(ahmed).getBw_c()));
	    		 
	    		 final Attr pes_ = ressource.getAttributeNode("PES_Number"); 
	    		 pes_.setValue(String.valueOf(Déclaration.list.get(ahmed).getPesNumber()));
	    		 
	    		 final Attr cost_ = ressource.getAttributeNode("Cost"); 
	    		 cost_.setValue(String.valueOf(Déclaration.list.get(ahmed).getCost_hour_c()));
	    		 
	    		 
	    		 TransformerFactory transformerFactory = TransformerFactory
	                     .newInstance();
	             Transformer transformer = transformerFactory.newTransformer();
	             DOMSource source = new DOMSource(doc);
	             StreamResult result = new StreamResult(new File(filepath));
	             transformer.transform(source, result);
	             ahmed++;
	             

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
			
			
			
	             
	             
	             
			
			
			
			
			

			Déclaration.task_run_ = new int[Déclaration.tas][Déclaration.list.size()];
			Page3.num_tache_t = new TextField();
			Page3.num_tache_t.setText("1");
			window.close();
			try {
				Page3.display("Tasks");
			} catch (TransformerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			

			
			
		});
		
		
		
		
		Button save = new Button();
		save.setText("Save");
		save.setOnAction(e -> {
			if (type.getValue().equals("Fog")) { 
				
				long size_f = Long.parseLong(size.getText()); 
				int ram_f = Integer.parseInt(ram.getText());
				int mips_f = Integer.parseInt(mips.getText());
				long bw_f = Long.parseLong(bw.getText()) ;
				int pesNumber = Integer.parseInt(pesNb.getText());
				String f_name = name.getText();
				double cost_hour_f = Double.parseDouble(cost.getText());
				int id_f= Déclaration.nb_f+Déclaration.nb_c;
				String m_type = type.getValue();
				fd[Déclaration.nb_f] = new Fog(id_f, f_name, size_f, bw_f, mips_f, ram_f, cost_hour_f, pesNumber);
				//fog_list.add(fd[0]);
				Déclaration.list_2.add(fd[Déclaration.nb_f]);
				Déclaration.nb_f++;
				ms[Déclaration.nb_f+Déclaration.nb_c]= new Machines(id_f, f_name, size_f, bw_f, mips_f, ram_f, cost_hour_f, pesNumber,m_type);
				//Machines mm = new Machines(id_f, f_name, size_f, bw_f, mips_f, ram_f, cost_hour_f, pesNumber);
				//machine_list.add(mm);
				Déclaration.list.add(ms[Déclaration.nb_f+Déclaration.nb_c]);
				list_vm.getItems().add(ms[Déclaration.nb_f+Déclaration.nb_c].getName());
				list_vm.refresh();
				name.clear();size.clear();ram.clear();bw.clear();pesNb.clear();cost.clear();mips.clear();
			}
			else {
				long size_c = Long.parseLong(size.getText()); 
				int ram_c = Integer.parseInt(ram.getText());
				int mips_c = Integer.parseInt(mips.getText());
				long bw_c = Long.parseLong(bw.getText()) ;
				int pesNumber = Integer.parseInt(pesNb.getText());
				String c_name = name.getText();
				double cost_hour_c = Double.parseDouble(cost.getText());
				int id = Déclaration.nb_c;
				String m_type = type.getValue();
				int user_id = 1;
				String vmm = "Xen";
				
				vm[Déclaration.nb_c]= new MyVm(id,c_name,user_id, mips_c, pesNumber, ram_c, bw_c, size_c, vmm, new CloudletSchedulerSpaceShared(), cost_hour_c);
				//MyVm m = new MyVm(id,c_name, user_id, mips_c, pesNumber, ram_c, bw_c, size_c, vmm, new CloudletSchedulerSpaceShared(), cost_hour_c);
				//cloud_list.add(m);
				Déclaration.list_1.add(vm[Déclaration.nb_c]);
				Déclaration.nb_c ++;
				ms[Déclaration.nb_c+Déclaration.nb_f]= new Machines (id,c_name, user_id, mips_c, pesNumber, ram_c, bw_c, size_c, vmm, new CloudletSchedulerSpaceShared(), cost_hour_c,m_type);
				//Machines mm = new Machines(id,c_name, user_id, mips_c, pesNumber, ram_c, bw_c, size_c, vmm, new CloudletSchedulerSpaceShared(), cost_hour_c);
				//machine_list.add(mm);
				Déclaration.list.add(ms[Déclaration.nb_c+Déclaration.nb_f]);
				list_vm.getItems().add(ms[Déclaration.nb_c+Déclaration.nb_f].getName());
				list_vm.refresh();
					
				
			}
			
		});
		
		
		Button save_modif = new Button();
		save_modif.setVisible(false);
		save_modif.setText("save modification");
		save_modif.setOnAction(e -> {
		
			int id_m= Déclaration.list.get(Déclaration.id_modif).getId();
			//list.get(id_modif).setName(name.getText());
			if (Déclaration.t.equals("Fog"))
			{
				Déclaration.list_2.get(id_m).setSize(Long.parseLong(size.getText())); 
				Déclaration.list_2.get(id_m).setRAM(Integer.parseInt(ram.getText()));
				Déclaration.list_2.get(id_m).setMIPS(Integer.parseInt(mips.getText()));
				Déclaration.list_2.get(id_m).setBw(Long.parseLong(bw.getText())) ;
				Déclaration.list_2.get(id_m).setPesNumber(Integer.parseInt(pesNb.getText()));
				Déclaration.list_2.get(id_m).setName(name.getText());
				Déclaration.list_2.get(id_m).setCost(Double.parseDouble(cost.getText()));
			}
			else
			{
				Déclaration.list_1.get(id_m).setSize(Long.parseLong(size.getText())); 
				Déclaration.list_1.get(id_m).setRam(Integer.parseInt(ram.getText()));
				Déclaration.list_1.get(id_m).setMips(Integer.parseInt(mips.getText()));
				Déclaration.list_1.get(id_m).setBw(Long.parseLong(bw.getText())) ;
				Déclaration.list_1.get(id_m).setNumberOfPes(Integer.parseInt(pesNb.getText()));
				Déclaration.list_1.get(id_m).setName(name.getText());
				Déclaration.list_1.get(id_m).setCost_hour(Double.parseDouble(cost.getText()));
			}
			
			Déclaration.list.get(Déclaration.id_modif).setSize_c(Long.parseLong(size.getText())); 
			Déclaration.list.get(Déclaration.id_modif).setRam_c(Integer.parseInt(ram.getText()));
			Déclaration.list.get(Déclaration.id_modif).setMips_c(Integer.parseInt(mips.getText()));
			Déclaration.list.get(Déclaration.id_modif).setBw_c(Long.parseLong(bw.getText())) ;
			Déclaration.list.get(Déclaration.id_modif).setPesNumber(Integer.parseInt(pesNb.getText()));
			Déclaration.list.get(Déclaration.id_modif).setName(name.getText());
			Déclaration.list.get(Déclaration.id_modif).setCost_hour_c(Double.parseDouble(cost.getText()));
			save_modif.setVisible(false);
			save.setVisible(true);
			list_vm.refresh();
			name.clear();size.clear();ram.clear();bw.clear();pesNb.clear();cost.clear();mips.clear();
		});
		
		
		
		
		
		Button modify = new Button();
		modify.setText("Modify");
		modify.setOnAction(e -> {
			saveButton();
			
			save.setVisible(false);
			save_modif.setVisible(true);
			
			
			Déclaration.id_modif = list_vm.getSelectionModel().getSelectedIndex();
			Déclaration.t = Déclaration.list.get(list_vm.getSelectionModel().getSelectedIndex()).getType();
			
				name.setText(Déclaration.list.get(Déclaration.id_modif).getName());
				size.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getSize_c())));
				ram.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getRam_c())));
				mips.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getMips_c())));
				bw.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getBw_c())));
				pesNb.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getPesNumber())));
				cost.setText((String.valueOf(Déclaration.list.get(Déclaration.id_modif).getCost_hour_c())));
				
						
			
		});
		
		
		
		
		
		
		
		//position des composants 
		label.setLayoutX(150);
		label.setLayoutY(20);
		
		list_vm.setLayoutX(50);
		list_vm.setLayoutY(80);
		list_vm.setMaxHeight(150);
		list_vm.setMaxWidth(250);
		
		pred.setLayoutX(35);   pred.setMaxHeight(20); pred.setMinWidth(100); pred.setMaxWidth(100);
		pred.setLayoutY(700);
		
		next.setLayoutX(350);  next.setMaxHeight(20); next.setMinWidth(100); next.setMaxWidth(100);
		next.setLayoutY(700);
		
		l_type.setLayoutX(30);   type.setLayoutX(135);       type.setMaxHeight(20);  type.setMinWidth(200);
		l_type.setLayoutY(250);  type.setLayoutY(245);       
		
		l_name.setLayoutX(30);   name.setLayoutX(135);       name.setMaxWidth(200);  name.setMinWidth(200);
		l_name.setLayoutY(300);  name.setLayoutY(295);      name.setMaxHeight(20);
		
		
		l_size.setLayoutX(30);   size.setLayoutX(135);       size.setMaxWidth(200);   size.setMinWidth(200);
		l_size.setLayoutY(350);  size.setLayoutY(345);      size.setMaxHeight(20);
		
		l_Ram.setLayoutX(30);   ram.setLayoutX(135);       ram.setMaxWidth(200);       ram.setMinWidth(200);
		l_Ram.setLayoutY(400);  ram.setLayoutY(395);      ram.setMaxHeight(20);
		
		l_mips.setLayoutX(30);   mips.setLayoutX(135);       mips.setMaxWidth(200);    mips.setMinWidth(200);
		l_mips.setLayoutY(450);  mips.setLayoutY(445);      mips.setMaxHeight(20);
		
		l_bw.setLayoutX(30);   bw.setLayoutX(135);       bw.setMaxWidth(200);           bw.setMinWidth(200);
		l_bw.setLayoutY(500);  bw.setLayoutY(495);      bw.setMaxHeight(20);
		
		l_pesnb.setLayoutX(30);   pesNb.setLayoutX(135);       pesNb.setMaxWidth(200);    pesNb.setMinWidth(200);
		l_pesnb.setLayoutY(550);  pesNb.setLayoutY(545);      pesNb.setMaxHeight(20);
		
		l_cost.setLayoutX(30);   cost.setLayoutX(135);       cost.setMaxWidth(200);      cost.setMinWidth(200);
		l_cost.setLayoutY(600);  cost.setLayoutY(595);      cost.setMaxHeight(20);
		
		save.setLayoutX(100);    save.setMaxHeight(20);
		save.setLayoutY(650);    save.setMaxWidth(300);     save.setMinWidth(300);
		
		remove.setLayoutX(350);  remove.setMaxHeight(20); remove.setMinWidth(100);
		remove.setLayoutY(110);  remove.setMaxWidth(100); 
		
		modify.setLayoutX(350);  modify.setMaxHeight(20); modify.setMinWidth(100);
		modify.setLayoutY(160);  modify.setMaxWidth(100);
		
		
		save_modif.setLayoutX(100);    save_modif.setMaxHeight(20);
		save_modif.setLayoutY(650);    save_modif.setMaxWidth(300);     save_modif.setMinWidth(300);
		
		
		Group g = new Group();
	    g.getChildren().addAll(label,list_vm,pred,next,l_type,type,l_name,name,l_size,size,l_Ram,ram,l_mips,mips,l_bw,bw,l_pesnb,pesNb,l_cost,cost
	    		,save,remove,modify,save_modif);
	   // Scene scene = new Scene(g,800,400);
		//StackPane layout = new StackPane();
		//layout.getChildren().addAll(label,list_vm);
		Scene scene = new Scene(g);
		window.setScene(scene);
		window.show();
	}
	public static void saveButton()
	
	{
		
	}

}
