import java.util.concurrent.ThreadLocalRandom

fun sleep(millSecond: Long){
    try {
        Thread.sleep(millSecond)
    }catch (e: Exception){

    }
}

fun <T> intenseCalculation(value: T): T{
    sleep(ThreadLocalRandom.current().nextLong(200))
    return value
}