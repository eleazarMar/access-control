package db

import SqlResource
import org.jdbi.v3.core.Jdbi

class ActivationHandler(private val jdbi: Jdbi) {

    private val canActivateDeviceSql = SqlResource.get("/db/scripts/can-activate-device.sql")

    fun accessIsAllowed(device: String, rfid: String): Boolean {
        val count = jdbi.withHandle<Int, Exception> {
            it.createQuery(canActivateDeviceSql)
                .bind("device", device)
                .bind("rfid", rfid)
                .mapTo(Int::class.java)
                .one()
        }

        return count > 0
    }
}
