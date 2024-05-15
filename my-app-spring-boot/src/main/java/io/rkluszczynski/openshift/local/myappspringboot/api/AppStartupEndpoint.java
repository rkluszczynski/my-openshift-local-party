package io.rkluszczynski.openshift.local.myappspringboot.api;

import io.rkluszczynski.openshift.local.myappspringboot.domain.AbstractProbeEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/start")
@Slf4j
public class AppStartupEndpoint extends AbstractProbeEndpoint {

    private AppStartupEndpoint() {
        super("startup");
    }

    @GetMapping
    public ResponseEntity<Void> get() {
        return super.get();
    }

    @PutMapping
    public ResponseEntity<Void> put(@RequestParam int statusCode) {
        return super.put(statusCode);
    }
}
