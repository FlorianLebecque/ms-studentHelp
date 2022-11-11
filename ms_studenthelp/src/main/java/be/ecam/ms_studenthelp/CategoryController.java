package be.ecam.ms_studenthelp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import be.ecam.ms_studenthelp.Database.entities.CategoryEntity;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import be.ecam.ms_studenthelp.Object.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/categories")
	public List<String> getCategories() {
		List<CategoryEntity> categories = categoryRepository.findAll();

		return categories
				.stream()
				.map(CategoryEntity::getTitle)
				.collect(Collectors.toList());
	}
}

