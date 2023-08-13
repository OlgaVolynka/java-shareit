package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentRequestDto;

public class CommentsMapper {

    public static Comments toComments(CommentDto commentDto) {
        Comments comments = new Comments();
        comments.setText(commentDto.getText());
        comments.setCreated(commentDto.getCreated());
        return comments;
    }

    public static CommentRequestDto toCommentsRequestDto(Comments comment) {

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText(comment.getText());
        commentRequestDto.setId(comment.getId());
        commentRequestDto.setCreated(comment.getCreated());
        commentRequestDto.setAuthorName(comment.getAuthorName());
        commentRequestDto.setItemId(commentRequestDto.getItemId());
        commentRequestDto.setAuthorId(commentRequestDto.getAuthorId());
        return commentRequestDto;
    }
}
