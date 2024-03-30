package io.rkluszczynski.openshift.local.myappspringboot.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@Slf4j
abstract
public class AbstractProbeEndpoint {

    protected final String logPrefix;

    private ResponseEntity<Void> responseEntity = ResponseEntity.status(HttpStatus.OK)
          .build();

    public ResponseEntity<Void> get() {
        log.info(logPrefix + " get: " + responseEntity.getStatusCode());
        return responseEntity;
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
