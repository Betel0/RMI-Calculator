package client;

import gui.CalculatorGUI;

import javax.swing.*;

/**
 * Calculator RMI Client
 */
public class CalculatorClient {

    public static void main(String[] args) {

        printBanner();

        // Start GUI on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                CalculatorGUI gui = new CalculatorGUI();
                gui.setVisible(true);
            } catch (Exception e) {
                System.err.println("❌ Failed to start client");
                e.printStackTrace();
            }
        });
    }

    private static void printBanner() {
        System.out.println("========================================");
        System.out.println("      RMI CALCULATOR CLIENT");
        System.out.println("========================================");
        System.out.println("Java Version : " + System.getProperty("java.version"));
        System.out.println("Java Home    : " + System.getProperty("java.home"));
        System.out.println("OS           : " + System.getProperty("os.name"));
        System.out.println("User         : " + System.getProperty("user.name"));
        System.out.println("========================================\n");
    }
}
