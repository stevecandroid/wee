package db;

public class User {
   protected int id;
   protected String email;
   protected String password;
   protected int age;
   protected String nickname;
   protected String avatar;
   protected String friends;
   protected int status;

    public User(int id,int age, String email, String password, String nickname, String avatar, String friends, int status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
        this.nickname = nickname;
        this.avatar = avatar;
        this.friends = friends;
        this.status = status;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
