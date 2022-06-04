package group3.mvc.model;



public class UserHolder {
    private static MyUser currentUser;

    public static void setCurrentUser(MyUser user){
        currentUser = user;
    }

    public static MyUser getCurrentUser(){
        return currentUser;
    }
}
