package com.narwhal.health.backend.utils;

import com.narwhal.basics.core.rest.utils.ServerWebConstants;

public abstract class AppClientConstants {

    public static String getAdminClientId() {
        return ServerWebConstants.getServerValue(MicroservicesConstants.Paysly.Admin.Development.clientId,
                MicroservicesConstants.Paysly.Admin.Development.clientId,
                MicroservicesConstants.Paysly.Admin.Production.clientId);
    }
}
