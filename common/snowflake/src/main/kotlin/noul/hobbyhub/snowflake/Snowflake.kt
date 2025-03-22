package noul.hobbyhub.snowflake

import java.util.*

class Snowflake {
    companion object {
        private const val UNUSED_BITS = 1
        private const val EPOCH_BITS = 41
        private const val NODE_ID_BITS = 10
        private const val SEQUENCE_BITS = 12

        private const val maxNodeId = (1L shl NODE_ID_BITS) - 1
        private const val maxSequence = (1L shl SEQUENCE_BITS) - 1
    }

    private val nodeId: Long = Random().nextLong(maxNodeId + 1)
    private val startTimeMillis: Long = 1_704_067_200_000L // UTC 2024-01-01T00:00:00Z

    @Volatile
    private var lastTimeMillis: Long = startTimeMillis
    private var sequence: Long = 0L

    @Synchronized
    fun nextId(): Long {
        var currentTimeMillis = System.currentTimeMillis()

        require(currentTimeMillis >= lastTimeMillis) { "Invalid Time " } // 시간 역행 방지

        if (currentTimeMillis == lastTimeMillis) { // 동일한 시간에 여러 ID를 생성할 경우, 시퀀스 번호 증가
            sequence = (sequence + 1) and maxSequence
            if (sequence == 0L) {
                currentTimeMillis = waitNextMillis(currentTimeMillis) // 시퀀스가 최댓값에 도달하면 다음 밀리초까지 대기
            }
        } else {
            sequence = 0L
        }

        lastTimeMillis = currentTimeMillis

        return ((currentTimeMillis - startTimeMillis) shl (NODE_ID_BITS + SEQUENCE_BITS)) or
                (nodeId shl SEQUENCE_BITS) or
                sequence
    }

    private fun waitNextMillis(currentTimestamp: Long): Long {
        var timestamp = currentTimestamp
        while (timestamp <= lastTimeMillis) {
            timestamp = System.currentTimeMillis()
        }
        return timestamp
    }
}