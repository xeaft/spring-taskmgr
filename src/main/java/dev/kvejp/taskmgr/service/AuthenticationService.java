package dev.kvejp.taskmgr.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean isAuthenticated() { // TODO: implement a working version
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        return authentication != null && authentication.isAuthenticated();
        return false;
    }
}