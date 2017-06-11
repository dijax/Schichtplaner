//wir erstellen die Klasse sqliteConnection um eine Verbindung zum DatenBank zu erstellen
import java.sql.*;  //wir importieren alle Klassen, die mit sql verbunden sind 
import javax.swing.*;  // und natürlich die Klassen javax.swing
public class sqliteConnection {

	// wir initialisieren eine globae Variale conn
	Connection conn=null;
	// Klassenmethode dbConnector()
	public static Connection dbConnector() { 
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Chouchou\\Desktop\\Study\\Semeste 2\\Programmieren 2\\SEG\\MitarbeiterData.sqlite");
			//JOptionPane.showMessageDialog(null, "Connection successfull!");
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return  null;
		}
	}
	
}
