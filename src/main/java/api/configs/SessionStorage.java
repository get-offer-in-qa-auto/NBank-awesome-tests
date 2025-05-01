package api.configs;

import api.models.CreateUserRequest;
import api.requests.steps.UserSteps;

import java.util.*;

public class SessionStorage {

    private static final SessionStorage INSTANCE = new SessionStorage();

    private final LinkedHashMap<CreateUserRequest, UserSteps> userStepsMap = new LinkedHashMap<>();

    private SessionStorage() {}

    public static SessionStorage getInstance() {
        return INSTANCE;
    }

    public static void addUsers(List<CreateUserRequest> users) {
        for (CreateUserRequest user : users) {
            INSTANCE.userStepsMap.put(user, new UserSteps(user.getUsername(), user.getPassword()));
        }
    }

    public static Map<CreateUserRequest, UserSteps> getUserStepsMap() {
        return Collections.unmodifiableMap(INSTANCE.userStepsMap);
    }

    public static List<CreateUserRequest> getAllUsers() {
        return new ArrayList<>(INSTANCE.userStepsMap.keySet());
    }

    public static List<UserSteps> getAllSteps() {
        return new ArrayList<>(INSTANCE.userStepsMap.values());
    }

    public static CreateUserRequest getUser(int index) {
        return getAllUsers().get(index);
    }

    public static UserSteps getSteps(int index) {
        return getAllSteps().get(index);
    }

    public static CreateUserRequest getUser() {
        return getUser(0);
    }

    public static UserSteps getSteps() {
        return getSteps(0);
    }

    public static UserSteps getSteps(CreateUserRequest user) {
        return INSTANCE.userStepsMap.get(user);
    }

    public static void clear() {
        INSTANCE.userStepsMap.clear();
    }
}
