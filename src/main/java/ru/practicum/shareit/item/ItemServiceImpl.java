package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingRequestForItemDto;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.exeption.WithoutXSharerUserId;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comments;
import ru.practicum.shareit.item.model.CommentsRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentsRepository commentsRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto getItemById(Long userId, Long itemId) {
        checkUser(userId);
        Optional<Item> item = itemRepository.findById(itemId);

        if (item.isEmpty()) {
            throw new DataNotFoundException("Неверно указан id вещи");
        }
        try {
            Item newItem = itemRepository.getReferenceById(itemId);

            ItemDto returnItemDto = ItemMapper.toItemDto(newItem);
            if (newItem.getOwner() != userId) {
                return returnItemDto;
            }
            setNextAndEndBooking(itemId, returnItemDto);
            return returnItemDto;
        } catch (
                DataIntegrityViolationException e) {
            throw new ValidationException("неверно указан пользователь");
        }
    }

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

    @Override
    public Item updateItem(ItemDto itemDto, long userId, long itemId) {
        checkUser(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(userId);
        item.setId(itemId);

        Item oldItem = itemRepository.getReferenceById(itemId);

        if (userId != oldItem.getOwner()) {
            throw new DataNotFoundException("неверно указан id пользователя");
        }
        if (item.getOwner() == null) {
            throw new WithoutXSharerUserId("Не указан id пользователя");
        }
        if (item.getName() != null) oldItem.setName(item.getName());
        if (item.getDescription() != null) oldItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) oldItem.setAvailable(item.getAvailable());

        return itemRepository.save(oldItem);
    }

    @Override
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> findAll(Long userId) {
        checkUser(userId);
        List<Item> itemList = itemRepository.findAllByOwner(userId);
        List<ItemDto> itemDtos = ItemMapper.toListItemDto(itemList);
        itemDtos.stream()
                .map(itemDto -> setNextAndEndBooking(itemDto.getId(), itemDto))
                .collect(Collectors.toList());

        return itemDtos;
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank()) return new ArrayList<>();
        return itemRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(text, text);
    }

    private void checkUser(Long userId) {
        if (userId == null) {
            throw new DataNotFoundException("не указан id пользователя");
        }
        if (userRepository.findAllById(Collections.singleton(userId)).isEmpty()) {
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

    @Override
    public Comments getItemCommentsById(Long userId, Long itemId, Comments comments) {

        checkUser(userId);
        if (bookingRepository.findAllByBooker_IdAndItem_IdAndEndBefore(userId, itemId, LocalDateTime.now()).isEmpty()) {
            throw new UnavalibleException("Вы не можее оставить отзыв");
        }
        comments.setItemId(itemId);
        comments.setAuthorId(userId);
        comments.setAuthorName(userRepository.getReferenceById(userId).getName());

        return commentsRepository.save(comments);
    }
}
