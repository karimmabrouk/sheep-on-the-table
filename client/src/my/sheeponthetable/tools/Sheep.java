package my.sheeponthetable.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Sheep class implements the program-internal representation of a sheep.
 * 
 * @author Filip
 */
public class Sheep {

    private int id;
    private int farmId;
    private String name;
    private String comment;
    private long born;
    private long deceased;
    private double weight;
    private List<SheepUpdate> updates;

    /**
     * Creates a new instance of the class sheep by specifying the information
     * about the sheep.
     */
    public Sheep(int id, int farmId, String name, long born, long deceased, String comment, List<SheepUpdate> updates, double weight) {
        this.id = id;
        this.farmId = farmId;
        this.name = name;
        this.comment = comment;
        this.born = born;
        this.deceased = deceased;
        this.weight = weight;
        this.updates = updates;
    }

    /**
     * Get the ID field.
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the FarmID field.
     */
    public int getFarmId() {
        return farmId;
    }

    /**
     * Gets the name of the sheep.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the comment field.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the unix timestamp of when sheep was born.
     */
    public Date getBorn() {
        Date formattedBorn = new Date(born);
        return formattedBorn;
    }

    /**
     * Gets the weight field.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the unix timestamp of when sheep was deceased.
     */
    public Date getDeceased() {
        Date formattedDeceased = new Date(deceased);
        return formattedDeceased;
    }

    /**
     * Gets the list of updates associated with this sheep.
     */
    public List<SheepUpdate> getUpdates() {
        return updates;
    }

    /**
     * Sets the id field.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Sets the farm ID field
     */
    public void setFarmID(int id) {
        farmId = id;
    }

    /**
     * Sets the name field.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the comment field.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the born date of the sheep. Make sure that the sheep is not given a
     * value that is older than it's deceased field, otherwise it will be 
     * registered as dead.
     */
    public void setBorn(Date date) {
        born = date.getTime();
    }

    /**
     * Sets the weight of the sheep
     */
    public void setWeight(double w) {
        this.weight = w;
    }

    /**
     * Set the deceased value of the sheep. If the sheep is to be registered as
     * alive, set the deceased value to "1st Jan 1970 00:00:00"
     */
    public void setDeceased(Date date) {
        deceased = date.getTime();
    }

    /**
     * Sets the SheepUpdate list in the sheep object. The program depends in 
     * many places on the fact that this list is sorted by timestamp, with the 
     * newest update having the lowest index. Therefore: Make sure that the 
     * list is sorted before calling this method.
     */
    public void setUpdates(List<SheepUpdate> updates) {
        this.updates = updates;
    }

    /**
     * Store an additional sheep update in the sheep's update list. This method
     * should only be called if it can be guaranteed that the new update has a
     * newer timestamp than the previous newest update, thus making sure that 
     * the update list remains sorted.
     * 
     * If this cannot be guaranteed, use getUpdates(), add the update to the 
     * list, sort the list, and then use setUpdates()
     */
    public void addUpdate(SheepUpdate su) {
        if (updates == null) {
            updates = new ArrayList<>();
        }
        updates.add(su);
    }

    /**
     * Returns true if the sheep is dead. In terms of data specifications, this 
     * is when the deceased time is set to be after the born time. Living sheep
     * should normally have their deceased field set to "1st Jan 1970 00:00:00"
     */
    public Boolean isAlive() {
        if (deceased <= born) {
            return true;
        }
        return false;
    }
}
