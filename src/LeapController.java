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
		if(input.equals("s")) {
			Frame frame = controller.frame();
			int[] distances = listener.getEuclidDistances(frame);
			String name = sc.nextLine();
			db.insertSample(name, distances);
			System.out.println("Snapshot taken");
		}else if(input.equals("f")) {
			while( !input.equals("exit")) {
				System.out.println("Enter gesture name: ");
				String name = sc.next();
				int[] distances = db.selectGesture(name);
				System.out.println(distances.length);
				System.out.println(calculator.frobNorm(distances));
				input = sc.next();
			}
			
		}else if(input.equals("c")) {
			Frame frame = controller.frame();
			String fileName = "Coordinates.csv";
			listener.createCoordinatesCSVFile(fileName,frame);
		}else if(input.equals("t")) {
			System.out.println("Enter gesture name: ");
			String name = sc.next();
			int counter = 0;
			for(int i = 0; i < 50; i++) {
				Frame frame = controller.frame();
				String fileName = "FeatureSet.csv";				
				listener.createFeaturesCSVFile(fileName,frame,name,counter);
				counter++;
			}
			

		}else if(input.equals("z")) {
			Frame frame = controller.frame();
			int counter = 0;
			//String[] features =  listener.getFeatures("",counter,1,frame);
			double[] feats = {0,0,0,40.62068,59.42944717,92.17320251,80.74962616,39.74961472,37.55830765,0.186537951,0.270516723,0.315542698,0.020274311,0.280239016,44.4185791,44.41504669,65.62532806,69.994133,65.4125061,106.383728,107.3475266,68.29267883,69.8995285,14.13694477
			};
			String[] s = new String[feats.length];
			for (int i = 0; i < s.length; i++)
			    s[i] = String.valueOf(feats[i]);
			SVC.main(s);
			double[] scaled = calculator.scaleFeatures(feats);
			System.out.println(Arrays.toString(scaled));
			

		}
				
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		controller.removeListener(listener); 
	}
	
	
	

}
