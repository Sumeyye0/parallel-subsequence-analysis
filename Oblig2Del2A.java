// Oblig2Del2A.java
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

public class Oblig2Del2A {
    public static void main(String[] args) {
        // Opprett Monitor1-instans
        SubsekvensRegister register = new SubsekvensRegister();
        Monitor1 monitor = new Monitor1(register);

        // Start en tråd for hver fil
        ArrayList<Thread> threads = new ArrayList<>();
        try {
            for (String filename : args) {
                Thread thread = new Thread(new LeseTrad(filename, monitor));
                thread.start();
                threads.add(thread);
            }

            // Vent på at alle trådene er ferdige
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    System.out.println("Noe gikk galt ved venting på trådene.");
                    e.printStackTrace();
                }
            }

            // Flett alle de resulterende HashMap-ene i Monitor1-objektet
            while (register.antallHashMaps() > 1) {
                HashMap<String, Subsekvens> map1 = monitor.taUtVilkårligHashMap();
                HashMap<String, Subsekvens> map2 = monitor.taUtVilkårligHashMap();
                HashMap<String, Subsekvens> mergedMap = Monitor1.slaaSammenHashMaps(map1, map2);
                monitor.settInnHashMap(mergedMap);
            }

            // Skriv ut resultatet
            HashMap<String, Subsekvens> resultatMap = monitor.taUtVilkårligHashMap();
            for (Subsekvens sub : resultatMap.values()) {
                System.out.println(sub);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke fil.");
            e.printStackTrace();
        }
    }
}
