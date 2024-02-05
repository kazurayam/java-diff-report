package com.kazurayam.difflib.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MarkdownReporterTest {

    private static Logger logger = LoggerFactory.getLogger(MarkdownReporterTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(MarkdownReporterTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(MarkdownReporterTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");
    private static DiffInfo diffInfo;

    @BeforeAll
    public static void beforeAll() throws IOException {
        diffInfo = new DiffInfo.Builder(text1, text2).build();
    }

    @Test
    public void test_mdDetail_full() {
        String report = MarkdownReporter.mdDetail(diffInfo, false);
        logger.debug("[test_mdDetail_full]\n" + report);
    }

    @Test
    public void test_mdDetail_compact() {
        String report = MarkdownReporter.mdDetail(diffInfo, true);
        logger.debug("[test_mdDetail_compact]\n" + report);
    }

    @Test
    public void testCompact() {
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).compact(false).build();
        assertThat(reporter.getCompact()).isFalse();
    }

    @Test
    public void testCompileMarkdownReport() throws IOException {
        String methodName = "testCompileMarkdownReport";
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo)
                .title("Sample diff report of 2 HTML files")
                .pathOriginal(text1.toString())
                .pathRevised(text2.toString())
                .build();

        String report = reporter.compileMarkdownReport();
        logger.debug(String.format("[%s]\n%s", methodName, report));
        assertThat(report)
                .contains("- original")
                .contains("- revised")
                .contains("**DIFFERENT**")
                .contains("|row#|S|original|revised|")
                .contains("|----|-|--------|-------|");
        too.cleanMethodOutputDirectory(methodName);
        Path output = too.getMethodOutputDirectory(methodName).resolve("output.md");
        Files.writeString(output, report);
    }

    @Test
    public void test_title() {
        String title = "γνῶθι σεαυτόν";
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).title(title).build();
        String report = reporter.compileMarkdownReport();
        assertThat(report).contains(String.format("## %s", title));
    }
}
