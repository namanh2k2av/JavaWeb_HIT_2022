package com.hit.btvn_buoi3;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public static List<User> userList = new ArrayList<>();
    static {
        userList.add(new User(1, "namanh1", "123", "Nguyễn Lương Nam Anh"));
        userList.add(new User(2, "namanh2", "123", "Nguyễn Lương Nam Anh"));
        userList.add(new User(3, "namanh3", "123", "Nguyễn Lương Nam Anh"));
        userList.add(new User(4, "namanh4", "123", "Nguyễn Lương Nam Anh"));
        userList.add(new User(5, "namanh5", "123", "Nguyễn Lương Nam Anh"));
    }

    public static boolean checkAccount(User user) {
        for (User u : userList) {
            if (user.equals(u)) {
                return true;
            }
        }
        return false;
    }
}
