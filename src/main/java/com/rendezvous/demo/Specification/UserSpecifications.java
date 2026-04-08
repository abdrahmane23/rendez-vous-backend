package com.rendezvous.demo.Specification;
import com.rendezvous.demo.domain.Entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
    public static Specification<User> FirstnameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("firstName")), "%" + name.toLowerCase() + "%");
        };
    }
    public static Specification<User> fieldEquals(String field) {
        return (root, query, cb) -> {
            if (field == null || field.isBlank()) return null;
            return cb.equal(root.get("field"), field);
        };
    }
}