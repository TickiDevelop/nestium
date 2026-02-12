import org.gradle.api.Project

object BuildConfig {
    val MINECRAFT_VERSION: String = "1.21.1"
    val NEOFORGE_VERSION: String = "21.1.219"
    val FABRIC_LOADER_VERSION: String = "0.16.9"
    val FABRIC_API_VERSION: String = "0.110.0+1.21.1"

    // This value can be set to null to disable Parchment.
    val PARCHMENT_VERSION: String? = "2024.11.17"

    // https://semver.org/
    var MOD_VERSION: String = "0.6.13"

    fun createVersionString(project: Project): String {
        return MOD_VERSION
    }
}
