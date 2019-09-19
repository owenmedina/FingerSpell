
public class Calculator {
	public static double frobNorm(int[]a){
		long sum = 0;
		for(int i : a){
			sum += (i*i);
		}
		return Math.sqrt(sum);
	} 
}
