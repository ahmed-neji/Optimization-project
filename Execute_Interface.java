package application;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.hana.pso.Location;
import org.xml.sax.SAXException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Execute_Interface {
	
	//*********************d�claration des variables************************************** 
	public static ListView res;
	public static double[][] TET = new double [D�claration.tas][D�claration.nb_c+D�claration.nb_f];
	public static double[][] TET_G = new double [D�claration.tas][D�claration.nb_c+D�claration.nb_f];
	public static double[][] DT = new double [D�claration.tas][D�claration.tas];
	public static double[][] ET = new double [D�claration.tas][D�claration.nb_c+D�claration.nb_f];
	public static double[][] ET_G = new double [D�claration.tas][D�claration.nb_c+D�claration.nb_f];
	static int[] loc = new int[D�claration.tas];
	static double[] lag_vm =new  double[D�claration.tas] ;
	static double[] UC_VM = new double [D�claration.nb_c+D�claration.nb_f];
	//*********************fin d�claration************************
	
	public static void display(String title) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(600);
		window.setHeight(850);
		
		
		
		//**************************execute with PSO code ***********************************
		Button ex = new Button() ;
		ex.setText("Execute With PSO");
		ex.setOnAction(e -> {
			res.getItems().clear();
			double[][] dependance_task_ = new double [D�claration.tas][D�claration.tas];
	    	ReadXml r1 = new ReadXml();
	    	try {
				dependance_task_=	r1.dependance_task();//r1.dependance_task(D�claration.name_WF_file);
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
	    	
   	
	    	Estimation c1= new Estimation(dependance_task_, D�claration.caracteristique_task);	
			ET = c1.Calcul_ET(D�claration.tas,(D�claration.nb_c+D�claration.nb_f),D�claration.list_task,D�claration.list_2,D�claration.list_1, D�claration.task_run_);
			DT = c1.Calcul_DT(D�claration.tas,D�claration.list_task,D�claration.list_1, dependance_task_ ); 
			double [][] DT = new double[D�claration.tas][D�claration.tas];  	
		    for (int i=0; i<D�claration.tas ; i++) {
			    for (int j=0; j<D�claration.tas; j++) {
			    	if( dependance_task_[i][j]!= 0 ) 
			    		{	
			    			DT[i][j] = (double)(D�claration.caracteristique_task[i][2]/60)/(D�claration.list_1.get(0).getBw());
			    		}
			    	}
		    	}
		  	
		    Mapp_task mt = new Mapp_task();
			Location location= new Location(loc);
			for (int d=0;d<D�claration.nb_c;d++)
				lag_vm[d]=1;
			for(int q=D�claration.nb_c;q<(D�claration.nb_c+D�claration.nb_f);q++)
					lag_vm[q]=0;
			TET = mt.calcul_Total_ex_time(DT, ET, lag_vm, D�claration.tas, D�claration.nb_c+D�claration.nb_f);
				
			System.out.println("***********Affichage de TET de PSO **********************");
		    for (int i1=0; i1<D�claration.tas; i1++) {
			     for (int j=0; j<D�claration.nb_c+D�claration.nb_f; j++) {
						System.out.println("--------TET["+i1+"]["+j+"]------------ :"+TET[i1][j] +"  ");//affichage de TET
						if(TET[i1][j]<0)
							System.out.println("Nombre de VM de TET["+ i1+"]["+ j+"] ======= 0");
						else
							System.out.println("Nombre de VM de TET["+ i1+"]["+ j+"] ======= "+ mt.calcul_Nb_VM(TET[i1][j]));  
							
				        }//fin "j"
				         System.out.println();
		         }
		         
		         System.out.println("**********************fin affichage TET de PSO *************************");
		         
		         
		         for (int c=0;c<D�claration.nb_c;c++)
		        	 UC_VM[c]=D�claration.list_1.get(c).getCost_hour();
		         
		         for (int c=0;c<D�claration.nb_f;c++)
		        	 UC_VM[c+D�claration.nb_c]=D�claration.list_2.get(c).getCost();
		         
		         pross pp = new pross();
		         pp.execute(TET,D�claration.tas, DT, ET,UC_VM,D�claration.deadline);
    	});
		
		
		
		//***********************************execute with cplex code ****************************************
		
		Button ex_c = new Button() ;
		ex_c.setText("Execute With Cplex");
		ex_c.setOnAction(e -> {
			res.getItems().clear();
		
			double[][] dependance_task_ = new double [D�claration.tas][D�claration.tas];
			

	    	ReadXml r1 = new ReadXml();
	    	try {
				dependance_task_=	r1.dependance_task();//r1.dependance_task(D�claration.name_WF_file);
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
	    	
	    	
	    	Estimation c1= new Estimation(dependance_task_, D�claration.caracteristique_task);
			//c1.generer_estimation(D�claration.tas,D�claration.nb_c, D�claration.caracteristique_task, dependance_task_);
		
		
			ET_G = c1.Calcul_ET_G(D�claration.tas,(D�claration.nb_c+D�claration.nb_f),D�claration.list_task,D�claration.list_2,D�claration.list_1);//--  Calcul de temps d'ex�cution des t�ches sur les # types de VMs
			
			//calcul de DT
			DT = c1.Calcul_DT(D�claration.tas,D�claration.list_task,D�claration.list_1, dependance_task_ ); 
			 
		 	
		 	
		    	for (int i=0; i<D�claration.tas ; i++) {
			    	for (int j=0; j<D�claration.tas; j++) {
			    		if( dependance_task_[i][j]!= 0 ) //---Il faut calculer le temps de transfert entre i et j
			    		{	
			    			DT[i][j] = (double)(D�claration.caracteristique_task[i][2]/60)/(D�claration.list_1.get(0).getBw());//--- (double) pour afficher les chiffres
			    			//--- pour r�cup�rer la valeur de bandwith, 
			
			    		}
			    	}
		    	}
		    	
	
		
		
		    	
		    	
		    	Mapp_task mt = new Mapp_task();
				Location location= new Location(loc);
				for (int d=0;d<D�claration.nb_c;d++)
					lag_vm[d]=1;
				for(int q=D�claration.nb_c;q<(D�claration.nb_c+D�claration.nb_f);q++)
					lag_vm[q]=0;
				TET_G = mt.calcul_TET_G(DT, ET_G, lag_vm, D�claration.tas, (D�claration.nb_c+D�claration.nb_f));
				
				System.out.println("***********Affichage de TET de cplex**********************");
		         for (int i=0; i<D�claration.tas; i++) {
				         for (int j=0; j<D�claration.nb_c+D�claration.nb_f; j++) {
						System.out.println("--------TET["+i+"]["+j+"]------------ :"+TET_G[i][j] +"  ");
				         }
				         System.out.println();
		         }
		         //*******************************ET affichage ***************************** 
		         System.out.println("**************afficher ET******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  for(int j=0;j<D�claration.nb_c+D�claration.nb_f;j++)
		        		  System.out.print(" ET["+i+"]["+j+"] ==========="+ET_G[i][j]+"    ");
		        	  System.out.println("  ");
		          }
		          System.out.println("**************Fin ET******************* ");
		         
		          
		          //****************************** DT affichage *****************************
		          System.out.println("**************afficher DT******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  for(int j=0;j<D�claration.tas;j++)
		        		  System.out.print(" DT["+i+"]["+j+"] ==========="+DT[i][j]+"    ");
		        	  System.out.println("  ");
		          }
		          System.out.println("**************Fin DT******************* ");
		          
		          
		          //*********************affichage temps esecution **************************
		          System.out.println("**************afficher temps execution ******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  
		        		  System.out.println(" task run ["+i+"] ==========="+D�claration.temps_execute[i]+"    ");
		        	  
		          }
		          System.out.println("**************Fin temps******************* ");
		          
		          
		          //*********************task run *****************************************
		          System.out.println("**************afficher task run******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  for(int j=0;j<D�claration.nb_c+D�claration.nb_f;j++)
		        		  System.out.print(" task_run["+i+"]["+j+"] ==========="+D�claration.task_run_[i][j]+"    ");
		        	  System.out.println("  ");
		          }
		          System.out.println("**************Fin task run ******************* ");
		          
		          //****************caracteristic task *******************************************
		          System.out.println("**************afficher caract�ristique des taches******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  for(int j=0;j<4;j++)
		        		  System.out.print(" caract["+i+"]["+j+"] ==========="+D�claration.caracteristique_task[i][j]+"    ");
		        	  System.out.println("  ");
		          }
		          System.out.println("**************Fin caracteristique task******************* ");
		          
		          //***************************dependance_task_*******************************
		          System.out.println("**************afficher dependance des taches******************* ");
		          for (int i=0;i<D�claration.tas;i++)
		          {
		        	  for(int j=0;j<D�claration.tas;j++)
		        		  System.out.print(" dep["+i+"]["+j+"] ==========="+dependance_task_[i][j]+"    ");
		        	  System.out.println("  ");
		          }
		          System.out.println("**************Fin dependance task******************* ");
		          
		          
		          
		          
		         //PSOProcess p = new PSOProcess();
		         for (int c=0;c<D�claration.nb_c;c++)
		        	 UC_VM[c]=D�claration.list_1.get(c).getCost_hour();
		         
		         for (int c=0;c<D�claration.nb_f;c++)
		        	 UC_VM[c+D�claration.nb_c]=D�claration.list_2.get(c).getCost();
			
		         //*******************************price**************************
		         System.out.println("**************afficher les prix******************* ");
		          for (int i=0;i<D�claration.nb_c+D�claration.nb_f;i++)
		          {
		        	 
		        		  System.out.println(" price["+i+"] ==========="+UC_VM[i]+"    ");
		        	  
		          }
		          System.out.println("**************Fin caracteristique task******************* ");
		          
		          
		          System.out.println("le dedline est =============="+D�claration.deadline);
		         
		         
		         Cplex_f app = new Cplex_f();
		          //Pross_cplex_FX pp = new Pross_cplex_FX();
		      	long Start_Mapping = System.currentTimeMillis(); 
	          	System.out.println("Start_Mapping =="+Start_Mapping);

	             //-----appel de la m�thode qui utilise CPLEX------
	         app.execute(TET_G, D�claration.tas,DT, ET_G, UC_VM,D�claration.deadline);   	            		         	         		       
			//pp.execute(D�claration.TET_G, D�claration.tas, DT, D�claration.ET_G, UC_VM, D�claration.deadline);
			 System.out.println("--------------Apr�s fin  p.execute--------------");
		
		});
		
		Label r = new Label();
		r.setText("R�sult");
		
		 res = new ListView<String>();
		
		Button fini = new Button() ;
		fini.setText("Finish");
		fini.setOnAction(e -> {
			window.close();
		});
		
		
		
		ex.setLayoutX(50);   ex.setMaxHeight(50);    ex.setMinHeight(50);    ex_c.setLayoutX(320);   ex_c.setMaxHeight(50);    ex_c.setMinHeight(50);
		ex.setLayoutY(50);    ex.setMaxWidth(230);   ex.setMinWidth(230);         ex_c.setLayoutY(50);    ex_c.setMaxWidth(230);   ex_c.setMinWidth(230);
		
		r.setLayoutX(50);   r.setLayoutY(150);
		
		
		
		res.setLayoutX(50);   res.setMaxHeight(500);    res.setMinHeight(500);
		res.setLayoutY(200);  res.setMaxWidth(500);   res.setMinWidth(500); 
		
		fini.setLayoutX(200);    fini.setMaxHeight(30);    fini.setMinHeight(30);
		fini.setLayoutY(750);    fini.setMaxWidth(200);   fini.setMinWidth(200); 
		
		Group g = new Group();
	    g.getChildren().addAll(ex,r,res,fini,ex_c);
	   	Scene scene = new Scene(g);
		window.setScene(scene);
		window.show();
		
		
		}

}
