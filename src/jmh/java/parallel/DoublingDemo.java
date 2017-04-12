package parallel;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// latest results:
// Benchmark                            Mode  Cnt    Score   Error  Units
// DoublingDemo.doubleAndSumParallel    avgt   40  103.523 ± 0.247  ms/op
// DoublingDemo.doubleAndSumSequential  avgt   40  620.242 ± 1.656  ms/op

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class DoublingDemo {
    public int doubleIt(int n) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return n * 2;
    }

    @Benchmark
    public int doubleAndSumSequential() {
        return IntStream.of(3, 1, 4, 1, 5, 9)
                .map(this::doubleIt)
                .sum();
    }

    @Benchmark
    public int doubleAndSumParallel() {
        return IntStream.of(3, 1, 4, 1, 5, 9)
                .parallel()
                .map(this::doubleIt)
                .sum();
    }
}