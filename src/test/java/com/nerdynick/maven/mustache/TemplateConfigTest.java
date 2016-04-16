package com.nerdynick.maven.mustache;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.maven.model.FileSet;
import org.junit.Test;

import com.google.common.io.Files;

public class TemplateConfigTest {

	@Test
	public void testGetTemplates_DirScanWithExclude() throws IOException {
		File outputDir = Files.createTempDir();
		outputDir.deleteOnExit();
		Path outputDirPath = outputDir.toPath();
		
		File inputDir = Files.createTempDir();
		inputDir.deleteOnExit();
		Path inputDirPath = inputDir.toPath();
		
		File inputFile1 = inputDirPath.resolve("tempfile_1.tmp").toFile();
		inputFile1.createNewFile();
		inputFile1.deleteOnExit();
		File inputFile2 = inputDirPath.resolve("tempfile_2.tmp").toFile();
		inputFile2.createNewFile();
		inputFile2.deleteOnExit();
		
		FileSet inputSet = new FileSet();
		inputSet.setDirectory(inputDir.getAbsolutePath());
		inputSet.addExclude("tempfile_2.+");
		
		TemplateConfig config = new TemplateConfig();
		config.setInputDir(inputSet);
		config.setOutputDir(outputDir);
		
		List<TemplateConfig.Template> templates = config.getTemplates();
		
		assertEquals(1, templates.size());
		assertEquals(inputFile1, templates.get(0).input);
		assertEquals(outputDirPath.resolve(inputFile1.getName()).toFile(), templates.get(0).output);
	}
	
	@Test
	public void testGetTemplates_DirScanWithInclude() throws IOException {
		File outputDir = Files.createTempDir();
		outputDir.deleteOnExit();
		Path outputDirPath = outputDir.toPath();
		
		File inputDir = Files.createTempDir();
		inputDir.deleteOnExit();
		Path inputDirPath = inputDir.toPath();
		
		File inputFile1 = inputDirPath.resolve("tempfile_1.tmp").toFile();
		inputFile1.createNewFile();
		inputFile1.deleteOnExit();
		File inputFile2 = inputDirPath.resolve("tempfile_2.tmp").toFile();
		inputFile2.createNewFile();
		inputFile2.deleteOnExit();
		
		FileSet inputSet = new FileSet();
		inputSet.setDirectory(inputDir.getAbsolutePath());
		inputSet.addInclude("tempfile_1.+");
		
		TemplateConfig config = new TemplateConfig();
		config.setInputDir(inputSet);
		config.setOutputDir(outputDir);
		
		List<TemplateConfig.Template> templates = config.getTemplates();
		
		assertEquals(1, templates.size());
		assertEquals(inputFile1, templates.get(0).input);
		assertEquals(outputDirPath.resolve(inputFile1.getName()).toFile(), templates.get(0).output);
	}

}
