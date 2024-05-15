package io.rkluszczynski.openshift.local.myappspringboot.domain;

import static java.util.Optional.ofNullable;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
abstract
public class AbstractProbeEndpoint {

    protected final String logPrefix;
    private final AtomicInteger initialFailuresCounter;
    private final ResponseEntity<Void> failureEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .build();

    private ResponseEntity<Void> responseEntity = ResponseEntity.status(HttpStatus.OK)
          .build();

    public AbstractProbeEndpoint(String name) {
        this.logPrefix = "[ " + name + " ]";
        this.initialFailuresCounter = new AtomicInteger(
              ofNullable(
                    System.getenv("X_" + name.toUpperCase() + "_FAILURES_COUNT")
              )
                    .map(Integer::parseInt)
                    .orElse(0)
        );
    }

    public ResponseEntity<Void> get() {
        var entity = responseEntity;
        if (initialFailuresCounter.get() > 0) {
            entity = failureEntity;
            log.warn(logPrefix + " failures to go: " + initialFailuresCounter.decrementAndGet());
        }
        log.info(logPrefix + " get: " + entity.getStatusCode());
        return entity;
    }

    public ResponseEntity<Void> put(int statusCode) {
        responseEntity = ResponseEntity
              .status(statusCode)
              .build();
        log.info(logPrefix + " put: " + responseEntity.getStatusCode());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
              .build();
    }
}
