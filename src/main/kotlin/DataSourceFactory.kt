import com.mysql.cj.jdbc.Driver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DataSourceFactory(dbConfig: DBConfig) {
    val datasource: HikariDataSource

    init {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://${dbConfig.host}:3306/${dbConfig.dbName}"
            username = dbConfig.username
            password = dbConfig.password
            driverClassName = Driver::class.java.name
        }

        datasource = HikariDataSource(config)
    }
}
