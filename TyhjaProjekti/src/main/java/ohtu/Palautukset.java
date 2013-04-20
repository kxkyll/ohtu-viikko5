
package ohtu;

import java.util.ArrayList;
import java.util.List;

public class Palautukset {
    List<Palautus> palautukset = new ArrayList<Palautus>();

    public void setPalautukset(List<Palautus> palautukset) {
        this.palautukset = palautukset;
    }

    public List<Palautus> getPalautukset() {
        return palautukset;
    }

    public String palautuksetToString(){
        int tehtaviaYhteensa = 0;
        int tuntejaYhteensa = 0;
        for (Palautus palautus : palautukset) {
            tehtaviaYhteensa += palautus.getTehtavia();
            tuntejaYhteensa += palautus.getTunteja();
        }
        return "yhteensä " +tehtaviaYhteensa + " tehtävää " +tuntejaYhteensa +" tuntia";
    }
    
    @Override
    public String toString() {
        return palautuksetToString();
    }
    
    
    
    
}
