mport java.util.Random;

public class Norm{
    public Norm() {
        super();
    }

    public static void main(String[] args) {
        int tnum = 4;
        int anum = 10000000;
        Cal[] thread = new Cal[tnum];
        for(int i=0;i<tnum;i++){
            thread[i] = new Cal(anum/tnum);
        }
        long start = System.currentTimeMillis();
        for(int i=0;i<tnum;i++){
            thread[i].start();
        }
        try {
            for(int i=0;i<tnum;i++){
                thread[i].join();
            }
        } catch (InterruptedException e) {}
        long stop = System.currentTimeMillis();
        System.out.println((stop - start) + "msec");
    }

}
class Cal extends Thread {
    Atom[] n;
    int nums;
    Cal(int nums){
        this.nums = nums;
        n = new Atom[nums];
        for (int i=0;i<nums;i++) {
            n[i] = new Atom();
        }
    }
    public void run() {
        int x =0;
        for (int i=0;i<nums;i++) {
            x += n[i].field * n[i].field;
        }
    }

}
class Atom {
    public int field = 0;
    Random rand = new Random();
    Atom(){
        field = (int)(100*Math.random());
    }
}