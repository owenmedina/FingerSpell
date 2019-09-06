import java.io.*;
import java.util.Scanner;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
public class LeapController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeapListener listener = new LeapListener(); // gets data from the Leap Motion Controller
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		Scanner sc = new Scanner(System.in);
		if(sc.nextLine().equals("Snapshot")) {
			Frame frame = controller.frame();
			for (Finger finger : frame.fingers()) {
				System.out.println("Finger type: " + finger.type()
									+ " ID: " + finger.id()
									+ " Finger Length (mm): " + finger.length()
									+ " Finger Width (mm): " + finger.width()
									);
				
				for (Bone.Type boneType : Bone.Type.values()) {
					Bone bone = finger.bone(boneType);
					System.out.println("Bone Type: " + bone.type()
										+ " Start: " + bone.prevJoint()
										+ " End: " + bone.nextJoint()
										+ " Direction: " + bone.direction()
										);
				}
			}
			System.out.println("Hello");
		}
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		controller.removeListener(listener); 
	}
	

}
