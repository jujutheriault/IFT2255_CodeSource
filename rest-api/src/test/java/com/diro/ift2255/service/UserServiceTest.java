package com.diro.ift2255.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;

import com.diro.ift2255.model.User;

public class UserServiceTest {
    private UserService userService;
    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("UserService Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        userService = new UserService();
        testStartTime = System.currentTimeMillis();

        System.out.println("\nTEST: " + testInfo.getDisplayName());
        System.out.println("    ├─ Method: " + testInfo.getTestMethod().get().getName());
        System.out.println("    ├─ Assertions:");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("    └─ Duration: " + duration + " ms");
    }

    @Test
    @DisplayName("Get all users should return 2 mock users")
    void testGetAllUsers() {
        // ARRANGE & ACT
        List<User> users = userService.getAllUsers();

        // ASSERT
        try {
            assertEquals(2, users.size(), "Should have 2 mock users");
            OK("Retrieved " + users.size() + " users as expected");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get user by ID should return the correct user when found")
    void testGetUserByIdFound() {
        // ARRANGE
        int userId = 1; // Assuming user with ID 1 exists in mock data

        // ACT
        Optional<User> user = userService.getUserById(userId);

        // ASSERT
        try {
            assertTrue(user.isPresent(), "User with ID 1 should exist");
            OK("User with ID 1 exists", false);

            assertEquals("Alice", user.get().getName(), "User name should be Alice");
            OK("Retrieved user: " + user.get().getName());
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get user by ID should return empty when user is not found")
    void testGetUserByIdNotFound() {
        // ARRANGE 
        int nonexistentUserId = 999;

        // ACT
        Optional<User> user = userService.getUserById(nonexistentUserId);

        // ASSERT
        try {
            assertTrue(user.isEmpty(), "User with ID 999 should not exist");
            OK("No user found as expected");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e; 
        }
    }

    @Test
    @DisplayName("Create user should add a new user with auto-incremented ID")
    void testCreateUser() {
        // ARRANGE
        User newUser = new User(0, "Vincent", "vincent@exemple.com");

        // ACT
        userService.createUser(newUser);

        // ASSERT
        try {
            List<User> users = userService.getAllUsers();
            assertEquals(3, users.size(), "There should now be 3 users");
            OK("User count is now 3", false);

            assertTrue(users.stream().anyMatch(u -> u.getName().equals("Vincent")),
                    "Created user should be present");
            OK("Vincent was successfully added");
        }   catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Update user should replace existing user's data")
    void testUpdateUser() {
        //ARRANGE
        User updated = new User(0,"VincentUpdated","newVincentemail@exemple.com");

        //ACT
        userService.updateUser(1,updated);

        //ASSERT
        try {
            Optional<User> user = userService.getUserById(1);

            assertTrue(user.isPresent(),"User should still exist");
            OK("User 1 found",false);

            assertEquals("VincentUpdated",user.get().getName());
            OK("User name updated as expected");

        } catch(AssertionError e){

            Err(e.getMessage());
            throw e;
        }

        }


    @Test
    @DisplayName("Delete user should remove user from the system")
    void testDeleteUser() {
        //ACT
        userService.deleteUser(1);


        //ASSERT
        try {
            Optional<User> user = userService.getUserById(1);

            assertTrue(user.isEmpty(), "User 1 should be deleted");
            OK("User 1 successfully deleted");
        
        } catch(AssertionError e){

            Err(e.getMessage());
            throw e;
        }

    }


    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: UserService Tests");
        System.out.println("=".repeat(80) + "\n");
    }


    // ------------------------------------------------------------------------
    // Helper methods for logging
    // ------------------------------------------------------------------------

    private void printMessage(String message, boolean isOk, boolean isLast) {
        String symbol = isLast ? "└─" : "├─";
        String status = isOk ? "[PASS]" : "[FAIL]";
        System.out.println("    │   " + symbol + " " + status + " " + message);
    }

    private void OK(String message) {
        printMessage(message, true, true);
    }

    private void OK(String message, boolean isLast) {
        printMessage(message, true, isLast);
    }

    private void Err(String message) {
        printMessage(message, false, true);
    }

    private void Err(String message, boolean isLast) {
        printMessage(message, false, isLast);
    }
}