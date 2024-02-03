package com.kazurayam.diffutil.text;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiffInfo {
    private String pathOriginal = null;
    private String pathRevised = null;
    private final List<DiffRow> rows;
    private final List<DiffRow> insertedRows;
    private final List<DiffRow> deletedRows;
    private final List<DiffRow> changedRows;
    private final List<DiffRow> equalRows;

    private static final String TAG_INSERTED_COLOR = "#e6ffec";
    private static final String TAG_DELETED_COLOR  = "#ffeef0";
    private static final String TAG_CHANGED_COLOR  = "#dbedff";

    public String getPathOriginal() { return pathOriginal; }
    public String getPathRevised() { return pathRevised; }
    public List<DiffRow> getRows() { return rows; }
    public List<DiffRow> getInsertedRows() { return insertedRows; }
    public List<DiffRow> getDeletedRows() { return deletedRows; }
    public List<DiffRow> getChangedRows() { return changedRows; }
    public List<DiffRow> getEqualRows() { return equalRows; }

    public boolean hasDifference() {
        return this.getEqualRows().size() < this.getRows().size();
    }

    private DiffInfo(String pathOriginal,
                     String pathRevised,
                     List<DiffRow> rows,
                     List<DiffRow> insertedRows,
                     List<DiffRow> deletedRows,
                     List<DiffRow> changedRows,
                     List<DiffRow> equalRows) {
        this.pathOriginal = pathOriginal;
        this.pathRevised = pathRevised;
        this.rows = rows;
        this.insertedRows = insertedRows;
        this.deletedRows = deletedRows;
        this.changedRows = changedRows;
        this.equalRows = equalRows;
    }

    public static class Builder {
        private String pathOriginal = ".";
        private String pathRevised = ".";
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
            return new DiffInfo(pathOriginal, pathRevised,
                    rows, insertedRows, deletedRows, changedRows, equalRows);
        }
    }
}
