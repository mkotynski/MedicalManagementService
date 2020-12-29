package com.mkotynski.mmf.Tooth.ToothHistory;


import com.mkotynski.mmf.Tooth.ToothHistory.Dto.ToothHistoryRequest;
import com.mkotynski.mmf.Tooth.ToothHistory.Dto.ToothHistoryResponse;
import com.mkotynski.mmf.Tooth.ToothRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToothHistoryService {


    ToothHistoryRepository toothHistoryRepository;
    ToothRepository toothRepository;

    public Optional<ToothHistoryResponse> getToothHistory(ToothHistory toothHistory) {
        return Optional.ofNullable(toothHistory.getResponseDto());
    }

    public Optional<ToothHistoryResponse> getToothHistory(Integer id) {
        return Optional.ofNullable(toothHistoryRepository.findById(id).orElse(null).getResponseDto());
    }

    public List<ToothHistoryResponse> getAllHistory() {
        return toothHistoryRepository.findAll()
                .stream()
                .map(ToothHistory::getResponseDto)
                .collect(Collectors.toList());
    }

    public List<ToothHistoryResponse> getAllHistoryOfTooth(Integer id) {
        return toothHistoryRepository.findAllByTooth_Id(id)
                .stream()
                .map(ToothHistory::getResponseDto)
                .collect(Collectors.toList());
    }

    public ToothHistory saveToothHistory(@RequestBody ToothHistoryRequest toothHistoryRequest) {
        ToothHistory def = new ToothHistory();
        if (toothHistoryRequest.getId() != null) {
            Optional<ToothHistory> object = toothHistoryRepository.findById(toothHistoryRequest.getId());

            if (object.isPresent()) {
                def = object.get();
            }
        }
        def.setTooth(toothRepository.findById(toothHistoryRequest.getTooth().getId()).orElse(null));
        def.setDate(new Date());
        def.setDescription(toothHistoryRequest.getDescription());

        return toothHistoryRepository.save(def);
    }

}
