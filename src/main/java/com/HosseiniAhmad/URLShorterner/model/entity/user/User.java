package com.HosseiniAhmad.URLShorterner.model.entity.user;

import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(exclude = {"urlBindings", "subscriptions"})
@ToString(exclude = {"urlBindings", "subscriptions"})
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "User.detail",
        attributeNodes = {@NamedAttributeNode("urlBindings"), @NamedAttributeNode("subscriptions")}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_NOT_CONFIRMED;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UrlBinding> urlBindings = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Subscription> subscriptions = new HashSet<>();

    public void addUrlBinding(UrlBinding binding) {
        setBidirectionalRef(binding, this.urlBindings, true, binding::setUser);
    }

    public void removeUrlBinding(UrlBinding binding) {
        setBidirectionalRef(binding, this.urlBindings, false, binding::setUser);
    }

    public void addSubscription(Subscription subscription) {
        setBidirectionalRef(subscription, this.subscriptions, true, subscription::setUser);
    }

    public void removeSubscription(Subscription subscription) {
        setBidirectionalRef(subscription, this.subscriptions, false, subscription::setUser);
    }

    private <T> void setBidirectionalRef(T element, Set<T> collection, boolean hasSetConnected, Consumer<User> setter) {
        Objects.requireNonNull(element, "Cannot be null");
        if (hasSetConnected) {
            collection.add(element);
            setter.accept(this);
        } else {
            collection.remove(element);
            setter.accept(null);
        }
    }

}
