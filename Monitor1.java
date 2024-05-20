import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor1 {
    SubsekvensRegister register;
    Lock lock;

    public Monitor1(SubsekvensRegister register) {
        this.register = register;
        lock = new ReentrantLock();
    }

    public void settInnHashMap(HashMap<String, Subsekvens> map) {
        lock.lock();
        try {
            register.settInnHashMap(map);
        } finally {
            lock.unlock();
        }
    }

    public HashMap<String, Subsekvens> taUtVilkårligHashMap() {
        return register.taUtVilkårligHashMap();
    }

    public int antallHashMaps() {
        return register.antallHashMaps();
    }

    public static HashMap<String, Subsekvens> lesFil(String filnavn) {
        try {
            return SubsekvensRegister.lesFil(filnavn);
        } catch (FileNotFoundException e) {
            System.out.println("Filen ble ikke funnet.");
            return null;
        }
    }

    public static HashMap<String, Subsekvens> slaaSammenHashMaps(HashMap<String, Subsekvens> map1, HashMap<String, Subsekvens> map2) {
        return SubsekvensRegister.slaaSammenHashMaps(map1, map2);
    }
}
