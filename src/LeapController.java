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
		Calculator calculator = new Calculator();

		LeapDB db = new LeapDB();
		
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
//		if(input.equals("s")) {
//			Frame frame = controller.frame();
//			int[] distances = listener.getEuclidDistances(frame);
//			String name = sc.nextLine();
//			db.insertSample(name, distances);
//			System.out.println("Snapshot taken");
//		}else if(input.equals("f")) {
//			while( !input.equals("exit")) {
//				System.out.println("Enter gesture name: ");
//				String name = sc.next();
//				int[] distances = db.selectGesture(name);
//				System.out.println(distances.length);
//				System.out.println(calculator.frobNorm(distances));
//				input = sc.next();
//			}
//			
//		}else if(input.equals("c")) {
//			Frame frame = controller.frame();
//			String fileName = "Coordinates.csv";
//			listener.createCoordinatesCSVFile(fileName,frame);
//		}else 
		if(input.equals("t")) {
			while(!input.equals("exit")) {
				System.out.println("Enter gesture name: ");
				String name = sc.next();
				int counter = 0;
				for(int i = 0; i < 50; i++) {
					Frame frame = controller.frame();
					String fileName = "TestBrian.csv";				
					listener.createFeaturesCSVFile(fileName,frame,name,counter);
					counter++;
				}
			}
			
		}
		else 
		if(input.equals("z")) {
			int counter = 0;
			while(!input.equals("p")) {
				Frame frame = controller.frame();
				String[] features =  listener.getFeatures(frame);
				System.out.println("Features: " + Arrays.toString(features));
				//System.out.println(features.length);
				//double[] feats = {0,0,0,79.035675,96.25945282,105.1138992,99.97422791,85.44957733,56.00112915,0.100621864,0.074714035,0.101935335,0.105789557,0.19114764,25.09286308,49.03758621,72.50086212,94.0956955,29.24085808,58.57175827,107.0080566,32.00622177,103.8028564,95.56828308};
				//System.out.println("test1");
				String[] s = new String[features.length];
				for (int i = 0; i < s.length; i++)
				    s[i] = String.valueOf(features[i]);
				//System.out.println("test2");
				SVC_Brian.main(s);
				System.out.println(counter++);
				//System.out.println("test3");
			}
			
			
			
			
			//double[] scaled = calculator.scaleFeatures(feats);
			//System.out.println(Arrays.toString(scaled));
			

		}
				
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		controller.removeListener(listener); 
	}
	
	
	

}
