package canalBoats;

/**
 * The code that simulates a canal boat.
 * It knows its length, thus allowing for calculations regarding
 * how long it takes to move from one place to another.
 * More importantly, it has two states: moving through a lock, or
 * traveling through a flat stretch.
 * For each Lock, there is a queue of waiting boats and a LockMaster
 * who moves boats through the lock. It is assumed that during this
 * time the boat's Pilot is suspended waiting for the method
 * {@link #waitUntilOut()} to return, meaning this Boat is out of
 * the lock.
 *
 * Author: Dhruv Rajpurohit
 */
public class Boat {

    // TODO private data here
    private final String name;
    private final int length;
    private boolean insideLock = false;

    /**
     * Initialize the boat with its name and dimensions.
     * @rit.post This Boat is not moving through a lock.
     * @param name Boat's string name, for simulation reporting
     * @param length Boat's length for timing calculations
     */
    public Boat(String name, int length) {

        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    /**
     * What is the boat's name?
     * @return the name of the boat given in the constructor
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * This Boat's
     * Pilot calls this to wait for their boat to exit the lock.
     * @rit.pre This Boat is moving through a lock (enqueued and
     *          hasn't come out).
     */
    public synchronized void waitUntilOut() throws InterruptedException {

        synchronized (this){
            while (insideLock){
                try {
                    this.wait();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is called when the boat is about to be
     * enqueued at a lock.
     * @rit.pre This Boat is not moving through a lock.
     * @rit.post This Boat is moving through a lock.
     */
    public synchronized void enteringLock() {

        insideLock = true;
    }

    /**
     * The lock master calls this method to signal that
     * the boat is through the lock, and the pilot can
     * have the boat back.
     * @rit.pre This Boat is moving through a lock.
     * @rit.post This Boat is not moving through a lock.
     * @param pilot
     */
    public synchronized void release(Pilot pilot) {

        insideLock = false;
        notifyAll();
    }
}
