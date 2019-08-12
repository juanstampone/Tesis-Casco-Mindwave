package com.Proyecto.ThinkGear;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingWorker;

import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONObject;
import com.Proyecto.Histograma.Histograma;


public class ThinkGearWorker extends SwingWorker<Void, Void> {
	private ThinkGearConexion connector = null;
	private final static int CONNECT_INTERVAL = 3000;
	private final static int MAX_CONNECT_RETRIES = 5;
	
	private ThinkGearSeñal senial = null;
	
	private DefaultCategoryDataset dataseteSense;
	private DefaultCategoryDataset datasetEeePower;
	private File file;
	private FileWriter escribir;
	
	private boolean guardar = false;
	
	public ThinkGearWorker(){
		setConnector(new ThinkGearConexion());
	}
	
/*
    public void done() {

		try {
			connector.desconectar();
		} catch (IOException e) {
			System.out.println("error2as");
			e.printStackTrace();
		}
		
		System.out.println("Stopped");
		System.out.println("Connected: " + connector.estaConectado());
		
    }
*/
	public ThinkGearConexion getConnector() {
		return connector;
	}
	public void setConnector(ThinkGearConexion connector) {
		this.connector = connector;
	}


	@Override
	protected Void doInBackground() throws Exception {
		int counter = 0;
 		while (!connector.estaConectado() && counter < MAX_CONNECT_RETRIES){
 			System.out.println("conectando....");
    		
    		try {
	    			connector.conectar();
	    	} catch (IOException e) {
	    		counter++;
				System.out.println("Connection failed. Count : " + counter);
				e.printStackTrace();
				Thread.currentThread();
				try {
					Thread.sleep(CONNECT_INTERVAL);
				} catch (InterruptedException e1) {
						//Logger.log(e1.toString());
				}
			}
    		System.out.println("llego");
    	}
 		if (!connector.estaConectado()) 
 			System.out.println("putin");
 		else
 		{
 			while (connector.hayDatosDisponible()) {
 				String datos = connector.getDatos();
 				JSONObject j = new JSONObject(datos);
 				System.out.println(datos);
 				
 				if(!j.isNull("eSense")) {
 					JSONObject eSense = j.getJSONObject("eSense");
					dataseteSense.setValue(eSense.getInt("attention"), "Atencion", "");
					dataseteSense.setValue(eSense.getInt("meditation"), "Meditacion", "");
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
 				}
 				if (!j.isNull("poorSignalLevel")) {
 					int nivelSeñal = 200 - j.getInt("poorSignalLevel");
 					nivelSeñal= (int)(((double)nivelSeñal/(double)200)*100);
 					senial.setStrength(nivelSeñal);
 				}				
 				if (guardar){
					if (escribir == null){			
						escribir = new FileWriter(file);
						escribir.append(j.toString());
    					escribir.append('\n'); 
						escribir.flush();	
					}else{
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
 		}
		return null;
 		
	}


	public ThinkGearSeñal getSeñal() {
		return senial;
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

	
}
	
	

