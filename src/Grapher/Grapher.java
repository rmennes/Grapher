package Grapher;

import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Marker;
import com.googlecode.charts4j.Markers;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.XYLine;
import com.googlecode.charts4j.XYLineChart;

public class Grapher {
	final int maxCount = 10;
	final Color[]	m_colors = {Color.GREEN, Color.TOMATO,
			Color.GOLD};

	ArrayList<Data>			 	m_data;
	ArrayList<List<Double>>		m_sets;
	ArrayList<String>			m_legends;
	Data						m_xaxis;
	String						m_title;
	
	double maxY		= Double.NEGATIVE_INFINITY;
	double minY		= Double.POSITIVE_INFINITY;
	double maxX		= Double.NEGATIVE_INFINITY;
	double minX		= Double.POSITIVE_INFINITY;
	
	public Grapher() {
		m_data = new ArrayList<Data>();
		m_sets = new ArrayList<List<Double>>();
		m_legends = new ArrayList<String>();
		m_title = "";
	}
	
	public Grapher(String title){
		m_data = new ArrayList<Data>();
		m_sets = new ArrayList<List<Double>>();
		m_legends = new ArrayList<String>();
		m_title = title;
	}
	
	public Grapher(Collection<Double> xasis){
		m_data = new ArrayList<Data>();
		m_sets = new ArrayList<List<Double>>();
		m_legends = new ArrayList<String>();
		m_title = "";
	}
	
	public Grapher(String title, Collection<Double> xasis){
		m_data = new ArrayList<Data>();
		m_sets = new ArrayList<List<Double>>();
		m_legends = new ArrayList<String>();
		m_title = title;
	}
	
	public void SetXData(List<Double> xaxis) throws Exception{
		// Reset scale
		maxX		= Double.NEGATIVE_INFINITY;
		minX		= Double.POSITIVE_INFINITY;
		
		int count = 0;
		Iterator<Double> it = xaxis.iterator();
		while (it.hasNext()) {
			count++;
			Double val = it.next();
			if (val < minX) minX = val;
			if (val > maxX) maxX = val;
		}
		m_xaxis = DataUtil.scale(xaxis.subList(0, maxCount));
	}
	
	public void addSet(String title, List<Double> data) throws Exception{
		if(data.size() != m_xaxis.getSize()) throw new Exception("Size data not equal to size x_as");
		m_legends.add(title);
		m_sets.add(data);
		Iterator<Double> it = data.iterator();
		while (it.hasNext()) {
			Double val = it.next();
			if (val < minY) minY = val;
			if (val > maxY) maxY = val;
		}
		/*
		Data newData = null;
		try {
			newData = DataUtil.scale(data.subList(0, maxCount));
			m_data.add(newData);
			
		
			int count = 0;
			Iterator<Double> it = data.iterator();
			while (it.hasNext()) {
				count++;
				Double val = it.next();
				if (val < minY) minY = val;
				if (val > maxY) maxY = val;
			}
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}*/
	}
	
	public String getLineChartURL(int width, int height, String xTitle, String yTitle) throws Exception {
		// Manual scaling ftw
		System.err.println("About to scale manually");
		Iterator<List<Double>> it = m_sets.iterator();
		while (it.hasNext()) {
			System.out.println("Scaaaliiiing");
			List<Double> oldData = it.next();
			List<Double> newData = new ArrayList<Double>();
			Iterator<Double> listIt = oldData.iterator();
			while (listIt.hasNext()) {
				Double scaledValue = ((listIt.next() - minY) / (maxY - minY)) * 100;
				newData.add(scaledValue);
			}
			m_data.add(Data.newData(newData));
		}
		
		System.err.println("About to plot");
		Vector<Plot> plots = new Vector<Plot>();
		for (int i = 0; i < m_data.size(); i++) {
			Data data = m_data.get(i);
			String title = m_legends.get(i);
			XYLine line = Plots.newXYLine(m_xaxis, data);
			line.setLegend(title);
			line.setColor(m_colors[i % m_colors.length]);
			plots.add(line);
		}
		System.out.println("Width and height: " + width + ", "+ height);
		System.out.println("MinX, MaxX: " + minX + ", " + maxX);
		System.out.println("MinY, MaxY: " + minY + ", " + maxY);
		AxisLabels xaxis = AxisLabelsFactory.newNumericRangeAxisLabels(minX, maxX);
		AxisLabels yaxis = AxisLabelsFactory.newNumericRangeAxisLabels(minY, maxY);
		AxisLabels xaxisTitle = AxisLabelsFactory.newAxisLabels(xTitle, 99);
		AxisLabels yaxisTitle = AxisLabelsFactory.newAxisLabels(yTitle, 99);
		
		XYLineChart chart = GCharts.newXYLineChart(plots);
		chart.setSize(width, height);
		chart.setMargins(20, 20, 20, 20);
		chart.setGrid(100. / (m_xaxis.getSize() - 1), 100. / (m_xaxis.getSize() - 1), 1000, 1000);
		chart.addXAxisLabels(xaxis);
		chart.addXAxisLabels(xaxisTitle);
		chart.addYAxisLabels(yaxis);
		chart.addYAxisLabels(yaxisTitle);
		chart.setTitle(m_title);
		return chart.toURLString();
	}
}
