package com.example.osamah.Fragments.caching;

import java.io.Serializable;

public class UserPerf implements Serializable {
    private Integer  noactivites,notriggers,nolocations;

    public UserPerf(Integer noactivites, Integer notriggers, Integer nolocations) {
        this.noactivites = noactivites;
        this.notriggers = notriggers;
        this.nolocations = nolocations;
    }

    public UserPerf() {
    }



    public Integer getNoactivites() {
        return noactivites;
    }

    public void setNoactivites(Integer noactivites) {
        this.noactivites = noactivites;
    }

    public Integer getNotriggers() {
        return notriggers;
    }

    public void setNotriggers(Integer notriggers) {
        this.notriggers = notriggers;
    }

    public Integer getNolocations() {
        return nolocations;
    }

    public void setNolocations(Integer nolocations) {
        this.nolocations = nolocations;
    }
}
