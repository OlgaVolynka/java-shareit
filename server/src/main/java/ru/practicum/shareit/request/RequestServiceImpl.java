package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestForRequestDto;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Transactional
    public RequestForRequestDto create(RequestDto requestDto, Long userId) {

        checkUser(userId);

        Request request = RequestMapper.toRequest(requestDto);
        request.setRequestMarker(userRepository.getReferenceById(userId));

        Request newRequest = requestRepository.save(request);
        return RequestMapper.requestForRequestDto(newRequest);
    }

    public List<RequestForRequestDto> findAll(Long userId) {
        checkUser(userId);

        Sort sort = Sort.by("created").descending();
        return RequestMapper.toListRequestDto(requestRepository.findAllByRequestMarker_Id(userId, sort));
    }

    private void checkUser(long userId) {

        userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Не найден пользователь по id=" + userId));

    }

    public RequestForRequestDto findById(long userId, Long requestId) {
        checkUser(userId);
        Optional<Request> request = requestRepository.findById(requestId);

        if (request.isEmpty()) {
            throw new DataNotFoundException("Неверно указан id запроса");
        }

        Request newRequest = requestRepository.getReferenceById(requestId);

        return RequestMapper.requestForRequestDto(newRequest);

    }

    public List<RequestForRequestDto> findAllWith(Long userId, Integer from1, Integer size1) {

        checkUser(userId);
        Sort sort = Sort.by("created").descending();

        Pageable page = PageRequest.of(from1 / size1, size1, sort);

        return RequestMapper.toListRequestDto(requestRepository.findAllByRequestMarker_IdNot(userId, page));
    }
}
