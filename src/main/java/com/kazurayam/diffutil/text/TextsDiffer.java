package com.kazurayam.diffutil.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

    private ReportFormat reportFormat = ReportFormat.MARKDOWN;

    public TextsDiffer() {}

    public TextsDiffer(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }
    public final String diffFiles(File file1, File file2, File output)
            throws IOException {
        return diffFiles(file1.toPath(), file2.toPath(), output.toPath());
    }

    public final String diffFiles(Path file1, Path file2, Path output)
            throws IOException {
        return this.diffFiles(Paths.get("."), file1, file2, output);
    }

    public final String diffFiles(Path baseDirectory, Path text1, Path text2, Path output)
            throws IOException {
        Path baseDir = baseDirectory.toAbsolutePath();
        Path t1 = baseDir.resolve(text1).toAbsolutePath();
        Path t2 = baseDir.resolve(text2).toAbsolutePath();
        validateInputs(baseDir, t1, t2);

        // read all lines of the two text files
        List<String> original = Files.readAllLines(t1);
        List<String> revised = Files.readAllLines(t2);
        DiffInfo diffInfo = new DiffInfo(original, revised);

        // compile a report
        if (reportFormat == ReportFormat.MARKDOWN) {
            StringBuilder sb = new StringBuilder();
            throw new RuntimeException("TODO");
        } else {
            throw new UnsupportedOperationException("output in HTML is yet TO BE supported");
        }
    }

    private void validateInputs(Path baseDir, Path text1, Path text2) throws FileNotFoundException {
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
