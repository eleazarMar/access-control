package db

import ActivationResponse
import SqlResource
import org.jdbi.v3.core.Jdbi
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class ActivationHandler(private val jdbi: Jdbi, private val eventLogger: DeviceEventLogger) {

    private val canActivateDeviceSql = SqlResource.get("/db/scripts/can-activate-device.sql")
    data class ActivationQueryResult(val enableAt: LocalTime?, val disableAt: LocalTime?)

    fun accessIsAllowed(device: String, rfid: String): ActivationResponse {
        val result = jdbi.withHandle<ActivationQueryResult, Exception> {
            it.createQuery(canActivateDeviceSql)
                .bind("device", device)
                .bind("rfid", rfid)
                .mapTo(ActivationQueryResult::class.java)
                .firstOrNull()
        }

        if (result == null) {
            eventLogger.logActivationDenied(rfid, device)
            return ActivationResponse(false, rfid, device, 0)
        }

        val now = LocalTime.now(ZoneOffset.UTC)
        val isAfterStartTime = result.enableAt == null || now > result.enableAt
        val isBeforeEndTime = result.disableAt == null || now < result.disableAt

        return if (isAfterStartTime && isBeforeEndTime) {
            val secondsActivation = ChronoUnit.SECONDS.between(now, result.disableAt).toInt()
            eventLogger.logActivationSuccess(rfid, device)
            ActivationResponse(true, rfid, device, secondsActivation)
        } else {
            eventLogger.logActivationDenied(rfid, device)
            ActivationResponse(false, rfid, device, 0)
        }
    }
}
