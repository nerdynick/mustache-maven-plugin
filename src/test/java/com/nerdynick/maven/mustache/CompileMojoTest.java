package com.nerdynick.maven.mustache;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompileMojoTest extends AbstractMojoTestCase {

	@BeforeClass
	public void setup() throws Exception {
		super.setUp();
	}

	@Test
	public void test() throws Exception {
		File pom = new File(this.getClass().getClassLoader().getResource("pom.xml").toURI());
		System.out.println(pom);
		assertNotNull(pom);
		assertTrue(pom.exists());
		
		CompileMojo mojo = (CompileMojo)this.lookupMojo("compile", pom);
		mojo.execute();
	}

}
