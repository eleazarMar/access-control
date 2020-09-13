import com.mysql.cj.jdbc.Driver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import javax.sql.DataSource

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


class JdbiFactory(dataSource: DataSource) {
    val jdbi: Jdbi = Jdbi.create(dataSource)

    init {
        jdbi.installPlugin(KotlinPlugin())
    }
}
