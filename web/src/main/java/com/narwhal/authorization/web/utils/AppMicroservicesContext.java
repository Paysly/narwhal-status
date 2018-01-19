package com.narwhal.authorization.web.utils;

import com.narwhal.health.backend.utils.BackendMicroserviceContext;
import lombok.Data;

@Data
public class AppMicroservicesContext extends BackendMicroserviceContext {

    @Override
    public String getStagingBaseServerUrl() {
        return MicroservicesConstants.APP_URL;
    }

    @Override
    public String getProductionBaseServerUrl() {
        return MicroservicesConstants.APP_URL;
    }
}
