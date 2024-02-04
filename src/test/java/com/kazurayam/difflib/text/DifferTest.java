package com.kazurayam.difflib.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class DifferTest {

    private static Logger logger = LoggerFactory.getLogger(DifferTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DifferTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(DifferTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");

    @Test
    public void test_diffFiles_premiere() throws IOException {
        too.cleanMethodOutputDirectory("test_diffFiles_premiere");
        Path output = too.getMethodOutputDirectory("test_diffFiles_premiere")
                .resolve("diffReport.md");
        DiffInfo diffInfo = Differ.diffFiles(text1, text2);
        logger.debug("[test_diffFiles_premiere] " + DiffInfoReporter.compileStatsJson(diffInfo));
    }

    @Test
    public void test_diffString_statsOnly() throws IOException {
        String original = String.join("\n", Files.readAllLines(text1));
        String revised  = String.join("\n", Files.readAllLines(text2));
        DiffInfo diffInfo = Differ.diffStrings(original, revised);
        String stats = DiffInfoReporter.compileStatsJson(diffInfo);
        logger.debug("[test_diffStrings_writeFile] " + stats);
        assertThat(stats).contains("equalRows");
    }

    @Test
    public void test_diffStrings_writeFile() throws IOException {
        too.cleanMethodOutputDirectory("test_diffStrings_writeFile");
        String original = String.join("\n", Files.readAllLines(text1));
        String revised  = String.join("\n", Files.readAllLines(text2));
        Path output = too.getMethodOutputDirectory("test_diffStrings")
                .resolve("report.md");
        DiffInfo diffInfo = Differ.diffStrings(original, revised);
        String stats = DiffInfoReporter.compileStatsJson(diffInfo);
        logger.debug("[test_diffStrings_writeFile] " + stats);
        Files.writeString(output, DiffInfoReporter.compileMarkdownReport(diffInfo));
        assertThat(output).exists();
    }

    @Test
    public void test_diffURLs() throws IOException, URISyntaxException {
        String methodName = "test_diffURLs";
        too.cleanMethodOutputDirectory(methodName);
        URL original = new URI("http://myadmin.kazurayam.com/").toURL();
        URL revised  = new URI("http://devadmin.kazurayam.com/").toURL();
        Path output = too.getMethodOutputDirectory(methodName)
                .resolve("report.md");
        DiffInfo diffInfo = Differ.diffURLs(original, revised);
        String stats = DiffInfoReporter.compileStatsJson(diffInfo);
        logger.debug("[" + methodName + "] " + stats);
        Files.writeString(output, DiffInfoReporter.compileMarkdownReport(diffInfo));
        assertThat(output).exists();

    }

}
