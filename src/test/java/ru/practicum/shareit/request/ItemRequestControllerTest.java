package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
        RequestDto requestDto = new RequestDto("description");
        requestDto.setDescription("новая ДТО");
        Mockito.when(requestService.create(requestDto, 1L)).thenReturn(returnRequest);
        RequestForRequestDto response = itemRequestController.createRequest(1L, requestDto);
        assertEquals(returnRequest, response);

    }

    @Test
    void findAll() {

        List<RequestForRequestDto> returnRequest = List.of(new RequestForRequestDto());


        Mockito.when(requestService.findAll(1L)).thenReturn(returnRequest);
        List<RequestForRequestDto> response = itemRequestController.findAll(1L);
        assertEquals(returnRequest, response);
        assertNotNull(response);

    }

    @Test
    void findById() {

        RequestForRequestDto returnRequest = new RequestForRequestDto();

        Mockito.when(requestService.findById(1L, 2L)).thenReturn(returnRequest);
        RequestForRequestDto response = itemRequestController.findById(1L, 2L);
        assertEquals(returnRequest, response);


    }

    @Test
    void findAllWith() {

        List<RequestForRequestDto> returnRequest = List.of(new RequestForRequestDto());

        Mockito.when(requestService.findAllWith(1L, 0, 1)).thenReturn(returnRequest);
        List<RequestForRequestDto> response = itemRequestController.findAllWith(1L, 0, 1);
        assertEquals(returnRequest, response);
        assertNotNull(response);

    }
}