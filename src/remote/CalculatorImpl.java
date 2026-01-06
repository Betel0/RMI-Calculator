package remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Calculator RMI Implementation
 */
public class CalculatorImpl extends UnicastRemoteObject implements CalculatorInterface {

    private double memory = 0.0;
    private int operationCount = 0;
    private final String startTime;

    public CalculatorImpl() throws RemoteException {
        super();
        startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        operationCount++;
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        operationCount++;
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        operationCount++;
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new RemoteException("Division by zero");
        operationCount++;
        return a / b;
    }

    @Override
    public double power(double base, double exp) throws RemoteException {
        operationCount++;
        return Math.pow(base, exp);
    }

    @Override
    public double squareRoot(double a) throws RemoteException {
        if (a < 0) throw new RemoteException("Negative root");
        operationCount++;
        return Math.sqrt(a);
    }

    @Override
    public double percentage(double v, double p) throws RemoteException {
        operationCount++;
        return (v * p) / 100.0;
    }

    @Override
    public double reciprocal(double a) throws RemoteException {
        if (a == 0) throw new RemoteException("Zero reciprocal");
        operationCount++;
        return 1.0 / a;
    }

    @Override
    public void memoryStore(double v) throws RemoteException {
        memory = v;
    }

    @Override
    public double memoryRecall() throws RemoteException {
        return memory;
    }

    @Override
    public void memoryClear() throws RemoteException {
        memory = 0.0;
    }

    @Override
    public void memoryAdd(double v) throws RemoteException {
        memory += v;
    }

    @Override
    public void memorySubtract(double v) throws RemoteException {
        memory -= v;
    }

    @Override
    public double changeSign(double a) throws RemoteException {
        return -a;
    }

    @Override
    public void clear() throws RemoteException {
        // no-op
    }

    @Override
    public String getLastOperation() throws RemoteException {
        return "Operations count: " + operationCount;
    }

    @Override
    public String getServerInfo() throws RemoteException {
        return "RMI Calculator Server\nStarted: " + startTime +
                "\nOperations: " + operationCount +
                "\nMemory: " + memory;
    }
}
