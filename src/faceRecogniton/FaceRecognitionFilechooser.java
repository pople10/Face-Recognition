package faceRecogniton;

import filechooser.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import com.google.gson.JsonObject;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FaceRecognitionFilechooser extends Application{
	
	private String ImagePath;
	public static void main(String[] args) {
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		launch(args);
	}
	
	@Override
	public void start(Stage PrimaryStage) throws Exception {
				
		Button button = new Button("Select Image");
		Button RecognizeFace = new Button("Recognize Face");
		
		FlowPane root = new FlowPane();	//root element
		PrimaryStage.setTitle("Face Recognition");
		HBox hbox= new HBox(HBox.USE_COMPUTED_SIZE);
		root.getChildren().add(button);
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
		FileChooser filechooser = new FileChooser(); 
		filechooser.getExtensionFilters().add(imageFilter);
		
		button.setOnAction(e->{
			button.setVisible(false);
			button.setDisable(true);
			
			File file = filechooser.showOpenDialog(PrimaryStage);
			this.ImagePath = file.getAbsolutePath();
			
			//ShowImage(hbox, this.ImagePath);

			
			RecognizeFace.setOnAction(e1->{
				FaceRecognitionProcess(this.ImagePath);
			});
			root.getChildren().add(hbox);
			root.getChildren().add(RecognizeFace);
		});
		
		Scene scene = new Scene(root, 960, 600);
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	public void ShowImage(HBox hbox, String imagePath) {
		
		try(InputStream IS = new FileInputStream(new File(imagePath))){
			Image image = new Image(IS);
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(200);
			//imageView.setRotate(90);
			imageView.setCache(true);
			imageView.setSmooth(true);
			imageView.setPreserveRatio(true);
			hbox.getChildren().add(imageView);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void FaceRecognitionProcess(String ImagePath) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat face = Imgcodecs.imread(ImagePath, Imgcodecs.IMREAD_GRAYSCALE);
		
		FileChooserDemo FC = new FileChooserDemo();
		ArrayList<JsonObject> json = FC.ReadJsonArray("Images.json");
		ArrayList<Mat> faces = new ArrayList<Mat>();
		for (int i = 0; i < json.size(); i++) {
			faces.add(FileChooserDemo.matFromJson(json.get(i)));
		}
		Labels labels = new Labels();
		RecognizeFace RF = new RecognizeFace(faces, labels.readingInteger());
		int Id = (int) RF.RecognizeFaceAlgorithm(face);
		if(Id == -1) {
			System.out.println("face is not found");
		}else {
			System.out.println("Id of face is: "+ Id);
		}
	}
	

}
