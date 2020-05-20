import java.lang.Thread.*;

import java.util.ArrayDeque;
import java.util.Queue;

public class PierLoader implements Runnable {
    private TunnelPass tunnel;
    public final static Object obj = new Object();
    private Type shipType;

    PierLoader(TunnelPass tunnel, Type shipType) {
        this.tunnel = tunnel;
        this.shipType = shipType;
    }

    @Override
    public void run() {
        try {
            Queue<Ship> queue = new ArrayDeque<>();
            while (true) {
                System.out.println("Getting the ship....");
                Ship ship;
                if (shipType == Type.DRESS) {
                    queue = tunnel.DressQueue;
                } else if (shipType == Type.BANANA) {
                    queue = tunnel.BananaQueue;
                } else {
                    queue = tunnel.MealQueue;;
                }
                synchronized (obj) {
                    while (queue.peek() == null) {
                        System.out.println(shipType + " queue is empty. Waiting for entrance....");
                        obj.wait();
                    }
                    System.out.println("Ship has come!");
                    ship = tunnel.get(shipType);
                    System.out.println( shipType + " pier got ship = " + ship);
                    System.out.println("Pier of type " + shipType + " got ship with id " + ship.id + " and size " + ship.size.getValue());
                    tunnel.ships_pass_left--;
                    System.out.println("here1");
                    synchronized (ShipGenerator.obj) {
                        System.out.println("here2");
                        ShipGenerator.obj.notifyAll();
                        System.out.println("here3");
                    }
                    System.out.println("here4");
                }
                while (ship.countCheck()) {
                    System.out.println("Loading the ship....");
                    Thread.sleep(10);
                    ship.count -= 10;
                }
                System.out.println("Sending the ship....");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted :(");
        }
    }
}
