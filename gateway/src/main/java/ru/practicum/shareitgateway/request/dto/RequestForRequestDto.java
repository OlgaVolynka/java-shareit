package ru.practicum.shareitgateway.request.dto;

import lombok.Data;
import net.sf.saxon.om.Item;
import org.apache.catalina.User;


import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RequestForRequestDto {

    private Long id;
    private String description;
    private LocalDateTime created;
    private User requestMarker;
    private Set<Item> items;

}
