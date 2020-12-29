package com.mkotynski.mmf.MedicalVisit.Dto;

import com.mkotynski.mmf.Receipt.Dto.ReceiptRequest;
import com.mkotynski.mmf.Recipe.Dto.RecipeRequest;
import com.mkotynski.mmf.Reference.Dto.ReferenceRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitWithDetailsRequest {
    private MedicalVisitRequest medicalVisit;
    private ReceiptRequest receipt;
    private List<RecipeRequest> recipes;
    private List<ReferenceRequest> references;
}
