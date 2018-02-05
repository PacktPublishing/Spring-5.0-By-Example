package springfive.airline.airlinefare.infra.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessToken {

    @JsonProperty("access_token")
    String token;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("expires_in")
    Integer expiresIn;

    String scope;

    String jti;

}
