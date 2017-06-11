import java.awt.BorderLayout;
import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import java.awt.ScrollPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class SchichtplanSL extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox comboBoxFunction;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchichtplanSL frame = new SchichtplanSL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection=null;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtFunction;
	private JTextField txtMID;
	private JTable tableSchicht;
	
	public void refreshTable() {
		try{
			String query = "select MID, Name, Surname, Function from MitarbeiterInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet result = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(result));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox() {
		try{
			String query="select * from MitarbeiterInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet result = pst.executeQuery();
			
			while(result.next()) {
				comboBoxFunction.addItem(result.getString("Function"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	
	public SchichtplanSL() {
		connection=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 492);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnFile.add(mnNew);
		
		JMenuItem mntmEmployee = new JMenuItem("Employee");
		mnNew.add(mntmEmployee);
		
		JMenuItem mntmPlan = new JMenuItem("Plan");
		mnNew.add(mntmPlan);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenuItem mntmCloseAll = new JMenuItem("Close All");
		mnFile.add(mntmCloseAll);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnSource = new JMenu("Source");
		menuBar.add(mnSource);
		
		JMenu mnSearch = new JMenu("Search");
		menuBar.add(mnSearch);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "select MID, Name, Surname, Function from MitarbeiterInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet result = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(result));
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLoad.setBounds(413, 398, 89, 23);
		contentPane.add(btnLoad);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "insert into MitarbeiterInfo (MID, Name, Surname, Function) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, txtMID.getText());
					pst.setString(2, txtName.getText());
					pst.setString(3, txtSurname.getText());
					pst.setString(4, txtFunction.getText());
					
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data saved");
					pst.close();
				
				}catch(Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		btnSave.setBounds(101, 398, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 304, 62, 14);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 336, 62, 14);
		contentPane.add(lblSurname);
		
		JLabel lblFunction = new JLabel("Function");
		lblFunction.setBounds(10, 373, 62, 14);
		contentPane.add(lblFunction);
		
		txtName = new JTextField();
		txtName.setBounds(101, 298, 86, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtSurname = new JTextField();
		txtSurname.setBounds(101, 333, 86, 20);
		contentPane.add(txtSurname);
		txtSurname.setColumns(10);
		
		txtFunction = new JTextField();
		txtFunction.setBounds(101, 370, 86, 20);
		contentPane.add(txtFunction);
		txtFunction.setColumns(10);
		
		JLabel lblMid = new JLabel("MID");
		lblMid.setBounds(10, 279, 46, 14);
		contentPane.add(lblMid);
		
		txtMID = new JTextField();
		txtMID.setBounds(101, 267, 86, 20);
		contentPane.add(txtMID);
		txtMID.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query = "Update MitarbeiterInfo set MID='"+txtMID.getText()+"', name='"+txtName.getText()+"', surname = '"+txtSurname.getText()+"', Function = '"+txtFunction.getText()+"'";
					PreparedStatement pst= connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data updated");
					pst.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		btnUpdate.setBounds(200, 398, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String query = "delete from MitarbeiterInfo where MID='"+txtMID.getText()+"' ";
					PreparedStatement pst= connection.prepareStatement(query);
					
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Deleted");
					pst.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				refreshTable();
			}
		});
		btnDelete.setBounds(0, 398, 89, 23);
		contentPane.add(btnDelete);
		
		comboBoxFunction = new JComboBox();
		comboBoxFunction.setBounds(318, 399, 72, 20);
		contentPane.add(comboBoxFunction);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(336, 264, 375, 108);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 36, 722, 154);
		contentPane.add(scrollPane_1);
		
		tableSchicht = new JTable();
		scrollPane_1.setViewportView(tableSchicht);
		tableSchicht.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Personal", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
			}
		));
		
		JButton btnLoad_1 = new JButton("Load");
		btnLoad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				try {
					String query = "select Surname, Name from MitarbeiterInfo";
					Statement st = (Statement) connection.createStatement();
					ResultSet result = st.executeQuery(query);
					tableSchicht.setModel(DbUtils.resultSetToTableModel(result));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLoad_1.setBounds(20, 201, 62, 23);
		contentPane.add(btnLoad_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 238, 732, 2);
		contentPane.add(separator_2);
		
		refreshTable();
		fillComboBox();
	}
}
