package com.doo.aqqle.factory;

import com.doo.aqqle.element.Naver;
import com.doo.aqqle.element.Site;

public class NaverFactory implements SiteFactoryInterface {
    @Override
    public Site getSite() {
        return new Naver();
    }
}
