
data class OverallConfig(val port: Int, val https: HttpsConfig, val db: DBConfig)
data class HttpsConfig(val enabled: Boolean, val caPath: String, val pemPath: String)
data class DBConfig(val host: String, val username: String, val password: String, val dbName: String)
