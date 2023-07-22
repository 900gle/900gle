package com.bbongdoo.doo.factory;

import com.bbongdoo.doo.component.Naver;
import com.bbongdoo.doo.component.Site;

public class NaverFactory implements SiteFactoryInterface {
    @Override
    public Site getSite() {
        return new Naver();
    }
}
