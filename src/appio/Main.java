package appio;
	
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
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import Db.UserInfo;
import OpenCV.FaceDetection;
import OpenCV.FaceRecognition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
	

public class Main extends Application {
	Scene Splash;
	static Scene scene,Load,LoadCamera,ListData,Lastscene,UserLoad;
	static Boolean istak=false;
	private static ImageView LiveView=new ImageView();
	private static boolean isC=false;
	static boolean Saveit=false;
	@Override
	public void start(Stage primaryStage) throws SQLException {
		try {

			//Splash Images
			primaryStage.setTitle("KuzularDet");
			File Icon=new File("images/Logo001.png");
			primaryStage.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));

			LoadCamera=CameraLoadingPage();
			UserLoad=UserLoadingPage();
			BorderPane rootSplash = new BorderPane();
			Splash = new Scene(rootSplash,1000,600);
			File fsL=new File("images/LogoS.png");
			Image imagesL = new Image("file:/"+fsL.getAbsolutePath());
			ImageView LogoSplash=new ImageView();
			LogoSplash.setImage(imagesL);
			LogoSplash.setFitHeight(10);
			LogoSplash.setFitWidth(10);
			LogoSplash.fitHeightProperty().bind(Splash.widthProperty().divide(2.2));
			LogoSplash.fitWidthProperty().bind(Splash.widthProperty().divide(2.2));
			rootSplash.setStyle("-fx-background-color: #FFFFFF;");
			rootSplash.setCenter(LogoSplash);
			primaryStage.setScene(Splash);
			Timer timer = new Timer();
			
			Button btn= new Button("Choose image");
			btn.setLayoutX(40);
			btn.setLayoutY(40);
			btn.setMinWidth(220);
			btn.setOpacity(0.8);
			
			
			btn.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			
			
			Button btn2= new Button("Camera");
			btn2.setLayoutX(80);
			btn2.setLayoutY(80);
			btn2.setMinWidth(220);
			btn2.setOpacity(0.8);
			
			
			
			btn2.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");

			
			Button btnListIm= new Button("List Imgaes");
			btnListIm.setLayoutX(40);
			btnListIm.setLayoutY(40);
			btnListIm.setMinWidth(220);
			btnListIm.setOpacity(0.8);

			btnListIm.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			Button LoginBnt= new Button("Login");
			LoginBnt.setLayoutX(40);
			LoginBnt.setLayoutY(40);
			LoginBnt.setMinWidth(100);
			LoginBnt.setOpacity(0.8);

			LoginBnt.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			Button RegisterBnt= new Button("Sign up");
			RegisterBnt.setLayoutX(40);
			RegisterBnt.setLayoutY(40);
			RegisterBnt.setMinWidth(100);
			RegisterBnt.setOpacity(0.8);

			RegisterBnt.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			HBox LRbtns=new HBox();
			LRbtns.getChildren().add(RegisterBnt);
			LRbtns.getChildren().add(LoginBnt);
			LRbtns.setMargin(RegisterBnt,new Insets(0,20, 0, 0));
			
			Button LogouBnt= new Button("Logout");
			LogouBnt.setLayoutX(40);
			LogouBnt.setLayoutY(40);
			LogouBnt.setMinWidth(100);
			LogouBnt.setOpacity(0.8);

			LogouBnt.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			Button ModifyBnt= new Button("Profil");
			ModifyBnt.setLayoutX(40);
			ModifyBnt.setLayoutY(40);
			ModifyBnt.setMinWidth(100);
			ModifyBnt.setOpacity(0.8);

			ModifyBnt.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			HBox Logbtns=new HBox();
			Logbtns.getChildren().add(ModifyBnt);
			Logbtns.getChildren().add(LogouBnt);
			Logbtns.setMargin(ModifyBnt,new Insets(0,20, 0, 0));

			
			primaryStage.show();
			Platform.runLater(() ->{
			HBox root = new HBox();
			scene = new Scene(root,1000,600,Color.web("#e4ebfd"));
			root.setStyle("-fx-background-color: #FFFFFF;");

			//Load Page;
			HBox Loadroot=new HBox();
			Load = new Scene(Loadroot,1000,600);
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
			Loadroot.setStyle("-fx-background-color: #FFFFFF;");
			RotateTransition LoadRt = new RotateTransition(Duration.millis(7000), LoadIm);
			LoadRt.setByAngle(3600);
			LoadRt.setCycleCount(Timeline.INDEFINITE);
			LoadRt.play();
			
			//Home
			
			Label txt1=new Label("");
			txt1.setLayoutX(20);
			txt1.setLayoutY(40);
			txt1.setStyle("\n-fx-text-fill: black;\n-fx-font-size:16;\n-fx-font-weight:600;");
			//Button
		
			
			ModifyBnt.setOnAction(e->{
				File file_User = new File("src/User.txt");
				BufferedReader r;
				try {
					r = new BufferedReader(new FileReader("src/User.txt"));
					String CIN=r.readLine();
					Lastscene=scene;
					primaryStage.setScene(UserLoad);
					Platform.runLater(()->{
							Main01 Login=new Main01();
							Stage profil=new Stage();
							try {
								Login.ProfilS(profil,CIN);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								primaryStage.setScene(Lastscene);
							}
							profil.setOnCloseRequest(eventC->{
								
							primaryStage.setScene(Home(Logbtns, LRbtns));
						
							});
							profil.show();
					});
				} catch (IOException e1) {primaryStage.setScene(Home(Logbtns, LRbtns));} 

				
			});
			File f=new File("images/back.png");
			Image image = new Image("file:/"+f.getAbsolutePath());
			ImageView Background=new ImageView();
			Background.setImage(image);
			Background.setFitHeight(400);
			Background.setFitWidth(400);
			Background.fitHeightProperty().bind(scene.widthProperty().divide(2.8));
			Background.fitWidthProperty().bind(scene.widthProperty().divide(2.8));
			Background.setX(100);
			Background.setY(100);
			VBox LeftPage0=new VBox();
			VBox LeftPage1=new VBox();
			
			VBox BackgroundContainer=new VBox();
			LeftPage0.getChildren().add(txt1);
			LeftPage0.getChildren().add(btn);
			LeftPage0.getChildren().add(btn2);
			LeftPage1.setMargin(LeftPage0, new Insets(40));
			LeftPage0.setMargin(LRbtns,new Insets(20, 0, 0, 0));
			LeftPage0.setMargin(btn,new Insets(20, 0, 0, 0));
			LeftPage0.setMargin(btn2,new Insets(20, 0, 0, 0));
			LeftPage0.setMargin(Logbtns,new Insets(20, 0, 0, 0));
			BackgroundContainer.setMargin(Background, new Insets(100,0,0,150));
			BackgroundContainer.getChildren().addAll(Background);
			Button btnGoBack= new Button("Home");
			btnGoBack.setLayoutX(40);
			btnGoBack.setLayoutY(40);
			btnGoBack.setMinWidth(220);
			btnGoBack.setOpacity(0.8);

			btnGoBack.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
				    + "-fx-font-size:15;\n-fx-font-weight:700;");
			
			

			btnGoBack.setOnAction((javafx.event.ActionEvent e)->{
				Lastscene=scene;
				primaryStage.setScene(scene);
			});			
			
			btn.setOnAction((javafx.event.ActionEvent e)->{
				Lastscene=scene;
				primaryStage.setScene(Load);
				try {
					Btn1(primaryStage,btnGoBack,btnListIm);
				} catch (Exception e1) {
					primaryStage.setScene(Lastscene);

				}

			});
			
			
			btn2.setOnAction((javafx.event.ActionEvent e)->{
				Lastscene=scene;
				istak=true;				
				primaryStage.setScene(CameraLoadingPage());
				
				new Thread(new Runnable() {
					@Override
					public void run() {
				            Platform.runLater(() -> {
				            	try {
									TakeP(new Stage(),primaryStage, btnGoBack, btnListIm,scene);
								} catch (Exception e) {

									primaryStage.setScene(Lastscene);
						            Platform.runLater(() -> {

									AlertP(new Stage(),"there is a problem in your Camera !");
						            });
								}
							});	
					}}).start();
			});
			
			
			
			File file_User = new File("src/User.txt");
			if (file_User.isFile()) {
				LeftPage0.getChildren().add(Logbtns);
			}
			else LeftPage0.getChildren().add(LRbtns);
			LeftPage1.getChildren().add(LeftPage0);
			
			Group Container01P1=new Group();
			Container01P1.getChildren().add(BackgroundContainer);
			Container01P1.getChildren().add(LeftPage1);
			root.getChildren().add(Container01P1);
			root.setAlignment(Pos.CENTER);
			
			LogouBnt.setOnAction(e->{
				primaryStage.setScene(Load);
				 LeftPage0.getChildren().remove(Logbtns);
				 LeftPage0.getChildren().add(LRbtns);
				 file_User.delete();
				 Timer timerx = new Timer();
					TimerTask taskx = new TimerTask() {
									@Override
									public void run() {
							            Platform.runLater(() -> {
											 primaryStage.setScene(scene);
							            });
									}
								};
								
					timerx.schedule(taskx, 2000);	
			});
			LoginBnt.setOnAction(e->{
				Lastscene=scene;
				primaryStage.setScene(UserLoad);
				new Thread(new Runnable() {
					@Override
					public void run() {
				            Platform.runLater(() -> {
								
				            	try {
					            	Main01 Login=new Main01();
									Stage LoginStage=new Stage();
									Login.Login(LoginStage);
									LoginStage.setOnCloseRequest(eventC->{
										
									primaryStage.setScene(Home(Logbtns, LRbtns));
								
									});
									LoginStage.show();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
				            	
				            });	
					}}).start();
			});
			RegisterBnt.setOnAction(e->{
				Lastscene=scene;
				primaryStage.setScene(UserLoad);
				new Thread(new Runnable() {
					@Override
					public void run() {
				            Platform.runLater(() -> {
								
				            	try {
					            	Main01 Login=new Main01();
									Stage LoginStage=new Stage();
									Login.Register(LoginStage);
									LoginStage.setOnCloseRequest(eventC->{
										
									primaryStage.setScene(Home(Logbtns, LRbtns));
								
									});
									LoginStage.show();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
				            	
				            });	
				            
					}}).start();
			});
			
			primaryStage.setScene(scene);
//			TimerTask task = new TimerTask() {
//
//				@Override
//				public void run() {
//		            Platform.runLater(() -> {
//					primaryStage.setScene(scene);
//					Lastscene=scene;
//		            });
//				}
//			};
//			
//			
//			timer.schedule(task, 1200);			
		});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Scene Home( HBox Logbtns,HBox LRbtns) {

		HBox root=(HBox) scene.getRoot();
		Group g=(Group) root.getChildren().get(0);
		VBox v=(VBox) g.getChildren().get(1);
		VBox v01=(VBox) v.getChildren().get(0);
		v01.getChildren().remove(3);
		File file_User = new File("src/User.txt");
		if (file_User.isFile()) {
			v01.getChildren().add(Logbtns);
		}
		else v01.getChildren().add(LRbtns);
		scene.setRoot(root);
		return scene;
		
	}
	
	
	public static  void ToPageList(Stage primaryStage,Button btnGoBack,Button btnListIm,int i ) throws SQLException{
		istak=false;
		VBox ListFroot=new VBox();
		ScrollPane Scrollp=new ScrollPane();
		ListData = new Scene(Scrollp,1000,600);
		Scrollp.setStyle("-fx-background: #FFFFFF;\n-fx-border-color: #FFFFFF;");
		

		VBox ElementsC=new VBox();
		VBox ElementsForallFaces=new VBox();
		HBox FirstContainer=new HBox();
		VBox ElementsForallFacesPart2=new VBox();
		File FacesF=new File("images/Faces/allfaces.jpg");
		Image imageFacesF = new Image("file:/"+FacesF.getAbsolutePath());
		double d=imageFacesF.getWidth()/imageFacesF.getHeight();
		ImageView FacesFIm=new ImageView();
		FacesFIm.setImage(imageFacesF);
		FacesFIm.setFitHeight(10);
		FacesFIm.setFitWidth(10);
		FacesFIm.fitHeightProperty().bind(Scrollp.widthProperty().divide(2.8));
		FacesFIm.fitWidthProperty().bind(Scrollp.widthProperty().divide(2.8/d));
		HBox Line1Btn=new HBox();
		HBox Line2Btn=new HBox();

		ElementsForallFaces.getChildren().add(FacesFIm);
		ElementsForallFaces.setAlignment(Pos.CENTER);
		Line1Btn.getChildren().add(btnGoBack);
		Button btn_0= new Button("Choose image");
		btn_0.setLayoutX(40);
		btn_0.setLayoutY(40);
		btn_0.setMinWidth(220);
		btn_0.setOpacity(0.8);

		btn_0.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
			    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
			    + "-fx-font-size:15;\n-fx-font-weight:700;");
		btn_0.setOnAction((javafx.event.ActionEvent e)->{
			primaryStage.setScene(Load);
			try {
				Btn1(primaryStage,btnGoBack,btnListIm);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
		Button btn2_0= new Button("Recognize with camera");
		btn2_0.setLayoutX(40);
		btn2_0.setLayoutY(40);
		btn2_0.setMinWidth(220);
		btn2_0.setOpacity(0.8);

		btn2_0.setStyle("-fx-cursor: hand;-fx-background-color: #4a4cf5;\n-fx-background-radius: 30;\n"+
			    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8 20;\n"
			    + "-fx-font-size:15;\n-fx-font-weight:700;");
		btn2_0.setOnAction((javafx.event.ActionEvent e)->{
			primaryStage.setScene(CameraLoadingPage());
			new Thread(new Runnable() {
				@Override
				public void run() {
			            Platform.runLater(() -> {
			            	try {
								TakeP(new Stage(),primaryStage, btnGoBack, btnListIm,scene);
							} catch (Exception e) {

								primaryStage.setScene(Lastscene);
								 Platform.runLater(() -> {

										AlertP(new Stage(),"there is a problem in your Camera !");
								 });
							}						
			            });	
				}}).start();
		});
		Line1Btn.getChildren().add(btn_0);
		Line2Btn.getChildren().add(btn2_0);
		Line2Btn.setAlignment(Pos.CENTER);
		Line1Btn.setAlignment(Pos.CENTER);
		Line1Btn.setMargin(btnGoBack, new Insets(0,10,0,0));
		ElementsForallFacesPart2.getChildren().add(Line1Btn);
		ElementsForallFacesPart2.getChildren().add(Line2Btn);
		ElementsForallFacesPart2.setMargin(Line1Btn, new Insets(30,0,10,10));
		ElementsForallFacesPart2.setMargin(Line2Btn, new Insets(0,0,10,10));


		ElementsForallFaces.getChildren().add(ElementsForallFacesPart2);

		for(int k=0;k<i;k++) {
			HBox ElementsE=new HBox();
			File LoadLi=new File("images/Faces/"+k+"face.jpg");
			Image imagesListE = new Image("file:/"+LoadLi.getAbsolutePath());
			ImageView ListEle=new ImageView();
			ListEle.setImage(imagesListE);
			ListEle.setFitHeight(100);
			ListEle.setFitWidth(100);
			ListEle.fitHeightProperty().bind(Scrollp.widthProperty().divide(10));
			ListEle.fitWidthProperty().bind(Scrollp.widthProperty().divide(10));
			ListEle.minWidth(60);
			ListEle.minHeight(60);

			VBox Info=new VBox();
			FaceRecognition FRN=new FaceRecognition();
			
			int id=-1;
			
			id=FRN.FaceRecognitionProcess(LoadLi.getAbsolutePath());
			System.out.println("id == "+id);
			if(id!=-1) {
				final String CIN=UserInfo.getCIN(id);
				
			if(CIN!=null) {
				ArrayList<String> arr=new ArrayList<String>();
					 arr=UserInfo.getIn(CIN);

				
				String G=arr.get(7).equals("M")==true?"Male":"Female";
				Label Name=new Label("Name : "+arr.get(0)+" "+arr.get(1));
				Label Sexe=new Label("Genre : "+G);
				Label Age=new Label("Age : "+arr.get(5));
				Name.setStyle("\n-fx-text-fill: black;\n-fx-font-size:14;\n-fx-font-weight:700;");
				Sexe.setStyle("\n-fx-text-fill: black;\n-fx-font-size:14;\n-fx-font-weight:700;");
				Age.setStyle("\n-fx-text-fill: black;\n-fx-font-size:14;\n-fx-font-weight:700;");
				Button btnL= new Button("More details ");
				btnL.setStyle("-fx-cursor: hand; -fx-font-size: 13; -fx-font-weight:700;  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #9c27b0;");
				btnL.setWrapText(false);
				btnL.setMinHeight(40);
				btnL.addEventFilter(MouseEvent.MOUSE_ENTERED, e->{btnL.setStyle("-fx-cursor: hand;-fx-font-size: 13.8;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #9c27b0;");});
				btnL.addEventFilter(MouseEvent.MOUSE_EXITED, e->{btnL.setStyle("-fx-cursor: hand;\n -fx-font-size: 13;\n -fx-font-weight:700;\n  -fx-border: none;\n -fx-background-color: transparent;\n -fx-text-fill: #9c27b0;");});
				HBox x=new HBox();
				x.setAlignment(Pos.CENTER_RIGHT);
				x.getChildren().add(btnL);
				btnL.setOnAction(e->{
					Main01 Login=new Main01();
					Stage profil=new Stage();
					try {
						Login.ProfilS(profil,CIN);
					} catch (Exception e1) {
					}
					profil.setOnCloseRequest(eventC->{});
					profil.show();
				});
				Info.getChildren().add(Name); 
				Info.getChildren().add(Sexe); 
				Info.getChildren().add(Age);
				Info.getChildren().add(x);
				}
			}
			else {
				Label Sexe=new Label("Genre : "+ FaceDetection.GenderDet("images/Faces/"+k+"face.jpg"));
				Label Age=new Label("Age : "+ FaceDetection.AgeDet("images/Faces/"+k+"face.jpg"));
				Sexe.setStyle("\n-fx-text-fill: black;\n-fx-font-size:14;\n-fx-font-weight:700;");
				Age.setStyle("\n-fx-text-fill: black;\n-fx-font-size:14;\n-fx-font-weight:700;");
				Info.getChildren().add(Sexe);
				Info.getChildren().add(Age);
			}
			ElementsE.setMargin(Info, new Insets(10,0,10,20));
			ElementsE.setMargin(ListEle, new Insets(10,0,10,20));
			Info.setAlignment(Pos.CENTER_LEFT);
			ElementsE.getChildren().add(ListEle);
			ElementsE.getChildren().add(Info);
			ElementsC.getChildren().add(ElementsE);
		}
		Scrollp.setPrefSize(115, 150);
		FirstContainer.getChildren().add(ElementsForallFaces);
		FirstContainer.getChildren().add(ElementsC);
		HBox topBar=new HBox();
		topBar.minHeight(35);
		topBar.setStyle("-fx-background-color: #fffff;");
		FirstContainer.setMargin( ElementsForallFaces, new Insets(30,0,10,20));
		FirstContainer.setMargin( ElementsC, new Insets(30,0,0,20));

		
		ListFroot.getChildren().add(FirstContainer);
		
		Scrollp.setContent(ListFroot);	
	}

	public static void Btn1(Stage primaryStage,Button btnGoBack,Button btnListIm ) throws Exception {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter ImageFile = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
		fileChooser.getExtensionFilters().add(ImageFile);

		fileChooser.setTitle("Open");
		 File file = fileChooser.showOpenDialog(primaryStage);
         if (file != null) {
 			primaryStage.setScene(Load);
         	String path=file.getPath();
         	int i=0;
         	i=FaceDetection.FaceCount(path);
				if(i>0) {
					ToPageList(primaryStage,btnGoBack,btnListIm,i );	
					Timer timerL = new Timer();
		 			TimerTask taskL = new TimerTask() {
		 				@Override
		 				public void run() {
		 		            Platform.runLater(() -> {
		 					primaryStage.setScene(ListData);
		 					Lastscene=ListData;
		 		            });
		 				}
		 			};
		 			timerL.schedule(taskL, 1200);
				}
				else {
			
 					primaryStage.setScene(Lastscene);
					AlertP(new Stage(),"there is no face detected in this image");
				}
         }
         else {
				primaryStage.setScene(Lastscene);
         }
	}
	public static void BtnCamera(Stage primaryStage,Button btnGoBack,Button btnListIm ,Boolean Saveit) throws SQLException {

	if(Saveit) {
			int i=0;
			i=FaceDetection.FaceCount("images/Faces/CameraUser.png");
			if(i>0) {
				ToPageList(primaryStage,btnGoBack,btnListIm,i );
				Timer timerL = new Timer();
				TimerTask taskL = new TimerTask() {
					@Override
					public void run() {
			            Platform.runLater(() -> {
			            	primaryStage.setScene(scene);
			            	Lastscene=scene;
			            });
					}
					
				};
				
				timerL.schedule(taskL, 1200);
			}
			else {
				primaryStage.setScene(Lastscene);
				AlertP(new Stage(),"there is no face detected in this image");
			}

		}
	}
	public static  void TakeP(Stage stageCamera,Stage primaryStage,Button btnGoBack,Button btnListIm,Scene sToc){
	
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
	   	Saveit=false;
	   	File Icon=new File("images/Logo001.png");
	   	stageCamera.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));
		VideoCapture cam=new VideoCapture(0);
		LiveView.setImage(getImage(cam));
		stageCamera.setTitle("Camera");
		VBox r = new VBox();
	    HBox rh1 = new HBox();
	    HBox rh = new HBox();
	    Scene s = new Scene(r, 800, 680);
	
	    LiveView.fitHeightProperty().bind(s.heightProperty().divide(1.2));
		LiveView.fitWidthProperty().bind(s.widthProperty().divide(1));
	    Button Done =new Button("Done");
	    Done.setLayoutX(40);
	    Done.setLayoutY(40);
	    Done.setMinWidth(100);
	    Done.setOpacity(0.8);
	
	    Done.setStyle("-fx-background-color: #1a0bae;\n-fx-background-radius:5;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8;\n"
				    + "-fx-font-size:14;\n-fx-font-weight:700;");
	    Button Cancel =new Button("Cancel");
	    Cancel.setLayoutX(40);
	    Cancel.setLayoutY(40);
	    Cancel.setMinWidth(100);
	    Cancel.setOpacity(0.8);
	
	    Cancel.setStyle("-fx-background-color: #1a0bae;\n-fx-background-radius:5;\n"+
				    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 8;\n"
				    + "-fx-font-size:14;\n-fx-font-weight:700;");

	   
	    rh.getChildren().add(Done);
	    rh.getChildren().add(Cancel);
	    rh.setMargin(Done, new Insets(10));
	    rh1.setMargin(Cancel, new Insets(20,0,0,0));
	    rh.setMargin(Cancel, new Insets(10));
	    rh.setAlignment(Pos.CENTER);
	    rh1.setAlignment(Pos.CENTER);
	    rh1.getChildren().add(LiveView);
	    r.setMargin(rh, new Insets(10));
	    
		r.getChildren().add(rh1);
	    r.getChildren().add(rh);
	
	
		LiveView.setPreserveRatio(true);
		LiveView.setSmooth(true);
		 
		stageCamera.setScene(s);
	
		stageCamera.setOnCloseRequest(event -> {
			isC=false;
			cam.release();
		});
	
		Thread p=new Thread(new Runnable() {
		@Override
			public void run() {
				while(!isC){
					LiveView.setImage(getImage(cam));
				}
		}});
		p.setDaemon(true);
		p.setPriority(9);
		p.start();
		stageCamera.setOnCloseRequest(e->{
			primaryStage.setScene(sToc);
			p.stop();
			cam.release();
		});
		 Cancel.setOnAction(e -> {
				p.stop();
				cam.release();
				primaryStage.setScene(sToc);
		   	 	stageCamera.close();
			});
	    Done.setOnAction(e -> {
			p.stop();
	    	Mat matrix = new Mat();
	   	 	cam.read(matrix);
	   	 	saveImage(matrix);
	   	 	int i=0;
	   	 	i=FaceDetection.FaceCount("images/Faces/CameraUser.png");
			if(i>0) {
				try {
					ToPageList(primaryStage,btnGoBack,btnListIm,i );
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Timer timerL = new Timer();
				TimerTask taskL = new TimerTask() {
				@Override
				public void run() {
		            Platform.runLater(() -> {
		            	primaryStage.setScene(ListData);
		            	Lastscene=ListData;
		            	});
					}
				};
			
				timerL.schedule(taskL, 1200);
			}
			else {
            	primaryStage.setScene(Lastscene);
				AlertP(new Stage(),"there is no face detected in this image");
			}
			
			cam.release();
	   	 	stageCamera.close();
	   	 });
		stageCamera.show();
	}
	public static void saveImage(Mat m){
		String file="images/Faces/CameraUser.png";
		Imgcodecs.imwrite(file,m);
	}

	public static WritableImage getImage(VideoCapture Vcp){

		WritableImage writableImage;
		Mat matrix =new Mat();

		Vcp.read(matrix);
		BufferedImage bufImage=new BufferedImage(
		matrix.width(),matrix.height(),BufferedImage.TYPE_3BYTE_BGR);

		WritableRaster raster =bufImage.getRaster();
		DataBufferByte dataBuffer=(DataBufferByte) raster.getDataBuffer();
		byte[] data=dataBuffer.getData();

		matrix.get(0,0,data);

		writableImage= SwingFXUtils.toFXImage(bufImage,null);

		return writableImage;
	}
	public static void AlertP(Stage AlertStage,String txt) {
		try {
			AlertStage.setResizable(false);
			File Icon=new File("images/Logo001.png");
			AlertStage.getIcons().add(new Image("file:/"+Icon.getAbsolutePath()));
			VBox rootVbALert01=new VBox();
			rootVbALert01.setStyle("-fx-background-color:#fff");
			rootVbALert01.setAlignment(Pos.BOTTOM_CENTER);
			HBox AlertEl01_0=new HBox();
			HBox AlertEl01_1=new HBox();
			AlertEl01_0.setAlignment(Pos.CENTER);
			Scene AlertScene=new Scene(rootVbALert01,350,100);
	        Text text = new Text(txt);
	        text.setStyle("-fx-font-size:15;\n-fx-font-weight:600;");
	        AlertEl01_0.getChildren().add(text);
	        AlertEl01_1.setAlignment(Pos.BASELINE_RIGHT);
	        Button Done =new Button("Done");
		    Done.setLayoutX(40);
		    Done.setLayoutY(40);
		    Done.setMinWidth(100);
		    Done.setOpacity(0.8);
		
		    Done.setStyle("-fx-background-color: #1a0bae;\n-fx-background-radius:5;\n"+
					    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 4;\n"
					    + "-fx-font-size:12;\n-fx-font-weight:700;");
		    
		    Button Cancel =new Button("Cancel");
		    Cancel.setLayoutX(40);
		    Cancel.setLayoutY(40);
		    Cancel.setMinWidth(50);
		    Cancel.setOpacity(0.8);
		
		    Cancel.setStyle("-fx-background-color: #1a0bae;\n-fx-background-radius:5;\n"+
					    "-fx-background-insets: 0;\n-fx-text-fill: white;\n-fx-padding: 4;\n"
					    + "-fx-font-size:12;\n-fx-font-weight:700;");
		    AlertEl01_1.getChildren().add(Done);
		    AlertEl01_0.setMargin(text, new Insets(20,10,10,10));
		    AlertEl01_1.setMargin(Done, new Insets(10));
		    
		    Done.addEventFilter(MouseEvent.MOUSE_ENTERED, e->{
		    	Done.setOpacity(0.8);
		    });
		    Done.addEventFilter(MouseEvent.MOUSE_EXITED, e->{
		    	Done.setOpacity(1);
		    });
		    Cancel.setOnAction(e -> {
		    	AlertStage.close();
			});
		    Done.setOnAction(e -> {
		    	AlertStage.close();
			});
		    rootVbALert01.getChildren().add(AlertEl01_0);
		    rootVbALert01.getChildren().add(AlertEl01_1);
	        AlertStage.setScene(AlertScene);
	        AlertStage.show();
	    }catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static Scene CameraLoadingPage() {
		//Load Camera Page
		HBox LoadrootC=new HBox();
		LoadCamera = new Scene(LoadrootC,1000,600);
		HBox LoadHbC=new HBox();
		File LoadfC=new File("images/CameraLoad.png");
		Image imagesLoadC = new Image("file:/"+LoadfC.getAbsolutePath());
		ImageView LoadImC=new ImageView();
		LoadImC.setImage(imagesLoadC);
		LoadImC.setFitHeight(10);
		LoadImC.setFitWidth(10);
		LoadImC.fitHeightProperty().bind(LoadCamera.heightProperty().divide(1.0));
		LoadImC.fitWidthProperty().bind(LoadCamera.heightProperty().divide(1.0));
		LoadHbC.getChildren().add(LoadImC);
		LoadrootC.getChildren().add(LoadHbC);
		LoadrootC.setAlignment(Pos.CENTER);
		LoadrootC.setStyle("-fx-background-color: #FFFFFF;");
		return LoadCamera;
	}
	public static Scene UserLoadingPage() {
		//Load Camera Page
		HBox LoadrootC=new HBox();
		LoadCamera = new Scene(LoadrootC,1000,600);
		HBox LoadHbC=new HBox();
		File LoadfC=new File("images/UserLoad.png");
		Image imagesLoadC = new Image("file:/"+LoadfC.getAbsolutePath());
		ImageView LoadImC=new ImageView();
		LoadImC.setImage(imagesLoadC);
		LoadImC.setFitHeight(10);
		LoadImC.setFitWidth(10);
		LoadImC.fitHeightProperty().bind(LoadCamera.heightProperty().divide(1.0));
		LoadImC.fitWidthProperty().bind(LoadCamera.heightProperty().divide(1.0));
		LoadHbC.getChildren().add(LoadImC);
		LoadrootC.getChildren().add(LoadHbC);
		LoadrootC.setAlignment(Pos.CENTER);
		LoadrootC.setStyle("-fx-background-color: #FFFFFF;");
		return LoadCamera;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}