package com.svashishtha.eclipsetomaven.pom;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PomDependencyCreatorImplTest {
    String pathAttribute;
    PomDependencyCreatorImpl pomDependencyCreator;

    @Before
    public void setup() {
        pomDependencyCreator = new PomDependencyCreatorImpl(null);

    }

    @Test
    @Ignore
    // Wont fix
    public void testCreatePomDependency_1() {
        pathAttribute = "/Component Repository/lib/com/oracle/ojdbc6/11.2.0.2.0/ojdbc6.zip";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("ojdbc6", pomDependency.getArtifactId());
        assertEquals("11.2.0.2.0", pomDependency.getJarVersion());
    }

    @Test
    @Ignore
    // Wont fix
    public void testCreatePomDependency_2() {
        pathAttribute = "lib/framework-5.0.2-java11.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("framework-java11", pomDependency.getArtifactId());
        assertEquals("5.0.2", pomDependency.getJarVersion());
    }

    @Test
    @Ignore
    // Wont fix
    public void testCreatePomDependency_4() {
        pathAttribute = "/Legal DB Web App/jakarta-oro-2.0.8-ols.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("2.0.8", pomDependency.getJarVersion());
        assertEquals("jakarta-oro-ols", pomDependency.getArtifactId());
    }

    @Test
    public void testCreatePomDependency_3() {
        pathAttribute = "/projecta/lib/test-framework-6.0.5-SNAPSHOT.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("test-framework", pomDependency.getArtifactId());
        assertEquals("6.0.5-SNAPSHOT", pomDependency.getJarVersion());
    }

    
    @Test
    public void testCreatePomDependency_5() {
        pathAttribute = "/Component Repository/lib/au/com/abc/testframework/2.3.0/depend/jce-jdk13-124.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("jce-jdk13", pomDependency.getArtifactId());
        assertEquals("124", pomDependency.getJarVersion());
    }
    
    
    @Test
    public void testCreatePomDependencyLog4j() {
        pathAttribute = "C:/ProjectsRAD7.5/HEAD/ExternalConnectivity/lib/log4j-1.2.15.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("log4j", pomDependency.getArtifactId());
        assertEquals("1.2.15", pomDependency.getJarVersion());
    }

    @Test
    public void testCreatePomDependencySl4j() {
        pathAttribute = "/Component Repository/lib/au/com/bankwest/testframework/2.3.0/depend/slf4j-api-1.5.0.jar";
        PomDependency pomDependency = pomDependencyCreator.createPomDependency(
                pathAttribute, null, null);
        assertEquals("slf4j-api", pomDependency.getArtifactId());
        assertEquals("1.5.0", pomDependency.getJarVersion());
    }

}
