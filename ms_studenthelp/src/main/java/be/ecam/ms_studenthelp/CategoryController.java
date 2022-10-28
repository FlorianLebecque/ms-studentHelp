package be.ecam.ms_studenthelp;

import java.util.ArrayList;
import java.util.List;


import be.ecam.ms_studenthelp.Database.entities.Category;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

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
		List<Category> categories = categoryRepository.findAll();

		categories.forEach((Category category) -> { categoryTitles.add(category.getTitle()); });

		System.out.println(categoryTitles);
		return new GetCategoriesResult(categoryTitles);
	}
}

