package com.Proyecto.Histograma;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;


import org.jfree.chart.ChartFactory;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.data.xy.XYDataset;


public class Histograma {

    public JFreeChart createESenseBarChart(DefaultCategoryDataset dataset,String title, String domain) {
        
        final JFreeChart chart = ChartFactory.createBarChart(
        		title,         // chart title
            domain,               // domain axis label
            "",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, 100.0); 

        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0d);

        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.GRAY, 
            0.0f, 0.0f, Color.GRAY
        );
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.BLACK, 
            0.0f, 0.0f, Color.BLACK
        );

        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
       
        return chart;
        
    }
    
    
public JFreeChart createEEGPowerBarChart(final CategoryDataset dataset, String title, String domain) {
        final JFreeChart chart = ChartFactory.createBarChart(
        		title,         // chart title
            domain,               // domain axis label
            "",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0d);
        
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f,new Color(219,211,42), 
            0.0f, 0.0f,new Color(219,211,42)
        );
        final GradientPaint gp1 = new GradientPaint(
        		 0.0f, 0.0f,new Color(245,80,71), 
        		 0.0f, 0.0f,new Color(245,80,71)
        );
        final GradientPaint gp2 = new GradientPaint(
        		 0.0f, 0.0f,new Color(237,0,119), 
                0.0f, 0.0f, new Color(237,0,119)
            );
        final GradientPaint gp3 = new GradientPaint(
                0.0f, 0.0f,new Color(212,0,149), 
                0.0f, 0.0f,new Color(212,0,149)
            );
        final GradientPaint gp4 = new GradientPaint(
                0.0f, 0.0f,new Color(158,18,188), 
                0.0f, 0.0f, new Color(158,18,188)
            );
        final GradientPaint gp5 = new GradientPaint(
                0.0f, 0.0f,new Color(116,23,190), 
                0.0f, 0.0f, new Color(116,23,190)
            );
        final GradientPaint gp6 = new GradientPaint(
                0.0f, 0.0f,new Color(39,35,159), 
                0.0f, 0.0f, new Color(39,35,159)
            );
        final GradientPaint gp7 = new GradientPaint(
                0.0f, 0.0f,new Color(23,26,153), 
                0.0f, 0.0f, new Color(23,26,153)
            );
        
        
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);
        renderer.setSeriesPaint(3, gp3);
        renderer.setSeriesPaint(4, gp4);
        renderer.setSeriesPaint(5, gp5);
        renderer.setSeriesPaint(6, gp6);
        renderer.setSeriesPaint(7, gp7);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        return chart;
	}

public JFreeChart createESenseLineChart(XYDataset dataset) {
    JFreeChart result = ChartFactory.createTimeSeriesChart(
        "eSense", 
        "", 
        "",
        dataset, 
        true, 
        true, 
        false
    );
    XYPlot plot = result.getXYPlot();
    ValueAxis axis = plot.getDomainAxis();
    axis.setFixedAutoRange(60000.0); // 60 seconds
    axis = plot.getRangeAxis();
    axis.setAutoRange(true);


	ValueAxis yAxis =plot.getRangeAxis();
	yAxis.setAutoRange(false);
	yAxis.setRange(0.0, 100.0); 
    
  
	XYItemRenderer renderer = plot.getRenderer();
	renderer.setSeriesStroke(0 , new BasicStroke(2f)); 
	renderer.setSeriesStroke(1, new BasicStroke(2f));
		
		 // set up gradient paints for series...
    final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.GRAY, 
            0.0f, 0.0f, Color.GRAY
        );
    final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.BLACK, 
            0.0f, 0.0f, Color.BLACK
        );    
    renderer.setSeriesPaint(0 , gp0); 
	renderer.setSeriesPaint(1, gp1);
		
    return result;
}

public JFreeChart createEEGPowerLineChart(XYDataset dataset) {
    JFreeChart result = ChartFactory.createTimeSeriesChart(
        "eegPower", 
        "", 
        "",
        dataset, 
        true, 
        true, 
        false
    );
    XYPlot plot = result.getXYPlot();
    ValueAxis axis = plot.getDomainAxis();
    axis.setAutoRange(true);
    axis.setFixedAutoRange(60000.0);  // 60 seconds
    axis = plot.getRangeAxis();
    axis.setRange(0.0, 200.0); 
    
	ValueAxis yAxis =plot.getRangeAxis();
	yAxis.setAutoRange(true);
    		
    
  
	XYItemRenderer renderer = plot.getRenderer();
	renderer.setSeriesStroke(0,new BasicStroke(2f));
	renderer.setSeriesStroke(1,new BasicStroke(2f));
	renderer.setSeriesStroke(2,new BasicStroke(2f));
	renderer.setSeriesStroke(3,new BasicStroke(2f));
	renderer.setSeriesStroke(4,new BasicStroke(2f));
	renderer.setSeriesStroke(5,new BasicStroke(2f));
	renderer.setSeriesStroke(6,new BasicStroke(2f));
		renderer.setSeriesStroke(7,new BasicStroke(2f));
		
    
    return result;
}

}
