package audioClassification;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class DisplayUI {
	
	public static final Boolean visibility=true;
	public static final int jwidth=500;
	public static final int jheight=750;

	public static JFrame frame = new JFrame("Developer: Rashi Goyal ~ UWID: rasghig10@uw.edu ~ 1668230 ");
	public static JPanel screenPanel= new JPanel();
	public static JPanel buttonPanel= new JPanel();
	
	public static JScrollPane trainScrollPane;
	public static JScrollPane testScrollPane;
	public static JScrollPane logAreaScrollPane;
	public static JList trainList;
	public static JList testList;
	public static JButton playButton = new JButton("Play");
	public static JButton testButton = new JButton("Run Classifier");
	public static JButton addToTestButton = new JButton(">>");		
	public static JButton addToTrainButton = new JButton("<<");
	public static JTextArea log = new JTextArea();
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		
	public static String[] trainfileNames; 
	public static String[] testfileNames; 
	
	public static String audioFileDirPath;
	public static String classiferFilesPath;
	public static ReadAudioFile readAudioFile = new ReadAudioFile();
	public static AudioClassifier audioClassifier = new AudioClassifier();
	public static HashSet trainArraySet= new HashSet();
	public static HashSet testArraySet= new HashSet();
	
	public static File audioFileDir;
	public static File audioFiles[];
	public static String selectedFileName;
	public static URL selectedFilePath;
	
	public static DisplayLog displayLog = new DisplayLog();
	
	public static void addToTrainList(String value){
		trainArraySet.add(value);
		trainArraySet.remove(null);
	}
	
	public static void removeFromTrainList(String value){
		trainArraySet.remove(value);
		trainArraySet.remove(null);
	}
	
	public static void addToTestList(String value){
		testArraySet.add(value);
		testArraySet.remove(null);
	}
	
	public static void removeFromTestList(String value){
		testArraySet.remove(value);	
		testArraySet.remove(null);
	}
	
	public static void loadJlist(){
		
		trainfileNames= new String[trainArraySet.size()];
		trainList = new JList();
		if(trainArraySet.size()>0){
			Iterator<?> itr = trainArraySet.iterator();
			int counter=0;
			while( itr.hasNext()){
				String value =(String) itr.next();
				trainfileNames[counter]=value;
				counter++;
			}
			trainList = new JList(trainfileNames);
		}
		
		testfileNames= new String[testArraySet.size()];
		testList = new JList();
		if(testArraySet.size()>0){
			Iterator<?> itr = testArraySet.iterator();
			int counter=0;
			while( itr.hasNext()){
				String value =(String) itr.next();
				testfileNames[counter]=value;
				counter++;
			}
			testList = new JList(testfileNames);
		}
	}
	
	public void LoadFiles(){
		
		for ( File f : audioFiles){
			if(f.getName().endsWith(".wav")){
				addToTrainList(f.getName());
			}
		}
		loadJlist();
	}
	
	public static Boolean ifExists(HashSet hashSet, String value){
		
		Boolean exists=false;
		
		Iterator<?> itr = hashSet.iterator();
		
		while(itr.hasNext()){
			String hashSetValue=(String) itr.next();
			System.out.println(hashSetValue+" "+value);
			if(hashSetValue.equalsIgnoreCase(value)){
				exists=true;
				break;
			}
		}
		return exists;
	}
	
	public void LoadDataFiles(){
		
		readAudioFile.createArffFile(classiferFilesPath);

		Iterator<?> itr = trainArraySet.iterator();
		
		while(itr.hasNext()){
			readAudioFile.readWavFile("train",(audioFileDirPath+"/"+((String) itr.next())));
		}
		itr = testArraySet.iterator();
		while(itr.hasNext()){
			readAudioFile.readWavFile("test",(audioFileDirPath+"/"+((String) itr.next())));
		}
		
//		for ( File f : audioFiles){
//			if(f.getName().endsWith(".wav")){
//				if(trainArraySet.contains(f.getName())){
////					System.out.println(f.getName());
//					readAudioFile.readWavFile("train",f.getAbsolutePath());									
//				}else if(testArraySet.contains(f.getName())){
//					readAudioFile.readWavFile("test",f.getAbsolutePath());														
//				}	
//			}
//		}
		readAudioFile.closeWriter();
	}

	public DisplayUI(){
		
		audioFileDirPath=getClass().getResource("/resources/").getPath();
		classiferFilesPath=getClass().getResource("/dataResources/").getPath();
		audioFileDir = new File(audioFileDirPath);
		audioFiles= audioFileDir.listFiles();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(jwidth, jheight);
		frame.setResizable(false);
		
		addToTestButton.addActionListener(new AddToTestButtonHandler());
		addToTrainButton.addActionListener(new AddToTrainButtonHandler());
		playButton.addActionListener(new playButtonHandler());
		testButton.addActionListener(new TestButtonHandler());

	}
	
	private static void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align,int fill) {
	    GridBagConstraints gc = new GridBagConstraints();
	    gc.gridx = x;
	    gc.gridy = y;
	    gc.gridwidth = width;
	    gc.gridheight = height;
	    gc.weightx = 100.0;
	    gc.weighty = 100.0;
	    gc.insets = new Insets(1, 1, 1, 1);
	    gc.anchor = align;
	    gc.fill = fill;
	    p.add(c, gc);
	  }
	
	public void DisplayUIX(){
		
		screenPanel.removeAll();
		screenPanel.setLayout(new GridBagLayout());
		
	    addItem(screenPanel, new JLabel("Audio Classification Program"), 0, 0, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.CENTER);
	    
	    addItem(screenPanel, new JLabel("Training Set"), 0, 1, 1, 1, GridBagConstraints.WEST,GridBagConstraints.CENTER);
	    addItem(screenPanel, new JLabel("Test Set"), 0, 1, 1, 1, GridBagConstraints.EAST,GridBagConstraints.CENTER);

		trainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		trainList.setLayoutOrientation(JList.VERTICAL);
		trainList.setVisibleRowCount(20);
		trainList.setFixedCellHeight(15);
		trainList.setFixedCellWidth(170);

	    MouseListener trainMouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	        	selectedFileName=trainList.getSelectedValue().toString().trim();
	        	selectedFilePath=getClass().getResource("/resources/"+selectedFileName);
	        	testList.clearSelection();
	        }
	      };

	    trainList.addMouseListener(trainMouseListener);
		trainScrollPane = new JScrollPane(trainList);
		addItem(screenPanel, trainScrollPane, 0, 2, 1, 1, GridBagConstraints.WEST,GridBagConstraints.CENTER);

		buttonPanel.removeAll();
		buttonPanel = new JPanel(new GridBagLayout());
		addItem(buttonPanel, addToTestButton, 0, 0, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.BOTH);
		addItem(buttonPanel, addToTrainButton, 0, 1, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.BOTH);
		addItem(screenPanel, buttonPanel, 0, 2, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.CENTER);

		testList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testList.setLayoutOrientation(JList.VERTICAL);
		testList.setVisibleRowCount(20);
		testList.setFixedCellHeight(15);
		testList.setFixedCellWidth(170);

		MouseListener testMouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	        	selectedFileName=testList.getSelectedValue().toString().trim();	        	
	        	selectedFilePath=getClass().getResource("/resources/"+selectedFileName);
			trainList.clearSelection();
	        }
	      };
	    
	    testList.addMouseListener(testMouseListener);
		testScrollPane = new JScrollPane(testList);
		addItem(screenPanel, testScrollPane, 0, 2, 1, 1, GridBagConstraints.EAST,GridBagConstraints.CENTER);
		
		addItem(screenPanel, playButton, 0, 3, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.CENTER);
		addItem(screenPanel, testButton, 0, 3, 1, 1, GridBagConstraints.EAST,GridBagConstraints.CENTER);

		log.setText(displayLog.getLogStatement());
		log.setEditable(false);
		log.setRows(15);

		logAreaScrollPane = new JScrollPane (log, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		addItem(screenPanel, logAreaScrollPane, 0, 4, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.BOTH);
		frame.repaint();
		frame.add(screenPanel);

		frame.setVisible(visibility);
	}
	
	public void refreshComponents(){
		screenPanel.removeAll();
		trainList.removeAll();
		testList.removeAll();
		buttonPanel.removeAll();
		trainScrollPane.removeAll();
		testScrollPane.removeAll();
		playButton.removeAll();
		testButton.removeAll();
		log.removeAll();
		logAreaScrollPane.removeAll();
	}
	public void refreshLogPane(){
		
//		logAreaScrollPane.removeAll();
		
		logAreaScrollPane.repaint();	
	}

	public class playButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		      try {
		    	  		if(!selectedFileName.equalsIgnoreCase("")){
		  		          Clip clip = AudioSystem.getClip();
				          AudioInputStream inputStream = AudioSystem.getAudioInputStream(selectedFilePath);
				          clip.open(inputStream);
				          clip.start(); 
		    	  		}
		        } catch (Exception e1) {
		        		e1.printStackTrace();
		        }		
		}
	}
	
	private class AddToTestButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				String selectedValue =(String) trainList.getSelectedValue();
				removeFromTrainList(selectedValue);
				addToTestList(selectedValue);
				loadJlist();
				refreshComponents();
				DisplayUIX();
	//			System.out.println("selected value: "+trainList.getSelectedValue().toString().trim());
			}
	}

	private class AddToTrainButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String selectedValue =(String) testList.getSelectedValue();
			
			removeFromTestList(selectedValue);
			addToTrainList(selectedValue);
			loadJlist();
			refreshComponents();
			DisplayUIX();
//			System.out.println("selected value: "+testList.getSelectedValue().toString().trim());
		}
	}

	private class TestButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println(sdf.format(new Date().getTime())+": loading Files");
			LoadDataFiles();
			System.out.println(sdf.format(new Date().getTime())+": Training Classifier");
			audioClassifier.trainClassifier(classiferFilesPath);
			System.out.println(sdf.format(new Date().getTime())+": Testing Classifier");
			audioClassifier.testClassifier(classiferFilesPath);
//			loadJlist();
//			System.out.println(sdf.format(new Date().getTime())+": calling DisplayUIX");
			refreshComponents();
			DisplayUIX();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DisplayUI displayUI = new DisplayUI();		
		displayUI.LoadFiles();		
		displayUI.DisplayUIX();
	}

}
