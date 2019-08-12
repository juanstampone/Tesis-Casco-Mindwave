package com.Proyecto.Tesis;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.Proyecto.Histograma.Histograma;
import com.Proyecto.ThinkGear.ThinkGear;
import com.Proyecto.ThinkGear.ThinkGearSeñal;




import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;


import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSpinner;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final String A_CONECTAR = "Conectar";
	private static final String A_DESCONECTAR = "Desconectar";
	private Histograma histo;
	private ThinkGearSeñal tgSenal;
	private ThinkGear tg;
	private boolean charType = true;
	private static final boolean CHART_TYPE_LINE = true;
	private static final boolean CHART_TYPE_BAR = false;
	private ChartPanel cpEeePower;
	private ChartPanel cpESense;
	private DefaultCategoryDataset dsEeePower;
	private DefaultCategoryDataset dsESense;
	private TimeSeriesCollection dsESenseTime;
	private TimeSeriesCollection dsEegPower;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1060, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
					
		histo=new Histograma();	
		
		dsESense = new DefaultCategoryDataset();
		dsEeePower = new DefaultCategoryDataset();
		dsESenseTime = new TimeSeriesCollection();
		dsEegPower = new TimeSeriesCollection();
		//ChartPanel cpESense = new ChartPanel(histo.createESenseBarChart(dsESense, "", "eSense"));
		
		dsESenseTime.addSeries(new TimeSeries("Atencion"));
		dsESenseTime.addSeries(new TimeSeries("Meditacion"));
		Millisecond now = new Millisecond();
		dsESenseTime.getSeries(0).add(now, 0);
	
		dsEegPower.addSeries(new TimeSeries("Delta"));
		dsEegPower.addSeries(new TimeSeries("Theta"));
		dsEegPower.addSeries(new TimeSeries("LowAlpha"));
		dsEegPower.addSeries(new TimeSeries("HighAlpha"));
		dsEegPower.addSeries(new TimeSeries("LowBeta"));
		dsEegPower.addSeries(new TimeSeries("HighBeta"));
		dsEegPower.addSeries(new TimeSeries("LowGamma"));
		dsEegPower.addSeries(new TimeSeries("HighGamma"));	
		
	
		
		JFreeChart eSenseLineChart = histo.createESenseLineChart(dsESenseTime);
		JFreeChart eegPowerLineChart = histo.createEEGPowerLineChart(dsEegPower);
		cpESense = new ChartPanel(eSenseLineChart);
		
		cpESense.setSize(250, 300);
		cpESense.setLocation(146, 33);
		contentPane.add(cpESense);
		
				
	
		cpEeePower = new ChartPanel(eegPowerLineChart);
		
		
		
		cpEeePower.setSize(619, 300);
		cpEeePower.setLocation(415, 33);
		contentPane.add(cpEeePower);
		
		JLabel lblSeal = new JLabel("Señal:");
		lblSeal.setBounds(10, 29, 46, 14);
		contentPane.add(lblSeal);
		
		tgSenal = new ThinkGearSeñal();
		tgSenal.setSize(30, 14);
		tgSenal.setLocation(58, 29);
		contentPane.add(tgSenal);
				
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(10, 54, 115, 23);
		contentPane.add(btnConectar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setEnabled(false);
		btnGuardar.setBounds(10, 88, 115, 23);
		contentPane.add(btnGuardar);
		
		JRadioButton rdbtnLineChart = new JRadioButton("Line Chart");
		rdbtnLineChart.setSelected(true);
		rdbtnLineChart.setBounds(10, 118, 115, 23);
		rdbtnLineChart.addActionListener(this);
		contentPane.add(rdbtnLineChart);
		
		JRadioButton rdbtnBarChart = new JRadioButton("Bar Chart");
		rdbtnBarChart.setBounds(10, 144, 109, 23);
		rdbtnBarChart.addActionListener(this);
		contentPane.add(rdbtnBarChart);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnBarChart);
		bg.add(rdbtnLineChart);
		
		JLabel lblPestaeo = new JLabel("Pestañeo:");
		lblPestaeo.setBounds(10, 356, 63, 14);
		contentPane.add(lblPestaeo);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(83, 353, 46, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//BOTON CONECTAR/DESCONECTAR CON NEUROSKY
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().equals(A_CONECTAR)) {
					tg = new ThinkGear();
					tg.setDseSense(dsESenseTime);
					tg.setDatasetEeePower(dsEeePower);
					tg.setDataset(dsESense);
					tg.setDseegPower(dsEegPower);
					tg.setSeñal(tgSenal);
					tg.setPestaneo(textField);
					Thread tgThread = new Thread(tg);
					tgThread.start();
					btnConectar.setActionCommand(A_DESCONECTAR);
					btnConectar.setText("Desconectar");
					btnGuardar.setEnabled(true);
				}
				else if (arg0.getActionCommand().equals(A_DESCONECTAR)) {
					if (tg != null) {
						tg.setSalir();
						tg = null;
						btnGuardar.setEnabled(false);
						btnConectar.setActionCommand(A_CONECTAR);
						btnConectar.setText("Conectar");
					}
				}
			}
		});
		
		//GUARDAR
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Guardar")) {
				JFileChooser file = new JFileChooser();
				FileNameExtensionFilter fExt = new FileNameExtensionFilter("*.json", "json");
				file.setFileFilter(fExt);
				file.showSaveDialog(file);
				File ruta = file.getSelectedFile();
				if (ruta != null) {
					tg.setFile(ruta);
					tg.setGuardar(true);
					btnGuardar.setActionCommand("Parar");
					btnGuardar.setText("Parar");
				}

			}
			else if (arg0.getActionCommand().equals("Parar")) {
				tg.setGuardar(false);
				btnGuardar.setActionCommand("Guardar");
				btnGuardar.setText("Guardar");
			}
			}
		});	
		/////////////////////////////////////////	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Line Chart")) {
			JRadioButton j =  (JRadioButton) e.getSource();
			if (j.isSelected())
				switchChart(CHART_TYPE_LINE);
		}
		else if (e.getActionCommand().equals("Bar Chart")) {
			JRadioButton j =  (JRadioButton) e.getSource();
			if (j.isSelected()) {
				switchChart(CHART_TYPE_BAR);
			}
		}
	}
	private void switchChart(boolean type) {
		if (type != charType){
			if (type == CHART_TYPE_LINE ){
				cpESense.setChart(histo.createESenseLineChart(dsESenseTime));
				cpEeePower.setChart(histo.createEEGPowerLineChart(dsEegPower));
			}if (type == CHART_TYPE_BAR ){
				cpESense.setChart(histo.createESenseBarChart(dsESense, "","eSense"));
				cpEeePower.setChart(histo.createEEGPowerBarChart(dsEeePower, "","eegPower"));

			}
			this.charType = !this.charType;
		}
	}
}
