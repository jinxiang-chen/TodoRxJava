import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Test {
    public static void main(String[] args) {

    }

    private void test(){
        Observable.just("WHISKEY/27635/TANGO", "6555/BRAVO", "232352/5675675/FOXTROT")
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.fromArray(s.split("/")))
                .observeOn(Schedulers.computation())
                .filter(s -> s.matches(""));
    }
}
