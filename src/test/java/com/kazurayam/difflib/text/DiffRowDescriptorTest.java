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

public class DiffRowDescriptorTest {

    private static Logger logger = LoggerFactory.getLogger(DiffRowDescriptorTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DiffRowDescriptorTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(DiffRowDescriptorTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("text1.txt");
    private static final Path text2 = fixturesDir.resolve("text2.txt");
    private static DiffInfo diffInfo;
    private static List<DiffRow> diffRows;

    @BeforeAll
    public static void beforeAll() throws IOException {
        diffInfo = new DiffInfo.Builder(text1, text2).build();
        diffRows = diffInfo.getRows();
    }

    @Test
    public void test_getSequence_first() {
        int index = 0;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.getSequence()).isEqualTo(index + 1);
    }

    @Test
    public void test_getSequence_last() {
        int index = diffRows.size() - 1;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.getSequence()).isEqualTo(index + 1);
    }

    @Test
    public void test_getTag() {
        int index = 0;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.getTag()).isEqualTo(DiffRow.Tag.EQUAL);
    }

    @Test
    public void test_isTaggedEqual() {
        int index = 0;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.isTaggedEqual()).isTrue();
    }

    @Test
    public void test_isTaggedInsertedDeletedChanged() {
        int index = 1;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.isTaggedInsertedDeletedChanged()).isTrue();
    }

    @Test
    public void test_findDiffRow() {
        int index = 1;
        DiffRowDescriptor drd = new DiffRowDescriptor(index, diffRows.get(index));
        assertThat(drd.getSequence()).isEqualTo(2);
        DiffRow dr = drd.findDiffRowIn(diffRows);
        assertThat(dr.getTag()).isEqualTo(DiffRow.Tag.INSERT);
    }
}
