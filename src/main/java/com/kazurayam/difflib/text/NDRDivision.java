package com.kazurayam.difflib.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (this.lastSeq() < other.firstSeq()) return false;
        if (other.lastSeq() < this.firstSeq()) return false;
        return true;
    }
    public void merge(NDRDivision other) {
        Set<NumberedDiffRow> bag = new HashSet<>();
        bag.addAll(this.getList());
        bag.addAll(other.getList());
        this.listNDR = bag.stream().sorted().collect(Collectors.toList());
    }
    public NumberedDiffRow get(int i) {
        return this.listNDR.get(i);
    }
    public NumberedDiffRow getFist() {
        return this.listNDR.get(0);
    }
    public NumberedDiffRow getLast() {
        return this.listNDR.get(listNDR.size() - 1);
    }
    public List<NumberedDiffRow> getList() {
        return this.listNDR;
    }
    public List<Integer> getSeqList() {
        List<Integer> seqList = new ArrayList<>();
        for (NumberedDiffRow ndr : listNDR) {
            seqList.add(ndr.getNumber());
        }
        return seqList;
    }
    public int firstSeq() {
        return this.listNDR.get(0).getNumber();
    }
    public int lastSeq() {
        return this.listNDR.get(listNDR.size() - 1).getNumber();
    }
    public int size() { return this.listNDR.size(); }
}