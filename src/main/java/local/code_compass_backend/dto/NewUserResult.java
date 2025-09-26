package local.code_compass_backend.dto;

public class NewUserResult {
    private final RegisterResponseDto registerResponseDto;
    private final CreateNewUser createNewUser;

    public NewUserResult(RegisterResponseDto registerResponseDto) {
        this.registerResponseDto = registerResponseDto;
        this.createNewUser = new CreateNewUser(
                registerResponseDto.getEmail(),
                registerResponseDto.getUserMetadata().getDisplayName()
        );
    }

    public RegisterResponseDto getRegisterResponseDto() {
        return registerResponseDto;
    }

    public CreateNewUser getCreateNewUser() {
        return createNewUser;
    }
}
