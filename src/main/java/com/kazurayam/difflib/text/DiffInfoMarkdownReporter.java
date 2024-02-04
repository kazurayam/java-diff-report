package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DiffInfoMarkdownReporter {

    private static final Logger logger = LoggerFactory.getLogger(DiffInfoMarkdownReporter.class);

    private DiffInfoMarkdownReporter() {}

    public static String compileStatsJson(DiffInfo diffInfo) {
        Objects.requireNonNull(diffInfo);
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(String.format("    \"rows\": %d,\n", diffInfo.getRows().size()));
        sb.append(String.format("    \"isDifferent\": %b,\n",  diffInfo.hasDifference()));
        sb.append(String.format("    \"insertedRows\": %d,\n", diffInfo.getInsertedRows().size()));
        sb.append(String.format("    \"deletedRows\": %d,\n",  diffInfo.getDeletedRows().size()));
        sb.append(String.format("    \"changedRows\": %d,\n",  diffInfo.getChangedRows().size()));
        sb.append(String.format("    \"equalRows\": %d\n",    diffInfo.getEqualRows().size()));
        sb.append("}\n");
        return sb.toString();
    }

    public static String compileMarkdownReport(DiffInfo diffInfo) {
        return compileMarkdownReport(diffInfo, true);
    }

    public static String compileMarkdownReport(DiffInfo diffInfo, boolean compact) {
        Objects.requireNonNull(diffInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(mdDifferentOrNot(diffInfo));
        sb.append("\n");
        sb.append(mdStats(diffInfo));
        sb.append("\n");
        sb.append(mdDetail(diffInfo, compact));
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

    static String mdDetail(DiffInfo diffInfo, boolean compact) {
        StringBuilder sb = new StringBuilder();
        sb.append("|row#|S|original|revised|\n");
        sb.append("|----|-|--------|-------|\n");
        List<DiffRow> allRows = diffInfo.getRows();
        if (compact) {
            // in the Compact format; hide the equal lines to shrink the report in size
            List<NDRDivision> container =
                    DiffInfoMarkdownReporter.divideDiffRowsCompact(allRows);
            for (int cx = 0; cx < container.size(); cx++) {
                NDRDivision division = container.get(cx);
                if (cx == 0 && division.get(0).getSeq() > 1) {
                    sb.append(gap());
                    sb.append("\n");
                }
                if (cx > 0) {
                    sb.append(gap());
                    sb.append("\n");
                }
                for (int dx = 0; dx < division.size(); dx++) {
                    NumberedDiffRow ndr = division.get(dx);
                    sb.append(formatDiffRow(ndr.getSeq(), ndr.getDiffRow()));
                    sb.append("\n");
                }
                if (cx == (container.size() - 1) &&
                        division.getLast().getSeq() < allRows.size()) {
                    sb.append(gap());
                    sb.append("\n");
                }
            }
        } else {
            // in the Full format; all lines are printed
            List<NumberedDiffRow> listNDR = toNumberedDiffRows(diffInfo.getRows());
            for (NumberedDiffRow ndr : listNDR) {
                sb.append(formatDiffRow(ndr.getSeq(), ndr.getDiffRow()));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String gap() { return "| | | | |"; }

    private static String formatDiffRow(int seq, DiffRow diffRow) {
        return String.format("| %d | %s | %s | %s |", seq, DiffInfoMarkdownReporter.formatStatus(diffRow),
                diffRow.getOldLine(), diffRow.getNewLine());
    }

    static String formatStatus(DiffRow dr) {
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

    //--------------------------------------------------------------------------

    static List<NDRDivision> divideDiffRowsCompact(List<DiffRow> diffRows) {
        List<NumberedDiffRow> allEntries = toNumberedDiffRows(diffRows);
        List<NDRDivision> container = new ArrayList<>();
        final int MARGIN_TOP = 2;
        final int MARGIN_BOTTOM = 2;
        NDRDivision division = null;
        for (int ax = 0; ax < allEntries.size(); ax++) {
            NumberedDiffRow focused = allEntries.get(ax);
            if (focused.isTaggedInsertedDeletedChanged()) {
                if (division == null) {
                    division = new NDRDivision();
                    for (int marginTopCount = 1; marginTopCount <= MARGIN_TOP; marginTopCount++) {
                        int marginTopIndex = ax - marginTopCount;
                        if (marginTopIndex >= 0) {
                            division.add(0, allEntries.get(marginTopIndex));
                        }
                    }
                }
                division.add(focused);
            } else { // allEntries.get(ax).isEqual() == true
                if (division != null) {
                    for (int marginBottomCount = 0; marginBottomCount < MARGIN_BOTTOM; marginBottomCount++) {
                        int marginBottomIndex = ax + marginBottomCount;
                        if (marginBottomIndex < allEntries.size()) {
                            division.add(allEntries.get(marginBottomIndex));
                        }
                    }
                    container.add(division);
                    division = null;
                }
            }
        }
        // merge the overlapping divisions


        return container;
    }

    static List<NumberedDiffRow> toNumberedDiffRows(List<DiffRow> diffRows) {
        List<NumberedDiffRow> allEntries = new ArrayList<>();
        for (int i = 0; i < diffRows.size(); i++) {
            NumberedDiffRow tdr = new NumberedDiffRow(i + 1, diffRows.get(i));
            allEntries.add(tdr);
        }
        return allEntries;
    }
}