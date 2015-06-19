package core.dbi;

/**
 * The type DBI params.
 */
public class DBIParams {
    private final String url;
    private final String user;
    private final String password;

    /**
     * Instantiates a new DBI params.
     *
     * @param url      the url
     * @param user     the user
     * @param password the password
     */
    public DBIParams(final String url, final String user, final String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
