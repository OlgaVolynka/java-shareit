package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestForRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @Test
    void create_whenUserFound() {

        User expectidUser = new User();
        RequestDto requestDto = new RequestDto("description");
        Request request = RequestMapper.toRequest(requestDto);
        Long id = 1L;

        Mockito.when(userRepository.getReferenceById(id)).thenReturn(expectidUser);
        Mockito.when(requestRepository.save(request)).thenReturn(request);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        RequestForRequestDto requestForRequestDto = requestService.create(requestDto, id);

        assertEquals(requestForRequestDto, RequestMapper.requestForRequestDto(request));

    }

    @Test
    void create_whenUserNotFound() {


        RequestDto requestDto = new RequestDto("description");

        Long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> requestService.create(requestDto, id));

    }

    @Test
    void create_whenUserIdNull() {


        RequestDto requestDto = new RequestDto("description");

        Long id = null;

        assertThrows(DataNotFoundException.class, () -> requestService.create(requestDto, id));

    }


    @Test
    void findAll() {

        User expectidUser = new User();
        RequestDto requestDto = new RequestDto("description");
        Request request = RequestMapper.toRequest(requestDto);
        List<Request> requestList = List.of(request);
        Long id = 1L;
        Sort sort = Sort.by("created").descending();

        Mockito.when(requestRepository.findAllByRequestMarker_Id(id, sort)).thenReturn(requestList);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        List<RequestForRequestDto> requestForRequestDto = requestService.findAll(id);

        assertEquals(requestForRequestDto, RequestMapper.toListRequestDto(requestList));

    }

    @Test
    void findById_WhenRequestFound() {
        User expectidUser = new User();
        RequestDto requestDto = new RequestDto("description");
        Request request = RequestMapper.toRequest(requestDto);
        Long id = 1L;

        Mockito.when(requestRepository.getReferenceById(id)).thenReturn(request);

        Mockito.when(requestRepository.findById(id)).thenReturn(Optional.of(request));

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        RequestForRequestDto requestForRequestDto = requestService.findById(id, id);

        assertEquals(requestForRequestDto, RequestMapper.requestForRequestDto(request));

    }

    @Test
    void findById_WhenRequestNotFound() {
        User expectidUser = new User();

        Long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        Mockito.when(requestRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> requestService.findById(id, id));

    }

    @Test
    void findAllWith_whenFromSizeCorrect() {

        User expectidUser = new User();
        RequestDto requestDto = new RequestDto("description");
        Request request = RequestMapper.toRequest(requestDto);
        List<Request> requestList = List.of(request);
        Long id = 1L;

        Sort sort = Sort.by("created").descending();
        int from1 = 0;
        int size1 = 1;
        Pageable page = PageRequest.of(from1 / size1, size1, sort);


        Mockito.when(requestRepository.findAllByRequestMarker_IdNot(id, page)).thenReturn(requestList);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        List<RequestForRequestDto> requestForRequestDto = requestService.findAllWith(id, from1, size1);

        assertEquals(requestForRequestDto, RequestMapper.toListRequestDto(requestList));


    }
}