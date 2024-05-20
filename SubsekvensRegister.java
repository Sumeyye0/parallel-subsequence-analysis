import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SubsekvensRegister {
    private ArrayList<HashMap<String, Subsekvens>> register;

    public SubsekvensRegister() {
        register = new ArrayList<>();
    }

    public void settInnHashMap(HashMap<String, Subsekvens> subsekvenser) {
        register.add(subsekvenser);
    }

    public ArrayList<HashMap<String, Subsekvens>> hentBeholder() {
        return register;
    }

    public HashMap<String, Subsekvens> taUtVilkårligHashMap() {
        if (register.isEmpty()) {
            return null;
        }
        return register.remove(0);
    }

    public int antallHashMaps() {
        return register.size();
    }

    public static HashMap<String, Subsekvens> lesFil(String filnavn) throws FileNotFoundException {
        HashMap<String, Subsekvens> subsekvenser = new HashMap<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filnavn));
            while (scanner.hasNextLine()) {
                String linje = scanner.nextLine();
                if (linje.length() < 3) {
                    throw new IllegalArgumentException("Ugyldig, må inneholde minst 3 tegn.");
                }

                for (int i = 0; i <= linje.length() - 3; i++) {
                    String subsekvens = linje.substring(i, i + 3);
                    if (!subsekvenser.containsKey(subsekvens)){
                        subsekvenser.put(subsekvens, new Subsekvens(subsekvens, 1));
                    } else {
                        Subsekvens eksisterende = subsekvenser.get(subsekvens);
                        eksisterende.settNyAntall(eksisterende.hentAntall() + 1);
                    }
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return subsekvenser;
    }

    public static HashMap<String,Subsekvens> slaaSammenHashMaps(HashMap<String,Subsekvens> map1, HashMap<String,Subsekvens> map2) {
        for (String subsekvens : map2.keySet()) {
            if (map1.containsKey(subsekvens)) {
                // hvis den andre inneholder subsekvensen tar den ut Subsekvens objektet og oeker med den andres antall forekomster
                map1.get(subsekvens).settNyAntall(map2.get(subsekvens).hentAntall());
            } else {
                map1.put(subsekvens, map2.get(subsekvens));
            }
        }
        return map1;
    }
}
