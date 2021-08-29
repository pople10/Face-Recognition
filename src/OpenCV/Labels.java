package OpenCV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Labels {
	
	private ArrayList<Integer> Labels;
	

	
	public void writingInteger(ArrayList<Integer> Labels) {
		try{
			File file = new File("Labels.json");
			Gson gson = new Gson();
			
			if(!file.exists()) {
				FileWriter fw = new FileWriter("Labels.json");
				fw.write(gson.toJson(Labels));
				fw.close();
			}else {
				ArrayList<Integer> labels = readingInteger();
				labels.addAll(Labels);
				file.delete();
				FileWriter fw = new FileWriter("Labels.json");
				fw.write(gson.toJson(labels));
				fw.close();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> readingInteger() {
		ArrayList<Integer> labels = new ArrayList<>();
		
		try(BufferedReader fr = new BufferedReader(new FileReader("Labels.json"))){
			Gson gson  =new Gson();
			@SuppressWarnings("deprecation")
			JsonParser jsonparser = new JsonParser();
			@SuppressWarnings("deprecation")
			JsonElement jsonElement = jsonparser.parse(fr);
			
			//Create generic Type
			Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
			labels =  gson.fromJson(jsonElement, type);
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return labels;
	}
	
	public int getLastInteger() {
		File file = new File("Labels.json");
		if(!file.exists()) {
			return 0;
		}else {
			ArrayList<Integer> Lab = readingInteger();
			return Lab.get(Lab.size()-1);
		}
	}
	public ArrayList<Integer> getLabels() {
		return Labels;
	}
	
}
