public class Subsekvens{
    public final String subsekvens;
    private int antall;
    
    public Subsekvens(String subsekvens, int antall) {
        this.subsekvens = subsekvens;
        this.antall = antall;
    }

    public int hentAntall() {
        return antall;
    }

    public void settNyAntall(int nyAntall) {
        this.antall = nyAntall;
    }

    @Override 
    public String toString(){
        return "(" + subsekvens + "," + antall + ")";
    }
}
          