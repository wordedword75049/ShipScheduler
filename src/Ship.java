class Ship extends Thread {
    public int count;
    Size size;
    Type type;

    public boolean countCheck() {
        if (count / 10 > 0) {
            return true;
        } else {
            return false;
        }
    }

    //...
}