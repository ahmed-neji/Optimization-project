package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ReadXml {
	
	
	


		static int n=Déclaration.tas;
		static int c=(Déclaration.nb_c+Déclaration.nb_f);
		static int b=2;
		static double [][] dependance_task = new double [n][n];
		static int[][] caracters_task = new int [n][c];
		static ArrayList<String> list_id_task = new ArrayList<String>(); 
		static ArrayList<String> list_outputSize_task = new ArrayList<String>(); 
		static ArrayList<String> list_id_branch = new ArrayList<String>(); 
		static ArrayList<String> list_task_succ = new ArrayList<String>(); 
		static ArrayList<String> list_succ_and_split = new ArrayList<String>(); 
		static ArrayList<String> list_pred_and_join = new ArrayList<String>(); 




		   
		
	
		
			
			
		//******************************Méthode :dependance_task**********************************//
			public static  double [][] dependance_task (String xml_name) throws ParserConfigurationException, SAXException, IOException {

				
				final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			    final DocumentBuilder builder = factory.newDocumentBuilder();
			    final Document document= builder.parse(xml_name);
			    //final Document document= builder.parse("monFichier.xml");//  final Document document= builder.parse(new File(name_WF_file));
				final Element racine = document.getDocumentElement();
				final Element transitions = (Element) racine.getElementsByTagName("Transitions").item(0);
				
		    	final int trans=  transitions.getElementsByTagName("Transition").getLength();// retourne nb de transition ( dans notre cas = 7)
                System.out.println("nombre de tratition  = " + trans );
		    	final Element branches = (Element) racine.getElementsByTagName("Branches").item(0);
		    	final int branc = branches.getElementsByTagName("Branch").getLength();//retourne nb de branchements (dans notre cas = 2)
		    	System.out.println("nombre de branche  = " + branc );	

		    	list_id_task = getList_IDTask(xml_name);
		    	System.out.println("list_id_task " +list_id_task);

		    	list_outputSize_task = getList_OutputSizeTask(xml_name);
		    	System.out.println("list_outputSize_task " +list_outputSize_task);
		    	
		    	list_id_branch = getList_Id_Branch(xml_name);
		    	System.out.println("list_id_branch " +list_id_branch);

		        list_task_succ = getSucc_branch(xml_name);
		        System.out.println ("list_task_succ " +list_task_succ);
		        
		        list_succ_and_split = getSucc_and_slpit(xml_name);
		       System.out.println ("list_succ_and_split " +list_succ_and_split);
		        
		        list_pred_and_join = getPred_and_join(xml_name);
		        System.out.println ("list_pred_and_join " +list_pred_and_join);

		    	   
		    	for (int i1 = 0; i1< trans ; i1++) {//boucle  sur nb d'élements de transition 
			    	final Element transition = (Element) transitions.getElementsByTagName("Transition").item(i1);   	
			    	
			    		//for (int i = 0; i< n ; i++) {// boucle sur nb de tasks (dans notre cas = 5)
			    	    	//Element task = (Element) tasks.getElementsByTagName("Task").item(i);//balise task
		                
			    	    	String from = transition.getAttribute("from"); 
					    	String to = transition.getAttribute("to");
					    	

									    	
	       if (list_id_task.contains(from) && list_id_task.contains(to)){ //Cas 1: Transition entre 2 tasks
	    	   int from_int = Integer.parseInt(from);// convertir attribut from de string en int
		    	int to_int = Integer.parseInt(to);// convertir attribut to de string en int
		    	
	    	  // System.out.println (transition.getAttribute("id"));
		    	 String ouput= list_outputSize_task.get(from_int-1);
		    	 int output_int = Integer.parseInt(ouput);
		    	dependance_task[from_int-1][to_int-1] = output_int;
	    	  
	    	   System.out.println ("dependance_task["+from_int+"]["+to_int+"] : "+ dependance_task[from_int-1][to_int-1] );
	    	  
	       } // fin "if" de Cas 1  
	       
	       else{
	    	   
	    	
	    	  if(list_id_task.contains(from) && to.contains(list_id_branch.get(0)) ){ // Cas 2: Source Task et Dest AND-Split
	    	   	    // System.out.println ("T :"+transition.getAttribute("id")); //retourne 'A'
	        	    int from_and_split = Integer.parseInt(from);
	    	   	     String and_split = list_id_branch.get(0);


			 	   	    	for( int i =0; i<list_succ_and_split.size();i++){
			 	   	    		
			 	   	    	String succ = list_succ_and_split.get(i);
			    	   	    int succ_ = Integer.parseInt(succ);
			    	   	    
			    	   	 String ouput= list_outputSize_task.get(from_and_split - 1);	 
				    	 int output_int = Integer.parseInt(ouput);
				   
			    	  dependance_task[from_and_split-1][succ_-1]= output_int;
			       System.out.println ("dependance_task["+from_and_split+"]["+succ_+"] : "+ dependance_task[from_and_split-1][succ_-1] );
			  		 	   	     	   	  
			 	   	    	}//fin "for"   
	    	  
	    	  } //fin "if" Cas 2
	          
	    	  else {
	    		  if( from.contains(list_id_branch.get(1)) && list_id_task.contains(to) ){ // Cas 3: Source Task et Dest AND-join
	    			 
	    			  int to_and_join= Integer.parseInt(to);
	    			  
	    			//  System.out.println("+++++++++++ " +transition.getAttribute("id") ) ; 
		 	   	    	
	    			  for( int i =0; i<list_pred_and_join.size() ;i++){
	    				  
		 	   	    	String pred = list_pred_and_join.get(i);

		    	   	    int pred_ = Integer.parseInt(pred);

		    	   	 String ouput= list_outputSize_task.get(pred_- 1);	 
			    	 int output_int = Integer.parseInt(ouput);
		    	 dependance_task[pred_-1][to_and_join -1]=  output_int;
		         System.out.println ("dependance_task["+pred_+"]["+to_and_join+"] : "+ dependance_task[pred_-1][to_and_join -1] );
		 	   	    	
		         
	    			  }// fin "for"
	    			  
	       	   }//fin "if"
	       		  

	    	  } //fin else 
	    	   } //fin else
	   
	    	  
	     
	     
		
		    	} //fin for"trans"	
		    /*	System.out.println("\nAffichage de la matrice dependance Task: \n");
		    	for (int i=0; i<n; i++){
		    		for (int j=0;j<n ;j++){
		    			 System.out.print(dependance_task[i][j] +" ");

		    		}// fin for "j"
		    	    System.out.println();

		    	}// fin for "i"*/
				return dependance_task;
				
			}
			
			
			
			
			
	   //*******************************Méthode :liste_Id_Tasks*******************************// 
	//---------------------------Cette méthode retourne la liste des ID des tasks (1,2,3,4,5)---------------------//
		public static ArrayList<String>  getList_IDTask(String xml) throws SAXException, IOException, ParserConfigurationException {
			 ArrayList<String> list_id_of_task = new ArrayList<String>(); 
			 String id_task;
			
		   		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		   	    final DocumentBuilder builder = factory.newDocumentBuilder();
			    final Document document= builder.parse(xml); // (new file("Workflow.xml)"
				final Element racine = document.getDocumentElement();
			    final Element tasks = (Element) racine.getElementsByTagName("Tasks").item(0);//balise Tasks
			    int task_nb =tasks.getElementsByTagName("Task").getLength();
			for (int i = 0; i< task_nb ; i++) {// boucle sur nb de tasks (dans notre cas = 5)
		    	Element task = (Element) tasks.getElementsByTagName("Task").item(i);//balise task
		         id_task =  task.getAttribute("id");
		         list_id_of_task.add(id_task);
			}
			
			return list_id_of_task;

		}// fin " getList_IDTask()"
		   //*******************************Fin :liste_Id_Tasks*******************************// 
		
		
		
		
		//*******************************Méthode :liste_output_Tasks*******************************// 			
		//--------------------------Cette méthode retourne la liste des outputSise de chaque task-------------------//
		public static ArrayList<String>  getList_OutputSizeTask(String xml) throws ParserConfigurationException, SAXException, IOException{
			 ArrayList<String> list_outputSize_of_task = new ArrayList<String>(); 
	         String outputSize_task;
	        
			    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		   	    final DocumentBuilder builder = factory.newDocumentBuilder();
			    final Document document= builder.parse(new File(xml));
				final Element racine = document.getDocumentElement();
			    final Element tasks = (Element) racine.getElementsByTagName("Tasks").item(0);//balise Tasks
			    int nb_task = tasks.getElementsByTagName("Task").getLength();
			for (int i = 0; i< nb_task ; i++) {// boucle sur nb de tasks (dans notre cas = 5)
				Element task = (Element) tasks.getElementsByTagName("Task").item(i);//balise task
				//outputSize_task = task.getAttribute("outputSize");
				outputSize_task= Integer.toString(Evaluation.main.caract_cloudlet[i][2]);
				list_outputSize_of_task.add(outputSize_task);
			}
			return list_outputSize_of_task;
		}// fin "getList_OutputSizeTask()"
		   //*******************************Fin :liste_output_Tasks*******************************// 
		
		
		   //*******************************Méthode :liste_output_Tasks*******************************// 
		public static ArrayList<String>  getList_Id_Branch(String xml) throws ParserConfigurationException, SAXException, IOException {
			
			ArrayList<String> list_id_of_branch = new ArrayList<String>(); 
			 String id_branch;
			
			 final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		   	    final DocumentBuilder builder = factory.newDocumentBuilder();
			    final Document document= builder.parse(new File(xml));
				final Element racine = document.getDocumentElement();
				final Element branches = (Element) racine.getElementsByTagName("Branches").item(0);
		    	final int branc = branches.getElementsByTagName("Branch").getLength();//retourne nb de branchements (dans notre cas = 2)
		    	  	
		    	for (int i = 0; i<branc ; i++) {// boucle sur nb de branches (dans notre cas = 2)
		    		Element branch = (Element) branches.getElementsByTagName("Branch").item(i);//balise branch
		    		id_branch = branch.getAttribute("id");
		    		list_id_of_branch.add(id_branch);
				}
		    	
				return list_id_of_branch;
		    

		}// fin "getList_Id_Branch() "

		   //*******************************Fin :liste_output_Tasks*******************************// 

		
		
		
		
		   //*******************************Méthode :get_succ_branch*******************************// 
		//--------------------Cette méthode est utlisé au début avant d'ajouter les 2 méthode de succ_AND_Split et Pred_AND_Join-----------//
		public static ArrayList<String>  getSucc_branch(String xml) throws ParserConfigurationException, SAXException, IOException{
			
			ArrayList<String> list_id_off_branch = new ArrayList<String>(); 
			ArrayList<String> list_task_of_succ = new ArrayList<String>(); 
			ArrayList<String> list_idd_task = new ArrayList<String>(); 

	    	String task_succ;
			
			    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		   	    final DocumentBuilder builder = factory.newDocumentBuilder();
			    final Document document= builder.parse(new File(xml));
				final Element racine = document.getDocumentElement();
				final Element branches = (Element) racine.getElementsByTagName("Branches").item(0);
		    	final int branc = branches.getElementsByTagName("Branch").getLength();//retourne nb de branchements (dans notre cas = 2)
		    	final Element transitions = (Element) racine.getElementsByTagName("Transitions").item(0);
		    	final int trans=  transitions.getElementsByTagName("Transition").getLength();// retourne nb de transition ( dans notre cas = 7)
		    	
		    	for (int i1 = 0; i1< trans ; i1++) {//boucle  sur nb d'élements de transition ( dans notre cas  = 7)
			    	final Element transition = (Element) transitions.getElementsByTagName("Transition").item(i1); 
		    	String from = transition.getAttribute("from"); 
		    	String to = transition.getAttribute("to"); 
		    	
		    	list_id_off_branch = getList_Id_Branch(xml);
		    	list_idd_task = getList_IDTask(xml);
		    	
		    	if (list_id_off_branch.contains(from) && list_idd_task.contains(to)){
		    		
		    		task_succ = transition.getAttribute("to");
		    		list_task_of_succ.add(task_succ); 
		    	}
		    	
		    	
		    	}
				return list_task_of_succ;
		    	
		
		} //fin "getSucc_branch()"
		   //********************************Fin :get_succ_branch***************************************// 

		
		   //*******************************Méthode :get_succ_and_slpit*******************************// 
			//-------------Cette méthode retourne la liste des succ de AND_Split(se sont des tasks) pour qu'on puisse après les mettre dans la colonne de dependance task-------------//
		public static ArrayList<String> getSucc_and_slpit(String xml) throws ParserConfigurationException, SAXException, IOException {
			
			ArrayList<String> list_id_off_branch = new ArrayList<String>(); 
			ArrayList<String> list_task_of_succ = new ArrayList<String>(); 
			ArrayList<String> list_succ = new ArrayList<String>(); 
			String succ;
			
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   	    final DocumentBuilder builder = factory.newDocumentBuilder();
		    final Document document= builder.parse(new File(xml));
			final Element racine = document.getDocumentElement();
			final Element transitions = (Element) racine.getElementsByTagName("Transitions").item(0);
	    	final int trans=  transitions.getElementsByTagName("Transition").getLength();// retourne nb de transition ( dans notre cas = 7)

	    	
			for (int i1 = 0; i1< trans ; i1++) {//boucle  sur nb d'élements de transition ( dans notre cas  = 7)
		    	final Element transition = (Element) transitions.getElementsByTagName("Transition").item(i1);  
	       	String from = transition.getAttribute("from"); 
	    	String to = transition.getAttribute("to");
	    	
	    	list_id_off_branch = getList_Id_Branch(xml);
	    	list_task_of_succ = getList_IDTask(xml);
	    	
			if ( from.contains( list_id_off_branch.get(0)) && list_task_of_succ.contains(to)  ){// list_task_suuc : donne == task succ de branch
				
		     succ = transition.getAttribute("to") ;
		     list_succ.add(succ);
	   	     
			} //finif

			}// fin for
			
			return list_succ;
			
			
		} //fin "getSucc_and_slpit()"
	  	
		   //*******************************Fin :get_succ_and_slpit*******************************// 
		
		
		   //*******************************Méthode :get_succ_and_join*******************************// 
		//---------------Cette méthode retourne la liste des pred de AND_Join(se sont des tasks) pour qu'on puisse les mettre dans ligne de dependance task--------------//

	public static ArrayList<String> getPred_and_join(String xml) throws ParserConfigurationException, SAXException, IOException {
			
		//ici on change list va prendre les pred de and _join pour qu'on puisse faire for dans main
			ArrayList<String> list_id_off_branch = new ArrayList<String>(); 
			ArrayList<String> list_pred = new ArrayList<String>(); 
			ArrayList<String> list_id_of_task  = new ArrayList<String>(); 
			String pred;
			
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   	    final DocumentBuilder builder = factory.newDocumentBuilder();
		    final Document document= builder.parse(new File(xml));
			final Element racine = document.getDocumentElement();
			final Element transitions = (Element) racine.getElementsByTagName("Transitions").item(0);
	    	final int trans=  transitions.getElementsByTagName("Transition").getLength();// retourne nb de transition ( dans notre cas = 7)

	    	
			for (int i1 = 0; i1< trans ; i1++) {//boucle  sur nb d'élements de transition ( dans notre cas  = 7)
		    	final Element transition = (Element) transitions.getElementsByTagName("Transition").item(i1);  
	       	String from = transition.getAttribute("from"); 
	    	String to = transition.getAttribute("to");
	    	
	    	list_id_off_branch = getList_Id_Branch(xml);
	    	list_id_of_task = getList_IDTask(xml);
	    	
			if ( list_id_of_task.contains(from) && to.contains( list_id_off_branch.get(1))){ 
		    	//System.out.println("++++++toto_join+++++" +transition.getAttribute("id") ) ; 	
		    	
		     pred = transition.getAttribute("from") ;
		     list_pred.add(pred);

			} //fin "if"

			}// fin "for"
			
			return list_pred;

			
		} //fin "getPred_and_join()"
	  	
	}// fin class



