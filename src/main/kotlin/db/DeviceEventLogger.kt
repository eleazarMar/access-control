package db

import SqlResource
import org.jdbi.v3.core.Jdbi

class DeviceEventLogger(private val jdbi: Jdbi) {

    private val activationDenied = "activation_denied"
    private val activationSuccess = "activation_success"
    private val deactivation = "deactivation"

    val logDeviceEventSql = SqlResource.get("/db/scripts/log-device-event.sql")

    fun logActivationDenied(rfid: String, device: String) = logEvent(rfid, device, activationDenied)
    fun logActivationSuccess(rfid: String, device: String) = logEvent(rfid, device, activationSuccess)
    fun logDeactivation(rfid: String, device: String) = logEvent(rfid, device, deactivation)

    private fun logEvent(rfid: String, device: String, event: String) {
        jdbi.useHandle<Exception> {
            it.createUpdate(logDeviceEventSql)
                .bind("rfid", rfid)
                .bind("device_id", device)
                .bind("event", event)
                .execute()
        }
    }
}
