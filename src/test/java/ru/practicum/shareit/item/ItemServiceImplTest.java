package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.exeption.DataNotFoundException;
import ru.practicum.shareit.exeption.UnavalibleException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {


    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentsRepository commentsRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Captor
    private ArgumentCaptor<Comments> commentsArgumentCaptor;

    @BeforeEach
    public void before() {

    }

    @Test
    void getItemById() {

        Long id = 1L;
        Item expectidItem = new Item();
        expectidItem.setOwner(id);
        expectidItem.setAvailable(true);
        User expectidUser = new User();
        ItemDto itemDto = ItemMapper.toItemDto(expectidItem);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(expectidItem));
        Mockito.when(itemRepository.getReferenceById(id)).thenReturn(expectidItem);

        ItemDto expectidItemDto = itemService.getItemById(id, id);

        assertEquals(itemDto, expectidItemDto);
    }

    @Test
    void getItemById_ItemNull() {

        Long id = 1L;
        Item expectidItem = new Item();
        expectidItem.setOwner(id);

        User expectidUser = new User();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> itemService.getItemById(id, id));
    }

    @Test
    void create_Correct() {

        Item expectidItem = new Item();
        expectidItem.setAvailable(true);
        User expectidUser = new User();
        ItemDto itemDto = ItemMapper.toItemDto(expectidItem);
        Long id = 1L;

        Mockito.when(itemRepository.save(expectidItem)).thenReturn(expectidItem);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));

        ItemDto expectidItemDto = itemService.create(itemDto, id);

        assertEquals(itemDto, expectidItemDto);

    }

    @Test
    void create_whenUserNotFound() {

        Item expectidItem = new Item();
        expectidItem.setAvailable(true);
        ItemDto itemDto = ItemMapper.toItemDto(expectidItem);

        Long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> itemService.create(itemDto, id));
    }

    @Test
    void create_whenUserIdNull() {

        Long id = null;

        Item expectidItem = new Item();
        expectidItem.setAvailable(true);
        ItemDto itemDto = ItemMapper.toItemDto(expectidItem);

        assertThrows(DataNotFoundException.class, () -> itemService.create(itemDto, id));
    }

    @Test
    void updateItem() {
        Long id = 1L;
        Item expectidItem = new Item();
        expectidItem.setAvailable(true);
        expectidItem.setOwner(id);
        User expectidUser = new User();
        expectidUser.setId(id);
        expectidItem.setId(id);
        ItemDto itemDto = ItemMapper.toItemDto(expectidItem);


        Mockito.when(itemRepository.save(expectidItem)).thenReturn(expectidItem);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));
        Mockito.when(itemRepository.getReferenceById(id)).thenReturn(expectidItem);

        ItemDto expectidItemDto = itemService.updateItem(itemDto, id, id);

        assertEquals(itemDto, expectidItemDto);

    }

    @Test
    void deleteItemById() {

        Long itemId = 0L;
        itemService.deleteItemById(itemId);

        Mockito.verify(itemRepository).deleteById(itemId);

    }

    @Test
    void findAll() {

        Long id = 1L;

        User expectidUser = new User();
        expectidUser.setId(id);

        Booking booking = new Booking();
        booking.setStatus(Status.APPROVED);
        booking.setBooker(expectidUser);
        List<Booking> bookings = List.of(booking);
        Booking booking1 = new Booking();
        booking1.setBooker(expectidUser);

        Item expectidItem = new Item();
        expectidItem.setId(id);
        expectidItem.setAvailable(true);
        expectidItem.setOwner(id);

        booking.setItem(expectidItem);
        booking1.setItem(expectidItem);

        booking.setBooker(expectidUser);
        booking.setStatus(Status.APPROVED);
        List<Booking> bookings1 = List.of(booking1);


        int from1 = 0;
        int size1 = 1;
        Pageable page = PageRequest.of(from1 / size1, size1);

        expectidItem.setAvailable(true);
        expectidItem.setOwner(id);
        expectidUser.setId(id);
        expectidItem.setId(id);

        List<Item> itemList = List.of(expectidItem);

        Mockito.when(itemRepository.findAllByOwner(id, page)).thenReturn(itemList);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectidUser));
        Mockito.when(bookingRepository.findAllByItem_IdAndStartAfterAndStatusOrderByStartAsc(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Status.class))).thenReturn(bookings);
        Mockito.when(bookingRepository.findAllByItem_IdAndStartBeforeAndStatusOrderByStartDesc(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Status.class))).thenReturn(bookings1);

        List<ItemDto> itemDtoList = itemService.findAll(id, from1, size1);

        assertEquals(itemDtoList.size(), ItemMapper.toListItemDto(itemList).size());

    }

    @Test
    void search() {

        String text = "text";
        Integer from1 = 0;
        Integer size1 = 1;

        Pageable page = PageRequest.of(from1, size1);

        Mockito.when(itemRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndAvailableIsTrue(text, text, page)).thenReturn(List.of(new Item()));

        List<ItemDto> itemDtoList = itemService.search(text, from1, size1);

        assertEquals(itemDtoList.size(), ItemMapper.toListItemDto(List.of(new Item())).size());


    }

    @Test
    void createItemCommentsById_whenCorrect() {

        Long userId = 1L;
        Long itemId = 2L;
        CommentDto commentDto = new CommentDto();
        commentDto.setText("text");

        List<Booking> listBooking = List.of(new Booking());

        Mockito.doReturn(listBooking).when(bookingRepository).findAllByBooker_IdAndItem_IdAndEndBefore(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(LocalDateTime.class));

        User user = new User();
        user.setName("name");
        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(commentsRepository.save(CommentsMapper.toComments(commentDto))).thenReturn(CommentsMapper.toComments(commentDto));

        itemService.createItemCommentsById(userId, itemId, commentDto);

        Mockito.verify(commentsRepository).save(commentsArgumentCaptor.capture());
        Comments saveComment = commentsArgumentCaptor.getValue();

        assertEquals(userId, saveComment.getAuthorId());
        assertEquals(itemId, saveComment.getItemId());
        assertEquals("name", saveComment.getAuthorName());

    }

    @Test
    void createItemCommentsById_whenNotCorrect() {

        Long userId = 1L;
        Long itemId = 2L;
        CommentDto commentDto = new CommentDto();
        commentDto.setText("text");

        List<Booking> listBooking = new ArrayList<>();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        Mockito.doReturn(listBooking).when(bookingRepository).findAllByBooker_IdAndItem_IdAndEndBefore(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(LocalDateTime.class));

        assertThrows(UnavalibleException.class, () -> itemService.createItemCommentsById(userId, itemId, commentDto));

    }
}