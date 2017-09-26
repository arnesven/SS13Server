
package tests;
import main.*;

import java.io.IOException;


/**
 * Created by erini02 on 26/09/17.
 */
public class SimulationTest {

    public static void main(String[] args) {
//
        Thread serverThread = new Thread() {

            @Override
            public void run() {
                try {
                    SS13ServerMain.main(new String[]{"Simulation Server", "55555"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        serverThread.start();

        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SimulationClient sim1 = new SimulationClient(55555, "SIM1");
        SimulationClient sim2 = new SimulationClient(55555, "SIM2");

        try {
            Thread.currentThread().sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sim1.setReady(true);
        sim2.setReady(true);


    }
}
