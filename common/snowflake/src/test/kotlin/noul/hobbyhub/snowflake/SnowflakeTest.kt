package noul.hobbyhub.snowflake

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureNanoTime

class SnowflakeTest {
    private val sut = Snowflake()

    @DisplayName("nextId 생성 테스트")
    @Test
    fun test1000() {
        // given
        val executorService = Executors.newFixedThreadPool(10)
        val futures = mutableListOf<Future<List<Long>>>()
        val repeatCount = 1000
        val idCount = 1000

        // when
        repeat(repeatCount) {
            futures.add(executorService.submit<List<Long>> { generateIdList(sut, idCount) })
        }

        // then
        val result = mutableListOf<Long>()
        for (future in futures) {
            val idList = future.get()
            for (i in 1 until idList.size) {
                assertThat(idList[i]).isGreaterThan(idList[i - 1])
            }
            result.addAll(idList)
        }
        assertThat(result.distinct().size).isEqualTo(repeatCount * idCount)

        executorService.shutdown()
    }

    @DisplayName("nextId 성능 테스트")
    @Test
    fun test1001() {
        // given
        val executorService = Executors.newFixedThreadPool(10)
        val repeatCount = 1000
        val idCount = 1000
        val latch = CountDownLatch(repeatCount)

        // when
        val time = measureNanoTime {
            repeat(repeatCount) {
                executorService.submit {
                    generateIdList(sut, idCount)
                    latch.countDown()
                }
            }
            latch.await()
        }

        println("times = ${time / 1_000_000} ms")

        executorService.shutdown()
    }

    private fun generateIdList(snowflake: Snowflake, count: Int): List<Long> {
        return List(count) { snowflake.nextId() }
    }
}