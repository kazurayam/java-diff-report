package com.kazurayam.diffutil.text;

public class DiffStats {

    private final String text1location;
    private final String text2location;
    private final int rows;
    private final int insertedRows;
    private final int deletedRows;
    private final int changedRows;
    private final int equalRows;

    private DiffStats(String text1location,
                      String text2location,
                      int rows, int insertedRows,
                      int deletedRows, int changedRows,
                      int equalRows) {
        this.text1location = text1location;
        this.text2location = text2location;
        this.rows = rows;
        this.insertedRows = insertedRows;
        this.deletedRows = deletedRows;
        this.changedRows = changedRows;
        this.equalRows = equalRows;
    }

    String getText1location() { return this.text1location; }
    String getText2location() { return this.text2location; }
    int getRows() { return this.rows; }
    int getInsertedRows() { return this.insertedRows; }
    int getDeletedRows() { return this.deletedRows; }
    int getChangedRows() { return this.changedRows; }
    int getEqualRows() { return this.equalRows; }

    public static class Builder {
        private String text1location;
        private String text2location;
        private int rows;
        private int insertedRows;
        private int deletedRows;
        private int changedRows;
        private int equalRows;
        public Builder() {
            text1location = "";
            text2location = "";
            rows = 0;
            insertedRows = 0;
            deletedRows = 0;
            changedRows = 0;
            equalRows = 0;
        }
        Builder text1location(String location) {
            this.text1location = location;
            return this;
        }
        Builder text2location(String location) {
            this.text2location = location;
            return this;
        }
        Builder rows(int rows) {
            this.rows = rows;
            return this;
        }
        Builder insertedRows(int insertedRows) {
            this.insertedRows = insertedRows;
            return this;
        }
        Builder deletedRows(int deletedRows) {
            this.deletedRows = deletedRows;
            return this;
        }
        Builder changedRows(int changedRows) {
            this.changedRows = changedRows;
            return this;
        }
        Builder equalRows(int equalRows) {
            this.equalRows = equalRows;
            return this;
        }
        DiffStats build() {
            DiffStats instance = new DiffStats(
                    this.text1location,
                    this.text2location,
                    this.rows,
                    this.insertedRows,
                    this.deletedRows,
                    this.changedRows,
                    this.equalRows
            );
            return instance;
        }
    }

}
