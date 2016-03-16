package dronemis.control;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.navdata.AltitudeListener;
import de.yadrone.base.navdata.GyroListener;
import de.yadrone.base.navdata.GyroPhysData;
import de.yadrone.base.navdata.GyroRawData;

import java.util.Scanner;

/**
 * Created by mathias on 02/03/16.
 */

public class Test {
    static IARDrone drone;

    public Test(){
        drone = new ARDrone();

        testGyro();
//        testUltrasound();

//        testTakeOfLand();
//        runTestLoop();

    }

    public void testGyro(){
        GyroListener gl = new GyroListener() {
            @Override
            public void receivedRawData(GyroRawData gyroRawData) {
                System.out.println("1");
                System.out.println(gyroRawData.toString());
            }

            @Override
            public void receivedPhysData(GyroPhysData gyroPhysData) {
                System.out.println("2");
                System.out.println(gyroPhysData.toString());
            }

            @Override
            public void receivedOffsets(float[] floats) {

            }
        };
        drone.getNavDataManager().addGyroListener(gl);
        drone.getNavDataManager().addGyroListener(gl);
    }

    public void testUltrasound(){
        AltitudeListener al = new Listener();
        AltitudeListener al2 = new Listener();
        drone.getNavDataManager().addAltitudeListener(al);
        drone.getNavDataManager().addAltitudeListener(al);

    }

    public static void testTakeOfLand() {
        drone.getCommandManager().takeOff();

        drone.getCommandManager().landing();
    }

    public static void runTestLoop() {
        drone.getCommandManager().takeOff();
        Scanner input = new Scanner(System.in);
        try {
            while (true) {
                String in = input.nextLine();

                if (in.equals("q")) {
                    System.exit(1);
                } else if (in.charAt(0) == 'a') {
                    drone.getCommandManager().goLeft(20).doFor(40);
                    drone.getCommandManager().freeze();
                } else if (in.charAt(0) == 'd') {
                    drone.getCommandManager().goRight(20).doFor(40);
                    drone.getCommandManager().freeze();
                } else if (in.charAt(0) == 'w') {
                    drone.getCommandManager().forward(20).doFor(40);
                    drone.getCommandManager().freeze();
                } else if (in.charAt(0) == 's') {
                    drone.getCommandManager().backward(20).doFor(40);
                    drone.getCommandManager().freeze();
                } else if (in.charAt(0) == 'l') {
                    drone.getCommandManager().landing();
                } else if (in.charAt(0) == 'e') {
                    drone.getCommandManager().stop();
                    break;
                } else if (in.charAt(0) == 't') {
                    drone.getCommandManager().takeOff();
                } else if (in.charAt(0) == 'u') {
                    drone.getCommandManager().up(20).doFor(40);
                    drone.getCommandManager().freeze();
                } else if (in.charAt(0) == 'j') {
                    drone.getCommandManager().down(20).doFor(40);
                    drone.getCommandManager().freeze();
                }

            }
        } catch (Exception e) {
            drone.getCommandManager().landing();
        }
        drone.getCommandManager().landing();
        drone.getCommandManager().stop();
    }
}
