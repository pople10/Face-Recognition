package filechooser;


import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetect {
	
	private String classifierfile;
	private ArrayList<Mat> images;
	private CascadeClassifier faceCascade;
	
	
	FaceDetect(ArrayList<Mat> images){
		this.images = images;
		this.classifierfile = "HAARClassifier\\haarcascade_frontalface_alt.xml";
		this.faceCascade = new CascadeClassifier();
		//Load classifier
		if(!faceCascade.load(classifierfile))
			System.err.println("Cannot load classifier");
	}
	/*
	 * this algorithm detect each image
	 * in array and detects the faces in
	 * each image and store them in a new
	 * array of faces
	 * */
	public ArrayList<Mat> FaceDetectAlgorithm() {
		ArrayList<Mat> FACES = new ArrayList<Mat>();
		
		for(int i = 0; i<images.size(); i++) {
			Mat imageGrey = new Mat();
			//convert image to grey scale
			//Imgproc.cvtColor(images.get(i), imageGrey, Imgproc.COLOR_RGB2GRAY);
			//equalize the image historgram
			Imgproc.equalizeHist(images.get(i), imageGrey);
			
			//where to store the face detected
			MatOfRect faces = new MatOfRect();
			faceCascade.detectMultiScale(imageGrey, faces);
			//transform the Mat object to a lost object
			List<Rect> listOfFaces = faces.toList();
			Mat OnlyFace = new Mat();
			for(Rect face : listOfFaces) {
				//take only the face
				OnlyFace = images.get(i).submat(face);
				//draw a rectangle on the face
				Imgproc.rectangle(images.get(i), face , new Scalar(51,255,255), Imgproc.CV_SHAPE_RECT);
			}
			if(OnlyFace == null) {
				System.err.println("face not detected of image : "+i+1);
			}
			FACES.add(OnlyFace);
		}
		
		
		/*
		if(OnlyFace != null) {
			HighGui.imshow("Only face", OnlyFace);
			MatOfInt M = new MatOfInt();
			M.alloc(Imgcodecs.IMWRITE_PXM_BINARY);
			//still have to resize the photo to mach the dataset
			Imgproc.cvtColor(OnlyFace, OnlyFace, Imgproc.COLOR_BGR2GRAY);
			Imgcodecs.imwrite("C:\\OnlyFace.pgm", OnlyFace, M);
		}
		*/
		return FACES;
	}
}
