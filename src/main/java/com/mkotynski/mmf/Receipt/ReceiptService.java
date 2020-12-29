package com.mkotynski.mmf.Receipt;


import com.mkotynski.mmf.Doctor.DoctorRepository;
import com.mkotynski.mmf.MedicalVisit.MedicalVisitRepository;
import com.mkotynski.mmf.Patient.PatientRepository;
import com.mkotynski.mmf.Receipt.Dto.ReceiptRequest;
import com.mkotynski.mmf.Receipt.Dto.ReceiptResponse;
import com.mkotynski.mmf.Receipt.ReceiptPosition.ReceiptPosition;
import com.mkotynski.mmf.Receipt.ReceiptPosition.ReceiptPositionRepository;
import com.mkotynski.mmf.Receipt.ReceiptPosition.ReceiptPositionService;
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
    ReceiptPositionService receiptPositionService;
    ReceiptPositionRepository receiptPositionRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    MedicalVisitRepository medicalVisitRepository;

    public Optional<ReceiptResponse> getReceipt(Receipt receipt) {
        return Optional.ofNullable(receipt.getResponseDto());
    }

    public ReceiptResponse getReceipt(Integer id) {
        return Optional.ofNullable(receiptRepository.findById(id).orElse(null).getResponseDto()).orElse(null);
    }

    public List<ReceiptResponse> getAllReceipts() {
        return receiptRepository.findAll()
                .stream()
                .map(e -> e.getResponseDto())
                .collect(Collectors.toList());
    }

    public ReceiptResponse getReceiptByVisitId(Integer id) {
        Optional<Receipt> receipt = Optional.ofNullable(receiptRepository.findByMedicalVisit_Id(id));
        if(receipt.isPresent()){
            return receiptRepository.findByMedicalVisit_Id(id).getResponseDto();
        }
        ReceiptResponse receiptResponse = new ReceiptResponse();
        receiptResponse.setPositions(new HashSet<>());
        return receiptResponse;
    }

    public Receipt saveReceipt(@RequestBody ReceiptRequest receiptRequest) {
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
        def.setReceiptStatus(receiptRequest.getReceiptStatus());
        def.setMedicalVisit(medicalVisitRepository.findById(receiptRequest.getVisit().getId()).orElse(null));
        def.setExpirationDate(receiptRequest.getExpirationDate());
        if(receiptRequest.getCode() == null) def.setCode(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)));

        Receipt finalReceipt = receiptRepository.save(def);
        Set<ReceiptPosition> positions = new HashSet<>();
        if (receiptRequest.getPositions() != null) {
            receiptRequest.getPositions().forEach(e -> {
                positions.add(receiptPositionService.saveReceiptPosition(e, finalReceipt.getId()));
            });

            def.setReceiptPositionSet(positions);
        } else def.setReceiptPositionSet(null);

        receiptPositionRepository.selectWhereNotExists(List.copyOf(positions), finalReceipt.getId()).forEach(e -> {
            System.out.println(e.getId());
            receiptPositionRepository.delete(e);
        });

        if (receiptPositionRepository.selectWhereNotExists(List.copyOf(positions), finalReceipt.getId()).isEmpty() && positions.isEmpty()) {
                receiptPositionRepository.findAllByReceipt_Id(finalReceipt.getId()).forEach(e->{
                receiptPositionRepository.delete(e);
            });
        }

        return receiptRepository.save(def);
    }
}
