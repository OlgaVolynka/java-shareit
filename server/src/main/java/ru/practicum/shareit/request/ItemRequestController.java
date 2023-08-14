package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestForRequestDto;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor

public class ItemRequestController {

    private final RequestServiceImpl requestService;

    @PostMapping
    public RequestForRequestDto createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestBody RequestDto requestDto) {
        log.info("Получен запрос Post createRequest");
        return requestService.create(requestDto, userId);
    }

    @GetMapping
    public List<RequestForRequestDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос GET allRequest");
        return requestService.findAll(userId);
    }

    @GetMapping("/{requestId}")
    public RequestForRequestDto findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable("requestId") Long requestId) {
        log.info("Получен запрос GET byIdRequest");
        return requestService.findById(userId, requestId);
    }

    @GetMapping("/all")
    public List<RequestForRequestDto> findAllWith(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "100") Integer size) {

        log.info("Получен запрос GET byIdRequest");
        return requestService.findAllWith(userId, from, size);

    }
}