package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		//지연 로딩을 null로 반환
//		return new Hibernate5Module(); 

		//지연 로딩을 DB 조회 후 데이터 반환 -> 엔티티 반환, 성능 등 다양한 문제로 사용 X
		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true); //사용하면 안되는 옵션 (엔티티를 반환하기 때문)
		return hibernate5Module;
	}
}
