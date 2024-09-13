package org.hyundai.backend.client;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hyundai.backend.exception.MyNotFoundException;
import org.hyundai.backend.utils.MyErrorResponse;
import org.hyundai.backend.utils.MyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    // Search a client by keyword with pagination
    public MyResponse search(String keyword, Integer page, Integer size) {
        // Create a new page request
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get page of clients from the database
        Page<Client> clientsPage = clientRepository.search(keyword, pageable);
        // Return list of client DTO
        if (clientsPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No clients found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            List<ClientDTO> clients = clientsPage.stream().map(ClientDTO::fromEntity).collect(Collectors.toList());
            // Pagination infos
            Map<String, Object> meta = Map.of(
                    "currentPage", clientsPage.getNumber() + 1,
                    "totalItems", clientsPage.getTotalElements(),
                    "totalPages", clientsPage.getTotalPages(),
                    "size", size);
            return MyResponse.builder()
                    .data(clients)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    // Get list of clients with pagination
    public MyResponse list(Integer page, Integer size) {
        // Create a new page request
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get page of clients from the database
        Page<Client> clientsPage = clientRepository.findAll(pageable);
        // Return list of client DTO
        if (clientsPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No clients found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            List<ClientDTO> clients = clientsPage.stream().map(ClientDTO::fromEntity).collect(Collectors.toList());
            // Pagination infos
            Map<String, Object> meta = Map.of(
                    "size", size,
                    "currentPage", clientsPage.getNumber() + 1,
                    "totalItems", clientsPage.getTotalElements(),
                    "totalPages", clientsPage.getTotalPages());
            return MyResponse.builder()
                    .data(clients)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }

    }

    // Get a client by id
    public MyResponse get(Long id) {
        // Find the client by id
        Client client = this.findById(id);
        // Return client DTO
        return MyResponse.builder()
                .data(ClientDTO.fromEntity(client))
                .status(HttpStatus.OK)
                .build();
    }

    // Create a new client
    public MyResponse create(ClientRequest request) {
        // Build a new client object
        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        // Save the client object to the database
        clientRepository.save(client);
        return MyResponse.builder()
                .message("Client created successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    // Update an existing client
    public MyResponse update(Long id, ClientRequest request) {
        // Find the client by id
        Client client = this.findById(id);
        // Update the client object
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        // Save the client object to the database
        clientRepository.save(client);
        return MyResponse.builder()
                .message("Client updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Delete a client by id
    public MyResponse delete(Long id) {
        // Find the client by id
        Client client = this.findById(id);
        // Delete the client object from the database
        clientRepository.delete(client);
        return MyResponse.builder()
                .message("Client deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Find a client by id
    private Client findById(Long id) throws MyNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new MyNotFoundException(MyErrorResponse.builder()
                            .message("Client not found wtih id: " + id)
                            .build());
                });
    }

}
