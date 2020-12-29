package com.mkotynski.mmf.MedicalVisit;

import com.mkotynski.mmf.Enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Integer> {

    List<MedicalVisit> findAllByPatient_Id(Integer id);

    MedicalVisit findByIdAndDoctor_SubjectOrPatient_Subject(Integer id, String doctorSubject, String patientSubject);

    List<MedicalVisit> findAllByDoctor_SubjectOrPatient_Subject(String patientSubject, String doctorSubject);

    List<MedicalVisit> findAllByVisitStatus(VisitStatus visitStatus);
}
