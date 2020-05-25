import java.util.concurrent.Delayed;

class Ship extends Thread{
    public int count;
    Size size;
    Type type;
    int id = -1;

    public boolean countCheck() {
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    //...
}
