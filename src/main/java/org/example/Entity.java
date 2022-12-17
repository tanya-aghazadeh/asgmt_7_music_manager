package org.example;

import java.util.Date;

/**
 * Entity Class
 *
 * @author Tanya Aghazadeh
 */
public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * This function checks if two entities are equal.
     *
     * @param otherEntity
     *
     * @return true/false
     */
    public boolean equals(Entity otherEntity) {
        return entityID == otherEntity.entityID;
    }

    /**
     * This function gets the DataCreated for an Entity.
     *
     * @return dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * This function sets the Data Created for an Entity.
     *
     * @param dateCreated
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * This function gets the Name for an Entity.
     *
     * @returns name
     */
    public String getName() {
        return name;
    }

    /**
     * This function sets name for an Entity.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function converts an Entity to string.
     *
     * @return the string which has Entity info
     */
    public String toString() {
        return "Name: " + this.name + " Entity ID: " + this.entityID;
    }

    /**
     * This function generates html for an Entity.
     *
     * @return string
     */
    public String toHTML() {
        return "<b>" + this.name + "</b><i> " + this.entityID + "</i>";
    }

    /**
     * This function generate xml for an Entity.
     *
     * @return string
     */
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }
}
