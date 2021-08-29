package OpenCV;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

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

public class FaceRecognition {
	
	private String ImagePath;
	
	

	
	public int FaceRecognitionProcess(String ImagePath) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat face = Imgcodecs.imread(ImagePath, Imgcodecs.IMREAD_GRAYSCALE);
		if(face != null) {
			Imgproc.resize(face, face, new Size(92, 112));
		}
		FaceDetectionM FC = new FaceDetectionM();
		ArrayList<JsonObject> json = FC.ReadJsonArray("Images.json");
		ArrayList<Mat> faces = new ArrayList<Mat>();
		for (int i = 0; i < json.size(); i++) {
			faces.add(FaceDetectionM.matFromJson(json.get(i)));
		}
		Labels labels = new Labels();
		RecognizeFace RF = new RecognizeFace(faces, labels.readingInteger());
		int Id = (int) RF.RecognizeFaceAlgorithm(face);
		return Id;
	}
	public Mat faceDetect(Mat face) {
		String classifierfile = "HAARClassifier\\haarcascade_frontalface_alt.xml";
		CascadeClassifier faceCascade = new CascadeClassifier();
		//Load classifier
		if(!faceCascade.load(classifierfile))
			System.err.println("Cannot load classifier");
		Mat faceGrey = new Mat();
		Imgproc.equalizeHist(face, faceGrey);
		
		//where to store the face detected
		MatOfRect faces = new MatOfRect();
		faceCascade.detectMultiScale(faceGrey, faces);
		//transform the Mat object to a lost object
		List<Rect> listOfFaces = faces.toList();
		Mat OnlyFace = new Mat();
		for(Rect faceR : listOfFaces) {
			//take only the face
			OnlyFace = face.submat(faceR);
			//draw a rectangle on the face
			Imgproc.rectangle(face, faceR , new Scalar(51,255,255), Imgproc.CV_SHAPE_RECT);
		}
		if(OnlyFace == null) {
			System.err.println("face not detected of image : "+1);
		}
		
		return OnlyFace;
	}


}
