package com.kazurayam.diffutil.text;

import com.kazurayam.unittest.ProjectDirectoryResolver;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class DiffStatsTest {

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(DiffStatsTest.class)
                    .subDirPath(DiffStatsTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");

    @Test
    public void testText1location() {
        DiffStats ds = new DiffStats.Builder().text1location(text1.toString()).build();
        assertThat(ds.getText1location()).isEqualTo(text1.toString());
    }

    @Test
    public void testText2location() {
        DiffStats ds = new DiffStats.Builder().text2location(text2.toString()).build();
        assertThat(ds.getText2location()).isEqualTo(text2.toString());
    }

    @Test
    public void testRows() {
        DiffStats ds = new DiffStats.Builder().rows(999).build();
        assertThat(ds.getRows()).isEqualTo(999);
    }

    @Test
    public void testInsertedRows() {
        DiffStats ds = new DiffStats.Builder().insertedRows(11).build();
        assertThat(ds.getInsertedRows()).isEqualTo(11);
    }

    @Test
    public void testDeletedRows() {
        DiffStats ds = new DiffStats.Builder().deletedRows(2).build();
        assertThat(ds.getDeletedRows()).isEqualTo(2);
    }

    @Test
    public void testChangedRows() {
        DiffStats ds = new DiffStats.Builder().changedRows(7).build();
        assertThat(ds.getChangedRows()).isEqualTo(7);
    }

    @Test
    public void testEqualRows() {
        DiffStats ds = new DiffStats.Builder().equalRows(789).build();
        assertThat(ds.getEqualRows()).isEqualTo(789);
    }
}
