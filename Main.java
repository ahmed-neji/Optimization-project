package application;
	
import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
//import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;



public class Main extends Application {
	Stage window ;
	Button bt1;
	Button bt2;
		
	public static void main(String[] args) {
		launch();
	}
		
		public void start(Stage primaryStage) throws Exception {
			
			 window = primaryStage;
			window.setTitle("PSO program");
			bt1 =new Button("Next");
			bt2 =new Button("Close");
			
			Label ff=new Label();
			ff.setText("Choose Your WorkFlow : ");
			
			Label nn=new Label();
			nn.setText("WorkFlow Name :  ");
			
			Label dead = new Label();                TextField dead_t= new TextField();
			dead.setText("Deadline : ");             dead_t.setText("200");
			
			Label swarm = new Label();                TextField swarm_t= new TextField();
			swarm.setText("Swarm size : ");             swarm_t.setText("3");
			
			Label iter = new Label();                TextField iter_t= new TextField();
			iter.setText("Iteration Number : ");             iter_t.setText("3");
			
			Label c1 = new Label();                TextField c1_t= new TextField();
			c1.setText("C1 : ");                   c1_t.setText("2.0");
			
			Label c2 = new Label();                TextField c2_t= new TextField();
			c2.setText("C2 : ");                   c2_t.setText("2.0");
			
			Label w = new Label();                TextField w_t= new TextField();
			w.setText("W : ");                    w_t.setText("0.5");
			
			
			
			
			
			
			
			TextField name = new TextField();
			TextField path = new TextField();
			
			
			Button brow = new Button();
			brow.setText("Import");
			brow.setOnAction(e -> {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("E:\\worksapce\\projet_fx_1\\src\\workflow"));
				File seletedFile = fc.showOpenDialog(null);
				if(seletedFile != null)
				{
					name.setText(seletedFile.getName());
					path.setText(seletedFile.getAbsolutePath());
				}
			});
			
			
			
			bt1.setOnAction(e -> {
				//Page4.display("Ressources");
				Page2.display("Ressources","List of Ressources");
				Déclaration.name_WF_file=name.getText();
				Déclaration.path_workflow=path.getText();
				
				
				
			     Déclaration.deadline=Integer.parseInt(dead_t.getText());
			     Déclaration.swarm_size_=Integer.parseInt(swarm_t.getText());
			     Déclaration.MAX_ITERATION_=Integer.parseInt(iter_t.getText());
			     Déclaration.C1_=Double.parseDouble(c1_t.getText());
			     Déclaration.C2_=Double.parseDouble(c2_t.getText());
			     Déclaration.w_=Double.parseDouble(w_t.getText());
			     //System.out.println(Déclaration.tas);
			   
			
			

				
				window.close();
				
				
					});
		
			
			
			bt2.setOnAction(e -> 
			{
				window.close();
		
			});
			
			
			ff.setLayoutX(70);    brow.setLayoutX(270);  brow.setMaxWidth(200);   brow.setMinWidth(200);
			ff.setLayoutY(50);    brow.setLayoutY(45);
			
			name.setLayoutX(190);  name.setMaxWidth(250);    name.setMinWidth(250);
			name.setLayoutY(95);
			
			nn.setLayoutX(50);
			nn.setLayoutY(100);
			
			dead.setLayoutX(50);        dead_t.setLayoutX(190); dead_t.setLayoutY(135); dead_t.setMaxWidth(250);  dead_t.setMinWidth(250); 
			dead.setLayoutY(140);
			
			
			swarm.setLayoutX(50);        swarm_t.setLayoutX(190); swarm_t.setLayoutY(175); swarm_t.setMaxWidth(250);  swarm_t.setMinWidth(250); 
			swarm.setLayoutY(180);
			
			iter.setLayoutX(50);        iter_t.setLayoutX(190); iter_t.setLayoutY(215); iter_t.setMaxWidth(250);  iter_t.setMinWidth(250); 
			iter.setLayoutY(220);
			
			c1.setLayoutX(50);        c1_t.setLayoutX(190); c1_t.setLayoutY(255); c1_t.setMaxWidth(250);  c1_t.setMinWidth(250); 
			c1.setLayoutY(260);
			
			c2.setLayoutX(50);        c2_t.setLayoutX(190); c2_t.setLayoutY(295); c2_t.setMaxWidth(250);  c2_t.setMinWidth(250); 
			c2.setLayoutY(300);
			
			w.setLayoutX(50);        w_t.setLayoutX(190); w_t.setLayoutY(335); w_t.setMaxWidth(250);  w_t.setMinWidth(250); 
			w.setLayoutY(340);
		
			
			
			bt1.setLayoutX(450);  bt1.setMaxWidth(100);   bt1.setMinWidth(100); 
			bt1.setLayoutY(300);
			bt2.setLayoutX(50);   bt2.setMaxWidth(100);   bt2.setMinWidth(100); 
			bt2.setLayoutY(300);
			Group g = new Group();
		    g.getChildren().addAll(bt1,bt2,name,brow,ff,nn,dead,dead_t,swarm,swarm_t,iter,iter_t/*,c1,c1_t,c2,c2_t,w,w_t*/);
		    Scene scene = new Scene(g,600,350);
			//StackPane layout = new StackPane();
			//layout.getChildren().add(bt1);
			//Scene scene = new Scene(layout,800,400);
			
			window.setScene(scene);
			
			window.show();
			
		}

		
	}

