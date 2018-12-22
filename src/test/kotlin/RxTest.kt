import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class RxTest{

    @Test
    fun testBlockSubscribe(){
        val atomicInteger = AtomicInteger()
        val source = Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
        source.blockingSubscribe { atomicInteger.incrementAndGet() }

        Assert.assertTrue(atomicInteger.get() == 5)
//        assert(atomicInteger.get() == 5)
    }
}
