import static java.lang.Thread.sleep;

public class PierLoader implements Runnable {
    private Tunnel tunnel;
    private Type shipType;

    PierLoader(Tunnel tunnel, Type shipType) {
        this.tunnel = tunnel;
        this.shipType = shipType;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Getting the ship....");
                Ship ship = tunnel.get(shipType);
                System.out.println(ship);
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
