package com.diro.ift2255.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.diro.ift2255.model.User;

public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    public UserService() {
        // Mock users
        users.put(nextId, new User(nextId++, "Alice", "alice@example.com"));
        users.put(nextId, new User(nextId++, "Bob", "bob@example.com"));
    }

    /** 
     * recupere tous els utilisateurs
     * @return une liste d'utilisateurs
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    /** 
     * recupere un utilisateur par son identifiant
     * @param id l'identifiant de l'utilisateur
     * @return un appel à la fonction Optional pour un utilisateur
     */
    public Optional<User> getUserById(int id) {
        try {
            User user = users.get(id);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /** 
     * cree un nouvel utilisateur
     * @param user l'utilisateur à creer
     */
    public void createUser(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);
    }

    /** 
     * mise à jour d'un utilisateur
     * @param id l'identifiant de l'utilisateur
     * @param updated l'utilisateur mis a jour
     */
    public void updateUser(int id, User updated) {
        updated.setId(id);
        users.put(id, updated);
    }

    /** 
     * supprime l'utilisateur par son identifiant
     * @param id l'identifiant de l'utlisateur
     */
    public void deleteUser(int id) {
        users.remove(id);
    }
}
