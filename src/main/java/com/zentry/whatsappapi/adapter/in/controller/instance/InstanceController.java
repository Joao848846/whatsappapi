package com.zentry.whatsappapi.adapter.in.controller.instance;

import com.zentry.whatsappapi.application.service.InstanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/instances")
public class InstanceController {

    private final InstanceService instanceService;

    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping("/conection")
    public ResponseEntity<?> fetchInstances() {
        try {
            return ResponseEntity.ok(instanceService.fetchInstances());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
    @GetMapping(value = "/{instanceId}")
    public ResponseEntity<?> fetchInstanceStatus(@PathVariable String instanceId) {

        try {
            return ResponseEntity.ok(instanceService.fetchInstanceStatus(instanceId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping(value = "/delete/{instanceId}")
    public ResponseEntity<?> deleteInstance(@PathVariable String instanceId) {
        try {
            return ResponseEntity.ok(instanceService.deleteInstance(instanceId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
