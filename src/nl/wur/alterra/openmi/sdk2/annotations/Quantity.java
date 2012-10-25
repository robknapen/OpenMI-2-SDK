package nl.wur.alterra.openmi.sdk2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Quantity metadata.
 *
 * @author Rob Knapen; Alterra, Wageningen UR, The Netherlands (2011)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Quantity {

    String unit();

    String id() default "";

    double siFactor() default 1.0;

    double siOffset() default 0.0;

    String description() default "";

    String missingValue() default "";

    double min() default Double.MIN_VALUE;

    double max() default Double.MAX_VALUE;

}
