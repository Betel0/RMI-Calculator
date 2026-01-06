package gui;

import remote.CalculatorInterface;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Modern Calculator GUI with RMI Client functionality
 */
public class CalculatorGUI extends JFrame {

    // ================= RMI CONFIG =================
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5004; // ✅ Match server port
    private static final String SERVICE_NAME = "CalculatorService";

    private CalculatorInterface calculator;
    private boolean connected = false;

    // ================= STATE =================
    private double currentValue = 0;
    private String currentOperation = "";
    private boolean startNewNumber = true;

    // ================= UI =================
    private JTextField display;
    private JLabel statusLabel;
    private JLabel memoryLabel;
    private JLabel operationLabel;

    private JButton[] numberButtons = new JButton[10];
    private JButton btnAdd, btnSubtract, btnMultiply, btnDivide, btnEquals;
    private JButton btnClear, btnDecimal, btnBackspace;

    // ================= COLORS =================
    private final Color BG = new Color(30, 30, 40);
    private final Color BTN = new Color(60, 60, 70);
    private final Color OP = new Color(255, 140, 0);
    private final Color EQUALS = new Color(0, 150, 255);
    private final Color TXT = Color.BLACK; // ALL button text → black

    // ================= CONSTRUCTOR =================
    public CalculatorGUI() {
        setTitle("RMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG);

        createDisplay();
        createButtons();
        createStatusBar();

        pack();
        setSize(400, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // RMI starts AFTER UI exists
        initializeRMI();
    }

    // ================= UI BUILD =================
    private void createDisplay() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        operationLabel = new JLabel("", SwingConstants.RIGHT);
        operationLabel.setForeground(Color.GRAY);

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 36));
        display.setBackground(new Color(45, 45, 55));
        display.setForeground(TXT);

        panel.add(operationLabel, BorderLayout.NORTH);
        panel.add(display, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
    }

    private void createButtons() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // Function Buttons
        btnClear = createButton("C", Color.RED);
        btnBackspace = createButton("⌫", BTN);
        btnDivide = createButton("÷", OP);
        btnMultiply = createButton("×", OP);
        btnSubtract = createButton("-", OP);
        btnAdd = createButton("+", OP);
        btnEquals = createButton("=", EQUALS);
        btnDecimal = createButton(".", BTN);

        // Number Buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton("" + i, BTN);
        }

        // Add buttons to panel
        panel.add(btnClear);
        panel.add(btnBackspace);
        panel.add(btnDivide);
        panel.add(btnMultiply);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(btnSubtract);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(btnAdd);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(btnEquals);

        panel.add(numberButtons[0]);
        panel.add(btnDecimal);

        add(panel, BorderLayout.CENTER);

        setupActions();
    }

    private void createStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(40, 40, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        statusLabel = new JLabel("🔴 Disconnected");
        statusLabel.setForeground(Color.LIGHT_GRAY);

        memoryLabel = new JLabel("Memory: 0.0");
        memoryLabel.setForeground(Color.GREEN);

        panel.add(statusLabel, BorderLayout.WEST);
        panel.add(memoryLabel, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBackground(bgColor);
        b.setForeground(TXT); // text black
        b.setFocusPainted(false);
        return b;
    }

    // ================= ACTIONS =================
    private void setupActions() {
        for (int i = 0; i < 10; i++) {
            final int n = i;
            numberButtons[i].addActionListener(e -> appendNumber(n));
        }

        btnAdd.addActionListener(e -> setOperation("+"));
        btnSubtract.addActionListener(e -> setOperation("-"));
        btnMultiply.addActionListener(e -> setOperation("×"));
        btnDivide.addActionListener(e -> setOperation("÷"));

        btnEquals.addActionListener(e -> calculate());
        btnDecimal.addActionListener(e -> addDecimal());
        btnClear.addActionListener(e -> clear());
        btnBackspace.addActionListener(e -> backspace());
    }

    // ================= RMI =================
    private void initializeRMI() {
        new Thread(() -> {
            try {
                statusLabel.setText("🟡 Connecting...");
                Registry registry = LocateRegistry.getRegistry(SERVER_HOST, SERVER_PORT);
                calculator = (CalculatorInterface) registry.lookup(SERVICE_NAME);

                calculator.add(1, 1); // test
                connected = true;

                SwingUtilities.invokeLater(() ->
                        statusLabel.setText("🟢 Connected to Server")
                );
            } catch (Exception e) {
                connected = false;
                SwingUtilities.invokeLater(() ->
                        statusLabel.setText("🔴 Connection failed")
                );
            }
        }).start();
    }

    // ================= LOGIC =================
    private void appendNumber(int n) {
        if (startNewNumber) {
            display.setText("" + n);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + n);
        }
    }

    private void addDecimal() {
        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void backspace() {
        String t = display.getText();
        display.setText(t.length() > 1 ? t.substring(0, t.length() - 1) : "0");
    }

    private void setOperation(String op) {
        currentValue = Double.parseDouble(display.getText());
        currentOperation = op;
        operationLabel.setText(currentValue + " " + op);
        startNewNumber = true;
    }

    private void calculate() {
        try {
            double b = Double.parseDouble(display.getText());
            double r = 0;

            switch (currentOperation) {
                case "+": r = calculator.add(currentValue, b); break;
                case "-": r = calculator.subtract(currentValue, b); break;
                case "×": r = calculator.multiply(currentValue, b); break;
                case "÷": r = calculator.divide(currentValue, b); break;
            }

            display.setText("" + r);
            operationLabel.setText("");
            startNewNumber = true;

        } catch (Exception e) {
            display.setText("Error");
        }
    }

    private void clear() {
        display.setText("0");
        operationLabel.setText("");
        startNewNumber = true;
    }
}
