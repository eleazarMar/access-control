object SqlResource {
    fun get(path: String): String = "/* $path */ ${javaClass.getResource(path).readText()}"
}
