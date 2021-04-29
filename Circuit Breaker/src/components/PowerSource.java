package components;

/**
 * The class is extended by component for the power source of the component
 *
 * Author: Dhruv Rajpurohit
 */
public class PowerSource extends Component{

    /**
     * The function is the constructor for power source
     * @param name the of the source
     */
    public PowerSource(String name) {
        super(name, null);
        Reporter.report(this, Reporter.Msg.CREATING);
    }

    /**
     * Engages the power source
     */
    public void engage() {
        Reporter.report(this, Reporter.Msg.ENGAGING);
        engaged = true;
        for(Component part:loads){
            Reporter.report(part, Reporter.Msg.ENGAGING);
            part.engaged = true;
        }
    }
}