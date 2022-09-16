package brc.studybuddy.backend.gateway.utils

import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

object SafeReqIdMap {
    private val map: ConcurrentHashMap<String, Long> = ConcurrentHashMap<String, Long>()

    fun put(key: String, value: Long) {
        map[key] = value
    }

    fun pop(key: String): Optional<Long> {
        val value = map[key]
        map.remove(key)
        return Optional.ofNullable(value)
    }
}
