package audioClassification;

import java.io.File;
import java.io.IOException;
import weka.classifiers.*;
import weka.classifiers.functions.SMO;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class AudioClassifier {

	public static Classifier classifier = new SMO();
	public static DisplayLog displayLog = new DisplayLog();

	public static void trainClassifier(String classiferFilesPath){
		
		try {
			
			displayLog.setLogStatement("Training SVM classifier...");
			ArffLoader loader = new ArffLoader();
//			loader.setFile(new File("/Users/x3n9/Desktop/Work/workspace/AudioClassification/dataResources/train.arff"));
			loader.setFile(new File(classiferFilesPath+"train.arff"));
			
			Instances trainInstance = loader.getDataSet();
			trainInstance.setClassIndex(trainInstance.numAttributes()-1);
			
			classifier.buildClassifier(trainInstance);
			displayLog.setLogStatement("Training successfully completed");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testClassifier(String classiferFilesPath){
		
		try {
			displayLog.setLogStatement("Running SVM classifier...");

			ArffLoader loader = new ArffLoader();
//			loader.setFile(new File("/Users/x3n9/Desktop/Work/workspace/AudioClassification/dataResources/test.arff"));
			loader.setFile(new File(classiferFilesPath+"test.arff"));
			
			Instances testInstance = loader.getDataSet(); 
			testInstance.setClassIndex(testInstance.numAttributes()-1);

			 for (int i = 0; i < testInstance.numInstances(); i++) {
				   double pred = classifier.classifyInstance(testInstance.instance(i));
					displayLog.setLogStatement("ID: " + testInstance.instance(i).value(0));
					displayLog.setLogStatement(", actual: " + testInstance.classAttribute().value((int) testInstance.instance(i).classValue()));
					displayLog.setLogStatement(", predicted: " + testInstance.classAttribute().value((int) pred));
//				   System.out.print("ID: " + testInstance.instance(i).value(0));
//				   System.out.print(", actual: " + testInstance.classAttribute().value((int) testInstance.instance(i).classValue()));
//				   System.out.println(", predicted: " + testInstance.classAttribute().value((int) pred));
				 }
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		trainClassifier();
//		testClassifier();
//	}

}
