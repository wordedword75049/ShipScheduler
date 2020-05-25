import java.util.ArrayDeque;
import java.util.Queue;

public class TunnelPass implements Tunnel{

    static int ships_pass_left;
    Queue<Ship> DressQueue = new ArrayDeque<>();
    Queue<Ship> BananaQueue = new  ArrayDeque<>();
    Queue<Ship> MealQueue = new  ArrayDeque<>();
    public final static Object obj = new Object();
    int tunnel_count = 0;

    TunnelPass(int ships) {
        ships_pass_left = ships;
    }

    public boolean check_ships() {
        synchronized (obj) {
            if (ships_pass_left > 0) {
                return true;
            } else {
                obj.notifyAll();
                return false;
            }
        }
    }

    @Override
    public boolean add(Ship element) {
        try {
            tunnel_count = DressQueue.size() + BananaQueue.size() + MealQueue.size();
            synchronized (obj) {
                while (tunnel_count >= 5) {
                    System.out.println("Tunnel is full, ship #" +element.id+" is  waiting for some place");
                    obj.wait();
                }
                System.out.println("Ship #" + element.id + " found a place in tunnel");
                 System.out.println("Adding "+element.id+" ship to tunnel, there will be " + (tunnel_count + 1));
                    if (element.type == Type.DRESS) {
                        DressQueue.offer(element);
                    } else if (element.type == Type.BANANA) {
                        BananaQueue.offer(element);
                    } else {
                        MealQueue.offer(element);
                    }
                    obj.notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted :(");
        }
        return true;
    }

    @Override
    public Ship get(Type shipType){
        Ship polled = new Ship();
        try {
        Queue<Ship> queue;
        if (shipType == Type.DRESS) {
            queue = DressQueue;
        } else if (shipType == Type.BANANA) {
            queue =  BananaQueue;
        } else {
            queue = MealQueue;
        }
        synchronized (obj) {
            while (queue.peek() == null && ships_pass_left > 0) {
                obj.wait();
            }
            if (ships_pass_left > 0) {
                polled = queue.poll();
                tunnel_count -= 1;
                ships_pass_left -= 1;
                System.out.println("Ship #" + polled.id + " was taken out of tunnel to pier.");
                obj.notifyAll();
            }
        }
        } catch (InterruptedException e) {
            System.out.println("Interrupted :(");
        }
        return polled;
    }
}
