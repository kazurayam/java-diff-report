package com.kazurayam.difflib.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
public final class Differ {

    private Differ() {}

    public static DiffInfo diffFiles(File file1, File file2) throws IOException {
        return diffFiles(file1.toPath(), file2.toPath());
    }

    public static DiffInfo diffFiles(Path file1, Path file2) throws IOException {
        Objects.requireNonNull(file1);
        if (!Files.exists(file1)) {
            throw new FileNotFoundException("file1(" + file1 + ") is not present");
        }
        Objects.requireNonNull(file2);
        if (!Files.exists(file2)) {
            throw new FileNotFoundException("file2(" + file2 + ") is not present");
        }
        // read all lines of the two text files to generate the diff information
        return new DiffInfo.Builder(file1, file2).build();
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
