package fr.softsf.sudofx2024.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class(es), method(s) or constructor(s), annotated with @ExcludedFromCoverageReportGenerated should be excluded from the coverage
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, CONSTRUCTOR})
public @interface ExcludedFromCoverageReportGenerated {
}
