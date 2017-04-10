package clienteudp.frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clienteudp.backend.clienteUDP;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class clienteInteface extends JFrame {

	private JPanel contentPane;
	private JTextField txtPuertoServidor;
	private JTextField txtIPServidor;
	private JTextField txtNumObjetos;
	private JButton btnEnviar;
	private clienteUDP backend;
	private JButton btnEscogerArchivo;
	private JRadioButton rdbtnPermitirEnvioArchivos;
	private File archivoAEnviar;
	private JLabel lblArchivoCargado;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clienteInteface frame = new clienteInteface();
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
	public clienteInteface() {
		try {
			backend=new clienteUDP(1, InetAddress.getByName("127.0.0.1"), 9867);
			backend.establecerConexion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIpServidor = new JLabel("DIreccion IP del \nServidor");
		lblIpServidor.setBounds(36, 98, 206, 15);
		contentPane.add(lblIpServidor);

		JLabel lblPuertoServidor = new JLabel("Puerto escucha del Servidor");
		lblPuertoServidor.setBounds(36, 127, 206, 15);
		contentPane.add(lblPuertoServidor);

		JLabel lblNumeroDeObjetos = new JLabel("Numero de Objetos a Enviar");
		lblNumeroDeObjetos.setBounds(36, 149, 206, 29);
		contentPane.add(lblNumeroDeObjetos);

		txtIPServidor = new JTextField();
		txtIPServidor.setBounds(260, 96, 144, 19);
		contentPane.add(txtIPServidor);
		txtIPServidor.setColumns(10);

		txtPuertoServidor = new JTextField();
		txtPuertoServidor.setColumns(10);
		txtPuertoServidor.setBounds(260, 125, 144, 19);
		contentPane.add(txtPuertoServidor);

		txtNumObjetos = new JTextField();
		txtNumObjetos.setColumns(10);
		txtNumObjetos.setBounds(260, 154, 144, 19);
		contentPane.add(txtNumObjetos);

		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					backend.setIpServidor(InetAddress.getByName(txtIPServidor.getText()));
					backend.setPuertoServidor(Integer.parseInt(txtPuertoServidor.getText()));
					backend.setNumObjetosAEnviar(Integer.parseInt(txtNumObjetos.getText()));
					backend.enviarPaquetes();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnEnviar.setBounds(87, 238, 154, 25);
		contentPane.add(btnEnviar);

		JLabel lblClienteUdp = new JLabel("Cliente UDP");
		lblClienteUdp.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClienteUdp.setBounds(162, 12, 136, 29);
		contentPane.add(lblClienteUdp);

		btnEscogerArchivo = new JButton("Escoger archivo");
		JFrame esto = this;
		btnEscogerArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(esto);
				if (returnVal ==  fc.APPROVE_OPTION){
					archivoAEnviar = fc.getSelectedFile();
					lblArchivoCargado.setText("Archivo cargado: "+archivoAEnviar.getName() );
				}



			}
		});
		btnEscogerArchivo.setBounds(259, 238, 162, 25);
		contentPane.add(btnEscogerArchivo);
		btnEscogerArchivo.setEnabled(false);

		rdbtnPermitirEnvioArchivos = new JRadioButton("permitir envio archivos");
		rdbtnPermitirEnvioArchivos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (rdbtnPermitirEnvioArchivos.isSelected())
				{
					btnEscogerArchivo.setEnabled(true);
					txtNumObjetos.setEnabled(false);
				}
				else{
					btnEscogerArchivo.setEnabled(false);
					txtNumObjetos.setEnabled(true);
				}
			}
		});
		rdbtnPermitirEnvioArchivos.setBounds(33, 61, 217, 23);
		contentPane.add(rdbtnPermitirEnvioArchivos);
		
		lblArchivoCargado = new JLabel("Archivo cargado:");
		lblArchivoCargado.setBounds(36, 190, 368, 15);
		contentPane.add(lblArchivoCargado);
	}
}
