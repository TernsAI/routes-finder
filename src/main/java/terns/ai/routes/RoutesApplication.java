package terns.ai.routes;

import eu.europa.ec.eurostat.jgiscotools.util.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import terns.ai.routes.csvMappings.NavalRoute;
import terns.ai.routes.csvMappings.Port;
import terns.ai.routes.csvMappings.RelNavalRouteCanals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class RoutesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RoutesApplication.class, args);
		Logger logger = LoggerFactory.getLogger(RoutesApplication.class);
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		List<Port> ports = CsvUtils.readPortsCsv();
		List<NavalRoute> navalRoutes = IterationUtils.combineRoutes(ports);
		List<NavalRoute> routes;
		List<RelNavalRouteCanals> canals;
		List<CompletableFuture<CombinationOutput>> completableFutures = new ArrayList<>();
		for (NavalRoute navalRoute : navalRoutes) {

				logger.debug("prendo la rotta " + navalRoute.getOrigin() + " - " + navalRoute.getDestination());
				CompletableFuture<CombinationOutput> future = CompletableFuture.supplyAsync(()->IterationUtils.combineCanalsAndRoutes(navalRoute,ports),executorService);
				completableFutures.add(future);
			}
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).exceptionally(ex -> null).join();
		List<CompletableFuture<CombinationOutput>> result =
				completableFutures.stream()
						.collect(Collectors.toList());
		routes = result.stream().map(combinationOutputCompletableFuture -> {
			try {
				return combinationOutputCompletableFuture.get().getRoutes();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
		canals = result.stream().map(combinationOutputCompletableFuture -> {
			try {
				return combinationOutputCompletableFuture.get().getCanals();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
		CsvUtils.writeRoutesCsv(routes);
		CsvUtils.writeRelNavalRouteCanalsCsv(canals);
		logger.info("!!!	HO FINITO	!!!");
	}

}
