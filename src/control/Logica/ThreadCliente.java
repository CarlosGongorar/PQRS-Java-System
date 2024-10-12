package Control.Logica;

import java.io.DataInputStream;
import java.io.IOException;

public class ThreadCliente extends Thread
{
	DataInputStream entrada;
	ControlCliente control;
	
	public static final int BAN_ID = 2;

	public ThreadCliente(ControlCliente control, DataInputStream entrada) 
	{
		this.control = control;
		this.entrada = entrada;
	}
        
	public void run()
	{
		String menser = "";
		int opcion = 0;
		boolean banned = false;
		
		while (!banned)
		{
			try
			{
				opcion = entrada.readInt();
				switch (opcion)
				{
				case 1:
					menser = entrada.readUTF();
					control.auxiliar(menser);
					break;
				case BAN_ID:
					control.auxiliar("Los insultos y malas palabras no" +
						" serán tolerados.");
					banned = true;
					control.closeWindow();
					break;
				case 3:
					menser=entrada.readUTF();
					control.formulario.link.setText(menser);
					break;
				}
			} catch (IOException e){}
		}
	}
}