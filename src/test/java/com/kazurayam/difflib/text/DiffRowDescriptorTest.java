package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
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
    private static List<DiffRowDescriptor> listNDR;

    @BeforeAll
    public static void beforeAll() throws IOException {
        diffInfo = new DiffInfo.Builder(text1, text2).build();
        diffRows = diffInfo.getRows();
        listNDR = new ArrayList<>();
        for (int i = 0; i < diffRows.size(); i++) {
            listNDR.add(new DiffRowDescriptor(i + 1, diffRows.get(i)));
        }
        assertThat(listNDR).isNotEmpty();
    }

    @Test
    public void test_getNumber() {
        DiffRowDescriptor ndr = listNDR.get(0);
        assertThat(ndr.getNumber()).isEqualTo(1);
    }

    @Test
    public void test_getDiffRow() {
        DiffRowDescriptor ndr = listNDR.get(listNDR.size() - 1);
        assertThat(ndr.getDiffRow().getTag()).isEqualTo(DiffRow.Tag.EQUAL);
    }
}
