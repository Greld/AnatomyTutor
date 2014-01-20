package cz.muni.fi.anatomytutor.web.restserver;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 *
 * @author Jan Kucera
 */
public class MyApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MyApplication() {
        register(RequestContextFilter.class);
        register(UserRestResource.class);
    }
}
