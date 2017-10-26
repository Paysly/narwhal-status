package com.narwhal.authorization.web.utils;

import com.narwhal.basics.core.rest.utils.MicroservicesContext;

public class AppMicroservicesContext extends MicroservicesContext {

    @Override
    public String getStagingBaseServerUrl() {
        return MicroservicesConstants.APP_URL;
    }

    @Override
    public String getProductionBaseServerUrl() {
        return MicroservicesConstants.APP_URL;
    }
}
