import io.reactivex.Observable


fun main(args: Array<String>) {
    createWithError()

}

fun createWithNoError(){
    var source= Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onNext(5)
        it.onNext(6)
        it.onComplete()
    }

    source.subscribe {
        System.out.println(it)
    }
}

fun createWithError(){
    var source= Observable.create<Int> {
        try{
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onNext(4)
            it.onNext(5)
            it.onNext(6)
            throw Exception("error")
        }catch (e: Throwable){
            it.onError(e)
        }

    }

    source.subscribe({
        System.out.println(it)
    },{
        System.out.println(it.message)
    })
}