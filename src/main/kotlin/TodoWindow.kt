import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    windowsBoundary()
}

fun window(){
    Observable.range(1, 50)
            .window(8)
            .flatMapSingle {
                it.reduce("") { total, next ->
                    total + if(total == ""){
                        ""
                    }  else{
                        "|"
                    } + next
                }
            }
            .subscribe{
                System.out.println(it)
            }
}

fun windowWithSkip(){
    Observable.range(1, 50)
            .window(2, 3)
            .flatMapSingle {
                it.reduce("") { total, next ->
                    total + if(total == ""){
                        ""
                    }  else{
                        "|"
                    } + next
                }
            }
            .subscribe{
                System.out.println(it)
            }
}

fun windowWithTime() {
    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map {
                (it + 1)*300
            }
            .window(1, TimeUnit.SECONDS)
            .flatMapSingle {
                it.reduce("") { total, next ->
                    total + if(total == ""){
                        ""
                    }  else{
                        "|"
                    } + next
                }
            }
            .subscribe {
                System.out.println(it)
            }
    sleep(5000)
}

fun windowsBoundary(){
    val cutOffs = Observable.interval(2, TimeUnit.SECONDS)
    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 300 }
            .window(cutOffs)
            .flatMapSingle {
                it.reduce("") { total, next ->
                    total + if (total == "") {
                        ""
                    } else {
                        "|"
                    } + next
                }
            }
            .subscribe {  System.out.println(it)}
    sleep(5000)
}