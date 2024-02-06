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

    static List<DiffRowDescriptor> toDiffRowDescriptorList(List<DiffRow> diffRowList) {
        List<DiffRowDescriptor> allEntries = new ArrayList<>();
        for (int i = 0; i < diffRowList.size(); i++) {
            DiffRowDescriptor tdr =
                    new DiffRowDescriptor(i , diffRowList.get(i));
            allEntries.add(tdr);
        }
        return allEntries;
    }

    static List<DRDSegment> divide(List<DiffRowDescriptor> drdList, final int MARGIN) {
        Objects.requireNonNull(drdList);
        if (MARGIN < 0 || MARGIN > 5) {
            throw new IllegalArgumentException("the margin must be in the range of [0..5]");
        }
        List<DRDSegment> container = new ArrayList<>();
        DRDSegment division = null;
        for (int listIndex = 0; listIndex < drdList.size(); listIndex++) {
            DiffRowDescriptor focused = drdList.get(listIndex);
            if (focused.isTaggedInsertedDeletedChanged()) {
                if (division == null) {
                    division = new DRDSegment();
                    container.add(division);
                    for (int marginTopCount = 1;
                         marginTopCount <= MARGIN;
                         marginTopCount++) {
                        int marginTopIndex = listIndex - marginTopCount;
                        if (marginTopIndex >= 0) {
                            division.add(0, drdList.get(marginTopIndex));
                        }
                    }
                }
                division.add(focused);
            } else { // diffRowDescriptorList.get(lx).isEqual() == true
                if (division != null) {
                    for (int marginBottomCount = 0;
                         marginBottomCount < MARGIN;
                         marginBottomCount++) {
                        int marginBottomIndex = listIndex + marginBottomCount;
                        if (marginBottomIndex < drdList.size()) {
                            division.add(drdList.get(marginBottomIndex));
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

    static List<DRDSegment> mergeOverlaps(List<DRDSegment> segments) {
        if (segments.size() >= 2) {
            DRDSegment first = segments.get(0);
            DRDSegment second = segments.get(1);
            if (first.overlapsWith(second)) {
                first.merge(second);
                List<DRDSegment> newList = new ArrayList<>();
                newList.add(first);
                newList.addAll(segments.subList(2, segments.size()));
                return mergeOverlaps(newList);
            } else {
                List<DRDSegment> newList = new ArrayList<>();
                newList.add(first);
                newList.addAll(mergeOverlaps(segments.subList(1, segments.size())));
                return newList;
            }
        } else {
            return segments;
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
