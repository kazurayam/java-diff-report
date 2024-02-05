package com.kazurayam.difflib.text;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ReporterSupportTest {

    private static Logger logger = LoggerFactory.getLogger(ReporterSupportTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(ReporterSupportTest.class)
                    .subDirPath(ReporterSupportTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("text1.txt");
    private static final Path text2 = fixturesDir.resolve("text2.txt");
    private static DiffInfo diffInfo;

    @BeforeAll
    public static void beforeAll() throws IOException {
        diffInfo = new DiffInfo.Builder(text1, text2).build();
    }

    @Test
    public void testCompileStats() throws JsonProcessingException {
        String stats = ReporterSupport.compileStats(diffInfo);
        logger.debug("[testCompileStatsJson]\n" + stats);
        // check if the report string is a valid JSON syntactically
        ObjectMapper mapper = new ObjectMapper();
        mapper.readTree(stats);
        assertThat(stats)
                .contains("rows")
                .contains("isDifferent")
                .contains("insertedRows")
                .contains("deletedRows")
                .contains("changedRows")
                .contains("equalRows");
    }

    @Test
    public void test_shortenPathString() {
        String path1 = ReporterSupport.shortenPathString(text1.toString());
        logger.debug("[test_shortenPathString] path1=" + path1);
        assertThat(path1)
                .startsWith("~/")
                .endsWith("/text1.txt");
    }
}
