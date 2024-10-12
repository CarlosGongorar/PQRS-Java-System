package Control.Logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import Vistas.VistaCliente;
import Vistas.Formulario;

public class ControlCliente implements ActionListener
{

	public String ipserver;
	DataOutputStream salida = null;
	DataInputStream entrada = null;
	FileInputStream pdf = null;
	Socket comunicacion = null;
	VistaCliente vent;
	Formulario formulario;
	String nombre;
	String codigo;
	String cedula;
	String apellido;
	String envio;
	//constructor

	public ControlCliente() throws IOException
	{
		vent = new VistaCliente();
		formulario = new Formulario();
		vent.Join.addActionListener(this);
		vent.Exit.addActionListener(this);
		vent.OldInterface.addActionListener(this);

		formulario.btnSend.addActionListener(this);
		formulario.btnExitForm.addActionListener(this);
		vent.setVisible(true);
	}

	//sockets
	public void conexion()
	/*Lanza el hilo de ejecución del socket del cliente*/
	{
		try
		{
			comunicacion = new Socket(ipserver, 8081);
			entrada = new DataInputStream(comunicacion.getInputStream());
			salida = new DataOutputStream(comunicacion.getOutputStream());
		} catch (IOException e)
		{
			vent.mensajeconsola("El servidor no está levantado");
		}
		new ThreadCliente(this, entrada).start();
	}

	public void auxiliar(String aux)
	/*Dentro del hilo del cliente verifica si
		el mensaje recibido (parámetro aux) coincide con la cadena de texto
		"Servidor: Conexión recibida de:*/
	{
		if ("Servidor: Conexion recibida de:".equals(aux))
		{
			vent.mensajepantalla(aux + vent.IPServer.getText(), "mensaje");
			vent.setVisible(false);
			formulario.setVisible(true);
		} else
		{
			vent.mensajepantalla(aux, "ERROR");
		}
	}

	public void closeWindow()
	/*Finaliza el proceso general de ControlCliente.
		Diseñado para */
	{
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	/*Implementaciones del
		comportamiento de los botones del formulario del usuario*/
	{
		if (e.getSource() == vent.Join)
		{
			/*Obtención y asignación de los datos del formulario*/
			nombre = vent.Name.getText();
			apellido = vent.LoginLastName.getText();
			codigo = vent.ID.getText();
			cedula = vent.LoginID.getText();
			envio = nombre + " " + apellido + " " + cedula + " " + codigo;
			ipserver = vent.IPServer.getText();
			/**
			 * *************************************************
			 */

			conexion();

			try
			{
				salida.writeInt(1); /*Para cualquier envío de datos, ya sea
				servidor-cliente o cliente-servidor, primeramente se hace un 
				envío de número entero que le indica al receptor el tipo de dato
				que se ha de enviar.*/
				
				salida.writeUTF(envio);
			} catch (IOException ex)
			{
				vent.mensajeconsola("error...." + ex);
			}
		}
		//***********************
		if (e.getSource() == formulario.btnSend) /*Envío del tipo y descripción
			de pqr*/
		{
			try
			{
				salida.writeInt(3);
				salida.writeUTF(formulario.Description.getText());
				salida.writeInt(4);
				salida.writeUTF(formulario.PQRSolicitude.getSelectedItem().toString());
			} catch (IOException ex)
			{
				vent.mensajeconsola("error...." + ex);
			}
		}

		if (e.getSource() == formulario.btnExitForm) /*Finaliza la ejecución*/
		{
			System.exit(0);
		}

		if (e.getSource() == vent.OldInterface) // ;)
		{
			vent.img.setIcon(new javax.swing.ImageIcon(getClass().getResource("img_pqrsmenu.png")));
		} else if (e.getSource() == vent.Exit)
		{
			System.exit(0); //REVISAR si se cierran ventanas hijas.
		}
	}
}
