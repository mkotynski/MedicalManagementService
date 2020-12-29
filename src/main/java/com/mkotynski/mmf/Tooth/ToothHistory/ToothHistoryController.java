package com.mkotynski.mmf.Tooth.ToothHistory;


import com.mkotynski.mmf.Tooth.ToothHistory.Dto.ToothHistoryRequest;
import com.mkotynski.mmf.Tooth.ToothHistory.Dto.ToothHistoryResponse;
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
public class ToothHistoryController {

    private final ToothHistoryRepository toothHistoryRepository;
    private final ToothHistoryService toothHistoryService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "tooth-history";
    private static final String RESOURCE = "/tooth-history";

    @GetMapping(RESOURCE)
    public List<ToothHistoryResponse> getAllToothHistory() {
        return toothHistoryService.getAllHistory();
    }

    @GetMapping(RESOURCE + "/{id}")
    public ResponseEntity<Optional<ToothHistoryResponse>> getToothHistory(@PathVariable Integer id) {
        log.debug("REST request to read tooth history");

        return ResponseEntity.ok().body(toothHistoryService.getToothHistory(id));
    }

    @PostMapping(RESOURCE)
    public ResponseEntity<ToothHistoryResponse> createToothHistory(@RequestBody ToothHistoryRequest toothHistoryRequest) throws URISyntaxException {
        log.debug("REST request to save tooth history: {}", toothHistoryRequest);

        ToothHistory toothHistory = toothHistoryService.saveToothHistory(toothHistoryRequest);
        ToothHistoryResponse result = toothHistoryService.getToothHistory(toothHistory).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping(RESOURCE)
    public ResponseEntity<ToothHistoryResponse> updateToothHistory(@RequestBody ToothHistoryRequest toothHistoryRequest) throws URISyntaxException {
        log.debug("REST request to save tooth history: {}", toothHistoryRequest);

        ToothHistory toothHistory = toothHistoryService.saveToothHistory(toothHistoryRequest);
        ToothHistoryResponse result = toothHistoryService.getToothHistory(toothHistory).get();
        return ResponseEntity.created(new URI("/api/specialization-type/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping(RESOURCE + "/{id}")
    public ResponseEntity<Void> deteleToothHistory(@PathVariable Integer id) {
        log.debug("REST request to delete tooth history : {}", id);
        toothHistoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
