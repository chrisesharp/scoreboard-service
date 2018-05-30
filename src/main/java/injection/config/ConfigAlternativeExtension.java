package injection.config;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;

import org.eclipse.microprofile.config.ConfigProvider;

public class ConfigAlternativeExtension implements Extension {
  
    private Logger log = Logger.getLogger(ConfigAlternativeExtension.class.getName());
    
    public <T> void processAnnotated(@Observes @WithAnnotations(ConfigAlternative.class) ProcessAnnotatedType<T> event) {
        ConfigAlternative alternative = event.getAnnotatedType().getJavaClass().getAnnotation(ConfigAlternative.class);
        log.info("Checking for " + alternative.value() + " on " + event.getAnnotatedType().getJavaClass() + " gives " + configExists(alternative.value()));
        if (alternative != null && !configExists(alternative.value())) {
            log.info("veto " + event.getAnnotatedType().getJavaClass());
            event.veto();
        }
    }

    private boolean configExists(String name) {
        for (String p: ConfigProvider.getConfig().getPropertyNames()) {
            if (name.equals(p)) return true;
        }
        return false;
    }

}