package canalBoats.testing;

import canalBoats.*;
import canalBoats.util.CanalSim;

import java.util.Arrays;

public class Test0 {

    public static void main(String[] args) {
        Segment level = new FlatStretch( 5280 );
        Boat boat = new Boat( "Geddes", 25);
        Pilot pilot = new Pilot( Arrays.asList( level ), boat );
        Thread th1 = new Thread( pilot );
        th1.start();
        // Geddes is now underway.

        try {
            th1.join();
        }
        catch( InterruptedException e ) {
            e.printStackTrace();
        }
        CanalSim.println( "All pilots have finished their routes." );

    }
}

/*
James Geddes was one of several engineers chosen in 1816 to supervise the
construction of the Erie Canal.
 */
