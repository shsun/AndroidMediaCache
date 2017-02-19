package com.biz.homepage;

import com.shsunframework.entry.BaseEntry;

/**
 * Created by shsun on 17/2/19.
 */

public class ChannelEntry extends BaseEntry {

    private final String name;

    public ChannelEntry(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
