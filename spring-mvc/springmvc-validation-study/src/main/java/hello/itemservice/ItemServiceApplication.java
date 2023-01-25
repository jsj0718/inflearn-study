package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication { //implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

/*
	// 글로벌 Validator 설정은 WebMvcConfigurer을 구현 후 getValidator()를 Override하면 된다.
	@Override
	public Validator getValidator() {
		return new ItemValidator();
	}
*/
}
