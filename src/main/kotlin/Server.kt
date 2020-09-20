import com.typesafe.config.ConfigFactory
import db.ActivationHandler
import db.DeviceEventLogger
import io.github.config4k.extract
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.flywaydb.core.Flyway
import java.time.Instant

fun main() {
    val config = ConfigFactory.load().extract<OverallConfig>()
    val datasource = DataSourceFactory(config.db).datasource
    val jdbi = JdbiFactory(datasource).jdbi
    val eventLogger = DeviceEventLogger(jdbi)
    val activationHandler = ActivationHandler(jdbi, eventLogger)

    Flyway.configure().dataSource(datasource).load().migrate()

    embeddedServer(Netty, port = config.port) {

        install(ContentNegotiation) {
            gson {}
        }

        routing {
            get("/") {
                call.respond(HttpStatusCode.OK, "This is access-control: ${Instant.now()}")
            }

            get("/equipment/activate") {

                val device = call.request.queryParameters["device"]
                val rfid = call.request.queryParameters["rfid"]

                if (device == null) {
                    call.respond(HttpStatusCode.BadRequest, "device must be specified")
                    return@get
                }
                if (rfid == null) {
                    call.respond(HttpStatusCode.BadRequest, "rfid must be specified")
                    return@get
                }

                val activationResponse = activationHandler.accessIsAllowed(device, rfid)

                call.respond(activationResponse)
            }

            get("/equipment/deactivate") {

                val device = call.request.queryParameters["device"]
                val rfid = call.request.queryParameters["rfid"]

                if (device == null) {
                    call.respond(HttpStatusCode.BadRequest, "device must be specified")
                    return@get
                }
                if (rfid == null) {
                    call.respond(HttpStatusCode.BadRequest, "rfid must be specified")
                    return@get
                }

                eventLogger.logDeactivation(rfid, device)

                call.respond(HttpStatusCodeContent(HttpStatusCode.NoContent))
            }
        }
    }.start(wait = true)
}

data class ActivationResponse(val grant: Boolean, val rfid: String?, val device: String?, val secondsActivation: Int)

// Support multiple rfids
// support pull down of auth db
