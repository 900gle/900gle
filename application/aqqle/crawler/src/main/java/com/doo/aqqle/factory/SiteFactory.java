package com.doo.aqqle.factory;

import com.doo.aqqle.element.Site;

public class SiteFactory {

    public static Site getSite(SiteFactoryInterface siteFactoryInterface) {
        return siteFactoryInterface.getSite();
    }
}
