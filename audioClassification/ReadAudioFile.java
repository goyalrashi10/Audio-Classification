package audioClassification;

import java.io.File;

import jAudioFeatureExtractor.AudioFeatures.FFTBinFrequencies;
import jAudioFeatureExtractor.jAudioTools.*;
import jAudioFeatureExtractor.AudioFeatures.BeatSum;
import jAudioFeatureExtractor.AudioFeatures.BeatHistogram;
import jAudioFeatureExtractor.AudioFeatures.RMS;
import jAudioFeatureExtractor.AudioFeatures.FFTBinFrequencies;
import audioClassification.CreateArffFile;
import org.jtransforms.fft.DoubleFFT_1D;


public class ReadAudioFile {
	
	public static CreateArffFile arffFile;
	public FFTBinFrequencies fftBinFreq = new FFTBinFrequencies();
	double[] fft ;
	double[] samples;
	public static DisplayLog displayLog = new DisplayLog();
	
	public double calculateZeroCrossingRate(){
		
		int size = samples.length;

		double sum=0.0;
		int val1=0;
		int val2=0;
		
		for(int i=1;i<size;i++){
			if(samples[i]<0.0){
				val1=-1;
			}else{
				val1=1;
			}
			if(samples[i-1]>=0.0){
				val2=1;
			}else{
				val2=-1;
			}
			sum=sum + (val2-val1);
		}
		double zeroCrossingValue=sum/(2*(size-1));
		displayLog.setLogStatement("zeroCrossingRate Value:"+zeroCrossingValue);

		return zeroCrossingValue;
	}
	
	public double calculateAverageEnergy(){
		
		int size = samples.length;

		double sum=0.0;
		
		for(int i=0;i<size;i++){
			sum=sum + (samples[i]*samples[i]);
		}
		
		double averageEnergyValue=sum/size;
		displayLog.setLogStatement("Average Energy Value:"+averageEnergyValue);

		return averageEnergyValue;
	}
		
	public void calculateFFTNew(){
		DoubleFFT_1D fftDo = new DoubleFFT_1D(samples.length);
        fft = new double[samples.length];
        System.arraycopy(samples, 0, fft, 0, samples.length);

		displayLog.setLogStatement("Executing Fast Fourier Transform..");
        fftDo.realForward(fft);
		
	}
	
	public double calculateSpectralCentroid(){
		
		double numerator=0.0;
		double denominator=0.0;
		double value=0.0;
		
		for(int i=0;i<samples.length;i++){
			numerator=numerator+(fft[i]*samples[i]);
			denominator=denominator+samples[i];
		}
		value=numerator/denominator;
		displayLog.setLogStatement("Spectral Centroid Value:"+value);
		return value;
	}
	
	public void createArffFile(String classiferFilesPath){
		arffFile=new CreateArffFile(classiferFilesPath);
	}
	
	public void readWavFile(String operation,String fileName){

		String filepath=fileName;
		
		File file = new File(filepath);
				
		try {
			AudioSamples audioSample= new AudioSamples(file, "0", false);
			
			displayLog.setLogStatement("\n\n\n##############################\n\n\n");
			displayLog.setLogStatement("File Name:"+file.getName());
			displayLog.setLogStatement("File Amplitude"+audioSample.getMaximumAmplitude());
			
			samples= audioSample.getSamplesMixedDown();
				
			double zecr=calculateZeroCrossingRate();
			double avge=calculateAverageEnergy();
			calculateFFTNew();
			double spce=calculateSpectralCentroid();

			if(file.getName().trim().contains("Speech")){
				arffFile.updateFile(operation, avge, zecr, spce, "Speech");
			}
			else{
				arffFile.updateFile(operation, avge, zecr, spce, "Music");
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeWriter(){
		arffFile.closeWriter();
	}
	
}