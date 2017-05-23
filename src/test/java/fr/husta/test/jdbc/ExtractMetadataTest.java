package fr.husta.test.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractMetadataTest
{

    private Properties dbProperties;

    @Before
    public void setUp() throws Exception
    {
        dbProperties = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        dbProperties.load(is);

        assertThat(dbProperties).isNotEmpty();
    }

    @Test
    public void testDatabaseMetadata() throws Exception
    {
        System.out.println("--- TEST : EXTRACTION METADATA JDBC ---");

        String jdbcUrl = dbProperties.getProperty("db.url");
        String jdbcUsername = dbProperties.getProperty("db.username");
        String jdbcPassword = dbProperties.getProperty("db.password");
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);

        DatabaseMetaData metaData = ExtractMetadataUtil.getMetaData(connection);
        System.out.println("DBMS nom     = " + ExtractMetadataUtil.getDatabaseProductName(metaData));
        System.out.println("DBMS version = " + ExtractMetadataUtil.getDatabaseProductVersion(metaData));
        System.out.println("DBMS full version = " + ExtractMetadataUtil.getDatabaseProductFullVersion(metaData));
        System.out.println("DBMS driver  = " + metaData.getDriverName());

        System.out.println("getMaxConnections = " + metaData.getMaxConnections());
        System.out.println("getMaxSchemaNameLength = " + metaData.getMaxSchemaNameLength());
        System.out.println("getMaxTableNameLength = " + metaData.getMaxTableNameLength());
        System.out.println("getMaxColumnNameLength = " + metaData.getMaxColumnNameLength());
        System.out.println("supportsMixedCaseIdentifiers = " + metaData.supportsMixedCaseIdentifiers());
        System.out.println("supportsMixedCaseQuotedIdentifiers = " + metaData.supportsMixedCaseQuotedIdentifiers());

        System.out.println();
        List<String> schemaList = ExtractMetadataUtil.getSchemaList(connection);
        System.out.println("--- LISTE DES SCHEMAS ---");
        System.out.println(schemaList);
        System.out.println();

        List<String> tableList = ExtractMetadataUtil.getTableList(connection, "public");
        System.out.println("--- LISTE DES TABLES ---");
        System.out.println(tableList);
        System.out.println();

        connection.close();
    }

    private static String listStringToBullet(List<String> source)
    {
        StringBuilder sb = new StringBuilder();
        for (String str : source)
        {
            sb.append(" * ").append(str).append("\n");
        }
        return sb.toString();
    }

}
