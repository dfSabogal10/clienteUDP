package clienteudp.backend;

import java.io.*;
import java.net.*;
import java.util.Date;

public class clienteUDP {

	private int numObjetosAEnviar;

	private InetAddress ipServidor;

	private int puertoServidor;

	private DatagramSocket socketCliente;

	public clienteUDP(int numObjetosAEnviar, InetAddress ipServidor, int puertoServidor) {
		this.numObjetosAEnviar = numObjetosAEnviar;
		this.ipServidor = ipServidor;
		this.puertoServidor = puertoServidor;
	}

	public void setNumObjetosAEnviar(int numObjetosAEnviar) {
		this.numObjetosAEnviar = numObjetosAEnviar;
	}

	public void setIpServidor(InetAddress ipServidor) {
		this.ipServidor = ipServidor;
	}

	public void setPuertoServidor(int puertoServidor) throws Exception {
			this.puertoServidor = puertoServidor;
	}

	public void establecerConexion() throws Exception{
	
		socketCliente = new DatagramSocket(9876);
	}

	public void enviarPaquetes() throws Exception{
		for (int i = 0; i < numObjetosAEnviar; i++) {
			byte[] sendData = new byte[1024];
			ObjetoUDP aEnviar=new ObjetoUDP(i+1,new Date());
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(aEnviar);
			sendData = b.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipServidor, puertoServidor);
			socketCliente.send(sendPacket);
			System.out.println("objeto "+ aEnviar.getNumeroSecuencia()+" enviado a las "+aEnviar.getMarcaTiempo());
		}
	}

	public void enviarArchivo(File archivoAEnviar) {
		// TODO Auto-generated method stub
		System.out.println("envio archivo");
	}






}
