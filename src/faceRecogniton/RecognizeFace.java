package faceRecogniton;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.utils.Converters;

public class RecognizeFace {
	
	private ArrayList<Mat> faces;
	private ArrayList<Integer> labels;
	
	RecognizeFace(ArrayList<Mat> faces, ArrayList<Integer> labels){
		this.faces = faces;
		this.labels = labels;
	}
	
	public int RecognizeFaceAlgorithm(Mat face){
		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		System.out.println("does it work?");
		
		

		int Height = faces.get(0).rows();

    	int lastlabel = labels.get(faces.size()-1);
    	LBPHFaceRecognizer  model = LBPHFaceRecognizer.create();    	
    	Mat labelsmat = Converters.vector_int_to_Mat(labels);
    	model.train(faces, labelsmat);
    	int predictedLabel = model.predict_label(face);
    	System.out.println("Predicted class = "+predictedLabel);
    	return predictedLabel;
	}
}
