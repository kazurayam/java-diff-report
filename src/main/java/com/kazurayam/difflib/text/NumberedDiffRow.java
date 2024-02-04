package com.kazurayam.difflib.text;

import com.github.difflib.text.DiffRow;

public class NumberedDiffRow implements Comparable<NumberedDiffRow> {

    private final int seq;
    private final DiffRow diffRow;
    public NumberedDiffRow(int seq, DiffRow diffRow) {
        this.seq = seq;
        this.diffRow = diffRow;
    }
    public int getSeq() { return seq; }
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
        if (!(obj instanceof NumberedDiffRow)) {
            return false;
        }
        NumberedDiffRow other = (NumberedDiffRow)obj;
        return this.seq == other.getSeq();
    }

    @Override
    public int compareTo(NumberedDiffRow other) {
        return this.seq - other.getSeq();
    }
}
