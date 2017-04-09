package clienteudp.frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clienteudp.backend.clienteUDP;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.awt.event.ActionEvent;

public class clienteInteface extends JFrame {

	private JPanel contentPane;
	private JTextField txtPuertoServidor;
	private JTextField txtIPServidor;
	private JTextField txtNumObjetos;
	private JButton btnEnviar;
	private clienteUDP backend;


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
		lblIpServidor.setBounds(33, 67, 206, 15);
		contentPane.add(lblIpServidor);

		JLabel lblPuertoServidor = new JLabel("Puerto escucha del Servidor");
		lblPuertoServidor.setBounds(33, 110, 206, 15);
		contentPane.add(lblPuertoServidor);

		JLabel lblNumeroDeObjetos = new JLabel("Numero de Objetos a Enviar");
		lblNumeroDeObjetos.setBounds(33, 145, 206, 29);
		contentPane.add(lblNumeroDeObjetos);

		txtIPServidor = new JTextField();
		txtIPServidor.setBounds(257, 65, 144, 19);
		contentPane.add(txtIPServidor);
		txtIPServidor.setColumns(10);

		txtPuertoServidor = new JTextField();
		txtPuertoServidor.setColumns(10);
		txtPuertoServidor.setBounds(257, 108, 144, 19);
		contentPane.add(txtPuertoServidor);

		txtNumObjetos = new JTextField();
		txtNumObjetos.setColumns(10);
		txtNumObjetos.setBounds(257, 150, 144, 19);
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
		btnEnviar.setBounds(173, 197, 117, 25);
		contentPane.add(btnEnviar);

		JLabel lblClienteUdp = new JLabel("Cliente UDP");
		lblClienteUdp.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClienteUdp.setBounds(162, 12, 136, 29);
		contentPane.add(lblClienteUdp);
	}
}
