package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginDialog {

	protected JFrame frmNcLogin;
	private JTextField firstNameTxt;
	private JTextField lastNameTxt;
	private String firstName;
	private String lastName;
	
	private ChatWindow chat;
	
	public LoginDialog() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		
		frmNcLogin = new JFrame();
		frmNcLogin.setTitle(" NC Chat App");
		frmNcLogin.setResizable(false);
		frmNcLogin.setBounds(750, 350, 409, 300);
		frmNcLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNcLogin.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 403, 73);
		frmNcLogin.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUserLogin = new JLabel("User Login");
		lblUserLogin.setFont(new Font("Eras Medium ITC", Font.PLAIN, 35));
		lblUserLogin.setBounds(131, 22, 176, 51);
		panel.add(lblUserLogin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 73, 403, 198);
		frmNcLogin.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		firstNameTxt = new JTextField();
		firstNameTxt.setFont(new Font("Arial", Font.PLAIN, 16));
		firstNameTxt.setBounds(88, 11, 306, 40);
		panel_1.add(firstNameTxt);
		firstNameTxt.setColumns(10);
		
		lastNameTxt = new JTextField();
		lastNameTxt.setFont(new Font("Arial", Font.PLAIN, 16));
		lastNameTxt.setBounds(88, 76, 306, 40);
		panel_1.add(lastNameTxt);
		lastNameTxt.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean isValidName = true;

				int x;
				int y;

				for(x = 0; x < firstNameTxt.getText().length(); x++) {

					if((!Character.isLetter(firstNameTxt.getText().charAt(x)) && !(firstNameTxt.getText().charAt(x) == '-'))) {

						isValidName = false;
						x = firstNameTxt.getText().length();
						y = lastNameTxt.getText().length();

					}



				}

				for(y = 0; y < lastNameTxt.getText().length(); y++) {

					if((!Character.isLetter(lastNameTxt.getText().charAt(y)) && !(lastNameTxt.getText().charAt(y) == '-'))) {

						isValidName = false;
						x = firstNameTxt.getText().length();
						y = lastNameTxt.getText().length();

					}



				}

				if(firstNameTxt.getText().replaceAll("\\s","").isEmpty() || lastNameTxt.getText().replaceAll("\\s","").isEmpty() || !isValidName ){
				
					final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
						
					if (runnable != null) runnable.run();
					
					firstNameTxt.setText(null);
					lastNameTxt.setText(null);
					JOptionPane.showMessageDialog(frmNcLogin, "Please enter a valid name.", "Error", 0);

				
				} else {
					
					firstName = firstNameTxt.getText();
					firstName = firstName.replaceAll("\\s","");
					
					lastName = lastNameTxt.getText();
					lastName = lastName.replaceAll("\\s","");
					
					frmNcLogin.setVisible(false);
					
					chat = new ChatWindow(firstName.substring(0, 1).toUpperCase() + firstName.substring(1), 
							lastName.substring(0, 1).toUpperCase() + lastName.substring(1));

				}
				
			}
			
		});
		loginButton.setFont(new Font("Arial", Font.PLAIN, 25));
		loginButton.setBounds(134, 127, 181, 40);
		panel_1.add(loginButton);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setForeground(Color.BLACK);
		firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		firstNameLabel.setBounds(10, 11, 68, 40);
		panel_1.add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setForeground(Color.BLACK);
		lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lastNameLabel.setBounds(10, 76, 68, 40);
		panel_1.add(lastNameLabel);
		
		frmNcLogin.setVisible(true);
		
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
