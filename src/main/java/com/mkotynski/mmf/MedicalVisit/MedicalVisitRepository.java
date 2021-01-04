package com.mkotynski.mmf.MedicalVisit;

import com.mkotynski.mmf.Enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalVisitRepository extends JpaRepository<MedicalVisit, Integer> {

    List<MedicalVisit> findAllByPatient_Id(Integer id);

    @Query("select m from MedicalVisit m where m.id = :id and (m.doctor.subject = :doctorSubject or m.patient.subject = :patientSubject)")
    MedicalVisit findDistinctByIdAndDoctor_SubjectOrPatient_Subject(Integer id, String doctorSubject, String patientSubject);

    List<MedicalVisit> findAllByDoctor_SubjectOrPatient_Subject(String patientSubject, String doctorSubject);

    List<MedicalVisit> findAllByVisitStatus(VisitStatus visitStatus);
}
