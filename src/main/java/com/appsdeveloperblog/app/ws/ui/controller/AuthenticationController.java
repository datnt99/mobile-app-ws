package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.ui.model.request.LoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @ApiOperation("User login")
    @ApiResponses(
            @ApiResponse(code = 200
            , message = "Response header"
            , responseHeaders = {
                    @ResponseHeader(name ="authorization"
                    , description = "Bearer <JWT value here>"
                    , response = String.class),
                    @ResponseHeader(name ="userId"
                    , description = "<Public user Id value here>"
                    , response = String.class)
            })
    )
    @PostMapping("/users/login")
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel){
        throw new IllegalStateException("This method should not be called." +
                " This method is implemented by Spring security");
    }
}
