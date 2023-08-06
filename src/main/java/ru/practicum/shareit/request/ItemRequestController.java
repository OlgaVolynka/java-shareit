package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    @Validated
    public ResponseEntity<RequestForRequestDto> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                              @RequestBody @Validated RequestDto requestDto) {
        log.info("Получен запрос Post createRequest");
        return ResponseEntity.ok(requestService.create(requestDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<RequestForRequestDto>> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос GET allRequest");
        return ResponseEntity.ok(requestService.findAll(userId));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestForRequestDto> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                         @PathVariable("requestId") Long requestId) {
        log.info("Получен запрос GET byIdRequest");
        return ResponseEntity.ok(requestService.findById(userId, requestId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestForRequestDto>> findAllWith(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                                  @RequestParam(required = false) Integer from,
                                                                  @RequestParam(required = false) Integer size) {

        log.info("Получен запрос GET byIdRequest");
        return ResponseEntity.ok(requestService.findAllWith(userId, from, size));

    }
}
