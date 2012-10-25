package nl.wur.alterra.openmi.sdk2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * General model information.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Model {

    // TODO: define annotation elements according to OpenMI Standard XSD files?

    String author() default "";

    String description() default "";

    String keywords() default "";

    String sourceInfo() default "";

    String versionInfo() default "";

    String license() default "";

    Status status() default Status.TEST;

}
