package com.kazurayam.difflib.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DRDSegment {
    private List<DiffRowDescriptor> memory;
    public DRDSegment() {
        memory = new ArrayList<>();
    }
    public void add(int index, DiffRowDescriptor drd) {
        memory.add(index, drd);
    }
    public void add(DiffRowDescriptor drd) {
        memory.add(drd);
    }
    public void addAll(List<DiffRowDescriptor> sourceList) {
        memory.addAll(sourceList);
    }
    public boolean overlapsWith(DRDSegment other) {
        if (this.getLastSequence() < other.getFirstSequence()) return false;
        if (other.getLastSequence() < this.getFirstSequence()) return false;
        return true;
    }
    public void merge(DRDSegment other) {
        Set<DiffRowDescriptor> bag = new HashSet<>();
        bag.addAll(this.getListDRD());
        bag.addAll(other.getListDRD());
        this.memory = bag.stream().sorted().collect(Collectors.toList());
    }
    public DiffRowDescriptor get(int i) {
        return this.memory.get(i);
    }
    public DiffRowDescriptor getFirst() {
        return this.memory.get(0);
    }
    public int getFirstSequence() {
        return getFirst().getSequence();
    }
    public DiffRowDescriptor getLast() {
        return this.memory.get(memory.size() - 1);
    }
    public int getLastSequence() {
        return getLast().getSequence();
    }
    public List<DiffRowDescriptor> getListDRD() {
        return this.memory;
    }
    public int size() { return this.memory.size(); }
}