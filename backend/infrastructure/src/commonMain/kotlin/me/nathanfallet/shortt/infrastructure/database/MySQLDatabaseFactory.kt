package me.nathanfallet.shortt.infrastructure.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.opentelemetry.instrumentation.jdbc.datasource.JdbcTelemetry
import me.nathanfallet.shortt.infrastructure.observability.TelemetryFactory
import org.jetbrains.exposed.v1.jdbc.Database

/**
 * Implementation of [DatabaseFactory] for MySQL databases.
 */
class MySQLDatabaseFactory(
    /**
     * Telemetry factory for OpenTelemetry integration.
     */
    telemetryFactory: TelemetryFactory,
    /**
     * Database host address.
     */
    host: String,
    /**
     * Database port number.
     */
    port: Int,
    /**
     * Database name.
     */
    name: String,
    /**
     * Database username.
     */
    user: String,
    /**
     * Database user password.
     */
    password: String,
    /**
     * Flag indicating whether to use SSL for the connection.
     */
    useSSL: Boolean,
    /**
     * SSL mode for the connection.
     */
    sslMode: String,
    /**
     * Maximum size of the connection pool.
     */
    maximumPoolSize: Int,
) : DatabaseFactory {

    private val db: Database by lazy {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = buildMySQLConnectionUrl(host, port, name, useSSL, sslMode)
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = user
            this.password = password
            this@apply.maximumPoolSize = maximumPoolSize
            minimumIdle = 5
            connectionTestQuery = "SELECT 1"
            validationTimeout = 3000
            connectionTimeout = 30000
            idleTimeout = 600000
            maxLifetime = 1800000
            keepaliveTime = 300000
            leakDetectionThreshold = 60000
        }
        val dataSource = HikariDataSource(hikariConfig)
        val instrumentedDataSource = JdbcTelemetry.create(telemetryFactory.getOpenTelemetry()).wrap(dataSource)
        Database.connect(instrumentedDataSource)
    }

    override fun getDatabase(): Database = db

    private fun buildMySQLConnectionUrl(
        host: String, port: Int, name: String,
        useSSL: Boolean, sslMode: String,
    ): String {
        val params = mutableListOf<String>()
        if (useSSL) {
            params.add("useSSL=true")
            params.add("requireSSL=true")
            params.add("sslMode=$sslMode")
            params.add("allowPublicKeyRetrieval=true")
        }
        params.add("autoReconnect=true")
        params.add("maxReconnects=3")
        params.add("initialTimeout=2")
        params.add("characterEncoding=UTF-8")
        params.add("useUnicode=true")
        params.add("cachePrepStmts=true")
        params.add("prepStmtCacheSize=250")
        params.add("prepStmtCacheSqlLimit=2048")
        params.add("useServerPrepStmts=true")
        params.add("serverTimezone=UTC")
        return "jdbc:mysql://$host:$port/$name?${params.joinToString("&")}"
    }

}
