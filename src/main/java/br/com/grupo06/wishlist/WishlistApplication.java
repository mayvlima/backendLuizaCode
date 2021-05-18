package br.com.grupo06.wishlist;

import br.com.grupo06.wishlist.config.SwaggerConfig;
import br.com.grupo06.wishlist.controller.ClienteController;
import br.com.grupo06.wishlist.domain.entity.ClienteEntity;
import br.com.grupo06.wishlist.service.ClienteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackageClasses =
		{		ClienteEntity.class,
				ClienteService.class,
				ClienteController.class,
				SwaggerConfig.class,

		})
@EnableJpaRepositories(basePackages = {
		"br.com.grupo06.wishlist.domain.repository.**"
})

@SpringBootApplication
public class WishlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}

}
