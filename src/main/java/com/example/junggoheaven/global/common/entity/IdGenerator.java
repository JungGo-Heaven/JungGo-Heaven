package com.example.junggoheaven.global.common.entity;

import com.github.f4b6a3.tsid.TsidFactory;

public class IdGenerator {
    private static final TsidFactory factory = TsidFactory.newInstance1024();

    public static Long generateId() {
        return factory.create().toLong();
    }
}
