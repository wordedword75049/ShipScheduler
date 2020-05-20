import java.util.ArrayDeque;
import java.util.Queue;

public class TunnelPass implements Tunnel{

    static int ships_pass_left;
    public final static Object obj = new Object();
    Queue<Ship> DressQueue = new ArrayDeque<>();
    Queue<Ship> BananaQueue = new ArrayDeque<>();
    Queue<Ship> MealQueue = new ArrayDeque<>();

    TunnelPass(int ships) {
        ships_pass_left = ships;
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
            return DressQueue.poll();
        } else if (shipType == Type.BANANA) {
            return DressQueue.poll();
        } else {
            return DressQueue.poll();
        }
    }
}
