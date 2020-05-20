package propets.configuration.lostandfound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@RefreshScope
@Getter
@Setter
public class LostAndFoundConfiguration {

	@Value("${authorizationImagga}")
	String authorizationImagga;

	@Value("${requestTags}")
	String requestTags;

	@Value("${requestColors}")
	String requestColors;

	@Value("${requestToken}")
	String requestToken;

}
