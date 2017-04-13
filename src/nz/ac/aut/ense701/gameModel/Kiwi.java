
package nz.ac.aut.ense701.gameModel;

/**
 * Kiwi represents a kiwi living on the island
 * @author AS
 * @version July 2011
 */
public class Kiwi  extends Item
{
    private static final double KIWI_WEIGHT = 3.0;
    
    private boolean safe;
    /**
     * Constructor for objects of class Kiwi
     * @param pos the position of the kiwi object
     * @param name the name of the kiwi object
     * @param description a longer description of the kiwi
     */
    public Kiwi(Position pos, String name, String description) 
    {
        super(pos, name, description, KIWI_WEIGHT);
        safe = false;
    } 

    /**
     * Set the safe status of a kiwi
     * @param safe 
     */
    public void setSafe(boolean safe){
        this.safe = safe;
    }
    
    /**
     * Is this Kiwi safe
     * @return 
     */
    public boolean saved() {
        return safe;
    }

    @Override
    public String getStringRepresentation() 
    {
        return "K";
    }     
}
