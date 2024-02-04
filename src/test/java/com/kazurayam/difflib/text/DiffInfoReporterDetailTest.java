package com.kazurayam.difflib.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffInfoReporterDetailTest {

    private static Logger logger = LoggerFactory.getLogger(DiffInfoReporterDetailTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DiffInfoReporterDetailTest.class)
                    .subDirPath(DiffInfoReporterDetailTest.class)
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
    public void test_mdDetail_full() {
        String report = DiffInfoReporter.mdDetail(diffInfo, false);
        logger.debug("[test_mdDetail_full]\n" + report);
    }

    @Test
    public void test_mdDetail_compact() {
        String report = DiffInfoReporter.mdDetail(diffInfo, true);
        logger.debug("[test_mdDetail_compact]\n" + report);
    }

}
