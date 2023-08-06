package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.model.dto.BookingRequestForItemDto;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto getItemById(long userId, Long itemId) {
        checkUser(userId);
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.isEmpty()) {
            throw new DataNotFoundException("Неверно указан id вещи");
        }
        Item newItem = itemRepository.getReferenceById(itemId);

        ItemDto returnItemDto = ItemMapper.toItemDto(newItem);
        if (newItem.getOwner() != userId) {
            return returnItemDto;
        }
        setNextAndEndBooking(itemId, returnItemDto);
        return returnItemDto;

    }

    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {

        checkUser(userId);

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);

        if (item.getAvailable() == false) {
            throw new ValidationException("не указан статус вещи");
        }
        Item newItem = itemRepository.save(item);
        return ItemMapper.toItemDto(newItem);
    }

    @Transactional
    @Override
    public ItemDto updateItem(ItemDto itemDto, long userId, long itemId) {
        checkUser(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        item.setId(itemId);

        Item oldItem = itemRepository.getReferenceById(itemId);

        if (userId != oldItem.getOwner()) {
            throw new DataNotFoundException("неверно указан id пользователя");
        }
        if (item.getOwner() == null) {
            throw new DataNotFoundException("Не указан id пользователя");
        }
        if (item.getName() != null) oldItem.setName(item.getName());
        if (item.getDescription() != null) oldItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) oldItem.setAvailable(item.getAvailable());

        return ItemMapper.toItemDto(itemRepository.save(oldItem));
    }

    @Transactional
    @Override
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> findAll(Long userId, Integer from1, Integer size1) {
        checkUser(userId);

        if (from1 == null) {
            from1 = 0;
        }
        if (size1 == null) {
            size1 = itemRepository.findAllByOwner(userId).size();
        }

        if (from1 < 0 || size1 <= 0) {
            throw new UnavalibleException("не верно заданы параметры для вывода страницы");
        }
        Pageable page = PageRequest.of(from1, size1);

        List<Item> itemList = itemRepository.findAllByOwner(userId, page);
        List<ItemDto> itemDtos = ItemMapper.toListItemDto(itemList);
        itemDtos.stream()
                .map(itemDto -> setNextAndEndBooking(itemDto.getId(), itemDto))
                .collect(Collectors.toList());

        return itemDtos;
    }

    @Override
    public List<ItemDto> search(String text, Integer from1, Integer size1) {
        if (text.isBlank()) return new ArrayList<>();
        if (from1 == null) {
            from1 = 0;
        }
        if (size1 == null) {
            size1 = 100;
        }

        if (from1 < 0 || size1 <= 0) {
            throw new UnavalibleException("не верно заданы параметры для вывода страницы");
        }
        Pageable page = PageRequest.of(from1, size1);

        return ItemMapper.toListItemDto(itemRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(text, text, page));
    }

    private void checkUser(Long userId) {
        if (userId == null) {
            throw new DataNotFoundException("не указан id пользователя");
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new DataNotFoundException("Не найден id пользователя");
        }
    }

    private ItemDto setNextAndEndBooking(Long itemId, ItemDto itemDto) {
        List<Booking> bookingsNext = bookingRepository.findAllByItem_IdAndStartAfterAndStatusOrderByStartAsc(itemId, LocalDateTime.now(), Status.APPROVED);
        if (!bookingsNext.isEmpty()) {
            BookingRequestForItemDto nextBooking = new BookingRequestForItemDto();
            nextBooking.setId(bookingsNext.get(0).getId());
            nextBooking.setBookerId(bookingsNext.get(0).getBooker().getId());
            itemDto.setNextBooking(nextBooking);
        }
        List<Booking> bookings = bookingRepository.findAllByItem_IdAndStartBeforeAndStatusOrderByStartDesc(itemId, LocalDateTime.now(), Status.APPROVED);
        if (!bookings.isEmpty()) {
            BookingRequestForItemDto lastBooking = new BookingRequestForItemDto();
            lastBooking.setId(bookings.get(0).getId());
            lastBooking.setBookerId(bookings.get(0).getBooker().getId());
            itemDto.setLastBooking(lastBooking);
        }
        return itemDto;
    }

    @Transactional
    @Override
    public CommentRequestDto createItemCommentsById(Long userId, Long itemId, CommentDto commentDto) {

        checkUser(userId);
        if (bookingRepository.findAllByBooker_IdAndItem_IdAndEndBefore(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new UnavalibleException("Вы не можее оставить отзыв");
        }
        Comments comments = CommentsMapper.toComments(commentDto);
        comments.setItemId(itemId);
        comments.setAuthorId(userId);
        comments.setAuthorName(userRepository.getReferenceById(userId).getName());

        return CommentsMapper.toCommentsRequestDto(commentsRepository.save(comments));
    }
}
