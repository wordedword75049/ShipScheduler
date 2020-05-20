import static java.lang.Thread.sleep;

public class PierLoader implements Runnable {
    private TunnelPass tunnel;
    private Type shipType;

    PierLoader(TunnelPass tunnel, Type shipType) {
        this.tunnel = tunnel;
        this.shipType = shipType;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Getting the ship....");
                Ship ship = tunnel.get(shipType);
                while (ship.countCheck()) {
                    System.out.println("Loading the ship....");
                    sleep(1000);
                    ship.count -= 10;
                }
                System.out.println("Getting the ship....");
                //sleep(10000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted :(");
        }
    }
}
