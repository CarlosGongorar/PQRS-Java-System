package Control.DAO;

import Control.Conexión.Network;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO
{

	private Connection con;
	private Statement st;
	private ResultSet rs;

	public ClienteDAO()
	{
		con = null;
		st = null;
		rs = null;
	}

	public String findName(String name, String lastname, String id,
		String codigo) throws SQLException /*obtiene el nombre de la base de
			datos*/
	{
		String consulta = "SELECT * FROM PQRS.Estudiantes where nombre='"
			+ name + "' and apellido='" + lastname + "'and cedula='" + id
			+ "'and codigo='" + codigo + "'"; /*Encapsula la instrucción sql en
			un string para optimizar legibilidad*/
		con = (Connection) Network.getConexion();
		st = con.createStatement();
		rs = st.executeQuery(consulta);
		if (rs.next())
		{
			return rs.getString("nombre");
		}
		st.close();
		Network.getConexion();

		return "";
	}

	public void addPQRS(String pqrs, String descripcion) /*Toma un tipo de pqr y
		su descripción para añadirlas a la base de datos*/
	{
		try
		{
			con = Network.getConexion();
			st = con.createStatement();
			String insercion = "INSERT INTO PQRS.PQRS VALUES('" + pqrs + "','" 
				+ descripcion + "')";
			st.executeUpdate(insercion);
			st.close();
			Network.desconectar();
		} catch (SQLException ex)
		{

		}
	}
}
