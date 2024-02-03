package com.kazurayam.diffutil.text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class Differ {

    private Differ() {}

    public static DiffInfo diffFiles(Path file1, Path file2) throws IOException {
        return diffFiles(Paths.get("."), file1, file2);
    }

    public static DiffInfo diffFiles(Path baseDirectory, Path text1, Path text2)
            throws IOException {
        Path baseDir = baseDirectory.toAbsolutePath();
        Path t1 = baseDir.resolve(text1).toAbsolutePath();
        Path t2 = baseDir.resolve(text2).toAbsolutePath();
        validateInputs(baseDir, t1, t2);
        // read all lines of the two text files to generate the diff information
        return new DiffInfo.Builder(t1, t2).build();
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

    public static DiffInfo diffStrings(String text1, String text2) {
        Objects.requireNonNull(text1);
        Objects.requireNonNull(text2);
        List<String> original = readAllLines(new StringReader(text1));
        List<String> revised  = readAllLines(new StringReader(text2));
        return new DiffInfo.Builder(original, revised).build();
    }

    /*
     * FIXME: Proxy is not taken into account
     */
    public static DiffInfo diffURLs(URL url1, URL url2) throws IOException {
        InputStream is1 = url1.openStream();
        InputStream is2 = url2.openStream();
        return new DiffInfo.Builder(is1, is2).build();
    }

    private static List<String> readAllLines(Reader reader) {
        return new BufferedReader(reader).lines().collect(Collectors.toList());
    }

}
