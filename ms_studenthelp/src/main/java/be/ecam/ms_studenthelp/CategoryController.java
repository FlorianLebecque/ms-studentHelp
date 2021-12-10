package be.ecam.ms_studenthelp;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

@RestController
public class CategoryController {

	@GetMapping("/categories")
	public GetCategoriesResult getCategories() {
        IIODatabaseObject db = MsStudenthelpApplication.DatabaseManager;

        List<String> categories = db.GetCategories();


		return new GetCategoriesResult(categories);
	}

	class GetCategoriesResult {
		public List<String> data;

		public GetCategoriesResult(List<String> data) {
			this.data = data;
		}
	}
}

