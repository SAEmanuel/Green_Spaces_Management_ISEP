package pt.ipp.isep.dei.esoft.project.application.session;

import pt.ipp.isep.dei.esoft.project.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents an application session.
 */
public class ApplicationSession {
    private final AuthenticationRepository authenticationRepository;
    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    private static final String COMPANY_DESIGNATION = "Company.Designation";

    /**
     * Private constructor for the ApplicationSession class.
     */
    private ApplicationSession() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        Properties props = getProperties();
    }

    /**
     * Retrieves the current user session.
     *
     * @return The current user session.
     */
    public UserSession getCurrentSession() {
        pt.isep.lei.esoft.auth.UserSession userSession = this.authenticationRepository.getCurrentUserSession();
        return new UserSession(userSession);
    }

    /**
     * Retrieves properties from the configuration file.
     *
     * @return The properties from the configuration file.
     */
    private Properties getProperties() {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(COMPANY_DESIGNATION, "MusgoSublime");

        // Read configured values
        try {
            InputStream in = new FileInputStream(CONFIGURATION_FILENAME);
            props.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }


    private static ApplicationSession singleton = null;

    /**
     * Retrieves the instance of ApplicationSession.
     *
     * @return The instance of ApplicationSession.
     */
    public static ApplicationSession getInstance() {
        if (singleton == null) {
            synchronized (ApplicationSession.class) {
                singleton = new ApplicationSession();
            }
        }
        return singleton;
    }
}