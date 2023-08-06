package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @Mock
    ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    void findAll() {
        ItemDto newItem = new ItemDto();
        newItem.setName("Name");
        newItem.setAvailable(true);
        newItem.setDescription("Description");

        List<ItemDto> returnRequest = List.of(newItem);

        Mockito.when(itemService.findAll( 1L, 0,1)).thenReturn(returnRequest);
        ResponseEntity<List<ItemDto>> response = itemController.findAll(1L, 0, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());
        assertNotNull(response);

        Mockito.when(itemService.findAll( 1L, null, null)).thenReturn(returnRequest);
        ResponseEntity<List<ItemDto>> response2 = itemController.findAll(1L, null, null);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(returnRequest, response2.getBody());
        assertNotNull(response2);

    }

    @Test
    void search() {

        ItemDto newItem = new ItemDto();
        newItem.setName("Name");
        newItem.setAvailable(true);
        newItem.setDescription("Description");

        List<ItemDto> returnRequest = List.of(newItem);

        Mockito.when(itemService.search( "text", 0,1)).thenReturn(returnRequest);
        ResponseEntity<List<ItemDto>> response = itemController.search("text", 0, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnRequest, response.getBody());
        assertNotNull(response);

        Mockito.when(itemService.search( "text", null, null)).thenReturn(returnRequest);
        ResponseEntity<List<ItemDto>> response2 = itemController.search("text", null, null);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(returnRequest, response2.getBody());
        assertNotNull(response2);

    }

    @Test
    void createItem() {

        ItemDto newItem = new ItemDto();
        newItem.setName("Name");
        newItem.setAvailable(true);
        newItem.setDescription("Description");
        Mockito.when(itemService.create(new ItemDto(), 1L)).thenReturn(newItem);
        ResponseEntity<ItemDto> response = itemController.createItem(1L, new ItemDto());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newItem, response.getBody());
        assertNotNull(response);

    }

    @Test
    void updateItem() {
        ItemDto newItem = new ItemDto();
        newItem.setId(1L);
        newItem.setName("Name");
        newItem.setAvailable(true);
        newItem.setDescription("Description");
        Mockito.when(itemService.updateItem(new ItemDto(), 1L, 2L)).thenReturn(newItem);
        ResponseEntity<ItemDto> response = itemController.updateItem(1L, new ItemDto(), 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newItem, response.getBody());
        assertNotNull(response);
    }

    @Test
    void getItemById() {

        ItemDto newItem = new ItemDto();
        newItem.setId(1L);
        newItem.setName("Name");
        newItem.setAvailable(true);
        newItem.setDescription("Description");
        Mockito.when(itemService.getItemById( 1L, 2L)).thenReturn(newItem);
        ResponseEntity<ItemDto> response = itemController.getItemById(1L,  2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newItem, response.getBody());
        assertNotNull(response);

    }

    @Test
    void deleteItemById() {

        itemController.deleteItemById(1L);

    }

    @Test
    void getItemCommentsById() {

        CommentDto commentDto = new CommentDto();
        commentDto.setText("text");
        CommentRequestDto commentRequestDto = new CommentRequestDto();

        Mockito.when(itemService.createItemCommentsById( 1L, 2L, commentDto)).thenReturn(commentRequestDto);
        ResponseEntity<CommentRequestDto> response = itemController.getItemCommentsById(1L,  2L, commentDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commentRequestDto, response.getBody());
        assertNotNull(response);

    }
}