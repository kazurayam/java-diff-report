package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;

import java.util.List;

public class DiffRowDescriptor implements Comparable<DiffRowDescriptor> {

    private final int sequence;
    private final DiffRow.Tag tag;
    public DiffRowDescriptor(int listIndex, DiffRow diffRow) {
        this.sequence = listIndex + 1;
        this.tag = diffRow.getTag();
    }
    public DiffRow findDiffRow(List<DiffRow> diffRow) {
        return diffRow.get(this.sequence - 1);
    }

    public int getSequence() { return sequence; }
    public DiffRow.Tag getTag() { return tag; }
    public boolean isTaggedInsertedDeletedChanged() {
        return (tag == DiffRow.Tag.INSERT ||
                tag == DiffRow.Tag.DELETE ||
                tag == DiffRow.Tag.CHANGE);
    }
    public boolean isTaggedEqual() {
        return tag == DiffRow.Tag.EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DiffRowDescriptor)) {
            return false;
        }
        DiffRowDescriptor other = (DiffRowDescriptor)obj;
        return this.sequence == other.getSequence();
    }

    @Override
    public int compareTo(DiffRowDescriptor other) {
        return this.sequence - other.getSequence();
    }
}
