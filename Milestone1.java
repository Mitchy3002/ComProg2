package MS1;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Milestone1 {

	  public static void main(String[] args) {
	    // Instantiate the login screen
	    LoginScreen loginScreen = new LoginScreen();

	    // Display the login screen
	    loginScreen.setVisible(true);
	  }
	}

	class LoginScreen extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Attributes
    private JTextField employeeIDField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JCheckBox keepSignedInCheckBox;
    private static final String CREDENTIALS_FILE = "C:\\Users\\Dennis\\eclipse-workspace\\MS1\\src\\MS1\\credentials.csv";
    
    // Constructor
    public LoginScreen() {
        // Set window title
        super("MotorPH Login");
        
        // Create components
        JLabel employeeIDLabel = new JLabel("Employee ID:");
        JLabel passwordLabel = new JLabel("Password:");
        employeeIDField = new JTextField(30); // Increased column width
        passwordField = new JPasswordField(30); // Increased column width
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        keepSignedInCheckBox = new JCheckBox("Keep me signed in");
        
        // Add action listeners
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        // Create panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(employeeIDLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(employeeIDField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        
        gbc.gridy = 3;
        panel.add(exitButton, gbc);
        
        gbc.gridy = 4;
        panel.add(keepSignedInCheckBox, gbc);
        
        // Add panel to frame
        add(panel, BorderLayout.CENTER);
        
        // Set frame properties
        setSize(500, 300); // Increased window size
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Action performed when login or exit button is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Get employee ID and password
            String employeeID = employeeIDField.getText();
            String password = new String(passwordField.getPassword());
            
            // Check credentials
            try {
				if (isValidUser(employeeID, password)) {
				    JOptionPane.showMessageDialog(this, "Login successful. Welcome, " + employeeID + "!");
				    dispose(); // Close the login window
				    
				    // Open the payroll system window
				    PayrollSystem payrollSystem = new PayrollSystem(employeeID);
				    payrollSystem.setVisible(true);
				} else {
				    JOptionPane.showMessageDialog(this, "Invalid employee ID or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
				}
			} catch (CsvValidationException | HeadlessException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else if (e.getSource() == exitButton) {
            // Show exit confirmation dialog
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            
            // Check user's choice
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
    
    // Method to validate user credentials 
    private boolean isValidUser(String employeeID, String password) throws IOException, CsvValidationException {
        try (FileReader reader = new FileReader(CREDENTIALS_FILE)) {
          CSVReader csvReader = new CSVReader(reader);
          String[] nextLine;
          while ((nextLine = csvReader.readNext()) != null) {
            if (nextLine[0].equals(employeeID) && nextLine[1].equals(password)) {
              return true;
            }
          }
        }
        return false;
    }

    class PayrollSystem extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Attributes
    private JButton logoutButton;
    private JTextArea employeeDetailsTextArea;
    private JTextArea payslipTextArea;
    private JButton searchButton;
    private JTextField searchField;
    private static final String EMPLOYEE_DATA_FILE = "C:\\Users\\Dennis\\eclipse-workspace\\MS1\\src\\MS1\\employee_data.csv";
    
 // Constructor
    public PayrollSystem(String employeeID) {
        // Set window title
        super("MotorPH Payroll System");
        
     // Initialize components
        JLabel employeeDetailsLabel = new JLabel("Employee Details:");
        employeeDetailsTextArea = new JTextArea(10, 40); // Increased width
        employeeDetailsTextArea.setEditable(false);
        payslipTextArea = new JTextArea(10, 40); // Increased width
        payslipTextArea.setEditable(false);
        JScrollPane employeeScrollPane = new JScrollPane(employeeDetailsTextArea);
        JScrollPane payslipScrollPane = new JScrollPane(payslipTextArea);
        searchField = new JTextField(20); // Adjusted width
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(this);
        
        // Set preferred sizes
        searchField.setPreferredSize(new Dimension(200, searchField.getPreferredSize().height));
        employeeScrollPane.setPreferredSize(new Dimension(400, 150));
        payslipScrollPane.setPreferredSize(new Dimension(400, 150));

        // Create panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Enter Employee ID:"), gbc);

        gbc.gridx = 1;
        panel.add(searchField, gbc);

        gbc.gridx = 2;
        panel.add(searchButton, gbc);
        
        gbc.gridx = 3;
        panel.add(logoutButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        panel.add(employeeDetailsLabel, gbc);

        gbc.gridy = 2;
        panel.add(employeeScrollPane, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Payslip View:"), gbc);

        gbc.gridy = 4;
        panel.add(payslipScrollPane, gbc);

        // Add panel to frame
        add(panel);

        // Set frame properties
        setSize(800, 600); // Adjusted window size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == searchButton) {
        String employeeID = searchField.getText();
        String employeeDetailsText = null;
        String payslipText = null;

        try (FileReader reader = new FileReader(EMPLOYEE_DATA_FILE);
             CSVReader csvReader = new CSVReader(reader)) {
          String[] nextLine;
          while ((nextLine = csvReader.readNext()) != null) {
            if (nextLine[0].equals(employeeID)) {
              // Assuming employee details are in columns 1 to 4 and payslip details are from 5 onwards
              employeeDetailsText = String.join("\n", Arrays.copyOfRange(nextLine, 1, 5));
              payslipText = String.join("\n", Arrays.copyOfRange(nextLine, 5, nextLine.length));
              break;
            }
          }
        } catch (IOException e1) {
          // Handle file reading errors
          e1.printStackTrace();
        } catch (CsvValidationException e1) {
          // Handle CSV parsing errors (e.g., invalid format)
          System.err.println("Error reading CSV file: " + e1.getMessage());
        }

        // Set the text for employee details and payslip text areas based on retrieved data (or handle cases where no data is found)
        employeeDetailsTextArea.setText(employeeDetailsText != null ? employeeDetailsText : "Employee not found");
        payslipTextArea.setText(payslipText != null ? payslipText : "Payslip not found");
      }
      else if (e.getSource() == logoutButton) {
    	  // Show exit confirmation dialog
          int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
          
          // Check user's choice
          if (result == JOptionPane.YES_OPTION) {
              dispose();
              LoginScreen loginScreen = new LoginScreen();
              loginScreen.setVisible(true);
          }
      }
    }
  }
}
	