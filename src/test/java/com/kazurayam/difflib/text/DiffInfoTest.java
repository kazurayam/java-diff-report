package com.kazurayam.difflib.text;

import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffInfoTest {

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DiffInfoTest.class)
                    .subDirPath(DiffInfoTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");


    @Test
    public void testGetRows() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getRows().size()).isEqualTo(73);
    }

    @Test
    public void testGetInsertedRows() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getInsertedRows().size()).isEqualTo(3);
    }

    @Test
    public void testGetDeletedRows() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getDeletedRows().size()).isEqualTo(1);
    }

    @Test
    public void testGetChangedRows() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getChangedRows().size()).isEqualTo(24);
    }

    @Test
    public void testGetEqualRows() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getEqualRows().size()).isEqualTo(45);
    }

    @Test
    public void testHasDifference() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.hasDifference()).isTrue();
    }

    @Test
    public void test_title() throws IOException {
        String title1 = "sample diff";
        DiffInfo di = new DiffInfo.Builder(text1, text2).title(title1).build();
        assertThat(di.getTitle()).isEqualTo(title1);
        String title2 = "another title";
        di.setTitle(title2);
        assertThat(di.getTitle()).isEqualTo(title2);
    }

    @Test
    public void test_pathOriginal_Path() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getPathOriginal()).isEqualTo(text1.toString());
    }

    @Test
    public void test_pathOriginal_String() throws IOException {
        String so = "original string";
        String sr = "revised string";
        String po = "ORIGINAL";
        String pox = "CHANGED";
        DiffInfo di = new DiffInfo.Builder(new StringReader(so), new StringReader(sr))
                .pathOriginal(po).build();
        assertThat(di.getPathOriginal()).isEqualTo(po);
        di.setPathOriginal(pox);
        assertThat(di.getPathOriginal()).isEqualTo(pox);
    }

    @Test
    public void test_pathRevised() throws IOException {
        DiffInfo di = new DiffInfo.Builder(text1, text2).build();
        assertThat(di.getPathRevised()).isEqualTo(text2.toString());
    }

    @Test
    public void test_pathRevised_String() throws IOException {
        String so = "original string";
        String sr = "revised string";
        String pr = "REVISED";
        String prx = "CHANGED";
        DiffInfo di = new DiffInfo.Builder(new StringReader(so), new StringReader(sr))
                .pathRevised(pr).build();
        assertThat(di.getPathRevised()).isEqualTo(pr);
        di.setPathRevised(prx);
        assertThat(di.getPathRevised()).isEqualTo(prx);
    }

}
