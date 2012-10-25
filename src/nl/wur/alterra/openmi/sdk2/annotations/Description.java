package nl.wur.alterra.openmi.sdk2.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Descriptive information.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
    String value();
}
