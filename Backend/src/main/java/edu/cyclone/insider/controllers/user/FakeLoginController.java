package edu.cyclone.insider.controllers.user;

import edu.cyclone.insider.controllers.user.models.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FakeLoginController {
    @ApiOperation("Login")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody LoginRequestModel body) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
