package org.example.usage.basics;

import java.util.ArrayList;
import java.util.List;

public class MergingContributorList {

    List<Contributor> contributors = new ArrayList<>();

    public MergingContributorList() {
    }

    public void addAll(List<Contributor> contributors) {
        this.contributors.addAll(contributors);
    }

    public List<Contributor> mergeResult() {
        return contributors;
    }
}
