package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorInterface extends Remote {

    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;

    double power(double base, double exponent) throws RemoteException;
    double squareRoot(double a) throws RemoteException;
    double percentage(double value, double percent) throws RemoteException;
    double reciprocal(double a) throws RemoteException;

    void memoryStore(double value) throws RemoteException;
    double memoryRecall() throws RemoteException;
    void memoryClear() throws RemoteException;
    void memoryAdd(double value) throws RemoteException;
    void memorySubtract(double value) throws RemoteException;

    double changeSign(double a) throws RemoteException;
    void clear() throws RemoteException;
    String getLastOperation() throws RemoteException;
    String getServerInfo() throws RemoteException;
}
