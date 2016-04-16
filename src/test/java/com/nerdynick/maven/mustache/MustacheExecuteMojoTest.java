package com.nerdynick.maven.mustache;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MustacheExecuteMojoTest extends AbstractMojoTestCase {

	@BeforeClass
	public void setup() throws Exception {
		super.setUp();
	}
	
	@AfterClass
	public void teardown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test() throws Exception {
		File pom = new File(this.getClass().getClassLoader().getResource("test-pom.xml").toURI());
		assertNotNull(pom);
		assertTrue(pom.exists());
		
		MustacheExecuteMojo mojo = (MustacheExecuteMojo)this.lookupMojo("mustache-execute", pom);
		mojo.execute();
		
		File output = getTestFile("target/test-harness/mustache_test.txt");
		List<String> lines = Files.readAllLines(output.toPath());
		
		assertNotNull(lines);
		assertEquals(2, lines.size());
		assertEquals("mustache-maven-plugin com.nerdynick.maven Mustache Maven Plugin Mustache Maven Plugin Desc", lines.get(0));
		assertEquals("my-test-prop", lines.get(1));
	}

}
