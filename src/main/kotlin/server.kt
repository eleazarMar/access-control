import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
    }
}

fun main() {
    val config = ConfigFactory.load().extract<OverallConfig>()

    embeddedServer(Netty, port = config.port) {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
        }
    }.start(wait = true)
}

data class OverallConfig(val port: Int, val https: HttpsConfig)
data class HttpsConfig(val enabled: Boolean, val caPath: String, val pemPath: String)