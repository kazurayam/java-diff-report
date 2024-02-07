package com.kazurayam.sample;

import com.kazurayam.difflib.text.Differ;
import com.kazurayam.difflib.text.DiffInfo;
import com.kazurayam.difflib.text.MarkdownReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>takes diff of 2 text files to generate a diff report in Markdown,
 * write the result into a file. The input texts are rather short,
 * hence the output result will be concise as well.</p>
 */
public class App {

    private App() {}

    public static void main(String[] args) throws Exception {
        Path original = Paths.get("./src/test/fixtures/text1.txt");
        Path revised = Paths.get("./src/test/fixtures/text2.txt");
        Path output = Paths.get("./build/tmp/report.md");
        Files.createDirectories(output.getParent());

        DiffInfo diffInfo = Differ.diffFiles(original, revised);
        diffInfo.setTitle("Sample diff report of 2 text files");
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).build();
        Files.writeString(output, reporter.compileMarkdownReport());
        System.out.println(reporter.compileStats());
    }
}
