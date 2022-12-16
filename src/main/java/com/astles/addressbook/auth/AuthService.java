package com.astles.addressbook.auth;

public class AuthService {
    public static Integer getCallingUserId() {
        return 1;
        // In reality, this would be read from a token supplied in the headers of the API request, but I believe this is out of the scope of this task
    }
}
