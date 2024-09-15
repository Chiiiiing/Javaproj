package Cafe;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage extends JPanel {
	
    private JLabel picLabel;
    private JLabel title;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private static JFrame frame;

    public LoginPage() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        // Malamang ang pag buhat top panel halata man siguro
        JPanel topPanel = new JPanel(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        
        try {
            Image img = ImageIO.read(this.getClass().getResource("/yowbrew_t2.png"));
            Image imgScaled = img.getScaledInstance(250, 150, Image.SCALE_SMOOTH);
            ImageIcon imgIcon = new ImageIcon(imgScaled);
            picLabel = new JLabel(imgIcon);
            topPanel.add(picLabel, BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 0, 5));  //Row, Columns, Horizontal pixel gap between cells, Vertical gap between cells
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Username:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 10); // kuan ra ni design (ibabaw, wala, ubos, tuo)
        loginPanel.add(userLabel, gbc);
        
        userField = new JTextField(15);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5);
        loginPanel.add(userField, gbc);
        
        JLabel passLabel = new JLabel("Password:");
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(50, -230, 0, 0);
        loginPanel.add(passLabel, gbc);
        
        passField = new JPasswordField(15);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(50, -159, 0, 0);
        loginPanel.add(passField, gbc);
        
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        Box box = Box.createVerticalBox();
        box.add(loginPanel);
        box.add(Box.createVerticalStrut(0));
        box.add(buttonPanel);
        topPanel.add(box, BorderLayout.CENTER);
        


        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());

                if (user.equals("admin") &&  pass.equals("admin123")) {
                	
                    Menu drinksMenu;
                    drinksMenu = new Menu();
                    try {
						drinksMenu.createAndShowGUI();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    try {
						drinksMenu.setVisible(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    setVisible(false);
                    frame.dispose();
                } else {
                    
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setTitle("Yow Brew Ordering System");
        LoginPage main = new LoginPage();
        frame.getContentPane().add(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        LoginPage.frame = frame;
    }
}