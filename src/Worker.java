package src;

public class Worker extends Thread {
  @Override
  public void run() {
    System.out.println("Hello from a thread!");
  }
}
