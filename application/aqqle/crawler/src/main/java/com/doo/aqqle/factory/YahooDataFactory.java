package com.doo.aqqle.factory;

import com.doo.aqqle.element.Site;
import com.doo.aqqle.element.Yahoo;
import com.doo.aqqle.element.YahooData;

public class YahooDataFactory implements SiteFactoryInterface {
    @Override
    public Site getSite() {
        return new YahooData();
    }
}
