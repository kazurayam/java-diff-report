package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Compiles a Diff report in Markdown format
 *
 * @author kazurayam
 */
public final class MarkdownReporter {

    private static final Logger logger = LoggerFactory.getLogger(MarkdownReporter.class);

    private final DiffInfo diffInfo;
    private final boolean compact;
    private final int margin;

    private MarkdownReporter(Builder builder) {
        this.diffInfo = builder.diffInfo;
        this.compact = builder.compact;
        this.margin = builder.margin;
    }

    public boolean getCompact() {
        return this.compact;
    }

    public int getMargin() { return this.margin; }

    public String compileMarkdownReport() {
        StringBuilder sb = new StringBuilder();
        if (diffInfo.getTitle() != null) {
            sb.append(String.format("## %s\n", diffInfo.getTitle()));
        }
        if (diffInfo.getPathOriginal() != null || diffInfo.getPathRevised() != null) {
            sb.append("### Sources\n");
        }
        if (diffInfo.getPathOriginal() != null) {
            sb.append("- original : " +
                    ReporterSupport.shortenPathString(diffInfo.getPathOriginal()) + "\n");
        }
        if (diffInfo.getPathRevised() != null) {
            sb.append("- revised : " +
                    ReporterSupport.shortenPathString(diffInfo.getPathRevised()) + "\n");
        }
        sb.append("\n");
        sb.append("### Stats\n\n");
        sb.append(String.format("%s at %s\n", mdDifferentOrNot(diffInfo), timestamp()));
        sb.append("\n");
        sb.append(mdStats(diffInfo));
        sb.append("\n");
        sb.append("### Detail\n\n");
        sb.append(mdDetail(diffInfo, compact, margin));
        return sb.toString();
    }
    private String timestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    public String compileStats() {
        return ReporterSupport.compileStats(diffInfo);
    }

    //--------------------------------------------------------------------------

    static String mdDifferentOrNot(DiffInfo diffInfo) {
        StringBuilder sb = new StringBuilder();
        if (diffInfo.hasDifference()) {
            sb.append("**DIFFERENT**");
        } else {
            sb.append("**NO DIFF**");
        }
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

    static String mdDetail(DiffInfo diffInfo, boolean compact, final int MARGIN) {
        StringBuilder sb = new StringBuilder();
        sb.append("|row#|S|original|revised|\n");
        sb.append("|----|-|--------|-------|\n");
        List<DiffRow> allRows = diffInfo.getRows();
        if (compact) {
            // divide a list of DiffRow objects into a set of NDRDivision objects.
            List<DiffRowDescriptor> allDRD = ReporterSupport.toDiffRowDescriptorList(allRows);
            List<DRDSegment> container = ReporterSupport.divide(allDRD, MARGIN);
            // in the Compact format; hide the equal lines to shrink the report in size
            for (int cx = 0; cx < container.size(); cx++) {
                DRDSegment division = container.get(cx);
                if (cx == 0 && division.get(0).getSequence() > 1) {
                    sb.append(mdGap());
                    sb.append("\n");
                }
                if (cx > 0) {
                    sb.append(mdGap());
                    sb.append("\n");
                }
                for (int dx = 0; dx < division.size(); dx++) {
                    DiffRowDescriptor drd = division.get(dx);
                    DiffRow dr = drd.findDiffRowIn(allRows);
                    sb.append(mdFormatDiffRow(drd.getSequence(), dr));
                    sb.append("\n");
                }
                if (cx == (container.size() - 1) &&
                        division.getLast().getSequence() < allRows.size()) {
                    sb.append(mdGap());
                    sb.append("\n");
                }
            }
        } else {
            // in the Full format; all lines are printed
            List<DiffRowDescriptor> listNDR = ReporterSupport.toDiffRowDescriptorList(diffInfo.getRows());
            for (DiffRowDescriptor drd : listNDR) {
                DiffRow dr = drd.findDiffRowIn(allRows);
                sb.append(mdFormatDiffRow(drd.getSequence(), dr));
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    static String mdGap() { return "|...| | | |"; }

    static String mdFormatDiffRow(int seq, DiffRow diffRow) {
        return String.format("| %d | %s | %s | %s |",
                seq, ReporterSupport.formatStatus(diffRow),
                diffRow.getOldLine(), diffRow.getNewLine());
    }

    //-------------------------------------------------------------------------

    public static class Builder {
        private DiffInfo diffInfo = null;
        private boolean compact = true;
        private int margin = 2;

        /**
         * Construct the Builder for @see com.kazurayam.difflib.text.MarkdownReporter
         * @param diffInfo created by @see com.kazurayam.difflib.text.Differ
         */
        public Builder(DiffInfo diffInfo) {
            Objects.requireNonNull(diffInfo);
            this.diffInfo = diffInfo;
        }

        /**
         *
         * @param compact when true, you want the resulting report to have compact detail section,
         *                where EQUAL rows are trimmed so that the report to be shorter.
         *                when false, the detail section will be full.
         *                Optional, will default to true
         * @return an instance of @see com.kazurayam.difflib.text.MarkdownReporter.Builder
         */
        public Builder compact(boolean compact) {
            this.compact = compact;
            return this;
        }

        /**
         * number of EQUAL rows in a DRDSegment
         * @param margin integer of the range [0..5]
         * @return an instance of @see com.kazurayam.difflib.text.MarkdownReporter.Builder
         */
        public Builder margin(int margin) {
            if (margin < 0 || margin > 5) {
                throw new IllegalArgumentException("margine must be in the range of [0..5]");
            }
            this.margin = margin;
            return this;
        }

        /**
         * constructs an instance of @see com.kazurayam.difflib.text.MarkdownReporter
         */
        public MarkdownReporter build() {
            return new MarkdownReporter(this);
        }
    }
}
