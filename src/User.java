public class User {
    public int id;
    public String name;
    public String email;
    public String email_verified_at;
    public String two_factor_confirmed_at;
    public String current_team_id;
    public String profile_photo_path;
    public String created_at;
    public String updated_at;
    public String profile_photo_url;

    @Override
    public String toString() {
        return "\n UserID: " + id + " || User:" + name;
    }

}
