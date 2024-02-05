/**
 * <p>
 * This module extends the capability of the <a href="https://github.com/java-diff-utils/java-diff-utils">java-diff-utils</a> library.
 * </p>
 * <p>Sample usage</p>
 * <pre>{@code
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
 *
 *   public static void main(String[] args) throws Exception {
 *     Path original = Paths.get("./src/test/fixtures/text1.txt");
 *     Path revised = Paths.get("./src/test/fixtures/text2.txt");
 *     Path output = Paths.get("./build/tmp/sample-report.md");
 *     Files.createDirectories(output.getParent());
 *
 *     DiffInfo diffInfo = Differ.diffFiles(original, revised);
 *     Files.writeString(output,
 *         DiffInfoReporter.compileMarkdownReport(diffInfo));
 *     System.out.println(DiffInfoReporter.compileStatsJson(diffInfo));
 *   }
 * }
 * }</pre>
 *
 * @since 0.1.0
 * @author kazurayam
 * @version 0.1.0-SNAPSHOT
 */
package com.kazurayam.difflib.text;