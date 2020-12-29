package com.mkotynski.mmf.Receipt.ReceiptPosition;


import com.mkotynski.mmf.Receipt.ReceiptPosition.Dto.ReceiptPositionRequest;
import com.mkotynski.mmf.Receipt.ReceiptPosition.Dto.ReceiptPositionResponse;
import com.mkotynski.mmf.Receipt.ReceiptRepository;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionRequest;
import com.mkotynski.mmf.Recipe.RecipePosition.Dto.RecipePositionResponse;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePosition;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionRepository;
import com.mkotynski.mmf.Recipe.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReceiptPositionService {

    ReceiptRepository receiptRepository;
    ReceiptPositionRepository receiptPositionRepository;

    public Optional<ReceiptPositionResponse> getReceiptPosition(ReceiptPosition receiptPosition) {
        return Optional.ofNullable(receiptPosition.getResponseDto());
    }

    public Optional<ReceiptPositionResponse> getReceiptPosition(Integer id) {
        return Optional.ofNullable(receiptPositionRepository.findById(id).orElse(null).getResponseDto());
    }
    public ReceiptPosition saveReceiptPosition(@RequestBody ReceiptPositionRequest receiptPosition, Integer receiptId) {
        ReceiptPosition def = new ReceiptPosition();
        if (receiptPosition.getId() != null) {
            Optional<ReceiptPosition> object = receiptPositionRepository.findById(receiptPosition.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDescription(receiptPosition.getDescription());
        def.setReceipt(receiptRepository.findById(receiptId).orElse(null));
        def.setValue(receiptPosition.getValue());
        return receiptPositionRepository.save(def);
    }
}
