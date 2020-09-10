import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.time.Instant

fun main() {
    val config = ConfigFactory.load().extract<OverallConfig>()

    embeddedServer(Netty, port = config.port) {

        install(ContentNegotiation) {
            gson {

            }
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

data class OverallConfig(val port: Int, val https: HttpsConfig)
data class HttpsConfig(val enabled: Boolean, val caPath: String, val pemPath: String)
