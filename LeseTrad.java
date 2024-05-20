import java.io.FileNotFoundException;
import java.util.HashMap;

public class LeseTrad implements Runnable {
    private String filnavn;
    private Monitor1 monitor;

    public LeseTrad(String filnavn, Monitor1 monitor) {
        this.filnavn = filnavn;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        HashMap<String, Subsekvens> map = Monitor1.lesFil(filnavn);
        monitor.settInn(map);
    }
}
