package nl.wur.alterra.openmi.sdk2.backbone;

/**
 * OpenMI SDK runtime exception.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
public class OmiException extends RuntimeException {

    private final static String MSG_METHOD_NOT_IMPLEMENTED = "Method not Implemented: %s:%s";
    private final static String MSG_METHOD_NOT_OVERWRITTEN = "Method not Overwritten: %s:%s";


    public static OmiException newNotImplementedException(String className, String methodName) {
        return new OmiException(MSG_METHOD_NOT_IMPLEMENTED, className, methodName);
    }


    public static OmiException newNotOverwrittenException(String className, String methodName) {
        return new OmiException(MSG_METHOD_NOT_OVERWRITTEN, className, methodName);
    }


    public OmiException(String msg) {
        super(msg);
    }


    public OmiException(String format, Object... args) {
        super(String.format(format, args));
    }


    public OmiException(String msg, Throwable throwable) {
        super(msg, throwable);
    }


}
