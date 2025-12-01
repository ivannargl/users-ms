package mx.edu.uteq.idgs12.users_ms.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs12.users_ms.dto.ChangePasswordDTO;
import mx.edu.uteq.idgs12.users_ms.dto.UserResponseDTO;
import mx.edu.uteq.idgs12.users_ms.entity.User;
import mx.edu.uteq.idgs12.users_ms.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /** Cambiar contraseña */
    public boolean changePassword(Integer userId, ChangePasswordDTO dto) {
        return userRepository.findById(userId).map(user -> {
            // Verificar contraseña actual
            if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
                return false;
            }
            // Guardar nueva contraseña
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    /** Convertir Entity -> DTO */
    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
