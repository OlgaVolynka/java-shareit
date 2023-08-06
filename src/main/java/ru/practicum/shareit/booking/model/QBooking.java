package ru.practicum.shareit.booking.model;

import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBooking extends EntityPathBase<Booking> {

    private static final long serialVersionUID = 2084451991L;

    public static final QBooking item = new QBooking("booking");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<String, StringPath> tags = this.<String, StringPath>createSet("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath url = createString("url");

    public final NumberPath<Long> userId = createNumber("booker", Long.class);

    public QBooking(String variable) {
        super(Booking.class, forVariable(variable));
    }

}

