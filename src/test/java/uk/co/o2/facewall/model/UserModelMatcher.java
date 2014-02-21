package uk.co.o2.facewall.model;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import uk.co.o2.facewall.util.CompositeMatcher;

public class UserModelMatcher extends CompositeMatcher<UserModel> {

    private UserModelMatcher(){
        super();
    }

    public static UserModelMatcher aUserModel() {
        return new UserModelMatcher();
    }

    public UserModelMatcher named(final String name) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.name.equals(name);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with name " + name);
            }
        });
        return this;
    }

    public UserModelMatcher withImgUrl(final String imgUrl) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.imgUrl.equals(imgUrl);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with has the imageURL " + imgUrl);
            }
        });
        return this;
    }

    public UserModelMatcher withEmail(final String email) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.email.equals(email);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with email " + email);
            }
        });
        return this;
    }

    public UserModelMatcher withTeamName(final String teamName) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.team.equals(teamName);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with the team name " + teamName);
            }
        });
        return this;
    }

    public UserModelMatcher withScrumName(final String scrumName) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.scrum.equals(scrumName);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with the scrum named" + scrumName);
            }
        });
        return this;
    }

    public UserModelMatcher withRole(final String role) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.role.equals(role);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with the role" + role);
            }
        });
        return this;
    }

    public UserModelMatcher withLocation(final String location) {
        add(new TypeSafeMatcher<UserModel>() {
            @Override
            public boolean matchesSafely(UserModel target) {
                return target.location.equals(location);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with location " + location);
            }
        });
        return this;
    }
}
