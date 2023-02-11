package dev.piste.vayna.config.tokens;

/**
 * @author Piste | https://github.com/zPiste
 */
// GSON CLASS
@SuppressWarnings("ALL")
public class MongoDbConfig {

    private String host;
    private int port;
    private String username;
    private String password;
    private String authDb;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthDb() {
        return authDb;
    }
}
