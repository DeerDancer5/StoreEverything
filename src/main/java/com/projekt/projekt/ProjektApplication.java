package com.projekt.projekt;

import com.projekt.projekt.Repositories.CategoryRepository;
import com.projekt.projekt.Repositories.NoteRepository;
import com.projekt.projekt.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjektApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext =
				SpringApplication.run(ProjektApplication.class, args);
		CategoryRepository categoryRepository=
				configurableApplicationContext.getBean(CategoryRepository.class);
		NoteRepository noteRepository=
				configurableApplicationContext.getBean(NoteRepository.class);
		UserRepository userRepository=
				configurableApplicationContext.getBean(UserRepository.class);

	}

}
