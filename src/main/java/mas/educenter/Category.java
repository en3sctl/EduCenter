package mas.educenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Category extends ObjectPlus {

    private String name;
    private String description;
    private Category parentCategory; // optional attribute (self-reference)

    private List<Category> subcategories = new ArrayList<>();

    // qualifier-based lookup for fast access
    private Map<String, Course> coursesByTitle = new HashMap<>();

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

    public void addSubcategory(Category child) {
        if (child == this) {
            return;
        }
        if (!subcategories.contains(child)) {
            subcategories.add(child);
            child.setParentCategory(this);
        }
    }

    public void removeSubcategory(Category child) {
        if (subcategories.contains(child)) {
            subcategories.remove(child);
            child.setParentCategory(null);
        }
    }

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void addCourse(Course course) {
        String key = course.getTitle();
        if (!coursesByTitle.containsKey(key)) {
            coursesByTitle.put(key, course);
            course.setCategory(this);
        }
    }

    public Course findCourseByTitle(String title) {
        return coursesByTitle.get(title);
    }

    public Map<String, Course> getCoursesByTitle() {
        return coursesByTitle;
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
