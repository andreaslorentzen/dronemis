package dronemis;

public class TestHandler implements IDroneHandler {
    @Override
    public void takeOff() {

    }

    @Override
    public void useFrontCamera() {
        System.out.println("Now using FrontCamera");
    }

    @Override
    public void useBottomCamera() {
        System.out.println("Now using BottomCamera");
    }

    @Override
    public void land() {

    }
}
