package com.narwhal.health.backend.utils;

public interface MicroservicesConstants {

    String NOTIFICATIONS_VERSION = "1.0.0";
    String NOTIFICATIONS_TEMPLATE = "en_us";
    String NOTIFICATIONS_KEY_SERVER_STATUS = "server.status";


    interface Paysly {
        interface Admin {
            interface Production {
                String clientId = "paysly-admin-production";
                String clientSecret = "7A787386EB0F2B1671258BAE296A5F5511D9ACB473A1FEDFE75BA7380EDBF712";
            }

            interface Development {
                String clientId = "paysly-admin-development";
                String clientSecret = "F70B499A57AD7C38AF16009ADED4B5488BC4C0E983FFEAD49D94D78E67AFF4DC";
            }
        }
        interface Users {
            // Same as beta for now
            interface Production {
                String clientId = "paysly-users-production";
                String clientSecret = "7A787386EB0D9ACB473A1F2B1671258BAE296A5F5511FEDFE75BA7380EDBF712";
            }
            // Same as production for now
            interface Beta {
                String clientId = "paysly-users-production";
                String clientSecret = "7A787386EB0D9ACB473A1F2B1671258BAE296A5F5511FEDFE75BA7380EDBF712";
            }

            // dev server
            interface Development {
                String clientId = "paysly-users-development";
                String clientSecret = "F70B499A57A5488BC4C0E983FFEAD49D9D7C38AF16009ADED4B4D78E67AFF4DC";
            }
        }
    }
}
