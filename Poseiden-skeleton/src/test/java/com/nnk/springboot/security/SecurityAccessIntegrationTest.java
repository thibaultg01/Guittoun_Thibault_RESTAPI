package com.nnk.springboot.security;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.repositories.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SecurityAccessIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
    @BeforeAll
    void setupUsers() {
        userRepository.deleteAll();

        var admin = new com.nnk.springboot.model.User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("Admin123!"));
        admin.setFullname("Admin User");
        admin.setRole("ADMIN");

        var user = new com.nnk.springboot.model.User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("User123!"));
        user.setFullname("Normal User");
        user.setRole("USER");

        userRepository.save(admin);
        userRepository.save(user);
    }

    @Test
    @WithUserDetails("admin")
    void adminShouldAccessUserList() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user")
    void userShouldNotAccessUserList() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("user")
    void userShouldAccessBidList() throws Exception {
        mockMvc.perform(get("/bidList/list"))
               .andExpect(status().isOk());
    }

    @Test
    void anonymousShouldBeRedirectedToLogin() throws Exception {
        mockMvc.perform(get("/bidList/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void loginPage_shouldBePublic() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void userShouldNotAccessAdminPage() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().isForbidden());
    }

    @Test
    void accessToProtectedPageWithoutLogin_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/bidList/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void accessToUserListWithoutLogin_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrlPattern("**/login"));
    }
}