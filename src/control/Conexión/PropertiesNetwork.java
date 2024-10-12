package Control.Conexión;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesNetwork {
    InputStream file = null;
    Properties props = null;  
    private static String User = null;
    private static String Password = null;
    private File fl;
    
    public PropertiesNetwork(File arch) throws FileNotFoundException, IOException{
        fl = arch;
        this.file = new FileInputStream(fl);
        this.props = new Properties();       
        props.load(file);               
        User = props.getProperty("user");
        Password = props.getProperty("password");    
    }
    
    public static String setUser(){
        return User;
    }
    
    public static String setPassword(){
        return Password;
    }
}
