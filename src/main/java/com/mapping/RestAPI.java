package com.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mapping.sync.SyncMapping;
import com.mapping.track.TrackMapping;

@RestController
@RequestMapping("/v1")
public class RestAPI {

    @GetMapping("/health")
    public String ping() {
        return "ok";
    }

    @SyncMapping("/sync-test")
    public String syncTest() {
        return "SYNC SUCCESS";
    }

    @TrackMapping("/track-test")
    public String trackTest() {
        return "Track Success";
    }

}
