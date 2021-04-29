package components;

/**
 * The class extends the component class to implement the outlet
 *
 * Author: Dhruv Rajpurohit
 */
public class Outlet extends Component{

    /**
     * The function is a constructor of the outlet class
     * @param name the name of the component
     * @param source the source of power
     */
    public Outlet(String name, Component source) {
        super(name, source);
        attach();
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * The function attaches the circuit to the outlet
     */
    public void attach(){
        Reporter.report(this.source,this, Reporter.Msg.ATTACHING);
        source.attach(this);
    }



}