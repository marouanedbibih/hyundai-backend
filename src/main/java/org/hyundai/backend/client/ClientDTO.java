package org.hyundai.backend.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // This method is used to convert an entity object to a DTO object
    public static ClientDTO fromEntity(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .build();
    }

    // This method is used to convert a DTO object to an entity object
    public Client toEntity() {
        return Client.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .address(address)
                .build();
    }
}
