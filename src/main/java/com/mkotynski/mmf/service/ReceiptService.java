package com.mkotynski.mmf.service;


import com.mkotynski.mmf.dto.ReceiptRequest;
import com.mkotynski.mmf.dto.ReceiptResponse;
import com.mkotynski.mmf.dto.RecipeRequest;
import com.mkotynski.mmf.dto.RecipeResponse;
import com.mkotynski.mmf.entity.Receipt;
import com.mkotynski.mmf.entity.ReceiptPosition;
import com.mkotynski.mmf.entity.Recipe;
import com.mkotynski.mmf.entity.RecipePosition;
import com.mkotynski.mmf.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReceiptService {

    ReceiptRepository receiptRepository;
    ReceiptPositionRepository receiptPositionRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public Optional<ReceiptResponse> getReceipt(Receipt receipt) {
        return Optional.ofNullable(receipt.getResponseDto());
    }

    public Optional<ReceiptResponse> getReceipt(Integer id) {
        return Optional.ofNullable(receiptRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<ReceiptResponse> getAllReceipts() {
        return receiptRepository.findAll()
                .stream()
                .map(e -> e.getResponseDto())
                .collect(Collectors.toList());
    }

    public Receipt saveRecipe(@RequestBody ReceiptRequest receiptRequest) {
        Receipt def = new Receipt();
        if (receiptRequest.getId() != null) {
            Optional<Receipt> object = receiptRepository.findById(receiptRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDoctor(doctorRepository.findById(receiptRequest.getDoctor().getId()).orElse(null));
        def.setPatient(patientRepository.findById(receiptRequest.getPatient().getId()).orElse(null));
        def.setDate(new Date());
        def.setExpirationDate(receiptRequest.getExpirationDate());
        def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        Receipt finalReceipt = receiptRepository.save(def);
        Set<ReceiptPosition> positions = new HashSet<>();
        if(receiptRequest.getPositions() != null) {
            receiptRequest.getPositions().forEach(e -> {
                ReceiptPosition pos = new ReceiptPosition();
                pos.setReceipt(finalReceipt);
                pos.setDesription(e.getDescription());
                positions.add(receiptPositionRepository.save(pos));
            });

            def.setReceiptPositionSet(positions);
        } else def.setReceiptPositionSet(null);

        return receiptRepository.save(def);
    }
}
