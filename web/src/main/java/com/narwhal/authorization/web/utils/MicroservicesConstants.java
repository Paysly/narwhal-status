package com.narwhal.authorization.web.utils;

public interface MicroservicesConstants {
    String APP_URL = "https://narwhal-status.appspot.com";

    String ADMIN_ENDPOINT = "https://narwhal-admin.appspot.com/api/v1";
    String AUTHORIZATION_ENDPOINT = "https://narwhal-authorization.appspot.com/api/v1";
    String NOTIFICATIONS_ENDPOINT = "https://narwhal-notifications.appspot.com/api/v1";
    //
    String APPLICATION_DEVELOPMENT_ENDPOINT = "https://dev.pays.ly/api/v1";
    String APPLICATION_BETA_ENDPOINT = "https://beta.pays.ly/api/v1";
    String APPLICATION_PRODUCTION_ENDPOINT = "https://pays.ly/api/v1";
    //
    String LANDING_ENDPOINT = "https://pays.ly/api/v1";

    interface Credentials {
        interface Production {
            String clientId = "paysly-production";
            String clientSecret = "7A787386EB0D9ACB473A1F2B1671258BAE296A5F5511FEDFE75BA7380EDBF712";
        }

        interface Development {
            String clientId = "paysly-development";
            String clientSecret = "F70B499A57A5488BC4C0E983FFEAD49D9D7C38AF16009ADED4B4D78E67AFF4DC";
        }
    }

}
