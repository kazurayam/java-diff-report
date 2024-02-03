package com.kazurayam.diffutil.text;

import java.util.Objects;

public class DiffInfoFormatterToMarkdown {

    private final DiffInfo diffInfo;

    DiffInfoFormatterToMarkdown(DiffInfo diffInfo) {
        Objects.requireNonNull(diffInfo);
        this.diffInfo = diffInfo;
    }


}
