import java.util.HashMap;
import java.util.List;
import javafx.util.Pair;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Finger.Type;
import com.sun.javafx.collections.MappingChange.Map;


public class Calculator {
	public static String[] letters = {"A","B","C","D","E","F","G","H","I","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
	
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
		System.out.println(tTipToPCSample);
		System.out.println(tTipToPCDB);
		scaleFactor2 = (double) tTipToPCSample/ (double) tTipToPCDB;
		return scaleFactor2;
	}
	
	public static double scaleGesture3(int[] sample, int[] db) {
		double scaleFactor3 = 0;
		int mTipToPCSample = sample[Constants.INDEX_MTIP_PC];
		int mTipToPCDB = db[Constants.INDEX_MTIP_PC];
		scaleFactor3 = (double) mTipToPCSample/ (double) mTipToPCDB;
		return scaleFactor3;
	}
	
	public static int[] scaleGesture(int[] sample, double scaleFactor) {
		int[] scaled = new int[Constants.NUM_DISTANCES];
		for(int i = 0; i < Constants.NUM_DISTANCES; i++){
			scaled[i] = (int) Math.floor(sample[i]/scaleFactor);
		}
		return scaled;
	}
	
	public static double[] getScalingFactors(int[] sample_five, int[] five_db) {
		double[] scalingFactors = new double[3];
		double sf1 = scaleGesture1(sample_five,five_db);
		double sf2 = scaleGesture2(sample_five,five_db);
		double sf3 = scaleGesture3(sample_five,five_db);
		scalingFactors[0] = sf1;
		scalingFactors[1] = sf2;
		scalingFactors[2] = sf3;
		return scalingFactors;
	}
	
	public static void recognizeGesture(int[] sample, double[] scalingFactors, LeapDB db) throws Exception {
		//get the 3 scaling factors
		double sf1 = scalingFactors[0];
		double sf2 = scalingFactors[1];
		double sf3 = scalingFactors[2];
		
		//Normalize the array for each factor
		int[] normalizedArray1 = scaleGesture(sample,sf1);
		int[] normalizedArray2 = scaleGesture(sample,sf2);
		int[] normalizedArray3 = scaleGesture(sample,sf3);
		
		//Get the MinMoS for each factor
		Pair<Double,Integer> minMOS1 = getMinMOS(normalizedArray1,db);
		Pair<Double,Integer> minMOS2 = getMinMOS(normalizedArray2,db);
		Pair<Double,Integer> minMOS3 = getMinMOS(normalizedArray3,db);
		
		//Print the recognized gesture for each
		System.out.println("Guess for SF1: " + getMinGesture(minMOS1.getValue()));
		System.out.println("Guess for SF2: " + getMinGesture(minMOS2.getValue()));
		System.out.println("Guess for SF3: " + getMinGesture(minMOS3.getValue()));
		
		//Final guess is the min of all minMOS
		double finalMOS = Math.min(Math.min(minMOS1.getKey(), minMOS2.getKey()), minMOS3.getKey());
		if(finalMOS == minMOS1.getKey()) System.out.println("The gesture is: " + getMinGesture(minMOS1.getValue()));
		else if(finalMOS == minMOS2.getKey()) System.out.println("The gesture is: " + getMinGesture(minMOS2.getValue()));
		else System.out.println("The gesture is: " + getMinGesture(minMOS3.getValue()));
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
	
	public static Pair<Double,Integer> getMinMOS(int[] sample, LeapDB db) throws Exception{
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
		Pair<Double,Integer> mosVals = new Pair<>(minMOS,indexOfMinMOS);
		return mosVals;
	}
	
	public static String getMinGesture(int n){
		//get the equivalent letter from the array
		return letters[n];	
	}
	
	// may not be usable
	public static double[] interquartileRange(double[] values) {
		double[] ranges = new double[2];
		int size = values.length;
		double Q1;
		double Q3;
		
		// sort values in ascending order
		
		// Find Q1 and Q3
		double median;
		int sizeOfQuartiles;
		if(size % 2 == 0) sizeOfQuartiles = size/2;
		else sizeOfQuartiles = (size-1)/2; // think of this as removing the median and getting the two sets before and after it
		
		int indexOfIQs = sizeOfQuartiles/2;
		if(sizeOfQuartiles % 2 == 0) {
			Q1 = (values[indexOfIQs]+values[indexOfIQs-1])/2;
			Q3 = (values[sizeOfQuartiles+indexOfIQs]+values[sizeOfQuartiles+indexOfIQs])/2;
		}
		else {
			Q1 = values[indexOfIQs];
			Q3 = values[sizeOfQuartiles+indexOfIQs];
		}
		
		double IQ = Q3-Q1;
		double boundary = IQ * 1.5;
		double lowerBoundary = Q1 - boundary; // what if Q1 is smaller than the boundary?
		double upperBoundary = Q3 + boundary;
		ranges[0] = lowerBoundary;
		ranges[1] = upperBoundary;
		
		return ranges;
	}
	
	// Average all the samples for a single gesture
	public static void averageSamples(String name, LeapDB db) throws Exception {
		List<int[]> allSamples = db.selectAllSamplesForGesture(name);
		int[] averageDistances = new int[Constants.NUM_DISTANCES];
		int numSamples = allSamples.size();
		for(int i = 0; i < Constants.NUM_DISTANCES; i++) {
			int total = 0;
			for(int[] arr : allSamples) {
				total += arr[i];
			}
			averageDistances[i] = (int) Math.floor(total/numSamples);
		}
		
		db.insertSample(name, averageDistances);
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
