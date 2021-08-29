package OpenCV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import appio.Main;
import OpenCV.Labels;
import OpenCV.FaceDetect;
import javafx.stage.Stage;

public class FaceDetectionM {
	ArrayList<Mat> Faces = new ArrayList<Mat>();
	ArrayList<Integer> faceId = new ArrayList<Integer>();
	

	public  boolean ShowListImages(ArrayList<String> imagePaths) {
		if(imagePaths.size() < 5) {
			String message;
			if(imagePaths.size() == 1) {
				message = imagePaths.size()+" image is not enough for face recognition to work properly.";
			}else {
				message = imagePaths.size() + " images are not enough for face recognition to work properly.";
			}
			
			return false;
		}
		return true;
	}
	
	public  void ReadImagesConvertToJson(ArrayList<String> ImagesPaths) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		ArrayList<Mat> Images = new ArrayList<Mat>();
		
		
		for (int i=0; i<ImagesPaths.size(); ++i){
			Images.add(Imgcodecs.imread(ImagesPaths.get(i), Imgcodecs.IMREAD_GRAYSCALE));
		}
		FaceDetect facedetect = new FaceDetect(Images);
		ArrayList<Mat> faces = facedetect.FaceDetectAlgorithm();
		this.Faces = new ArrayList<Mat>(faces);
		Collections.copy(this.Faces, faces);
	}
	public int SaveImagesInJson(String cin) {
		
		ArrayList<JsonObject> ImagesJson = new ArrayList<JsonObject>();
		Size size = new Size(92, 112);
		for(int i=0; i<Faces.size(); ++i){
			if(!Faces.get(i).empty()) {
			     Imgproc.resize(Faces.get(i), Faces.get(i), size);
			     ImagesJson.add(matToJson(Faces.get(i)));
			}else {
				System.out.println("could not detect a face in image "+i);
			}

		}

		return WriteJsonArray(ImagesJson);
	}
	
	public int WriteJsonArray(ArrayList<JsonObject> json) {
		int x=-1;
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
				ArrayList<JsonObject> jsonobject = ReadJsonArray("Images.json");
				jsonobject.addAll(json);
				ArrayList<Mat> faces = new ArrayList<Mat>();
				file.delete();
				FileWriter fw = new FileWriter("Images.json");
				fw.write(gson.toJson(jsonobject));

				for (int i = 0; i < jsonobject.size(); i++) {
					faces.add(matFromJson(jsonobject.get(i)));					
				}
				fw.close();
			}
			Labels label = new Labels();
			x=label.getLastInteger()+1;
			for(int i=0; i<json.size(); ++i) {
				faceId.add(label.getLastInteger()+1);
			}
			label.writingInteger(faceId);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return x;
	}
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

	        return obj;
	    } else {
	        System.out.println("Mat not continuous.");
	    }
	    return null;
	}
	public static Mat matFromJson(JsonObject JsonObject){
	    int rows = JsonObject.get("rows").getAsInt();
	    int cols = JsonObject.get("cols").getAsInt();
	    int type = JsonObject.get("type").getAsInt();

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
	public ArrayList<JsonObject> ReadJsonArray(String file) {
		ArrayList<JsonObject> images = null;
		try(BufferedReader fr = new BufferedReader(new FileReader("Images.json"))){

			Gson gson  =new Gson();
			JsonParser jsonparser = new JsonParser();
			JsonElement jsonElement = jsonparser.parse(fr);
			
			Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
			System.out.println("TypeToken location");
			images =  gson.fromJson(jsonElement, type);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return images; 
	}
	
}
