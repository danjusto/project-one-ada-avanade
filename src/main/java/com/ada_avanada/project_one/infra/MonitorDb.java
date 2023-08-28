package com.ada_avanada.project_one.infra;

import com.ada_avanada.project_one.service.FeedDbService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class MonitorDb {
    private FeedDbService feedDbService;
    public MonitorDb(FeedDbService feedDbService) {
        this.feedDbService = feedDbService;
    }

    @PostConstruct
    public void init() throws URISyntaxException, IOException, InterruptedException {
        if (this.feedDbService.userTableEmpty()) {
            this.feedDbService.createAdmin();
        }
        if (this.feedDbService.productTableEmpty()) {
            this.feedDbService.feedDb();
        }
    }
}
