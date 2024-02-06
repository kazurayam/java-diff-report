package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DRDSegmentTest {

    private static Logger logger = LoggerFactory.getLogger(DRDSegmentTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DRDSegmentTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(DRDSegmentTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("text1.txt");
    private static final Path text2 = fixturesDir.resolve("text2.txt");
    private static DRDSegment segment;

    @BeforeAll
    public static void beforeAll() throws IOException {
        DiffInfo diffInfo = new DiffInfo.Builder(text1, text2).build();
        List<DiffRow> diffRows = diffInfo.getRows();
        List<DiffRowDescriptor> listDRD = ReporterSupport.toDiffRowDescriptorList(diffRows);
        segment = new DRDSegment();
        segment.addAll(listDRD);
    }

    @Test
    public void test_getFirst() {
        DiffRowDescriptor drd = segment.getFirst();
        assertThat(drd.getSequence()).isEqualTo(1);
    }

    @Test
    public void test_getLast() {
        DiffRowDescriptor drd = segment.getLast();
        assertThat(drd.getSequence()).isEqualTo(segment.size());
    }

}
