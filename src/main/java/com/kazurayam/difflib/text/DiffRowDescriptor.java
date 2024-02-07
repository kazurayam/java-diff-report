package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;

import java.util.List;

/**
 * A pointer to a DiffRow entry in a List of DiffRows. A DiffRowDescription contains only 2 properties:
 * int sequence, @see com.github.difflib.text.DiffRow. A DiffRowDescription does not contain a copy of a DiffRow object; therefore it is light-weighted.
 * The List of DiffRows is supposed to be the one created by @see com.kazurayam.difflib.text.Differ#diffFiles(Path, Path) and others.
 * @author kazurayam
 */
public class DiffRowDescriptor implements Comparable<DiffRowDescriptor> {

    private final int sequence;
    private final DiffRow.Tag tag;
    public DiffRowDescriptor(int listIndex, DiffRow diffRow) {
        this.sequence = listIndex + 1;
        this.tag = diffRow.getTag();
    }
    public DiffRow findDiffRowIn(List<DiffRow> diffRow) {
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
