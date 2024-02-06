package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DiffInfo {
    private String title;
    private String pathOriginal;
    private String pathRevised;
    private final List<DiffRow> rows;
    private final List<DiffRow> insertedRows;
    private final List<DiffRow> deletedRows;
    private final List<DiffRow> changedRows;
    private final List<DiffRow> equalRows;

    private static final String TAG_INSERTED_COLOR = "#e6ffec";
    private static final String TAG_DELETED_COLOR  = "#ffeef0";
    private static final String TAG_CHANGED_COLOR  = "#dbedff";

    public void setTitle(String title) {
        Objects.requireNonNull(title);
        this.title = title;
    }
    public String getTitle() { return title; }

    public void setPathOriginal(String pathOriginal) {
        Objects.requireNonNull(pathOriginal);
        this.pathOriginal = pathOriginal;
    }
    public String getPathOriginal() { return pathOriginal; }

    public void setPathRevised(String pathRevised) {
        Objects.requireNonNull(pathRevised);
        this.pathRevised = pathRevised;
    }
    public String getPathRevised() { return pathRevised; }

    public List<DiffRow> getRows() { return rows; }
    public List<DiffRow> getInsertedRows() { return insertedRows; }
    public List<DiffRow> getDeletedRows() { return deletedRows; }
    public List<DiffRow> getChangedRows() { return changedRows; }
    public List<DiffRow> getEqualRows() { return equalRows; }

    public boolean hasDifference() {
        return this.getEqualRows().size() < this.getRows().size();
    }

    private DiffInfo(String title,
                     String pathOriginal,
                     String pathRevised,
                     List<DiffRow> rows,
                     List<DiffRow> insertedRows,
                     List<DiffRow> deletedRows,
                     List<DiffRow> changedRows,
                     List<DiffRow> equalRows) {
        this.title = title;
        this.pathOriginal = pathOriginal;
        this.pathRevised = pathRevised;
        this.rows = rows;
        this.insertedRows = insertedRows;
        this.deletedRows = deletedRows;
        this.changedRows = changedRows;
        this.equalRows = equalRows;
    }

    public static class Builder {
        private String title = null;
        private String pathOriginal = null;
        private String pathRevised = null;
        private final List<String> originalLines;
        private final List<String> revisedLines;

        Builder(Path original, Path revised) throws IOException {
            this.pathOriginal = original.toString();
            this.pathRevised = revised.toString();
            this.originalLines = Files.readAllLines(original);
            this.revisedLines = Files.readAllLines(revised);
        }

        Builder(List<String> originalLines, List<String> revisedLines) {
            this.originalLines = originalLines;
            this.revisedLines = revisedLines;
        }

        Builder(InputStream is1, InputStream is2) {
            Reader rdr1 = new InputStreamReader(is1, StandardCharsets.UTF_8);
            this.originalLines = readAllLines(rdr1);
            Reader rdr2 = new InputStreamReader(is2, StandardCharsets.UTF_8);
            this.revisedLines = readAllLines(rdr2);
        }

        Builder(Reader rdr1, Reader rdr2) {
            this.originalLines = readAllLines(rdr1);
            this.revisedLines = readAllLines(rdr2);
        }
        Builder title(String title) {
            Objects.requireNonNull(title);
            this.title = title;
            return this;
        }

        Builder pathOriginal(String pathOriginal) {
            Objects.requireNonNull(pathOriginal);
            this.pathOriginal = pathOriginal;
            return this;
        }

        Builder pathRevised(String pathRevised) {
            Objects.requireNonNull(pathRevised);
            this.pathRevised = pathRevised;
            return this;
        }

        private List<String> readAllLines(Reader reader) {
            return new BufferedReader(reader).lines().collect(Collectors.toList());
        }

        public DiffInfo build() {
            // compute the difference between the two inputs
            DiffRowGenerator generator =
                    DiffRowGenerator.create()
                            .showInlineDiffs(true)
                            .inlineDiffByWord(true)
                            .oldTag(f -> f ? String.format("<span style=\"color:red; font-weight:bold; background-color:%s\">", TAG_DELETED_COLOR) : "</span>")
                            .newTag(f -> f ? String.format("<span style=\"color:green; font-weight:bold; background-color:%s\">", TAG_INSERTED_COLOR) : "</span>")
                            .build();
            List<DiffRow> rows = generator.generateDiffRows(this.originalLines, this.revisedLines);
            List<DiffRow> insertedRows = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.INSERT).collect(Collectors.toList());
            List<DiffRow> deletedRows  = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.DELETE).collect(Collectors.toList());
            List<DiffRow> changedRows  = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.CHANGE).collect(Collectors.toList());
            List<DiffRow> equalRows    = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.EQUAL).collect(Collectors.toList());
            return new DiffInfo(title, pathOriginal, pathRevised,
                    rows, insertedRows, deletedRows, changedRows, equalRows);
        }
    }
}
