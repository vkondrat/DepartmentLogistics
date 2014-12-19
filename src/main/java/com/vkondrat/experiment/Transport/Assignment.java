package com.vkondrat.experiment.transport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vkondrat on 12/18/14.
 */

public class Assignment {
    private int to;
    private int what;
    private List<Integer> whatIds = new ArrayList<>();

    public Assignment() {
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public List<Integer> getWhatIds() {
        return whatIds;
    }

    public void setWhatIds(List<Integer> whatIds) {
        this.whatIds = whatIds;
    }
}
