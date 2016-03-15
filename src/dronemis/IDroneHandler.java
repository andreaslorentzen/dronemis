package dronemis;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IDroneHandler {
    void takeOff();

    void useFrontCamera();

    void useBottomCamera();

    void land();

}
