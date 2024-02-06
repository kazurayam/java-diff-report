package com.kazurayam.difflib.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DiffRowDescriptorSegment {
    private List<DiffRowDescriptor> listNDR;
    public DiffRowDescriptorSegment() {
        listNDR = new ArrayList<>();
    }
    public void add(int index, DiffRowDescriptor ndr) {
        listNDR.add(index, ndr);
    }
    public void add(DiffRowDescriptor ndr) {
        listNDR.add(ndr);
    }
    public boolean overlapsWith(DiffRowDescriptorSegment other) {
        if (this.lastSeq() < other.firstSeq()) return false;
        if (other.lastSeq() < this.firstSeq()) return false;
        return true;
    }
    public void merge(DiffRowDescriptorSegment other) {
        Set<DiffRowDescriptor> bag = new HashSet<>();
        bag.addAll(this.getList());
        bag.addAll(other.getList());
        this.listNDR = bag.stream().sorted().collect(Collectors.toList());
    }
    public DiffRowDescriptor get(int i) {
        return this.listNDR.get(i);
    }
    public DiffRowDescriptor getFist() {
        return this.listNDR.get(0);
    }
    public DiffRowDescriptor getLast() {
        return this.listNDR.get(listNDR.size() - 1);
    }
    public List<DiffRowDescriptor> getList() {
        return this.listNDR;
    }
    public List<Integer> getSeqList() {
        List<Integer> seqList = new ArrayList<>();
        for (DiffRowDescriptor ndr : listNDR) {
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