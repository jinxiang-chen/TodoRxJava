import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    bufferBoundary()
}

fun buffer(){
    Observable.range(1, 50)
            .buffer(8)
            .subscribe{
                System.out.println(it)
            }
}

fun bufferWithSkip(){
    Observable.range(1, 10)
            .buffer(3,4)
            .subscribe{
                System.out.println(it)
            }
}

fun bufferWithSkipLessCount(){
    Observable.range(1, 10)
            .buffer(3,1)
            .subscribe{
                System.out.println(it)
            }
}

fun bufferWithSkipLessCountWithFilter(){
    Observable.range(1, 10)
            .buffer(2,1)
            .filter {
                it.size == 2
            }
            .subscribe{
                System.out.println(it)
            }
}

fun bufferWithTime(){
    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map {
                (it + 1)*300
            }
            .buffer(1, TimeUnit.SECONDS)
            .subscribe {
                System.out.println(it)
            }
    sleep(5000)
}

fun bufferWithTimeCount(){
    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map {
                (it + 1)*300
            }
            .buffer(1, TimeUnit.SECONDS,2)
            .subscribe {
                System.out.println(it)
            }
    sleep(5000)
}

fun bufferBoundary(){
    val cutOffs = Observable.interval(1, TimeUnit.SECONDS)
    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 300 }
            .buffer(cutOffs)
            .subscribe {  System.out.println(it)}
    sleep(5000)
}