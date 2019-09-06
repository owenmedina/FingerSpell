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

}
