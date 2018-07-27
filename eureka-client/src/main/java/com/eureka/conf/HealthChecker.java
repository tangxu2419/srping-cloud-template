package com.eureka.conf;

import lombok.Setter;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author tangxu
 * @date 2018/7/1013:54
 */
@Component
@Setter
public class HealthChecker implements HealthIndicator {

    private boolean up = false;

    public Health health() {
        Health.Builder builder = new Health.Builder();
        if (up) {
            return builder.withDetail("aaa_cnt", 10)
                    .withDetail("bbb_status", "up").up().build();
        } else {
            return builder.withDetail("error", "client is down").down().build();
        }
    }

}
