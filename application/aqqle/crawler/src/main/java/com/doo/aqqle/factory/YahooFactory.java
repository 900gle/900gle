package com.doo.aqqle.factory;

import com.doo.aqqle.element.Site;
import com.doo.aqqle.element.Yahoo;

public class YahooFactory implements SiteFactoryInterface {
    @Override
    public Site getSite() {
        return new Yahoo();
    }


}
