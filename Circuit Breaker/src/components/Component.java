package components;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This an abstract class where the components of the circuit are setup
 *
 * Author: Dhruv Rajpurohit
 */

public abstract class Component {
    protected final String name;
    protected Component source;
    protected int draw;
    protected boolean engaged;
    protected Collection<Component> loads;
    protected String front;
    protected int rating;

    /**
     * The function is the constructor for the class
     * @param name name of the component
     * @param source the source of the component
     */
    protected Component(String name, Component source) {
        this.name = name;
        this.source = source;
        draw = 0;
        engaged = false;
        loads = new ArrayList<>();
        front = "";
    }

    /**
     * Accessor for the name of the component
     * @return the name
     */
    public String getName(){
        return this.name;
    }


    /**
     * The function attaches the component
     * @param load the component to be attached
     */
    protected void attach(Component load){
        loads.add(load);
        if(engaged){
            load.engaged = true;
        }
    }

    /**
     * The function changes the amount of current passing
     * @param delta number of amperes
     */
    protected void changeDraw(int delta){
        System.out.println(this.toString() + " changing draw by " + delta);
        draw += delta;
    }

    /**
     * The function engages the power in the source
     */
    public void engage(){
        engaged = true;
    }

    /**
     * The function disengages the power in the source
     */
    public void disengage(){
        engaged = false;
    }

    /**
     * The function is a mutator to change the draw to current value when needed
     * @param draw the value to be changed to
     */
    public void setDraw(int draw){
        System.out.println(this.toString() + " setting draw");
        this.draw = draw;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, this.draw*-1);
    }

    /**
     * Accessor for draw
     * @return the draw
     */
    public int getDraw(){
        return this.draw;
    }

    /**
     * Accessor of the source
     * @return the current source
     */
    protected Component getSource(){
        return this.source;
    }

    /**
     * Accessor of loads
     * @return the loads
     */
    protected Collection<Component> getLoads(){
        return this.loads;
    }

    /**
     * check if the component is fed power
     * @return the true or false
     */
    protected boolean engaged(){
        if (engaged){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * adds load to the component
     * @param newLoad the new load to be added
     */
    protected void addLoad(Component newLoad){
        System.out.println(this.toString() + " adding load");
        attach(newLoad);
        draw += newLoad.draw;
    }

    /**
     * The function engages the load
     */
    protected void engageLoads(){
        for(Component load:loads) {
            if (load instanceof PowerSource) {
                load.engage();
                for (Component outlet : load.loads) {
                    outlet.engage();
                    for (Component appliance : outlet.loads) {
                        appliance.engage();
                    }
                }
            }
            if (load instanceof CircuitBreaker) {
                load.engage();
                for (Component outlet : load.loads) {
                    for (Component appliance : outlet.loads) {
                        appliance.engage();
                    }
                }
            }
            if (load instanceof Outlet) {
                load.engage();
                for (Component appliance : load.loads) {
                    appliance.engage();
                }
            }
        }
    }

    /**
     * The funtion disengages the load
     */
    protected void disengageLoads(){
        for(Component load:loads) {
            if (load instanceof PowerSource) {
                for (Component outlet : load.loads) {
                    outlet.disengage();
                    for (Component appliance : outlet.loads) {
                        appliance.disengage();
                    }
                }
            }
            if (load instanceof CircuitBreaker) {
                for (Component outlet : load.loads) {
                    for (Component appliance : outlet.loads) {
                        appliance.disengage();
                    }
                }
            }
            if (load instanceof Outlet) {
                for (Component appliance : load.loads) {
                    appliance.disengage();
                }
            }if(load instanceof Appliance){
                load.disengage();
            }
        }
    }

    /**
     * the function is to display the sub tree
     */
    public void display() {
        System.out.println("");
        System.out.println(" + " + this.toString());
        for (Component attachment : loads) {
            System.out.println("    + " + attachment.toString());
            for (Component out : attachment.loads) {
                System.out.println(front + "        + " + out.toString());
                for (Component app : out.loads) {
                    System.out.println("            + " + app.toString());
                }
            }
        }
    }

    @Override
    public String toString(){
        return Reporter.identify(this);
    }
}
