package server;

import remote.CalculatorImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetAddress;

/**
 * RMI Calculator Server
 */
public class CalculatorServer {

    private static final int RMI_PORT = 5004;
    private static final String SERVICE_NAME = "CalculatorService";

    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("   RMI CALCULATOR SERVER STARTING");
            System.out.println("========================================");

            // Display server info
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("📡 Host Address : " + hostAddress);
            System.out.println("🔢 RMI Port     : " + RMI_PORT);

            // Create RMI Registry
            System.out.println("🌐 Creating RMI Registry...");
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);

            // Create Calculator Service
            System.out.println("🧮 Creating Calculator Service...");
            CalculatorImpl calculator = new CalculatorImpl();

            // Bind service
            registry.rebind(SERVICE_NAME, calculator);

            System.out.println("\n✅ SERVER STARTED SUCCESSFULLY");
            System.out.println("----------------------------------------");
            System.out.println("Service Name : " + SERVICE_NAME);
            System.out.println("Host         : " + hostAddress);
            System.out.println("Port         : " + RMI_PORT);
            System.out.println("----------------------------------------");
            System.out.println("⏳ Waiting for client connections...");
            System.out.println("Press Ctrl+C to stop the server\n");

            // Keep server alive
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("\n❌ SERVER FAILED TO START");
            e.printStackTrace();
        }
    }
}
