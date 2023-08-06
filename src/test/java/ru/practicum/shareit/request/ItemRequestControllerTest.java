package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestForRequestDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {
    @Mock
    RequestServiceImpl requestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    @Test
    void createRequest() {

        RequestForRequestDto returnRequest = new RequestForRequestDto();
        RequestDto requestDto = new RequestDto();
        requestDto.setDescription("новая ДТО");
        Mockito.when(requestService.create(requestDto, 1L)).thenReturn(returnRequest);
        ResponseEntity<RequestForRequestDto> response = itemRequestController.createRequest(1L, requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());

    }

    @Test
    void findAll() {

        List<RequestForRequestDto> returnRequest = List.of(new RequestForRequestDto());


        Mockito.when(requestService.findAll(1L)).thenReturn(returnRequest);
        ResponseEntity<List<RequestForRequestDto>> response = itemRequestController.findAll(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());
        assertNotNull(response);

    }

    @Test
    void findById() {

        RequestForRequestDto returnRequest = new RequestForRequestDto();

        Mockito.when(requestService.findById(1L, 2L)).thenReturn(returnRequest);
        ResponseEntity<RequestForRequestDto> response = itemRequestController.findById(1L, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());


    }

    @Test
    void findAllWith() {

        List<RequestForRequestDto> returnRequest = List.of(new RequestForRequestDto());

        Mockito.when(requestService.findAllWith(1L, 0, 1)).thenReturn(returnRequest);
        ResponseEntity<List<RequestForRequestDto>> response = itemRequestController.findAllWith(1L, 0, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());
        assertNotNull(response);

    }
}