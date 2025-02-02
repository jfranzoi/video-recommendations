package my.projects.videorecommendations.tests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@ActiveProfiles({"test"})
@DirtiesContext
public @interface ComponentTest {
    @AliasFor(
            annotation = SpringBootTest.class,
            attribute = "webEnvironment"
    )
    SpringBootTest.WebEnvironment webEnvironment() default SpringBootTest.WebEnvironment.MOCK;

    @AliasFor(
            annotation = SpringBootTest.class,
            attribute = "properties"
    )
    String[] properties() default {};

    @AliasFor(
            annotation = DirtiesContext.class,
            attribute = "classMode"
    )
    DirtiesContext.ClassMode classMode() default DirtiesContext.ClassMode.AFTER_CLASS;
}
