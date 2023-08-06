package ru.practicum.shareit.request;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestForRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass

public class RequestMapper {

    public static Request toRequest(RequestDto requestDto) {

        Request request = new Request();
        request.setDescription(requestDto.getDescription());
        request.setCreated(requestDto.getCreated());
        return request;
    }

    public static RequestForRequestDto requestForRequestDto(Request request) {
        RequestForRequestDto requestForRequestDto = new RequestForRequestDto();
        requestForRequestDto.setId(request.getId());
        requestForRequestDto.setDescription(request.getDescription());
        requestForRequestDto.setCreated(request.getCreated());
        requestForRequestDto.setItems(request.getItems());

        return requestForRequestDto;
    }

    public static List<RequestForRequestDto> toListRequestDto(List<Request> requests) {
        return requests.stream()
                .map(request -> requestForRequestDto(request))
                .collect(Collectors.toList());
    }
}
