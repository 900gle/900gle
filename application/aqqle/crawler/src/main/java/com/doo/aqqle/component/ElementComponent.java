package com.doo.aqqle.component;

import com.doo.aqqle.element.Site;
import com.doo.aqqle.factory.NaverFactory;
import com.doo.aqqle.factory.SiteFactory;

public class ElementComponent {

    public static Site getElementSite(String type) {
        Site site = null;
        switch (type) {
            case "NAVER" :
                site = new SiteFactory().getSite(new NaverFactory());
                break;
            default:
                site = new SiteFactory().getSite(new NaverFactory());
        }
        return site;
    }
}
