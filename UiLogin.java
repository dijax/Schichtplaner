import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.sql.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class UiLogin {

	private JFrame frame;
	private JPasswordField txtPassword;
	private JTextField textFieldUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiLogin window = new UiLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	
	/**
	 * Create the application.
	 */
	public UiLogin() {
		initialize();
		//Die Methode dbConnector() aufrufen, um die Verbindung mit db zu überprüfen
		connection = sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login System");
		lblLogin.setFont(new Font("Sitka Small", Font.BOLD, 15));
		lblLogin.setBounds(176, 30, 114, 14);
		frame.getContentPane().add(lblLogin);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(175, 116, 165, 20);
		frame.getContentPane().add(txtPassword);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(176, 72, 165, 20);
		frame.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(33, 75, 62, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(33, 119, 62, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "select * from MitarbeiterInfo where username=? and password=?";
					PreparedStatement pst = connection.prepareStatement(query);
					//die Angegebenen Daten in Query kopieren
					pst.setString(1, textFieldUserName.getText());
					pst.setString(2, txtPassword.getText());
					
					ResultSet result = pst.executeQuery();
					int count = 0;
					while(result.next()) {
						count ++;
					}
				
					if (count==1)
					{
						JOptionPane.showMessageDialog(null, "Daten sind korrekt");					
						JOptionPane.showMessageDialog(null, "Login Success.");
						frame.dispose();
						SchichtplanSL schichtplan = new SchichtplanSL();
						schichtplan.setVisible(true);
					}
					
					else if(count > 1) {
						JOptionPane.showMessageDialog(null, "duplicate Daten");
					}
					else {
						JOptionPane.showMessageDialog(null, "Daten sind falsch .. Versuchen Sie nochmal! ");
					}
					
					result.close();
					pst.close();
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLogin.setBounds(300, 184, 89, 23);
		frame.getContentPane().add(btnLogin);
	}
}
