package com.mkotynski.mmf.AvailableDate;


import com.mkotynski.mmf.AvailableDate.Dto.AvailableDateRequest;
import com.mkotynski.mmf.AvailableDate.Dto.AvailableDateResponse;
import com.mkotynski.mmf.Doctor.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvailableDateService {

    AvailableDateRepository availableDateRepository;
    DoctorRepository doctorRepository;

    public Optional<AvailableDateResponse> getAvailableDate(AvailableDate availableDate) {
        return Optional.ofNullable(availableDate.getResponseDto());
    }

    public Optional<AvailableDateResponse> getAvailableDate(Integer id) {
        return Optional.ofNullable(availableDateRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<AvailableDateResponse> getAllAvailableDates() {
        return availableDateRepository.findAll()
                .stream()
                .map(AvailableDate::getResponseDto)
                .collect(Collectors.toList());
    }

    public AvailableDate saveAvailableDate(@RequestBody AvailableDateRequest availableDateRequest) {
        AvailableDate def = new AvailableDate();
        if (availableDateRequest.getId() != null) {
            Optional<AvailableDate> object = availableDateRepository.findById(availableDateRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDate(availableDateRequest.getDate());
        def.setDuration(availableDateRequest.getDuration());
        def.setEndDate(new Date(availableDateRequest.getDate().getTime() + availableDateRequest.getDuration() * 60000));
        def.setDoctor(doctorRepository.findById(availableDateRequest.getDoctor().getId()).orElse(null));
        def.setRepeatable(availableDateRequest.getRepeatable());
        def.setRepeatablePeriod(availableDateRequest.getRepeatablePeriod());
        def.setReserved(availableDateRequest.getReserved() != null);

        System.out.println(availableDateRequest.getDate());

        if(def.getRepeatable() != null && def.getRepeatable()) {
            for (int i = 0; i < 30 / (def.getRepeatablePeriod().getValue() + 1); i++) {
                AvailableDate def2 = new AvailableDate();
                def2.setDuration(availableDateRequest.getDuration());

                def2.setDoctor(doctorRepository.findById(availableDateRequest.getDoctor().getId()).orElse(null));
                def2.setRepeatable(availableDateRequest.getRepeatable());
                def2.setRepeatablePeriod(availableDateRequest.getRepeatablePeriod());
                def2.setReserved(availableDateRequest.getReserved() != null);

                switch (def2.getRepeatablePeriod()) {
                    case EVERYDAY: {
                        def2.setDate(
                                Date.from((def.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(1+i))
                                        .atZone(ZoneId.systemDefault()).toInstant())
                        );
                    } break;
                    case EVERYWEEK:{
                        def2.setDate(
                                Date.from((def.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(7*(i+1)))
                                        .atZone(ZoneId.systemDefault()).toInstant())
                        );
                    } break;
                    case EVERYMONTH:{
                        def2.setDate(
                                Date.from((def.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(30*(i+1)))
                                        .atZone(ZoneId.systemDefault()).toInstant())
                        );
                    } break;
                }
                def2.setEndDate(new Date(def2.getDate().getTime() + availableDateRequest.getDuration() * 60000));
                availableDateRepository.save(def2);
            }

        }
        return availableDateRepository.save(def);
    }

    public AvailableDate saveAvailableDate(@RequestBody AvailableDateRequest availableDateRequest, String subject) {
        availableDateRequest.setDoctor(doctorRepository.findBySubject(subject).getResponseDto());
        return saveAvailableDate(availableDateRequest);
    }

    public AvailableDate updateAvailableDate(@RequestBody AvailableDateRequest availableDateRequest) {
        AvailableDate def = new AvailableDate();
        if (availableDateRequest.getId() != null) {
            Optional<AvailableDate> object = availableDateRepository.findById(availableDateRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setDate(availableDateRequest.getDate());
        def.setDuration(availableDateRequest.getDuration());
        def.setEndDate(new Date(availableDateRequest.getDate().getTime() + availableDateRequest.getDuration() * 60000));
        def.setDoctor(doctorRepository.findById(availableDateRequest.getDoctor().getId()).orElse(null));
        def.setRepeatable(availableDateRequest.getRepeatable());
        def.setRepeatablePeriod(availableDateRequest.getRepeatablePeriod());
        def.setReserved(availableDateRequest.getReserved() != null);

        return availableDateRepository.save(def);
    }


    public List<AvailableDateResponse> getAllAvailableDatesByDoctorId(Integer id){
        return availableDateRepository.findAllByDoctor_Id(id)
                .stream()
                .map(AvailableDate::getResponseDto)
                .collect(Collectors.toList());
    }

    public List<AvailableDateResponse> getAllAvailableDatesByDoctorId(String subject){
        return getAllAvailableDatesByDoctorId(doctorRepository.findBySubject(subject).getId());
    }

}
