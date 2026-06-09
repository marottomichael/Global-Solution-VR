package br.com.globalsolution.controller;

import br.com.globalsolution.dto.response.DashboardResponse;
import br.com.globalsolution.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/amazonia")
    public ResponseEntity<DashboardResponse> getAmazoniaDashboard() {
        return ResponseEntity.ok(dashboardService.getAmazoniaDashboardData());
    }
}
