package com.kazurayam.diffutil.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Compare 2 texts to create a report which shows the diff of the 2 inputs.
 * The input text can be an instance of various types:
 * java.nio.file.Path, java.io.File, java.lang.String, java.net.URL,
 * java.io.Reader and java.io.InputStream.
 * The output report is compiled in Markdown formats.
 * To view a report in Markdown, you would require an appropriate viewer,
 * for example, VSCode with Markdown extension
 * https://code.visualstudio.com/Docs/languages/markdown
 * date: Feb 2024
 * @author kazurayam
 */
public class TextsDiffer {

    private TextsDiffer() {}

    public static String diffFiles(Path file1, Path file2, Path output)
            throws IOException {
        return diffFiles(Paths.get("."), file1, file2, output,
                DiffInfoReporter.ReportFormat.MARKDOWN);
    }

    public static String diffFiles(Path file1, Path file2, Path output,
                                  DiffInfoReporter.ReportFormat reportFormat) throws IOException {
        return diffFiles(Paths.get("."), file1, file2, output,
                reportFormat);
    }

    public static String diffFiles(Path baseDir, Path text1, Path text2, Path output)
            throws IOException {
        return diffFiles(baseDir, text1, text2, output, DiffInfoReporter.ReportFormat.MARKDOWN);
    }

    public static String diffFiles(
            Path baseDirectory, Path text1, Path text2, Path output,
            DiffInfoReporter.ReportFormat reportFormat) throws IOException {
        Path baseDir = baseDirectory.toAbsolutePath();
        Path t1 = baseDir.resolve(text1).toAbsolutePath();
        Path t2 = baseDir.resolve(text2).toAbsolutePath();
        validateInputs(baseDir, t1, t2);

        // read all lines of the two text files to generate the diff information
        DiffInfo diffInfo = new DiffInfo.Builder(t1, t2).build();

        // compile a report
        if (reportFormat == DiffInfoReporter.ReportFormat.MARKDOWN) {
            StringBuilder sb = new StringBuilder();
            throw new RuntimeException("TODO");
        } else {
            throw new UnsupportedOperationException(
                    "output in HTML is yet TO BE supported");
        }
    }

    private static void validateInputs(Path baseDir, Path text1, Path text2)
            throws FileNotFoundException {
        if (baseDir == null) {
            throw new IllegalArgumentException("baseDir must not be null");
        }
        if (!Files.exists(baseDir)) {
            throw new FileNotFoundException("basedir(" + baseDir + ") is not present");
        }
        Objects.requireNonNull(text1);
        if (!Files.exists(text1)) {
            throw new FileNotFoundException("text1(" + text1 + ") is not present");
        }
        Objects.requireNonNull(text2);
        if (!Files.exists(text2)) {
            throw new FileNotFoundException("text2(" + text2 + ") is not present");
        }
    }
}
