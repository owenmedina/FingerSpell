import java.util.HashMap;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Finger.Type;
import com.sun.javafx.collections.MappingChange.Map;

public class Calculator {
	public static double frobNorm(int[]a){
		long sum = 0;
		for(int i : a){
			sum += (i*i);
		}
		return Math.sqrt(sum);
	}
	
	public static double scaleGesture1(int[] sample, int[] db) {
		double scaleFactor1 = 0;
		double frobNormSample = frobNorm(sample);
		double frobNormDB = frobNorm(db);
		scaleFactor1 = frobNormSample/frobNormDB;
		return scaleFactor1;
	}
	
	public static double scaleGesture2(int[] sample, int[] db) {
		double scaleFactor2 = 0;
		int tTipToPCSample = sample[Constants.INDEX_TTIP_PC];
		int tTipToPCDB = db[Constants.INDEX_TTIP_PC];
		scaleFactor2 = tTipToPCSample/tTipToPCDB;
		return scaleFactor2;
	}
	
	public static double scaleGesture3(int[] sample, int[] db) {
		double scaleFactor3 = 0;
		int mTipToPCSample = sample[Constants.INDEX_MTIP_PC];
		int mTipToPCDB = db[Constants.INDEX_MTIP_PC];
		scaleFactor3 = mTipToPCSample/mTipToPCDB;
		return scaleFactor3;
	}
	
	public static int[] scaleGesture(int[] sample, double scaleFactor) {
		int[] scaled = new int[Constants.NUM_DISTANCES];
		for(int i = 0; i < Constants.NUM_DISTANCES; i++){
			scaled[i] = (int) Math.floor(sample[i]/scaleFactor);
		}
		return scaled;
	}
	
	public static int[] getDifferenceArray(int[] sample, int[] db) {
		int[] difference = new int[Constants.NUM_DISTANCES];
		for(int i = 0; i < Constants.NUM_DISTANCES; i++) {
			difference[i] = Math.abs(sample[i] - db[i]);
		}
		return difference;
	}
	
	public static double MetricOfSimilarity(int[] sample, int[] db) {
		double mos = 0;
		int[] differenceArray = getDifferenceArray(sample, db);
		mos = frobNorm(differenceArray);
		return mos;
	}
	
	public static int getMinMOS(int[] sample, LeapDB db) {
		int indexOfMinMOS = -1;
		double minMOS = Double.MAX_VALUE;
		int[][] allGestures = db.selectAllGestures();
		for(int i = 0; i < Constants.NUM_LETTERS; i++) {
			double currMOS = MetricOfSimilarity(sample, allGestures[i]);
			if(currMOS < minMOS) {
				indexOfMinMOS = i;
				minMOS = currMOS;
			}
		}
		return indexOfMinMOS;
	}
	
	// Feature set R
	public static float getPalmSphereRadius(Frame frame) {
		HandList hands = frame.hands();
		return hands.get(0).sphereRadius();
	}
	
	// Feature set SD
	public static double[] getSDPalmPosition(double[] x, double[] y, double[] z) {	
		double xSD = standardDeviation(x);
		double ySD = standardDeviation(y);
		double zSD = standardDeviation(z);
		
		double[] posSD = {xSD, ySD, zSD};
		return posSD;
	}
	
	// Feature set PD
	public static double[] getPalmFingerDistances(Frame frame) {
		Vector[] fingers = new Vector[Constants.NUM_FINGERS];
		int indexFingers = 0;
		for (Finger finger : frame.fingers()) {
//			System.out.println("Finger type: " + finger.type()
//								+ " ID: " + finger.id()
//								+ " Finger Length (mm): " + finger.length()
//								+ " Finger Width (mm): " + finger.width()
//								);

			Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
//			System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
//								+ " End: " + tip.nextJoint()
//								);
			fingers[indexFingers++] = tip.nextJoint();
		}
		HandList handsInFrame = frame.hands();
		Vector palmCenter = handsInFrame.get(0).palmPosition();
		
		// get all 5 distances (palm to each finger tip)
		double[] distances = new double[Constants.NUM_FINGERS];
		//System.out.println(fingers.length);
		for(int i = 0; i < Constants.NUM_FINGERS; i++) {
			Vector finger = fingers[i];
			distances[i] = palmCenter.distanceTo(finger);
			//System.out.println("Point" + i + " to Palm" + ": " + distances[i]);
		}
		return distances;
	}
	
	// Feature set FD
	public static double[] getFingerDistances(Frame frame) {
		Vector[] fingers = new Vector[Constants.NUM_FINGERS];
		int indexFingers = 0;
		for (Finger finger : frame.fingers()) {
//			System.out.println("Finger type: " + finger.type()
//								+ " ID: " + finger.id()
//								+ " Finger Length (mm): " + finger.length()
//								+ " Finger Width (mm): " + finger.width()
//								);

			Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
//			System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
//								+ " End: " + tip.nextJoint()
//								);
			fingers[indexFingers++] = tip.nextJoint();
		}
		
		// get all 10 distances (palm to each finger tip)
		double[] distances = new double[Constants.NUM_FINGER_DISTANCES];
		int iDistances = 0;
		for(int i = 0; i < Constants.NUM_FINGERS; i++) {
			for(int j = i+1; j < Constants.NUM_FINGERS; j++) {
				Vector finger1 = fingers[i];
				Vector finger2 = fingers[j];
				distances[iDistances++] = finger2.distanceTo(finger1);
				//System.out.println("Point" + i + " to Point" + j + ": " + distances[iDistances-1]);
			}
		}
		return distances;
	}
	
	// Feature set A
	public static double[] getFingerAngles(Frame frame) {
		HashMap<Type, Vector> fingers = new HashMap<Type, Vector>();
		for (Finger finger : frame.fingers()) {
//			System.out.println("Finger type: " + finger.type()
//								+ " ID: " + finger.id()
//								+ " Finger Length (mm): " + finger.length()
//								+ " Finger Width (mm): " + finger.width()
//								);

			Bone tip = finger.bone(Bone.Type.TYPE_DISTAL);
//			System.out.println("Bone Type: " + Bone.Type.TYPE_DISTAL
//								+ " End: " + tip.nextJoint()
//								);
			fingers.put(finger.type(), tip.nextJoint());
		}
		
		// get all 5 angles
		Vector thumb = fingers.get(Finger.Type.TYPE_THUMB);
		Vector index = fingers.get(Finger.Type.TYPE_INDEX);
		Vector middle = fingers.get(Finger.Type.TYPE_MIDDLE);
		Vector ring = fingers.get(Finger.Type.TYPE_RING);
		Vector pinky = fingers.get(Finger.Type.TYPE_PINKY);
		double[] angles = new double[Constants.NUM_FINGERS];
		// thumb to index
		angles[0] = thumb.angleTo(index);
		// index to middle
		angles[1] = index.angleTo(middle);		
		// middle to ring
		angles[2] = middle.angleTo(ring);		
		// ring to pinky
		angles[3] = ring.angleTo(pinky);		
		// pinky to thumb
		angles[4] = pinky.angleTo(thumb);
		
		return angles;
	}
	
	
	public static double standardDeviation(double[] data) {
		int size = data.length;
		double sum = 0.0;
		double standardDeviation = 0.0;
		
		// get mean
        for(double elem : data) {
            sum += elem;
        }
        double mean = sum/size;
        
        // add all squares of the differences between each element and the mean
        for(double elem: data) {
            standardDeviation += Math.pow(elem - mean, 2);
        }
        
        return Math.sqrt(standardDeviation/size);
	}

	public static double max(double[] array) {
		double maximum = 0.0;
		for(double elem : array) {
			if(maximum < elem) maximum = elem;
		}
		return maximum;
	}
	
	public static double[] scaleFeatures(double[] features) {
		double max = max(features);
		int size = features.length;
		for(int i = 0; i < size; i++) {
			features[i] /= max;
		}
		return features;
	}
}
