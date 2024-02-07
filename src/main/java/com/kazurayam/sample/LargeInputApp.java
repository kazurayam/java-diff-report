package com.kazurayam.sample;

import com.kazurayam.difflib.text.Differ;
import com.kazurayam.difflib.text.DiffInfo;
import com.kazurayam.difflib.text.MarkdownReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URI;
import java.net.URL;

/**
 * <p>takes diff of 2 text files to generate a diff report in Markdown,
 * write the result into a file. The input texts are rather short,
 * hence the output result will be concise as well.</p>
 */
public class LargeInputApp {

    private void LargeInputApp() {}

    public static void main(String[] args) throws Exception {
        URL original = new URI("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css").toURL();
        URL revised = new URI("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css").toURL();
        Path output = Paths.get("./build/tmp/largeInputDiff.md");
        Files.createDirectories(output.getParent());

        DiffInfo diffInfo = Differ.diffURLs(original, revised);
        diffInfo.setTitle("bootstrap-icon.css, 2 versions comparison");
        diffInfo.setPathOriginal(original.toExternalForm());
        diffInfo.setPathRevised(revised.toExternalForm());
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).build();
        Files.writeString(output, reporter.compileMarkdownReport());
        System.out.println(reporter.compileStats());
    }
}
