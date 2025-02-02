package my.projects.videorecommendations.tests;

import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ComponentTest(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD, properties = {
        "application.data.folder=src/test/resources/data/empty"
})
public @interface PersistenceTest {
}
