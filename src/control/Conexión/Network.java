package Control.Conexión;
import Control.Conexión.PropertiesNetwork;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Network{
    private static PropertiesNetwork auyama;
    private static Connection cn = null;
    private static Driver driver = new org.apache.derby.jdbc.ClientDriver();
    private static String URLBD = "jdbc:derby://localhost:1527/PQRS;create=true";
    private static String usuario = auyama.setUser();
    private static String contrasena = auyama.setPassword();
    
    public static Connection getConexion() throws SQLException{
		DriverManager.registerDriver(driver);
		cn = DriverManager.getConnection(URLBD, usuario, contrasena);
		
        return cn;
    }
	
    public static void desconectar() {
        cn = null;
    }        
}