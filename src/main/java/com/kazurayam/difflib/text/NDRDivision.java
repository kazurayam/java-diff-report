package com.kazurayam.difflib.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NDRDivision {
    private List<NumberedDiffRow> listNDR;
    public NDRDivision() {
        listNDR = new ArrayList<>();
    }
    public void add(int index, NumberedDiffRow ndr) {
        listNDR.add(index, ndr);
    }
    public void add(NumberedDiffRow ndr) {
        listNDR.add(ndr);
    }
    public boolean overlapsWith(NDRDivision other) {
        if (other.end() <= this.begin()) return false;
        if (this.end() < other.begin()) return false;
        return true;
    }
    public void merge(NDRDivision other) {
        Set<NumberedDiffRow> bag = new HashSet<>();
        bag.addAll(this.getList());
        bag.addAll(other.getList());
        List<NumberedDiffRow> mergeResult = bag.stream().sorted().toList();
        this.listNDR = mergeResult;
    }
    public NumberedDiffRow get(int i) {
        return this.listNDR.get(i);
    }
    public NumberedDiffRow getFist() {
        return this.listNDR.getFirst();
    }
    public NumberedDiffRow getLast() {
        return this.listNDR.getLast();
    }
    public List<NumberedDiffRow> getList() {
        return this.listNDR;
    }
    public int begin() {
        return this.listNDR.getFirst().getSeq();
    }
    public int end() {
        return this.listNDR.getLast().getSeq();
    }
    public int size() { return this.listNDR.size(); }
}