/**
 * <p>
 * This module extends the capability of the <a href="https://github.com/java-diff-utils/java-diff-utils">java-diff-utils</a> library.
 * </p>
 * <p>Sample usage</p>
 * <pre>
 * package com.kazurayam.sample;
 *
 * import com.kazurayam.difflib.text.Differ;
 * import com.kazurayam.difflib.text.DiffInfo;
 * import com.kazurayam.difflib.text.DiffInfoReporter;
 *
 * import java.nio.file.Files;
 * import java.nio.file.Path;
 * import java.nio.file.Paths;
 *
 * public class App {
 *     public static void main(String[] args) throws Exception {
 *         Path original = Paths.get("./src/test/fixtures/text1.txt");
 *         Path revised = Paths.get("./src/test/fixtures/text2.txt");
 *         Path output = Paths.get("./build/temp/report.md");
 *         Files.createDirectories(output.getParent());
 *
 *         DiffInfo diffInfo = Differ.diffFiles(original, revised);
 *         diffInfo.setTitle("Sample diff report of 2 text files");
 *         MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).build();
 *         Files.writeString(output, reporter.compileMarkdownReport());
 *         System.out.println(reporter.compileStats());
 *     }
 * }</pre>
 *
 * @since 0.1.0
 * @author kazurayam
 * @version 0.1.0-SNAPSHOT
 */
package com.kazurayam.difflib.text;