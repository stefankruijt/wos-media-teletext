package nl.wos.teletext.entity;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Startup
@Singleton
public class PropertyManager {

    private Properties properties = new Properties();

    @PostConstruct
    public void init() {
        String propFileName = "config.properties";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Property file '" + propFileName + "' not found in the classpath");
        }
    }
}