package com.narwhal.health.backend.types;

public interface StatusMemcachedKeys {
    String STATUS_HOURLY_CHECK = "SERVER_%s_STATUS_DAY_%d_HOUR_%d_CHECK_KEY";
    String STATUS_DAILY_CHECK = "SERVER_%s_STATUS_DAY_%d_CHECK_KEY";
}
