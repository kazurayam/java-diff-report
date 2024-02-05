package com.kazurayam.difflib.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

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

    public void compileMarkdownReport() throws IOException {
        DiffInfo diffInfo = Differ.diffFiles(original, revised);
        DiffInfoReporter reporter = new DiffInfoReporter.Builder(diffInfo).build();
        Files.writeString(output, reporter.compileMarkdownReport());
        System.out.println(reporter.compileStatsJson());
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: java -jar java-diff-report.jar " +
                    "<original file path> <revised file path> <output file path> [markdown|html]");
            System.exit(-1);
        }
        String format = "markdown";
        if (args.length >= 4) {
            format = args[3];
        }
        Main instance = new Main();
        instance.setOriginal(Paths.get(args[0]));
        instance.setRevised(Paths.get(args[1]));
        instance.setOutput(Paths.get(args[2]));

        if (format.equals("markdown")) {
            instance.compileMarkdownReport();
        } else if (format.equals("html")) {
            System.err.println("report in HTML is yet to be supported");
        } else {
            System.err.printf("report format %s is not supported%n", format);
        }
    }
}
