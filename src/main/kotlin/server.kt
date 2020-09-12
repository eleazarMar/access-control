import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.flywaydb.core.Flyway
import java.time.Instant


fun main() {
    val config = ConfigFactory.load().extract<OverallConfig>()
    val datasource = DataSourceFactory(config.db).datasource

    Flyway.configure().dataSource(datasource).load().migrate()

    embeddedServer(Netty, port = config.port) {

        install(ContentNegotiation) {
            gson {}
        }

        routing {
            get("/") {
                call.respond(HttpStatusCode.OK, "This is access-control: ${Instant.now()}")
            }
            get("/activate"){
                call.respond(config)
            }
        }
    }.start(wait = true)
}

data class OverallConfig(val port: Int, val https: HttpsConfig, val db: DBConfig)
data class HttpsConfig(val enabled: Boolean, val caPath: String, val pemPath: String)
data class DBConfig(val host: String, val username: String, val password: String, val dbName: String)

