package application;
import org.hana.pso.Location;
import org.hana.pso.ProblemSet;
import org.hana.pso.Velocity;

import Evaluation.ProbSet;

public class Particule_UI {

	
	



//Classe particule , contient des caractéristiques: Velocity & Location
	
		private double fitnessValue;//valeur de la fonction objective
		private Velocity velocity;
		private Location location;
		
		public Particule_UI() {
			super();
		}

		

		public Particule_UI(double fitnessValue, Velocity velocity, Location location) {
			super();
			this.fitnessValue = fitnessValue;
			this.velocity = velocity;
			this.location = location;
		
		}

		public Velocity getVelocity() {
			return velocity;
		}

		public void setVelocity(Velocity velocity) {
			this.velocity = velocity;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public double getFitnessValue_UI(double [][] TET,int n,double[][] DT,double[] UC_VM) { //ajouter TET en entrée
			
			fitnessValue = ProblemSet_UI.evaluate(location,n,TET,DT,UC_VM);
			return fitnessValue;
		}
public double getFitnessValue_pso(double [][] TET,int n,double[][] DT,double[] UC_VM) { //ajouter TET en entrée
			
			fitnessValue = ProbSet.evaluate(location,n,TET,DT,UC_VM);
			return fitnessValue;
		}
		
		
		
		
		
	

}
