package Question7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialGraph {
    private Map<String, User> users;
    private List<Connection> connections;

    public SocialGraph() {
        users = new HashMap<>();
        connections = new ArrayList<>();
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addConnection(User user1, User user2) {
        Connection connection = new Connection(user1, user2);
        connections.add(connection);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Social Graph Content:\n");

        for (User user : users.values()) {
            result.append(user.getUsername()).append("\n");
        }

        result.append("Connections:\n");
        for (Connection connection : connections) {
            result.append(connection.getUser1().getUsername())
                    .append(" - ")
                    .append(connection.getUser2().getUsername())
                    .append("\n");
        }

        return result.toString();
    }

    private static class Connection {
        private User user1;
        private User user2;

        public Connection(User user1, User user2) {
            this.user1 = user1;
            this.user2 = user2;
        }

        public User getUser1() {
            return user1;
        }

        public User getUser2() {
            return user2;
        }
    }

    public boolean containsUser(String username) {
        return users.containsKey(username);
    }
}