package com.hideyoshi.hideyoshiportfolio.utils.injector;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class SameSiteInjector {

    private final ApplicationContext applicationContext;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        DefaultCookieSerializer cookieSerializer = applicationContext.getBean(DefaultCookieSerializer.class);
        log.info("Received DefaultCookieSerializer, Overriding SameSite Strict");
        cookieSerializer.setSameSite("none");
    }
}