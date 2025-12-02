package com.gamehub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CognitoService {

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.region}")
    private String region;

    private final CognitoIdentityProviderClient cognitoClient;

    public CognitoService() {
        this.cognitoClient = CognitoIdentityProviderClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    /**
     * Sign up a new user in Cognito
     *
     * @param email    User's email
     * @param password User's password
     * @param fullName User's full name
     * @return True if signup was successful
     */
    public boolean signUpUser(String email, String password, String fullName) {
        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                    .clientId(clientId)
                    .username(email)
                    .password(password)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("name").value(fullName).build()
                    )
                    .build();

            SignUpResponse response = cognitoClient.signUp(signUpRequest);
            log.info("User signed up successfully: {}", email);
            return response.userConfirmed();
        } catch (Exception e) {
            log.error("Error signing up user: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Confirm user registration with confirmation code
     *
     * @param email            User's email
     * @param confirmationCode Confirmation code sent to user's email
     * @return True if confirmation was successful
     */
    public boolean confirmUser(String email, String confirmationCode) {
        try {
            ConfirmSignUpRequest confirmRequest = ConfirmSignUpRequest.builder()
                    .clientId(clientId)
                    .username(email)
                    .confirmationCode(confirmationCode)
                    .build();

            cognitoClient.confirmSignUp(confirmRequest);
            log.info("User confirmed successfully: {}", email);
            return true;
        } catch (Exception e) {
            log.error("Error confirming user: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Authenticate user with email and password
     *
     * @param email    User's email
     * @param password User's password
     * @return Authentication result containing access token and user details
     */
    public AdminInitiateAuthResponse authenticateUser(String email, String password) {
        try {
            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .userPoolId(userPoolId)
                    .clientId(clientId)
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .authParameters(
                            java.util.Map.of(
                                    "USERNAME", email,
                                    "PASSWORD", password
                            )
                    )
                    .build();

            AdminInitiateAuthResponse authResult = cognitoClient.adminInitiateAuth(authRequest);
            log.info("User authenticated successfully: {}", email);
            return authResult;
        } catch (Exception e) {
            log.error("Error authenticating user: {}", e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    /**
     * Get user details from Cognito
     *
     * @param accessToken Access token from authentication
     * @return User details
     */
    public GetUserResponse getUserDetails(String accessToken) {
        try {
            GetUserRequest getUserRequest = GetUserRequest.builder()
                    .accessToken(accessToken)
                    .build();

            return cognitoClient.getUser(getUserRequest);
        } catch (Exception e) {
            log.error("Error getting user details: {}", e.getMessage());
            throw new RuntimeException("Failed to get user details: " + e.getMessage());
        }
    }
}