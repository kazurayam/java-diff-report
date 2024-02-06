package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;

public class DiffRowDescriptor implements Comparable<DiffRowDescriptor> {

    private final int sequenceNumber;
    private final DiffRow diffRow;
    public DiffRowDescriptor(int sequenceNumber, DiffRow diffRow) {
        this.sequenceNumber = sequenceNumber;
        this.diffRow = diffRow;
    }
    public int getNumber() { return sequenceNumber; }
    public DiffRow getDiffRow() { return diffRow; }
    boolean isTaggedInsertedDeletedChanged() {
        return (this.getDiffRow().getTag() == DiffRow.Tag.INSERT ||
                this.getDiffRow().getTag() == DiffRow.Tag.DELETE ||
                this.getDiffRow().getTag() == DiffRow.Tag.CHANGE);
    }
    boolean isTaggedEqual() {
        return this.getDiffRow().getTag() == DiffRow.Tag.EQUAL;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DiffRowDescriptor)) {
            return false;
        }
        DiffRowDescriptor other = (DiffRowDescriptor)obj;
        return this.sequenceNumber == other.getNumber();
    }

    @Override
    public int compareTo(DiffRowDescriptor other) {
        return this.sequenceNumber - other.getNumber();
    }
}
