package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.model.Comments;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;


    @Test
    void itemDtoJsonTest() throws IOException {

        ItemDto item = new ItemDto();
        List<Comments> commentsList = List.of(new Comments());

        item.setAvailable(true);
        item.setDescription("Description");
        item.setName("name");
        item.setId(1L);
        item.setComments(commentsList);
        item.setRequestId(1L);


        JsonContent<ItemDto> result = json.write(item);

        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Description");

        assertThat(result).extractingJsonPathNumberValue("$.requestId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("name");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);

    }
}