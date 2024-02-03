package com.kazurayam.diffutil.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private Path original;
    private Path revised;
    private Path output;

    public Main() {}

    public void setOriginal(Path original) throws IOException {
        if (!Files.exists(original)) {
            throw new FileNotFoundException(original.toString() +
                    " is not found");
        }
        this.original = original;
    }

    public void setRevised(Path revised) throws IOException {
        if (!Files.exists(revised)) {
            throw new FileNotFoundException(revised.toString() +
                    " is not found");
        }
        this.revised = revised;
    }

    public void setOutput(Path output) throws IOException {
        Files.createDirectories(output.getParent());
        this.output = output;
    }

    public void execute() throws IOException {
        DiffInfo diffInfo = Differ.diffFiles(original, revised);
        Files.writeString(output,
                DiffInfoReporter.compileMarkdownReport(diffInfo));
        System.out.println(DiffInfoReporter.compileStatsJson(diffInfo));
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: Main <original file path> <revised file path> <output file path>");
            System.exit(-1);
        }
        Main instance = new Main();
        instance.setOriginal(Paths.get(args[0]));
        instance.setRevised(Paths.get(args[1]));
        instance.setOutput(Paths.get(args[2]));
        instance.execute();
    }
}
