package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IIdentifiable;


/**
 * OmiObject with base implementation of the IIdentifiable interface.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class IdentifiableOmiObject extends OmiObject implements IIdentifiable {

    /**
     * Give every instance a unique ID.
     * Id refers to unique real world object (default empty string)
     * The IDescribable reserves room on every business object to provide some
     * textual documentation (description) for it and a caption. The caption is
     * the screen name for a business object. This should not be used as an ID
     * or anything meaningful, it is only for user convenience and subject to
     * localization.
     */
    private Identifier identifier;


    public static IdentifiableOmiObject newInstance(String id, String caption,
                                                    String description) {
        IdentifiableOmiObject result = new IdentifiableOmiObject(id, caption, description);
        return result;
    }


    public static IdentifiableOmiObject newInstanceWithRandomId(String caption, String description) {
        IdentifiableOmiObject result = new IdentifiableOmiObject(caption, description);
        return result;
    }


    /**
     * Creates an instance.
     */
    public IdentifiableOmiObject() {
        super();
        identifier = Identifier.newRandomUuid("", "");
    }


    public IdentifiableOmiObject(String caption, String description) {
        super();
        identifier = Identifier.newRandomUuid(caption, description);
    }


    public IdentifiableOmiObject(String id, String caption, String description) {
        super();
        identifier = Identifier.newInstance(id, caption, description);
    }


    public Identifier getIdentifier() {
        return identifier;
    }


    /**
     * Gets the ID of the object.
     *
     * @return String id
     */
    public String getId() {
        return identifier.getId();
    }


    /**
     * Sets the ID of the instance to the specified value. Changing the ID is
     * not encouraged!
     *
     * @param id String ID to set
     */
    public void setId(String id) {
        if (id == null) {
            id = "";
        }

        if (!id.equals(getId())) {
            identifier.setId(id);
            sendObjectChangedNotification(identifier);
        }
    }


    /**
     * Gets the description of the object. This is like an extended caption,
     * but might not be editable all the time by the user.
     *
     * @return String Description of the object
     */
    public String getDescription() {
        return identifier.getDescription();
    }


    /**
     * Sets the description of the object. This is like an extended caption,
     * but might not be editable all the time by the user.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }

        if (!description.equals(identifier.getDescription())) {
            identifier.setDescription(description);
            sendObjectChangedNotification(identifier);
        }
    }


    /**
     * Gets the caption of the object. The caption is typically used for
     * display in an user interface, and the user might be allowed to change
     * it at will. So it is best to not rely on it to have a specific value.
     *
     * @return String The caption
     */
    public String getCaption() {
        return identifier.getCaption();
    }


    /**
     * Sets the caption of the object.
     *
     * @param caption The new caption
     */
    public void setCaption(String caption) {
        if (caption == null) {
            caption = "";
        }

        // update description if it is an exact match to the caption
        if (!caption.equals(identifier.getCaption())) {
            if (identifier.getDescription().equals(identifier.getCaption())) {
                identifier.setDescription(caption);
            }
            identifier.setCaption(caption);
            sendObjectChangedNotification(identifier);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifiableOmiObject)) return false;

        IdentifiableOmiObject that = (IdentifiableOmiObject) o;

        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "IdentifiableOmiObject{" +
                "identifier=" + identifier +
                '}';
    }
}
