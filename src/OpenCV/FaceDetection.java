package OpenCV;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
	
	private static String[] ageList={"(0-2)", "(4-6)", "(8-12)","(15-20)","(25-32)","(38-43)","(48-53)", "(60-100)"};
	private static String[] genderList={"Male","Female"};
	
	public static int FaceCount(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	      File x = new File("images/Faces");
	      if(!x.exists()) x.mkdir();

		
		String imgFile = path;
		String xmlFile = "xml/lbpcascade_frontalface.xml";
		Mat src = Imgcodecs.imread(imgFile);
		Mat src1 = Imgcodecs.imread(imgFile);
		CascadeClassifier cc = new CascadeClassifier(xmlFile);
		
		MatOfRect faceDetection = new MatOfRect();
		cc.detectMultiScale(src, faceDetection);
		int i=0;
		for(Rect rect: faceDetection.toArray()) {
			try {
				Imgproc.rectangle(src, new Point(rect.x-20, rect.y-20), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(245, 76, 74), 3);
				Rect rectCrop = new Rect(rect.x-20, rect.y-20, rect.width+20, rect.height+20);
				Mat image_roi = new Mat(src,rectCrop);
				Imgcodecs.imwrite("images/Faces/"+i+"face.jpg",image_roi);
			}
			catch(Exception e){
				Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(245, 76, 74), 3);
				Rect rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
				Mat image_roi = new Mat(src,rectCrop);
				Imgcodecs.imwrite("images/Faces/"+i+"face.jpg",image_roi);
			}
			Imgproc.rectangle(src1, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height) , new Scalar(245, 76, 74), 3);
			i++;
		}
		Imgcodecs.imwrite("images/Faces/allfaces.jpg",src1);
		return i;
	}
	public static String AgeDet(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String imgFile = path;
		Mat src = Imgcodecs.imread(imgFile);
         String ageProto = "Files/age_deploy.prototxt";
         String ageModel = "Files/age_net.caffemodel";
         
         
        Net ageNet = Dnn.readNet(ageProto, ageModel);
        
        Mat dst=new Mat();
        Imgproc.resize(src, dst, new Size(227, 227));
        Mat blob = Dnn.blobFromImage(dst, 1.0f, new Size(227, 227), new Scalar(78.4263377603, 87.7689143744, 114.895847746), false);
        ageNet.setInput(blob);
        Mat probs = ageNet.forward().reshape(1,1); 
        MinMaxLocResult r = Core.minMaxLoc(probs);
        int reAge = (int) r.maxLoc.x;
        return ageList[reAge];
	}
	
	public static String GenderDet(String path) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String imgFile = path;
		Mat src = Imgcodecs.imread(imgFile);
       
         String genderProto = "Files/gender_deploy.prototxt";
         String genderModel = "Files/gender_net.caffemodel";
         
        Net genderNet = Dnn.readNet(genderProto, genderModel);
        
        Mat dst=new Mat();
        Imgproc.resize(src, dst, new Size(227, 227));
        Mat blob = Dnn.blobFromImage(dst, 1.0f, new Size(227, 227), new Scalar(78.4263377603, 87.7689143744, 114.895847746), false);
        genderNet.setInput(blob);
        Mat probs = genderNet.forward().reshape(1,1); 
        MinMaxLocResult r = Core.minMaxLoc(probs);
        int reGender = (int) r.maxLoc.x;
        return genderList[reGender];
	}
}
