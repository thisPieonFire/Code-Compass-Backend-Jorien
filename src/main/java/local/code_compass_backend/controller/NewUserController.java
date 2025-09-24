package local.code_compass_backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import local.code_compass_backend.dto.CreateUserDto;
import local.code_compass_backend.dto.ProfileDto;
import local.code_compass_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserController {

    @Autowired
    private AuthService authService;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record User(String email, String displayName) {}
    private record CreationResponse(User user) {}

    @PostMapping ("/api/users")
    public ResponseEntity<?> createNewUser (@RequestBody CreateUserDto createUserDto) {
        AuthService.CreateNewUser result = authService.createNewUser(createUserDto);
        CreationResponse body  = new CreationResponse(new User(result.email(), result.displayName()));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}

/*def create_user_with_role(email: str, role: str):
    response = supabase.auth.admin.create_user(
        {
            "email": email,
                   "email_confirm": True,
            "user_metadata": {"role": role},
        }
    )
    print("User created:", response)
    return response*/


//gebruiker aanmaken = iemand registreert zich, dan moet de backend een bericht sturen naar SupaBase met "registreer deze gebruiker"
//de UUID die dan terugkomt moet de UserId worden, dit is een 1 op 1 relatie (Dat is dus die PK/FK relatie)
