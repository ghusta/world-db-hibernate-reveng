package fr.husta.test.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConnectionPoolTest
{

    private Properties dbProperties;

    private DataSource dataSource;

    @BeforeClass
    public static void setUpGlobal() throws Exception
    {
        initSLF4JBridgeHandler();
    }

    private static void initSLF4JBridgeHandler()
    {
        // FINEST  -> TRACE
        // FINER   -> DEBUG
        // FINE    -> DEBUG
        // INFO    -> INFO
        // WARNING -> WARN
        // SEVERE  -> ERROR

        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();
    }

    @Before
    public void setUp() throws Exception
    {
        dbProperties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        dbProperties.load(is);

        assertThat(dbProperties).isNotEmpty();

        HikariConfig hikariConfig = createHikariConfig(dbProperties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        dataSource = hikariDataSource;
    }

    private HikariConfig createHikariConfig(Properties dbProperties)
    {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(dbProperties.getProperty("db.username"));
        hikariConfig.setPassword(dbProperties.getProperty("db.password"));
        hikariConfig.setJdbcUrl(dbProperties.getProperty("db.url"));
        // return null if schema unknown
        hikariConfig.setSchema("pg_catalog");
        // ignored by postgres driver
        hikariConfig.setCatalog("cat_foo");
        return hikariConfig;
    }

    @Test
    public void useConnectionPool() throws SQLException
    {
        Connection cnx;

        cnx = dataSource.getConnection();
        System.out.println("Schema = " + cnx.getSchema());
        System.out.println("Catalog = " + cnx.getCatalog());
        assertNotEquals("cat_foo", cnx.getCatalog());
        assertEquals("pg_catalog", cnx.getSchema());

        DataSourceUtils.releaseConnection(cnx, dataSource);
    }

    @Test
    public void withoutConnectionPool() throws SQLException
    {
        String jdbcUrl = dbProperties.getProperty("db.url");
        String jdbcUsername = dbProperties.getProperty("db.username");
        String jdbcPassword = dbProperties.getProperty("db.password");
        Connection cnx = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        cnx.setSchema("pg_catalog");

        // return null if schema unknown
        System.out.println("Schema = " + cnx.getSchema());
        System.out.println("Catalog = " + cnx.getCatalog());
        assertEquals("pg_catalog", cnx.getSchema());

        DataSourceUtils.releaseConnection(cnx, null);
    }

}
