- Table of contents
{:toc}

# java-diff-report

## What is this for

The java-diff-report is a small Java libary which aims to supplement the [java-diff-utils](https://github.com/java-diff-utils/java-diff-utils) library. The java-diff-report accepts 2 input texts (as Files, Paths, Strings, URLs) and call the java-diff-utils to generate the Diff. The java-diff-report accepts the output Diff info from the java-diff-utils libary and compiles a report in Markdown format.

## How to get the jar

The jar is available at

-   [Maven Central](https://mvnrepository.com/artifact/com.kazurayam/java-diff-report)

-   [Releases page](https://github.com/kazurayam/java-diff-report/releases) of the project’s GitHub repository

## API Javadoc

-   [javadoc](https://kazurayam.github.io/java-diff-report/api/)

## Sample usage

    package com.kazurayam.sample;

    import com.kazurayam.difflib.text.Differ;
    import com.kazurayam.difflib.text.DiffInfo;
    import com.kazurayam.difflib.text.DiffInfoReporter;

    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;

    public class App {
        public static void main(String[] args) throws Exception {
            Path original = Paths.get("./src/test/fixtures/text1.txt");
            Path revised = Paths.get("./src/test/fixtures/text2.txt");
            Path output = Paths.get("./build/temp/report.md");
            Files.createDirectories(output.getParent());

            DiffInfo diffInfo = Differ.diffFiles(original, revised);
            Files.writeString(output,
                    DiffInfoReporter.compileMarkdownReport(diffInfo));
            System.out.println(DiffInfoReporter.compileStatsJson(diffInfo));
        }
    }

Output:

-   <https://kazurayam.github.io/java-diff-report/sample-report.md>