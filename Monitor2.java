// Her har jeg jobbet sammen med @Awatefhj

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {
    private final SubsekvensRegister register;
    private final Lock lock;
    private final Condition notReady;

    public Monitor2(SubsekvensRegister register) {
        this.register = register;
        this.lock = new ReentrantLock();
        this.notReady = lock.newCondition();
    }

    public HashMap<String, Subsekvens> taUtForFletting() throws InterruptedException {
        lock.lock();
        try {
            while (register.antallHashMaps()< 2){
                notReady.await();
             }

            HashMap<String, Subsekvens> map1 = register.taUtVilkårligHashMap();
            HashMap<String, Subsekvens> map2 = register.taUtVilkårligHashMap();
            
            // Slå sammen map1 og map2 til en ny HashMap
            HashMap<String, Subsekvens> mergedMap = new HashMap<>();
            mergedMap.putAll(map1);
            mergedMap.putAll(map2);

            return mergedMap; // Returnerer en kopi av de to HashMap-ene
        } finally {
            lock.unlock();
        }
    }

    public void settInn(HashMap<String, Subsekvens> map) {
        lock.lock();
        try {
            register.settInnHashMap(map); // Setter inn den flettede HashMap-en
            notReady.signal(); // Vekker en tråd som venter på nye HashMap-er
            
            // Sjekker om det bare er én HashMap igjen etter fletting
            if (register.antallHashMaps() == 1) {
                System.out.println("Fletting er fullført. Stopp tråder.");
                System.exit(0); // Avslutter programmet
            }
        } finally {
            lock.unlock();
        }
    }
}
