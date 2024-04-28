package mirea.mobile.kamilla.reviews;

public class Review {
    private String id;
    private String name;
    private String description;
    private int mark;

    public Review(String id, String name, int mark, String description) {
        this.setId(id);
        this.setName(name);
        this.setMark(mark);
        this.setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}