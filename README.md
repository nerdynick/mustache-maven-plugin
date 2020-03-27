# mustache-maven-plugin


Maven Plugin for compiling Mustache.js templates using Maven Properties for variables

[![Build Status](https://api.travis-ci.org/nerdynick/mustache-maven-plugin.png "Build Status")](https://travis-ci.org/nerdynick/mustache-maven-plugin)

## Features

* Scan a directory of template files and compile
  - Supports optional Includes and Excludes via RegEx pattern match
    - Includes and Excludes can be used together. 
    - Includes will take presidence over excludes so that you may include files that may match an exclude pattern.
  - Filenames are preserved on the output
* Execute on a single file or multiple files
  - Optionally rename the file on the output for single files
* Compile multiple combinations of the above
* Most all directive support by the Mustache.java framework are supported
  - Imports are not supported
* Maven project information imported as variables
  - All properties defined in <property> are added by default
    - All . in property names will be replaced with -
  - Project details are imported
    - ArtifactId - {{project-artifactId}}
    - GroupId - {{project-groupId}}
    - Name - {{project-name}}
    - Description - {{project-description}}

# Usage

**Basic Plugin Setup**

```xml

<build>
    <plugins>
        <plugin>
            <groupId>com.nerdynick</groupId>
            <artifactId>mustache-maven-plugin</artifactId>
            <version>${version}</version>
            <configuration>
                <templates>.</templates>
            </configuration>
        </plugin>
    </plugins>
</build>
```

**Template Setups**

```xml

<templates>

    <!-- Single File -->
    
    <param>
        <outputDir>target/test-harness</outputDir>
        <inputFile>src/test/resources/mustache_test.txt</inputFile>
    </param>
    
    <!-- Single File with name change -->
    
    <param>
        <outputDir>target/test-harness</outputDir>
        <outputName>mustache.txt</outputName>
        <inputFile>src/test/resources/mustache_test.txt</inputFile>
    </param>
    
    <!-- Multiple File -->
    
    <param>
        <outputDir>target/test-harness</outputDir>
        <inputFiles>
            <parma>src/test/resources/mustache_test.txt</param>
            <parma>src/test/resources/mustache_test_2.txt</param>
        </inputFiles>
    </param>
    
    <!-- Directory Scan -->
    
    <param>
        <outputDir>target/test-harness/scan</outputDir>
        <inputDir>
            <directory>src/test/resources/templates</directory>
        </inputDir>
    </param>
    
    <!-- Multiple Directory Scan -->
    
    <param>
        <outputDir>target/test-harness/scan</outputDir>
        <inputDirs>
            <param>
                <directory>src/test/resources/templates</directory>
            </param>
            <param>
                <directory>src/test/resources/other_templates</directory>
            </param>
        </inputDirs>
    </param>
    
    <!-- Directory Scan with Include -->
    
    <param>
        <outputDir>target/test-harness/scan/include</outputDir>
        <inputDir>
            <directory>src/test/resources/templates</directory>
            <includes>
                <param>.+_2.+</param>
            </includes>
        </inputDir>
    </param>
    
    <!-- Directory Scan with Exclude -->
    
    <param>
        <outputDir>target/test-harness/scan/exclude</outputDir>
        <inputDir>
            <directory>src/test/resources/templates</directory>
            <excludes>
                <param>.+_2.+</param>
            </excludes>
        </inputDir>
    </param>
    
    <!-- Combine a number of rules for 1 output dir. -->
    
    <param>
        <outputDir>target/test-harness/scan/exclude</outputDir>
        <outputName>mustache.txt</outputName>
        <inputFile>src/test/resources/mustache_test.txt</inputFile>
        <inputFiles>
            <parma>src/test/resources/mustache_test.txt</param>
            <parma>src/test/resources/mustache_test_2.txt</param>
        </inputFiles>
        <inputDir>
            <directory>src/test/resources/templates</directory>
            <excludes>
                <param>.+_2.+</param>
            </excludes>
        </inputDir>
        <inputDirs>
            <param>
                <directory>src/test/resources/templates</directory>
                <excludes>
                    <param>.+_2.+</param>
                </excludes>
            </param>
            <param>
                <directory>src/test/resources/templates</directory>
                <excludes>
                    <param>.+_2.+</param>
                </excludes>
            </param>
        </inputDirs>
    </param>
</templates>
```
