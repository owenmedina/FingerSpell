import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
public class LeapController {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LeapListener listener = new LeapListener(); // gets data from the Leap Motion Controller
		Controller controller = new Controller();

		LeapDB db = new LeapDB();
		
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		Scanner sc = new Scanner(System.in);
		if(sc.nextLine().equals("Snapshot")) {
			Frame frame = controller.frame();
			int[] distances = listener.getEuclidDistances(frame);
			String name = sc.nextLine();
			db.insertSample(name, distances);
			System.out.println("Snapshot taken");
		}
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		controller.removeListener(listener); 
	}
	

}
