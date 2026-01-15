import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ATMSystem extends JFrame {

    // Demo user credentials
    private final String USER_ID = "12345";
    private final String USER_PIN = "6789";

    private double balance = 10000.0;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    // Login components
    private JTextField userIdField;
    private JPasswordField pinField;

    public ATMSystem() {
        showLoginScreen();
    }

    // ---------------- LOGIN SCREEN ----------------
    private void showLoginScreen() {
        setTitle("ATM Login");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        panel.add(userIdField);

        panel.add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        panel.add(pinField);

        JButton loginBtn = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> authenticate());

        add(panel);
        setVisible(true);
    }

    private void authenticate() {
        String uid = userIdField.getText();
        String pin = new String(pinField.getPassword());

        if (uid.equals(USER_ID) && pin.equals(USER_PIN)) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            showMenuScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid User ID or PIN");
        }
    }

    // ---------------- MAIN MENU ----------------
    private void showMenuScreen() {
        getContentPane().removeAll();
        setTitle("ATM Menu");
        setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton historyBtn = new JButton("Transaction History");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton depositBtn = new JButton("Deposit");
        JButton transferBtn = new JButton("Transfer");
        JButton quitBtn = new JButton("Quit");

        historyBtn.addActionListener(e -> showTransactionHistory());
        withdrawBtn.addActionListener(e -> withdrawMoney());
        depositBtn.addActionListener(e -> depositMoney());
        transferBtn.addActionListener(e -> transferMoney());
        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(historyBtn);
        panel.add(withdrawBtn);
        panel.add(depositBtn);
        panel.add(transferBtn);
        panel.add(quitBtn);

        add(panel);
        revalidate();
        repaint();
    }

    // ---------------- FUNCTIONS ----------------
    private void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions yet.");
            return;
        }

        StringBuilder history = new StringBuilder();
        for (String t : transactionHistory) {
            history.append(t).append("\n");
        }

        JTextArea area = new JTextArea(history.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Transaction History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void withdrawMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");

        if (input == null) return;

        double amount = Double.parseDouble(input);
        if (amount <= 0 || amount > balance) {
            JOptionPane.showMessageDialog(this, "Invalid or insufficient balance.");
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: ₹" + amount);
            JOptionPane.showMessageDialog(this, "Withdraw Successful\nBalance: ₹" + balance);
        }
    }

    private void depositMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");

        if (input == null) return;

        double amount = Double.parseDouble(input);
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        } else {
            balance += amount;
            transactionHistory.add("Deposited: ₹" + amount);
            JOptionPane.showMessageDialog(this, "Deposit Successful\nBalance: ₹" + balance);
        }
    }

    private void transferMoney() {
        String acc = JOptionPane.showInputDialog(this, "Enter receiver account number:");
        if (acc == null) return;

        String input = JOptionPane.showInputDialog(this, "Enter amount to transfer:");
        if (input == null) return;

        double amount = Double.parseDouble(input);
        if (amount <= 0 || amount > balance) {
            JOptionPane.showMessageDialog(this, "Invalid or insufficient balance.");
        } else {
            balance -= amount;
            transactionHistory.add("Transferred ₹" + amount + " to A/C " + acc);
            JOptionPane.showMessageDialog(this, "Transfer Successful\nBalance: ₹" + balance);
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMSystem::new);
    }
}
