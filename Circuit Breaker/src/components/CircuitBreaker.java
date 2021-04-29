package components;

/**
 * The class is extended by the Component class to implement the circuit breaker
 *
 * Author: Dhruv Rajpurohit
 */

public class CircuitBreaker extends Component{

    protected int limit;

    /**
     * Constructor of the class
     * @param name name of the component
     * @param source the source
     * @param limit the limit of the draw
     */
    public CircuitBreaker(String name, Component source, int limit) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.limit = limit;
    }

    public void attach(){
        Reporter.report(this.source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    /**
     * Checks if the switch is on or not
     * @return true or false
     */
    public boolean isSwitchOn(){
        if (engaged){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Accessor for the limit
     * @return
     */
    public int getLimit(){
        return this.limit;
    }

    /**
     * The function turns on the appliance
     */
    public void turnOn(){
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        for(Component elec:loads){
            elec.engage();
            for (Component appliance : elec.loads) {
                appliance.engage();
                if(appliance.engaged){
                    appliance.changeDraw(appliance.rating);
                }
            }
        }
    }

    /**
     * Checks if there is an overload
     * @return true or false
     */
    public boolean isOverload(){
        if (draw > limit){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * The function changes the draw
     * @param rating the rating of the appliance
     */
    public void changeDraw(int rating) {
        draw += rating;
        if(isOverload()){
            Reporter.report(this, Reporter.Msg.BLOWN, draw);
            overload();
        }else{
            Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
            source.changeDraw(rating);
        }
    }

    /**
     * the function perform all the necessary tasks after detecting an overload
     * Switches of the components and the appliance.
     */
    public void overload(){
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        setDraw(0);
        source.setDraw(0);
        for(Component reason:loads) {
            if (reason instanceof Outlet) {
                Reporter.report(this, Reporter.Msg.DISENGAGING);
                engaged = false;
                for (Component appliance : reason.loads) {
                    appliance.disengage();
                    Reporter.report(appliance, Reporter.Msg.DRAW_CHANGE, appliance.rating*-1);
                    Reporter.report(appliance.source, Reporter.Msg.DRAW_CHANGE, appliance.rating*-1);
                    appliance.source.draw = appliance.source.draw + -1*appliance.rating;
                }
            }else if(reason instanceof Appliance){
                disengage();
                Reporter.report(this, Reporter.Msg.DISENGAGING);
                engaged = false;
            }
        }
    }
}