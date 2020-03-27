package com.nerdynick.maven.plugins.mustache;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.io.Files;
import com.nerdynick.maven.plugins.template.AbstractTemplateMojo;
import com.nerdynick.maven.plugins.template.Template;
import com.nerdynick.maven.plugins.template.TemplateConfig;

@Mojo(name="mustache-execute", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
public class MustacheExecuteMojo extends AbstractTemplateMojo {
	private DefaultMustacheFactory mf = new DefaultMustacheFactory();

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		final Charset charset = this.getCharset();
		
		final List<Object> scope = new ArrayList<>();
		if(props != null){
			scope.add(props);
		}
		scope.add(getProjectProps());
		scope.add(getProjectInfoProps());
		
		getLog().info("Mustache Scope: "+ scope);
		
		for(TemplateConfig t: templates){
			try {
				List<Template> temps = t.getTemplates();
				for(Template temp: temps){
					getLog().info("Compiling '"+ temp.input +"' to '"+ temp.output +"'");
					final Mustache mustache = createTemplate(temp.input, charset);
					final Writer writer = Files.newWriter(temp.output, charset);
					mustache.execute(writer, scope).close();
				}
			} catch (IOException e) {
				throw new MojoFailureException("Failed to process Template", e);
			}
		}
	}
	
	private Mustache createTemplate(File template, Charset charset) throws IOException {
        Reader reader = Files.newReader(template, charset);
        return mf.compile(reader, "template");
    }
}
