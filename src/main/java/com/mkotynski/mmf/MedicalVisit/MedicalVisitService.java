package com.mkotynski.mmf.MedicalVisit;


import com.mkotynski.mmf.Doctor.DoctorRepository;
import com.mkotynski.mmf.Enums.VisitStatus;
import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitRequest;
import com.mkotynski.mmf.MedicalVisit.Dto.MedicalVisitResponse;
import com.mkotynski.mmf.MedicalVisit.Dto.VisitWithDetailsRequest;
import com.mkotynski.mmf.MedicalVisit.Dto.VisitWithDetailsResponse;
import com.mkotynski.mmf.Patient.PatientRepository;
import com.mkotynski.mmf.Receipt.Dto.ReceiptRequest;
import com.mkotynski.mmf.Receipt.Dto.ReceiptResponse;
import com.mkotynski.mmf.Receipt.ReceiptService;
import com.mkotynski.mmf.Recipe.Dto.RecipeResponse;
import com.mkotynski.mmf.Recipe.Recipe;
import com.mkotynski.mmf.Recipe.RecipePosition.RecipePositionRepository;
import com.mkotynski.mmf.Recipe.RecipeRepository;
import com.mkotynski.mmf.Recipe.RecipeService;
import com.mkotynski.mmf.Reference.Dto.ReferenceResponse;
import com.mkotynski.mmf.Reference.Reference;
import com.mkotynski.mmf.Reference.ReferenceRepository;
import com.mkotynski.mmf.Reference.ReferenceService;
import com.mkotynski.mmf.VisitType.VisitTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalVisitService {

    MedicalVisitRepository medicalVisitRepository;
    VisitTypeRepository visitTypeRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    RecipeService recipeService;
    RecipeRepository recipeRepository;
    RecipePositionRepository recipePositionRepository;
    ReferenceService referenceService;
    ReferenceRepository referenceRepository;
    ReceiptService receiptService;

    public Optional<MedicalVisitResponse> getMedicalVisit(MedicalVisit medicalVisit) {
        return Optional.ofNullable(medicalVisit.getResponseDto());
    }

    public Optional<MedicalVisitResponse> getMedicalVisit(Integer id) {
        return Optional.ofNullable(medicalVisitRepository.findById(id).orElse(null).getResponseDto());
    }

    public Optional<MedicalVisitResponse> getMedicalVisit(Integer id, String subject) {
        return Optional.ofNullable(medicalVisitRepository.findByIdAndDoctor_SubjectOrPatient_Subject(id, subject, subject).getResponseDto());
    }

    public List<MedicalVisitResponse> getAllMedicalVisit() {
        return medicalVisitRepository.findAll()
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }

    public List<MedicalVisitResponse> getAllMedicalVisit(String subject) {
        return medicalVisitRepository.findAllByDoctor_SubjectOrPatient_Subject(subject, subject)
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }

    public MedicalVisit saveMedicalVisit(@RequestBody MedicalVisitRequest medicalVisitRequest) {
        MedicalVisit def = new MedicalVisit();
        if (medicalVisitRequest.getId() != null) {
            Optional<MedicalVisit> object = medicalVisitRepository.findById(medicalVisitRequest.getId());
            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setName(medicalVisitRequest.getName());
        def.setDate(medicalVisitRequest.getDate());
        def.setEndDate(medicalVisitRequest.getEndDate());
        def.setDone(medicalVisitRequest.getDone());
        def.setVisitType(visitTypeRepository.findById(medicalVisitRequest.getVisitType().getId()).get());
        def.setDescription(medicalVisitRequest.getDescription());
        def.setVisitStatus(medicalVisitRequest.getVisitStatus());
        def.setDoctor(doctorRepository.findById(medicalVisitRequest.getDoctor().getId()).get());
        def.setPatient(patientRepository.findById(medicalVisitRequest.getPatient().getId()).get());

        return medicalVisitRepository.save(def);
    }

    public List<MedicalVisitResponse> getAllMedicalVisitByPatientId(Integer id) {
        return medicalVisitRepository.findAllByPatient_Id(id)
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }

    public List<MedicalVisitResponse> getAllMedicalVisitByPatientId(String subject) {
        return getAllMedicalVisitByPatientId(doctorRepository.findBySubject(subject).getId());
    }

    @Transactional
    @Modifying
    public VisitWithDetailsResponse saveVisitWithDetails(VisitWithDetailsRequest visitWithDetailsRequest, String subject) {
        MedicalVisitRequest medicalVisitRequest = visitWithDetailsRequest.getMedicalVisit();
        MedicalVisit medicalVisit = saveMedicalVisit(visitWithDetailsRequest.getMedicalVisit());
        MedicalVisitResponse medicalVisitResponse = medicalVisit.getResponseDto();
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        List<ReferenceResponse> referenceResponses = new ArrayList<>();
        ReceiptRequest receiptRequest = visitWithDetailsRequest.getReceipt();


        List<Recipe> recipes = new ArrayList<>();
        visitWithDetailsRequest.getRecipes().forEach(e -> {
            if (e.getId() == null) {
                e.setDoctor(doctorRepository.findBySubject(subject).getResponseDto());
                e.setPatient(patientRepository.findById(medicalVisitRequest.getPatient().getId()).get().getResponseDto());
                e.setVisit(medicalVisit.getResponseDto());
                RecipeResponse recipeResponse = recipeService.saveRecipe(e).getResponseDto();
                recipeResponses.add(recipeResponse);
                recipes.add(recipeRepository.findById(recipeResponse.getId()).orElse(null));
            } else {
                recipes.add(recipeRepository.findById(e.getId()).orElse(null));
            }
        });

        recipeRepository.selectWhereNotExists(recipes, medicalVisit.getId()).forEach(e -> {
            System.out.println(e.getId());
            recipeRepository.delete(e);
        });

        if (recipeRepository.selectWhereNotExists(recipes, medicalVisit.getId()).isEmpty() && recipes.isEmpty()) {
            recipeRepository.findAllByMedicalVisit_Id(medicalVisit.getId()).forEach(e->{
                System.out.println(e.getId());
                recipeRepository.delete(e);
            });
        }


        List<Reference> references = new ArrayList<>();
        visitWithDetailsRequest.getReferences().forEach(e -> {
            if (e.getId() == null) {
                e.setDoctor(doctorRepository.findBySubject(subject).getResponseDto());
                e.setPatient(patientRepository.findById(medicalVisitRequest.getPatient().getId()).get().getResponseDto());
                e.setVisit(medicalVisit.getResponseDto());
                ReferenceResponse referenceResponse = referenceService.saveReference(e).getResponseDto();
                referenceResponses.add(referenceResponse);
                references.add(referenceRepository.findById(referenceResponse.getId()).orElse(null));
            } else {
                references.add(referenceRepository.findById(e.getId()).orElse(null));
            }
        });

        referenceRepository.selectWhereNotExists(references, medicalVisit.getId()).forEach(e -> {
            System.out.println(e.getId());
            referenceRepository.delete(e);
        });

        if (referenceRepository.selectWhereNotExists(references, medicalVisit.getId()).isEmpty() && references.isEmpty()) {
            referenceRepository.findAllByMedicalVisit_Id(medicalVisit.getId()).forEach(e->{
                System.out.println(e.getId());

                referenceRepository.delete(e);
            });
        }


        receiptRequest.setDoctor(doctorRepository.findBySubject(subject).getResponseDto());
        receiptRequest.setPatient(patientRepository.findById(medicalVisitRequest.getPatient().getId()).get().getResponseDto());
        receiptRequest.setVisit(medicalVisit.getResponseDto());

        ReceiptResponse receiptResponse = receiptService.saveReceipt(receiptRequest).getResponseDto();

        VisitWithDetailsResponse visitWithDetailsResponse = new VisitWithDetailsResponse();
        visitWithDetailsResponse.setMedicalVisit(medicalVisitResponse);
        visitWithDetailsResponse.setReceipt(receiptResponse);
        visitWithDetailsResponse.setReferenceList(referenceResponses);
        visitWithDetailsResponse.setRecipeList(recipeResponses);

        return visitWithDetailsResponse;
    }

    public List<MedicalVisitResponse> getVisitHistoryOfSubject(String subjectFromRequest) {
        return medicalVisitRepository.findAllByVisitStatus(VisitStatus.DONE)
                .stream()
                .map(MedicalVisit::getResponseDto)
                .collect(Collectors.toList());
    }
}
