package canalBoats;

import canalBoats.util.CanalSim;

/**
 * Each LockMaster object belongs to a Lock.
 * Its {@link #run()} method executes in a separate thread.
 * Its job is to wait for Boats to arrive at the Lock (they're in a queue),
 * move them through the Lock (a sleep calculation for this simulation),
 * and release them to their Pilots.
 *
 * Author: Dhruv Rajpurohit
 */
public class LockMaster implements Runnable {

    private static int counter = 0;
    private final int id = counter++;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.id;
    }

    /**
     * A sentinel Boat object that is sent through locks to make their
     * LockMasters stop running.
     * Because it is a single object that everyone uses, ordinary
     * "==" comparison can be used to check for it.
     */
    public static Boat END_BOAT = new Boat( "END", 0);

    private Lock lock;

    /**
     * Create a new LockMaster.
     * Called from {@link Lock#Lock(String,int, int)}, since each
     * LockMaster is owned by a Lock.
     * Locks don't have assigned names; they are
     * just assigned sequential IDs, starting at 0.<br>
     * Format: <code>Lock<i>n</i></code>
     * @param lock the Lock to which this LockMaster belongs
     */
    public LockMaster( Lock lock ) {
        this.lock = lock;
    }

    /**
     * Repeatedly do the following.
     * <ol>
     *     <li>Wait and fetch a Boat off the Lock's queue.</li>
     *     <li>
     *         Calculate the time to go through the lock based on
     *         the boat's length, the lock's length, and the depth
     *         (level change) of the lock. Sleep for that much time.
     *     </li>
     *     <li>Release the Boat ({@link Boat#release(Pilot)}).</li>
     *     <li>
     *         Calculate the time to do the opposite level change.
     *         Sleep for that much time.
     *     </li>
     * </ol>
     * This method returns after the LockMaster has ferried
     * {@link LockMaster#END_BOAT} through its Lock.
     */
    public void run() {
        Boat nBoat;
        CanalSim.println( this + ": I'm on duty." );
        synchronized (this){
            synchronized (lock){
                while ((nBoat = lock.admitNextBoat()) != END_BOAT){
                    CanalSim.println( this + " accepted " + this.lock.toString());
                    CanalSim.sleep((CanalSim.UP_DOWN_SPEED * lock.getDepth())
                            + (CanalSim.BOAT_LOCK_SPEED * lock.getLength()));
                    CanalSim.println( this + " helping " + this.lock.toString() + " through " + lock );
                    CanalSim.println( this + ": " + this.lock.toString() + " is through the lock");
                }
            }
        }
        CanalSim.println( this + ": I'm going off duty!" );
    }
}
