package com.example.heroic_properties.Models;

import java.util.List;

public class Model_parent_all {
    String title;
    List<Model_Child_all> childmodelclasslist;

    public Model_parent_all(String title, List<Model_Child_all> childmodelclasslist) {
        this.title = title;
        this.childmodelclasslist = childmodelclasslist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Model_Child_all> getChildmodelclasslist() {
        return childmodelclasslist;
    }

    public void setChildmodelclasslist(List<Model_Child_all> childmodelclasslist) {
        this.childmodelclasslist = childmodelclasslist;
    }
}
