import java.util.ArrayDeque;
import java.util.Queue;

public class TunnelPass implements Tunnel{

    private static int ships_pass_left;
    public final static Object obj = new Object();
    private Queue<Ship> DressQueue = new ArrayDeque<>();
    private Queue<Ship> BananaQueue = new ArrayDeque<>();
    private Queue<Ship> MealQueue = new ArrayDeque<>();

    TunnelPass(int ships) {
        ships_pass_left = ships;
    }

    private static Ship getShipFromQueue(Queue<Ship> queue) {
        final Ship[] gotShip = {new Ship()};
        new Thread(new Runnable() {
        @Override
        public void run() {

                try {
                    if (ships_pass_left > 0) {
                        synchronized (obj) {
                            while (queue.peek() == null) {
                                System.out.println("desired queue is empty. Waiting for entrance....");
                                this.wait();
                            }
                            System.out.println("Ship has come!");
                            gotShip[0] = queue.poll();
                            ships_pass_left--;
                            notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted :(");
                }
        }
        }).start();

        return gotShip[0];
    }

    @Override
    public boolean add(Ship element) {
        int tunnel_count = DressQueue.size()+BananaQueue.size()+MealQueue.size();
        System.out.println("Adding to tunnel, there will be ");
        System.out.println(tunnel_count+1);
        if (tunnel_count < 5 ) {
            if (element.type == Type.DRESS) {
                DressQueue.offer(element);
                return true;
            } else if (element.type == Type.BANANA) {
                BananaQueue.offer(element);
                return true;
            } else {
                MealQueue.offer(element);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public Ship get(Type shipType) {
        if (shipType == Type.DRESS) {
            return getShipFromQueue(DressQueue);
        } else if (shipType == Type.BANANA) {
            return getShipFromQueue(BananaQueue);
        } else {
            return getShipFromQueue(MealQueue);
        }
    }
}
