package audioClassification;
import jAudioFeatureExtractor.AudioFeatures.BeatSum;
import jAudioFeatureExtractor.AudioFeatures.FFTBinFrequencies;

import java.io.File;
import java.util.Arrays;

import org.jtransforms.fft.DoubleFFT_1D;

import jAudioFeatureExtractor.AudioFeatures.BeatHistogram;
import jAudioFeatureExtractor.AudioFeatures.RMS;
import jAudioFeatureExtractor.AudioFeatures.SpectralCentroid;
import jAudioFeatureExtractor.AudioFeatures.ZeroCrossings;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;
import jAudioFeatureExtractor.jAudioTools.FFT;

public class TestClass {
	
	public static void printDependency(){
		BeatSum beatSum = new BeatSum();

		System.out.println("BeatSum Dependency");	

		for (String dep : beatSum.getDepenedencies()){
			System.out.println(dep);	
			
		}
		for (int dep : beatSum.getDepenedencyOffsets()){
			System.out.println(dep);	
		}
		
		System.out.println("Beat Histogram Dependency");	

		BeatHistogram beatHistogram = new BeatHistogram();

		for (String dep : beatHistogram.getDepenedencies()){
			System.out.println(dep);	
			
		}
		for (int dep : beatHistogram.getDepenedencyOffsets()){
			System.out.println(dep);	
		}

		System.out.println("RMS Dependency");	
		RMS rms = new RMS();

		for (String dep : rms.getDepenedencies()){
			System.out.println(dep);	
			
		}
		for (int dep : rms.getDepenedencyOffsets()){
			System.out.println(dep);	
		}
	}
	
	public static void main(String[] args) {
	
		
//		printDependency();
//		
		String audioFileDirPath="/Users/x3n9/Desktop/Work/workspace/AudioClassification/bin/resources/";
		File audioFileDir = new File(audioFileDirPath);
		File[] audioFiles= audioFileDir.listFiles();
		
		for ( File f : audioFiles){
			if(f.getName().endsWith(".wav")){

				AudioSamples audioSample;
				try {
					audioSample = new AudioSamples(f, "0", false);
					System.out.println("\n\n\n##############################\n\n\n");
					System.out.println("File Name:"+f.getName());
					System.out.println("File Amplitude"+audioSample.getMaximumAmplitude());
					
					double[] samples = audioSample.getSamplesMixedDown();

//					RMS rms = new RMS();
//					double [][] rootMeanSquare = new double[256][1];
//					double[] value=new double[1];
//					value=rms.extractFeature(samples,audioSample.getSamplingRateAsDouble(),null);
//					
//					Arrays.fill(rootMeanSquare, value);
//					
////					for (int i=0;i< 256;i++){
////						System.out.println("rms:"+rootMeanSquare[i][0]);	
////					}
//		
//					BeatHistogram beatHistogram = new BeatHistogram();
//					double[][] beatHistogramValue = new double[1][1];
//					
//					beatHistogramValue[0]=beatHistogram.extractFeature(samples,audioSample.getSamplingRateAsDouble(),rootMeanSquare);
//		
////					System.out.println("beatHistogramValue length"+beatHistogramValue[0].length);
////					for (double val : beatHistogramValue[0]){
////						System.out.println("beatHistogram:"+val);	
////					}
////		
//					BeatSum beatSum = new BeatSum();
//					double[]beatSumValue=beatSum.extractFeature(samples, audioSample.getSamplingRateAsDouble(), beatHistogramValue);
//					
//					for (double val : beatSumValue){
//						System.out.println("beatSum:"+val);	
//					}
					
					
					
					
//					##################### method 1
//					FFT fft = new FFT(samples,null,false,false);
//					
//					int counter=0;
////					for (double val : fft.getRealValues()){
////						System.out.println(counter+" ->fft value:"+val);	
////						counter++;
////					}
//					
//					double[] powerSpectrum = fft.getPowerSpectrum();
//
//					double[][] powerSpectrumArray = new double[1][powerSpectrum.length];
//					powerSpectrumArray[0]=powerSpectrum;
//					
//					SpectralCentroid spectralCentroid = new SpectralCentroid();
//					double[] spectralCentroidValue = spectralCentroid.extractFeature(samples, audioSample.getSamplingRateAsDouble(), powerSpectrumArray);
//					
//					for (double val : spectralCentroidValue){
//					System.out.println(counter+" ->spectralCentroidValue value:"+val);	
//					counter++;
//				}

					
//					##################### method 2
//					double[] fftvalue ;
		//
//					DoubleFFT_1D fftDo = new DoubleFFT_1D(samples.length);
//					fftvalue = new double[samples.length];
//			        System.arraycopy(samples, 0, fftvalue, 0, samples.length);
		//
//			        fftDo.realForward(fftvalue);
//			        
//			        int counter=0;
//					for (double val : fftvalue){
//						System.out.println(counter+"fft value :"+val);
//						counter++;
//					}


//					##################### zero Crossing Rate
//					ZeroCrossings zeroCrossings = new ZeroCrossings();
//					double[] zeroCrossing=zeroCrossings.extractFeature(samples, audioSample.getSamplingRateAsDouble(), null);
//			        int counter=0;
//					for (double val : zeroCrossing){
//						System.out.println(counter+"zeroCrossing value :"+val);
//						counter++;
//					}

//					##################### method 3
//			        FFTBinFrequencies fftBinFrequencies = new FFTBinFrequencies();
//			        double[] freq=fftBinFrequencies.extractFeature(samples, audioSample.getSamplingRateAsDouble(), null);
//			        int counter=0;
//					for (double val : freq){
//						System.out.println(counter+"freq value :"+val);
//						counter++;
//					}
							
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
//		File file = new File("/Users/x3n9/Desktop/Work/workspace/AudioClassification/bin/resources/Speech File 20.wav");

			


	}

}
