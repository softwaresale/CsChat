package ui;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatWindow {

	protected JFrame frmNcChatApp;
	private JTextField txtConnected;
	
	private String firstName;
	private String lastName;
	
	public ChatWindow(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		
		initialize();
	}
	
	public void initialize() {
		
		frmNcChatApp = new JFrame();
		frmNcChatApp.setResizable(false);
		frmNcChatApp.setTitle("NC Chat App");
		frmNcChatApp.setBounds(700, 200, 713, 735);
		frmNcChatApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNcChatApp.getContentPane().setLayout(null);
		frmNcChatApp.setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(282, 539, 310, 32);
		frmNcChatApp.getContentPane().add(scrollPane);
		
		JTextArea inputTextArea = new JTextArea();
		inputTextArea.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(inputTextArea);
		inputTextArea.setBackground(Color.WHITE);
		inputTextArea.setLineWrap(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		panel.setBounds(0, 579, 697, 115);
		frmNcChatApp.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(" NC Chat App");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(210, 22, 248, 52);
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(Color.RED);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 36));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		panel_1.setBounds(0, 0, 272, 578);
		frmNcChatApp.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtConnected = new JTextField();
		txtConnected.setEditable(false);
		txtConnected.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtConnected.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		txtConnected.setFont(new Font("Arial", Font.BOLD, 16));
		txtConnected.setText("  Connected: ");
		txtConnected.setBounds(84, 22, 99, 20);
		panel_1.add(txtConnected);
		txtConnected.setColumns(10);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 53, 252, 476);
		panel_1.add(scrollPane_3);
		
		JTextArea connectedUserArea = new JTextArea(" " + firstName + " " + lastName.charAt(0) + "\n IP: 127.0.0.1\n"); // implement IP display this area updates
		connectedUserArea.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane_3.setViewportView(connectedUserArea);
		connectedUserArea.setLineWrap(true);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(Color.BLACK);
		lblName.setFont(new Font("Arial", Font.BOLD, 16));
		lblName.setBounds(10, 540, 61, 27);
		panel_1.add(lblName);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(65, 540, 197, 27);
		panel_1.add(scrollPane_2);
		
		JTextArea nameTextArea = new JTextArea(firstName + " " + lastName.charAt(0)); // this area does not update between users
		nameTextArea.setEditable(false);
		nameTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
		nameTextArea.setLineWrap(true);
		scrollPane_2.setViewportView(nameTextArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		panel_2.setBounds(273, 0, 424, 578);
		frmNcChatApp.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 51, 404, 480);
		panel_2.add(scrollPane_1);
		
		JTextArea displayTextArea = new JTextArea();
		displayTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
		displayTextArea.setLineWrap(true);
		scrollPane_1.setViewportView(displayTextArea);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean firstMessageIsSent;
				
				if(displayTextArea.getText().isEmpty()) { // checks if first message has been sent. I did this in order to fix how the messages look when sent.
					
					firstMessageIsSent = false;
					
				} else {
					
					firstMessageIsSent = true;
					
				}
				
				if(firstMessageIsSent) {
					
					
					if(inputTextArea.getText().replaceAll("\\s","").isEmpty()) { 
						
						inputTextArea.setText(null); // can't send empty messages 
						
					} else {
					
					displayTextArea.setText(displayTextArea.getText() + "\n" + nameTextArea.getText() + ": " + inputTextArea.getText());
					
					inputTextArea.setText(null); }
				
				} else {
					
					if(inputTextArea.getText().replaceAll("\\s","").isEmpty()) { 
						
						inputTextArea.setText(null); // can't send empty messages 
						
					} else {
					
					displayTextArea.setText(nameTextArea.getText() + ": " + inputTextArea.getText());
					
					inputTextArea.setText(null); }
					
					
				}
				
				// the displayTextArea updates with other users except when cleared.
				
			}
		});
		sendButton.setBounds(325, 539, 89, 32);
		panel_2.add(sendButton);
		sendButton.setBackground(UIManager.getColor("Button.background"));
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				displayTextArea.setText(null);			// just clears the screen for client-side only
				
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clearButton.setBounds(170, 17, 89, 23);
		panel_2.add(clearButton);
		
	}
}
