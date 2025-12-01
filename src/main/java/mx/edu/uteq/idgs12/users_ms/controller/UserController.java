package mx.edu.uteq.idgs12.users_ms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.idgs12.users_ms.dto.ChangePasswordDTO;
import mx.edu.uteq.idgs12.users_ms.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordDTO dto) {
        boolean changed = userService.changePassword(id, dto);
        if (changed) {
            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "Invalid current password or user not found"));
        }
    }
}
