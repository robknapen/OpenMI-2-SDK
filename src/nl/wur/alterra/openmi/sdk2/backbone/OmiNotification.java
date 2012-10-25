package nl.wur.alterra.openmi.sdk2.backbone;

import org.openmi.standard2.IIdentifiable;

import java.text.DateFormat;
import java.util.Date;


/**
 * Class for exchanging notification details from observable to observer.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class OmiNotification {

    public enum Type {ADDED, DELETED, MODIFIED, OTHER}


    private Object sender;
    private Object variable;
    private Type changeType;
    private String message;


    public static OmiNotification newAddNotification(Object sender, Object variable, boolean addMessage) {
        String msg = addMessage ? createMessage(sender, variable, OmiNotification.Type.ADDED) :
                null;
        return new OmiNotification(sender, variable, OmiNotification.Type.ADDED, msg);
    }


    public static OmiNotification newDeleteNotification(Object sender, Object variable,
                                                        boolean addMessage) {
        String msg = addMessage ? createMessage(sender, variable, OmiNotification.Type.DELETED) :
                null;
        return new OmiNotification(sender, variable, OmiNotification.Type.DELETED, msg);
    }


    public static OmiNotification newModifiedNotification(Object sender, Object variable,
                                                          boolean addMessage) {
        String msg = addMessage ? createMessage(sender, variable, OmiNotification.Type.MODIFIED) :
                null;
        return new OmiNotification(sender, variable, OmiNotification.Type.MODIFIED, msg);
    }


    public static OmiNotification newOtherNotification(Object sender, Object variable,
                                                       boolean addMessage) {
        String msg = addMessage ? createMessage(sender, variable, OmiNotification.Type.OTHER) :
                null;
        return new OmiNotification(sender, variable, OmiNotification.Type.OTHER, msg);
    }


    private static String createMessage(Object sender, Object variable, Type changeType) {
        StringBuilder sb = new StringBuilder();

        // timestamp
        sb.append(DateFormat.getTimeInstance().format(new Date()));
        sb.append(": ");
        sb.append(sender.getClass().getSimpleName());

        // add sender info
        if (sender instanceof IIdentifiable) {
            sb.append("{");
            sb.append(((IIdentifiable) sender).getId());
            sb.append(",");
            sb.append(((IIdentifiable) sender).getCaption());
            sb.append("}");
        }

        // type of change
        switch (changeType) {
            case ADDED:
                sb.append(": Added object ");
                break;
            case DELETED:
                sb.append(": Deleted object ");
                break;
            case MODIFIED:
                sb.append(": Changed object ");
                break;
            case OTHER:
                sb.append(": Notifies about object ");
                break;
        }
        sb.append(variable.getClass().getSimpleName() + ": " + variable);

        return sb.toString();
    }


    public OmiNotification(Object sender, Object variable, Type changeType, String message) {
        this.sender = sender;
        this.variable = variable;
        this.changeType = changeType;
        this.message = message;
    }


    public Object getSender() {
        return sender;
    }


    public void setSender(Object sender) {
        this.sender = sender;
    }


    public Object getVariable() {
        return variable;
    }


    public void setVariable(Object variable) {
        this.variable = variable;
    }


    public Type getChangeType() {
        return changeType;
    }


    public void setChangeType(Type changeType) {
        this.changeType = changeType;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public boolean hasMessage() {
        return (message != null) && (message.length() > 0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OmiNotification)) return false;

        OmiNotification that = (OmiNotification) o;

        if (changeType != that.changeType) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (variable != null ? !variable.equals(that.variable) : that.variable != null)
            return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (variable != null ? variable.hashCode() : 0);
        result = 31 * result + (changeType != null ? changeType.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "OmiNotification{" +
                "sender=" + sender +
                ", variable=" + variable +
                ", changeType=" + changeType +
                ", message='" + message + '\'' +
                '}';
    }
}
