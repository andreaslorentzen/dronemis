package dronemis;

public interface IDroneHandler {

    void init();

    void takeOff();

    void land();

    void moveForward();

    void moveBackward();

    void moveLeft();

    void moveRight();

    void moveUp();

    void moveDown();

    void turnLeft();

    void turnRight();

    void useFrontCamera();

    void useBottomCamera();

    void emergencyStop();

    boolean isFlying();
}
