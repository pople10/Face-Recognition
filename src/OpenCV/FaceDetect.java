package OpenCV;


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
	
	
	public FaceDetect(ArrayList<Mat> images){
		this.images = images;
		this.classifierfile = "xml/haarcascade_frontalface_alt.xml";
		this.faceCascade = new CascadeClassifier();
		if(!faceCascade.load(classifierfile))
			System.err.println("Cannot load classifier");
	}
	
	public ArrayList<Mat> FaceDetectAlgorithm() {
		ArrayList<Mat> FACES = new ArrayList<Mat>();
		
		for(int i = 0; i<images.size(); i++) {
			Mat imageGrey = new Mat();
			
			Imgproc.equalizeHist(images.get(i), imageGrey);
			
			MatOfRect faces = new MatOfRect();
			faceCascade.detectMultiScale(imageGrey, faces);
			List<Rect> listOfFaces = faces.toList();
			Mat OnlyFace = new Mat();
			for(Rect face : listOfFaces) {
				OnlyFace = images.get(i).submat(face);
				Imgproc.rectangle(images.get(i), face , new Scalar(51,255,255), Imgproc.CV_SHAPE_RECT);
			}
			if(OnlyFace == null) {
				System.err.println("face not detected of image : "+i+1);
			}
			FACES.add(OnlyFace);
		}

		return FACES;
	}
}
