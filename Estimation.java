package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.hana.pso.Fog;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.MyVm;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
public class Estimation {

	static double [][] dependance_task;
	static int [][] caracters_task;

		/** The cloudlet list. */
		private static List<Cloudlet> cloudletList;
	    /** The vmlist. */
		static List<MyVm> vmlist;
	 	
		public static ArrayList<Cloudlet> list = new ArrayList<Cloudlet>();

		 static double[] c_1_vm;           //   tableau contient le coût par heure de chaque type de VM                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		 List<Cloudlet> newList;
		 
	
		 
	
		 
		 
		//------------- Constructeur ----------------------- 
		public Estimation(double[][] dependance_tache,
				int[][] caracters_task) {
			super();
			dependance_task = dependance_tache;
			this.caracters_task = caracters_task;
					 
		}
		
	
	

		
		private static Datacenter createDatacenter(String name){

			// Here are the steps needed to create a PowerDatacenter:
			// 1. We need to create a list to store one or more
			//    Machines
			List<Host> hostList = new ArrayList<Host>();
			// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
			//    create a list to store these PEs before creating
			//    a Machine.
			List<Pe> peList1 = new ArrayList<Pe>();

			//--FF  int mips = 1000;
			   int mips = 1000000;   //--FF 
			// 3. Create PEs and add these into the list.
			//for a quad-core machine, a list of 4 PEs is required:
			peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
			peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
			peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
			peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

			//Another list, for a dual-core machine
			List<Pe> peList2 = new ArrayList<Pe>();

			peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
			peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

			//4. Create Hosts with its id and list of PEs and add them to the list of machines
			int hostId=0;
			int ram = 204800; //host memory (MB)
			long storage = 10000000; //host storage
			int bw = 100000;
			
			hostList.add(
	    			new Host(
	    				hostId,
	    				new RamProvisionerSimple(ram),
	    				new BwProvisionerSimple(bw),
	    				storage,
	    				peList1,
	    				new VmSchedulerTimeShared(peList1)
	    			)
	    		); // This is our first machine

			hostId++;

			hostList.add(
	    			new Host(
	    				hostId,
	    				new RamProvisionerSimple(ram),
	    				new BwProvisionerSimple(bw),
	    				storage,
	    				peList2,
	    				new VmSchedulerTimeShared(peList2)
	    			)
	    		); // Second machine

			// 5. Create a DatacenterCharacteristics object that stores the
			//    properties of a data center: architecture, OS, list of
			//    Machines, allocation policy: time- or space-shared, time zone
			//    and its price (G$/Pe time unit).
			String arch = "x86";      // system architecture
			String os = "Linux";          // operating system
			String vmm = "Xen";
			double time_zone = 10.0;         // time zone this resource located
			double cost = 3.0;              // the cost of using processing in this resource
			double costPerMem = 0.05;		// the cost of using memory in this resource
			double costPerStorage = 0.1;	// the cost of using storage in this resource
			double costPerBw = 0.1;			// the cost of using bw in this resource
			LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

			DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
	                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


			// 6. Finally, we need to create a PowerDatacenter object.
			Datacenter datacenter = null;
			try {
				datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
			} catch (Exception e) {
				e.printStackTrace();
			} 

			return datacenter;
		}

		//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
		//to the specific rules of the simulated scenario
		private static DatacenterBroker createBroker(){

			DatacenterBroker broker = null;
			try {
				broker = new DatacenterBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return broker;
		}
		
		
		
		
		
		
		//--FF Calcul de temps d'exécution des tâches sur les # types de VMs ------------------
	 	public static double[][] Calcul_ET(int n,int m, List<Cloudlet> list_task,List<Fog> list2,List<MyVm> liste1,int[][] task_run) {
	 		double[][] ET = new double[n][m]; 
	 		DecimalFormat dft = new DecimalFormat("0.00");
	 		for (int cc=0;cc<n;cc++)
	 			for(int bb=0;bb<m;bb++)
	 				ET[cc][bb]=0;
	 		//System.out.println(" ressource mips de cloud1 ("+liste1.get(0).getMips()+"  ) | ");
	 		//System.out.println(" ressource mips de cloud 2 ("+liste1.get(1).getMips()+"  ) | ");
	 		//System.out.print(" nobre de cloudlet   =  "+list_task.size() );
	 		//System.out.print(" cloudlet 4   ("+list_task.get(3).getCloudletLength()+"  ) | ");
	 		for (int cc=0;cc<n;cc++)
	 		{
	 			System.out.println("\n");
	 			for(int bb=0;bb<Déclaration.nb_c;bb++)
	 			{
	 				System.out.print("     "+ET[cc][bb]+"    ");
	 				System.out.print(" cloudlet length ("+list_task.get(cc).getCloudletLength()+"  ) | ");
	 				System.out.print(" ressource mips ("+liste1.get(bb).getMips()+"  ) | ");
	 				
	 				
	 			}
	 		}
	 		// for cloud ressouce
	 		for (int i=0; i<Déclaration.tas; i++) { 
	 			for (int j=0;j<Déclaration.nb_c;j++)
	 				
	 			{
	 		
			if(task_run[i][j]==1) 
			{
				
					ET[i][j] = list_task.get(i).getCloudletLength()/liste1.get(j).getMips()/60;//list.get(i).getActualCPUTime()/60; // ET[cloudlet_id][j] 
			}	
				else
				{
					
					ET[i][j] = -1;
				}
	 			}
				
				
			}	
	 		//for fog ressource
	 		for (int i=0; i<Déclaration.tas; i++) { 
	 			for (int j=Déclaration.nb_c;j<(Déclaration.nb_f+Déclaration.nb_c);j++)
	 				
	 			{
	 		
			if(task_run[i][j]==1) {
				
					ET[i][j] = list_task.get(i).getCloudletLength()/list2.get(j-Déclaration.nb_c).getMIPS()/60;}
				
				else {
					
					ET[i][j] = -1;} 
	 			}
				
				
			}	
	 		
	 		
	 		
	 		//-------------------------------------------------------
	 		
	 		for(int i=0;i<Déclaration.tas; i++)
	 			for (int k=Déclaration.nb_c;k<m;k++)
	 			if (task_run[i][k]==1)
	 				ET[i][k]=list_task.get(i).getCloudletLength()/ list2.get(k-Déclaration.nb_c).getMIPS() /60;
	 			else
	 				ET[i][k]=-1;
	 		
	 		
	 		
	 		
	 		//-------------------------------------------------------
	 		
	 		System.out.println(" ****** --------------- Calcul_ET ----------------***** ");
	     	for (int i=0; i<n ; i++) {
		    	for (int j=0; j<m; j++) {
	    	 		System.out.print(dft.format(ET[i][j])+"  ");
	    		}
		    	
	    		System.out.println();
	     	}
	    		
			return ET;
			
			 
	 }
	 	//****************calcul ET_G****************************************
 		public static double[][] Calcul_ET_G(int n,int m, List<Cloudlet> list_task,List<Fog> list2,List<MyVm> liste1) {
 			double[][] ET_G = new double[n][m];
 			for (int i=0;i<n;i++)
 			{
 				for(int j=0;j<Déclaration.nb_c;j++)
 					ET_G[i][j]= (list_task.get(i).getCloudletLength()/liste1.get(j).getMips())/60;
 				for(int k=Déclaration.nb_c;k<m;k++)
 					ET_G[i][k]= (list_task.get(i).getCloudletLength()/list2.get(k-Déclaration.nb_c).getMIPS())/60;
 			}
 			return ET_G;
 		}
	 	
	 	//--FF Calcul de temps de transfert entre les tâches : la matrice DT  ------------------
	 
	 	public static  double[][]  Calcul_DT(int n, List<Cloudlet> list, List<MyVm> vmlist, double[][] dependance_task) {
	 		double [][] DT = new double[n][n]; 
	 	//	DecimalFormat dft = new DecimalFormat("0.00");
	 	
	    	for (int i=0; i<n ; i++) {
		    	for (int j=0; j<n; j++) {
		    		if( dependance_task[i][j]!= 0 ) //---Il faut calculer le temps de transfert entre i et j
		    		{	
		    			DT[i][j] = (double)(caracters_task[i][2]/60)/(vmlist.get(0).getBw());//--- (double) pour afficher les chiffres
		    			//--- pour récupérer la valeur de bandwith, j'ai pris la valeur du 1er élèment de vmlist puisque la valeur de bw des VMs sont égaux
		    		}
	    	 	}
		   }
	 		System.out.println(" ** --------------- Calcul_DT ---------------- ** ");
	 		for (int i=0; i<n ; i++) {
		    	for (int j=0; j<n; j++) {
	    	 		System.out.print(DT[i][j]+"    ");
	    		}
	    		System.out.println();
		     }  
	 		
	 		return DT;
	 	}
	 	
	 		
	 	/**
		 * Prints the Cloudlet objects
		 * @param list  list of Cloudlets
		 */
		public static void printCloudletList(List<Cloudlet> list) {	
			int size = list.size();
			Cloudlet cloudlet;

			String indent = "    ";
			Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
					"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

			DecimalFormat dft = new DecimalFormat("###.##");
			
			for (int i = 0; i < size; i++) {
				cloudlet = list.get(i);
				Log.print(indent + cloudlet.getCloudletId() + indent + indent);

				if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
					Log.print("SUCCESS");

					Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
							indent + indent + indent + dft.format(cloudlet.getActualCPUTime()/60) +
							indent + indent + dft.format(cloudlet.getExecStartTime()/60)+ indent + indent + indent + dft.format(cloudlet.getFinishTime()/60));
				}
			}
		 
	  }
		//--------------
		  public void generer_estimation(int n,int m,int[][] caracters_task, double[][] dependance_task){
				
			
				try {
					// First step: Initialize the CloudSim package. It should be called
					// before creating any entities.
					int num_user = 1;   // number of grid users
					Calendar calendar = Calendar.getInstance();
					boolean trace_flag = false;  // mean trace events

					// Initialize the CloudSim library
					CloudSim.init(num_user, calendar, trace_flag);

					// Second step: Create Datacenters
					//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
					@SuppressWarnings("unused")
					Datacenter datacenter0 = createDatacenter("Datacenter_0");
					
					//Third step: Create Broker
					DatacenterBroker broker = createBroker();
				
					int brokerId = broker.getId();
					System.out.println("=======brokerId ============= "+ brokerId);	
					//Fourth step: Create VMs and Cloudlets and send them to broker
					
				
					
					
					//---
					
					
					
					
				
					// Fifth step: Starts the simulation
					CloudSim.startSimulation();

					newList = broker.getCloudletReceivedList();
					
		            CloudSim.stopSimulation();
		        	printCloudletList(newList); //--- Tout l'affichage sera dans la même liste (newList):pour les m=7 types de VM
		        	
		         	

	     	
		    		
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Log.printLine("The simulation has been terminated due to an unexpected error");
				} 
				
			}
		public static double[] getC_1_vm() {
			return c_1_vm;
		}
		public void setC_1_vm(double[] c_1_vm) {
			this.c_1_vm = c_1_vm;
		}  
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

