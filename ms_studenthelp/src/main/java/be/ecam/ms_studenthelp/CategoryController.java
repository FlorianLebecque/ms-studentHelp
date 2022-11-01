package be.ecam.ms_studenthelp;

import java.util.ArrayList;
import java.util.List;


import be.ecam.ms_studenthelp.Database.entities.CategoryEntity;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
	static class GetCategoriesResult {
		public List<String> data;

		public GetCategoriesResult(List<String> data) {
			this.data = data;
		}
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/categories")
	public GetCategoriesResult getCategories() {
		List<String> categoryTitles = new ArrayList<String>();
		List<CategoryEntity> categories = categoryRepository.findAll();

		categories.forEach((CategoryEntity categoryEntity) -> { categoryTitles.add(categoryEntity.getTitle()); });

		return new GetCategoriesResult(categoryTitles);
	}
}

