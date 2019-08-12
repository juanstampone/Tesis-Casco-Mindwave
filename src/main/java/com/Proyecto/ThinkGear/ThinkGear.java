package com.Proyecto.ThinkGear;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeriesCollection;
import org.json.JSONException;
import org.json.JSONObject;

public class ThinkGear implements Runnable {
	private ThinkGearConexion connector = null;

	private ThinkGearSeñal senial = null;
	
	private DefaultCategoryDataset dataseteSense;
	private DefaultCategoryDataset datasetEeePower;
	private TimeSeriesCollection dseSense;
	private TimeSeriesCollection dseegPower;
	JTextField textField;
	private File file;
	private FileWriter escribir;
	
	private boolean guardar = false;
	private int pestaneo = 0;
	private boolean salir= false;
	
	public void setSalir() {
		salir = true;
	}
	
	public ThinkGear(){
		connector = new ThinkGearConexion();
	}
	
	@Override
	public void run() {		   
        try {
            this.connector.conectar();
            System.out.println("Conectado");
        }catch(IOException e){
            System.err.println("No se pudo establecer conexion");
        }
        
        while(!salir){
            if(this.connector.hayDatosDisponible()){           	
            	String datos = connector.getDatos(); 				
				try {
					JSONObject j = new JSONObject(datos);
					System.out.println(datos);
					Millisecond now = new Millisecond();
					
	 				if(!j.isNull("eSense")) {
	 					JSONObject eSense = j.getJSONObject("eSense");
						dataseteSense.setValue(eSense.getInt("attention"), "Atencion", "");
						dataseteSense.setValue(eSense.getInt("meditation"), "Meditacion", "");
	 					dseSense.getSeries(0).add(now, eSense.getInt("attention"));
	 					dseSense.getSeries(1).add(now, eSense.getInt("meditation"));
	 				}
	 				if (!j.isNull("eegPower")) {
	 					JSONObject eegPower = j.getJSONObject("eegPower");
	 					datasetEeePower.setValue(eegPower.getInt("delta"), "Delta", "");
	 					datasetEeePower.setValue(eegPower.getInt("theta"), "Theta", "");
	 					datasetEeePower.setValue(eegPower.getInt("lowAlpha"), "LowAlpha", "");
	 					datasetEeePower.setValue(eegPower.getInt("highAlpha"), "HighAlpha", "");
	 					datasetEeePower.setValue(eegPower.getInt("lowBeta"), "LowBeta", "");
	 					datasetEeePower.setValue(eegPower.getInt("highBeta"), "HighBeta", "");
	 					datasetEeePower.setValue(eegPower.getInt("lowGamma"), "LowGamma", "");
	 					datasetEeePower.setValue(eegPower.getInt("highGamma"), "HighGamma", "");	
	 					
	 					dseegPower.getSeries(0).add(now, (double)eegPower.getInt("delta"));
	 					dseegPower.getSeries(1).add(now, (double)eegPower.getInt("theta"));
	 					dseegPower.getSeries(2).add(now, (double)eegPower.getInt("lowAlpha"));
	 					dseegPower.getSeries(3).add(now, (double)eegPower.getInt("highAlpha"));
	 					dseegPower.getSeries(4).add(now, (double)eegPower.getInt("lowBeta"));
	 					dseegPower.getSeries(5).add(now, (double)eegPower.getInt("highBeta"));
	 					dseegPower.getSeries(6).add(now, (double)eegPower.getInt("lowGamma"));
	 					dseegPower.getSeries(7).add(now, (double)eegPower.getInt("highGamma"));
	 				}
	 				if (!j.isNull("poorSignalLevel")) {
	 					int nivelSeñal = 200 - j.getInt("poorSignalLevel");
	 					nivelSeñal= (int)(((double)nivelSeñal/(double)200)*100);
	 					senial.setStrength(nivelSeñal);
	 				}
	 				if (!j.isNull("blinkStrength")) {
	 					pestaneo++;
	 					textField.setText("" +pestaneo);
	 				}
	 				if (guardar){
						if (escribir == null){			
							escribir = new FileWriter(file + ".json");
							escribir.append(j.toString());
	    					escribir.append('\n'); 
							escribir.flush();
							System.out.println("entro una sola vez");
						}else{
							System.out.println("muchas veces");
								escribir.append(j.toString());
								escribir.append('\n'); 
								escribir.flush();    					
						}					
					}else 
						if (escribir != null){
							escribir.close();
							escribir = null;
							file = null;
						}
	 			
	            }
				 catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}			
        }
            else{
                try {
                    Thread.sleep(200);
                }catch(InterruptedException e){}
            }
        }        
        try {
        	this.senial.setStrength(0);
        	System.out.println(pestaneo);
        	pestaneo= 0;
            this.connector.desconectar();
            this.connector=null;
        }catch(IOException e){
            System.err.println("No se pudo cerrar la conexion");
        }
	
		
	}

	public ThinkGearSeñal getSeñal() {
		return senial;
	}

	public void setPestaneo(JTextField tField) {
		textField = tField;
	}
	public void setSeñal(ThinkGearSeñal señal) {
		this.senial = señal;
	}

	public DefaultCategoryDataset getDataset() {
		return dataseteSense;
	}

	public void setDataset(DefaultCategoryDataset dataset) {
		this.dataseteSense = dataset;
	}

	public DefaultCategoryDataset getDatasetEeePower() {
		return datasetEeePower;
	}

	public void setDatasetEeePower(DefaultCategoryDataset datasetEeePower) {
		this.datasetEeePower = datasetEeePower;
	}


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isGuardar() {
		return guardar;
	}

	public void setGuardar(boolean guardar) {
		this.guardar = guardar;
	}

	public TimeSeriesCollection getDseSense() {
		return dseSense;
	}

	public void setDseSense(TimeSeriesCollection dseSense) {
		this.dseSense = dseSense;
	}

	public TimeSeriesCollection getDseegPower() {
		return dseegPower;
	}

	public void setDseegPower(TimeSeriesCollection dseegPower) {
		this.dseegPower = dseegPower;
	}

	
}
