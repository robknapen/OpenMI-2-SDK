package nl.wur.alterra.openmi.sdk2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Model argument (set at initialisation).
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Arg {
    String value() default "";
}
