import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig2Hele {
    public static void main(String[] args) {
        SubsekvensRegister registerSyke = new SubsekvensRegister();
        Monitor1 monitorSyke = new Monitor1(registerSyke);

        SubsekvensRegister registerFrisk = new SubsekvensRegister();
        Monitor1 monitorFrisk = new Monitor1(registerFrisk);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(args[0] + "/metadata.csv"));
            ArrayList<Thread> leseTraader = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String filnavn = scanner.nextLine();
                if (filnavn.endsWith("True")) {
                    LeseTrad leseTrad = new LeseTrad(args[0] + "/" + filnavn, monitorSyke);
                    Thread traad = new Thread(leseTrad);
                    leseTraader.add(traad);
                    traad.start();
                } else {
                    LeseTrad leseTrad = new LeseTrad(args[0] + "/" + filnavn, monitorFrisk);
                    Thread traad = new Thread(leseTrad);
                    leseTraader.add(traad);
                    traad.start();
                }
            }

            // Venter på at alle lesetråder skal bli ferdige
            for (Thread traad : leseTraader) {
                traad.join();
            }
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
            return;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        // Starter flettingstråder
        int antallFlettere = 8;
        ArrayList<Thread> fletteTraader = new ArrayList<>();
        for (int i = 0; i < antallFlettere; i++) {
            FletteTrad fletteTrad = new FletteTrad(monitorSyke);
            Thread traad = new Thread(fletteTrad);
            fletteTraader.add(traad);
            traad.start();
        }
        for (int i = 0; i < antallFlettere; i++) {
            FletteTrad fletteTrad = new FletteTrad(monitorFrisk);
            Thread traad = new Thread(fletteTrad);
            fletteTraader.add(traad);
            traad.start();
        }

        // Venter på at alle flettingstrådene skal bli ferdige
        for (Thread traad : fletteTraader) {
            try {
                traad.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Analyserer resultatet for å finne dominante subsekvenser
        analyseResultat(monitorSyke, monitorFrisk);
    }

    private static void analyseResultat(Monitor1 monitorSyke, Monitor1 monitorFrisk) {
        HashMap<String, Subsekvens> mapSyke = monitorSyke.hentBeholder().get(0);
        HashMap<String, Subsekvens> mapFrisk = monitorFrisk.hentBeholder().get(0);

        // Finn og skriv ut dominante subsekvenser
        for (String subsekvens : mapSyke.keySet()) {
            if (mapFrisk.containsKey(subsekvens)) {
                int antallSyke = mapSyke.get(subsekvens).hentAntall();
                int antallFrisk = mapFrisk.get(subsekvens).hentAntall();
                if (antallSyke > antallFrisk + 7) {
                    System.out.println("Dominant subsekvens: " + subsekvens);
                }
            }
        }
    }
}
