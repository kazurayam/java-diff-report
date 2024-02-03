package com.kazurayam.diffutil.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(MainTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(MainTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir()
            .resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");

    @BeforeAll
    public static void beforeAll() throws IOException {
        too.cleanClassOutputDirectory();
    }

    @Test
    public void test_main() throws IOException {
        Path output = too.getMethodOutputDirectory("test_main")
                .resolve("output.md");
        String[] args = {
                text1.toString(),
                text2.toString(),
                output.toString()
        };
        Main.main(args);
        assertThat(output).exists();
    }

}
