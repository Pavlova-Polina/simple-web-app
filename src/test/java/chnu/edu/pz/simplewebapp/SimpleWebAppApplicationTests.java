package chnu.edu.pz.simplewebapp;

import chnu.edu.pz.simplewebapp.model.Item;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SimpleWebAppApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url() {
        return "http://localhost:" + port + "/api/items";
    }

    @Test
    @Order(1)
    void createItem() {
        Item item = new Item(null, "Laptop", 999.99);
        ResponseEntity<Item> response = restTemplate.postForEntity(url(), item, Item.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.getId());
        assertEquals("Laptop", body.getName());
        System.out.println("✅ POST: " + body);
    }

    @Test
    @Order(2)
    void getAllItems() {
        ResponseEntity<Item[]> response = restTemplate.getForEntity(url(), Item[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item[] body = response.getBody();
        assertNotNull(body);
        System.out.println("✅ GET ALL: " + body.length + " items");
    }

    @Test
    @Order(3)
    void getItemById() {
        Item item = new Item(null, "Phone", 499.99);
        Item created = restTemplate.postForEntity(url(), item, Item.class).getBody();
        assertNotNull(created);
        Long id = created.getId();
        assertNotNull(id);
        ResponseEntity<Item> response = restTemplate.getForEntity(url() + "/" + id, Item.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item body = response.getBody();
        assertNotNull(body);
        assertEquals(id, body.getId());
        System.out.println("✅ GET BY ID: " + body);
    }

    @Test
    @Order(4)
    void updateItem() {
        Item item = new Item(null, "Tablet", 299.99);
        Item created = restTemplate.postForEntity(url(), item, Item.class).getBody();
        assertNotNull(created);
        Long id = created.getId();
        assertNotNull(id);
        Item updated = new Item(null, "Tablet Pro", 599.99);
        HttpEntity<Item> request = new HttpEntity<>(updated);
        ResponseEntity<Item> response = restTemplate.exchange(url() + "/" + id, HttpMethod.PUT, request, Item.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Item body = response.getBody();
        assertNotNull(body);
        assertEquals("Tablet Pro", body.getName());
        System.out.println("✅ PUT: " + body);
    }

    @Test
    @Order(5)
    void deleteItem() {
        Item item = new Item(null, "Monitor", 199.99);
        Item created = restTemplate.postForEntity(url(), item, Item.class).getBody();
        assertNotNull(created);
        Long id = created.getId();
        assertNotNull(id);
        restTemplate.delete(url() + "/" + id);
        ResponseEntity<Item> response = restTemplate.getForEntity(url() + "/" + id, Item.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        System.out.println("✅ DELETE: id=" + id + " видалено");
    }
}