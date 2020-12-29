package com.mkotynski.mmf.Tooth;


import com.mkotynski.mmf.Tooth.Dto.ToothRequest;
import com.mkotynski.mmf.Tooth.Dto.ToothResponse;
import com.mkotynski.mmf.Utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ToothController {

    private final ToothRepository toothRepository;
    private final ToothService toothService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "tooth";
    private static final String RESOURCE = "/tooth";

    @GetMapping(RESOURCE)
    public List<ToothResponse> getAllTooth() {
        return toothService.getAllTooth();
    }

    @GetMapping(RESOURCE+"/{id}")
    public ResponseEntity<Optional<ToothResponse>> getTooth(@PathVariable Integer id) {
        log.debug("REST request to read tooth");

        return ResponseEntity.ok().body(toothService.getTooth(id));
    }

    @PostMapping(RESOURCE)
    public ResponseEntity<ToothResponse> createTooth(@RequestBody ToothRequest toothRequest) throws URISyntaxException {
        log.debug("REST request to save tooth : {}", toothRequest);

        Tooth tooth = toothService.saveTooth(toothRequest);
        ToothResponse result = toothService.getTooth(tooth).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping(RESOURCE)
    public ResponseEntity<ToothResponse> updateTooth(@RequestBody ToothRequest toothRequest) throws URISyntaxException {
        log.debug("REST request to save tooth : {}", toothRequest);

        Tooth tooth = toothService.saveTooth(toothRequest);
        ToothResponse result = toothService.getTooth(tooth).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping(RESOURCE+"/{id}")
    public ResponseEntity<Void> deteleTooth(@PathVariable Integer id) {
        log.debug("REST request to delete tooth : {}", id);
        toothRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
