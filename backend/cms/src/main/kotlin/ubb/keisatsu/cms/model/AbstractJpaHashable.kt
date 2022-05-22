package ubb.keisatsu.cms.model

import org.springframework.data.util.ProxyUtils
import java.util.*

abstract class AbstractJpaHashable {
    val hashId: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractJpaHashable

        return this.hashId == other.hashId
    }

    override fun hashCode(): Int {
        return hashId.hashCode()
    }

}