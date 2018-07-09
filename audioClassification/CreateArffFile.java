package audioClassification;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateArffFile {

	public static PrintWriter trainFileWriter;
	public static PrintWriter testFileWriter;
	public static DisplayLog displayLog = new DisplayLog();

	public CreateArffFile(String classiferFilesPath){
		try{
//			trainFileWriter = new PrintWriter("/Users/x3n9/Desktop/Work/workspace/AudioClassification/dataResources/train.arff", "UTF-8");
			
			File file=new File (classiferFilesPath+"/train.arff");
			file.delete();
			trainFileWriter = new PrintWriter(classiferFilesPath+"/train.arff", "UTF-8");
			trainFileWriter.println("@relation audio_classification");
			trainFileWriter.println("@attribute 'avge' real");
			trainFileWriter.println("@attribute 'zecr' real");
			trainFileWriter.println("@attribute 'spce' real");
			trainFileWriter.println("@attribute 'class' { Speech, Music}");
			trainFileWriter.println("@data");		    

//			testFileWriter = new PrintWriter("/Users/x3n9/Desktop/Work/workspace/AudioClassification/dataResources/test.arff", "UTF-8");
			file=new File (classiferFilesPath+"/test.arff");
			file.delete();
			testFileWriter = new PrintWriter(classiferFilesPath+"/test.arff", "UTF-8");
			testFileWriter.println("@relation audio_classification");
			testFileWriter.println("@attribute 'avge' real");
			testFileWriter.println("@attribute 'zecr' real");
			testFileWriter.println("@attribute 'spce' real");
			testFileWriter.println("@attribute 'class' { Speech, Music}");
			testFileWriter.println("@data");		    
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}

	public void closeWriter(){
		trainFileWriter.close();
		testFileWriter.close();
	}
	
	public void updateFile(String fileName,double avge, double zecr, double spce, String classification ){
		if(fileName.equalsIgnoreCase("train")){
			trainFileWriter.println(avge+","+zecr+","+spce+","+classification);
		}else if(fileName.equalsIgnoreCase("test")) {			
			testFileWriter.println(avge+","+zecr+","+spce+","+classification);
		}
	}
	
}
