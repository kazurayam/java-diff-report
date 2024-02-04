package com.github.difflib.text;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.PatchFailedException;
import com.kazurayam.unittest.TestOutputOrganizer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GeneratingUnifiedDiffTest {
    private static Logger logger = LoggerFactory.getLogger(GeneratingUnifiedDiffTest.class);

    private static final TestOutputOrganizer too =
            new TestOutputOrganizer.Builder(GeneratingUnifiedDiffTest.class)
                    .outputDirPath("build/tmp/testOutput")
                    .subDirPath(GeneratingUnifiedDiffTest.class)
                    .build();
    private static final Path fixturesDir = too.getProjectDir().resolve("src/test/fixtures");
    private static final Path text1 = fixturesDir.resolve("left.html");
    private static final Path text2 = fixturesDir.resolve("right.html");

    @Test
    public void testGeneratingUnified() throws IOException {
        List<String> origLines = Files.readAllLines(text1);
        List<String> revLines = Files.readAllLines(text2);
        verify(origLines, revLines, "left.html", "right.html");
    }

    private void verify(List<String> origLines, List<String> revLines,
                        String originalFile, String revisedFile) {
        Patch<String> patch = DiffUtils.diff(origLines, revLines);
        List<String> unifiedDiff = UnifiedDiffUtils.generateUnifiedDiff(originalFile, revisedFile,
                origLines, patch, 10);

        logger.debug("[verify] " + unifiedDiff.stream().collect(joining("\n")));

        Patch<String> fromUnifiedPath = UnifiedDiffUtils.parseUnifiedDiff(unifiedDiff);
        List<String> patchedLines;
        try {
            patchedLines = fromUnifiedPath.applyTo(origLines);
            assertEquals(revLines.size(), patchedLines.size());
            for (int i = 0; i < revLines.size(); i++) {
                String l1 = revLines.get(i);
                String l2 = patchedLines.get(i);
                if (!l1.equals(l2)) {
                    fail("Line " + (i + 1) + " of the patched file did not match the revised original");
                }
            }
        } catch (PatchFailedException e) {
            fail(e.getMessage());
        }
    }
}
