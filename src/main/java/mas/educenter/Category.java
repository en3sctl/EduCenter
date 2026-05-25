package mas.educenter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Table(name = "categories")
public class Category extends ObjectPlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // Recursive 1-* association - parent_id points back to the same table
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subcategories = new ArrayList<>();

    // Qualified association - the qualifier (course title) is used as the map key
    @OneToMany(mappedBy = "category")
    @MapKey(name = "title")
    private Map<String, Course> coursesByTitle = new HashMap<>();

    public Category() {
        super();
    }

    public Category(String name, String description, Category parentCategory) {
        super();
        this.name = name;
        this.description = description;
        this.parentCategory = parentCategory;
    }

    public Category(String name, String description) {
        this(name, description, null);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

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
