package mas.educenter;

import java.util.Optional;

public class Category extends ObjectPlus {

    private String name;
    private String description;
    private Category parentCategory; // optional attribute (self-reference)

    // Full constructor
    public Category(String name, String description, Category parentCategory) {
        super();
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
    }

    // Minimal constructor (no parent)
    public Category(String name, String description) {
        this(name, description, null);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    // Optional attribute
    public Optional<Category> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public String toString() {
        String parentName = "none";
        if (parentCategory != null) {
            parentName = parentCategory.getName();
        }
        return "Category{name='" + name + "', description='" + description
                + "', parent=" + parentName + "}";
    }
}
