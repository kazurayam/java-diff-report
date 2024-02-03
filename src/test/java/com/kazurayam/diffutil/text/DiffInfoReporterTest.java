package com.kazurayam.diffutil.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffInfoReporterTest {

    private static Logger logger = LoggerFactory.getLogger(DiffInfoReporterTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DiffInfoReporterTest.class)
                    .subDirPath(DiffInfoReporterTest.class)
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
    public void testCompileJsonReport() {
        String report = DiffInfoReporter.compileStatsJson(diffInfo);
        logger.info("[testCompileJsonReport]\n" + report);
        assertThat(report)
                .contains("rows")
                .contains("isDifferent")
                .contains("insertedRows")
                .contains("deletedRows")
                .contains("changedRows")
                .contains("equalRows");
    }

    @Test
    public void testCompileMarkdownReport() {
        String report = DiffInfoReporter.compileMarkdownReport(diffInfo);
        logger.info("[testCompileMarkdownReport]\n" + report);
        assertThat(report)
                .contains("**DIFFERENT**")
                .contains("|row#|S|original|revised|")
                .contains("|-----|-|--------|-------|");
    }
}
