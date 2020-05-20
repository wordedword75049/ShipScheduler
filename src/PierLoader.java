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
                        System.out.println("desired queue is empty. Waiting for entrance....");
                        obj.wait();
                    }
                    System.out.println("Ship has come!");
                    ship = tunnel.get(shipType);
                    tunnel.ships_pass_left--;
                    notifyAll();
                }
                while ((ship != null) && ship.countCheck()) {
                    System.out.println("Loading the ship....");
                    Thread.sleep(1000);
                    ship.count -= 10;
                }
                System.out.println("Sending the ship....");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted :(");
        }
    }
}
