package za.co.mafsoft.fault.tolerance.resource;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;
import za.co.mafsoft.fault.tolerance.model.Coffee;
import za.co.mafsoft.fault.tolerance.service.CoffeeRepositoryService;

@Path("/coffee")
public class CoffeeResource {

    private static final Logger LOGGER = Logger.getLogger(CoffeeResource.class);

    @Inject
    CoffeeRepositoryService coffeeRepositoryService;

    private final AtomicLong counter = new AtomicLong(0);

    @GET
    @Retry(retryOn = RuntimeException.class, delay = 2L, maxRetries = 10)
    public List<Coffee> coffees() {
        final Long invocationNumber = counter.getAndIncrement();

        maybeFail(String.format("CoffeeResource#coffees() invocation #%d failed", invocationNumber));

        LOGGER.infof("CoffeeResource#coffees() invocation #%d returning successfully", invocationNumber);
        return coffeeRepositoryService.getAllCoffees();
    }

    private void maybeFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }
}