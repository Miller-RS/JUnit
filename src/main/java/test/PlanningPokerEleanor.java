package test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

public class PlanningPokerEleanor {
    public List<String> identifyExtremes(List<Estimate> estimates) {
        if (estimates == null){
            throw new IllegalArgumentException("Estimados no pueden ser nulos");
        }
        if (estimates.size() <= 1){
            throw  new IllegalArgumentException("Tiene que haber mas de 1 estimado");
        }
        Estimate lowestEstimate = null;
        Estimate highestEstimate = null;

        for(Estimate estimate: estimates) {
            if(highestEstimate == null ||
                    estimate.getEstimate() > highestEstimate.getEstimate()) {
                highestEstimate = estimate;
            }
            if(lowestEstimate == null ||
                    estimate.getEstimate() < lowestEstimate.getEstimate()) {
                lowestEstimate = estimate;
            }
        }
        if(lowestEstimate.equals(highestEstimate))
            return Collections.emptyList();
        return Arrays.asList(
                lowestEstimate.getDeveloper(),
                highestEstimate.getDeveloper()
        );
    }
}