package com.spinner.www.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = -202203623L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final QChatRoom chatRoom;

    public final com.spinner.www.member.entity.QMember member;

    public final StringPath messageContent = createString("messageContent");

    public final NumberPath<Long> messageIdx = createNumber("messageIdx", Long.class);

    public final DatePath<java.time.LocalDate> messageSendDate = createDate("messageSendDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> messageSendTime = createTime("messageSendTime", java.time.LocalTime.class);

    public final EnumPath<com.spinner.www.chat.constants.MessageType> messageType = createEnum("messageType", com.spinner.www.chat.constants.MessageType.class);

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom")) : null;
        this.member = inits.isInitialized("member") ? new com.spinner.www.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

