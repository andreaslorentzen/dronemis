package dronemis.control;

import de.yadrone.base.navdata.Altitude;
import de.yadrone.base.navdata.AltitudeListener;

/**
 * Created by mathias on 09/03/16.
 */
public class Listener implements AltitudeListener {
    static int instances = 1;
    private int i;

    public Listener(){
        i = instances;
        instances++;
    }

    @Override
    public void receivedAltitude(int i) {

    }

    @Override
    public void receivedExtendedAltitude(Altitude altitude) {
        if ( i == 1)
            System.out.println("Altitude: " + altitude.getRaw());
    }
}
