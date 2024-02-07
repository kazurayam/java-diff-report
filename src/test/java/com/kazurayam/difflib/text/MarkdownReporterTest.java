package com.kazurayam.difflib.text;

import com.kazurayam.subprocessj.CommandLocator;
import com.kazurayam.subprocessj.Subprocess;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

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
    private DiffInfo diffInfo;

    @BeforeEach
    public void beforeEach() throws IOException {
        diffInfo = new DiffInfo.Builder(text1, text2).build();
    }

    @Test
    public void test_mdDetail_full() {
        String report = MarkdownReporter.mdDetail(diffInfo, false, 0);
        logger.debug("[test_mdDetail_full]\n" + report);
    }

    @Test
    public void test_mdDetail_compact_margin0() {
        String report = MarkdownReporter.mdDetail(diffInfo, true, 0);
        logger.debug("[test_mdDetail_compact_margine0]\n" + report);
    }

    @Test
    public void test_mdDetail_compact_too_large_margin() {
        try {
            String report = MarkdownReporter.mdDetail(diffInfo, true, 999);
            fail("test_mdDetail_compact_too_large_margine was expected to fail, but actually not");
        } catch (IllegalArgumentException e) {
            logger.debug("[test_mdDetail_compact_too_large_margin] failed as expected");
        }
    }

    @Test
    public void testCompact() {
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).compact(false).build();
        assertThat(reporter.getCompact()).isFalse();
    }

    @Test
    public void testCompileMarkdownReport_Paths_as_input() throws IOException {
        String methodName = "testCompileMarkdownReport_Paths_as_input";
        String title = "Sample diff report of 2 HTML files";
        diffInfo.setTitle(title);
        MarkdownReporter reporter =
                new MarkdownReporter.Builder(diffInfo)
                        .margin(3).build();

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
    public void testCompileMarkdownReport_Strings_as_input() throws IOException {
        String methodName = "testCompileMarkdownReport_Strings_as_input";
        String string1 = "AAA\nBBB\nCCC\nDDD\nEEE\nFFF\nGGG\nHHH\nIII\nJJJ\nKKK\nLLL\nMMM\nNNN\n";
        String string2 = "AAA\nXXX\nBBB\nDDD\nEEE\nFFF\nGGG\nHHH\nIII\nJJJ\nKKK\nLLL\nmmm\nNNN\n";
        String title = "Sample diff report of 2 strings";
        DiffInfo di =
                new DiffInfo.Builder(new StringReader(string1), new StringReader(string2))
                        .title(title)
                        .pathOriginal("you can write any explanatory text here")
                        .pathRevised("and any fancy text here")
                        .build();
        MarkdownReporter reporter =
                new MarkdownReporter.Builder(di)
                        .margin(1).build();

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
        diffInfo.setTitle(title);
        MarkdownReporter reporter = new MarkdownReporter.Builder(diffInfo).build();
        String report = reporter.compileMarkdownReport();
        assertThat(report).contains(String.format("## %s", title));
    }
}
