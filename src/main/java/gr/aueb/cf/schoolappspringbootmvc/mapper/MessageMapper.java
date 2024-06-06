package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.message.*;
import gr.aueb.cf.schoolappspringbootmvc.model.Message;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the Message entity.
 * Contains methods for converting a Message entity to a DTO and vice versa.
 */

@Component
public class MessageMapper {

    @Autowired
    private UserRepository userRepository;

    /**
     * Converts a Message entity to a GetMessageDTO.
     *
     * @param message the Message entity to convert
     * @return the converted GetMessageDTO
     */
    public GetMessageDTO toGetMessageDTO(Message message) {
        GetMessageDTO getMessageDTO = new GetMessageDTO();
        getMessageDTO.setId(message.getId());
        getMessageDTO.setContent(message.getContent());
        getMessageDTO.setTimestamp(message.getTimestamp());
        getMessageDTO.setSenderId(message.getSender().getId());
        getMessageDTO.setSenderUsername(message.getSender().getUsername());
        getMessageDTO.setReceiverId(message.getReceiver().getId());
        getMessageDTO.setReceiverUsername(message.getReceiver().getUsername());
        return getMessageDTO;
    }

    /**
     * Converts a list of Message entities to a list of GetMessageDTOs.
     *
     * @param messages the list of Message entities to convert
     * @return the converted list of GetMessageDTOs
     */
    public List<GetMessageDTO> toGetMessageDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toGetMessageDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a SendMessageDTO to a Message entity.
     *
     * @param sendMessageDTO the SendMessageDTO to convert
     * @return the converted Message entity
     */
    public Message toMessage(SendMessageDTO sendMessageDTO) {
        Message message = new Message();
        message.setContent(sendMessageDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        User sender = userRepository.findById(sendMessageDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(sendMessageDTO.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        message.setSender(sender);
        message.setReceiver(receiver);
        return message;
    }

}
