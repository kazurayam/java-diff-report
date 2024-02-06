package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ReporterSupport {

    static String compileStats(DiffInfo diffInfo) {
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

    static List<DiffRowDescriptor> toNumberedDiffRows(List<DiffRow> diffRows) {
        List<DiffRowDescriptor> allEntries = new ArrayList<>();
        for (int i = 0; i < diffRows.size(); i++) {
            DiffRowDescriptor tdr =
                    new DiffRowDescriptor(i + 1, diffRows.get(i));
            allEntries.add(tdr);
        }
        return allEntries;
    }

    static List<DiffRowDescriptorSegment> divide(List<DiffRow> diffRows, final int MARGIN) {
        Objects.requireNonNull(diffRows);
        if (MARGIN < 0 || MARGIN > 5) {
            throw new IllegalArgumentException("the margin must be in the range of [0..5]");
        }
        List<DiffRowDescriptor> allEntries = toNumberedDiffRows(diffRows);
        List<DiffRowDescriptorSegment> container = new ArrayList<>();
        DiffRowDescriptorSegment division = null;
        for (int ax = 0; ax < allEntries.size(); ax++) {
            DiffRowDescriptor focused = allEntries.get(ax);
            if (focused.isTaggedInsertedDeletedChanged()) {
                if (division == null) {
                    division = new DiffRowDescriptorSegment();
                    container.add(division);
                    for (int marginTopCount = 1;
                         marginTopCount <= MARGIN;
                         marginTopCount++) {
                        int marginTopIndex = ax - marginTopCount;
                        if (marginTopIndex >= 0) {
                            division.add(0, allEntries.get(marginTopIndex));
                        }
                    }
                }
                division.add(focused);
            } else { // allEntries.get(ax).isEqual() == true
                if (division != null) {
                    for (int marginBottomCount = 0;
                         marginBottomCount < MARGIN;
                         marginBottomCount++) {
                        int marginBottomIndex = ax + marginBottomCount;
                        if (marginBottomIndex < allEntries.size()) {
                            division.add(allEntries.get(marginBottomIndex));
                        }
                    }
                    division = null;
                }
            }
        }
        // merge the overlapping divisions
        // done
        return mergeOverlaps(container);
    }

    static List<DiffRowDescriptorSegment> mergeOverlaps(List<DiffRowDescriptorSegment> list) {
        if (list.size() >= 2) {
            DiffRowDescriptorSegment first = list.get(0);
            DiffRowDescriptorSegment second = list.get(1);
            if (first.overlapsWith(second)) {
                first.merge(second);
                List<DiffRowDescriptorSegment> newList = new ArrayList<>();
                newList.add(first);
                newList.addAll(list.subList(2, list.size()));
                return mergeOverlaps(newList);
            } else {
                List<DiffRowDescriptorSegment> newList = new ArrayList<>();
                newList.add(first);
                newList.addAll(mergeOverlaps(list.subList(1, list.size())));
                return newList;
            }
        } else {
            return list;
        }
    }

    static String shortenPathString(String pathString) {
        try {
            Path p = Paths.get(pathString);
            String userHome = System.getProperty("user.home");
            String ps = p.toAbsolutePath().normalize().toString();
            if (ps.startsWith(userHome)) {
                Path base = Paths.get(userHome);
                Path relative = base.relativize(p);
                return "~/" + relative;
            } else {
                return pathString;
            }
        } catch (Exception e) {
            return pathString;
        }

    }
}
