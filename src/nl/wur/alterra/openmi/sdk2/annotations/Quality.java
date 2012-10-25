package nl.wur.alterra.openmi.sdk2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Quality metadata.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Quality {

    String categories();

    String id() default "";

    String description() default "";

    boolean isOrdered() default false;

    String missingValue() default "";

}
