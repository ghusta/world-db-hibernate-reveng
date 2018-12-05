package fr.husta.test.jdbc;

import fr.husta.test.ansi.AnsiColor;
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
        System.out.println("DBMS nom             = " + ExtractMetadataUtil.getDatabaseProductName(metaData));
        System.out.println("DBMS version         = " + ExtractMetadataUtil.getDatabaseProductVersion(metaData));
        System.out.println("DBMS full version    = " + ExtractMetadataUtil.getDatabaseProductFullVersion(metaData));
        System.out.println("DBMS driver name     = " + metaData.getDriverName());
        System.out.println("DBMS driver version  = " + metaData.getDriverVersion());

        System.out.println("getMaxConnections = " + metaData.getMaxConnections());
        System.out.println("getMaxSchemaNameLength = " + metaData.getMaxSchemaNameLength());
        System.out.println("getMaxTableNameLength = " + metaData.getMaxTableNameLength());
        System.out.println("getMaxColumnNameLength = " + metaData.getMaxColumnNameLength());
        System.out.println("getMaxIndexLength = " + metaData.getMaxIndexLength());
        System.out.println("getMaxColumnsInIndex = " + metaData.getMaxColumnsInIndex());
        System.out.println("supportsMixedCaseIdentifiers = " + metaData.supportsMixedCaseIdentifiers());
        System.out.println("supportsMixedCaseQuotedIdentifiers = " + metaData.supportsMixedCaseQuotedIdentifiers());
        System.out.println("supportsSchemasInDataManipulation = " + metaData.supportsSchemasInDataManipulation());
        System.out.println("supportsSchemasInTableDefinitions = " + metaData.supportsSchemasInTableDefinitions());
        System.out.println("supportsANSI92IntermediateSQL = " + metaData.supportsANSI92IntermediateSQL());
        System.out.println("supportsANSI92FullSQL = " + metaData.supportsANSI92FullSQL());
        System.out.println("supportsTransactions = " + metaData.supportsTransactions());
        System.out.println("supportsMultipleTransactions = " + metaData.supportsMultipleTransactions());
        System.out.println("supportsOpenCursorsAcrossCommit = " + metaData.supportsOpenCursorsAcrossCommit());
        System.out.println("supportsOpenStatementsAcrossCommit = " + metaData.supportsOpenStatementsAcrossCommit());
        System.out.println("supportsSelectForUpdate = " + metaData.supportsSelectForUpdate());
        System.out.println("supportsUnion = " + metaData.supportsUnion());
        System.out.println("supportsStoredProcedures = " + metaData.supportsStoredProcedures());
        System.out.println("supportsNamedParameters = " + metaData.supportsNamedParameters());
        System.out.println("supportsOrderByUnrelated = " + metaData.supportsOrderByUnrelated());

        System.out.println();
        List<String> schemaList = ExtractMetadataUtil.getSchemaList(connection);
        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES SCHEMAS ---"));
        System.out.println(schemaList);
        System.out.println();

        String currentSchema = "public";

        List<String> tableList = ExtractMetadataUtil.getTableList(connection, currentSchema);
        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES TABLES ---"));
        System.out.println(AnsiColor.colorize("SCHEMA => " + currentSchema, AnsiColor.FG_RED));
        System.out.println(tableList);
        System.out.println();

        currentSchema = "sc_world";

        List<String> tableListScWorld = ExtractMetadataUtil.getTableList(connection, currentSchema);
        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES TABLES ---"));
        System.out.println(AnsiColor.colorize("SCHEMA => " + currentSchema, AnsiColor.FG_RED));
        System.out.println(tableListScWorld);
        System.out.println();

        currentSchema = "public";

        List<String> sequenceList = ExtractMetadataUtil.getSequenceList(connection, currentSchema);
        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES SEQUENCES ---"));
        System.out.println(AnsiColor.colorize("SCHEMA => " + currentSchema, AnsiColor.FG_RED));
        System.out.println(sequenceList);
        System.out.println();

        currentSchema = "public";
        List<String> colsList;

        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES COLONNES / TABLE ---"));
        colsList = ExtractMetadataUtil.getTableColumnList(connection, currentSchema, "city");
        System.out.println(AnsiColor.colorize("CITY => ", AnsiColor.FG_GREEN));
        System.out.println(listStringToBullet(colsList));

        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES COLONNES / TABLE ---"));
        colsList = ExtractMetadataUtil.getTableColumnList(connection, currentSchema, "country");
        System.out.println(AnsiColor.colorize("COUNTRY => ", AnsiColor.FG_GREEN));
        System.out.println(listStringToBullet(colsList));

        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES PK / TABLE ---"));
        colsList = ExtractMetadataUtil.getTablePrimaryKeysList(connection, currentSchema, "city");
        System.out.println(AnsiColor.colorize("PK CITY => ", AnsiColor.FG_MAGENTA));
        System.out.println(listStringToBullet(colsList));

        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES PK / TABLE ---"));
        colsList = ExtractMetadataUtil.getTablePrimaryKeysList(connection, currentSchema, "country");
        System.out.println(AnsiColor.colorize("PK COUNTRY => ", AnsiColor.FG_MAGENTA));
        System.out.println(listStringToBullet(colsList));

        System.out.println(AnsiColor.colorizeDefault("--- LISTE DES FK / TABLE ---"));
        colsList = ExtractMetadataUtil.getTableImportedForeignKeysList(connection, currentSchema, "city");
        System.out.println(AnsiColor.colorize("FK CITY => ", AnsiColor.FG_YELLOW));
        System.out.println(listStringToBullet(colsList));

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
