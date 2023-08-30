public class Event {
    public String event_name;
    public int id;
    public String created_at;
    public String event_description;
    public String class_type;
    public int syllabus_id;
    public String updated_at;
    public String event_date;


    @Override
    public String toString() {
        return
                "\n\nEvent - '" + event_name + '\'' +
                "\n ID - " + id +
                "\n Creation - '" + created_at + '\'' +
                "\n Desc - " + event_description + '\'' +
                "\n Type - " + class_type + '\'' +
                "\n Syllabus ID - " + syllabus_id +
                "\n Updated - " + updated_at + '\'' +
                "\n Event Date - '" + event_date + '\'' +
                '}';
    }

}
