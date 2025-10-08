package local.code_compass_backend.controller;
import com.fasterxml.jackson.annotation.JsonInclude;
import local.code_compass_backend.dto.CreateUserDto;
import local.code_compass_backend.dto.NewUserResult;
import local.code_compass_backend.service.AuthService;
import local.code_compass_backend.service.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private NewUserService newUserService;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record User(String email, String displayName) {}
    private record CreationResponse(User user) {}

    @PostMapping ("/api/users")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@RequestBody CreateUserDto createUserDto) {
        NewUserResult result = authService.createNewUser(createUserDto);
        newUserService.saveNewUserProfile(result.getRegisterResponseDto());
        CreationResponse body = new CreationResponse(
                new User(result.getCreateNewUser().email(), result.getCreateNewUser().displayName())
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}