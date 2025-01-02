package data.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The Bind annotation is used to mark fields within a class for automatic
 * binding of data. The fields annotated with Bind are typically part of a
 * data model and can be dynamically assigned or utilized within other data
 * processing mechanisms like file reading or script execution.
 * <p>
 * This annotation is effective at runtime and can only be applied to fields.
 * It allows the linkage between external data sources or scripts and their
 * corresponding fields in a model class.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface Bind {
}
