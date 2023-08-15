package ru.practicum.shareitgateway.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareitgateway.request.dto.RequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemRequestController {

    private final ItemRequestClient requestService;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @RequestBody @Validated RequestDto requestDto) {
        log.info("Create ite, request {}", userId);
        return requestService.create(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Get all  for user with id =  {}", userId);
        return requestService.findAll(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                           @PathVariable("requestId") Long requestId) {
        log.info("Get request with id = {}", requestId);
        return requestService.findById(requestId, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllWith(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(defaultValue = "100") @Positive Integer size) {

        log.info("Get all item requests for user with id =  {}", userId);
        return requestService.findAllWith(userId, from, size);

    }
}
