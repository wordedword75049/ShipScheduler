import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShipGenerator implements Runnable {
    private Tunnel currentTunnel;
    private int AwaitedShipNumber;
    public final static Object obj = new Object();
    ShipGenerator(Tunnel tunnel, int shipcount) {
        this.currentTunnel = tunnel;
        this.AwaitedShipNumber = shipcount;
    }

    @Override
    public void run() {
        for (int i = 0; i < AwaitedShipNumber; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (obj) {
                        try {
                            Ship createdShip = new Ship();
                            createdShip.size = getRandomSize();
                            createdShip.type = getRandomType();
                            createdShip.count = createdShip.size.getValue();
                            System.out.println("New ship created. Sending...");
                            while (!currentTunnel.add(createdShip)) {
                                System.out.println("Tunnel Full, we have to wait");
                                wait();
                            }
                            System.out.println("Sent to tunnel");
                            notifyAll();
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted :(");
                        }
                    }
                }
            }).start();
        }
    }

    //вспомогательные методы для генерации кораблей.
    private Type getRandomType() {
        Random random = new Random();
        return Type.values()[random.nextInt(Type.values().length)];
    }

    private Size getRandomSize() {
        Random random = new Random();
        return Size.values()[random.nextInt(Size.values().length)];
    }
}
