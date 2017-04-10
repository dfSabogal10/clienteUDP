package clienteudp.backend;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
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
		socketCliente.setReceiveBufferSize(64000);
	}

	public void enviarPaquetes() throws Exception{
		for (int i = 0; i < numObjetosAEnviar; i++) {
			byte[] sendData = new byte[1024];
			ObjetoUDP aEnviar=new ObjetoUDP(i+1,new Date(),numObjetosAEnviar);
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(aEnviar);
			sendData = b.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipServidor, puertoServidor);
			socketCliente.send(sendPacket);
			System.out.println("objeto "+ aEnviar.getNumeroSecuencia()+" de "+aEnviar.getNumeroTotal() +" enviado a las "+aEnviar.getMarcaTiempo());
		}
	}

	public void enviarArchivo(File archivoAEnviar) throws Exception{
		System.out.println("envio archivo");
		// calculate hash

		MessageDigest md = MessageDigest.getInstance("SHA1");
	    FileInputStream fis = new FileInputStream(archivoAEnviar);
	    byte[] dataBytes = new byte[1024];

	    int nread = 0;

	    while ((nread = fis.read(dataBytes)) != -1) {
	      md.update(dataBytes, 0, nread);
	    };

	    byte[] mdbytes = md.digest();

	    //convert the byte to hex format
	    StringBuffer sb = new StringBuffer("");
	    for (int i = 0; i < mdbytes.length; i++) {
	    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }

	    System.out.println("Digest(in hex format):: " + sb.toString());

		//send fileInfo
	    byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = "ARCHIVO:"+archivoAEnviar.getName()+":"+sb.toString()+":"+archivoAEnviar.length()+":";
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipServidor, puertoServidor);
		socketCliente.send(sendPacket);
		System.out.println("envio info: "+sentence);

		// send file
		sendData =new byte[(int) archivoAEnviar.length()];
		FileInputStream fis2 = new FileInputStream(archivoAEnviar);
		BufferedInputStream bis = new BufferedInputStream(fis2);
		bis.read(sendData,0,sendData.length);
		System.out.println("longitud archivo: "+sendData.length);
		for (int i = 0; i < sendData.length; i++) {
			
			if((sendData.length-i)>= 64000){
				
				sendPacket = new DatagramPacket(sendData, i,64000, ipServidor, puertoServidor);
				socketCliente.send(sendPacket);
				i+=(64000-1);
				System.out.println("enviados "+i+" bytes del archivo...");
			}
			else{
				sendPacket = new DatagramPacket(sendData, i,sendData.length-i, ipServidor, puertoServidor);
				socketCliente.send(sendPacket);
				System.out.println("enviado ultimo paquete de"+(i+(sendData.length-i))+" bytes del archivo.");
				i+=(64000-1);


			}
			
		}

	}

}
