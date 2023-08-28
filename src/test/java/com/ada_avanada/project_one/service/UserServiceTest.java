package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.dto.AddressRequestDTO;
import com.ada_avanada.project_one.dto.UserEditDTO;
import com.ada_avanada.project_one.dto.UserRequestDTO;
import com.ada_avanada.project_one.dto.UserResponseDTO;
import com.ada_avanada.project_one.entity.User;
import com.ada_avanada.project_one.exception.AppException;
import com.ada_avanada.project_one.infra.Role;
import com.ada_avanada.project_one.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    public static final Long ID             = 1L;
    public static final String NAME         = "Tester";
    public static final String USERNAME     = "tester";
    public static final String PASSWORD     = "12345678";
    public static final String EMAIL        = "tester@email.com";
    public static final String PHONE        = "00000000000";
    public static final String NUMBER       = "00";
    public static final String POSTAL_CODE  = "00000000";
    public static final String NAME_EDIT = "Tester Edited";
    public static final String EMAIL_EDIT = "tester.edited@email.com";
    public static final String PHONE_EDIT = "11111111111";
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private User user;
    private Optional<User> userOptional;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;
    private UserEditDTO editDTO;
    private User userEdit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUserTester();
    }

    @Test
    @DisplayName("When create Then return a created user")
    void create() {
        when(userRepository.findByUsernameOrCpfOrEmail(any(), any(), any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        var response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals(UserResponseDTO.class, response.getClass());
        assertEquals(NAME, response.name());
        assertEquals(POSTAL_CODE, response.addresses().get(0).postalCode());
    }

    @Test
    @DisplayName("When create with existing email, cpf or username Then return an app exception")
    void createFail() {
        when(userRepository.findByUsernameOrCpfOrEmail(requestDTO.username(), requestDTO.cpf(), requestDTO.email())).thenReturn(userOptional);
        try {
            service.create(requestDTO);
        } catch (Exception e) {
            assertEquals(AppException.class, e.getClass());
            assertEquals("User already exists.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When find all Then return an list of users")
    void getAll() {
        when(userRepository.findAllByOrderByIdAsc()).thenReturn(List.of(user));
        var response = service.getAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserResponseDTO.class, response.get(0).getClass());
        assertEquals(USERNAME, response.get(0).username());
    }

    @Test
    @DisplayName("When find by id Then return an UserResponseDTO instance")
    void getOne() {
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        var response = service.getOne(ID);

        assertNotNull(response);
        assertEquals(UserResponseDTO.class, response.getClass());
        assertEquals(USERNAME, response.username());
        assertEquals(EMAIL, response.email());
    }

    @Test
    @DisplayName("When find by id Then return an entity not found exception")
    void getOneFail() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            service.getOne(ID);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals("User not found.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When delete Then success")
    void remove() {
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        doNothing().when(userRepository).deleteById(anyLong());
        service.remove(ID);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("When update Then return a updated user")
    void edit() {
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        when(userRepository.save(any())).thenReturn(userEdit);

        var response = service.edit(ID, editDTO);

        assertNotNull(response);
        assertEquals(UserResponseDTO.class, response.getClass());
        assertEquals(NAME_EDIT, response.name());
        assertEquals(EMAIL_EDIT, response.email());
        assertEquals(PHONE_EDIT, response.phone());
    }

    @Test
    @DisplayName("When update with inexist id Then return an entity not found exception")
    void editFailById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            service.edit(ID, editDTO);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
            assertEquals("User not found.", e.getMessage());
        }
    }

    @Test
    @DisplayName("When set admin Then return a user with role admin")
    void setAdmin() {
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        when(userRepository.save(userOptional.get())).thenReturn(user);

        var response = service.setAdmin(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(Role.ADMIN, response.getRole());
    }

    private void startUserTester() {
        var address = new AddressRequestDTO(null, null, NUMBER, POSTAL_CODE, null, null, null);
        this.requestDTO = new UserRequestDTO(NAME, USERNAME, PASSWORD, null, EMAIL, PHONE, address);
        this.user = new User(this.requestDTO);
        this.userOptional = Optional.of(this.user);
        this.editDTO = new UserEditDTO(NAME_EDIT, EMAIL_EDIT, PHONE_EDIT);
        this.userEdit = new User(new UserRequestDTO(NAME_EDIT, USERNAME, PASSWORD, null, EMAIL_EDIT, PHONE_EDIT, address));
    }
}