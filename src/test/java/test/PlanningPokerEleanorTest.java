package test;
import org.junit.jupiter.api.Test;
import net.jqwik.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPokerEleanorTest {


        @Test
        void rejectNullInput() {
            assertThatThrownBy(() -> new PlanningPokerEleanor().identifyExtremes(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectEmptyList() {
            assertThatThrownBy(() -> {
                List<Estimate> emptyList = Collections.emptyList();
                new PlanningPokerEleanor().identifyExtremes(emptyList);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void rejectSingleEstimate() {
            assertThatThrownBy(() -> {
                List<Estimate> list = Collections.singletonList(new Estimate("Eleanor", 1));
                new PlanningPokerEleanor().identifyExtremes(list);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void twoEstimates(){
            List<Estimate> list = Arrays.asList(
                    new Estimate("Mauricio", 10),
                    new Estimate("Frank", 5)
            );

            List<String> devs = new PlanningPokerEleanor().identifyExtremes(list);
            assertThat(devs)
                    .containsExactlyInAnyOrder("Mauricio", "Frank");
        }

        @Test
        void manyEstimates(){
            List<Estimate> list = Arrays.asList(
                    new Estimate("Mauricio", 10),
                    new Estimate("Cesar", 5),
                    new Estimate("Frank", 7)
            );

            List<String> devs = new PlanningPokerEleanor().identifyExtremes(list);
            assertThat(devs)
                    .containsExactlyInAnyOrder("Mauricio", "Cesar");
        }


        @Property
        void estimatesInAnyOrder(@ForAll("estimates") List<Estimate> estimates) {
            estimates.add(new Estimate("MrLowEstimate", 1));
            estimates.add(new Estimate("MsHighEstimate", 100));
            Collections.shuffle(estimates);
            List<String> dev = new PlanningPokerEleanor().identifyExtremes(estimates);

            assertThat(dev)
                    .containsExactlyInAnyOrder("MrLowEstimate", "MsHighEstimate");
        }

        @Provide
        Arbitrary<List<Estimate>> estimates() {
            Arbitrary<String> names = Arbitraries.strings().withCharRange('a', 'z').ofLength(5);
            Arbitrary<Integer> values = Arbitraries.integers().between(2, 99);

            Arbitrary<Estimate> estimates = Combinators.combine(names, values)
                    .as((name, value) -> new Estimate(name, value));

            return estimates.list().ofMinSize(1);
        }

        @Test
        void developersWithSameEstimates() {
            List<Estimate> list = Arrays.asList(
                    new Estimate("Mauricio", 10),
                    new Estimate("Cesar", 5),
                    new Estimate("Andy", 10),
                    new Estimate("Frank", 7),
                    new Estimate("Annibale", 5)
            );

            List<String> devs = new PlanningPokerEleanor().identifyExtremes(list);
            assertThat(devs)
                    .containsExactlyInAnyOrder("Mauricio", "Cesar");

        }

        @Test
        void allDevelopersWithTheSameEstimate() {
            List<Estimate> list = Arrays.asList(
                    new Estimate("Mauricio", 10),
                    new Estimate("Cesar", 10),
                    new Estimate("Andy", 10),
                    new Estimate("Frank", 10),
                    new Estimate("Annibale", 10)
            );
            List<String> devs = new PlanningPokerEleanor().identifyExtremes(list);
            assertThat(devs).isEmpty();

        }

}
