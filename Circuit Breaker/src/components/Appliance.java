package components;

/**
 * The class is extended by the component class
 *
 * Author: Dhruv Rajpurohit
 */

public class Appliance extends Component{
    public int rating;

    /**
     * Constructor for the class
     * @param name name of the component
     * @param source source
     * @param rating rating of the appliance
     */
    public Appliance(String name, Component source, int rating) {
        super(name, source);
        Reporter.report(this, Reporter.Msg.CREATING);
        attach();
        this.rating = rating;
    }

    /**
     * Attach the appliance
     */
    public void attach(){
        Reporter.report(source, this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }

    /**
     * Accessor of the rating
     * @return
     */
    public int getRating(){
        return this.rating;
    }

    /**
     * Turns on the appliance
     */
    public void turnOn(){
        engaged = true;
        Reporter.report(this, Reporter.Msg.SWITCHING_ON);
        source.changeDraw(rating);
    }

    /**
     * Turns off the appliance
     */
    public void turnOff(){
        engaged = false;
        Reporter.report(this, Reporter.Msg.SWITCHING_OFF);
        if(engaged) {
            source.changeDraw(rating * -1);
        }
    }

    /**
     * Changes the draw of the appliance
     * @param rating the rating of the appliance
     */
    public void changeDraw(int rating){
        draw += rating;
        Reporter.report(this, Reporter.Msg.DRAW_CHANGE, rating);
        System.out.println(toString() +  " changing draw by " + rating);
        source.changeDraw(rating);
    }

    /**
     * Checks if the switch is on or not
     * @return
     */
    public boolean isSwitchOn(){
        if (engaged){
            return true;
        }
        else {
            return false;
        }
    }

}