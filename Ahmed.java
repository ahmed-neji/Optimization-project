package application;

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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Ahmed {
	 static Element tt;
	 static String supp_task="Task_";
	 static String supp_gatway="ParallelGateway_";
	
	public static void display (String fichier,String name) throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
		 final DocumentBuilder builder =  factory.newDocumentBuilder();
		 final Document document= builder.parse(new File(fichier));
		 //final Document document= builder.parse(new File(fichier));
		 final Element racine = document.getDocumentElement();
		System.out.println(racine.getNodeName());
		final NodeList racineNoeuds = racine.getChildNodes();

		 final Element pross = (Element) ((Element) racineNoeuds).getElementsByTagName("bpmn2:process").item(0);
		 final NodeList prossNodes = pross.getChildNodes();
		 final Element task = (Element)((Element) prossNodes).getElementsByTagName("bpmn2:task").item(0);
		 //final NodeList tasks =((Document) prossNodes).getElementsByTagName("bpmn2:task"); 
		 final int task_nb=  pross.getElementsByTagName("bpmn2:task").getLength();
		 //bpmn2:parallelGateway
		 final int gatway_nb=  pross.getElementsByTagName("bpmn2:parallelGateway").getLength();
		 final int trans_nb=  pross.getElementsByTagName("bpmn2:sequenceFlow").getLength();
		 final Element gatway = (Element)((Element) prossNodes).getElementsByTagName("bpmn2:parallelGateway").item(0);
		 final Element trans = (Element)((Element) prossNodes).getElementsByTagName("bpmn2:sequenceFlow").item(0);
		
		 
		 
		 
		 
		 System.out.println(task.getAttribute("id"));
		System.out.println(task_nb);
		System.out.println(gatway_nb);
		System.out.println(trans_nb);
		
		System.out.println(gatway.getAttribute("id"));
		System.out.println(gatway.getAttribute("name"));
		System.out.println(gatway.getAttribute("gatewayDirection"));
		
		
		System.out.println(trans.getAttribute("id"));
		
		
		
		
		
		
		
		
		
	     try {
	    	 
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
	  
	         // élément de racine (workflow)
	         Document doc = docBuilder.newDocument();
	         Element workflow = doc.createElement("Workflow");
	         doc.appendChild(workflow);
	  
	         // l'élément tasks
	         Element tasks_g = doc.createElement("Tasks");
	         workflow.appendChild(tasks_g);
	  
	  
	         // chaque task
	         for (int i =0 ;i<task_nb;i++)
	         {
	        	  tt = (Element)((Element) prossNodes).getElementsByTagName("bpmn2:task").item(i);
	         Element task_g = doc.createElement("Task");
	         String a=tt.getAttribute("id"); 
	         a=a.replace(supp_task,"");
	         Attr task_id=doc.createAttribute("id");
	         task_id.setValue(a);
	        
	         
	         Attr task_time=doc.createAttribute("Time");
	         
	         
	         Attr task_length =doc.createAttribute("lenght");
	         
	         Attr task_size =doc.createAttribute("fileSize");
	         
	         Attr task_Outsize =doc.createAttribute("outputSize");
	         
	         Attr task_nbpes =doc.createAttribute("nbOfPes");
	                 
	                 // ajouter les attributs au task_g créee 
	        		 
	        		
	        		 task_g.setAttributeNode(task_length);
	        		 
	        		 task_g.setAttributeNode(task_size);
	        		 
	        		 task_g.setAttributeNode(task_Outsize);
	        		 
	        		 task_g.setAttributeNode(task_nbpes);
	        		
	        		 task_g.setAttributeNode(task_time);
	        		 task_g.setAttributeNode(task_id);
	        		 
	         tasks_g.appendChild(task_g);
	         }
	  
	         // branches
	         Element Branches_g = doc.createElement("Branches");
	         
	         workflow.appendChild(Branches_g);
	  
	         // chaque branche
	         for (int i=0;i<gatway_nb;i++)
	         {
	        	 tt=(Element)((Element) prossNodes).getElementsByTagName("bpmn2:parallelGateway").item(i);
	        	 Element branche_g = doc.createElement("Branch");
	        	 String aa = tt.getAttribute("id");
	        	aa= aa.replace(supp_gatway, "");
	        	 aa=aa+i+i+i;
	        	 Attr branche_id =doc.createAttribute("id");
	        	 branche_id.setValue(aa);
	        	 
	        	 Attr branche_type =doc.createAttribute("type");
	        	 branche_type.setValue(tt.getAttribute("gatewayDirection"));
		         
        		            branche_g.setAttributeNode(branche_id);
        		            branche_g.setAttributeNode(branche_type);
        
	        	 
	        	 
	        	 Branches_g.appendChild(branche_g);
	         }

	         // balise transitions
             Element transitions_g = doc.createElement("Transitions");
   	         workflow.appendChild(transitions_g);
	  
	         //chaque trans
   	         for (int i=0;i<trans_nb;i++)
   	         {
   	        	tt=(Element)((Element) prossNodes).getElementsByTagName("bpmn2:sequenceFlow").item(i);
	        	 Element trans_g = doc.createElement("Transition");
	        	 Attr trans_id =doc.createAttribute("id");
	        	 trans_id.setValue(tt.getAttribute("id"));
	        	 
	        	 Attr trans_from =doc.createAttribute("from");
	        	 String f = tt.getAttribute("sourceRef");
	        	 boolean test1 = false;
	        	 boolean test2=false;
	        	
	        	 
	        	 
	        	 if (f.contains("Task_")) 
	        	 {
	        		 f=f.replace(supp_task, "");
	        	     trans_from.setValue(f);
	        	     test1=true ;
	        	 
	        	 }
	        	 
	        	 else if(f.contains("ParallelGateway"))
	        	 {
	        		 f=f.replace(supp_gatway, "");
	        		 for (int k=0;k<gatway_nb;k++)
	        		
	        		 {
	        			 String y="ParallelGateway_"+(k+1);
	        			 if(tt.getAttribute("sourceRef").equals(y))
	        			 {
	        				 f=f+k+k+k;
	        			     test1=true;
	        			 }
	        		 }
	        		 
	        		 
	        		 
	        	 }
	        	 
	        		 
	        	 trans_from.setValue(f);
	        	 
	        	 Attr trans_to =doc.createAttribute("to");
	             
	        	 String t=tt.getAttribute("targetRef");
	             
	        	 if(t.contains("Task_"))
	             {
	            	 t=t.replace(supp_task,"");
	            	 trans_to.setValue(t);
	            	 test2=true ;
	             }
	             else if(t.contains("ParallelGateway"))
	        	 {
	        		 t=t.replace(supp_gatway, "");
	        		 for (int k=0;k<gatway_nb;k++)
	        		 {
	        			 String y="ParallelGateway_"+(k+1);
	        			 if(tt.getAttribute("targetRef").equals(y)) {
	        				 t=t+k+k+k;
	        			 trans_to.setValue(t);
	        			 test2=true;}
	        		 }
	        		 test1=true;
	        		 
	        	 }
	        	 trans_to.setValue(t);
		         
       		            trans_g.setAttributeNode(trans_id);
       		            trans_g.setAttributeNode(trans_from);
       		            trans_g.setAttributeNode(trans_to);
	        	 if(test1==true && test2==true)
	        	 transitions_g.appendChild(trans_g);
	     
   	         }
   	         
   	      // l'élément ressources
	         Element ressources_g = doc.createElement("Ressources");
	         workflow.appendChild(ressources_g);
	         for (int i =0;i<Déclaration.list.size();i++)
	         {
	        	 Element ressource = doc.createElement("Ressource");
	        	 
		         Attr vm_id=doc.createAttribute("ID");
		         Attr vm_type=doc.createAttribute("Type");
		         Attr vm_size=doc.createAttribute("Size");
		         Attr vm_ram=doc.createAttribute("RAM");
		         Attr vm_mips=doc.createAttribute("MIPS");
		         Attr vm_bw=doc.createAttribute("BandWidth");
		         Attr vm_pes=doc.createAttribute("PES_Number");
		         Attr vm_cost=doc.createAttribute("Cost");
		                      
		                 // ajouter les attributs au task_g créee 
		        		 
		        		
		        		 ressource.setAttributeNode(vm_id);
		        		 ressource.setAttributeNode(vm_type);
		        		 ressource.setAttributeNode(vm_size);
		        		 ressource.setAttributeNode(vm_ram);
		        		 ressource.setAttributeNode(vm_mips);
		        		 ressource.setAttributeNode(vm_bw);
		        		 ressource.setAttributeNode(vm_pes);
		        		 ressource.setAttributeNode(vm_cost);
		        		
		        		 
		        		 ressources_g.appendChild(ressource);
	         }
	   
	        
	  
	         // write the content into xml file
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         DOMSource source = new DOMSource(doc);
	         StreamResult resultat = new StreamResult(new File("monFichier.xml"));
	  
	         transformer.transform(source, resultat);
	  
	         System.out.println("Fichier sauvegardé avec succès!");
	  
	      } catch (ParserConfigurationException pce) {
	          pce.printStackTrace();
	      } catch (TransformerException tfe) {
	          tfe.printStackTrace();
	      }
	   }
	
	}

