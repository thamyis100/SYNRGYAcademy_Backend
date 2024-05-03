package synrgy7thapmoch4.Services.UserService;


import synrgy7thapmoch4.Entity.User;

public interface UserService {

    // Method untuk membuat user baru
    void createUser(Long userId, String userName, String userEmail);

    // Method untuk membaca informasi user berdasarkan ID
    User readUser(Long userId);

    // Method untuk memperbarui informasi user
    void updateUser(Long userId, String userName, String userEmail);

    // Method untuk menghapus user berdasarkan ID
    void deleteUser(Long userId);
}
