package Control.Logica;

import Control.Conexión.PropertiesNetwork;
import Control.DAO.ClienteDAO;
import Control.Conexión.Network;
import Vistas.VistaServer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ControlServer
{

	private VistaServer serverView;
	private PropertiesNetwork props;
	protected ClienteDAO db;
	public static int pQRCount = 0;

	public ControlServer() throws FileNotFoundException, IOException
	{
		serverView = new VistaServer();
		serverView.Mensajes("Por favor carge el archivo de propiedades");

		props = new PropertiesNetwork(serverView.getFile());
		db = new ClienteDAO();
		serverView.setVisible(true);
		runServer();
	}

	public void runServer()
	/*Cubre todos los protocolos para iniciar los hilos
		de ServerSockets*/
	{
		ServerSocket serv = null;
		boolean listening = true;
		try
		{
			serv = new ServerSocket(8081);
			serverView.mensajepanel(":::SERVER ACTIVO:::\n");
			while (listening)
			{
				Socket sock = null;
				try
				{
					sock = serv.accept();
					serverView.mensajepanel("Se ha recibido una solicitud de ingreso de: "
						+ sock.getPort() + '\n');
				} catch (IOException e)
				{
					serverView.mensajepanel("Accept failed " + serv + ", " + e.getMessage());
					continue;
				}
				threadServer user = new threadServer(sock, this);
				/*Lanza
					la ejecución de un nuevo hilo por cada usuario aceptado*/
				user.start();
			}
		} catch (IOException e)
		{
			//variable tipo vista.mostrar
			serverView.Mensajes("error :" + e);

		}

	}

	public void split(String word, ArrayList<String> words)
	/*Toma una cadena de
		texto y la separa en varias palabras tomando sus espacios como
		separadores entre cada una, guardandolas en un arrayList que se ingrese
		como parámetro*/
	{
		char separator = ' ';

		for (int i = 0, wordNum = 1; i < word.length(); ++i)
		{
			if (words.size() < wordNum)
			{
				words.add("");
			}

			if (word.charAt(i) != separator)
			{
				words.set(wordNum - 1,
					words.get(wordNum - 1) + word.charAt(i));
			} else
			{
				++wordNum;
			}
		}
	}

	public void mensajes(String texto) /*Interfaz pública para mostrar mensajes
		en el servidor desde otras clases en su paquete*/
	{
		serverView.Mensajes(texto);
	}

}
