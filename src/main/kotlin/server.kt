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
    val jdbi = JdbiFactory(datasource).jdbi

    val canActivateDeviceSql = SqlResource.get("/db/scripts/can-activate-device.sql")

    Flyway.configure().dataSource(datasource).load().migrate()

    embeddedServer(Netty, port = config.port) {

        install(ContentNegotiation) {
            gson {}
        }

        routing {
            get("/") {
                call.respond(HttpStatusCode.OK, "This is access-control: ${Instant.now()}")
            }
            get("/equipment/activate"){

                val device = call.request.queryParameters["device"]
                val rfid = call.request.queryParameters["rfid"]

                if(device == null){
                    call.respond(HttpStatusCode.BadRequest, "device must be specified")
                    return@get
                }
                if(rfid == null){
                    call.respond(HttpStatusCode.BadRequest, "rfid must be specified")
                    return@get
                }

                val count = jdbi.withHandle<Int, Exception> {
                    it.createQuery(canActivateDeviceSql)
                        .bind("device", device)
                        .bind("rfid", rfid)
                        .mapTo(Int::class.java)
                        .one()
                }

                val grant = count > 0

                call.respond(ActivationResponse(grant, rfid, device))
            }
        }
    }.start(wait = true)
}

data class ActivationResponse(val grant: Boolean, val rfid: String?, val device: String?)

