package com.doo.aqqle.element;

public abstract class Site<T, U> {
    public abstract String getUrl(T k, U i);
    public abstract String getListCssSelector();

}
