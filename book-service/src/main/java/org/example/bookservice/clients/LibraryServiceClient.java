package org.example.bookservice.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class LibraryServiceClient {
    private final WebClient webClient;

    public LibraryServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void addBookToLibrary(Long bookId) {
        try {
            System.out.println("Sending request to library service to add book with ID: " + bookId);
            String response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/library/add")
                            .queryParam("bookId", bookId)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Response from library-service: " + response);
        } catch (WebClientResponseException e) {
            System.err.println("HTTP error: " + e.getStatusCode());
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteBookFromLibrary(Long bookId) {
        try {
            String response = webClient.delete()
                    .uri(uriBuilder -> uriBuilder
                            .path("/library/delete")
                            .queryParam("bookId", bookId)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Response from library-service: " + response);
        } catch (WebClientResponseException e) {
            System.err.println("HTTP error: " + e.getStatusCode());
            System.err.println("Error message: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
            throw e;
        }
    }
}
