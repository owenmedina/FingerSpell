import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
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
	public void onFrame (Controller controller) {
//		Frame frame = controller.frame();
//		for (Finger finger : frame.fingers()) {
//			System.out.println("Finger type: " + finger.type()
//								+ " ID: " + finger.id()
//								+ " Finger Length (mm): " + finger.length()
//								+ " Finger Width (mm): " + finger.width()
//								);
//			
//			for (Bone.Type boneType : Bone.Type.values()) {
//				Bone bone = finger.bone(boneType);
//				System.out.println("Bone Type: " + bone.type()
//									+ " Start: " + bone.prevJoint()
//									+ " End: " + bone.nextJoint()
//									+ " Direction: " + bone.direction()
//									);
//			}
//		}
	}
	
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
			
			System.out.println("Pitch: " + Math.toDegrees(direction.pitch())
								+ " Roll: " + Math.toDegrees(normal.roll())
								+ " Yaw: " + Math.toDegrees(direction.yaw()));
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

}
