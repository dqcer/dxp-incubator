package com.dqcer.dxptools.core.cglib;

import java.beans.PropertyChangeListener;

public abstract class Bean implements java.io.Serializable{

    String sampleProperty;

    /**
     * 添加属性改变监听器
     *
     * @param listener 侦听器
     */
    abstract public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * 删除属性改变监听器
     *
     * @param listener 侦听器
     */
    abstract public void removePropertyChangeListener(PropertyChangeListener listener);

    public String getSampleProperty(){
        return sampleProperty;
    }

    public void setSampleProperty(String value){
        this.sampleProperty = value;
    }

    public String toString(){
        return "sampleProperty is " + sampleProperty;
    }

}
