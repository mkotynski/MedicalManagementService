package com.mkotynski.mmf.controller;


import com.mkotynski.mmf.dto.ReceiptRequest;
import com.mkotynski.mmf.dto.ReceiptResponse;
import com.mkotynski.mmf.dto.RecipeRequest;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.entity.Receipt;
import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.repository.ReceiptRepository;
import com.mkotynski.mmf.repository.RecipeRepository;
import com.mkotynski.mmf.service.ReceiptService;
import com.mkotynski.mmf.service.RecipeService;
import com.mkotynski.mmf.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReceiptController {


    private final ReceiptRepository receiptRepository;
    private final ReceiptService receiptService;

    @Value("${pl.mkotynski.mfms.app-name}")
    private String applicationName;
    private static final String ENTITY_NAME = "receipt";
    private static final String resource = "receipt";

    @GetMapping(resource)
    public List<ReceiptResponse> getAllReceipts() {
        log.debug("REST request to read all receipts");

        return receiptService.getAllReceipts();
    }

    @GetMapping(resource + "/{id}")
    public ResponseEntity<Optional<ReceiptResponse>> getRecipe(@PathVariable Integer id) {
        log.debug("REST request to read receipt");

        return ResponseEntity.ok().body(receiptService.getReceipt(id));
    }


    @PostMapping(resource)
    public ResponseEntity<ReceiptResponse> createRecipe(@RequestBody ReceiptRequest receiptRequest) throws URISyntaxException {
        log.debug("REST request to create receipt : {}", receiptRequest);

        Receipt receipt = receiptService.saveRecipe(receiptRequest);
        ReceiptResponse result = receiptService.getReceipt(receipt).get();
        return ResponseEntity.created(new URI("/api/receipt/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PutMapping(resource)
    public ResponseEntity<ReceiptResponse> updateRecipe(@RequestBody ReceiptRequest receiptRequest) throws URISyntaxException {
        log.debug("REST request to create recipe : {z}", receiptRequest);

        Receipt receipt = receiptService.saveRecipe(receiptRequest);
        ReceiptResponse result = receiptService.getReceipt(receipt).get();
        return ResponseEntity.created(new URI("/api/recipe/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping(resource + "/{id}")
    public ResponseEntity<Void> deleteMedicalVisit(@PathVariable Integer id) {
        log.debug("REST request to delete receipt : {}", id);
        receiptRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

}
