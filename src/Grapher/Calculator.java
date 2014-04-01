package Grapher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class Calculator {

	public Calculator() {
		// TODO Auto-generated constructor stub
	}

	public static Vector<Double> Average(Collection<Vector<Double>> valuesInput){
		double [][] values = toPrimitiveArray(valuesInput);
		double[] result = new double[values[0].length];
		for(int i = 0; i < result.length; i++){
			result[i] = 0;
		}
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < result.length; j++){
				result[j] = result[j] + values[i][j];
			}
		}
		for(int i = 0; i < result.length; i++){
			result[i] = result[i]/(double)values.length;
			System.out.println("At moment " +  Integer.toString(i) + " AVG = " + Double.toString(result[i]));
		}
		return toVector(result);
	}
	
	public static Vector<Double> Max(Collection<Vector<Double>> valuesInput){
		double [][] values = toPrimitiveArray(valuesInput);
		double[] result = new double[values[0].length];
		for(int i = 0; i < result.length; i++){
			result[i] = Double.MIN_NORMAL;
		}
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < result.length; j++){
				if(result[j] < values[i][j]){
					result[j] = values[i][j];
				}
			}
		}
		
		for(int i = 0 ; i < result.length; i++){
			System.out.println("At moment " +  Integer.toString(i) + " MAX = " + Double.toString(result[i]));
		}
		
		return toVector(result);
	}
	
	public static Vector<Double> Min(Collection<Vector<Double>> valuesInput){
		double [][] values = toPrimitiveArray(valuesInput);
		double[] result = new double[values[0].length];
		for(int i = 0; i < result.length; i++){
			result[i] = Double.MAX_VALUE;
		}
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < result.length; j++){
				if(result[j] > values[i][j]){
					result[j] = values[i][j];
				}
			}
		}
		
		for(int i = 0 ; i < result.length; i++){
			System.out.println("At moment " +  Integer.toString(i) + " MIN = " + Double.toString(result[i]));
		}
		
		return toVector(result);
	}
	
	public static double[][] toPrimitiveArray(Collection<Vector<Double>> m_data2){
		if(m_data2.isEmpty()) return new double[0][0];
		Iterator<Vector<Double>> it = m_data2.iterator();
		double[][] result = new double[m_data2.size()][];
		int i = 0;
		while(it.hasNext()){
			Collection<Double> temp = it.next();
			result[i] = toPrimitiveDouble(temp);
			i++;
		}
		
		return result;
	}
	
	public static double[] toPrimitiveDouble(Collection<Double> input){
		double[] result = new double[input.size()];
		Iterator<Double> it = input.iterator();
		int i = 0;
		while(it.hasNext()){
			result[i] = it.next().doubleValue();
			i++;
		}
		return result;
	}
	
	public static Vector<Double> toVector(double[] input){
		Vector<Double> result = new Vector<Double>(input.length);
		for(int i = 0; i < input.length; i++){
			result.add(i, new Double(input[i]));
		}
		return result;
	}
}
