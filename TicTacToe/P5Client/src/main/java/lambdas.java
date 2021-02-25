import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class lambdas {
	
	
//	ToDoubleBifunction<Double,Double>;
	public static Double stanDHelper(Double value, Double mean,ToDoubleBiFunction<Double, Double>d) {
		return d.applyAsDouble(value,  mean);
	}
	public double applyAsDouble(Double val, Double mean){
		return (val-mean)*val-mean
				;
	}
	public static void main(String[] args) {
		Double result = 0.0;
		Double mean = 0.0;
		ArrayList<Double> list = new ArrayList<Double>();
		Double sum = 0.0;
		double tmp = 0.0;
//		ArrayList<Double> list = new ArrayList<Double>();
		list.add(12.0);
		list.add(13.0);
		list.add(14.0);
		mean = 13.0;
//		sum = list .stream().mapToDouble(Double::doubleValue).sum();
		sum = 39.0;
		ToDoubleBiFunction<Double,Double> f = (Double1,Double2)->Math.pow(Double1-Double2, 2);
		for(Double e : list) {
			tmp =+ stanDHelper(e,mean,f);
		}
		System.out.println(Math.sqrt(tmp/list.size()));
		System.out.println(Math.sqrt(tmp/3));
	}
	
		
		
		
		

		
	
	}
