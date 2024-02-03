package com.kazurayam.diffutil.text;

import com.github.difflib.text.DiffRow;

import java.util.List;
import java.util.Objects;

public class DiffInfoReporter {

    private DiffInfoReporter() {}

    public static String compileStatsJson(DiffInfo diffInfo) {
        Objects.requireNonNull(diffInfo);
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(String.format("    \"rows\": %d\n", diffInfo.getRows().size()));
        sb.append(String.format("    \"isDifferent\": %b\n",  diffInfo.hasDifference()));
        sb.append(String.format("    \"insertedRows\": %d\n", diffInfo.getInsertedRows().size()));
        sb.append(String.format("    \"deletedRows\": %d\n",  diffInfo.getDeletedRows().size()));
        sb.append(String.format("    \"changedRows\": %d\n",  diffInfo.getChangedRows().size()));
        sb.append(String.format("    \"equalRows\": %d\n",    diffInfo.getEqualRows().size()));
        sb.append("}\n");
        return sb.toString();
    }

    public static String compileMarkdownReport(DiffInfo diffInfo) {
        Objects.requireNonNull(diffInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(mdDifferentOrNot(diffInfo));
        sb.append("\n");
        sb.append(mdStats(diffInfo));
        sb.append("\n");
        sb.append(mdDetail(diffInfo));
        return sb.toString();
    }

    static String mdDifferentOrNot(DiffInfo diffInfo) {
        StringBuilder sb = new StringBuilder();
        if (diffInfo.hasDifference()) {
            sb.append("**DIFFERENT**");
        } else {
            sb.append("**NO DIFF**");
        }
        sb.append("\n");
        return sb.toString();
    }

    static String mdStats(DiffInfo diffInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("- inserted rows : %d\n", diffInfo.getInsertedRows().size()));
        sb.append(String.format("- deleted rows  : %d\n", diffInfo.getDeletedRows().size()));
        sb.append(String.format("- changed rows  : %d\n", diffInfo.getChangedRows().size()));
        sb.append(String.format("- equal rows:   : %d\n", diffInfo.getEqualRows().size()));
        return sb.toString();
    }

    static String mdDetail(DiffInfo diffInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("|row#|S|original|revised|\n");
        sb.append("|-----|-|--------|-------|\n");
        List<DiffRow> rows = diffInfo.getRows();
        for (int index = 0; index < rows.size(); index++) {
            DiffRow row = rows.get(index);
            sb.append("|" + (index+1) + "|" + formatStatus(row) + "|" +
                    row.getOldLine() + "|" + row.getNewLine() + "|\n");
        }
        return sb.toString();
    }

    private static String formatStatus(DiffRow dr) {
        if (dr.getTag() == DiffRow.Tag.INSERT) {
            return "I";
        } else if (dr.getTag() == DiffRow.Tag.DELETE) {
            return "D";
        } else if (dr.getTag() == DiffRow.Tag.CHANGE) {
            return "C";
        } else {
            return " ";
        }
    }
}
