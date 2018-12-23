import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
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

    @Test
    fun blockingFirst(){
        val source = Observable.just("Alpha", "Beta",
                "Gamma", "Delta", "Zeta")
        val firstWithLengthFour = source.filter { it.length == 4 }
                .blockingFirst()
        Assert.assertTrue(firstWithLengthFour == "Beta")

    }

    @Test
    fun blockingGet(){
        val source = Observable.just("Alpha", "Beta",
                "Gamma", "Delta", "Zeta")
        val allWithLengthFour = source.filter { it.length == 4 }
                .toList()
                .blockingGet()
        Assert.assertTrue(allWithLengthFour == listOf("Beta", "Zeta"))

    }

    @Test
    fun blockingLast(){
        val source = Observable.just("Alpha", "Beta",
                "Gamma", "Delta", "Zeta")
        val lastWithLengthFour = source.filter { it.length == 4 }
                .blockingLast()
        Assert.assertTrue(lastWithLengthFour == "Zeta")
    }

    @Test
    fun blockingIterable(){
        val source = Observable.just("Alpha", "Beta",
                "Gamma", "Delta", "Zeta")
        val allWithLengthFive = source.filter{it.length == 5}
                .blockingIterable()

        for(s: String in allWithLengthFive){
            Assert.assertTrue(s.length == 5)
        }
    }

    @Test
    fun blockingForEach(){
        val source = Observable.just("Alpha", "Beta",
                "Gamma", "Delta", "Zeta")
        source.filter{ it.length == 5 }
                .blockingForEach {
                    Assert.assertTrue(it.length == 5)
                }
    }

    @Test
    fun blockingNext(){
        val source = Observable.interval(1, TimeUnit.MILLISECONDS)
                .take(1000)

        val iterable = source.blockingNext()

        for(s: Long in iterable){
            System.out.println(s)
        }

    }

    @Test
    fun blockingLatest(){
        val source = Observable.interval(1, TimeUnit.MILLISECONDS)
                .take(1000)

        val iterable = source.blockingLatest()

        for(s: Long in iterable){
            System.out.println(s)
        }

    }

    @Test
    fun blockingMostRecent(){
        val source = Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(5)

        val iterable = source.blockingMostRecent(-1)

        for(s: Long in iterable){
            System.out.println(s)
        }

    }

    @Test
    fun testObserver(){
        val source = Observable.interval(1, TimeUnit.SECONDS)
                .take(5)

        val testObserver= TestObserver<Long>()
        testObserver.assertNotSubscribed()

        source.subscribe(testObserver)

        testObserver.assertSubscribed()

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()

        testObserver.assertNoErrors()

        testObserver.assertValueCount(5)

        testObserver.assertValues(0,1,2,3,4)
    }

    @Test
    fun testScheduler(){
        val testScheduler = TestScheduler()

        val testObserver = TestObserver<Long>()

        val minuteTicker = Observable.interval(1, TimeUnit.MINUTES, testScheduler)

        minuteTicker.subscribe(testObserver)

        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS)

        testObserver.assertValueCount(0)

        testScheduler.advanceTimeBy(70, TimeUnit.SECONDS)

        testObserver.assertValueCount(1)

        testScheduler.advanceTimeTo(90, TimeUnit.MINUTES)

        testObserver.assertValueCount(90)

    }

    @Test
    fun debugWalkThrough(){
        val testObserver = TestObserver<String>()

        val items = Observable.just("521934/2342/Foxtrot",
                "Bravo/12112,78886/Tango", "283242/4542/Whiskey/2348562")

//        items.doOnNext { System.out.println("Source pushed: $it") }
        items.concatMap { Observable.fromIterable(it.split("/")) }
//                .doOnNext { System.out.println("concatMap() pushed: $it") }
                .filter { it.matches(Regex("[A-Za-z]+")) }
//                .doOnNext { System.out.println("filter() pushed: $it") }
                .subscribe(testObserver)

        System.out.println(testObserver.values())
        testObserver.assertValues("Foxtrot", "Bravo", "Tango", "Whiskey")

    }
}
