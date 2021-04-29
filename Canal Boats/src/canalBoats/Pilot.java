package canalBoats;

import java.util.ArrayList;
import java.util.List;

import canalBoats.util.CanalSim;

/**
 * Each Boat has a Pilot.
 * The Pilot's {@link #run()} method executes in a separate thread.
 * Its job is to drive its Boat through FlatStretches, enqueue it at
 * each Lock, and wait for the Boat to come out of the Lock.
 *
 * Author: Dhruv Rajpurohit
 */
public class Pilot implements Runnable {

    private static int counter = 0;
    private final int id = counter++;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.id;
    }


    private List<Segment> route;
    private final Boat boat;


    /**
     * Create a new Pilot for an already-created Boat.
     * Pilots don't have assigned names; they are
     * just assigned sequential IDs, starting at 0.<br>
     * Format: <code>Pilot<i>n</i></code>
     * @param route the sequence of FlatStretches and Locks through which
     *              the Boat must be piloted.
     * @param boat this Pilot's Boat
     */
    public Pilot( List< Segment > route, Boat boat ) {

        CanalSim.println( "New " + this + " has " + boat );
        this.boat = boat;
        route = new ArrayList<>();
    }

    /**
     * Repeatedly do the following.
     * <ol>
     *     <li>Look up the next Segment of the route.</li>
     *     <li>
     *         Arrive at the Segment, noting whether or not it must
     *         give up its Boat to a LockMaster.
     *         <ul>
     *             <li>
     *                 If so, wait until the Boat says it's out.
     *                 {@link Boat#waitUntilOut()}
     *             </li>
     *             <li>
     *                 If not, simulate moving over water with a
     *                 call to {@link CanalSim#sleep(float)},
     *                 taking into account the Boat length and speed
     *                 and the length of the stretch of water.
     *             </li>
     *         </ul>
     *     </li>
     * </ol>
     * This method returns when the entire route has been traveled.
     */
    public void run() {
        synchronized (this) {
            synchronized (boat) {
                for (Segment seg : route){
                    CanalSim.println( this + " is arriving at " + seg );
                    if (seg.arrive(boat)){
                        try {
                            boat.waitUntilOut();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        CanalSim.println( this + " has " + boat + " back." );
                    }
                    else {
                        CanalSim.sleep(boat.getLength() * CanalSim.BOAT_LOCK_SPEED);
                        CanalSim.println(this + ": " + boat + " is through the stretch.");
                    }
                }
            }

        }
    }

}
