package com.kazurayam.diffutil.text;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import java.util.List;
import java.util.stream.Collectors;

public class DiffInfo {
    private final List<DiffRow> rows;
    private final List<DiffRow> insertedRows;
    private final List<DiffRow> deletedRows;
    private final List<DiffRow> changedRows;
    private final List<DiffRow> equalRows;

    private static final String TAG_INSERTED_COLOR = "#e6ffec";
    private static final String TAG_DELETED_COLOR  = "#ffeef0";
    private static final String TAG_CHANGED_COLOR  = "#dbedff";

    DiffInfo(List<String> original, List<String> revised) {
        // compute the difference between the two inputs
        DiffRowGenerator generator =
                DiffRowGenerator.create()
                        .showInlineDiffs(true)
                        .inlineDiffByWord(true)
                        .oldTag(f -> f ? String.format("<span style=\"color:red; font-weight:bold; background-color:%s\">", TAG_DELETED_COLOR) : "</span>")
                        .newTag(f -> f ? String.format("<span style=\"color:green; font-weight:bold; background-color:%s\">", TAG_INSERTED_COLOR) : "</span>")
                        .build();
        rows = generator.generateDiffRows(original, revised);
        insertedRows = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.INSERT).collect(Collectors.toList());
        deletedRows  = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.DELETE).collect(Collectors.toList());
        changedRows  = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.CHANGE).collect(Collectors.toList());
        equalRows    = rows.stream().filter(diffRow -> diffRow.getTag() == DiffRow.Tag.EQUAL).collect(Collectors.toList());
    }

    List<DiffRow> getRows() {
        return rows;
    }

    List<DiffRow> getInsertedRows() {
        return insertedRows;
    }

    List<DiffRow> getDeletedRows() {
        return deletedRows;
    }

    List<DiffRow> getChangedRows() {
        return changedRows;
    }

    List<DiffRow> getEqualRows() {
        return equalRows;
    }

    boolean hasDifference() {
        return this.getEqualRows().size() < this.getRows().size();
    }
}
