package com.bbongdoo.doo.factory;

import com.bbongdoo.doo.component.Site;

public class SiteFactory {

    public Site getSite(SiteFactoryInterface siteFactoryInterface){
        return siteFactoryInterface.getSite();
    }
}
