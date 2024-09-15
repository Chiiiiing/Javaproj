package Cafe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Payment extends JFrame implements Printable {
	
    private double totalBill;
    private JTextField moneyInput;
    private JLabel errorLabel;
    private JButton confirmButton, cancelButton, viewReceiptButton;
    private JFrame receiptFrame;
    private Menu menu;
    private DefaultTableModel dtm;
    private int customerNumber;

    
    
    public Payment(double totalBill, Menu menu, DefaultTableModel dtm) {
        this.totalBill = totalBill;
        this.menu = menu;
        this.dtm = dtm;
        createGUI();
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    
    
    private void createGUI() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Total Bill: ₱ " + String.format("%.2f", totalBill)));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, -5, 0, 25));
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("Enter your money: ₱ "));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        moneyInput = new JTextField(10);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        centerPanel.add(moneyInput);
        centerPanel.add(errorLabel);
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ConfirmListener());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());

        viewReceiptButton = new JButton("View Receipt");
        viewReceiptButton.addActionListener(new ViewReceiptListener());
        viewReceiptButton.setVisible(false);

        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(viewReceiptButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    
    
    private class ConfirmListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double money = Double.parseDouble(moneyInput.getText());
                if (money < totalBill) {
                    errorLabel.setText("Error: Insufficient money");
                } else {
                    double change = money - totalBill;
                    confirmButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                    moneyInput.setEditable(false);
                    errorLabel.setText("");
                    viewReceiptButton.setVisible(true);
                    JOptionPane.showMessageDialog(Payment.this, "Payment successful! Your change is ₱" + String.format("%.2f", change));
                }
            } catch (NumberFormatException ex) {
                errorLabel.setText("Error: Invalid input");
            }
        }
    }

    
    
    private class CancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            try {
                menu.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Payment.this, "An error occurred while returning to the menu.");
            }
        }
    }

    
    
    private class ViewReceiptListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            receiptFrame = new JFrame("Receipt");
            JPanel receiptPanel = new JPanel();
            receiptPanel.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            customerNumber = (int) (Math.random() * 99999);
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.insets = new Insets(0, 10, 0, 0);
            topPanel.add(new JLabel("#" + customerNumber), gbc);

            gbc.anchor = GridBagConstraints.NORTH;
            gbc.weightx = 1;
            gbc.insets = new Insets(0, 0, 0, 10);
            JLabel label = new JLabel("<html>YOW <b>BREW </b></html>");
            Font boldFont = new Font("Arial", Font.ITALIC ,20);
            label.setFont(boldFont);
            topPanel.add(label, gbc);
            receiptPanel.add(topPanel, BorderLayout.NORTH);

            JPanel middlePanel = new JPanel();
            middlePanel.setLayout(new BorderLayout());

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new GridLayout(0, 1));

            for (int i = 0; i < dtm.getRowCount(); i++) {
                Object value0 = dtm.getValueAt(i, 0);
                Object value1 = dtm.getValueAt(i, 1);
                Object value2 = dtm.getValueAt(i, 2);

                if (value0 != null && value1 != null && value2 != null) {
                    detailsPanel.add(new JLabel(value0 + " x " + value1 + " = ₱" + value2));
                } else {
                    detailsPanel.add(new JLabel("Invalid data at row " + i));
                }
            }

            double money = Double.parseDouble(moneyInput.getText());
            detailsPanel.add(new JLabel("Total Bill: ₱" + String.format("%.2f", totalBill)));
            detailsPanel.add(new JLabel("Money: ₱" + String.format("%.2f", money)));
            double change = money - totalBill;
            detailsPanel.add(new JLabel("Change: ₱" + String.format("%.2f", change)));
            detailsPanel.setBorder(BorderFactory.createTitledBorder("Receipt Details"));
            
            middlePanel.add(detailsPanel, BorderLayout.CENTER);
            receiptPanel.add(middlePanel, BorderLayout.CENTER);
            
            JPanel bottomPanel = new JPanel();
            JButton returnButton = new JButton("Return To Menu");
            returnButton.addActionListener(new ReturnListener());
            JButton printButton = new JButton("Print Receipt");
            printButton.addActionListener(new PrintReceiptListener());

            bottomPanel.add(returnButton);
            bottomPanel.add(printButton);
            receiptPanel.add(bottomPanel, BorderLayout.SOUTH);

            receiptFrame.add(receiptPanel);
            receiptFrame.setSize(400, 300);
            receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            receiptFrame.setLocationRelativeTo(null);
            receiptFrame.setVisible(true);
        }
    }


    
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        int y = 100;
        g.drawString("#" + customerNumber, 65, y);
        g.drawString("YOW BREW", 150, y);
        y += 20;
        g.drawString("==========================================", 50, y);
        y += 20;
        
        for (int i = 0; i < dtm.getRowCount(); i++) {
            Object value0 = dtm.getValueAt(i, 0);
            Object value1 = dtm.getValueAt(i, 1);
            Object value2 = dtm.getValueAt(i, 2);

            if (value0 != null && value1 != null && value2 != null) {
                g.drawString(value0 + " x " + value1 + " = ₱" + value2, 100, y);
                y += 20;
            } else {
                g.drawString("Invalid data at row " + i, 100, y);
                y += 20;
            }
        }
        g.drawString("----------------------------------------------------------------------", 50, y);
        y += 20;
        g.drawString("Total Bill: ₱" + String.format("%.2f", totalBill), 100, y);
        y += 20;
        g.drawString("----------------------------------------------------------------------", 50, y);
        y += 20;
        double money = Double.parseDouble(moneyInput.getText());
        g.drawString("Money: ₱" + String.format("%.2f", money), 100, y);
        y += 20;
        g.drawString("----------------------------------------------------------------------", 50, y);
        y += 20;
        double change = money - totalBill;
        g.drawString("Change: ₱" + String.format("%.2f", change), 100, y);

        return PAGE_EXISTS;
    }

    
    
    private class PrintReceiptListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(Payment.this);

            boolean doPrint = job.printDialog();
            if (doPrint) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    System.err.println("Print error: " + ex.getMessage());
                }
            }
        }
    }

    
    
    private class ReturnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            receiptFrame.dispose();
            dispose();
            try {
                menu.setVisible(true); // Show the original Menu instance
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle unexpected exceptions
                JOptionPane.showMessageDialog(Payment.this, "An error occurred while returning to the menu.");
            }
        }
    }

    
    
    public static void main(String[] args) {
        Menu menu = new Menu();
        DefaultTableModel dtm = new DefaultTableModel();
    }
}
