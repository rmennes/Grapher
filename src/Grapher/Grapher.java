package Grapher;

import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import de.progra.charting.ChartEncoder;
import de.progra.charting.DefaultChart;
import de.progra.charting.model.DefaultChartDataModel;
import de.progra.charting.render.InterpolationChartRenderer;
import de.progra.charting.render.LineChartRenderer;
import de.progra.charting.render.RowColorModel;

public class Grapher {

	ArrayList<Vector<Double>> 	m_data;
	ArrayList<String>			m_setTitle;
	Vector<Double>				m_xasis;
	String						m_title;
	
	public Grapher() {
		m_data = new ArrayList<Vector<Double> >();
		m_xasis = new Vector<Double>();
		m_setTitle = new ArrayList<String>();
		m_title = "";
	}
	
	public Grapher(String title){
		m_data = new ArrayList<Vector<Double>>();
		m_xasis = new Vector<Double>();
		m_setTitle = new ArrayList<String>();
		m_title = title;
	}
	
	public Grapher(Collection<Double> xasis){
		m_data = new ArrayList<Vector<Double>>();
		m_xasis = new Vector<Double>();
		m_setTitle = new ArrayList<String>();
		m_title = "";
		m_xasis.addAll(xasis);
	}
	
	public Grapher(String title, Collection<Double> xasis){
		m_data = new ArrayList<Vector<Double>>();
		m_xasis = new Vector<Double>();
		m_setTitle = new ArrayList<String>();
		m_title = title;
		m_xasis.addAll(xasis);
	}
	
	public void SetXData(Collection<Double> xasis) throws Exception{
		if(m_data.size() != 0){
			Iterator<Vector<Double>> it = m_data.iterator();
			while(it.hasNext()){
				Vector<Double> next = it.next();
				if(xasis.size() != next.size())throw new Exception("Size xasis not equal to size values");
			}
		}
		
		m_xasis.clear();
		m_xasis.addAll(xasis);
	}
	
	public void addSet(String title, Collection<Double> data) throws Exception{
		if(data.size() != m_xasis.size())throw new Exception("Size data not equal to size x_as");
		Vector<Double> temp = new Vector<Double>(data);
		m_data.add(temp);
		m_setTitle.add(title);
	}
	
	public boolean printLineChart(String file, int width, int height, String xTitle, String yTitle) throws Exception{
		double[][] data = Calculator.toPrimitiveArray(m_data);
		double[] xas = Calculator.toPrimitiveDouble(m_xasis);
		String[] setTitle = toPrimitiveString(m_setTitle);
		System.out.println("Start modelling");
		DefaultChartDataModel model = new DefaultChartDataModel(data,
				xas, setTitle);
		model.setAutoScale(true);
		DefaultChart c = new DefaultChart(model, m_title, DefaultChart.LINEAR_X_LINEAR_Y, xTitle, yTitle);
		LineChartRenderer lc = new LineChartRenderer(c.getCoordSystem(), model);
		lc.setRowColorModel(new RowColorModel(model));
		c.addChartRenderer(lc, 1);
		c.setBounds(new Rectangle(0, 0, width, height));
		System.out.println("Create PNG");
		ChartEncoder.createPNG(new FileOutputStream(file), c);
		//ChartEncoder.
		
		return true;
		
	}
	
	public boolean printInterpolatingChart(String file, int width, int height, String xTitle, String yTitle) throws Exception{
		double[][] data = Calculator.toPrimitiveArray(m_data);
		double[] xas = Calculator.toPrimitiveDouble(m_xasis);
		String[] setTitle = toPrimitiveString(m_setTitle);
		
		DefaultChartDataModel model = new DefaultChartDataModel(data,
				xas, setTitle);
		model.setAutoScale(true);
		DefaultChart c = new DefaultChart(model, m_title, DefaultChart.LINEAR_X_LINEAR_Y, xTitle, yTitle);
		InterpolationChartRenderer ipr = new InterpolationChartRenderer(c.getCoordSystem(), model);
		ipr.setRowColorModel(new RowColorModel(model));
		c.addChartRenderer(ipr, 1);
		c.setBounds(new Rectangle(0, 0, width, height));
		
		ChartEncoder.createPNG(new FileOutputStream(file), c);
		
		return true;
		
	}
	
	private String[] toPrimitiveString(Collection<String> input){
		String[] result = new String[input.size()];
		Iterator<String> it = input.iterator();
		int i = 0;
		while(it.hasNext()){
			result[i] = it.next();
			i++;
		}
		return result;
	}
	
//	public static void main(String[] args) {
//		try{
//			Double[] xas = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
//			Grapher test = new Grapher("A First Test", new Vector<Double>(Arrays.asList(xas)));
//			Vector<Double> first = new Vector<Double>();
//			first.add(30.0);
//			first.add(45.2);
//			first.add(42.1);
//			first.add(31.9);
//			first.add(50.2);
//			first.add(10.0);
//			Vector<Double> second = new Vector<Double>();
//			second.add(30.0);
//			second.add(10.0);
//			second.add(42.1);
//			second.add(31.9);
//			second.add(45.2);
//			second.add(50.2);
//			Vector<Vector<Double>> data = new Vector<Vector<Double>>();
//			data.add(first);
//			test.addSet("DataSet 1", first);
//			test.addSet("DataSet 2", second);
//			test.addSet("DataSet 3", first);
//			test.printLineChart("testChart.png", 640, 480);
//			test.printInterpolatingChart("Interpolation.png", 640, 480);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

}
