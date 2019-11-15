import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class LeapListener extends Listener {
	public void onInit (Controller controller) {
		System.out.println("Initialized");
	}
	
	public void onConnect (Controller controller) {
		System.out.println("Connected to Motion Sensor");
	}
	
	public void onDisconnect (Controller controller) {
		System.out.println("Motion Sensor Disconnected");
	}
	
	public void onExit (Controller controller) {
		System.out.println("Exited");
	}
	
	// What to do with each frame ("photo" of the hand/s)
	// Each frame has data on the hand, fingers and bones
//	public void onFrame (Controller controller) {
//		Frame frame = controller.frame();
////		for (Finger finger : frame.fingers()) {
////			System.out.println("Finger type: " + finger.type()
////								+ " ID: " + finger.id()
////								+ " Finger Length (mm): " + finger.length()
////								+ " Finger Width (mm): " + finger.width()
////								);
////			
////			for (Bone.Type boneType : Bone.Type.values()) {
////				Bone bone = finger.bone(boneType);
////				System.out.println("Bone Type: " + bone.type()
////									+ " Start: " + bone.prevJoint()
////									+ " End: " + bone.nextJoint()
////									+ " Direction: " + bone.direction()
////									);
////			}
////		}
//		HandList hands = frame.hands();
//		System.out.println(hands.get(0).sphereRadius());
//	}
	
	public int[] getEuclidDistances(Frame frame) {
		int[] distances = new int[Constants.NUM_DISTANCES];
		int indexPoints = 0;
		
		// get all 11 points of interest
		Vector[] pointsOfInterest = new Vector[Constants.NUM_POINTS];
		for (Finger finger : frame.fingers()) {
			System.out.println("Finger type: " + finger.type()
								+ " ID: " + finger.id()
								+ " Finger Length (mm): " + finger.length()
								+ " Finger Width (mm): " + finger.width()
								);

			Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
			System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
								+ " End: " + tip.nextJoint()
								);
			pointsOfInterest[indexPoints++] = tip.nextJoint();
			Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);
			System.out.println("Bone Type: " + Bone.Type.TYPE_PROXIMAL
								+ " Start: " + prox.prevJoint()
								);
			pointsOfInterest[indexPoints++] = prox.prevJoint();
		}
		HandList handsInFrame = frame.hands();
		pointsOfInterest[indexPoints] = handsInFrame.get(0).palmPosition();
		
		//Getting the degrees
		
		for( Hand h : handsInFrame ) {
			Vector normal = h.palmNormal();
			Vector direction = h.direction();
			
			System.out.println("Pitch: " + Math.toDegrees(direction.pitch()) //Yes
								+ " Roll: " + Math.toDegrees(normal.roll()) //Maybe
								+ " Yaw: " + Math.toDegrees(direction.yaw())); //No
		}
		
		// get all 55 distances
		int indexDistances = 0;
		for(int i = 0; i < Constants.NUM_POINTS; i++) {
			for(int j = i+1; j < Constants.NUM_POINTS; j++) {
				Vector point1 = pointsOfInterest[i];
				Vector point2 = pointsOfInterest[j];
				distances[indexDistances++] = Math.round(point1.distanceTo(point2));
				System.out.println("Point" + i + " to Point" + j + ": " + distances[indexDistances-1]);
			}
		}
		return distances;
	}
	
	public Vector[] getPointsOfInterest(Frame frame) {
		int indexPoints = 0;
		
		// get all 11 points of interest
		Vector[] pointsOfInterest = new Vector[Constants.NUM_POINTS];
		for (Finger finger : frame.fingers()) {
			System.out.println("Finger type: " + finger.type()
								+ " ID: " + finger.id()
								+ " Finger Length (mm): " + finger.length()
								+ " Finger Width (mm): " + finger.width()
								);

			Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
			System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
								+ " End: " + tip.nextJoint()
								);
			pointsOfInterest[indexPoints++] = tip.nextJoint();
			Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);
			System.out.println("Bone Type: " + Bone.Type.TYPE_PROXIMAL
								+ " Start: " + prox.prevJoint()
								);
			pointsOfInterest[indexPoints++] = prox.prevJoint();
		}
		HandList handsInFrame = frame.hands();
		pointsOfInterest[indexPoints] = handsInFrame.get(0).palmPosition();
		
		return pointsOfInterest;
	}
	
	public void createCoordinatesCSVFile(String fileName, Frame frame) { 
		// first create file object for file placed at location 
		// specified by filepath 
		
		File file = new File(fileName); 
		try { 
			// create FileWriter object with file as parameter 
			FileWriter outputfile = new FileWriter(file); 

			// create CSVWriter object filewriter object as parameter 
			CSVWriter writer = new CSVWriter(outputfile); 

			// adding header to csv 
			String[] header = { "t_ProxX", "t_ProxY", "t_ProxZ", 
								"t_TipX", "t_TipY", "t_TipZ", 
								"i_ProxX", "i_ProxY", "i_ProxZ", 
								"i_TipX", "i_TipY", "i_ProxZ",
								"m_ProxX", "m_ProxY", "m_ProxZ",
								"m_TipX", "m_TipY", "m_TipZ", 
								"r_ProxX", "r_ProxY", "r_ProxZ",
								"r_TipX", "r_TipY", "r_TipZ", 
								"p_ProxX", "p_ProxY", "p_ProxZ",
								"p_TipX", "p_TipY", "p_TipZ",
								"pcX", "pcY", "pcZ" }; 
			writer.writeNext(header); 
			
			int index = 0;
			String[] coordinates = new String[Constants.TOTAL_COORDINATES];
			
			// get all 11 points of interest
			for (Finger finger : frame.fingers()) {
				System.out.println("Finger type: " + finger.type()
									+ " ID: " + finger.id()
									+ " Finger Length (mm): " + finger.length()
									+ " Finger Width (mm): " + finger.width()
									);

				Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
				System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
									+ " End: " + tip.nextJoint()
									);
				
				//append to string array of coordinates
				Vector tipC = tip.nextJoint();
				coordinates[index++] = tipC.getX() + "";
				coordinates[index++] = tipC.getY() + "";
				coordinates[index++] = tipC.getZ() + "";
				
				Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);
				System.out.println("Bone Type: " + Bone.Type.TYPE_PROXIMAL
									+ " Start: " + prox.prevJoint()
									);
				
				//append to string array of coordinates
				Vector proxC = prox.nextJoint();
				coordinates[index++] = proxC.getX() + "";
				coordinates[index++] = proxC.getY() + "";
				coordinates[index++] = proxC.getZ() + "";
			}
			HandList handsInFrame = frame.hands();
			Vector pc = handsInFrame.get(0).palmPosition();
			coordinates[index++] = pc.getX() + "";
			coordinates[index++] = pc.getY() + "";
			coordinates[index++] = pc.getZ() + "";
			
			//write the coordinates as one row in the csv
			writer.writeNext(coordinates);
			

			// closing writer connection 
			writer.close(); 
		} 
		catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
	}
	
	public void createFeaturesCSVFile(String fileName, Frame frame, String name, int counter) { 
		// first create file object for file placed at location 
		// specified by filepath 
		
		File file = new File(fileName); 
		try { 
			// create FileWriter object with file as parameter 
			FileWriter outputfile = new FileWriter(file,true ); 

			// create CSVWriter object filewriter object as parameter 
			CSVWriter writer = new CSVWriter(outputfile); 

			// adding header to csv 
			
			System.out.println(frame.hands().count());
			
			
			// adding header to csv 
			if(counter == 0) {
				String[] header = { "name","SDx","SDy","SDz","SR","PFD_t",
						"PFD_i","PFD_m","PFD_r","PFD_p","FA_ti","FA_im",
						"FA_mr","FA_rp","FA_pt","FD_ti","FD_tm","FD_tr",
						"FD_tp","FD_im","FD_ir","FD_ip","FD_mr","FD_mp","FD_rp" }; 
				writer.writeNext(header); 
			}
						
			
			//write the coordinates as one row in the csv
			String[] features = getFeatures(name, counter,0, frame);
			writer.writeNext(features);
			
			// closing writer connection 
			writer.close(); 

		} 
		catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 

		} 
		
	}
	
	public String[] getFeatures(String name, int counter, int flag, Frame frame) {
		Calculator calculator = new Calculator();
		Hand hand = frame.hand(0);
		
		//test if feature set is done properly
		float featureR = calculator.getPalmSphereRadius(frame);
		double[] featureD = calculator.getPalmFingerDistances(frame);
		double[] featureL = calculator.getFingerDistances(frame);
		double[] featureA = calculator.getFingerAngles(frame);
		//palm SD
		float palmX = hand.palmPosition().getX();
		float palmY = hand.palmPosition().getY();
		float palmZ = hand.palmPosition().getZ();
		double[] x = new double[1];
		double[] y = new double[1];
		double[] z = new double[1];
		x[0] = palmX;
		y[0] = palmY;
		z[0] = palmZ;
		double[] featureS = calculator.getSDPalmPosition(x, y, z);
		
		System.out.println("Sphere Radius: " + featureR);
		System.out.println("Palm-Finger Distances " + Arrays.toString(featureD));
		System.out.println("Finger Distances: " + Arrays.toString(featureL));
		System.out.println("Finger Angles: " + Arrays.toString(featureA));
		System.out.println("Palm SD: " + Arrays.toString(featureS));
		
		String[] features =  new String[24];
		int iFeatures = 0;
		//if(flag == 0) features[iFeatures++] = name + Integer.toString(counter);//1
		for(int i = 0; i < 3; i++) {
			features[iFeatures++] = featureS[i] + "";
		}
		features[iFeatures++] = featureR + "";//1
		for(int i = 0; i < 5; i++) {
			features[iFeatures++] = featureD[i] + "";
		}
		for(int i = 0; i < 5; i++) {
			features[iFeatures++] = featureA[i] + "";
		}
		for(int i = 0; i < 10; i++) {
			features[iFeatures++] = featureL[i] + "";
		}
		return features;
	}
	
	public double[] getFeaturesDouble(String name, int counter, int flag, Frame frame) {
		Calculator calculator = new Calculator();
		Hand hand = frame.hand(0);
		
		//test if feature set is done properly
		float featureR = calculator.getPalmSphereRadius(frame);
		double[] featureD = calculator.getPalmFingerDistances(frame);
		double[] featureL = calculator.getFingerDistances(frame);
		double[] featureA = calculator.getFingerAngles(frame);
		//palm SD
		float palmX = hand.palmPosition().getX();
		float palmY = hand.palmPosition().getY();
		float palmZ = hand.palmPosition().getZ();
		double[] x = new double[1];
		double[] y = new double[1];
		double[] z = new double[1];
		x[0] = palmX;
		y[0] = palmY;
		z[0] = palmZ;
		double[] featureS = calculator.getSDPalmPosition(x, y, z);
		
		System.out.println("Sphere Radius: " + featureR);
		System.out.println("Palm-Finger Distances " + Arrays.toString(featureD));
		System.out.println("Finger Distances: " + Arrays.toString(featureL));
		System.out.println("Finger Angles: " + Arrays.toString(featureA));
		System.out.println("Palm SD: " + Arrays.toString(featureS));
		
		double[] features =  new double[24];
		int iFeatures = 0;
		//if(flag == 0) features[iFeatures++] = name + Integer.toString(counter);//1
		for(int i = 0; i < 3; i++) {
			features[iFeatures++] = featureS[i];
		}
		features[iFeatures++] = featureR;//1
		for(int i = 0; i < 5; i++) {
			features[iFeatures++] = featureD[i];
		}
		for(int i = 0; i < 5; i++) {
			features[iFeatures++] = featureA[i];
		}
		for(int i = 0; i < 10; i++) {
			features[iFeatures++] = featureL[i];
		}
		return features;
	}

}
