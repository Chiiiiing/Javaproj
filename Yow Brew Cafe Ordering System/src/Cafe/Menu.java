package Cafe;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class Menu {
	static private JFrame frame;
	static private JButton backButton, payButton, clearButton;
	static private JTextField textField;
	static private GridBagConstraints gbc;
	private JTable table;
	
	DefaultTableModel dtm;
	Double[] priceHot;
	Double[] priceCold;
	Double[] pricePastries;
	
	double totalPrice = 0;
	double h1, h2, h3, h4;
	double c1, c2, c3, c4;
	double pa1, pa2, pa3, pa4;

	private JSpinner[] numSpinner;
	static private JLabel[] hotLabel;
	static private JLabel[] hotImage;
	private String[] filehot;

	private JSpinner[] numSpinnercold;
	static private JLabel[] coldLabel;
	static private JLabel[] coldImage;
	private String[] filecold;

	private JSpinner[] numSpinnerpastries;
	static private JLabel[] pastriesLabel;
	static private JLabel[] pastriesImage;
	private String[] filepastries;

	private static final int ELEMENTS = 4;
	private static final int COLD_ELEMENTS = 4;
	private static final int PASTRIES_ELEMENTS = 4;

	double total = 0;
	double hot1, hot2, hot3, hot4;
	double cold1, cold2, cold3, cold4;
	double pr, pr1, pr2, pr3;

	double totalForhots;
	double totalForcold;
	double totalForpastries;
	
	
	void createAndShowGUI() throws IOException {
		
		frame = new JFrame("Order Menu ");
		frame.setBounds(100, 100, 750, 550);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		JLabel lblhotOrdered = new JLabel("Ordered Item");
		lblhotOrdered.setBounds(555, 11, 81, 14);

		frame.getContentPane().add(lblhotOrdered);

		table = new JTable();
		backButton = new JButton();
		payButton = new JButton();
		dtm = new DefaultTableModel(0, 0);
		final String header[] = new String[] { "Item", "Qty", "Price", "Spinner" };
		dtm.setColumnIdentifiers(header);
		dtm.addRow(header);
		table = new JTable();
		table.setModel(dtm);
		table.setBounds(475, 31, 1, 1); // int x, int y, int width, int height
		table.setSize(245, 300); // width,height
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setMinWidth(0); // hide spinner
															// column
		table.getColumnModel().getColumn(3).setMaxWidth(0); // hide spinner
															// column
		table.setShowGrid(false); // this will remove the cell boarder
		frame.getContentPane().add(table);
		JLabel lblTotal = new JLabel("Total  : ");
		lblTotal.setBounds(515, 345, 46, 14);
		frame.getContentPane().add(lblTotal);
		textField = new JTextField();
		textField.setBounds(585, 345, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		payButton = new JButton("Pay");
		payButton.setBounds(500, 375, 89, 23);
		frame.getContentPane().add(payButton);
		backButton = new JButton("Back");
		backButton.setBounds(610, 375, 89, 23);
		frame.getContentPane().add(backButton);
		clearButton = new JButton("Clear");
        clearButton.setBounds(550, 410, 89, 23);
        frame.getContentPane().add(clearButton);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		addIt(tabbedPane, "Hot");
		addIt1(tabbedPane, "Cold");
		addIt2(tabbedPane, "Pastries");
		tabbedPane.setBounds(18, 11, 450, 450);
		frame.getContentPane().add(tabbedPane);
		frame.setVisible(true);
		
		
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage menu = new LoginPage();
				menu.main(header);
				menu.setVisible(true);
				frame.dispose();
			}
		});

		
		
		payButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (textField.getText().equals("")) {
		            JOptionPane.showMessageDialog(null, "You did not order anything yet.", "Order Error", JOptionPane.ERROR_MESSAGE);
		        } else {
		            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		            Payment order = new Payment(total, Menu.this, dtm); // Ipasa anf total amount, Menu instance, and table model sa Payment class
		            frame.dispose();
		        }
		    }
		});
		
		
		
		clearButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		        int rowCount = dtm.getRowCount();
		        totalForhots = 0;
		        totalForcold = 0;
		        totalForpastries = 0;
		        total = 0;
		        textField.setText("0.00");
		        
		        for (int i = rowCount - 1; i > 0; i--) {
		            dtm.removeRow(i);
		        }
		        for (int i = 0; i < ELEMENTS; i++) {
		            numSpinner[i].setValue(0);
		        }
		        for (int i = 0; i < COLD_ELEMENTS; i++) {
		            numSpinnercold[i].setValue(0);
		        }
		        for (int i = 0; i < PASTRIES_ELEMENTS; i++) {
		            numSpinnerpastries[i].setValue(0);
		        }
		    }
		});
	}
	
	
	
	void addIt(JTabbedPane tabbedPane, String text) throws IOException {
		
		JPanel panel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints(); // getting constraints for the specified
										// component
		gbc.insets = new Insets(5, 0, 0, 40); // This is the margins top, left, buttom, right
		hotImage = new JLabel[ELEMENTS];
		hotLabel = new JLabel[ELEMENTS];
		numSpinner = new JSpinner[ELEMENTS];
		filehot = new String[ELEMENTS];
		priceHot = new Double[ELEMENTS];

		filehot[0] = new String("/espresso.png");
		filehot[1] = new String("/americano.png");
		filehot[2] = new String("/cappuccino.png");
		filehot[3] = new String("/matchaLatte.png");
		
		hotLabel[0] = new JLabel("Espresso");
		hotLabel[1] = new JLabel("Americano");
		hotLabel[2] = new JLabel("Cappuccino");
		hotLabel[3] = new JLabel("Matcha Latte");
		
		priceHot[0] = 45.00;
		priceHot[1] = 45.00;
		priceHot[2] = 55.00;
		priceHot[3] = 55.00;
		
		for (int i = 0; i < ELEMENTS; i++){
			
			System.out.print(filehot[i]);	
			
			try{
				
			Image image = ImageIO.read(this.getClass().getResource(filehot[i]));
			Image imageScaled = image.getScaledInstance(90, 95, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(imageScaled);
			SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 50, 1); // value,minimum,maximum,stepSize
			numSpinner[i] = new JSpinner(spnummodel);
			numSpinner[i].addChangeListener(listener);
			hotImage[i] = new JLabel(imageIcon);
			
			}
			catch(Exception e) {
				System.out.print(e);
			}
		}
		
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		
		for (int i = 0; i < ELEMENTS; i++) {
			panel.add(hotImage[i], gbc);
			gbc.gridx++; 
			panel.add(hotLabel[i], gbc);
			gbc.gridx++; 
			panel.add(numSpinner[i], gbc);
			gbc.gridx -= 2; 
			gbc.gridy++; 
			
			if ((i + 1) % 2 == 0) {
				gbc.gridx = 0;
				gbc.gridy++;
			}
		}
		tabbedPane.addTab(text, panel);
	}

	void addIt2(JTabbedPane tabbedPane, String text) throws IOException {
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 0, 0, 40); 
		
		pastriesImage = new JLabel[PASTRIES_ELEMENTS];
		pastriesLabel = new JLabel[PASTRIES_ELEMENTS];
		numSpinnerpastries = new JSpinner[PASTRIES_ELEMENTS];
		pricePastries = new Double[PASTRIES_ELEMENTS];

		filepastries = new String[PASTRIES_ELEMENTS];
		filepastries[0] = new String("/cheesecake.png");
		filepastries[1] = new String("/brownie.png");
		filepastries[2] = new String("/cinnamon.png");
		filepastries[3] = new String("/cookies.png");

		pastriesLabel[0] = new JLabel("Cheesecake");
		pastriesLabel[1] = new JLabel("Chocolate Brownie");
		pastriesLabel[2] = new JLabel("Cinnamon Roll");
		pastriesLabel[3] = new JLabel("Cookies");

		pricePastries[0] = 45.00;
		pricePastries[1] = 40.00;
		pricePastries[2] = 30.00;
		pricePastries[3] = 35.00;

		for (int i = 0; i < PASTRIES_ELEMENTS; i++) {
			
			Image image = ImageIO.read(this.getClass().getResource(filepastries[i]));
			Image imageScaled = image.getScaledInstance(90, 95, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(imageScaled);
			SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 50, 1); // value,minimum,maximum,stepSize
			numSpinnerpastries[i] = new JSpinner(spnummodel);
			numSpinnerpastries[i].addChangeListener(listenerForpastriess);
			pastriesImage[i] = new JLabel(imageIcon);
			
		}
				   
		gbc.gridx = 0; 
		gbc.gridy = 0; 
		
		for (int i = 0; i < PASTRIES_ELEMENTS; i++) {
			panel.add(pastriesImage[i], gbc);
			gbc.gridx++; 
			panel.add(pastriesLabel[i], gbc);
			gbc.gridx++; 
			panel.add(numSpinnerpastries[i], gbc);
			gbc.gridx -= 2; 
			gbc.gridy++; 
			
			if ((i + 1) % 2 == 0) {
				gbc.gridx = 0;
				gbc.gridy++;
			}
		}
		tabbedPane.addTab(text, panel);
	}

	void addIt1(JTabbedPane tabbedPane, String text) throws IOException {
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 0, 0, 40); 
		
		coldImage = new JLabel[COLD_ELEMENTS];
		coldLabel = new JLabel[COLD_ELEMENTS];
		numSpinnercold = new JSpinner[COLD_ELEMENTS];
		priceCold = new Double[COLD_ELEMENTS];

		filecold = new String[COLD_ELEMENTS];
		filecold[0] = new String("/Frappuccino.png");
		filecold[1] = new String("/macchiato.png");
		filecold[2] = new String("/vanilla.png");
		filecold[3] = new String("/cold_brew.png");

		coldLabel[0] = new JLabel("Frappuccino");
		coldLabel[1] = new JLabel("Iced Caramel Macchiato");
		coldLabel[2] = new JLabel("Iced Vanilla Latte");
		coldLabel[3] = new JLabel("Cold Brew Coffee");

		priceCold[0] = 55.00;
		priceCold[1] = 55.00;
		priceCold[2] = 45.00;
		priceCold[3] = 45.00;


		for (int i = 0; i < COLD_ELEMENTS; i++) {
			
			Image image = ImageIO.read(this.getClass().getResource(filecold[i]));
			Image imageScaled = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(imageScaled);
			SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 50, 1); // value,minimum,maximum,stepSize
			numSpinnercold[i] = new JSpinner(spnummodel);
			numSpinnercold[i].addChangeListener(listenerForcold);
			coldImage[i] = new JLabel(imageIcon);
		}

		gbc.gridx = 0; 
		gbc.gridy = 0; 
		
		for (int i = 0; i < COLD_ELEMENTS; i++) {
			panel.add(coldImage[i], gbc);
			gbc.gridx++; 
			panel.add(coldLabel[i], gbc);
			gbc.gridx++; 
			panel.add(numSpinnercold[i], gbc);
			gbc.gridx -= 2; 
			gbc.gridy++; 
			
			if ((i + 1) % 2 == 0) {
				gbc.gridx = 0;
				gbc.gridy++;
			}
		}
		tabbedPane.addTab(text, panel);
	}

	ChangeListener listener = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {

			final int quantity = (int) ((JSpinner) e.getSource()).getValue();
			final int rows = table.getRowCount();
			
			for (int row = 0; row < rows; row++) {
				if (dtm.getValueAt(row, 3) == e.getSource()) {
					if (dtm.getValueAt(row, 0).equals("Espresso")) {
						dtm.setValueAt(quantity, row, 1); // obj, row, column
						dtm.setValueAt(h1 * quantity, row, 2);
						hot1 = h1 * quantity;

					}
					else if (dtm.getValueAt(row, 0).equals("Americano")) {
						
						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(h2 * quantity, row, 2);
						hot2 = h2 * quantity;
					}
					else if (dtm.getValueAt(row, 0).equals("Cappuccino")) {
						
						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(h3 * quantity, row, 2);
						hot3 = h3 * quantity;
					}
					else if (dtm.getValueAt(row, 0).equals("Matcha Latte")) {
						
						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(h4 * quantity, row, 2);
						hot4 = h4 * quantity;
					}

					if (quantity == 0) {
						
						dtm.removeRow(row);
					}
					
					totalForhots = hot1 + hot2 + hot3 + hot4;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");
					return;
				}
			}

			// there was no row with this JSpinner, so we have to add it
			for (int i = 0; i < ELEMENTS; i++) {
				// looking for the "clicked" JSpinner
				if (numSpinner[i] == e.getSource()){
					
					totalPrice = priceHot[i];
					switch (hotLabel[i].getText()) {
					
					case "Espresso":
						h1 = 45.00;
						hot1 = h1;
						break;
					case "Americano":
						h2 = 45.00;
						hot2 = h2;
						break;
					case "Cappuccino":
						h3 = 55.00;
						hot3 = h3;
						break;
					case "Matcha Latte":
						h4 = 55.00;
						hot4 = h4;
						break;
					}
					totalForhots = hot1 + hot2 + hot3 + hot4;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");
					dtm.addRow(new Object[] { hotLabel[i].getText(), quantity, totalPrice, numSpinner[i] });
					return;
				}

			}
		}
	};

	ChangeListener listenerForpastriess = new ChangeListener()
	{
		public void stateChanged(ChangeEvent e) {

			final int quantity = (int) ((JSpinner) e.getSource()).getValue();
			final int rows = table.getRowCount();

			for (int row = 0; row < rows; row++){
				if (dtm.getValueAt(row, 3) == e.getSource()){
					if (dtm.getValueAt(row, 0).equals("Cheesecake")){
						dtm.setValueAt(quantity, row, 1); // obj, row,
						pr = pa1 * quantity; // column
						dtm.setValueAt(pa1 * quantity, row, 2);
					} 
					else if (dtm.getValueAt(row, 0).equals("Chocolate Brownie"))
					{
						dtm.setValueAt(quantity, row, 1); // obj, row, // column
						dtm.setValueAt(pa2 * quantity, row, 2);
						pr1 = pa2 * quantity;
					}
					else if (dtm.getValueAt(row, 0).equals("Cinnamon Roll"))
					{
						dtm.setValueAt(quantity, row, 1); // obj, row, // column
						dtm.setValueAt(pa3 * quantity, row, 2);
						pr2 = pa3 * quantity;
					}
					else if (dtm.getValueAt(row, 0).equals("Cookies"))
					{
						dtm.setValueAt(quantity, row, 1); // obj, row, // column
						dtm.setValueAt(pa4 * quantity, row, 2);
						pr3 = pa4 * quantity;
					}
					if (quantity == 0)
					{
						dtm.removeRow(row);
					}
					totalForpastries = pr + pr1 +pr2 +pr3;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");
					return;
				}
			}

			// there was no row with this JSpinner, so we have to add it
			for (int i = 0; i < PASTRIES_ELEMENTS; i++)
			{
				// looking for the "clicked" JSpinner
				if (numSpinnerpastries[i] == e.getSource())
				{
					totalPrice = pricePastries[i];
					switch (pastriesLabel[i].getText())
					{
					case "Cheesecake":
						pa1 = 45.00;
						pr = pa1;
						break;
					case "Chocolate Brownie":
						pa2 = 40.00;
						pr1 = pa2;
						break;
					case "Cinnamon Roll":
						pa3 = 30.00;
						pr2 = pa3;
						break;
					case "Cookies":
						pa4 = 35.00;
						pr3 = pa4;
						break;
					}
					
					totalForpastries = pr + pr1 + pr2 + pr3;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");
					dtm.addRow(new Object[]
						{
						pastriesLabel[i].getText(), quantity, totalPrice, numSpinnerpastries[i]
						});
					return;
				}
			}
		}

	};

	ChangeListener listenerForcold = new ChangeListener()
	{
		public void stateChanged(ChangeEvent e)
		{

			final int quantity = (int) ((JSpinner) e.getSource()).getValue();
			final int rows = table.getRowCount();
			for (int row = 0; row < rows; row++)
			{
				if (dtm.getValueAt(row, 3) == e.getSource())
				{
					if (dtm.getValueAt(row, 0).equals("Frappuccino"))
					{
						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(c1 * quantity, row, 2);
						cold1 = c1 * quantity;

					} 
					else if (dtm.getValueAt(row, 0).equals("Iced Caramel Macchiato")) 
					{
						dtm.setValueAt(quantity, row, 1); // obj, row,
															// column
						dtm.setValueAt(c2 * quantity, row, 2);
						cold2 = c2 * quantity;

					} 
					else if (dtm.getValueAt(row, 0).equals("Iced Vanilla Latte")) 
					{

						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(c3 * quantity, row, 2);
						cold3 = c3 * quantity;

					} 
					else if (dtm.getValueAt(row, 0).equals("Cold Brew Coffee")) 
					{

						dtm.setValueAt(quantity, row, 1);
						dtm.setValueAt(c4 * quantity, row, 2);
						cold4 = c4 * quantity;

					} 
					
					if (quantity == 0) {
						dtm.removeRow(row);
					}
					totalForcold = cold1 + cold2 + cold3 + cold4;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");

					return;
				}
			}

			// there was no row with this JSpinner, so we have to add it
			for (int i = 0; i < COLD_ELEMENTS; i++) 
			{
				// looking for the "clicked" JSpinner
				if (numSpinnercold[i] == e.getSource()) 
				{
					totalPrice = priceCold[i];
					switch (coldLabel[i].getText()) 
					{
					case "Frappuccino":
						c1 = 55.00;
						cold1 = c1;
						break;
					case "Iced Caramel Macchiato":
						c2 = 55.00;
						cold2 = c2;
						break;
					case "Iced Vanilla Latte":
						c3 = 45.00;
						cold3 = c3;
						break;
					case "Cold Brew Coffee":
						c4 = 45.00;
						cold4 = c4;
						break;
					}
					
					totalForcold = cold1 + cold2 + cold3 + cold4;
					total = totalForhots + totalForcold + totalForpastries;
					textField.setText(total + "");
					dtm.addRow(new Object[]
						{
							coldLabel[i].getText(), quantity, totalPrice, numSpinnercold[i]
						});
					return;
				}

			}

		}
	};
	
	public double getTotalPrice() {
        return total;
    }

	public void setVisible(boolean b) throws IOException {
		frame.setVisible(b);
	}
	
	public void toFront() {
		// TODO Auto-generated method stub
		frame.toFront();
	}


}
