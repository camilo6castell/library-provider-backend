package com.libraryproviderbackend.data;

import com.libraryproviderbackend.JSONMapper;
import com.libraryproviderbackend.generic.DomainEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa un evento guardado en la base de datos MongoDB.
 * Esta clase actúa como un envoltorio para los eventos de dominio,
 * serializándolos y deserializándolos cuando es necesario.
 */
@Document(collection = "events")
public class EventSaved implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(EventSaved.class);

    @Id
    private String id;
    private String aggregateRootId;
    private String type;
    private LocalDateTime occurredOn;
    private String body;

    /**
     * Constructor vacío necesario para la deserialización de objetos.
     */
    public EventSaved() {
    }

    /**
     * Constructor con parámetros para crear una instancia de EventSaved.
     *
     * @param aggregateRootId el ID del agregado raíz.
     * @param type            el tipo de evento (clase de evento).
     * @param occurredOn      la fecha y hora en que ocurrió el evento.
     * @param body            el cuerpo del evento, que es el evento de dominio serializado.
     */
    public EventSaved(String aggregateRootId, String type, LocalDateTime occurredOn, String body) {
        this.aggregateRootId = aggregateRootId;
        this.type = type;
        this.occurredOn = occurredOn;
        this.body = body;
    }

    /**
     * Serializa un evento de dominio a un formato JSON.
     *
     * @param domainEvent    el evento de dominio a serializar.
     * @param eventSerializer el serializador utilizado para convertir el evento a JSON.
     * @return una cadena JSON que representa el evento serializado.
     */
    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer) {
        try {
            return eventSerializer.writeToJson(domainEvent);
        } catch (Exception e) {
            logger.error("Failed to serialize event", e);
            throw new RuntimeException("Failed to serialize event", e);
        }
    }

    /**
     * Deserializa el cuerpo JSON de este evento guardado en un evento de dominio.
     *
     * @param eventSerializer el serializador utilizado para convertir JSON a un evento de dominio.
     * @return el evento de dominio deserializado.
     */
    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
        try {
            return (DomainEvent) eventSerializer.readFromJson(this.getBody(), Class.forName(this.getType()));
        } catch (ClassNotFoundException e) {
            logger.error("Failed to deserialize event", e);
            return null;
        }
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(LocalDateTime occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

//@Document
//public class EventSaved implements Serializable {
//
//    private static final Logger logger = LoggerFactory.getLogger(EventSaved.class);
//
//
//    @Id
//    public String aggregateRootId;
//    public String type;
//    public LocalDateTime occurredOn;
//    public String body;
//
//    public EventSaved() {
//    }
//
//    public EventSaved(
//            String aggregateRootId,
//            String type,
//            LocalDateTime occurredOn,
//            String body
//    ) {
//        this.aggregateRootId = aggregateRootId;
//        this.type = type;
//        this.occurredOn = occurredOn;
//        this.body = body;
//    }
//
//    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer){
//        return eventSerializer.writeToJson(domainEvent);
//    }
//
//
////    public static String wrapEvent(DomainEvent event, IJSONMapper eventSerializer) {
////        try {
////            return eventSerializer.serialize(event);
////        } catch (JsonProcessingException e) {
////            throw new RuntimeException("Failed to serialize event", e);
////        }
////    }
//
////    public Event deserializeEvent(IJSONMapper eventSerializer) {
////        try {
////            return (Event) eventSerializer
////                    .deserialize(this.getBody(), Class.forName(this.getType()));
////        } catch (ClassNotFoundException e) {
////            return null;
////        }
////    }
//
//    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
//        try {
//            return (DomainEvent) eventSerializer
//                    .readFromJson(this.getBody(), Class.forName(this.getType()));
//        } catch (ClassNotFoundException e) {
//            return null;
//        }
//    }
//
////    public DomainEvent deserializeEvent(IJSONMapper eventSerializer) {
////        try {
////            return (DomainEvent) eventSerializer.deserialize(this.getBody(), Class.forName(this.getType()));
////        } catch (JsonProcessingException e) {
////            throw new RuntimeException("Failed to deserialize event", e);
////        } catch (ClassNotFoundException e) {
////            return null;
////        }
////    }
//
////    public static String wrapEvent(Event domainEvent, IJSONMapper eventSerializer){
////        return eventSerializer.serialize(domainEvent);
////    }
//
//    public String getAggregateRootId() {
//        return aggregateRootId;
//    }
//
//    public void setAggregateRootId(String aggregateRootId) {
//        this.aggregateRootId = aggregateRootId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public LocalDateTime getOccurredOn() {
//        return occurredOn;
//    }
//
//    public void setOccurredOn(LocalDateTime occurredOn) {
//        this.occurredOn = occurredOn;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public interface EventSerializer {
//        <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType);
//
//        String serialize(DomainEvent object);
//    }
//}
