import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Oblig2Del2B {
    public static void main(String[] args) {
        SubsekvensRegister register = new SubsekvensRegister();
        Monitor2 mon = new Monitor2(register);

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(args[0] + "/metadata.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke fil.");
            return;
        }

        ArrayList<Thread> leseTraader = new ArrayList<>();
        while (scanner.hasNextLine()) {
            LeseTrad leseTrad = new LeseTrad(args[0] + "/" + scanner.nextLine(), mon);
            Thread traad = new Thread(leseTrad);
            leseTraader.add(traad);
            traad.start();
        }

        for (Thread traad : leseTraader) {
            try {
                traad.join();
            } catch (InterruptedException e) {
                System.out.println("Error.");
            }
        }
        System.out.println("Tråden er lest ferdig.");

        int antallFlettere = 8;
        ArrayList<Thread> fletteTraader = new ArrayList<>();
        for (int i = 0; i < antallFlettere; i++) {
            FletteTrad fletteTrad = new FletteTrad(mon);
            Thread traad = new Thread(fletteTrad);
            fletteTraader.add(traad);
            traad.start();
        }

        for (Thread traad : fletteTraader) {
            try {
                traad.join();
            } catch (InterruptedException e) {
                System.out.println("Error");
            }
        }

        HashMap<String, Subsekvens> Hash = mon.taUt();

        int antall = 0;
        Subsekvens peker = null;
        for (Subsekvens sub : Hash.values()) {
            if (sub.hentAntall() > antall) {
                antall = sub.hentAntall();
                peker = sub;
            }
        }

        System.out.println(peker);
    }
}
