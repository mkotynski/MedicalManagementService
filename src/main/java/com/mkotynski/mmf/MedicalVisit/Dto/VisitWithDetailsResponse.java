package com.mkotynski.mmf.MedicalVisit.Dto;

import com.mkotynski.mmf.Receipt.Dto.ReceiptResponse;
import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import com.mkotynski.mmf.Reference.Dto.ReferenceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitWithDetailsResponse {
    private MedicalVisitResponse medicalVisit;
    private ReceiptResponse receipt;
    private List<RecipeResponse> recipeList;
    private List<ReferenceResponse> referenceList;
}
