package co.saber11preparate.app.data.auth

import co.saber11preparate.app.domain.UserProfile
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * Almacén temporal en memoria para la autenticación de demostración.
 *
 * - No persiste entre reinicios de la aplicación.
 * - No guarda contraseñas en texto plano: solo un hash SHA-256 en RAM.
 * - No escribe preferencias, archivos ni logs con secretos.
 */
class InMemoryCredentialStore {

    private data class StoredAccount(
        val profile: UserProfile,
        val passwordHash: String,
    )

    private val accountsByEmail = ConcurrentHashMap<String, StoredAccount>()

    @Volatile
    var currentSessionProfileId: String? = null
        private set

    fun findByEmail(normalizedEmail: String): UserProfile? =
        accountsByEmail[normalizedEmail]?.profile

    fun emailExists(normalizedEmail: String): Boolean =
        accountsByEmail.containsKey(normalizedEmail)

    fun register(profile: UserProfile, rawPassword: String) {
        accountsByEmail[profile.email] = StoredAccount(
            profile = profile,
            passwordHash = hashPassword(rawPassword),
        )
    }

    fun authenticate(normalizedEmail: String, rawPassword: String): UserProfile? {
        val stored = accountsByEmail[normalizedEmail] ?: return null
        if (stored.passwordHash != hashPassword(rawPassword)) return null
        return stored.profile
    }

    fun setSession(profileId: String?) {
        currentSessionProfileId = profileId
    }

    fun profileById(id: String): UserProfile? =
        accountsByEmail.values.firstOrNull { it.profile.id == id }?.profile

    fun newId(): String = UUID.randomUUID().toString()

    /**
     * Hash solo para comparación en memoria en el modo demo.
     * No sustituye el hashing de un proveedor de autenticación real.
     */
    private fun hashPassword(rawPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(rawPassword.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
