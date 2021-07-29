package com.dqcer.dxptools.sync.groovy;

public interface IRule {
    static final int NORMAL_TYPE = 0;
    static final int GROOVY_FILE_TYPE = 1;
    static final int GROOVY_DB_TYPE = 2;
    static final int GROOVY_SPRING_TYPE = 3;

    int getType();

    void printInfo();
}
