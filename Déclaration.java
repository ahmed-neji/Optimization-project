package application;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.MyVm;
import org.hana.pso.Fog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class Déclaration {
	
	static LinkedList<MyVm> list_1 = new LinkedList<MyVm>(); 
	static LinkedList<Fog> list_2 = new LinkedList<Fog>();
	static LinkedList<Machines> list = new LinkedList<Machines>();
	 public static int nb_f;
	 public static int nb_c;
	 public static String t;
	 static int id_modif;
	 static String name_WF_file;
	 static String path_workflow;
	 static int i;
	 
	 
	  
	    // parseur déclarartion
	    static  DocumentBuilderFactory factory; 
	    static DocumentBuilder builder;
	    static Document document;
	    static  Element racine ;
	    static NodeList racineNoeuds ;
	    static int nbRacineNoeuds ;
	    static Element tasks ;
	    public static  int tas;
	    static  int [][] caracteristique_task;
		static  int[] temps_execute ; // duration_constraint
		public static int[][] task_run_;
		public static LinkedList<Cloudlet> list_task = new LinkedList<Cloudlet>();
		public  static double deadline = 235;
		public static int swarm_size_=3;
		public static int MAX_ITERATION_ =3;
		public static double C1_ = 2.0;
		public static double C2_ = 2.0;
		public static double w_=0.5;
		
		 public static double[][] TET_G;
		 public static double[][] ET_G;
	    
	  

}
