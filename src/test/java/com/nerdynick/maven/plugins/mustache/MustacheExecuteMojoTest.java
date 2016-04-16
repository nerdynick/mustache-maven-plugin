package com.nerdynick.maven.plugins.mustache;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nerdynick.maven.plugins.mustache.MustacheExecuteMojo;

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
		
		//Check Scan
		File scanDir = getTestFile("target/test-harness/scan");
		Path scanDirPath = scanDir.toPath();
		
		assertTrue(scanDir.exists());
		assertTrue(scanDir.isDirectory());
		assertEquals(5, scanDir.listFiles().length);
		
		//Check Scan Files
		File temp1 = scanDirPath.resolve("mustache_template.txt").toFile();
		File temp2 = scanDirPath.resolve("mustache_template_2.txt").toFile();
		
		assertTrue(temp1.exists());
		assertTrue(temp1.isFile());
		assertTrue(temp2.exists());
		assertTrue(temp2.isFile());
		
		//Check Scan Files Multi
		Path multiDir = scanDirPath.resolve("multi");
		temp1 = multiDir.resolve("mustache_template.txt").toFile();
		temp2 = multiDir.resolve("mustache_template_2.txt").toFile();
		File temp3 = multiDir.resolve("mustache_template_3.txt").toFile();
		File temp4 = multiDir.resolve("mustache_template_4.txt").toFile();
		
		assertEquals(4, multiDir.toFile().listFiles().length);
		assertTrue(temp1.exists());
		assertTrue(temp1.isFile());
		assertTrue(temp2.exists());
		assertTrue(temp2.isFile());
		assertTrue(temp3.exists());
		assertTrue(temp3.isFile());
		assertTrue(temp4.exists());
		assertTrue(temp4.isFile());
		
		//Check Scan Files with Exclude
		temp1 = scanDirPath.resolve("exclude").resolve("mustache_template.txt").toFile();
		temp2 = scanDirPath.resolve("exclude").resolve("mustache_template_2.txt").toFile();
		
		assertTrue(temp1.exists());
		assertTrue(temp1.isFile());
		assertTrue(!temp2.exists());
		
		//Check Scan Files with Exclude
		temp1 = scanDirPath.resolve("include").resolve("mustache_template.txt").toFile();
		temp2 = scanDirPath.resolve("include").resolve("mustache_template_2.txt").toFile();
		
		assertTrue(!temp1.exists());
		assertTrue(temp2.exists());
		assertTrue(temp2.isFile());
		
	}

}
