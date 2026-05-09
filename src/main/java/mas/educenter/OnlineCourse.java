package mas.educenter;

import java.util.List;

// Disjoint subclass of Course - a course is online OR in-person, not both
public class OnlineCourse extends Course {

    private String platform;     // e.g. "Zoom", "Teams"
    private String videoUrl;

    public OnlineCourse(String title, int maxCapacity, List<Integer> lessonDurations,
                        String description, String platform, String videoUrl) {
        super(title, maxCapacity, lessonDurations, description);
        this.platform = platform;
        this.videoUrl = videoUrl;
    }

    public OnlineCourse(String title, int maxCapacity, String platform) {
        super(title, maxCapacity);
        this.platform = platform;
        this.videoUrl = null;
    }

    public String getPlatform() { return platform; }
    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    @Override
    public String toString() {
        return "OnlineCourse{title='" + getTitle() + "', platform='" + platform
                + "', maxCapacity=" + getMaxCapacity() + "}";
    }
}