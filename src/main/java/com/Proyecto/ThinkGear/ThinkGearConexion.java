package com.Proyecto.ThinkGear;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Scanner;


public class ThinkGearConexion {
	public static final String DEFAULT_HOST = "127.0.0.1";
	public static final int DEFAULT_PORT = 13854;
	
	private SocketChannel canal;
	private Scanner in;
	private boolean estaConectado = false;
	
	public ThinkGearConexion() {
		canal = null;
		in = null;
	}

	
	public void conectar() throws IOException {
		if (canal == null || !canal.isConnected()) {
			System.out.println("Empezando Conexion");
			this.canal = SocketChannel.open( new InetSocketAddress(DEFAULT_HOST, DEFAULT_PORT));			
			boolean rawOutput = false; 
			String cmd = "{\"enableRawOutput\": " +rawOutput+ ", \"format\": \"Json\"}\n";
			enviarComando(cmd);
			estaConectado = true;
			this.in = new Scanner(canal);
		}
	}

	public boolean hayDatosDisponible() {
		if(in != null) 
			return in.hasNextLine();
		else
			return false;
	}
	
	public String getDatos() {
		return in.nextLine();
	}
	

	private void enviarComando(String cmd) {
		CharsetEncoder enc = Charset.forName("US-ASCII").newEncoder();		
		try {
			this.canal.write(enc.encode(CharBuffer.wrap(cmd)));
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public void desconectar() throws IOException {
		if (estaConectado) {
			System.out.println("Desconectado");
			canal.close();
			in.close();
			estaConectado = false;
		}
		
	}

	 boolean estaConectado() {
		return estaConectado;
	}
	
	
}
