package Control.Logica;

import Vistas.PQR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class threadServer extends Thread implements ActionListener
{
	Socket scli = null;
	DataInputStream entrada;
	DataOutputStream salida;
	ControlServer control;
	PQR pqr;
	File pdf;
	private boolean banned = false;

	public threadServer(Socket scli, ControlServer control)
	{
		this.scli = scli;
		this.control = control;
		this.pqr = new PQR();
	}

	public void actionPerformed(ActionEvent e) /*Definición de las
		implementaciones de cada botón en la interfaz del pqr*/
	{
		if (e.getSource() == pqr.sendPDF) /*Implementación del proceso de envío
			de pdf*/
		{
                    String x = pqr.inputdialog("Escriba la direccion del link");
                    try {
                        salida.writeInt(3);
                        salida.writeUTF(x);
                    } catch (IOException ex) {
                        Logger.getLogger(threadServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    banned = true; // SOLO CUANDO SE DEFINA LA IMPLEMENTACIÓN

		} else if (e.getSource() == pqr.reportUser) /*Implementación del proceso
			de reportar usuarios*/
		{	
			try
			{
				salida.writeInt(ThreadCliente.BAN_ID);
			} catch (IOException ex)
			{
				Logger.getLogger(threadServer.class.getName()).log(Level.SEVERE, null, ex);
			}
			banned = true;
			//TODO definir implementación para reporte de usuario
		}
	}

	/**
	 *
	 * @throws SQLException
	 */
	public void run()
	{
		try
		{
			entrada = new DataInputStream(scli.getInputStream());
			salida = new DataOutputStream(scli.getOutputStream());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		int opcion = 0;
		String mensaje = "";
		
		String pqrText = "";
		String pqrType = "";
		//Guarda el tipo y la descripción del pqr para envío.

		while (!banned)
		{
			ArrayList<String> palabras = new ArrayList<String>(); // para
			//guardar por separado los datos de ingreso del cliente.

			try
			{
				opcion = entrada.readInt();
				switch (opcion)
				{
				case 1: /*recibe y procesa los datos del usuario para determinar
					si se encuentra en la base de datos, y por lo tanto, si se
					realiza la conexión*/
					mensaje = entrada.readUTF();
					control.split(mensaje, palabras); //separa el string
					String condition = control.db.findName(palabras.get(0),
						palabras.get(1), palabras.get(2),
						palabras.get(3));

					if (condition != "")
					{
						pqr.setVisible(true);
						pqr.reportUser.addActionListener(this);
						pqr.sendPDF.addActionListener(this);
						//****************
						++ControlServer.pQRCount;
						salida.writeInt(1);
						salida.writeUTF("Servidor: Conexion recibida de:");
						control.mensajes("Servidor>>Conexion recibida de:" + this);
					} else
					{
						salida.writeInt(1);
						salida.writeUTF("El cliente no esta registrado");
						control.mensajes("Servidor >> Cliente no existe");
					}

					break;
				case 2: //imprime directamente en el panel sin guardar
					mensaje = entrada.readUTF();
					pqr.mensajepanel(mensaje);
					break;
				case 3://recibe el pqr
					mensaje = entrada.readUTF();
					pqrText = mensaje;
					pqr.mensajepanel(mensaje);
					//metodo que lo almacene en variables
					break;
				case 4://recibe el tipo de pqr
					mensaje = entrada.readUTF();
					pqrType = mensaje;
					pqr.pqrType.setText(mensaje);
					control.db.addPQRS(pqrType, pqrText);
					break;
				}
			} catch (IOException e)
			{
				break;
			} catch (SQLException ex)
			{
				control.mensajes("Desde" + scli.getPort() + ": No se pudo " + 
					"acceder a la base de datos.");
			}
		}

		try
		{
			scli.close();
			pqr.Mensajes("El server se desconectó correctamente");
		} catch (IOException ex)
		{
			pqr.Mensajes("por qué?");
		}
		
		pqr.setVisible(false);
	}
}