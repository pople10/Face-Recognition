package appio;
	
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import Db.User;
import Db.UserInfo;
import OpenCV.FaceDetection;
import OpenCV.FaceDetectionM;
import appio.Main;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
	

public class Main01 extends Application {
	static Scene Login_Scene,Register_Scene,ChangeInfoScene,Register_Scene2,Profil;
	static Boolean isLogin=false;
	static Boolean isSignUp=false;

	@Override
	public void start(Stage UserStage) throws Exception {
	
	}
	
	public static void Login(Stage UserStage) throws Exception {
		UserStage.initStyle(StageStyle.TRANSPARENT);
		UserStage.initStyle(StageStyle.UNDECORATED);
		Login_Scene=LoginPage(UserStage,Login_Scene);
		Register_Scene=RegisterPage(UserStage, Register_Scene);
		UserStage.initStyle(StageStyle.TRANSPARENT);
		UserStage.setScene(Login_Scene);
	}
	
	public static void Register(Stage UserStage) throws Exception {
		UserStage.initStyle(StageStyle.TRANSPARENT);
		UserStage.initStyle(StageStyle.UNDECORATED);
		Login_Scene=LoginPage(UserStage,Login_Scene);
		Register_Scene=RegisterPage(UserStage, Register_Scene);
		UserStage.setScene(Register_Scene);
	}
	
	public static Scene LoginPage(Stage UserStage,Scene LoginScene) throws Exception {
		File Icon=new File("images/Logo001.png");
		UserStage.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));
		
		String StyleInp="-fx-font-size: 14;\n -fx-padding: 10,1,1,1;\n -fx-border-color: #eee;\n -fx-border-width: 1.5;\n -fx-border-radius: 1;\n -fx-border: gone;\n -fx-background-color: transparent;\n -fx-text-fill: #000;";
		String StyleBtn="-fx-font-size: 14;\n -fx-font-weight:700;\n -fx-border-color: #8033d4;\n -fx-border-width: 1.5;\n -fx-padding: 10,1,1,1;\n -fx-background-color: #8033d4;\n-fx-border-radius: 0;\n -fx-text-fill: #fff;";

		HBox rootLogin =new HBox();
		rootLogin.minWidth(800);
		VBox rootLoginV1 =new VBox();
		VBox rootLoginV2 =new VBox();
		rootLoginV2.setMinWidth(400);
		rootLoginV1.setMinWidth(400);
		rootLogin.setStyle("-fx-background-color:#fff;\n-fx-border-color: #5111ba;\n-fx-border-width:0.5;");
		HBox email =new HBox();
		HBox password =new HBox();
		HBox buttonL =new HBox();
		HBox Title =new HBox();
		VBox LeftB=new VBox();
		LoginScene=new Scene(rootLogin,800,600);
		rootLoginV1.setId("V1B");


		Button btn= new Button("LOGIN");
		btn.setMinWidth(270);
		btn.setMinHeight(45);
		btn.setStyle(StyleBtn);
		
	
		Text Txt =new Text("Welcome Back !");
		Txt.setStyle("-fx-font-size: 24;\n -fx-font-weight:700;\n-fx-text-fill:#676767");
		
		Text Txt1L =new Text("New member ?");
		Txt1L.setFill(Color.WHITE); 
		Txt1L.setFont(Font.font("Courier", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 10));
		Txt1L.setStyle("-fx-font-size: 24;\n -fx-font-weight:900;\n -fx-text-fill:#fff");
		
		Text Txt2L =new Text("Go to Sign up page & create a new account");
		Txt2L.setFill(Color.WHITE); 
		Txt2L.setStyle("-fx-font-size: 14;\n -fx-font-weight:400;\n -fx-text-fill:#fff");
		
		Button btnL= new Button("Sign Up ");
		btnL.setStyle("-fx-cursor: hand; -fx-font-size: 17; -fx-font-weight:700;  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");
		btnL.setWrapText(false);
		btnL.setMinHeight(40);
		btnL.addEventFilter(MouseEvent.MOUSE_ENTERED, e->{btnL.setStyle("-fx-cursor: hand;-fx-font-size: 18;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");});
		btnL.addEventFilter(MouseEvent.MOUSE_EXITED, e->{btnL.setStyle("-fx-cursor: hand;\n -fx-font-size: 17;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");});
		LeftB.setAlignment(Pos.BOTTOM_CENTER);
		LeftB.setStyle("-fx-text-fill: #fff;");
		LeftB.getChildren().add(Txt1L);
		LeftB.getChildren().add(Txt2L);
		LeftB.getChildren().add(btnL);
		rootLoginV1.getChildren().add(LeftB);
		

		btnL.setOnAction(e->{
				UserStage.setScene(Register_Scene);
		});

		TextField Transp = new TextField();
		Transp.setOpacity(1);
		Transp.setMinWidth(0);
		Transp.setMaxWidth(0);
		
		TextField emailInput = new TextField();
		PasswordField passwordInput =new PasswordField();
		passwordInput.setMinWidth(270);
		passwordInput.setMinHeight(35);
		passwordInput.setPromptText("Password");
		passwordInput.setStyle(StyleInp);
		emailInput.setMinWidth(270);
		emailInput.setMinHeight(35);
		emailInput.setPromptText("CIN");
		emailInput.setStyle(StyleInp);
		
		email.setAlignment(Pos.CENTER);
		password.setAlignment(Pos.CENTER);
		buttonL.setAlignment(Pos.CENTER);
		Title.setAlignment(Pos.CENTER);
		
		rootLoginV1.setAlignment(Pos.BOTTOM_CENTER);

		buttonL.getChildren().add(btn);
		email.getChildren().add(emailInput);
		password.getChildren().add(passwordInput);
		Title.getChildren().add(Txt);
		
		rootLoginV2.setMargin(email, new Insets(10));
		rootLoginV2.setMargin(password, new Insets(10));
		rootLoginV2.setMargin(buttonL, new Insets(10));
		rootLoginV2.setMargin(Title, new Insets(0,10,20,10));
		rootLoginV1.setMargin(LeftB, new Insets(0,10,60,10));
		LeftB.setMargin(btnL, new Insets(10,0,40,0));
		
		btn.setOnAction(e->{
			btn.setDisable(true);	
			isLogin=false;
			Boolean f=true;
			String error="";
					try {
						if(passwordInput.getText().length()<3) {
					    	  f=false;
					    	  error="Password is invalide\n";
					      }
					      if(emailInput.getText().length()<3) {
					    	  f=false;
					    	  error="CIN is invalide\n";
					      }
					      if(f)
					    	  isLogin = User.Login(emailInput.getText(),passwordInput.getText());
					      else {
					    	  Main.AlertP(new Stage(),error);
					    	  btn.setDisable(false);
					      }
					} catch (SQLException | IOException e1) {}
				
			if(isLogin) {
				UserStage.getOnCloseRequest()
			    .handle(
			        new WindowEvent(
			        		UserStage,
			            WindowEvent.WINDOW_CLOSE_REQUEST
			        )
			    );
				UserStage.close();
			}
			else {
				if(f) Main.AlertP(new Stage(),"CIN or Password is inncorect");
				btn.setDisable(false);	
			}
		});
		
		File rm=new File("images/xbtn-01.png");
		Image rmimg = new Image("file:/"+rm.getAbsolutePath());
		ImageView rmimgv=new ImageView();
		rmimgv.setImage(rmimg);
		rmimgv.setFitHeight(25);
		rmimgv.setFitWidth(25);
		Button remove= new Button();
		remove.setGraphic(rmimgv);
		remove.setStyle("-fx-cursor: hand;\n-fx-font-size: 18;\n -fx-font-weight:500;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: gray;");
		remove.setOnAction(e->{
			UserStage.getOnCloseRequest()
		    .handle(
		        new WindowEvent(
		        		UserStage,
		            WindowEvent.WINDOW_CLOSE_REQUEST
		        )
		    );
			UserStage.close();

		});
		HBox ReTop=new HBox();
		ReTop.setAlignment(Pos.TOP_RIGHT);
		ReTop.getChildren().add(remove);
		
		rootLoginV2.setMargin(ReTop, new Insets(5,0,120,0));

		rootLoginV2.getChildren().add(ReTop);
		rootLoginV2.getChildren().add(Title);
		rootLoginV2.getChildren().add(Transp);
		rootLoginV2.getChildren().add(email);
		rootLoginV2.getChildren().add(password);
		rootLoginV2.getChildren().add(buttonL);
		
	    Class<?> classe=new Object(){}.getClass().getEnclosingClass();

		LoginScene.getStylesheets().add(classe.getResource("application.css").toExternalForm());

		rootLogin.getChildren().add(rootLoginV1);
		rootLogin.getChildren().add(rootLoginV2);
		return LoginScene;	
	}
	
	public static Scene RegisterPage(Stage UserStage,Scene LoginScene) throws Exception{
		File Icon=new File("images/Logo001.png");
		UserStage.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));
		String StyleInp="-fx-font-size: 14;\n -fx-padding: 10,1,1,1;\n -fx-border-color: #eee;\n -fx-border-width: 1.5;\n -fx-border-radius: 1;\n -fx-border: gone;\n -fx-background-color: transparent;\n -fx-text-fill: #000;";
		String StyleBtn="-fx-font-size: 14;\n -fx-font-weight:700;\n -fx-border-color: #8033d4;\n -fx-border-width: 1.5;\n -fx-padding: 10,1,1,1;\n -fx-background-color: #8033d4;\n-fx-border-radius: 0;\n -fx-text-fill: #fff;";
		
		HBox rootLogin =new HBox();
		rootLogin.minWidth(800);
		
		VBox rootLoginV1 =new VBox();
		VBox rootLoginV2 =new VBox();

		rootLoginV2.setMinWidth(400);
		rootLoginV1.setMinWidth(400);
		
		rootLogin.setStyle("-fx-background-color:#fff;\n-fx-border-color: #5111ba;\n-fx-border-width:0.5;");
		
		HBox CIN =new HBox();
		HBox email =new HBox();
		HBox Name =new HBox();
		HBox password =new HBox();
		HBox buttonL =new HBox();
		HBox Title =new HBox();
		VBox LeftB=new VBox();
		LoginScene=new Scene(rootLogin,800,600);
		rootLoginV1.setId("V1B");
		
	    Class<?> classe=new Object(){}.getClass().getEnclosingClass();
		LoginScene.getStylesheets().add(classe.getResource("application.css").toExternalForm());

		Button btn= new Button("SIGN UP");
	
		btn.setMinWidth(270);
		btn.setMinHeight(45);
		btn.setStyle(StyleBtn);
		
		Text Txt =new Text("Sign Up");
		Txt.setStyle("-fx-font-size: 24;\n -fx-font-weight:700;\n-fx-text-fill:#676767");
		
		Text Txt1L =new Text("Already a member ?");
		Txt1L.setFill(Color.WHITE); 
		Txt1L.setFont(Font.font("Courier", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 10));
		Txt1L.setStyle("-fx-font-size: 22;\n -fx-font-weight:900;\n -fx-text-fill:#fff");
		
		Text Txt2L =new Text("Go to Log in page & enjoy our services");
		Txt2L.setFill(Color.WHITE); 
		Txt2L.setStyle("-fx-font-size: 14;\n -fx-font-weight:400;\n -fx-text-fill:#fff");
		
		Button btnL= new Button("Log in ");
		btnL.setStyle("-fx-cursor: hand;\n-fx-font-size: 17;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");
		
		LeftB.setAlignment(Pos.BOTTOM_CENTER);
		LeftB.setStyle("-fx-text-fill: #fff;");
		LeftB.getChildren().add(Txt1L);
		LeftB.getChildren().add(Txt2L);
		LeftB.getChildren().add(btnL);
		rootLoginV1.getChildren().add(LeftB);

		
		TextField Transp = new TextField();
		Transp.setOpacity(1);
		Transp.setMinWidth(0);
		Transp.setMaxWidth(0);
		
		
		PasswordField passwordInput =new PasswordField();
		passwordInput.setMinWidth(270);
		passwordInput.setMinHeight(35);
		passwordInput.setPromptText("Mot de passe");
		passwordInput.setStyle(StyleInp);
		
		TextField emailInput = new TextField();
		emailInput.setMinWidth(270);
		emailInput.setMinHeight(35);
		emailInput.setPromptText("Email");
		emailInput.setStyle(StyleInp);
		
		TextField Fname = new TextField();
		Fname.setMinWidth(100);
		Fname.setMaxWidth(125);
		Fname.setMinHeight(35);
		Fname.setPromptText("First Name");
		Fname.setStyle(StyleInp);
		
		TextField Lname = new TextField();
		Lname.setMinWidth(125);
		Lname.setMaxWidth(125);
		Lname.setMinHeight(35);
		Lname.setPromptText("Last Name");
		Lname.setStyle(StyleInp);
		
		TextField CINInput = new TextField();
		CINInput.setMinWidth(270);
		CINInput.setMinHeight(35);
		CINInput.setPromptText("CIN");
		CINInput.setStyle(StyleInp);
		
		
		CIN.setAlignment(Pos.CENTER);
		Name.setAlignment(Pos.CENTER);
		email.setAlignment(Pos.CENTER);
		password.setAlignment(Pos.CENTER);
		buttonL.setAlignment(Pos.CENTER);
		Title.setAlignment(Pos.CENTER);
		
		rootLoginV1.setAlignment(Pos.BOTTOM_CENTER);
		
		CIN.getChildren().add(CINInput);
		buttonL.getChildren().add(btn);
		email.getChildren().add(emailInput);
		password.getChildren().add(passwordInput);
		Name.getChildren().add(Fname);
		Name.getChildren().add(Lname);
		Title.getChildren().add(Txt);
		
		rootLoginV2.setMargin(Name, new Insets(10));
		rootLoginV2.setMargin(email, new Insets(10));
		rootLoginV2.setMargin(password, new Insets(10));
		rootLoginV2.setMargin(CIN, new Insets(10));
		rootLoginV2.setMargin(buttonL, new Insets(10));
		rootLoginV2.setMargin(Title, new Insets(0,10,10,10));
		rootLoginV1.setMargin(LeftB, new Insets(0,10,60,10));
		LeftB.setMargin(btnL, new Insets(10,0,40,0));
		Name.setMargin(Lname, new Insets(0,0,0,20));

		
		File rm=new File("images/xbtn-01.png");
		Image rmimg = new Image("file:/"+rm.getAbsolutePath());
		ImageView rmimgv=new ImageView();
		rmimgv.setImage(rmimg);
		rmimgv.setFitHeight(25);
		rmimgv.setFitWidth(25);
		Button remove= new Button();
		remove.setGraphic(rmimgv);
		remove.setStyle("-fx-cursor: hand;\n-fx-font-size: 18;\n -fx-font-weight:500;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: gray;");
		remove.setOnAction(e->{
			UserStage.getOnCloseRequest()
		    .handle(
		        new WindowEvent(
		        		UserStage,
		            WindowEvent.WINDOW_CLOSE_REQUEST
		        )
		    );
			UserStage.close();
		});
		HBox ReTop=new HBox();
		ReTop.setAlignment(Pos.TOP_RIGHT);
		ReTop.getChildren().add(remove);
		

		
		btn.setOnAction(e->{
			btn.setDisable(true);	
			isSignUp=false;
			Boolean f=true;
			String error="";
			
			
			try {
			      Matcher emailRegex =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(emailInput.getText());
			      if(passwordInput.getText().length()<5) {
			    	  f=false;
			    	  error="Invalid Password\n";
			      }
			      if(!emailRegex.find()) {
			    	  f=false;
			    	  error="Invalid Email\n";
			      }
			     
			      if(CINInput.getText().length()<3) {
			    	  f=false;
			    	  error="Invalid CIN\n";
			      }
	
			      if(Lname.getText().length()<3) {
			    	  f=false;
			    	  error="Invalid Last name\n";
			      }
			      if(Fname.getText().length()<3) {
			    	  f=false;
			    	  error="Invalid First name\n";
			      }
			      
			      if(f) {
						isSignUp=User.SignUp(CINInput.getText(), Fname.getText(), Lname.getText(), emailInput.getText(), passwordInput.getText());
			      }
			      else {
						Main.AlertP(new Stage(),error);
						btn.setDisable(false);	
			      }
			} catch (SQLException  e1) {
				String error_sql;
				if(e1.toString().contains("Duplicate entry"))
					error_sql="You are already registred";
				else
					error_sql="Something went wrong";
				Main.AlertP(new Stage(),error_sql);
				btn.setDisable(false);
				}
			catch(IOException e1) {Main.AlertP(new Stage(),"Something went wrong");btn.setDisable(false);}
			if(isSignUp) {
            	UserStage.setScene(LoadP(800,600));

				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
								@Override
								public void run() {
						            Platform.runLater(() -> {
										UserStage.setScene(NextPage(UserStage, CINInput.getText(), Fname.getText(), Lname.getText()));
						            });
								}
							};
							
				timer.schedule(task, 2500);	
			}

			
		});
		btnL.setMinHeight(40);
		btnL.addEventFilter(MouseEvent.MOUSE_ENTERED, e->{btnL.setStyle("-fx-cursor: hand;-fx-font-size: 18;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");});
		btnL.addEventFilter(MouseEvent.MOUSE_EXITED, e->{btnL.setStyle("-fx-cursor: hand;\n -fx-font-size: 17;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #fff;");});
		
		

		btnL.setOnAction(e->{
				UserStage.setScene(Login_Scene);
			});
		
		rootLoginV2.setMargin(ReTop, new Insets(5,0,80,0));

		rootLoginV2.getChildren().add(ReTop);
		rootLoginV2.getChildren().add(Title);
		rootLoginV2.getChildren().add(Transp);
		rootLoginV2.getChildren().add(Name);
		rootLoginV2.getChildren().add(CIN);
		rootLoginV2.getChildren().add(email);
		rootLoginV2.getChildren().add(password);
		rootLoginV2.getChildren().add(buttonL);

		rootLogin.getChildren().add(rootLoginV1);
		rootLogin.getChildren().add(rootLoginV2);
		return LoginScene;
		
	}
	public static boolean Age(String AgeN) {
	    if (AgeN == null) {
	        return false;
	    }
	    try {
	        double d = Integer.parseInt(AgeN);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	public static boolean Height(String AgeN) {
	    if (AgeN == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(AgeN);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static Scene NextPage(Stage UserStage,String CIN,String fname,String lname) {
		String stCSS="-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-background-color: #fff; -fx-text-fill: #5111ba;";
		VBox root=new VBox();

		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color:#fff;-fx-border-color: #5111ba;");
		Register_Scene2=new Scene(root,800,600);
		HBox Element1=new HBox();
		Element1.setAlignment(Pos.CENTER);
		
		TextField Job=new TextField();
		Job.setMinWidth(250);
		Job.setMinHeight(45);
		Job.setPromptText("Job"); 
		Job.setStyle(stCSS);
		
		TextField Nationality=new TextField();
		Nationality.setMinWidth(250);
		Nationality.setMinHeight(45);
		Nationality.setPromptText("Nationality");
		Nationality.setStyle(stCSS);
				
		Element1.getChildren().add(Job);
		Element1.getChildren().add(Nationality);
		Element1.setMargin(Job, new Insets(10,30,10,0));
		
		HBox Element2=new HBox();
		Element2.setAlignment(Pos.CENTER);
		
		TextArea Adress=new TextArea();
		Adress.setMaxWidth(530);
		Adress.setMaxHeight(125);
		Adress.setPromptText("Adress");
		Adress.setStyle(stCSS);
		
		
				
		Element2.getChildren().add(Adress);
		Element2.setMargin(Adress, new Insets(10,0,10,0));

		VBox Element3=new VBox();
		Element2.setAlignment(Pos.CENTER);
		Element3.setStyle(stCSS);
		Element3.setMaxWidth(250);
		Element3.setMinWidth(250);
		Element3.setMaxHeight(120);
		Element3.setMinHeight(120);
		Label txt1=new Label("Gender");
		txt1.setStyle("-fx-text-fill: #5111ba;\n-fx-font-size:16;\n-fx-font-weight:600;");

		ToggleGroup rdio = new ToggleGroup();
		VBox radioBox=new VBox();
		RadioButton male = new RadioButton("Male");
		male.setToggleGroup(rdio);

		RadioButton female = new RadioButton("Female");
		female.setToggleGroup(rdio);
		
		Element3.getChildren().add(txt1);
		radioBox.getChildren().add(female);
		radioBox.getChildren().add(male);
		Element3.getChildren().add(radioBox);
	    Class<?> classe=new Object(){}.getClass().getEnclosingClass();
		radioBox.getStylesheets().add(classe.getResource("Application.css").toExternalForm());
		radioBox.setMargin(female, new Insets(0,0,5,20));
		radioBox.setMargin(male, new Insets(0,0,10,20));
		Element3.setMargin(radioBox, new Insets(10,0,10,0));
		Element3.setMargin(txt1, new Insets(5,4,0,4));
		HBox Element3_01=new HBox();
		Element3_01.setAlignment(Pos.CENTER);
		
		TextField Age=new TextField();
		Age.setMinWidth(250);
		Age.setMinHeight(45);
		Age.setPromptText("Age");
		Age.setStyle(stCSS);
		
		TextField Height=new TextField();
		Height.setMinWidth(250);
		Height.setMinHeight(45);
		Height.setPromptText("Height");
		Height.setStyle(stCSS);
		
		VBox AGH=new VBox();
		AGH.getChildren().add(Age);
		AGH.getChildren().add(Height);

		AGH.setMargin(Height, new Insets(30,0,0,0));

		Element3_01.getChildren().add(Element3);
		Element3_01.getChildren().add(AGH);

		Element3_01.setMargin(Element3, new Insets(10,30,10,0));
		Element3_01.setMargin(AGH, new Insets(10,0,10,0));

		Button Images=new Button("Add at least 5 images of your face");
		Images.setMinWidth(530);
		Images.setMinHeight(50);
		Images.setStyle(stCSS+"-fx-text-fill: #5111ba;");
		
		
		
		TextField Transp = new TextField();
		Transp.setOpacity(1);
		Transp.setMinWidth(0);
		Transp.setMaxWidth(0);
		
		HBox SaveC=new HBox();
		Button Svaebtn=new Button("Save");
		Svaebtn.setMinWidth(120);
		Svaebtn.setMinHeight(45);
		Svaebtn.setStyle("-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-background-color: #5111ba; -fx-text-fill: #fff;");
		
		SaveC.getChildren().add(Svaebtn);
		SaveC.setAlignment(Pos.BOTTOM_RIGHT);
		SaveC.setMargin(Svaebtn, new Insets(40,130,0,0));
		
		root.getChildren().add(Transp);
		root.getChildren().add(Element1);
		root.getChildren().add(Element2);
		root.getChildren().add(Element3_01);
		root.getChildren().add(Images);
		root.getChildren().add(SaveC);

		root.setMargin(Images, new Insets(10,0,0,0));
		
		FileChooser filechooser = new FileChooser();
		final ArrayList<String> ImagePaths = new ArrayList<String>();
		
		Images.setOnAction(e->{
			FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
			filechooser.getExtensionFilters().add(imageFilter);
			filechooser.setTitle("Add 5 Images");
			List<File> selected = filechooser.showOpenMultipleDialog(UserStage);
			for(int i=0; i<selected.size();i++) {
				ImagePaths.add(selected.get(i).getPath());
			}
		
			
		});
		Svaebtn.setOnAction(e->{
			Svaebtn.setDisable(true);
			boolean f=true;
			String S="";
			FaceDetectionM FDM=new FaceDetectionM();
			if(!FDM.ShowListImages(ImagePaths)) {
				f=false;
				S="insufficient number of photos";
			}
			if(Height.getText().length()<2||!Height(Age.getText())) {
				f=false;
				S="invalid Height !";
			}
			if(Age.getText().length()>3||!Age(Age.getText())) {
				f=false;
				S="invalid Age !";
			}
			if(!male.isSelected()&&!female.isSelected()) {
				f=false;
				S="Invalid Geneder !";
			}
			if(Adress.getText().length()<3) {
				f=false;
				S="Invalid Adress !";
			}
			if(Nationality.getText().length()<3) {
				f=false;
				S="Invalid Nationality !";
			}
			if(Job.getText().length()<3) {
				f=false;
				S="Invalid Job !";
			}
			if(!f) {
				Main.AlertP(new Stage(), S);
			}
			else {
				String g=male.isSelected() ?"M":"F";

				

				
				FDM.ReadImagesConvertToJson(ImagePaths);
				int x=FDM.SaveImagesInJson(CIN);
				try {
					UserInfo.InsertUserInfo(x,CIN, fname, lname, Job.getText(), Nationality.getText(), Adress.getText(), g,Integer.parseInt(Age.getText()) , Double.parseDouble(Height.getText()));
				} catch (NumberFormatException | SQLException | IOException e1) {
					e1.printStackTrace();
				}
				UserStage.getOnCloseRequest()
			    .handle(
			            new WindowEvent(
			            	UserStage,
			                WindowEvent.WINDOW_CLOSE_REQUEST
			            )
			        );
				UserStage.close();

			}
			Svaebtn.setDisable(false);
		});

		return Register_Scene2;
		
	}
	public static Scene ProfilS(Stage UserStage,String Cin)  throws Exception{
	UserStage.initStyle(StageStyle.TRANSPARENT);
	File Icon=new File("images/Logo001.png");
	UserStage.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));
	String stCSS="-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-background-color: #fff; -fx-text-fill: #5111ba;";
	
	String CIN="";
	try {
		File file_User = new File("src/User.txt");
		BufferedReader r=new BufferedReader(new FileReader("src/User.txt"));
		CIN=r.readLine();
	}catch(Exception e) {}
	
	ArrayList arr=new ArrayList();
	try {
		arr = UserInfo.getIn(Cin);
	} catch (SQLException e2) {
		e2.printStackTrace();
	}
		VBox root=new VBox();
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color:#fff;-fx-border-color: #5111ba;");
		Scene Profils=new Scene(root,800,550);
		HBox Element1=new HBox();
		Element1.setAlignment(Pos.CENTER);
		
		File fsL=new File("images/"+arr.get(7)+".png");
		Image imagesL = new Image("file:/"+fsL.getAbsolutePath());
		ImageView G=new ImageView();
		G.setImage(imagesL);
		G.setFitHeight(125);
		G.setFitWidth(125);
		HBox Img=new HBox();
		Img.setStyle("-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-border-width: 1;");
		Img.getChildren().add(G);
		
		
		VBox FullN=new VBox();
		
		HBox FN=new HBox();
		Label FNl=new Label("First Name : ");
		FN.setAlignment(Pos.CENTER);
		
		TextField FNi=new TextField();
		FNi.setMinWidth(200);
		FNi.setMinHeight(50);
		FNi.setText((String) arr.get(0));
		FNi.setStyle(stCSS);
		
		FN.getChildren().add(FNl);
		FN.getChildren().add(FNi);
		
		HBox LN=new HBox();
		Label LNl=new Label("Last Name : ");
		LN.setAlignment(Pos.CENTER);
		
		TextField LNi=new TextField();
		LNi.setMinWidth(200);
		LNi.setMinHeight(50);
		LNi.setText((String) arr.get(1));
		LNi.setStyle(stCSS);
		
		LN.getChildren().add(LNl);
		LN.getChildren().add(LNi);
		 
		
		FullN.getChildren().add(FN);
		FullN.getChildren().add(LN);
		
		FullN.setMargin(LN, new Insets(25,0,0,0));
		TextArea Adress=new TextArea();
		Adress.setMaxWidth(200);
		Adress.setMaxHeight(108);
		Adress.setText((String) arr.get(2));
		Adress.setStyle(stCSS);
		Adress.setEditable(false);
		VBox AdrV=new VBox();
		
		Label AdrL=new Label("Adress : ");
		AdrL.setMinHeight(17);
		AdrL.setMaxHeight(17);
		
		AdrV.getChildren().add(AdrL);
		AdrV.getChildren().add(Adress);

		Element1.getChildren().add(Img);
		Element1.getChildren().add(FullN);
		Element1.getChildren().add(AdrV);
		
		Element1.setMargin(Img, new Insets(15));
		Element1.setMargin(FullN, new Insets(15));
		Element1.setMargin(AdrV, new Insets(15));
		
		HBox Element2=new HBox();
		Element2.setAlignment(Pos.CENTER);
		
		Label jbL=new Label("Job : ");

		TextField Job=new TextField();
		Job.setMinWidth(300);
		Job.setMinHeight(45);
		Job.setText((String) arr.get(3)); 
		Job.setStyle(stCSS);
		
		Label Nat=new Label("Nationality : ");

		TextField NATi=new TextField();
		NATi.setMinWidth(300);
		NATi.setMinHeight(45);
		NATi.setText((String) arr.get(4));
		NATi.setStyle(stCSS);
		
		VBox JV=new VBox();
		JV.getChildren().add(Nat);
		JV.getChildren().add(NATi);
		JV.getChildren().add(jbL);
		JV.getChildren().add(Job);

		JV.setMargin(Nat, new Insets(0,0,5,0));
		JV.setMargin(jbL, new Insets(15,0,5,0));
		

		
		Label Agel=new Label("Age : ");

		TextField Agei=new TextField();
		Agei.setMinWidth(250);
		Agei.setMinHeight(55);
		Agei.setText((String) arr.get(5)); 
		Agei.setStyle(stCSS);
		
		Label Hl=new Label("Height : ");
		Agel.setMinWidth(45);
		Agel.setMaxWidth(45);
		Hl.setMinWidth(45);
		Hl.setMaxWidth(45);

		TextField Hi=new TextField();
		Hi.setMinWidth(250);
		Hi.setMinHeight(55);
		Hi.setText((String) arr.get(6));
		Hi.setStyle(stCSS);
		
		HBox agh=new HBox();
		HBox Heh=new HBox();
		agh.setAlignment(Pos.CENTER);
		Heh.setAlignment(Pos.CENTER);
		agh.getChildren().add(Agel);
		agh.getChildren().add(Agei);
		Heh.getChildren().add(Hl);
		Heh.getChildren().add(Hi);
		
		
		VBox AHV=new VBox();
		AHV.getChildren().add(agh);
		AHV.getChildren().add(Heh);
		
		AHV.setMargin(agh, new Insets(10,20,10,20));
		AHV.setMargin(Heh, new Insets(20));


		JV.setMargin(Nat, new Insets(0,0,5,0));
		JV.setMargin(jbL, new Insets(10,0,5,0));
		
		
		Element2.getChildren().add(JV);
		Element2.getChildren().add(AHV);

		Element2.setMargin(JV, new Insets(20));
		Element2.setMargin(AHV, new Insets(20));
		Element2.setMaxWidth(630);
		Element1.setMinWidth(630);

		Adress.setEditable(false);
		LNi.setEditable(false);
		FNi.setEditable(false);
		Job.setEditable(false);
		NATi.setEditable(false);
		Agei.setEditable(false);
		Hi.setEditable(false);

		TextField Transp = new TextField();
		Transp.setOpacity(1);
		Transp.setMinWidth(0);
		Transp.setMaxWidth(0);
		File rm=new File("images/xbtn-01.png");
		Image rmimg = new Image("file:/"+rm.getAbsolutePath());
		ImageView rmimgv=new ImageView();
		rmimgv.setImage(rmimg);
		rmimgv.setFitHeight(25);
		rmimgv.setFitWidth(25);
		Button remove= new Button();
		remove.setGraphic(rmimgv);
		remove.setStyle("-fx-cursor: hand;\n-fx-font-size: 18;\n -fx-font-weight:500;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: gray;");
		remove.setOnAction(e->{
			UserStage.getOnCloseRequest()
		    .handle(
		        new WindowEvent(
		        		UserStage,
		            WindowEvent.WINDOW_CLOSE_REQUEST
		        )
		    );
			UserStage.close();

		});
		
		HBox Tp=new HBox();
		Tp.getChildren().add(Transp);
		Tp.getChildren().add(remove);
		Tp.setAlignment(Pos.TOP_RIGHT);
		Tp.setMinHeight(80);
		
		Button Edit=new Button("Edit");
		Edit.setMinWidth(120);
		Edit.setMinHeight(45);
		Edit.setStyle("-fx-cursor: hand;-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-border-width: 1.2;-fx-background-color: #fff; -fx-text-fill: #5111ba;");
		
		Button Save=new Button("Save");
		Save.setMinWidth(70);
		Save.setMinHeight(45);
		Save.setStyle("-fx-cursor: hand;-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-border-width: 0;-fx-background-color: #5111ba; -fx-text-fill: #fff;");
		
		Button Cancel=new Button("Cancel");
		Cancel.setMinWidth(70);
		Cancel.setMinHeight(45);
		Cancel.setStyle("-fx-cursor: hand;-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-border-width: 1.2;-fx-background-color: #fff; -fx-text-fill: #5111ba;");
		
		HBox btm=new HBox();
		btm.setAlignment(Pos.BOTTOM_RIGHT);
		btm.getChildren().add(Cancel);
		btm.getChildren().add(Save);
		btm.getChildren().add(Edit);
		btm.setMinHeight(80);
	    Save.setVisible(false);
		Cancel.setVisible(false);
		
		btm.setMargin(Edit, new Insets(0,50,0,0));
		btm.setMargin(Save, new Insets(0,140,0,20));

		if(!CIN.equals(Cin))Edit.setVisible(false);
		
		Edit.setOnAction(e->{
			UserStage.setScene(LoadP(800,550));
			Save.setVisible(true);
			Cancel.setVisible(true);
			Edit.setVisible(false);
			Adress.setEditable(true);
			LNi.setEditable(true);
			FNi.setEditable(true);
			Job.setEditable(true);
			NATi.setEditable(true);
			Agei.setEditable(true);
			Hi.setEditable(true);
				
			
			
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
							@Override
							public void run() {
					            Platform.runLater(() -> {
									UserStage.setScene(Profils);
					            });
							}
						};
						
			timer.schedule(task, 2000);	

		});
		Cancel.setOnAction(e->{
			try {
				ArrayList ar=UserInfo.getIn(Cin);
				LNi.setText((String) ar.get(1));
				FNi.setText((String) ar.get(0));
				Job.setText((String) ar.get(3));
				Adress.setText((String) ar.get(2));
				NATi.setText((String) ar.get(4));
				Agei.setText((String) ar.get(5));
				Hi.setText((String) ar.get(6));
			} catch (SQLException e1) {}
			UserStage.setScene(LoadP(800,550));
			Save.setVisible(false);
			Cancel.setVisible(false);
			Edit.setVisible(true);
			Adress.setEditable(false);
			LNi.setEditable(false);
			FNi.setEditable(false);
			Job.setEditable(false);
			NATi.setEditable(false);
			Agei.setEditable(false);
			Hi.setEditable(false);
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
							@Override
							public void run() {
					            Platform.runLater(() -> {
									UserStage.setScene(Profils);
					            });
							}
						};
						
			timer.schedule(task, 2000);	

		});
		
		Save.setOnAction(e->{
			Save.setDisable(true);
			Platform.runLater(()->{
			boolean f=true;
			String S="";
			if(Hi.getText().length()<2||!Height(Hi.getText())) {
				f=false;
				S="invalid Height !";
			}
			if(Agei.getText().length()>3||!Age(Agei.getText())) {
				f=false;
				S="invalid Age !";
			}
			if(Adress.getText().length()<3) {
				f=false;
				S="Invalid Adress !";
			}
			if(NATi.getText().length()<3) {
				f=false;
				S="Invalid Nationality !";
			}
			if(Job.getText().length()<3) {
				f=false;
				S="Invalid Job !";
			}
			if(FNi.getText().length()<3) {
				f=false;
				S="Invalid First Name !";
			}
			if(LNi.getText().length()<3) {
				f=false;
				S="Invalid Last Name !";
			}
			if(!f) {
				Main.AlertP(new Stage(), S);
			}
			else {
				try {
					UserInfo.Update(Cin, FNi.getText(), LNi.getText(), Job.getText(), NATi.getText(), Adress.getText(), Integer.parseInt(Agei.getText()), Integer.parseInt(Hi.getText()));
					}
				catch (SQLException e1) {}
			}
			try {
				ArrayList ar=UserInfo.getIn(Cin);
				LNi.setText((String) ar.get(1));
				FNi.setText((String) ar.get(0));
				Job.setText((String) ar.get(3));
				Adress.setText((String) ar.get(2));
				NATi.setText((String) ar.get(4));
				Agei.setText((String) ar.get(5));
				Hi.setText((String) ar.get(6));
			} catch (SQLException e1) {}
			UserStage.setScene(LoadP(800,550));
			Save.setVisible(false);
			Cancel.setVisible(false);
			Edit.setVisible(true);
			Adress.setEditable(false);
			LNi.setEditable(false);
			FNi.setEditable(false);
			Job.setEditable(false);
			NATi.setEditable(false);
			Agei.setEditable(false);
			Hi.setEditable(false);
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
							@Override
							public void run() {
					            Platform.runLater(() -> {
									UserStage.setScene(Profils);
					            });
							}
						};
						
			timer.schedule(task, 1000);	

			});
			Save.setDisable(false);

			
		});
		
		root.getChildren().add(Tp);
		root.getChildren().add(Element1);
		root.getChildren().add(Element2);
		root.getChildren().add(btm);
		root.setMargin(Element2, new Insets(0,0,0,22));
		UserStage.setScene(Profils);
		return Profils;
	}
	
	public static Scene LoadP(int x,int y) {
		HBox Loadroot=new HBox();
		Loadroot.setStyle("-fx-font-size: 15;-fx-border-color: #5111ba;-fx-border-radius: 0;-fx-background-color: #fff; -fx-text-fill: #5111ba;");
		Scene Load = new Scene(Loadroot,x,y);
		HBox LoadHb=new HBox();
		File Loadf=new File("images/Load.png");
		Image imagesLoad = new Image("file:/"+Loadf.getAbsolutePath());
		ImageView LoadIm=new ImageView();
		LoadIm.setImage(imagesLoad);
		LoadIm.setFitHeight(10);
		LoadIm.setFitWidth(10);
		LoadIm.fitHeightProperty().bind(Load.heightProperty().divide(1.0));
		LoadIm.fitWidthProperty().bind(Load.heightProperty().divide(1.0));
		LoadHb.getChildren().add(LoadIm);
		Loadroot.getChildren().add(LoadHb);
		Loadroot.setAlignment(Pos.CENTER);
		
		RotateTransition LoadRt = new RotateTransition(Duration.millis(7000), LoadIm);
		LoadRt.setByAngle(3600);
		LoadRt.setCycleCount(Timeline.INDEFINITE);
		LoadRt.play();
		return Load;
	}
	
}