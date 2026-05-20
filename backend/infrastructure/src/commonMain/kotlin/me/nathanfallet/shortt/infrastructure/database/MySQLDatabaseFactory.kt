package me.nathanfallet.shortt.infrastructure.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.opentelemetry.instrumentation.hikaricp.v3_0.HikariTelemetry
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
    private val dataSource: HikariDataSource by lazy {
        val hikariConfig = HikariConfig().apply {
            poolName = "hikari-$name"
            jdbcUrl = buildMySQLConnectionUrl(host, port, name, useSSL, sslMode)
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = user
            this.password = password
            isAutoCommit = false
            this@apply.maximumPoolSize = maximumPoolSize
            minimumIdle = 1
            validationTimeout = 3000
            connectionTimeout = 30000
            idleTimeout = 300_000
            maxLifetime = 1_800_000
            keepaliveTime = 600_000
            leakDetectionThreshold = 60_000
            metricsTrackerFactory = HikariTelemetry.create(telemetryFactory.getOpenTelemetry())
                .createMetricsTrackerFactory()
        }
        HikariDataSource(hikariConfig)
    }

    private val db: Database by lazy {
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
        params.add("characterEncoding=UTF-8")
        params.add("useUnicode=true")
        params.add("cachePrepStmts=true")
        params.add("prepStmtCacheSize=250")
        params.add("prepStmtCacheSqlLimit=2048")
        params.add("serverTimezone=UTC")
        return "jdbc:mysql://$host:$port/$name?${params.joinToString("&")}"
    }

    override fun isHealthy(): Boolean {
        return !dataSource.isClosed && dataSource.isRunning
    }
}
