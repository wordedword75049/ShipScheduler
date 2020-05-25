import java.lang.Thread.*;
import java.util.concurrent.*;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PierLoader implements Runnable {
    private TunnelPass tunnel;
    public final static Object obj = new Object();
    private Type shipType;
    private int sent_ships = 0;

    PierLoader(TunnelPass tunnel, Type shipType) {
        this.tunnel = tunnel;
        this.shipType = shipType;
    }

    @Override
    public void run() {
            Ship ship = new Ship();
            while (tunnel.check_ships()) {
                System.out.println(shipType + " pier is getting the ship....");
                ship = tunnel.get(shipType);
                if (ship.id != -1) {
                    System.out.println(shipType + " pier got the ship with id " + ship.id);
                    try {
                        while (ship.countCheck()) {
                            Thread.sleep(10);
                            ship.count -= 10;
                        }
                        sent_ships += 1;
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted :(");
                    }
                    System.out.println(shipType + " pier is sending away the ship with id " + ship.id + ".  Total ships sent from this pier " + sent_ships);
                }
            }
        System.out.println(shipType + " pier is closed.");
    }
}
