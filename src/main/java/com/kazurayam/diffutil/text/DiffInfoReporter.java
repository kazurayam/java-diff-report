package com.kazurayam.diffutil.text;

import java.util.Objects;

public class DiffInfoReporter {

    private final DiffInfo diffInfo;

    DiffInfoReporter(DiffInfo diffInfo) {
        Objects.requireNonNull(diffInfo);
        this.diffInfo = diffInfo;
    }


    public enum ReportFormat {
        MARKDOWN,
        HTML;
    }
}
