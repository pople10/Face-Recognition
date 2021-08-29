package filechooser;


import java.lang.reflect.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.*;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class FileChooserDemo extends Application  {
		
	ArrayList<Mat> Faces = new ArrayList<Mat>();
	ArrayList<Integer> faceId = new ArrayList<Integer>();
	
	
	public static void main(String[] args) {
		launch(args);
		/*System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		FileChooserDemo FC = new FileChooserDemo();
		ArrayList<JsonObject> json = FC.ReadJsonArray("Images.json");
		ArrayList<Mat> faces = new ArrayList<Mat>();
		for (int i = 0; i < json.size(); i++) {
			faces.add(matFromJson(json.get(i)));
			HighGui.imshow("img"+i, faces.get(i));
			System.out.println("faces22");
			
		}
		HighGui.waitKey();*/
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		FileChooser filechooser = new FileChooser();
		
		Button button = new Button("Select File");
		
		FlowPane root = new FlowPane();	//root element
		
		HBox hbox= new HBox(HBox.USE_COMPUTED_SIZE);//hbow where images are put
		HBox hboxfaces = new HBox(HBox.USE_COMPUTED_SIZE); 
		
		primaryStage.setTitle("Face Recognition");
		
		//File Chooser where u select files 
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
		filechooser.getExtensionFilters().add(imageFilter);
		//button that will show the filechooser stage
		
		//where to store image path string
		final ArrayList<String> ImagePaths = new ArrayList<String>();
		

		root.getChildren().add(button);
		root.setAlignment(Pos.TOP_CENTER);
		root.setHgap(10);
		root.setVgap(10);
		
		button.setOnAction(e ->{	
					//when the button is clicked hide it
					button.setDisable(true);
					button.setVisible(false);
					//select multiple files
					List<File> selected = filechooser.showOpenMultipleDialog(primaryStage);
					for(int i=0; i<selected.size();i++) {
						ImagePaths.add(selected.get(i).getPath());
					}
					//SHow selected images
					ShowListImages(hbox, ImagePaths);
					ScrollPane SP = new ScrollPane();	
			        SP.setVmax(880);
			        SP.setPrefSize(800, 150);
			        //put the Hbox inside a scroll pane
					SP.setContent(hbox);
					SP.setHbarPolicy(ScrollBarPolicy.ALWAYS);
					SP.setVbarPolicy(ScrollBarPolicy.NEVER);
					
					ScrollPane SPfaces = new ScrollPane(SP);
			        SPfaces.setVmax(880);
			        SPfaces.setPrefSize(800, 150);
			        //put the Hbox inside a scroll pane
					SPfaces.setContent(hboxfaces);
					SPfaces.setHbarPolicy(ScrollBarPolicy.ALWAYS);
					SPfaces.setVbarPolicy(ScrollBarPolicy.NEVER);
					//what to do with the files ????
					Thread thread = new Thread(()->{
					    //Platform.runLater(()-> messageField.getStyleClass().add("smallLoading"));

						ReadImagesConvertToJson(ImagePaths);
						
						//Platform.runLater(()-> messageField.getStyleClass().remove("smallLoading"));
					});
					//add a try again button and detect faces button
					Button tryAgain = new Button("try again");
					Button detectFaces = new Button("Detect Faces");
					Button saveImages = new Button("Save Images");
					tryAgain.setOnAction(e1->{
						try {
							thread.stop();
							thread.interrupt();
							RestartSelection(primaryStage);
							//primaryStage.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});
					detectFaces.setOnAction(e3->{
						ShowWarning("This operation should not be interupted", "Detecting faces is running in the background, DO NOT CLOSE THE WINDOW!!!");
						//thread.start();
						ReadImagesConvertToJson(ImagePaths);
						try {
							showFaces(hboxfaces, this.Faces);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					});
					saveImages.setOnAction(e4->{
						SaveImagesInJson();
					});
					root.getChildren().add(SP);
					root.getChildren().add(tryAgain);
					root.getChildren().add(detectFaces);
					root.getChildren().add(saveImages);
					root.getChildren().add(SPfaces);
				});

		
		Scene scene = new Scene(root, 960, 600);
		

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	/*
	 * this function takes a parameter of type ArrayList<String>
	 * that represents the path of the list images.
	 * We show a list of images in grid pane
	 * number of images should be more or equal to 5.
	 * */
	public void ShowListImages(HBox hbox, ArrayList<String> imagePaths) {

		if(imagePaths.size() < 5) {
			String message;
			if(imagePaths.size() == 1) {
				message = imagePaths.size()+" image is not enough for face recognition to work properly.";
			}else {
				message = imagePaths.size() + " images are not enough for face recognition to work properly.";
			}
			ShowError("insufficient number of photos", message);
			System.err.println("Select 5 or more images of the person");
			return;
		}
		
		hbox.setAlignment(Pos.CENTER);
		//read images from path
		//show images using javafx
		ArrayList<Image> ImagesFx = new ArrayList<Image>();
		ArrayList<ImageView> ImageView= new ArrayList<ImageView>();
		
		for (int i=0; i<imagePaths.size(); ++i){
			try(InputStream stream = new FileInputStream(imagePaths.get(i));){
				ImagesFx.add(new Image(stream));
				ImageView.add(new ImageView(ImagesFx.get(i)));
				
				ImageView.get(i).setFitHeight(100);
				ImageView.get(i).setRotate(90);
			    ImageView.get(i).setSmooth(true);
			    ImageView.get(i).setCache(true);
			    ImageView.get(i).setPreserveRatio(true);

			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		hbox.getChildren().addAll(ImageView);
		
		System.out.println("number of images is "+ImagesFx.size());
		
	}
	
	//show detected faces
	public void showFaces(HBox hbox, ArrayList<Mat> faces) throws IOException{
		//show images using javafx
		ArrayList<Image> ImagesFx = new ArrayList<Image>();
		ArrayList<ImageView> ImageView= new ArrayList<ImageView>();
		
		int j=0;
		for(int i= 0; i<faces.size(); ++i) {
			if(!faces.get(i).empty()) {
				ImageView.add(new ImageView(loadImage(faces.get(i))));
				ImageView.get(j).setFitHeight(112);
				//ImageView.get(i).setRotate(90);
			    ImageView.get(j).setSmooth(true);
			    ImageView.get(j).setCache(true);
			    ImageView.get(j).setPreserveRatio(true);	
			    j++;
			}
		}
		hbox.getChildren().addAll(ImageView);
	}
	
	//convert mat to WritableImage
   public WritableImage loadImage(Mat image) throws IOException {
	      //Loading the OpenCV core library
	      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	      //Reading the Image from the file and storing it in to a Matrix object
	      //Encoding the image
	      MatOfByte matOfByte = new MatOfByte();
	      Imgcodecs.imencode(".jpg", image, matOfByte);
	      //Storing the encoded Mat in a byte array
	      byte[] byteArray = matOfByte.toArray();
	      //Displaying the image
	      InputStream in = new ByteArrayInputStream(byteArray);
	      BufferedImage bufImage = ImageIO.read(in);
	      System.out.println("Image Loaded");
	      WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
	      return writableImage;
	   }
	
	
	
	
	public void ShowWarning(String Header, String Content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(Header);
		alert.setContentText(Content);
		alert.showAndWait();
	}
	
	public void ShowError(String Header, String Content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(Header);
		alert.setContentText(Content);
		alert.showAndWait();
	}
	
	//read a list of 
	//don't forget!!!!
	/*
	 * extract the face, then resize it then store json file
	 * */
	public void ReadImagesConvertToJson(ArrayList<String> ImagesPaths) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		ArrayList<Mat> Images = new ArrayList<Mat>();
		
		
		//encode images to Mat
		for (int i=0; i<ImagesPaths.size(); ++i){
			Images.add(Imgcodecs.imread(ImagesPaths.get(i), Imgcodecs.IMREAD_GRAYSCALE));
		}
		FaceDetect facedetect = new FaceDetect(Images);
		System.out.println("detecting faces..");
		ArrayList<Mat> faces = facedetect.FaceDetectAlgorithm();
		//copy detected faces to show them to the user
		this.Faces = new ArrayList<Mat>(faces);
		Collections.copy(this.Faces, faces);
}

	public void SaveImagesInJson() {
		
		ArrayList<JsonObject> ImagesJson = new ArrayList<JsonObject>();
		//this code is for storing json file
		System.out.println("faces detected");
		Size size = new Size(92, 112);
		for(int i=0; i<Faces.size(); ++i){
			if(!Faces.get(i).empty()) {
			     Imgproc.resize(Faces.get(i), Faces.get(i), size);
			     ImagesJson.add(matToJson(Faces.get(i)));
			}else {
				System.out.println("could not detect a face in image "+i);
			}

			//Imgproc.cvtColor(faces.get(i), faces.get(i), Imgproc.COLOR_RGB2GRAY);
			//HighGui.imshow("face"+i, faces.get(i));
		}

		WriteJsonArray(ImagesJson);
		System.out.println("operation done");
	}
	
	public void SaveLabel() {
		
	}
	
	///////////////////////////////////////////////////////////////////////
	//return Json String From a Mat object
	public static JsonObject matToJson(Mat mat){
	    JsonObject obj = new JsonObject();

	    if(mat.isContinuous()){
	        int cols = mat.cols();
	        int rows = mat.rows();
	        int elemSize = (int) mat.elemSize();
	        int type = mat.type();

	        obj.addProperty("rows", rows);
	        obj.addProperty("cols", cols);
	        obj.addProperty("type", type);

	        // We cannot set binary data to a json object, so:
	        // Encoding data byte array to Base64.
	        String dataString;
	        if( type == CvType.CV_8U ) {
	            byte[] data = new byte[cols * rows * elemSize];
	            mat.get(0, 0, data);
	            dataString = new String(Base64.getEncoder().encode(data));
	        }
	        else {

	            throw new UnsupportedOperationException("unknown type");
	        }
	        obj.addProperty("data", dataString);

	        //Gson gson = new Gson();
	        //convert json obj to string
	        // String json = gson.toJson(obj);

	        return obj;
	    } else {
	        System.out.println("Mat not continuous.");
	    }
	    return null;
	}
	//////////////////////////////////////////////////////////////////////
	//returns Mat from json String
	public static Mat matFromJson(JsonObject JsonObject){
	    int rows = JsonObject.get("rows").getAsInt();
	    int cols = JsonObject.get("cols").getAsInt();
	    int type = JsonObject.get("type").getAsInt();
	   // String CIN = JsonObject.get("CIN").getAsString();
	    
	    Mat mat = new Mat(rows, cols, type);

	    String dataString = JsonObject.get("data").getAsString();
	    if( type == CvType.CV_8U ) {
	        byte[] data = Base64.getDecoder().decode(dataString.getBytes());
	        mat.put(0, 0, data);
	    }
	    else {
	        throw new UnsupportedOperationException("unknown type");
	    }
	    return mat;
	}
	
	public void WriteJsonArray(ArrayList<JsonObject> json) {
		try{
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			File file = new File("Images.json");
			if(!file.exists()) {
				System.out.println("File Images.json doesn't exist" );
				FileWriter fw = new FileWriter("Images.json");
				fw.write(gson.toJson(json));
				fw.close();
			}else {
				System.out.println("File Images.json already exists");
				//read array from file
				ArrayList<JsonObject> jsonobject = ReadJsonArray("Images.json");
				//add new json objects
				jsonobject.addAll(json);
				ArrayList<Mat> faces = new ArrayList<Mat>();
				file.delete();//delete Images.json
				FileWriter fw = new FileWriter("Images.json");
				fw.write(gson.toJson(jsonobject));

				
				
				for (int i = 0; i < jsonobject.size(); i++) {
					faces.add(matFromJson(jsonobject.get(i)));
					//HighGui.imshow("img"+i, faces.get(i));
					System.out.println("faces22");
					
				}
				fw.close();
			}
			//writeLabels
			Labels label = new Labels();
			for(int i=0; i<json.size(); ++i) {
				faceId.add(label.getLastInteger()+1);
			}
			label.writingInteger(faceId);

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * read a json file
	 * extract the json array
	 * convert json array to mat array
	 * and add images
	 * */
	

	
	//read Json file
	public ArrayList<JsonObject> ReadJsonArray(String file) {
		ArrayList<JsonObject> images = null;
		try(BufferedReader fr = new BufferedReader(new FileReader("Images.json"))){

			Gson gson  =new Gson();
			JsonParser jsonparser = new JsonParser();
			JsonElement jsonElement = jsonparser.parse(fr);
			
			//Create generic Type
			Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
			System.out.println("TypeToken location");
			images =  gson.fromJson(jsonElement, type);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return images; 
	}
	
	public void RestartSelection(Stage PrimaryStage) throws Exception {
		//always clear array 
		Faces.clear();
		faceId.clear();
		start(PrimaryStage);
	}
	

	//writes a json file

	
}
